package com.xiaomi.push.service;

import com.xiaomi.push.fg;
import com.xiaomi.push.fs;
import com.xiaomi.push.ge;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class ad implements fs {
    final /* synthetic */ XMPushService a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ad(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // com.xiaomi.push.fs
    public void a(fg fgVar) {
        XMPushService xMPushService = this.a;
        xMPushService.a(new XMPushService.c(fgVar));
    }

    @Override // com.xiaomi.push.fs, com.xiaomi.push.ga
    /* renamed from: a */
    public void mo782a(ge geVar) {
        XMPushService xMPushService = this.a;
        xMPushService.a(new XMPushService.k(geVar));
    }
}
