package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservablePublishAlt<T> extends ConnectableObservable<T> implements ResettableConnectable, HasUpstreamObservableSource<T> {
    final ObservableSource<T> a;
    final AtomicReference<b<T>> b = new AtomicReference<>();

    public ObservablePublishAlt(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    @Override // io.reactivex.observables.ConnectableObservable
    public void connect(Consumer<? super Disposable> consumer) {
        b<T> bVar;
        while (true) {
            bVar = this.b.get();
            if (bVar != null && !bVar.isDisposed()) {
                break;
            }
            b<T> bVar2 = new b<>(this.b);
            if (this.b.compareAndSet(bVar, bVar2)) {
                bVar = bVar2;
                break;
            }
        }
        boolean z = true;
        if (bVar.connect.get() || !bVar.connect.compareAndSet(false, true)) {
            z = false;
        }
        try {
            consumer.accept(bVar);
            if (z) {
                this.a.subscribe(bVar);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        b<T> bVar;
        while (true) {
            bVar = this.b.get();
            if (bVar != null) {
                break;
            }
            b<T> bVar2 = new b<>(this.b);
            if (this.b.compareAndSet(bVar, bVar2)) {
                bVar = bVar2;
                break;
            }
        }
        a<T> aVar = new a<>(observer, bVar);
        observer.onSubscribe(aVar);
        if (!bVar.a(aVar)) {
            Throwable th = bVar.error;
            if (th != null) {
                observer.onError(th);
            } else {
                observer.onComplete();
            }
        } else if (aVar.isDisposed()) {
            bVar.b(aVar);
        }
    }

    @Override // io.reactivex.internal.disposables.ResettableConnectable
    public void resetIf(Disposable disposable) {
        this.b.compareAndSet((b) disposable, null);
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamObservableSource
    public ObservableSource<T> source() {
        return this.a;
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends AtomicReference<a<T>[]> implements Observer<T>, Disposable {
        static final a[] a = new a[0];
        static final a[] b = new a[0];
        private static final long serialVersionUID = -3251430252873581268L;
        final AtomicReference<b<T>> current;
        Throwable error;
        final AtomicBoolean connect = new AtomicBoolean();
        final AtomicReference<Disposable> upstream = new AtomicReference<>();

        b(AtomicReference<b<T>> atomicReference) {
            this.current = atomicReference;
            lazySet(a);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            getAndSet(b);
            this.current.compareAndSet(this, null);
            DisposableHelper.dispose(this.upstream);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == b;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.upstream, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            for (a<T> aVar : get()) {
                aVar.downstream.onNext(t);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.upstream.lazySet(DisposableHelper.DISPOSED);
            for (a<T> aVar : getAndSet(b)) {
                aVar.downstream.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.upstream.lazySet(DisposableHelper.DISPOSED);
            for (a<T> aVar : getAndSet(b)) {
                aVar.downstream.onComplete();
            }
        }

        public boolean a(a<T> aVar) {
            a<T>[] aVarArr;
            a[] aVarArr2;
            do {
                aVarArr = get();
                if (aVarArr == b) {
                    return false;
                }
                int length = aVarArr.length;
                aVarArr2 = new a[length + 1];
                System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
                aVarArr2[length] = aVar;
            } while (!compareAndSet(aVarArr, aVarArr2));
            return true;
        }

        public void b(a<T> aVar) {
            a<T>[] aVarArr;
            a[] aVarArr2;
            do {
                aVarArr = get();
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
                        aVarArr2 = a;
                        if (length != 1) {
                            aVarArr2 = new a[length - 1];
                            System.arraycopy(aVarArr, 0, aVarArr2, 0, i);
                            System.arraycopy(aVarArr, i + 1, aVarArr2, i, (length - i) - 1);
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!compareAndSet(aVarArr, aVarArr2));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicReference<b<T>> implements Disposable {
        private static final long serialVersionUID = 7463222674719692880L;
        final Observer<? super T> downstream;

        a(Observer<? super T> observer, b<T> bVar) {
            this.downstream = observer;
            lazySet(bVar);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            b<T> andSet = getAndSet(null);
            if (andSet != null) {
                andSet.b(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == null;
        }
    }
}
