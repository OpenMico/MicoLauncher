package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class ParallelSortedJoin<T> extends Flowable<T> {
    final ParallelFlowable<List<T>> b;
    final Comparator<? super T> c;

    public ParallelSortedJoin(ParallelFlowable<List<T>> parallelFlowable, Comparator<? super T> comparator) {
        this.b = parallelFlowable;
        this.c = comparator;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        b bVar = new b(subscriber, this.b.parallelism(), this.c);
        subscriber.onSubscribe(bVar);
        this.b.subscribe(bVar.subscribers);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 3481980673745556697L;
        volatile boolean cancelled;
        final Comparator<? super T> comparator;
        final Subscriber<? super T> downstream;
        final int[] indexes;
        final List<T>[] lists;
        final a<T>[] subscribers;
        final AtomicLong requested = new AtomicLong();
        final AtomicInteger remaining = new AtomicInteger();
        final AtomicReference<Throwable> error = new AtomicReference<>();

        b(Subscriber<? super T> subscriber, int i, Comparator<? super T> comparator) {
            this.downstream = subscriber;
            this.comparator = comparator;
            a<T>[] aVarArr = new a[i];
            for (int i2 = 0; i2 < i; i2++) {
                aVarArr[i2] = new a<>(this, i2);
            }
            this.subscribers = aVarArr;
            this.lists = new List[i];
            this.indexes = new int[i];
            this.remaining.lazySet(i);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                if (this.remaining.get() == 0) {
                    b();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                a();
                if (getAndIncrement() == 0) {
                    Arrays.fill(this.lists, (Object) null);
                }
            }
        }

        void a() {
            for (a<T> aVar : this.subscribers) {
                aVar.a();
            }
        }

        void a(List<T> list, int i) {
            this.lists[i] = list;
            if (this.remaining.decrementAndGet() == 0) {
                b();
            }
        }

        void a(Throwable th) {
            if (this.error.compareAndSet(null, th)) {
                b();
            } else if (th != this.error.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        void b() {
            if (getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = this.downstream;
                List<T>[] listArr = this.lists;
                int[] iArr = this.indexes;
                int length = iArr.length;
                int i = 1;
                do {
                    long j = this.requested.get();
                    long j2 = 0;
                    while (j2 != j) {
                        if (this.cancelled) {
                            Arrays.fill(listArr, (Object) null);
                            return;
                        }
                        Throwable th = this.error.get();
                        if (th != null) {
                            a();
                            Arrays.fill(listArr, (Object) null);
                            subscriber.onError(th);
                            return;
                        }
                        int i2 = -1;
                        int i3 = 0;
                        Object obj = (Object) null;
                        while (i3 < length) {
                            List<T> list = listArr[i3];
                            int i4 = iArr[i3];
                            if (list.size() != i4) {
                                if (obj == null) {
                                    obj = (Object) list.get(i4);
                                    i2 = i3;
                                } else {
                                    T t = list.get(i4);
                                    try {
                                        if (this.comparator.compare(obj, t) > 0) {
                                            obj = (Object) t;
                                            i2 = i3;
                                        }
                                    } catch (Throwable th2) {
                                        Exceptions.throwIfFatal(th2);
                                        a();
                                        Arrays.fill(listArr, (Object) null);
                                        if (!this.error.compareAndSet(null, th2)) {
                                            RxJavaPlugins.onError(th2);
                                        }
                                        subscriber.onError(this.error.get());
                                        return;
                                    }
                                }
                            }
                            i3++;
                            obj = obj;
                        }
                        if (obj == null) {
                            Arrays.fill(listArr, (Object) null);
                            subscriber.onComplete();
                            return;
                        }
                        subscriber.onNext(obj);
                        iArr[i2] = iArr[i2] + 1;
                        j2++;
                    }
                    boolean z = true;
                    if (this.cancelled) {
                        Arrays.fill(listArr, (Object) null);
                        return;
                    }
                    Throwable th3 = this.error.get();
                    if (th3 != null) {
                        a();
                        Arrays.fill(listArr, (Object) null);
                        subscriber.onError(th3);
                        return;
                    }
                    int i5 = 0;
                    while (true) {
                        if (i5 >= length) {
                            break;
                        } else if (iArr[i5] != listArr[i5].size()) {
                            z = false;
                            break;
                        } else {
                            i5++;
                        }
                    }
                    if (z) {
                        Arrays.fill(listArr, (Object) null);
                        subscriber.onComplete();
                        return;
                    }
                    if (j2 != 0) {
                        BackpressureHelper.produced(this.requested, j2);
                    }
                    i = addAndGet(-i);
                } while (i != 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicReference<Subscription> implements FlowableSubscriber<List<T>> {
        private static final long serialVersionUID = 6751017204873808094L;
        final int index;
        final b<T> parent;

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        a(b<T> bVar, int i) {
            this.parent = bVar;
            this.index = i;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }

        /* renamed from: a */
        public void onNext(List<T> list) {
            this.parent.a(list, this.index);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.a(th);
        }

        void a() {
            SubscriptionHelper.cancel(this);
        }
    }
}
