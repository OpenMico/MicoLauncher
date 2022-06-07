package androidx.work.impl.workers;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.ListenableWorker;
import androidx.work.Logger;
import androidx.work.WorkerParameters;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class ConstraintTrackingWorker extends ListenableWorker implements WorkConstraintsCallback {
    public static final String ARGUMENT_CLASS_NAME = "androidx.work.impl.workers.ConstraintTrackingWorker.ARGUMENT_CLASS_NAME";
    private static final String d = Logger.tagWithPrefix("ConstraintTrkngWrkr");
    final Object a = new Object();
    volatile boolean b = false;
    SettableFuture<ListenableWorker.Result> c = SettableFuture.create();
    private WorkerParameters e;
    @Nullable
    private ListenableWorker f;

    @Override // androidx.work.impl.constraints.WorkConstraintsCallback
    public void onAllConstraintsMet(@NonNull List<String> list) {
    }

    public ConstraintTrackingWorker(@NonNull Context context, @NonNull WorkerParameters workerParameters) {
        super(context, workerParameters);
        this.e = workerParameters;
    }

    @Override // androidx.work.ListenableWorker
    @NonNull
    public ListenableFuture<ListenableWorker.Result> startWork() {
        getBackgroundExecutor().execute(new Runnable() { // from class: androidx.work.impl.workers.ConstraintTrackingWorker.1
            @Override // java.lang.Runnable
            public void run() {
                ConstraintTrackingWorker.this.a();
            }
        });
        return this.c;
    }

    void a() {
        String string = getInputData().getString(ARGUMENT_CLASS_NAME);
        if (TextUtils.isEmpty(string)) {
            Logger.get().error(d, "No worker to delegate to.", new Throwable[0]);
            b();
            return;
        }
        this.f = getWorkerFactory().createWorkerWithDefaultFallback(getApplicationContext(), string, this.e);
        if (this.f == null) {
            Logger.get().debug(d, "No worker to delegate to.", new Throwable[0]);
            b();
            return;
        }
        WorkSpec workSpec = getWorkDatabase().workSpecDao().getWorkSpec(getId().toString());
        if (workSpec == null) {
            b();
            return;
        }
        WorkConstraintsTracker workConstraintsTracker = new WorkConstraintsTracker(getApplicationContext(), getTaskExecutor(), this);
        workConstraintsTracker.replace(Collections.singletonList(workSpec));
        if (workConstraintsTracker.areAllConstraintsMet(getId().toString())) {
            Logger.get().debug(d, String.format("Constraints met for delegate %s", string), new Throwable[0]);
            try {
                final ListenableFuture<ListenableWorker.Result> startWork = this.f.startWork();
                startWork.addListener(new Runnable() { // from class: androidx.work.impl.workers.ConstraintTrackingWorker.2
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (ConstraintTrackingWorker.this.a) {
                            if (ConstraintTrackingWorker.this.b) {
                                ConstraintTrackingWorker.this.c();
                            } else {
                                ConstraintTrackingWorker.this.c.setFuture(startWork);
                            }
                        }
                    }
                }, getBackgroundExecutor());
            } catch (Throwable th) {
                Logger.get().debug(d, String.format("Delegated worker %s threw exception in startWork.", string), th);
                synchronized (this.a) {
                    try {
                        if (this.b) {
                            Logger.get().debug(d, "Constraints were unmet, Retrying.", new Throwable[0]);
                            c();
                        } else {
                            b();
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
            }
        } else {
            Logger.get().debug(d, String.format("Constraints not met for delegate %s. Requesting retry.", string), new Throwable[0]);
            c();
        }
    }

    void b() {
        this.c.set(ListenableWorker.Result.failure());
    }

    void c() {
        this.c.set(ListenableWorker.Result.retry());
    }

    @Override // androidx.work.ListenableWorker
    public void onStopped() {
        super.onStopped();
        ListenableWorker listenableWorker = this.f;
        if (listenableWorker != null && !listenableWorker.isStopped()) {
            this.f.stop();
        }
    }

    @Override // androidx.work.ListenableWorker
    public boolean isRunInForeground() {
        ListenableWorker listenableWorker = this.f;
        return listenableWorker != null && listenableWorker.isRunInForeground();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public WorkDatabase getWorkDatabase() {
        return WorkManagerImpl.getInstance(getApplicationContext()).getWorkDatabase();
    }

    @Override // androidx.work.ListenableWorker
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public TaskExecutor getTaskExecutor() {
        return WorkManagerImpl.getInstance(getApplicationContext()).getWorkTaskExecutor();
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public ListenableWorker getDelegate() {
        return this.f;
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsCallback
    public void onAllConstraintsNotMet(@NonNull List<String> list) {
        Logger.get().debug(d, String.format("Constraints changed for %s", list), new Throwable[0]);
        synchronized (this.a) {
            this.b = true;
        }
    }
}
