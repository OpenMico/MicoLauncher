package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.jdk8.MaybeFlattenStreamAsObservable;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public final class SingleFlattenStreamAsObservable<T, R> extends Observable<R> {
    final Single<T> a;
    final Function<? super T, ? extends Stream<? extends R>> b;

    public SingleFlattenStreamAsObservable(Single<T> single, Function<? super T, ? extends Stream<? extends R>> function) {
        this.a = single;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(@NonNull Observer<? super R> observer) {
        this.a.subscribe(new MaybeFlattenStreamAsObservable.a(observer, this.b));
    }
}
