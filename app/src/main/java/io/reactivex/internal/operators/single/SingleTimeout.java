package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class SingleTimeout<T> extends Single<T> {
    final SingleSource<T> a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final SingleSource<? extends T> e;

    public SingleTimeout(SingleSource<T> singleSource, long j, TimeUnit timeUnit, Scheduler scheduler, SingleSource<? extends T> singleSource2) {
        this.a = singleSource;
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = singleSource2;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        a aVar = new a(singleObserver, this.e, this.b, this.c);
        singleObserver.onSubscribe(aVar);
        DisposableHelper.replace(aVar.task, this.d.scheduleDirect(aVar, this.b, this.c));
        this.a.subscribe(aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 37497744973048446L;
        final SingleObserver<? super T> downstream;
        final C0274a<T> fallback;
        SingleSource<? extends T> other;
        final AtomicReference<Disposable> task = new AtomicReference<>();
        final long timeout;
        final TimeUnit unit;

        /* renamed from: io.reactivex.internal.operators.single.SingleTimeout$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static final class C0274a<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            private static final long serialVersionUID = 2071387740092105509L;
            final SingleObserver<? super T> downstream;

            C0274a(SingleObserver<? super T> singleObserver) {
                this.downstream = singleObserver;
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(T t) {
                this.downstream.onSuccess(t);
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.downstream.onError(th);
            }
        }

        a(SingleObserver<? super T> singleObserver, SingleSource<? extends T> singleSource, long j, TimeUnit timeUnit) {
            this.downstream = singleObserver;
            this.other = singleSource;
            this.timeout = j;
            this.unit = timeUnit;
            if (singleSource != null) {
                this.fallback = new C0274a<>(singleObserver);
            } else {
                this.fallback = null;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Disposable disposable = get();
            if (disposable != DisposableHelper.DISPOSED && compareAndSet(disposable, DisposableHelper.DISPOSED)) {
                if (disposable != null) {
                    disposable.dispose();
                }
                SingleSource<? extends T> singleSource = this.other;
                if (singleSource == null) {
                    this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.timeout, this.unit)));
                    return;
                }
                this.other = null;
                singleSource.subscribe(this.fallback);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            Disposable disposable = get();
            if (disposable != DisposableHelper.DISPOSED && compareAndSet(disposable, DisposableHelper.DISPOSED)) {
                DisposableHelper.dispose(this.task);
                this.downstream.onSuccess(t);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            Disposable disposable = get();
            if (disposable == DisposableHelper.DISPOSED || !compareAndSet(disposable, DisposableHelper.DISPOSED)) {
                RxJavaPlugins.onError(th);
                return;
            }
            DisposableHelper.dispose(this.task);
            this.downstream.onError(th);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            DisposableHelper.dispose(this.task);
            C0274a<T> aVar = this.fallback;
            if (aVar != null) {
                DisposableHelper.dispose(aVar);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }
    }
}
