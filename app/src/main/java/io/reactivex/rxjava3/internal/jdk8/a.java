package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* compiled from: FlowableStageSubscriber.java */
/* loaded from: classes4.dex */
public abstract class a<T> extends CompletableFuture<T> implements FlowableSubscriber<T> {
    final AtomicReference<Subscription> c = new AtomicReference<>();
    T d;

    protected abstract void afterSubscribe(Subscription subscription);

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(@NonNull Subscription subscription) {
        if (SubscriptionHelper.setOnce(this.c, subscription)) {
            afterSubscribe(subscription);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public final void onError(Throwable th) {
        clear();
        if (!completeExceptionally(th)) {
            RxJavaPlugins.onError(th);
        }
    }

    protected final void cancelUpstream() {
        SubscriptionHelper.cancel(this.c);
    }

    protected final void clear() {
        this.d = null;
        this.c.lazySet(SubscriptionHelper.CANCELLED);
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        cancelUpstream();
        return super.cancel(z);
    }

    @Override // java.util.concurrent.CompletableFuture
    public final boolean complete(T t) {
        cancelUpstream();
        return super.complete(t);
    }

    @Override // java.util.concurrent.CompletableFuture
    public final boolean completeExceptionally(Throwable th) {
        cancelUpstream();
        return super.completeExceptionally(th);
    }
}
