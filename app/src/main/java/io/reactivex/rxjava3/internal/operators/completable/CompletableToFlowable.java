package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromCompletable;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class CompletableToFlowable<T> extends Flowable<T> {
    final CompletableSource b;

    public CompletableToFlowable(CompletableSource completableSource) {
        this.b = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe(new FlowableFromCompletable.FromCompletableObserver(subscriber));
    }
}
