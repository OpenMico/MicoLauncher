package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableFromObservable<T> extends Flowable<T> {
    private final ObservableSource<T> b;

    public FlowableFromObservable(ObservableSource<T> observableSource) {
        this.b = observableSource;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe(new a(subscriber));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements Observer<T>, Subscription {
        final Subscriber<? super T> a;
        Disposable b;

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
        }

        a(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.a.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            this.b = disposable;
            this.a.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.b.dispose();
        }
    }
}
