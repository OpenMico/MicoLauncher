package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.BasicFuseableObserver;

/* loaded from: classes4.dex */
public final class ObservableDoAfterNext<T> extends a<T, T> {
    final Consumer<? super T> a;

    public ObservableDoAfterNext(ObservableSource<T> observableSource, Consumer<? super T> consumer) {
        super(observableSource);
        this.a = consumer;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a));
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends BasicFuseableObserver<T, T> {
        final Consumer<? super T> a;

        a(Observer<? super T> observer, Consumer<? super T> consumer) {
            super(observer);
            this.a = consumer;
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.downstream.onNext(t);
            if (this.sourceMode == 0) {
                try {
                    this.a.accept(t);
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
            T poll = this.qd.poll();
            if (poll != null) {
                this.a.accept(poll);
            }
            return poll;
        }
    }
}
