package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.single.SingleMap;
import io.reactivex.internal.operators.single.SingleZipArray;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public final class SingleZipIterable<T, R> extends Single<R> {
    final Iterable<? extends SingleSource<? extends T>> a;
    final Function<? super Object[], ? extends R> b;

    public SingleZipIterable(Iterable<? extends SingleSource<? extends T>> iterable, Function<? super Object[], ? extends R> function) {
        this.a = iterable;
        this.b = function;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        SingleSource[] singleSourceArr = new SingleSource[8];
        try {
            Iterator<? extends SingleSource<? extends T>> it = this.a.iterator();
            SingleSource[] singleSourceArr2 = singleSourceArr;
            int i = 0;
            while (it.hasNext()) {
                SingleSource singleSource = (SingleSource) it.next();
                if (singleSource == null) {
                    EmptyDisposable.error(new NullPointerException("One of the sources is null"), singleObserver);
                    return;
                }
                if (i == singleSourceArr2.length) {
                    singleSourceArr2 = (SingleSource[]) Arrays.copyOf(singleSourceArr2, (i >> 2) + i);
                }
                i++;
                singleSourceArr2[i] = singleSource;
            }
            if (i == 0) {
                EmptyDisposable.error(new NoSuchElementException(), singleObserver);
            } else if (i == 1) {
                singleSourceArr2[0].subscribe(new SingleMap.a(singleObserver, new a()));
            } else {
                SingleZipArray.b bVar = new SingleZipArray.b(singleObserver, i, this.b);
                singleObserver.onSubscribe(bVar);
                for (int i2 = 0; i2 < i && !bVar.isDisposed(); i2++) {
                    singleSourceArr2[i2].subscribe(bVar.observers[i2]);
                }
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }

    /* loaded from: classes4.dex */
    final class a implements Function<T, R> {
        a() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object[], java.lang.Object] */
        @Override // io.reactivex.functions.Function
        public R apply(T t) throws Exception {
            return (R) ObjectHelper.requireNonNull(SingleZipIterable.this.b.apply(new Object[]{t}), "The zipper returned a null value");
        }
    }
}
