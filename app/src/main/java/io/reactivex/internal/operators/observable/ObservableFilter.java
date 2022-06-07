package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.observers.BasicFuseableObserver;

/* loaded from: classes4.dex */
public final class ObservableFilter<T> extends a<T, T> {
    final Predicate<? super T> a;

    public ObservableFilter(ObservableSource<T> observableSource, Predicate<? super T> predicate) {
        super(observableSource);
        this.a = predicate;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a));
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends BasicFuseableObserver<T, T> {
        final Predicate<? super T> a;

        a(Observer<? super T> observer, Predicate<? super T> predicate) {
            super(observer);
            this.a = predicate;
        }

        @Override // io.reactivex.Observer
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

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
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
