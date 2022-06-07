package io.reactivex.internal.operators.completable;

import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.internal.observers.SubscriberCompletableObserver;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class CompletableToFlowable<T> extends Flowable<T> {
    final CompletableSource b;

    public CompletableToFlowable(CompletableSource completableSource) {
        this.b = completableSource;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe(new SubscriberCompletableObserver(subscriber));
    }
}
