package com.xiaomi.onetrack.util.oaid;

import android.content.Context;
import com.xiaomi.onetrack.util.n;
import com.xiaomi.onetrack.util.oaid.helpers.b;
import com.xiaomi.onetrack.util.oaid.helpers.g;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.q;
import com.xiaomi.onetrack.util.w;

/* loaded from: classes4.dex */
public class a {
    private static final String a = "a";
    private static volatile a b;
    private volatile boolean c = false;
    private volatile String d = "";

    public static a a() {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a();
                }
            }
        }
        return b;
    }

    public String a(Context context) {
        synchronized (this.d) {
            if (w.a()) {
                if (!p.a) {
                    p.b(a, "getOaid() throw exception : Don't use it on the main thread");
                    return "";
                }
                throw new IllegalStateException("Don't use it on the main thread");
            } else if (this.d != null && !this.d.equals("")) {
                return this.d;
            } else if (this.c) {
                return this.d;
            } else if (q.a()) {
                this.d = n.b(context);
                return this.d;
            } else {
                String a2 = new g().a(context);
                if (a2 == null || a2.equals("")) {
                    String a3 = new b().a(context);
                    if (a3 == null || a3.equals("")) {
                        this.c = true;
                        return this.d;
                    }
                    this.d = a3;
                    return a3;
                }
                this.d = a2;
                return a2;
            }
        }
    }
}
