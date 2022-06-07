package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.fy;
import com.xiaomi.push.id;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
final class bn extends XMPushService.i {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ id c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bn(int i, XMPushService xMPushService, id idVar) {
        super(i);
        this.b = xMPushService;
        this.c = idVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "send ack message for unrecognized new miui message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        try {
            id a = p.a((Context) this.b, this.c);
            a.m1021a().a("miui_message_unrecognized", "1");
            bq.a(this.b, a);
        } catch (fy e) {
            b.a(e);
            this.b.a(10, e);
        }
    }
}
