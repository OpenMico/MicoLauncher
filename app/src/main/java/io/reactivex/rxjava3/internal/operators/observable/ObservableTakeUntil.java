package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableTakeUntil<T, U> extends a<T, T> {
    final ObservableSource<? extends U> a;

    public ObservableTakeUntil(ObservableSource<T> observableSource, ObservableSource<? extends U> observableSource2) {
        super(observableSource);
        this.a = observableSource2;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        a aVar = new a(observer);
        observer.onSubscribe(aVar);
        this.a.subscribe(aVar.otherObserver);
        this.source.subscribe(aVar);
    }

    /* loaded from: classes5.dex */
    static final class a<T, U> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 1418547743690811973L;
        final Observer<? super T> downstream;
        final AtomicReference<Disposable> upstream = new AtomicReference<>();
        final a<T, U>.C0334a otherObserver = new C0334a();
        final AtomicThrowable error = new AtomicThrowable();

        a(Observer<? super T> observer) {
            this.downstream = observer;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            DisposableHelper.dispose(this.otherObserver);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.upstream.get());
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.upstream, disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            HalfSerializer.onNext(this.downstream, t, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.otherObserver);
            HalfSerializer.onError(this.downstream, th, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            DisposableHelper.dispose(this.otherObserver);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        void a(Throwable th) {
            DisposableHelper.dispose(this.upstream);
            HalfSerializer.onError(this.downstream, th, this, this.error);
        }

        void a() {
            DisposableHelper.dispose(this.upstream);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.observable.ObservableTakeUntil$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class C0334a extends AtomicReference<Disposable> implements Observer<U> {
            private static final long serialVersionUID = -8693423678067375039L;

            C0334a() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(U u) {
                DisposableHelper.dispose(this);
                a.this.a();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                a.this.a(th);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                a.this.a();
            }
        }
    }
}
