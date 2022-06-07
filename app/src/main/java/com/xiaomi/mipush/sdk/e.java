package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.eg;
import com.xiaomi.push.el;
import com.xiaomi.push.ep;
import com.xiaomi.push.hh;
import com.xiaomi.push.hr;
import com.xiaomi.push.hu;
import com.xiaomi.push.ig;
import com.xiaomi.push.service.aj;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class e implements ep {
    @Override // com.xiaomi.push.ep
    public void a(Context context, HashMap<String, String> hashMap) {
        ig igVar = new ig();
        igVar.b(el.a(context).m893a());
        igVar.d(el.a(context).b());
        igVar.c(hr.AwakeAppResponse.f67a);
        igVar.a(aj.a());
        igVar.f129a = hashMap;
        ay.a(context).a((ay) igVar, hh.Notification, true, (hu) null, true);
        b.m149a("MoleInfo：\u3000send data in app layer");
    }

    @Override // com.xiaomi.push.ep
    public void b(Context context, HashMap<String, String> hashMap) {
        MiTinyDataClient.upload("category_awake_app", "wake_up_app", 1L, eg.a(hashMap));
        b.m149a("MoleInfo：\u3000send data in app layer");
    }

    @Override // com.xiaomi.push.ep
    public void c(Context context, HashMap<String, String> hashMap) {
        b.m149a("MoleInfo：\u3000" + eg.b(hashMap));
        String str = hashMap.get("awake_info");
        if (String.valueOf(1007).equals(hashMap.get("event_type"))) {
            p.a(context, str);
        }
    }
}
