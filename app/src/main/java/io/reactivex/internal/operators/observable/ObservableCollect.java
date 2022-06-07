package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class ObservableCollect<T, U> extends a<T, U> {
    final Callable<? extends U> a;
    final BiConsumer<? super U, ? super T> b;

    public ObservableCollect(ObservableSource<T> observableSource, Callable<? extends U> callable, BiConsumer<? super U, ? super T> biConsumer) {
        super(observableSource);
        this.a = callable;
        this.b = biConsumer;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super U> observer) {
        try {
            this.source.subscribe(new a(observer, ObjectHelper.requireNonNull(this.a.call(), "The initialSupplier returned a null value"), this.b));
        } catch (Throwable th) {
            EmptyDisposable.error(th, observer);
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, U> implements Observer<T>, Disposable {
        final Observer<? super U> a;
        final BiConsumer<? super U, ? super T> b;
        final U c;
        Disposable d;
        boolean e;

        a(Observer<? super U> observer, U u, BiConsumer<? super U, ? super T> biConsumer) {
            this.a = observer;
            this.b = biConsumer;
            this.c = u;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.d.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.e) {
                try {
                    this.b.accept((U) this.c, t);
                } catch (Throwable th) {
                    this.d.dispose();
                    onError(th);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.a.onNext((U) this.c);
                this.a.onComplete();
            }
        }
    }
}
