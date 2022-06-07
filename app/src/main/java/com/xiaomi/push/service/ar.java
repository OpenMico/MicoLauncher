package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.eg;
import com.xiaomi.push.el;
import com.xiaomi.push.ep;
import com.xiaomi.push.hf;
import com.xiaomi.push.hh;
import com.xiaomi.push.hr;
import com.xiaomi.push.ig;
import com.xiaomi.push.ir;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class ar implements ep {
    @Override // com.xiaomi.push.ep
    public void a(Context context, HashMap<String, String> hashMap) {
        ig igVar = new ig();
        igVar.b(el.a(context).m893a());
        igVar.d(el.a(context).b());
        igVar.c(hr.AwakeAppResponse.f67a);
        igVar.a(aj.a());
        igVar.f129a = hashMap;
        byte[] a = ir.a(bq.a(igVar.c(), igVar.b(), igVar, hh.Notification));
        if (context instanceof XMPushService) {
            b.m149a("MoleInfo : send data directly in pushLayer " + igVar.a());
            ((XMPushService) context).a(context.getPackageName(), a, true);
            return;
        }
        b.m149a("MoleInfo : context is not correct in pushLayer " + igVar.a());
    }

    @Override // com.xiaomi.push.ep
    public void b(Context context, HashMap<String, String> hashMap) {
        hf a = hf.a(context);
        if (a != null) {
            a.a("category_awake_app", "wake_up_app", 1L, eg.a(hashMap));
        }
    }

    @Override // com.xiaomi.push.ep
    public void c(Context context, HashMap<String, String> hashMap) {
        b.m149a("MoleInfo：\u3000" + eg.b(hashMap));
    }
}
