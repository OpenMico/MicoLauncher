package com.xiaomi.push.service;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.hh;
import com.xiaomi.push.hr;
import com.xiaomi.push.i;
import com.xiaomi.push.ig;
import com.xiaomi.push.ir;
import com.xiaomi.push.service.bc;
import java.util.HashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class br extends bc.a {
    final /* synthetic */ XMPushService c;
    final /* synthetic */ k d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public br(String str, long j, XMPushService xMPushService, k kVar) {
        super(str, j);
        this.c = xMPushService;
        this.d = kVar;
    }

    @Override // com.xiaomi.push.service.bc.a
    void a(bc bcVar) {
        String a = bcVar.a("GAID", "gaid");
        String b = i.b(this.c);
        b.c("gaid :" + b);
        if (!TextUtils.isEmpty(b) && !TextUtils.equals(a, b)) {
            bcVar.a("GAID", "gaid", b);
            ig igVar = new ig();
            igVar.b(this.d.d);
            igVar.c(hr.ClientInfoUpdate.f67a);
            igVar.a(aj.a());
            igVar.a(new HashMap());
            igVar.m1036a().put("gaid", b);
            byte[] a2 = ir.a(bq.a(this.c.getPackageName(), this.d.d, igVar, hh.Notification));
            XMPushService xMPushService = this.c;
            xMPushService.a(xMPushService.getPackageName(), a2, true);
        }
    }
}
