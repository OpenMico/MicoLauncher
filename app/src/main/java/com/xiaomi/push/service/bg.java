package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.fy;
import com.xiaomi.push.service.XMPushService;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class bg extends XMPushService.i {
    final /* synthetic */ String b;
    final /* synthetic */ byte[] c;
    final /* synthetic */ XMPushService d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bg(XMPushService xMPushService, int i, String str, byte[] bArr) {
        super(i);
        this.d = xMPushService;
        this.b = str;
        this.c = bArr;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "send mi push message";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        try {
            bq.a(this.d, this.b, this.c);
        } catch (fy e) {
            b.a(e);
            this.d.a(10, e);
        }
    }
}
