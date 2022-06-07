package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableBufferBoundary<T, U extends Collection<? super T>, Open, Close> extends a<T, U> {
    final Callable<U> b;
    final Publisher<? extends Open> c;
    final Function<? super Open, ? extends Publisher<? extends Close>> d;

    public FlowableBufferBoundary(Flowable<T> flowable, Publisher<? extends Open> publisher, Function<? super Open, ? extends Publisher<? extends Close>> function, Callable<U> callable) {
        super(flowable);
        this.c = publisher;
        this.d = function;
        this.b = callable;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        a aVar = new a(subscriber, this.c, this.d, this.b);
        subscriber.onSubscribe(aVar);
        this.source.subscribe((FlowableSubscriber) aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<T, C extends Collection<? super T>, Open, Close> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -8466418554264089604L;
        final Function<? super Open, ? extends Publisher<? extends Close>> bufferClose;
        final Publisher<? extends Open> bufferOpen;
        final Callable<C> bufferSupplier;
        volatile boolean cancelled;
        volatile boolean done;
        final Subscriber<? super C> downstream;
        long emitted;
        long index;
        final SpscLinkedArrayQueue<C> queue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        final CompositeDisposable subscribers = new CompositeDisposable();
        final AtomicLong requested = new AtomicLong();
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        Map<Long, C> buffers = new LinkedHashMap();
        final AtomicThrowable errors = new AtomicThrowable();

        a(Subscriber<? super C> subscriber, Publisher<? extends Open> publisher, Function<? super Open, ? extends Publisher<? extends Close>> function, Callable<C> callable) {
            this.downstream = subscriber;
            this.bufferSupplier = callable;
            this.bufferOpen = publisher;
            this.bufferClose = function;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.upstream, subscription)) {
                C0217a aVar = new C0217a(this);
                this.subscribers.add(aVar);
                this.bufferOpen.subscribe(aVar);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            synchronized (this) {
                Map<Long, C> map = this.buffers;
                if (map != null) {
                    for (C c : map.values()) {
                        c.add(t);
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.errors.addThrowable(th)) {
                this.subscribers.dispose();
                synchronized (this) {
                    this.buffers = null;
                }
                this.done = true;
                a();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.subscribers.dispose();
            synchronized (this) {
                Map<Long, C> map = this.buffers;
                if (map != null) {
                    for (C c : map.values()) {
                        this.queue.offer(c);
                    }
                    this.buffers = null;
                    this.done = true;
                    a();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.requested, j);
            a();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (SubscriptionHelper.cancel(this.upstream)) {
                this.cancelled = true;
                this.subscribers.dispose();
                synchronized (this) {
                    this.buffers = null;
                }
                if (getAndIncrement() != 0) {
                    this.queue.clear();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void a(Open open) {
            try {
                Collection collection = (Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null Collection");
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.bufferClose.apply(open), "The bufferClose returned a null Publisher");
                long j = this.index;
                this.index = 1 + j;
                synchronized (this) {
                    Map<Long, C> map = this.buffers;
                    if (map != 0) {
                        map.put(Long.valueOf(j), collection);
                        b bVar = new b(this, j);
                        this.subscribers.add(bVar);
                        publisher.subscribe(bVar);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                SubscriptionHelper.cancel(this.upstream);
                onError(th);
            }
        }

        void a(C0217a<Open> aVar) {
            this.subscribers.delete(aVar);
            if (this.subscribers.size() == 0) {
                SubscriptionHelper.cancel(this.upstream);
                this.done = true;
                a();
            }
        }

        void a(b<T, C> bVar, long j) {
            boolean z;
            this.subscribers.delete(bVar);
            if (this.subscribers.size() == 0) {
                SubscriptionHelper.cancel(this.upstream);
                z = true;
            } else {
                z = false;
            }
            synchronized (this) {
                if (this.buffers != null) {
                    this.queue.offer(this.buffers.remove(Long.valueOf(j)));
                    if (z) {
                        this.done = true;
                    }
                    a();
                }
            }
        }

        void a(Disposable disposable, Throwable th) {
            SubscriptionHelper.cancel(this.upstream);
            this.subscribers.delete(disposable);
            onError(th);
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x0057, code lost:
            if (r8 != 0) goto L_0x0084;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x005b, code lost:
            if (r12.cancelled == false) goto L_0x0061;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x005d, code lost:
            r3.clear();
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0060, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0063, code lost:
            if (r12.done == false) goto L_0x0084;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x006b, code lost:
            if (r12.errors.get() == null) goto L_0x007a;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x006d, code lost:
            r3.clear();
            r2.onError(r12.errors.terminate());
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0079, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x007e, code lost:
            if (r3.isEmpty() == false) goto L_0x0084;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0080, code lost:
            r2.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x0083, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x0084, code lost:
            r12.emitted = r0;
            r5 = addAndGet(-r5);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a() {
            /*
                r12 = this;
                int r0 = r12.getAndIncrement()
                if (r0 == 0) goto L_0x0007
                return
            L_0x0007:
                long r0 = r12.emitted
                org.reactivestreams.Subscriber<? super C extends java.util.Collection<? super T>> r2 = r12.downstream
                io.reactivex.internal.queue.SpscLinkedArrayQueue<C extends java.util.Collection<? super T>> r3 = r12.queue
                r4 = 1
                r5 = r4
            L_0x000f:
                java.util.concurrent.atomic.AtomicLong r6 = r12.requested
                long r6 = r6.get()
            L_0x0015:
                int r8 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                if (r8 == 0) goto L_0x0057
                boolean r9 = r12.cancelled
                if (r9 == 0) goto L_0x0021
                r3.clear()
                return
            L_0x0021:
                boolean r9 = r12.done
                if (r9 == 0) goto L_0x003a
                io.reactivex.internal.util.AtomicThrowable r10 = r12.errors
                java.lang.Object r10 = r10.get()
                if (r10 == 0) goto L_0x003a
                r3.clear()
                io.reactivex.internal.util.AtomicThrowable r0 = r12.errors
                java.lang.Throwable r0 = r0.terminate()
                r2.onError(r0)
                return
            L_0x003a:
                java.lang.Object r10 = r3.poll()
                java.util.Collection r10 = (java.util.Collection) r10
                if (r10 != 0) goto L_0x0044
                r11 = r4
                goto L_0x0045
            L_0x0044:
                r11 = 0
            L_0x0045:
                if (r9 == 0) goto L_0x004d
                if (r11 == 0) goto L_0x004d
                r2.onComplete()
                return
            L_0x004d:
                if (r11 == 0) goto L_0x0050
                goto L_0x0057
            L_0x0050:
                r2.onNext(r10)
                r8 = 1
                long r0 = r0 + r8
                goto L_0x0015
            L_0x0057:
                if (r8 != 0) goto L_0x0084
                boolean r6 = r12.cancelled
                if (r6 == 0) goto L_0x0061
                r3.clear()
                return
            L_0x0061:
                boolean r6 = r12.done
                if (r6 == 0) goto L_0x0084
                io.reactivex.internal.util.AtomicThrowable r6 = r12.errors
                java.lang.Object r6 = r6.get()
                if (r6 == 0) goto L_0x007a
                r3.clear()
                io.reactivex.internal.util.AtomicThrowable r0 = r12.errors
                java.lang.Throwable r0 = r0.terminate()
                r2.onError(r0)
                return
            L_0x007a:
                boolean r6 = r3.isEmpty()
                if (r6 == 0) goto L_0x0084
                r2.onComplete()
                return
            L_0x0084:
                r12.emitted = r0
                int r5 = -r5
                int r5 = r12.addAndGet(r5)
                if (r5 != 0) goto L_0x000f
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableBufferBoundary.a.a():void");
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableBufferBoundary$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static final class C0217a<Open> extends AtomicReference<Subscription> implements FlowableSubscriber<Open>, Disposable {
            private static final long serialVersionUID = -8498650778633225126L;
            final a<?, ?, Open, ?> parent;

            C0217a(a<?, ?, Open, ?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription subscription) {
                SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Open open) {
                this.parent.a((a<?, ?, Open, ?>) open);
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable th) {
                lazySet(SubscriptionHelper.CANCELLED);
                this.parent.a(this, th);
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                lazySet(SubscriptionHelper.CANCELLED);
                this.parent.a((C0217a) this);
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                SubscriptionHelper.cancel(this);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return get() == SubscriptionHelper.CANCELLED;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T, C extends Collection<? super T>> extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        private static final long serialVersionUID = -8498650778633225126L;
        final long index;
        final a<T, C, ?, ?> parent;

        b(a<T, C, ?, ?> aVar, long j) {
            this.parent = aVar;
            this.index = j;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Subscription subscription = get();
            if (subscription != SubscriptionHelper.CANCELLED) {
                lazySet(SubscriptionHelper.CANCELLED);
                subscription.cancel();
                this.parent.a(this, this.index);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (get() != SubscriptionHelper.CANCELLED) {
                lazySet(SubscriptionHelper.CANCELLED);
                this.parent.a(this, th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (get() != SubscriptionHelper.CANCELLED) {
                lazySet(SubscriptionHelper.CANCELLED);
                this.parent.a(this, this.index);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }
    }
}
