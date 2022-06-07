package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.observers.BasicQueueDisposable;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class ObservableFromIterable<T> extends Observable<T> {
    final Iterable<? extends T> a;

    public ObservableFromIterable(Iterable<? extends T> iterable) {
        this.a = iterable;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        try {
            Iterator<? extends T> it = this.a.iterator();
            try {
                if (!it.hasNext()) {
                    EmptyDisposable.complete(observer);
                    return;
                }
                a aVar = new a(observer, it);
                observer.onSubscribe(aVar);
                if (!aVar.d) {
                    aVar.a();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptyDisposable.error(th2, observer);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends BasicQueueDisposable<T> {
        final Observer<? super T> a;
        final Iterator<? extends T> b;
        volatile boolean c;
        boolean d;
        boolean e;
        boolean f;

        a(Observer<? super T> observer, Iterator<? extends T> it) {
            this.a = observer;
            this.b = it;
        }

        void a() {
            while (!isDisposed()) {
                try {
                    this.a.onNext(Objects.requireNonNull(this.b.next(), "The iterator returned a null value"));
                    if (!isDisposed()) {
                        try {
                            if (!this.b.hasNext()) {
                                if (!isDisposed()) {
                                    this.a.onComplete();
                                    return;
                                }
                                return;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.a.onError(th);
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.a.onError(th2);
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 1) == 0) {
                return 0;
            }
            this.d = true;
            return 1;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            if (this.e) {
                return null;
            }
            if (!this.f) {
                this.f = true;
            } else if (!this.b.hasNext()) {
                this.e = true;
                return null;
            }
            return (T) Objects.requireNonNull(this.b.next(), "The iterator returned a null value");
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.e;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.e = true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.c = true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }
    }
}
