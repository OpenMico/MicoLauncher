package com.xiaomi.push;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class fa extends XMPushService.i {
    final /* synthetic */ long b;
    final /* synthetic */ fu c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public fa(fu fuVar, int i, long j) {
        super(i);
        this.c = fuVar;
        this.b = j;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "check the ping-pong." + this.b;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        Thread.yield();
        if (this.c.c() && !this.c.a(this.b)) {
            this.c.b.a(22, (Exception) null);
        }
    }
}
