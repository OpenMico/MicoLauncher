package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableScan<T> extends a<T, T> {
    final BiFunction<T, T, T> b;

    public FlowableScan(Flowable<T> flowable, BiFunction<T, T, T> biFunction) {
        super(flowable);
        this.b = biFunction;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        final BiFunction<T, T, T> b;
        Subscription c;
        T d;
        boolean e;

        a(Subscriber<? super T> subscriber, BiFunction<T, T, T> biFunction) {
            this.a = subscriber;
            this.b = biFunction;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
            }
        }

        /* JADX WARN: Type inference failed for: r4v3, types: [T, java.lang.Object] */
        /* JADX WARN: Unknown variable types count: 1 */
        @Override // org.reactivestreams.Subscriber
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onNext(T r4) {
            /*
                r3 = this;
                boolean r0 = r3.e
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                org.reactivestreams.Subscriber<? super T> r0 = r3.a
                T r1 = r3.d
                if (r1 != 0) goto L_0x0011
                r3.d = r4
                r0.onNext(r4)
                goto L_0x0022
            L_0x0011:
                io.reactivex.rxjava3.functions.BiFunction<T, T, T> r2 = r3.b     // Catch: Throwable -> 0x0023
                java.lang.Object r4 = r2.apply(r1, r4)     // Catch: Throwable -> 0x0023
                java.lang.String r1 = "The value returned by the accumulator is null"
                java.lang.Object r4 = java.util.Objects.requireNonNull(r4, r1)     // Catch: Throwable -> 0x0023
                r3.d = r4
                r0.onNext(r4)
            L_0x0022:
                return
            L_0x0023:
                r4 = move-exception
                io.reactivex.rxjava3.exceptions.Exceptions.throwIfFatal(r4)
                org.reactivestreams.Subscription r0 = r3.c
                r0.cancel()
                r3.onError(r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableScan.a.onNext(java.lang.Object):void");
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.a.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.c.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.c.cancel();
        }
    }
}
