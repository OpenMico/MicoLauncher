package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FutureSubscriber<T> extends CountDownLatch implements FlowableSubscriber<T>, Future<T>, Subscription {
    T a;
    Throwable b;
    final AtomicReference<Subscription> c = new AtomicReference<>();

    @Override // org.reactivestreams.Subscription
    public void cancel() {
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
    }

    public FutureSubscriber() {
        super(1);
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        Subscription subscription;
        do {
            subscription = this.c.get();
            if (subscription == this || subscription == SubscriptionHelper.CANCELLED) {
                return false;
            }
        } while (!this.c.compareAndSet(subscription, SubscriptionHelper.CANCELLED));
        if (subscription != null) {
            subscription.cancel();
        }
        countDown();
        return true;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.c.get() == SubscriptionHelper.CANCELLED;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return getCount() == 0;
    }

    @Override // java.util.concurrent.Future
    public T get() throws InterruptedException, ExecutionException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            await();
        }
        if (!isCancelled()) {
            Throwable th = this.b;
            if (th == null) {
                return this.a;
            }
            throw new ExecutionException(th);
        }
        throw new CancellationException();
    }

    @Override // java.util.concurrent.Future
    public T get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            if (!await(j, timeUnit)) {
                throw new TimeoutException(ExceptionHelper.timeoutMessage(j, timeUnit));
            }
        }
        if (!isCancelled()) {
            Throwable th = this.b;
            if (th == null) {
                return this.a;
            }
            throw new ExecutionException(th);
        }
        throw new CancellationException();
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        SubscriptionHelper.setOnce(this.c, subscription, Long.MAX_VALUE);
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.a != null) {
            this.c.get().cancel();
            onError(new IndexOutOfBoundsException("More than one element received"));
            return;
        }
        this.a = t;
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        Subscription subscription;
        do {
            subscription = this.c.get();
            if (subscription == this || subscription == SubscriptionHelper.CANCELLED) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = th;
        } while (!this.c.compareAndSet(subscription, this));
        countDown();
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        Subscription subscription;
        if (this.a == null) {
            onError(new NoSuchElementException("The source is empty"));
            return;
        }
        do {
            subscription = this.c.get();
            if (subscription == this || subscription == SubscriptionHelper.CANCELLED) {
                return;
            }
        } while (!this.c.compareAndSet(subscription, this));
        countDown();
    }
}
