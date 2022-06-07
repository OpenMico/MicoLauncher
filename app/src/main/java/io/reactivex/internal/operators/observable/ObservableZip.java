package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableZip<T, R> extends Observable<R> {
    final ObservableSource<? extends T>[] a;
    final Iterable<? extends ObservableSource<? extends T>> b;
    final Function<? super Object[], ? extends R> c;
    final int d;
    final boolean e;

    public ObservableZip(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.a = observableSourceArr;
        this.b = iterable;
        this.c = function;
        this.d = i;
        this.e = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        int i;
        ObservableSource<? extends T>[] observableSourceArr = this.a;
        if (observableSourceArr == null) {
            observableSourceArr = new ObservableSource[8];
            i = 0;
            for (ObservableSource<? extends T> observableSource : this.b) {
                if (i == observableSourceArr.length) {
                    ObservableSource<? extends T>[] observableSourceArr2 = new ObservableSource[(i >> 2) + i];
                    System.arraycopy(observableSourceArr, 0, observableSourceArr2, 0, i);
                    observableSourceArr = observableSourceArr2;
                }
                i++;
                observableSourceArr[i] = observableSource;
            }
        } else {
            i = observableSourceArr.length;
        }
        if (i == 0) {
            EmptyDisposable.complete(observer);
        } else {
            new a(observer, this.c, i, this.e).a(observableSourceArr, this.d);
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 2983708048395377667L;
        volatile boolean cancelled;
        final boolean delayError;
        final Observer<? super R> downstream;
        final b<T, R>[] observers;
        final T[] row;
        final Function<? super Object[], ? extends R> zipper;

        a(Observer<? super R> observer, Function<? super Object[], ? extends R> function, int i, boolean z) {
            this.downstream = observer;
            this.zipper = function;
            this.observers = new b[i];
            this.row = (T[]) new Object[i];
            this.delayError = z;
        }

        public void a(ObservableSource<? extends T>[] observableSourceArr, int i) {
            b<T, R>[] bVarArr = this.observers;
            int length = bVarArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                bVarArr[i2] = new b<>(this, i);
            }
            lazySet(0);
            this.downstream.onSubscribe(this);
            for (int i3 = 0; i3 < length && !this.cancelled; i3++) {
                observableSourceArr[i3].subscribe(bVarArr[i3]);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                b();
                if (getAndIncrement() == 0) {
                    c();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a() {
            c();
            b();
        }

        void b() {
            for (b<T, R> bVar : this.observers) {
                bVar.a();
            }
        }

        void c() {
            for (b<T, R> bVar : this.observers) {
                bVar.b.clear();
            }
        }

        public void d() {
            Throwable th;
            if (getAndIncrement() == 0) {
                b<T, R>[] bVarArr = this.observers;
                Observer<? super R> observer = this.downstream;
                T[] tArr = this.row;
                boolean z = this.delayError;
                int i = 1;
                while (true) {
                    int i2 = 0;
                    int i3 = 0;
                    for (b<T, R> bVar : bVarArr) {
                        if (tArr[i3] == null) {
                            boolean z2 = bVar.c;
                            T poll = bVar.b.poll();
                            boolean z3 = poll == null;
                            if (a(z2, z3, observer, z, bVar)) {
                                return;
                            }
                            if (!z3) {
                                tArr[i3] = poll;
                            } else {
                                i2++;
                            }
                        } else if (bVar.c && !z && (th = bVar.d) != null) {
                            this.cancelled = true;
                            a();
                            observer.onError(th);
                            return;
                        }
                        i3++;
                    }
                    if (i2 != 0) {
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        try {
                            observer.onNext((Object) ObjectHelper.requireNonNull(this.zipper.apply(tArr.clone()), "The zipper returned a null value"));
                            Arrays.fill(tArr, (Object) null);
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            a();
                            observer.onError(th2);
                            return;
                        }
                    }
                }
            }
        }

        boolean a(boolean z, boolean z2, Observer<? super R> observer, boolean z3, b<?, ?> bVar) {
            if (this.cancelled) {
                a();
                return true;
            } else if (!z) {
                return false;
            } else {
                if (!z3) {
                    Throwable th = bVar.d;
                    if (th != null) {
                        this.cancelled = true;
                        a();
                        observer.onError(th);
                        return true;
                    } else if (!z2) {
                        return false;
                    } else {
                        this.cancelled = true;
                        a();
                        observer.onComplete();
                        return true;
                    }
                } else if (!z2) {
                    return false;
                } else {
                    Throwable th2 = bVar.d;
                    this.cancelled = true;
                    a();
                    if (th2 != null) {
                        observer.onError(th2);
                    } else {
                        observer.onComplete();
                    }
                    return true;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T, R> implements Observer<T> {
        final a<T, R> a;
        final SpscLinkedArrayQueue<T> b;
        volatile boolean c;
        Throwable d;
        final AtomicReference<Disposable> e = new AtomicReference<>();

        b(a<T, R> aVar, int i) {
            this.a = aVar;
            this.b = new SpscLinkedArrayQueue<>(i);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.e, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.b.offer(t);
            this.a.d();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.d = th;
            this.c = true;
            this.a.d();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.c = true;
            this.a.d();
        }

        public void a() {
            DisposableHelper.dispose(this.e);
        }
    }
}
