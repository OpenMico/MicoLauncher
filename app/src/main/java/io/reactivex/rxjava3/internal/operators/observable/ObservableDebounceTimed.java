package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.observers.SerializedObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableDebounceTimed<T> extends a<T, T> {
    final long a;
    final TimeUnit b;
    final Scheduler c;

    public ObservableDebounceTimed(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler) {
        super(observableSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new b(new SerializedObserver(observer), this.a, this.b, this.c.createWorker()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        final long b;
        final TimeUnit c;
        final Scheduler.Worker d;
        Disposable e;
        Disposable f;
        volatile long g;
        boolean h;

        b(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler.Worker worker) {
            this.a = observer;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.h) {
                long j = this.g + 1;
                this.g = j;
                Disposable disposable = this.f;
                if (disposable != null) {
                    disposable.dispose();
                }
                a aVar = new a(t, j, this);
                this.f = aVar;
                aVar.a(this.d.schedule(aVar, this.b, this.c));
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            Disposable disposable = this.f;
            if (disposable != null) {
                disposable.dispose();
            }
            this.h = true;
            this.a.onError(th);
            this.d.dispose();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.h) {
                this.h = true;
                Disposable disposable = this.f;
                if (disposable != null) {
                    disposable.dispose();
                }
                a aVar = (a) disposable;
                if (aVar != null) {
                    aVar.run();
                }
                this.a.onComplete();
                this.d.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.e.dispose();
            this.d.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        void a(long j, T t, a<T> aVar) {
            if (j == this.g) {
                this.a.onNext(t);
                aVar.dispose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicReference<Disposable> implements Disposable, Runnable {
        private static final long serialVersionUID = 6812032969491025141L;
        final long idx;
        final AtomicBoolean once = new AtomicBoolean();
        final b<T> parent;
        final T value;

        a(T t, long j, b<T> bVar) {
            this.value = t;
            this.idx = j;
            this.parent = bVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.once.compareAndSet(false, true)) {
                this.parent.a(this.idx, this.value, this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        public void a(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
        }
    }
}
