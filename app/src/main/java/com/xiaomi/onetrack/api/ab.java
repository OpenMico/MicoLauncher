package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.v;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ab implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ab(f fVar, Map map) {
        this.b = fVar;
        this.a = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONObject d;
        Configuration configuration;
        OneTrack.IEventHook iEventHook;
        v vVar;
        d dVar;
        try {
            d = this.b.d(b.c);
            JSONObject a = r.a((Map<String, Object>) this.a, false);
            configuration = this.b.f;
            iEventHook = this.b.h;
            vVar = this.b.i;
            String a2 = c.a(a, configuration, iEventHook, d, vVar);
            dVar = this.b.b;
            dVar.a(b.c, a2);
        } catch (Exception e) {
            p.b("OneTrackImp", "setUserProfile map error:" + e.toString());
        }
    }
}
