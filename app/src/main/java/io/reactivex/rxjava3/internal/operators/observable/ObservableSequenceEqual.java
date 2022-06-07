package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
public final class ObservableSequenceEqual<T> extends Observable<Boolean> {
    final ObservableSource<? extends T> a;
    final ObservableSource<? extends T> b;
    final BiPredicate<? super T, ? super T> c;
    final int d;

    public ObservableSequenceEqual(ObservableSource<? extends T> observableSource, ObservableSource<? extends T> observableSource2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        this.a = observableSource;
        this.b = observableSource2;
        this.c = biPredicate;
        this.d = i;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super Boolean> observer) {
        a aVar = new a(observer, this.d, this.a, this.b, this.c);
        observer.onSubscribe(aVar);
        aVar.a();
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -6178010334400373240L;
        volatile boolean cancelled;
        final BiPredicate<? super T, ? super T> comparer;
        final Observer<? super Boolean> downstream;
        final ObservableSource<? extends T> first;
        final b<T>[] observers;
        final ArrayCompositeDisposable resources = new ArrayCompositeDisposable(2);
        final ObservableSource<? extends T> second;
        T v1;
        T v2;

        a(Observer<? super Boolean> observer, int i, ObservableSource<? extends T> observableSource, ObservableSource<? extends T> observableSource2, BiPredicate<? super T, ? super T> biPredicate) {
            this.downstream = observer;
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

        @Override // io.reactivex.rxjava3.disposables.Disposable
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

        @Override // io.reactivex.rxjava3.disposables.Disposable
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
                                this.downstream.onNext(true);
                                this.downstream.onComplete();
                                return;
                            } else if (!z || !z2 || z3 == z4) {
                                if (!z3 && !z4) {
                                    try {
                                        if (!this.comparer.test((T) this.v1, (T) this.v2)) {
                                            a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                                            this.downstream.onNext(false);
                                            this.downstream.onComplete();
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
                                this.downstream.onNext(false);
                                this.downstream.onComplete();
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
    /* loaded from: classes5.dex */
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

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            this.a.a(disposable, this.c);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            this.b.offer(t);
            this.a.b();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.e = th;
            this.d = true;
            this.a.b();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.d = true;
            this.a.b();
        }
    }
}
