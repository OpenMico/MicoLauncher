package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.subscribers.SerializedSubscriber;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class FlowableSerialized<T> extends a<T, T> {
    public FlowableSerialized(Flowable<T> flowable) {
        super(flowable);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new SerializedSubscriber(subscriber));
    }
}
