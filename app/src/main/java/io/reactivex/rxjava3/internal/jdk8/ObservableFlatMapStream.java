package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public final class ObservableFlatMapStream<T, R> extends Observable<R> {
    final Observable<T> a;
    final Function<? super T, ? extends Stream<? extends R>> b;

    public ObservableFlatMapStream(Observable<T> observable, Function<? super T, ? extends Stream<? extends R>> function) {
        this.a = observable;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        Observable<T> observable = this.a;
        if (observable instanceof Supplier) {
            Stream stream = null;
            try {
                Object obj = ((Supplier) observable).get();
                if (obj != null) {
                    stream = (Stream) Objects.requireNonNull(this.b.apply(obj), "The mapper returned a null Stream");
                }
                if (stream != null) {
                    ObservableFromStream.subscribeStream(observer, stream);
                } else {
                    EmptyDisposable.complete(observer);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        } else {
            observable.subscribe(new a(observer, this.b));
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = -5127032662980523968L;
        volatile boolean disposed;
        boolean done;
        final Observer<? super R> downstream;
        final Function<? super T, ? extends Stream<? extends R>> mapper;
        Disposable upstream;

        a(Observer<? super R> observer, Function<? super T, ? extends Stream<? extends R>> function) {
            this.downstream = observer;
            this.mapper = function;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(@NonNull Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(@NonNull T t) {
            if (!this.done) {
                try {
                    Stream stream = (Stream) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Stream");
                    Iterator<T> it = stream.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (!this.disposed) {
                                Object requireNonNull = Objects.requireNonNull(it.next(), "The Stream's Iterator.next returned a null value");
                                if (!this.disposed) {
                                    this.downstream.onNext(requireNonNull);
                                    if (this.disposed) {
                                        this.done = true;
                                        break;
                                    }
                                } else {
                                    this.done = true;
                                    break;
                                }
                            } else {
                                this.done = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (stream != null) {
                        stream.close();
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.dispose();
                    onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(@NonNull Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }
    }
}
