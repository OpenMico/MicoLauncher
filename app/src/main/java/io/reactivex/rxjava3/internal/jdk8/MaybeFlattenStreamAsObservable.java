package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.observers.BasicIntQueueDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public final class MaybeFlattenStreamAsObservable<T, R> extends Observable<R> {
    final Maybe<T> a;
    final Function<? super T, ? extends Stream<? extends R>> b;

    public MaybeFlattenStreamAsObservable(Maybe<T> maybe, Function<? super T, ? extends Stream<? extends R>> function) {
        this.a = maybe;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(@NonNull Observer<? super R> observer) {
        this.a.subscribe(new a(observer, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends BasicIntQueueDisposable<R> implements MaybeObserver<T>, SingleObserver<T> {
        private static final long serialVersionUID = 7363336003027148283L;
        AutoCloseable close;
        volatile boolean disposed;
        final Observer<? super R> downstream;
        volatile Iterator<? extends R> iterator;
        final Function<? super T, ? extends Stream<? extends R>> mapper;
        boolean once;
        boolean outputFused;
        Disposable upstream;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(Observer<? super R> observer, Function<? super T, ? extends Stream<? extends R>> function) {
            this.downstream = observer;
            this.mapper = function;
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(@NonNull Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(@NonNull T t) {
            try {
                Stream stream = (Stream) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Stream");
                Iterator<? extends R> it = stream.iterator();
                if (!it.hasNext()) {
                    this.downstream.onComplete();
                    a(stream);
                    return;
                }
                this.iterator = it;
                this.close = stream;
                a();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(@NonNull Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.upstream.dispose();
            if (!this.outputFused) {
                a();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public R poll() throws Throwable {
            Iterator<? extends R> it = this.iterator;
            if (it == null) {
                return null;
            }
            if (!this.once) {
                this.once = true;
            } else if (!it.hasNext()) {
                clear();
                return null;
            }
            return (R) it.next();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            Iterator<? extends R> it = this.iterator;
            if (it == null) {
                return true;
            }
            if (!this.once || it.hasNext()) {
                return false;
            }
            clear();
            return true;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.iterator = null;
            AutoCloseable autoCloseable = this.close;
            this.close = null;
            a(autoCloseable);
        }

        void a(AutoCloseable autoCloseable) {
            if (autoCloseable != null) {
                try {
                    autoCloseable.close();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }

        void a() {
            if (getAndIncrement() == 0) {
                Observer<? super R> observer = this.downstream;
                Iterator<? extends R> it = this.iterator;
                int i = 1;
                while (true) {
                    if (this.disposed) {
                        clear();
                    } else if (this.outputFused) {
                        observer.onNext(null);
                        observer.onComplete();
                    } else {
                        try {
                            Object obj = (Object) it.next();
                            if (!this.disposed) {
                                observer.onNext(obj);
                                if (!this.disposed) {
                                    try {
                                        boolean hasNext = it.hasNext();
                                        if (!this.disposed && !hasNext) {
                                            observer.onComplete();
                                            this.disposed = true;
                                        }
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        observer.onError(th);
                                        this.disposed = true;
                                    }
                                }
                            }
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            observer.onError(th2);
                            this.disposed = true;
                        }
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }
    }
}
