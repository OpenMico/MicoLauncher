package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowablePublishMulticast<T, R> extends a<T, R> {
    final Function<? super Flowable<T>, ? extends Publisher<? extends R>> b;
    final int c;
    final boolean d;

    public FlowablePublishMulticast(Flowable<T> flowable, Function<? super Flowable<T>, ? extends Publisher<? extends R>> function, int i, boolean z) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        a aVar = new a(this.c, this.d);
        try {
            ((Publisher) ObjectHelper.requireNonNull(this.b.apply(aVar), "selector returned a null Publisher")).subscribe(new c(subscriber, aVar));
            this.source.subscribe((FlowableSubscriber) aVar);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }

    /* loaded from: classes4.dex */
    static final class c<R> implements FlowableSubscriber<R>, Subscription {
        final Subscriber<? super R> a;
        final a<?> b;
        Subscription c;

        c(Subscriber<? super R> subscriber, a<?> aVar) {
            this.a = subscriber;
            this.b = aVar;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            this.a.onNext(r);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.onError(th);
            this.b.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.onComplete();
            this.b.dispose();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.c.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.c.cancel();
            this.b.dispose();
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends Flowable<T> implements FlowableSubscriber<T>, Disposable {
        static final b[] b = new b[0];
        static final b[] c = new b[0];
        final int f;
        final int g;
        final boolean h;
        volatile SimpleQueue<T> j;
        int k;
        volatile boolean l;
        Throwable m;
        int n;
        final AtomicInteger d = new AtomicInteger();
        final AtomicReference<Subscription> i = new AtomicReference<>();
        final AtomicReference<b<T>[]> e = new AtomicReference<>(b);

        a(int i, boolean z) {
            this.f = i;
            this.g = i - (i >> 2);
            this.h = z;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.i, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.k = requestFusion;
                        this.j = queueSubscription;
                        this.l = true;
                        a();
                        return;
                    } else if (requestFusion == 2) {
                        this.k = requestFusion;
                        this.j = queueSubscription;
                        QueueDrainHelper.request(subscription, this.f);
                        return;
                    }
                }
                this.j = QueueDrainHelper.createQueue(this.f);
                QueueDrainHelper.request(subscription, this.f);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SimpleQueue<T> simpleQueue;
            SubscriptionHelper.cancel(this.i);
            if (this.d.getAndIncrement() == 0 && (simpleQueue = this.j) != null) {
                simpleQueue.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.i.get() == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.l) {
                if (this.k != 0 || this.j.offer(t)) {
                    a();
                    return;
                }
                this.i.get().cancel();
                onError(new MissingBackpressureException());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.l) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.m = th;
            this.l = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.l) {
                this.l = true;
                a();
            }
        }

        boolean a(b<T> bVar) {
            b<T>[] bVarArr;
            b<T>[] bVarArr2;
            do {
                bVarArr = this.e.get();
                if (bVarArr == c) {
                    return false;
                }
                int length = bVarArr.length;
                bVarArr2 = new b[length + 1];
                System.arraycopy(bVarArr, 0, bVarArr2, 0, length);
                bVarArr2[length] = bVar;
            } while (!this.e.compareAndSet(bVarArr, bVarArr2));
            return true;
        }

        void b(b<T> bVar) {
            b<T>[] bVarArr;
            b<T>[] bVarArr2;
            do {
                bVarArr = this.e.get();
                int length = bVarArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (bVarArr[i2] == bVar) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            bVarArr2 = b;
                        } else {
                            b<T>[] bVarArr3 = new b[length - 1];
                            System.arraycopy(bVarArr, 0, bVarArr3, 0, i);
                            System.arraycopy(bVarArr, i + 1, bVarArr3, i, (length - i) - 1);
                            bVarArr2 = bVarArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.e.compareAndSet(bVarArr, bVarArr2));
        }

        @Override // io.reactivex.Flowable
        protected void subscribeActual(Subscriber<? super T> subscriber) {
            b<T> bVar = new b<>(subscriber, this);
            subscriber.onSubscribe(bVar);
            if (!a(bVar)) {
                Throwable th = this.m;
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
            } else if (bVar.a()) {
                b(bVar);
            } else {
                a();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:131:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x00eb, code lost:
            r8 = r5;
            r7 = r21;
         */
        /* JADX WARN: Code restructure failed: missing block: B:76:0x00ff, code lost:
            if (r7 != 0) goto L_0x0131;
         */
        /* JADX WARN: Code restructure failed: missing block: B:78:0x0105, code lost:
            if (isDisposed() == false) goto L_0x010b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:79:0x0107, code lost:
            r0.clear();
         */
        /* JADX WARN: Code restructure failed: missing block: B:80:0x010a, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:81:0x010b, code lost:
            r5 = r24.l;
         */
        /* JADX WARN: Code restructure failed: missing block: B:82:0x010d, code lost:
            if (r5 == false) goto L_0x011b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:84:0x0111, code lost:
            if (r24.h != false) goto L_0x011b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:85:0x0113, code lost:
            r6 = r24.m;
         */
        /* JADX WARN: Code restructure failed: missing block: B:86:0x0115, code lost:
            if (r6 == null) goto L_0x011b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:87:0x0117, code lost:
            a(r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:88:0x011a, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:89:0x011b, code lost:
            if (r5 == false) goto L_0x0131;
         */
        /* JADX WARN: Code restructure failed: missing block: B:91:0x0121, code lost:
            if (r0.isEmpty() == false) goto L_0x0131;
         */
        /* JADX WARN: Code restructure failed: missing block: B:92:0x0123, code lost:
            r0 = r24.m;
         */
        /* JADX WARN: Code restructure failed: missing block: B:93:0x0125, code lost:
            if (r0 == null) goto L_0x012b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:94:0x0127, code lost:
            a(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:95:0x012b, code lost:
            b();
         */
        /* JADX WARN: Code restructure failed: missing block: B:96:0x012e, code lost:
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a() {
            /*
                Method dump skipped, instructions count: 333
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowablePublishMulticast.a.a():void");
        }

        void a(Throwable th) {
            b<T>[] andSet = this.e.getAndSet(c);
            for (b<T> bVar : andSet) {
                if (bVar.get() != Long.MIN_VALUE) {
                    bVar.downstream.onError(th);
                }
            }
        }

        void b() {
            b<T>[] andSet = this.e.getAndSet(c);
            for (b<T> bVar : andSet) {
                if (bVar.get() != Long.MIN_VALUE) {
                    bVar.downstream.onComplete();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = 8664815189257569791L;
        final Subscriber<? super T> downstream;
        long emitted;
        final a<T> parent;

        b(Subscriber<? super T> subscriber, a<T> aVar) {
            this.downstream = subscriber;
            this.parent = aVar;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this, j);
                this.parent.a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.b(this);
                this.parent.a();
            }
        }

        public boolean a() {
            return get() == Long.MIN_VALUE;
        }
    }
}
