package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class FlowableFromPublisher<T> extends Flowable<T> {
    final Publisher<? extends T> b;

    public FlowableFromPublisher(Publisher<? extends T> publisher) {
        this.b = publisher;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe(subscriber);
    }
}
