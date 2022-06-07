package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriber;
import io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableConcatMapEager<T, R> extends a<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> b;
    final int c;
    final int d;
    final ErrorMode e;

    public FlowableConcatMapEager(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, ErrorMode errorMode) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = i2;
        this.e = errorMode;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d, this.e));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, InnerQueuedSubscriberSupport<R>, Subscription {
        private static final long serialVersionUID = -4255299542215038287L;
        volatile boolean cancelled;
        volatile InnerQueuedSubscriber<R> current;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final ErrorMode errorMode;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int maxConcurrency;
        final int prefetch;
        final SpscLinkedArrayQueue<InnerQueuedSubscriber<R>> subscribers;
        Subscription upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();

        public a(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, ErrorMode errorMode) {
            this.downstream = subscriber;
            this.mapper = function;
            this.maxConcurrency = i;
            this.prefetch = i2;
            this.errorMode = errorMode;
            this.subscribers = new SpscLinkedArrayQueue<>(Math.min(i2, i));
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                int i = this.maxConcurrency;
                subscription.request(i == Integer.MAX_VALUE ? Long.MAX_VALUE : i);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            try {
                Publisher publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher");
                InnerQueuedSubscriber<R> innerQueuedSubscriber = new InnerQueuedSubscriber<>(this, this.prefetch);
                if (!this.cancelled) {
                    this.subscribers.offer(innerQueuedSubscriber);
                    publisher.subscribe(innerQueuedSubscriber);
                    if (this.cancelled) {
                        innerQueuedSubscriber.cancel();
                        a();
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                this.errors.tryTerminateAndReport();
                a();
            }
        }

        void a() {
            if (getAndIncrement() == 0) {
                do {
                    b();
                } while (decrementAndGet() != 0);
            }
        }

        void b() {
            InnerQueuedSubscriber<R> innerQueuedSubscriber = this.current;
            this.current = null;
            if (innerQueuedSubscriber != null) {
                innerQueuedSubscriber.cancel();
            }
            while (true) {
                InnerQueuedSubscriber<R> poll = this.subscribers.poll();
                if (poll != null) {
                    poll.cancel();
                } else {
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerNext(InnerQueuedSubscriber<R> innerQueuedSubscriber, R r) {
            if (innerQueuedSubscriber.queue().offer(r)) {
                drain();
                return;
            }
            innerQueuedSubscriber.cancel();
            innerError(innerQueuedSubscriber, new MissingBackpressureException());
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerError(InnerQueuedSubscriber<R> innerQueuedSubscriber, Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                innerQueuedSubscriber.setDone();
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.cancel();
                }
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerComplete(InnerQueuedSubscriber<R> innerQueuedSubscriber) {
            innerQueuedSubscriber.setDone();
            drain();
        }

        /* JADX WARN: Removed duplicated region for block: B:74:0x0117  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x012c A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:91:0x0128 A[SYNTHETIC] */
        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drain() {
            /*
                Method dump skipped, instructions count: 310
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapEager.a.drain():void");
        }
    }
}
