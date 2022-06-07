package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.d;
import com.xiaomi.push.az;
import com.xiaomi.push.fb;
import com.xiaomi.push.g;
import com.xiaomi.push.hh;
import com.xiaomi.push.hr;
import com.xiaomi.push.hu;
import com.xiaomi.push.hv;
import com.xiaomi.push.hx;
import com.xiaomi.push.i;
import com.xiaomi.push.ig;
import com.xiaomi.push.ih;
import com.xiaomi.push.ii;
import com.xiaomi.push.in;
import com.xiaomi.push.io;
import com.xiaomi.push.ir;
import com.xiaomi.push.l;
import com.xiaomi.push.service.aj;
import com.xiaomi.push.service.z;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/* loaded from: classes4.dex */
public class MiPushClient4Hybrid {
    private static Map<String, d.a> a = new HashMap();
    private static Map<String, Long> b = new HashMap();
    private static MiPushCallback c;

    /* loaded from: classes4.dex */
    public static class MiPushCallback {
        public void onCommandResult(String str, MiPushCommandMessage miPushCommandMessage) {
        }

        public void onReceiveRegisterResult(String str, MiPushCommandMessage miPushCommandMessage) {
        }

        public void onReceiveUnregisterResult(String str, MiPushCommandMessage miPushCommandMessage) {
        }
    }

    private static short a(MiPushMessage miPushMessage, boolean z) {
        String str = miPushMessage.getExtra() == null ? "" : miPushMessage.getExtra().get(Constants.EXTRA_KEY_HYBRID_DEVICE_STATUS);
        int i = 0;
        if (!TextUtils.isEmpty(str)) {
            i = Integer.valueOf(str).intValue();
        }
        if (!z) {
            i = (i & (-4)) + g.a.NOT_ALLOWED.a();
        }
        return (short) i;
    }

    private static void a(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        sharedPreferences.edit().putLong("last_pull_notification_" + str, System.currentTimeMillis()).commit();
    }

    private static boolean b(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        StringBuilder sb = new StringBuilder();
        sb.append("last_pull_notification_");
        sb.append(str);
        return Math.abs(System.currentTimeMillis() - sharedPreferences.getLong(sb.toString(), -1L)) > 300000;
    }

    public static boolean isRegistered(Context context, String str) {
        return d.m727a(context).a(str) != null;
    }

    public static void onReceiveRegisterResult(Context context, ii iiVar) {
        d.a aVar;
        String b2 = iiVar.b();
        if (iiVar.a() == 0 && (aVar = a.get(b2)) != null) {
            aVar.a(iiVar.e, iiVar.f);
            d.m727a(context).a(b2, aVar);
        }
        ArrayList arrayList = null;
        if (!TextUtils.isEmpty(iiVar.e)) {
            arrayList = new ArrayList();
            arrayList.add(iiVar.e);
        }
        MiPushCommandMessage generateCommandMessage = PushMessageHelper.generateCommandMessage(fb.COMMAND_REGISTER.f26a, arrayList, iiVar.f143a, iiVar.d, null);
        MiPushCallback miPushCallback = c;
        if (miPushCallback != null) {
            miPushCallback.onReceiveRegisterResult(b2, generateCommandMessage);
        }
    }

    public static void onReceiveUnregisterResult(Context context, io ioVar) {
        MiPushCommandMessage generateCommandMessage = PushMessageHelper.generateCommandMessage(fb.COMMAND_UNREGISTER.f26a, null, ioVar.a, ioVar.d, null);
        String a2 = ioVar.a();
        MiPushCallback miPushCallback = c;
        if (miPushCallback != null) {
            miPushCallback.onReceiveUnregisterResult(a2, generateCommandMessage);
        }
    }

    public static void registerPush(Context context, String str, String str2, String str3) {
        if (d.m727a(context).m732a(str2, str3, str)) {
            ArrayList arrayList = new ArrayList();
            d.a a2 = d.m727a(context).a(str);
            if (a2 != null) {
                arrayList.add(a2.c);
                MiPushCommandMessage generateCommandMessage = PushMessageHelper.generateCommandMessage(fb.COMMAND_REGISTER.f26a, arrayList, 0L, null, null);
                MiPushCallback miPushCallback = c;
                if (miPushCallback != null) {
                    miPushCallback.onReceiveRegisterResult(str, generateCommandMessage);
                }
            }
            if (b(context, str)) {
                ig igVar = new ig();
                igVar.b(str2);
                igVar.c(hr.PullOfflineMessage.f67a);
                igVar.a(aj.a());
                igVar.a(false);
                ay.a(context).a(igVar, hh.Notification, false, true, null, false, str, str2);
                b.b("MiPushClient4Hybrid pull offline pass through message");
                a(context, str);
                return;
            }
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - (b.get(str) != null ? b.get(str).longValue() : 0L)) < 5000) {
            b.m149a("MiPushClient4Hybrid  Could not send register message within 5s repeatedly.");
            return;
        }
        b.put(str, Long.valueOf(currentTimeMillis));
        String a3 = az.a(6);
        d.a aVar = new d.a(context);
        aVar.c(str2, str3, a3);
        a.put(str, aVar);
        ih ihVar = new ih();
        ihVar.a(aj.a());
        ihVar.b(str2);
        ihVar.e(str3);
        ihVar.d(str);
        ihVar.f(a3);
        ihVar.c(g.m929a(context, context.getPackageName()));
        ihVar.b(g.a(context, context.getPackageName()));
        ihVar.g("3_6_19");
        ihVar.a(30619);
        ihVar.h(i.e(context));
        ihVar.a(hv.Init);
        if (!l.d()) {
            String g = i.g(context);
            if (!TextUtils.isEmpty(g)) {
                if (l.m1114b()) {
                    ihVar.i(g);
                }
                ihVar.k(az.a(g));
            }
        }
        ihVar.j(i.m1009a());
        int a4 = i.a();
        if (a4 >= 0) {
            ihVar.c(a4);
        }
        ig igVar2 = new ig();
        igVar2.c(hr.HybridRegister.f67a);
        igVar2.b(d.m727a(context).m728a());
        igVar2.d(context.getPackageName());
        igVar2.a(ir.a(ihVar));
        igVar2.a(aj.a());
        ay.a(context).a((ay) igVar2, hh.Notification, (hu) null);
    }

    public static void removeDuplicateCache(Context context, MiPushMessage miPushMessage) {
        String str = miPushMessage.getExtra() != null ? miPushMessage.getExtra().get("jobkey") : null;
        if (TextUtils.isEmpty(str)) {
            str = miPushMessage.getMessageId();
        }
        av.a(context, str);
    }

    public static void reportMessageArrived(Context context, MiPushMessage miPushMessage, boolean z) {
        if (miPushMessage == null || miPushMessage.getExtra() == null) {
            b.m149a("do not ack message, message is null");
            return;
        }
        try {
            hx hxVar = new hx();
            hxVar.b(d.m727a(context).m728a());
            hxVar.a(miPushMessage.getMessageId());
            hxVar.a(Long.valueOf(miPushMessage.getExtra().get(Constants.EXTRA_KEY_HYBRID_MESSAGE_TS)).longValue());
            hxVar.a(a(miPushMessage, z));
            if (!TextUtils.isEmpty(miPushMessage.getTopic())) {
                hxVar.c(miPushMessage.getTopic());
            }
            ay.a(context).a((ay) hxVar, hh.AckMessage, false, PushMessageHelper.generateMessage(miPushMessage));
            b.b("MiPushClient4Hybrid ack mina message, messageId is " + miPushMessage.getMessageId());
        } finally {
            miPushMessage.getExtra().remove(Constants.EXTRA_KEY_HYBRID_MESSAGE_TS);
            miPushMessage.getExtra().remove(Constants.EXTRA_KEY_HYBRID_DEVICE_STATUS);
        }
    }

    public static void reportMessageClicked(Context context, MiPushMessage miPushMessage) {
        MiPushClient.reportMessageClicked(context, miPushMessage);
    }

    public static void setCallback(MiPushCallback miPushCallback) {
        c = miPushCallback;
    }

    public static void unregisterPush(Context context, String str) {
        b.remove(str);
        d.a a2 = d.m727a(context).a(str);
        if (a2 != null) {
            in inVar = new in();
            inVar.a(aj.a());
            inVar.d(str);
            inVar.b(a2.f0a);
            inVar.c(a2.c);
            inVar.e(a2.b);
            ig igVar = new ig();
            igVar.c(hr.HybridUnregister.f67a);
            igVar.b(d.m727a(context).m728a());
            igVar.d(context.getPackageName());
            igVar.a(ir.a(inVar));
            igVar.a(aj.a());
            ay.a(context).a((ay) igVar, hh.Notification, (hu) null);
            d.m727a(context).b(str);
        }
    }

    public static void uploadClearMessageData(Context context, LinkedList<? extends Object> linkedList) {
        z.a(context, linkedList);
    }
}
