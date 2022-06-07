package com.xiaomi.push;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class am {
    private a a;
    private Handler b;
    private volatile boolean c;
    private final boolean d;
    private int e;
    private volatile b f;

    /* loaded from: classes4.dex */
    public class a extends Thread {
        private final LinkedBlockingQueue<b> b = new LinkedBlockingQueue<>();

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a() {
            super("PackageProcessor");
            am.this = r1;
        }

        private void a(int i, b bVar) {
            am.this.b.sendMessage(am.this.b.obtainMessage(i, bVar));
        }

        public void a(b bVar) {
            try {
                this.b.add(bVar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            long j = am.this.e > 0 ? am.this.e : Long.MAX_VALUE;
            while (!am.this.c) {
                try {
                    b poll = this.b.poll(j, TimeUnit.SECONDS);
                    am.this.f = poll;
                    if (poll != null) {
                        a(0, poll);
                        poll.b();
                        a(1, poll);
                    } else if (am.this.e > 0) {
                        am.this.a();
                    }
                } catch (InterruptedException e) {
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class b {
        public void a() {
        }

        public abstract void b();

        public void c() {
        }
    }

    public am() {
        this(false);
    }

    public am(boolean z) {
        this(z, 0);
    }

    public am(boolean z, int i) {
        this.b = null;
        this.c = false;
        this.e = 0;
        this.b = new x(this, Looper.getMainLooper());
        this.d = z;
        this.e = i;
    }

    public synchronized void a() {
        this.a = null;
        this.c = true;
    }

    public synchronized void a(b bVar) {
        if (this.a == null) {
            this.a = new a();
            this.a.setDaemon(this.d);
            this.c = false;
            this.a.start();
        }
        this.a.a(bVar);
    }

    public void a(b bVar, long j) {
        this.b.postDelayed(new aa(this, bVar), j);
    }
}
