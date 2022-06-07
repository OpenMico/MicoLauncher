package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: InstantPeriodicTask.java */
/* loaded from: classes5.dex */
final class c implements Disposable, Callable<Void> {
    static final FutureTask<Void> f = new FutureTask<>(Functions.EMPTY_RUNNABLE, null);
    final Runnable a;
    final ExecutorService d;
    Thread e;
    final AtomicReference<Future<?>> c = new AtomicReference<>();
    final AtomicReference<Future<?>> b = new AtomicReference<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(Runnable runnable, ExecutorService executorService) {
        this.a = runnable;
        this.d = executorService;
    }

    /* renamed from: a */
    public Void call() {
        this.e = Thread.currentThread();
        try {
            this.a.run();
            this.e = null;
            b(this.d.submit(this));
            return null;
        } catch (Throwable th) {
            this.e = null;
            RxJavaPlugins.onError(th);
            throw th;
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        Future<?> andSet = this.c.getAndSet(f);
        boolean z = true;
        if (!(andSet == null || andSet == f)) {
            andSet.cancel(this.e != Thread.currentThread());
        }
        Future<?> andSet2 = this.b.getAndSet(f);
        if (andSet2 != null && andSet2 != f) {
            if (this.e == Thread.currentThread()) {
                z = false;
            }
            andSet2.cancel(z);
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.c.get() == f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Future<?> future) {
        Future<?> future2;
        do {
            future2 = this.c.get();
            if (future2 == f) {
                future.cancel(this.e != Thread.currentThread());
                return;
            }
        } while (!this.c.compareAndSet(future2, future));
    }

    void b(Future<?> future) {
        Future<?> future2;
        do {
            future2 = this.b.get();
            if (future2 == f) {
                future.cancel(this.e != Thread.currentThread());
                return;
            }
        } while (!this.b.compareAndSet(future2, future));
    }
}
