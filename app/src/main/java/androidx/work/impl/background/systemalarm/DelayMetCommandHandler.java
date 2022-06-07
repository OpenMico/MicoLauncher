package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.background.systemalarm.SystemAlarmDispatcher;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.WakeLocks;
import androidx.work.impl.utils.WorkTimer;
import java.util.Collections;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class DelayMetCommandHandler implements ExecutionListener, WorkConstraintsCallback, WorkTimer.TimeLimitExceededListener {
    private static final String a = Logger.tagWithPrefix("DelayMetCommandHandler");
    private final Context b;
    private final int c;
    private final String d;
    private final SystemAlarmDispatcher e;
    private final WorkConstraintsTracker f;
    @Nullable
    private PowerManager.WakeLock i;
    private boolean j = false;
    private int h = 0;
    private final Object g = new Object();

    /* JADX INFO: Access modifiers changed from: package-private */
    public DelayMetCommandHandler(@NonNull Context context, int i, @NonNull String str, @NonNull SystemAlarmDispatcher systemAlarmDispatcher) {
        this.b = context;
        this.c = i;
        this.e = systemAlarmDispatcher;
        this.d = str;
        this.f = new WorkConstraintsTracker(this.b, systemAlarmDispatcher.e(), this);
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsCallback
    public void onAllConstraintsMet(@NonNull List<String> list) {
        if (list.contains(this.d)) {
            synchronized (this.g) {
                if (this.h == 0) {
                    this.h = 1;
                    Logger.get().debug(a, String.format("onAllConstraintsMet for %s", this.d), new Throwable[0]);
                    if (this.e.b().startWork(this.d)) {
                        this.e.c().startTimer(this.d, 600000L, this);
                    } else {
                        c();
                    }
                } else {
                    Logger.get().debug(a, String.format("Already started work for %s", this.d), new Throwable[0]);
                }
            }
        }
    }

    @Override // androidx.work.impl.ExecutionListener
    public void onExecuted(@NonNull String str, boolean z) {
        Logger.get().debug(a, String.format("onExecuted %s, %s", str, Boolean.valueOf(z)), new Throwable[0]);
        c();
        if (z) {
            Intent a2 = CommandHandler.a(this.b, this.d);
            SystemAlarmDispatcher systemAlarmDispatcher = this.e;
            systemAlarmDispatcher.a(new SystemAlarmDispatcher.a(systemAlarmDispatcher, a2, this.c));
        }
        if (this.j) {
            Intent a3 = CommandHandler.a(this.b);
            SystemAlarmDispatcher systemAlarmDispatcher2 = this.e;
            systemAlarmDispatcher2.a(new SystemAlarmDispatcher.a(systemAlarmDispatcher2, a3, this.c));
        }
    }

    @Override // androidx.work.impl.utils.WorkTimer.TimeLimitExceededListener
    public void onTimeLimitExceeded(@NonNull String str) {
        Logger.get().debug(a, String.format("Exceeded time limits on execution for %s", str), new Throwable[0]);
        b();
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsCallback
    public void onAllConstraintsNotMet(@NonNull List<String> list) {
        b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @WorkerThread
    public void a() {
        this.i = WakeLocks.newWakeLock(this.b, String.format("%s (%s)", this.d, Integer.valueOf(this.c)));
        Logger.get().debug(a, String.format("Acquiring wakelock %s for WorkSpec %s", this.i, this.d), new Throwable[0]);
        this.i.acquire();
        WorkSpec workSpec = this.e.d().getWorkDatabase().workSpecDao().getWorkSpec(this.d);
        if (workSpec == null) {
            b();
            return;
        }
        this.j = workSpec.hasConstraints();
        if (!this.j) {
            Logger.get().debug(a, String.format("No constraints for %s", this.d), new Throwable[0]);
            onAllConstraintsMet(Collections.singletonList(this.d));
            return;
        }
        this.f.replace(Collections.singletonList(workSpec));
    }

    private void b() {
        synchronized (this.g) {
            if (this.h < 2) {
                this.h = 2;
                Logger.get().debug(a, String.format("Stopping work for WorkSpec %s", this.d), new Throwable[0]);
                this.e.a(new SystemAlarmDispatcher.a(this.e, CommandHandler.c(this.b, this.d), this.c));
                if (this.e.b().isEnqueued(this.d)) {
                    Logger.get().debug(a, String.format("WorkSpec %s needs to be rescheduled", this.d), new Throwable[0]);
                    this.e.a(new SystemAlarmDispatcher.a(this.e, CommandHandler.a(this.b, this.d), this.c));
                } else {
                    Logger.get().debug(a, String.format("Processor does not have WorkSpec %s. No need to reschedule ", this.d), new Throwable[0]);
                }
            } else {
                Logger.get().debug(a, String.format("Already stopped work for %s", this.d), new Throwable[0]);
            }
        }
    }

    private void c() {
        synchronized (this.g) {
            this.f.reset();
            this.e.c().stopTimer(this.d);
            if (this.i != null && this.i.isHeld()) {
                Logger.get().debug(a, String.format("Releasing wakelock %s for WorkSpec %s", this.i, this.d), new Throwable[0]);
                this.i.release();
            }
        }
    }
}
