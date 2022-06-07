package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Cancellable;
import io.reactivex.rxjava3.internal.disposables.CancellableDisposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableCreate<T> extends Flowable<T> {
    final FlowableOnSubscribe<T> b;
    final BackpressureStrategy c;

    public FlowableCreate(FlowableOnSubscribe<T> flowableOnSubscribe, BackpressureStrategy backpressureStrategy) {
        this.b = flowableOnSubscribe;
        this.c = backpressureStrategy;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar;
        switch (this.c) {
            case MISSING:
                aVar = new f(subscriber);
                break;
            case ERROR:
                aVar = new d(subscriber);
                break;
            case DROP:
                aVar = new c(subscriber);
                break;
            case LATEST:
                aVar = new e(subscriber);
                break;
            default:
                aVar = new b(subscriber, bufferSize());
                break;
        }
        subscriber.onSubscribe(aVar);
        try {
            this.b.subscribe(aVar);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            aVar.onError(th);
        }
    }

    /* loaded from: classes5.dex */
    static final class h<T> extends AtomicInteger implements FlowableEmitter<T> {
        private static final long serialVersionUID = 4883307006032401862L;
        volatile boolean done;
        final a<T> emitter;
        final AtomicThrowable errors = new AtomicThrowable();
        final SimplePlainQueue<T> queue = new SpscLinkedArrayQueue(16);

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public FlowableEmitter<T> serialize() {
            return this;
        }

        h(a<T> aVar) {
            this.emitter = aVar;
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(T t) {
            if (!this.emitter.isCancelled() && !this.done) {
                if (t == null) {
                    onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
                    return;
                }
                if (get() != 0 || !compareAndSet(0, 1)) {
                    SimplePlainQueue<T> simplePlainQueue = this.queue;
                    synchronized (simplePlainQueue) {
                        simplePlainQueue.offer(t);
                    }
                    if (getAndIncrement() != 0) {
                        return;
                    }
                } else {
                    this.emitter.onNext(t);
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
                b();
            }
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onError(Throwable th) {
            if (!tryOnError(th)) {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public boolean tryOnError(Throwable th) {
            if (this.emitter.isCancelled() || this.done) {
                return false;
            }
            if (th == null) {
                th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            if (!this.errors.tryAddThrowable(th)) {
                return false;
            }
            this.done = true;
            a();
            return true;
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            if (!this.emitter.isCancelled() && !this.done) {
                this.done = true;
                a();
            }
        }

        void a() {
            if (getAndIncrement() == 0) {
                b();
            }
        }

        void b() {
            a<T> aVar = this.emitter;
            SimplePlainQueue<T> simplePlainQueue = this.queue;
            AtomicThrowable atomicThrowable = this.errors;
            int i = 1;
            while (!aVar.isCancelled()) {
                if (atomicThrowable.get() != null) {
                    simplePlainQueue.clear();
                    atomicThrowable.tryTerminateConsumer(aVar);
                    return;
                }
                boolean z = this.done;
                T poll = simplePlainQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    aVar.onComplete();
                    return;
                } else if (z2) {
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                } else {
                    aVar.onNext(poll);
                }
            }
            simplePlainQueue.clear();
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public void setDisposable(Disposable disposable) {
            this.emitter.setDisposable(disposable);
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public void setCancellable(Cancellable cancellable) {
            this.emitter.setCancellable(cancellable);
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public long requested() {
            return this.emitter.requested();
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public boolean isCancelled() {
            return this.emitter.isCancelled();
        }

        @Override // java.util.concurrent.atomic.AtomicInteger
        public String toString() {
            return this.emitter.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static abstract class a<T> extends AtomicLong implements FlowableEmitter<T>, Subscription {
        private static final long serialVersionUID = 7326289992464377023L;
        final Subscriber<? super T> downstream;
        final SequentialDisposable serial = new SequentialDisposable();

        void b() {
        }

        void c() {
        }

        a(Subscriber<? super T> subscriber) {
            this.downstream = subscriber;
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            a();
        }

        protected void a() {
            if (!isCancelled()) {
                try {
                    this.downstream.onComplete();
                } finally {
                    this.serial.dispose();
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public final void onError(Throwable th) {
            if (th == null) {
                th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            if (!a(th)) {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public final boolean tryOnError(Throwable th) {
            if (th == null) {
                th = ExceptionHelper.createNullPointerException("tryOnError called with a null Throwable.");
            }
            return a(th);
        }

        public boolean a(Throwable th) {
            return b(th);
        }

        /* JADX WARN: Finally extract failed */
        protected boolean b(Throwable th) {
            if (isCancelled()) {
                return false;
            }
            try {
                this.downstream.onError(th);
                this.serial.dispose();
                return true;
            } catch (Throwable th2) {
                this.serial.dispose();
                throw th2;
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.serial.dispose();
            b();
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public final boolean isCancelled() {
            return this.serial.isDisposed();
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
                c();
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public final void setDisposable(Disposable disposable) {
            this.serial.update(disposable);
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public final void setCancellable(Cancellable cancellable) {
            setDisposable(new CancellableDisposable(cancellable));
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public final long requested() {
            return get();
        }

        @Override // io.reactivex.rxjava3.core.FlowableEmitter
        public final FlowableEmitter<T> serialize() {
            return new h(this);
        }

        @Override // java.util.concurrent.atomic.AtomicLong
        public String toString() {
            return String.format("%s{%s}", getClass().getSimpleName(), super.toString());
        }
    }

    /* loaded from: classes5.dex */
    static final class f<T> extends a<T> {
        private static final long serialVersionUID = 3776720187248809713L;

        f(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(T t) {
            long j;
            if (!isCancelled()) {
                if (t != null) {
                    this.downstream.onNext(t);
                    do {
                        j = get();
                        if (j == 0) {
                            return;
                        }
                    } while (!compareAndSet(j, j - 1));
                    return;
                }
                onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
            }
        }
    }

    /* loaded from: classes5.dex */
    static abstract class g<T> extends a<T> {
        private static final long serialVersionUID = 4127754106204442833L;

        abstract void d();

        g(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public final void onNext(T t) {
            if (!isCancelled()) {
                if (t == null) {
                    onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
                } else if (get() != 0) {
                    this.downstream.onNext(t);
                    BackpressureHelper.produced(this, 1L);
                } else {
                    d();
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class c<T> extends g<T> {
        private static final long serialVersionUID = 8360058422307496563L;

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.g
        void d() {
        }

        c(Subscriber<? super T> subscriber) {
            super(subscriber);
        }
    }

    /* loaded from: classes5.dex */
    static final class d<T> extends g<T> {
        private static final long serialVersionUID = 338953216916120960L;

        d(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.g
        void d() {
            onError(new MissingBackpressureException("create: could not emit value due to lack of requests"));
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends a<T> {
        private static final long serialVersionUID = 2427151001689639875L;
        volatile boolean done;
        Throwable error;
        final SpscLinkedArrayQueue<T> queue;
        final AtomicInteger wip = new AtomicInteger();

        b(Subscriber<? super T> subscriber, int i) {
            super(subscriber);
            this.queue = new SpscLinkedArrayQueue<>(i);
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(T t) {
            if (!this.done && !isCancelled()) {
                if (t == null) {
                    onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
                    return;
                }
                this.queue.offer(t);
                d();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.a
        public boolean a(Throwable th) {
            if (this.done || isCancelled()) {
                return false;
            }
            this.error = th;
            this.done = true;
            d();
            return true;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.a, io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            this.done = true;
            d();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.a
        void c() {
            d();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.a
        void b() {
            if (this.wip.getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        void d() {
            int i;
            if (this.wip.getAndIncrement() == 0) {
                Subscriber subscriber = this.downstream;
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
                int i2 = 1;
                do {
                    long j = get();
                    long j2 = 0;
                    while (true) {
                        i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
                        if (i == 0) {
                            break;
                        } else if (isCancelled()) {
                            spscLinkedArrayQueue.clear();
                            return;
                        } else {
                            boolean z = this.done;
                            T poll = spscLinkedArrayQueue.poll();
                            boolean z2 = poll == null;
                            if (z && z2) {
                                Throwable th = this.error;
                                if (th != null) {
                                    b(th);
                                    return;
                                } else {
                                    a();
                                    return;
                                }
                            } else if (z2) {
                                break;
                            } else {
                                subscriber.onNext(poll);
                                j2++;
                            }
                        }
                    }
                    if (i == 0) {
                        if (isCancelled()) {
                            spscLinkedArrayQueue.clear();
                            return;
                        }
                        boolean z3 = this.done;
                        boolean isEmpty = spscLinkedArrayQueue.isEmpty();
                        if (z3 && isEmpty) {
                            Throwable th2 = this.error;
                            if (th2 != null) {
                                b(th2);
                                return;
                            } else {
                                a();
                                return;
                            }
                        }
                    }
                    if (j2 != 0) {
                        BackpressureHelper.produced(this, j2);
                    }
                    i2 = this.wip.addAndGet(-i2);
                } while (i2 != 0);
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class e<T> extends a<T> {
        private static final long serialVersionUID = 4023437720691792495L;
        volatile boolean done;
        Throwable error;
        final AtomicReference<T> queue = new AtomicReference<>();
        final AtomicInteger wip = new AtomicInteger();

        e(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(T t) {
            if (!this.done && !isCancelled()) {
                if (t == null) {
                    onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
                    return;
                }
                this.queue.set(t);
                d();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.a
        public boolean a(Throwable th) {
            if (this.done || isCancelled()) {
                return false;
            }
            this.error = th;
            this.done = true;
            d();
            return true;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.a, io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            this.done = true;
            d();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.a
        void c() {
            d();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.a
        void b() {
            if (this.wip.getAndIncrement() == 0) {
                this.queue.lazySet(null);
            }
        }

        void d() {
            int i;
            boolean z;
            if (this.wip.getAndIncrement() == 0) {
                Subscriber subscriber = this.downstream;
                AtomicReference<T> atomicReference = this.queue;
                int i2 = 1;
                do {
                    long j = get();
                    long j2 = 0;
                    while (true) {
                        i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
                        z = false;
                        if (i == 0) {
                            break;
                        } else if (isCancelled()) {
                            atomicReference.lazySet(null);
                            return;
                        } else {
                            boolean z2 = this.done;
                            T andSet = atomicReference.getAndSet(null);
                            boolean z3 = andSet == null;
                            if (z2 && z3) {
                                Throwable th = this.error;
                                if (th != null) {
                                    b(th);
                                    return;
                                } else {
                                    a();
                                    return;
                                }
                            } else if (z3) {
                                break;
                            } else {
                                subscriber.onNext(andSet);
                                j2++;
                            }
                        }
                    }
                    if (i == 0) {
                        if (isCancelled()) {
                            atomicReference.lazySet(null);
                            return;
                        }
                        boolean z4 = this.done;
                        if (atomicReference.get() == null) {
                            z = true;
                        }
                        if (z4 && z) {
                            Throwable th2 = this.error;
                            if (th2 != null) {
                                b(th2);
                                return;
                            } else {
                                a();
                                return;
                            }
                        }
                    }
                    if (j2 != 0) {
                        BackpressureHelper.produced(this, j2);
                    }
                    i2 = this.wip.addAndGet(-i2);
                } while (i2 != 0);
            }
        }
    }
}
