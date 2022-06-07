package io.reactivex.android.schedulers;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: HandlerScheduler.java */
/* loaded from: classes4.dex */
public final class a extends Scheduler {
    private final Handler c;
    private final boolean d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Handler handler, boolean z) {
        this.c = handler;
        this.d = z;
    }

    @Override // io.reactivex.Scheduler
    @SuppressLint({"NewApi"})
    public Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        if (runnable == null) {
            throw new NullPointerException("run == null");
        } else if (timeUnit != null) {
            b bVar = new b(this.c, RxJavaPlugins.onSchedule(runnable));
            Message obtain = Message.obtain(this.c, bVar);
            if (this.d) {
                obtain.setAsynchronous(true);
            }
            this.c.sendMessageDelayed(obtain, timeUnit.toMillis(j));
            return bVar;
        } else {
            throw new NullPointerException("unit == null");
        }
    }

    @Override // io.reactivex.Scheduler
    public Scheduler.Worker createWorker() {
        return new C0211a(this.c, this.d);
    }

    /* compiled from: HandlerScheduler.java */
    /* renamed from: io.reactivex.android.schedulers.a$a  reason: collision with other inner class name */
    /* loaded from: classes4.dex */
    private static final class C0211a extends Scheduler.Worker {
        private final Handler a;
        private final boolean b;
        private volatile boolean c;

        C0211a(Handler handler, boolean z) {
            this.a = handler;
            this.b = z;
        }

        @Override // io.reactivex.Scheduler.Worker
        @SuppressLint({"NewApi"})
        public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            if (runnable == null) {
                throw new NullPointerException("run == null");
            } else if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            } else if (this.c) {
                return Disposables.disposed();
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
                return Disposables.disposed();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c = true;
            this.a.removeCallbacksAndMessages(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }
    }

    /* compiled from: HandlerScheduler.java */
    /* loaded from: classes4.dex */
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

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.a.removeCallbacks(this);
            this.c = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }
    }
}
