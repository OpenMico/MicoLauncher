package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableOnBackpressureBufferStrategy<T> extends a<T, T> {
    final long b;
    final Action c;
    final BackpressureOverflowStrategy d;

    public FlowableOnBackpressureBufferStrategy(Flowable<T> flowable, long j, Action action, BackpressureOverflowStrategy backpressureOverflowStrategy) {
        super(flowable);
        this.b = j;
        this.c = action;
        this.d = backpressureOverflowStrategy;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.c, this.d, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 3240706908776709697L;
        final long bufferSize;
        volatile boolean cancelled;
        volatile boolean done;
        final Subscriber<? super T> downstream;
        Throwable error;
        final Action onOverflow;
        final BackpressureOverflowStrategy strategy;
        Subscription upstream;
        final AtomicLong requested = new AtomicLong();
        final Deque<T> deque = new ArrayDeque();

        a(Subscriber<? super T> subscriber, Action action, BackpressureOverflowStrategy backpressureOverflowStrategy, long j) {
            this.downstream = subscriber;
            this.onOverflow = action;
            this.strategy = backpressureOverflowStrategy;
            this.bufferSize = j;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            boolean z;
            boolean z2;
            if (!this.done) {
                Deque<T> deque = this.deque;
                synchronized (deque) {
                    z = false;
                    z2 = true;
                    if (deque.size() == this.bufferSize) {
                        switch (this.strategy) {
                            case DROP_LATEST:
                                deque.pollLast();
                                deque.offer(t);
                                z2 = false;
                                z = true;
                                break;
                            case DROP_OLDEST:
                                deque.poll();
                                deque.offer(t);
                                z2 = false;
                                z = true;
                                break;
                        }
                    } else {
                        deque.offer(t);
                        z2 = false;
                    }
                }
                if (z) {
                    Action action = this.onOverflow;
                    if (action != null) {
                        try {
                            action.run();
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.upstream.cancel();
                            onError(th);
                        }
                    }
                } else if (z2) {
                    this.upstream.cancel();
                    onError(new MissingBackpressureException());
                } else {
                    a();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            a();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            if (getAndIncrement() == 0) {
                a(this.deque);
            }
        }

        void a(Deque<T> deque) {
            synchronized (deque) {
                deque.clear();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x004f, code lost:
            if (r10 != 0) goto L_0x0077;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0053, code lost:
            if (r14.cancelled == false) goto L_0x0059;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0055, code lost:
            a(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0058, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0059, code lost:
            r4 = r14.done;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x005b, code lost:
            monitor-enter(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x005c, code lost:
            r5 = r0.isEmpty();
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0060, code lost:
            monitor-exit(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0061, code lost:
            if (r4 == false) goto L_0x0077;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x0063, code lost:
            r4 = r14.error;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x0065, code lost:
            if (r4 == null) goto L_0x006e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x0067, code lost:
            a(r0);
            r1.onError(r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x006d, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x006e, code lost:
            if (r5 == false) goto L_0x0077;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x0070, code lost:
            r1.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0073, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x0079, code lost:
            if (r8 == 0) goto L_0x0080;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x007b, code lost:
            io.reactivex.rxjava3.internal.util.BackpressureHelper.produced(r14.requested, r8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x0080, code lost:
            r3 = addAndGet(-r3);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a() {
            /*
                r14 = this;
                int r0 = r14.getAndIncrement()
                if (r0 == 0) goto L_0x0007
                return
            L_0x0007:
                java.util.Deque<T> r0 = r14.deque
                org.reactivestreams.Subscriber<? super T> r1 = r14.downstream
                r2 = 1
                r3 = r2
            L_0x000d:
                java.util.concurrent.atomic.AtomicLong r4 = r14.requested
                long r4 = r4.get()
                r6 = 0
                r8 = r6
            L_0x0016:
                int r10 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r10 == 0) goto L_0x004f
                boolean r11 = r14.cancelled
                if (r11 == 0) goto L_0x0022
                r14.a(r0)
                return
            L_0x0022:
                boolean r11 = r14.done
                monitor-enter(r0)
                java.lang.Object r12 = r0.poll()     // Catch: all -> 0x004c
                monitor-exit(r0)     // Catch: all -> 0x004c
                if (r12 != 0) goto L_0x002e
                r13 = r2
                goto L_0x002f
            L_0x002e:
                r13 = 0
            L_0x002f:
                if (r11 == 0) goto L_0x0042
                java.lang.Throwable r11 = r14.error
                if (r11 == 0) goto L_0x003c
                r14.a(r0)
                r1.onError(r11)
                return
            L_0x003c:
                if (r13 == 0) goto L_0x0042
                r1.onComplete()
                return
            L_0x0042:
                if (r13 == 0) goto L_0x0045
                goto L_0x004f
            L_0x0045:
                r1.onNext(r12)
                r10 = 1
                long r8 = r8 + r10
                goto L_0x0016
            L_0x004c:
                r1 = move-exception
                monitor-exit(r0)     // Catch: all -> 0x004c
                throw r1
            L_0x004f:
                if (r10 != 0) goto L_0x0077
                boolean r4 = r14.cancelled
                if (r4 == 0) goto L_0x0059
                r14.a(r0)
                return
            L_0x0059:
                boolean r4 = r14.done
                monitor-enter(r0)
                boolean r5 = r0.isEmpty()     // Catch: all -> 0x0074
                monitor-exit(r0)     // Catch: all -> 0x0074
                if (r4 == 0) goto L_0x0077
                java.lang.Throwable r4 = r14.error
                if (r4 == 0) goto L_0x006e
                r14.a(r0)
                r1.onError(r4)
                return
            L_0x006e:
                if (r5 == 0) goto L_0x0077
                r1.onComplete()
                return
            L_0x0074:
                r1 = move-exception
                monitor-exit(r0)     // Catch: all -> 0x0074
                throw r1
            L_0x0077:
                int r4 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r4 == 0) goto L_0x0080
                java.util.concurrent.atomic.AtomicLong r4 = r14.requested
                io.reactivex.rxjava3.internal.util.BackpressureHelper.produced(r4, r8)
            L_0x0080:
                int r3 = -r3
                int r3 = r14.addAndGet(r3)
                if (r3 != 0) goto L_0x000d
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureBufferStrategy.a.a():void");
        }
    }
}
