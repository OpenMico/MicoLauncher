package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableBuffer<T, C extends Collection<? super T>> extends a<T, C> {
    final int b;
    final int c;
    final Supplier<C> d;

    public FlowableBuffer(Flowable<T> flowable, int i, int i2, Supplier<C> supplier) {
        super(flowable);
        this.b = i;
        this.c = i2;
        this.d = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super C> subscriber) {
        int i = this.b;
        int i2 = this.c;
        if (i == i2) {
            this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.d));
        } else if (i2 > i) {
            this.source.subscribe((FlowableSubscriber) new c(subscriber, this.b, this.c, this.d));
        } else {
            this.source.subscribe((FlowableSubscriber) new b(subscriber, this.b, this.c, this.d));
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, C extends Collection<? super T>> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super C> a;
        final Supplier<C> b;
        final int c;
        C d;
        Subscription e;
        boolean f;
        int g;

        a(Subscriber<? super C> subscriber, int i, Supplier<C> supplier) {
            this.a = subscriber;
            this.c = i;
            this.b = supplier;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                this.e.request(BackpressureHelper.multiplyCap(j, this.c));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.e.cancel();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.f) {
                C c = this.d;
                if (c == null) {
                    try {
                        c = (C) ((Collection) Objects.requireNonNull(this.b.get(), "The bufferSupplier returned a null buffer"));
                        this.d = c;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        cancel();
                        onError(th);
                        return;
                    }
                }
                c.add(t);
                int i = this.g + 1;
                if (i == this.c) {
                    this.g = 0;
                    this.d = null;
                    this.a.onNext(c);
                    return;
                }
                this.g = i;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.f) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = null;
            this.f = true;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.f) {
                this.f = true;
                C c = this.d;
                this.d = null;
                if (c != null) {
                    this.a.onNext(c);
                }
                this.a.onComplete();
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class c<T, C extends Collection<? super T>> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5616169793639412593L;
        C buffer;
        final Supplier<C> bufferSupplier;
        boolean done;
        final Subscriber<? super C> downstream;
        int index;
        final int size;
        final int skip;
        Subscription upstream;

        c(Subscriber<? super C> subscriber, int i, int i2, Supplier<C> supplier) {
            this.downstream = subscriber;
            this.size = i;
            this.skip = i2;
            this.bufferSupplier = supplier;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (!SubscriptionHelper.validate(j)) {
                return;
            }
            if (get() != 0 || !compareAndSet(0, 1)) {
                this.upstream.request(BackpressureHelper.multiplyCap(this.skip, j));
                return;
            }
            this.upstream.request(BackpressureHelper.addCap(BackpressureHelper.multiplyCap(j, this.size), BackpressureHelper.multiplyCap(this.skip - this.size, j - 1)));
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                C c = this.buffer;
                int i = this.index;
                int i2 = i + 1;
                if (i == 0) {
                    try {
                        c = (C) ((Collection) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer"));
                        this.buffer = c;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        cancel();
                        onError(th);
                        return;
                    }
                }
                if (c != null) {
                    c.add(t);
                    if (c.size() == this.size) {
                        this.buffer = null;
                        this.downstream.onNext(c);
                    }
                }
                if (i2 == this.skip) {
                    i2 = 0;
                }
                this.index = i2;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.buffer = null;
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                C c = this.buffer;
                this.buffer = null;
                if (c != null) {
                    this.downstream.onNext(c);
                }
                this.downstream.onComplete();
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T, C extends Collection<? super T>> extends AtomicLong implements FlowableSubscriber<T>, BooleanSupplier, Subscription {
        private static final long serialVersionUID = -7370244972039324525L;
        final Supplier<C> bufferSupplier;
        volatile boolean cancelled;
        boolean done;
        final Subscriber<? super C> downstream;
        int index;
        long produced;
        final int size;
        final int skip;
        Subscription upstream;
        final AtomicBoolean once = new AtomicBoolean();
        final ArrayDeque<C> buffers = new ArrayDeque<>();

        b(Subscriber<? super C> subscriber, int i, int i2, Supplier<C> supplier) {
            this.downstream = subscriber;
            this.size = i;
            this.skip = i2;
            this.bufferSupplier = supplier;
        }

        @Override // io.reactivex.rxjava3.functions.BooleanSupplier
        public boolean getAsBoolean() {
            return this.cancelled;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j) && !QueueDrainHelper.postCompleteRequest(j, this.downstream, this.buffers, this, this)) {
                if (this.once.get() || !this.once.compareAndSet(false, true)) {
                    this.upstream.request(BackpressureHelper.multiplyCap(this.skip, j));
                    return;
                }
                this.upstream.request(BackpressureHelper.addCap(this.size, BackpressureHelper.multiplyCap(this.skip, j - 1)));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                ArrayDeque<C> arrayDeque = this.buffers;
                int i = this.index;
                int i2 = i + 1;
                if (i == 0) {
                    try {
                        arrayDeque.offer((Collection) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer"));
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        cancel();
                        onError(th);
                        return;
                    }
                }
                Collection collection = (Collection) arrayDeque.peek();
                if (collection.size() + 1 == this.size) {
                    arrayDeque.poll();
                    collection.add(t);
                    this.produced++;
                    this.downstream.onNext(collection);
                }
                Iterator it = arrayDeque.iterator();
                while (it.hasNext()) {
                    ((Collection) it.next()).add(t);
                }
                if (i2 == this.skip) {
                    i2 = 0;
                }
                this.index = i2;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.buffers.clear();
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                long j = this.produced;
                if (j != 0) {
                    BackpressureHelper.produced(this, j);
                }
                QueueDrainHelper.postComplete(this.downstream, this.buffers, this, this);
            }
        }
    }
}
