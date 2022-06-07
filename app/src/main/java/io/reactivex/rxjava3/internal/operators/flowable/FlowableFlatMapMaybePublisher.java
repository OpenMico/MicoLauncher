package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapMaybe;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class FlowableFlatMapMaybePublisher<T, R> extends Flowable<R> {
    final Publisher<T> b;
    final Function<? super T, ? extends MaybeSource<? extends R>> c;
    final boolean d;
    final int e;

    public FlowableFlatMapMaybePublisher(Publisher<T> publisher, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z, int i) {
        this.b = publisher;
        this.c = function;
        this.d = z;
        this.e = i;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe(new FlowableFlatMapMaybe.a(subscriber, this.c, this.d, this.e));
    }
}
