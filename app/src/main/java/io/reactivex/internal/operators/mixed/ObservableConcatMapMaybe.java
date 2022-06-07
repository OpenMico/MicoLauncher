package io.reactivex.internal.operators.mixed;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableConcatMapMaybe<T, R> extends Observable<R> {
    final Observable<T> a;
    final Function<? super T, ? extends MaybeSource<? extends R>> b;
    final ErrorMode c;
    final int d;

    public ObservableConcatMapMaybe(Observable<T> observable, Function<? super T, ? extends MaybeSource<? extends R>> function, ErrorMode errorMode, int i) {
        this.a = observable;
        this.b = function;
        this.c = errorMode;
        this.d = i;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        if (!a.a(this.a, this.b, observer)) {
            this.a.subscribe(new a(observer, this.b, this.d, this.c));
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = -9140123220065488293L;
        volatile boolean cancelled;
        volatile boolean done;
        final Observer<? super R> downstream;
        final ErrorMode errorMode;
        final AtomicThrowable errors = new AtomicThrowable();
        final C0248a<R> inner = new C0248a<>(this);
        R item;
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
        final SimplePlainQueue<T> queue;
        volatile int state;
        Disposable upstream;

        a(Observer<? super R> observer, Function<? super T, ? extends MaybeSource<? extends R>> function, int i, ErrorMode errorMode) {
            this.downstream = observer;
            this.mapper = function;
            this.errorMode = errorMode;
            this.queue = new SpscLinkedArrayQueue(i);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.queue.offer(t);
            b();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.errors.addThrowable(th)) {
                if (this.errorMode == ErrorMode.IMMEDIATE) {
                    this.inner.a();
                }
                this.done = true;
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            b();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.inner.a();
            if (getAndIncrement() == 0) {
                this.queue.clear();
                this.item = null;
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a(R r) {
            this.item = r;
            this.state = 2;
            b();
        }

        void a() {
            this.state = 0;
            b();
        }

        void a(Throwable th) {
            if (this.errors.addThrowable(th)) {
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.dispose();
                }
                this.state = 0;
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        void b() {
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
                                    Throwable terminate = atomicThrowable.terminate();
                                    if (terminate == null) {
                                        observer.onComplete();
                                        return;
                                    } else {
                                        observer.onError(terminate);
                                        return;
                                    }
                                } else if (!z) {
                                    try {
                                        MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.mapper.apply(poll), "The mapper returned a null MaybeSource");
                                        this.state = 1;
                                        maybeSource.subscribe(this.inner);
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        this.upstream.dispose();
                                        simplePlainQueue.clear();
                                        atomicThrowable.addThrowable(th);
                                        observer.onError(atomicThrowable.terminate());
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
                observer.onError(atomicThrowable.terminate());
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.internal.operators.mixed.ObservableConcatMapMaybe$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static final class C0248a<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
            private static final long serialVersionUID = -3051469169682093892L;
            final a<?, R> parent;

            C0248a(a<?, R> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSuccess(R r) {
                this.parent.a((a<?, R>) r);
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.parent.a(th);
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                this.parent.a();
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
