package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableWindowBoundarySelector<T, B, V> extends a<T, Observable<T>> {
    final ObservableSource<B> a;
    final Function<? super B, ? extends ObservableSource<V>> b;
    final int c;

    public ObservableWindowBoundarySelector(ObservableSource<T> observableSource, ObservableSource<B> observableSource2, Function<? super B, ? extends ObservableSource<V>> function, int i) {
        super(observableSource);
        this.a = observableSource2;
        this.b = function;
        this.c = i;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Observable<T>> observer) {
        this.source.subscribe(new c(new SerializedObserver(observer), this.a, this.b, this.c));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c<T, B, V> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
        final ObservableSource<B> b;
        final Function<? super B, ? extends ObservableSource<V>> c;
        final int d;
        Disposable f;
        final AtomicReference<Disposable> g = new AtomicReference<>();
        final AtomicLong i = new AtomicLong();
        final AtomicBoolean j = new AtomicBoolean();
        final CompositeDisposable e = new CompositeDisposable();
        final List<UnicastSubject<T>> h = new ArrayList();

        @Override // io.reactivex.internal.observers.QueueDrainObserver, io.reactivex.internal.util.ObservableQueueDrain
        public void accept(Observer<? super Observable<T>> observer, Object obj) {
        }

        c(Observer<? super Observable<T>> observer, ObservableSource<B> observableSource, Function<? super B, ? extends ObservableSource<V>> function, int i) {
            super(observer, new MpscLinkedQueue());
            this.b = observableSource;
            this.c = function;
            this.d = i;
            this.i.lazySet(1L);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.downstream.onSubscribe(this);
                if (!this.j.get()) {
                    b bVar = new b(this);
                    if (this.g.compareAndSet(null, bVar)) {
                        this.b.subscribe(bVar);
                    }
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (fastEnter()) {
                for (UnicastSubject<T> unicastSubject : this.h) {
                    unicastSubject.onNext(t);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(NotificationLite.next(t));
                if (!enter()) {
                    return;
                }
            }
            b();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            if (enter()) {
                b();
            }
            if (this.i.decrementAndGet() == 0) {
                this.e.dispose();
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                if (enter()) {
                    b();
                }
                if (this.i.decrementAndGet() == 0) {
                    this.e.dispose();
                }
                this.downstream.onComplete();
            }
        }

        void a(Throwable th) {
            this.f.dispose();
            this.e.dispose();
            onError(th);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.j.compareAndSet(false, true)) {
                DisposableHelper.dispose(this.g);
                if (this.i.decrementAndGet() == 0) {
                    this.f.dispose();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.j.get();
        }

        void a() {
            this.e.dispose();
            DisposableHelper.dispose(this.g);
        }

        void b() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer observer = this.downstream;
            List<UnicastSubject<T>> list = this.h;
            int i = 1;
            while (true) {
                boolean z = this.done;
                Object poll = mpscLinkedQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    a();
                    Throwable th = this.error;
                    if (th != null) {
                        for (UnicastSubject<T> unicastSubject : list) {
                            unicastSubject.onError(th);
                        }
                    } else {
                        for (UnicastSubject<T> unicastSubject2 : list) {
                            unicastSubject2.onComplete();
                        }
                    }
                    list.clear();
                    return;
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (poll instanceof d) {
                    d dVar = (d) poll;
                    if (dVar.a != null) {
                        if (list.remove(dVar.a)) {
                            dVar.a.onComplete();
                            if (this.i.decrementAndGet() == 0) {
                                a();
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!this.j.get()) {
                        UnicastSubject<T> create = UnicastSubject.create(this.d);
                        list.add(create);
                        observer.onNext(create);
                        try {
                            ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.c.apply((B) dVar.b), "The ObservableSource supplied is null");
                            a aVar = new a(this, create);
                            if (this.e.add(aVar)) {
                                this.i.getAndIncrement();
                                observableSource.subscribe(aVar);
                            }
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.j.set(true);
                            observer.onError(th2);
                        }
                    }
                } else {
                    for (UnicastSubject<T> unicastSubject3 : list) {
                        unicastSubject3.onNext((T) NotificationLite.getValue(poll));
                    }
                }
            }
        }

        void a(B b) {
            this.queue.offer(new d(null, b));
            if (enter()) {
                b();
            }
        }

        void a(a<T, V> aVar) {
            this.e.delete(aVar);
            this.queue.offer(new d(aVar.b, null));
            if (enter()) {
                b();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class d<T, B> {
        final UnicastSubject<T> a;
        final B b;

        d(UnicastSubject<T> unicastSubject, B b) {
            this.a = unicastSubject;
            this.b = b;
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, B> extends DisposableObserver<B> {
        final c<T, B, ?> a;

        b(c<T, B, ?> cVar) {
            this.a = cVar;
        }

        @Override // io.reactivex.Observer
        public void onNext(B b) {
            this.a.a((c<T, B, ?>) b);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.a(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a.onComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, V> extends DisposableObserver<V> {
        final c<T, ?, V> a;
        final UnicastSubject<T> b;
        boolean c;

        a(c<T, ?, V> cVar, UnicastSubject<T> unicastSubject) {
            this.a = cVar;
            this.b = unicastSubject;
        }

        @Override // io.reactivex.Observer
        public void onNext(V v) {
            dispose();
            onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.a.a(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.a.a((a) this);
            }
        }
    }
}
