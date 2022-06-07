package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMergeArrayDelayError;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class CompletableMergeDelayErrorIterable extends Completable {
    final Iterable<? extends CompletableSource> a;

    public CompletableMergeDelayErrorIterable(Iterable<? extends CompletableSource> iterable) {
        this.a = iterable;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        completableObserver.onSubscribe(compositeDisposable);
        try {
            Iterator it = (Iterator) Objects.requireNonNull(this.a.iterator(), "The source iterator returned is null");
            AtomicInteger atomicInteger = new AtomicInteger(1);
            AtomicThrowable atomicThrowable = new AtomicThrowable();
            compositeDisposable.add(new CompletableMergeArrayDelayError.b(atomicThrowable));
            while (!compositeDisposable.isDisposed()) {
                try {
                    if (it.hasNext()) {
                        if (!compositeDisposable.isDisposed()) {
                            try {
                                CompletableSource completableSource = (CompletableSource) Objects.requireNonNull(it.next(), "The iterator returned a null CompletableSource");
                                if (!compositeDisposable.isDisposed()) {
                                    atomicInteger.getAndIncrement();
                                    completableSource.subscribe(new CompletableMergeArrayDelayError.a(completableObserver, compositeDisposable, atomicThrowable, atomicInteger));
                                } else {
                                    return;
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                atomicThrowable.tryAddThrowableOrReport(th);
                            }
                        } else {
                            return;
                        }
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    atomicThrowable.tryAddThrowableOrReport(th2);
                }
                if (atomicInteger.decrementAndGet() == 0) {
                    atomicThrowable.tryTerminateConsumer(completableObserver);
                    return;
                }
                return;
            }
        } catch (Throwable th3) {
            Exceptions.throwIfFatal(th3);
            completableObserver.onError(th3);
        }
    }
}
