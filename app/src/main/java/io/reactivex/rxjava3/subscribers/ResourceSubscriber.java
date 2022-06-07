package io.reactivex.rxjava3.subscribers;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.EndConsumerHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public abstract class ResourceSubscriber<T> implements FlowableSubscriber<T>, Disposable {
    private final AtomicReference<Subscription> a = new AtomicReference<>();
    private final ListCompositeDisposable b = new ListCompositeDisposable();
    private final AtomicLong c = new AtomicLong();

    public final void add(Disposable disposable) {
        Objects.requireNonNull(disposable, "resource is null");
        this.b.add(disposable);
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription subscription) {
        if (EndConsumerHelper.setOnce(this.a, subscription, getClass())) {
            long andSet = this.c.getAndSet(0L);
            if (andSet != 0) {
                subscription.request(andSet);
            }
            onStart();
        }
    }

    protected void onStart() {
        request(Long.MAX_VALUE);
    }

    protected final void request(long j) {
        SubscriptionHelper.deferredRequest(this.a, this.c, j);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        if (SubscriptionHelper.cancel(this.a)) {
            this.b.dispose();
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return this.a.get() == SubscriptionHelper.CANCELLED;
    }
}
