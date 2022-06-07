package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.BackpressureKind;
import io.reactivex.rxjava3.annotations.BackpressureSupport;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.annotations.SchedulerSupport;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.functions.Function4;
import io.reactivex.rxjava3.functions.Function5;
import io.reactivex.rxjava3.functions.Function6;
import io.reactivex.rxjava3.functions.Function7;
import io.reactivex.rxjava3.functions.Function8;
import io.reactivex.rxjava3.functions.Function9;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.fuseable.FuseToFlowable;
import io.reactivex.rxjava3.internal.fuseable.FuseToObservable;
import io.reactivex.rxjava3.internal.jdk8.CompletionStageConsumer;
import io.reactivex.rxjava3.internal.jdk8.MaybeFlattenStreamAsFlowable;
import io.reactivex.rxjava3.internal.jdk8.MaybeFlattenStreamAsObservable;
import io.reactivex.rxjava3.internal.jdk8.MaybeFromCompletionStage;
import io.reactivex.rxjava3.internal.jdk8.MaybeMapOptional;
import io.reactivex.rxjava3.internal.observers.BlockingDisposableMultiObserver;
import io.reactivex.rxjava3.internal.observers.BlockingMultiObserver;
import io.reactivex.rxjava3.internal.observers.FutureMultiObserver;
import io.reactivex.rxjava3.internal.observers.SafeMaybeObserver;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableElementAtMaybePublisher;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapMaybePublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeAmb;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeCache;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeCallbackObserver;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeConcatArray;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeConcatArrayDelayError;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeConcatIterable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeContains;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeCount;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeCreate;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDefer;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDelay;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDelayOtherPublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDelaySubscriptionOtherPublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDematerialize;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDetach;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDoAfterSuccess;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDoFinally;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDoOnEvent;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDoOnLifecycle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDoOnTerminate;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeEmpty;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeEqualSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeError;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeErrorCallable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFilter;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapBiSelector;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapCompletable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapIterableFlowable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapIterableObservable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapNotification;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatten;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromAction;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromCallable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromCompletable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromFuture;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromRunnable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromSupplier;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeHide;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeIgnoreElementCompletable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeIsEmptySingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeJust;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeLift;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeMap;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeMaterialize;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeNever;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeObserveOn;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeOnErrorComplete;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeOnErrorNext;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeOnErrorReturn;
import io.reactivex.rxjava3.internal.operators.maybe.MaybePeek;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeSubscribeOn;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeSwitchIfEmpty;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeSwitchIfEmptySingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTakeUntilMaybe;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTakeUntilPublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTimeInterval;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTimeoutMaybe;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTimeoutPublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTimer;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToFlowable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToObservable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToPublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeUnsafeCreate;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeUnsubscribeOn;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeUsing;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeZipArray;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeZipIterable;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapMaybePublisher;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapMaybePublisher;
import io.reactivex.rxjava3.internal.operators.mixed.MaybeFlatMapObservable;
import io.reactivex.rxjava3.internal.operators.mixed.MaybeFlatMapPublisher;
import io.reactivex.rxjava3.internal.operators.observable.ObservableElementAtMaybe;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;

/* loaded from: classes4.dex */
public abstract class Maybe<T> implements MaybeSource<T> {
    protected abstract void subscribeActual(@NonNull MaybeObserver<? super T> maybeObserver);

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> amb(@NonNull Iterable<? extends MaybeSource<? extends T>> iterable) {
        Objects.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new MaybeAmb(null, iterable));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Maybe<T> ambArray(@NonNull MaybeSource<? extends T>... maybeSourceArr) {
        Objects.requireNonNull(maybeSourceArr, "sources is null");
        if (maybeSourceArr.length == 0) {
            return empty();
        }
        if (maybeSourceArr.length == 1) {
            return wrap(maybeSourceArr[0]);
        }
        return RxJavaPlugins.onAssembly(new MaybeAmb(maybeSourceArr, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Iterable<? extends MaybeSource<? extends T>> iterable) {
        Objects.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new MaybeConcatIterable(iterable));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        return concatArray(maybeSource, maybeSource2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2, @NonNull MaybeSource<? extends T> maybeSource3) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        return concatArray(maybeSource, maybeSource2, maybeSource3);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2, @NonNull MaybeSource<? extends T> maybeSource3, @NonNull MaybeSource<? extends T> maybeSource4) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        Objects.requireNonNull(maybeSource4, "source4 is null");
        return concatArray(maybeSource, maybeSource2, maybeSource3, maybeSource4);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher) {
        return concat(publisher, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher, int i) {
        Objects.requireNonNull(publisher, "sources is null");
        ObjectHelper.verifyPositive(i, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapMaybePublisher(publisher, Functions.identity(), ErrorMode.IMMEDIATE, i));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArray(@NonNull MaybeSource<? extends T>... maybeSourceArr) {
        Objects.requireNonNull(maybeSourceArr, "sources is null");
        if (maybeSourceArr.length == 0) {
            return Flowable.empty();
        }
        if (maybeSourceArr.length == 1) {
            return RxJavaPlugins.onAssembly(new MaybeToFlowable(maybeSourceArr[0]));
        }
        return RxJavaPlugins.onAssembly(new MaybeConcatArray(maybeSourceArr));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayDelayError(@NonNull MaybeSource<? extends T>... maybeSourceArr) {
        Objects.requireNonNull(maybeSourceArr, "sources is null");
        if (maybeSourceArr.length == 0) {
            return Flowable.empty();
        }
        if (maybeSourceArr.length == 1) {
            return RxJavaPlugins.onAssembly(new MaybeToFlowable(maybeSourceArr[0]));
        }
        return RxJavaPlugins.onAssembly(new MaybeConcatArrayDelayError(maybeSourceArr));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEager(@NonNull MaybeSource<? extends T>... maybeSourceArr) {
        return Flowable.fromArray(maybeSourceArr).concatMapEager(MaybeToPublisher.instance());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEagerDelayError(@NonNull MaybeSource<? extends T>... maybeSourceArr) {
        return Flowable.fromArray(maybeSourceArr).concatMapEagerDelayError(MaybeToPublisher.instance(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Iterable<? extends MaybeSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).concatMapMaybeDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher) {
        return Flowable.fromPublisher(publisher).concatMapMaybeDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher, int i) {
        return Flowable.fromPublisher(publisher).concatMapMaybeDelayError(Functions.identity(), true, i);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Iterable<? extends MaybeSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).concatMapEagerDelayError(MaybeToPublisher.instance(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Iterable<? extends MaybeSource<? extends T>> iterable, int i) {
        return Flowable.fromIterable(iterable).concatMapEagerDelayError(MaybeToPublisher.instance(), false, i, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher) {
        return Flowable.fromPublisher(publisher).concatMapEager(MaybeToPublisher.instance());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher, int i) {
        return Flowable.fromPublisher(publisher).concatMapEager(MaybeToPublisher.instance(), i, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Iterable<? extends MaybeSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).concatMapEagerDelayError(MaybeToPublisher.instance(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Iterable<? extends MaybeSource<? extends T>> iterable, int i) {
        return Flowable.fromIterable(iterable).concatMapEagerDelayError(MaybeToPublisher.instance(), true, i, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher) {
        return Flowable.fromPublisher(publisher).concatMapEagerDelayError(MaybeToPublisher.instance(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher, int i) {
        return Flowable.fromPublisher(publisher).concatMapEagerDelayError(MaybeToPublisher.instance(), true, i, 1);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> create(@NonNull MaybeOnSubscribe<T> maybeOnSubscribe) {
        Objects.requireNonNull(maybeOnSubscribe, "onSubscribe is null");
        return RxJavaPlugins.onAssembly(new MaybeCreate(maybeOnSubscribe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> defer(@NonNull Supplier<? extends MaybeSource<? extends T>> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new MaybeDefer(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> empty() {
        return RxJavaPlugins.onAssembly(MaybeEmpty.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> error(@NonNull Throwable th) {
        Objects.requireNonNull(th, "throwable is null");
        return RxJavaPlugins.onAssembly(new MaybeError(th));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> error(@NonNull Supplier<? extends Throwable> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new MaybeErrorCallable(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromAction(@NonNull Action action) {
        Objects.requireNonNull(action, "action is null");
        return RxJavaPlugins.onAssembly(new MaybeFromAction(action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromCompletable(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "completableSource is null");
        return RxJavaPlugins.onAssembly(new MaybeFromCompletable(completableSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromSingle(@NonNull SingleSource<T> singleSource) {
        Objects.requireNonNull(singleSource, "single is null");
        return RxJavaPlugins.onAssembly(new MaybeFromSingle(singleSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromCallable(@NonNull Callable<? extends T> callable) {
        Objects.requireNonNull(callable, "callable is null");
        return RxJavaPlugins.onAssembly(new MaybeFromCallable(callable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromFuture(@NonNull Future<? extends T> future) {
        Objects.requireNonNull(future, "future is null");
        return RxJavaPlugins.onAssembly(new MaybeFromFuture(future, 0L, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromFuture(@NonNull Future<? extends T> future, long j, @NonNull TimeUnit timeUnit) {
        Objects.requireNonNull(future, "future is null");
        Objects.requireNonNull(timeUnit, "unit is null");
        return RxJavaPlugins.onAssembly(new MaybeFromFuture(future, j, timeUnit));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromObservable(@NonNull ObservableSource<T> observableSource) {
        Objects.requireNonNull(observableSource, "source is null");
        return RxJavaPlugins.onAssembly(new ObservableElementAtMaybe(observableSource, 0L));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromPublisher(@NonNull Publisher<T> publisher) {
        Objects.requireNonNull(publisher, "source is null");
        return RxJavaPlugins.onAssembly(new FlowableElementAtMaybePublisher(publisher, 0L));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromRunnable(@NonNull Runnable runnable) {
        Objects.requireNonNull(runnable, "run is null");
        return RxJavaPlugins.onAssembly(new MaybeFromRunnable(runnable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromSupplier(@NonNull Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new MaybeFromSupplier(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> just(T t) {
        Objects.requireNonNull(t, "item is null");
        return RxJavaPlugins.onAssembly(new MaybeJust(t));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Iterable<? extends MaybeSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).flatMapMaybe(Functions.identity(), false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher) {
        return merge(publisher, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher, int i) {
        Objects.requireNonNull(publisher, "sources is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapMaybePublisher(publisher, Functions.identity(), false, i));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> merge(@NonNull MaybeSource<? extends MaybeSource<? extends T>> maybeSource) {
        Objects.requireNonNull(maybeSource, "source is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatten(maybeSource, Functions.identity()));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        return mergeArray(maybeSource, maybeSource2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2, @NonNull MaybeSource<? extends T> maybeSource3) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        return mergeArray(maybeSource, maybeSource2, maybeSource3);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2, @NonNull MaybeSource<? extends T> maybeSource3, @NonNull MaybeSource<? extends T> maybeSource4) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        Objects.requireNonNull(maybeSource4, "source4 is null");
        return mergeArray(maybeSource, maybeSource2, maybeSource3, maybeSource4);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArray(MaybeSource<? extends T>... maybeSourceArr) {
        Objects.requireNonNull(maybeSourceArr, "sources is null");
        if (maybeSourceArr.length == 0) {
            return Flowable.empty();
        }
        if (maybeSourceArr.length == 1) {
            return RxJavaPlugins.onAssembly(new MaybeToFlowable(maybeSourceArr[0]));
        }
        return RxJavaPlugins.onAssembly(new MaybeMergeArray(maybeSourceArr));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArrayDelayError(@NonNull MaybeSource<? extends T>... maybeSourceArr) {
        Objects.requireNonNull(maybeSourceArr, "sources is null");
        return Flowable.fromArray(maybeSourceArr).flatMapMaybe(Functions.identity(), true, Math.max(1, maybeSourceArr.length));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Iterable<? extends MaybeSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).flatMapMaybe(Functions.identity(), true, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher) {
        return mergeDelayError(publisher, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher, int i) {
        Objects.requireNonNull(publisher, "sources is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapMaybePublisher(publisher, Functions.identity(), true, i));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        return mergeArrayDelayError(maybeSource, maybeSource2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2, @NonNull MaybeSource<? extends T> maybeSource3) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        return mergeArrayDelayError(maybeSource, maybeSource2, maybeSource3);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2, @NonNull MaybeSource<? extends T> maybeSource3, @NonNull MaybeSource<? extends T> maybeSource4) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        Objects.requireNonNull(maybeSource4, "source4 is null");
        return mergeArrayDelayError(maybeSource, maybeSource2, maybeSource3, maybeSource4);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> never() {
        return RxJavaPlugins.onAssembly(MaybeNever.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2) {
        return sequenceEqual(maybeSource, maybeSource2, ObjectHelper.equalsPredicate());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull MaybeSource<? extends T> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2, @NonNull BiPredicate<? super T, ? super T> biPredicate) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(biPredicate, "isEqual is null");
        return RxJavaPlugins.onAssembly(new MaybeEqualSingle(maybeSource, maybeSource2, biPredicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNext(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher) {
        Objects.requireNonNull(publisher, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapMaybePublisher(publisher, Functions.identity(), false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNextDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> publisher) {
        Objects.requireNonNull(publisher, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapMaybePublisher(publisher, Functions.identity(), true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public static Maybe<Long> timer(long j, @NonNull TimeUnit timeUnit) {
        return timer(j, timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public static Maybe<Long> timer(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeTimer(Math.max(0L, j), timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> unsafeCreate(@NonNull MaybeSource<T> maybeSource) {
        if (!(maybeSource instanceof Maybe)) {
            Objects.requireNonNull(maybeSource, "onSubscribe is null");
            return RxJavaPlugins.onAssembly(new MaybeUnsafeCreate(maybeSource));
        }
        throw new IllegalArgumentException("unsafeCreate(Maybe) should be upgraded");
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, D> Maybe<T> using(@NonNull Supplier<? extends D> supplier, @NonNull Function<? super D, ? extends MaybeSource<? extends T>> function, @NonNull Consumer<? super D> consumer) {
        return using(supplier, function, consumer, true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, D> Maybe<T> using(@NonNull Supplier<? extends D> supplier, @NonNull Function<? super D, ? extends MaybeSource<? extends T>> function, @NonNull Consumer<? super D> consumer, boolean z) {
        Objects.requireNonNull(supplier, "resourceSupplier is null");
        Objects.requireNonNull(function, "sourceSupplier is null");
        Objects.requireNonNull(consumer, "resourceCleanup is null");
        return RxJavaPlugins.onAssembly(new MaybeUsing(supplier, function, consumer, z));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> wrap(@NonNull MaybeSource<T> maybeSource) {
        if (maybeSource instanceof Maybe) {
            return RxJavaPlugins.onAssembly((Maybe) maybeSource);
        }
        Objects.requireNonNull(maybeSource, "source is null");
        return RxJavaPlugins.onAssembly(new MaybeUnsafeCreate(maybeSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Maybe<R> zip(@NonNull Iterable<? extends MaybeSource<? extends T>> iterable, @NonNull Function<? super Object[], ? extends R> function) {
        Objects.requireNonNull(function, "zipper is null");
        Objects.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new MaybeZipIterable(iterable, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> maybeSource, @NonNull MaybeSource<? extends T2> maybeSource2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(biFunction, "zipper is null");
        return zipArray(Functions.toFunction(biFunction), maybeSource, maybeSource2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> maybeSource, @NonNull MaybeSource<? extends T2> maybeSource2, @NonNull MaybeSource<? extends T3> maybeSource3, @NonNull Function3<? super T1, ? super T2, ? super T3, ? extends R> function3) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        Objects.requireNonNull(function3, "zipper is null");
        return zipArray(Functions.toFunction(function3), maybeSource, maybeSource2, maybeSource3);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> maybeSource, @NonNull MaybeSource<? extends T2> maybeSource2, @NonNull MaybeSource<? extends T3> maybeSource3, @NonNull MaybeSource<? extends T4> maybeSource4, @NonNull Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> function4) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        Objects.requireNonNull(maybeSource4, "source4 is null");
        Objects.requireNonNull(function4, "zipper is null");
        return zipArray(Functions.toFunction(function4), maybeSource, maybeSource2, maybeSource3, maybeSource4);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> maybeSource, @NonNull MaybeSource<? extends T2> maybeSource2, @NonNull MaybeSource<? extends T3> maybeSource3, @NonNull MaybeSource<? extends T4> maybeSource4, @NonNull MaybeSource<? extends T5> maybeSource5, @NonNull Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> function5) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        Objects.requireNonNull(maybeSource4, "source4 is null");
        Objects.requireNonNull(maybeSource5, "source5 is null");
        Objects.requireNonNull(function5, "zipper is null");
        return zipArray(Functions.toFunction(function5), maybeSource, maybeSource2, maybeSource3, maybeSource4, maybeSource5);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> maybeSource, @NonNull MaybeSource<? extends T2> maybeSource2, @NonNull MaybeSource<? extends T3> maybeSource3, @NonNull MaybeSource<? extends T4> maybeSource4, @NonNull MaybeSource<? extends T5> maybeSource5, @NonNull MaybeSource<? extends T6> maybeSource6, @NonNull Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> function6) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        Objects.requireNonNull(maybeSource4, "source4 is null");
        Objects.requireNonNull(maybeSource5, "source5 is null");
        Objects.requireNonNull(maybeSource6, "source6 is null");
        Objects.requireNonNull(function6, "zipper is null");
        return zipArray(Functions.toFunction(function6), maybeSource, maybeSource2, maybeSource3, maybeSource4, maybeSource5, maybeSource6);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> maybeSource, @NonNull MaybeSource<? extends T2> maybeSource2, @NonNull MaybeSource<? extends T3> maybeSource3, @NonNull MaybeSource<? extends T4> maybeSource4, @NonNull MaybeSource<? extends T5> maybeSource5, @NonNull MaybeSource<? extends T6> maybeSource6, @NonNull MaybeSource<? extends T7> maybeSource7, @NonNull Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> function7) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        Objects.requireNonNull(maybeSource4, "source4 is null");
        Objects.requireNonNull(maybeSource5, "source5 is null");
        Objects.requireNonNull(maybeSource6, "source6 is null");
        Objects.requireNonNull(maybeSource7, "source7 is null");
        Objects.requireNonNull(function7, "zipper is null");
        return zipArray(Functions.toFunction(function7), maybeSource, maybeSource2, maybeSource3, maybeSource4, maybeSource5, maybeSource6, maybeSource7);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> maybeSource, @NonNull MaybeSource<? extends T2> maybeSource2, @NonNull MaybeSource<? extends T3> maybeSource3, @NonNull MaybeSource<? extends T4> maybeSource4, @NonNull MaybeSource<? extends T5> maybeSource5, @NonNull MaybeSource<? extends T6> maybeSource6, @NonNull MaybeSource<? extends T7> maybeSource7, @NonNull MaybeSource<? extends T8> maybeSource8, @NonNull Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> function8) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        Objects.requireNonNull(maybeSource4, "source4 is null");
        Objects.requireNonNull(maybeSource5, "source5 is null");
        Objects.requireNonNull(maybeSource6, "source6 is null");
        Objects.requireNonNull(maybeSource7, "source7 is null");
        Objects.requireNonNull(maybeSource8, "source8 is null");
        Objects.requireNonNull(function8, "zipper is null");
        return zipArray(Functions.toFunction(function8), maybeSource, maybeSource2, maybeSource3, maybeSource4, maybeSource5, maybeSource6, maybeSource7, maybeSource8);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> maybeSource, @NonNull MaybeSource<? extends T2> maybeSource2, @NonNull MaybeSource<? extends T3> maybeSource3, @NonNull MaybeSource<? extends T4> maybeSource4, @NonNull MaybeSource<? extends T5> maybeSource5, @NonNull MaybeSource<? extends T6> maybeSource6, @NonNull MaybeSource<? extends T7> maybeSource7, @NonNull MaybeSource<? extends T8> maybeSource8, @NonNull MaybeSource<? extends T9> maybeSource9, @NonNull Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> function9) {
        Objects.requireNonNull(maybeSource, "source1 is null");
        Objects.requireNonNull(maybeSource2, "source2 is null");
        Objects.requireNonNull(maybeSource3, "source3 is null");
        Objects.requireNonNull(maybeSource4, "source4 is null");
        Objects.requireNonNull(maybeSource5, "source5 is null");
        Objects.requireNonNull(maybeSource6, "source6 is null");
        Objects.requireNonNull(maybeSource7, "source7 is null");
        Objects.requireNonNull(maybeSource8, "source8 is null");
        Objects.requireNonNull(maybeSource9, "source9 is null");
        Objects.requireNonNull(function9, "zipper is null");
        return zipArray(Functions.toFunction(function9), maybeSource, maybeSource2, maybeSource3, maybeSource4, maybeSource5, maybeSource6, maybeSource7, maybeSource8, maybeSource9);
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T, R> Maybe<R> zipArray(@NonNull Function<? super Object[], ? extends R> function, @NonNull MaybeSource<? extends T>... maybeSourceArr) {
        Objects.requireNonNull(maybeSourceArr, "sources is null");
        if (maybeSourceArr.length == 0) {
            return empty();
        }
        Objects.requireNonNull(function, "zipper is null");
        return RxJavaPlugins.onAssembly(new MaybeZipArray(maybeSourceArr, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> ambWith(@NonNull MaybeSource<? extends T> maybeSource) {
        Objects.requireNonNull(maybeSource, "other is null");
        return ambArray(this, maybeSource);
    }

    @CheckReturnValue
    @Nullable
    @SchedulerSupport("none")
    public final T blockingGet() {
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        return (T) blockingMultiObserver.blockingGet();
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final T blockingGet(@NonNull T t) {
        Objects.requireNonNull(t, "defaultValue is null");
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        return (T) blockingMultiObserver.blockingGet(t);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe() {
        blockingSubscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> consumer) {
        blockingSubscribe(consumer, Functions.ERROR_CONSUMER, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> consumer, @NonNull Consumer<? super Throwable> consumer2) {
        blockingSubscribe(consumer, consumer2, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> consumer, @NonNull Consumer<? super Throwable> consumer2, @NonNull Action action) {
        Objects.requireNonNull(consumer, "onSuccess is null");
        Objects.requireNonNull(consumer2, "onError is null");
        Objects.requireNonNull(action, "onComplete is null");
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        blockingMultiObserver.blockingConsume(consumer, consumer2, action);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull MaybeObserver<? super T> maybeObserver) {
        Objects.requireNonNull(maybeObserver, "observer is null");
        BlockingDisposableMultiObserver blockingDisposableMultiObserver = new BlockingDisposableMultiObserver();
        maybeObserver.onSubscribe(blockingDisposableMultiObserver);
        subscribe(blockingDisposableMultiObserver);
        blockingDisposableMultiObserver.blockingConsume(maybeObserver);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> cache() {
        return RxJavaPlugins.onAssembly(new MaybeCache(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Maybe<U> cast(@NonNull Class<? extends U> cls) {
        Objects.requireNonNull(cls, "clazz is null");
        return (Maybe<U>) map(Functions.castFunction(cls));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> compose(@NonNull MaybeTransformer<? super T, ? extends R> maybeTransformer) {
        return wrap(((MaybeTransformer) Objects.requireNonNull(maybeTransformer, "transformer is null")).apply(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> concatMap(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> function) {
        return flatMap(function);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable concatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> function) {
        return flatMapCompletable(function);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> concatMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> function) {
        return flatMapSingle(function);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> concatWith(@NonNull MaybeSource<? extends T> maybeSource) {
        Objects.requireNonNull(maybeSource, "other is null");
        return concat(this, maybeSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> contains(@NonNull Object obj) {
        Objects.requireNonNull(obj, "item is null");
        return RxJavaPlugins.onAssembly(new MaybeContains(this, obj));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Long> count() {
        return RxJavaPlugins.onAssembly(new MaybeCount(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> defaultIfEmpty(@NonNull T t) {
        Objects.requireNonNull(t, "defaultItem is null");
        return RxJavaPlugins.onAssembly(new MaybeToSingle(this, t));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> dematerialize(@NonNull Function<? super T, Notification<R>> function) {
        Objects.requireNonNull(function, "selector is null");
        return RxJavaPlugins.onAssembly(new MaybeDematerialize(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<T> delay(long j, @NonNull TimeUnit timeUnit) {
        return delay(j, timeUnit, Schedulers.computation(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<T> delay(long j, @NonNull TimeUnit timeUnit, boolean z) {
        return delay(j, timeUnit, Schedulers.computation(), z);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> delay(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return delay(j, timeUnit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> delay(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler, boolean z) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeDelay(this, Math.max(0L, j), timeUnit, scheduler, z));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Maybe<T> delay(@NonNull Publisher<U> publisher) {
        Objects.requireNonNull(publisher, "delayIndicator is null");
        return RxJavaPlugins.onAssembly(new MaybeDelayOtherPublisher(this, publisher));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Maybe<T> delaySubscription(@NonNull Publisher<U> publisher) {
        Objects.requireNonNull(publisher, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new MaybeDelaySubscriptionOtherPublisher(this, publisher));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<T> delaySubscription(long j, @NonNull TimeUnit timeUnit) {
        return delaySubscription(j, timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> delaySubscription(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return delaySubscription(Flowable.timer(j, timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doAfterSuccess(@NonNull Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "onAfterSuccess is null");
        return RxJavaPlugins.onAssembly(new MaybeDoAfterSuccess(this, consumer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doAfterTerminate(@NonNull Action action) {
        return RxJavaPlugins.onAssembly(new MaybePeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, (Action) Objects.requireNonNull(action, "onAfterTerminate is null"), Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doFinally(@NonNull Action action) {
        Objects.requireNonNull(action, "onFinally is null");
        return RxJavaPlugins.onAssembly(new MaybeDoFinally(this, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnDispose(@NonNull Action action) {
        return RxJavaPlugins.onAssembly(new MaybePeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, (Action) Objects.requireNonNull(action, "onDispose is null")));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnComplete(@NonNull Action action) {
        return RxJavaPlugins.onAssembly(new MaybePeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), (Action) Objects.requireNonNull(action, "onComplete is null"), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnError(@NonNull Consumer<? super Throwable> consumer) {
        return RxJavaPlugins.onAssembly(new MaybePeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), (Consumer) Objects.requireNonNull(consumer, "onError is null"), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnEvent(@NonNull BiConsumer<? super T, ? super Throwable> biConsumer) {
        Objects.requireNonNull(biConsumer, "onEvent is null");
        return RxJavaPlugins.onAssembly(new MaybeDoOnEvent(this, biConsumer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnLifecycle(@NonNull Consumer<? super Disposable> consumer, @NonNull Action action) {
        Objects.requireNonNull(consumer, "onSubscribe is null");
        Objects.requireNonNull(action, "onDispose is null");
        return RxJavaPlugins.onAssembly(new MaybeDoOnLifecycle(this, consumer, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnSubscribe(@NonNull Consumer<? super Disposable> consumer) {
        return RxJavaPlugins.onAssembly(new MaybePeek(this, (Consumer) Objects.requireNonNull(consumer, "onSubscribe is null"), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnTerminate(@NonNull Action action) {
        Objects.requireNonNull(action, "onTerminate is null");
        return RxJavaPlugins.onAssembly(new MaybeDoOnTerminate(this, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnSuccess(@NonNull Consumer<? super T> consumer) {
        return RxJavaPlugins.onAssembly(new MaybePeek(this, Functions.emptyConsumer(), (Consumer) Objects.requireNonNull(consumer, "onSuccess is null"), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> filter(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new MaybeFilter(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> flatMap(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatten(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> flatMap(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> function, @NonNull Function<? super Throwable, ? extends MaybeSource<? extends R>> function2, @NonNull Supplier<? extends MaybeSource<? extends R>> supplier) {
        Objects.requireNonNull(function, "onSuccessMapper is null");
        Objects.requireNonNull(function2, "onErrorMapper is null");
        Objects.requireNonNull(supplier, "onCompleteSupplier is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapNotification(this, function, function2, supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Maybe<R> flatMap(@NonNull Function<? super T, ? extends MaybeSource<? extends U>> function, @NonNull BiFunction<? super T, ? super U, ? extends R> biFunction) {
        Objects.requireNonNull(function, "mapper is null");
        Objects.requireNonNull(biFunction, "combiner is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapBiSelector(this, function, biFunction));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<U> flattenAsFlowable(@NonNull Function<? super T, ? extends Iterable<? extends U>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapIterableFlowable(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<U> flattenAsObservable(@NonNull Function<? super T, ? extends Iterable<? extends U>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapIterableObservable(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMapObservable(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapObservable(this, function));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMapPublisher(@NonNull Function<? super T, ? extends Publisher<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapPublisher(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> flatMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapSingle(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable flatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapCompletable(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> hide() {
        return RxJavaPlugins.onAssembly(new MaybeHide(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable ignoreElement() {
        return RxJavaPlugins.onAssembly(new MaybeIgnoreElementCompletable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> isEmpty() {
        return RxJavaPlugins.onAssembly(new MaybeIsEmptySingle(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> lift(@NonNull MaybeOperator<? extends R, ? super T> maybeOperator) {
        Objects.requireNonNull(maybeOperator, "lift is null");
        return RxJavaPlugins.onAssembly(new MaybeLift(this, maybeOperator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> map(@NonNull Function<? super T, ? extends R> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeMap(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Notification<T>> materialize() {
        return RxJavaPlugins.onAssembly(new MaybeMaterialize(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> mergeWith(@NonNull MaybeSource<? extends T> maybeSource) {
        Objects.requireNonNull(maybeSource, "other is null");
        return merge(this, maybeSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> observeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeObserveOn(this, scheduler));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Maybe<U> ofType(@NonNull Class<U> cls) {
        Objects.requireNonNull(cls, "clazz is null");
        return filter(Functions.isInstanceOf(cls)).cast(cls);
    }

    @CheckReturnValue
    @SchedulerSupport("none")
    public final <R> R to(@NonNull MaybeConverter<T, ? extends R> maybeConverter) {
        return (R) ((MaybeConverter) Objects.requireNonNull(maybeConverter, "converter is null")).apply(this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> toFlowable() {
        if (this instanceof FuseToFlowable) {
            return ((FuseToFlowable) this).fuseToFlowable();
        }
        return RxJavaPlugins.onAssembly(new MaybeToFlowable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Future<T> toFuture() {
        return (Future) subscribeWith(new FutureMultiObserver());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> toObservable() {
        if (this instanceof FuseToObservable) {
            return ((FuseToObservable) this).fuseToObservable();
        }
        return RxJavaPlugins.onAssembly(new MaybeToObservable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> toSingle() {
        return RxJavaPlugins.onAssembly(new MaybeToSingle(this, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorComplete() {
        return onErrorComplete(Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorComplete(@NonNull Predicate<? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new MaybeOnErrorComplete(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorResumeWith(@NonNull MaybeSource<? extends T> maybeSource) {
        Objects.requireNonNull(maybeSource, "fallback is null");
        return onErrorResumeNext(Functions.justFunction(maybeSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorResumeNext(@NonNull Function<? super Throwable, ? extends MaybeSource<? extends T>> function) {
        Objects.requireNonNull(function, "fallbackSupplier is null");
        return RxJavaPlugins.onAssembly(new MaybeOnErrorNext(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorReturn(@NonNull Function<? super Throwable, ? extends T> function) {
        Objects.requireNonNull(function, "itemSupplier is null");
        return RxJavaPlugins.onAssembly(new MaybeOnErrorReturn(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorReturnItem(@NonNull T t) {
        Objects.requireNonNull(t, "item is null");
        return onErrorReturn(Functions.justFunction(t));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onTerminateDetach() {
        return RxJavaPlugins.onAssembly(new MaybeDetach(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeat() {
        return repeat(Long.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeat(long j) {
        return toFlowable().repeat(j);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeatUntil(@NonNull BooleanSupplier booleanSupplier) {
        return toFlowable().repeatUntil(booleanSupplier);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeatWhen(@NonNull Function<? super Flowable<Object>, ? extends Publisher<?>> function) {
        return toFlowable().repeatWhen(function);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retry() {
        return retry(Long.MAX_VALUE, Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retry(@NonNull BiPredicate<? super Integer, ? super Throwable> biPredicate) {
        return toFlowable().retry(biPredicate).singleElement();
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retry(long j) {
        return retry(j, Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retry(long j, @NonNull Predicate<? super Throwable> predicate) {
        return toFlowable().retry(j, predicate).singleElement();
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retry(@NonNull Predicate<? super Throwable> predicate) {
        return retry(Long.MAX_VALUE, predicate);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retryUntil(@NonNull BooleanSupplier booleanSupplier) {
        Objects.requireNonNull(booleanSupplier, "stop is null");
        return retry(Long.MAX_VALUE, Functions.predicateReverseFor(booleanSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retryWhen(@NonNull Function<? super Flowable<Throwable>, ? extends Publisher<?>> function) {
        return toFlowable().retryWhen(function).singleElement();
    }

    @SchedulerSupport("none")
    public final void safeSubscribe(@NonNull MaybeObserver<? super T> maybeObserver) {
        Objects.requireNonNull(maybeObserver, "observer is null");
        subscribe(new SafeMaybeObserver(maybeObserver));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "other is null");
        return Flowable.concat(Completable.wrap(completableSource).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull SingleSource<T> singleSource) {
        Objects.requireNonNull(singleSource, "other is null");
        return Flowable.concat(Single.wrap(singleSource).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull MaybeSource<T> maybeSource) {
        Objects.requireNonNull(maybeSource, "other is null");
        return Flowable.concat(wrap(maybeSource).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> startWith(@NonNull ObservableSource<T> observableSource) {
        Objects.requireNonNull(observableSource, "other is null");
        return Observable.wrap(observableSource).concatWith(toObservable());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull Publisher<T> publisher) {
        Objects.requireNonNull(publisher, "other is null");
        return toFlowable().startWith(publisher);
    }

    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe() {
        return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> consumer) {
        return subscribe(consumer, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> consumer, @NonNull Consumer<? super Throwable> consumer2) {
        return subscribe(consumer, consumer2, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> consumer, @NonNull Consumer<? super Throwable> consumer2, @NonNull Action action) {
        Objects.requireNonNull(consumer, "onSuccess is null");
        Objects.requireNonNull(consumer2, "onError is null");
        Objects.requireNonNull(action, "onComplete is null");
        return (Disposable) subscribeWith(new MaybeCallbackObserver(consumer, consumer2, action));
    }

    @Override // io.reactivex.rxjava3.core.MaybeSource
    @SchedulerSupport("none")
    public final void subscribe(@NonNull MaybeObserver<? super T> maybeObserver) {
        Objects.requireNonNull(maybeObserver, "observer is null");
        MaybeObserver<? super T> onSubscribe = RxJavaPlugins.onSubscribe(this, maybeObserver);
        Objects.requireNonNull(onSubscribe, "The RxJavaPlugins.onSubscribe hook returned a null MaybeObserver. Please check the handler provided to RxJavaPlugins.setOnMaybeSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
        try {
            subscribeActual(onSubscribe);
        } catch (NullPointerException e) {
            throw e;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            NullPointerException nullPointerException = new NullPointerException("subscribeActual failed");
            nullPointerException.initCause(th);
            throw nullPointerException;
        }
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> subscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeSubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <E extends MaybeObserver<? super T>> E subscribeWith(E e) {
        subscribe(e);
        return e;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> switchIfEmpty(@NonNull MaybeSource<? extends T> maybeSource) {
        Objects.requireNonNull(maybeSource, "other is null");
        return RxJavaPlugins.onAssembly(new MaybeSwitchIfEmpty(this, maybeSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> switchIfEmpty(@NonNull SingleSource<? extends T> singleSource) {
        Objects.requireNonNull(singleSource, "other is null");
        return RxJavaPlugins.onAssembly(new MaybeSwitchIfEmptySingle(this, singleSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Maybe<T> takeUntil(@NonNull MaybeSource<U> maybeSource) {
        Objects.requireNonNull(maybeSource, "other is null");
        return RxJavaPlugins.onAssembly(new MaybeTakeUntilMaybe(this, maybeSource));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Maybe<T> takeUntil(@NonNull Publisher<U> publisher) {
        Objects.requireNonNull(publisher, "other is null");
        return RxJavaPlugins.onAssembly(new MaybeTakeUntilPublisher(this, publisher));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<Timed<T>> timeInterval() {
        return timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<Timed<T>> timeInterval(@NonNull Scheduler scheduler) {
        return timeInterval(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<Timed<T>> timeInterval(@NonNull TimeUnit timeUnit) {
        return timeInterval(timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<Timed<T>> timeInterval(@NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeInterval(this, timeUnit, scheduler, true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<Timed<T>> timestamp() {
        return timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<Timed<T>> timestamp(@NonNull Scheduler scheduler) {
        return timestamp(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<Timed<T>> timestamp(@NonNull TimeUnit timeUnit) {
        return timestamp(timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<Timed<T>> timestamp(@NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeInterval(this, timeUnit, scheduler, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<T> timeout(long j, @NonNull TimeUnit timeUnit) {
        return timeout(j, timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<T> timeout(long j, @NonNull TimeUnit timeUnit, @NonNull MaybeSource<? extends T> maybeSource) {
        Objects.requireNonNull(maybeSource, "fallback is null");
        return timeout(j, timeUnit, Schedulers.computation(), maybeSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> timeout(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler, @NonNull MaybeSource<? extends T> maybeSource) {
        Objects.requireNonNull(maybeSource, "fallback is null");
        return timeout(timer(j, timeUnit, scheduler), maybeSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> timeout(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return timeout(timer(j, timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Maybe<T> timeout(@NonNull MaybeSource<U> maybeSource) {
        Objects.requireNonNull(maybeSource, "timeoutIndicator is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeoutMaybe(this, maybeSource, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Maybe<T> timeout(@NonNull MaybeSource<U> maybeSource, @NonNull MaybeSource<? extends T> maybeSource2) {
        Objects.requireNonNull(maybeSource, "timeoutIndicator is null");
        Objects.requireNonNull(maybeSource2, "fallback is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeoutMaybe(this, maybeSource, maybeSource2));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Maybe<T> timeout(@NonNull Publisher<U> publisher) {
        Objects.requireNonNull(publisher, "timeoutIndicator is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeoutPublisher(this, publisher, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Maybe<T> timeout(@NonNull Publisher<U> publisher, @NonNull MaybeSource<? extends T> maybeSource) {
        Objects.requireNonNull(publisher, "timeoutIndicator is null");
        Objects.requireNonNull(maybeSource, "fallback is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeoutPublisher(this, publisher, maybeSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> unsubscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeUnsubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Maybe<R> zipWith(@NonNull MaybeSource<? extends U> maybeSource, @NonNull BiFunction<? super T, ? super U, ? extends R> biFunction) {
        Objects.requireNonNull(maybeSource, "other is null");
        return zip(this, maybeSource, biFunction);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final TestObserver<T> test() {
        TestObserver<T> testObserver = new TestObserver<>();
        subscribe(testObserver);
        return testObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final TestObserver<T> test(boolean z) {
        TestObserver<T> testObserver = new TestObserver<>();
        if (z) {
            testObserver.dispose();
        }
        subscribe(testObserver);
        return testObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromOptional(@NonNull Optional<T> optional) {
        Objects.requireNonNull(optional, "optional is null");
        return (Maybe) optional.map($$Lambda$2F_0PL0U2sjObY6vmNHRV_X8jJA.INSTANCE).orElseGet($$Lambda$xdX11GqNOwAcsyGUAYvJ0EiGgPE.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromCompletionStage(@NonNull CompletionStage<T> completionStage) {
        Objects.requireNonNull(completionStage, "stage is null");
        return RxJavaPlugins.onAssembly(new MaybeFromCompletionStage(completionStage));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> mapOptional(@NonNull Function<? super T, Optional<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeMapOptional(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> toCompletionStage() {
        return (CompletionStage) subscribeWith(new CompletionStageConsumer(false, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> toCompletionStage(@Nullable T t) {
        return (CompletionStage) subscribeWith(new CompletionStageConsumer(true, t));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flattenStreamAsFlowable(@NonNull Function<? super T, ? extends Stream<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlattenStreamAsFlowable(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flattenStreamAsObservable(@NonNull Function<? super T, ? extends Stream<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlattenStreamAsObservable(this, function));
    }
}
