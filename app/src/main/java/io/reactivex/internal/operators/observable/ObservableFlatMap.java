package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableFlatMap<T, U> extends a<T, U> {
    final Function<? super T, ? extends ObservableSource<? extends U>> a;
    final boolean b;
    final int c;
    final int d;

    public ObservableFlatMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends U>> function, boolean z, int i, int i2) {
        super(observableSource);
        this.a = function;
        this.b = z;
        this.c = i;
        this.d = i2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super U> observer) {
        if (!ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.a)) {
            this.source.subscribe(new b(observer, this.a, this.b, this.c, this.d));
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, U> extends AtomicInteger implements Observer<T>, Disposable {
        static final a<?, ?>[] a = new a[0];
        static final a<?, ?>[] b = new a[0];
        private static final long serialVersionUID = -2117620485640801370L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Observer<? super U> downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        long lastId;
        int lastIndex;
        final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
        final int maxConcurrency;
        final AtomicReference<a<?, ?>[]> observers;
        volatile SimplePlainQueue<U> queue;
        Queue<ObservableSource<? extends U>> sources;
        long uniqueId;
        Disposable upstream;
        int wip;

        b(Observer<? super U> observer, Function<? super T, ? extends ObservableSource<? extends U>> function, boolean z, int i, int i2) {
            this.downstream = observer;
            this.mapper = function;
            this.delayErrors = z;
            this.maxConcurrency = i;
            this.bufferSize = i2;
            if (i != Integer.MAX_VALUE) {
                this.sources = new ArrayDeque(i);
            }
            this.observers = new AtomicReference<>(a);
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
            if (!this.done) {
                try {
                    ObservableSource<? extends U> observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null ObservableSource");
                    if (this.maxConcurrency != Integer.MAX_VALUE) {
                        synchronized (this) {
                            if (this.wip == this.maxConcurrency) {
                                this.sources.offer(observableSource);
                                return;
                            }
                            this.wip++;
                        }
                    }
                    a(observableSource);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.dispose();
                    onError(th);
                }
            }
        }

        void a(ObservableSource<? extends U> observableSource) {
            ObservableSource<? extends U> poll;
            while (observableSource instanceof Callable) {
                if (a((Callable) observableSource) && this.maxConcurrency != Integer.MAX_VALUE) {
                    boolean z = false;
                    synchronized (this) {
                        poll = this.sources.poll();
                        if (poll == null) {
                            this.wip--;
                            z = true;
                        }
                    }
                    if (z) {
                        a();
                        return;
                    }
                    observableSource = poll;
                } else {
                    return;
                }
            }
            long j = this.uniqueId;
            this.uniqueId = 1 + j;
            a<T, U> aVar = new a<>(this, j);
            if (a(aVar)) {
                observableSource.subscribe(aVar);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        boolean a(a<T, U> aVar) {
            a<?, ?>[] aVarArr;
            a[] aVarArr2;
            do {
                aVarArr = this.observers.get();
                if (aVarArr == b) {
                    aVar.a();
                    return false;
                }
                int length = aVarArr.length;
                aVarArr2 = new a[length + 1];
                System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
                aVarArr2[length] = aVar;
            } while (!this.observers.compareAndSet(aVarArr, aVarArr2));
            return true;
        }

        void b(a<T, U> aVar) {
            a<?, ?>[] aVarArr;
            a<?, ?>[] aVarArr2;
            do {
                aVarArr = this.observers.get();
                int length = aVarArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (aVarArr[i2] == aVar) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            aVarArr2 = a;
                        } else {
                            a<?, ?>[] aVarArr3 = new a[length - 1];
                            System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                            System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                            aVarArr2 = aVarArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.observers.compareAndSet(aVarArr, aVarArr2));
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v3, types: [io.reactivex.internal.fuseable.SimplePlainQueue] */
        boolean a(Callable<? extends U> callable) {
            SpscArrayQueue spscArrayQueue;
            try {
                Object call = callable.call();
                if (call == null) {
                    return true;
                }
                if (get() != 0 || !compareAndSet(0, 1)) {
                    SimplePlainQueue<U> simplePlainQueue = this.queue;
                    SimplePlainQueue<U> simplePlainQueue2 = simplePlainQueue;
                    if (simplePlainQueue == null) {
                        int i = this.maxConcurrency;
                        if (i == Integer.MAX_VALUE) {
                            spscArrayQueue = new SpscLinkedArrayQueue(this.bufferSize);
                        } else {
                            spscArrayQueue = new SpscArrayQueue(i);
                        }
                        this.queue = spscArrayQueue;
                        simplePlainQueue2 = spscArrayQueue;
                    }
                    if (!simplePlainQueue2.offer(call)) {
                        onError(new IllegalStateException("Scalar queue full?!"));
                        return true;
                    } else if (getAndIncrement() != 0) {
                        return false;
                    }
                } else {
                    this.downstream.onNext(call);
                    if (decrementAndGet() == 0) {
                        return true;
                    }
                }
                b();
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.errors.addThrowable(th);
                a();
                return true;
            }
        }

        void a(U u, a<T, U> aVar) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                SimpleQueue simpleQueue = aVar.queue;
                if (simpleQueue == null) {
                    simpleQueue = new SpscLinkedArrayQueue(this.bufferSize);
                    aVar.queue = simpleQueue;
                }
                simpleQueue.offer(u);
                if (getAndIncrement() != 0) {
                    return;
                }
            } else {
                this.downstream.onNext(u);
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            b();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else if (this.errors.addThrowable(th)) {
                this.done = true;
                a();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                a();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            Throwable terminate;
            if (!this.cancelled) {
                this.cancelled = true;
                if (d() && (terminate = this.errors.terminate()) != null && terminate != ExceptionHelper.TERMINATED) {
                    RxJavaPlugins.onError(terminate);
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a() {
            if (getAndIncrement() == 0) {
                b();
            }
        }

        void b() {
            int i;
            int i2;
            Observer<? super U> observer = this.downstream;
            int i3 = 1;
            while (!c()) {
                SimplePlainQueue<U> simplePlainQueue = this.queue;
                if (simplePlainQueue != null) {
                    i = 0;
                    while (!c()) {
                        Object obj = (U) simplePlainQueue.poll();
                        if (obj != null) {
                            observer.onNext(obj);
                            i++;
                        }
                    }
                    return;
                }
                i = 0;
                if (i == 0) {
                    boolean z = this.done;
                    SimplePlainQueue<U> simplePlainQueue2 = this.queue;
                    a<?, ?>[] aVarArr = this.observers.get();
                    int length = aVarArr.length;
                    if (this.maxConcurrency != Integer.MAX_VALUE) {
                        synchronized (this) {
                            i2 = this.sources.size();
                        }
                    } else {
                        i2 = 0;
                    }
                    if (!z || !((simplePlainQueue2 == null || simplePlainQueue2.isEmpty()) && length == 0 && i2 == 0)) {
                        if (length != 0) {
                            long j = this.lastId;
                            int i4 = this.lastIndex;
                            if (length <= i4 || aVarArr[i4].id != j) {
                                if (length <= i4) {
                                    i4 = 0;
                                }
                                for (int i5 = 0; i5 < length && aVarArr[i4].id != j; i5++) {
                                    i4++;
                                    if (i4 == length) {
                                        i4 = 0;
                                    }
                                }
                                this.lastIndex = i4;
                                this.lastId = aVarArr[i4].id;
                                i4 = i4;
                            }
                            int i6 = i;
                            for (int i7 = 0; i7 < length; i7++) {
                                if (!c()) {
                                    a<T, U> aVar = aVarArr[i4];
                                    SimpleQueue<U> simpleQueue = aVar.queue;
                                    if (simpleQueue != null) {
                                        while (true) {
                                            try {
                                                Object obj2 = (U) simpleQueue.poll();
                                                if (obj2 == null) {
                                                    break;
                                                }
                                                observer.onNext(obj2);
                                                if (c()) {
                                                    return;
                                                }
                                            } catch (Throwable th) {
                                                Exceptions.throwIfFatal(th);
                                                aVar.a();
                                                this.errors.addThrowable(th);
                                                if (!c()) {
                                                    b(aVar);
                                                    i6++;
                                                    i4++;
                                                    if (i4 == length) {
                                                        i4 = 0;
                                                    }
                                                } else {
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                    boolean z2 = aVar.done;
                                    SimpleQueue<U> simpleQueue2 = aVar.queue;
                                    if (z2 && (simpleQueue2 == null || simpleQueue2.isEmpty())) {
                                        b(aVar);
                                        if (!c()) {
                                            i6++;
                                        } else {
                                            return;
                                        }
                                    }
                                    int i8 = i4 + 1;
                                    i4 = i8 == length ? 0 : i8;
                                } else {
                                    return;
                                }
                            }
                            this.lastIndex = i4;
                            this.lastId = aVarArr[i4].id;
                            i = i6;
                        }
                        if (i == 0) {
                            i3 = addAndGet(-i3);
                            if (i3 == 0) {
                                return;
                            }
                        } else if (this.maxConcurrency != Integer.MAX_VALUE) {
                            a(i);
                        }
                    } else {
                        Throwable terminate = this.errors.terminate();
                        if (terminate == ExceptionHelper.TERMINATED) {
                            return;
                        }
                        if (terminate == null) {
                            observer.onComplete();
                            return;
                        } else {
                            observer.onError(terminate);
                            return;
                        }
                    }
                } else if (this.maxConcurrency != Integer.MAX_VALUE) {
                    a(i);
                }
            }
        }

        void a(int i) {
            while (true) {
                int i2 = i - 1;
                if (i != 0) {
                    synchronized (this) {
                        ObservableSource<? extends U> poll = this.sources.poll();
                        if (poll == null) {
                            this.wip--;
                        } else {
                            a(poll);
                        }
                    }
                    i = i2;
                } else {
                    return;
                }
            }
        }

        boolean c() {
            if (this.cancelled) {
                return true;
            }
            Throwable th = this.errors.get();
            if (this.delayErrors || th == null) {
                return false;
            }
            d();
            Throwable terminate = this.errors.terminate();
            if (terminate != ExceptionHelper.TERMINATED) {
                this.downstream.onError(terminate);
            }
            return true;
        }

        boolean d() {
            a<?, ?>[] andSet;
            this.upstream.dispose();
            a<?, ?>[] aVarArr = this.observers.get();
            a<?, ?>[] aVarArr2 = b;
            if (aVarArr == aVarArr2 || (andSet = this.observers.getAndSet(aVarArr2)) == b) {
                return false;
            }
            for (a<?, ?> aVar : andSet) {
                aVar.a();
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, U> extends AtomicReference<Disposable> implements Observer<U> {
        private static final long serialVersionUID = -4606175640614850599L;
        volatile boolean done;
        int fusionMode;
        final long id;
        final b<T, U> parent;
        volatile SimpleQueue<U> queue;

        a(b<T, U> bVar, long j) {
            this.id = j;
            this.parent = bVar;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable) && (disposable instanceof QueueDisposable)) {
                QueueDisposable queueDisposable = (QueueDisposable) disposable;
                int requestFusion = queueDisposable.requestFusion(7);
                if (requestFusion == 1) {
                    this.fusionMode = requestFusion;
                    this.queue = queueDisposable;
                    this.done = true;
                    this.parent.a();
                } else if (requestFusion == 2) {
                    this.fusionMode = requestFusion;
                    this.queue = queueDisposable;
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(U u) {
            if (this.fusionMode == 0) {
                this.parent.a(u, this);
            } else {
                this.parent.a();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.parent.errors.addThrowable(th)) {
                if (!this.parent.delayErrors) {
                    this.parent.d();
                }
                this.done = true;
                this.parent.a();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            this.parent.a();
        }

        public void a() {
            DisposableHelper.dispose(this);
        }
    }
}
