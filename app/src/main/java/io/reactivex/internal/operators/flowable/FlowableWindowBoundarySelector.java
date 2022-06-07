package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
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

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        this.source.subscribe((FlowableSubscriber) new c(new SerializedSubscriber(subscriber), this.b, this.c, this.d));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c<T, B, V> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
        final Publisher<B> a;
        final Function<? super B, ? extends Publisher<V>> b;
        final int c;
        Subscription e;
        final AtomicReference<Disposable> f = new AtomicReference<>();
        final AtomicLong h = new AtomicLong();
        final AtomicBoolean i = new AtomicBoolean();
        final CompositeDisposable d = new CompositeDisposable();
        final List<UnicastProcessor<T>> g = new ArrayList();

        @Override // io.reactivex.internal.subscribers.QueueDrainSubscriber, io.reactivex.internal.util.QueueDrain
        public boolean accept(Subscriber<? super Flowable<T>> subscriber, Object obj) {
            return false;
        }

        c(Subscriber<? super Flowable<T>> subscriber, Publisher<B> publisher, Function<? super B, ? extends Publisher<V>> function, int i) {
            super(subscriber, new MpscLinkedQueue());
            this.a = publisher;
            this.b = function;
            this.c = i;
            this.h.lazySet(1L);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.downstream.onSubscribe(this);
                if (!this.i.get()) {
                    b bVar = new b(this);
                    if (this.f.compareAndSet(null, bVar)) {
                        subscription.request(Long.MAX_VALUE);
                        this.a.subscribe(bVar);
                    }
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                if (fastEnter()) {
                    for (UnicastProcessor<T> unicastProcessor : this.g) {
                        unicastProcessor.onNext(t);
                    }
                    if (leave(-1) == 0) {
                        return;
                    }
                } else {
                    this.queue.offer(NotificationLite.next(t));
                    if (!enter()) {
                        return;
                    }
                }
                b();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            if (enter()) {
                b();
            }
            if (this.h.decrementAndGet() == 0) {
                this.d.dispose();
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                if (enter()) {
                    b();
                }
                if (this.h.decrementAndGet() == 0) {
                    this.d.dispose();
                }
                this.downstream.onComplete();
            }
        }

        void a(Throwable th) {
            this.e.cancel();
            this.d.dispose();
            DisposableHelper.dispose(this.f);
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.i.compareAndSet(false, true)) {
                DisposableHelper.dispose(this.f);
                if (this.h.decrementAndGet() == 0) {
                    this.e.cancel();
                }
            }
        }

        void a() {
            this.d.dispose();
            DisposableHelper.dispose(this.f);
        }

        void b() {
            SimplePlainQueue simplePlainQueue = this.queue;
            Subscriber subscriber = this.downstream;
            List<UnicastProcessor<T>> list = this.g;
            int i = 1;
            while (true) {
                boolean z = this.done;
                Object poll = simplePlainQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    a();
                    Throwable th = this.error;
                    if (th != null) {
                        for (UnicastProcessor<T> unicastProcessor : list) {
                            unicastProcessor.onError(th);
                        }
                    } else {
                        for (UnicastProcessor<T> unicastProcessor2 : list) {
                            unicastProcessor2.onComplete();
                        }
                    }
                    list.clear();
                    return;
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (poll instanceof d) {
                    d dVar = (d) poll;
                    if (dVar.a != null) {
                        if (list.remove(dVar.a)) {
                            dVar.a.onComplete();
                            if (this.h.decrementAndGet() == 0) {
                                a();
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!this.i.get()) {
                        UnicastProcessor<T> create = UnicastProcessor.create(this.c);
                        long requested = requested();
                        if (requested != 0) {
                            list.add(create);
                            subscriber.onNext(create);
                            if (requested != Long.MAX_VALUE) {
                                produced(1L);
                            }
                            try {
                                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply((B) dVar.b), "The publisher supplied is null");
                                a aVar = new a(this, create);
                                if (this.d.add(aVar)) {
                                    this.h.getAndIncrement();
                                    publisher.subscribe(aVar);
                                }
                            } catch (Throwable th2) {
                                cancel();
                                subscriber.onError(th2);
                            }
                        } else {
                            cancel();
                            subscriber.onError(new MissingBackpressureException("Could not deliver new window due to lack of requests"));
                        }
                    }
                } else {
                    for (UnicastProcessor<T> unicastProcessor3 : list) {
                        unicastProcessor3.onNext((T) NotificationLite.getValue(poll));
                    }
                }
            }
        }

        void a(B b) {
            this.queue.offer(new d(null, b));
            if (enter()) {
                b();
            }
        }

        void a(a<T, V> aVar) {
            this.d.delete(aVar);
            this.queue.offer(new d(aVar.b, null));
            if (enter()) {
                b();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class d<T, B> {
        final UnicastProcessor<T> a;
        final B b;

        d(UnicastProcessor<T> unicastProcessor, B b) {
            this.a = unicastProcessor;
            this.b = b;
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, B> extends DisposableSubscriber<B> {
        final c<T, B, ?> a;

        b(c<T, B, ?> cVar) {
            this.a = cVar;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b) {
            this.a.a((c<T, B, ?>) b);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.onComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, V> extends DisposableSubscriber<V> {
        final c<T, ?, V> a;
        final UnicastProcessor<T> b;
        boolean c;

        a(c<T, ?, V> cVar, UnicastProcessor<T> unicastProcessor) {
            this.a = cVar;
            this.b = unicastProcessor;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(V v) {
            cancel();
            onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.a.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.a.a((a) this);
            }
        }
    }
}
