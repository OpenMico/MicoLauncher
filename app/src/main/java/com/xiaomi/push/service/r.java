package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.al;

/* loaded from: classes4.dex */
class r extends XMPushService.i {
    final /* synthetic */ al.b.c b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public r(al.b.c cVar, int i) {
        super(i);
        this.b = cVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "clear peer job";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        if (this.b.b == this.b.a.l) {
            b.b("clean peer, chid = " + this.b.a.g);
            this.b.a.l = null;
        }
    }
}
