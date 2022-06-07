package com.xiaomi.push;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.gt;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.al;
import java.util.Hashtable;

/* loaded from: classes4.dex */
public class hb {
    private static final int a = fc.PING_RTT.a();

    /* loaded from: classes4.dex */
    public static class a {
        static Hashtable<Integer, Long> a = new Hashtable<>();
    }

    public static void a() {
        a(0, a);
    }

    public static void a(int i) {
        fd d = gz.m951a().d();
        d.a(fc.CHANNEL_STATS_COUNTER.a());
        d.c(i);
        gz.m951a().a(d);
    }

    public static synchronized void a(int i, int i2) {
        synchronized (hb.class) {
            if (i2 < 16777215) {
                a.a.put(Integer.valueOf((i << 24) | i2), Long.valueOf(System.currentTimeMillis()));
            } else {
                b.d("stats key should less than 16777215");
            }
        }
    }

    public static void a(int i, int i2, int i3, String str, int i4) {
        fd d = gz.m951a().d();
        d.a((byte) i);
        d.a(i2);
        d.b(i3);
        d.b(str);
        d.c(i4);
        gz.m951a().a(d);
    }

    public static synchronized void a(int i, int i2, String str, int i3) {
        synchronized (hb.class) {
            long currentTimeMillis = System.currentTimeMillis();
            int i4 = (i << 24) | i2;
            if (a.a.containsKey(Integer.valueOf(i4))) {
                fd d = gz.m951a().d();
                d.a(i2);
                d.b((int) (currentTimeMillis - a.a.get(Integer.valueOf(i4)).longValue()));
                d.b(str);
                if (i3 > -1) {
                    d.c(i3);
                }
                gz.m951a().a(d);
                a.a.remove(Integer.valueOf(i2));
            } else {
                b.d("stats key not found");
            }
        }
    }

    public static void a(XMPushService xMPushService, al.b bVar) {
        new gn(xMPushService, bVar).a();
    }

    public static void a(String str, int i, Exception exc) {
        fd d = gz.m951a().d();
        if (i > 0) {
            d.a(fc.GSLB_REQUEST_SUCCESS.a());
            d.b(str);
            d.b(i);
            gz.m951a().a(d);
            return;
        }
        try {
            gt.a a2 = gt.a(exc);
            d.a(a2.a.a());
            d.c(a2.b);
            d.b(str);
            gz.m951a().a(d);
        } catch (NullPointerException unused) {
        }
    }

    public static void a(String str, Exception exc) {
        try {
            gt.a b = gt.b(exc);
            fd d = gz.m951a().d();
            d.a(b.a.a());
            d.c(b.b);
            d.b(str);
            gz.m951a().a(d);
        } catch (NullPointerException unused) {
        }
    }

    /* renamed from: a */
    public static byte[] m953a() {
        fe c = gz.m951a().c();
        if (c != null) {
            return ir.a(c);
        }
        return null;
    }

    public static void b() {
        a(0, a, null, -1);
    }

    public static void b(String str, Exception exc) {
        try {
            gt.a d = gt.d(exc);
            fd d2 = gz.m951a().d();
            d2.a(d.a.a());
            d2.c(d.b);
            d2.b(str);
            gz.m951a().a(d2);
        } catch (NullPointerException unused) {
        }
    }
}
