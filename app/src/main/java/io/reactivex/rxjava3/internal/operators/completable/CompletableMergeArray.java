package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class CompletableMergeArray extends Completable {
    final CompletableSource[] a;

    public CompletableMergeArray(CompletableSource[] completableSourceArr) {
        this.a = completableSourceArr;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        a aVar = new a(completableObserver, new AtomicBoolean(), compositeDisposable, this.a.length + 1);
        completableObserver.onSubscribe(aVar);
        CompletableSource[] completableSourceArr = this.a;
        for (CompletableSource completableSource : completableSourceArr) {
            if (!compositeDisposable.isDisposed()) {
                if (completableSource == null) {
                    compositeDisposable.dispose();
                    aVar.onError(new NullPointerException("A completable source is null"));
                    return;
                }
                completableSource.subscribe(aVar);
            } else {
                return;
            }
        }
        aVar.onComplete();
    }

    /* loaded from: classes4.dex */
    static final class a extends AtomicInteger implements CompletableObserver, Disposable {
        private static final long serialVersionUID = -8360547806504310570L;
        final CompletableObserver downstream;
        final AtomicBoolean once;
        final CompositeDisposable set;

        a(CompletableObserver completableObserver, AtomicBoolean atomicBoolean, CompositeDisposable compositeDisposable, int i) {
            this.downstream = completableObserver;
            this.once = atomicBoolean;
            this.set = compositeDisposable;
            lazySet(i);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.set.dispose();
            if (this.once.compareAndSet(false, true)) {
                this.downstream.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            if (decrementAndGet() == 0) {
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.set.dispose();
            this.once.set(true);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.set.isDisposed();
        }
    }
}
