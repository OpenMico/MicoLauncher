package io.reactivex.rxjava3.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes5.dex */
public final class SafeObserver<T> implements Observer<T>, Disposable {
    final Observer<? super T> a;
    Disposable b;
    boolean c;

    public SafeObserver(@NonNull Observer<? super T> observer) {
        this.a = observer;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(@NonNull Disposable disposable) {
        if (DisposableHelper.validate(this.b, disposable)) {
            this.b = disposable;
            try {
                this.a.onSubscribe(this);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.c = true;
                try {
                    disposable.dispose();
                    RxJavaPlugins.onError(th);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    RxJavaPlugins.onError(new CompositeException(th, th2));
                }
            }
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        this.b.dispose();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.b.isDisposed();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(@NonNull T t) {
        if (!this.c) {
            if (this.b == null) {
                a();
            } else if (t == null) {
                NullPointerException createNullPointerException = ExceptionHelper.createNullPointerException("onNext called with a null value.");
                try {
                    this.b.dispose();
                    onError(createNullPointerException);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    onError(new CompositeException(createNullPointerException, th));
                }
            } else {
                try {
                    this.a.onNext(t);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    try {
                        this.b.dispose();
                        onError(th2);
                    } catch (Throwable th3) {
                        Exceptions.throwIfFatal(th3);
                        onError(new CompositeException(th2, th3));
                    }
                }
            }
        }
    }

    void a() {
        this.c = true;
        NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
        try {
            this.a.onSubscribe(EmptyDisposable.INSTANCE);
            try {
                this.a.onError(nullPointerException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(nullPointerException, th));
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(new CompositeException(nullPointerException, th2));
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(@NonNull Throwable th) {
        if (this.c) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.c = true;
        if (this.b == null) {
            NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
            try {
                this.a.onSubscribe(EmptyDisposable.INSTANCE);
                try {
                    this.a.onError(new CompositeException(th, nullPointerException));
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    RxJavaPlugins.onError(new CompositeException(th, nullPointerException, th2));
                }
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(new CompositeException(th, nullPointerException, th3));
            }
        } else {
            if (th == null) {
                th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            try {
                this.a.onError(th);
            } catch (Throwable th4) {
                Exceptions.throwIfFatal(th4);
                RxJavaPlugins.onError(new CompositeException(th, th4));
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (!this.c) {
            this.c = true;
            if (this.b == null) {
                b();
                return;
            }
            try {
                this.a.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }

    void b() {
        NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
        try {
            this.a.onSubscribe(EmptyDisposable.INSTANCE);
            try {
                this.a.onError(nullPointerException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(nullPointerException, th));
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(new CompositeException(nullPointerException, th2));
        }
    }
}
