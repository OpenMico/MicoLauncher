package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
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
    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        DeferredScalarSubscription deferredScalarSubscription = new DeferredScalarSubscription(subscriber);
        subscriber.onSubscribe(deferredScalarSubscription);
        try {
            Object obj = this.d != null ? this.b.get(this.c, this.d) : this.b.get();
            if (obj == null) {
                subscriber.onError(ExceptionHelper.createNullPointerException("The future returned a null value."));
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
