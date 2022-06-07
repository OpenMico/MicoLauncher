package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
public final class MaybeDoFinally<T> extends a<T, T> {
    final Action a;

    public MaybeDoFinally(MaybeSource<T> maybeSource, Action action) {
        super(maybeSource);
        this.a = action;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a));
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 4109457741734051389L;
        final MaybeObserver<? super T> downstream;
        final Action onFinally;
        Disposable upstream;

        a(MaybeObserver<? super T> maybeObserver, Action action) {
            this.downstream = maybeObserver;
            this.onFinally = action;
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(T t) {
            this.downstream.onSuccess(t);
            a();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
            a();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
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
