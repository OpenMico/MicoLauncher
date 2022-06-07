package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.rxjava3.internal.subscribers.SubscriberResourceWrapper;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableReplay<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T> {
    static final Supplier f = new b();
    final Flowable<T> b;
    final AtomicReference<i<T>> c;
    final Supplier<? extends f<T>> d;
    final Publisher<T> e;

    /* loaded from: classes5.dex */
    interface f<T> {
        void a(c<T> cVar);

        void a(T t);

        void a(Throwable th);

        void b();
    }

    public static <U, R> Flowable<R> multicastSelector(Supplier<? extends ConnectableFlowable<U>> supplier, Function<? super Flowable<U>, ? extends Publisher<R>> function) {
        return new d(supplier, function);
    }

    public static <T> ConnectableFlowable<T> createFrom(Flowable<? extends T> flowable) {
        return a(flowable, f);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, int i2, boolean z) {
        if (i2 == Integer.MAX_VALUE) {
            return createFrom(flowable);
        }
        return a(flowable, new g(i2, z));
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        return create(flowable, j2, timeUnit, scheduler, Integer.MAX_VALUE, z);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, boolean z) {
        return a(flowable, new j(i2, j2, timeUnit, scheduler, z));
    }

    static <T> ConnectableFlowable<T> a(Flowable<T> flowable, Supplier<? extends f<T>> supplier) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable) new FlowableReplay(new h(atomicReference, supplier), flowable, atomicReference, supplier));
    }

    private FlowableReplay(Publisher<T> publisher, Flowable<T> flowable, AtomicReference<i<T>> atomicReference, Supplier<? extends f<T>> supplier) {
        this.e = publisher;
        this.b = flowable;
        this.c = atomicReference;
        this.d = supplier;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.HasUpstreamPublisher
    public Publisher<T> source() {
        return this.b;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.e.subscribe(subscriber);
    }

    @Override // io.reactivex.rxjava3.flowables.ConnectableFlowable
    public void reset() {
        i<T> iVar = this.c.get();
        if (iVar != null && iVar.isDisposed()) {
            this.c.compareAndSet(iVar, null);
        }
    }

    /* JADX WARN: Finally extract failed */
    @Override // io.reactivex.rxjava3.flowables.ConnectableFlowable
    public void connect(Consumer<? super Disposable> consumer) {
        i<T> iVar;
        while (true) {
            iVar = this.c.get();
            if (iVar != null && !iVar.isDisposed()) {
                break;
            }
            try {
                i<T> iVar2 = new i<>((f) this.d.get(), this.c);
                if (this.c.compareAndSet(iVar, iVar2)) {
                    iVar = iVar2;
                    break;
                }
            } finally {
                Exceptions.throwIfFatal(th);
                RuntimeException wrapOrThrow = ExceptionHelper.wrapOrThrow(th);
            }
        }
        boolean z = !iVar.shouldConnect.get() && iVar.shouldConnect.compareAndSet(false, true);
        try {
            consumer.accept(iVar);
            if (z) {
                this.b.subscribe((FlowableSubscriber) iVar);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (z) {
                iVar.shouldConnect.compareAndSet(true, false);
            }
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class i<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Disposable {
        static final c[] a = new c[0];
        static final c[] b = new c[0];
        private static final long serialVersionUID = 7224554242710036740L;
        final f<T> buffer;
        final AtomicReference<i<T>> current;
        boolean done;
        long requestedFromUpstream;
        final AtomicInteger management = new AtomicInteger();
        final AtomicReference<c<T>[]> subscribers = new AtomicReference<>(a);
        final AtomicBoolean shouldConnect = new AtomicBoolean();

        i(f<T> fVar, AtomicReference<i<T>> atomicReference) {
            this.buffer = fVar;
            this.current = atomicReference;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.subscribers.get() == b;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.subscribers.set(b);
            this.current.compareAndSet(this, null);
            SubscriptionHelper.cancel(this);
        }

        boolean a(c<T> cVar) {
            c<T>[] cVarArr;
            c<T>[] cVarArr2;
            do {
                cVarArr = this.subscribers.get();
                if (cVarArr == b) {
                    return false;
                }
                int length = cVarArr.length;
                cVarArr2 = new c[length + 1];
                System.arraycopy(cVarArr, 0, cVarArr2, 0, length);
                cVarArr2[length] = cVar;
            } while (!this.subscribers.compareAndSet(cVarArr, cVarArr2));
            return true;
        }

        void b(c<T> cVar) {
            c<T>[] cVarArr;
            c<T>[] cVarArr2;
            do {
                cVarArr = this.subscribers.get();
                int length = cVarArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (cVarArr[i2].equals(cVar)) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            cVarArr2 = a;
                        } else {
                            c<T>[] cVarArr3 = new c[length - 1];
                            System.arraycopy(cVarArr, 0, cVarArr3, 0, i);
                            System.arraycopy(cVarArr, i + 1, cVarArr3, i, (length - i) - 1);
                            cVarArr2 = cVarArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.subscribers.compareAndSet(cVarArr, cVarArr2));
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                a();
                for (c<T> cVar : this.subscribers.get()) {
                    this.buffer.a((c) cVar);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                this.buffer.a((f<T>) t);
                for (c<T> cVar : this.subscribers.get()) {
                    this.buffer.a((c) cVar);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.done) {
                this.done = true;
                this.buffer.a(th);
                for (c<T> cVar : this.subscribers.getAndSet(b)) {
                    this.buffer.a((c) cVar);
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
                for (c<T> cVar : this.subscribers.getAndSet(b)) {
                    this.buffer.a((c) cVar);
                }
            }
        }

        void a() {
            AtomicInteger atomicInteger = this.management;
            if (atomicInteger.getAndIncrement() == 0) {
                int i = 1;
                while (!isDisposed()) {
                    Subscription subscription = get();
                    if (subscription != null) {
                        long j = this.requestedFromUpstream;
                        long j2 = j;
                        for (c<T> cVar : this.subscribers.get()) {
                            j2 = Math.max(j2, cVar.totalRequested.get());
                        }
                        long j3 = j2 - j;
                        if (j3 != 0) {
                            this.requestedFromUpstream = j2;
                            subscription.request(j3);
                        }
                    }
                    i = atomicInteger.addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c<T> extends AtomicLong implements Disposable, Subscription {
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        boolean emitting;
        Object index;
        boolean missed;
        final i<T> parent;
        final AtomicLong totalRequested = new AtomicLong();

        c(i<T> iVar, Subscriber<? super T> subscriber) {
            this.parent = iVar;
            this.child = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.addCancel(this, j) != Long.MIN_VALUE) {
                BackpressureHelper.add(this.totalRequested, j);
                this.parent.a();
                this.parent.buffer.a((c) this);
            }
        }

        public long a(long j) {
            return BackpressureHelper.producedCancel(this, j);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == Long.MIN_VALUE;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
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

    /* loaded from: classes5.dex */
    static final class m<T> extends ArrayList<Object> implements f<T> {
        private static final long serialVersionUID = 7063189396499112664L;
        volatile int size;

        m(int i) {
            super(i);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.f
        public void a(T t) {
            add(NotificationLite.next(t));
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.f
        public void a(Throwable th) {
            add(NotificationLite.error(th));
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.f
        public void b() {
            add(NotificationLite.complete());
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.f
        public void a(c<T> cVar) {
            synchronized (cVar) {
                if (cVar.emitting) {
                    cVar.missed = true;
                    return;
                }
                cVar.emitting = true;
                Subscriber<? super T> subscriber = cVar.child;
                while (!cVar.isDisposed()) {
                    int i = this.size;
                    Integer num = (Integer) cVar.a();
                    int intValue = num != null ? num.intValue() : 0;
                    long j = cVar.get();
                    long j2 = j;
                    long j3 = 0;
                    while (j2 != 0 && intValue < i) {
                        Object obj = get(intValue);
                        try {
                            if (!NotificationLite.accept(obj, subscriber) && !cVar.isDisposed()) {
                                intValue++;
                                j2--;
                                j3++;
                            } else {
                                return;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            cVar.dispose();
                            if (NotificationLite.isError(obj) || NotificationLite.isComplete(obj)) {
                                RxJavaPlugins.onError(th);
                                return;
                            } else {
                                subscriber.onError(th);
                                return;
                            }
                        }
                    }
                    if (j3 != 0) {
                        cVar.index = Integer.valueOf(intValue);
                        if (j != Long.MAX_VALUE) {
                            cVar.a(j3);
                        }
                    }
                    synchronized (cVar) {
                        if (!cVar.missed) {
                            cVar.emitting = false;
                            return;
                        }
                        cVar.missed = false;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class e extends AtomicReference<e> {
        private static final long serialVersionUID = 245354315435971818L;
        final long index;
        final Object value;

        e(Object obj, long j) {
            this.value = obj;
            this.index = j;
        }
    }

    /* loaded from: classes5.dex */
    static abstract class a<T> extends AtomicReference<e> implements f<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        final boolean eagerTruncate;
        long index;
        int size;
        e tail;

        Object a(Object obj, boolean z) {
            return obj;
        }

        Object b(Object obj) {
            return obj;
        }

        abstract void d();

        a(boolean z) {
            this.eagerTruncate = z;
            e eVar = new e(null, 0L);
            this.tail = eVar;
            set(eVar);
        }

        final void a(e eVar) {
            this.tail.set(eVar);
            this.tail = eVar;
            this.size++;
        }

        final void a() {
            e eVar = get().get();
            if (eVar != null) {
                this.size--;
                b(eVar);
                return;
            }
            throw new IllegalStateException("Empty list!");
        }

        final void b(e eVar) {
            if (this.eagerTruncate) {
                e eVar2 = new e(null, eVar.index);
                eVar2.lazySet(eVar.get());
                eVar = eVar2;
            }
            set(eVar);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.f
        public final void a(T t) {
            Object a = a(NotificationLite.next(t), false);
            long j = this.index + 1;
            this.index = j;
            a(new e(a, j));
            d();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.f
        public final void a(Throwable th) {
            Object a = a(NotificationLite.error(th), true);
            long j = this.index + 1;
            this.index = j;
            a(new e(a, j));
            e();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.f
        public final void b() {
            Object a = a(NotificationLite.complete(), true);
            long j = this.index + 1;
            this.index = j;
            a(new e(a, j));
            e();
        }

        final void c() {
            e eVar = get();
            if (eVar.value != null) {
                e eVar2 = new e(null, 0L);
                eVar2.lazySet(eVar.get());
                set(eVar2);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.f
        public final void a(c<T> cVar) {
            long j;
            int i;
            synchronized (cVar) {
                if (cVar.emitting) {
                    cVar.missed = true;
                    return;
                }
                cVar.emitting = true;
                while (true) {
                    long j2 = cVar.get();
                    boolean z = j2 == Long.MAX_VALUE;
                    e eVar = (e) cVar.a();
                    if (eVar == null) {
                        eVar = f();
                        cVar.index = eVar;
                        BackpressureHelper.add(cVar.totalRequested, eVar.index);
                        j = 0;
                    } else {
                        j = 0;
                    }
                    while (true) {
                        i = (j2 > 0L ? 1 : (j2 == 0L ? 0 : -1));
                        if (i != 0) {
                            if (!cVar.isDisposed()) {
                                e eVar2 = eVar.get();
                                if (eVar2 == null) {
                                    break;
                                }
                                Object b = b(eVar2.value);
                                try {
                                    if (NotificationLite.accept(b, cVar.child)) {
                                        cVar.index = null;
                                        return;
                                    }
                                    j++;
                                    j2--;
                                    eVar = eVar2;
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    cVar.index = null;
                                    cVar.dispose();
                                    if (NotificationLite.isError(b) || NotificationLite.isComplete(b)) {
                                        RxJavaPlugins.onError(th);
                                        return;
                                    } else {
                                        cVar.child.onError(th);
                                        return;
                                    }
                                }
                            } else {
                                cVar.index = null;
                                return;
                            }
                        } else {
                            break;
                        }
                    }
                    if (i != 0 || !cVar.isDisposed()) {
                        if (j != 0) {
                            cVar.index = eVar;
                            if (!z) {
                                cVar.a(j);
                            }
                        }
                        synchronized (cVar) {
                            if (!cVar.missed) {
                                cVar.emitting = false;
                                return;
                            }
                            cVar.missed = false;
                        }
                    } else {
                        cVar.index = null;
                        return;
                    }
                }
            }
        }

        void e() {
            c();
        }

        e f() {
            return get();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class l<T> extends a<T> {
        private static final long serialVersionUID = -5898283885385201806L;
        final int limit;

        l(int i, boolean z) {
            super(z);
            this.limit = i;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.a
        void d() {
            if (this.size > this.limit) {
                a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class k<T> extends a<T> {
        private static final long serialVersionUID = 3457957419649567404L;
        final int limit;
        final long maxAge;
        final Scheduler scheduler;
        final TimeUnit unit;

        k(int i, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            super(z);
            this.scheduler = scheduler;
            this.limit = i;
            this.maxAge = j;
            this.unit = timeUnit;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.a
        Object a(Object obj, boolean z) {
            return new Timed(obj, z ? Long.MAX_VALUE : this.scheduler.now(this.unit), this.unit);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.a
        Object b(Object obj) {
            return ((Timed) obj).value();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.a
        void d() {
            long now = this.scheduler.now(this.unit) - this.maxAge;
            e eVar = (e) get();
            eVar = eVar.get();
            int i = 0;
            while (this.size > 1) {
                if (this.size <= this.limit) {
                    if (((Timed) eVar.value).time() > now) {
                        break;
                    }
                    i++;
                    this.size--;
                    eVar = eVar.get();
                } else {
                    i++;
                    this.size--;
                    eVar = eVar.get();
                }
            }
            if (i != 0) {
                b(eVar);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.a
        void e() {
            long now = this.scheduler.now(this.unit) - this.maxAge;
            e eVar = (e) get();
            eVar = eVar.get();
            int i = 0;
            while (this.size > 1 && ((Timed) eVar.value).time() <= now) {
                i++;
                this.size--;
                eVar = eVar.get();
            }
            if (i != 0) {
                b(eVar);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.a
        e f() {
            long now = this.scheduler.now(this.unit) - this.maxAge;
            e eVar = (e) get();
            e eVar2 = eVar.get();
            e eVar3 = eVar;
            while (eVar2 != null) {
                Timed timed = (Timed) eVar2.value;
                if (NotificationLite.isComplete(timed.value()) || NotificationLite.isError(timed.value()) || timed.time() > now) {
                    break;
                }
                eVar2 = eVar2.get();
                eVar3 = eVar2;
            }
            return eVar3;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class d<R, U> extends Flowable<R> {
        private final Supplier<? extends ConnectableFlowable<U>> b;
        private final Function<? super Flowable<U>, ? extends Publisher<R>> c;

        d(Supplier<? extends ConnectableFlowable<U>> supplier, Function<? super Flowable<U>, ? extends Publisher<R>> function) {
            this.b = supplier;
            this.c = function;
        }

        @Override // io.reactivex.rxjava3.core.Flowable
        protected void subscribeActual(Subscriber<? super R> subscriber) {
            try {
                ConnectableFlowable connectableFlowable = (ConnectableFlowable) ExceptionHelper.nullCheck(this.b.get(), "The connectableFactory returned a null ConnectableFlowable.");
                try {
                    Publisher publisher = (Publisher) ExceptionHelper.nullCheck(this.c.apply(connectableFlowable), "The selector returned a null Publisher.");
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

        /* loaded from: classes5.dex */
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
    /* loaded from: classes5.dex */
    public static final class g<T> implements Supplier<f<T>> {
        final int a;
        final boolean b;

        g(int i, boolean z) {
            this.a = i;
            this.b = z;
        }

        /* renamed from: a */
        public f<T> get() {
            return new l(this.a, this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class j<T> implements Supplier<f<T>> {
        final boolean a;
        private final int b;
        private final long c;
        private final TimeUnit d;
        private final Scheduler e;

        j(int i, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.b = i;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
            this.a = z;
        }

        /* renamed from: a */
        public f<T> get() {
            return new k(this.b, this.c, this.d, this.e, this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class h<T> implements Publisher<T> {
        private final AtomicReference<i<T>> a;
        private final Supplier<? extends f<T>> b;

        h(AtomicReference<i<T>> atomicReference, Supplier<? extends f<T>> supplier) {
            this.a = atomicReference;
            this.b = supplier;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            i<T> iVar;
            while (true) {
                iVar = this.a.get();
                if (iVar != null) {
                    break;
                }
                try {
                    i<T> iVar2 = new i<>((f) this.b.get(), this.a);
                    if (this.a.compareAndSet(null, iVar2)) {
                        iVar = iVar2;
                        break;
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                    return;
                }
            }
            c<T> cVar = new c<>(iVar, subscriber);
            subscriber.onSubscribe(cVar);
            iVar.a(cVar);
            if (cVar.isDisposed()) {
                iVar.b(cVar);
                return;
            }
            iVar.a();
            iVar.buffer.a((c) cVar);
        }
    }

    /* loaded from: classes5.dex */
    static final class b implements Supplier<Object> {
        b() {
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public Object get() {
            return new m(16);
        }
    }
}
