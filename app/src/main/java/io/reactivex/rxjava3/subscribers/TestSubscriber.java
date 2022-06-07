package io.reactivex.rxjava3.subscribers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.observers.BaseTestConsumer;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public class TestSubscriber<T> extends BaseTestConsumer<T, TestSubscriber<T>> implements FlowableSubscriber<T>, Subscription {
    private final Subscriber<? super T> a;
    private volatile boolean b;
    private final AtomicReference<Subscription> c;
    private final AtomicLong d;

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

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
        }
    }

    protected void onStart() {
    }

    @NonNull
    public static <T> TestSubscriber<T> create() {
        return new TestSubscriber<>();
    }

    @NonNull
    public static <T> TestSubscriber<T> create(long j) {
        return new TestSubscriber<>(j);
    }

    public static <T> TestSubscriber<T> create(@NonNull Subscriber<? super T> subscriber) {
        return new TestSubscriber<>(subscriber);
    }

    public TestSubscriber() {
        this(a.INSTANCE, Long.MAX_VALUE);
    }

    public TestSubscriber(long j) {
        this(a.INSTANCE, j);
    }

    public TestSubscriber(@NonNull Subscriber<? super T> subscriber) {
        this(subscriber, Long.MAX_VALUE);
    }

    public TestSubscriber(@NonNull Subscriber<? super T> subscriber, long j) {
        if (j >= 0) {
            this.a = subscriber;
            this.c = new AtomicReference<>();
            this.d = new AtomicLong(j);
            return;
        }
        throw new IllegalArgumentException("Negative initial request not allowed");
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(@NonNull Subscription subscription) {
        this.lastThread = Thread.currentThread();
        if (subscription == null) {
            this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
        } else if (!this.c.compareAndSet(null, subscription)) {
            subscription.cancel();
            if (this.c.get() != SubscriptionHelper.CANCELLED) {
                List list = this.errors;
                list.add(new IllegalStateException("onSubscribe received multiple subscriptions: " + subscription));
            }
        } else {
            this.a.onSubscribe(subscription);
            long andSet = this.d.getAndSet(0L);
            if (andSet != 0) {
                subscription.request(andSet);
            }
            onStart();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NonNull T t) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.c.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        this.lastThread = Thread.currentThread();
        this.values.add(t);
        if (t == null) {
            this.errors.add(new NullPointerException("onNext received a null value"));
        }
        this.a.onNext(t);
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(@NonNull Throwable th) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.c.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            if (th == null) {
                this.errors.add(new NullPointerException("onError received a null Throwable"));
            } else {
                this.errors.add(th);
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

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer, io.reactivex.rxjava3.disposables.Disposable
    protected final void dispose() {
        cancel();
    }

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer, io.reactivex.rxjava3.disposables.Disposable
    protected final boolean isDisposed() {
        return this.b;
    }

    public final boolean hasSubscription() {
        return this.c.get() != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer
    public final TestSubscriber<T> assertSubscribed() {
        if (this.c.get() != null) {
            return this;
        }
        throw fail("Not subscribed!");
    }

    public final TestSubscriber<T> requestMore(long j) {
        request(j);
        return this;
    }
}
