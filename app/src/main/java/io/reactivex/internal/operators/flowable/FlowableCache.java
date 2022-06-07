package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableCache<T> extends a<T, T> implements FlowableSubscriber<T> {
    static final a[] e = new a[0];
    static final a[] f = new a[0];
    final int c;
    volatile long g;
    final b<T> h;
    b<T> i;
    int j;
    Throwable k;
    volatile boolean l;
    final AtomicBoolean b = new AtomicBoolean();
    final AtomicReference<a<T>[]> d = new AtomicReference<>(e);

    public FlowableCache(Flowable<T> flowable, int i) {
        super(flowable);
        this.c = i;
        b<T> bVar = new b<>(i);
        this.h = bVar;
        this.i = bVar;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a<T> aVar = new a<>(subscriber, this);
        subscriber.onSubscribe(aVar);
        a(aVar);
        if (this.b.get() || !this.b.compareAndSet(false, true)) {
            c(aVar);
        } else {
            this.source.subscribe((FlowableSubscriber) this);
        }
    }

    void a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.d.get();
            if (aVarArr != f) {
                int length = aVarArr.length;
                aVarArr2 = new a[length + 1];
                System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
                aVarArr2[length] = aVar;
            } else {
                return;
            }
        } while (!this.d.compareAndSet(aVarArr, aVarArr2));
    }

    void b(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.d.get();
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
                        aVarArr2 = e;
                    } else {
                        a<T>[] aVarArr3 = new a[length - 1];
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
        } while (!this.d.compareAndSet(aVarArr, aVarArr2));
    }

    void c(a<T> aVar) {
        if (aVar.getAndIncrement() == 0) {
            long j = aVar.index;
            int i = aVar.offset;
            b<T> bVar = aVar.node;
            AtomicLong atomicLong = aVar.requested;
            Subscriber<? super T> subscriber = aVar.downstream;
            int i2 = this.c;
            b<T> bVar2 = bVar;
            int i3 = i;
            int i4 = 1;
            while (true) {
                boolean z = this.l;
                boolean z2 = this.g == j;
                if (!z || !z2) {
                    if (!z2) {
                        long j2 = atomicLong.get();
                        if (j2 == Long.MIN_VALUE) {
                            aVar.node = null;
                            return;
                        } else if (j2 != j) {
                            if (i3 == i2) {
                                bVar2 = bVar2.b;
                                i3 = 0;
                            }
                            subscriber.onNext((Object) bVar2.a[i3]);
                            i3++;
                            j++;
                        }
                    }
                    aVar.index = j;
                    aVar.offset = i3;
                    aVar.node = bVar2;
                    i4 = aVar.addAndGet(-i4);
                    if (i4 == 0) {
                        return;
                    }
                } else {
                    aVar.node = null;
                    Throwable th = this.k;
                    if (th != null) {
                        subscriber.onError(th);
                        return;
                    } else {
                        subscriber.onComplete();
                        return;
                    }
                }
            }
        }
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        int i = this.j;
        if (i == this.c) {
            b<T> bVar = new b<>(i);
            bVar.a[0] = t;
            this.j = 1;
            this.i.b = bVar;
            this.i = bVar;
        } else {
            this.i.a[i] = t;
            this.j = i + 1;
        }
        this.g++;
        for (a<T> aVar : this.d.get()) {
            c(aVar);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.l) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.k = th;
        this.l = true;
        for (a<T> aVar : this.d.getAndSet(f)) {
            c(aVar);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        this.l = true;
        for (a<T> aVar : this.d.getAndSet(f)) {
            c(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 6770240836423125754L;
        final Subscriber<? super T> downstream;
        long index;
        b<T> node;
        int offset;
        final FlowableCache<T> parent;
        final AtomicLong requested = new AtomicLong();

        a(Subscriber<? super T> subscriber, FlowableCache<T> flowableCache) {
            this.downstream = subscriber;
            this.parent = flowableCache;
            this.node = flowableCache.h;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this.requested, j);
                this.parent.c(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.requested.getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.b(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> {
        final T[] a;
        volatile b<T> b;

        b(int i) {
            this.a = (T[]) new Object[i];
        }
    }
}
