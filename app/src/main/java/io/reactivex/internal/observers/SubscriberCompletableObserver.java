package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class SubscriberCompletableObserver<T> implements CompletableObserver, Subscription {
    final Subscriber<? super T> a;
    Disposable b;

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
    }

    public SubscriberCompletableObserver(Subscriber<? super T> subscriber) {
        this.a = subscriber;
    }

    @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
    public void onComplete() {
        this.a.onComplete();
    }

    @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
    public void onError(Throwable th) {
        this.a.onError(th);
    }

    @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.validate(this.b, disposable)) {
            this.b = disposable;
            this.a.onSubscribe(this);
        }
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        this.b.dispose();
    }
}
