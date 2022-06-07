package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableReplay<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T> {
    static final b e = new n();
    final ObservableSource<T> a;
    final AtomicReference<i<T>> b;
    final b<T> c;
    final ObservableSource<T> d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public interface b<T> {
        g<T> a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public interface g<T> {
        void a(d<T> dVar);

        void a(T t);

        void a(Throwable th);

        void c();
    }

    public static <U, R> Observable<R> multicastSelector(Supplier<? extends ConnectableObservable<U>> supplier, Function<? super Observable<U>, ? extends ObservableSource<R>> function) {
        return RxJavaPlugins.onAssembly(new e(supplier, function));
    }

    public static <T> ConnectableObservable<T> createFrom(ObservableSource<? extends T> observableSource) {
        return a(observableSource, e);
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, int i2, boolean z) {
        if (i2 == Integer.MAX_VALUE) {
            return createFrom(observableSource);
        }
        return a(observableSource, new h(i2, z));
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, long j2, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        return create(observableSource, j2, timeUnit, scheduler, Integer.MAX_VALUE, z);
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, boolean z) {
        return a(observableSource, new k(i2, j2, timeUnit, scheduler, z));
    }

    static <T> ConnectableObservable<T> a(ObservableSource<T> observableSource, b<T> bVar) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableObservable) new ObservableReplay(new j(atomicReference, bVar), observableSource, atomicReference, bVar));
    }

    private ObservableReplay(ObservableSource<T> observableSource, ObservableSource<T> observableSource2, AtomicReference<i<T>> atomicReference, b<T> bVar) {
        this.d = observableSource;
        this.a = observableSource2;
        this.b = atomicReference;
        this.c = bVar;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.HasUpstreamObservableSource
    public ObservableSource<T> source() {
        return this.a;
    }

    @Override // io.reactivex.rxjava3.observables.ConnectableObservable
    public void reset() {
        i<T> iVar = this.b.get();
        if (iVar != null && iVar.isDisposed()) {
            this.b.compareAndSet(iVar, null);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.d.subscribe(observer);
    }

    @Override // io.reactivex.rxjava3.observables.ConnectableObservable
    public void connect(Consumer<? super Disposable> consumer) {
        i<T> iVar;
        while (true) {
            iVar = this.b.get();
            if (iVar != null && !iVar.isDisposed()) {
                break;
            }
            i<T> iVar2 = new i<>(this.c.a(), this.b);
            if (this.b.compareAndSet(iVar, iVar2)) {
                iVar = iVar2;
                break;
            }
        }
        boolean z = !iVar.shouldConnect.get() && iVar.shouldConnect.compareAndSet(false, true);
        try {
            consumer.accept(iVar);
            if (z) {
                this.a.subscribe(iVar);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (z) {
                iVar.shouldConnect.compareAndSet(true, false);
            }
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    /* loaded from: classes5.dex */
    static final class i<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        static final d[] a = new d[0];
        static final d[] b = new d[0];
        private static final long serialVersionUID = -533785617179540163L;
        final g<T> buffer;
        final AtomicReference<i<T>> current;
        boolean done;
        final AtomicReference<d[]> observers = new AtomicReference<>(a);
        final AtomicBoolean shouldConnect = new AtomicBoolean();

        i(g<T> gVar, AtomicReference<i<T>> atomicReference) {
            this.buffer = gVar;
            this.current = atomicReference;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.observers.get() == b;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.observers.set(b);
            this.current.compareAndSet(this, null);
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

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                a();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.done) {
                this.buffer.a((g<T>) t);
                a();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (!this.done) {
                this.done = true;
                this.buffer.a(th);
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
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
    /* loaded from: classes5.dex */
    public static final class d<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 2728361546769921047L;
        volatile boolean cancelled;
        final Observer<? super T> child;
        Object index;
        final i<T> parent;

        d(i<T> iVar, Observer<? super T> observer) {
            this.parent = iVar;
            this.child = observer;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
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

    /* loaded from: classes5.dex */
    static final class o<T> extends ArrayList<Object> implements g<T> {
        private static final long serialVersionUID = 7063189396499112664L;
        volatile int size;

        o(int i) {
            super(i);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.g
        public void a(T t) {
            add(NotificationLite.next(t));
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.g
        public void a(Throwable th) {
            add(NotificationLite.error(th));
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.g
        public void c() {
            add(NotificationLite.complete());
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.g
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
    /* loaded from: classes5.dex */
    public static final class f extends AtomicReference<f> {
        private static final long serialVersionUID = 245354315435971818L;
        final Object value;

        f(Object obj) {
            this.value = obj;
        }
    }

    /* loaded from: classes5.dex */
    static abstract class a<T> extends AtomicReference<f> implements g<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        final boolean eagerTruncate;
        int size;
        f tail;

        Object b(Object obj) {
            return obj;
        }

        Object c(Object obj) {
            return obj;
        }

        abstract void d();

        a(boolean z) {
            this.eagerTruncate = z;
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
            if (this.eagerTruncate) {
                f fVar2 = new f(null);
                fVar2.lazySet(fVar.get());
                fVar = fVar2;
            }
            set(fVar);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.g
        public final void a(T t) {
            a(new f(b(NotificationLite.next(t))));
            d();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.g
        public final void a(Throwable th) {
            a(new f(b(NotificationLite.error(th))));
            e();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.g
        public final void c() {
            a(new f(b(NotificationLite.complete())));
            e();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.g
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

    /* loaded from: classes5.dex */
    static final class m<T> extends a<T> {
        private static final long serialVersionUID = -5898283885385201806L;
        final int limit;

        m(int i, boolean z) {
            super(z);
            this.limit = i;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.a
        void d() {
            if (this.size > this.limit) {
                a();
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class l<T> extends a<T> {
        private static final long serialVersionUID = 3457957419649567404L;
        final int limit;
        final long maxAge;
        final Scheduler scheduler;
        final TimeUnit unit;

        l(int i, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            super(z);
            this.scheduler = scheduler;
            this.limit = i;
            this.maxAge = j;
            this.unit = timeUnit;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.a
        Object b(Object obj) {
            return new Timed(obj, this.scheduler.now(this.unit), this.unit);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.a
        Object c(Object obj) {
            return ((Timed) obj).value();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.a
        void d() {
            long now = this.scheduler.now(this.unit) - this.maxAge;
            f fVar = (f) get();
            fVar = fVar.get();
            int i = 0;
            while (this.size > 1) {
                if (this.size <= this.limit) {
                    if (((Timed) fVar.value).time() > now) {
                        break;
                    }
                    i++;
                    this.size--;
                    fVar = fVar.get();
                } else {
                    i++;
                    this.size--;
                    fVar = fVar.get();
                }
            }
            if (i != 0) {
                b(fVar);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.a
        void e() {
            long now = this.scheduler.now(this.unit) - this.maxAge;
            f fVar = (f) get();
            fVar = fVar.get();
            int i = 0;
            while (this.size > 1 && ((Timed) fVar.value).time() <= now) {
                i++;
                this.size--;
                fVar = fVar.get();
            }
            if (i != 0) {
                b(fVar);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.a
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

    /* loaded from: classes5.dex */
    static final class n implements b<Object> {
        n() {
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.b
        public g<Object> a() {
            return new o(16);
        }
    }

    /* loaded from: classes5.dex */
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
    /* loaded from: classes5.dex */
    public static final class h<T> implements b<T> {
        final int a;
        final boolean b;

        h(int i, boolean z) {
            this.a = i;
            this.b = z;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.b
        public g<T> a() {
            return new m(this.a, this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class k<T> implements b<T> {
        final boolean a;
        private final int b;
        private final long c;
        private final TimeUnit d;
        private final Scheduler e;

        k(int i, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.b = i;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
            this.a = z;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.b
        public g<T> a() {
            return new l(this.b, this.c, this.d, this.e, this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class j<T> implements ObservableSource<T> {
        private final AtomicReference<i<T>> a;
        private final b<T> b;

        j(AtomicReference<i<T>> atomicReference, b<T> bVar) {
            this.a = atomicReference;
            this.b = bVar;
        }

        @Override // io.reactivex.rxjava3.core.ObservableSource
        public void subscribe(Observer<? super T> observer) {
            i<T> iVar;
            while (true) {
                iVar = this.a.get();
                if (iVar != null) {
                    break;
                }
                i<T> iVar2 = new i<>(this.b.a(), this.a);
                if (this.a.compareAndSet(null, iVar2)) {
                    iVar = iVar2;
                    break;
                }
            }
            d<T> dVar = new d<>(iVar, observer);
            observer.onSubscribe(dVar);
            iVar.a(dVar);
            if (dVar.isDisposed()) {
                iVar.b(dVar);
            } else {
                iVar.buffer.a((d) dVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class e<R, U> extends Observable<R> {
        private final Supplier<? extends ConnectableObservable<U>> a;
        private final Function<? super Observable<U>, ? extends ObservableSource<R>> b;

        e(Supplier<? extends ConnectableObservable<U>> supplier, Function<? super Observable<U>, ? extends ObservableSource<R>> function) {
            this.a = supplier;
            this.b = function;
        }

        @Override // io.reactivex.rxjava3.core.Observable
        protected void subscribeActual(Observer<? super R> observer) {
            try {
                ConnectableObservable connectableObservable = (ConnectableObservable) Objects.requireNonNull(this.a.get(), "The connectableFactory returned a null ConnectableObservable");
                ObservableSource observableSource = (ObservableSource) Objects.requireNonNull(this.b.apply(connectableObservable), "The selector returned a null ObservableSource");
                ObserverResourceWrapper observerResourceWrapper = new ObserverResourceWrapper(observer);
                observableSource.subscribe(observerResourceWrapper);
                connectableObservable.connect(new c(observerResourceWrapper));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        }
    }
}
