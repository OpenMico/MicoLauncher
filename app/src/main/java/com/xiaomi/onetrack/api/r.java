package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.v;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class r implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Map b;
    final /* synthetic */ String c;
    final /* synthetic */ f d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public r(f fVar, String str, Map map, String str2) {
        this.d = fVar;
        this.a = str;
        this.b = map;
        this.c = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean c;
        JSONObject d;
        Configuration configuration;
        OneTrack.IEventHook iEventHook;
        v vVar;
        d dVar;
        try {
            c = this.d.c(this.a);
            if (!c) {
                JSONObject a = com.xiaomi.onetrack.util.r.a((Map<String, Object>) this.b, true);
                d = this.d.d(this.a);
                String str = this.c;
                String str2 = this.a;
                configuration = this.d.f;
                iEventHook = this.d.h;
                vVar = this.d.i;
                String a2 = c.a(str, str2, a, configuration, iEventHook, d, vVar);
                dVar = this.d.b;
                dVar.a(this.a, a2);
            }
        } catch (Exception e) {
            p.b("OneTrackImp", "track json error:" + e.toString());
        }
    }
}
