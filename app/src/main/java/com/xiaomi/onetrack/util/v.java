package com.xiaomi.onetrack.util;

import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.onetrack.e.a;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;

/* loaded from: classes4.dex */
public class v {
    private static final String a = "custom_open";
    private static final String b = "custom_close";
    private static final String c = "exprience_open";
    private static final String d = "exprience_close";
    private static final String e = "PrivacyManager";
    private static final long k = 900000;
    private OneTrack.IEventHook f;
    private Configuration g;
    private boolean h;
    private boolean i;
    private long j = 0;

    public v(Configuration configuration) {
        this.g = configuration;
        this.h = aa.k(r.a(configuration));
    }

    public boolean a(String str) {
        boolean z;
        if (this.g.isUseCustomPrivacyPolicy()) {
            StringBuilder sb = new StringBuilder();
            sb.append("use custom privacy policy, the policy is ");
            sb.append(this.h ? AbstractCircuitBreaker.PROPERTY_NAME : "close");
            p.a(e, sb.toString());
            z = this.h;
        } else {
            z = b();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("use system experience plan, the policy is ");
            sb2.append(z ? AbstractCircuitBreaker.PROPERTY_NAME : "close");
            p.a(e, sb2.toString());
        }
        if (z) {
            return z;
        }
        boolean b2 = b(str);
        boolean c2 = c(str);
        boolean d2 = d(str);
        StringBuilder sb3 = new StringBuilder();
        sb3.append("This event ");
        sb3.append(str);
        sb3.append(b2 ? " is " : " is not ");
        sb3.append("basic event and ");
        sb3.append(c2 ? ai.ae : "is not");
        sb3.append(" recommend event and ");
        sb3.append(d2 ? ai.ae : "is not");
        sb3.append(" custom dau event");
        p.a(e, sb3.toString());
        return b2 || c2 || d2;
    }

    private boolean b(String str) {
        return b.h.equals(str) || b.g.equals(str);
    }

    private boolean c(String str) {
        OneTrack.IEventHook iEventHook = this.f;
        return iEventHook != null && iEventHook.isRecommendEvent(str);
    }

    private boolean d(String str) {
        OneTrack.IEventHook iEventHook = this.f;
        return iEventHook != null && iEventHook.isCustomDauEvent(str);
    }

    public void a(OneTrack.IEventHook iEventHook) {
        this.f = iEventHook;
    }

    public void a(boolean z) {
        this.h = z;
    }

    public String a() {
        return this.g.isUseCustomPrivacyPolicy() ? this.h ? a : b : b() ? c : d;
    }

    private boolean b() {
        if (Math.abs(System.currentTimeMillis() - this.j) > 900000) {
            this.j = System.currentTimeMillis();
            this.i = q.b(a.a());
        }
        return this.i;
    }
}
