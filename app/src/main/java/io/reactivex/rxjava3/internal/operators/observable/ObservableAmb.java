package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableAmb<T> extends Observable<T> {
    final ObservableSource<? extends T>[] a;
    final Iterable<? extends ObservableSource<? extends T>> b;

    public ObservableAmb(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable) {
        this.a = observableSourceArr;
        this.b = iterable;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        int i;
        ObservableSource<? extends T>[] observableSourceArr = this.a;
        if (observableSourceArr == null) {
            observableSourceArr = new ObservableSource[8];
            try {
                i = 0;
                for (ObservableSource<? extends T> observableSource : this.b) {
                    if (observableSource == null) {
                        EmptyDisposable.error(new NullPointerException("One of the sources is null"), observer);
                        return;
                    }
                    if (i == observableSourceArr.length) {
                        ObservableSource<? extends T>[] observableSourceArr2 = new ObservableSource[(i >> 2) + i];
                        System.arraycopy(observableSourceArr, 0, observableSourceArr2, 0, i);
                        observableSourceArr = observableSourceArr2;
                    }
                    i++;
                    observableSourceArr[i] = observableSource;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
                return;
            }
        } else {
            i = observableSourceArr.length;
        }
        if (i == 0) {
            EmptyDisposable.complete(observer);
        } else if (i == 1) {
            observableSourceArr[0].subscribe(observer);
        } else {
            new a(observer, i).a(observableSourceArr);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements Disposable {
        final Observer<? super T> a;
        final b<T>[] b;
        final AtomicInteger c = new AtomicInteger();

        a(Observer<? super T> observer, int i) {
            this.a = observer;
            this.b = new b[i];
        }

        public void a(ObservableSource<? extends T>[] observableSourceArr) {
            b<T>[] bVarArr = this.b;
            int length = bVarArr.length;
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                bVarArr[i] = new b<>(this, i2, this.a);
                i = i2;
            }
            this.c.lazySet(0);
            this.a.onSubscribe(this);
            for (int i3 = 0; i3 < length && this.c.get() == 0; i3++) {
                observableSourceArr[i3].subscribe(bVarArr[i3]);
            }
        }

        public boolean a(int i) {
            int i2 = 0;
            if (this.c.get() != 0 || !this.c.compareAndSet(0, i)) {
                return false;
            }
            b<T>[] bVarArr = this.b;
            int length = bVarArr.length;
            while (i2 < length) {
                int i3 = i2 + 1;
                if (i3 != i) {
                    bVarArr[i2].a();
                }
                i2 = i3;
            }
            return true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.c.get() != -1) {
                this.c.lazySet(-1);
                for (b<T> bVar : this.b) {
                    bVar.a();
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c.get() == -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b<T> extends AtomicReference<Disposable> implements Observer<T> {
        private static final long serialVersionUID = -1185974347409665484L;
        final Observer<? super T> downstream;
        final int index;
        final a<T> parent;
        boolean won;

        b(a<T> aVar, int i, Observer<? super T> observer) {
            this.parent = aVar;
            this.index = i;
            this.downstream = observer;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (this.won) {
                this.downstream.onNext(t);
            } else if (this.parent.a(this.index)) {
                this.won = true;
                this.downstream.onNext(t);
            } else {
                get().dispose();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.won) {
                this.downstream.onError(th);
            } else if (this.parent.a(this.index)) {
                this.won = true;
                this.downstream.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.won) {
                this.downstream.onComplete();
            } else if (this.parent.a(this.index)) {
                this.won = true;
                this.downstream.onComplete();
            }
        }

        public void a() {
            DisposableHelper.dispose(this);
        }
    }
}
