package io.reactivex.disposables;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Future;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class Disposables {
    private Disposables() {
        throw new IllegalStateException("No instances!");
    }

    @NonNull
    public static Disposable fromRunnable(@NonNull Runnable runnable) {
        ObjectHelper.requireNonNull(runnable, "run is null");
        return new d(runnable);
    }

    @NonNull
    public static Disposable fromAction(@NonNull Action action) {
        ObjectHelper.requireNonNull(action, "run is null");
        return new a(action);
    }

    @NonNull
    public static Disposable fromFuture(@NonNull Future<?> future) {
        ObjectHelper.requireNonNull(future, "future is null");
        return fromFuture(future, true);
    }

    @NonNull
    public static Disposable fromFuture(@NonNull Future<?> future, boolean z) {
        ObjectHelper.requireNonNull(future, "future is null");
        return new b(future, z);
    }

    @NonNull
    public static Disposable fromSubscription(@NonNull Subscription subscription) {
        ObjectHelper.requireNonNull(subscription, "subscription is null");
        return new e(subscription);
    }

    @NonNull
    public static Disposable empty() {
        return fromRunnable(Functions.EMPTY_RUNNABLE);
    }

    @NonNull
    public static Disposable disposed() {
        return EmptyDisposable.INSTANCE;
    }
}
