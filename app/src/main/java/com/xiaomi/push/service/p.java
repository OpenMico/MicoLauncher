package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.google.android.exoplayer2.PlaybackException;
import com.umeng.analytics.pro.ai;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.db;
import com.xiaomi.push.ew;
import com.xiaomi.push.fg;
import com.xiaomi.push.fy;
import com.xiaomi.push.g;
import com.xiaomi.push.gb;
import com.xiaomi.push.gd;
import com.xiaomi.push.ge;
import com.xiaomi.push.gs;
import com.xiaomi.push.hh;
import com.xiaomi.push.hr;
import com.xiaomi.push.hu;
import com.xiaomi.push.hx;
import com.xiaomi.push.id;
import com.xiaomi.push.ig;
import com.xiaomi.push.ir;
import com.xiaomi.push.service.al;
import com.xiaomi.push.service.z;
import com.xiaomi.push.u;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class p {
    public static Intent a(byte[] bArr, long j) {
        id a = a(bArr);
        if (a == null) {
            return null;
        }
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.putExtra("mipush_payload", bArr);
        intent.putExtra("mrt", Long.toString(j));
        intent.setPackage(a.b);
        return intent;
    }

    public static id a(Context context, id idVar) {
        hx hxVar = new hx();
        hxVar.b(idVar.m1022a());
        hu a = idVar.m1021a();
        if (a != null) {
            hxVar.a(a.m988a());
            hxVar.a(a.m986a());
            if (!TextUtils.isEmpty(a.m993b())) {
                hxVar.c(a.m993b());
            }
        }
        hxVar.a(ir.a(context, idVar));
        id a2 = bq.a(idVar.b(), idVar.m1022a(), hxVar, hh.AckMessage);
        hu a3 = idVar.m1021a().m987a();
        a3.a("mat", Long.toString(System.currentTimeMillis()));
        a2.a(a3);
        return a2;
    }

    public static id a(byte[] bArr) {
        id idVar = new id();
        try {
            ir.a(idVar, bArr);
            return idVar;
        } catch (Throwable th) {
            b.a(th);
            return null;
        }
    }

    private static void a(XMPushService xMPushService, id idVar) {
        xMPushService.a(new bk(4, xMPushService, idVar));
    }

    private static void a(XMPushService xMPushService, id idVar, String str) {
        xMPushService.a(new bo(4, xMPushService, idVar, str));
    }

    private static void a(XMPushService xMPushService, id idVar, String str, String str2) {
        xMPushService.a(new bp(4, xMPushService, idVar, str, str2));
    }

    public static void a(XMPushService xMPushService, String str, byte[] bArr, Intent intent) {
        ew a;
        String b;
        String str2;
        String a2;
        int i;
        String str3;
        ew a3;
        String b2;
        String b3;
        String a4;
        String str4;
        boolean z;
        id a5 = a(bArr);
        hu a6 = a5.m1021a();
        String str5 = null;
        if (bArr != null) {
            db.a(a5.b(), xMPushService.getApplicationContext(), null, a5.a(), bArr.length);
        }
        if (c(a5) && a(xMPushService, str)) {
            if (z.e(a5)) {
                ew.a(xMPushService.getApplicationContext()).a(a5.b(), z.b(a5), a6.m988a(), "old message received by new SDK.");
            }
            c(xMPushService, a5);
        } else if (a(a5) && !a(xMPushService, str) && !b(a5)) {
            if (z.e(a5)) {
                ew.a(xMPushService.getApplicationContext()).a(a5.b(), z.b(a5), a6.m988a(), "new message received by old SDK.");
            }
            d(xMPushService, a5);
        } else if ((z.a(a5) && g.m932b((Context) xMPushService, a5.b)) || a(xMPushService, intent)) {
            if (hh.Registration == a5.a()) {
                String b4 = a5.b();
                SharedPreferences.Editor edit = xMPushService.getSharedPreferences("pref_registered_pkg_names", 0).edit();
                edit.putString(b4, a5.f120a);
                edit.commit();
                ew.a(xMPushService.getApplicationContext()).a(b4, "E100003", a6.m988a(), PlaybackException.ERROR_CODE_DRM_CONTENT_ERROR, "receive a register message");
                if (!TextUtils.isEmpty(a6.m988a())) {
                    intent.putExtra("messageId", a6.m988a());
                    intent.putExtra("eventMessageType", 6000);
                }
            }
            if (z.c(a5)) {
                ew.a(xMPushService.getApplicationContext()).a(a5.b(), z.b(a5), a6.m988a(), 1001, System.currentTimeMillis(), "receive notification message ");
                if (!TextUtils.isEmpty(a6.m988a())) {
                    intent.putExtra("messageId", a6.m988a());
                    intent.putExtra("eventMessageType", 1000);
                }
            }
            if (z.m1169b(a5)) {
                ew.a(xMPushService.getApplicationContext()).a(a5.b(), z.b(a5), a6.m988a(), 2001, System.currentTimeMillis(), "receive passThrough message");
                if (!TextUtils.isEmpty(a6.m988a())) {
                    intent.putExtra("messageId", a6.m988a());
                    intent.putExtra("eventMessageType", 2000);
                }
            }
            if (z.a(a5)) {
                ew.a(xMPushService.getApplicationContext()).a(a5.b(), z.b(a5), a6.m988a(), 3001, System.currentTimeMillis(), "receive business message");
                if (!TextUtils.isEmpty(a6.m988a())) {
                    intent.putExtra("messageId", a6.m988a());
                    intent.putExtra("eventMessageType", 3000);
                }
            }
            if (a6 != null && !TextUtils.isEmpty(a6.m996c()) && !TextUtils.isEmpty(a6.d()) && a6.b != 1 && (z.a(a6.m989a()) || !z.m1168a((Context) xMPushService, a5.b))) {
                if (a6 != null) {
                    if (a6.f83a != null) {
                        str5 = a6.f83a.get("jobkey");
                    }
                    if (TextUtils.isEmpty(str5)) {
                        str5 = a6.m988a();
                    }
                    z = ab.a(xMPushService, a5.b, str5);
                } else {
                    z = false;
                }
                if (z) {
                    ew.a(xMPushService.getApplicationContext()).c(a5.b(), z.b(a5), a6.m988a(), "drop a duplicate message");
                    b.m149a("drop a duplicate message, key=" + str5);
                } else {
                    z.c a7 = z.a(xMPushService, a5, bArr);
                    if (a7.a > 0 && !TextUtils.isEmpty(a7.f191a)) {
                        gs.a(xMPushService, a7.f191a, a7.a, true, false, System.currentTimeMillis());
                    }
                    if (!z.a(a5)) {
                        Intent intent2 = new Intent("com.xiaomi.mipush.MESSAGE_ARRIVED");
                        intent2.putExtra("mipush_payload", bArr);
                        intent2.setPackage(a5.b);
                        try {
                            List<ResolveInfo> queryBroadcastReceivers = xMPushService.getPackageManager().queryBroadcastReceivers(intent2, 0);
                            if (queryBroadcastReceivers != null && !queryBroadcastReceivers.isEmpty()) {
                                xMPushService.sendBroadcast(intent2, bq.a(a5.b));
                            }
                        } catch (Exception e) {
                            xMPushService.sendBroadcast(intent2, bq.a(a5.b));
                            ew.a(xMPushService.getApplicationContext()).b(a5.b(), z.b(a5), a6.m988a(), e.getMessage());
                        }
                    }
                }
                b(xMPushService, a5);
            } else if ("com.xiaomi.xmsf".contains(a5.b) && !a5.m1029b() && a6 != null && a6.m989a() != null && a6.m989a().containsKey("ab")) {
                b(xMPushService, a5);
                b.c("receive abtest message. ack it." + a6.m988a());
            } else if (a(xMPushService, str, a5, a6)) {
                if (a6 != null && !TextUtils.isEmpty(a6.m988a())) {
                    if (z.m1169b(a5)) {
                        a = ew.a(xMPushService.getApplicationContext());
                        b = a5.b();
                        str2 = z.b(a5);
                        a2 = a6.m988a();
                        i = PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT;
                        str3 = "try send passThrough message Broadcast";
                    } else {
                        if (z.a(a5)) {
                            a3 = ew.a(xMPushService.getApplicationContext());
                            b2 = a5.b();
                            b3 = z.b(a5);
                            a4 = a6.m988a();
                            str4 = "try show awake message , but it don't show in foreground";
                        } else if (z.c(a5)) {
                            a3 = ew.a(xMPushService.getApplicationContext());
                            b2 = a5.b();
                            b3 = z.b(a5);
                            a4 = a6.m988a();
                            str4 = "try show notification message , but it don't show in foreground";
                        } else if (z.d(a5)) {
                            a = ew.a(xMPushService.getApplicationContext());
                            b = a5.b();
                            str2 = "E100003";
                            a2 = a6.m988a();
                            i = PlaybackException.ERROR_CODE_DRM_LICENSE_ACQUISITION_FAILED;
                            str3 = "try send register broadcast";
                        }
                        a3.a(b2, b3, a4, str4);
                    }
                    a.a(b, str2, a2, i, str3);
                }
                xMPushService.sendBroadcast(intent, bq.a(a5.b));
            } else {
                ew.a(xMPushService.getApplicationContext()).a(a5.b(), z.b(a5), a6.m988a(), "passThough message: not permit to send broadcast ");
            }
            if (a5.a() == hh.UnRegistration && !"com.xiaomi.xmsf".equals(xMPushService.getPackageName())) {
                xMPushService.stopSelf();
            }
        } else if (!g.m932b((Context) xMPushService, a5.b)) {
            if (z.e(a5)) {
                ew.a(xMPushService.getApplicationContext()).b(a5.b(), z.b(a5), a6.m988a(), "receive a message, but the package is removed.");
            }
            a(xMPushService, a5);
        } else {
            b.m149a("receive a mipush message, we can see the app, but we can't see the receiver.");
            if (z.e(a5)) {
                ew.a(xMPushService.getApplicationContext()).b(a5.b(), z.b(a5), a6.m988a(), "receive a mipush message, we can see the app, but we can't see the receiver.");
            }
        }
    }

    private static void a(XMPushService xMPushService, byte[] bArr, long j) {
        Map<String, String> a;
        id a2 = a(bArr);
        if (a2 != null) {
            if (TextUtils.isEmpty(a2.b)) {
                b.m149a("receive a mipush message without package name");
                return;
            }
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            Intent a3 = a(bArr, valueOf.longValue());
            String f = z.f(a2);
            gs.a(xMPushService, f, j, true, true, System.currentTimeMillis());
            hu a4 = a2.m1021a();
            if (a4 != null) {
                a4.a("mrt", Long.toString(valueOf.longValue()));
            }
            if (hh.SendMessage == a2.a() && m.a(xMPushService).m1161a(a2.b) && !z.a(a2)) {
                String str = "";
                if (a4 != null) {
                    str = a4.m988a();
                    if (z.e(a2)) {
                        ew.a(xMPushService.getApplicationContext()).a(a2.b(), z.b(a2), str, "Drop a message for unregistered");
                    }
                }
                b.m149a("Drop a message for unregistered, msgid=" + str);
                a(xMPushService, a2, a2.b);
            } else if (hh.SendMessage == a2.a() && m.a(xMPushService).m1163c(a2.b) && !z.a(a2)) {
                String str2 = "";
                if (a4 != null) {
                    str2 = a4.m988a();
                    if (z.e(a2)) {
                        ew.a(xMPushService.getApplicationContext()).a(a2.b(), z.b(a2), str2, "Drop a message for push closed");
                    }
                }
                b.m149a("Drop a message for push closed, msgid=" + str2);
                a(xMPushService, a2, a2.b);
            } else if (hh.SendMessage != a2.a() || TextUtils.equals(xMPushService.getPackageName(), "com.xiaomi.xmsf") || TextUtils.equals(xMPushService.getPackageName(), a2.b)) {
                if (!(a4 == null || a4.m988a() == null)) {
                    b.m149a(String.format("receive a message, appid=%1$s, msgid= %2$s", a2.m1022a(), a4.m988a()));
                }
                if (a4 == null || (a = a4.m989a()) == null || !a.containsKey("hide") || !"true".equalsIgnoreCase(a.get("hide"))) {
                    if (!(a4 == null || a4.m989a() == null || !a4.m989a().containsKey("__miid"))) {
                        String str3 = a4.m989a().get("__miid");
                        String a5 = u.a(xMPushService.getApplicationContext());
                        if (TextUtils.isEmpty(a5) || !TextUtils.equals(str3, a5)) {
                            if (z.e(a2)) {
                                ew.a(xMPushService.getApplicationContext()).a(a2.b(), z.b(a2), a4.m988a(), "miid already logout or anther already login");
                            }
                            b.m149a(str3 + " should be login, but got " + a5);
                            a(xMPushService, a2, "miid already logout or anther already login", str3 + " should be login, but got " + a5);
                            return;
                        }
                    }
                    a(xMPushService, f, bArr, a3);
                    return;
                }
                b(xMPushService, a2);
            } else {
                b.m149a("Receive a message with wrong package name, expect " + xMPushService.getPackageName() + ", received " + a2.b);
                a(xMPushService, a2, "unmatched_package", "package should be " + xMPushService.getPackageName() + ", but got " + a2.b);
                if (a4 != null && z.e(a2)) {
                    ew.a(xMPushService.getApplicationContext()).a(a2.b(), z.b(a2), a4.m988a(), "Receive a message with wrong package name");
                }
            }
        }
    }

    private static boolean a(Context context, Intent intent) {
        try {
            List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 32);
            if (queryBroadcastReceivers != null) {
                if (!queryBroadcastReceivers.isEmpty()) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    private static boolean a(Context context, String str) {
        Intent intent = new Intent("com.xiaomi.mipush.miui.CLICK_MESSAGE");
        intent.setPackage(str);
        Intent intent2 = new Intent("com.xiaomi.mipush.miui.RECEIVE_MESSAGE");
        intent2.setPackage(str);
        PackageManager packageManager = context.getPackageManager();
        try {
            List<ResolveInfo> queryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent2, 32);
            List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 32);
            if (queryBroadcastReceivers.isEmpty()) {
                if (queryIntentServices.isEmpty()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            b.a(e);
            return false;
        }
    }

    private static boolean a(id idVar) {
        return "com.xiaomi.xmsf".equals(idVar.b) && idVar.m1021a() != null && idVar.m1021a().m989a() != null && idVar.m1021a().m989a().containsKey("miui_package_name");
    }

    private static boolean a(XMPushService xMPushService, String str, id idVar, hu huVar) {
        boolean z = true;
        if (huVar != null && huVar.m989a() != null && huVar.m989a().containsKey("__check_alive") && huVar.m989a().containsKey("__awake")) {
            ig igVar = new ig();
            igVar.b(idVar.m1022a());
            igVar.d(str);
            igVar.c(hr.AwakeSystemApp.f67a);
            igVar.a(huVar.m988a());
            igVar.f129a = new HashMap();
            boolean a = g.m931a(xMPushService.getApplicationContext(), str);
            igVar.f129a.put("app_running", Boolean.toString(a));
            if (!a) {
                boolean parseBoolean = Boolean.parseBoolean(huVar.m989a().get("__awake"));
                igVar.f129a.put("awaked", Boolean.toString(parseBoolean));
                if (!parseBoolean) {
                    z = false;
                }
            }
            try {
                bq.a(xMPushService, bq.a(idVar.b(), idVar.m1022a(), igVar, hh.Notification));
            } catch (fy e) {
                b.a(e);
            }
        }
        return z;
    }

    private static void b(XMPushService xMPushService, id idVar) {
        xMPushService.a(new bl(4, xMPushService, idVar));
    }

    private static boolean b(id idVar) {
        Map<String, String> a = idVar.m1021a().m989a();
        return a != null && a.containsKey("notify_effect");
    }

    private static void c(XMPushService xMPushService, id idVar) {
        xMPushService.a(new bm(4, xMPushService, idVar));
    }

    private static boolean c(id idVar) {
        if (idVar.m1021a() == null || idVar.m1021a().m989a() == null) {
            return false;
        }
        return "1".equals(idVar.m1021a().m989a().get("obslete_ads_message"));
    }

    private static void d(XMPushService xMPushService, id idVar) {
        xMPushService.a(new bn(4, xMPushService, idVar));
    }

    public void a(Context context, al.b bVar, boolean z, int i, String str) {
        k a;
        if (!z && (a = l.a(context)) != null && "token-expired".equals(str)) {
            try {
                l.a(context, a.f, a.d, a.e);
            } catch (IOException | JSONException e) {
                b.a(e);
            }
        }
    }

    public void a(XMPushService xMPushService, fg fgVar, al.b bVar) {
        try {
            a(xMPushService, fgVar.m911a(bVar.h), fgVar.c());
        } catch (IllegalArgumentException e) {
            b.a(e);
        }
    }

    public void a(XMPushService xMPushService, ge geVar, al.b bVar) {
        if (geVar instanceof gd) {
            gd gdVar = (gd) geVar;
            gb a = gdVar.a(ai.az);
            if (a != null) {
                try {
                    a(xMPushService, au.a(au.a(bVar.h, gdVar.j()), a.c()), gs.a(geVar.m939a()));
                } catch (IllegalArgumentException e) {
                    b.a(e);
                }
            }
        } else {
            b.m149a("not a mipush message");
        }
    }
}
