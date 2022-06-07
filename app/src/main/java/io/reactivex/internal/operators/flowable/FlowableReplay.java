package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.subscribers.SubscriberResourceWrapper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableReplay<T> extends ConnectableFlowable<T> implements ResettableConnectable, HasUpstreamPublisher<T> {
    static final Callable f = new c();
    final Flowable<T> b;
    final AtomicReference<j<T>> c;
    final Callable<? extends g<T>> d;
    final Publisher<T> e;

    /* loaded from: classes4.dex */
    interface g<T> {
        void a(d<T> dVar);

        void a(T t);

        void a(Throwable th);

        void b();
    }

    public static <U, R> Flowable<R> multicastSelector(Callable<? extends ConnectableFlowable<U>> callable, Function<? super Flowable<U>, ? extends Publisher<R>> function) {
        return new e(callable, function);
    }

    public static <T> ConnectableFlowable<T> observeOn(ConnectableFlowable<T> connectableFlowable, Scheduler scheduler) {
        return RxJavaPlugins.onAssembly((ConnectableFlowable) new b(connectableFlowable, connectableFlowable.observeOn(scheduler)));
    }

    public static <T> ConnectableFlowable<T> createFrom(Flowable<? extends T> flowable) {
        return a(flowable, f);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, int i2) {
        if (i2 == Integer.MAX_VALUE) {
            return createFrom(flowable);
        }
        return a(flowable, new h(i2));
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return create(flowable, j2, timeUnit, scheduler, Integer.MAX_VALUE);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2) {
        return a(flowable, new k(i2, j2, timeUnit, scheduler));
    }

    static <T> ConnectableFlowable<T> a(Flowable<T> flowable, Callable<? extends g<T>> callable) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable) new FlowableReplay(new i(atomicReference, callable), flowable, atomicReference, callable));
    }

    private FlowableReplay(Publisher<T> publisher, Flowable<T> flowable, AtomicReference<j<T>> atomicReference, Callable<? extends g<T>> callable) {
        this.e = publisher;
        this.b = flowable;
        this.c = atomicReference;
        this.d = callable;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamPublisher
    public Publisher<T> source() {
        return this.b;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.e.subscribe(subscriber);
    }

    @Override // io.reactivex.internal.disposables.ResettableConnectable
    public void resetIf(Disposable disposable) {
        this.c.compareAndSet((j) disposable, null);
    }

    @Override // io.reactivex.flowables.ConnectableFlowable
    public void connect(Consumer<? super Disposable> consumer) {
        j<T> jVar;
        while (true) {
            jVar = this.c.get();
            if (jVar != null && !jVar.isDisposed()) {
                break;
            }
            try {
                j<T> jVar2 = new j<>((g) this.d.call());
                if (this.c.compareAndSet(jVar, jVar2)) {
                    jVar = jVar2;
                    break;
                }
            } finally {
                Exceptions.throwIfFatal(th);
                RuntimeException wrapOrThrow = ExceptionHelper.wrapOrThrow(th);
            }
        }
        boolean z = !jVar.shouldConnect.get() && jVar.shouldConnect.compareAndSet(false, true);
        try {
            consumer.accept(jVar);
            if (z) {
                this.b.subscribe((FlowableSubscriber) jVar);
            }
        } catch (Throwable th) {
            if (z) {
                jVar.shouldConnect.compareAndSet(true, false);
            }
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class j<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Disposable {
        static final d[] a = new d[0];
        static final d[] b = new d[0];
        private static final long serialVersionUID = 7224554242710036740L;
        final g<T> buffer;
        boolean done;
        long maxChildRequested;
        long maxUpstreamRequested;
        final AtomicInteger management = new AtomicInteger();
        final AtomicReference<d<T>[]> subscribers = new AtomicReference<>(a);
        final AtomicBoolean shouldConnect = new AtomicBoolean();

        j(g<T> gVar) {
            this.buffer = gVar;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.subscribers.get() == b;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.subscribers.set(b);
            SubscriptionHelper.cancel(this);
        }

        boolean a(d<T> dVar) {
            d<T>[] dVarArr;
            d<T>[] dVarArr2;
            if (dVar != null) {
                do {
                    dVarArr = this.subscribers.get();
                    if (dVarArr == b) {
                        return false;
                    }
                    int length = dVarArr.length;
                    dVarArr2 = new d[length + 1];
                    System.arraycopy(dVarArr, 0, dVarArr2, 0, length);
                    dVarArr2[length] = dVar;
                } while (!this.subscribers.compareAndSet(dVarArr, dVarArr2));
                return true;
            }
            throw new NullPointerException();
        }

        void b(d<T> dVar) {
            d<T>[] dVarArr;
            d<T>[] dVarArr2;
            do {
                dVarArr = this.subscribers.get();
                int length = dVarArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (dVarArr[i2].equals(dVar)) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            dVarArr2 = a;
                        } else {
                            d<T>[] dVarArr3 = new d[length - 1];
                            System.arraycopy(dVarArr, 0, dVarArr3, 0, i);
                            System.arraycopy(dVarArr, i + 1, dVarArr3, i, (length - i) - 1);
                            dVarArr2 = dVarArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.subscribers.compareAndSet(dVarArr, dVarArr2));
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                a();
                for (d<T> dVar : this.subscribers.get()) {
                    this.buffer.a((d) dVar);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                this.buffer.a((g<T>) t);
                for (d<T> dVar : this.subscribers.get()) {
                    this.buffer.a((d) dVar);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.done) {
                this.done = true;
                this.buffer.a(th);
                for (d<T> dVar : this.subscribers.getAndSet(b)) {
                    this.buffer.a((d) dVar);
                }
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.buffer.b();
                for (d<T> dVar : this.subscribers.getAndSet(b)) {
                    this.buffer.a((d) dVar);
                }
            }
        }

        void a() {
            if (this.management.getAndIncrement() == 0) {
                int i = 1;
                while (!isDisposed()) {
                    d<T>[] dVarArr = this.subscribers.get();
                    long j = this.maxChildRequested;
                    long j2 = j;
                    for (d<T> dVar : dVarArr) {
                        j2 = Math.max(j2, dVar.totalRequested.get());
                    }
                    long j3 = this.maxUpstreamRequested;
                    Subscription subscription = get();
                    long j4 = j2 - j;
                    if (j4 != 0) {
                        this.maxChildRequested = j2;
                        if (subscription == null) {
                            long j5 = j3 + j4;
                            if (j5 < 0) {
                                j5 = Long.MAX_VALUE;
                            }
                            this.maxUpstreamRequested = j5;
                        } else if (j3 != 0) {
                            this.maxUpstreamRequested = 0L;
                            subscription.request(j3 + j4);
                        } else {
                            subscription.request(j4);
                        }
                    } else if (!(j3 == 0 || subscription == null)) {
                        this.maxUpstreamRequested = 0L;
                        subscription.request(j3);
                    }
                    i = this.management.addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class d<T> extends AtomicLong implements Disposable, Subscription {
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        boolean emitting;
        Object index;
        boolean missed;
        final j<T> parent;
        final AtomicLong totalRequested = new AtomicLong();

        d(j<T> jVar, Subscriber<? super T> subscriber) {
            this.parent = jVar;
            this.child = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.addCancel(this, j) != Long.MIN_VALUE) {
                BackpressureHelper.add(this.totalRequested, j);
                this.parent.a();
                this.parent.buffer.a((d) this);
            }
        }

        public long a(long j) {
            return BackpressureHelper.producedCancel(this, j);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == Long.MIN_VALUE;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.b(this);
                this.parent.a();
                this.index = null;
            }
        }

        <U> U a() {
            return (U) this.index;
        }
    }

    /* loaded from: classes4.dex */
    static final class n<T> extends ArrayList<Object> implements g<T> {
        private static final long serialVersionUID = 7063189396499112664L;
        volatile int size;

        n(int i) {
            super(i);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public void a(T t) {
            add(NotificationLite.next(t));
            this.size++;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public void a(Throwable th) {
            add(NotificationLite.error(th));
            this.size++;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public void b() {
            add(NotificationLite.complete());
            this.size++;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public void a(d<T> dVar) {
            synchronized (dVar) {
                if (dVar.emitting) {
                    dVar.missed = true;
                    return;
                }
                dVar.emitting = true;
                Subscriber<? super T> subscriber = dVar.child;
                while (!dVar.isDisposed()) {
                    int i = this.size;
                    Integer num = (Integer) dVar.a();
                    int intValue = num != null ? num.intValue() : 0;
                    long j = dVar.get();
                    long j2 = j;
                    long j3 = 0;
                    while (j2 != 0 && intValue < i) {
                        Object obj = get(intValue);
                        try {
                            if (!NotificationLite.accept(obj, subscriber) && !dVar.isDisposed()) {
                                intValue++;
                                j2--;
                                j3++;
                            } else {
                                return;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            dVar.dispose();
                            if (!NotificationLite.isError(obj) && !NotificationLite.isComplete(obj)) {
                                subscriber.onError(th);
                                return;
                            }
                            return;
                        }
                    }
                    if (j3 != 0) {
                        dVar.index = Integer.valueOf(intValue);
                        if (j != Long.MAX_VALUE) {
                            dVar.a(j3);
                        }
                    }
                    synchronized (dVar) {
                        if (!dVar.missed) {
                            dVar.emitting = false;
                            return;
                        }
                        dVar.missed = false;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class f extends AtomicReference<f> {
        private static final long serialVersionUID = 245354315435971818L;
        final long index;
        final Object value;

        f(Object obj, long j) {
            this.value = obj;
            this.index = j;
        }
    }

    /* loaded from: classes4.dex */
    static class a<T> extends AtomicReference<f> implements g<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        long index;
        int size;
        f tail;

        Object b(Object obj) {
            return obj;
        }

        Object c(Object obj) {
            return obj;
        }

        void d() {
        }

        a() {
            f fVar = new f(null, 0L);
            this.tail = fVar;
            set(fVar);
        }

        final void a(f fVar) {
            this.tail.set(fVar);
            this.tail = fVar;
            this.size++;
        }

        final void a() {
            f fVar = get().get();
            if (fVar != null) {
                this.size--;
                b(fVar);
                return;
            }
            throw new IllegalStateException("Empty list!");
        }

        final void b(f fVar) {
            set(fVar);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public final void a(T t) {
            Object b = b(NotificationLite.next(t));
            long j = this.index + 1;
            this.index = j;
            a(new f(b, j));
            d();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public final void a(Throwable th) {
            Object b = b(NotificationLite.error(th));
            long j = this.index + 1;
            this.index = j;
            a(new f(b, j));
            e();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public final void b() {
            Object b = b(NotificationLite.complete());
            long j = this.index + 1;
            this.index = j;
            a(new f(b, j));
            e();
        }

        final void c() {
            f fVar = get();
            if (fVar.value != null) {
                f fVar2 = new f(null, 0L);
                fVar2.lazySet(fVar.get());
                set(fVar2);
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public final void a(d<T> dVar) {
            long j;
            f fVar;
            synchronized (dVar) {
                if (dVar.emitting) {
                    dVar.missed = true;
                    return;
                }
                dVar.emitting = true;
                while (!dVar.isDisposed()) {
                    long j2 = dVar.get();
                    boolean z = j2 == Long.MAX_VALUE;
                    f fVar2 = (f) dVar.a();
                    if (fVar2 == null) {
                        fVar2 = f();
                        dVar.index = fVar2;
                        BackpressureHelper.add(dVar.totalRequested, fVar2.index);
                        j = 0;
                    } else {
                        j = 0;
                    }
                    while (j2 != 0 && (fVar = fVar2.get()) != null) {
                        Object c = c(fVar.value);
                        try {
                            if (NotificationLite.accept(c, dVar.child)) {
                                dVar.index = null;
                                return;
                            }
                            j++;
                            j2--;
                            if (dVar.isDisposed()) {
                                dVar.index = null;
                                return;
                            }
                            fVar2 = fVar;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            dVar.index = null;
                            dVar.dispose();
                            if (!NotificationLite.isError(c) && !NotificationLite.isComplete(c)) {
                                dVar.child.onError(th);
                                return;
                            }
                            return;
                        }
                    }
                    if (j != 0) {
                        dVar.index = fVar2;
                        if (!z) {
                            dVar.a(j);
                        }
                    }
                    synchronized (dVar) {
                        if (!dVar.missed) {
                            dVar.emitting = false;
                            return;
                        }
                        dVar.missed = false;
                    }
                }
                dVar.index = null;
            }
        }

        void e() {
            c();
        }

        f f() {
            return get();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class m<T> extends a<T> {
        private static final long serialVersionUID = -5898283885385201806L;
        final int limit;

        m(int i) {
            this.limit = i;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        void d() {
            if (this.size > this.limit) {
                a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class l<T> extends a<T> {
        private static final long serialVersionUID = 3457957419649567404L;
        final int limit;
        final long maxAge;
        final Scheduler scheduler;
        final TimeUnit unit;

        l(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.scheduler = scheduler;
            this.limit = i;
            this.maxAge = j;
            this.unit = timeUnit;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        Object b(Object obj) {
            return new Timed(obj, this.scheduler.now(this.unit), this.unit);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        Object c(Object obj) {
            return ((Timed) obj).value();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        void d() {
            long now = this.scheduler.now(this.unit) - this.maxAge;
            f fVar = (f) get();
            f fVar2 = fVar.get();
            int i = 0;
            f fVar3 = fVar;
            while (fVar2 != null) {
                if (this.size <= this.limit || this.size <= 1) {
                    if (((Timed) fVar2.value).time() > now) {
                        break;
                    }
                    i++;
                    this.size--;
                    fVar2 = fVar2.get();
                    fVar3 = fVar2;
                } else {
                    i++;
                    this.size--;
                    fVar2 = fVar2.get();
                    fVar3 = fVar2;
                }
            }
            if (i != 0) {
                b(fVar3);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0041, code lost:
            b(r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0044, code lost:
            return;
         */
        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void e() {
            /*
                r10 = this;
                io.reactivex.Scheduler r0 = r10.scheduler
                java.util.concurrent.TimeUnit r1 = r10.unit
                long r0 = r0.now(r1)
                long r2 = r10.maxAge
                long r0 = r0 - r2
                java.lang.Object r2 = r10.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$f r2 = (io.reactivex.internal.operators.flowable.FlowableReplay.f) r2
                java.lang.Object r3 = r2.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$f r3 = (io.reactivex.internal.operators.flowable.FlowableReplay.f) r3
                r4 = 0
                r9 = r3
                r3 = r2
                r2 = r9
            L_0x001b:
                if (r2 == 0) goto L_0x003f
                int r5 = r10.size
                r6 = 1
                if (r5 <= r6) goto L_0x003f
                java.lang.Object r5 = r2.value
                io.reactivex.schedulers.Timed r5 = (io.reactivex.schedulers.Timed) r5
                long r7 = r5.time()
                int r5 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                if (r5 > 0) goto L_0x003f
                int r4 = r4 + 1
                int r3 = r10.size
                int r3 = r3 - r6
                r10.size = r3
                java.lang.Object r3 = r2.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$f r3 = (io.reactivex.internal.operators.flowable.FlowableReplay.f) r3
                r9 = r3
                r3 = r2
                r2 = r9
                goto L_0x001b
            L_0x003f:
                if (r4 == 0) goto L_0x0044
                r10.b(r3)
            L_0x0044:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableReplay.l.e():void");
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        f f() {
            long now = this.scheduler.now(this.unit) - this.maxAge;
            f fVar = (f) get();
            f fVar2 = fVar.get();
            f fVar3 = fVar;
            while (fVar2 != null) {
                Timed timed = (Timed) fVar2.value;
                if (NotificationLite.isComplete(timed.value()) || NotificationLite.isError(timed.value()) || timed.time() > now) {
                    break;
                }
                fVar2 = fVar2.get();
                fVar3 = fVar2;
            }
            return fVar3;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class e<R, U> extends Flowable<R> {
        private final Callable<? extends ConnectableFlowable<U>> b;
        private final Function<? super Flowable<U>, ? extends Publisher<R>> c;

        e(Callable<? extends ConnectableFlowable<U>> callable, Function<? super Flowable<U>, ? extends Publisher<R>> function) {
            this.b = callable;
            this.c = function;
        }

        @Override // io.reactivex.Flowable
        protected void subscribeActual(Subscriber<? super R> subscriber) {
            try {
                ConnectableFlowable connectableFlowable = (ConnectableFlowable) ObjectHelper.requireNonNull(this.b.call(), "The connectableFactory returned null");
                try {
                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.c.apply(connectableFlowable), "The selector returned a null Publisher");
                    SubscriberResourceWrapper subscriberResourceWrapper = new SubscriberResourceWrapper(subscriber);
                    publisher.subscribe(subscriberResourceWrapper);
                    connectableFlowable.connect(new a(subscriberResourceWrapper));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, subscriber);
            }
        }

        /* loaded from: classes4.dex */
        final class a implements Consumer<Disposable> {
            private final SubscriberResourceWrapper<R> b;

            a(SubscriberResourceWrapper<R> subscriberResourceWrapper) {
                this.b = subscriberResourceWrapper;
            }

            /* renamed from: a */
            public void accept(Disposable disposable) {
                this.b.setResource(disposable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> extends ConnectableFlowable<T> {
        private final ConnectableFlowable<T> b;
        private final Flowable<T> c;

        b(ConnectableFlowable<T> connectableFlowable, Flowable<T> flowable) {
            this.b = connectableFlowable;
            this.c = flowable;
        }

        @Override // io.reactivex.flowables.ConnectableFlowable
        public void connect(Consumer<? super Disposable> consumer) {
            this.b.connect(consumer);
        }

        @Override // io.reactivex.Flowable
        protected void subscribeActual(Subscriber<? super T> subscriber) {
            this.c.subscribe(subscriber);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class h<T> implements Callable<g<T>> {
        private final int a;

        h(int i) {
            this.a = i;
        }

        /* renamed from: a */
        public g<T> call() {
            return new m(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class k<T> implements Callable<g<T>> {
        private final int a;
        private final long b;
        private final TimeUnit c;
        private final Scheduler d;

        k(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = i;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        /* renamed from: a */
        public g<T> call() {
            return new l(this.a, this.b, this.c, this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class i<T> implements Publisher<T> {
        private final AtomicReference<j<T>> a;
        private final Callable<? extends g<T>> b;

        i(AtomicReference<j<T>> atomicReference, Callable<? extends g<T>> callable) {
            this.a = atomicReference;
            this.b = callable;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            j<T> jVar;
            while (true) {
                jVar = this.a.get();
                if (jVar != null) {
                    break;
                }
                try {
                    j<T> jVar2 = new j<>((g) this.b.call());
                    if (this.a.compareAndSet(null, jVar2)) {
                        jVar = jVar2;
                        break;
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                    return;
                }
            }
            d<T> dVar = new d<>(jVar, subscriber);
            subscriber.onSubscribe(dVar);
            jVar.a(dVar);
            if (dVar.isDisposed()) {
                jVar.b(dVar);
                return;
            }
            jVar.a();
            jVar.buffer.a((d) dVar);
        }
    }

    /* loaded from: classes4.dex */
    static final class c implements Callable<Object> {
        c() {
        }

        @Override // java.util.concurrent.Callable
        public Object call() {
            return new n(16);
        }
    }
}
