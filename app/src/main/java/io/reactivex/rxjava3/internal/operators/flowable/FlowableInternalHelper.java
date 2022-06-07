package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableInternalHelper {
    private FlowableInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class i<T, S> implements BiFunction<S, Emitter<T>, S> {
        final Consumer<Emitter<T>> a;

        i(Consumer<Emitter<T>> consumer) {
            this.a = consumer;
        }

        /* renamed from: a */
        public S apply(S s, Emitter<T> emitter) throws Throwable {
            this.a.accept(emitter);
            return s;
        }
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> consumer) {
        return new i(consumer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class h<T, S> implements BiFunction<S, Emitter<T>, S> {
        final BiConsumer<S, Emitter<T>> a;

        h(BiConsumer<S, Emitter<T>> biConsumer) {
            this.a = biConsumer;
        }

        /* renamed from: a */
        public S apply(S s, Emitter<T> emitter) throws Throwable {
            this.a.accept(s, emitter);
            return s;
        }
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> biConsumer) {
        return new h(biConsumer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class f<T, U> implements Function<T, Publisher<T>> {
        final Function<? super T, ? extends Publisher<U>> a;

        f(Function<? super T, ? extends Publisher<U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public Publisher<T> apply(T t) throws Throwable {
            return new FlowableTakePublisher((Publisher) Objects.requireNonNull(this.a.apply(t), "The itemDelay returned a null Publisher"), 1L).map(Functions.justFunction(t)).defaultIfEmpty(t);
        }
    }

    public static <T, U> Function<T, Publisher<T>> itemDelay(Function<? super T, ? extends Publisher<U>> function) {
        return new f(function);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class l<T> implements Consumer<T> {
        final Subscriber<T> a;

        l(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(T t) {
            this.a.onNext(t);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class k<T> implements Consumer<Throwable> {
        final Subscriber<T> a;

        k(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        /* renamed from: a */
        public void accept(Throwable th) {
            this.a.onError(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class j<T> implements Action {
        final Subscriber<T> a;

        j(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() {
            this.a.onComplete();
        }
    }

    public static <T> Consumer<T> subscriberOnNext(Subscriber<T> subscriber) {
        return new l(subscriber);
    }

    public static <T> Consumer<Throwable> subscriberOnError(Subscriber<T> subscriber) {
        return new k(subscriber);
    }

    public static <T> Action subscriberOnComplete(Subscriber<T> subscriber) {
        return new j(subscriber);
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
    public static final class e<T, R, U> implements Function<T, Publisher<R>> {
        private final BiFunction<? super T, ? super U, ? extends R> a;
        private final Function<? super T, ? extends Publisher<? extends U>> b;

        e(BiFunction<? super T, ? super U, ? extends R> biFunction, Function<? super T, ? extends Publisher<? extends U>> function) {
            this.a = biFunction;
            this.b = function;
        }

        /* renamed from: a */
        public Publisher<R> apply(T t) throws Throwable {
            return new FlowableMapPublisher((Publisher) Objects.requireNonNull(this.b.apply(t), "The mapper returned a null Publisher"), new d(this.a, t));
        }
    }

    public static <T, U, R> Function<T, Publisher<R>> flatMapWithCombiner(Function<? super T, ? extends Publisher<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return new e(biFunction, function);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c<T, U> implements Function<T, Publisher<U>> {
        private final Function<? super T, ? extends Iterable<? extends U>> a;

        c(Function<? super T, ? extends Iterable<? extends U>> function) {
            this.a = function;
        }

        /* renamed from: a */
        public Publisher<U> apply(T t) throws Throwable {
            return new FlowableFromIterable((Iterable) Objects.requireNonNull(this.a.apply(t), "The mapper returned a null Iterable"));
        }
    }

    public static <T, U> Function<T, Publisher<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        return new c(function);
    }

    public static <T> Supplier<ConnectableFlowable<T>> replaySupplier(Flowable<T> flowable) {
        return new g(flowable);
    }

    public static <T> Supplier<ConnectableFlowable<T>> replaySupplier(Flowable<T> flowable, int i2, boolean z) {
        return new a(flowable, i2, z);
    }

    public static <T> Supplier<ConnectableFlowable<T>> replaySupplier(Flowable<T> flowable, int i2, long j2, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        return new b(flowable, i2, j2, timeUnit, scheduler, z);
    }

    public static <T> Supplier<ConnectableFlowable<T>> replaySupplier(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        return new m(flowable, j2, timeUnit, scheduler, z);
    }

    /* loaded from: classes5.dex */
    public enum RequestMax implements Consumer<Subscription> {
        INSTANCE;

        public void accept(Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class g<T> implements Supplier<ConnectableFlowable<T>> {
        final Flowable<T> a;

        g(Flowable<T> flowable) {
            this.a = flowable;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> get() {
            return this.a.replay();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> implements Supplier<ConnectableFlowable<T>> {
        final Flowable<T> a;
        final int b;
        final boolean c;

        a(Flowable<T> flowable, int i, boolean z) {
            this.a = flowable;
            this.b = i;
            this.c = z;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> get() {
            return this.a.replay(this.b, this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b<T> implements Supplier<ConnectableFlowable<T>> {
        final Flowable<T> a;
        final int b;
        final long c;
        final TimeUnit d;
        final Scheduler e;
        final boolean f;

        b(Flowable<T> flowable, int i, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.a = flowable;
            this.b = i;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
            this.f = z;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> get() {
            return this.a.replay(this.b, this.c, this.d, this.e, this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class m<T> implements Supplier<ConnectableFlowable<T>> {
        final boolean a;
        private final Flowable<T> b;
        private final long c;
        private final TimeUnit d;
        private final Scheduler e;

        m(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.b = flowable;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
            this.a = z;
        }

        /* renamed from: a */
        public ConnectableFlowable<T> get() {
            return this.b.replay(this.c, this.d, this.e, this.a);
        }
    }
}
