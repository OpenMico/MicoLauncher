package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableRetryWhen<T> extends a<T, T> {
    final Function<? super Observable<Throwable>, ? extends ObservableSource<?>> a;

    public ObservableRetryWhen(ObservableSource<T> observableSource, Function<? super Observable<Throwable>, ? extends ObservableSource<?>> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        Subject<T> serialized = PublishSubject.create().toSerialized();
        try {
            ObservableSource observableSource = (ObservableSource) Objects.requireNonNull(this.a.apply(serialized), "The handler returned a null ObservableSource");
            a aVar = new a(observer, serialized, this.source);
            observer.onSubscribe(aVar);
            observableSource.subscribe(aVar.inner);
            aVar.c();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 802743776666017014L;
        volatile boolean active;
        final Observer<? super T> downstream;
        final Subject<Throwable> signaller;
        final ObservableSource<T> source;
        final AtomicInteger wip = new AtomicInteger();
        final AtomicThrowable error = new AtomicThrowable();
        final a<T>.C0333a inner = new C0333a();
        final AtomicReference<Disposable> upstream = new AtomicReference<>();

        a(Observer<? super T> observer, Subject<Throwable> subject, ObservableSource<T> observableSource) {
            this.downstream = observer;
            this.signaller = subject;
            this.source = observableSource;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this.upstream, disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            HalfSerializer.onNext(this.downstream, t, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            DisposableHelper.replace(this.upstream, null);
            this.active = false;
            this.signaller.onNext(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            DisposableHelper.dispose(this.inner);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.upstream.get());
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            DisposableHelper.dispose(this.inner);
        }

        void a() {
            c();
        }

        void a(Throwable th) {
            DisposableHelper.dispose(this.upstream);
            HalfSerializer.onError(this.downstream, th, this, this.error);
        }

        void b() {
            DisposableHelper.dispose(this.upstream);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        void c() {
            if (this.wip.getAndIncrement() == 0) {
                while (!isDisposed()) {
                    if (!this.active) {
                        this.active = true;
                        this.source.subscribe(this);
                    }
                    if (this.wip.decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.observable.ObservableRetryWhen$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class C0333a extends AtomicReference<Disposable> implements Observer<Object> {
            private static final long serialVersionUID = 3254781284376480842L;

            C0333a() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(Object obj) {
                a.this.a();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                a.this.a(th);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                a.this.b();
            }
        }
    }
}
