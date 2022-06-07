package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class MaybeCache<T> extends Maybe<T> implements MaybeObserver<T> {
    static final a[] a = new a[0];
    static final a[] b = new a[0];
    final AtomicReference<MaybeSource<T>> c;
    final AtomicReference<a<T>[]> d = new AtomicReference<>(a);
    T e;
    Throwable f;

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSubscribe(Disposable disposable) {
    }

    public MaybeCache(MaybeSource<T> maybeSource) {
        this.c = new AtomicReference<>(maybeSource);
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        a<T> aVar = new a<>(maybeObserver, this);
        maybeObserver.onSubscribe(aVar);
        if (a(aVar)) {
            if (aVar.isDisposed()) {
                b(aVar);
                return;
            }
            MaybeSource<T> andSet = this.c.getAndSet(null);
            if (andSet != null) {
                andSet.subscribe(this);
            }
        } else if (!aVar.isDisposed()) {
            Throwable th = this.f;
            if (th != null) {
                maybeObserver.onError(th);
                return;
            }
            Object obj = (T) this.e;
            if (obj != null) {
                maybeObserver.onSuccess(obj);
            } else {
                maybeObserver.onComplete();
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSuccess(T t) {
        this.e = t;
        a<T>[] andSet = this.d.getAndSet(b);
        for (a<T> aVar : andSet) {
            if (!aVar.isDisposed()) {
                aVar.downstream.onSuccess(t);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onError(Throwable th) {
        this.f = th;
        a<T>[] andSet = this.d.getAndSet(b);
        for (a<T> aVar : andSet) {
            if (!aVar.isDisposed()) {
                aVar.downstream.onError(th);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onComplete() {
        a<T>[] andSet = this.d.getAndSet(b);
        for (a<T> aVar : andSet) {
            if (!aVar.isDisposed()) {
                aVar.downstream.onComplete();
            }
        }
    }

    boolean a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.d.get();
            if (aVarArr == b) {
                return false;
            }
            int length = aVarArr.length;
            aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
        } while (!this.d.compareAndSet(aVarArr, aVarArr2));
        return true;
    }

    void b(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.d.get();
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
        } while (!this.d.compareAndSet(aVarArr, aVarArr2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicReference<MaybeCache<T>> implements Disposable {
        private static final long serialVersionUID = -5791853038359966195L;
        final MaybeObserver<? super T> downstream;

        a(MaybeObserver<? super T> maybeObserver, MaybeCache<T> maybeCache) {
            super(maybeCache);
            this.downstream = maybeObserver;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            MaybeCache<T> andSet = getAndSet(null);
            if (andSet != null) {
                andSet.b(this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == null;
        }
    }
}
