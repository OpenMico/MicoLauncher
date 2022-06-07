package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.QueueDisposable;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.observers.InnerQueuedObserver;
import io.reactivex.rxjava3.internal.observers.InnerQueuedObserverSupport;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
public final class ObservableConcatMapEager<T, R> extends a<T, R> {
    final Function<? super T, ? extends ObservableSource<? extends R>> a;
    final ErrorMode b;
    final int c;
    final int d;

    public ObservableConcatMapEager(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends R>> function, ErrorMode errorMode, int i, int i2) {
        super(observableSource);
        this.a = function;
        this.b = errorMode;
        this.c = i;
        this.d = i2;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new a(observer, this.a, this.c, this.d, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable, InnerQueuedObserverSupport<R> {
        private static final long serialVersionUID = 8080567949447303262L;
        int activeCount;
        volatile boolean cancelled;
        InnerQueuedObserver<R> current;
        volatile boolean done;
        final Observer<? super R> downstream;
        final ErrorMode errorMode;
        final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
        final int maxConcurrency;
        final int prefetch;
        SimpleQueue<T> queue;
        int sourceMode;
        Disposable upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final ArrayDeque<InnerQueuedObserver<R>> observers = new ArrayDeque<>();

        a(Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, int i2, ErrorMode errorMode) {
            this.downstream = observer;
            this.mapper = function;
            this.maxConcurrency = i;
            this.prefetch = i2;
            this.errorMode = errorMode;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int requestFusion = queueDisposable.requestFusion(3);
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueDisposable;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        drain();
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueDisposable;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new SpscLinkedArrayQueue(this.prefetch);
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (this.sourceMode == 0) {
                this.queue.offer(t);
            }
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.done = true;
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.dispose();
                this.errors.tryTerminateAndReport();
                a();
            }
        }

        void a() {
            if (getAndIncrement() == 0) {
                do {
                    this.queue.clear();
                    b();
                } while (decrementAndGet() != 0);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void b() {
            InnerQueuedObserver<R> innerQueuedObserver = this.current;
            if (innerQueuedObserver != null) {
                innerQueuedObserver.dispose();
            }
            while (true) {
                InnerQueuedObserver<R> poll = this.observers.poll();
                if (poll != null) {
                    poll.dispose();
                } else {
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.observers.InnerQueuedObserverSupport
        public void innerNext(InnerQueuedObserver<R> innerQueuedObserver, R r) {
            innerQueuedObserver.queue().offer(r);
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.observers.InnerQueuedObserverSupport
        public void innerError(InnerQueuedObserver<R> innerQueuedObserver, Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (this.errorMode == ErrorMode.IMMEDIATE) {
                    this.upstream.dispose();
                }
                innerQueuedObserver.setDone();
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.observers.InnerQueuedObserverSupport
        public void innerComplete(InnerQueuedObserver<R> innerQueuedObserver) {
            innerQueuedObserver.setDone();
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.observers.InnerQueuedObserverSupport
        public void drain() {
            R poll;
            boolean z;
            if (getAndIncrement() == 0) {
                SimpleQueue<T> simpleQueue = this.queue;
                ArrayDeque<InnerQueuedObserver<R>> arrayDeque = this.observers;
                Observer<? super R> observer = this.downstream;
                ErrorMode errorMode = this.errorMode;
                int i = 1;
                while (true) {
                    int i2 = this.activeCount;
                    while (i2 != this.maxConcurrency) {
                        if (this.cancelled) {
                            simpleQueue.clear();
                            b();
                            return;
                        } else if (errorMode != ErrorMode.IMMEDIATE || this.errors.get() == null) {
                            try {
                                T poll2 = simpleQueue.poll();
                                if (poll2 == null) {
                                    break;
                                }
                                ObservableSource observableSource = (ObservableSource) Objects.requireNonNull(this.mapper.apply(poll2), "The mapper returned a null ObservableSource");
                                InnerQueuedObserver<R> innerQueuedObserver = new InnerQueuedObserver<>(this, this.prefetch);
                                arrayDeque.offer(innerQueuedObserver);
                                observableSource.subscribe(innerQueuedObserver);
                                i2++;
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.upstream.dispose();
                                simpleQueue.clear();
                                b();
                                this.errors.tryAddThrowableOrReport(th);
                                this.errors.tryTerminateConsumer(this.downstream);
                                return;
                            }
                        } else {
                            simpleQueue.clear();
                            b();
                            this.errors.tryTerminateConsumer(this.downstream);
                            return;
                        }
                    }
                    this.activeCount = i2;
                    if (this.cancelled) {
                        simpleQueue.clear();
                        b();
                        return;
                    } else if (errorMode != ErrorMode.IMMEDIATE || this.errors.get() == null) {
                        InnerQueuedObserver<R> innerQueuedObserver2 = this.current;
                        if (innerQueuedObserver2 == null) {
                            if (errorMode != ErrorMode.BOUNDARY || this.errors.get() == null) {
                                boolean z2 = this.done;
                                InnerQueuedObserver<R> poll3 = arrayDeque.poll();
                                boolean z3 = poll3 == null;
                                if (!z2 || !z3) {
                                    if (!z3) {
                                        this.current = poll3;
                                    }
                                    innerQueuedObserver2 = poll3;
                                } else if (this.errors.get() != null) {
                                    simpleQueue.clear();
                                    b();
                                    this.errors.tryTerminateConsumer(observer);
                                    return;
                                } else {
                                    observer.onComplete();
                                    return;
                                }
                            } else {
                                simpleQueue.clear();
                                b();
                                this.errors.tryTerminateConsumer(observer);
                                return;
                            }
                        }
                        if (innerQueuedObserver2 != null) {
                            SimpleQueue<R> queue = innerQueuedObserver2.queue();
                            while (!this.cancelled) {
                                boolean isDone = innerQueuedObserver2.isDone();
                                if (errorMode != ErrorMode.IMMEDIATE || this.errors.get() == null) {
                                    try {
                                        poll = queue.poll();
                                        z = poll == null;
                                    } catch (Throwable th2) {
                                        Exceptions.throwIfFatal(th2);
                                        this.errors.tryAddThrowableOrReport(th2);
                                        this.current = null;
                                        this.activeCount--;
                                    }
                                    if (isDone && z) {
                                        this.current = null;
                                        this.activeCount--;
                                    } else if (!z) {
                                        observer.onNext(poll);
                                    }
                                } else {
                                    simpleQueue.clear();
                                    b();
                                    this.errors.tryTerminateConsumer(observer);
                                    return;
                                }
                            }
                            simpleQueue.clear();
                            b();
                            return;
                        }
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        simpleQueue.clear();
                        b();
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    }
                }
            }
        }
    }
}
