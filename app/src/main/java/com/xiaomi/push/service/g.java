package com.xiaomi.push.service;

import android.os.SystemClock;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes4.dex */
public class g {
    private static long a;
    private static long b;
    private static long c;
    private final c d;
    private final a e;

    /* loaded from: classes4.dex */
    private static final class a {
        private final c a;

        a(c cVar) {
            this.a = cVar;
        }

        protected void finalize() {
            try {
                synchronized (this.a) {
                    this.a.e = true;
                    this.a.notify();
                }
            } finally {
                super.finalize();
            }
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class b implements Runnable {
        protected int a;

        public b(int i) {
            this.a = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class c extends Thread {
        private boolean d;
        private boolean e;
        private volatile long a = 0;
        private volatile boolean b = false;
        private long c = 50;
        private a f = new a();

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes4.dex */
        public static final class a {
            private int a;
            private d[] b;
            private int c;
            private int d;

            private a() {
                this.a = 256;
                this.b = new d[this.a];
                this.c = 0;
                this.d = 0;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public int b(d dVar) {
                int i = 0;
                while (true) {
                    d[] dVarArr = this.b;
                    if (i >= dVarArr.length) {
                        return -1;
                    }
                    if (dVarArr[i] == dVar) {
                        return i;
                    }
                    i++;
                }
            }

            private void d(int i) {
                int i2 = (i * 2) + 1;
                while (true) {
                    int i3 = this.c;
                    if (i2 < i3 && i3 > 0) {
                        int i4 = i2 + 1;
                        if (i4 < i3 && this.b[i4].c < this.b[i2].c) {
                            i2 = i4;
                        }
                        if (this.b[i].c >= this.b[i2].c) {
                            d[] dVarArr = this.b;
                            d dVar = dVarArr[i];
                            dVarArr[i] = dVarArr[i2];
                            dVarArr[i2] = dVar;
                            i2 = (i2 * 2) + 1;
                            i = i2;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }

            private void e() {
                int i = this.c - 1;
                int i2 = (i - 1) / 2;
                while (this.b[i].c < this.b[i2].c) {
                    d[] dVarArr = this.b;
                    d dVar = dVarArr[i];
                    dVarArr[i] = dVarArr[i2];
                    dVarArr[i2] = dVar;
                    i2 = (i2 - 1) / 2;
                    i = i2;
                }
            }

            public d a() {
                return this.b[0];
            }

            public void a(int i, b bVar) {
                for (int i2 = 0; i2 < this.c; i2++) {
                    if (this.b[i2].d == bVar) {
                        this.b[i2].a();
                    }
                }
                d();
            }

            public void a(d dVar) {
                d[] dVarArr = this.b;
                int length = dVarArr.length;
                int i = this.c;
                if (length == i) {
                    d[] dVarArr2 = new d[i * 2];
                    System.arraycopy(dVarArr, 0, dVarArr2, 0, i);
                    this.b = dVarArr2;
                }
                d[] dVarArr3 = this.b;
                int i2 = this.c;
                this.c = i2 + 1;
                dVarArr3[i2] = dVar;
                e();
            }

            public boolean a(int i) {
                for (int i2 = 0; i2 < this.c; i2++) {
                    if (this.b[i2].e == i) {
                        return true;
                    }
                }
                return false;
            }

            public void b(int i) {
                for (int i2 = 0; i2 < this.c; i2++) {
                    if (this.b[i2].e == i) {
                        this.b[i2].a();
                    }
                }
                d();
            }

            public boolean b() {
                return this.c == 0;
            }

            public void c() {
                this.b = new d[this.a];
                this.c = 0;
            }

            public void c(int i) {
                int i2;
                if (i >= 0 && i < (i2 = this.c)) {
                    d[] dVarArr = this.b;
                    int i3 = i2 - 1;
                    this.c = i3;
                    dVarArr[i] = dVarArr[i3];
                    dVarArr[this.c] = null;
                    d(i);
                }
            }

            public void d() {
                int i = 0;
                while (i < this.c) {
                    if (this.b[i].b) {
                        this.d++;
                        c(i);
                        i--;
                    }
                    i++;
                }
            }
        }

        c(String str, boolean z) {
            setName(str);
            setDaemon(z);
            start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(d dVar) {
            this.f.a(dVar);
            notify();
        }

        public synchronized void a() {
            this.d = true;
            this.f.c();
            notify();
        }

        public boolean b() {
            return this.b && SystemClock.uptimeMillis() - this.a > 600000;
        }

        /* JADX WARN: Code restructure failed: missing block: B:49:0x0095, code lost:
            r10.a = android.os.SystemClock.uptimeMillis();
            r10.b = true;
            r2.d.run();
            r10.b = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00a6, code lost:
            r1 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x00a7, code lost:
            monitor-enter(r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00a8, code lost:
            r10.d = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00ab, code lost:
            throw r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00ac, code lost:
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x00ae, code lost:
            throw r0;
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 184
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.g.c.run():void");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static class d {
        final Object a = new Object();
        boolean b;
        long c;
        b d;
        int e;
        private long f;

        d() {
        }

        void a(long j) {
            synchronized (this.a) {
                this.f = j;
            }
        }

        public boolean a() {
            boolean z;
            synchronized (this.a) {
                z = !this.b && this.c > 0;
                this.b = true;
            }
            return z;
        }
    }

    static {
        long j = 0;
        if (SystemClock.elapsedRealtime() > 0) {
            j = SystemClock.elapsedRealtime();
        }
        a = j;
        b = a;
    }

    public g() {
        this(false);
    }

    public g(String str) {
        this(str, false);
    }

    public g(String str, boolean z) {
        if (str != null) {
            this.d = new c(str, z);
            this.e = new a(this.d);
            return;
        }
        throw new NullPointerException("name == null");
    }

    public g(boolean z) {
        this("Timer-" + d(), z);
    }

    private void b(b bVar, long j) {
        synchronized (this.d) {
            if (!this.d.d) {
                long c2 = j + c();
                if (c2 >= 0) {
                    d dVar = new d();
                    dVar.e = bVar.a;
                    dVar.d = bVar;
                    dVar.c = c2;
                    this.d.a(dVar);
                } else {
                    throw new IllegalArgumentException("Illegal delay to start the TimerTask: " + c2);
                }
            } else {
                throw new IllegalStateException("Timer was canceled");
            }
        }
    }

    static synchronized long c() {
        long j;
        synchronized (g.class) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (elapsedRealtime > b) {
                a += elapsedRealtime - b;
            }
            b = elapsedRealtime;
            j = a;
        }
        return j;
    }

    private static synchronized long d() {
        long j;
        synchronized (g.class) {
            j = c;
            c = 1 + j;
        }
        return j;
    }

    public void a() {
        this.d.a();
    }

    public void a(int i) {
        synchronized (this.d) {
            this.d.f.b(i);
        }
    }

    public void a(int i, b bVar) {
        synchronized (this.d) {
            this.d.f.a(i, bVar);
        }
    }

    public void a(b bVar) {
        if (com.xiaomi.channel.commonutils.logger.b.a() >= 1 || Thread.currentThread() == this.d) {
            bVar.run();
        } else {
            com.xiaomi.channel.commonutils.logger.b.d("run job outside job job thread");
            throw new RejectedExecutionException("Run job outside job thread");
        }
    }

    public void a(b bVar, long j) {
        if (j >= 0) {
            b(bVar, j);
            return;
        }
        throw new IllegalArgumentException("delay < 0: " + j);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1157a() {
        return this.d.b();
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1158a(int i) {
        boolean a2;
        synchronized (this.d) {
            a2 = this.d.f.a(i);
        }
        return a2;
    }

    public void b() {
        synchronized (this.d) {
            this.d.f.c();
        }
    }
}
