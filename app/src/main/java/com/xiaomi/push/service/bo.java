package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.fy;
import com.xiaomi.push.id;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
final class bo extends XMPushService.i {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ id c;
    final /* synthetic */ String d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bo(int i, XMPushService xMPushService, id idVar, String str) {
        super(i);
        this.b = xMPushService;
        this.c = idVar;
        this.d = str;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "send app absent ack message for message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        try {
            id a = p.a((Context) this.b, this.c);
            a.m1021a().a("absent_target_package", this.d);
            bq.a(this.b, a);
        } catch (fy e) {
            b.a(e);
            this.b.a(10, e);
        }
    }
}
