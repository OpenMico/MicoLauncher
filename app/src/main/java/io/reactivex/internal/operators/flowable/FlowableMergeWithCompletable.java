package io.reactivex.internal.operators.flowable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableMergeWithCompletable<T> extends a<T, T> {
    final CompletableSource b;

    public FlowableMergeWithCompletable(Flowable<T> flowable, CompletableSource completableSource) {
        super(flowable);
        this.b = completableSource;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber);
        subscriber.onSubscribe(aVar);
        this.source.subscribe((FlowableSubscriber) aVar);
        this.b.subscribe(aVar.otherObserver);
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4592979584110982903L;
        final Subscriber<? super T> downstream;
        volatile boolean mainDone;
        volatile boolean otherDone;
        final AtomicReference<Subscription> mainSubscription = new AtomicReference<>();
        final C0225a otherObserver = new C0225a(this);
        final AtomicThrowable error = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();

        a(Subscriber<? super T> subscriber) {
            this.downstream = subscriber;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.mainSubscription, this.requested, subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            HalfSerializer.onNext(this.downstream, t, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.otherObserver);
            HalfSerializer.onError(this.downstream, th, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.mainDone = true;
            if (this.otherDone) {
                HalfSerializer.onComplete(this.downstream, this, this.error);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.mainSubscription, this.requested, j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.mainSubscription);
            DisposableHelper.dispose(this.otherObserver);
        }

        void a(Throwable th) {
            SubscriptionHelper.cancel(this.mainSubscription);
            HalfSerializer.onError(this.downstream, th, this, this.error);
        }

        void a() {
            this.otherDone = true;
            if (this.mainDone) {
                HalfSerializer.onComplete(this.downstream, this, this.error);
            }
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableMergeWithCompletable$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static final class C0225a extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -2935427570954647017L;
            final a<?> parent;

            C0225a(a<?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.parent.a(th);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                this.parent.a();
            }
        }
    }
}
