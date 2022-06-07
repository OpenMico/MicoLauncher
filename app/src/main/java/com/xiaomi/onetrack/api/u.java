package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.v;

/* loaded from: classes4.dex */
class u implements Runnable {
    final /* synthetic */ f a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public u(f fVar) {
        this.a = fVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Configuration configuration;
        OneTrack.IEventHook iEventHook;
        v vVar;
        d dVar;
        try {
            configuration = this.a.f;
            iEventHook = this.a.h;
            vVar = this.a.i;
            String a = c.a(configuration, iEventHook, vVar);
            dVar = this.a.b;
            dVar.a(b.i, a);
        } catch (Exception e) {
            p.b("OneTrackImp", "cta event error: " + e.toString());
        }
    }
}
