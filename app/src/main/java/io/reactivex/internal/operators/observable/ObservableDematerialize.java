package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class ObservableDematerialize<T, R> extends a<T, R> {
    final Function<? super T, ? extends Notification<R>> a;

    public ObservableDematerialize(ObservableSource<T> observableSource, Function<? super T, ? extends Notification<R>> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new a(observer, this.a));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> implements Observer<T>, Disposable {
        final Observer<? super R> a;
        final Function<? super T, ? extends Notification<R>> b;
        boolean c;
        Disposable d;

        a(Observer<? super R> observer, Function<? super T, ? extends Notification<R>> function) {
            this.a = observer;
            this.b = function;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.d.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.c) {
                try {
                    Notification notification = (Notification) ObjectHelper.requireNonNull(this.b.apply(t), "The selector returned a null Notification");
                    if (notification.isOnError()) {
                        this.d.dispose();
                        onError(notification.getError());
                    } else if (notification.isOnComplete()) {
                        this.d.dispose();
                        onComplete();
                    } else {
                        this.a.onNext((Object) notification.getValue());
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.d.dispose();
                    onError(th);
                }
            } else if (t instanceof Notification) {
                Notification notification2 = (Notification) t;
                if (notification2.isOnError()) {
                    RxJavaPlugins.onError(notification2.getError());
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.a.onComplete();
            }
        }
    }
}
