package org.greenrobot.eventbus;

import java.util.logging.Level;

/* compiled from: BackgroundPoster.java */
/* loaded from: classes5.dex */
final class b implements Runnable, e {
    private final d a = new d();
    private final EventBus b;
    private volatile boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(EventBus eventBus) {
        this.b = eventBus;
    }

    @Override // org.greenrobot.eventbus.e
    public void enqueue(g gVar, Object obj) {
        c a = c.a(gVar, obj);
        synchronized (this) {
            this.a.a(a);
            if (!this.c) {
                this.c = true;
                this.b.a().execute(this);
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            try {
                c a = this.a.a(1000);
                if (a == null) {
                    synchronized (this) {
                        a = this.a.a();
                        if (a == null) {
                            return;
                        }
                    }
                }
                this.b.a(a);
            } catch (InterruptedException e) {
                Logger logger = this.b.getLogger();
                Level level = Level.WARNING;
                logger.log(level, Thread.currentThread().getName() + " was interruppted", e);
                return;
            } finally {
                this.c = false;
            }
        }
    }
}
