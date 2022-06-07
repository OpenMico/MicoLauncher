package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableGroupJoin;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends a<TLeft, R> {
    final ObservableSource<? extends TRight> a;
    final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> b;
    final Function<? super TRight, ? extends ObservableSource<TRightEnd>> c;
    final BiFunction<? super TLeft, ? super TRight, ? extends R> d;

    public ObservableJoin(ObservableSource<TLeft> observableSource, ObservableSource<? extends TRight> observableSource2, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> function, Function<? super TRight, ? extends ObservableSource<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
        super(observableSource);
        this.a = observableSource2;
        this.b = function;
        this.c = function2;
        this.d = biFunction;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        a aVar = new a(observer, this.b, this.c, this.d);
        observer.onSubscribe(aVar);
        ObservableGroupJoin.d dVar = new ObservableGroupJoin.d(aVar, true);
        aVar.disposables.add(dVar);
        ObservableGroupJoin.d dVar2 = new ObservableGroupJoin.d(aVar, false);
        aVar.disposables.add(dVar2);
        this.source.subscribe(dVar);
        this.a.subscribe(dVar2);
    }

    /* loaded from: classes4.dex */
    static final class a<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Disposable, ObservableGroupJoin.b {
        static final Integer a = 1;
        static final Integer b = 2;
        static final Integer c = 3;
        static final Integer d = 4;
        private static final long serialVersionUID = -6071216598687999801L;
        volatile boolean cancelled;
        final Observer<? super R> downstream;
        final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> leftEnd;
        int leftIndex;
        final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
        final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;
        int rightIndex;
        final CompositeDisposable disposables = new CompositeDisposable();
        final SpscLinkedArrayQueue<Object> queue = new SpscLinkedArrayQueue<>(Observable.bufferSize());
        final Map<Integer, TLeft> lefts = new LinkedHashMap();
        final Map<Integer, TRight> rights = new LinkedHashMap();
        final AtomicReference<Throwable> error = new AtomicReference<>();
        final AtomicInteger active = new AtomicInteger(2);

        a(Observer<? super R> observer, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> function, Function<? super TRight, ? extends ObservableSource<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
            this.downstream = observer;
            this.leftEnd = function;
            this.rightEnd = function2;
            this.resultSelector = biFunction;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                a();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a() {
            this.disposables.dispose();
        }

        void a(Observer<?> observer) {
            Throwable terminate = ExceptionHelper.terminate(this.error);
            this.lefts.clear();
            this.rights.clear();
            observer.onError(terminate);
        }

        void a(Throwable th, Observer<?> observer, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            Exceptions.throwIfFatal(th);
            ExceptionHelper.addThrowable(this.error, th);
            spscLinkedArrayQueue.clear();
            a();
            a(observer);
        }

        /* JADX WARN: Multi-variable type inference failed */
        void b() {
            if (getAndIncrement() == 0) {
                SpscLinkedArrayQueue<?> spscLinkedArrayQueue = this.queue;
                Observer<? super R> observer = this.downstream;
                int i = 1;
                while (!this.cancelled) {
                    if (this.error.get() != null) {
                        spscLinkedArrayQueue.clear();
                        a();
                        a(observer);
                        return;
                    }
                    boolean z = this.active.get() == 0;
                    Integer num = (Integer) spscLinkedArrayQueue.poll();
                    boolean z2 = num == null;
                    if (z && z2) {
                        this.lefts.clear();
                        this.rights.clear();
                        this.disposables.dispose();
                        observer.onComplete();
                        return;
                    } else if (z2) {
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        Object poll = spscLinkedArrayQueue.poll();
                        if (num == a) {
                            int i2 = this.leftIndex;
                            this.leftIndex = i2 + 1;
                            this.lefts.put(Integer.valueOf(i2), poll);
                            try {
                                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.leftEnd.apply(poll), "The leftEnd returned a null ObservableSource");
                                ObservableGroupJoin.c cVar = new ObservableGroupJoin.c(this, true, i2);
                                this.disposables.add(cVar);
                                observableSource.subscribe(cVar);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(observer);
                                    return;
                                }
                                for (TRight tright : this.rights.values()) {
                                    try {
                                        observer.onNext((Object) ObjectHelper.requireNonNull(this.resultSelector.apply(poll, tright), "The resultSelector returned a null value"));
                                    } catch (Throwable th) {
                                        a(th, observer, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                                continue;
                            } catch (Throwable th2) {
                                a(th2, observer, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == b) {
                            int i3 = this.rightIndex;
                            this.rightIndex = i3 + 1;
                            this.rights.put(Integer.valueOf(i3), poll);
                            try {
                                ObservableSource observableSource2 = (ObservableSource) ObjectHelper.requireNonNull(this.rightEnd.apply(poll), "The rightEnd returned a null ObservableSource");
                                ObservableGroupJoin.c cVar2 = new ObservableGroupJoin.c(this, false, i3);
                                this.disposables.add(cVar2);
                                observableSource2.subscribe(cVar2);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(observer);
                                    return;
                                }
                                for (TLeft tleft : this.lefts.values()) {
                                    try {
                                        observer.onNext((Object) ObjectHelper.requireNonNull(this.resultSelector.apply(tleft, poll), "The resultSelector returned a null value"));
                                    } catch (Throwable th3) {
                                        a(th3, observer, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                                continue;
                            } catch (Throwable th4) {
                                a(th4, observer, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == c) {
                            ObservableGroupJoin.c cVar3 = (ObservableGroupJoin.c) poll;
                            this.lefts.remove(Integer.valueOf(cVar3.index));
                            this.disposables.remove(cVar3);
                        } else {
                            ObservableGroupJoin.c cVar4 = (ObservableGroupJoin.c) poll;
                            this.rights.remove(Integer.valueOf(cVar4.index));
                            this.disposables.remove(cVar4);
                        }
                    }
                }
                spscLinkedArrayQueue.clear();
            }
        }

        @Override // io.reactivex.internal.operators.observable.ObservableGroupJoin.b
        public void a(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                this.active.decrementAndGet();
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableGroupJoin.b
        public void a(ObservableGroupJoin.d dVar) {
            this.disposables.delete(dVar);
            this.active.decrementAndGet();
            b();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableGroupJoin.b
        public void a(boolean z, Object obj) {
            synchronized (this) {
                this.queue.offer(z ? a : b, obj);
            }
            b();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableGroupJoin.b
        public void a(boolean z, ObservableGroupJoin.c cVar) {
            synchronized (this) {
                this.queue.offer(z ? c : d, cVar);
            }
            b();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableGroupJoin.b
        public void b(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                b();
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }
}
