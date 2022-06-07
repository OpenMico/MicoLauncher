package io.reactivex.internal.subscribers;

import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class BlockingFirstSubscriber<T> extends BlockingBaseSubscriber<T> {
    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.a == null) {
            this.a = t;
            this.c.cancel();
            countDown();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.a == null) {
            this.b = th;
        } else {
            RxJavaPlugins.onError(th);
        }
        countDown();
    }
}
