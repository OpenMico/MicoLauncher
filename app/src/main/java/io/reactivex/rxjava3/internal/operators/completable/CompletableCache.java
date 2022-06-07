package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class CompletableCache extends Completable implements CompletableObserver {
    static final a[] a = new a[0];
    static final a[] b = new a[0];
    final CompletableSource c;
    final AtomicReference<a[]> d = new AtomicReference<>(a);
    final AtomicBoolean e = new AtomicBoolean();
    Throwable f;

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(Disposable disposable) {
    }

    public CompletableCache(CompletableSource completableSource) {
        this.c = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        a aVar = new a(completableObserver);
        completableObserver.onSubscribe(aVar);
        if (a(aVar)) {
            if (aVar.isDisposed()) {
                b(aVar);
            }
            if (this.e.compareAndSet(false, true)) {
                this.c.subscribe(this);
                return;
            }
            return;
        }
        Throwable th = this.f;
        if (th != null) {
            completableObserver.onError(th);
        } else {
            completableObserver.onComplete();
        }
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onError(Throwable th) {
        this.f = th;
        a[] andSet = this.d.getAndSet(b);
        for (a aVar : andSet) {
            if (!aVar.get()) {
                aVar.downstream.onError(th);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        a[] andSet = this.d.getAndSet(b);
        for (a aVar : andSet) {
            if (!aVar.get()) {
                aVar.downstream.onComplete();
            }
        }
    }

    boolean a(a aVar) {
        a[] aVarArr;
        a[] aVarArr2;
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

    void b(a aVar) {
        a[] aVarArr;
        a[] aVarArr2;
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
                        a[] aVarArr3 = new a[length - 1];
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
    /* loaded from: classes4.dex */
    public final class a extends AtomicBoolean implements Disposable {
        private static final long serialVersionUID = 8943152917179642732L;
        final CompletableObserver downstream;

        a(CompletableObserver completableObserver) {
            this.downstream = completableObserver;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (compareAndSet(false, true)) {
                CompletableCache.this.b(this);
            }
        }
    }
}
