package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableCache<T> extends a<T, T> implements Observer<T> {
    static final a[] d = new a[0];
    static final a[] e = new a[0];
    final int b;
    volatile long f;
    final b<T> g;
    b<T> h;
    int i;
    Throwable j;
    volatile boolean k;
    final AtomicBoolean a = new AtomicBoolean();
    final AtomicReference<a<T>[]> c = new AtomicReference<>(d);

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable disposable) {
    }

    public ObservableCache(Observable<T> observable, int i) {
        super(observable);
        this.b = i;
        b<T> bVar = new b<>(i);
        this.g = bVar;
        this.h = bVar;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        a<T> aVar = new a<>(observer, this);
        observer.onSubscribe(aVar);
        a(aVar);
        if (this.a.get() || !this.a.compareAndSet(false, true)) {
            c(aVar);
        } else {
            this.source.subscribe(this);
        }
    }

    void a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.c.get();
            if (aVarArr != e) {
                int length = aVarArr.length;
                aVarArr2 = new a[length + 1];
                System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
                aVarArr2[length] = aVar;
            } else {
                return;
            }
        } while (!this.c.compareAndSet(aVarArr, aVarArr2));
    }

    void b(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.c.get();
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
                        aVarArr2 = d;
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
        } while (!this.c.compareAndSet(aVarArr, aVarArr2));
    }

    void c(a<T> aVar) {
        if (aVar.getAndIncrement() == 0) {
            long j = aVar.index;
            int i = aVar.offset;
            b<T> bVar = aVar.node;
            Observer<? super T> observer = aVar.downstream;
            int i2 = this.b;
            int i3 = 1;
            while (!aVar.disposed) {
                boolean z = this.k;
                boolean z2 = this.f == j;
                if (z && z2) {
                    aVar.node = null;
                    Throwable th = this.j;
                    if (th != null) {
                        observer.onError(th);
                        return;
                    } else {
                        observer.onComplete();
                        return;
                    }
                } else if (!z2) {
                    if (i == i2) {
                        bVar = bVar.b;
                        i = 0;
                    }
                    observer.onNext((Object) bVar.a[i]);
                    i++;
                    j++;
                } else {
                    aVar.index = j;
                    aVar.offset = i;
                    aVar.node = bVar;
                    i3 = aVar.addAndGet(-i3);
                    if (i3 == 0) {
                        return;
                    }
                }
            }
            aVar.node = null;
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        int i = this.i;
        if (i == this.b) {
            b<T> bVar = new b<>(i);
            bVar.a[0] = t;
            this.i = 1;
            this.h.b = bVar;
            this.h = bVar;
        } else {
            this.h.a[i] = t;
            this.i = i + 1;
        }
        this.f++;
        for (a<T> aVar : this.c.get()) {
            c(aVar);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        this.j = th;
        this.k = true;
        for (a<T> aVar : this.c.getAndSet(e)) {
            c(aVar);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        this.k = true;
        for (a<T> aVar : this.c.getAndSet(e)) {
            c(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 6770240836423125754L;
        volatile boolean disposed;
        final Observer<? super T> downstream;
        long index;
        b<T> node;
        int offset;
        final ObservableCache<T> parent;

        a(Observer<? super T> observer, ObservableCache<T> observableCache) {
            this.downstream = observer;
            this.parent = observableCache;
            this.node = observableCache.g;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (!this.disposed) {
                this.disposed = true;
                this.parent.b(this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b<T> {
        final T[] a;
        volatile b<T> b;

        b(int i) {
            this.a = (T[]) new Object[i];
        }
    }
}
