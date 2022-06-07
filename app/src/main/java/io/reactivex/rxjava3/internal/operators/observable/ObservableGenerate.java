package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes5.dex */
public final class ObservableGenerate<T, S> extends Observable<T> {
    final Supplier<S> a;
    final BiFunction<S, Emitter<T>, S> b;
    final Consumer<? super S> c;

    public ObservableGenerate(Supplier<S> supplier, BiFunction<S, Emitter<T>, S> biFunction, Consumer<? super S> consumer) {
        this.a = supplier;
        this.b = biFunction;
        this.c = consumer;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        try {
            a aVar = new a(observer, this.b, this.c, this.a.get());
            observer.onSubscribe(aVar);
            aVar.a();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, S> implements Emitter<T>, Disposable {
        final Observer<? super T> a;
        final BiFunction<S, ? super Emitter<T>, S> b;
        final Consumer<? super S> c;
        S d;
        volatile boolean e;
        boolean f;
        boolean g;

        a(Observer<? super T> observer, BiFunction<S, ? super Emitter<T>, S> biFunction, Consumer<? super S> consumer, S s) {
            this.a = observer;
            this.b = biFunction;
            this.c = consumer;
            this.d = s;
        }

        public void a() {
            S s = this.d;
            if (this.e) {
                this.d = null;
                a(s);
                return;
            }
            BiFunction<S, ? super Emitter<T>, S> biFunction = this.b;
            while (!this.e) {
                this.g = false;
                try {
                    s = biFunction.apply(s, this);
                    if (this.f) {
                        this.e = true;
                        this.d = null;
                        a(s);
                        return;
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.d = null;
                    this.e = true;
                    onError(th);
                    a(s);
                    return;
                }
            }
            this.d = null;
            a(s);
        }

        private void a(S s) {
            try {
                this.c.accept(s);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.e = true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.e;
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(T t) {
            if (this.f) {
                return;
            }
            if (this.g) {
                onError(new IllegalStateException("onNext already called in this generate turn"));
            } else if (t == null) {
                onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
            } else {
                this.g = true;
                this.a.onNext(t);
            }
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onError(Throwable th) {
            if (this.f) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (th == null) {
                th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            this.f = true;
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            if (!this.f) {
                this.f = true;
                this.a.onComplete();
            }
        }
    }
}
