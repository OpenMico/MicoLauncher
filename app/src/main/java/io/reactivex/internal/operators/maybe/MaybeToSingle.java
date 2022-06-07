package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;
import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public final class MaybeToSingle<T> extends Single<T> implements HasUpstreamMaybeSource<T> {
    final MaybeSource<T> a;
    final T b;

    public MaybeToSingle(MaybeSource<T> maybeSource, T t) {
        this.a = maybeSource;
        this.b = t;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamMaybeSource
    public MaybeSource<T> source() {
        return this.a;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements MaybeObserver<T>, Disposable {
        final SingleObserver<? super T> a;
        final T b;
        Disposable c;

        a(SingleObserver<? super T> singleObserver, T t) {
            this.a = singleObserver;
            this.b = t;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.c = DisposableHelper.DISPOSED;
            this.a.onSuccess(t);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.c = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.c = DisposableHelper.DISPOSED;
            T t = this.b;
            if (t != null) {
                this.a.onSuccess(t);
            } else {
                this.a.onError(new NoSuchElementException("The MaybeSource is empty"));
            }
        }
    }
}
