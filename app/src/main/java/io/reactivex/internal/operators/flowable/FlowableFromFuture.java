package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableFromFuture<T> extends Flowable<T> {
    final Future<? extends T> b;
    final long c;
    final TimeUnit d;

    public FlowableFromFuture(Future<? extends T> future, long j, TimeUnit timeUnit) {
        this.b = future;
        this.c = j;
        this.d = timeUnit;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        DeferredScalarSubscription deferredScalarSubscription = new DeferredScalarSubscription(subscriber);
        subscriber.onSubscribe(deferredScalarSubscription);
        try {
            Object obj = this.d != null ? this.b.get(this.c, this.d) : this.b.get();
            if (obj == null) {
                subscriber.onError(new NullPointerException("The future returned null"));
            } else {
                deferredScalarSubscription.complete(obj);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (!deferredScalarSubscription.isCancelled()) {
                subscriber.onError(th);
            }
        }
    }
}
