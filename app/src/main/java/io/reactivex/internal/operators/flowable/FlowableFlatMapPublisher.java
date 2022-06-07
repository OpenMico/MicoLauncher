package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableFlatMapPublisher<T, U> extends Flowable<U> {
    final Publisher<T> b;
    final Function<? super T, ? extends Publisher<? extends U>> c;
    final boolean d;
    final int e;
    final int f;

    public FlowableFlatMapPublisher(Publisher<T> publisher, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        this.b = publisher;
        this.c = function;
        this.d = z;
        this.e = i;
        this.f = i2;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.b, subscriber, this.c)) {
            this.b.subscribe(FlowableFlatMap.subscribe(subscriber, this.c, this.d, this.e, this.f));
        }
    }
}
