package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.v;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class h implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Number b;
    final /* synthetic */ f c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(f fVar, String str, Number number) {
        this.c = fVar;
        this.a = str;
        this.b = number;
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONObject d;
        Configuration configuration;
        OneTrack.IEventHook iEventHook;
        v vVar;
        d dVar;
        try {
            d = this.c.d(b.d);
            JSONObject put = new JSONObject().put(this.a, this.b);
            configuration = this.c.f;
            iEventHook = this.c.h;
            vVar = this.c.i;
            String b = c.b(put, configuration, iEventHook, d, vVar);
            dVar = this.c.b;
            dVar.a(b.d, b);
        } catch (Exception e) {
            p.b("OneTrackImp", "userProfileIncrement single error:" + e.toString());
        }
    }
}
