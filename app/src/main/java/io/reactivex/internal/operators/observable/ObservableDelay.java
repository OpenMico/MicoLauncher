package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class ObservableDelay<T> extends a<T, T> {
    final long a;
    final TimeUnit b;
    final Scheduler c;
    final boolean d;

    public ObservableDelay(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(this.d ? observer : new SerializedObserver(observer), this.a, this.b, this.c.createWorker(), this.d));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        final long b;
        final TimeUnit c;
        final Scheduler.Worker d;
        final boolean e;
        Disposable f;

        a(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler.Worker worker, boolean z) {
            this.a = observer;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
            this.e = z;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.d.schedule(new c(t), this.b, this.c);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.d.schedule(new b(th), this.e ? this.b : 0L, this.c);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.d.schedule(new RunnableC0257a(), this.b, this.c);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f.dispose();
            this.d.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        /* loaded from: classes4.dex */
        final class c implements Runnable {
            private final T b;

            c(T t) {
                this.b = t;
            }

            @Override // java.lang.Runnable
            public void run() {
                a.this.a.onNext((T) this.b);
            }
        }

        /* loaded from: classes4.dex */
        final class b implements Runnable {
            private final Throwable b;

            b(Throwable th) {
                this.b = th;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    a.this.a.onError(this.b);
                } finally {
                    a.this.d.dispose();
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDelay$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        final class RunnableC0257a implements Runnable {
            RunnableC0257a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    a.this.a.onComplete();
                } finally {
                    a.this.d.dispose();
                }
            }
        }
    }
}
