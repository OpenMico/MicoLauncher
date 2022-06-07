package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.flowable.FlowableConcatMapEager;
import io.reactivex.internal.util.ErrorMode;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableConcatMapEagerPublisher<T, R> extends Flowable<R> {
    final Publisher<T> b;
    final Function<? super T, ? extends Publisher<? extends R>> c;
    final int d;
    final int e;
    final ErrorMode f;

    public FlowableConcatMapEagerPublisher(Publisher<T> publisher, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, ErrorMode errorMode) {
        this.b = publisher;
        this.c = function;
        this.d = i;
        this.e = i2;
        this.f = errorMode;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe(new FlowableConcatMapEager.a(subscriber, this.c, this.d, this.e, this.f));
    }
}
