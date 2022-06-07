package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class MaybeDelay<T> extends a<T, T> {
    final long a;
    final TimeUnit b;
    final Scheduler c;

    public MaybeDelay(MaybeSource<T> maybeSource, long j, TimeUnit timeUnit, Scheduler scheduler) {
        super(maybeSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a, this.b, this.c));
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 5566860102500855068L;
        final long delay;
        final MaybeObserver<? super T> downstream;
        Throwable error;
        final Scheduler scheduler;
        final TimeUnit unit;
        T value;

        a(MaybeObserver<? super T> maybeObserver, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.downstream = maybeObserver;
            this.delay = j;
            this.unit = timeUnit;
            this.scheduler = scheduler;
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

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.value = t;
            a();
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.error = th;
            a();
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            a();
        }

        void a() {
            DisposableHelper.replace(this, this.scheduler.scheduleDirect(this, this.delay, this.unit));
        }
    }
}
