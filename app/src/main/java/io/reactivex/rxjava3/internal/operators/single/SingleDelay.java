package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public final class SingleDelay<T> extends Single<T> {
    final SingleSource<? extends T> a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final boolean e;

    public SingleDelay(SingleSource<? extends T> singleSource, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        this.a = singleSource;
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = z;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        singleObserver.onSubscribe(sequentialDisposable);
        this.a.subscribe(new a(sequentialDisposable, singleObserver));
    }

    /* loaded from: classes5.dex */
    final class a implements SingleObserver<T> {
        final SingleObserver<? super T> a;
        private final SequentialDisposable c;

        a(SequentialDisposable sequentialDisposable, SingleObserver<? super T> singleObserver) {
            this.c = sequentialDisposable;
            this.a = singleObserver;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.c.replace(disposable);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t) {
            this.c.replace(SingleDelay.this.d.scheduleDirect(new b(t), SingleDelay.this.b, SingleDelay.this.c));
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.c.replace(SingleDelay.this.d.scheduleDirect(new RunnableC0338a(th), SingleDelay.this.e ? SingleDelay.this.b : 0L, SingleDelay.this.c));
        }

        /* loaded from: classes5.dex */
        final class b implements Runnable {
            private final T b;

            b(T t) {
                this.b = t;
            }

            @Override // java.lang.Runnable
            public void run() {
                a.this.a.onSuccess((T) this.b);
            }
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.single.SingleDelay$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class RunnableC0338a implements Runnable {
            private final Throwable b;

            RunnableC0338a(Throwable th) {
                this.b = th;
            }

            @Override // java.lang.Runnable
            public void run() {
                a.this.a.onError(this.b);
            }
        }
    }
}
