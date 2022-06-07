package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class HWPushHelper {
    private static boolean a = false;

    public static void convertMessage(Intent intent) {
        j.a(intent);
    }

    public static boolean hasNetwork(Context context) {
        return j.m743a(context);
    }

    public static boolean isHmsTokenSynced(Context context) {
        String a2 = j.a(f.ASSEMBLE_PUSH_HUAWEI);
        if (TextUtils.isEmpty(a2)) {
            return false;
        }
        String a3 = j.a(context, a2);
        String a4 = ao.a(context).a(bd.UPLOAD_HUAWEI_TOKEN);
        return !TextUtils.isEmpty(a3) && !TextUtils.isEmpty(a4) && "synced".equals(a4);
    }

    public static boolean isUserOpenHmsPush(Context context) {
        return MiPushClient.getOpenHmsPush(context);
    }

    public static boolean needConnect() {
        return a;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0026, code lost:
        r0 = r2.getString("pushMsg");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void notifyHmsNotificationMessageClicked(android.content.Context r4, java.lang.String r5) {
        /*
            java.lang.String r0 = ""
            boolean r1 = android.text.TextUtils.isEmpty(r5)
            if (r1 != 0) goto L_0x0039
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch: Exception -> 0x0031
            r1.<init>(r5)     // Catch: Exception -> 0x0031
            int r5 = r1.length()     // Catch: Exception -> 0x0031
            if (r5 <= 0) goto L_0x0039
            r5 = 0
        L_0x0014:
            int r2 = r1.length()     // Catch: Exception -> 0x0031
            if (r5 >= r2) goto L_0x0039
            org.json.JSONObject r2 = r1.getJSONObject(r5)     // Catch: Exception -> 0x0031
            java.lang.String r3 = "pushMsg"
            boolean r3 = r2.has(r3)     // Catch: Exception -> 0x0031
            if (r3 == 0) goto L_0x002e
            java.lang.String r5 = "pushMsg"
            java.lang.String r5 = r2.getString(r5)     // Catch: Exception -> 0x0031
            r0 = r5
            goto L_0x0039
        L_0x002e:
            int r5 = r5 + 1
            goto L_0x0014
        L_0x0031:
            r5 = move-exception
            java.lang.String r5 = r5.toString()
            com.xiaomi.channel.commonutils.logger.b.d(r5)
        L_0x0039:
            com.xiaomi.mipush.sdk.PushMessageReceiver r5 = com.xiaomi.mipush.sdk.j.a(r4)
            if (r5 == 0) goto L_0x0053
            com.xiaomi.mipush.sdk.MiPushMessage r0 = com.xiaomi.mipush.sdk.j.a(r0)
            java.util.Map r1 = r0.getExtra()
            java.lang.String r2 = "notify_effect"
            boolean r1 = r1.containsKey(r2)
            if (r1 == 0) goto L_0x0050
            return
        L_0x0050:
            r5.onNotificationMessageClicked(r4, r0)
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.HWPushHelper.notifyHmsNotificationMessageClicked(android.content.Context, java.lang.String):void");
    }

    public static void notifyHmsPassThoughMessageArrived(Context context, String str) {
        String str2 = "";
        try {
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("content")) {
                    str2 = jSONObject.getString("content");
                }
            }
        } catch (Exception e) {
            b.d(e.toString());
        }
        PushMessageReceiver a2 = j.a(context);
        if (a2 != null) {
            a2.onReceivePassThroughMessage(context, j.a(str2));
        }
    }

    public static void registerHuaWeiAssemblePush(Context context) {
        AbstractPushManager a2 = g.a(context).a(f.ASSEMBLE_PUSH_HUAWEI);
        if (a2 != null) {
            a2.register();
        }
    }

    public static void reportError(String str, int i) {
        j.a(str, i);
    }

    public static synchronized void setConnectTime(Context context) {
        synchronized (HWPushHelper.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putLong("last_connect_time", System.currentTimeMillis()).commit();
        }
    }

    public static synchronized void setGetTokenTime(Context context) {
        synchronized (HWPushHelper.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putLong("last_get_token_time", System.currentTimeMillis()).commit();
        }
    }

    public static void setNeedConnect(boolean z) {
        a = z;
    }

    public static synchronized boolean shouldGetToken(Context context) {
        boolean z;
        synchronized (HWPushHelper.class) {
            z = false;
            if (Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_get_token_time", -1L)) > 172800000) {
                z = true;
            }
        }
        return z;
    }

    public static synchronized boolean shouldTryConnect(Context context) {
        boolean z;
        synchronized (HWPushHelper.class) {
            z = false;
            if (Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_connect_time", -1L)) > 5000) {
                z = true;
            }
        }
        return z;
    }

    public static void uploadToken(Context context, String str) {
        j.a(context, f.ASSEMBLE_PUSH_HUAWEI, str);
    }
}
