package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

/* loaded from: classes4.dex */
public final class MaybeContains<T> extends Single<Boolean> implements HasUpstreamMaybeSource<T> {
    final MaybeSource<T> a;
    final Object b;

    public MaybeContains(MaybeSource<T> maybeSource, Object obj) {
        this.a = maybeSource;
        this.b = obj;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamMaybeSource
    public MaybeSource<T> source() {
        return this.a;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a implements MaybeObserver<Object>, Disposable {
        final SingleObserver<? super Boolean> a;
        final Object b;
        Disposable c;

        a(SingleObserver<? super Boolean> singleObserver, Object obj) {
            this.a = singleObserver;
            this.b = obj;
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
        public void onSuccess(Object obj) {
            this.c = DisposableHelper.DISPOSED;
            this.a.onSuccess(Boolean.valueOf(ObjectHelper.equals(obj, this.b)));
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.c = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.c = DisposableHelper.DISPOSED;
            this.a.onSuccess(false);
        }
    }
}
