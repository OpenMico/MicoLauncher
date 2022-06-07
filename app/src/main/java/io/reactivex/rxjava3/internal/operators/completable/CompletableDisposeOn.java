package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class CompletableDisposeOn extends Completable {
    final CompletableSource a;
    final Scheduler b;

    public CompletableDisposeOn(CompletableSource completableSource, Scheduler scheduler) {
        this.a = completableSource;
        this.b = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new a(completableObserver, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a implements CompletableObserver, Disposable, Runnable {
        final CompletableObserver a;
        final Scheduler b;
        Disposable c;
        volatile boolean d;

        a(CompletableObserver completableObserver, Scheduler scheduler) {
            this.a = completableObserver;
            this.b = scheduler;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            if (!this.d) {
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.d = true;
            this.b.scheduleDirect(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.d;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
        }
    }
}
