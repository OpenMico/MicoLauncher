package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

/* loaded from: classes4.dex */
public final class MaybeCount<T> extends Single<Long> implements HasUpstreamMaybeSource<T> {
    final MaybeSource<T> a;

    public MaybeCount(MaybeSource<T> maybeSource) {
        this.a = maybeSource;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamMaybeSource
    public MaybeSource<T> source() {
        return this.a;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Long> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes4.dex */
    static final class a implements MaybeObserver<Object>, Disposable {
        final SingleObserver<? super Long> a;
        Disposable b;

        a(SingleObserver<? super Long> singleObserver) {
            this.a = singleObserver;
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(Object obj) {
            this.b = DisposableHelper.DISPOSED;
            this.a.onSuccess(1L);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.b = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.b = DisposableHelper.DISPOSED;
            this.a.onSuccess(0L);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.b.dispose();
            this.b = DisposableHelper.DISPOSED;
        }
    }
}
