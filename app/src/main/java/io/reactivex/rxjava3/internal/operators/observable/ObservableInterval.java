package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableInterval extends Observable<Long> {
    final Scheduler a;
    final long b;
    final long c;
    final TimeUnit d;

    public ObservableInterval(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        this.b = j;
        this.c = j2;
        this.d = timeUnit;
        this.a = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super Long> observer) {
        a aVar = new a(observer);
        observer.onSubscribe(aVar);
        Scheduler scheduler = this.a;
        if (scheduler instanceof TrampolineScheduler) {
            Scheduler.Worker createWorker = scheduler.createWorker();
            aVar.a(createWorker);
            createWorker.schedulePeriodically(aVar, this.b, this.c, this.d);
            return;
        }
        aVar.a(scheduler.schedulePeriodicallyDirect(aVar, this.b, this.c, this.d));
    }

    /* loaded from: classes5.dex */
    static final class a extends AtomicReference<Disposable> implements Disposable, Runnable {
        private static final long serialVersionUID = 346773832286157679L;
        long count;
        final Observer<? super Long> downstream;

        a(Observer<? super Long> observer) {
            this.downstream = observer;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (get() != DisposableHelper.DISPOSED) {
                Observer<? super Long> observer = this.downstream;
                long j = this.count;
                this.count = 1 + j;
                observer.onNext(Long.valueOf(j));
            }
        }

        public void a(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }
}
