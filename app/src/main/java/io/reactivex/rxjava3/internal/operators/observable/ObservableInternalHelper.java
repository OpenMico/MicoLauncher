package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public final class ObservableInternalHelper {
    private ObservableInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class l<T, S> implements BiFunction<S, Emitter<T>, S> {
        final Consumer<Emitter<T>> a;

        l(Consumer<Emitter<T>> consumer) {
            this.a = consumer;
        }

        /* renamed from: a */
        public S apply(S s, Emitter<T> emitter) throws Throwable {
            this.a.accept(emitter);
            return s;
        }
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> consumer) {
        return new l(consumer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class k<T, S> implements BiFunction<S, Emitter<T>, S> {
        final BiConsumer<S, Emitter<T>> a;

        k(BiConsumer<S, Emitter<T>> biConsumer) {
            this.a = biConsumer;
        }

        /* renamed from: a */
        public S apply(S s, Emitter<T> emitter) throws Throwable {
            this.a.accept(s, emitter);
            return s;
        }
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> biConsumer) {
        return new k(biConsumer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class f<T, U> implements Function<T, ObservableSource<T>> {
        final Function<? super T, ? extends ObservableSource<U>> a;

        f(Function<? super T, ? extends ObservableSource<U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public ObservableSource<T> apply(T t) throws Throwable {
            return new ObservableTake((ObservableSource) Objects.requireNonNull(this.a.apply(t), "The itemDelay returned a null ObservableSource"), 1L).map(Functions.justFunction(t)).defaultIfEmpty(t);
        }
    }

    public static <T, U> Function<T, ObservableSource<T>> itemDelay(Function<? super T, ? extends ObservableSource<U>> function) {
        return new f(function);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class i<T> implements Consumer<T> {
        final Observer<T> a;

        i(Observer<T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(T t) {
            this.a.onNext(t);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class h<T> implements Consumer<Throwable> {
        final Observer<T> a;

        h(Observer<T> observer) {
            this.a = observer;
        }

        /* renamed from: a */
        public void accept(Throwable th) {
            this.a.onError(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class g<T> implements Action {
        final Observer<T> a;

        g(Observer<T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() {
            this.a.onComplete();
        }
    }

    public static <T> Consumer<T> observerOnNext(Observer<T> observer) {
        return new i(observer);
    }

    public static <T> Consumer<Throwable> observerOnError(Observer<T> observer) {
        return new h(observer);
    }

    public static <T> Action observerOnComplete(Observer<T> observer) {
        return new g(observer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class d<U, R, T> implements Function<U, R> {
        private final BiFunction<? super T, ? super U, ? extends R> a;
        private final T b;

        d(BiFunction<? super T, ? super U, ? extends R> biFunction, T t) {
            this.a = biFunction;
            this.b = t;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(U u) throws Throwable {
            return (R) this.a.apply((T) this.b, u);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class e<T, R, U> implements Function<T, ObservableSource<R>> {
        private final BiFunction<? super T, ? super U, ? extends R> a;
        private final Function<? super T, ? extends ObservableSource<? extends U>> b;

        e(BiFunction<? super T, ? super U, ? extends R> biFunction, Function<? super T, ? extends ObservableSource<? extends U>> function) {
            this.a = biFunction;
            this.b = function;
        }

        /* renamed from: a */
        public ObservableSource<R> apply(T t) throws Throwable {
            return new ObservableMap((ObservableSource) Objects.requireNonNull(this.b.apply(t), "The mapper returned a null ObservableSource"), new d(this.a, t));
        }
    }

    public static <T, U, R> Function<T, ObservableSource<R>> flatMapWithCombiner(Function<? super T, ? extends ObservableSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return new e(biFunction, function);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c<T, U> implements Function<T, ObservableSource<U>> {
        private final Function<? super T, ? extends Iterable<? extends U>> a;

        c(Function<? super T, ? extends Iterable<? extends U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public ObservableSource<U> apply(T t) throws Throwable {
            return new ObservableFromIterable((Iterable) Objects.requireNonNull(this.a.apply(t), "The mapper returned a null Iterable"));
        }
    }

    public static <T, U> Function<T, ObservableSource<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        return new c(function);
    }

    public static <T> Supplier<ConnectableObservable<T>> replaySupplier(Observable<T> observable) {
        return new j(observable);
    }

    public static <T> Supplier<ConnectableObservable<T>> replaySupplier(Observable<T> observable, int i2, boolean z) {
        return new a(observable, i2, z);
    }

    public static <T> Supplier<ConnectableObservable<T>> replaySupplier(Observable<T> observable, int i2, long j2, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        return new b(observable, i2, j2, timeUnit, scheduler, z);
    }

    public static <T> Supplier<ConnectableObservable<T>> replaySupplier(Observable<T> observable, long j2, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        return new m(observable, j2, timeUnit, scheduler, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class j<T> implements Supplier<ConnectableObservable<T>> {
        private final Observable<T> a;

        j(Observable<T> observable) {
            this.a = observable;
        }

        /* renamed from: a */
        public ConnectableObservable<T> get() {
            return this.a.replay();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> implements Supplier<ConnectableObservable<T>> {
        final Observable<T> a;
        final int b;
        final boolean c;

        a(Observable<T> observable, int i, boolean z) {
            this.a = observable;
            this.b = i;
            this.c = z;
        }

        /* renamed from: a */
        public ConnectableObservable<T> get() {
            return this.a.replay(this.b, this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b<T> implements Supplier<ConnectableObservable<T>> {
        final Observable<T> a;
        final int b;
        final long c;
        final TimeUnit d;
        final Scheduler e;
        final boolean f;

        b(Observable<T> observable, int i, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.a = observable;
            this.b = i;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
            this.f = z;
        }

        /* renamed from: a */
        public ConnectableObservable<T> get() {
            return this.a.replay(this.b, this.c, this.d, this.e, this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class m<T> implements Supplier<ConnectableObservable<T>> {
        final Observable<T> a;
        final long b;
        final TimeUnit c;
        final Scheduler d;
        final boolean e;

        m(Observable<T> observable, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.a = observable;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = z;
        }

        /* renamed from: a */
        public ConnectableObservable<T> get() {
            return this.a.replay(this.b, this.c, this.d, this.e);
        }
    }
}
