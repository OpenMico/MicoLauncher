package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class CompletableAmb extends Completable {
    private final CompletableSource[] a;
    private final Iterable<? extends CompletableSource> b;

    public CompletableAmb(CompletableSource[] completableSourceArr, Iterable<? extends CompletableSource> iterable) {
        this.a = completableSourceArr;
        this.b = iterable;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        int i;
        CompletableSource[] completableSourceArr = this.a;
        if (completableSourceArr == null) {
            completableSourceArr = new CompletableSource[8];
            try {
                i = 0;
                for (CompletableSource completableSource : this.b) {
                    if (completableSource == null) {
                        EmptyDisposable.error(new NullPointerException("One of the sources is null"), completableObserver);
                        return;
                    }
                    if (i == completableSourceArr.length) {
                        CompletableSource[] completableSourceArr2 = new CompletableSource[(i >> 2) + i];
                        System.arraycopy(completableSourceArr, 0, completableSourceArr2, 0, i);
                        completableSourceArr = completableSourceArr2;
                    }
                    i++;
                    completableSourceArr[i] = completableSource;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, completableObserver);
                return;
            }
        } else {
            i = completableSourceArr.length;
        }
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        completableObserver.onSubscribe(compositeDisposable);
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        for (int i2 = 0; i2 < i; i2++) {
            CompletableSource completableSource2 = completableSourceArr[i2];
            if (!compositeDisposable.isDisposed()) {
                if (completableSource2 == null) {
                    Throwable nullPointerException = new NullPointerException("One of the sources is null");
                    if (atomicBoolean.compareAndSet(false, true)) {
                        compositeDisposable.dispose();
                        completableObserver.onError(nullPointerException);
                        return;
                    }
                    RxJavaPlugins.onError(nullPointerException);
                    return;
                }
                completableSource2.subscribe(new a(atomicBoolean, compositeDisposable, completableObserver));
            } else {
                return;
            }
        }
        if (i == 0) {
            completableObserver.onComplete();
        }
    }

    /* loaded from: classes4.dex */
    static final class a implements CompletableObserver {
        final AtomicBoolean a;
        final CompositeDisposable b;
        final CompletableObserver c;
        Disposable d;

        a(AtomicBoolean atomicBoolean, CompositeDisposable compositeDisposable, CompletableObserver completableObserver) {
            this.a = atomicBoolean;
            this.b = compositeDisposable;
            this.c = completableObserver;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            if (this.a.compareAndSet(false, true)) {
                this.b.delete(this.d);
                this.b.dispose();
                this.c.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            if (this.a.compareAndSet(false, true)) {
                this.b.delete(this.d);
                this.b.dispose();
                this.c.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.d = disposable;
            this.b.add(disposable);
        }
    }
}
