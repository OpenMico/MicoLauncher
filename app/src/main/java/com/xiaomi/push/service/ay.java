package com.xiaomi.push.service;

import android.database.ContentObserver;
import android.os.Handler;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class ay extends ContentObserver {
    final /* synthetic */ XMPushService a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ay(XMPushService xMPushService, Handler handler) {
        super(handler);
        this.a = xMPushService;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        boolean l;
        super.onChange(z);
        l = this.a.l();
        b.m149a("ExtremePowerMode:" + l);
        if (l) {
            XMPushService xMPushService = this.a;
            xMPushService.a(new XMPushService.f(23, null));
            return;
        }
        this.a.a(true);
    }
}
