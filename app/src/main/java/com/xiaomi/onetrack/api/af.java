package com.xiaomi.onetrack.api;

import android.content.Context;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.a.a;
import com.xiaomi.onetrack.b.g;
import com.xiaomi.onetrack.e.d;
import com.xiaomi.onetrack.e.f;
import com.xiaomi.onetrack.util.i;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.v;

/* loaded from: classes4.dex */
public class af implements d {
    private static final String a = "OneTrackLocalImp";
    private static final int b = 102400;
    private static final int c = 2;
    private Configuration d;
    private v e;

    public af(Context context, Configuration configuration, v vVar) {
        f.a(context);
        this.d = configuration;
        this.e = vVar;
    }

    @Override // com.xiaomi.onetrack.api.d
    public void a(String str, String str2) {
        v vVar = this.e;
        if (vVar != null && !vVar.a(str)) {
            p.a(a, "The privacy policy is not permitted, and the event is not basic or recommend event or custom dau event, skip it.");
        } else if (b(str, str2)) {
            if (!g.b()) {
                g.a(str, str2);
                return;
            }
            g.a(this);
            if (p.a && !str.equalsIgnoreCase("onetrack_bug_report")) {
                p.a(a, "track data:" + str2);
            }
            a.a(this.d.getAppId());
            if (!a(str)) {
                d.a(this.d.getAppId(), com.xiaomi.onetrack.e.a.d(), str, str2);
            }
        }
    }

    @Override // com.xiaomi.onetrack.api.d
    public void a(int i) {
        if (g.b() && i == 2) {
            i.a(new ag(this));
        }
    }

    public boolean b(String str, String str2) {
        if (OneTrack.isDisable() || OneTrack.isUseSystemNetTrafficOnly()) {
            return false;
        }
        if ((str != null && str.equals("onetrack_bug_report")) || str2 == null || str2.length() * 2 <= b) {
            return true;
        }
        p.a(a, "Event size exceed limitation!");
        return false;
    }

    public boolean a(String str) {
        boolean z;
        boolean z2;
        boolean z3;
        Exception e;
        try {
            z3 = com.xiaomi.onetrack.a.f.a().a(this.d.getAppId(), a.a);
            try {
                z2 = com.xiaomi.onetrack.a.f.a().a(this.d.getAppId(), str, a.a, false);
            } catch (Exception e2) {
                e = e2;
                z2 = false;
            }
        } catch (Exception e3) {
            e = e3;
            z3 = false;
            z2 = false;
        }
        try {
            z = b(str);
        } catch (Exception e4) {
            e = e4;
            p.a(a, "isDisableTrack: " + e.toString());
            z = false;
            if (!z3) {
            }
        }
        return !z3 || z2 || z;
    }

    private boolean b(String str) {
        long b2 = com.xiaomi.onetrack.a.f.a().b(this.d.getAppId(), str);
        long abs = Math.abs(com.xiaomi.onetrack.util.oaid.a.a().a(com.xiaomi.onetrack.e.a.a()).hashCode()) % 100;
        boolean z = true;
        if (b2 <= abs) {
            z = false;
        }
        p.a(a, "shouldUploadBySampling " + str + ",  shouldUpload=" + z + ", sample=" + b2 + ", val=" + abs);
        return !z;
    }
}
