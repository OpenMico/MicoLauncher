package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableIntervalRange extends Flowable<Long> {
    final Scheduler b;
    final long c;
    final long d;
    final long e;
    final long f;
    final TimeUnit g;

    public FlowableIntervalRange(long j, long j2, long j3, long j4, TimeUnit timeUnit, Scheduler scheduler) {
        this.e = j3;
        this.f = j4;
        this.g = timeUnit;
        this.b = scheduler;
        this.c = j;
        this.d = j2;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super Long> subscriber) {
        a aVar = new a(subscriber, this.c, this.d);
        subscriber.onSubscribe(aVar);
        Scheduler scheduler = this.b;
        if (scheduler instanceof TrampolineScheduler) {
            Scheduler.Worker createWorker = scheduler.createWorker();
            aVar.a(createWorker);
            createWorker.schedulePeriodically(aVar, this.e, this.f, this.g);
            return;
        }
        aVar.a(scheduler.schedulePeriodicallyDirect(aVar, this.e, this.f, this.g));
    }

    /* loaded from: classes5.dex */
    static final class a extends AtomicLong implements Runnable, Subscription {
        private static final long serialVersionUID = -2809475196591179431L;
        long count;
        final Subscriber<? super Long> downstream;
        final long end;
        final AtomicReference<Disposable> resource = new AtomicReference<>();

        a(Subscriber<? super Long> subscriber, long j, long j2) {
            this.downstream = subscriber;
            this.count = j;
            this.end = j2;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            DisposableHelper.dispose(this.resource);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.resource.get() != DisposableHelper.DISPOSED) {
                long j = get();
                if (j != 0) {
                    long j2 = this.count;
                    this.downstream.onNext(Long.valueOf(j2));
                    if (j2 == this.end) {
                        if (this.resource.get() != DisposableHelper.DISPOSED) {
                            this.downstream.onComplete();
                        }
                        DisposableHelper.dispose(this.resource);
                        return;
                    }
                    this.count = j2 + 1;
                    if (j != Long.MAX_VALUE) {
                        decrementAndGet();
                        return;
                    }
                    return;
                }
                Subscriber<? super Long> subscriber = this.downstream;
                subscriber.onError(new MissingBackpressureException("Can't deliver value " + this.count + " due to lack of requests"));
                DisposableHelper.dispose(this.resource);
            }
        }

        public void a(Disposable disposable) {
            DisposableHelper.setOnce(this.resource, disposable);
        }
    }
}
