package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableTimer extends Flowable<Long> {
    final Scheduler b;
    final long c;
    final TimeUnit d;

    public FlowableTimer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.c = j;
        this.d = timeUnit;
        this.b = scheduler;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Long> subscriber) {
        a aVar = new a(subscriber);
        subscriber.onSubscribe(aVar);
        aVar.a(this.b.scheduleDirect(aVar, this.c, this.d));
    }

    /* loaded from: classes4.dex */
    static final class a extends AtomicReference<Disposable> implements Runnable, Subscription {
        private static final long serialVersionUID = -2809475196591179431L;
        final Subscriber<? super Long> downstream;
        volatile boolean requested;

        a(Subscriber<? super Long> subscriber) {
            this.downstream = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                this.requested = true;
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            DisposableHelper.dispose(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (get() == DisposableHelper.DISPOSED) {
                return;
            }
            if (this.requested) {
                this.downstream.onNext(0L);
                lazySet(EmptyDisposable.INSTANCE);
                this.downstream.onComplete();
                return;
            }
            lazySet(EmptyDisposable.INSTANCE);
            this.downstream.onError(new MissingBackpressureException("Can't deliver value due to lack of requests"));
        }

        public void a(Disposable disposable) {
            DisposableHelper.trySet(this, disposable);
        }
    }
}
