package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.util.ErrorMode;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableConcatMapPublisher<T, R> extends Flowable<R> {
    final Publisher<T> b;
    final Function<? super T, ? extends Publisher<? extends R>> c;
    final int d;
    final ErrorMode e;

    public FlowableConcatMapPublisher(Publisher<T> publisher, Function<? super T, ? extends Publisher<? extends R>> function, int i, ErrorMode errorMode) {
        this.b = publisher;
        this.c = function;
        this.d = i;
        this.e = errorMode;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.b, subscriber, this.c)) {
            this.b.subscribe(FlowableConcatMap.subscribe(subscriber, this.c, this.d, this.e));
        }
    }
}
