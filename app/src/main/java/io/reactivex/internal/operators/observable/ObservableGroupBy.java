package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.observables.GroupedObservable;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableGroupBy<T, K, V> extends a<T, GroupedObservable<K, V>> {
    final Function<? super T, ? extends K> a;
    final Function<? super T, ? extends V> b;
    final int c;
    final boolean d;

    public ObservableGroupBy(ObservableSource<T> observableSource, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z) {
        super(observableSource);
        this.a = function;
        this.b = function2;
        this.c = i;
        this.d = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super GroupedObservable<K, V>> observer) {
        this.source.subscribe(new GroupByObserver(observer, this.a, this.b, this.c, this.d));
    }

    /* loaded from: classes4.dex */
    public static final class GroupByObserver<T, K, V> extends AtomicInteger implements Observer<T>, Disposable {
        static final Object a = new Object();
        private static final long serialVersionUID = -3688291656102519502L;
        final int bufferSize;
        final boolean delayError;
        final Observer<? super GroupedObservable<K, V>> downstream;
        final Function<? super T, ? extends K> keySelector;
        Disposable upstream;
        final Function<? super T, ? extends V> valueSelector;
        final AtomicBoolean cancelled = new AtomicBoolean();
        final Map<Object, a<K, V>> groups = new ConcurrentHashMap();

        public GroupByObserver(Observer<? super GroupedObservable<K, V>> observer, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z) {
            this.downstream = observer;
            this.keySelector = function;
            this.valueSelector = function2;
            this.bufferSize = i;
            this.delayError = z;
            lazySet(1);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.Observer
        public void onNext(T t) {
            try {
                Object apply = this.keySelector.apply(t);
                Object obj = apply != null ? apply : a;
                a<K, V> aVar = this.groups.get(obj);
                if (aVar == null) {
                    if (!this.cancelled.get()) {
                        aVar = a.a(apply, this.bufferSize, (GroupByObserver<?, Object, T>) this, this.delayError);
                        this.groups.put(obj, aVar);
                        getAndIncrement();
                        this.downstream.onNext(aVar);
                    } else {
                        return;
                    }
                }
                try {
                    aVar.a((a) ObjectHelper.requireNonNull(this.valueSelector.apply(t), "The value supplied is null"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.dispose();
                    onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.upstream.dispose();
                onError(th2);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            ArrayList<a> arrayList = new ArrayList(this.groups.values());
            this.groups.clear();
            for (a aVar : arrayList) {
                aVar.a(th);
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            ArrayList<a> arrayList = new ArrayList(this.groups.values());
            this.groups.clear();
            for (a aVar : arrayList) {
                aVar.a();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.cancelled.compareAndSet(false, true) && decrementAndGet() == 0) {
                this.upstream.dispose();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled.get();
        }

        public void cancel(K k) {
            if (k == null) {
                k = (K) a;
            }
            this.groups.remove(k);
            if (decrementAndGet() == 0) {
                this.upstream.dispose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<K, T> extends GroupedObservable<K, T> {
        final b<T, K> a;

        public static <T, K> a<K, T> a(K k, int i, GroupByObserver<?, K, T> groupByObserver, boolean z) {
            return new a<>(k, new b(i, groupByObserver, k, z));
        }

        protected a(K k, b<T, K> bVar) {
            super(k);
            this.a = bVar;
        }

        @Override // io.reactivex.Observable
        protected void subscribeActual(Observer<? super T> observer) {
            this.a.subscribe(observer);
        }

        public void a(T t) {
            this.a.a((b<T, K>) t);
        }

        public void a(Throwable th) {
            this.a.a(th);
        }

        public void a() {
            this.a.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T, K> extends AtomicInteger implements ObservableSource<T>, Disposable {
        private static final long serialVersionUID = -3852313036005250360L;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final K key;
        final GroupByObserver<?, K, T> parent;
        final SpscLinkedArrayQueue<T> queue;
        final AtomicBoolean cancelled = new AtomicBoolean();
        final AtomicBoolean once = new AtomicBoolean();
        final AtomicReference<Observer<? super T>> actual = new AtomicReference<>();

        b(int i, GroupByObserver<?, K, T> groupByObserver, K k, boolean z) {
            this.queue = new SpscLinkedArrayQueue<>(i);
            this.parent = groupByObserver;
            this.key = k;
            this.delayError = z;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.cancelled.compareAndSet(false, true) && getAndIncrement() == 0) {
                this.actual.lazySet(null);
                this.parent.cancel(this.key);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled.get();
        }

        @Override // io.reactivex.ObservableSource
        public void subscribe(Observer<? super T> observer) {
            if (this.once.compareAndSet(false, true)) {
                observer.onSubscribe(this);
                this.actual.lazySet(observer);
                if (this.cancelled.get()) {
                    this.actual.lazySet(null);
                } else {
                    b();
                }
            } else {
                EmptyDisposable.error(new IllegalStateException("Only one Observer allowed!"), observer);
            }
        }

        public void a(T t) {
            this.queue.offer(t);
            b();
        }

        public void a(Throwable th) {
            this.error = th;
            this.done = true;
            b();
        }

        public void a() {
            this.done = true;
            b();
        }

        void b() {
            if (getAndIncrement() == 0) {
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
                boolean z = this.delayError;
                Observer<? super T> observer = this.actual.get();
                int i = 1;
                while (true) {
                    if (observer != null) {
                        while (true) {
                            boolean z2 = this.done;
                            Object obj = (T) spscLinkedArrayQueue.poll();
                            boolean z3 = obj == null;
                            if (!a(z2, z3, observer, z)) {
                                if (z3) {
                                    break;
                                }
                                observer.onNext(obj);
                            } else {
                                return;
                            }
                        }
                    }
                    i = addAndGet(-i);
                    if (i != 0) {
                        if (observer == null) {
                            observer = this.actual.get();
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        boolean a(boolean z, boolean z2, Observer<? super T> observer, boolean z3) {
            if (this.cancelled.get()) {
                this.queue.clear();
                this.parent.cancel(this.key);
                this.actual.lazySet(null);
                return true;
            } else if (!z) {
                return false;
            } else {
                if (!z3) {
                    Throwable th = this.error;
                    if (th != null) {
                        this.queue.clear();
                        this.actual.lazySet(null);
                        observer.onError(th);
                        return true;
                    } else if (!z2) {
                        return false;
                    } else {
                        this.actual.lazySet(null);
                        observer.onComplete();
                        return true;
                    }
                } else if (!z2) {
                    return false;
                } else {
                    Throwable th2 = this.error;
                    this.actual.lazySet(null);
                    if (th2 != null) {
                        observer.onError(th2);
                    } else {
                        observer.onComplete();
                    }
                    return true;
                }
            }
        }
    }
}
