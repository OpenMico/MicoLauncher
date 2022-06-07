package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.observers.BasicFuseableObserver;
import java.util.Objects;
import java.util.Optional;

/* loaded from: classes4.dex */
public final class ObservableMapOptional<T, R> extends Observable<R> {
    final Observable<T> a;
    final Function<? super T, Optional<? extends R>> b;

    public ObservableMapOptional(Observable<T> observable, Function<? super T, Optional<? extends R>> function) {
        this.a = observable;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        this.a.subscribe(new a(observer, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends BasicFuseableObserver<T, R> {
        final Function<? super T, Optional<? extends R>> a;

        a(Observer<? super R> observer, Function<? super T, Optional<? extends R>> function) {
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
                    Optional optional = (Optional) Objects.requireNonNull(this.a.apply(t), "The mapper returned a null Optional");
                    if (optional.isPresent()) {
                        this.downstream.onNext(optional.get());
                    }
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
        public R poll() throws Throwable {
            Optional optional;
            do {
                T poll = this.qd.poll();
                if (poll == null) {
                    return null;
                }
                optional = (Optional) Objects.requireNonNull(this.a.apply(poll), "The mapper returned a null Optional");
            } while (!optional.isPresent());
            return (R) optional.get();
        }
    }
}
