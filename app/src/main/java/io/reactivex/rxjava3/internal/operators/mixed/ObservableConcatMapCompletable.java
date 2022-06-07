package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.QueueDisposable;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableConcatMapCompletable<T> extends Completable {
    final Observable<T> a;
    final Function<? super T, ? extends CompletableSource> b;
    final ErrorMode c;
    final int d;

    public ObservableConcatMapCompletable(Observable<T> observable, Function<? super T, ? extends CompletableSource> function, ErrorMode errorMode, int i) {
        this.a = observable;
        this.b = function;
        this.c = errorMode;
        this.d = i;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        if (!a.a(this.a, this.b, completableObserver)) {
            this.a.subscribe(new a(completableObserver, this.b, this.c, this.d));
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 3610901111000061034L;
        volatile boolean active;
        volatile boolean disposed;
        volatile boolean done;
        final CompletableObserver downstream;
        final ErrorMode errorMode;
        final AtomicThrowable errors = new AtomicThrowable();
        final C0312a inner = new C0312a(this);
        final Function<? super T, ? extends CompletableSource> mapper;
        final int prefetch;
        SimpleQueue<T> queue;
        Disposable upstream;

        a(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, ErrorMode errorMode, int i) {
            this.downstream = completableObserver;
            this.mapper = function;
            this.errorMode = errorMode;
            this.prefetch = i;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int requestFusion = queueDisposable.requestFusion(3);
                    if (requestFusion == 1) {
                        this.queue = queueDisposable;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        b();
                        return;
                    } else if (requestFusion == 2) {
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
            if (t != null) {
                this.queue.offer(t);
            }
            b();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (!this.errors.tryAddThrowableOrReport(th)) {
                return;
            }
            if (this.errorMode == ErrorMode.IMMEDIATE) {
                this.disposed = true;
                this.inner.a();
                this.errors.tryTerminateConsumer(this.downstream);
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                    return;
                }
                return;
            }
            this.done = true;
            b();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            b();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.upstream.dispose();
            this.inner.a();
            this.errors.tryTerminateAndReport();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        void a(Throwable th) {
            if (!this.errors.tryAddThrowableOrReport(th)) {
                return;
            }
            if (this.errorMode == ErrorMode.IMMEDIATE) {
                this.disposed = true;
                this.upstream.dispose();
                this.errors.tryTerminateConsumer(this.downstream);
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                    return;
                }
                return;
            }
            this.active = false;
            b();
        }

        void a() {
            this.active = false;
            b();
        }

        void b() {
            boolean z;
            if (getAndIncrement() == 0) {
                AtomicThrowable atomicThrowable = this.errors;
                ErrorMode errorMode = this.errorMode;
                while (!this.disposed) {
                    if (!this.active) {
                        if (errorMode != ErrorMode.BOUNDARY || atomicThrowable.get() == null) {
                            boolean z2 = this.done;
                            CompletableSource completableSource = null;
                            try {
                                T poll = this.queue.poll();
                                if (poll != null) {
                                    completableSource = (CompletableSource) Objects.requireNonNull(this.mapper.apply(poll), "The mapper returned a null CompletableSource");
                                    z = false;
                                } else {
                                    z = true;
                                }
                                if (z2 && z) {
                                    this.disposed = true;
                                    atomicThrowable.tryTerminateConsumer(this.downstream);
                                    return;
                                } else if (!z) {
                                    this.active = true;
                                    completableSource.subscribe(this.inner);
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.disposed = true;
                                this.queue.clear();
                                this.upstream.dispose();
                                atomicThrowable.tryAddThrowableOrReport(th);
                                atomicThrowable.tryTerminateConsumer(this.downstream);
                                return;
                            }
                        } else {
                            this.disposed = true;
                            this.queue.clear();
                            atomicThrowable.tryTerminateConsumer(this.downstream);
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
                this.queue.clear();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapCompletable$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public static final class C0312a extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = 5638352172918776687L;
            final a<?> parent;

            C0312a(a<?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                this.parent.a(th);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.a();
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
