package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ef;
import com.xiaomi.push.fo;
import com.xiaomi.push.fr;
import java.util.Map;

/* loaded from: classes4.dex */
class av extends fo {
    final /* synthetic */ XMPushService d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public av(XMPushService xMPushService, Map map, int i, String str, fr frVar) {
        super(map, i, str, frVar);
        this.d = xMPushService;
    }

    @Override // com.xiaomi.push.fo
    /* renamed from: a */
    public byte[] mo923a() {
        try {
            ef.b bVar = new ef.b();
            bVar.a(ba.a().c());
            return bVar.mo888a();
        } catch (Exception e) {
            b.m149a("getOBBString err: " + e.toString());
            return null;
        }
    }
}
