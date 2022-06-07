package io.reactivex.processors;

import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@SchedulerSupport("none")
@BackpressureSupport(BackpressureKind.FULL)
/* loaded from: classes4.dex */
public final class MulticastProcessor<T> extends FlowableProcessor<T> {
    static final a[] n = new a[0];
    static final a[] o = new a[0];
    final int f;
    final int g;
    final boolean h;
    volatile SimpleQueue<T> i;
    volatile boolean j;
    volatile Throwable k;
    int l;
    int m;
    final AtomicInteger b = new AtomicInteger();
    final AtomicReference<a<T>[]> d = new AtomicReference<>(n);
    final AtomicReference<Subscription> c = new AtomicReference<>();
    final AtomicBoolean e = new AtomicBoolean();

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create() {
        return new MulticastProcessor<>(bufferSize(), false);
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create(boolean z) {
        return new MulticastProcessor<>(bufferSize(), z);
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create(int i) {
        return new MulticastProcessor<>(i, false);
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create(int i, boolean z) {
        return new MulticastProcessor<>(i, z);
    }

    MulticastProcessor(int i, boolean z) {
        ObjectHelper.verifyPositive(i, "bufferSize");
        this.f = i;
        this.g = i - (i >> 2);
        this.h = z;
    }

    public void start() {
        if (SubscriptionHelper.setOnce(this.c, EmptySubscription.INSTANCE)) {
            this.i = new SpscArrayQueue(this.f);
        }
    }

    public void startUnbounded() {
        if (SubscriptionHelper.setOnce(this.c, EmptySubscription.INSTANCE)) {
            this.i = new SpscLinkedArrayQueue(this.f);
        }
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this.c, subscription)) {
            if (subscription instanceof QueueSubscription) {
                QueueSubscription queueSubscription = (QueueSubscription) subscription;
                int requestFusion = queueSubscription.requestFusion(3);
                if (requestFusion == 1) {
                    this.m = requestFusion;
                    this.i = queueSubscription;
                    this.j = true;
                    a();
                    return;
                } else if (requestFusion == 2) {
                    this.m = requestFusion;
                    this.i = queueSubscription;
                    subscription.request(this.f);
                    return;
                }
            }
            this.i = new SpscArrayQueue(this.f);
            subscription.request(this.f);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (!this.e.get()) {
            if (this.m == 0) {
                ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
                if (!this.i.offer(t)) {
                    SubscriptionHelper.cancel(this.c);
                    onError(new MissingBackpressureException());
                    return;
                }
            }
            a();
        }
    }

    public boolean offer(T t) {
        if (this.e.get()) {
            return false;
        }
        ObjectHelper.requireNonNull(t, "offer called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.m != 0 || !this.i.offer(t)) {
            return false;
        }
        a();
        return true;
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.e.compareAndSet(false, true)) {
            this.k = th;
            this.j = true;
            a();
            return;
        }
        RxJavaPlugins.onError(th);
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.e.compareAndSet(false, true)) {
            this.j = true;
            a();
        }
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasSubscribers() {
        return this.d.get().length != 0;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasThrowable() {
        return this.e.get() && this.k != null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasComplete() {
        return this.e.get() && this.k == null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public Throwable getThrowable() {
        if (this.e.get()) {
            return this.k;
        }
        return null;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        Throwable th;
        a<T> aVar = new a<>(subscriber, this);
        subscriber.onSubscribe(aVar);
        if (a(aVar)) {
            if (aVar.get() == Long.MIN_VALUE) {
                b(aVar);
            } else {
                a();
            }
        } else if ((this.e.get() || !this.h) && (th = this.k) != null) {
            subscriber.onError(th);
        } else {
            subscriber.onComplete();
        }
    }

    boolean a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.d.get();
            if (aVarArr == o) {
                return false;
            }
            int length = aVarArr.length;
            aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
        } while (!this.d.compareAndSet(aVarArr, aVarArr2));
        return true;
    }

    void b(a<T> aVar) {
        while (true) {
            a<T>[] aVarArr = this.d.get();
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
                    if (length != 1) {
                        a<T>[] aVarArr2 = new a[length - 1];
                        System.arraycopy(aVarArr, 0, aVarArr2, 0, i);
                        System.arraycopy(aVarArr, i + 1, aVarArr2, i, (length - i) - 1);
                        if (this.d.compareAndSet(aVarArr, aVarArr2)) {
                            return;
                        }
                    } else if (this.h) {
                        if (this.d.compareAndSet(aVarArr, o)) {
                            SubscriptionHelper.cancel(this.c);
                            this.e.set(true);
                            return;
                        }
                    } else if (this.d.compareAndSet(aVarArr, n)) {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    void a() {
        int i;
        boolean z;
        T t;
        if (this.b.getAndIncrement() == 0) {
            AtomicReference<a<T>[]> atomicReference = this.d;
            int i2 = this.l;
            int i3 = this.g;
            int i4 = this.m;
            int i5 = 1;
            while (true) {
                SimpleQueue<T> simpleQueue = this.i;
                if (simpleQueue != null) {
                    a<T>[] aVarArr = atomicReference.get();
                    if (aVarArr.length != 0) {
                        int length = aVarArr.length;
                        long j = -1;
                        long j2 = -1;
                        int i6 = 0;
                        while (i6 < length) {
                            a<T> aVar = aVarArr[i6];
                            long j3 = aVar.get();
                            if (j3 >= 0) {
                                if (j2 == j) {
                                    j2 = j3 - aVar.emitted;
                                } else {
                                    j2 = Math.min(j2, j3 - aVar.emitted);
                                }
                            }
                            i6++;
                            j = -1;
                        }
                        int i7 = i2;
                        while (true) {
                            i = (j2 > 0L ? 1 : (j2 == 0L ? 0 : -1));
                            if (i <= 0) {
                                break;
                            }
                            a<T>[] aVarArr2 = atomicReference.get();
                            if (aVarArr2 == o) {
                                simpleQueue.clear();
                                return;
                            } else if (aVarArr != aVarArr2) {
                                break;
                            } else {
                                try {
                                    z = this.j;
                                    t = simpleQueue.poll();
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    SubscriptionHelper.cancel(this.c);
                                    t = null;
                                    this.k = th;
                                    this.j = true;
                                    z = true;
                                }
                                boolean z2 = t == null;
                                if (z && z2) {
                                    Throwable th2 = this.k;
                                    if (th2 != null) {
                                        for (a<T> aVar2 : atomicReference.getAndSet(o)) {
                                            aVar2.a(th2);
                                        }
                                        return;
                                    }
                                    for (a<T> aVar3 : atomicReference.getAndSet(o)) {
                                        aVar3.a();
                                    }
                                    return;
                                } else if (z2) {
                                    break;
                                } else {
                                    for (a<T> aVar4 : aVarArr) {
                                        aVar4.a((a<T>) t);
                                    }
                                    j2--;
                                    if (i4 != 1) {
                                        int i8 = i7 + 1;
                                        if (i8 == i3) {
                                            this.c.get().request(i3);
                                            i7 = 0;
                                        } else {
                                            i7 = i8;
                                        }
                                    }
                                }
                            }
                        }
                        if (i == 0) {
                            a<T>[] aVarArr3 = atomicReference.get();
                            if (aVarArr3 == o) {
                                simpleQueue.clear();
                                return;
                            } else if (aVarArr != aVarArr3) {
                                i2 = i7;
                            } else if (this.j && simpleQueue.isEmpty()) {
                                Throwable th3 = this.k;
                                if (th3 != null) {
                                    for (a<T> aVar5 : atomicReference.getAndSet(o)) {
                                        aVar5.a(th3);
                                    }
                                    return;
                                }
                                for (a<T> aVar6 : atomicReference.getAndSet(o)) {
                                    aVar6.a();
                                }
                                return;
                            }
                        }
                        i2 = i7;
                    }
                }
                this.l = i2;
                i5 = this.b.addAndGet(-i5);
                if (i5 == 0) {
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = -363282618957264509L;
        final Subscriber<? super T> downstream;
        long emitted;
        final MulticastProcessor<T> parent;

        a(Subscriber<? super T> subscriber, MulticastProcessor<T> multicastProcessor) {
            this.downstream = subscriber;
            this.parent = multicastProcessor;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            long j2;
            long j3;
            if (SubscriptionHelper.validate(j)) {
                do {
                    j2 = get();
                    if (j2 != Long.MIN_VALUE) {
                        j3 = Long.MAX_VALUE;
                        if (j2 != Long.MAX_VALUE) {
                            long j4 = j2 + j;
                            if (j4 >= 0) {
                                j3 = j4;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } while (!compareAndSet(j2, j3));
                this.parent.a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.b(this);
            }
        }

        void a(T t) {
            if (get() != Long.MIN_VALUE) {
                this.emitted++;
                this.downstream.onNext(t);
            }
        }

        void a(Throwable th) {
            if (get() != Long.MIN_VALUE) {
                this.downstream.onError(th);
            }
        }

        void a() {
            if (get() != Long.MIN_VALUE) {
                this.downstream.onComplete();
            }
        }
    }
}
