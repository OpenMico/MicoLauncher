package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.jdk8.MaybeFlattenStreamAsFlowable;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class SingleFlattenStreamAsFlowable<T, R> extends Flowable<R> {
    final Single<T> b;
    final Function<? super T, ? extends Stream<? extends R>> c;

    public SingleFlattenStreamAsFlowable(Single<T> single, Function<? super T, ? extends Stream<? extends R>> function) {
        this.b = single;
        this.c = function;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(@NonNull Subscriber<? super R> subscriber) {
        this.b.subscribe(new MaybeFlattenStreamAsFlowable.a(subscriber, this.c));
    }
}
