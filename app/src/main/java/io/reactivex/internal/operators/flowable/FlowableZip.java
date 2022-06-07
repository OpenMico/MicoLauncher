package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableZip<T, R> extends Flowable<R> {
    final Publisher<? extends T>[] b;
    final Iterable<? extends Publisher<? extends T>> c;
    final Function<? super Object[], ? extends R> d;
    final int e;
    final boolean f;

    public FlowableZip(Publisher<? extends T>[] publisherArr, Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.b = publisherArr;
        this.c = iterable;
        this.d = function;
        this.e = i;
        this.f = z;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        int i;
        Publisher<? extends T>[] publisherArr = this.b;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            i = 0;
            for (Publisher<? extends T> publisher : this.c) {
                if (i == publisherArr.length) {
                    Publisher<? extends T>[] publisherArr2 = new Publisher[(i >> 2) + i];
                    System.arraycopy(publisherArr, 0, publisherArr2, 0, i);
                    publisherArr = publisherArr2;
                }
                i++;
                publisherArr[i] = publisher;
            }
        } else {
            i = publisherArr.length;
        }
        if (i == 0) {
            EmptySubscription.complete(subscriber);
            return;
        }
        a aVar = new a(subscriber, this.d, i, this.e, this.f);
        subscriber.onSubscribe(aVar);
        aVar.a(publisherArr, i);
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = -2434867452883857743L;
        volatile boolean cancelled;
        final Object[] current;
        final boolean delayErrors;
        final Subscriber<? super R> downstream;
        final AtomicThrowable errors;
        final AtomicLong requested;
        final b<T, R>[] subscribers;
        final Function<? super Object[], ? extends R> zipper;

        a(Subscriber<? super R> subscriber, Function<? super Object[], ? extends R> function, int i, int i2, boolean z) {
            this.downstream = subscriber;
            this.zipper = function;
            this.delayErrors = z;
            b<T, R>[] bVarArr = new b[i];
            for (int i3 = 0; i3 < i; i3++) {
                bVarArr[i3] = new b<>(this, i2);
            }
            this.current = new Object[i];
            this.subscribers = bVarArr;
            this.requested = new AtomicLong();
            this.errors = new AtomicThrowable();
        }

        void a(Publisher<? extends T>[] publisherArr, int i) {
            b<T, R>[] bVarArr = this.subscribers;
            for (int i2 = 0; i2 < i && !this.cancelled; i2++) {
                if (this.delayErrors || this.errors.get() == null) {
                    publisherArr[i2].subscribe(bVarArr[i2]);
                } else {
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                b();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                a();
            }
        }

        void a(b<T, R> bVar, Throwable th) {
            if (this.errors.addThrowable(th)) {
                bVar.done = true;
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        void a() {
            for (b<T, R> bVar : this.subscribers) {
                bVar.cancel();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:128:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00d4, code lost:
            if (r14 != 0) goto L_0x0154;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00d8, code lost:
            if (r18.cancelled == false) goto L_0x00db;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00da, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x00dd, code lost:
            if (r18.delayErrors != false) goto L_0x00f4;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00e5, code lost:
            if (r18.errors.get() == null) goto L_0x00f4;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x00e7, code lost:
            a();
            r2.onError(r18.errors.terminate());
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x00f3, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00f4, code lost:
            r6 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x00f6, code lost:
            if (r6 >= r4) goto L_0x0151;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x00f8, code lost:
            r0 = r3[r6];
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x00fc, code lost:
            if (r5[r6] != null) goto L_0x014e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x00fe, code lost:
            r10 = r0.done;
            r0 = r0.queue;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x0102, code lost:
            if (r0 == null) goto L_0x0109;
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x0104, code lost:
            r0 = r0.poll();
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x0109, code lost:
            r0 = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x010a, code lost:
            if (r0 != null) goto L_0x010e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x010c, code lost:
            r11 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x010e, code lost:
            r11 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:73:0x0110, code lost:
            if (r10 == false) goto L_0x012f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:74:0x0112, code lost:
            if (r11 == false) goto L_0x012f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:75:0x0114, code lost:
            a();
         */
        /* JADX WARN: Code restructure failed: missing block: B:76:0x011f, code lost:
            if (r18.errors.get() == null) goto L_0x012b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:77:0x0121, code lost:
            r2.onError(r18.errors.terminate());
         */
        /* JADX WARN: Code restructure failed: missing block: B:78:0x012b, code lost:
            r2.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:79:0x012e, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:80:0x012f, code lost:
            if (r11 != false) goto L_0x014e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:81:0x0131, code lost:
            r5[r6] = r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:82:0x0134, code lost:
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:83:0x0135, code lost:
            io.reactivex.exceptions.Exceptions.throwIfFatal(r0);
            r18.errors.addThrowable(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:84:0x013f, code lost:
            if (r18.delayErrors == false) goto L_0x0141;
         */
        /* JADX WARN: Code restructure failed: missing block: B:85:0x0141, code lost:
            a();
            r2.onError(r18.errors.terminate());
         */
        /* JADX WARN: Code restructure failed: missing block: B:86:0x014d, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:87:0x014e, code lost:
            r6 = r6 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:88:0x0151, code lost:
            r10 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:89:0x0154, code lost:
            r10 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:91:0x0158, code lost:
            if (r12 == r10) goto L_0x0176;
         */
        /* JADX WARN: Code restructure failed: missing block: B:92:0x015a, code lost:
            r0 = r3.length;
            r6 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:93:0x015d, code lost:
            if (r6 >= r0) goto L_0x0167;
         */
        /* JADX WARN: Code restructure failed: missing block: B:94:0x015f, code lost:
            r3[r6].request(r12);
            r6 = r6 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:96:0x016e, code lost:
            if (r8 == Long.MAX_VALUE) goto L_0x0176;
         */
        /* JADX WARN: Code restructure failed: missing block: B:97:0x0170, code lost:
            r18.requested.addAndGet(-r12);
         */
        /* JADX WARN: Code restructure failed: missing block: B:98:0x0176, code lost:
            r7 = addAndGet(-r7);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void b() {
            /*
                Method dump skipped, instructions count: 382
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableZip.a.b():void");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4627193790118206028L;
        volatile boolean done;
        final int limit;
        final a<T, R> parent;
        final int prefetch;
        long produced;
        SimpleQueue<T> queue;
        int sourceMode;

        b(a<T, R> aVar, int i) {
            this.parent = aVar;
            this.prefetch = i;
            this.limit = i - (i >> 2);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.b();
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        subscription.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                subscription.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode != 2) {
                this.queue.offer(t);
            }
            this.parent.b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.a(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.b();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.sourceMode != 1) {
                long j2 = this.produced + j;
                if (j2 >= this.limit) {
                    this.produced = 0L;
                    get().request(j2);
                    return;
                }
                this.produced = j2;
            }
        }
    }
}
