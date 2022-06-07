package io.reactivex.rxjava3.android.schedulers;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;

/* compiled from: HandlerScheduler.java */
/* loaded from: classes2.dex */
final class a extends Scheduler {
    private final Handler b;
    private final boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Handler handler, boolean z) {
        this.b = handler;
        this.c = z;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @SuppressLint({"NewApi"})
    public Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        if (runnable == null) {
            throw new NullPointerException("run == null");
        } else if (timeUnit != null) {
            b bVar = new b(this.b, RxJavaPlugins.onSchedule(runnable));
            Message obtain = Message.obtain(this.b, bVar);
            if (this.c) {
                obtain.setAsynchronous(true);
            }
            this.b.sendMessageDelayed(obtain, timeUnit.toMillis(j));
            return bVar;
        } else {
            throw new NullPointerException("unit == null");
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Scheduler.Worker createWorker() {
        return new C0276a(this.b, this.c);
    }

    /* compiled from: HandlerScheduler.java */
    /* renamed from: io.reactivex.rxjava3.android.schedulers.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    private static final class C0276a extends Scheduler.Worker {
        private final Handler a;
        private final boolean b;
        private volatile boolean c;

        C0276a(Handler handler, boolean z) {
            this.a = handler;
            this.b = z;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @SuppressLint({"NewApi"})
        public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            if (runnable == null) {
                throw new NullPointerException("run == null");
            } else if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            } else if (this.c) {
                return Disposable.disposed();
            } else {
                b bVar = new b(this.a, RxJavaPlugins.onSchedule(runnable));
                Message obtain = Message.obtain(this.a, bVar);
                obtain.obj = this;
                if (this.b) {
                    obtain.setAsynchronous(true);
                }
                this.a.sendMessageDelayed(obtain, timeUnit.toMillis(j));
                if (!this.c) {
                    return bVar;
                }
                this.a.removeCallbacks(bVar);
                return Disposable.disposed();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.c = true;
            this.a.removeCallbacksAndMessages(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }
    }

    /* compiled from: HandlerScheduler.java */
    /* loaded from: classes2.dex */
    private static final class b implements Disposable, Runnable {
        private final Handler a;
        private final Runnable b;
        private volatile boolean c;

        b(Handler handler, Runnable runnable) {
            this.a = handler;
            this.b = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.b.run();
            } catch (Throwable th) {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.a.removeCallbacks(this);
            this.c = true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }
    }
}
