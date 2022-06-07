package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.push.ac;
import com.xiaomi.push.fo;
import com.xiaomi.push.service.XMPushService;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class bd extends XMPushService.i {
    final /* synthetic */ int b;
    final /* synthetic */ byte[] c;
    final /* synthetic */ String d;
    final /* synthetic */ XMPushService e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bd(XMPushService xMPushService, int i, int i2, byte[] bArr, String str) {
        super(i);
        this.e = xMPushService;
        this.b = i2;
        this.c = bArr;
        this.d = str;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "clear account cache.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        fo foVar;
        l.m1160a((Context) this.e);
        al.a().m1139a(Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND);
        ac.a(this.b);
        foVar = this.e.c;
        foVar.c(fo.a());
        this.e.a(this.c, this.d);
    }
}
