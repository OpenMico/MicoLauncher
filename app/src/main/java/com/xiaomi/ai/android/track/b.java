package com.xiaomi.ai.android.track;

import android.util.Log;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import java.util.concurrent.ScheduledFuture;

/* loaded from: classes2.dex */
public abstract class b {
    protected int a;
    protected int b;
    protected c c;
    protected boolean d;
    private ScheduledFuture<?> e;
    private ScheduledFuture<?> f;
    private final Object g = getClass();
    private volatile boolean h = true;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class a implements Runnable {
        private a() {
        }

        private void a() {
            Logger.a("BaseTrackStrategy", "postDiskDataDelay:" + b.this.b);
            if (b.this.f == null || b.this.f.isCancelled() || b.this.f.isDone()) {
                Logger.a("BaseTrackStrategy", "start DiskCheck task");
                b bVar = b.this;
                bVar.f = com.xiaomi.ai.b.c.a(new RunnableC0156b(), b.this.b * 1000);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (b.this.g) {
                Logger.a("BaseTrackStrategy", "CacheCheckRunnable run");
                b.this.i();
                if (!b.this.f()) {
                    if (b.this.b()) {
                        if (b.this.g()) {
                            b.this.h = true;
                        }
                        a();
                        b.this.k();
                    } else {
                        b.this.b(false);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.xiaomi.ai.android.track.b$b  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public class RunnableC0156b implements Runnable {
        private RunnableC0156b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Logger.a("BaseTrackStrategy", "DiskCheckRunnable run", b.this.d);
            b.this.a(false);
        }
    }

    /* loaded from: classes2.dex */
    public interface c {
        void a();

        void a(AivsError aivsError);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public b(int i, int i2, boolean z, c cVar) {
        this.c = cVar;
        this.a = i;
        this.b = i2;
        this.d = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(boolean z) {
        if (f()) {
            return false;
        }
        if (!z || !b()) {
            i();
            j();
            Logger.a("BaseTrackStrategy", "postTrackInfo:start to post", this.d);
            boolean c2 = c();
            k();
            return c2;
        }
        h();
        return true;
    }

    private void h() {
        ScheduledFuture<?> scheduledFuture = this.e;
        if (scheduledFuture == null || scheduledFuture.isCancelled() || this.e.isDone()) {
            Logger.a("BaseTrackStrategy", "start CacheCheck task", this.d);
            this.e = com.xiaomi.ai.b.c.a(new a(), this.a * 1000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        ScheduledFuture<?> scheduledFuture = this.e;
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            Logger.a("BaseTrackStrategy", "cancel Cache task", this.d);
            this.e.cancel(false);
        }
    }

    private void j() {
        Logger.a("BaseTrackStrategy", "cleanDiskPeriodCheck");
        ScheduledFuture<?> scheduledFuture = this.f;
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            Logger.a("BaseTrackStrategy", "cancel disk task");
            this.f.cancel(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        c cVar = this.c;
        if (cVar != null) {
            cVar.a();
        }
    }

    public boolean a() {
        Logger.a("BaseTrackStrategy", "postFailedData", this.d);
        try {
            if (e()) {
                return a(false);
            }
            Logger.a("BaseTrackStrategy", "postFailedData: no data", this.d);
            return true;
        } catch (Exception e) {
            Logger.c("BaseTrackStrategy", "postFailedData error :" + Log.getStackTraceString(e), this.d);
            return false;
        }
    }

    public boolean a(boolean z) {
        boolean b;
        synchronized (this.g) {
            try {
                try {
                    if (this.h) {
                        d();
                        this.h = false;
                    }
                    b = b(z);
                } catch (Exception e) {
                    Logger.c("BaseTrackStrategy", "postTrackData error :" + Logger.throwableToString(e), this.d);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return b;
    }

    protected abstract boolean b();

    protected abstract boolean c();

    protected abstract void d();

    protected abstract boolean e();

    protected abstract boolean f();

    protected abstract boolean g();
}
