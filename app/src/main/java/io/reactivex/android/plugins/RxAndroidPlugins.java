package io.reactivex.android.plugins;

import io.reactivex.Scheduler;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class RxAndroidPlugins {
    private static volatile Function<Callable<Scheduler>, Scheduler> a;
    private static volatile Function<Scheduler, Scheduler> b;

    public static void setInitMainThreadSchedulerHandler(Function<Callable<Scheduler>, Scheduler> function) {
        a = function;
    }

    public static Scheduler initMainThreadScheduler(Callable<Scheduler> callable) {
        if (callable != null) {
            Function<Callable<Scheduler>, Scheduler> function = a;
            if (function == null) {
                return a(callable);
            }
            return a(function, callable);
        }
        throw new NullPointerException("scheduler == null");
    }

    public static void setMainThreadSchedulerHandler(Function<Scheduler, Scheduler> function) {
        b = function;
    }

    public static Scheduler onMainThreadScheduler(Scheduler scheduler) {
        if (scheduler != null) {
            Function<Scheduler, Scheduler> function = b;
            return function == null ? scheduler : (Scheduler) a(function, scheduler);
        }
        throw new NullPointerException("scheduler == null");
    }

    public static Function<Callable<Scheduler>, Scheduler> getInitMainThreadSchedulerHandler() {
        return a;
    }

    public static Function<Scheduler, Scheduler> getOnMainThreadSchedulerHandler() {
        return b;
    }

    public static void reset() {
        setInitMainThreadSchedulerHandler(null);
        setMainThreadSchedulerHandler(null);
    }

    static Scheduler a(Callable<Scheduler> callable) {
        try {
            Scheduler call = callable.call();
            if (call != null) {
                return call;
            }
            throw new NullPointerException("Scheduler Callable returned null");
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    static Scheduler a(Function<Callable<Scheduler>, Scheduler> function, Callable<Scheduler> callable) {
        Scheduler scheduler = (Scheduler) a((Function<Callable<Scheduler>, Object>) function, callable);
        if (scheduler != null) {
            return scheduler;
        }
        throw new NullPointerException("Scheduler Callable returned null");
    }

    static <T, R> R a(Function<T, R> function, T t) {
        try {
            return function.apply(t);
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    private RxAndroidPlugins() {
        throw new AssertionError("No instances.");
    }
}
