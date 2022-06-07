package io.reactivex.rxjava3.internal.jdk8;

import java.util.NoSuchElementException;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableFirstStageSubscriber<T> extends a<T> {
    final boolean a;
    final T b;

    public FlowableFirstStageSubscriber(boolean z, T t) {
        this.a = z;
        this.b = t;
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        complete(t);
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!isDone()) {
            clear();
            if (this.a) {
                complete(this.b);
            } else {
                completeExceptionally(new NoSuchElementException());
            }
        }
    }

    @Override // io.reactivex.rxjava3.internal.jdk8.a
    protected void afterSubscribe(Subscription subscription) {
        subscription.request(1L);
    }
}
