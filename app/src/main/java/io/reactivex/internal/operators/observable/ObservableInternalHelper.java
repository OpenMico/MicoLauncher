package io.reactivex.internal.operators.observable;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observables.ConnectableObservable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class ObservableInternalHelper {
    private ObservableInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class m<T, S> implements BiFunction<S, Emitter<T>, S> {
        final Consumer<Emitter<T>> a;

        m(Consumer<Emitter<T>> consumer) {
            this.a = consumer;
        }

        /* renamed from: a */
        public S apply(S s, Emitter<T> emitter) throws Exception {
            this.a.accept(emitter);
            return s;
        }
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> consumer) {
        return new m(consumer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class l<T, S> implements BiFunction<S, Emitter<T>, S> {
        final BiConsumer<S, Emitter<T>> a;

        l(BiConsumer<S, Emitter<T>> biConsumer) {
            this.a = biConsumer;
        }

        /* renamed from: a */
        public S apply(S s, Emitter<T> emitter) throws Exception {
            this.a.accept(s, emitter);
            return s;
        }
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> biConsumer) {
        return new l(biConsumer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class f<T, U> implements Function<T, ObservableSource<T>> {
        final Function<? super T, ? extends ObservableSource<U>> a;

        f(Function<? super T, ? extends ObservableSource<U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public ObservableSource<T> apply(T t) throws Exception {
            return new ObservableTake((ObservableSource) ObjectHelper.requireNonNull(this.a.apply(t), "The itemDelay returned a null ObservableSource"), 1L).map(Functions.justFunction(t)).defaultIfEmpty(t);
        }
    }

    public static <T, U> Function<T, ObservableSource<T>> itemDelay(Function<? super T, ? extends ObservableSource<U>> function) {
        return new f(function);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class i<T> implements Consumer<T> {
        final Observer<T> a;

        i(Observer<T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(T t) throws Exception {
            this.a.onNext(t);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class h<T> implements Consumer<Throwable> {
        final Observer<T> a;

        h(Observer<T> observer) {
            this.a = observer;
        }

        /* renamed from: a */
        public void accept(Throwable th) throws Exception {
            this.a.onError(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class g<T> implements Action {
        final Observer<T> a;

        g(Observer<T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.functions.Action
        public void run() throws Exception {
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
    /* loaded from: classes4.dex */
    public static final class d<U, R, T> implements Function<U, R> {
        private final BiFunction<? super T, ? super U, ? extends R> a;
        private final T b;

        d(BiFunction<? super T, ? super U, ? extends R> biFunction, T t) {
            this.a = biFunction;
            this.b = t;
        }

        @Override // io.reactivex.functions.Function
        public R apply(U u) throws Exception {
            return (R) this.a.apply((T) this.b, u);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class e<T, R, U> implements Function<T, ObservableSource<R>> {
        private final BiFunction<? super T, ? super U, ? extends R> a;
        private final Function<? super T, ? extends ObservableSource<? extends U>> b;

        e(BiFunction<? super T, ? super U, ? extends R> biFunction, Function<? super T, ? extends ObservableSource<? extends U>> function) {
            this.a = biFunction;
            this.b = function;
        }

        /* renamed from: a */
        public ObservableSource<R> apply(T t) throws Exception {
            return new ObservableMap((ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null ObservableSource"), new d(this.a, t));
        }
    }

    public static <T, U, R> Function<T, ObservableSource<R>> flatMapWithCombiner(Function<? super T, ? extends ObservableSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return new e(biFunction, function);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c<T, U> implements Function<T, ObservableSource<U>> {
        private final Function<? super T, ? extends Iterable<? extends U>> a;

        c(Function<? super T, ? extends Iterable<? extends U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public ObservableSource<U> apply(T t) throws Exception {
            return new ObservableFromIterable((Iterable) ObjectHelper.requireNonNull(this.a.apply(t), "The mapper returned a null Iterable"));
        }
    }

    public static <T, U> Function<T, ObservableSource<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        return new c(function);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable) {
        return new j(observable);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable, int i2) {
        return new a(observable, i2);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable, int i2, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return new b(observable, i2, j2, timeUnit, scheduler);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return new n(observable, j2, timeUnit, scheduler);
    }

    public static <T, R> Function<Observable<T>, ObservableSource<R>> replayFunction(Function<? super Observable<T>, ? extends ObservableSource<R>> function, Scheduler scheduler) {
        return new k(function, scheduler);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class o<T, R> implements Function<List<ObservableSource<? extends T>>, ObservableSource<? extends R>> {
        private final Function<? super Object[], ? extends R> a;

        o(Function<? super Object[], ? extends R> function) {
            this.a = function;
        }

        /* renamed from: a */
        public ObservableSource<? extends R> apply(List<ObservableSource<? extends T>> list) {
            return Observable.zipIterable(list, this.a, false, Observable.bufferSize());
        }
    }

    public static <T, R> Function<List<ObservableSource<? extends T>>, ObservableSource<? extends R>> zipIterable(Function<? super Object[], ? extends R> function) {
        return new o(function);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class j<T> implements Callable<ConnectableObservable<T>> {
        private final Observable<T> a;

        j(Observable<T> observable) {
            this.a = observable;
        }

        /* renamed from: a */
        public ConnectableObservable<T> call() {
            return this.a.replay();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> implements Callable<ConnectableObservable<T>> {
        private final Observable<T> a;
        private final int b;

        a(Observable<T> observable, int i) {
            this.a = observable;
            this.b = i;
        }

        /* renamed from: a */
        public ConnectableObservable<T> call() {
            return this.a.replay(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> implements Callable<ConnectableObservable<T>> {
        private final Observable<T> a;
        private final int b;
        private final long c;
        private final TimeUnit d;
        private final Scheduler e;

        b(Observable<T> observable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = observable;
            this.b = i;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
        }

        /* renamed from: a */
        public ConnectableObservable<T> call() {
            return this.a.replay(this.b, this.c, this.d, this.e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class n<T> implements Callable<ConnectableObservable<T>> {
        private final Observable<T> a;
        private final long b;
        private final TimeUnit c;
        private final Scheduler d;

        n(Observable<T> observable, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = observable;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        /* renamed from: a */
        public ConnectableObservable<T> call() {
            return this.a.replay(this.b, this.c, this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class k<T, R> implements Function<Observable<T>, ObservableSource<R>> {
        private final Function<? super Observable<T>, ? extends ObservableSource<R>> a;
        private final Scheduler b;

        k(Function<? super Observable<T>, ? extends ObservableSource<R>> function, Scheduler scheduler) {
            this.a = function;
            this.b = scheduler;
        }

        /* renamed from: a */
        public ObservableSource<R> apply(Observable<T> observable) throws Exception {
            return Observable.wrap((ObservableSource) ObjectHelper.requireNonNull(this.a.apply(observable), "The selector returned a null ObservableSource")).observeOn(this.b);
        }
    }
}
