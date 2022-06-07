package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ef;
import com.xiaomi.push.service.al;
import java.util.HashMap;

/* loaded from: classes4.dex */
class er {
    public static void a(al.b bVar, String str, fn fnVar) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        ef.c cVar = new ef.c();
        if (!TextUtils.isEmpty(bVar.c)) {
            cVar.a(bVar.c);
        }
        if (!TextUtils.isEmpty(bVar.e)) {
            cVar.d(bVar.e);
        }
        if (!TextUtils.isEmpty(bVar.f)) {
            cVar.e(bVar.f);
        }
        cVar.b(bVar.f189a ? "1" : "0");
        cVar.c(!TextUtils.isEmpty(bVar.d) ? bVar.d : "XIAOMI-SASL");
        fg fgVar = new fg();
        fgVar.c(bVar.b);
        fgVar.a(Integer.parseInt(bVar.g));
        fgVar.b(bVar.f188a);
        fgVar.a("BIND", (String) null);
        fgVar.a(fgVar.e());
        b.m149a("[Slim]: bind id=" + fgVar.e());
        HashMap hashMap = new HashMap();
        hashMap.put("challenge", str);
        hashMap.put("token", bVar.c);
        hashMap.put("chid", bVar.g);
        hashMap.put("from", bVar.b);
        hashMap.put("id", fgVar.e());
        hashMap.put("to", "xiaomi.com");
        if (bVar.f189a) {
            str2 = "kick";
            str3 = "1";
        } else {
            str2 = "kick";
            str3 = "0";
        }
        hashMap.put(str2, str3);
        if (!TextUtils.isEmpty(bVar.e)) {
            str4 = "client_attrs";
            str5 = bVar.e;
        } else {
            str4 = "client_attrs";
            str5 = "";
        }
        hashMap.put(str4, str5);
        if (!TextUtils.isEmpty(bVar.f)) {
            str6 = "cloud_attrs";
            str7 = bVar.f;
        } else {
            str6 = "cloud_attrs";
            str7 = "";
        }
        hashMap.put(str6, str7);
        if (bVar.d.equals("XIAOMI-PASS") || bVar.d.equals("XMPUSH-PASS")) {
            str8 = ax.a(bVar.d, null, hashMap, bVar.h);
        } else {
            bVar.d.equals("XIAOMI-SASL");
            str8 = null;
        }
        cVar.f(str8);
        fgVar.a(cVar.mo888a(), (String) null);
        fnVar.b(fgVar);
    }

    public static void a(String str, String str2, fn fnVar) {
        fg fgVar = new fg();
        fgVar.c(str2);
        fgVar.a(Integer.parseInt(str));
        fgVar.a("UBND", (String) null);
        fnVar.b(fgVar);
    }
}
