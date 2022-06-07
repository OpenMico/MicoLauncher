package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableRefCount<T> extends Flowable<T> {
    final ConnectableFlowable<T> b;
    final int c;
    final long d;
    final TimeUnit e;
    final Scheduler f;
    a g;

    public FlowableRefCount(ConnectableFlowable<T> connectableFlowable) {
        this(connectableFlowable, 1, 0L, TimeUnit.NANOSECONDS, null);
    }

    public FlowableRefCount(ConnectableFlowable<T> connectableFlowable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.b = connectableFlowable;
        this.c = i;
        this.d = j;
        this.e = timeUnit;
        this.f = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar;
        boolean z;
        synchronized (this) {
            aVar = this.g;
            if (aVar == null) {
                aVar = new a(this);
                this.g = aVar;
            }
            long j = aVar.subscriberCount;
            if (j == 0 && aVar.timer != null) {
                aVar.timer.dispose();
            }
            long j2 = j + 1;
            aVar.subscriberCount = j2;
            z = true;
            if (aVar.connected || j2 != this.c) {
                z = false;
            } else {
                aVar.connected = true;
            }
        }
        this.b.subscribe((FlowableSubscriber) new b(subscriber, this, aVar));
        if (z) {
            this.b.connect(aVar);
        }
    }

    void a(a aVar) {
        synchronized (this) {
            if (this.g != null && this.g == aVar) {
                long j = aVar.subscriberCount - 1;
                aVar.subscriberCount = j;
                if (j == 0 && aVar.connected) {
                    if (this.d == 0) {
                        c(aVar);
                        return;
                    }
                    SequentialDisposable sequentialDisposable = new SequentialDisposable();
                    aVar.timer = sequentialDisposable;
                    sequentialDisposable.replace(this.f.scheduleDirect(aVar, this.d, this.e));
                }
            }
        }
    }

    void b(a aVar) {
        synchronized (this) {
            if (this.g == aVar) {
                if (aVar.timer != null) {
                    aVar.timer.dispose();
                    aVar.timer = null;
                }
                long j = aVar.subscriberCount - 1;
                aVar.subscriberCount = j;
                if (j == 0) {
                    this.g = null;
                    this.b.reset();
                }
            }
        }
    }

    void c(a aVar) {
        synchronized (this) {
            if (aVar.subscriberCount == 0 && aVar == this.g) {
                this.g = null;
                Disposable disposable = aVar.get();
                DisposableHelper.dispose(aVar);
                if (disposable == null) {
                    aVar.disconnectedEarly = true;
                } else {
                    this.b.reset();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a extends AtomicReference<Disposable> implements Consumer<Disposable>, Runnable {
        private static final long serialVersionUID = -4552101107598366241L;
        boolean connected;
        boolean disconnectedEarly;
        final FlowableRefCount<?> parent;
        long subscriberCount;
        Disposable timer;

        a(FlowableRefCount<?> flowableRefCount) {
            this.parent = flowableRefCount;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.parent.c(this);
        }

        /* renamed from: a */
        public void accept(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
            synchronized (this.parent) {
                if (this.disconnectedEarly) {
                    this.parent.b.reset();
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -7419642935409022375L;
        final a connection;
        final Subscriber<? super T> downstream;
        final FlowableRefCount<T> parent;
        Subscription upstream;

        b(Subscriber<? super T> subscriber, FlowableRefCount<T> flowableRefCount, a aVar) {
            this.downstream = subscriber;
            this.parent = flowableRefCount;
            this.connection = aVar;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (compareAndSet(false, true)) {
                this.parent.b(this.connection);
                this.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (compareAndSet(false, true)) {
                this.parent.b(this.connection);
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.upstream.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            if (compareAndSet(false, true)) {
                this.parent.a(this.connection);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }
    }
}
