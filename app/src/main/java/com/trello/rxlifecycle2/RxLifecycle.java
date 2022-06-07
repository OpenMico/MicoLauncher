package com.trello.rxlifecycle2;

import com.trello.rxlifecycle2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/* loaded from: classes2.dex */
public class RxLifecycle {
    private RxLifecycle() {
        throw new AssertionError("No instances");
    }

    @Nonnull
    @CheckReturnValue
    public static <T, R> LifecycleTransformer<T> bindUntilEvent(@Nonnull Observable<R> observable, @Nonnull R r) {
        Preconditions.checkNotNull(observable, "lifecycle == null");
        Preconditions.checkNotNull(r, "event == null");
        return bind(a(observable, r));
    }

    private static <R> Observable<R> a(Observable<R> observable, final R r) {
        return observable.filter(new Predicate<R>() { // from class: com.trello.rxlifecycle2.RxLifecycle.1
            @Override // io.reactivex.functions.Predicate
            public boolean test(R r2) throws Exception {
                return r2.equals(r);
            }
        });
    }

    @Nonnull
    @CheckReturnValue
    public static <T, R> LifecycleTransformer<T> bind(@Nonnull Observable<R> observable) {
        return new LifecycleTransformer<>(observable);
    }

    @Nonnull
    @CheckReturnValue
    public static <T, R> LifecycleTransformer<T> bind(@Nonnull Observable<R> observable, @Nonnull Function<R, R> function) {
        Preconditions.checkNotNull(observable, "lifecycle == null");
        Preconditions.checkNotNull(function, "correspondingEvents == null");
        return bind(a((Observable) observable.share(), (Function) function));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <R> Observable<Boolean> a(Observable<R> observable, Function<R, R> function) {
        return Observable.combineLatest(observable.take(1L).map(function), observable.skip(1L), new BiFunction<R, R, Boolean>() { // from class: com.trello.rxlifecycle2.RxLifecycle.2
            /* renamed from: a */
            public Boolean apply(R r, R r2) throws Exception {
                return Boolean.valueOf(r2.equals(r));
            }
        }).onErrorReturn(a.a).filter(a.b);
    }
}
