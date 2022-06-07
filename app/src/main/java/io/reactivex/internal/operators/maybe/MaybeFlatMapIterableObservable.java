package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final class MaybeFlatMapIterableObservable<T, R> extends Observable<R> {
    final MaybeSource<T> a;
    final Function<? super T, ? extends Iterable<? extends R>> b;

    public MaybeFlatMapIterableObservable(MaybeSource<T> maybeSource, Function<? super T, ? extends Iterable<? extends R>> function) {
        this.a = maybeSource;
        this.b = function;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        this.a.subscribe(new a(observer, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends BasicQueueDisposable<R> implements MaybeObserver<T> {
        final Observer<? super R> a;
        final Function<? super T, ? extends Iterable<? extends R>> b;
        Disposable c;
        volatile Iterator<? extends R> d;
        volatile boolean e;
        boolean f;

        a(Observer<? super R> observer, Function<? super T, ? extends Iterable<? extends R>> function) {
            this.a = observer;
            this.b = function;
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            Observer<? super R> observer = this.a;
            try {
                Iterator<? extends R> it = ((Iterable) this.b.apply(t)).iterator();
                if (!it.hasNext()) {
                    observer.onComplete();
                    return;
                }
                this.d = it;
                if (this.f) {
                    observer.onNext(null);
                    observer.onComplete();
                    return;
                }
                while (!this.e) {
                    try {
                        observer.onNext((Object) it.next());
                        if (!this.e) {
                            try {
                                if (!it.hasNext()) {
                                    observer.onComplete();
                                    return;
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                observer.onError(th);
                                return;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        observer.onError(th2);
                        return;
                    }
                }
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                observer.onError(th3);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.c = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.a.onComplete();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.e = true;
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.f = true;
            return 2;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.d = null;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.d == null;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public R poll() throws Exception {
            Iterator<? extends R> it = this.d;
            if (it == null) {
                return null;
            }
            R r = (R) ObjectHelper.requireNonNull(it.next(), "The iterator returned a null value");
            if (!it.hasNext()) {
                this.d = null;
            }
            return r;
        }
    }
}
