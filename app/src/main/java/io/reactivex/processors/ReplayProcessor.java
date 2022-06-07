package io.reactivex.processors;

import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class ReplayProcessor<T> extends FlowableProcessor<T> {
    final b<T> b;
    boolean c;
    final AtomicReference<c<T>[]> d = new AtomicReference<>(e);
    private static final Object[] g = new Object[0];
    static final c[] e = new c[0];
    static final c[] f = new c[0];

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface b<T> {
        void a();

        void a(c<T> cVar);

        void a(T t);

        void a(Throwable th);

        T[] a(T[] tArr);

        int b();

        @Nullable
        T c();

        boolean d();

        Throwable e();

        void f();
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> create() {
        return new ReplayProcessor<>(new g(16));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> create(int i) {
        return new ReplayProcessor<>(new g(i));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> createWithSize(int i) {
        return new ReplayProcessor<>(new e(i));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> createWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return new ReplayProcessor<>(new d(Integer.MAX_VALUE, j, timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> createWithTimeAndSize(long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        return new ReplayProcessor<>(new d(i, j, timeUnit, scheduler));
    }

    ReplayProcessor(b<T> bVar) {
        this.b = bVar;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        c<T> cVar = new c<>(subscriber, this);
        subscriber.onSubscribe(cVar);
        if (!a(cVar) || !cVar.cancelled) {
            this.b.a((c) cVar);
        } else {
            b(cVar);
        }
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (this.c) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (!this.c) {
            b<T> bVar = this.b;
            bVar.a((b<T>) t);
            for (c<T> cVar : this.d.get()) {
                bVar.a((c) cVar);
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.c) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.c = true;
        b<T> bVar = this.b;
        bVar.a(th);
        for (c<T> cVar : this.d.getAndSet(f)) {
            bVar.a((c) cVar);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!this.c) {
            this.c = true;
            b<T> bVar = this.b;
            bVar.a();
            for (c<T> cVar : this.d.getAndSet(f)) {
                bVar.a((c) cVar);
            }
        }
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasSubscribers() {
        return this.d.get().length != 0;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    @Nullable
    public Throwable getThrowable() {
        b<T> bVar = this.b;
        if (bVar.d()) {
            return bVar.e();
        }
        return null;
    }

    public void cleanupBuffer() {
        this.b.f();
    }

    public T getValue() {
        return this.b.c();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object[] getValues() {
        Object[] values = getValues(g);
        return values == g ? new Object[0] : values;
    }

    public T[] getValues(T[] tArr) {
        return this.b.a((Object[]) tArr);
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasComplete() {
        b<T> bVar = this.b;
        return bVar.d() && bVar.e() == null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasThrowable() {
        b<T> bVar = this.b;
        return bVar.d() && bVar.e() != null;
    }

    public boolean hasValue() {
        return this.b.b() != 0;
    }

    boolean a(c<T> cVar) {
        c<T>[] cVarArr;
        c<T>[] cVarArr2;
        do {
            cVarArr = this.d.get();
            if (cVarArr == f) {
                return false;
            }
            int length = cVarArr.length;
            cVarArr2 = new c[length + 1];
            System.arraycopy(cVarArr, 0, cVarArr2, 0, length);
            cVarArr2[length] = cVar;
        } while (!this.d.compareAndSet(cVarArr, cVarArr2));
        return true;
    }

    void b(c<T> cVar) {
        c<T>[] cVarArr;
        c<T>[] cVarArr2;
        do {
            cVarArr = this.d.get();
            if (cVarArr != f && cVarArr != e) {
                int length = cVarArr.length;
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (cVarArr[i2] == cVar) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        cVarArr2 = e;
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
        } while (!this.d.compareAndSet(cVarArr, cVarArr2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 466549804534799122L;
        volatile boolean cancelled;
        final Subscriber<? super T> downstream;
        long emitted;
        Object index;
        final AtomicLong requested = new AtomicLong();
        final ReplayProcessor<T> state;

        c(Subscriber<? super T> subscriber, ReplayProcessor<T> replayProcessor) {
            this.downstream = subscriber;
            this.state = replayProcessor;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                this.state.b.a((c) this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.state.b(this);
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class g<T> implements b<T> {
        final List<T> a;
        Throwable b;
        volatile boolean c;
        volatile int d;

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void f() {
        }

        g(int i) {
            this.a = new ArrayList(ObjectHelper.verifyPositive(i, "capacityHint"));
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(T t) {
            this.a.add(t);
            this.d++;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(Throwable th) {
            this.b = th;
            this.c = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a() {
            this.c = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        @Nullable
        public T c() {
            int i = this.d;
            if (i == 0) {
                return null;
            }
            return this.a.get(i - 1);
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public T[] a(T[] tArr) {
            int i = this.d;
            if (i == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
            List<T> list = this.a;
            if (tArr.length < i) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i));
            }
            for (int i2 = 0; i2 < i; i2++) {
                tArr[i2] = list.get(i2);
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            return tArr;
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x005b, code lost:
            if (r9 != 0) goto L_0x007c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x005f, code lost:
            if (r14.cancelled == false) goto L_0x0064;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0061, code lost:
            r14.index = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0063, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0064, code lost:
            r7 = r13.c;
            r8 = r13.d;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0068, code lost:
            if (r7 == false) goto L_0x007c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x006a, code lost:
            if (r3 != r8) goto L_0x007c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x006c, code lost:
            r14.index = null;
            r14.cancelled = true;
            r14 = r13.b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0072, code lost:
            if (r14 != null) goto L_0x0078;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0074, code lost:
            r1.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0078, code lost:
            r1.onError(r14);
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x007b, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x007c, code lost:
            r14.index = java.lang.Integer.valueOf(r3);
            r14.emitted = r4;
            r6 = r14.addAndGet(-r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:?, code lost:
            return;
         */
        @Override // io.reactivex.processors.ReplayProcessor.b
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a(io.reactivex.processors.ReplayProcessor.c<T> r14) {
            /*
                r13 = this;
                int r0 = r14.getAndIncrement()
                if (r0 == 0) goto L_0x0007
                return
            L_0x0007:
                java.util.List<T> r0 = r13.a
                org.reactivestreams.Subscriber<? super T> r1 = r14.downstream
                java.lang.Object r2 = r14.index
                java.lang.Integer r2 = (java.lang.Integer) r2
                r3 = 0
                if (r2 == 0) goto L_0x0017
                int r3 = r2.intValue()
                goto L_0x001d
            L_0x0017:
                java.lang.Integer r2 = java.lang.Integer.valueOf(r3)
                r14.index = r2
            L_0x001d:
                long r4 = r14.emitted
                r2 = 1
                r6 = r2
            L_0x0021:
                java.util.concurrent.atomic.AtomicLong r7 = r14.requested
                long r7 = r7.get()
            L_0x0027:
                int r9 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
                r10 = 0
                if (r9 == 0) goto L_0x005b
                boolean r11 = r14.cancelled
                if (r11 == 0) goto L_0x0033
                r14.index = r10
                return
            L_0x0033:
                boolean r11 = r13.c
                int r12 = r13.d
                if (r11 == 0) goto L_0x004b
                if (r3 != r12) goto L_0x004b
                r14.index = r10
                r14.cancelled = r2
                java.lang.Throwable r14 = r13.b
                if (r14 != 0) goto L_0x0047
                r1.onComplete()
                goto L_0x004a
            L_0x0047:
                r1.onError(r14)
            L_0x004a:
                return
            L_0x004b:
                if (r3 != r12) goto L_0x004e
                goto L_0x005b
            L_0x004e:
                java.lang.Object r9 = r0.get(r3)
                r1.onNext(r9)
                int r3 = r3 + 1
                r9 = 1
                long r4 = r4 + r9
                goto L_0x0027
            L_0x005b:
                if (r9 != 0) goto L_0x007c
                boolean r7 = r14.cancelled
                if (r7 == 0) goto L_0x0064
                r14.index = r10
                return
            L_0x0064:
                boolean r7 = r13.c
                int r8 = r13.d
                if (r7 == 0) goto L_0x007c
                if (r3 != r8) goto L_0x007c
                r14.index = r10
                r14.cancelled = r2
                java.lang.Throwable r14 = r13.b
                if (r14 != 0) goto L_0x0078
                r1.onComplete()
                goto L_0x007b
            L_0x0078:
                r1.onError(r14)
            L_0x007b:
                return
            L_0x007c:
                java.lang.Integer r7 = java.lang.Integer.valueOf(r3)
                r14.index = r7
                r14.emitted = r4
                int r6 = -r6
                int r6 = r14.addAndGet(r6)
                if (r6 != 0) goto L_0x0021
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.processors.ReplayProcessor.g.a(io.reactivex.processors.ReplayProcessor$c):void");
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public int b() {
            return this.d;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public boolean d() {
            return this.c;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public Throwable e() {
            return this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicReference<a<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final T value;

        a(T t) {
            this.value = t;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class f<T> extends AtomicReference<f<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final long time;
        final T value;

        f(T t, long j) {
            this.value = t;
            this.time = j;
        }
    }

    /* loaded from: classes4.dex */
    static final class e<T> implements b<T> {
        final int a;
        int b;
        volatile a<T> c;
        a<T> d;
        Throwable e;
        volatile boolean f;

        e(int i) {
            this.a = ObjectHelper.verifyPositive(i, "maxSize");
            a<T> aVar = new a<>(null);
            this.d = aVar;
            this.c = aVar;
        }

        void g() {
            int i = this.b;
            if (i > this.a) {
                this.b = i - 1;
                this.c = this.c.get();
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(T t) {
            a<T> aVar = new a<>(t);
            a<T> aVar2 = this.d;
            this.d = aVar;
            this.b++;
            aVar2.set(aVar);
            g();
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(Throwable th) {
            this.e = th;
            f();
            this.f = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a() {
            f();
            this.f = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void f() {
            if (this.c.value != null) {
                a<T> aVar = new a<>(null);
                aVar.lazySet(this.c.get());
                this.c = aVar;
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public boolean d() {
            return this.f;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public Throwable e() {
            return this.e;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public T c() {
            a<T> aVar = this.c;
            while (true) {
                a<T> aVar2 = aVar.get();
                if (aVar2 == null) {
                    return aVar.value;
                }
                aVar = aVar2;
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public T[] a(T[] tArr) {
            a<T> aVar = this.c;
            a<T> aVar2 = aVar;
            int i = 0;
            while (true) {
                aVar2 = aVar2.get();
                if (aVar2 == null) {
                    break;
                }
                i++;
            }
            if (tArr.length < i) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i));
            }
            for (int i2 = 0; i2 < i; i2++) {
                aVar = aVar.get();
                tArr[i2] = aVar.value;
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(c<T> cVar) {
            int i;
            if (cVar.getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = cVar.downstream;
                a<T> aVar = (a) cVar.index;
                if (aVar == null) {
                    aVar = this.c;
                }
                long j = cVar.emitted;
                int i2 = 1;
                do {
                    long j2 = cVar.requested.get();
                    while (true) {
                        i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
                        if (i == 0) {
                            break;
                        } else if (cVar.cancelled) {
                            cVar.index = null;
                            return;
                        } else {
                            boolean z = this.f;
                            a<T> aVar2 = aVar.get();
                            boolean z2 = aVar2 == null;
                            if (z && z2) {
                                cVar.index = null;
                                cVar.cancelled = true;
                                Throwable th = this.e;
                                if (th == null) {
                                    subscriber.onComplete();
                                    return;
                                } else {
                                    subscriber.onError(th);
                                    return;
                                }
                            } else if (z2) {
                                break;
                            } else {
                                subscriber.onNext((T) aVar2.value);
                                j++;
                                aVar = aVar2;
                            }
                        }
                    }
                    if (i == 0) {
                        if (cVar.cancelled) {
                            cVar.index = null;
                            return;
                        } else if (this.f && aVar.get() == null) {
                            cVar.index = null;
                            cVar.cancelled = true;
                            Throwable th2 = this.e;
                            if (th2 == null) {
                                subscriber.onComplete();
                                return;
                            } else {
                                subscriber.onError(th2);
                                return;
                            }
                        }
                    }
                    cVar.index = aVar;
                    cVar.emitted = j;
                    i2 = cVar.addAndGet(-i2);
                } while (i2 != 0);
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public int b() {
            a<T> aVar = this.c;
            int i = 0;
            while (i != Integer.MAX_VALUE && (aVar = aVar.get()) != null) {
                i++;
            }
            return i;
        }
    }

    /* loaded from: classes4.dex */
    static final class d<T> implements b<T> {
        final int a;
        final long b;
        final TimeUnit c;
        final Scheduler d;
        int e;
        volatile f<T> f;
        f<T> g;
        Throwable h;
        volatile boolean i;

        d(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = ObjectHelper.verifyPositive(i, "maxSize");
            this.b = ObjectHelper.verifyPositive(j, "maxAge");
            this.c = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
            this.d = (Scheduler) ObjectHelper.requireNonNull(scheduler, "scheduler is null");
            f<T> fVar = new f<>(null, 0L);
            this.g = fVar;
            this.f = fVar;
        }

        void g() {
            int i = this.e;
            if (i > this.a) {
                this.e = i - 1;
                this.f = this.f.get();
            }
            long now = this.d.now(this.c) - this.b;
            f<T> fVar = this.f;
            while (this.e > 1) {
                f<T> fVar2 = fVar.get();
                if (fVar2 == null) {
                    this.f = fVar;
                    return;
                } else if (fVar2.time > now) {
                    this.f = fVar;
                    return;
                } else {
                    this.e--;
                    fVar = fVar2;
                }
            }
            this.f = fVar;
        }

        void h() {
            long now = this.d.now(this.c) - this.b;
            f<T> fVar = this.f;
            while (true) {
                f<T> fVar2 = fVar.get();
                if (fVar2 == null) {
                    if (fVar.value != null) {
                        this.f = new f<>(null, 0L);
                        return;
                    } else {
                        this.f = fVar;
                        return;
                    }
                } else if (fVar2.time <= now) {
                    fVar = fVar2;
                } else if (fVar.value != null) {
                    f<T> fVar3 = new f<>(null, 0L);
                    fVar3.lazySet(fVar.get());
                    this.f = fVar3;
                    return;
                } else {
                    this.f = fVar;
                    return;
                }
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void f() {
            if (this.f.value != null) {
                f<T> fVar = new f<>(null, 0L);
                fVar.lazySet(this.f.get());
                this.f = fVar;
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(T t) {
            f<T> fVar = new f<>(t, this.d.now(this.c));
            f<T> fVar2 = this.g;
            this.g = fVar;
            this.e++;
            fVar2.set(fVar);
            g();
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(Throwable th) {
            h();
            this.h = th;
            this.i = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a() {
            h();
            this.i = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        @Nullable
        public T c() {
            f<T> fVar = this.f;
            while (true) {
                f<T> fVar2 = fVar.get();
                if (fVar2 == null) {
                    break;
                }
                fVar = fVar2;
            }
            if (fVar.time < this.d.now(this.c) - this.b) {
                return null;
            }
            return fVar.value;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public T[] a(T[] tArr) {
            f<T> i = i();
            int a = a((f) i);
            if (a != 0) {
                if (tArr.length < a) {
                    tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), a));
                }
                for (int i2 = 0; i2 != a; i2++) {
                    i = i.get();
                    tArr[i2] = i.value;
                }
                if (tArr.length > a) {
                    tArr[a] = null;
                }
            } else if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        f<T> i() {
            f<T> fVar = this.f;
            long now = this.d.now(this.c) - this.b;
            f<T> fVar2 = fVar.get();
            f<T> fVar3 = fVar;
            while (fVar2 != null && fVar2.time <= now) {
                fVar2 = fVar2.get();
                fVar3 = fVar2;
            }
            return fVar3;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(c<T> cVar) {
            int i;
            if (cVar.getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = cVar.downstream;
                f<T> fVar = (f) cVar.index;
                if (fVar == null) {
                    fVar = i();
                }
                long j = cVar.emitted;
                int i2 = 1;
                do {
                    long j2 = cVar.requested.get();
                    while (true) {
                        i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
                        if (i == 0) {
                            break;
                        } else if (cVar.cancelled) {
                            cVar.index = null;
                            return;
                        } else {
                            boolean z = this.i;
                            f<T> fVar2 = fVar.get();
                            boolean z2 = fVar2 == null;
                            if (z && z2) {
                                cVar.index = null;
                                cVar.cancelled = true;
                                Throwable th = this.h;
                                if (th == null) {
                                    subscriber.onComplete();
                                    return;
                                } else {
                                    subscriber.onError(th);
                                    return;
                                }
                            } else if (z2) {
                                break;
                            } else {
                                subscriber.onNext((T) fVar2.value);
                                j++;
                                fVar = fVar2;
                            }
                        }
                    }
                    if (i == 0) {
                        if (cVar.cancelled) {
                            cVar.index = null;
                            return;
                        } else if (this.i && fVar.get() == null) {
                            cVar.index = null;
                            cVar.cancelled = true;
                            Throwable th2 = this.h;
                            if (th2 == null) {
                                subscriber.onComplete();
                                return;
                            } else {
                                subscriber.onError(th2);
                                return;
                            }
                        }
                    }
                    cVar.index = fVar;
                    cVar.emitted = j;
                    i2 = cVar.addAndGet(-i2);
                } while (i2 != 0);
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public int b() {
            return a((f) i());
        }

        int a(f<T> fVar) {
            int i = 0;
            while (i != Integer.MAX_VALUE && (fVar = fVar.get()) != null) {
                i++;
            }
            return i;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public Throwable e() {
            return this.h;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public boolean d() {
            return this.i;
        }
    }
}
