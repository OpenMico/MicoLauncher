package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.BackpressureKind;
import io.reactivex.rxjava3.annotations.BackpressureSupport;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
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
import io.reactivex.rxjava3.internal.fuseable.FuseToMaybe;
import io.reactivex.rxjava3.internal.fuseable.FuseToObservable;
import io.reactivex.rxjava3.internal.jdk8.CompletionStageConsumer;
import io.reactivex.rxjava3.internal.jdk8.SingleFlattenStreamAsFlowable;
import io.reactivex.rxjava3.internal.jdk8.SingleFlattenStreamAsObservable;
import io.reactivex.rxjava3.internal.jdk8.SingleFromCompletionStage;
import io.reactivex.rxjava3.internal.jdk8.SingleMapOptional;
import io.reactivex.rxjava3.internal.observers.BiConsumerSingleObserver;
import io.reactivex.rxjava3.internal.observers.BlockingDisposableMultiObserver;
import io.reactivex.rxjava3.internal.observers.BlockingMultiObserver;
import io.reactivex.rxjava3.internal.observers.ConsumerSingleObserver;
import io.reactivex.rxjava3.internal.observers.FutureMultiObserver;
import io.reactivex.rxjava3.internal.observers.SafeSingleObserver;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromSingle;
import io.reactivex.rxjava3.internal.operators.completable.CompletableToFlowable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapSinglePublisher;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSingleSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFilterSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToSingle;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapSinglePublisher;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapSinglePublisher;
import io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapSingle;
import io.reactivex.rxjava3.internal.operators.mixed.SingleFlatMapObservable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSingleSingle;
import io.reactivex.rxjava3.internal.operators.single.SingleAmb;
import io.reactivex.rxjava3.internal.operators.single.SingleCache;
import io.reactivex.rxjava3.internal.operators.single.SingleContains;
import io.reactivex.rxjava3.internal.operators.single.SingleCreate;
import io.reactivex.rxjava3.internal.operators.single.SingleDefer;
import io.reactivex.rxjava3.internal.operators.single.SingleDelay;
import io.reactivex.rxjava3.internal.operators.single.SingleDelayWithCompletable;
import io.reactivex.rxjava3.internal.operators.single.SingleDelayWithObservable;
import io.reactivex.rxjava3.internal.operators.single.SingleDelayWithPublisher;
import io.reactivex.rxjava3.internal.operators.single.SingleDelayWithSingle;
import io.reactivex.rxjava3.internal.operators.single.SingleDematerialize;
import io.reactivex.rxjava3.internal.operators.single.SingleDetach;
import io.reactivex.rxjava3.internal.operators.single.SingleDoAfterSuccess;
import io.reactivex.rxjava3.internal.operators.single.SingleDoAfterTerminate;
import io.reactivex.rxjava3.internal.operators.single.SingleDoFinally;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnDispose;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnError;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnEvent;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnLifecycle;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnSubscribe;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnSuccess;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnTerminate;
import io.reactivex.rxjava3.internal.operators.single.SingleEquals;
import io.reactivex.rxjava3.internal.operators.single.SingleError;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMap;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapBiSelector;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapCompletable;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapIterableFlowable;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapIterableObservable;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapMaybe;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapNotification;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapPublisher;
import io.reactivex.rxjava3.internal.operators.single.SingleFromCallable;
import io.reactivex.rxjava3.internal.operators.single.SingleFromPublisher;
import io.reactivex.rxjava3.internal.operators.single.SingleFromSupplier;
import io.reactivex.rxjava3.internal.operators.single.SingleFromUnsafeSource;
import io.reactivex.rxjava3.internal.operators.single.SingleHide;
import io.reactivex.rxjava3.internal.operators.single.SingleInternalHelper;
import io.reactivex.rxjava3.internal.operators.single.SingleJust;
import io.reactivex.rxjava3.internal.operators.single.SingleLift;
import io.reactivex.rxjava3.internal.operators.single.SingleMap;
import io.reactivex.rxjava3.internal.operators.single.SingleMaterialize;
import io.reactivex.rxjava3.internal.operators.single.SingleNever;
import io.reactivex.rxjava3.internal.operators.single.SingleObserveOn;
import io.reactivex.rxjava3.internal.operators.single.SingleOnErrorComplete;
import io.reactivex.rxjava3.internal.operators.single.SingleOnErrorReturn;
import io.reactivex.rxjava3.internal.operators.single.SingleResumeNext;
import io.reactivex.rxjava3.internal.operators.single.SingleSubscribeOn;
import io.reactivex.rxjava3.internal.operators.single.SingleTakeUntil;
import io.reactivex.rxjava3.internal.operators.single.SingleTimeInterval;
import io.reactivex.rxjava3.internal.operators.single.SingleTimeout;
import io.reactivex.rxjava3.internal.operators.single.SingleTimer;
import io.reactivex.rxjava3.internal.operators.single.SingleToFlowable;
import io.reactivex.rxjava3.internal.operators.single.SingleToObservable;
import io.reactivex.rxjava3.internal.operators.single.SingleUnsubscribeOn;
import io.reactivex.rxjava3.internal.operators.single.SingleUsing;
import io.reactivex.rxjava3.internal.operators.single.SingleZipArray;
import io.reactivex.rxjava3.internal.operators.single.SingleZipIterable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;

/* loaded from: classes4.dex */
public abstract class Single<T> implements SingleSource<T> {
    protected abstract void subscribeActual(@NonNull SingleObserver<? super T> singleObserver);

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> amb(@NonNull Iterable<? extends SingleSource<? extends T>> iterable) {
        Objects.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new SingleAmb(null, iterable));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Single<T> ambArray(@NonNull SingleSource<? extends T>... singleSourceArr) {
        Objects.requireNonNull(singleSourceArr, "sources is null");
        if (singleSourceArr.length == 0) {
            return error(SingleInternalHelper.emptyThrower());
        }
        if (singleSourceArr.length == 1) {
            return wrap(singleSourceArr[0]);
        }
        return RxJavaPlugins.onAssembly(new SingleAmb(singleSourceArr, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Iterable<? extends SingleSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).concatMapSingleDelayError(Functions.identity(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concat(@NonNull ObservableSource<? extends SingleSource<? extends T>> observableSource) {
        Objects.requireNonNull(observableSource, "sources is null");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapSingle(observableSource, Functions.identity(), ErrorMode.IMMEDIATE, 2));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends SingleSource<? extends T>> publisher) {
        return concat(publisher, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends SingleSource<? extends T>> publisher, int i) {
        Objects.requireNonNull(publisher, "sources is null");
        ObjectHelper.verifyPositive(i, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapSinglePublisher(publisher, Functions.identity(), ErrorMode.IMMEDIATE, i));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull SingleSource<? extends T> singleSource, @NonNull SingleSource<? extends T> singleSource2) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        return Flowable.fromArray(singleSource, singleSource2).concatMapSingleDelayError(Functions.identity(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull SingleSource<? extends T> singleSource, @NonNull SingleSource<? extends T> singleSource2, @NonNull SingleSource<? extends T> singleSource3) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        return Flowable.fromArray(singleSource, singleSource2, singleSource3).concatMapSingleDelayError(Functions.identity(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull SingleSource<? extends T> singleSource, @NonNull SingleSource<? extends T> singleSource2, @NonNull SingleSource<? extends T> singleSource3, @NonNull SingleSource<? extends T> singleSource4) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        Objects.requireNonNull(singleSource4, "source4 is null");
        return Flowable.fromArray(singleSource, singleSource2, singleSource3, singleSource4).concatMapSingleDelayError(Functions.identity(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArray(@NonNull SingleSource<? extends T>... singleSourceArr) {
        return Flowable.fromArray(singleSourceArr).concatMapSingleDelayError(Functions.identity(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayDelayError(@NonNull SingleSource<? extends T>... singleSourceArr) {
        return Flowable.fromArray(singleSourceArr).concatMapSingleDelayError(Functions.identity(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEager(@NonNull SingleSource<? extends T>... singleSourceArr) {
        return Flowable.fromArray(singleSourceArr).concatMapEager(SingleInternalHelper.toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEagerDelayError(@NonNull SingleSource<? extends T>... singleSourceArr) {
        return Flowable.fromArray(singleSourceArr).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Iterable<? extends SingleSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).concatMapSingleDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> publisher) {
        return Flowable.fromPublisher(publisher).concatMapSingleDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> publisher, int i) {
        return Flowable.fromPublisher(publisher).concatMapSingleDelayError(Functions.identity(), true, i);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Iterable<? extends SingleSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Iterable<? extends SingleSource<? extends T>> iterable, int i) {
        return Flowable.fromIterable(iterable).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), false, i, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Publisher<? extends SingleSource<? extends T>> publisher) {
        return Flowable.fromPublisher(publisher).concatMapEager(SingleInternalHelper.toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Publisher<? extends SingleSource<? extends T>> publisher, int i) {
        return Flowable.fromPublisher(publisher).concatMapEager(SingleInternalHelper.toFlowable(), i, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Iterable<? extends SingleSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Iterable<? extends SingleSource<? extends T>> iterable, int i) {
        return Flowable.fromIterable(iterable).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), true, i, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> publisher) {
        return Flowable.fromPublisher(publisher).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> publisher, int i) {
        return Flowable.fromPublisher(publisher).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), true, i, 1);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> create(@NonNull SingleOnSubscribe<T> singleOnSubscribe) {
        Objects.requireNonNull(singleOnSubscribe, "source is null");
        return RxJavaPlugins.onAssembly(new SingleCreate(singleOnSubscribe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> defer(@NonNull Supplier<? extends SingleSource<? extends T>> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new SingleDefer(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> error(@NonNull Supplier<? extends Throwable> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new SingleError(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> error(@NonNull Throwable th) {
        Objects.requireNonNull(th, "throwable is null");
        return error(Functions.justSupplier(th));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromCallable(@NonNull Callable<? extends T> callable) {
        Objects.requireNonNull(callable, "callable is null");
        return RxJavaPlugins.onAssembly(new SingleFromCallable(callable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromFuture(@NonNull Future<? extends T> future) {
        return a(Flowable.fromFuture(future));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromFuture(@NonNull Future<? extends T> future, long j, @NonNull TimeUnit timeUnit) {
        return a(Flowable.fromFuture(future, j, timeUnit));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromMaybe(@NonNull MaybeSource<T> maybeSource) {
        Objects.requireNonNull(maybeSource, "maybe is null");
        return RxJavaPlugins.onAssembly(new MaybeToSingle(maybeSource, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromMaybe(@NonNull MaybeSource<T> maybeSource, @NonNull T t) {
        Objects.requireNonNull(maybeSource, "maybe is null");
        Objects.requireNonNull(t, "defaultItem is null");
        return RxJavaPlugins.onAssembly(new MaybeToSingle(maybeSource, t));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static <T> Single<T> fromPublisher(@NonNull Publisher<? extends T> publisher) {
        Objects.requireNonNull(publisher, "publisher is null");
        return RxJavaPlugins.onAssembly(new SingleFromPublisher(publisher));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromObservable(@NonNull ObservableSource<? extends T> observableSource) {
        Objects.requireNonNull(observableSource, "observable is null");
        return RxJavaPlugins.onAssembly(new ObservableSingleSingle(observableSource, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromSupplier(@NonNull Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new SingleFromSupplier(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> just(T t) {
        Objects.requireNonNull(t, "item is null");
        return RxJavaPlugins.onAssembly(new SingleJust(t));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Iterable<? extends SingleSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).flatMapSingle(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends SingleSource<? extends T>> publisher) {
        Objects.requireNonNull(publisher, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapSinglePublisher(publisher, Functions.identity(), false, Integer.MAX_VALUE));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> merge(@NonNull SingleSource<? extends SingleSource<? extends T>> singleSource) {
        Objects.requireNonNull(singleSource, "source is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMap(singleSource, Functions.identity()));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull SingleSource<? extends T> singleSource, @NonNull SingleSource<? extends T> singleSource2) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        return Flowable.fromArray(singleSource, singleSource2).flatMapSingle(Functions.identity(), false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull SingleSource<? extends T> singleSource, @NonNull SingleSource<? extends T> singleSource2, @NonNull SingleSource<? extends T> singleSource3) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        return Flowable.fromArray(singleSource, singleSource2, singleSource3).flatMapSingle(Functions.identity(), false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull SingleSource<? extends T> singleSource, @NonNull SingleSource<? extends T> singleSource2, @NonNull SingleSource<? extends T> singleSource3, @NonNull SingleSource<? extends T> singleSource4) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        Objects.requireNonNull(singleSource4, "source4 is null");
        return Flowable.fromArray(singleSource, singleSource2, singleSource3, singleSource4).flatMapSingle(Functions.identity(), false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArray(SingleSource<? extends T>... singleSourceArr) {
        return Flowable.fromArray(singleSourceArr).flatMapSingle(Functions.identity(), false, Math.max(1, singleSourceArr.length));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArrayDelayError(@NonNull SingleSource<? extends T>... singleSourceArr) {
        return Flowable.fromArray(singleSourceArr).flatMapSingle(Functions.identity(), true, Math.max(1, singleSourceArr.length));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Iterable<? extends SingleSource<? extends T>> iterable) {
        return Flowable.fromIterable(iterable).flatMapSingle(Functions.identity(), true, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> publisher) {
        Objects.requireNonNull(publisher, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapSinglePublisher(publisher, Functions.identity(), true, Integer.MAX_VALUE));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull SingleSource<? extends T> singleSource, @NonNull SingleSource<? extends T> singleSource2) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        return Flowable.fromArray(singleSource, singleSource2).flatMapSingle(Functions.identity(), true, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull SingleSource<? extends T> singleSource, @NonNull SingleSource<? extends T> singleSource2, @NonNull SingleSource<? extends T> singleSource3) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        return Flowable.fromArray(singleSource, singleSource2, singleSource3).flatMapSingle(Functions.identity(), true, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull SingleSource<? extends T> singleSource, @NonNull SingleSource<? extends T> singleSource2, @NonNull SingleSource<? extends T> singleSource3, @NonNull SingleSource<? extends T> singleSource4) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        Objects.requireNonNull(singleSource4, "source4 is null");
        return Flowable.fromArray(singleSource, singleSource2, singleSource3, singleSource4).flatMapSingle(Functions.identity(), true, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> never() {
        return RxJavaPlugins.onAssembly(SingleNever.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public static Single<Long> timer(long j, @NonNull TimeUnit timeUnit) {
        return timer(j, timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public static Single<Long> timer(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleTimer(j, timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull SingleSource<? extends T> singleSource, @NonNull SingleSource<? extends T> singleSource2) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        return RxJavaPlugins.onAssembly(new SingleEquals(singleSource, singleSource2));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNext(@NonNull Publisher<? extends SingleSource<? extends T>> publisher) {
        Objects.requireNonNull(publisher, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapSinglePublisher(publisher, Functions.identity(), false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNextDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> publisher) {
        Objects.requireNonNull(publisher, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapSinglePublisher(publisher, Functions.identity(), true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> unsafeCreate(@NonNull SingleSource<T> singleSource) {
        Objects.requireNonNull(singleSource, "onSubscribe is null");
        if (!(singleSource instanceof Single)) {
            return RxJavaPlugins.onAssembly(new SingleFromUnsafeSource(singleSource));
        }
        throw new IllegalArgumentException("unsafeCreate(Single) should be upgraded");
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, U> Single<T> using(@NonNull Supplier<U> supplier, @NonNull Function<? super U, ? extends SingleSource<? extends T>> function, @NonNull Consumer<? super U> consumer) {
        return using(supplier, function, consumer, true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, U> Single<T> using(@NonNull Supplier<U> supplier, @NonNull Function<? super U, ? extends SingleSource<? extends T>> function, @NonNull Consumer<? super U> consumer, boolean z) {
        Objects.requireNonNull(supplier, "resourceSupplier is null");
        Objects.requireNonNull(function, "sourceSupplier is null");
        Objects.requireNonNull(consumer, "resourceCleanup is null");
        return RxJavaPlugins.onAssembly(new SingleUsing(supplier, function, consumer, z));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> wrap(@NonNull SingleSource<T> singleSource) {
        Objects.requireNonNull(singleSource, "source is null");
        if (singleSource instanceof Single) {
            return RxJavaPlugins.onAssembly((Single) singleSource);
        }
        return RxJavaPlugins.onAssembly(new SingleFromUnsafeSource(singleSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Single<R> zip(@NonNull Iterable<? extends SingleSource<? extends T>> iterable, @NonNull Function<? super Object[], ? extends R> function) {
        Objects.requireNonNull(function, "zipper is null");
        Objects.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new SingleZipIterable(iterable, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, R> Single<R> zip(@NonNull SingleSource<? extends T1> singleSource, @NonNull SingleSource<? extends T2> singleSource2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(biFunction, "zipper is null");
        return zipArray(Functions.toFunction(biFunction), singleSource, singleSource2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, R> Single<R> zip(@NonNull SingleSource<? extends T1> singleSource, @NonNull SingleSource<? extends T2> singleSource2, @NonNull SingleSource<? extends T3> singleSource3, @NonNull Function3<? super T1, ? super T2, ? super T3, ? extends R> function3) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        Objects.requireNonNull(function3, "zipper is null");
        return zipArray(Functions.toFunction(function3), singleSource, singleSource2, singleSource3);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, R> Single<R> zip(@NonNull SingleSource<? extends T1> singleSource, @NonNull SingleSource<? extends T2> singleSource2, @NonNull SingleSource<? extends T3> singleSource3, @NonNull SingleSource<? extends T4> singleSource4, @NonNull Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> function4) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        Objects.requireNonNull(singleSource4, "source4 is null");
        Objects.requireNonNull(function4, "zipper is null");
        return zipArray(Functions.toFunction(function4), singleSource, singleSource2, singleSource3, singleSource4);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, R> Single<R> zip(@NonNull SingleSource<? extends T1> singleSource, @NonNull SingleSource<? extends T2> singleSource2, @NonNull SingleSource<? extends T3> singleSource3, @NonNull SingleSource<? extends T4> singleSource4, @NonNull SingleSource<? extends T5> singleSource5, @NonNull Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> function5) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        Objects.requireNonNull(singleSource4, "source4 is null");
        Objects.requireNonNull(singleSource5, "source5 is null");
        Objects.requireNonNull(function5, "zipper is null");
        return zipArray(Functions.toFunction(function5), singleSource, singleSource2, singleSource3, singleSource4, singleSource5);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, R> Single<R> zip(@NonNull SingleSource<? extends T1> singleSource, @NonNull SingleSource<? extends T2> singleSource2, @NonNull SingleSource<? extends T3> singleSource3, @NonNull SingleSource<? extends T4> singleSource4, @NonNull SingleSource<? extends T5> singleSource5, @NonNull SingleSource<? extends T6> singleSource6, @NonNull Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> function6) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        Objects.requireNonNull(singleSource4, "source4 is null");
        Objects.requireNonNull(singleSource5, "source5 is null");
        Objects.requireNonNull(singleSource6, "source6 is null");
        Objects.requireNonNull(function6, "zipper is null");
        return zipArray(Functions.toFunction(function6), singleSource, singleSource2, singleSource3, singleSource4, singleSource5, singleSource6);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, R> Single<R> zip(@NonNull SingleSource<? extends T1> singleSource, @NonNull SingleSource<? extends T2> singleSource2, @NonNull SingleSource<? extends T3> singleSource3, @NonNull SingleSource<? extends T4> singleSource4, @NonNull SingleSource<? extends T5> singleSource5, @NonNull SingleSource<? extends T6> singleSource6, @NonNull SingleSource<? extends T7> singleSource7, @NonNull Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> function7) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        Objects.requireNonNull(singleSource4, "source4 is null");
        Objects.requireNonNull(singleSource5, "source5 is null");
        Objects.requireNonNull(singleSource6, "source6 is null");
        Objects.requireNonNull(singleSource7, "source7 is null");
        Objects.requireNonNull(function7, "zipper is null");
        return zipArray(Functions.toFunction(function7), singleSource, singleSource2, singleSource3, singleSource4, singleSource5, singleSource6, singleSource7);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Single<R> zip(@NonNull SingleSource<? extends T1> singleSource, @NonNull SingleSource<? extends T2> singleSource2, @NonNull SingleSource<? extends T3> singleSource3, @NonNull SingleSource<? extends T4> singleSource4, @NonNull SingleSource<? extends T5> singleSource5, @NonNull SingleSource<? extends T6> singleSource6, @NonNull SingleSource<? extends T7> singleSource7, @NonNull SingleSource<? extends T8> singleSource8, @NonNull Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> function8) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        Objects.requireNonNull(singleSource4, "source4 is null");
        Objects.requireNonNull(singleSource5, "source5 is null");
        Objects.requireNonNull(singleSource6, "source6 is null");
        Objects.requireNonNull(singleSource7, "source7 is null");
        Objects.requireNonNull(singleSource8, "source8 is null");
        Objects.requireNonNull(function8, "zipper is null");
        return zipArray(Functions.toFunction(function8), singleSource, singleSource2, singleSource3, singleSource4, singleSource5, singleSource6, singleSource7, singleSource8);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Single<R> zip(@NonNull SingleSource<? extends T1> singleSource, @NonNull SingleSource<? extends T2> singleSource2, @NonNull SingleSource<? extends T3> singleSource3, @NonNull SingleSource<? extends T4> singleSource4, @NonNull SingleSource<? extends T5> singleSource5, @NonNull SingleSource<? extends T6> singleSource6, @NonNull SingleSource<? extends T7> singleSource7, @NonNull SingleSource<? extends T8> singleSource8, @NonNull SingleSource<? extends T9> singleSource9, @NonNull Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> function9) {
        Objects.requireNonNull(singleSource, "source1 is null");
        Objects.requireNonNull(singleSource2, "source2 is null");
        Objects.requireNonNull(singleSource3, "source3 is null");
        Objects.requireNonNull(singleSource4, "source4 is null");
        Objects.requireNonNull(singleSource5, "source5 is null");
        Objects.requireNonNull(singleSource6, "source6 is null");
        Objects.requireNonNull(singleSource7, "source7 is null");
        Objects.requireNonNull(singleSource8, "source8 is null");
        Objects.requireNonNull(singleSource9, "source9 is null");
        Objects.requireNonNull(function9, "zipper is null");
        return zipArray(Functions.toFunction(function9), singleSource, singleSource2, singleSource3, singleSource4, singleSource5, singleSource6, singleSource7, singleSource8, singleSource9);
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T, R> Single<R> zipArray(@NonNull Function<? super Object[], ? extends R> function, @NonNull SingleSource<? extends T>... singleSourceArr) {
        Objects.requireNonNull(function, "zipper is null");
        Objects.requireNonNull(singleSourceArr, "sources is null");
        if (singleSourceArr.length == 0) {
            return error(new NoSuchElementException());
        }
        return RxJavaPlugins.onAssembly(new SingleZipArray(singleSourceArr, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> ambWith(@NonNull SingleSource<? extends T> singleSource) {
        Objects.requireNonNull(singleSource, "other is null");
        return ambArray(this, singleSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> hide() {
        return RxJavaPlugins.onAssembly(new SingleHide(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> compose(@NonNull SingleTransformer<? super T, ? extends R> singleTransformer) {
        return wrap(((SingleTransformer) Objects.requireNonNull(singleTransformer, "transformer is null")).apply(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> cache() {
        return RxJavaPlugins.onAssembly(new SingleCache(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Single<U> cast(@NonNull Class<? extends U> cls) {
        Objects.requireNonNull(cls, "clazz is null");
        return (Single<U>) map(Functions.castFunction(cls));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> concatMap(@NonNull Function<? super T, ? extends SingleSource<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMap(this, function));
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
    public final <R> Maybe<R> concatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> function) {
        return flatMapMaybe(function);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> concatWith(@NonNull SingleSource<? extends T> singleSource) {
        return concat(this, singleSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<T> delay(long j, @NonNull TimeUnit timeUnit) {
        return delay(j, timeUnit, Schedulers.computation(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<T> delay(long j, @NonNull TimeUnit timeUnit, boolean z) {
        return delay(j, timeUnit, Schedulers.computation(), z);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> delay(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return delay(j, timeUnit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> delay(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler, boolean z) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleDelay(this, j, timeUnit, scheduler, z));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> delaySubscription(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new SingleDelayWithCompletable(this, completableSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Single<T> delaySubscription(@NonNull SingleSource<U> singleSource) {
        Objects.requireNonNull(singleSource, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new SingleDelayWithSingle(this, singleSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Single<T> delaySubscription(@NonNull ObservableSource<U> observableSource) {
        Objects.requireNonNull(observableSource, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new SingleDelayWithObservable(this, observableSource));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Single<T> delaySubscription(@NonNull Publisher<U> publisher) {
        Objects.requireNonNull(publisher, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new SingleDelayWithPublisher(this, publisher));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<T> delaySubscription(long j, @NonNull TimeUnit timeUnit) {
        return delaySubscription(j, timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> delaySubscription(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return delaySubscription(Observable.timer(j, timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> dematerialize(@NonNull Function<? super T, Notification<R>> function) {
        Objects.requireNonNull(function, "selector is null");
        return RxJavaPlugins.onAssembly(new SingleDematerialize(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doAfterSuccess(@NonNull Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "onAfterSuccess is null");
        return RxJavaPlugins.onAssembly(new SingleDoAfterSuccess(this, consumer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doAfterTerminate(@NonNull Action action) {
        Objects.requireNonNull(action, "onAfterTerminate is null");
        return RxJavaPlugins.onAssembly(new SingleDoAfterTerminate(this, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doFinally(@NonNull Action action) {
        Objects.requireNonNull(action, "onFinally is null");
        return RxJavaPlugins.onAssembly(new SingleDoFinally(this, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnLifecycle(@NonNull Consumer<? super Disposable> consumer, @NonNull Action action) {
        Objects.requireNonNull(consumer, "onSubscribe is null");
        Objects.requireNonNull(action, "onDispose is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnLifecycle(this, consumer, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnSubscribe(@NonNull Consumer<? super Disposable> consumer) {
        Objects.requireNonNull(consumer, "onSubscribe is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnSubscribe(this, consumer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnTerminate(@NonNull Action action) {
        Objects.requireNonNull(action, "onTerminate is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnTerminate(this, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnSuccess(@NonNull Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "onSuccess is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnSuccess(this, consumer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnEvent(@NonNull BiConsumer<? super T, ? super Throwable> biConsumer) {
        Objects.requireNonNull(biConsumer, "onEvent is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnEvent(this, biConsumer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnError(@NonNull Consumer<? super Throwable> consumer) {
        Objects.requireNonNull(consumer, "onError is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnError(this, consumer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnDispose(@NonNull Action action) {
        Objects.requireNonNull(action, "onDispose is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnDispose(this, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> filter(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new MaybeFilterSingle(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> flatMap(@NonNull Function<? super T, ? extends SingleSource<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMap(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Single<R> flatMap(@NonNull Function<? super T, ? extends SingleSource<? extends U>> function, @NonNull BiFunction<? super T, ? super U, ? extends R> biFunction) {
        Objects.requireNonNull(function, "mapper is null");
        Objects.requireNonNull(biFunction, "combiner is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapBiSelector(this, function, biFunction));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> flatMap(@NonNull Function<? super T, ? extends SingleSource<? extends R>> function, @NonNull Function<? super Throwable, ? extends SingleSource<? extends R>> function2) {
        Objects.requireNonNull(function, "onSuccessMapper is null");
        Objects.requireNonNull(function2, "onErrorMapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapNotification(this, function, function2));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> flatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapMaybe(this, function));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMapPublisher(@NonNull Function<? super T, ? extends Publisher<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapPublisher(this, function));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<U> flattenAsFlowable(@NonNull Function<? super T, ? extends Iterable<? extends U>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapIterableFlowable(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<U> flattenAsObservable(@NonNull Function<? super T, ? extends Iterable<? extends U>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapIterableObservable(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMapObservable(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapObservable(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable flatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapCompletable(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final T blockingGet() {
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        return (T) blockingMultiObserver.blockingGet();
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe() {
        blockingSubscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> consumer) {
        blockingSubscribe(consumer, Functions.ERROR_CONSUMER);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> consumer, @NonNull Consumer<? super Throwable> consumer2) {
        Objects.requireNonNull(consumer, "onSuccess is null");
        Objects.requireNonNull(consumer2, "onError is null");
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        blockingMultiObserver.blockingConsume(consumer, consumer2, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull SingleObserver<? super T> singleObserver) {
        Objects.requireNonNull(singleObserver, "observer is null");
        BlockingDisposableMultiObserver blockingDisposableMultiObserver = new BlockingDisposableMultiObserver();
        singleObserver.onSubscribe(blockingDisposableMultiObserver);
        subscribe(blockingDisposableMultiObserver);
        blockingDisposableMultiObserver.blockingConsume(singleObserver);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> lift(@NonNull SingleOperator<? extends R, ? super T> singleOperator) {
        Objects.requireNonNull(singleOperator, "lift is null");
        return RxJavaPlugins.onAssembly(new SingleLift(this, singleOperator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> map(@NonNull Function<? super T, ? extends R> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleMap(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Notification<T>> materialize() {
        return RxJavaPlugins.onAssembly(new SingleMaterialize(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> contains(@NonNull Object obj) {
        return contains(obj, ObjectHelper.equalsPredicate());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> contains(@NonNull Object obj, @NonNull BiPredicate<Object, Object> biPredicate) {
        Objects.requireNonNull(obj, "item is null");
        Objects.requireNonNull(biPredicate, "comparer is null");
        return RxJavaPlugins.onAssembly(new SingleContains(this, obj, biPredicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> mergeWith(@NonNull SingleSource<? extends T> singleSource) {
        return merge(this, singleSource);
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
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> observeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleObserveOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> onErrorReturn(@NonNull Function<Throwable, ? extends T> function) {
        Objects.requireNonNull(function, "itemSupplier is null");
        return RxJavaPlugins.onAssembly(new SingleOnErrorReturn(this, function, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> onErrorReturnItem(@NonNull T t) {
        Objects.requireNonNull(t, "item is null");
        return RxJavaPlugins.onAssembly(new SingleOnErrorReturn(this, null, t));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> onErrorResumeWith(@NonNull SingleSource<? extends T> singleSource) {
        Objects.requireNonNull(singleSource, "fallback is null");
        return onErrorResumeNext(Functions.justFunction(singleSource));
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
        return RxJavaPlugins.onAssembly(new SingleOnErrorComplete(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> onErrorResumeNext(@NonNull Function<? super Throwable, ? extends SingleSource<? extends T>> function) {
        Objects.requireNonNull(function, "fallbackSupplier is null");
        return RxJavaPlugins.onAssembly(new SingleResumeNext(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> onTerminateDetach() {
        return RxJavaPlugins.onAssembly(new SingleDetach(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeat() {
        return toFlowable().repeat();
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
    public final Flowable<T> repeatWhen(@NonNull Function<? super Flowable<Object>, ? extends Publisher<?>> function) {
        return toFlowable().repeatWhen(function);
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
    @SchedulerSupport("none")
    public final Single<T> retry() {
        return a(toFlowable().retry());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retry(long j) {
        return a(toFlowable().retry(j));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retry(@NonNull BiPredicate<? super Integer, ? super Throwable> biPredicate) {
        return a(toFlowable().retry(biPredicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retry(long j, @NonNull Predicate<? super Throwable> predicate) {
        return a(toFlowable().retry(j, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retry(@NonNull Predicate<? super Throwable> predicate) {
        return a(toFlowable().retry(predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retryUntil(@NonNull BooleanSupplier booleanSupplier) {
        Objects.requireNonNull(booleanSupplier, "stop is null");
        return retry(Long.MAX_VALUE, Functions.predicateReverseFor(booleanSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retryWhen(@NonNull Function<? super Flowable<Throwable>, ? extends Publisher<?>> function) {
        return a(toFlowable().retryWhen(function));
    }

    @SchedulerSupport("none")
    public final void safeSubscribe(@NonNull SingleObserver<? super T> singleObserver) {
        Objects.requireNonNull(singleObserver, "observer is null");
        subscribe(new SafeSingleObserver(singleObserver));
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
        return Flowable.concat(wrap(singleSource).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull MaybeSource<T> maybeSource) {
        Objects.requireNonNull(maybeSource, "other is null");
        return Flowable.concat(Maybe.wrap(maybeSource).toFlowable(), toFlowable());
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
        return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull BiConsumer<? super T, ? super Throwable> biConsumer) {
        Objects.requireNonNull(biConsumer, "onCallback is null");
        BiConsumerSingleObserver biConsumerSingleObserver = new BiConsumerSingleObserver(biConsumer);
        subscribe(biConsumerSingleObserver);
        return biConsumerSingleObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> consumer) {
        return subscribe(consumer, Functions.ON_ERROR_MISSING);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> consumer, @NonNull Consumer<? super Throwable> consumer2) {
        Objects.requireNonNull(consumer, "onSuccess is null");
        Objects.requireNonNull(consumer2, "onError is null");
        ConsumerSingleObserver consumerSingleObserver = new ConsumerSingleObserver(consumer, consumer2);
        subscribe(consumerSingleObserver);
        return consumerSingleObserver;
    }

    @Override // io.reactivex.rxjava3.core.SingleSource
    @SchedulerSupport("none")
    public final void subscribe(@NonNull SingleObserver<? super T> singleObserver) {
        Objects.requireNonNull(singleObserver, "observer is null");
        SingleObserver<? super T> onSubscribe = RxJavaPlugins.onSubscribe(this, singleObserver);
        Objects.requireNonNull(onSubscribe, "The RxJavaPlugins.onSubscribe hook returned a null SingleObserver. Please check the handler provided to RxJavaPlugins.setOnSingleSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
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
    @SchedulerSupport("none")
    public final <E extends SingleObserver<? super T>> E subscribeWith(E e) {
        subscribe(e);
        return e;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> subscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleSubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<Timed<T>> timeInterval() {
        return timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<Timed<T>> timeInterval(@NonNull Scheduler scheduler) {
        return timeInterval(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<Timed<T>> timeInterval(@NonNull TimeUnit timeUnit) {
        return timeInterval(timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<Timed<T>> timeInterval(@NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleTimeInterval(this, timeUnit, scheduler, true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<Timed<T>> timestamp() {
        return timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<Timed<T>> timestamp(@NonNull Scheduler scheduler) {
        return timestamp(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<Timed<T>> timestamp(@NonNull TimeUnit timeUnit) {
        return timestamp(timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<Timed<T>> timestamp(@NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleTimeInterval(this, timeUnit, scheduler, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> takeUntil(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "other is null");
        return takeUntil(new CompletableToFlowable(completableSource));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <E> Single<T> takeUntil(@NonNull Publisher<E> publisher) {
        Objects.requireNonNull(publisher, "other is null");
        return RxJavaPlugins.onAssembly(new SingleTakeUntil(this, publisher));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <E> Single<T> takeUntil(@NonNull SingleSource<? extends E> singleSource) {
        Objects.requireNonNull(singleSource, "other is null");
        return takeUntil(new SingleToFlowable(singleSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<T> timeout(long j, @NonNull TimeUnit timeUnit) {
        return a(j, timeUnit, Schedulers.computation(), null);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> timeout(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return a(j, timeUnit, scheduler, null);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> timeout(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler, @NonNull SingleSource<? extends T> singleSource) {
        Objects.requireNonNull(singleSource, "fallback is null");
        return a(j, timeUnit, scheduler, singleSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<T> timeout(long j, @NonNull TimeUnit timeUnit, @NonNull SingleSource<? extends T> singleSource) {
        Objects.requireNonNull(singleSource, "fallback is null");
        return a(j, timeUnit, Schedulers.computation(), singleSource);
    }

    private Single<T> a(long j, TimeUnit timeUnit, Scheduler scheduler, SingleSource<? extends T> singleSource) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleTimeout(this, j, timeUnit, scheduler, singleSource));
    }

    @CheckReturnValue
    @SchedulerSupport("none")
    public final <R> R to(@NonNull SingleConverter<T, ? extends R> singleConverter) {
        return (R) ((SingleConverter) Objects.requireNonNull(singleConverter, "converter is null")).apply(this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable ignoreElement() {
        return RxJavaPlugins.onAssembly(new CompletableFromSingle(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> toFlowable() {
        if (this instanceof FuseToFlowable) {
            return ((FuseToFlowable) this).fuseToFlowable();
        }
        return RxJavaPlugins.onAssembly(new SingleToFlowable(this));
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
    public final Maybe<T> toMaybe() {
        if (this instanceof FuseToMaybe) {
            return ((FuseToMaybe) this).fuseToMaybe();
        }
        return RxJavaPlugins.onAssembly(new MaybeFromSingle(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> toObservable() {
        if (this instanceof FuseToObservable) {
            return ((FuseToObservable) this).fuseToObservable();
        }
        return RxJavaPlugins.onAssembly(new SingleToObservable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> unsubscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleUnsubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Single<R> zipWith(@NonNull SingleSource<U> singleSource, @NonNull BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return zip(this, singleSource, biFunction);
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

    @NonNull
    private static <T> Single<T> a(@NonNull Flowable<T> flowable) {
        return RxJavaPlugins.onAssembly(new FlowableSingleSingle(flowable, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromCompletionStage(@NonNull CompletionStage<T> completionStage) {
        Objects.requireNonNull(completionStage, "stage is null");
        return RxJavaPlugins.onAssembly(new SingleFromCompletionStage(completionStage));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> mapOptional(@NonNull Function<? super T, Optional<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleMapOptional(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> toCompletionStage() {
        return (CompletionStage) subscribeWith(new CompletionStageConsumer(false, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flattenStreamAsFlowable(@NonNull Function<? super T, ? extends Stream<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlattenStreamAsFlowable(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flattenStreamAsObservable(@NonNull Function<? super T, ? extends Stream<? extends R>> function) {
        Objects.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlattenStreamAsObservable(this, function));
    }
}
