package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableConcatMapSingle<T, R> extends Observable<R> {
    final ObservableSource<T> a;
    final Function<? super T, ? extends SingleSource<? extends R>> b;
    final ErrorMode c;
    final int d;

    public ObservableConcatMapSingle(ObservableSource<T> observableSource, Function<? super T, ? extends SingleSource<? extends R>> function, ErrorMode errorMode, int i) {
        this.a = observableSource;
        this.b = function;
        this.c = errorMode;
        this.d = i;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        if (!a.b(this.a, this.b, observer)) {
            this.a.subscribe(new a(observer, this.b, this.d, this.c));
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = -9140123220065488293L;
        volatile boolean cancelled;
        volatile boolean done;
        final Observer<? super R> downstream;
        final ErrorMode errorMode;
        final AtomicThrowable errors = new AtomicThrowable();
        final C0314a<R> inner = new C0314a<>(this);
        R item;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        final SimplePlainQueue<T> queue;
        volatile int state;
        Disposable upstream;

        a(Observer<? super R> observer, Function<? super T, ? extends SingleSource<? extends R>> function, int i, ErrorMode errorMode) {
            this.downstream = observer;
            this.mapper = function;
            this.errorMode = errorMode;
            this.queue = new SpscLinkedArrayQueue(i);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            this.queue.offer(t);
            a();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (this.errorMode == ErrorMode.IMMEDIATE) {
                    this.inner.a();
                }
                this.done = true;
                a();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            a();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.inner.a();
            this.errors.tryTerminateAndReport();
            if (getAndIncrement() == 0) {
                this.queue.clear();
                this.item = null;
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a(R r) {
            this.item = r;
            this.state = 2;
            a();
        }

        void a(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.dispose();
                }
                this.state = 0;
                a();
            }
        }

        void a() {
            if (getAndIncrement() == 0) {
                Observer<? super R> observer = this.downstream;
                ErrorMode errorMode = this.errorMode;
                SimplePlainQueue<T> simplePlainQueue = this.queue;
                AtomicThrowable atomicThrowable = this.errors;
                int i = 1;
                while (true) {
                    if (this.cancelled) {
                        simplePlainQueue.clear();
                        this.item = null;
                    } else {
                        int i2 = this.state;
                        if (atomicThrowable.get() == null || !(errorMode == ErrorMode.IMMEDIATE || (errorMode == ErrorMode.BOUNDARY && i2 == 0))) {
                            boolean z = false;
                            if (i2 == 0) {
                                boolean z2 = this.done;
                                T poll = simplePlainQueue.poll();
                                if (poll == null) {
                                    z = true;
                                }
                                if (z2 && z) {
                                    atomicThrowable.tryTerminateConsumer(observer);
                                    return;
                                } else if (!z) {
                                    try {
                                        SingleSource singleSource = (SingleSource) Objects.requireNonNull(this.mapper.apply(poll), "The mapper returned a null SingleSource");
                                        this.state = 1;
                                        singleSource.subscribe(this.inner);
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        this.upstream.dispose();
                                        simplePlainQueue.clear();
                                        atomicThrowable.tryAddThrowableOrReport(th);
                                        atomicThrowable.tryTerminateConsumer(observer);
                                        return;
                                    }
                                }
                            } else if (i2 == 2) {
                                this.item = null;
                                observer.onNext((R) this.item);
                                this.state = 0;
                            }
                        }
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
                simplePlainQueue.clear();
                this.item = null;
                atomicThrowable.tryTerminateConsumer(observer);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapSingle$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public static final class C0314a<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
            private static final long serialVersionUID = -3051469169682093892L;
            final a<?, R> parent;

            C0314a(a<?, R> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R r) {
                this.parent.a((a<?, R>) r);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                this.parent.a(th);
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
