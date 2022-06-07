package io.reactivex.internal.operators.flowable;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableInternalHelper {
    private FlowableInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class j<T, S> implements BiFunction<S, Emitter<T>, S> {
        final Consumer<Emitter<T>> a;

        j(Consumer<Emitter<T>> consumer) {
            this.a = consumer;
        }

        /* renamed from: a */
        public S apply(S s, Emitter<T> emitter) throws Exception {
            this.a.accept(emitter);
            return s;
        }
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> consumer) {
        return new j(consumer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class i<T, S> implements BiFunction<S, Emitter<T>, S> {
        final BiConsumer<S, Emitter<T>> a;

        i(BiConsumer<S, Emitter<T>> biConsumer) {
            this.a = biConsumer;
        }

        /* renamed from: a */
        public S apply(S s, Emitter<T> emitter) throws Exception {
            this.a.accept(s, emitter);
            return s;
        }
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> biConsumer) {
        return new i(biConsumer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class f<T, U> implements Function<T, Publisher<T>> {
        final Function<? super T, ? extends Publisher<U>> a;

        f(Function<? super T, ? extends Publisher<U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public Publisher<T> apply(T t) throws Exception {
            return new FlowableTakePublisher((Publisher) ObjectHelper.requireNonNull(this.a.apply(t), "The itemDelay returned a null Publisher"), 1L).map(Functions.justFunction(t)).defaultIfEmpty(t);
        }
    }

    public static <T, U> Function<T, Publisher<T>> itemDelay(Function<? super T, ? extends Publisher<U>> function) {
        return new f(function);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class m<T> implements Consumer<T> {
        final Subscriber<T> a;

        m(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(T t) throws Exception {
            this.a.onNext(t);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class l<T> implements Consumer<Throwable> {
        final Subscriber<T> a;

        l(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        /* renamed from: a */
        public void accept(Throwable th) throws Exception {
            this.a.onError(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class k<T> implements Action {
        final Subscriber<T> a;

        k(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        @Override // io.reactivex.functions.Action
        public void run() throws Exception {
            this.a.onComplete();
        }
    }

    public static <T> Consumer<T> subscriberOnNext(Subscriber<T> subscriber) {
        return new m(subscriber);
    }

    public static <T> Consumer<Throwable> subscriberOnError(Subscriber<T> subscriber) {
        return new l(subscriber);
    }

    public static <T> Action subscriberOnComplete(Subscriber<T> subscriber) {
        return new k(subscriber);
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
    public static final class e<T, R, U> implements Function<T, Publisher<R>> {
        private final BiFunction<? super T, ? super U, ? extends R> a;
        private final Function<? super T, ? extends Publisher<? extends U>> b;

        e(BiFunction<? super T, ? super U, ? extends R> biFunction, Function<? super T, ? extends Publisher<? extends U>> function) {
            this.a = biFunction;
            this.b = function;
        }

        /* renamed from: a */
        public Publisher<R> apply(T t) throws Exception {
            return new FlowableMapPublisher((Publisher) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null Publisher"), new d(this.a, t));
        }
    }

    public static <T, U, R> Function<T, Publisher<R>> flatMapWithCombiner(Function<? super T, ? extends Publisher<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return new e(biFunction, function);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c<T, U> implements Function<T, Publisher<U>> {
        private final Function<? super T, ? extends Iterable<? extends U>> a;

        c(Function<? super T, ? extends Iterable<? extends U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public Publisher<U> apply(T t) throws Exception {
            return new FlowableFromIterable((Iterable) ObjectHelper.requireNonNull(this.a.apply(t), "The mapper returned a null Iterable"));
        }
    }

    public static <T, U> Function<T, Publisher<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        return new c(function);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable) {
        return new g(flowable);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable, int i2) {
        return new a(flowable, i2);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable, int i2, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return new b(flowable, i2, j2, timeUnit, scheduler);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return new n(flowable, j2, timeUnit, scheduler);
    }

    public static <T, R> Function<Flowable<T>, Publisher<R>> replayFunction(Function<? super Flowable<T>, ? extends Publisher<R>> function, Scheduler scheduler) {
        return new h(function, scheduler);
    }

    /* loaded from: classes4.dex */
    public enum RequestMax implements Consumer<Subscription> {
        INSTANCE;

        public void accept(Subscription subscription) throws Exception {
            subscription.request(Long.MAX_VALUE);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class o<T, R> implements Function<List<Publisher<? extends T>>, Publisher<? extends R>> {
        private final Function<? super Object[], ? extends R> a;

        o(Function<? super Object[], ? extends R> function) {
            this.a = function;
        }

        /* renamed from: a */
        public Publisher<? extends R> apply(List<Publisher<? extends T>> list) {
            return Flowable.zipIterable(list, this.a, false, Flowable.bufferSize());
        }
    }

    public static <T, R> Function<List<Publisher<? extends T>>, Publisher<? extends R>> zipIterable(Function<? super Object[], ? extends R> function) {
        return new o(function);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class g<T> implements Callable<ConnectableFlowable<T>> {
        private final Flowable<T> a;

        g(Flowable<T> flowable) {
            this.a = flowable;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> call() {
            return this.a.replay();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> implements Callable<ConnectableFlowable<T>> {
        private final Flowable<T> a;
        private final int b;

        a(Flowable<T> flowable, int i) {
            this.a = flowable;
            this.b = i;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> call() {
            return this.a.replay(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> implements Callable<ConnectableFlowable<T>> {
        private final Flowable<T> a;
        private final int b;
        private final long c;
        private final TimeUnit d;
        private final Scheduler e;

        b(Flowable<T> flowable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = flowable;
            this.b = i;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> call() {
            return this.a.replay(this.b, this.c, this.d, this.e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class n<T> implements Callable<ConnectableFlowable<T>> {
        private final Flowable<T> a;
        private final long b;
        private final TimeUnit c;
        private final Scheduler d;

        n(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = flowable;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> call() {
            return this.a.replay(this.b, this.c, this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class h<T, R> implements Function<Flowable<T>, Publisher<R>> {
        private final Function<? super Flowable<T>, ? extends Publisher<R>> a;
        private final Scheduler b;

        h(Function<? super Flowable<T>, ? extends Publisher<R>> function, Scheduler scheduler) {
            this.a = function;
            this.b = scheduler;
        }

        /* renamed from: a */
        public Publisher<R> apply(Flowable<T> flowable) throws Exception {
            return Flowable.fromPublisher((Publisher) ObjectHelper.requireNonNull(this.a.apply(flowable), "The selector returned a null Publisher")).observeOn(this.b);
        }
    }
}
