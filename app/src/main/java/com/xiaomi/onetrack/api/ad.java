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
public class ad implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ OneTrack.UserIdType b;
    final /* synthetic */ boolean c;
    final /* synthetic */ Map d;
    final /* synthetic */ f e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ad(f fVar, String str, OneTrack.UserIdType userIdType, boolean z, Map map) {
        this.e = fVar;
        this.a = str;
        this.b = userIdType;
        this.c = z;
        this.d = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONObject d;
        Configuration configuration;
        OneTrack.IEventHook iEventHook;
        v vVar;
        d dVar;
        try {
            aa.g(this.a);
            aa.h(this.b.getUserIdType());
            if (!this.c) {
                JSONObject a = r.a((Map<String, Object>) this.d, false);
                a.put(OneTrack.Param.UID, this.a);
                a.put("uid_type", this.b.getUserIdType());
                d = this.e.d(b.a);
                configuration = this.e.f;
                iEventHook = this.e.h;
                vVar = this.e.i;
                String c = c.c(a, configuration, iEventHook, d, vVar);
                dVar = this.e.b;
                dVar.a(b.a, c);
            }
        } catch (Exception e) {
            p.b("OneTrackImp", "login error:" + e.toString());
        }
    }
}
