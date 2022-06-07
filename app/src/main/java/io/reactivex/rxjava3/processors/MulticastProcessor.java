package io.reactivex.rxjava3.processors;

import io.reactivex.rxjava3.annotations.BackpressureKind;
import io.reactivex.rxjava3.annotations.BackpressureSupport;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.SchedulerSupport;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@BackpressureSupport(BackpressureKind.FULL)
@SchedulerSupport("none")
/* loaded from: classes5.dex */
public final class MulticastProcessor<T> extends FlowableProcessor<T> {
    static final a[] m = new a[0];
    static final a[] n = new a[0];
    final int e;
    final int f;
    final boolean g;
    volatile SimpleQueue<T> h;
    volatile boolean i;
    volatile Throwable j;
    int k;
    int l;
    final AtomicInteger b = new AtomicInteger();
    final AtomicReference<a<T>[]> d = new AtomicReference<>(m);
    final AtomicReference<Subscription> c = new AtomicReference<>();

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
        ObjectHelper.verifyPositive(i, "bufferSize");
        return new MulticastProcessor<>(i, false);
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create(int i, boolean z) {
        ObjectHelper.verifyPositive(i, "bufferSize");
        return new MulticastProcessor<>(i, z);
    }

    MulticastProcessor(int i, boolean z) {
        this.e = i;
        this.f = i - (i >> 2);
        this.g = z;
    }

    public void start() {
        if (SubscriptionHelper.setOnce(this.c, EmptySubscription.INSTANCE)) {
            this.h = new SpscArrayQueue(this.e);
        }
    }

    public void startUnbounded() {
        if (SubscriptionHelper.setOnce(this.c, EmptySubscription.INSTANCE)) {
            this.h = new SpscLinkedArrayQueue(this.e);
        }
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(@NonNull Subscription subscription) {
        if (SubscriptionHelper.setOnce(this.c, subscription)) {
            if (subscription instanceof QueueSubscription) {
                QueueSubscription queueSubscription = (QueueSubscription) subscription;
                int requestFusion = queueSubscription.requestFusion(3);
                if (requestFusion == 1) {
                    this.l = requestFusion;
                    this.h = queueSubscription;
                    this.i = true;
                    a();
                    return;
                } else if (requestFusion == 2) {
                    this.l = requestFusion;
                    this.h = queueSubscription;
                    subscription.request(this.e);
                    return;
                }
            }
            this.h = new SpscArrayQueue(this.e);
            subscription.request(this.e);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NonNull T t) {
        if (!this.i) {
            if (this.l == 0) {
                ExceptionHelper.nullCheck(t, "onNext called with a null value.");
                if (!this.h.offer(t)) {
                    SubscriptionHelper.cancel(this.c);
                    onError(new MissingBackpressureException());
                    return;
                }
            }
            a();
        }
    }

    @CheckReturnValue
    public boolean offer(@NonNull T t) {
        ExceptionHelper.nullCheck(t, "offer called with a null value.");
        if (this.i) {
            return false;
        }
        if (this.l != 0) {
            throw new IllegalStateException("offer() should not be called in fusion mode!");
        } else if (!this.h.offer(t)) {
            return false;
        } else {
            a();
            return true;
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(@NonNull Throwable th) {
        ExceptionHelper.nullCheck(th, "onError called with a null Throwable.");
        if (!this.i) {
            this.j = th;
            this.i = true;
            a();
            return;
        }
        RxJavaPlugins.onError(th);
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        this.i = true;
        a();
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasSubscribers() {
        return this.d.get().length != 0;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasThrowable() {
        return this.i && this.j != null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasComplete() {
        return this.i && this.j == null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public Throwable getThrowable() {
        if (this.i) {
            return this.j;
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(@NonNull Subscriber<? super T> subscriber) {
        Throwable th;
        a<T> aVar = new a<>(subscriber, this);
        subscriber.onSubscribe(aVar);
        if (a(aVar)) {
            if (aVar.get() == Long.MIN_VALUE) {
                b(aVar);
            } else {
                a();
            }
        } else if (!this.i || (th = this.j) == null) {
            subscriber.onComplete();
        } else {
            subscriber.onError(th);
        }
    }

    boolean a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.d.get();
            if (aVarArr == n) {
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
                    } else if (this.g) {
                        if (this.d.compareAndSet(aVarArr, n)) {
                            SubscriptionHelper.cancel(this.c);
                            this.i = true;
                            return;
                        }
                    } else if (this.d.compareAndSet(aVarArr, m)) {
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
            int i2 = this.k;
            int i3 = this.f;
            int i4 = this.l;
            int i5 = 1;
            while (true) {
                SimpleQueue<T> simpleQueue = this.h;
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
                            if (aVarArr2 == n) {
                                simpleQueue.clear();
                                return;
                            } else if (aVarArr != aVarArr2) {
                                break;
                            } else {
                                try {
                                    z = this.i;
                                    t = simpleQueue.poll();
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    SubscriptionHelper.cancel(this.c);
                                    t = null;
                                    this.j = th;
                                    this.i = true;
                                    z = true;
                                }
                                boolean z2 = t == null;
                                if (z && z2) {
                                    Throwable th2 = this.j;
                                    if (th2 != null) {
                                        for (a<T> aVar2 : atomicReference.getAndSet(n)) {
                                            aVar2.a(th2);
                                        }
                                        return;
                                    }
                                    for (a<T> aVar3 : atomicReference.getAndSet(n)) {
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
                            if (aVarArr3 == n) {
                                simpleQueue.clear();
                                return;
                            } else if (aVarArr != aVarArr3) {
                                i2 = i7;
                            } else if (this.i && simpleQueue.isEmpty()) {
                                Throwable th3 = this.j;
                                if (th3 != null) {
                                    for (a<T> aVar5 : atomicReference.getAndSet(n)) {
                                        aVar5.a(th3);
                                    }
                                    return;
                                }
                                for (a<T> aVar6 : atomicReference.getAndSet(n)) {
                                    aVar6.a();
                                }
                                return;
                            }
                        }
                        i2 = i7;
                    }
                }
                this.k = i2;
                i5 = this.b.addAndGet(-i5);
                if (i5 == 0) {
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
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
            if (SubscriptionHelper.validate(j)) {
                long addCancel = BackpressureHelper.addCancel(this, j);
                if (addCancel != Long.MIN_VALUE && addCancel != Long.MAX_VALUE) {
                    this.parent.a();
                }
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
