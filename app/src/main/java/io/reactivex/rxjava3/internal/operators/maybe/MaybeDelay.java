package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class MaybeDelay<T> extends a<T, T> {
    final long a;
    final TimeUnit b;
    final Scheduler c;
    final boolean d;

    public MaybeDelay(MaybeSource<T> maybeSource, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(maybeSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = z;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a, this.b, this.c, this.d));
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 5566860102500855068L;
        final long delay;
        final boolean delayError;
        final MaybeObserver<? super T> downstream;
        Throwable error;
        final Scheduler scheduler;
        final TimeUnit unit;
        T value;

        a(MaybeObserver<? super T> maybeObserver, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.downstream = maybeObserver;
            this.delay = j;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.delayError = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            Throwable th = this.error;
            if (th != null) {
                this.downstream.onError(th);
                return;
            }
            T t = this.value;
            if (t != null) {
                this.downstream.onSuccess(t);
            } else {
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(T t) {
            this.value = t;
            a(this.delay);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            this.error = th;
            a(this.delayError ? this.delay : 0L);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            a(this.delay);
        }

        void a(long j) {
            DisposableHelper.replace(this, this.scheduler.scheduleDirect(this, j, this.unit));
        }
    }
}
