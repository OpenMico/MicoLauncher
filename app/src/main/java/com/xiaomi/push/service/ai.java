package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class ai extends XMPushService.i {
    final /* synthetic */ XMPushService b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ai(XMPushService xMPushService, int i) {
        super(i);
        this.b = xMPushService;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "disconnect for service destroy.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        if (this.b.j != null) {
            this.b.j.b(15, (Exception) null);
            this.b.j = null;
        }
    }
}
