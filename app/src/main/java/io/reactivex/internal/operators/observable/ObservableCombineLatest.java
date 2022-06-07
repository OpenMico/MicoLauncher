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
import io.reactivex.internal.util.AtomicThrowable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableCombineLatest<T, R> extends Observable<R> {
    final ObservableSource<? extends T>[] a;
    final Iterable<? extends ObservableSource<? extends T>> b;
    final Function<? super Object[], ? extends R> c;
    final int d;
    final boolean e;

    public ObservableCombineLatest(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i, boolean z) {
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
            int i2 = 0;
            for (ObservableSource<? extends T> observableSource : this.b) {
                if (i2 == observableSourceArr.length) {
                    ObservableSource<? extends T>[] observableSourceArr2 = new ObservableSource[(i2 >> 2) + i2];
                    System.arraycopy(observableSourceArr, 0, observableSourceArr2, 0, i2);
                    observableSourceArr = observableSourceArr2;
                }
                i2++;
                observableSourceArr[i2] = observableSource;
            }
            i = i2;
        } else {
            i = observableSourceArr.length;
        }
        if (i == 0) {
            EmptyDisposable.complete(observer);
        } else {
            new b(observer, this.c, i, this.d, this.e).a(observableSourceArr);
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 8567835998786448817L;
        int active;
        volatile boolean cancelled;
        final Function<? super Object[], ? extends R> combiner;
        int complete;
        final boolean delayError;
        volatile boolean done;
        final Observer<? super R> downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        Object[] latest;
        final a<T, R>[] observers;
        final SpscLinkedArrayQueue<Object[]> queue;

        b(Observer<? super R> observer, Function<? super Object[], ? extends R> function, int i, int i2, boolean z) {
            this.downstream = observer;
            this.combiner = function;
            this.delayError = z;
            this.latest = new Object[i];
            a<T, R>[] aVarArr = new a[i];
            for (int i3 = 0; i3 < i; i3++) {
                aVarArr[i3] = new a<>(this, i3);
            }
            this.observers = aVarArr;
            this.queue = new SpscLinkedArrayQueue<>(i2);
        }

        public void a(ObservableSource<? extends T>[] observableSourceArr) {
            a<T, R>[] aVarArr = this.observers;
            int length = aVarArr.length;
            this.downstream.onSubscribe(this);
            for (int i = 0; i < length && !this.done && !this.cancelled; i++) {
                observableSourceArr[i].subscribe(aVarArr[i]);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                a();
                if (getAndIncrement() == 0) {
                    a((SpscLinkedArrayQueue<?>) this.queue);
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a() {
            for (a<T, R> aVar : this.observers) {
                aVar.a();
            }
        }

        void a(SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            synchronized (this) {
                this.latest = null;
            }
            spscLinkedArrayQueue.clear();
        }

        void b() {
            if (getAndIncrement() == 0) {
                SpscLinkedArrayQueue<Object[]> spscLinkedArrayQueue = this.queue;
                Observer<? super R> observer = this.downstream;
                boolean z = this.delayError;
                int i = 1;
                while (!this.cancelled) {
                    if (z || this.errors.get() == null) {
                        boolean z2 = this.done;
                        Object[] poll = spscLinkedArrayQueue.poll();
                        boolean z3 = poll == null;
                        if (z2 && z3) {
                            a((SpscLinkedArrayQueue<?>) spscLinkedArrayQueue);
                            Throwable terminate = this.errors.terminate();
                            if (terminate == null) {
                                observer.onComplete();
                                return;
                            } else {
                                observer.onError(terminate);
                                return;
                            }
                        } else if (z3) {
                            i = addAndGet(-i);
                            if (i == 0) {
                                return;
                            }
                        } else {
                            try {
                                observer.onNext((Object) ObjectHelper.requireNonNull(this.combiner.apply(poll), "The combiner returned a null value"));
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.errors.addThrowable(th);
                                a();
                                a((SpscLinkedArrayQueue<?>) spscLinkedArrayQueue);
                                observer.onError(this.errors.terminate());
                                return;
                            }
                        }
                    } else {
                        a();
                        a((SpscLinkedArrayQueue<?>) spscLinkedArrayQueue);
                        observer.onError(this.errors.terminate());
                        return;
                    }
                }
                a((SpscLinkedArrayQueue<?>) spscLinkedArrayQueue);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void a(int i, T t) {
            boolean z;
            synchronized (this) {
                Object[] objArr = this.latest;
                if (objArr != null) {
                    Object obj = objArr[i];
                    int i2 = this.active;
                    if (obj == null) {
                        i2++;
                        this.active = i2;
                    }
                    objArr[i] = t;
                    if (i2 == objArr.length) {
                        this.queue.offer(objArr.clone());
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        b();
                    }
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0023, code lost:
            if (r1 == r4.length) goto L_0x0025;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a(int r3, java.lang.Throwable r4) {
            /*
                r2 = this;
                io.reactivex.internal.util.AtomicThrowable r0 = r2.errors
                boolean r0 = r0.addThrowable(r4)
                if (r0 == 0) goto L_0x0036
                boolean r4 = r2.delayError
                r0 = 1
                if (r4 == 0) goto L_0x002c
                monitor-enter(r2)
                java.lang.Object[] r4 = r2.latest     // Catch: all -> 0x0029
                if (r4 != 0) goto L_0x0014
                monitor-exit(r2)     // Catch: all -> 0x0029
                return
            L_0x0014:
                r3 = r4[r3]     // Catch: all -> 0x0029
                if (r3 != 0) goto L_0x001a
                r3 = r0
                goto L_0x001b
            L_0x001a:
                r3 = 0
            L_0x001b:
                if (r3 != 0) goto L_0x0025
                int r1 = r2.complete     // Catch: all -> 0x0029
                int r1 = r1 + r0
                r2.complete = r1     // Catch: all -> 0x0029
                int r4 = r4.length     // Catch: all -> 0x0029
                if (r1 != r4) goto L_0x0027
            L_0x0025:
                r2.done = r0     // Catch: all -> 0x0029
            L_0x0027:
                monitor-exit(r2)     // Catch: all -> 0x0029
                goto L_0x002d
            L_0x0029:
                r3 = move-exception
                monitor-exit(r2)     // Catch: all -> 0x0029
                throw r3
            L_0x002c:
                r3 = r0
            L_0x002d:
                if (r3 == 0) goto L_0x0032
                r2.a()
            L_0x0032:
                r2.b()
                goto L_0x0039
            L_0x0036:
                io.reactivex.plugins.RxJavaPlugins.onError(r4)
            L_0x0039:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableCombineLatest.b.a(int, java.lang.Throwable):void");
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0017, code lost:
            if (r2 == r0.length) goto L_0x0019;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a(int r4) {
            /*
                r3 = this;
                monitor-enter(r3)
                java.lang.Object[] r0 = r3.latest     // Catch: all -> 0x0025
                if (r0 != 0) goto L_0x0007
                monitor-exit(r3)     // Catch: all -> 0x0025
                return
            L_0x0007:
                r4 = r0[r4]     // Catch: all -> 0x0025
                r1 = 1
                if (r4 != 0) goto L_0x000e
                r4 = r1
                goto L_0x000f
            L_0x000e:
                r4 = 0
            L_0x000f:
                if (r4 != 0) goto L_0x0019
                int r2 = r3.complete     // Catch: all -> 0x0025
                int r2 = r2 + r1
                r3.complete = r2     // Catch: all -> 0x0025
                int r0 = r0.length     // Catch: all -> 0x0025
                if (r2 != r0) goto L_0x001b
            L_0x0019:
                r3.done = r1     // Catch: all -> 0x0025
            L_0x001b:
                monitor-exit(r3)     // Catch: all -> 0x0025
                if (r4 == 0) goto L_0x0021
                r3.a()
            L_0x0021:
                r3.b()
                return
            L_0x0025:
                r4 = move-exception
                monitor-exit(r3)     // Catch: all -> 0x0025
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableCombineLatest.b.a(int):void");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, R> extends AtomicReference<Disposable> implements Observer<T> {
        private static final long serialVersionUID = -4823716997131257941L;
        final int index;
        final b<T, R> parent;

        a(b<T, R> bVar, int i) {
            this.parent = bVar;
            this.index = i;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.parent.a(this.index, (int) t);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.parent.a(this.index, th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.parent.a(this.index);
        }

        public void a() {
            DisposableHelper.dispose(this);
        }
    }
}
