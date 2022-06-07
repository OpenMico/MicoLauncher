package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.fuseable.QueueDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public final class ObservableFromStream<T> extends Observable<T> {
    final Stream<T> a;

    public ObservableFromStream(Stream<T> stream) {
        this.a = stream;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        subscribeStream(observer, this.a);
    }

    public static <T> void subscribeStream(Observer<? super T> observer, Stream<T> stream) {
        try {
            Iterator<T> it = stream.iterator();
            if (!it.hasNext()) {
                EmptyDisposable.complete(observer);
                a(stream);
                return;
            }
            a aVar = new a(observer, it, stream);
            observer.onSubscribe(aVar);
            aVar.a();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
            a(stream);
        }
    }

    static void a(AutoCloseable autoCloseable) {
        try {
            autoCloseable.close();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> implements QueueDisposable<T> {
        final Observer<? super T> a;
        Iterator<T> b;
        AutoCloseable c;
        volatile boolean d;
        boolean e;
        boolean f;

        a(Observer<? super T> observer, Iterator<T> it, AutoCloseable autoCloseable) {
            this.a = observer;
            this.b = it;
            this.c = autoCloseable;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.d = true;
            a();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.d;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 1) == 0) {
                return 0;
            }
            this.f = true;
            return 1;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean offer(@NonNull T t) {
            throw new UnsupportedOperationException();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean offer(@NonNull T t, @NonNull T t2) {
            throw new UnsupportedOperationException();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            Iterator<T> it = this.b;
            if (it == null) {
                return null;
            }
            if (!this.e) {
                this.e = true;
            } else if (!it.hasNext()) {
                clear();
                return null;
            }
            return (T) Objects.requireNonNull(this.b.next(), "The Stream's Iterator.next() returned a null value");
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            Iterator<T> it = this.b;
            if (it == null) {
                return true;
            }
            if (!this.e || it.hasNext()) {
                return false;
            }
            clear();
            return true;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.b = null;
            AutoCloseable autoCloseable = this.c;
            this.c = null;
            if (autoCloseable != null) {
                ObservableFromStream.a(autoCloseable);
            }
        }

        public void a() {
            if (!this.f) {
                Iterator<T> it = this.b;
                Observer<? super T> observer = this.a;
                while (!this.d) {
                    try {
                        Object obj = (Object) Objects.requireNonNull(it.next(), "The Stream's Iterator.next returned a null value");
                        if (!this.d) {
                            observer.onNext(obj);
                            if (!this.d) {
                                try {
                                    if (!it.hasNext()) {
                                        observer.onComplete();
                                        this.d = true;
                                    }
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    observer.onError(th);
                                    this.d = true;
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        observer.onError(th2);
                        this.d = true;
                    }
                }
                clear();
            }
        }
    }
}
