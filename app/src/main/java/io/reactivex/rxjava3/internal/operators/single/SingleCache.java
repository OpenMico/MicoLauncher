package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class SingleCache<T> extends Single<T> implements SingleObserver<T> {
    static final a[] a = new a[0];
    static final a[] b = new a[0];
    final SingleSource<? extends T> c;
    final AtomicInteger d = new AtomicInteger();
    final AtomicReference<a<T>[]> e = new AtomicReference<>(a);
    T f;
    Throwable g;

    @Override // io.reactivex.rxjava3.core.SingleObserver
    public void onSubscribe(Disposable disposable) {
    }

    public SingleCache(SingleSource<? extends T> singleSource) {
        this.c = singleSource;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        a<T> aVar = new a<>(singleObserver, this);
        singleObserver.onSubscribe(aVar);
        if (a(aVar)) {
            if (aVar.isDisposed()) {
                b(aVar);
            }
            if (this.d.getAndIncrement() == 0) {
                this.c.subscribe(this);
                return;
            }
            return;
        }
        Throwable th = this.g;
        if (th != null) {
            singleObserver.onError(th);
        } else {
            singleObserver.onSuccess((T) this.f);
        }
    }

    boolean a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.e.get();
            if (aVarArr == b) {
                return false;
            }
            int length = aVarArr.length;
            aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
        } while (!this.e.compareAndSet(aVarArr, aVarArr2));
        return true;
    }

    void b(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.e.get();
            int length = aVarArr.length;
            if (length != 0) {
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (aVarArr[i2] == aVar) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        aVarArr2 = a;
                    } else {
                        a<T>[] aVarArr3 = new a[length - 1];
                        System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                        System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                        aVarArr2 = aVarArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.e.compareAndSet(aVarArr, aVarArr2));
    }

    @Override // io.reactivex.rxjava3.core.SingleObserver
    public void onSuccess(T t) {
        this.f = t;
        a<T>[] andSet = this.e.getAndSet(b);
        for (a<T> aVar : andSet) {
            if (!aVar.isDisposed()) {
                aVar.downstream.onSuccess(t);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.SingleObserver
    public void onError(Throwable th) {
        this.g = th;
        a<T>[] andSet = this.e.getAndSet(b);
        for (a<T> aVar : andSet) {
            if (!aVar.isDisposed()) {
                aVar.downstream.onError(th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicBoolean implements Disposable {
        private static final long serialVersionUID = 7514387411091976596L;
        final SingleObserver<? super T> downstream;
        final SingleCache<T> parent;

        a(SingleObserver<? super T> singleObserver, SingleCache<T> singleCache) {
            this.downstream = singleObserver;
            this.parent = singleCache;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (compareAndSet(false, true)) {
                this.parent.b(this);
            }
        }
    }
}
