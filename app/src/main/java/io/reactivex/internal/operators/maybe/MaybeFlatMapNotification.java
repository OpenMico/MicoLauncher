package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class MaybeFlatMapNotification<T, R> extends a<T, R> {
    final Function<? super T, ? extends MaybeSource<? extends R>> a;
    final Function<? super Throwable, ? extends MaybeSource<? extends R>> b;
    final Callable<? extends MaybeSource<? extends R>> c;

    public MaybeFlatMapNotification(MaybeSource<T> maybeSource, Function<? super T, ? extends MaybeSource<? extends R>> function, Function<? super Throwable, ? extends MaybeSource<? extends R>> function2, Callable<? extends MaybeSource<? extends R>> callable) {
        super(maybeSource);
        this.a = function;
        this.b = function2;
        this.c = callable;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a, this.b, this.c));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 4375739915521278546L;
        final MaybeObserver<? super R> downstream;
        final Callable<? extends MaybeSource<? extends R>> onCompleteSupplier;
        final Function<? super Throwable, ? extends MaybeSource<? extends R>> onErrorMapper;
        final Function<? super T, ? extends MaybeSource<? extends R>> onSuccessMapper;
        Disposable upstream;

        a(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends R>> function, Function<? super Throwable, ? extends MaybeSource<? extends R>> function2, Callable<? extends MaybeSource<? extends R>> callable) {
            this.downstream = maybeObserver;
            this.onSuccessMapper = function;
            this.onErrorMapper = function2;
            this.onCompleteSupplier = callable;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            this.upstream.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            try {
                ((MaybeSource) ObjectHelper.requireNonNull(this.onSuccessMapper.apply(t), "The onSuccessMapper returned a null MaybeSource")).subscribe(new C0234a());
            } catch (Exception e) {
                Exceptions.throwIfFatal(e);
                this.downstream.onError(e);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            try {
                ((MaybeSource) ObjectHelper.requireNonNull(this.onErrorMapper.apply(th), "The onErrorMapper returned a null MaybeSource")).subscribe(new C0234a());
            } catch (Exception e) {
                Exceptions.throwIfFatal(e);
                this.downstream.onError(new CompositeException(th, e));
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            try {
                ((MaybeSource) ObjectHelper.requireNonNull(this.onCompleteSupplier.call(), "The onCompleteSupplier returned a null MaybeSource")).subscribe(new C0234a());
            } catch (Exception e) {
                Exceptions.throwIfFatal(e);
                this.downstream.onError(e);
            }
        }

        /* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapNotification$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        final class C0234a implements MaybeObserver<R> {
            C0234a() {
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(a.this, disposable);
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSuccess(R r) {
                a.this.downstream.onSuccess(r);
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a.this.downstream.onError(th);
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                a.this.downstream.onComplete();
            }
        }
    }
}
