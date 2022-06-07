package io.reactivex.rxjava3.internal.subscribers;

/* loaded from: classes5.dex */
public final class BlockingLastSubscriber<T> extends BlockingBaseSubscriber<T> {
    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        this.a = t;
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        this.a = null;
        this.b = th;
        countDown();
    }
}
