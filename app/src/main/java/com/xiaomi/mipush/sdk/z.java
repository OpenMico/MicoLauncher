package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes4.dex */
public class z {

    /* loaded from: classes4.dex */
    public static class a extends RuntimeException {
        public a(String str) {
            super(str);
        }
    }

    /* loaded from: classes4.dex */
    public static class b {
        public String a;

        /* renamed from: a  reason: collision with other field name */
        public boolean f4a;
        public String b;

        /* renamed from: b  reason: collision with other field name */
        public boolean f5b;

        public b(String str, boolean z, boolean z2, String str2) {
            this.a = str;
            this.f4a = z;
            this.f5b = z2;
            this.b = str2;
        }
    }

    private static ActivityInfo a(PackageManager packageManager, Intent intent, Class<?> cls) {
        for (ResolveInfo resolveInfo : packageManager.queryBroadcastReceivers(intent, 16384)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null && cls.getCanonicalName().equals(activityInfo.name)) {
                return activityInfo;
            }
        }
        return null;
    }

    public static void a(Context context) {
        new Thread(new c(context)).start();
    }

    private static void a(Context context, String str, String str2) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        Intent intent = new Intent(str);
        intent.setPackage(packageName);
        boolean z = false;
        for (ResolveInfo resolveInfo : packageManager.queryBroadcastReceivers(intent, 16384)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo == null || TextUtils.isEmpty(activityInfo.name) || !activityInfo.name.equals(str2)) {
                z = false;
                continue;
            } else {
                z = true;
                continue;
            }
            if (z) {
                break;
            }
        }
        if (!z) {
            throw new a(String.format("<receiver android:name=\"%1$s\" .../> is missing or disabled in AndroidManifest.", str2));
        }
    }

    private static void a(ActivityInfo activityInfo, Boolean[] boolArr) {
        if (boolArr[0].booleanValue() != activityInfo.enabled) {
            throw new a(String.format("<receiver android:name=\"%1$s\" .../> in AndroidManifest had the wrong enabled attribute, which should be android:enabled=%2$b.", activityInfo.name, boolArr[0]));
        } else if (boolArr[1].booleanValue() != activityInfo.exported) {
            throw new a(String.format("<receiver android:name=\"%1$s\" .../> in AndroidManifest had the wrong exported attribute, which should be android:exported=%2$b.", activityInfo.name, boolArr[1]));
        }
    }

    private static boolean a(PackageInfo packageInfo, String[] strArr) {
        for (ServiceInfo serviceInfo : packageInfo.services) {
            if (a(strArr, serviceInfo.name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(String[] strArr, String str) {
        if (!(strArr == null || str == null)) {
            for (String str2 : strArr) {
                if (TextUtils.equals(str2, str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a9 A[EDGE_INSN: B:46:0x00a9->B:30:0x00a9 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0075 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void c(android.content.Context r8) {
        /*
            android.content.pm.PackageManager r0 = r8.getPackageManager()
            java.lang.String r1 = r8.getPackageName()
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r3 = com.xiaomi.push.service.ap.o
            r2.<init>(r3)
            r2.setPackage(r1)
            r3 = 1
            r4 = 0
            java.lang.String r5 = "com.xiaomi.push.service.receivers.PingReceiver"
            java.lang.Class r5 = java.lang.Class.forName(r5)     // Catch: ClassNotFoundException -> 0x005c
            android.content.pm.ActivityInfo r2 = a(r0, r2, r5)     // Catch: ClassNotFoundException -> 0x005c
            boolean r5 = com.xiaomi.mipush.sdk.MiPushClient.shouldUseMIUIPush(r8)     // Catch: ClassNotFoundException -> 0x005c
            r6 = 2
            if (r5 != 0) goto L_0x004b
            if (r2 == 0) goto L_0x0039
            java.lang.Boolean[] r5 = new java.lang.Boolean[r6]     // Catch: ClassNotFoundException -> 0x005c
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r3)     // Catch: ClassNotFoundException -> 0x005c
            r5[r4] = r6     // Catch: ClassNotFoundException -> 0x005c
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)     // Catch: ClassNotFoundException -> 0x005c
            r5[r3] = r6     // Catch: ClassNotFoundException -> 0x005c
        L_0x0035:
            a(r2, r5)     // Catch: ClassNotFoundException -> 0x005c
            goto L_0x0060
        L_0x0039:
            com.xiaomi.mipush.sdk.z$a r2 = new com.xiaomi.mipush.sdk.z$a     // Catch: ClassNotFoundException -> 0x005c
            java.lang.String r5 = "<receiver android:name=\"%1$s\" .../> is missing or disabled in AndroidManifest."
            java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch: ClassNotFoundException -> 0x005c
            java.lang.String r7 = "com.xiaomi.push.service.receivers.PingReceiver"
            r6[r4] = r7     // Catch: ClassNotFoundException -> 0x005c
            java.lang.String r5 = java.lang.String.format(r5, r6)     // Catch: ClassNotFoundException -> 0x005c
            r2.<init>(r5)     // Catch: ClassNotFoundException -> 0x005c
            throw r2     // Catch: ClassNotFoundException -> 0x005c
        L_0x004b:
            if (r2 == 0) goto L_0x0060
            java.lang.Boolean[] r5 = new java.lang.Boolean[r6]     // Catch: ClassNotFoundException -> 0x005c
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r3)     // Catch: ClassNotFoundException -> 0x005c
            r5[r4] = r6     // Catch: ClassNotFoundException -> 0x005c
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)     // Catch: ClassNotFoundException -> 0x005c
            r5[r3] = r6     // Catch: ClassNotFoundException -> 0x005c
            goto L_0x0035
        L_0x005c:
            r2 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r2)
        L_0x0060:
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r5 = "com.xiaomi.mipush.RECEIVE_MESSAGE"
            r2.<init>(r5)
            r2.setPackage(r1)
            r1 = 16384(0x4000, float:2.2959E-41)
            java.util.List r0 = r0.queryBroadcastReceivers(r2, r1)
            java.util.Iterator r0 = r0.iterator()
            r1 = r4
        L_0x0075:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x00a9
            java.lang.Object r2 = r0.next()
            android.content.pm.ResolveInfo r2 = (android.content.pm.ResolveInfo) r2
            android.content.pm.ActivityInfo r2 = r2.activityInfo
            if (r2 == 0) goto L_0x00a6
            java.lang.String r5 = r2.name     // Catch: ClassNotFoundException -> 0x00a1
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch: ClassNotFoundException -> 0x00a1
            if (r5 != 0) goto L_0x00a6
            java.lang.Class<com.xiaomi.mipush.sdk.PushMessageReceiver> r5 = com.xiaomi.mipush.sdk.PushMessageReceiver.class
            java.lang.String r6 = r2.name     // Catch: ClassNotFoundException -> 0x00a1
            java.lang.Class r6 = java.lang.Class.forName(r6)     // Catch: ClassNotFoundException -> 0x00a1
            boolean r5 = r5.isAssignableFrom(r6)     // Catch: ClassNotFoundException -> 0x00a1
            if (r5 == 0) goto L_0x00a6
            boolean r1 = r2.enabled     // Catch: ClassNotFoundException -> 0x00a1
            if (r1 == 0) goto L_0x00a6
            r1 = r3
            goto L_0x00a7
        L_0x00a1:
            r2 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r2)
            goto L_0x0075
        L_0x00a6:
            r1 = r4
        L_0x00a7:
            if (r1 == 0) goto L_0x0075
        L_0x00a9:
            if (r1 == 0) goto L_0x00cd
            boolean r0 = com.xiaomi.mipush.sdk.MiPushClient.getOpenHmsPush(r8)
            if (r0 == 0) goto L_0x00bf
            java.lang.String r0 = "com.huawei.android.push.intent.RECEIVE"
            java.lang.String r1 = "com.xiaomi.assemble.control.HmsPushReceiver"
            a(r8, r0, r1)
            java.lang.String r0 = "com.huawei.intent.action.PUSH"
            java.lang.String r1 = "com.huawei.hms.support.api.push.PushEventReceiver"
            a(r8, r0, r1)
        L_0x00bf:
            boolean r0 = com.xiaomi.mipush.sdk.MiPushClient.getOpenVIVOPush(r8)
            if (r0 == 0) goto L_0x00cc
            java.lang.String r0 = "com.vivo.pushclient.action.RECEIVE"
            java.lang.String r1 = "com.xiaomi.assemble.control.FTOSPushMessageReceiver"
            a(r8, r0, r1)
        L_0x00cc:
            return
        L_0x00cd:
            com.xiaomi.mipush.sdk.z$a r8 = new com.xiaomi.mipush.sdk.z$a
            java.lang.String r0 = "Receiver: none of the subclasses of PushMessageReceiver is enabled or defined."
            r8.<init>(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.z.c(android.content.Context):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context, PackageInfo packageInfo) {
        boolean z;
        HashSet hashSet = new HashSet();
        String str = context.getPackageName() + ".permission.MIPUSH_RECEIVE";
        hashSet.addAll(Arrays.asList("android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", str, "android.permission.ACCESS_WIFI_STATE", "android.permission.READ_PHONE_STATE", "android.permission.GET_TASKS", "android.permission.VIBRATE"));
        if (packageInfo.permissions != null) {
            for (PermissionInfo permissionInfo : packageInfo.permissions) {
                if (str.equals(permissionInfo.name)) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (z) {
            if (packageInfo.requestedPermissions != null) {
                String[] strArr = packageInfo.requestedPermissions;
                for (String str2 : strArr) {
                    if (!TextUtils.isEmpty(str2) && hashSet.contains(str2)) {
                        hashSet.remove(str2);
                        if (hashSet.isEmpty()) {
                            break;
                        }
                    }
                }
            }
            if (!hashSet.isEmpty()) {
                throw new a(String.format("<uses-permission android:name=\"%1$s\"/> is missing in AndroidManifest.", hashSet.iterator().next()));
            }
            return;
        }
        throw new a(String.format("<permission android:name=\"%1$s\" .../> is undefined in AndroidManifest.", str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(Context context, PackageInfo packageInfo) {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        hashMap2.put(PushMessageHandler.class.getCanonicalName(), new b(PushMessageHandler.class.getCanonicalName(), true, true, ""));
        hashMap2.put(MessageHandleService.class.getCanonicalName(), new b(MessageHandleService.class.getCanonicalName(), true, false, ""));
        if (!MiPushClient.shouldUseMIUIPush(context) || a(packageInfo, new String[]{"com.xiaomi.push.service.XMJobService", "com.xiaomi.push.service.XMPushService"})) {
            hashMap2.put("com.xiaomi.push.service.XMJobService", new b("com.xiaomi.push.service.XMJobService", true, false, "android.permission.BIND_JOB_SERVICE"));
            hashMap2.put("com.xiaomi.push.service.XMPushService", new b("com.xiaomi.push.service.XMPushService", true, false, ""));
        }
        if (MiPushClient.getOpenFCMPush(context)) {
            hashMap2.put("com.xiaomi.assemble.control.MiFireBaseInstanceIdService", new b("com.xiaomi.assemble.control.MiFireBaseInstanceIdService", true, false, ""));
            hashMap2.put("com.xiaomi.assemble.control.MiFirebaseMessagingService", new b("com.xiaomi.assemble.control.MiFirebaseMessagingService", true, false, ""));
        }
        if (MiPushClient.getOpenOPPOPush(context)) {
            hashMap2.put("com.xiaomi.assemble.control.COSPushMessageService", new b("com.xiaomi.assemble.control.COSPushMessageService", true, true, "com.coloros.mcs.permission.SEND_MCS_MESSAGE"));
        }
        if (packageInfo.services != null) {
            ServiceInfo[] serviceInfoArr = packageInfo.services;
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                if (!TextUtils.isEmpty(serviceInfo.name) && hashMap2.containsKey(serviceInfo.name)) {
                    b bVar = (b) hashMap2.remove(serviceInfo.name);
                    boolean z = bVar.f4a;
                    boolean z2 = bVar.f5b;
                    String str = bVar.b;
                    if (z != serviceInfo.enabled) {
                        throw new a(String.format("<service android:name=\"%1$s\" .../> in AndroidManifest had the wrong enabled attribute, which should be android:enabled=%2$b.", serviceInfo.name, Boolean.valueOf(z)));
                    } else if (z2 != serviceInfo.exported) {
                        throw new a(String.format("<service android:name=\"%1$s\" .../> in AndroidManifest had the wrong exported attribute, which should be android:exported=%2$b.", serviceInfo.name, Boolean.valueOf(z2)));
                    } else if (TextUtils.isEmpty(str) || TextUtils.equals(str, serviceInfo.permission)) {
                        hashMap.put(serviceInfo.name, serviceInfo.processName);
                        if (hashMap2.isEmpty()) {
                            break;
                        }
                    } else {
                        throw new a(String.format("<service android:name=\"%1$s\" .../> in AndroidManifest had the wrong permission attribute, which should be android:permission=\"%2$s\".", serviceInfo.name, str));
                    }
                }
            }
        }
        if (!hashMap2.isEmpty()) {
            throw new a(String.format("<service android:name=\"%1$s\" .../> is missing or disabled in AndroidManifest.", hashMap2.keySet().iterator().next()));
        } else if (!TextUtils.equals((CharSequence) hashMap.get(PushMessageHandler.class.getCanonicalName()), (CharSequence) hashMap.get(MessageHandleService.class.getCanonicalName()))) {
            throw new a(String.format("\"%1$s\" and \"%2$s\" must be running in the same process.", PushMessageHandler.class.getCanonicalName(), MessageHandleService.class.getCanonicalName()));
        } else if (hashMap.containsKey("com.xiaomi.push.service.XMJobService") && hashMap.containsKey("com.xiaomi.push.service.XMPushService") && !TextUtils.equals((CharSequence) hashMap.get("com.xiaomi.push.service.XMJobService"), (CharSequence) hashMap.get("com.xiaomi.push.service.XMPushService"))) {
            throw new a(String.format("\"%1$s\" and \"%2$s\" must be running in the same process.", "com.xiaomi.push.service.XMJobService", "com.xiaomi.push.service.XMPushService"));
        }
    }
}
