package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeOnErrorComplete;

/* loaded from: classes5.dex */
public final class SingleOnErrorComplete<T> extends Maybe<T> {
    final Single<T> a;
    final Predicate<? super Throwable> b;

    public SingleOnErrorComplete(Single<T> single, Predicate<? super Throwable> predicate) {
        this.a = single;
        this.b = predicate;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new MaybeOnErrorComplete.OnErrorCompleteMultiObserver(maybeObserver, this.b));
    }
}
