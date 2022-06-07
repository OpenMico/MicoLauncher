package androidx.work.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.work.Configuration;
import androidx.work.Data;
import androidx.work.InputMerger;
import androidx.work.ListenableWorker;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.WorkerParameters;
import androidx.work.impl.background.systemalarm.RescheduleReceiver;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTagDao;
import androidx.work.impl.utils.PackageManagerHelper;
import androidx.work.impl.utils.WorkForegroundUpdater;
import androidx.work.impl.utils.WorkProgressUpdater;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WorkerWrapper implements Runnable {
    static final String a = Logger.tagWithPrefix("WorkerWrapper");
    Context b;
    WorkSpec c;
    ListenableWorker d;
    @NonNull
    ListenableWorker.Result e = ListenableWorker.Result.failure();
    @NonNull
    SettableFuture<Boolean> f = SettableFuture.create();
    @Nullable
    ListenableFuture<ListenableWorker.Result> g = null;
    private String h;
    private List<Scheduler> i;
    private WorkerParameters.RuntimeExtras j;
    private Configuration k;
    private TaskExecutor l;
    private ForegroundProcessor m;
    private WorkDatabase n;
    private WorkSpecDao o;
    private DependencyDao p;
    private WorkTagDao q;
    private List<String> r;
    private String s;
    private volatile boolean t;

    WorkerWrapper(@NonNull Builder builder) {
        this.b = builder.a;
        this.l = builder.d;
        this.m = builder.c;
        this.h = builder.g;
        this.i = builder.h;
        this.j = builder.i;
        this.d = builder.b;
        this.k = builder.e;
        this.n = builder.f;
        this.o = this.n.workSpecDao();
        this.p = this.n.dependencyDao();
        this.q = this.n.workTagDao();
    }

    @NonNull
    public ListenableFuture<Boolean> getFuture() {
        return this.f;
    }

    @Override // java.lang.Runnable
    @WorkerThread
    public void run() {
        this.r = this.q.getTagsForWorkSpecId(this.h);
        this.s = a(this.r);
        c();
    }

    private void c() {
        Data data;
        if (!e()) {
            this.n.beginTransaction();
            try {
                this.c = this.o.getWorkSpec(this.h);
                if (this.c == null) {
                    Logger.get().error(a, String.format("Didn't find WorkSpec for id %s", this.h), new Throwable[0]);
                    a(false);
                    this.n.setTransactionSuccessful();
                } else if (this.c.state != WorkInfo.State.ENQUEUED) {
                    d();
                    this.n.setTransactionSuccessful();
                    Logger.get().debug(a, String.format("%s is not in ENQUEUED state. Nothing more to do.", this.c.workerClassName), new Throwable[0]);
                } else {
                    if (this.c.isPeriodic() || this.c.isBackedOff()) {
                        long currentTimeMillis = System.currentTimeMillis();
                        if (!(this.c.periodStartTime == 0) && currentTimeMillis < this.c.calculateNextRunTime()) {
                            Logger.get().debug(a, String.format("Delaying execution for %s because it is being executed before schedule.", this.c.workerClassName), new Throwable[0]);
                            a(true);
                            this.n.setTransactionSuccessful();
                            return;
                        }
                    }
                    this.n.setTransactionSuccessful();
                    this.n.endTransaction();
                    if (this.c.isPeriodic()) {
                        data = this.c.input;
                    } else {
                        InputMerger createInputMergerWithDefaultFallback = this.k.getInputMergerFactory().createInputMergerWithDefaultFallback(this.c.inputMergerClassName);
                        if (createInputMergerWithDefaultFallback == null) {
                            Logger.get().error(a, String.format("Could not create Input Merger %s", this.c.inputMergerClassName), new Throwable[0]);
                            b();
                            return;
                        }
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(this.c.input);
                        arrayList.addAll(this.o.getInputsFromPrerequisites(this.h));
                        data = createInputMergerWithDefaultFallback.merge(arrayList);
                    }
                    WorkerParameters workerParameters = new WorkerParameters(UUID.fromString(this.h), data, this.r, this.j, this.c.runAttemptCount, this.k.getExecutor(), this.l, this.k.getWorkerFactory(), new WorkProgressUpdater(this.n, this.l), new WorkForegroundUpdater(this.n, this.m, this.l));
                    if (this.d == null) {
                        this.d = this.k.getWorkerFactory().createWorkerWithDefaultFallback(this.b, this.c.workerClassName, workerParameters);
                    }
                    ListenableWorker listenableWorker = this.d;
                    if (listenableWorker == null) {
                        Logger.get().error(a, String.format("Could not create Worker %s", this.c.workerClassName), new Throwable[0]);
                        b();
                    } else if (listenableWorker.isUsed()) {
                        Logger.get().error(a, String.format("Received an already-used Worker %s; WorkerFactory should return new instances", this.c.workerClassName), new Throwable[0]);
                        b();
                    } else {
                        this.d.setUsed();
                        if (!f()) {
                            d();
                        } else if (!e()) {
                            final SettableFuture create = SettableFuture.create();
                            this.l.getMainThreadExecutor().execute(new Runnable() { // from class: androidx.work.impl.WorkerWrapper.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    try {
                                        Logger.get().debug(WorkerWrapper.a, String.format("Starting work for %s", WorkerWrapper.this.c.workerClassName), new Throwable[0]);
                                        WorkerWrapper.this.g = WorkerWrapper.this.d.startWork();
                                        create.setFuture(WorkerWrapper.this.g);
                                    } catch (Throwable th) {
                                        create.setException(th);
                                    }
                                }
                            });
                            final String str = this.s;
                            create.addListener(new Runnable() { // from class: androidx.work.impl.WorkerWrapper.2
                                @Override // java.lang.Runnable
                                @SuppressLint({"SyntheticAccessor"})
                                public void run() {
                                    Throwable e;
                                    try {
                                        try {
                                            ListenableWorker.Result result = (ListenableWorker.Result) create.get();
                                            if (result == null) {
                                                Logger.get().error(WorkerWrapper.a, String.format("%s returned a null result. Treating it as a failure.", WorkerWrapper.this.c.workerClassName), new Throwable[0]);
                                            } else {
                                                Logger.get().debug(WorkerWrapper.a, String.format("%s returned a %s result.", WorkerWrapper.this.c.workerClassName, result), new Throwable[0]);
                                                WorkerWrapper.this.e = result;
                                            }
                                        } catch (InterruptedException e2) {
                                            e = e2;
                                            Logger.get().error(WorkerWrapper.a, String.format("%s failed because it threw an exception/error", str), e);
                                        } catch (CancellationException e3) {
                                            Logger.get().info(WorkerWrapper.a, String.format("%s was cancelled", str), e3);
                                        } catch (ExecutionException e4) {
                                            e = e4;
                                            Logger.get().error(WorkerWrapper.a, String.format("%s failed because it threw an exception/error", str), e);
                                        }
                                    } finally {
                                        WorkerWrapper.this.a();
                                    }
                                }
                            }, this.l.getBackgroundExecutor());
                        }
                    }
                }
            } finally {
                this.n.endTransaction();
            }
        }
    }

    void a() {
        if (!e()) {
            this.n.beginTransaction();
            try {
                WorkInfo.State state = this.o.getState(this.h);
                this.n.workProgressDao().delete(this.h);
                if (state == null) {
                    a(false);
                } else if (state == WorkInfo.State.RUNNING) {
                    a(this.e);
                } else if (!state.isFinished()) {
                    g();
                }
                this.n.setTransactionSuccessful();
            } finally {
                this.n.endTransaction();
            }
        }
        List<Scheduler> list = this.i;
        if (list != null) {
            for (Scheduler scheduler : list) {
                scheduler.cancel(this.h);
            }
            Schedulers.schedule(this.k, this.n, this.i);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void interrupt() {
        boolean z;
        this.t = true;
        e();
        ListenableFuture<ListenableWorker.Result> listenableFuture = this.g;
        if (listenableFuture != null) {
            z = listenableFuture.isDone();
            this.g.cancel(true);
        } else {
            z = false;
        }
        ListenableWorker listenableWorker = this.d;
        if (listenableWorker == null || z) {
            Logger.get().debug(a, String.format("WorkSpec %s is already done. Not interrupting.", this.c), new Throwable[0]);
        } else {
            listenableWorker.stop();
        }
    }

    private void d() {
        WorkInfo.State state = this.o.getState(this.h);
        if (state == WorkInfo.State.RUNNING) {
            Logger.get().debug(a, String.format("Status for %s is RUNNING;not doing any work and rescheduling for later execution", this.h), new Throwable[0]);
            a(true);
            return;
        }
        Logger.get().debug(a, String.format("Status for %s is %s; not doing any work", this.h, state), new Throwable[0]);
        a(false);
    }

    private boolean e() {
        if (!this.t) {
            return false;
        }
        Logger.get().debug(a, String.format("Work interrupted for %s", this.s), new Throwable[0]);
        WorkInfo.State state = this.o.getState(this.h);
        if (state == null) {
            a(false);
        } else {
            a(!state.isFinished());
        }
        return true;
    }

    /* JADX WARN: Finally extract failed */
    private void a(boolean z) {
        this.n.beginTransaction();
        try {
            if (!this.n.workSpecDao().hasUnfinishedWork()) {
                PackageManagerHelper.setComponentEnabled(this.b, RescheduleReceiver.class, false);
            }
            if (z) {
                this.o.setState(WorkInfo.State.ENQUEUED, this.h);
                this.o.markWorkSpecScheduled(this.h, -1L);
            }
            if (!(this.c == null || this.d == null || !this.d.isRunInForeground())) {
                this.m.stopForeground(this.h);
            }
            this.n.setTransactionSuccessful();
            this.n.endTransaction();
            this.f.set(Boolean.valueOf(z));
        } catch (Throwable th) {
            this.n.endTransaction();
            throw th;
        }
    }

    private void a(ListenableWorker.Result result) {
        if (result instanceof ListenableWorker.Result.Success) {
            Logger.get().info(a, String.format("Worker result SUCCESS for %s", this.s), new Throwable[0]);
            if (this.c.isPeriodic()) {
                h();
            } else {
                i();
            }
        } else if (result instanceof ListenableWorker.Result.Retry) {
            Logger.get().info(a, String.format("Worker result RETRY for %s", this.s), new Throwable[0]);
            g();
        } else {
            Logger.get().info(a, String.format("Worker result FAILURE for %s", this.s), new Throwable[0]);
            if (this.c.isPeriodic()) {
                h();
            } else {
                b();
            }
        }
    }

    private boolean f() {
        this.n.beginTransaction();
        try {
            boolean z = true;
            if (this.o.getState(this.h) == WorkInfo.State.ENQUEUED) {
                this.o.setState(WorkInfo.State.RUNNING, this.h);
                this.o.incrementWorkSpecRunAttemptCount(this.h);
            } else {
                z = false;
            }
            this.n.setTransactionSuccessful();
            return z;
        } finally {
            this.n.endTransaction();
        }
    }

    @VisibleForTesting
    void b() {
        this.n.beginTransaction();
        try {
            a(this.h);
            this.o.setOutput(this.h, ((ListenableWorker.Result.Failure) this.e).getOutputData());
            this.n.setTransactionSuccessful();
        } finally {
            this.n.endTransaction();
            a(false);
        }
    }

    private void a(String str) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(str);
        while (!linkedList.isEmpty()) {
            String str2 = (String) linkedList.remove();
            if (this.o.getState(str2) != WorkInfo.State.CANCELLED) {
                this.o.setState(WorkInfo.State.FAILED, str2);
            }
            linkedList.addAll(this.p.getDependentWorkIds(str2));
        }
    }

    private void g() {
        this.n.beginTransaction();
        try {
            this.o.setState(WorkInfo.State.ENQUEUED, this.h);
            this.o.setPeriodStartTime(this.h, System.currentTimeMillis());
            this.o.markWorkSpecScheduled(this.h, -1L);
            this.n.setTransactionSuccessful();
        } finally {
            this.n.endTransaction();
            a(true);
        }
    }

    private void h() {
        this.n.beginTransaction();
        try {
            this.o.setPeriodStartTime(this.h, System.currentTimeMillis());
            this.o.setState(WorkInfo.State.ENQUEUED, this.h);
            this.o.resetWorkSpecRunAttemptCount(this.h);
            this.o.markWorkSpecScheduled(this.h, -1L);
            this.n.setTransactionSuccessful();
        } finally {
            this.n.endTransaction();
            a(false);
        }
    }

    private void i() {
        this.n.beginTransaction();
        try {
            this.o.setState(WorkInfo.State.SUCCEEDED, this.h);
            this.o.setOutput(this.h, ((ListenableWorker.Result.Success) this.e).getOutputData());
            long currentTimeMillis = System.currentTimeMillis();
            for (String str : this.p.getDependentWorkIds(this.h)) {
                if (this.o.getState(str) == WorkInfo.State.BLOCKED && this.p.hasCompletedAllPrerequisites(str)) {
                    Logger.get().info(a, String.format("Setting status to enqueued for %s", str), new Throwable[0]);
                    this.o.setState(WorkInfo.State.ENQUEUED, str);
                    this.o.setPeriodStartTime(str, currentTimeMillis);
                }
            }
            this.n.setTransactionSuccessful();
        } finally {
            this.n.endTransaction();
            a(false);
        }
    }

    private String a(List<String> list) {
        StringBuilder sb = new StringBuilder("Work [ id=");
        sb.append(this.h);
        sb.append(", tags={ ");
        boolean z = true;
        for (String str : list) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(str);
        }
        sb.append(" } ]");
        return sb.toString();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public static class Builder {
        @NonNull
        Context a;
        @Nullable
        ListenableWorker b;
        @NonNull
        ForegroundProcessor c;
        @NonNull
        TaskExecutor d;
        @NonNull
        Configuration e;
        @NonNull
        WorkDatabase f;
        @NonNull
        String g;
        List<Scheduler> h;
        @NonNull
        WorkerParameters.RuntimeExtras i = new WorkerParameters.RuntimeExtras();

        public Builder(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor taskExecutor, @NonNull ForegroundProcessor foregroundProcessor, @NonNull WorkDatabase workDatabase, @NonNull String str) {
            this.a = context.getApplicationContext();
            this.d = taskExecutor;
            this.c = foregroundProcessor;
            this.e = configuration;
            this.f = workDatabase;
            this.g = str;
        }

        @NonNull
        public Builder withSchedulers(@NonNull List<Scheduler> list) {
            this.h = list;
            return this;
        }

        @NonNull
        public Builder withRuntimeExtras(@Nullable WorkerParameters.RuntimeExtras runtimeExtras) {
            if (runtimeExtras != null) {
                this.i = runtimeExtras;
            }
            return this;
        }

        @NonNull
        @VisibleForTesting
        public Builder withWorker(@NonNull ListenableWorker listenableWorker) {
            this.b = listenableWorker;
            return this;
        }

        public WorkerWrapper build() {
            return new WorkerWrapper(this);
        }
    }
}
