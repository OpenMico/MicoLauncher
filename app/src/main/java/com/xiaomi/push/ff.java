package com.xiaomi.push;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class ff extends XMPushService.i {
    final /* synthetic */ int b;
    final /* synthetic */ Exception c;
    final /* synthetic */ fu d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ff(fu fuVar, int i, int i2, Exception exc) {
        super(i);
        this.d = fuVar;
        this.b = i2;
        this.c = exc;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "shutdown the connection. " + this.b + ", " + this.c;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        this.d.b.a(this.b, this.c);
    }
}
