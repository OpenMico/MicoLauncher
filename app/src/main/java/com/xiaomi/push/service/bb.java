package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.l;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class bb implements l.a {
    final /* synthetic */ XMPushService.i a;
    final /* synthetic */ XMPushService b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public bb(XMPushService xMPushService, XMPushService.i iVar) {
        this.b = xMPushService;
        this.a = iVar;
    }

    @Override // com.xiaomi.push.service.l.a
    public void a() {
        this.b.a(this.a);
    }
}
