package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.av;
import com.xiaomi.push.jj;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.ba;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public class gz {
    private String a;
    private int c;
    private long d;
    private gy e;
    private boolean b = false;
    private av f = av.a();

    /* loaded from: classes4.dex */
    public static class a {
        static final gz a = new gz();
    }

    private fd a(av.a aVar) {
        if (aVar.a != 0) {
            fd d = d();
            d.a(fc.CHANNEL_STATS_COUNTER.a());
            d.c(aVar.a);
            d.c(aVar.f13a);
            return d;
        } else if (aVar.f12a instanceof fd) {
            return (fd) aVar.f12a;
        } else {
            return null;
        }
    }

    public static gy a() {
        gy gyVar;
        synchronized (a.a) {
            gyVar = a.a.e;
        }
        return gyVar;
    }

    /* renamed from: a */
    public static gz m951a() {
        return a.a;
    }

    private fe b(int i) {
        ArrayList arrayList = new ArrayList();
        fe feVar = new fe(this.a, arrayList);
        if (!at.d(this.e.a)) {
            feVar.a(i.m(this.e.a));
        }
        jl jlVar = new jl(i);
        jd a2 = new jj.a().a(jlVar);
        try {
            feVar.b(a2);
        } catch (ix unused) {
        }
        LinkedList<av.a> a3 = this.f.m760a();
        while (a3.size() > 0) {
            try {
                fd a4 = a(a3.getLast());
                if (a4 != null) {
                    a4.b(a2);
                }
                if (jlVar.a_() > i) {
                    break;
                }
                if (a4 != null) {
                    arrayList.add(a4);
                }
                a3.removeLast();
            } catch (ix | NoSuchElementException unused2) {
            }
        }
        return feVar;
    }

    private void e() {
        if (this.b && System.currentTimeMillis() - this.d > this.c) {
            this.b = false;
            this.d = 0L;
        }
    }

    public void a(int i) {
        if (i > 0) {
            int i2 = i * 1000;
            if (i2 > 604800000) {
                i2 = 604800000;
            }
            if (this.c != i2 || !this.b) {
                this.b = true;
                this.d = System.currentTimeMillis();
                this.c = i2;
                b.c("enable dot duration = " + i2 + " start = " + this.d);
            }
        }
    }

    public synchronized void a(fd fdVar) {
        this.f.a(fdVar);
    }

    public synchronized void a(XMPushService xMPushService) {
        this.e = new gy(xMPushService);
        this.a = "";
        ba.a().a(new gu(this));
    }

    /* renamed from: a */
    public boolean m952a() {
        return this.b;
    }

    boolean b() {
        e();
        return this.b && this.f.m759a() > 0;
    }

    public synchronized fe c() {
        fe feVar;
        feVar = null;
        if (b()) {
            int i = 750;
            if (!at.d(this.e.a)) {
                i = 375;
            }
            feVar = b(i);
        }
        return feVar;
    }

    public synchronized fd d() {
        fd fdVar;
        fdVar = new fd();
        fdVar.a(at.m756a((Context) this.e.a));
        fdVar.a = (byte) 0;
        fdVar.b = 1;
        fdVar.d((int) (System.currentTimeMillis() / 1000));
        return fdVar;
    }
}
