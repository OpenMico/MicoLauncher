package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class ObservableSequenceEqualSingle<T> extends Single<Boolean> implements FuseToObservable<Boolean> {
    final ObservableSource<? extends T> a;
    final ObservableSource<? extends T> b;
    final BiPredicate<? super T, ? super T> c;
    final int d;

    public ObservableSequenceEqualSingle(ObservableSource<? extends T> observableSource, ObservableSource<? extends T> observableSource2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        this.a = observableSource;
        this.b = observableSource2;
        this.c = biPredicate;
        this.d = i;
    }

    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        a aVar = new a(singleObserver, this.d, this.a, this.b, this.c);
        singleObserver.onSubscribe(aVar);
        aVar.a();
    }

    @Override // io.reactivex.internal.fuseable.FuseToObservable
    public Observable<Boolean> fuseToObservable() {
        return RxJavaPlugins.onAssembly(new ObservableSequenceEqual(this.a, this.b, this.c, this.d));
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -6178010334400373240L;
        volatile boolean cancelled;
        final BiPredicate<? super T, ? super T> comparer;
        final SingleObserver<? super Boolean> downstream;
        final ObservableSource<? extends T> first;
        final b<T>[] observers;
        final ArrayCompositeDisposable resources = new ArrayCompositeDisposable(2);
        final ObservableSource<? extends T> second;
        T v1;
        T v2;

        a(SingleObserver<? super Boolean> singleObserver, int i, ObservableSource<? extends T> observableSource, ObservableSource<? extends T> observableSource2, BiPredicate<? super T, ? super T> biPredicate) {
            this.downstream = singleObserver;
            this.first = observableSource;
            this.second = observableSource2;
            this.comparer = biPredicate;
            this.observers = r3;
            b<T>[] bVarArr = {new b<>(this, 0, i), new b<>(this, 1, i)};
        }

        boolean a(Disposable disposable, int i) {
            return this.resources.setResource(i, disposable);
        }

        void a() {
            b<T>[] bVarArr = this.observers;
            this.first.subscribe(bVarArr[0]);
            this.second.subscribe(bVarArr[1]);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.resources.dispose();
                if (getAndIncrement() == 0) {
                    b<T>[] bVarArr = this.observers;
                    bVarArr[0].b.clear();
                    bVarArr[1].b.clear();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a(SpscLinkedArrayQueue<T> spscLinkedArrayQueue, SpscLinkedArrayQueue<T> spscLinkedArrayQueue2) {
            this.cancelled = true;
            spscLinkedArrayQueue.clear();
            spscLinkedArrayQueue2.clear();
        }

        void b() {
            Throwable th;
            Throwable th2;
            if (getAndIncrement() == 0) {
                b<T>[] bVarArr = this.observers;
                b<T> bVar = bVarArr[0];
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue = bVar.b;
                b<T> bVar2 = bVarArr[1];
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue2 = bVar2.b;
                int i = 1;
                while (!this.cancelled) {
                    boolean z = bVar.d;
                    if (!z || (th2 = bVar.e) == null) {
                        boolean z2 = bVar2.d;
                        if (!z2 || (th = bVar2.e) == null) {
                            if (this.v1 == null) {
                                this.v1 = spscLinkedArrayQueue.poll();
                            }
                            boolean z3 = this.v1 == null;
                            if (this.v2 == null) {
                                this.v2 = spscLinkedArrayQueue2.poll();
                            }
                            boolean z4 = this.v2 == null;
                            if (z && z2 && z3 && z4) {
                                this.downstream.onSuccess(true);
                                return;
                            } else if (!z || !z2 || z3 == z4) {
                                if (!z3 && !z4) {
                                    try {
                                        if (!this.comparer.test((T) this.v1, (T) this.v2)) {
                                            a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                                            this.downstream.onSuccess(false);
                                            return;
                                        }
                                        this.v1 = null;
                                        this.v2 = null;
                                    } catch (Throwable th3) {
                                        Exceptions.throwIfFatal(th3);
                                        a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                                        this.downstream.onError(th3);
                                        return;
                                    }
                                }
                                if (z3 || z4) {
                                    i = addAndGet(-i);
                                    if (i == 0) {
                                        return;
                                    }
                                }
                            } else {
                                a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                                this.downstream.onSuccess(false);
                                return;
                            }
                        } else {
                            a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                            this.downstream.onError(th);
                            return;
                        }
                    } else {
                        a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                        this.downstream.onError(th2);
                        return;
                    }
                }
                spscLinkedArrayQueue.clear();
                spscLinkedArrayQueue2.clear();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> implements Observer<T> {
        final a<T> a;
        final SpscLinkedArrayQueue<T> b;
        final int c;
        volatile boolean d;
        Throwable e;

        b(a<T> aVar, int i, int i2) {
            this.a = aVar;
            this.c = i;
            this.b = new SpscLinkedArrayQueue<>(i2);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            this.a.a(disposable, this.c);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.b.offer(t);
            this.a.b();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.e = th;
            this.d = true;
            this.a.b();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.d = true;
            this.a.b();
        }
    }
}
