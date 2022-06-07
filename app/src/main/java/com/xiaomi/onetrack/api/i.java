package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.v;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class i implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ Map b;
    final /* synthetic */ f c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(f fVar, boolean z, Map map) {
        this.c = fVar;
        this.a = z;
        this.b = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONObject d;
        Configuration configuration;
        OneTrack.IEventHook iEventHook;
        v vVar;
        d dVar;
        try {
            if (!this.a) {
                JSONObject a = r.a((Map<String, Object>) this.b, false);
                String u = aa.u();
                String w = aa.w();
                a.put(OneTrack.Param.UID, u);
                a.put("uid_type", w);
                d = this.c.d(b.b);
                configuration = this.c.f;
                iEventHook = this.c.h;
                vVar = this.c.i;
                String d2 = c.d(a, configuration, iEventHook, d, vVar);
                dVar = this.c.b;
                dVar.a(b.b, d2);
            }
            aa.v();
            aa.x();
        } catch (Exception e) {
            p.b("OneTrackImp", "logout error:" + e.toString());
        }
    }
}
