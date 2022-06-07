package com.xiaomi.mipush.sdk;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.push.aj;
import com.xiaomi.push.el;
import com.xiaomi.push.en;
import com.xiaomi.push.hm;
import com.xiaomi.push.hr;
import com.xiaomi.push.ig;
import com.xiaomi.push.ir;
import com.xiaomi.push.is;
import com.xiaomi.push.l;
import com.xiaomi.push.service.ag;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class p {
    public static void a(Context context, Intent intent, Uri uri) {
        el a;
        en enVar;
        if (context != null) {
            ay.a(context).a();
            if (el.a(context.getApplicationContext()).m892a() == null) {
                el.a(context.getApplicationContext()).a(d.m727a(context.getApplicationContext()).m728a(), context.getPackageName(), ag.a(context.getApplicationContext()).a(hm.AwakeInfoUploadWaySwitch.a(), 0), new e());
                ag.a(context).a(new ba(102, "awake online config", context));
            }
            if ((context instanceof Activity) && intent != null) {
                a = el.a(context.getApplicationContext());
                enVar = en.ACTIVITY;
            } else if (!(context instanceof Service) || intent == null) {
                if (uri != null && !TextUtils.isEmpty(uri.toString())) {
                    el.a(context.getApplicationContext()).a(en.PROVIDER, context, (Intent) null, uri.toString());
                    return;
                }
                return;
            } else if ("com.xiaomi.mipush.sdk.WAKEUP".equals(intent.getAction())) {
                a = el.a(context.getApplicationContext());
                enVar = en.SERVICE_COMPONENT;
            } else {
                a = el.a(context.getApplicationContext());
                enVar = en.SERVICE_ACTION;
            }
            a.a(enVar, context, intent, (String) null);
        }
    }

    private static void a(Context context, ig igVar) {
        boolean a = ag.a(context).a(hm.AwakeAppPingSwitch.a(), false);
        int a2 = ag.a(context).a(hm.AwakeAppPingFrequency.a(), 0);
        if (a2 >= 0 && a2 < 30) {
            b.c("aw_ping: frquency need > 30s.");
            a2 = 30;
        }
        if (a2 < 0) {
            a = false;
        }
        if (!l.m1113a()) {
            a(context, igVar, a, a2);
        } else if (a) {
            aj.a(context.getApplicationContext()).a((aj.a) new az(igVar, context), a2);
        }
    }

    public static final <T extends is<T, ?>> void a(Context context, T t, boolean z, int i) {
        byte[] a = ir.a(t);
        if (a == null) {
            b.m149a("send message fail, because msgBytes is null.");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("action_help_ping");
        intent.putExtra("extra_help_ping_switch", z);
        intent.putExtra("extra_help_ping_frequency", i);
        intent.putExtra("mipush_payload", a);
        intent.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
        ay.a(context).a(intent);
    }

    public static void a(Context context, String str) {
        b.m149a("aw_ping : send aw_ping cmd and content to push service from 3rd app");
        HashMap hashMap = new HashMap();
        hashMap.put("awake_info", str);
        hashMap.put("event_type", String.valueOf((int) Constants.RESULT_RESTART_BINDING_EMAIL));
        hashMap.put("description", "ping message");
        ig igVar = new ig();
        igVar.b(d.m727a(context).m728a());
        igVar.d(context.getPackageName());
        igVar.c(hr.AwakeAppResponse.f67a);
        igVar.a(com.xiaomi.push.service.aj.a());
        igVar.f129a = hashMap;
        a(context, igVar);
    }

    public static void a(Context context, String str, int i, String str2) {
        ig igVar = new ig();
        igVar.b(str);
        igVar.a(new HashMap());
        igVar.m1036a().put("extra_aw_app_online_cmd", String.valueOf(i));
        igVar.m1036a().put("extra_help_aw_info", str2);
        igVar.a(com.xiaomi.push.service.aj.a());
        byte[] a = ir.a(igVar);
        if (a == null) {
            b.m149a("send message fail, because msgBytes is null.");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("action_aw_app_logic");
        intent.putExtra("mipush_payload", a);
        ay.a(context).a(intent);
    }
}
