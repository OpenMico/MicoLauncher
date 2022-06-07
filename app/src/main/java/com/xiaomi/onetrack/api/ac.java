package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.v;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ac implements Runnable {
    final /* synthetic */ Object a;
    final /* synthetic */ String b;
    final /* synthetic */ f c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ac(f fVar, Object obj, String str) {
        this.c = fVar;
        this.a = obj;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONObject d;
        Configuration configuration;
        OneTrack.IEventHook iEventHook;
        v vVar;
        d dVar;
        try {
            if (!r.b(this.a)) {
                r.a("OneTrackImp", this.b);
                return;
            }
            d = this.c.d(b.c);
            JSONObject put = new JSONObject().put(this.b, this.a);
            configuration = this.c.f;
            iEventHook = this.c.h;
            vVar = this.c.i;
            String a = c.a(put, configuration, iEventHook, d, vVar);
            dVar = this.c.b;
            dVar.a(b.c, a);
        } catch (Exception e) {
            p.b("OneTrackImp", "setUserProfile single error:" + e.toString());
        }
    }
}
