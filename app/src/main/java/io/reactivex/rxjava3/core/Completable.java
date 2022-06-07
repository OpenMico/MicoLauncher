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
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.fuseable.FuseToFlowable;
import io.reactivex.rxjava3.internal.fuseable.FuseToMaybe;
import io.reactivex.rxjava3.internal.fuseable.FuseToObservable;
import io.reactivex.rxjava3.internal.jdk8.CompletableFromCompletionStage;
import io.reactivex.rxjava3.internal.jdk8.CompletionStageConsumer;
import io.reactivex.rxjava3.internal.observers.BlockingDisposableMultiObserver;
import io.reactivex.rxjava3.internal.observers.BlockingMultiObserver;
import io.reactivex.rxjava3.internal.observers.CallbackCompletableObserver;
import io.reactivex.rxjava3.internal.observers.EmptyCompletableObserver;
import io.reactivex.rxjava3.internal.observers.FutureMultiObserver;
import io.reactivex.rxjava3.internal.observers.SafeCompletableObserver;
import io.reactivex.rxjava3.internal.operators.completable.CompletableAmb;
import io.reactivex.rxjava3.internal.operators.completable.CompletableAndThenCompletable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableCache;
import io.reactivex.rxjava3.internal.operators.completable.CompletableConcat;
import io.reactivex.rxjava3.internal.operators.completable.CompletableConcatArray;
import io.reactivex.rxjava3.internal.operators.completable.CompletableConcatIterable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableCreate;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDefer;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDelay;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDetach;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDisposeOn;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDoFinally;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDoOnEvent;
import io.reactivex.rxjava3.internal.operators.completable.CompletableEmpty;
import io.reactivex.rxjava3.internal.operators.completable.CompletableError;
import io.reactivex.rxjava3.internal.operators.completable.CompletableErrorSupplier;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromAction;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromCallable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromObservable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromPublisher;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromRunnable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromSingle;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromSupplier;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromUnsafeSource;
import io.reactivex.rxjava3.internal.operators.completable.CompletableHide;
import io.reactivex.rxjava3.internal.operators.completable.CompletableLift;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMaterialize;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMerge;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMergeArray;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMergeArrayDelayError;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMergeDelayErrorIterable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMergeIterable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableNever;
import io.reactivex.rxjava3.internal.operators.completable.CompletableObserveOn;
import io.reactivex.rxjava3.internal.operators.completable.CompletableOnErrorComplete;
import io.reactivex.rxjava3.internal.operators.completable.CompletableOnErrorReturn;
import io.reactivex.rxjava3.internal.operators.completable.CompletablePeek;
import io.reactivex.rxjava3.internal.operators.completable.CompletableResumeNext;
import io.reactivex.rxjava3.internal.operators.completable.CompletableSubscribeOn;
import io.reactivex.rxjava3.internal.operators.completable.CompletableTakeUntilCompletable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableTimeout;
import io.reactivex.rxjava3.internal.operators.completable.CompletableTimer;
import io.reactivex.rxjava3.internal.operators.completable.CompletableToFlowable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableToObservable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableToSingle;
import io.reactivex.rxjava3.internal.operators.completable.CompletableUsing;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDelayWithCompletable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromCompletable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeIgnoreElementCompletable;
import io.reactivex.rxjava3.internal.operators.mixed.CompletableAndThenObservable;
import io.reactivex.rxjava3.internal.operators.mixed.CompletableAndThenPublisher;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapCompletablePublisher;
import io.reactivex.rxjava3.internal.operators.single.SingleDelayWithCompletable;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

/* loaded from: classes4.dex */
public abstract class Completable implements CompletableSource {
    protected abstract void subscribeActual(@NonNull CompletableObserver completableObserver);

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static Completable ambArray(@NonNull CompletableSource... completableSourceArr) {
        Objects.requireNonNull(completableSourceArr, "sources is null");
        if (completableSourceArr.length == 0) {
            return complete();
        }
        if (completableSourceArr.length == 1) {
            return wrap(completableSourceArr[0]);
        }
        return RxJavaPlugins.onAssembly(new CompletableAmb(completableSourceArr, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable amb(@NonNull Iterable<? extends CompletableSource> iterable) {
        Objects.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new CompletableAmb(null, iterable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable complete() {
        return RxJavaPlugins.onAssembly(CompletableEmpty.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static Completable concatArray(@NonNull CompletableSource... completableSourceArr) {
        Objects.requireNonNull(completableSourceArr, "sources is null");
        if (completableSourceArr.length == 0) {
            return complete();
        }
        if (completableSourceArr.length == 1) {
            return wrap(completableSourceArr[0]);
        }
        return RxJavaPlugins.onAssembly(new CompletableConcatArray(completableSourceArr));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static Completable concatArrayDelayError(@NonNull CompletableSource... completableSourceArr) {
        return Flowable.fromArray(completableSourceArr).concatMapCompletableDelayError(Functions.identity(), true, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable concat(@NonNull Iterable<? extends CompletableSource> iterable) {
        Objects.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new CompletableConcatIterable(iterable));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable concat(@NonNull Publisher<? extends CompletableSource> publisher) {
        return concat(publisher, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable concat(@NonNull Publisher<? extends CompletableSource> publisher, int i) {
        Objects.requireNonNull(publisher, "sources is null");
        ObjectHelper.verifyPositive(i, "prefetch");
        return RxJavaPlugins.onAssembly(new CompletableConcat(publisher, i));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable concatDelayError(@NonNull Iterable<? extends CompletableSource> iterable) {
        return Flowable.fromIterable(iterable).concatMapCompletableDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable concatDelayError(@NonNull Publisher<? extends CompletableSource> publisher) {
        return concatDelayError(publisher, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable concatDelayError(@NonNull Publisher<? extends CompletableSource> publisher, int i) {
        return Flowable.fromPublisher(publisher).concatMapCompletableDelayError(Functions.identity(), true, i);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable create(@NonNull CompletableOnSubscribe completableOnSubscribe) {
        Objects.requireNonNull(completableOnSubscribe, "source is null");
        return RxJavaPlugins.onAssembly(new CompletableCreate(completableOnSubscribe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Single<Boolean> sequenceEqual(@NonNull CompletableSource completableSource, @NonNull CompletableSource completableSource2) {
        Objects.requireNonNull(completableSource, "source1 is null");
        Objects.requireNonNull(completableSource2, "source2 is null");
        return mergeArrayDelayError(completableSource, completableSource2).andThen(Single.just(true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable unsafeCreate(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "onSubscribe is null");
        if (!(completableSource instanceof Completable)) {
            return RxJavaPlugins.onAssembly(new CompletableFromUnsafeSource(completableSource));
        }
        throw new IllegalArgumentException("Use of unsafeCreate(Completable)!");
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable defer(@NonNull Supplier<? extends CompletableSource> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new CompletableDefer(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable error(@NonNull Supplier<? extends Throwable> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new CompletableErrorSupplier(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable error(@NonNull Throwable th) {
        Objects.requireNonNull(th, "throwable is null");
        return RxJavaPlugins.onAssembly(new CompletableError(th));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromAction(@NonNull Action action) {
        Objects.requireNonNull(action, "action is null");
        return RxJavaPlugins.onAssembly(new CompletableFromAction(action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromCallable(@NonNull Callable<?> callable) {
        Objects.requireNonNull(callable, "callable is null");
        return RxJavaPlugins.onAssembly(new CompletableFromCallable(callable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromFuture(@NonNull Future<?> future) {
        Objects.requireNonNull(future, "future is null");
        return fromAction(Functions.futureAction(future));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Completable fromMaybe(@NonNull MaybeSource<T> maybeSource) {
        Objects.requireNonNull(maybeSource, "maybe is null");
        return RxJavaPlugins.onAssembly(new MaybeIgnoreElementCompletable(maybeSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromRunnable(@NonNull Runnable runnable) {
        Objects.requireNonNull(runnable, "run is null");
        return RxJavaPlugins.onAssembly(new CompletableFromRunnable(runnable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Completable fromObservable(@NonNull ObservableSource<T> observableSource) {
        Objects.requireNonNull(observableSource, "observable is null");
        return RxJavaPlugins.onAssembly(new CompletableFromObservable(observableSource));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static <T> Completable fromPublisher(@NonNull Publisher<T> publisher) {
        Objects.requireNonNull(publisher, "publisher is null");
        return RxJavaPlugins.onAssembly(new CompletableFromPublisher(publisher));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Completable fromSingle(@NonNull SingleSource<T> singleSource) {
        Objects.requireNonNull(singleSource, "single is null");
        return RxJavaPlugins.onAssembly(new CompletableFromSingle(singleSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromSupplier(@NonNull Supplier<?> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new CompletableFromSupplier(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static Completable mergeArray(@NonNull CompletableSource... completableSourceArr) {
        Objects.requireNonNull(completableSourceArr, "sources is null");
        if (completableSourceArr.length == 0) {
            return complete();
        }
        if (completableSourceArr.length == 1) {
            return wrap(completableSourceArr[0]);
        }
        return RxJavaPlugins.onAssembly(new CompletableMergeArray(completableSourceArr));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable merge(@NonNull Iterable<? extends CompletableSource> iterable) {
        Objects.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new CompletableMergeIterable(iterable));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static Completable merge(@NonNull Publisher<? extends CompletableSource> publisher) {
        return a(publisher, Integer.MAX_VALUE, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable merge(@NonNull Publisher<? extends CompletableSource> publisher, int i) {
        return a(publisher, i, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    private static Completable a(@NonNull Publisher<? extends CompletableSource> publisher, int i, boolean z) {
        Objects.requireNonNull(publisher, "sources is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new CompletableMerge(publisher, i, z));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static Completable mergeArrayDelayError(@NonNull CompletableSource... completableSourceArr) {
        Objects.requireNonNull(completableSourceArr, "sources is null");
        return RxJavaPlugins.onAssembly(new CompletableMergeArrayDelayError(completableSourceArr));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable mergeDelayError(@NonNull Iterable<? extends CompletableSource> iterable) {
        Objects.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new CompletableMergeDelayErrorIterable(iterable));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static Completable mergeDelayError(@NonNull Publisher<? extends CompletableSource> publisher) {
        return a(publisher, Integer.MAX_VALUE, true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable mergeDelayError(@NonNull Publisher<? extends CompletableSource> publisher, int i) {
        return a(publisher, i, true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable never() {
        return RxJavaPlugins.onAssembly(CompletableNever.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public static Completable timer(long j, @NonNull TimeUnit timeUnit) {
        return timer(j, timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public static Completable timer(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableTimer(j, timeUnit, scheduler));
    }

    private static NullPointerException a(Throwable th) {
        NullPointerException nullPointerException = new NullPointerException("Actually not, but can't pass out an exception otherwise...");
        nullPointerException.initCause(th);
        return nullPointerException;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static Completable switchOnNext(@NonNull Publisher<? extends CompletableSource> publisher) {
        Objects.requireNonNull(publisher, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapCompletablePublisher(publisher, Functions.identity(), false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static Completable switchOnNextDelayError(@NonNull Publisher<? extends CompletableSource> publisher) {
        Objects.requireNonNull(publisher, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapCompletablePublisher(publisher, Functions.identity(), true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <R> Completable using(@NonNull Supplier<R> supplier, @NonNull Function<? super R, ? extends CompletableSource> function, @NonNull Consumer<? super R> consumer) {
        return using(supplier, function, consumer, true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <R> Completable using(@NonNull Supplier<R> supplier, @NonNull Function<? super R, ? extends CompletableSource> function, @NonNull Consumer<? super R> consumer, boolean z) {
        Objects.requireNonNull(supplier, "resourceSupplier is null");
        Objects.requireNonNull(function, "sourceSupplier is null");
        Objects.requireNonNull(consumer, "resourceCleanup is null");
        return RxJavaPlugins.onAssembly(new CompletableUsing(supplier, function, consumer, z));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable wrap(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "source is null");
        if (completableSource instanceof Completable) {
            return RxJavaPlugins.onAssembly((Completable) completableSource);
        }
        return RxJavaPlugins.onAssembly(new CompletableFromUnsafeSource(completableSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable ambWith(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "other is null");
        return ambArray(this, completableSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Observable<T> andThen(@NonNull ObservableSource<T> observableSource) {
        Objects.requireNonNull(observableSource, "next is null");
        return RxJavaPlugins.onAssembly(new CompletableAndThenObservable(this, observableSource));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <T> Flowable<T> andThen(@NonNull Publisher<T> publisher) {
        Objects.requireNonNull(publisher, "next is null");
        return RxJavaPlugins.onAssembly(new CompletableAndThenPublisher(this, publisher));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Single<T> andThen(@NonNull SingleSource<T> singleSource) {
        Objects.requireNonNull(singleSource, "next is null");
        return RxJavaPlugins.onAssembly(new SingleDelayWithCompletable(singleSource, this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Maybe<T> andThen(@NonNull MaybeSource<T> maybeSource) {
        Objects.requireNonNull(maybeSource, "next is null");
        return RxJavaPlugins.onAssembly(new MaybeDelayWithCompletable(maybeSource, this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable andThen(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "next is null");
        return RxJavaPlugins.onAssembly(new CompletableAndThenCompletable(this, completableSource));
    }

    @SchedulerSupport("none")
    public final void blockingAwait() {
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        blockingMultiObserver.blockingGet();
    }

    @CheckReturnValue
    @SchedulerSupport("none")
    public final boolean blockingAwait(long j, @NonNull TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit, "unit is null");
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        return blockingMultiObserver.blockingAwait(j, timeUnit);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe() {
        blockingSubscribe(Functions.EMPTY_ACTION, Functions.ERROR_CONSUMER);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Action action) {
        blockingSubscribe(action, Functions.ERROR_CONSUMER);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Action action, @NonNull Consumer<? super Throwable> consumer) {
        Objects.requireNonNull(action, "onComplete is null");
        Objects.requireNonNull(consumer, "onError is null");
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        blockingMultiObserver.blockingConsume(Functions.emptyConsumer(), consumer, action);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull CompletableObserver completableObserver) {
        Objects.requireNonNull(completableObserver, "observer is null");
        BlockingDisposableMultiObserver blockingDisposableMultiObserver = new BlockingDisposableMultiObserver();
        completableObserver.onSubscribe(blockingDisposableMultiObserver);
        subscribe(blockingDisposableMultiObserver);
        blockingDisposableMultiObserver.blockingConsume(completableObserver);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable cache() {
        return RxJavaPlugins.onAssembly(new CompletableCache(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable compose(@NonNull CompletableTransformer completableTransformer) {
        return wrap(((CompletableTransformer) Objects.requireNonNull(completableTransformer, "transformer is null")).apply(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable concatWith(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "other is null");
        return RxJavaPlugins.onAssembly(new CompletableAndThenCompletable(this, completableSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Completable delay(long j, @NonNull TimeUnit timeUnit) {
        return delay(j, timeUnit, Schedulers.computation(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable delay(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return delay(j, timeUnit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable delay(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler, boolean z) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableDelay(this, j, timeUnit, scheduler, z));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Completable delaySubscription(long j, @NonNull TimeUnit timeUnit) {
        return delaySubscription(j, timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable delaySubscription(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return timer(j, timeUnit, scheduler).andThen(this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnComplete(@NonNull Action action) {
        return a(Functions.emptyConsumer(), Functions.emptyConsumer(), action, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnDispose(@NonNull Action action) {
        return a(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, action);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnError(@NonNull Consumer<? super Throwable> consumer) {
        return a(Functions.emptyConsumer(), consumer, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnEvent(@NonNull Consumer<? super Throwable> consumer) {
        Objects.requireNonNull(consumer, "onEvent is null");
        return RxJavaPlugins.onAssembly(new CompletableDoOnEvent(this, consumer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnLifecycle(@NonNull Consumer<? super Disposable> consumer, @NonNull Action action) {
        return a(consumer, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, action);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    private Completable a(Consumer<? super Disposable> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2, Action action3, Action action4) {
        Objects.requireNonNull(consumer, "onSubscribe is null");
        Objects.requireNonNull(consumer2, "onError is null");
        Objects.requireNonNull(action, "onComplete is null");
        Objects.requireNonNull(action2, "onTerminate is null");
        Objects.requireNonNull(action3, "onAfterTerminate is null");
        Objects.requireNonNull(action4, "onDispose is null");
        return RxJavaPlugins.onAssembly(new CompletablePeek(this, consumer, consumer2, action, action2, action3, action4));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnSubscribe(@NonNull Consumer<? super Disposable> consumer) {
        return a(consumer, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnTerminate(@NonNull Action action) {
        return a(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, action, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doAfterTerminate(@NonNull Action action) {
        return a(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, action, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doFinally(@NonNull Action action) {
        Objects.requireNonNull(action, "onFinally is null");
        return RxJavaPlugins.onAssembly(new CompletableDoFinally(this, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable lift(@NonNull CompletableOperator completableOperator) {
        Objects.requireNonNull(completableOperator, "onLift is null");
        return RxJavaPlugins.onAssembly(new CompletableLift(this, completableOperator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Single<Notification<T>> materialize() {
        return RxJavaPlugins.onAssembly(new CompletableMaterialize(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable mergeWith(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "other is null");
        return mergeArray(this, completableSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable observeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableObserveOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable onErrorComplete() {
        return onErrorComplete(Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable onErrorComplete(@NonNull Predicate<? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new CompletableOnErrorComplete(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable onErrorResumeNext(@NonNull Function<? super Throwable, ? extends CompletableSource> function) {
        Objects.requireNonNull(function, "fallbackSupplier is null");
        return RxJavaPlugins.onAssembly(new CompletableResumeNext(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable onErrorResumeWith(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "fallback is null");
        return onErrorResumeNext(Functions.justFunction(completableSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Maybe<T> onErrorReturn(@NonNull Function<? super Throwable, ? extends T> function) {
        Objects.requireNonNull(function, "itemSupplier is null");
        return RxJavaPlugins.onAssembly(new CompletableOnErrorReturn(this, function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Maybe<T> onErrorReturnItem(@NonNull T t) {
        Objects.requireNonNull(t, "item is null");
        return onErrorReturn(Functions.justFunction(t));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable onTerminateDetach() {
        return RxJavaPlugins.onAssembly(new CompletableDetach(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable repeat() {
        return fromPublisher(toFlowable().repeat());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable repeat(long j) {
        return fromPublisher(toFlowable().repeat(j));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable repeatUntil(@NonNull BooleanSupplier booleanSupplier) {
        return fromPublisher(toFlowable().repeatUntil(booleanSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable repeatWhen(@NonNull Function<? super Flowable<Object>, ? extends Publisher<?>> function) {
        return fromPublisher(toFlowable().repeatWhen(function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retry() {
        return fromPublisher(toFlowable().retry());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retry(@NonNull BiPredicate<? super Integer, ? super Throwable> biPredicate) {
        return fromPublisher(toFlowable().retry(biPredicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retry(long j) {
        return fromPublisher(toFlowable().retry(j));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retry(long j, @NonNull Predicate<? super Throwable> predicate) {
        return fromPublisher(toFlowable().retry(j, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retry(@NonNull Predicate<? super Throwable> predicate) {
        return fromPublisher(toFlowable().retry(predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retryUntil(@NonNull BooleanSupplier booleanSupplier) {
        Objects.requireNonNull(booleanSupplier, "stop is null");
        return retry(Long.MAX_VALUE, Functions.predicateReverseFor(booleanSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retryWhen(@NonNull Function<? super Flowable<Throwable>, ? extends Publisher<?>> function) {
        return fromPublisher(toFlowable().retryWhen(function));
    }

    @SchedulerSupport("none")
    public final void safeSubscribe(@NonNull CompletableObserver completableObserver) {
        Objects.requireNonNull(completableObserver, "observer is null");
        subscribe(new SafeCompletableObserver(completableObserver));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable startWith(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "other is null");
        return concatArray(completableSource, this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <T> Flowable<T> startWith(@NonNull SingleSource<T> singleSource) {
        Objects.requireNonNull(singleSource, "other is null");
        return Flowable.concat(Single.wrap(singleSource).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <T> Flowable<T> startWith(@NonNull MaybeSource<T> maybeSource) {
        Objects.requireNonNull(maybeSource, "other is null");
        return Flowable.concat(Maybe.wrap(maybeSource).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Observable<T> startWith(@NonNull ObservableSource<T> observableSource) {
        Objects.requireNonNull(observableSource, "other is null");
        return Observable.wrap(observableSource).concatWith(toObservable());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <T> Flowable<T> startWith(@NonNull Publisher<T> publisher) {
        Objects.requireNonNull(publisher, "other is null");
        return toFlowable().startWith(publisher);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable hide() {
        return RxJavaPlugins.onAssembly(new CompletableHide(this));
    }

    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe() {
        EmptyCompletableObserver emptyCompletableObserver = new EmptyCompletableObserver();
        subscribe(emptyCompletableObserver);
        return emptyCompletableObserver;
    }

    @Override // io.reactivex.rxjava3.core.CompletableSource
    @SchedulerSupport("none")
    public final void subscribe(@NonNull CompletableObserver completableObserver) {
        Objects.requireNonNull(completableObserver, "observer is null");
        try {
            CompletableObserver onSubscribe = RxJavaPlugins.onSubscribe(this, completableObserver);
            Objects.requireNonNull(onSubscribe, "The RxJavaPlugins.onSubscribe hook returned a null CompletableObserver. Please check the handler provided to RxJavaPlugins.setOnCompletableSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
            subscribeActual(onSubscribe);
        } catch (NullPointerException e) {
            throw e;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
            throw a(th);
        }
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <E extends CompletableObserver> E subscribeWith(E e) {
        subscribe(e);
        return e;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Action action, @NonNull Consumer<? super Throwable> consumer) {
        Objects.requireNonNull(consumer, "onError is null");
        Objects.requireNonNull(action, "onComplete is null");
        CallbackCompletableObserver callbackCompletableObserver = new CallbackCompletableObserver(consumer, action);
        subscribe(callbackCompletableObserver);
        return callbackCompletableObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Action action) {
        Objects.requireNonNull(action, "onComplete is null");
        CallbackCompletableObserver callbackCompletableObserver = new CallbackCompletableObserver(action);
        subscribe(callbackCompletableObserver);
        return callbackCompletableObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable subscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableSubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable takeUntil(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "other is null");
        return RxJavaPlugins.onAssembly(new CompletableTakeUntilCompletable(this, completableSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Completable timeout(long j, @NonNull TimeUnit timeUnit) {
        return a(j, timeUnit, Schedulers.computation(), null);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Completable timeout(long j, @NonNull TimeUnit timeUnit, @NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "fallback is null");
        return a(j, timeUnit, Schedulers.computation(), completableSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable timeout(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return a(j, timeUnit, scheduler, null);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable timeout(long j, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler, @NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "fallback is null");
        return a(j, timeUnit, scheduler, completableSource);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    private Completable a(long j, TimeUnit timeUnit, Scheduler scheduler, CompletableSource completableSource) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableTimeout(this, j, timeUnit, scheduler, completableSource));
    }

    @CheckReturnValue
    @SchedulerSupport("none")
    public final <R> R to(@NonNull CompletableConverter<? extends R> completableConverter) {
        return (R) ((CompletableConverter) Objects.requireNonNull(completableConverter, "converter is null")).apply(this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <T> Flowable<T> toFlowable() {
        if (this instanceof FuseToFlowable) {
            return ((FuseToFlowable) this).fuseToFlowable();
        }
        return RxJavaPlugins.onAssembly(new CompletableToFlowable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Future<Void> toFuture() {
        return (Future) subscribeWith(new FutureMultiObserver());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Maybe<T> toMaybe() {
        if (this instanceof FuseToMaybe) {
            return ((FuseToMaybe) this).fuseToMaybe();
        }
        return RxJavaPlugins.onAssembly(new MaybeFromCompletable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Observable<T> toObservable() {
        if (this instanceof FuseToObservable) {
            return ((FuseToObservable) this).fuseToObservable();
        }
        return RxJavaPlugins.onAssembly(new CompletableToObservable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Single<T> toSingle(@NonNull Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "completionValueSupplier is null");
        return RxJavaPlugins.onAssembly(new CompletableToSingle(this, supplier, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Single<T> toSingleDefault(T t) {
        Objects.requireNonNull(t, "completionValue is null");
        return RxJavaPlugins.onAssembly(new CompletableToSingle(this, null, t));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable unsubscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableDisposeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final TestObserver<Void> test() {
        TestObserver<Void> testObserver = new TestObserver<>();
        subscribe(testObserver);
        return testObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final TestObserver<Void> test(boolean z) {
        TestObserver<Void> testObserver = new TestObserver<>();
        if (z) {
            testObserver.dispose();
        }
        subscribe(testObserver);
        return testObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromCompletionStage(@NonNull CompletionStage<?> completionStage) {
        Objects.requireNonNull(completionStage, "stage is null");
        return RxJavaPlugins.onAssembly(new CompletableFromCompletionStage(completionStage));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> CompletionStage<T> toCompletionStage(@Nullable T t) {
        return (CompletionStage) subscribeWith(new CompletionStageConsumer(true, t));
    }
}
