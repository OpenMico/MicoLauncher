package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.observers.ResumeSingleObserver;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class SingleDelayWithCompletable<T> extends Single<T> {
    final SingleSource<T> a;
    final CompletableSource b;

    public SingleDelayWithCompletable(SingleSource<T> singleSource, CompletableSource completableSource) {
        this.a = singleSource;
        this.b = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.b.subscribe(new a(singleObserver, this.a));
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
        private static final long serialVersionUID = -8565274649390031272L;
        final SingleObserver<? super T> downstream;
        final SingleSource<T> source;

        a(SingleObserver<? super T> singleObserver, SingleSource<T> singleSource) {
            this.downstream = singleObserver;
            this.source = singleSource;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.source.subscribe(new ResumeSingleObserver(this, this.downstream));
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }
    }
}
