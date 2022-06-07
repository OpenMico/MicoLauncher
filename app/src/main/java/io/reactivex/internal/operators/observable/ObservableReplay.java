package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableReplay<T> extends ConnectableObservable<T> implements ResettableConnectable, HasUpstreamObservableSource<T> {
    static final b e = new o();
    final ObservableSource<T> a;
    final AtomicReference<j<T>> b;
    final b<T> c;
    final ObservableSource<T> d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface b<T> {
        h<T> a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface h<T> {
        void a(d<T> dVar);

        void a(T t);

        void a(Throwable th);

        void c();
    }

    public static <U, R> Observable<R> multicastSelector(Callable<? extends ConnectableObservable<U>> callable, Function<? super Observable<U>, ? extends ObservableSource<R>> function) {
        return RxJavaPlugins.onAssembly(new e(callable, function));
    }

    public static <T> ConnectableObservable<T> observeOn(ConnectableObservable<T> connectableObservable, Scheduler scheduler) {
        return RxJavaPlugins.onAssembly((ConnectableObservable) new g(connectableObservable, connectableObservable.observeOn(scheduler)));
    }

    public static <T> ConnectableObservable<T> createFrom(ObservableSource<? extends T> observableSource) {
        return a(observableSource, e);
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, int i2) {
        if (i2 == Integer.MAX_VALUE) {
            return createFrom(observableSource);
        }
        return a(observableSource, new i(i2));
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return create(observableSource, j2, timeUnit, scheduler, Integer.MAX_VALUE);
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2) {
        return a(observableSource, new l(i2, j2, timeUnit, scheduler));
    }

    static <T> ConnectableObservable<T> a(ObservableSource<T> observableSource, b<T> bVar) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableObservable) new ObservableReplay(new k(atomicReference, bVar), observableSource, atomicReference, bVar));
    }

    private ObservableReplay(ObservableSource<T> observableSource, ObservableSource<T> observableSource2, AtomicReference<j<T>> atomicReference, b<T> bVar) {
        this.d = observableSource;
        this.a = observableSource2;
        this.b = atomicReference;
        this.c = bVar;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamObservableSource
    public ObservableSource<T> source() {
        return this.a;
    }

    @Override // io.reactivex.internal.disposables.ResettableConnectable
    public void resetIf(Disposable disposable) {
        this.b.compareAndSet((j) disposable, null);
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.d.subscribe(observer);
    }

    @Override // io.reactivex.observables.ConnectableObservable
    public void connect(Consumer<? super Disposable> consumer) {
        j<T> jVar;
        while (true) {
            jVar = this.b.get();
            if (jVar != null && !jVar.isDisposed()) {
                break;
            }
            j<T> jVar2 = new j<>(this.c.a());
            if (this.b.compareAndSet(jVar, jVar2)) {
                jVar = jVar2;
                break;
            }
        }
        boolean z = !jVar.shouldConnect.get() && jVar.shouldConnect.compareAndSet(false, true);
        try {
            consumer.accept(jVar);
            if (z) {
                this.a.subscribe(jVar);
            }
        } catch (Throwable th) {
            if (z) {
                jVar.shouldConnect.compareAndSet(true, false);
            }
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    /* loaded from: classes4.dex */
    static final class j<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        static final d[] a = new d[0];
        static final d[] b = new d[0];
        private static final long serialVersionUID = -533785617179540163L;
        final h<T> buffer;
        boolean done;
        final AtomicReference<d[]> observers = new AtomicReference<>(a);
        final AtomicBoolean shouldConnect = new AtomicBoolean();

        j(h<T> hVar) {
            this.buffer = hVar;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.observers.get() == b;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.observers.set(b);
            DisposableHelper.dispose(this);
        }

        boolean a(d<T> dVar) {
            d[] dVarArr;
            d[] dVarArr2;
            do {
                dVarArr = this.observers.get();
                if (dVarArr == b) {
                    return false;
                }
                int length = dVarArr.length;
                dVarArr2 = new d[length + 1];
                System.arraycopy(dVarArr, 0, dVarArr2, 0, length);
                dVarArr2[length] = dVar;
            } while (!this.observers.compareAndSet(dVarArr, dVarArr2));
            return true;
        }

        void b(d<T> dVar) {
            d[] dVarArr;
            d[] dVarArr2;
            do {
                dVarArr = this.observers.get();
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
                            d[] dVarArr3 = new d[length - 1];
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
            } while (!this.observers.compareAndSet(dVarArr, dVarArr2));
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                a();
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.done) {
                this.buffer.a((h<T>) t);
                a();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.done) {
                this.done = true;
                this.buffer.a(th);
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.buffer.c();
                b();
            }
        }

        void a() {
            for (d<T> dVar : this.observers.get()) {
                this.buffer.a((d) dVar);
            }
        }

        void b() {
            for (d<T> dVar : this.observers.getAndSet(b)) {
                this.buffer.a((d) dVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class d<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 2728361546769921047L;
        volatile boolean cancelled;
        final Observer<? super T> child;
        Object index;
        final j<T> parent;

        d(j<T> jVar, Observer<? super T> observer) {
            this.parent = jVar;
            this.child = observer;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.parent.b(this);
                this.index = null;
            }
        }

        <U> U a() {
            return (U) this.index;
        }
    }

    /* loaded from: classes4.dex */
    static final class p<T> extends ArrayList<Object> implements h<T> {
        private static final long serialVersionUID = 7063189396499112664L;
        volatile int size;

        p(int i) {
            super(i);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public void a(T t) {
            add(NotificationLite.next(t));
            this.size++;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public void a(Throwable th) {
            add(NotificationLite.error(th));
            this.size++;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public void c() {
            add(NotificationLite.complete());
            this.size++;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public void a(d<T> dVar) {
            if (dVar.getAndIncrement() == 0) {
                Observer<? super T> observer = dVar.child;
                int i = 1;
                while (!dVar.isDisposed()) {
                    int i2 = this.size;
                    Integer num = (Integer) dVar.a();
                    int intValue = num != null ? num.intValue() : 0;
                    while (intValue < i2) {
                        if (!NotificationLite.accept(get(intValue), observer) && !dVar.isDisposed()) {
                            intValue++;
                        } else {
                            return;
                        }
                    }
                    dVar.index = Integer.valueOf(intValue);
                    i = dVar.addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class f extends AtomicReference<f> {
        private static final long serialVersionUID = 245354315435971818L;
        final Object value;

        f(Object obj) {
            this.value = obj;
        }
    }

    /* loaded from: classes4.dex */
    static abstract class a<T> extends AtomicReference<f> implements h<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        int size;
        f tail;

        Object b(Object obj) {
            return obj;
        }

        Object c(Object obj) {
            return obj;
        }

        abstract void d();

        a() {
            f fVar = new f(null);
            this.tail = fVar;
            set(fVar);
        }

        final void a(f fVar) {
            this.tail.set(fVar);
            this.tail = fVar;
            this.size++;
        }

        final void a() {
            this.size--;
            b(get().get());
        }

        final void b() {
            f fVar = get();
            if (fVar.value != null) {
                f fVar2 = new f(null);
                fVar2.lazySet(fVar.get());
                set(fVar2);
            }
        }

        final void b(f fVar) {
            set(fVar);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public final void a(T t) {
            a(new f(b(NotificationLite.next(t))));
            d();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public final void a(Throwable th) {
            a(new f(b(NotificationLite.error(th))));
            e();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public final void c() {
            a(new f(b(NotificationLite.complete())));
            e();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public final void a(d<T> dVar) {
            if (dVar.getAndIncrement() == 0) {
                int i = 1;
                do {
                    f fVar = (f) dVar.a();
                    if (fVar == null) {
                        fVar = f();
                        dVar.index = fVar;
                    }
                    while (!dVar.isDisposed()) {
                        f fVar2 = fVar.get();
                        if (fVar2 == null) {
                            dVar.index = fVar;
                            i = dVar.addAndGet(-i);
                        } else if (NotificationLite.accept(c(fVar2.value), dVar.child)) {
                            dVar.index = null;
                            return;
                        } else {
                            fVar = fVar2;
                        }
                    }
                    dVar.index = null;
                    return;
                } while (i != 0);
            }
        }

        void e() {
            b();
        }

        f f() {
            return get();
        }
    }

    /* loaded from: classes4.dex */
    static final class n<T> extends a<T> {
        private static final long serialVersionUID = -5898283885385201806L;
        final int limit;

        n(int i) {
            this.limit = i;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
        void d() {
            if (this.size > this.limit) {
                a();
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class m<T> extends a<T> {
        private static final long serialVersionUID = 3457957419649567404L;
        final int limit;
        final long maxAge;
        final Scheduler scheduler;
        final TimeUnit unit;

        m(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.scheduler = scheduler;
            this.limit = i;
            this.maxAge = j;
            this.unit = timeUnit;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
        Object b(Object obj) {
            return new Timed(obj, this.scheduler.now(this.unit), this.unit);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
        Object c(Object obj) {
            return ((Timed) obj).value();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
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
        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
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
                io.reactivex.internal.operators.observable.ObservableReplay$f r2 = (io.reactivex.internal.operators.observable.ObservableReplay.f) r2
                java.lang.Object r3 = r2.get()
                io.reactivex.internal.operators.observable.ObservableReplay$f r3 = (io.reactivex.internal.operators.observable.ObservableReplay.f) r3
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
                io.reactivex.internal.operators.observable.ObservableReplay$f r3 = (io.reactivex.internal.operators.observable.ObservableReplay.f) r3
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
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableReplay.m.e():void");
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
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

    /* loaded from: classes4.dex */
    static final class o implements b<Object> {
        o() {
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.b
        public h<Object> a() {
            return new p(16);
        }
    }

    /* loaded from: classes4.dex */
    static final class c<R> implements Consumer<Disposable> {
        private final ObserverResourceWrapper<R> a;

        c(ObserverResourceWrapper<R> observerResourceWrapper) {
            this.a = observerResourceWrapper;
        }

        /* renamed from: a */
        public void accept(Disposable disposable) {
            this.a.setResource(disposable);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class i<T> implements b<T> {
        private final int a;

        i(int i) {
            this.a = i;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.b
        public h<T> a() {
            return new n(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class l<T> implements b<T> {
        private final int a;
        private final long b;
        private final TimeUnit c;
        private final Scheduler d;

        l(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = i;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.b
        public h<T> a() {
            return new m(this.a, this.b, this.c, this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class k<T> implements ObservableSource<T> {
        private final AtomicReference<j<T>> a;
        private final b<T> b;

        k(AtomicReference<j<T>> atomicReference, b<T> bVar) {
            this.a = atomicReference;
            this.b = bVar;
        }

        @Override // io.reactivex.ObservableSource
        public void subscribe(Observer<? super T> observer) {
            j<T> jVar;
            while (true) {
                jVar = this.a.get();
                if (jVar != null) {
                    break;
                }
                j<T> jVar2 = new j<>(this.b.a());
                if (this.a.compareAndSet(null, jVar2)) {
                    jVar = jVar2;
                    break;
                }
            }
            d<T> dVar = new d<>(jVar, observer);
            observer.onSubscribe(dVar);
            jVar.a(dVar);
            if (dVar.isDisposed()) {
                jVar.b(dVar);
            } else {
                jVar.buffer.a((d) dVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class e<R, U> extends Observable<R> {
        private final Callable<? extends ConnectableObservable<U>> a;
        private final Function<? super Observable<U>, ? extends ObservableSource<R>> b;

        e(Callable<? extends ConnectableObservable<U>> callable, Function<? super Observable<U>, ? extends ObservableSource<R>> function) {
            this.a = callable;
            this.b = function;
        }

        @Override // io.reactivex.Observable
        protected void subscribeActual(Observer<? super R> observer) {
            try {
                ConnectableObservable connectableObservable = (ConnectableObservable) ObjectHelper.requireNonNull(this.a.call(), "The connectableFactory returned a null ConnectableObservable");
                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(connectableObservable), "The selector returned a null ObservableSource");
                ObserverResourceWrapper observerResourceWrapper = new ObserverResourceWrapper(observer);
                observableSource.subscribe(observerResourceWrapper);
                connectableObservable.connect(new c(observerResourceWrapper));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class g<T> extends ConnectableObservable<T> {
        private final ConnectableObservable<T> a;
        private final Observable<T> b;

        g(ConnectableObservable<T> connectableObservable, Observable<T> observable) {
            this.a = connectableObservable;
            this.b = observable;
        }

        @Override // io.reactivex.observables.ConnectableObservable
        public void connect(Consumer<? super Disposable> consumer) {
            this.a.connect(consumer);
        }

        @Override // io.reactivex.Observable
        protected void subscribeActual(Observer<? super T> observer) {
            this.b.subscribe(observer);
        }
    }
}
