package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.fy;
import com.xiaomi.push.id;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
final class bk extends XMPushService.i {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ id c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bk(int i, XMPushService xMPushService, id idVar) {
        super(i);
        this.b = xMPushService;
        this.c = idVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "send app absent message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        try {
            bq.a(this.b, bq.a(this.c.b(), this.c.m1022a()));
        } catch (fy e) {
            b.a(e);
            this.b.a(10, e);
        }
    }
}
