package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapMaybe;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class FlowableConcatMapMaybePublisher<T, R> extends Flowable<R> {
    final Publisher<T> b;
    final Function<? super T, ? extends MaybeSource<? extends R>> c;
    final ErrorMode d;
    final int e;

    public FlowableConcatMapMaybePublisher(Publisher<T> publisher, Function<? super T, ? extends MaybeSource<? extends R>> function, ErrorMode errorMode, int i) {
        this.b = publisher;
        this.c = function;
        this.d = errorMode;
        this.e = i;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe(new FlowableConcatMapMaybe.a(subscriber, this.c, this.e, this.d));
    }
}
