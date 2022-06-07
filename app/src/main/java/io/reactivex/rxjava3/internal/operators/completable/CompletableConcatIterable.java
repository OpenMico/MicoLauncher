package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class CompletableConcatIterable extends Completable {
    final Iterable<? extends CompletableSource> a;

    public CompletableConcatIterable(Iterable<? extends CompletableSource> iterable) {
        this.a = iterable;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        try {
            a aVar = new a(completableObserver, (Iterator) Objects.requireNonNull(this.a.iterator(), "The iterator returned is null"));
            completableObserver.onSubscribe(aVar.sd);
            aVar.a();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, completableObserver);
        }
    }

    /* loaded from: classes4.dex */
    static final class a extends AtomicInteger implements CompletableObserver {
        private static final long serialVersionUID = -7965400327305809232L;
        final CompletableObserver downstream;
        final SequentialDisposable sd = new SequentialDisposable();
        final Iterator<? extends CompletableSource> sources;

        a(CompletableObserver completableObserver, Iterator<? extends CompletableSource> it) {
            this.downstream = completableObserver;
            this.sources = it;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.sd.replace(disposable);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            a();
        }

        void a() {
            if (!this.sd.isDisposed() && getAndIncrement() == 0) {
                Iterator<? extends CompletableSource> it = this.sources;
                while (!this.sd.isDisposed()) {
                    try {
                        if (!it.hasNext()) {
                            this.downstream.onComplete();
                            return;
                        }
                        try {
                            ((CompletableSource) Objects.requireNonNull(it.next(), "The CompletableSource returned is null")).subscribe(this);
                            if (decrementAndGet() == 0) {
                                return;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.downstream.onError(th);
                            return;
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        this.downstream.onError(th2);
                        return;
                    }
                }
            }
        }
    }
}
