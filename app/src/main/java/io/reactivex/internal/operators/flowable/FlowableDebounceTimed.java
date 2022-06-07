package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableDebounceTimed<T> extends a<T, T> {
    final long b;
    final TimeUnit c;
    final Scheduler d;

    public FlowableDebounceTimed(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler) {
        super(flowable);
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new b(new SerializedSubscriber(subscriber), this.b, this.c, this.d.createWorker()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -9102637559663639004L;
        boolean done;
        final Subscriber<? super T> downstream;
        volatile long index;
        final long timeout;
        Disposable timer;
        final TimeUnit unit;
        Subscription upstream;
        final Scheduler.Worker worker;

        b(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Scheduler.Worker worker) {
            this.downstream = subscriber;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                long j = this.index + 1;
                this.index = j;
                Disposable disposable = this.timer;
                if (disposable != null) {
                    disposable.dispose();
                }
                a aVar = new a(t, j, this);
                this.timer = aVar;
                aVar.a(this.worker.schedule(aVar, this.timeout, this.unit));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            Disposable disposable = this.timer;
            if (disposable != null) {
                disposable.dispose();
            }
            this.downstream.onError(th);
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                Disposable disposable = this.timer;
                if (disposable != null) {
                    disposable.dispose();
                }
                a aVar = (a) disposable;
                if (aVar != null) {
                    aVar.a();
                }
                this.downstream.onComplete();
                this.worker.dispose();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            this.worker.dispose();
        }

        void a(long j, T t, a<T> aVar) {
            if (j != this.index) {
                return;
            }
            if (get() != 0) {
                this.downstream.onNext(t);
                BackpressureHelper.produced(this, 1L);
                aVar.dispose();
                return;
            }
            cancel();
            this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicReference<Disposable> implements Disposable, Runnable {
        private static final long serialVersionUID = 6812032969491025141L;
        final long idx;
        final AtomicBoolean once = new AtomicBoolean();
        final b<T> parent;
        final T value;

        a(T t, long j, b<T> bVar) {
            this.value = t;
            this.idx = j;
            this.parent = bVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            a();
        }

        void a() {
            if (this.once.compareAndSet(false, true)) {
                this.parent.a(this.idx, this.value, this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        public void a(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
        }
    }
}
