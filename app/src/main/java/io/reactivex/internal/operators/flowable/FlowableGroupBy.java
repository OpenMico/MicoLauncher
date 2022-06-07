package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.EmptyComponent;
import io.reactivex.plugins.RxJavaPlugins;
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

/* loaded from: classes4.dex */
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

    @Override // io.reactivex.Flowable
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
        } catch (Exception e) {
            Exceptions.throwIfFatal(e);
            subscriber.onSubscribe(EmptyComponent.INSTANCE);
            subscriber.onError(e);
        }
    }

    /* loaded from: classes4.dex */
    public static final class GroupBySubscriber<T, K, V> extends BasicIntQueueSubscription<GroupedFlowable<K, V>> implements FlowableSubscriber<T> {
        static final Object a = new Object();
        private static final long serialVersionUID = -3688291656102519502L;
        final int bufferSize;
        final boolean delayError;
        boolean done;
        final Subscriber<? super GroupedFlowable<K, V>> downstream;
        Throwable error;
        final Queue<b<K, V>> evictedGroups;
        volatile boolean finished;
        final Map<Object, b<K, V>> groups;
        final Function<? super T, ? extends K> keySelector;
        boolean outputFused;
        final SpscLinkedArrayQueue<GroupedFlowable<K, V>> queue;
        Subscription upstream;
        final Function<? super T, ? extends V> valueSelector;
        final AtomicBoolean cancelled = new AtomicBoolean();
        final AtomicLong requested = new AtomicLong();
        final AtomicInteger groupCount = new AtomicInteger(1);

        public GroupBySubscriber(Subscriber<? super GroupedFlowable<K, V>> subscriber, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z, Map<Object, b<K, V>> map, Queue<b<K, V>> queue) {
            this.downstream = subscriber;
            this.keySelector = function;
            this.valueSelector = function2;
            this.bufferSize = i;
            this.delayError = z;
            this.groups = map;
            this.evictedGroups = queue;
            this.queue = new SpscLinkedArrayQueue<>(i);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
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
                SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.queue;
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
                        bVar.a((b) ObjectHelper.requireNonNull(this.valueSelector.apply(t), "The valueSelector returned null"));
                        d();
                        if (z) {
                            spscLinkedArrayQueue.offer(bVar);
                            a();
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.upstream.cancel();
                        onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.upstream.cancel();
                    onError(th2);
                }
            }
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
            this.error = th;
            this.finished = true;
            a();
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
                this.finished = true;
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled.compareAndSet(false, true)) {
                d();
                if (this.groupCount.decrementAndGet() == 0) {
                    this.upstream.cancel();
                }
            }
        }

        private void d() {
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
                if (!this.outputFused && getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        void a() {
            if (getAndIncrement() == 0) {
                if (this.outputFused) {
                    b();
                } else {
                    c();
                }
            }
        }

        void b() {
            Throwable th;
            SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.queue;
            Subscriber<? super GroupedFlowable<K, V>> subscriber = this.downstream;
            int i = 1;
            while (!this.cancelled.get()) {
                boolean z = this.finished;
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
                    } else {
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    }
                } else {
                    spscLinkedArrayQueue.clear();
                    subscriber.onError(th);
                    return;
                }
            }
        }

        void c() {
            int i;
            SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.queue;
            Subscriber<? super GroupedFlowable<K, V>> subscriber = this.downstream;
            int i2 = 1;
            do {
                long j = this.requested.get();
                long j2 = 0;
                while (true) {
                    i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
                    if (i == 0) {
                        break;
                    }
                    boolean z = this.finished;
                    GroupedFlowable<K, V> poll = spscLinkedArrayQueue.poll();
                    boolean z2 = poll == null;
                    if (!a(z, z2, subscriber, spscLinkedArrayQueue)) {
                        if (z2) {
                            break;
                        }
                        subscriber.onNext(poll);
                        j2++;
                    } else {
                        return;
                    }
                }
                if (i != 0 || !a(this.finished, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                    if (j2 != 0) {
                        if (j != Long.MAX_VALUE) {
                            this.requested.addAndGet(-j2);
                        }
                        this.upstream.request(j2);
                    }
                    i2 = addAndGet(-i2);
                } else {
                    return;
                }
            } while (i2 != 0);
        }

        boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.cancelled.get()) {
                spscLinkedArrayQueue.clear();
                return true;
            } else if (this.delayError) {
                if (!z || !z2) {
                    return false;
                }
                Throwable th = this.error;
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
                return true;
            } else if (!z) {
                return false;
            } else {
                Throwable th2 = this.error;
                if (th2 != null) {
                    spscLinkedArrayQueue.clear();
                    subscriber.onError(th2);
                    return true;
                } else if (!z2) {
                    return false;
                } else {
                    subscriber.onComplete();
                    return true;
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public GroupedFlowable<K, V> poll() {
            return this.queue.poll();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.queue.clear();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }
    }

    /* loaded from: classes4.dex */
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
    /* loaded from: classes4.dex */
    public static final class b<K, T> extends GroupedFlowable<K, T> {
        final c<T, K> c;

        public static <T, K> b<K, T> a(K k, int i, GroupBySubscriber<?, K, T> groupBySubscriber, boolean z) {
            return new b<>(k, new c(i, groupBySubscriber, k, z));
        }

        protected b(K k, c<T, K> cVar) {
            super(k);
            this.c = cVar;
        }

        @Override // io.reactivex.Flowable
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
    /* loaded from: classes4.dex */
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
        final AtomicBoolean once = new AtomicBoolean();

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
                b();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled.compareAndSet(false, true)) {
                this.parent.cancel(this.key);
                b();
            }
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            if (this.once.compareAndSet(false, true)) {
                subscriber.onSubscribe(this);
                this.actual.lazySet(subscriber);
                b();
                return;
            }
            EmptySubscription.error(new IllegalStateException("Only one Subscriber allowed!"), subscriber);
        }

        public void a(T t) {
            this.queue.offer(t);
            b();
        }

        public void a(Throwable th) {
            this.error = th;
            this.done = true;
            b();
        }

        public void a() {
            this.done = true;
            b();
        }

        void b() {
            if (getAndIncrement() == 0) {
                if (this.outputFused) {
                    c();
                } else {
                    d();
                }
            }
        }

        void c() {
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

        void d() {
            int i;
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
            boolean z = this.delayError;
            Subscriber<? super T> subscriber = this.actual.get();
            int i2 = 1;
            while (true) {
                if (subscriber != null) {
                    long j = this.requested.get();
                    long j2 = 0;
                    while (true) {
                        i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
                        if (i == 0) {
                            break;
                        }
                        boolean z2 = this.done;
                        Object obj = (T) spscLinkedArrayQueue.poll();
                        boolean z3 = obj == null;
                        if (!a(z2, z3, subscriber, z, j2)) {
                            if (z3) {
                                j2 = j2;
                                break;
                            } else {
                                subscriber.onNext(obj);
                                j2++;
                            }
                        } else {
                            return;
                        }
                    }
                    if (i == 0) {
                        if (!a(this.done, spscLinkedArrayQueue.isEmpty(), subscriber, z, j2)) {
                            j2 = j2;
                        } else {
                            return;
                        }
                    }
                    if (j2 != 0) {
                        if (j != Long.MAX_VALUE) {
                            this.requested.addAndGet(-j2);
                        }
                        this.parent.upstream.request(j2);
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

        boolean a(boolean z, boolean z2, Subscriber<? super T> subscriber, boolean z3, long j) {
            if (this.cancelled.get()) {
                while (this.queue.poll() != null) {
                    j++;
                }
                if (j != 0) {
                    this.parent.upstream.request(j);
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

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            T poll = this.queue.poll();
            if (poll != null) {
                this.produced++;
                return poll;
            }
            e();
            return null;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            if (!this.queue.isEmpty()) {
                return false;
            }
            e();
            return true;
        }

        void e() {
            int i = this.produced;
            if (i != 0) {
                this.produced = 0;
                this.parent.upstream.request(i);
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
            while (spscLinkedArrayQueue.poll() != null) {
                this.produced++;
            }
            e();
        }
    }
}
