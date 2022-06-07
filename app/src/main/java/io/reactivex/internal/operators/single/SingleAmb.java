package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class SingleAmb<T> extends Single<T> {
    private final SingleSource<? extends T>[] a;
    private final Iterable<? extends SingleSource<? extends T>> b;

    public SingleAmb(SingleSource<? extends T>[] singleSourceArr, Iterable<? extends SingleSource<? extends T>> iterable) {
        this.a = singleSourceArr;
        this.b = iterable;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        int i;
        SingleSource<? extends T>[] singleSourceArr = this.a;
        if (singleSourceArr == null) {
            singleSourceArr = new SingleSource[8];
            try {
                i = 0;
                for (SingleSource<? extends T> singleSource : this.b) {
                    if (singleSource == null) {
                        EmptyDisposable.error(new NullPointerException("One of the sources is null"), singleObserver);
                        return;
                    }
                    if (i == singleSourceArr.length) {
                        SingleSource<? extends T>[] singleSourceArr2 = new SingleSource[(i >> 2) + i];
                        System.arraycopy(singleSourceArr, 0, singleSourceArr2, 0, i);
                        singleSourceArr = singleSourceArr2;
                    }
                    i++;
                    singleSourceArr[i] = singleSource;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, singleObserver);
                return;
            }
        } else {
            i = singleSourceArr.length;
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        singleObserver.onSubscribe(compositeDisposable);
        for (int i2 = 0; i2 < i; i2++) {
            SingleSource<? extends T> singleSource2 = singleSourceArr[i2];
            if (compositeDisposable.isDisposed()) {
                return;
            }
            if (singleSource2 == null) {
                compositeDisposable.dispose();
                NullPointerException nullPointerException = new NullPointerException("One of the sources is null");
                if (atomicBoolean.compareAndSet(false, true)) {
                    singleObserver.onError(nullPointerException);
                    return;
                } else {
                    RxJavaPlugins.onError(nullPointerException);
                    return;
                }
            } else {
                singleSource2.subscribe(new a(singleObserver, compositeDisposable, atomicBoolean));
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements SingleObserver<T> {
        final CompositeDisposable a;
        final SingleObserver<? super T> b;
        final AtomicBoolean c;
        Disposable d;

        a(SingleObserver<? super T> singleObserver, CompositeDisposable compositeDisposable, AtomicBoolean atomicBoolean) {
            this.b = singleObserver;
            this.a = compositeDisposable;
            this.c = atomicBoolean;
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.d = disposable;
            this.a.add(disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            if (this.c.compareAndSet(false, true)) {
                this.a.delete(this.d);
                this.a.dispose();
                this.b.onSuccess(t);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            if (this.c.compareAndSet(false, true)) {
                this.a.delete(this.d);
                this.a.dispose();
                this.b.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }
}
