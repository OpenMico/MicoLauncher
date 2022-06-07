package androidx.work.impl.background.greedy;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Configuration;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Scheduler;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.ProcessUtils;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.xiaomi.mipush.sdk.Constants;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class GreedyScheduler implements ExecutionListener, Scheduler, WorkConstraintsCallback {
    private static final String b = Logger.tagWithPrefix("GreedyScheduler");
    Boolean a;
    private final Context c;
    private final WorkManagerImpl d;
    private final WorkConstraintsTracker e;
    private DelayedWorkTracker g;
    private boolean h;
    private final Set<WorkSpec> f = new HashSet();
    private final Object i = new Object();

    @Override // androidx.work.impl.Scheduler
    public boolean hasLimitedSchedulingSlots() {
        return false;
    }

    public GreedyScheduler(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor taskExecutor, @NonNull WorkManagerImpl workManagerImpl) {
        this.c = context;
        this.d = workManagerImpl;
        this.e = new WorkConstraintsTracker(context, taskExecutor, this);
        this.g = new DelayedWorkTracker(this, configuration.getRunnableScheduler());
    }

    @VisibleForTesting
    public GreedyScheduler(@NonNull Context context, @NonNull WorkManagerImpl workManagerImpl, @NonNull WorkConstraintsTracker workConstraintsTracker) {
        this.c = context;
        this.d = workManagerImpl;
        this.e = workConstraintsTracker;
    }

    @VisibleForTesting
    public void setDelayedWorkTracker(@NonNull DelayedWorkTracker delayedWorkTracker) {
        this.g = delayedWorkTracker;
    }

    @Override // androidx.work.impl.Scheduler
    public void schedule(@NonNull WorkSpec... workSpecArr) {
        if (this.a == null) {
            a();
        }
        if (!this.a.booleanValue()) {
            Logger.get().info(b, "Ignoring schedule request in a secondary process", new Throwable[0]);
            return;
        }
        b();
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        for (WorkSpec workSpec : workSpecArr) {
            long calculateNextRunTime = workSpec.calculateNextRunTime();
            long currentTimeMillis = System.currentTimeMillis();
            if (workSpec.state == WorkInfo.State.ENQUEUED) {
                if (currentTimeMillis < calculateNextRunTime) {
                    DelayedWorkTracker delayedWorkTracker = this.g;
                    if (delayedWorkTracker != null) {
                        delayedWorkTracker.schedule(workSpec);
                    }
                } else if (!workSpec.hasConstraints()) {
                    Logger.get().debug(b, String.format("Starting work for %s", workSpec.id), new Throwable[0]);
                    this.d.startWork(workSpec.id);
                } else if (Build.VERSION.SDK_INT >= 23 && workSpec.constraints.requiresDeviceIdle()) {
                    Logger.get().debug(b, String.format("Ignoring WorkSpec %s, Requires device idle.", workSpec), new Throwable[0]);
                } else if (Build.VERSION.SDK_INT < 24 || !workSpec.constraints.hasContentUriTriggers()) {
                    hashSet.add(workSpec);
                    hashSet2.add(workSpec.id);
                } else {
                    Logger.get().debug(b, String.format("Ignoring WorkSpec %s, Requires ContentUri triggers.", workSpec), new Throwable[0]);
                }
            }
        }
        synchronized (this.i) {
            if (!hashSet.isEmpty()) {
                Logger.get().debug(b, String.format("Starting tracking for [%s]", TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, hashSet2)), new Throwable[0]);
                this.f.addAll(hashSet);
                this.e.replace(this.f);
            }
        }
    }

    private void a() {
        this.a = Boolean.valueOf(ProcessUtils.isDefaultProcess(this.c, this.d.getConfiguration()));
    }

    @Override // androidx.work.impl.Scheduler
    public void cancel(@NonNull String str) {
        if (this.a == null) {
            a();
        }
        if (!this.a.booleanValue()) {
            Logger.get().info(b, "Ignoring schedule request in non-main process", new Throwable[0]);
            return;
        }
        b();
        Logger.get().debug(b, String.format("Cancelling work ID %s", str), new Throwable[0]);
        DelayedWorkTracker delayedWorkTracker = this.g;
        if (delayedWorkTracker != null) {
            delayedWorkTracker.unschedule(str);
        }
        this.d.stopWork(str);
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsCallback
    public void onAllConstraintsMet(@NonNull List<String> list) {
        for (String str : list) {
            Logger.get().debug(b, String.format("Constraints met: Scheduling work ID %s", str), new Throwable[0]);
            this.d.startWork(str);
        }
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsCallback
    public void onAllConstraintsNotMet(@NonNull List<String> list) {
        for (String str : list) {
            Logger.get().debug(b, String.format("Constraints not met: Cancelling work ID %s", str), new Throwable[0]);
            this.d.stopWork(str);
        }
    }

    @Override // androidx.work.impl.ExecutionListener
    public void onExecuted(@NonNull String str, boolean z) {
        a(str);
    }

    private void a(@NonNull String str) {
        synchronized (this.i) {
            Iterator<WorkSpec> it = this.f.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WorkSpec next = it.next();
                if (next.id.equals(str)) {
                    Logger.get().debug(b, String.format("Stopping tracking for %s", str), new Throwable[0]);
                    this.f.remove(next);
                    this.e.replace(this.f);
                    break;
                }
            }
        }
    }

    private void b() {
        if (!this.h) {
            this.d.getProcessor().addExecutionListener(this);
            this.h = true;
        }
    }
}
