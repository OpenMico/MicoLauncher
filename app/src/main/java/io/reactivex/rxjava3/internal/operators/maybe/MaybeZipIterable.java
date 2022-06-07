package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeMap;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeZipArray;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class MaybeZipIterable<T, R> extends Maybe<R> {
    final Iterable<? extends MaybeSource<? extends T>> a;
    final Function<? super Object[], ? extends R> b;

    public MaybeZipIterable(Iterable<? extends MaybeSource<? extends T>> iterable, Function<? super Object[], ? extends R> function) {
        this.a = iterable;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        MaybeSource[] maybeSourceArr = new MaybeSource[8];
        try {
            Iterator<? extends MaybeSource<? extends T>> it = this.a.iterator();
            MaybeSource[] maybeSourceArr2 = maybeSourceArr;
            int i = 0;
            while (it.hasNext()) {
                MaybeSource maybeSource = (MaybeSource) it.next();
                if (maybeSource == null) {
                    EmptyDisposable.error(new NullPointerException("One of the sources is null"), maybeObserver);
                    return;
                }
                if (i == maybeSourceArr2.length) {
                    maybeSourceArr2 = (MaybeSource[]) Arrays.copyOf(maybeSourceArr2, (i >> 2) + i);
                }
                i++;
                maybeSourceArr2[i] = maybeSource;
            }
            if (i == 0) {
                EmptyDisposable.complete(maybeObserver);
            } else if (i == 1) {
                maybeSourceArr2[0].subscribe(new MaybeMap.a(maybeObserver, new a()));
            } else {
                MaybeZipArray.b bVar = new MaybeZipArray.b(maybeObserver, i, this.b);
                maybeObserver.onSubscribe(bVar);
                for (int i2 = 0; i2 < i && !bVar.isDisposed(); i2++) {
                    maybeSourceArr2[i2].subscribe(bVar.observers[i2]);
                }
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, maybeObserver);
        }
    }

    /* loaded from: classes5.dex */
    final class a implements Function<T, R> {
        a() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object[], java.lang.Object] */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t) throws Throwable {
            return (R) Objects.requireNonNull(MaybeZipIterable.this.b.apply(new Object[]{t}), "The zipper returned a null value");
        }
    }
}
