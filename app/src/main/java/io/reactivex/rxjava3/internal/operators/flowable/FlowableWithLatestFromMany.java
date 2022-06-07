package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableWithLatestFromMany<T, R> extends a<T, R> {
    @Nullable
    final Publisher<?>[] b;
    @Nullable
    final Iterable<? extends Publisher<?>> c;
    final Function<? super Object[], R> d;

    public FlowableWithLatestFromMany(@NonNull Flowable<T> flowable, @NonNull Publisher<?>[] publisherArr, Function<? super Object[], R> function) {
        super(flowable);
        this.b = publisherArr;
        this.c = null;
        this.d = function;
    }

    public FlowableWithLatestFromMany(@NonNull Flowable<T> flowable, @NonNull Iterable<? extends Publisher<?>> iterable, @NonNull Function<? super Object[], R> function) {
        super(flowable);
        this.b = null;
        this.c = iterable;
        this.d = function;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        int i;
        Publisher<?>[] publisherArr = this.b;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                i = 0;
                for (Publisher<?> publisher : this.c) {
                    if (i == publisherArr.length) {
                        publisherArr = (Publisher[]) Arrays.copyOf(publisherArr, (i >> 1) + i);
                    }
                    i++;
                    publisherArr[i] = publisher;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptySubscription.error(th, subscriber);
                return;
            }
        } else {
            i = publisherArr.length;
        }
        if (i == 0) {
            new FlowableMap(this.source, new a()).subscribeActual(subscriber);
            return;
        }
        b bVar = new b(subscriber, this.d, i);
        subscriber.onSubscribe(bVar);
        bVar.a(publisherArr, i);
        this.source.subscribe((FlowableSubscriber) bVar);
    }

    /* loaded from: classes5.dex */
    static final class b<T, R> extends AtomicInteger implements ConditionalSubscriber<T>, Subscription {
        private static final long serialVersionUID = 1577321883966341961L;
        final Function<? super Object[], R> combiner;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final AtomicThrowable error;
        final AtomicLong requested;
        final c[] subscribers;
        final AtomicReference<Subscription> upstream;
        final AtomicReferenceArray<Object> values;

        b(Subscriber<? super R> subscriber, Function<? super Object[], R> function, int i) {
            this.downstream = subscriber;
            this.combiner = function;
            c[] cVarArr = new c[i];
            for (int i2 = 0; i2 < i; i2++) {
                cVarArr[i2] = new c(this, i2);
            }
            this.subscribers = cVarArr;
            this.values = new AtomicReferenceArray<>(i);
            this.upstream = new AtomicReference<>();
            this.requested = new AtomicLong();
            this.error = new AtomicThrowable();
        }

        void a(Publisher<?>[] publisherArr, int i) {
            c[] cVarArr = this.subscribers;
            AtomicReference<Subscription> atomicReference = this.upstream;
            for (int i2 = 0; i2 < i && atomicReference.get() != SubscriptionHelper.CANCELLED; i2++) {
                publisherArr[i2].subscribe(cVarArr[i2]);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!tryOnNext(t) && !this.done) {
                this.upstream.get().request(1L);
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            AtomicReferenceArray<Object> atomicReferenceArray = this.values;
            int length = atomicReferenceArray.length();
            Object[] objArr = new Object[length + 1];
            objArr[0] = t;
            int i = 0;
            while (i < length) {
                Object obj = atomicReferenceArray.get(i);
                if (obj == null) {
                    return false;
                }
                i++;
                objArr[i] = obj;
            }
            try {
                HalfSerializer.onNext(this.downstream, Objects.requireNonNull(this.combiner.apply(objArr), "The combiner returned a null value"), this, this.error);
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                onError(th);
                return false;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            a(-1);
            HalfSerializer.onError(this.downstream, th, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                a(-1);
                HalfSerializer.onComplete(this.downstream, this, this.error);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.upstream, this.requested, j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.upstream);
            for (c cVar : this.subscribers) {
                cVar.a();
            }
        }

        void a(int i, Object obj) {
            this.values.set(i, obj);
        }

        void a(int i, Throwable th) {
            this.done = true;
            SubscriptionHelper.cancel(this.upstream);
            a(i);
            HalfSerializer.onError(this.downstream, th, this, this.error);
        }

        void a(int i, boolean z) {
            if (!z) {
                this.done = true;
                SubscriptionHelper.cancel(this.upstream);
                a(i);
                HalfSerializer.onComplete(this.downstream, this, this.error);
            }
        }

        void a(int i) {
            c[] cVarArr = this.subscribers;
            for (int i2 = 0; i2 < cVarArr.length; i2++) {
                if (i2 != i) {
                    cVarArr[i2].a();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        private static final long serialVersionUID = 3256684027868224024L;
        boolean hasValue;
        final int index;
        final b<?, ?> parent;

        c(b<?, ?> bVar, int i) {
            this.parent = bVar;
            this.index = i;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (!this.hasValue) {
                this.hasValue = true;
            }
            this.parent.a(this.index, obj);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.a(this.index, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.a(this.index, this.hasValue);
        }

        void a() {
            SubscriptionHelper.cancel(this);
        }
    }

    /* loaded from: classes5.dex */
    final class a implements Function<T, R> {
        a() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object[], java.lang.Object] */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t) throws Throwable {
            return (R) Objects.requireNonNull(FlowableWithLatestFromMany.this.d.apply(new Object[]{t}), "The combiner returned a null value");
        }
    }
}
