package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.AbstractEmptyQueueFuseable;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamCompletableSource;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class FlowableFromCompletable<T> extends Flowable<T> implements HasUpstreamCompletableSource {
    final CompletableSource b;

    public FlowableFromCompletable(CompletableSource completableSource) {
        this.b = completableSource;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.HasUpstreamCompletableSource
    public CompletableSource source() {
        return this.b;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe(new FromCompletableObserver(subscriber));
    }

    /* loaded from: classes5.dex */
    public static final class FromCompletableObserver<T> extends AbstractEmptyQueueFuseable<T> implements CompletableObserver {
        final Subscriber<? super T> a;
        Disposable b;

        public FromCompletableObserver(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.AbstractEmptyQueueFuseable, org.reactivestreams.Subscription
        public void cancel() {
            this.b.dispose();
            this.b = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.b = DisposableHelper.DISPOSED;
            this.a.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.b = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }
    }
}
