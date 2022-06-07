package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableTimeoutTimed<T> extends a<T, T> {
    final long a;
    final TimeUnit b;
    final Scheduler c;
    final ObservableSource<? extends T> d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface d {
        void a(long j);
    }

    public ObservableTimeoutTimed(Observable<T> observable, long j, TimeUnit timeUnit, Scheduler scheduler, ObservableSource<? extends T> observableSource) {
        super(observable);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = observableSource;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        if (this.d == null) {
            c cVar = new c(observer, this.a, this.b, this.c.createWorker());
            observer.onSubscribe(cVar);
            cVar.b(0L);
            this.source.subscribe(cVar);
            return;
        }
        b bVar = new b(observer, this.a, this.b, this.c.createWorker(), this.d);
        observer.onSubscribe(bVar);
        bVar.b(0L);
        this.source.subscribe(bVar);
    }

    /* loaded from: classes4.dex */
    static final class c<T> extends AtomicLong implements Observer<T>, Disposable, d {
        private static final long serialVersionUID = 3764492702657003550L;
        final Observer<? super T> downstream;
        final long timeout;
        final TimeUnit unit;
        final Scheduler.Worker worker;
        final SequentialDisposable task = new SequentialDisposable();
        final AtomicReference<Disposable> upstream = new AtomicReference<>();

        c(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler.Worker worker) {
            this.downstream = observer;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.upstream, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            long j = get();
            if (j != Long.MAX_VALUE) {
                long j2 = 1 + j;
                if (compareAndSet(j, j2)) {
                    this.task.get().dispose();
                    this.downstream.onNext(t);
                    b(j2);
                }
            }
        }

        void b(long j) {
            this.task.replace(this.worker.schedule(new e(j, this), this.timeout, this.unit));
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onError(th);
                this.worker.dispose();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onComplete();
                this.worker.dispose();
            }
        }

        @Override // io.reactivex.internal.operators.observable.ObservableTimeoutTimed.d
        public void a(long j) {
            if (compareAndSet(j, Long.MAX_VALUE)) {
                DisposableHelper.dispose(this.upstream);
                this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.timeout, this.unit)));
                this.worker.dispose();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            this.worker.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.upstream.get());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class e implements Runnable {
        final d a;
        final long b;

        e(long j, d dVar) {
            this.b = j;
            this.a = dVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.a(this.b);
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable, d {
        private static final long serialVersionUID = 3764492702657003550L;
        final Observer<? super T> downstream;
        ObservableSource<? extends T> fallback;
        final long timeout;
        final TimeUnit unit;
        final Scheduler.Worker worker;
        final SequentialDisposable task = new SequentialDisposable();
        final AtomicLong index = new AtomicLong();
        final AtomicReference<Disposable> upstream = new AtomicReference<>();

        b(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler.Worker worker, ObservableSource<? extends T> observableSource) {
            this.downstream = observer;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker;
            this.fallback = observableSource;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.upstream, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            long j = this.index.get();
            if (j != Long.MAX_VALUE) {
                long j2 = 1 + j;
                if (this.index.compareAndSet(j, j2)) {
                    this.task.get().dispose();
                    this.downstream.onNext(t);
                    b(j2);
                }
            }
        }

        void b(long j) {
            this.task.replace(this.worker.schedule(new e(j, this), this.timeout, this.unit));
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onError(th);
                this.worker.dispose();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onComplete();
                this.worker.dispose();
            }
        }

        @Override // io.reactivex.internal.operators.observable.ObservableTimeoutTimed.d
        public void a(long j) {
            if (this.index.compareAndSet(j, Long.MAX_VALUE)) {
                DisposableHelper.dispose(this.upstream);
                ObservableSource<? extends T> observableSource = this.fallback;
                this.fallback = null;
                observableSource.subscribe(new a(this.downstream, this));
                this.worker.dispose();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            DisposableHelper.dispose(this);
            this.worker.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T> {
        final Observer<? super T> a;
        final AtomicReference<Disposable> b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(Observer<? super T> observer, AtomicReference<Disposable> atomicReference) {
            this.a = observer;
            this.b = atomicReference;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this.b, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a.onComplete();
        }
    }
}
