package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.fg;
import com.xiaomi.push.fy;
import com.xiaomi.push.service.XMPushService;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class u extends XMPushService.i {
    private XMPushService b;
    private fg c;

    public u(XMPushService xMPushService, fg fgVar) {
        super(4);
        this.b = null;
        this.b = xMPushService;
        this.c = fgVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "send a message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        try {
            if (this.c != null) {
                this.b.a(this.c);
            }
        } catch (fy e) {
            b.a(e);
            this.b.a(10, e);
        }
    }
}
