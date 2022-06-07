package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.al;

/* loaded from: classes4.dex */
class aw implements al.a {
    final /* synthetic */ XMPushService a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aw(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // com.xiaomi.push.service.al.a
    public void a() {
        this.a.n();
        if (al.a().m1135a() <= 0) {
            XMPushService xMPushService = this.a;
            xMPushService.a(new XMPushService.f(12, null));
        }
    }
}
