package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class ParallelSortedJoin<T> extends Flowable<T> {
    final ParallelFlowable<List<T>> b;
    final Comparator<? super T> c;

    public ParallelSortedJoin(ParallelFlowable<List<T>> parallelFlowable, Comparator<? super T> comparator) {
        this.b = parallelFlowable;
        this.c = comparator;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        b bVar = new b(subscriber, this.b.parallelism(), this.c);
        subscriber.onSubscribe(bVar);
        this.b.subscribe(bVar.subscribers);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 3481980673745556697L;
        volatile boolean cancelled;
        final Comparator<? super T> comparator;
        final Subscriber<? super T> downstream;
        final int[] indexes;
        final List<T>[] lists;
        final a<T>[] subscribers;
        final AtomicLong requested = new AtomicLong();
        final AtomicInteger remaining = new AtomicInteger();
        final AtomicReference<Throwable> error = new AtomicReference<>();

        b(Subscriber<? super T> subscriber, int i, Comparator<? super T> comparator) {
            this.downstream = subscriber;
            this.comparator = comparator;
            a<T>[] aVarArr = new a[i];
            for (int i2 = 0; i2 < i; i2++) {
                aVarArr[i2] = new a<>(this, i2);
            }
            this.subscribers = aVarArr;
            this.lists = new List[i];
            this.indexes = new int[i];
            this.remaining.lazySet(i);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                if (this.remaining.get() == 0) {
                    b();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                a();
                if (getAndIncrement() == 0) {
                    Arrays.fill(this.lists, (Object) null);
                }
            }
        }

        void a() {
            for (a<T> aVar : this.subscribers) {
                aVar.a();
            }
        }

        void a(List<T> list, int i) {
            this.lists[i] = list;
            if (this.remaining.decrementAndGet() == 0) {
                b();
            }
        }

        void a(Throwable th) {
            if (this.error.compareAndSet(null, th)) {
                b();
            } else if (th != this.error.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:41:0x00a5, code lost:
            r16 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00a6, code lost:
            if (r13 != 0) goto L_0x00e5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x00aa, code lost:
            if (r18.cancelled == false) goto L_0x00b0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x00ac, code lost:
            java.util.Arrays.fill(r3, (java.lang.Object) null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x00af, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00b0, code lost:
            r5 = r18.error.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x00b8, code lost:
            if (r5 == null) goto L_0x00c4;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x00ba, code lost:
            a();
            java.util.Arrays.fill(r3, (java.lang.Object) null);
            r2.onError(r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00c3, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x00c4, code lost:
            r5 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00c5, code lost:
            if (r5 >= r4) goto L_0x00d7;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00cf, code lost:
            if (r0[r5] == r3[r5].size()) goto L_0x00d4;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00d1, code lost:
            r16 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00d4, code lost:
            r5 = r5 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00d9, code lost:
            if (r16 == false) goto L_0x00e2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00db, code lost:
            java.util.Arrays.fill(r3, (java.lang.Object) null);
            r2.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x00e1, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x00e2, code lost:
            r13 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00e5, code lost:
            r13 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x00e9, code lost:
            if (r11 == r13) goto L_0x00fa;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x00f2, code lost:
            if (r7 == Long.MAX_VALUE) goto L_0x00fa;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x00f4, code lost:
            r18.requested.addAndGet(-r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x00fa, code lost:
            r5 = get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x00fe, code lost:
            if (r5 != r6) goto L_0x010b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x0100, code lost:
            r5 = addAndGet(-r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x0105, code lost:
            if (r5 != 0) goto L_0x0108;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x0107, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:73:0x0108, code lost:
            r6 = r5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:74:0x010b, code lost:
            r6 = r5;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void b() {
            /*
                Method dump skipped, instructions count: 270
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.parallel.ParallelSortedJoin.b.b():void");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicReference<Subscription> implements FlowableSubscriber<List<T>> {
        private static final long serialVersionUID = 6751017204873808094L;
        final int index;
        final b<T> parent;

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        a(b<T> bVar, int i) {
            this.parent = bVar;
            this.index = i;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }

        /* renamed from: a */
        public void onNext(List<T> list) {
            this.parent.a(list, this.index);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.a(th);
        }

        void a() {
            SubscriptionHelper.cancel(this);
        }
    }
}
