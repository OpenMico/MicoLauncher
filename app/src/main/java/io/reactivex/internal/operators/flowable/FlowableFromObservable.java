package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableFromObservable<T> extends Flowable<T> {
    private final Observable<T> b;

    public FlowableFromObservable(Observable<T> observable) {
        this.b = observable;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe(new a(subscriber));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T>, Subscription {
        final Subscriber<? super T> a;
        Disposable b;

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
        }

        a(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // io.reactivex.Observer
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
