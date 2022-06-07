package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class SingleFlatMapNotification<T, R> extends Single<R> {
    final SingleSource<T> a;
    final Function<? super T, ? extends SingleSource<? extends R>> b;
    final Function<? super Throwable, ? extends SingleSource<? extends R>> c;

    public SingleFlatMapNotification(SingleSource<T> singleSource, Function<? super T, ? extends SingleSource<? extends R>> function, Function<? super Throwable, ? extends SingleSource<? extends R>> function2) {
        this.a = singleSource;
        this.b = function;
        this.c = function2;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.b, this.c));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = 4375739915521278546L;
        final SingleObserver<? super R> downstream;
        final Function<? super Throwable, ? extends SingleSource<? extends R>> onErrorMapper;
        final Function<? super T, ? extends SingleSource<? extends R>> onSuccessMapper;
        Disposable upstream;

        a(SingleObserver<? super R> singleObserver, Function<? super T, ? extends SingleSource<? extends R>> function, Function<? super Throwable, ? extends SingleSource<? extends R>> function2) {
            this.downstream = singleObserver;
            this.onSuccessMapper = function;
            this.onErrorMapper = function2;
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

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t) {
            try {
                SingleSource singleSource = (SingleSource) Objects.requireNonNull(this.onSuccessMapper.apply(t), "The onSuccessMapper returned a null SingleSource");
                if (!isDisposed()) {
                    singleSource.subscribe(new C0341a());
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            try {
                SingleSource singleSource = (SingleSource) Objects.requireNonNull(this.onErrorMapper.apply(th), "The onErrorMapper returned a null SingleSource");
                if (!isDisposed()) {
                    singleSource.subscribe(new C0341a());
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
            }
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.single.SingleFlatMapNotification$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class C0341a implements SingleObserver<R> {
            C0341a() {
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(a.this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R r) {
                a.this.downstream.onSuccess(r);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                a.this.downstream.onError(th);
            }
        }
    }
}
