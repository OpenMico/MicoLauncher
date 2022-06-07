package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class MaybeSwitchIfEmpty<T> extends a<T, T> {
    final MaybeSource<? extends T> a;

    public MaybeSwitchIfEmpty(MaybeSource<T> maybeSource, MaybeSource<? extends T> maybeSource2) {
        super(maybeSource);
        this.a = maybeSource2;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a));
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = -2223459372976438024L;
        final MaybeObserver<? super T> downstream;
        final MaybeSource<? extends T> other;

        a(MaybeObserver<? super T> maybeObserver, MaybeSource<? extends T> maybeSource) {
            this.downstream = maybeObserver;
            this.other = maybeSource;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.downstream.onSuccess(t);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            Disposable disposable = get();
            if (disposable != DisposableHelper.DISPOSED && compareAndSet(disposable, null)) {
                this.other.subscribe(new C0237a(this.downstream, this));
            }
        }

        /* renamed from: io.reactivex.internal.operators.maybe.MaybeSwitchIfEmpty$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static final class C0237a<T> implements MaybeObserver<T> {
            final MaybeObserver<? super T> a;
            final AtomicReference<Disposable> b;

            C0237a(MaybeObserver<? super T> maybeObserver, AtomicReference<Disposable> atomicReference) {
                this.a = maybeObserver;
                this.b = atomicReference;
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this.b, disposable);
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSuccess(T t) {
                this.a.onSuccess(t);
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.a.onError(th);
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                this.a.onComplete();
            }
        }
    }
}
