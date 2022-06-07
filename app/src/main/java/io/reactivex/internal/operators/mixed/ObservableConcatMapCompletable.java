package io.reactivex.internal.operators.mixed;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
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

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        if (!a.a(this.a, this.b, completableObserver)) {
            this.a.subscribe(new a(completableObserver, this.b, this.c, this.d));
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 3610901111000061034L;
        volatile boolean active;
        volatile boolean disposed;
        volatile boolean done;
        final CompletableObserver downstream;
        final ErrorMode errorMode;
        final AtomicThrowable errors = new AtomicThrowable();
        final C0247a inner = new C0247a(this);
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

        @Override // io.reactivex.Observer
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

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (t != null) {
                this.queue.offer(t);
            }
            b();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (this.errorMode == ErrorMode.IMMEDIATE) {
                this.disposed = true;
                this.inner.a();
                Throwable terminate = this.errors.terminate();
                if (terminate != ExceptionHelper.TERMINATED) {
                    this.downstream.onError(terminate);
                }
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            } else {
                this.done = true;
                b();
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            b();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.upstream.dispose();
            this.inner.a();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        void a(Throwable th) {
            if (!this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (this.errorMode == ErrorMode.IMMEDIATE) {
                this.disposed = true;
                this.upstream.dispose();
                Throwable terminate = this.errors.terminate();
                if (terminate != ExceptionHelper.TERMINATED) {
                    this.downstream.onError(terminate);
                }
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            } else {
                this.active = false;
                b();
            }
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
                                    completableSource = (CompletableSource) ObjectHelper.requireNonNull(this.mapper.apply(poll), "The mapper returned a null CompletableSource");
                                    z = false;
                                } else {
                                    z = true;
                                }
                                if (z2 && z) {
                                    this.disposed = true;
                                    Throwable terminate = atomicThrowable.terminate();
                                    if (terminate != null) {
                                        this.downstream.onError(terminate);
                                        return;
                                    } else {
                                        this.downstream.onComplete();
                                        return;
                                    }
                                } else if (!z) {
                                    this.active = true;
                                    completableSource.subscribe(this.inner);
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.disposed = true;
                                this.queue.clear();
                                this.upstream.dispose();
                                atomicThrowable.addThrowable(th);
                                this.downstream.onError(atomicThrowable.terminate());
                                return;
                            }
                        } else {
                            this.disposed = true;
                            this.queue.clear();
                            this.downstream.onError(atomicThrowable.terminate());
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
        /* renamed from: io.reactivex.internal.operators.mixed.ObservableConcatMapCompletable$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static final class C0247a extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = 5638352172918776687L;
            final a<?> parent;

            C0247a(a<?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.parent.a(th);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                this.parent.a();
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
