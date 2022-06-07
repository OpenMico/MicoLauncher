package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.fy;
import com.xiaomi.push.id;
import com.xiaomi.push.service.XMPushService;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes4.dex */
final class bp extends XMPushService.i {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ id c;
    final /* synthetic */ String d;
    final /* synthetic */ String e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bp(int i, XMPushService xMPushService, id idVar, String str, String str2) {
        super(i);
        this.b = xMPushService;
        this.c = idVar;
        this.d = str;
        this.e = str2;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "send wrong message ack for message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        try {
            id a = p.a((Context) this.b, this.c);
            a.f118a.a("error", this.d);
            a.f118a.a(IChannel.EXTRA_CLOSE_REASON, this.e);
            bq.a(this.b, a);
        } catch (fy e) {
            b.a(e);
            this.b.a(10, e);
        }
    }
}
