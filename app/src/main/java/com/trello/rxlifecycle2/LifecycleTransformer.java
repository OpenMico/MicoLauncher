package com.trello.rxlifecycle2;

import com.trello.rxlifecycle2.internal.Preconditions;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import javax.annotation.ParametersAreNonnullByDefault;
import org.reactivestreams.Publisher;

@ParametersAreNonnullByDefault
/* loaded from: classes2.dex */
public final class LifecycleTransformer<T> implements CompletableTransformer, FlowableTransformer<T, T>, MaybeTransformer<T, T>, ObservableTransformer<T, T>, SingleTransformer<T, T> {
    final Observable<?> a;

    public LifecycleTransformer(Observable<?> observable) {
        Preconditions.checkNotNull(observable, "observable == null");
        this.a = observable;
    }

    @Override // io.reactivex.ObservableTransformer
    public ObservableSource<T> apply(Observable<T> observable) {
        return observable.takeUntil(this.a);
    }

    @Override // io.reactivex.FlowableTransformer
    public Publisher<T> apply(Flowable<T> flowable) {
        return flowable.takeUntil(this.a.toFlowable(BackpressureStrategy.LATEST));
    }

    @Override // io.reactivex.SingleTransformer
    public SingleSource<T> apply(Single<T> single) {
        return single.takeUntil(this.a.firstOrError());
    }

    @Override // io.reactivex.MaybeTransformer
    public MaybeSource<T> apply(Maybe<T> maybe) {
        return maybe.takeUntil(this.a.firstElement());
    }

    @Override // io.reactivex.CompletableTransformer
    public CompletableSource apply(Completable completable) {
        return Completable.ambArray(completable, this.a.flatMapCompletable(a.c));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.a.equals(((LifecycleTransformer) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return "LifecycleTransformer{observable=" + this.a + '}';
    }
}
