package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.text.TextUtils;
import com.blankj.utilcode.constant.CacheConstants;
import com.umeng.analytics.pro.c;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.clientreport.data.Config;
import com.xiaomi.clientreport.manager.ClientReportClient;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mipush.sdk.MiTinyDataClient;
import com.xiaomi.push.az;
import com.xiaomi.push.dm;
import com.xiaomi.push.et;
import com.xiaomi.push.eu;
import com.xiaomi.push.ev;
import com.xiaomi.push.fb;
import com.xiaomi.push.g;
import com.xiaomi.push.hh;
import com.xiaomi.push.hm;
import com.xiaomi.push.hr;
import com.xiaomi.push.hu;
import com.xiaomi.push.hv;
import com.xiaomi.push.i;
import com.xiaomi.push.ib;
import com.xiaomi.push.ig;
import com.xiaomi.push.ih;
import com.xiaomi.push.il;
import com.xiaomi.push.in;
import com.xiaomi.push.ip;
import com.xiaomi.push.l;
import com.xiaomi.push.o;
import com.xiaomi.push.s;
import com.xiaomi.push.service.ag;
import com.xiaomi.push.service.aj;
import com.xiaomi.push.service.receivers.NetworkStatusReceiver;
import com.xiaomi.push.u;
import com.xiaomi.smarthome.library.common.util.DateTimeHelper;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public abstract class MiPushClient {
    public static final String COMMAND_REGISTER = "register";
    public static final String COMMAND_SET_ACCEPT_TIME = "accept-time";
    public static final String COMMAND_SET_ACCOUNT = "set-account";
    public static final String COMMAND_SET_ALIAS = "set-alias";
    public static final String COMMAND_SUBSCRIBE_TOPIC = "subscribe-topic";
    public static final String COMMAND_UNREGISTER = "unregister";
    public static final String COMMAND_UNSET_ACCOUNT = "unset-account";
    public static final String COMMAND_UNSET_ALIAS = "unset-alias";
    public static final String COMMAND_UNSUBSCRIBE_TOPIC = "unsubscibe-topic";
    public static final String PREF_EXTRA = "mipush_extra";
    private static boolean a = false;
    private static Context b;
    private static long c = System.currentTimeMillis();

    @Deprecated
    /* loaded from: classes4.dex */
    public static abstract class MiPushClientCallback {
        private String a;

        public String getCategory() {
            return this.a;
        }

        public void onCommandResult(String str, long j, String str2, List<String> list) {
        }

        public void onInitializeResult(long j, String str, String str2) {
        }

        public void onReceiveMessage(MiPushMessage miPushMessage) {
        }

        public void onReceiveMessage(String str, String str2, String str3, boolean z) {
        }

        public void onSubscribeResult(long j, String str, String str2) {
        }

        public void onUnsubscribeResult(long j, String str, String str2) {
        }

        protected void setCategory(String str) {
            this.a = str;
        }
    }

    public static synchronized void a(Context context) {
        synchronized (MiPushClient.class) {
            for (String str : getAllAlias(context)) {
                b(context, str);
            }
        }
    }

    public static void a(Context context, hv hvVar) {
        if (d.m727a(context).m735c()) {
            String a2 = az.a(6);
            String a3 = d.m727a(context).m728a();
            String b2 = d.m727a(context).b();
            d.m727a(context).m729a();
            d.m727a(context).a(Constants.a());
            d.m727a(context).a(a3, b2, a2);
            ih ihVar = new ih();
            ihVar.a(aj.a());
            ihVar.b(a3);
            ihVar.e(b2);
            ihVar.f(a2);
            ihVar.d(context.getPackageName());
            ihVar.c(g.m929a(context, context.getPackageName()));
            ihVar.a(hvVar);
            ay.a(context).a(ihVar, false);
        }
    }

    public static synchronized void a(Context context, String str) {
        synchronized (MiPushClient.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            edit.putLong("alias_" + str, System.currentTimeMillis()).commit();
        }
    }

    public static void a(Context context, String str, hu huVar, String str2) {
        ig igVar = new ig();
        if (TextUtils.isEmpty(str2)) {
            if (d.m727a(context).m734b()) {
                str2 = d.m727a(context).m728a();
            } else {
                b.d("do not report clicked message");
                return;
            }
        }
        igVar.b(str2);
        igVar.c("bar:click");
        igVar.a(str);
        igVar.a(false);
        ay.a(context).a((ay) igVar, hh.Notification, false, huVar);
    }

    public static void a(Context context, String str, hu huVar, String str2, String str3) {
        ig igVar = new ig();
        if (TextUtils.isEmpty(str3)) {
            b.d("do not report clicked message");
            return;
        }
        igVar.b(str3);
        igVar.c("bar:click");
        igVar.a(str);
        igVar.a(false);
        ay.a(context).a(igVar, hh.Notification, false, true, huVar, true, str2, str3);
    }

    public static synchronized void a(Context context, String str, String str2) {
        synchronized (MiPushClient.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            edit.putString(Constants.EXTRA_KEY_ACCEPT_TIME, str + Constants.ACCEPT_TIME_SEPARATOR_SP + str2);
            s.a(edit);
        }
    }

    private static void a(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException("param " + str + " is not nullable");
        }
    }

    public static long accountSetTime(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        return sharedPreferences.getLong("account_" + str, -1L);
    }

    public static long aliasSetTime(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        return sharedPreferences.getLong("alias_" + str, -1L);
    }

    public static void awakeApps(Context context, String[] strArr) {
        com.xiaomi.push.aj.a(context).a(new r(strArr, context));
    }

    private static void b() {
        com.xiaomi.push.aj.a(b).a(new an(b), ag.a(b).a(hm.OcVersionCheckFrequency.a(), CacheConstants.DAY), 5);
    }

    public static synchronized void b(Context context) {
        synchronized (MiPushClient.class) {
            for (String str : getAllUserAccount(context)) {
                d(context, str);
            }
        }
    }

    public static void b(Context context, PackageInfo packageInfo) {
        ServiceInfo[] serviceInfoArr = packageInfo.services;
        if (serviceInfoArr != null) {
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                if (serviceInfo.exported && serviceInfo.enabled && "com.xiaomi.mipush.sdk.PushMessageHandler".equals(serviceInfo.name) && !context.getPackageName().equals(serviceInfo.packageName)) {
                    try {
                        Thread.sleep(((long) ((Math.random() * 2.0d) + 1.0d)) * 1000);
                        Intent intent = new Intent();
                        intent.setClassName(serviceInfo.packageName, serviceInfo.name);
                        intent.setAction("com.xiaomi.mipush.sdk.WAKEUP");
                        intent.putExtra("waker_pkgname", context.getPackageName());
                        PushMessageHandler.a(context, intent);
                        return;
                    } catch (Throwable unused) {
                        return;
                    }
                }
            }
        }
    }

    public static synchronized void b(Context context, String str) {
        synchronized (MiPushClient.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            edit.remove("alias_" + str).commit();
        }
    }

    private static boolean b(Context context, String str, String str2) {
        String acceptTime = getAcceptTime(context);
        return TextUtils.equals(acceptTime, str + Constants.ACCEPT_TIME_SEPARATOR_SP + str2);
    }

    public static synchronized void c(Context context) {
        synchronized (MiPushClient.class) {
            for (String str : getAllTopic(context)) {
                f(context, str);
            }
        }
    }

    public static synchronized void c(Context context, String str) {
        synchronized (MiPushClient.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            edit.putLong("account_" + str, System.currentTimeMillis()).commit();
        }
    }

    private static boolean c() {
        return l.m1114b();
    }

    public static void clearExtras(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
        edit.clear();
        edit.commit();
    }

    public static void clearLocalNotificationType(Context context) {
        ay.a(context).e();
    }

    public static void clearNotification(Context context) {
        ay.a(context).a(-1);
    }

    public static void clearNotification(Context context, int i) {
        ay.a(context).a(i);
    }

    public static void clearNotification(Context context, String str, String str2) {
        ay.a(context).a(str, str2);
    }

    private static void d() {
        new Thread(new q()).start();
    }

    public static synchronized void d(Context context) {
        synchronized (MiPushClient.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            edit.remove(Constants.EXTRA_KEY_ACCEPT_TIME);
            s.a(edit);
        }
    }

    public static synchronized void d(Context context, String str) {
        synchronized (MiPushClient.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            edit.remove("account_" + str).commit();
        }
    }

    public static void disablePush(Context context) {
        ay.a(context).a(true);
    }

    private static void e() {
        boolean a2 = ag.a(b).a(hm.ForceHandleCrashSwitch.a(), false);
        if (!a && a2) {
            Thread.setDefaultUncaughtExceptionHandler(new bc(b));
        }
    }

    public static synchronized void e(Context context, String str) {
        synchronized (MiPushClient.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            edit.putLong("topic_" + str, System.currentTimeMillis()).commit();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003d, code lost:
        if (com.xiaomi.push.n.a(r5, "android.permission.WRITE_EXTERNAL_STORAGE") == false) goto L_0x0055;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0052, code lost:
        if (android.text.TextUtils.isEmpty(r2) != false) goto L_0x0055;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean e(android.content.Context r5) {
        /*
            r0 = 1
            if (r5 == 0) goto L_0x0055
            boolean r1 = com.xiaomi.push.l.m1113a()
            if (r1 != 0) goto L_0x0056
            java.lang.String r1 = "com.xiaomi.xmsf"
            java.lang.String r2 = r5.getPackageName()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0016
            goto L_0x0056
        L_0x0016:
            java.lang.String r1 = com.xiaomi.push.i.b(r5)
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0021
            goto L_0x0056
        L_0x0021:
            android.content.pm.ApplicationInfo r1 = r5.getApplicationInfo()
            int r1 = r1.targetSdkVersion
            r2 = 23
            if (r1 < r2) goto L_0x0040
            int r1 = android.os.Build.VERSION.SDK_INT
            if (r1 < r2) goto L_0x0040
            java.lang.String r1 = "android.permission.READ_PHONE_STATE"
            boolean r1 = com.xiaomi.push.n.a(r5, r1)
            if (r1 != 0) goto L_0x0056
            java.lang.String r1 = "android.permission.WRITE_EXTERNAL_STORAGE"
            boolean r1 = com.xiaomi.push.n.a(r5, r1)
            if (r1 == 0) goto L_0x0055
            goto L_0x0056
        L_0x0040:
            java.lang.String r1 = com.xiaomi.push.i.f(r5)
            java.lang.String r2 = com.xiaomi.push.i.m1009a()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x0056
            boolean r1 = android.text.TextUtils.isEmpty(r2)
            if (r1 != 0) goto L_0x0055
            goto L_0x0056
        L_0x0055:
            r0 = 0
        L_0x0056:
            if (r0 != 0) goto L_0x00b1
            java.lang.String r1 = "Because of lack of necessary information, mi push can't be initialized"
            com.xiaomi.channel.commonutils.logger.b.d(r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.lang.String r2 = "android.permission.READ_PHONE_STATE"
            boolean r2 = com.xiaomi.push.n.a(r5, r2)
            if (r2 != 0) goto L_0x006f
            java.lang.String r2 = "android.permission.READ_PHONE_STATE"
            r1.add(r2)
        L_0x006f:
            java.lang.String r2 = "android.permission.WRITE_EXTERNAL_STORAGE"
            boolean r2 = com.xiaomi.push.n.a(r5, r2)
            if (r2 != 0) goto L_0x007c
            java.lang.String r2 = "android.permission.WRITE_EXTERNAL_STORAGE"
            r1.add(r2)
        L_0x007c:
            boolean r2 = r1.isEmpty()
            if (r2 != 0) goto L_0x00b1
            int r2 = r1.size()
            java.lang.String[] r2 = new java.lang.String[r2]
            r1.toArray(r2)
            android.content.Intent r1 = new android.content.Intent
            r1.<init>()
            java.lang.String r3 = "com.xiaomi.mipush.ERROR"
            r1.setAction(r3)
            java.lang.String r3 = r5.getPackageName()
            r1.setPackage(r3)
            java.lang.String r3 = "message_type"
            r4 = 5
            r1.putExtra(r3, r4)
            java.lang.String r3 = "error_type"
            java.lang.String r4 = "error_lack_of_permission"
            r1.putExtra(r3, r4)
            java.lang.String r3 = "error_message"
            r1.putExtra(r3, r2)
            r5.sendBroadcast(r1)
        L_0x00b1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.MiPushClient.e(android.content.Context):boolean");
    }

    public static void enablePush(Context context) {
        ay.a(context).a(false);
    }

    private static void f(Context context) {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            intentFilter.addCategory("android.intent.category.DEFAULT");
            context.getApplicationContext().registerReceiver(new NetworkStatusReceiver(null), intentFilter);
        } catch (Throwable th) {
            b.a(th);
        }
    }

    public static synchronized void f(Context context, String str) {
        synchronized (MiPushClient.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            edit.remove("topic_" + str).commit();
        }
    }

    private static void g(Context context) {
        if (ag.a(b).a(hm.DataCollectionSwitch.a(), c())) {
            dm.a().a(new s(context));
            com.xiaomi.push.aj.a(b).a(new n(), 10);
        }
    }

    public static String getAcceptTime(Context context) {
        return context.getSharedPreferences("mipush_extra", 0).getString(Constants.EXTRA_KEY_ACCEPT_TIME, "00:00-23:59");
    }

    public static List<String> getAllAlias(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("alias_")) {
                arrayList.add(str.substring(6));
            }
        }
        return arrayList;
    }

    public static List<String> getAllTopic(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("topic_") && !str.contains("**ALL**")) {
                arrayList.add(str.substring(6));
            }
        }
        return arrayList;
    }

    public static List<String> getAllUserAccount(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("account_")) {
                arrayList.add(str.substring(8));
            }
        }
        return arrayList;
    }

    public static String getAppRegion(Context context) {
        if (d.m727a(context).m735c()) {
            return d.m727a(context).f();
        }
        return null;
    }

    public static boolean getOpenFCMPush(Context context) {
        a((Object) context, c.R);
        return g.a(context).b(f.ASSEMBLE_PUSH_FCM);
    }

    public static boolean getOpenHmsPush(Context context) {
        a((Object) context, c.R);
        return g.a(context).b(f.ASSEMBLE_PUSH_HUAWEI);
    }

    public static boolean getOpenOPPOPush(Context context) {
        a((Object) context, c.R);
        return g.a(context).b(f.ASSEMBLE_PUSH_COS);
    }

    public static boolean getOpenVIVOPush(Context context) {
        return g.a(context).b(f.ASSEMBLE_PUSH_FTOS);
    }

    public static String getRegId(Context context) {
        if (d.m727a(context).m735c()) {
            return d.m727a(context).c();
        }
        return null;
    }

    private static void h(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
        edit.putLong("last_pull_notification", System.currentTimeMillis());
        s.a(edit);
    }

    private static boolean i(Context context) {
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_pull_notification", -1L)) > 300000;
    }

    @Deprecated
    public static void initialize(Context context, String str, String str2, MiPushClientCallback miPushClientCallback) {
        try {
            b.m149a("sdk_version = 3_6_19");
            if (miPushClientCallback != null) {
                PushMessageHandler.a(miPushClientCallback);
            }
            if (u.m1174a(b)) {
                z.a(b);
            }
            if (d.m727a(b).a(str, str2) || e(b)) {
                boolean z = d.m727a(b).a() != Constants.a();
                if (z || k(b)) {
                    if (z || !d.m727a(b).a(str, str2) || d.m727a(b).m737e()) {
                        String a2 = az.a(6);
                        d.m727a(b).m729a();
                        d.m727a(b).a(Constants.a());
                        d.m727a(b).a(str, str2, a2);
                        MiTinyDataClient.a.a().b(MiTinyDataClient.PENDING_REASON_APPID);
                        clearExtras(b);
                        ih ihVar = new ih();
                        ihVar.a(aj.a());
                        ihVar.b(str);
                        ihVar.e(str2);
                        ihVar.d(b.getPackageName());
                        ihVar.f(a2);
                        ihVar.c(g.m929a(b, b.getPackageName()));
                        ihVar.b(g.a(b, b.getPackageName()));
                        ihVar.g("3_6_19");
                        ihVar.a(30619);
                        ihVar.h(i.e(b));
                        ihVar.a(hv.Init);
                        if (!l.d()) {
                            String g = i.g(b);
                            String i = i.i(b);
                            if (!TextUtils.isEmpty(g)) {
                                if (l.m1114b()) {
                                    if (!TextUtils.isEmpty(i)) {
                                        g = g + Constants.ACCEPT_TIME_SEPARATOR_SP + i;
                                    }
                                    ihVar.i(g);
                                }
                                ihVar.k(az.a(g) + Constants.ACCEPT_TIME_SEPARATOR_SP + i.j(b));
                            }
                        }
                        ihVar.j(i.m1009a());
                        int a3 = i.a();
                        if (a3 >= 0) {
                            ihVar.c(a3);
                        }
                        ay.a(b).a(ihVar, z);
                        b.a(b);
                        b.getSharedPreferences("mipush_extra", 4).getBoolean("mipush_registed", true);
                    } else {
                        if (1 == PushMessageHelper.getPushMode(b)) {
                            a(miPushClientCallback, "callback");
                            miPushClientCallback.onInitializeResult(0L, null, d.m727a(b).c());
                        } else {
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(d.m727a(b).c());
                            PushMessageHelper.sendCommandMessageBroadcast(b, PushMessageHelper.generateCommandMessage(fb.COMMAND_REGISTER.f26a, arrayList, 0L, null, null));
                        }
                        ay.a(b).a();
                        if (d.m727a(b).m731a()) {
                            ig igVar = new ig();
                            igVar.b(d.m727a(b).m728a());
                            igVar.c("client_info_update");
                            igVar.a(aj.a());
                            igVar.f129a = new HashMap();
                            igVar.f129a.put("app_version", g.m929a(b, b.getPackageName()));
                            igVar.f129a.put(Constants.EXTRA_KEY_APP_VERSION_CODE, Integer.toString(g.a(b, b.getPackageName())));
                            igVar.f129a.put("push_sdk_vn", "3_6_19");
                            igVar.f129a.put("push_sdk_vc", Integer.toString(30619));
                            String e = d.m727a(b).e();
                            if (!TextUtils.isEmpty(e)) {
                                igVar.f129a.put(MicoSettings.Secure.DEVICE_ID, e);
                            }
                            ay.a(b).a((ay) igVar, hh.Notification, false, (hu) null);
                            b.a(b);
                        }
                        if (!o.m1115a(b, "update_devId", false)) {
                            d();
                            o.a(b, "update_devId", true);
                        }
                        String d = i.d(b);
                        if (!TextUtils.isEmpty(d)) {
                            ib ibVar = new ib();
                            ibVar.a(aj.a());
                            ibVar.b(str);
                            ibVar.c(fb.COMMAND_CHK_VDEVID.f26a);
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(i.c(b));
                            arrayList2.add(d);
                            arrayList2.add(Build.MODEL != null ? Build.MODEL : "");
                            arrayList2.add(Build.BOARD != null ? Build.BOARD : "");
                            ibVar.a(arrayList2);
                            ay.a(b).a((ay) ibVar, hh.Command, false, (hu) null);
                        }
                        if (shouldUseMIUIPush(b) && i(b)) {
                            ig igVar2 = new ig();
                            igVar2.b(d.m727a(b).m728a());
                            igVar2.c(hr.PullOfflineMessage.f67a);
                            igVar2.a(aj.a());
                            igVar2.a(false);
                            ay.a(b).a((ay) igVar2, hh.Notification, false, (hu) null, false);
                            h(b);
                        }
                    }
                    j(b);
                    b();
                    g(b);
                    l(b);
                    be.a(b);
                    e();
                    if (!b.getPackageName().equals("com.xiaomi.xmsf")) {
                        if (Logger.getUserLogger() != null) {
                            Logger.setLogger(b, Logger.getUserLogger());
                        }
                        b.a(2);
                    }
                    m(context);
                    return;
                }
                ay.a(b).a();
                b.m149a("Could not send  register message within 5s repeatly .");
            }
        } catch (Throwable th) {
            b.a(th);
        }
    }

    private static void j(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
        edit.putLong("last_reg_request", System.currentTimeMillis());
        s.a(edit);
    }

    private static boolean k(Context context) {
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_reg_request", -1L)) > 5000;
    }

    private static void l(Context context) {
        ev.a(new w());
        Config a2 = ev.a(context);
        ClientReportClient.init(context, a2, new et(context), new eu(context));
        a.a(context);
        t.a(context, a2);
        ag.a(context).a(new x(100, "perf event job update", context));
    }

    private static void m(Context context) {
        if ("syncing".equals(ao.a(b).a(bd.DISABLE_PUSH))) {
            disablePush(b);
        }
        if ("syncing".equals(ao.a(b).a(bd.ENABLE_PUSH))) {
            enablePush(b);
        }
        if ("syncing".equals(ao.a(b).a(bd.UPLOAD_HUAWEI_TOKEN))) {
            syncAssemblePushToken(b);
        }
        if ("syncing".equals(ao.a(b).a(bd.UPLOAD_FCM_TOKEN))) {
            syncAssembleFCMPushToken(b);
        }
        if ("syncing".equals(ao.a(b).a(bd.UPLOAD_COS_TOKEN))) {
            syncAssembleCOSPushToken(context);
        }
        if ("syncing".equals(ao.a(b).a(bd.UPLOAD_FTOS_TOKEN))) {
            syncAssembleFTOSPushToken(context);
        }
    }

    public static void pausePush(Context context, String str) {
        setAcceptTime(context, 0, 0, 0, 0, str);
    }

    public static void registerCrashHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        Thread.setDefaultUncaughtExceptionHandler(new bc(b, uncaughtExceptionHandler));
        a = true;
    }

    public static void registerPush(Context context, String str, String str2) {
        registerPush(context, str, str2, new PushConfiguration());
    }

    public static void registerPush(Context context, String str, String str2, PushConfiguration pushConfiguration) {
        a((Object) context, c.R);
        a(str, "appID");
        a(str2, "appToken");
        b = context.getApplicationContext();
        if (b == null) {
            b = context;
        }
        Context context2 = b;
        u.m1172a(context2);
        if (!NetworkStatusReceiver.a()) {
            f(b);
        }
        g.a(b).a(pushConfiguration);
        b.a();
        com.xiaomi.push.aj.a(context2).a(new l(str, str2));
    }

    public static void reportAppRunInBackground(Context context, boolean z) {
        if (d.m727a(context).m734b()) {
            hr hrVar = z ? hr.APP_SLEEP : hr.APP_WAKEUP;
            ig igVar = new ig();
            igVar.b(d.m727a(context).m728a());
            igVar.c(hrVar.f67a);
            igVar.d(context.getPackageName());
            igVar.a(aj.a());
            igVar.a(false);
            ay.a(context).a((ay) igVar, hh.Notification, false, (hu) null, false);
        }
    }

    public static void reportMessageClicked(Context context, MiPushMessage miPushMessage) {
        hu huVar = new hu();
        huVar.a(miPushMessage.getMessageId());
        huVar.b(miPushMessage.getTopic());
        huVar.d(miPushMessage.getDescription());
        huVar.c(miPushMessage.getTitle());
        huVar.c(miPushMessage.getNotifyId());
        huVar.a(miPushMessage.getNotifyType());
        huVar.b(miPushMessage.getPassThrough());
        huVar.a(miPushMessage.getExtra());
        a(context, miPushMessage.getMessageId(), huVar, null);
    }

    @Deprecated
    public static void reportMessageClicked(Context context, String str) {
        a(context, str, null, null);
    }

    public static void resumePush(Context context, String str) {
        setAcceptTime(context, 0, 0, 23, 59, str);
    }

    public static void setAcceptTime(Context context, int i, int i2, int i3, int i4, String str) {
        if (i < 0 || i >= 24 || i3 < 0 || i3 >= 24 || i2 < 0 || i2 >= 60 || i4 < 0 || i4 >= 60) {
            throw new IllegalArgumentException("the input parameter is not valid.");
        }
        long rawOffset = ((TimeZone.getTimeZone("GMT+08").getRawOffset() - TimeZone.getDefault().getRawOffset()) / 1000) / 60;
        long j = ((((i * 60) + i2) + rawOffset) + DateTimeHelper.sDayInMinutes) % DateTimeHelper.sDayInMinutes;
        long j2 = ((((i3 * 60) + i4) + rawOffset) + DateTimeHelper.sDayInMinutes) % DateTimeHelper.sDayInMinutes;
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j / 60), Long.valueOf(j % 60)));
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j2 / 60), Long.valueOf(j2 % 60)));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(String.format("%1$02d:%2$02d", Integer.valueOf(i), Integer.valueOf(i2)));
        arrayList2.add(String.format("%1$02d:%2$02d", Integer.valueOf(i3), Integer.valueOf(i4)));
        if (!b(context, (String) arrayList.get(0), (String) arrayList.get(1))) {
            setCommand(context, fb.COMMAND_SET_ACCEPT_TIME.f26a, arrayList, str);
        } else if (1 == PushMessageHelper.getPushMode(context)) {
            PushMessageHandler.a(context, str, fb.COMMAND_SET_ACCEPT_TIME.f26a, 0L, null, arrayList2);
        } else {
            PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(fb.COMMAND_SET_ACCEPT_TIME.f26a, arrayList2, 0L, null, null));
        }
    }

    public static void setAlias(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            setCommand(context, fb.COMMAND_SET_ALIAS.f26a, str, str2);
        }
    }

    protected static void setCommand(Context context, String str, String str2, String str3) {
        StringBuilder sb;
        String str4;
        fb fbVar;
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
        }
        if (!fb.COMMAND_SET_ALIAS.f26a.equalsIgnoreCase(str) || Math.abs(System.currentTimeMillis() - aliasSetTime(context, str2)) >= 3600000) {
            if (fb.COMMAND_UNSET_ALIAS.f26a.equalsIgnoreCase(str) && aliasSetTime(context, str2) < 0) {
                sb = new StringBuilder();
                str4 = "Don't cancel alias for ";
            } else if (fb.COMMAND_SET_ACCOUNT.f26a.equalsIgnoreCase(str) && Math.abs(System.currentTimeMillis() - accountSetTime(context, str2)) < 3600000) {
                if (1 != PushMessageHelper.getPushMode(context)) {
                    fbVar = fb.COMMAND_SET_ACCOUNT;
                    PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(fbVar.f26a, arrayList, 0L, null, str3));
                    return;
                }
                PushMessageHandler.a(context, str3, str, 0L, null, arrayList);
            } else if (!fb.COMMAND_UNSET_ACCOUNT.f26a.equalsIgnoreCase(str) || accountSetTime(context, str2) >= 0) {
                setCommand(context, str, arrayList, str3);
                return;
            } else {
                sb = new StringBuilder();
                str4 = "Don't cancel account for ";
            }
            sb.append(str4);
            sb.append(az.a(arrayList.toString(), 3));
            sb.append(" is unseted");
            b.m149a(sb.toString());
            return;
        }
        if (1 != PushMessageHelper.getPushMode(context)) {
            fbVar = fb.COMMAND_SET_ALIAS;
            PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(fbVar.f26a, arrayList, 0L, null, str3));
            return;
        }
        PushMessageHandler.a(context, str3, str, 0L, null, arrayList);
    }

    protected static void setCommand(Context context, String str, ArrayList<String> arrayList, String str2) {
        if (!TextUtils.isEmpty(d.m727a(context).m728a())) {
            ib ibVar = new ib();
            ibVar.a(aj.a());
            ibVar.b(d.m727a(context).m728a());
            ibVar.c(str);
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                ibVar.m1013a(it.next());
            }
            ibVar.e(str2);
            ibVar.d(context.getPackageName());
            ay.a(context).a((ay) ibVar, hh.Command, (hu) null);
        }
    }

    public static void setLocalNotificationType(Context context, int i) {
        ay.a(context).b(i & (-1));
    }

    public static void setUserAccount(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            setCommand(context, fb.COMMAND_SET_ACCOUNT.f26a, str, str2);
        }
    }

    public static boolean shouldUseMIUIPush(Context context) {
        return ay.a(context).m724a();
    }

    public static void subscribe(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(d.m727a(context).m728a()) && !TextUtils.isEmpty(str)) {
            if (Math.abs(System.currentTimeMillis() - topicSubscribedTime(context, str)) > 86400000) {
                il ilVar = new il();
                ilVar.a(aj.a());
                ilVar.b(d.m727a(context).m728a());
                ilVar.c(str);
                ilVar.d(context.getPackageName());
                ilVar.e(str2);
                ay.a(context).a((ay) ilVar, hh.Subscription, (hu) null);
            } else if (1 == PushMessageHelper.getPushMode(context)) {
                PushMessageHandler.a(context, str2, 0L, null, str);
            } else {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(fb.COMMAND_SUBSCRIBE_TOPIC.f26a, arrayList, 0L, null, null));
            }
        }
    }

    public static void syncAssembleCOSPushToken(Context context) {
        ay.a(context).a((String) null, bd.UPLOAD_COS_TOKEN, f.ASSEMBLE_PUSH_COS);
    }

    public static void syncAssembleFCMPushToken(Context context) {
        ay.a(context).a((String) null, bd.UPLOAD_FCM_TOKEN, f.ASSEMBLE_PUSH_FCM);
    }

    public static void syncAssembleFTOSPushToken(Context context) {
        ay.a(context).a((String) null, bd.UPLOAD_FTOS_TOKEN, f.ASSEMBLE_PUSH_FTOS);
    }

    public static void syncAssemblePushToken(Context context) {
        ay.a(context).a((String) null, bd.UPLOAD_HUAWEI_TOKEN, f.ASSEMBLE_PUSH_HUAWEI);
    }

    public static long topicSubscribedTime(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        return sharedPreferences.getLong("topic_" + str, -1L);
    }

    public static void unregisterPush(Context context) {
        j.c(context);
        ag.a(context).a();
        if (d.m727a(context).m734b()) {
            in inVar = new in();
            inVar.a(aj.a());
            inVar.b(d.m727a(context).m728a());
            inVar.c(d.m727a(context).c());
            inVar.e(d.m727a(context).b());
            inVar.d(context.getPackageName());
            ay.a(context).a(inVar);
            PushMessageHandler.a();
            d.m727a(context).m733b();
            clearLocalNotificationType(context);
            clearNotification(context);
            clearExtras(context);
        }
    }

    public static void unsetAlias(Context context, String str, String str2) {
        setCommand(context, fb.COMMAND_UNSET_ALIAS.f26a, str, str2);
    }

    public static void unsetUserAccount(Context context, String str, String str2) {
        setCommand(context, fb.COMMAND_UNSET_ACCOUNT.f26a, str, str2);
    }

    public static void unsubscribe(Context context, String str, String str2) {
        if (d.m727a(context).m734b()) {
            if (topicSubscribedTime(context, str) < 0) {
                b.m149a("Don't cancel subscribe for " + str + " is unsubscribed");
                return;
            }
            ip ipVar = new ip();
            ipVar.a(aj.a());
            ipVar.b(d.m727a(context).m728a());
            ipVar.c(str);
            ipVar.d(context.getPackageName());
            ipVar.e(str2);
            ay.a(context).a((ay) ipVar, hh.UnSubscription, (hu) null);
        }
    }
}
