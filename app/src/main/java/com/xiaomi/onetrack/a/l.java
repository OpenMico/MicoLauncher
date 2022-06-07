package com.xiaomi.onetrack.a;

import android.text.TextUtils;
import com.xiaomi.onetrack.c.f;
import com.xiaomi.onetrack.f.c;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.q;

/* loaded from: classes4.dex */
public class l {
    private static String a = "ConfigProvider";
    private static volatile boolean b = false;
    private static volatile boolean c = true;

    public static boolean a() {
        try {
            String[] b2 = f.a().b();
            return (!TextUtils.isEmpty(b2[0]) && !TextUtils.isEmpty(b2[1])) && !q.a(a);
        } catch (Exception e) {
            p.a(a, "ConfigProvider.available", e);
            return false;
        }
    }

    public static int a(int i) {
        int i2;
        if (p.b) {
            p.a(a, "debug upload mode, send events immediately");
            return 0;
        }
        try {
            i2 = c.c().get(Integer.valueOf(i + 1)).intValue();
        } catch (Exception unused) {
            i2 = 60000;
        }
        String str = a;
        p.a(str, "getUploadInterval " + i2);
        return i2;
    }

    public static synchronized void a(boolean z) {
        synchronized (l.class) {
            b = z;
        }
    }

    public static synchronized boolean b() {
        boolean z;
        synchronized (l.class) {
            z = b;
        }
        return z;
    }

    public static boolean c() {
        return c.a();
    }
}
