package com.xiaomi.push.service;

import com.xiaomi.push.aj;
import com.xiaomi.push.hh;
import com.xiaomi.push.ig;
import com.xiaomi.push.ir;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class b extends aj.a {
    private ig a;
    private WeakReference<XMPushService> b;
    private boolean c;

    public b(ig igVar, WeakReference<XMPushService> weakReference, boolean z) {
        this.c = false;
        this.a = igVar;
        this.b = weakReference;
        this.c = z;
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 22;
    }

    @Override // java.lang.Runnable
    public void run() {
        XMPushService xMPushService;
        WeakReference<XMPushService> weakReference = this.b;
        if (weakReference != null && this.a != null && (xMPushService = weakReference.get()) != null) {
            this.a.a(aj.a());
            this.a.a(false);
            com.xiaomi.channel.commonutils.logger.b.c("MoleInfo aw_ping : send aw_Ping msg " + this.a.a());
            try {
                String c = this.a.c();
                xMPushService.a(c, ir.a(bq.a(c, this.a.b(), this.a, hh.Notification)), this.c);
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.d("MoleInfo aw_ping : send help app ping error" + e.toString());
            }
        }
    }
}
