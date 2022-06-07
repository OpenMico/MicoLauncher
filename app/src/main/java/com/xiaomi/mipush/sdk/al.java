package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.az;
import com.xiaomi.push.g;
import com.xiaomi.push.hh;
import com.xiaomi.push.hr;
import com.xiaomi.push.hu;
import com.xiaomi.push.i;
import com.xiaomi.push.ig;
import com.xiaomi.push.l;
import com.xiaomi.push.o;
import com.xiaomi.push.service.aj;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class al implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ boolean b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public al(Context context, boolean z) {
        this.a = context;
        this.b = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str;
        String str2;
        Map<String, String> map;
        String d;
        String d2;
        String c;
        String c2;
        b.m149a("do sync info");
        ig igVar = new ig(aj.a(), false);
        d a = d.m727a(this.a);
        igVar.c(hr.SyncInfo.f67a);
        igVar.b(a.m728a());
        igVar.d(this.a.getPackageName());
        igVar.f129a = new HashMap();
        Map<String, String> map2 = igVar.f129a;
        Context context = this.a;
        o.a(map2, "app_version", g.m929a(context, context.getPackageName()));
        Map<String, String> map3 = igVar.f129a;
        Context context2 = this.a;
        o.a(map3, Constants.EXTRA_KEY_APP_VERSION_CODE, Integer.toString(g.a(context2, context2.getPackageName())));
        o.a(igVar.f129a, "push_sdk_vn", "3_6_19");
        o.a(igVar.f129a, "push_sdk_vc", Integer.toString(30619));
        o.a(igVar.f129a, "token", a.b());
        if (!l.d()) {
            String a2 = az.a(i.f(this.a));
            String h = i.h(this.a);
            if (!TextUtils.isEmpty(h)) {
                a2 = a2 + Constants.ACCEPT_TIME_SEPARATOR_SP + h;
            }
            if (!TextUtils.isEmpty(a2)) {
                o.a(igVar.f129a, Constants.EXTRA_KEY_IMEI_MD5, a2);
            }
        }
        o.a(igVar.f129a, Constants.EXTRA_KEY_REG_ID, a.c());
        o.a(igVar.f129a, Constants.EXTRA_KEY_REG_SECRET, a.d());
        o.a(igVar.f129a, Constants.EXTRA_KEY_ACCEPT_TIME, MiPushClient.getAcceptTime(this.a).replace(Constants.ACCEPT_TIME_SEPARATOR_SP, Constants.ACCEPT_TIME_SEPARATOR_SERVER));
        if (this.b) {
            Map<String, String> map4 = igVar.f129a;
            c = be.c(MiPushClient.getAllAlias(this.a));
            o.a(map4, Constants.EXTRA_KEY_ALIASES_MD5, c);
            Map<String, String> map5 = igVar.f129a;
            c2 = be.c(MiPushClient.getAllTopic(this.a));
            o.a(map5, Constants.EXTRA_KEY_TOPICS_MD5, c2);
            map = igVar.f129a;
            str2 = Constants.EXTRA_KEY_ACCOUNTS_MD5;
            str = be.c(MiPushClient.getAllUserAccount(this.a));
        } else {
            Map<String, String> map6 = igVar.f129a;
            d = be.d(MiPushClient.getAllAlias(this.a));
            o.a(map6, Constants.EXTRA_KEY_ALIASES, d);
            Map<String, String> map7 = igVar.f129a;
            d2 = be.d(MiPushClient.getAllTopic(this.a));
            o.a(map7, Constants.EXTRA_KEY_TOPICS, d2);
            map = igVar.f129a;
            str2 = Constants.EXTRA_KEY_ACCOUNTS;
            str = be.d(MiPushClient.getAllUserAccount(this.a));
        }
        o.a(map, str2, str);
        ay.a(this.a).a((ay) igVar, hh.Notification, false, (hu) null);
    }
}
