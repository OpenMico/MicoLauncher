package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class MaybeOnErrorNext<T> extends a<T, T> {
    final Function<? super Throwable, ? extends MaybeSource<? extends T>> a;
    final boolean b;

    public MaybeOnErrorNext(MaybeSource<T> maybeSource, Function<? super Throwable, ? extends MaybeSource<? extends T>> function, boolean z) {
        super(maybeSource);
        this.a = function;
        this.b = z;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 2026620218879969836L;
        final boolean allowFatal;
        final MaybeObserver<? super T> downstream;
        final Function<? super Throwable, ? extends MaybeSource<? extends T>> resumeFunction;

        a(MaybeObserver<? super T> maybeObserver, Function<? super Throwable, ? extends MaybeSource<? extends T>> function, boolean z) {
            this.downstream = maybeObserver;
            this.resumeFunction = function;
            this.allowFatal = z;
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
            if (this.allowFatal || (th instanceof Exception)) {
                try {
                    MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.resumeFunction.apply(th), "The resumeFunction returned a null MaybeSource");
                    DisposableHelper.replace(this, null);
                    maybeSource.subscribe(new C0236a(this.downstream, this));
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.downstream.onError(new CompositeException(th, th2));
                }
            } else {
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.downstream.onComplete();
        }

        /* renamed from: io.reactivex.internal.operators.maybe.MaybeOnErrorNext$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static final class C0236a<T> implements MaybeObserver<T> {
            final MaybeObserver<? super T> a;
            final AtomicReference<Disposable> b;

            C0236a(MaybeObserver<? super T> maybeObserver, AtomicReference<Disposable> atomicReference) {
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
