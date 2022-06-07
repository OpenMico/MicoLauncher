package com.xiaomi.push.service;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.service.al;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class bs implements al.b.a {
    final /* synthetic */ XMPushService a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public bs(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // com.xiaomi.push.service.al.b.a
    public void a(al.c cVar, al.c cVar2, int i) {
        if (cVar2 == al.c.binded) {
            o.a(this.a);
            o.b(this.a);
        } else if (cVar2 == al.c.unbind) {
            o.a(this.a, ErrorCode.ERROR_SERVICE_UNAVAILABLE, " the push is not connected.");
        }
    }
}
