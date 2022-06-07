package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class CompletableMergeArrayDelayError extends Completable {
    final CompletableSource[] a;

    public CompletableMergeArrayDelayError(CompletableSource[] completableSourceArr) {
        this.a = completableSourceArr;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AtomicInteger atomicInteger = new AtomicInteger(this.a.length + 1);
        AtomicThrowable atomicThrowable = new AtomicThrowable();
        compositeDisposable.add(new b(atomicThrowable));
        completableObserver.onSubscribe(compositeDisposable);
        CompletableSource[] completableSourceArr = this.a;
        for (CompletableSource completableSource : completableSourceArr) {
            if (!compositeDisposable.isDisposed()) {
                if (completableSource == null) {
                    atomicThrowable.tryAddThrowableOrReport(new NullPointerException("A completable source is null"));
                    atomicInteger.decrementAndGet();
                } else {
                    completableSource.subscribe(new a(completableObserver, compositeDisposable, atomicThrowable, atomicInteger));
                }
            } else {
                return;
            }
        }
        if (atomicInteger.decrementAndGet() == 0) {
            atomicThrowable.tryTerminateConsumer(completableObserver);
        }
    }

    /* loaded from: classes4.dex */
    static final class b implements Disposable {
        final AtomicThrowable a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(AtomicThrowable atomicThrowable) {
            this.a = atomicThrowable;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.a.tryTerminateAndReport();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.a.isTerminated();
        }
    }

    /* loaded from: classes4.dex */
    static final class a implements CompletableObserver {
        final CompletableObserver a;
        final CompositeDisposable b;
        final AtomicThrowable c;
        final AtomicInteger d;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(CompletableObserver completableObserver, CompositeDisposable compositeDisposable, AtomicThrowable atomicThrowable, AtomicInteger atomicInteger) {
            this.a = completableObserver;
            this.b = compositeDisposable;
            this.c = atomicThrowable;
            this.d = atomicInteger;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.b.add(disposable);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            if (this.c.tryAddThrowableOrReport(th)) {
                a();
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            a();
        }

        void a() {
            if (this.d.decrementAndGet() == 0) {
                this.c.tryTerminateConsumer(this.a);
            }
        }
    }
}
