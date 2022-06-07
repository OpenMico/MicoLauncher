package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
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
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableSwitchMap<T, R> extends a<T, R> {
    final Function<? super T, ? extends ObservableSource<? extends R>> a;
    final int b;
    final boolean c;

    public ObservableSwitchMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, boolean z) {
        super(observableSource);
        this.a = function;
        this.b = i;
        this.c = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        if (!ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.a)) {
            this.source.subscribe(new b(observer, this.a, this.b, this.c));
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        static final a<Object, Object> a = new a<>(null, -1, 1);
        private static final long serialVersionUID = -3491074160481096299L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Observer<? super R> downstream;
        final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
        volatile long unique;
        Disposable upstream;
        final AtomicReference<a<T, R>> active = new AtomicReference<>();
        final AtomicThrowable errors = new AtomicThrowable();

        static {
            a.a();
        }

        b(Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, boolean z) {
            this.downstream = observer;
            this.mapper = function;
            this.bufferSize = i;
            this.delayErrors = z;
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
            a<T, R> aVar;
            long j = this.unique + 1;
            this.unique = j;
            a<T, R> aVar2 = this.active.get();
            if (aVar2 != null) {
                aVar2.a();
            }
            try {
                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The ObservableSource returned is null");
                a<T, R> aVar3 = new a<>(this, j, this.bufferSize);
                do {
                    aVar = this.active.get();
                    if (aVar == a) {
                        return;
                    }
                } while (!this.active.compareAndSet(aVar, aVar3));
                observableSource.subscribe(aVar3);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done || !this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.delayErrors) {
                a();
            }
            this.done = true;
            b();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                b();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.dispose();
                a();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        /* JADX WARN: Multi-variable type inference failed */
        void a() {
            a<Object, Object> aVar;
            a<T, R> aVar2 = this.active.get();
            a<Object, Object> aVar3 = a;
            if (aVar2 != aVar3 && (aVar = (a) this.active.getAndSet(aVar3)) != a && aVar != null) {
                aVar.a();
            }
        }

        void b() {
            SimpleQueue<R> simpleQueue;
            R r;
            if (getAndIncrement() == 0) {
                Observer<? super R> observer = this.downstream;
                AtomicReference<a<T, R>> atomicReference = this.active;
                boolean z = this.delayErrors;
                int i = 1;
                while (!this.cancelled) {
                    if (this.done) {
                        boolean z2 = atomicReference.get() == null;
                        if (z) {
                            if (z2) {
                                Throwable th = this.errors.get();
                                if (th != null) {
                                    observer.onError(th);
                                    return;
                                } else {
                                    observer.onComplete();
                                    return;
                                }
                            }
                        } else if (this.errors.get() != null) {
                            observer.onError(this.errors.terminate());
                            return;
                        } else if (z2) {
                            observer.onComplete();
                            return;
                        }
                    }
                    a<T, R> aVar = atomicReference.get();
                    if (!(aVar == null || (simpleQueue = aVar.queue) == null)) {
                        if (aVar.done) {
                            boolean isEmpty = simpleQueue.isEmpty();
                            if (z) {
                                if (isEmpty) {
                                    atomicReference.compareAndSet(aVar, null);
                                }
                            } else if (this.errors.get() != null) {
                                observer.onError(this.errors.terminate());
                                return;
                            } else if (isEmpty) {
                                atomicReference.compareAndSet(aVar, null);
                            }
                        }
                        boolean z3 = false;
                        while (!this.cancelled) {
                            if (aVar != atomicReference.get()) {
                                z3 = true;
                            } else if (z || this.errors.get() == null) {
                                boolean z4 = aVar.done;
                                try {
                                    r = simpleQueue.poll();
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    this.errors.addThrowable(th2);
                                    atomicReference.compareAndSet(aVar, null);
                                    if (!z) {
                                        a();
                                        this.upstream.dispose();
                                        this.done = true;
                                    } else {
                                        aVar.a();
                                    }
                                    z3 = true;
                                    r = (Object) null;
                                }
                                boolean z5 = r == null;
                                if (z4 && z5) {
                                    atomicReference.compareAndSet(aVar, null);
                                    z3 = true;
                                } else if (!z5) {
                                    observer.onNext(r);
                                }
                            } else {
                                observer.onError(this.errors.terminate());
                                return;
                            }
                            if (z3) {
                                continue;
                            }
                        }
                        return;
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }

        void a(a<T, R> aVar, Throwable th) {
            if (aVar.index != this.unique || !this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.delayErrors) {
                this.upstream.dispose();
                this.done = true;
            }
            aVar.done = true;
            b();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, R> extends AtomicReference<Disposable> implements Observer<R> {
        private static final long serialVersionUID = 3837284832786408377L;
        final int bufferSize;
        volatile boolean done;
        final long index;
        final b<T, R> parent;
        volatile SimpleQueue<R> queue;

        a(b<T, R> bVar, long j, int i) {
            this.parent = bVar;
            this.index = j;
            this.bufferSize = i;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int requestFusion = queueDisposable.requestFusion(7);
                    if (requestFusion == 1) {
                        this.queue = queueDisposable;
                        this.done = true;
                        this.parent.b();
                        return;
                    } else if (requestFusion == 2) {
                        this.queue = queueDisposable;
                        return;
                    }
                }
                this.queue = new SpscLinkedArrayQueue(this.bufferSize);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(R r) {
            if (this.index == this.parent.unique) {
                if (r != null) {
                    this.queue.offer(r);
                }
                this.parent.b();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.parent.a(this, th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.index == this.parent.unique) {
                this.done = true;
                this.parent.b();
            }
        }

        public void a() {
            DisposableHelper.dispose(this);
        }
    }
}
