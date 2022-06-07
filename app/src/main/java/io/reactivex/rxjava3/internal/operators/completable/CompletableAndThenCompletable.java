package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class CompletableAndThenCompletable extends Completable {
    final CompletableSource a;
    final CompletableSource b;

    public CompletableAndThenCompletable(CompletableSource completableSource, CompletableSource completableSource2) {
        this.a = completableSource;
        this.b = completableSource2;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new b(completableObserver, this.b));
    }

    /* loaded from: classes4.dex */
    static final class b extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
        private static final long serialVersionUID = -4101678820158072998L;
        final CompletableObserver actualObserver;
        final CompletableSource next;

        b(CompletableObserver completableObserver, CompletableSource completableSource) {
            this.actualObserver = completableObserver;
            this.next = completableSource;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.actualObserver.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.actualObserver.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.next.subscribe(new a(this, this.actualObserver));
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

    /* loaded from: classes4.dex */
    static final class a implements CompletableObserver {
        final AtomicReference<Disposable> a;
        final CompletableObserver b;

        a(AtomicReference<Disposable> atomicReference, CompletableObserver completableObserver) {
            this.a = atomicReference;
            this.b = completableObserver;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this.a, disposable);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.b.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.b.onError(th);
        }
    }
}
