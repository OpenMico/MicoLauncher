package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class MaybeDelayWithCompletable<T> extends Maybe<T> {
    final MaybeSource<T> a;
    final CompletableSource b;

    public MaybeDelayWithCompletable(MaybeSource<T> maybeSource, CompletableSource completableSource) {
        this.a = maybeSource;
        this.b = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.b.subscribe(new b(maybeObserver, this.a));
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
        private static final long serialVersionUID = 703409937383992161L;
        final MaybeObserver<? super T> downstream;
        final MaybeSource<T> source;

        b(MaybeObserver<? super T> maybeObserver, MaybeSource<T> maybeSource) {
            this.downstream = maybeObserver;
            this.source = maybeSource;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.source.subscribe(new a(this, this.downstream));
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements MaybeObserver<T> {
        final AtomicReference<Disposable> a;
        final MaybeObserver<? super T> b;

        a(AtomicReference<Disposable> atomicReference, MaybeObserver<? super T> maybeObserver) {
            this.a = atomicReference;
            this.b = maybeObserver;
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this.a, disposable);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(T t) {
            this.b.onSuccess(t);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            this.b.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            this.b.onComplete();
        }
    }
}
