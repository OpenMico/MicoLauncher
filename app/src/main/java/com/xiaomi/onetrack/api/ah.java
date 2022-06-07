package com.xiaomi.onetrack.api;

import android.os.Process;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.aj;
import com.xiaomi.onetrack.b.g;
import com.xiaomi.onetrack.util.i;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.v;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class ah implements aj.b, d {
    private static final String a = "OneTrackSystemImp";
    private static final int b = 102400;
    private static final int c = 2;
    private Configuration e;
    private v g;
    private final ConcurrentHashMap<String, String> d = new ConcurrentHashMap<>();
    private aj f = aj.a();

    public ah(Configuration configuration, v vVar) {
        this.e = configuration;
        this.g = vVar;
        this.f.a(this);
    }

    @Override // com.xiaomi.onetrack.api.d
    public void a(String str, String str2) {
        v vVar = this.g;
        if (vVar != null && !vVar.a(str)) {
            p.a(a, "The privacy policy is not permitted, and the event is not basic or recommend event or custom dau event, skip it.");
        } else if (b(str, str2)) {
            if (g.b()) {
                g.a(this);
            } else if (!b.i.equalsIgnoreCase(str)) {
                g.a(str, str2);
                return;
            }
            if (p.a) {
                p.a(a, "track name:" + str + " data :" + str2 + " tid" + Process.myTid());
            }
            synchronized (this.d) {
                if (!this.f.a(str, str2, this.e)) {
                    this.d.put(str2, str);
                    if (p.a) {
                        p.a(a, "track mIOneTrackService is null!" + this.d.size() + "  " + str2);
                    }
                }
            }
        }
    }

    private boolean b(String str, String str2) {
        if (OneTrack.isDisable()) {
            return false;
        }
        if ((str != null && str.equals("onetrack_bug_report")) || str2 == null || str2.length() * 2 <= b) {
            return true;
        }
        p.a(a, "Event size exceed limitation!");
        return false;
    }

    @Override // com.xiaomi.onetrack.api.d
    public void a(int i) {
        this.f.a(i);
    }

    @Override // com.xiaomi.onetrack.api.aj.b
    public void a() {
        if (g.b()) {
            b();
        }
    }

    private void b() {
        i.a(new ai(this));
    }
}
