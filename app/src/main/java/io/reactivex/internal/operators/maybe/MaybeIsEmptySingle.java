package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToMaybe;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class MaybeIsEmptySingle<T> extends Single<Boolean> implements FuseToMaybe<Boolean>, HasUpstreamMaybeSource<T> {
    final MaybeSource<T> a;

    public MaybeIsEmptySingle(MaybeSource<T> maybeSource) {
        this.a = maybeSource;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamMaybeSource
    public MaybeSource<T> source() {
        return this.a;
    }

    @Override // io.reactivex.internal.fuseable.FuseToMaybe
    public Maybe<Boolean> fuseToMaybe() {
        return RxJavaPlugins.onAssembly(new MaybeIsEmpty(this.a));
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements MaybeObserver<T>, Disposable {
        final SingleObserver<? super Boolean> a;
        Disposable b;

        a(SingleObserver<? super Boolean> singleObserver) {
            this.a = singleObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.b.dispose();
            this.b = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.b = DisposableHelper.DISPOSED;
            this.a.onSuccess(false);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.b = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.b = DisposableHelper.DISPOSED;
            this.a.onSuccess(true);
        }
    }
}
