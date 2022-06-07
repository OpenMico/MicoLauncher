package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class MaybeAmb<T> extends Maybe<T> {
    private final MaybeSource<? extends T>[] a;
    private final Iterable<? extends MaybeSource<? extends T>> b;

    public MaybeAmb(MaybeSource<? extends T>[] maybeSourceArr, Iterable<? extends MaybeSource<? extends T>> iterable) {
        this.a = maybeSourceArr;
        this.b = iterable;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        int i;
        MaybeSource<? extends T>[] maybeSourceArr = this.a;
        if (maybeSourceArr == null) {
            maybeSourceArr = new MaybeSource[8];
            try {
                i = 0;
                for (MaybeSource<? extends T> maybeSource : this.b) {
                    if (maybeSource == null) {
                        EmptyDisposable.error(new NullPointerException("One of the sources is null"), maybeObserver);
                        return;
                    }
                    if (i == maybeSourceArr.length) {
                        MaybeSource<? extends T>[] maybeSourceArr2 = new MaybeSource[(i >> 2) + i];
                        System.arraycopy(maybeSourceArr, 0, maybeSourceArr2, 0, i);
                        maybeSourceArr = maybeSourceArr2;
                    }
                    i++;
                    maybeSourceArr[i] = maybeSource;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, maybeObserver);
                return;
            }
        } else {
            i = maybeSourceArr.length;
        }
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        maybeObserver.onSubscribe(compositeDisposable);
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        for (int i2 = 0; i2 < i; i2++) {
            MaybeSource<? extends T> maybeSource2 = maybeSourceArr[i2];
            if (compositeDisposable.isDisposed()) {
                return;
            }
            if (maybeSource2 == null) {
                compositeDisposable.dispose();
                NullPointerException nullPointerException = new NullPointerException("One of the MaybeSources is null");
                if (atomicBoolean.compareAndSet(false, true)) {
                    maybeObserver.onError(nullPointerException);
                    return;
                } else {
                    RxJavaPlugins.onError(nullPointerException);
                    return;
                }
            } else {
                maybeSource2.subscribe(new a(maybeObserver, compositeDisposable, atomicBoolean));
            }
        }
        if (i == 0) {
            maybeObserver.onComplete();
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements MaybeObserver<T> {
        final MaybeObserver<? super T> a;
        final AtomicBoolean b;
        final CompositeDisposable c;
        Disposable d;

        a(MaybeObserver<? super T> maybeObserver, CompositeDisposable compositeDisposable, AtomicBoolean atomicBoolean) {
            this.a = maybeObserver;
            this.c = compositeDisposable;
            this.b = atomicBoolean;
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.d = disposable;
            this.c.add(disposable);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            if (this.b.compareAndSet(false, true)) {
                this.c.delete(this.d);
                this.c.dispose();
                this.a.onSuccess(t);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            if (this.b.compareAndSet(false, true)) {
                this.c.delete(this.d);
                this.c.dispose();
                this.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            if (this.b.compareAndSet(false, true)) {
                this.c.delete(this.d);
                this.c.dispose();
                this.a.onComplete();
            }
        }
    }
}
