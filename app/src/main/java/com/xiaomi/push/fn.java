package com.xiaomi.push;

import android.util.Pair;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.al;
import com.xiaomi.push.service.ap;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public abstract class fn {
    public static boolean a;
    private static final AtomicInteger e = new AtomicInteger(0);

    /* renamed from: a */
    protected fo f37a;

    /* renamed from: a */
    protected XMPushService f39a;

    /* renamed from: a */
    protected int f35a = 0;

    /* renamed from: a */
    protected long f36a = -1;

    /* renamed from: b */
    protected volatile long f42b = 0;
    protected volatile long c = 0;
    private LinkedList<Pair<Integer, Long>> f = new LinkedList<>();
    private final Collection<fq> g = new CopyOnWriteArrayList();

    /* renamed from: a */
    protected final Map<fs, a> f41a = new ConcurrentHashMap();

    /* renamed from: b */
    protected final Map<fs, a> f44b = new ConcurrentHashMap();

    /* renamed from: a */
    protected fz f38a = null;

    /* renamed from: a */
    protected String f40a = "";

    /* renamed from: b */
    protected String f43b = "";
    private int h = 2;
    protected final int b = e.getAndIncrement();
    private long i = 0;
    protected long d = 0;

    /* loaded from: classes4.dex */
    public static class a {
        private fs a;
        private ga b;

        public a(fs fsVar, ga gaVar) {
            this.a = fsVar;
            this.b = gaVar;
        }

        public void a(fg fgVar) {
            this.a.a(fgVar);
        }

        public void a(ge geVar) {
            ga gaVar = this.b;
            if (gaVar == null || gaVar.mo782a(geVar)) {
                this.a.mo782a(geVar);
            }
        }
    }

    static {
        a = false;
        try {
            a = Boolean.getBoolean("smack.debugEnabled");
        } catch (Exception unused) {
        }
        ft.m926a();
    }

    public fn(XMPushService xMPushService, fo foVar) {
        this.f37a = foVar;
        this.f39a = xMPushService;
        m919b();
    }

    private String a(int i) {
        return i == 1 ? "connected" : i == 0 ? "connecting" : i == 2 ? "disconnected" : "unknown";
    }

    private void b(int i) {
        synchronized (this.f) {
            if (i == 1) {
                this.f.clear();
            } else {
                this.f.add(new Pair<>(Integer.valueOf(i), Long.valueOf(System.currentTimeMillis())));
                if (this.f.size() > 6) {
                    this.f.remove(0);
                }
            }
        }
    }

    /* renamed from: a */
    public int mo927a() {
        return this.f35a;
    }

    public long a() {
        return this.c;
    }

    /* renamed from: a */
    public fo m915a() {
        return this.f37a;
    }

    /* renamed from: a */
    public String m916a() {
        return this.f37a.c();
    }

    public void a(int i, int i2, Exception exc) {
        int i3 = this.h;
        if (i != i3) {
            b.m149a(String.format("update the connection status. %1$s -> %2$s : %3$s ", a(i3), a(i), ap.a(i2)));
        }
        if (at.b(this.f39a)) {
            b(i);
        }
        if (i == 1) {
            this.f39a.a(10);
            if (this.h != 0) {
                b.m149a("try set connected while not connecting.");
            }
            this.h = i;
            for (fq fqVar : this.g) {
                fqVar.a(this);
            }
        } else if (i == 0) {
            if (this.h != 2) {
                b.m149a("try set connecting while not disconnected.");
            }
            this.h = i;
            for (fq fqVar2 : this.g) {
                fqVar2.b(this);
            }
        } else if (i == 2) {
            this.f39a.a(10);
            int i4 = this.h;
            if (i4 == 0) {
                for (fq fqVar3 : this.g) {
                    fqVar3.a(this, exc == null ? new CancellationException("disconnect while connecting") : exc);
                }
            } else if (i4 == 1) {
                for (fq fqVar4 : this.g) {
                    fqVar4.a(this, i2, exc);
                }
            }
            this.h = i;
        }
    }

    public void a(fq fqVar) {
        if (fqVar != null && !this.g.contains(fqVar)) {
            this.g.add(fqVar);
        }
    }

    public void a(fs fsVar, ga gaVar) {
        if (fsVar != null) {
            this.f41a.put(fsVar, new a(fsVar, gaVar));
            return;
        }
        throw new NullPointerException("Packet listener is null.");
    }

    public abstract void a(ge geVar);

    public abstract void a(al.b bVar);

    public synchronized void a(String str) {
        if (this.h == 0) {
            b.m149a("setChallenge hash = " + ay.a(str).substring(0, 8));
            this.f40a = str;
            a(1, 0, null);
        } else {
            b.m149a("ignore setChallenge because connection was disconnected");
        }
    }

    public abstract void a(String str, String str2);

    public abstract void a(fg[] fgVarArr);

    /* renamed from: a */
    public boolean m917a() {
        return false;
    }

    public synchronized boolean a(long j) {
        return this.i >= j;
    }

    public int b() {
        return this.h;
    }

    /* renamed from: b */
    public String m918b() {
        return this.f37a.b();
    }

    /* renamed from: b */
    protected void m919b() {
        String str;
        if (this.f37a.m924a() && this.f38a == null) {
            Class<?> cls = null;
            try {
                str = System.getProperty("smack.debuggerClass");
            } catch (Throwable unused) {
                str = null;
            }
            if (str != null) {
                try {
                    cls = Class.forName(str);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (cls == null) {
                this.f38a = new bj(this);
                return;
            }
            try {
                this.f38a = (fz) cls.getConstructor(fn.class, Writer.class, Reader.class).newInstance(this);
            } catch (Exception e3) {
                throw new IllegalArgumentException("Can't initialize the configured debugger!", e3);
            }
        }
    }

    public abstract void b(int i, Exception exc);

    public abstract void b(fg fgVar);

    public void b(fq fqVar) {
        this.g.remove(fqVar);
    }

    public void b(fs fsVar, ga gaVar) {
        if (fsVar != null) {
            this.f44b.put(fsVar, new a(fsVar, gaVar));
            return;
        }
        throw new NullPointerException("Packet listener is null.");
    }

    public abstract void b(boolean z);

    /* renamed from: b */
    public boolean m920b() {
        return this.h == 0;
    }

    public synchronized void c() {
        this.i = System.currentTimeMillis();
    }

    /* renamed from: c */
    public boolean m921c() {
        return this.h == 1;
    }

    public void d() {
        synchronized (this.f) {
            this.f.clear();
        }
    }

    /* renamed from: d */
    public synchronized boolean m922d() {
        return System.currentTimeMillis() - this.i < ((long) ft.a());
    }

    public synchronized boolean e() {
        boolean z;
        z = true;
        if (System.currentTimeMillis() - this.d >= (ft.a() << 1)) {
            z = false;
        }
        return z;
    }
}
