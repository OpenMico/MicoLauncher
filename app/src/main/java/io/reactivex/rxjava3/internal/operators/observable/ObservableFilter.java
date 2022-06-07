package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.observers.BasicFuseableObserver;

/* loaded from: classes5.dex */
public final class ObservableFilter<T> extends a<T, T> {
    final Predicate<? super T> a;

    public ObservableFilter(ObservableSource<T> observableSource, Predicate<? super T> predicate) {
        super(observableSource);
        this.a = predicate;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a));
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends BasicFuseableObserver<T, T> {
        final Predicate<? super T> a;

        a(Observer<? super T> observer, Predicate<? super T> predicate) {
            super(observer);
            this.a = predicate;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (this.sourceMode == 0) {
                try {
                    if (this.a.test(t)) {
                        this.downstream.onNext(t);
                    }
                } catch (Throwable th) {
                    fail(th);
                }
            } else {
                this.downstream.onNext(null);
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T poll;
            do {
                poll = this.qd.poll();
                if (poll == null) {
                    break;
                }
            } while (!this.a.test(poll));
            return poll;
        }
    }
}
