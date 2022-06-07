package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class MaybeTimer extends Maybe<Long> {
    final long a;
    final TimeUnit b;
    final Scheduler c;

    public MaybeTimer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super Long> maybeObserver) {
        a aVar = new a(maybeObserver);
        maybeObserver.onSubscribe(aVar);
        aVar.a(this.c.scheduleDirect(aVar, this.a, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a extends AtomicReference<Disposable> implements Disposable, Runnable {
        private static final long serialVersionUID = 2875964065294031672L;
        final MaybeObserver<? super Long> downstream;

        a(MaybeObserver<? super Long> maybeObserver) {
            this.downstream = maybeObserver;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.downstream.onSuccess(0L);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        void a(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
        }
    }
}
