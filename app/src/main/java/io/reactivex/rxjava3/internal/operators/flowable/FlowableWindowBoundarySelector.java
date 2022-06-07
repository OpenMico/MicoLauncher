package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableWindowBoundarySelector<T, B, V> extends a<T, Flowable<T>> {
    final Publisher<B> b;
    final Function<? super B, ? extends Publisher<V>> c;
    final int d;

    public FlowableWindowBoundarySelector(Flowable<T> flowable, Publisher<B> publisher, Function<? super B, ? extends Publisher<V>> function, int i) {
        super(flowable);
        this.b = publisher;
        this.c = function;
        this.d = i;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d));
    }

    /* loaded from: classes5.dex */
    static final class a<T, B, V> extends AtomicInteger implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = 8646217640096099753L;
        final int bufferSize;
        final Function<? super B, ? extends Publisher<V>> closingIndicator;
        final Subscriber<? super Flowable<T>> downstream;
        long emitted;
        final Publisher<B> open;
        volatile boolean openDone;
        Subscription upstream;
        volatile boolean upstreamCanceled;
        volatile boolean upstreamDone;
        final SimplePlainQueue<Object> queue = new MpscLinkedQueue();
        final CompositeDisposable resources = new CompositeDisposable();
        final List<UnicastProcessor<T>> windows = new ArrayList();
        final AtomicLong windowCount = new AtomicLong(1);
        final AtomicBoolean downstreamCancelled = new AtomicBoolean();
        final AtomicThrowable error = new AtomicThrowable();
        final c<B> startSubscriber = new c<>(this);
        final AtomicLong requested = new AtomicLong();

        a(Subscriber<? super Flowable<T>> subscriber, Publisher<B> publisher, Function<? super B, ? extends Publisher<V>> function, int i) {
            this.downstream = subscriber;
            this.open = publisher;
            this.closingIndicator = function;
            this.bufferSize = i;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                this.open.subscribe(this.startSubscriber);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.queue.offer(t);
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.startSubscriber.a();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                b();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.startSubscriber.a();
            this.resources.dispose();
            this.upstreamDone = true;
            b();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.downstreamCancelled.compareAndSet(false, true)) {
                return;
            }
            if (this.windowCount.decrementAndGet() == 0) {
                this.upstream.cancel();
                this.startSubscriber.a();
                this.resources.dispose();
                this.error.tryTerminateAndReport();
                this.upstreamCanceled = true;
                b();
                return;
            }
            this.startSubscriber.a();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.windowCount.decrementAndGet() == 0) {
                this.upstream.cancel();
                this.startSubscriber.a();
                this.resources.dispose();
                this.error.tryTerminateAndReport();
                this.upstreamCanceled = true;
                b();
            }
        }

        void a(B b2) {
            this.queue.offer(new b(b2));
            b();
        }

        void a(Throwable th) {
            this.upstream.cancel();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                b();
            }
        }

        void a() {
            this.openDone = true;
            b();
        }

        void a(C0297a<T, V> aVar) {
            this.queue.offer(aVar);
            b();
        }

        void b(Throwable th) {
            this.upstream.cancel();
            this.startSubscriber.a();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                b();
            }
        }

        void b() {
            if (getAndIncrement() == 0) {
                Subscriber<? super Flowable<T>> subscriber = this.downstream;
                SimplePlainQueue<Object> simplePlainQueue = this.queue;
                List<UnicastProcessor<T>> list = this.windows;
                int i = 1;
                while (true) {
                    if (this.upstreamCanceled) {
                        simplePlainQueue.clear();
                        list.clear();
                    } else {
                        boolean z = this.upstreamDone;
                        Object poll = simplePlainQueue.poll();
                        boolean z2 = poll == null;
                        if (z && (z2 || this.error.get() != null)) {
                            a((Subscriber<?>) subscriber);
                            this.upstreamCanceled = true;
                        } else if (!z2) {
                            if (poll instanceof b) {
                                if (!this.downstreamCancelled.get()) {
                                    long j = this.emitted;
                                    if (this.requested.get() != j) {
                                        this.emitted = j + 1;
                                        try {
                                            Publisher publisher = (Publisher) Objects.requireNonNull(this.closingIndicator.apply(((b) poll).a), "The closingIndicator returned a null Publisher");
                                            this.windowCount.getAndIncrement();
                                            UnicastProcessor<T> create = UnicastProcessor.create(this.bufferSize, this);
                                            C0297a aVar = new C0297a(this, create);
                                            subscriber.onNext(aVar);
                                            if (aVar.a()) {
                                                create.onComplete();
                                            } else {
                                                list.add(create);
                                                this.resources.add(aVar);
                                                publisher.subscribe(aVar);
                                            }
                                        } catch (Throwable th) {
                                            Exceptions.throwIfFatal(th);
                                            this.upstream.cancel();
                                            this.startSubscriber.a();
                                            this.resources.dispose();
                                            Exceptions.throwIfFatal(th);
                                            this.error.tryAddThrowableOrReport(th);
                                            this.upstreamDone = true;
                                        }
                                    } else {
                                        this.upstream.cancel();
                                        this.startSubscriber.a();
                                        this.resources.dispose();
                                        this.error.tryAddThrowableOrReport(new MissingBackpressureException(FlowableWindowTimed.a(j)));
                                        this.upstreamDone = true;
                                    }
                                }
                            } else if (poll instanceof C0297a) {
                                UnicastProcessor<T> unicastProcessor = ((C0297a) poll).c;
                                list.remove(unicastProcessor);
                                this.resources.delete((Disposable) poll);
                                unicastProcessor.onComplete();
                            } else {
                                for (UnicastProcessor<T> unicastProcessor2 : list) {
                                    unicastProcessor2.onNext(poll);
                                }
                            }
                        } else if (this.openDone && list.size() == 0) {
                            this.upstream.cancel();
                            this.startSubscriber.a();
                            this.resources.dispose();
                            a((Subscriber<?>) subscriber);
                            this.upstreamCanceled = true;
                        }
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }

        void a(Subscriber<?> subscriber) {
            Throwable terminate = this.error.terminate();
            if (terminate == null) {
                for (UnicastProcessor<T> unicastProcessor : this.windows) {
                    unicastProcessor.onComplete();
                }
                subscriber.onComplete();
            } else if (terminate != ExceptionHelper.TERMINATED) {
                for (UnicastProcessor<T> unicastProcessor2 : this.windows) {
                    unicastProcessor2.onError(terminate);
                }
                subscriber.onError(terminate);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes5.dex */
        public static final class b<B> {
            final B a;

            b(B b) {
                this.a = b;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes5.dex */
        public static final class c<B> extends AtomicReference<Subscription> implements FlowableSubscriber<B> {
            private static final long serialVersionUID = -3326496781427702834L;
            final a<?, B, ?> parent;

            c(a<?, B, ?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription subscription) {
                if (SubscriptionHelper.setOnce(this, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(B b) {
                this.parent.a((a<?, B, ?>) b);
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable th) {
                this.parent.a(th);
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                this.parent.a();
            }

            void a() {
                SubscriptionHelper.cancel(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowBoundarySelector$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public static final class C0297a<T, V> extends Flowable<T> implements FlowableSubscriber<V>, Disposable {
            final a<T, ?, V> b;
            final UnicastProcessor<T> c;
            final AtomicReference<Subscription> d = new AtomicReference<>();
            final AtomicBoolean e = new AtomicBoolean();

            C0297a(a<T, ?, V> aVar, UnicastProcessor<T> unicastProcessor) {
                this.b = aVar;
                this.c = unicastProcessor;
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription subscription) {
                if (SubscriptionHelper.setOnce(this.d, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(V v) {
                if (SubscriptionHelper.cancel(this.d)) {
                    this.b.a((C0297a) this);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable th) {
                if (isDisposed()) {
                    RxJavaPlugins.onError(th);
                } else {
                    this.b.b(th);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                this.b.a((C0297a) this);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                SubscriptionHelper.cancel(this.d);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return this.d.get() == SubscriptionHelper.CANCELLED;
            }

            @Override // io.reactivex.rxjava3.core.Flowable
            protected void subscribeActual(Subscriber<? super T> subscriber) {
                this.c.subscribe(subscriber);
                this.e.set(true);
            }

            boolean a() {
                return !this.e.get() && this.e.compareAndSet(false, true);
            }
        }
    }
}
