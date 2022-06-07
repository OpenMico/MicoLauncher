package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
public final class ObservableRepeat<T> extends a<T, T> {
    final long a;

    public ObservableRepeat(Observable<T> observable, long j) {
        super(observable);
        this.a = j;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        observer.onSubscribe(sequentialDisposable);
        long j = this.a;
        long j2 = Long.MAX_VALUE;
        if (j != Long.MAX_VALUE) {
            j2 = j - 1;
        }
        new a(observer, j2, sequentialDisposable, this.source).a();
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements Observer<T> {
        private static final long serialVersionUID = -7098360935104053232L;
        final Observer<? super T> downstream;
        long remaining;
        final SequentialDisposable sd;
        final ObservableSource<? extends T> source;

        a(Observer<? super T> observer, long j, SequentialDisposable sequentialDisposable, ObservableSource<? extends T> observableSource) {
            this.downstream = observer;
            this.sd = sequentialDisposable;
            this.source = observableSource;
            this.remaining = j;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            this.sd.replace(disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            long j = this.remaining;
            if (j != Long.MAX_VALUE) {
                this.remaining = j - 1;
            }
            if (j != 0) {
                a();
            } else {
                this.downstream.onComplete();
            }
        }

        void a() {
            if (getAndIncrement() == 0) {
                int i = 1;
                while (!this.sd.isDisposed()) {
                    this.source.subscribe(this);
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }
    }
}
