package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapSingle;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class FlowableSwitchMapSinglePublisher<T, R> extends Flowable<R> {
    final Publisher<T> b;
    final Function<? super T, ? extends SingleSource<? extends R>> c;
    final boolean d;

    public FlowableSwitchMapSinglePublisher(Publisher<T> publisher, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
        this.b = publisher;
        this.c = function;
        this.d = z;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe(new FlowableSwitchMapSingle.a(subscriber, this.c, this.d));
    }
}
