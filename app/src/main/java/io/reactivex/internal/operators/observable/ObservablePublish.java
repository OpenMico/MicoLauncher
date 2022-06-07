package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservablePublish<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T>, ObservablePublishClassic<T> {
    final ObservableSource<T> a;
    final AtomicReference<b<T>> b;
    final ObservableSource<T> c;

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableObservable) new ObservablePublish(new c(atomicReference), observableSource, atomicReference));
    }

    private ObservablePublish(ObservableSource<T> observableSource, ObservableSource<T> observableSource2, AtomicReference<b<T>> atomicReference) {
        this.c = observableSource;
        this.a = observableSource2;
        this.b = atomicReference;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamObservableSource
    public ObservableSource<T> source() {
        return this.a;
    }

    @Override // io.reactivex.internal.operators.observable.ObservablePublishClassic
    public ObservableSource<T> publishSource() {
        return this.a;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.c.subscribe(observer);
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
        if (bVar.e.get() || !bVar.e.compareAndSet(false, true)) {
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

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> implements Observer<T>, Disposable {
        static final a[] b = new a[0];
        static final a[] c = new a[0];
        final AtomicReference<b<T>> a;
        final AtomicReference<Disposable> f = new AtomicReference<>();
        final AtomicReference<a<T>[]> d = new AtomicReference<>(b);
        final AtomicBoolean e = new AtomicBoolean();

        b(AtomicReference<b<T>> atomicReference) {
            this.a = atomicReference;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.d.getAndSet(c) != c) {
                this.a.compareAndSet(this, null);
                DisposableHelper.dispose(this.f);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.get() == c;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.f, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            for (a<T> aVar : this.d.get()) {
                aVar.child.onNext(t);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.compareAndSet(this, null);
            a<T>[] andSet = this.d.getAndSet(c);
            if (andSet.length != 0) {
                for (a<T> aVar : andSet) {
                    aVar.child.onError(th);
                }
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a.compareAndSet(this, null);
            for (a<T> aVar : this.d.getAndSet(c)) {
                aVar.child.onComplete();
            }
        }

        boolean a(a<T> aVar) {
            a<T>[] aVarArr;
            a<T>[] aVarArr2;
            do {
                aVarArr = this.d.get();
                if (aVarArr == c) {
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
                        } else if (aVarArr[i2].equals(aVar)) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            aVarArr2 = b;
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
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicReference<Object> implements Disposable {
        private static final long serialVersionUID = -1100270633763673112L;
        final Observer<? super T> child;

        a(Observer<? super T> observer) {
            this.child = observer;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == this;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            Object andSet = getAndSet(this);
            if (andSet != null && andSet != this) {
                ((b) andSet).b(this);
            }
        }

        void a(b<T> bVar) {
            if (!compareAndSet(null, bVar)) {
                bVar.b(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c<T> implements ObservableSource<T> {
        private final AtomicReference<b<T>> a;

        c(AtomicReference<b<T>> atomicReference) {
            this.a = atomicReference;
        }

        @Override // io.reactivex.ObservableSource
        public void subscribe(Observer<? super T> observer) {
            a aVar = new a(observer);
            observer.onSubscribe(aVar);
            while (true) {
                b<T> bVar = this.a.get();
                if (bVar == null || bVar.isDisposed()) {
                    b<T> bVar2 = new b<>(this.a);
                    if (!this.a.compareAndSet(bVar, bVar2)) {
                        continue;
                    } else {
                        bVar = bVar2;
                    }
                }
                if (bVar.a(aVar)) {
                    aVar.a(bVar);
                    return;
                }
            }
        }
    }
}
