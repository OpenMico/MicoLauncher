package io.reactivex.rxjava3.processors;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class UnicastProcessor<T> extends FlowableProcessor<T> {
    final SpscLinkedArrayQueue<T> b;
    final AtomicReference<Runnable> c;
    final boolean d;
    volatile boolean e;
    Throwable f;
    volatile boolean h;
    boolean l;
    final AtomicReference<Subscriber<? super T>> g = new AtomicReference<>();
    final AtomicBoolean i = new AtomicBoolean();
    final BasicIntQueueSubscription<T> j = new a();
    final AtomicLong k = new AtomicLong();

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create() {
        return new UnicastProcessor<>(bufferSize(), null, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int i) {
        ObjectHelper.verifyPositive(i, "capacityHint");
        return new UnicastProcessor<>(i, null, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(boolean z) {
        return new UnicastProcessor<>(bufferSize(), null, z);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int i, @NonNull Runnable runnable) {
        return create(i, runnable, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int i, @NonNull Runnable runnable, boolean z) {
        Objects.requireNonNull(runnable, "onTerminate");
        ObjectHelper.verifyPositive(i, "capacityHint");
        return new UnicastProcessor<>(i, runnable, z);
    }

    UnicastProcessor(int i, Runnable runnable, boolean z) {
        this.b = new SpscLinkedArrayQueue<>(i);
        this.c = new AtomicReference<>(runnable);
        this.d = z;
    }

    void a() {
        Runnable andSet = this.c.getAndSet(null);
        if (andSet != null) {
            andSet.run();
        }
    }

    void a(Subscriber<? super T> subscriber) {
        int i;
        long j;
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.b;
        boolean z = true;
        boolean z2 = !this.d;
        int i2 = 1;
        while (true) {
            long j2 = this.k.get();
            long j3 = 0;
            while (true) {
                i = (j2 > j3 ? 1 : (j2 == j3 ? 0 : -1));
                if (i == 0) {
                    j = j3;
                    break;
                }
                boolean z3 = this.e;
                T poll = spscLinkedArrayQueue.poll();
                if (poll != null) {
                    z = false;
                }
                j = j3;
                if (!a(z2, z3, z, subscriber, spscLinkedArrayQueue)) {
                    if (z) {
                        break;
                    }
                    subscriber.onNext(poll);
                    j3 = 1 + j;
                    z = true;
                } else {
                    return;
                }
            }
            if (i != 0 || !a(z2, this.e, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                if (!(j == 0 || j2 == Long.MAX_VALUE)) {
                    this.k.addAndGet(-j);
                }
                i2 = this.j.addAndGet(-i2);
                if (i2 != 0) {
                    z = true;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    void b(Subscriber<? super T> subscriber) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.b;
        int i = 1;
        boolean z = !this.d;
        while (!this.h) {
            boolean z2 = this.e;
            if (!z || !z2 || this.f == null) {
                subscriber.onNext(null);
                if (z2) {
                    this.g.lazySet(null);
                    Throwable th = this.f;
                    if (th != null) {
                        subscriber.onError(th);
                        return;
                    } else {
                        subscriber.onComplete();
                        return;
                    }
                } else {
                    i = this.j.addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            } else {
                spscLinkedArrayQueue.clear();
                this.g.lazySet(null);
                subscriber.onError(this.f);
                return;
            }
        }
        this.g.lazySet(null);
    }

    void b() {
        if (this.j.getAndIncrement() == 0) {
            int i = 1;
            Subscriber<? super T> subscriber = this.g.get();
            while (subscriber == null) {
                i = this.j.addAndGet(-i);
                if (i != 0) {
                    subscriber = this.g.get();
                } else {
                    return;
                }
            }
            if (this.l) {
                b(subscriber);
            } else {
                a(subscriber);
            }
        }
    }

    boolean a(boolean z, boolean z2, boolean z3, Subscriber<? super T> subscriber, SpscLinkedArrayQueue<T> spscLinkedArrayQueue) {
        if (this.h) {
            spscLinkedArrayQueue.clear();
            this.g.lazySet(null);
            return true;
        } else if (!z2) {
            return false;
        } else {
            if (z && this.f != null) {
                spscLinkedArrayQueue.clear();
                this.g.lazySet(null);
                subscriber.onError(this.f);
                return true;
            } else if (!z3) {
                return false;
            } else {
                Throwable th = this.f;
                this.g.lazySet(null);
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (this.e || this.h) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        ExceptionHelper.nullCheck(t, "onNext called with a null value.");
        if (!this.e && !this.h) {
            this.b.offer(t);
            b();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ExceptionHelper.nullCheck(th, "onError called with a null Throwable.");
        if (this.e || this.h) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.f = th;
        this.e = true;
        a();
        b();
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!this.e && !this.h) {
            this.e = true;
            a();
            b();
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        if (this.i.get() || !this.i.compareAndSet(false, true)) {
            EmptySubscription.error(new IllegalStateException("This processor allows only a single Subscriber"), subscriber);
            return;
        }
        subscriber.onSubscribe(this.j);
        this.g.set(subscriber);
        if (this.h) {
            this.g.lazySet(null);
        } else {
            b();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public final class a extends BasicIntQueueSubscription<T> {
        private static final long serialVersionUID = -4896760517184205454L;

        a() {
            UnicastProcessor.this = r1;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            return UnicastProcessor.this.b.poll();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return UnicastProcessor.this.b.isEmpty();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            UnicastProcessor.this.b.clear();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            UnicastProcessor.this.l = true;
            return 2;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(UnicastProcessor.this.k, j);
                UnicastProcessor.this.b();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!UnicastProcessor.this.h) {
                UnicastProcessor unicastProcessor = UnicastProcessor.this;
                unicastProcessor.h = true;
                unicastProcessor.a();
                UnicastProcessor.this.g.lazySet(null);
                if (UnicastProcessor.this.j.getAndIncrement() == 0) {
                    UnicastProcessor.this.g.lazySet(null);
                    if (!UnicastProcessor.this.l) {
                        UnicastProcessor.this.b.clear();
                    }
                }
            }
        }
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasSubscribers() {
        return this.g.get() != null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        if (this.e) {
            return this.f;
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasComplete() {
        return this.e && this.f == null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasThrowable() {
        return this.e && this.f != null;
    }
}
