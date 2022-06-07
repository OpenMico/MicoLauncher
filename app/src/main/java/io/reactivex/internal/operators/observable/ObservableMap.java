package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicFuseableObserver;

/* loaded from: classes4.dex */
public final class ObservableMap<T, U> extends a<T, U> {
    final Function<? super T, ? extends U> a;

    public ObservableMap(ObservableSource<T> observableSource, Function<? super T, ? extends U> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super U> observer) {
        this.source.subscribe(new a(observer, this.a));
    }

    /* loaded from: classes4.dex */
    static final class a<T, U> extends BasicFuseableObserver<T, U> {
        final Function<? super T, ? extends U> a;

        a(Observer<? super U> observer, Function<? super T, ? extends U> function) {
            super(observer);
            this.a = function;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 0) {
                    this.downstream.onNext(null);
                    return;
                }
                try {
                    this.downstream.onNext(ObjectHelper.requireNonNull(this.a.apply(t), "The mapper function returned a null value."));
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
        public U poll() throws Exception {
            T poll = this.qd.poll();
            if (poll != null) {
                return (U) ObjectHelper.requireNonNull(this.a.apply(poll), "The mapper function returned a null value.");
            }
            return null;
        }
    }
}
