package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableRefCount<T> extends Observable<T> {
    final ConnectableObservable<T> a;
    final int b;
    final long c;
    final TimeUnit d;
    final Scheduler e;
    a f;

    public ObservableRefCount(ConnectableObservable<T> connectableObservable) {
        this(connectableObservable, 1, 0L, TimeUnit.NANOSECONDS, null);
    }

    public ObservableRefCount(ConnectableObservable<T> connectableObservable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.a = connectableObservable;
        this.b = i;
        this.c = j;
        this.d = timeUnit;
        this.e = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        a aVar;
        boolean z;
        synchronized (this) {
            aVar = this.f;
            if (aVar == null) {
                aVar = new a(this);
                this.f = aVar;
            }
            long j = aVar.subscriberCount;
            if (j == 0 && aVar.timer != null) {
                aVar.timer.dispose();
            }
            long j2 = j + 1;
            aVar.subscriberCount = j2;
            z = true;
            if (aVar.connected || j2 != this.b) {
                z = false;
            } else {
                aVar.connected = true;
            }
        }
        this.a.subscribe(new b(observer, this, aVar));
        if (z) {
            this.a.connect(aVar);
        }
    }

    void a(a aVar) {
        synchronized (this) {
            if (this.f != null && this.f == aVar) {
                long j = aVar.subscriberCount - 1;
                aVar.subscriberCount = j;
                if (j == 0 && aVar.connected) {
                    if (this.c == 0) {
                        c(aVar);
                        return;
                    }
                    SequentialDisposable sequentialDisposable = new SequentialDisposable();
                    aVar.timer = sequentialDisposable;
                    sequentialDisposable.replace(this.e.scheduleDirect(aVar, this.c, this.d));
                }
            }
        }
    }

    void b(a aVar) {
        synchronized (this) {
            if (this.f == aVar) {
                if (aVar.timer != null) {
                    aVar.timer.dispose();
                    aVar.timer = null;
                }
                long j = aVar.subscriberCount - 1;
                aVar.subscriberCount = j;
                if (j == 0) {
                    this.f = null;
                    this.a.reset();
                }
            }
        }
    }

    void c(a aVar) {
        synchronized (this) {
            if (aVar.subscriberCount == 0 && aVar == this.f) {
                this.f = null;
                Disposable disposable = aVar.get();
                DisposableHelper.dispose(aVar);
                if (disposable == null) {
                    aVar.disconnectedEarly = true;
                } else {
                    this.a.reset();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a extends AtomicReference<Disposable> implements Consumer<Disposable>, Runnable {
        private static final long serialVersionUID = -4552101107598366241L;
        boolean connected;
        boolean disconnectedEarly;
        final ObservableRefCount<?> parent;
        long subscriberCount;
        Disposable timer;

        a(ObservableRefCount<?> observableRefCount) {
            this.parent = observableRefCount;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.parent.c(this);
        }

        /* renamed from: a */
        public void accept(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
            synchronized (this.parent) {
                if (this.disconnectedEarly) {
                    this.parent.a.reset();
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = -7419642935409022375L;
        final a connection;
        final Observer<? super T> downstream;
        final ObservableRefCount<T> parent;
        Disposable upstream;

        b(Observer<? super T> observer, ObservableRefCount<T> observableRefCount, a aVar) {
            this.downstream = observer;
            this.parent = observableRefCount;
            this.connection = aVar;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (compareAndSet(false, true)) {
                this.parent.b(this.connection);
                this.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (compareAndSet(false, true)) {
                this.parent.b(this.connection);
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            if (compareAndSet(false, true)) {
                this.parent.a(this.connection);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }
}
