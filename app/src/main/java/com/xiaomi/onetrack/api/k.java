package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.v;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class k implements Runnable {
    final /* synthetic */ f a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public k(f fVar) {
        this.a = fVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONObject d;
        Configuration configuration;
        OneTrack.IEventHook iEventHook;
        v vVar;
        d dVar;
        try {
            if (!ac.d(aa.t())) {
                aa.m(System.currentTimeMillis());
                d = this.a.d(b.h);
                configuration = this.a.f;
                iEventHook = this.a.h;
                vVar = this.a.i;
                String a = c.a(configuration, iEventHook, d, vVar);
                dVar = this.a.b;
                dVar.a(b.h, a);
            }
        } catch (Exception e) {
            p.b("OneTrackImp", "trackDau error  e:" + e.toString());
        }
    }
}
