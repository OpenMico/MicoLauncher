package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class MaybeFlatMapNotification<T, R> extends a<T, R> {
    final Function<? super T, ? extends MaybeSource<? extends R>> a;
    final Function<? super Throwable, ? extends MaybeSource<? extends R>> b;
    final Supplier<? extends MaybeSource<? extends R>> c;

    public MaybeFlatMapNotification(MaybeSource<T> maybeSource, Function<? super T, ? extends MaybeSource<? extends R>> function, Function<? super Throwable, ? extends MaybeSource<? extends R>> function2, Supplier<? extends MaybeSource<? extends R>> supplier) {
        super(maybeSource);
        this.a = function;
        this.b = function2;
        this.c = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a, this.b, this.c));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 4375739915521278546L;
        final MaybeObserver<? super R> downstream;
        final Supplier<? extends MaybeSource<? extends R>> onCompleteSupplier;
        final Function<? super Throwable, ? extends MaybeSource<? extends R>> onErrorMapper;
        final Function<? super T, ? extends MaybeSource<? extends R>> onSuccessMapper;
        Disposable upstream;

        a(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends R>> function, Function<? super Throwable, ? extends MaybeSource<? extends R>> function2, Supplier<? extends MaybeSource<? extends R>> supplier) {
            this.downstream = maybeObserver;
            this.onSuccessMapper = function;
            this.onErrorMapper = function2;
            this.onCompleteSupplier = supplier;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
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
            try {
                MaybeSource maybeSource = (MaybeSource) Objects.requireNonNull(this.onSuccessMapper.apply(t), "The onSuccessMapper returned a null MaybeSource");
                if (!isDisposed()) {
                    maybeSource.subscribe(new C0299a());
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            try {
                MaybeSource maybeSource = (MaybeSource) Objects.requireNonNull(this.onErrorMapper.apply(th), "The onErrorMapper returned a null MaybeSource");
                if (!isDisposed()) {
                    maybeSource.subscribe(new C0299a());
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            try {
                MaybeSource maybeSource = (MaybeSource) Objects.requireNonNull(this.onCompleteSupplier.get(), "The onCompleteSupplier returned a null MaybeSource");
                if (!isDisposed()) {
                    maybeSource.subscribe(new C0299a());
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapNotification$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class C0299a implements MaybeObserver<R> {
            C0299a() {
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(a.this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver
            public void onSuccess(R r) {
                a.this.downstream.onSuccess(r);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver
            public void onError(Throwable th) {
                a.this.downstream.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver
            public void onComplete() {
                a.this.downstream.onComplete();
            }
        }
    }
}
