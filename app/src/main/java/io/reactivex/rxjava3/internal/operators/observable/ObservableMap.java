package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.observers.BasicFuseableObserver;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class ObservableMap<T, U> extends a<T, U> {
    final Function<? super T, ? extends U> a;

    public ObservableMap(ObservableSource<T> observableSource, Function<? super T, ? extends U> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super U> observer) {
        this.source.subscribe(new a(observer, this.a));
    }

    /* loaded from: classes5.dex */
    static final class a<T, U> extends BasicFuseableObserver<T, U> {
        final Function<? super T, ? extends U> a;

        a(Observer<? super U> observer, Function<? super T, ? extends U> function) {
            super(observer);
            this.a = function;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 0) {
                    this.downstream.onNext(null);
                    return;
                }
                try {
                    this.downstream.onNext(Objects.requireNonNull(this.a.apply(t), "The mapper function returned a null value."));
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public U poll() throws Throwable {
            T poll = this.qd.poll();
            if (poll != null) {
                return (U) Objects.requireNonNull(this.a.apply(poll), "The mapper function returned a null value.");
            }
            return null;
        }
    }
}
