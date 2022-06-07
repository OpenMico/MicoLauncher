package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes5.dex */
public final class MaybePeek<T> extends a<T, T> {
    final Consumer<? super Disposable> a;
    final Consumer<? super T> b;
    final Consumer<? super Throwable> c;
    final Action d;
    final Action e;
    final Action f;

    public MaybePeek(MaybeSource<T> maybeSource, Consumer<? super Disposable> consumer, Consumer<? super T> consumer2, Consumer<? super Throwable> consumer3, Action action, Action action2, Action action3) {
        super(maybeSource);
        this.a = consumer;
        this.b = consumer2;
        this.c = consumer3;
        this.d = action;
        this.e = action2;
        this.f = action3;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> a;
        final MaybePeek<T> b;
        Disposable c;

        a(MaybeObserver<? super T> maybeObserver, MaybePeek<T> maybePeek) {
            this.a = maybeObserver;
            this.b = maybePeek;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            try {
                this.b.f.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                try {
                    this.b.a.accept(disposable);
                    this.c = disposable;
                    this.a.onSubscribe(this);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    disposable.dispose();
                    this.c = DisposableHelper.DISPOSED;
                    EmptyDisposable.error(th, this.a);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(T t) {
            if (this.c != DisposableHelper.DISPOSED) {
                try {
                    this.b.b.accept(t);
                    this.c = DisposableHelper.DISPOSED;
                    this.a.onSuccess(t);
                    a();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    a(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            if (this.c == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
            } else {
                a(th);
            }
        }

        void a(Throwable th) {
            try {
                this.b.c.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.c = DisposableHelper.DISPOSED;
            this.a.onError(th);
            a();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            if (this.c != DisposableHelper.DISPOSED) {
                try {
                    this.b.d.run();
                    this.c = DisposableHelper.DISPOSED;
                    this.a.onComplete();
                    a();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    a(th);
                }
            }
        }

        void a() {
            try {
                this.b.e.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }
}
