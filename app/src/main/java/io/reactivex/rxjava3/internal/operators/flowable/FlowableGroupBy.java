package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.flowables.GroupedFlowable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.EmptyComponent;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableGroupBy<T, K, V> extends a<T, GroupedFlowable<K, V>> {
    final Function<? super T, ? extends K> b;
    final Function<? super T, ? extends V> c;
    final int d;
    final boolean e;
    final Function<? super Consumer<Object>, ? extends Map<K, Object>> f;

    public FlowableGroupBy(Flowable<T> flowable, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z, Function<? super Consumer<Object>, ? extends Map<K, Object>> function3) {
        super(flowable);
        this.b = function;
        this.c = function2;
        this.d = i;
        this.e = z;
        this.f = function3;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super GroupedFlowable<K, V>> subscriber) {
        ConcurrentLinkedQueue concurrentLinkedQueue;
        Map map;
        try {
            if (this.f == null) {
                concurrentLinkedQueue = null;
                map = new ConcurrentHashMap();
            } else {
                ConcurrentLinkedQueue concurrentLinkedQueue2 = new ConcurrentLinkedQueue();
                map = (Map) this.f.apply(new a(concurrentLinkedQueue2));
                concurrentLinkedQueue = concurrentLinkedQueue2;
            }
            this.source.subscribe((FlowableSubscriber) new GroupBySubscriber(subscriber, this.b, this.c, this.d, this.e, map, concurrentLinkedQueue));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            subscriber.onSubscribe(EmptyComponent.INSTANCE);
            subscriber.onError(th);
        }
    }

    /* loaded from: classes5.dex */
    public static final class GroupBySubscriber<T, K, V> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        static final Object a = new Object();
        private static final long serialVersionUID = -3688291656102519502L;
        final int bufferSize;
        final boolean delayError;
        boolean done;
        final Subscriber<? super GroupedFlowable<K, V>> downstream;
        long emittedGroups;
        final Queue<b<K, V>> evictedGroups;
        final Map<Object, b<K, V>> groups;
        final Function<? super T, ? extends K> keySelector;
        final int limit;
        Subscription upstream;
        final Function<? super T, ? extends V> valueSelector;
        final AtomicBoolean cancelled = new AtomicBoolean();
        final AtomicInteger groupCount = new AtomicInteger(1);
        final AtomicLong groupConsumed = new AtomicLong();

        public GroupBySubscriber(Subscriber<? super GroupedFlowable<K, V>> subscriber, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z, Map<Object, b<K, V>> map, Queue<b<K, V>> queue) {
            this.downstream = subscriber;
            this.keySelector = function;
            this.valueSelector = function2;
            this.bufferSize = i;
            this.limit = i - (i >> 2);
            this.delayError = z;
            this.groups = map;
            this.evictedGroups = queue;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(this.bufferSize);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                try {
                    Object apply = this.keySelector.apply(t);
                    boolean z = false;
                    Object obj = apply != null ? apply : a;
                    b<K, V> bVar = this.groups.get(obj);
                    if (bVar == null) {
                        if (!this.cancelled.get()) {
                            bVar = b.a(apply, this.bufferSize, (GroupBySubscriber<?, Object, T>) this, this.delayError);
                            this.groups.put(obj, bVar);
                            this.groupCount.getAndIncrement();
                            z = true;
                        } else {
                            return;
                        }
                    }
                    try {
                        bVar.a((b<K, V>) ((V) ExceptionHelper.nullCheck(this.valueSelector.apply(t), "The valueSelector returned a null value.")));
                        a();
                        if (!z) {
                            return;
                        }
                        if (this.emittedGroups != get()) {
                            this.emittedGroups++;
                            this.downstream.onNext(bVar);
                            if (bVar.c.c()) {
                                cancel(apply);
                                bVar.a();
                                b(1L);
                                return;
                            }
                            return;
                        }
                        this.upstream.cancel();
                        onError(new MissingBackpressureException(a(this.emittedGroups)));
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.upstream.cancel();
                        if (z) {
                            if (this.emittedGroups != get()) {
                                this.downstream.onNext(bVar);
                            } else {
                                MissingBackpressureException missingBackpressureException = new MissingBackpressureException(a(this.emittedGroups));
                                missingBackpressureException.initCause(th);
                                onError(missingBackpressureException);
                                return;
                            }
                        }
                        onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.upstream.cancel();
                    onError(th2);
                }
            }
        }

        static String a(long j) {
            return "Unable to emit a new group (#" + j + ") due to lack of requests. Please make sure the downstream can always accept a new group as well as each group is consumed in order for the whole operator to be able to proceed.";
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            for (b<K, V> bVar : this.groups.values()) {
                bVar.a(th);
            }
            this.groups.clear();
            Queue<b<K, V>> queue = this.evictedGroups;
            if (queue != null) {
                queue.clear();
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                for (b<K, V> bVar : this.groups.values()) {
                    bVar.a();
                }
                this.groups.clear();
                Queue<b<K, V>> queue = this.evictedGroups;
                if (queue != null) {
                    queue.clear();
                }
                this.done = true;
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled.compareAndSet(false, true)) {
                a();
                if (this.groupCount.decrementAndGet() == 0) {
                    this.upstream.cancel();
                }
            }
        }

        private void a() {
            if (this.evictedGroups != null) {
                int i = 0;
                while (true) {
                    b<K, V> poll = this.evictedGroups.poll();
                    if (poll == null) {
                        break;
                    }
                    poll.a();
                    i++;
                }
                if (i != 0) {
                    this.groupCount.addAndGet(-i);
                }
            }
        }

        public void cancel(K k) {
            if (k == null) {
                k = (K) a;
            }
            this.groups.remove(k);
            if (this.groupCount.decrementAndGet() == 0) {
                this.upstream.cancel();
            }
        }

        void b(long j) {
            long j2;
            long addCap;
            AtomicLong atomicLong = this.groupConsumed;
            int i = this.limit;
            do {
                j2 = atomicLong.get();
                addCap = BackpressureHelper.addCap(j2, j);
            } while (!atomicLong.compareAndSet(j2, addCap));
            while (true) {
                long j3 = i;
                if (addCap >= j3) {
                    if (atomicLong.compareAndSet(addCap, addCap - j3)) {
                        this.upstream.request(j3);
                    }
                    addCap = atomicLong.get();
                } else {
                    return;
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class a<K, V> implements Consumer<b<K, V>> {
        final Queue<b<K, V>> a;

        a(Queue<b<K, V>> queue) {
            this.a = queue;
        }

        /* renamed from: a */
        public void accept(b<K, V> bVar) {
            this.a.offer(bVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b<K, T> extends GroupedFlowable<K, T> {
        final c<T, K> c;

        public static <T, K> b<K, T> a(K k, int i, GroupBySubscriber<?, K, T> groupBySubscriber, boolean z) {
            return new b<>(k, new c(i, groupBySubscriber, k, z));
        }

        protected b(K k, c<T, K> cVar) {
            super(k);
            this.c = cVar;
        }

        @Override // io.reactivex.rxjava3.core.Flowable
        protected void subscribeActual(Subscriber<? super T> subscriber) {
            this.c.subscribe(subscriber);
        }

        public void a(T t) {
            this.c.a((c<T, K>) t);
        }

        public void a(Throwable th) {
            this.c.a(th);
        }

        public void a() {
            this.c.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c<T, K> extends BasicIntQueueSubscription<T> implements Publisher<T> {
        private static final long serialVersionUID = -3852313036005250360L;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final K key;
        boolean outputFused;
        final GroupBySubscriber<?, K, T> parent;
        int produced;
        final SpscLinkedArrayQueue<T> queue;
        final AtomicLong requested = new AtomicLong();
        final AtomicBoolean cancelled = new AtomicBoolean();
        final AtomicReference<Subscriber<? super T>> actual = new AtomicReference<>();
        final AtomicInteger once = new AtomicInteger();

        c(int i, GroupBySubscriber<?, K, T> groupBySubscriber, K k, boolean z) {
            this.queue = new SpscLinkedArrayQueue<>(i);
            this.parent = groupBySubscriber;
            this.key = k;
            this.delayError = z;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                d();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled.compareAndSet(false, true)) {
                b();
                d();
            }
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            int i;
            do {
                i = this.once.get();
                if ((i & 1) != 0) {
                    EmptySubscription.error(new IllegalStateException("Only one Subscriber allowed!"), subscriber);
                    return;
                }
            } while (!this.once.compareAndSet(i, i | 1));
            subscriber.onSubscribe(this);
            this.actual.lazySet(subscriber);
            if (this.cancelled.get()) {
                this.actual.lazySet(null);
            } else {
                d();
            }
        }

        public void a(T t) {
            this.queue.offer(t);
            d();
        }

        public void a(Throwable th) {
            this.error = th;
            this.done = true;
            d();
        }

        public void a() {
            this.done = true;
            d();
        }

        void b() {
            if ((this.once.get() & 2) == 0) {
                this.parent.cancel(this.key);
            }
        }

        boolean c() {
            return this.once.get() == 0 && this.once.compareAndSet(0, 2);
        }

        void d() {
            if (getAndIncrement() == 0) {
                if (this.outputFused) {
                    e();
                } else {
                    f();
                }
            }
        }

        void e() {
            Throwable th;
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
            Subscriber<? super T> subscriber = this.actual.get();
            int i = 1;
            while (true) {
                if (subscriber != null) {
                    if (!this.cancelled.get()) {
                        boolean z = this.done;
                        if (!z || this.delayError || (th = this.error) == null) {
                            subscriber.onNext(null);
                            if (z) {
                                Throwable th2 = this.error;
                                if (th2 != null) {
                                    subscriber.onError(th2);
                                    return;
                                } else {
                                    subscriber.onComplete();
                                    return;
                                }
                            }
                        } else {
                            spscLinkedArrayQueue.clear();
                            subscriber.onError(th);
                            return;
                        }
                    } else {
                        return;
                    }
                }
                i = addAndGet(-i);
                if (i != 0) {
                    if (subscriber == null) {
                        subscriber = this.actual.get();
                    }
                } else {
                    return;
                }
            }
        }

        void f() {
            int i;
            long j;
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
            boolean z = this.delayError;
            Subscriber<? super T> subscriber = this.actual.get();
            int i2 = 1;
            while (true) {
                if (subscriber != null) {
                    long j2 = this.requested.get();
                    long j3 = 0;
                    while (true) {
                        i = (j3 > j2 ? 1 : (j3 == j2 ? 0 : -1));
                        if (i == 0) {
                            break;
                        }
                        boolean z2 = this.done;
                        Object obj = (T) spscLinkedArrayQueue.poll();
                        boolean z3 = obj == null;
                        if (!a(z2, z3, subscriber, z, j3)) {
                            if (z3) {
                                j3 = j3;
                                break;
                            } else {
                                subscriber.onNext(obj);
                                j3++;
                            }
                        } else {
                            return;
                        }
                    }
                    if (i == 0) {
                        j = j3;
                        if (a(this.done, spscLinkedArrayQueue.isEmpty(), subscriber, z, j3)) {
                            return;
                        }
                    } else {
                        j = j3;
                    }
                    if (j != 0) {
                        BackpressureHelper.produced(this.requested, j);
                        a(j);
                    }
                }
                i2 = addAndGet(-i2);
                if (i2 != 0) {
                    if (subscriber == null) {
                        subscriber = this.actual.get();
                    }
                } else {
                    return;
                }
            }
        }

        void a(long j) {
            if ((this.once.get() & 2) == 0) {
                this.parent.b(j);
            }
        }

        boolean a(boolean z, boolean z2, Subscriber<? super T> subscriber, boolean z3, long j) {
            if (this.cancelled.get()) {
                while (this.queue.poll() != null) {
                    j++;
                }
                if (j != 0) {
                    a(j);
                }
                return true;
            } else if (!z) {
                return false;
            } else {
                if (!z3) {
                    Throwable th = this.error;
                    if (th != null) {
                        this.queue.clear();
                        subscriber.onError(th);
                        return true;
                    } else if (!z2) {
                        return false;
                    } else {
                        subscriber.onComplete();
                        return true;
                    }
                } else if (!z2) {
                    return false;
                } else {
                    Throwable th2 = this.error;
                    if (th2 != null) {
                        subscriber.onError(th2);
                    } else {
                        subscriber.onComplete();
                    }
                    return true;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        void g() {
            int i = this.produced;
            if (i != 0) {
                this.produced = 0;
                a(i);
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            T poll = this.queue.poll();
            if (poll != null) {
                this.produced++;
                return poll;
            }
            g();
            return null;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            if (this.queue.isEmpty()) {
                g();
                return true;
            }
            g();
            return false;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
            while (spscLinkedArrayQueue.poll() != null) {
                this.produced++;
            }
            g();
        }
    }
}
