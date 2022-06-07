package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public final class MaybeTimeInterval<T> extends Maybe<Timed<T>> {
    final MaybeSource<T> a;
    final TimeUnit b;
    final Scheduler c;
    final boolean d;

    public MaybeTimeInterval(MaybeSource<T> maybeSource, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        this.a = maybeSource;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = z;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(@NonNull MaybeObserver<? super Timed<T>> maybeObserver) {
        this.a.subscribe(new a(maybeObserver, this.b, this.c, this.d));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super Timed<T>> a;
        final TimeUnit b;
        final Scheduler c;
        final long d;
        Disposable e;

        a(MaybeObserver<? super Timed<T>> maybeObserver, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.a = maybeObserver;
            this.b = timeUnit;
            this.c = scheduler;
            this.d = z ? scheduler.now(timeUnit) : 0L;
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(@NonNull Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(@NonNull T t) {
            this.a.onSuccess(new Timed(t, this.c.now(this.b) - this.d, this.b));
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(@NonNull Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            this.a.onComplete();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.e.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.e.isDisposed();
        }
    }
}
