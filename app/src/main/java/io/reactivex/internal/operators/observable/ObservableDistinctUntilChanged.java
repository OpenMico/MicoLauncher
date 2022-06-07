package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.internal.observers.BasicFuseableObserver;

/* loaded from: classes4.dex */
public final class ObservableDistinctUntilChanged<T, K> extends a<T, T> {
    final Function<? super T, K> a;
    final BiPredicate<? super K, ? super K> b;

    public ObservableDistinctUntilChanged(ObservableSource<T> observableSource, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
        super(observableSource);
        this.a = function;
        this.b = biPredicate;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T, K> extends BasicFuseableObserver<T, T> {
        final Function<? super T, K> a;
        final BiPredicate<? super K, ? super K> b;
        K c;
        boolean d;

        a(Observer<? super T> observer, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            super(observer);
            this.a = function;
            this.b = biPredicate;
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 0) {
                    this.downstream.onNext(t);
                    return;
                }
                try {
                    K apply = this.a.apply(t);
                    if (this.d) {
                        boolean test = this.b.test((K) this.c, apply);
                        this.c = apply;
                        if (test) {
                            return;
                        }
                    } else {
                        this.d = true;
                        this.c = apply;
                    }
                    this.downstream.onNext(t);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            while (true) {
                T poll = this.qd.poll();
                if (poll == null) {
                    return null;
                }
                K apply = this.a.apply(poll);
                if (!this.d) {
                    this.d = true;
                    this.c = apply;
                    return poll;
                } else if (!this.b.test((K) this.c, apply)) {
                    this.c = apply;
                    return poll;
                } else {
                    this.c = apply;
                }
            }
        }
    }
}
