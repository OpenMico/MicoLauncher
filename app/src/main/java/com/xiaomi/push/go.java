package com.xiaomi.push;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class go extends XMPushService.i {
    final /* synthetic */ gn b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public go(gn gnVar, int i) {
        super(i);
        this.b = gnVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "Handling bind stats";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        this.b.c();
    }
}
