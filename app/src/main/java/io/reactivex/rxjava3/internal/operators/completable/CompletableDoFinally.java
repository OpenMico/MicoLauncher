package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class CompletableDoFinally extends Completable {
    final CompletableSource a;
    final Action b;

    public CompletableDoFinally(CompletableSource completableSource, Action action) {
        this.a = completableSource;
        this.b = action;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new a(completableObserver, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a extends AtomicInteger implements CompletableObserver, Disposable {
        private static final long serialVersionUID = 4109457741734051389L;
        final CompletableObserver downstream;
        final Action onFinally;
        Disposable upstream;

        a(CompletableObserver completableObserver, Action action) {
            this.downstream = completableObserver;
            this.onFinally = action;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
            a();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.downstream.onComplete();
            a();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            a();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        void a() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }
}
