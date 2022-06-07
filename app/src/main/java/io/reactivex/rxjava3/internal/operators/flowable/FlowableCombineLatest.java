package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableMap;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableCombineLatest<T, R> extends Flowable<R> {
    @Nullable
    final Publisher<? extends T>[] b;
    @Nullable
    final Iterable<? extends Publisher<? extends T>> c;
    final Function<? super Object[], ? extends R> d;
    final int e;
    final boolean f;

    public FlowableCombineLatest(@NonNull Publisher<? extends T>[] publisherArr, @NonNull Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.b = publisherArr;
        this.c = null;
        this.d = function;
        this.e = i;
        this.f = z;
    }

    public FlowableCombineLatest(@NonNull Iterable<? extends Publisher<? extends T>> iterable, @NonNull Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.b = null;
        this.c = iterable;
        this.d = function;
        this.e = i;
        this.f = z;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        int i;
        Publisher<? extends T>[] publisherArr = this.b;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                Iterator<? extends Publisher<? extends T>> it = this.c.iterator();
                i = 0;
                while (it.hasNext()) {
                    Publisher publisher = (Publisher) it.next();
                    if (i == publisherArr.length) {
                        Publisher<? extends T>[] publisherArr2 = new Publisher[(i >> 2) + i];
                        System.arraycopy(publisherArr, 0, publisherArr2, 0, i);
                        publisherArr = publisherArr2;
                    }
                    i++;
                    publisherArr[i] = (Publisher) Objects.requireNonNull(publisher, "The Iterator returned a null Publisher");
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptySubscription.error(th, subscriber);
                return;
            }
        } else {
            i = publisherArr.length;
        }
        if (i == 0) {
            EmptySubscription.complete(subscriber);
        } else if (i == 1) {
            publisherArr[0].subscribe(new FlowableMap.b(subscriber, new c()));
        } else {
            a aVar = new a(subscriber, this.d, i, this.e, this.f);
            subscriber.onSubscribe(aVar);
            aVar.a(publisherArr, i);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends BasicIntQueueSubscription<R> {
        private static final long serialVersionUID = -5082275438355852221L;
        volatile boolean cancelled;
        final Function<? super Object[], ? extends R> combiner;
        int completedSources;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final AtomicThrowable error;
        final Object[] latest;
        int nonEmptySources;
        boolean outputFused;
        final SpscLinkedArrayQueue<Object> queue;
        final AtomicLong requested;
        final b<T>[] subscribers;

        a(Subscriber<? super R> subscriber, Function<? super Object[], ? extends R> function, int i, int i2, boolean z) {
            this.downstream = subscriber;
            this.combiner = function;
            b<T>[] bVarArr = new b[i];
            for (int i3 = 0; i3 < i; i3++) {
                bVarArr[i3] = new b<>(this, i3, i2);
            }
            this.subscribers = bVarArr;
            this.latest = new Object[i];
            this.queue = new SpscLinkedArrayQueue<>(i2);
            this.requested = new AtomicLong();
            this.error = new AtomicThrowable();
            this.delayErrors = z;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                c();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            d();
            c();
        }

        void a(Publisher<? extends T>[] publisherArr, int i) {
            b<T>[] bVarArr = this.subscribers;
            for (int i2 = 0; i2 < i && !this.done && !this.cancelled; i2++) {
                publisherArr[i2].subscribe(bVarArr[i2]);
            }
        }

        void a(int i, T t) {
            boolean z;
            synchronized (this) {
                Object[] objArr = this.latest;
                int i2 = this.nonEmptySources;
                if (objArr[i] == null) {
                    i2++;
                    this.nonEmptySources = i2;
                }
                objArr[i] = t;
                if (objArr.length == i2) {
                    this.queue.offer(this.subscribers[i], objArr.clone());
                    z = false;
                } else {
                    z = true;
                }
            }
            if (z) {
                this.subscribers[i].b();
            } else {
                c();
            }
        }

        void a(int i) {
            synchronized (this) {
                Object[] objArr = this.latest;
                if (objArr[i] != null) {
                    int i2 = this.completedSources + 1;
                    if (i2 == objArr.length) {
                        this.done = true;
                    } else {
                        this.completedSources = i2;
                        return;
                    }
                } else {
                    this.done = true;
                }
                c();
            }
        }

        void a(int i, Throwable th) {
            if (!ExceptionHelper.addThrowable(this.error, th)) {
                RxJavaPlugins.onError(th);
            } else if (!this.delayErrors) {
                d();
                this.done = true;
                c();
            } else {
                a(i);
            }
        }

        void a() {
            Subscriber<? super R> subscriber = this.downstream;
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
            int i = 1;
            while (!this.cancelled) {
                Throwable th = this.error.get();
                if (th != null) {
                    spscLinkedArrayQueue.clear();
                    subscriber.onError(th);
                    return;
                }
                boolean z = this.done;
                boolean isEmpty = spscLinkedArrayQueue.isEmpty();
                if (!isEmpty) {
                    subscriber.onNext(null);
                }
                if (!z || !isEmpty) {
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                } else {
                    subscriber.onComplete();
                    return;
                }
            }
            spscLinkedArrayQueue.clear();
        }

        void b() {
            int i;
            Subscriber<? super R> subscriber = this.downstream;
            SpscLinkedArrayQueue<?> spscLinkedArrayQueue = this.queue;
            int i2 = 1;
            do {
                long j = this.requested.get();
                long j2 = 0;
                while (true) {
                    i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
                    if (i == 0) {
                        break;
                    }
                    boolean z = this.done;
                    Object poll = spscLinkedArrayQueue.poll();
                    boolean z2 = poll == null;
                    if (!a(z, z2, subscriber, spscLinkedArrayQueue)) {
                        if (z2) {
                            break;
                        }
                        try {
                            subscriber.onNext((Object) Objects.requireNonNull(this.combiner.apply((Object[]) spscLinkedArrayQueue.poll()), "The combiner returned a null value"));
                            ((b) poll).b();
                            j2++;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            d();
                            ExceptionHelper.addThrowable(this.error, th);
                            subscriber.onError(ExceptionHelper.terminate(this.error));
                            return;
                        }
                    } else {
                        return;
                    }
                }
                if (i != 0 || !a(this.done, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                    if (!(j2 == 0 || j == Long.MAX_VALUE)) {
                        this.requested.addAndGet(-j2);
                    }
                    i2 = addAndGet(-i2);
                } else {
                    return;
                }
            } while (i2 != 0);
        }

        void c() {
            if (getAndIncrement() == 0) {
                if (this.outputFused) {
                    a();
                } else {
                    b();
                }
            }
        }

        boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.cancelled) {
                d();
                spscLinkedArrayQueue.clear();
                this.error.tryTerminateAndReport();
                return true;
            } else if (!z) {
                return false;
            } else {
                if (!this.delayErrors) {
                    Throwable terminate = ExceptionHelper.terminate(this.error);
                    if (terminate != null && terminate != ExceptionHelper.TERMINATED) {
                        d();
                        spscLinkedArrayQueue.clear();
                        subscriber.onError(terminate);
                        return true;
                    } else if (!z2) {
                        return false;
                    } else {
                        d();
                        subscriber.onComplete();
                        return true;
                    }
                } else if (!z2) {
                    return false;
                } else {
                    d();
                    this.error.tryTerminateConsumer(subscriber);
                    return true;
                }
            }
        }

        void d() {
            for (b<T> bVar : this.subscribers) {
                bVar.a();
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            boolean z = false;
            if ((i & 4) != 0) {
                return 0;
            }
            int i2 = i & 2;
            if (i2 != 0) {
                z = true;
            }
            this.outputFused = z;
            return i2;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public R poll() throws Throwable {
            Object poll = this.queue.poll();
            if (poll == null) {
                return null;
            }
            R r = (R) Objects.requireNonNull(this.combiner.apply((Object[]) this.queue.poll()), "The combiner returned a null value");
            ((b) poll).b();
            return r;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.queue.clear();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -8730235182291002949L;
        final int index;
        final int limit;
        final a<T, ?> parent;
        final int prefetch;
        int produced;

        b(a<T, ?> aVar, int i, int i2) {
            this.parent = aVar;
            this.index = i;
            this.prefetch = i2;
            this.limit = i2 - (i2 >> 2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, this.prefetch);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.parent.a(this.index, (int) t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.a(this.index, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.a(this.index);
        }

        public void a() {
            SubscriptionHelper.cancel(this);
        }

        public void b() {
            int i = this.produced + 1;
            if (i == this.limit) {
                this.produced = 0;
                get().request(i);
                return;
            }
            this.produced = i;
        }
    }

    /* loaded from: classes5.dex */
    final class c implements Function<T, R> {
        c() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object[], java.lang.Object] */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t) throws Throwable {
            return (R) FlowableCombineLatest.this.d.apply(new Object[]{t});
        }
    }
}
