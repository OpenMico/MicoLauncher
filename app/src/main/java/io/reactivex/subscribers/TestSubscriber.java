package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.BaseTestConsumer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public class TestSubscriber<T> extends BaseTestConsumer<T, TestSubscriber<T>> implements FlowableSubscriber<T>, Disposable, Subscription {
    private final Subscriber<? super T> a;
    private volatile boolean b;
    private final AtomicReference<Subscription> c;
    private final AtomicLong d;
    private QueueSubscription<T> e;

    /* loaded from: classes5.dex */
    enum a implements FlowableSubscriber<Object> {
        INSTANCE;

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
        }
    }

    protected void onStart() {
    }

    public static <T> TestSubscriber<T> create() {
        return new TestSubscriber<>();
    }

    public static <T> TestSubscriber<T> create(long j) {
        return new TestSubscriber<>(j);
    }

    public static <T> TestSubscriber<T> create(Subscriber<? super T> subscriber) {
        return new TestSubscriber<>(subscriber);
    }

    public TestSubscriber() {
        this(a.INSTANCE, Long.MAX_VALUE);
    }

    public TestSubscriber(long j) {
        this(a.INSTANCE, j);
    }

    public TestSubscriber(Subscriber<? super T> subscriber) {
        this(subscriber, Long.MAX_VALUE);
    }

    public TestSubscriber(Subscriber<? super T> subscriber, long j) {
        if (j >= 0) {
            this.a = subscriber;
            this.c = new AtomicReference<>();
            this.d = new AtomicLong(j);
            return;
        }
        throw new IllegalArgumentException("Negative initial request not allowed");
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        this.lastThread = Thread.currentThread();
        if (subscription == null) {
            this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
        } else if (!this.c.compareAndSet(null, subscription)) {
            subscription.cancel();
            if (this.c.get() != SubscriptionHelper.CANCELLED) {
                this.errors.add(new IllegalStateException("onSubscribe received multiple subscriptions: " + subscription));
            }
        } else {
            if (this.initialFusionMode != 0 && (subscription instanceof QueueSubscription)) {
                this.e = (QueueSubscription) subscription;
                int requestFusion = this.e.requestFusion(this.initialFusionMode);
                this.establishedFusionMode = requestFusion;
                if (requestFusion == 1) {
                    this.checkSubscriptionOnce = true;
                    this.lastThread = Thread.currentThread();
                    while (true) {
                        try {
                            T poll = this.e.poll();
                            if (poll != null) {
                                this.values.add(poll);
                            } else {
                                this.completions++;
                                return;
                            }
                        } catch (Throwable th) {
                            this.errors.add(th);
                            return;
                        }
                    }
                }
            }
            this.a.onSubscribe(subscription);
            long andSet = this.d.getAndSet(0L);
            if (andSet != 0) {
                subscription.request(andSet);
            }
            onStart();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.c.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        this.lastThread = Thread.currentThread();
        if (this.establishedFusionMode == 2) {
            while (true) {
                try {
                    T poll = this.e.poll();
                    if (poll != null) {
                        this.values.add(poll);
                    } else {
                        return;
                    }
                } catch (Throwable th) {
                    this.errors.add(th);
                    this.e.cancel();
                    return;
                }
            }
        } else {
            this.values.add(t);
            if (t == null) {
                this.errors.add(new NullPointerException("onNext received a null value"));
            }
            this.a.onNext(t);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.c.get() == null) {
                this.errors.add(new NullPointerException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            this.errors.add(th);
            if (th == null) {
                this.errors.add(new IllegalStateException("onError received a null Throwable"));
            }
            this.a.onError(th);
        } finally {
            this.done.countDown();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.c.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            this.completions++;
            this.a.onComplete();
        } finally {
            this.done.countDown();
        }
    }

    @Override // org.reactivestreams.Subscription
    public final void request(long j) {
        SubscriptionHelper.deferredRequest(this.c, this.d, j);
    }

    @Override // org.reactivestreams.Subscription
    public final void cancel() {
        if (!this.b) {
            this.b = true;
            SubscriptionHelper.cancel(this.c);
        }
    }

    public final boolean isCancelled() {
        return this.b;
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        cancel();
    }

    @Override // io.reactivex.disposables.Disposable
    public final boolean isDisposed() {
        return this.b;
    }

    public final boolean hasSubscription() {
        return this.c.get() != null;
    }

    @Override // io.reactivex.observers.BaseTestConsumer
    public final TestSubscriber<T> assertSubscribed() {
        if (this.c.get() != null) {
            return this;
        }
        throw fail("Not subscribed!");
    }

    @Override // io.reactivex.observers.BaseTestConsumer
    public final TestSubscriber<T> assertNotSubscribed() {
        if (this.c.get() != null) {
            throw fail("Subscribed!");
        } else if (this.errors.isEmpty()) {
            return this;
        } else {
            throw fail("Not subscribed but errors found");
        }
    }

    public final TestSubscriber<T> assertOf(Consumer<? super TestSubscriber<T>> consumer) {
        try {
            consumer.accept(this);
            return this;
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public final TestSubscriber<T> requestMore(long j) {
        request(j);
        return this;
    }
}
