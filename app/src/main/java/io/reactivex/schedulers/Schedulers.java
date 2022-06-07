package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.NonNull;
import io.reactivex.internal.schedulers.ComputationScheduler;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.internal.schedulers.SchedulerPoolFactory;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

/* loaded from: classes5.dex */
public final class Schedulers {
    @NonNull
    static final Scheduler a = RxJavaPlugins.initSingleScheduler(new h());
    @NonNull
    static final Scheduler b = RxJavaPlugins.initComputationScheduler(new b());
    @NonNull
    static final Scheduler c = RxJavaPlugins.initIoScheduler(new c());
    @NonNull
    static final Scheduler d = TrampolineScheduler.instance();
    @NonNull
    static final Scheduler e = RxJavaPlugins.initNewThreadScheduler(new f());

    /* loaded from: classes5.dex */
    public static final class a {
        static final Scheduler a = new ComputationScheduler();
    }

    /* loaded from: classes5.dex */
    public static final class d {
        static final Scheduler a = new IoScheduler();
    }

    /* loaded from: classes5.dex */
    public static final class e {
        static final Scheduler a = new NewThreadScheduler();
    }

    /* loaded from: classes5.dex */
    public static final class g {
        static final Scheduler a = new SingleScheduler();
    }

    private Schedulers() {
        throw new IllegalStateException("No instances!");
    }

    @NonNull
    public static Scheduler computation() {
        return RxJavaPlugins.onComputationScheduler(b);
    }

    @NonNull
    public static Scheduler io() {
        return RxJavaPlugins.onIoScheduler(c);
    }

    @NonNull
    public static Scheduler trampoline() {
        return d;
    }

    @NonNull
    public static Scheduler newThread() {
        return RxJavaPlugins.onNewThreadScheduler(e);
    }

    @NonNull
    public static Scheduler single() {
        return RxJavaPlugins.onSingleScheduler(a);
    }

    @NonNull
    public static Scheduler from(@NonNull Executor executor) {
        return new ExecutorScheduler(executor, false);
    }

    @Experimental
    @NonNull
    public static Scheduler from(@NonNull Executor executor, boolean z) {
        return new ExecutorScheduler(executor, z);
    }

    public static void shutdown() {
        computation().shutdown();
        io().shutdown();
        newThread().shutdown();
        single().shutdown();
        trampoline().shutdown();
        SchedulerPoolFactory.shutdown();
    }

    public static void start() {
        computation().start();
        io().start();
        newThread().start();
        single().start();
        trampoline().start();
        SchedulerPoolFactory.start();
    }

    /* loaded from: classes5.dex */
    static final class c implements Callable<Scheduler> {
        c() {
        }

        /* renamed from: a */
        public Scheduler call() throws Exception {
            return d.a;
        }
    }

    /* loaded from: classes5.dex */
    static final class f implements Callable<Scheduler> {
        f() {
        }

        /* renamed from: a */
        public Scheduler call() throws Exception {
            return e.a;
        }
    }

    /* loaded from: classes5.dex */
    static final class h implements Callable<Scheduler> {
        h() {
        }

        /* renamed from: a */
        public Scheduler call() throws Exception {
            return g.a;
        }
    }

    /* loaded from: classes5.dex */
    static final class b implements Callable<Scheduler> {
        b() {
        }

        /* renamed from: a */
        public Scheduler call() throws Exception {
            return a.a;
        }
    }
}
