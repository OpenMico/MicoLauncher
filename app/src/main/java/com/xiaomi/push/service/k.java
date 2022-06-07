package com.xiaomi.push.service;

import android.content.Context;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.g;
import com.xiaomi.push.service.al;
import com.xiaomi.push.u;
import java.util.Locale;

/* loaded from: classes4.dex */
public class k {
    public final int a;

    /* renamed from: a  reason: collision with other field name */
    public final String f190a;
    public final String b;
    public final String c;
    public final String d;
    public final String e;
    public final String f;

    public k(String str, String str2, String str3, String str4, String str5, String str6, int i) {
        this.f190a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.a = i;
    }

    public static boolean a() {
        try {
            return Class.forName("miui.os.Build").getField("IS_ALPHA_BUILD").getBoolean(null);
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean a(Context context) {
        return "com.xiaomi.xmsf".equals(context.getPackageName()) && a();
    }

    private static boolean b(Context context) {
        return context.getPackageName().equals("com.xiaomi.xmsf");
    }

    public al.b a(XMPushService xMPushService) {
        al.b bVar = new al.b(xMPushService);
        a(bVar, xMPushService, xMPushService.b(), ai.aD);
        return bVar;
    }

    public al.b a(al.b bVar, Context context, d dVar, String str) {
        bVar.f188a = context.getPackageName();
        bVar.b = this.f190a;
        bVar.h = this.c;
        bVar.c = this.b;
        bVar.g = Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND;
        bVar.d = "XMPUSH-PASS";
        bVar.f189a = false;
        String str2 = "";
        if (b(context)) {
            str2 = g.b(context);
        }
        bVar.e = String.format("%1$s:%2$s,%3$s:%4$s,%5$s:%6$s:%7$s:%8$s,%9$s:%10$s,%11$s:%12$s", "sdk_ver", 38, "cpvn", "3_6_19", "cpvc", 30619, "aapn", str2, "country_code", a.a(context).b(), "region", a.a(context).a());
        bVar.f = String.format("%1$s:%2$s,%3$s:%4$s,%5$s:%6$s,sync:1", "appid", b(context) ? "1000271" : this.d, "locale", Locale.getDefault().toString(), Constants.EXTRA_KEY_MIID, u.a(context));
        if (a(context)) {
            bVar.f += String.format(",%1$s:%2$s", "ab", str);
        }
        bVar.f187a = dVar;
        return bVar;
    }
}
