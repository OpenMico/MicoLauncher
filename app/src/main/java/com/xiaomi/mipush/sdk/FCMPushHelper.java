package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import java.util.Map;

/* loaded from: classes4.dex */
public class FCMPushHelper {
    public static void clearToken(Context context) {
        j.m742a(context, f.ASSEMBLE_PUSH_FCM);
    }

    public static void convertMessage(Intent intent) {
        j.a(intent);
    }

    public static boolean isFCMSwitchOpen(Context context) {
        return j.m744a(context, f.ASSEMBLE_PUSH_FCM) && MiPushClient.getOpenFCMPush(context);
    }

    public static void notifyFCMNotificationCome(Context context, Map<String, String> map) {
        PushMessageReceiver a;
        String str = map.get("pushMsg");
        if (!TextUtils.isEmpty(str) && (a = j.a(context)) != null) {
            a.onNotificationMessageArrived(context, j.a(str));
        }
    }

    public static void notifyFCMPassThoughMessageCome(Context context, Map<String, String> map) {
        PushMessageReceiver a;
        String str = map.get("pushMsg");
        if (!TextUtils.isEmpty(str) && (a = j.a(context)) != null) {
            a.onReceivePassThroughMessage(context, j.a(str));
        }
    }

    public static void reportFCMMessageDelete() {
        MiTinyDataClient.upload(j.b(f.ASSEMBLE_PUSH_FCM), "fcm", 1L, "some fcm messages was deleted ");
    }

    public static void uploadToken(Context context, String str) {
        j.a(context, f.ASSEMBLE_PUSH_FCM, str);
    }
}
