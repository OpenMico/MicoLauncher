package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableDelay<T> extends a<T, T> {
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final boolean e;

    public FlowableDelay(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(flowable);
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(this.e ? subscriber : new SerializedSubscriber(subscriber), this.b, this.c, this.d.createWorker(), this.e));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        final long b;
        final TimeUnit c;
        final Scheduler.Worker d;
        final boolean e;
        Subscription f;

        a(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Scheduler.Worker worker, boolean z) {
            this.a = subscriber;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
            this.e = z;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.d.schedule(new c(t), this.b, this.c);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.d.schedule(new b(th), this.e ? this.b : 0L, this.c);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.d.schedule(new RunnableC0219a(), this.b, this.c);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f.cancel();
            this.d.dispose();
        }

        /* loaded from: classes4.dex */
        final class c implements Runnable {
            private final T b;

            c(T t) {
                this.b = t;
            }

            @Override // java.lang.Runnable
            public void run() {
                a.this.a.onNext((T) this.b);
            }
        }

        /* loaded from: classes4.dex */
        final class b implements Runnable {
            private final Throwable b;

            b(Throwable th) {
                this.b = th;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    a.this.a.onError(this.b);
                } finally {
                    a.this.d.dispose();
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        final class RunnableC0219a implements Runnable {
            RunnableC0219a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    a.this.a.onComplete();
                } finally {
                    a.this.d.dispose();
                }
            }
        }
    }
}
