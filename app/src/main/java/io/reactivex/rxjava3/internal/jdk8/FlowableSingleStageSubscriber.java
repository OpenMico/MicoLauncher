package io.reactivex.rxjava3.internal.jdk8;

import java.util.NoSuchElementException;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableSingleStageSubscriber<T> extends a<T> {
    final boolean a;
    final T b;

    public FlowableSingleStageSubscriber(boolean z, T t) {
        this.a = z;
        this.b = t;
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.d != null) {
            this.d = null;
            completeExceptionally(new IllegalArgumentException("Sequence contains more than one element!"));
            return;
        }
        this.d = t;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!isDone()) {
            Object obj = this.d;
            clear();
            if (obj != null) {
                complete(obj);
            } else if (this.a) {
                complete(this.b);
            } else {
                completeExceptionally(new NoSuchElementException());
            }
        }
    }

    @Override // io.reactivex.rxjava3.internal.jdk8.a
    protected void afterSubscribe(Subscription subscription) {
        subscription.request(2L);
    }
}
