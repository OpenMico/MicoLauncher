package io.reactivex.internal.operators.maybe;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class MaybeDelayWithCompletable<T> extends Maybe<T> {
    final MaybeSource<T> a;
    final CompletableSource b;

    public MaybeDelayWithCompletable(MaybeSource<T> maybeSource, CompletableSource completableSource) {
        this.a = maybeSource;
        this.b = completableSource;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.b.subscribe(new b(maybeObserver, this.a));
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
        private static final long serialVersionUID = 703409937383992161L;
        final MaybeObserver<? super T> downstream;
        final MaybeSource<T> source;

        b(MaybeObserver<? super T> maybeObserver, MaybeSource<T> maybeSource) {
            this.downstream = maybeObserver;
            this.source = maybeSource;
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
        public void onComplete() {
            this.source.subscribe(new a(this, this.downstream));
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements MaybeObserver<T> {
        final AtomicReference<Disposable> a;
        final MaybeObserver<? super T> b;

        a(AtomicReference<Disposable> atomicReference, MaybeObserver<? super T> maybeObserver) {
            this.a = atomicReference;
            this.b = maybeObserver;
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this.a, disposable);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.b.onSuccess(t);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.b.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.b.onComplete();
        }
    }
}
