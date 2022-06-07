package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.subjects.UnicastSubject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends a<TLeft, R> {
    final ObservableSource<? extends TRight> a;
    final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> b;
    final Function<? super TRight, ? extends ObservableSource<TRightEnd>> c;
    final BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> d;

    /* loaded from: classes5.dex */
    public interface b {
        void a(d dVar);

        void a(Throwable th);

        void a(boolean z, c cVar);

        void a(boolean z, Object obj);

        void b(Throwable th);
    }

    public ObservableGroupJoin(ObservableSource<TLeft> observableSource, ObservableSource<? extends TRight> observableSource2, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> function, Function<? super TRight, ? extends ObservableSource<TRightEnd>> function2, BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> biFunction) {
        super(observableSource);
        this.a = observableSource2;
        this.b = function;
        this.c = function2;
        this.d = biFunction;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        a aVar = new a(observer, this.b, this.c, this.d);
        observer.onSubscribe(aVar);
        d dVar = new d(aVar, true);
        aVar.disposables.add(dVar);
        d dVar2 = new d(aVar, false);
        aVar.disposables.add(dVar2);
        this.source.subscribe(dVar);
        this.a.subscribe(dVar2);
    }

    /* loaded from: classes5.dex */
    static final class a<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Disposable, b {
        static final Integer a = 1;
        static final Integer b = 2;
        static final Integer c = 3;
        static final Integer d = 4;
        private static final long serialVersionUID = -6071216598687999801L;
        volatile boolean cancelled;
        final Observer<? super R> downstream;
        final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> leftEnd;
        int leftIndex;
        final BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> resultSelector;
        final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;
        int rightIndex;
        final CompositeDisposable disposables = new CompositeDisposable();
        final SpscLinkedArrayQueue<Object> queue = new SpscLinkedArrayQueue<>(Observable.bufferSize());
        final Map<Integer, UnicastSubject<TRight>> lefts = new LinkedHashMap();
        final Map<Integer, TRight> rights = new LinkedHashMap();
        final AtomicReference<Throwable> error = new AtomicReference<>();
        final AtomicInteger active = new AtomicInteger(2);

        a(Observer<? super R> observer, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> function, Function<? super TRight, ? extends ObservableSource<TRightEnd>> function2, BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> biFunction) {
            this.downstream = observer;
            this.leftEnd = function;
            this.rightEnd = function2;
            this.resultSelector = biFunction;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                a();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a() {
            this.disposables.dispose();
        }

        void a(Observer<?> observer) {
            Throwable terminate = ExceptionHelper.terminate(this.error);
            for (UnicastSubject<TRight> unicastSubject : this.lefts.values()) {
                unicastSubject.onError(terminate);
            }
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
                        for (UnicastSubject<TRight> unicastSubject : this.lefts.values()) {
                            unicastSubject.onComplete();
                        }
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
                            UnicastSubject<TRight> create = UnicastSubject.create();
                            int i2 = this.leftIndex;
                            this.leftIndex = i2 + 1;
                            this.lefts.put(Integer.valueOf(i2), create);
                            try {
                                ObservableSource observableSource = (ObservableSource) Objects.requireNonNull(this.leftEnd.apply(poll), "The leftEnd returned a null ObservableSource");
                                c cVar = new c(this, true, i2);
                                this.disposables.add(cVar);
                                observableSource.subscribe(cVar);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(observer);
                                    return;
                                }
                                try {
                                    observer.onNext((Object) Objects.requireNonNull(this.resultSelector.apply(poll, create), "The resultSelector returned a null value"));
                                    for (TRight tright : this.rights.values()) {
                                        create.onNext(tright);
                                    }
                                } catch (Throwable th) {
                                    a(th, observer, spscLinkedArrayQueue);
                                    return;
                                }
                            } catch (Throwable th2) {
                                a(th2, observer, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == b) {
                            int i3 = this.rightIndex;
                            this.rightIndex = i3 + 1;
                            this.rights.put(Integer.valueOf(i3), poll);
                            try {
                                ObservableSource observableSource2 = (ObservableSource) Objects.requireNonNull(this.rightEnd.apply(poll), "The rightEnd returned a null ObservableSource");
                                c cVar2 = new c(this, false, i3);
                                this.disposables.add(cVar2);
                                observableSource2.subscribe(cVar2);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(observer);
                                    return;
                                }
                                for (UnicastSubject<TRight> unicastSubject2 : this.lefts.values()) {
                                    unicastSubject2.onNext(poll);
                                }
                            } catch (Throwable th3) {
                                a(th3, observer, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == c) {
                            c cVar3 = (c) poll;
                            UnicastSubject<TRight> remove = this.lefts.remove(Integer.valueOf(cVar3.index));
                            this.disposables.remove(cVar3);
                            if (remove != null) {
                                remove.onComplete();
                            }
                        } else {
                            c cVar4 = (c) poll;
                            this.rights.remove(Integer.valueOf(cVar4.index));
                            this.disposables.remove(cVar4);
                        }
                    }
                }
                spscLinkedArrayQueue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableGroupJoin.b
        public void a(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                this.active.decrementAndGet();
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableGroupJoin.b
        public void a(d dVar) {
            this.disposables.delete(dVar);
            this.active.decrementAndGet();
            b();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableGroupJoin.b
        public void a(boolean z, Object obj) {
            synchronized (this) {
                this.queue.offer(z ? a : b, obj);
            }
            b();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableGroupJoin.b
        public void a(boolean z, c cVar) {
            synchronized (this) {
                this.queue.offer(z ? c : d, cVar);
            }
            b();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableGroupJoin.b
        public void b(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                b();
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class d extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
        private static final long serialVersionUID = 1883890389173668373L;
        final boolean isLeft;
        final b parent;

        public d(b bVar, boolean z) {
            this.parent = bVar;
            this.isLeft = z;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            this.parent.a(this.isLeft, obj);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.parent.a(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.parent.a(this);
        }
    }

    /* loaded from: classes5.dex */
    public static final class c extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
        private static final long serialVersionUID = 1883890389173668373L;
        final int index;
        final boolean isLeft;
        final b parent;

        public c(b bVar, boolean z, int i) {
            this.parent = bVar;
            this.isLeft = z;
            this.index = i;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            if (DisposableHelper.dispose(this)) {
                this.parent.a(this.isLeft, this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.parent.b(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.parent.a(this.isLeft, this);
        }
    }
}
