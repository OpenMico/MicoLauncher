package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.ServiceQualityEvent;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.v;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class t implements Runnable {
    final /* synthetic */ ServiceQualityEvent a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public t(f fVar, ServiceQualityEvent serviceQualityEvent) {
        this.b = fVar;
        this.a = serviceQualityEvent;
    }

    @Override // java.lang.Runnable
    public void run() {
        Configuration configuration;
        OneTrack.IEventHook iEventHook;
        v vVar;
        d dVar;
        try {
            ServiceQualityEvent serviceQualityEvent = this.a;
            configuration = this.b.f;
            iEventHook = this.b.h;
            vVar = this.b.i;
            String a = c.a(serviceQualityEvent, configuration, iEventHook, vVar);
            dVar = this.b.b;
            dVar.a(b.e, a);
        } catch (Exception e) {
            p.b("OneTrackImp", "trackNetAvailableEvent error: " + e.toString());
        }
    }
}
