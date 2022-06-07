package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.xiaomi.channel.commonutils.logger.b;

/* loaded from: classes4.dex */
public class COSPushHelper {
    private static volatile boolean a = false;
    private static long b;

    public static void convertMessage(Intent intent) {
        j.a(intent);
    }

    public static void doInNetworkChange(Context context) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (getNeedRegister()) {
            long j = b;
            if (j <= 0 || j + 300000 <= elapsedRealtime) {
                b = elapsedRealtime;
                registerCOSAssemblePush(context);
            }
        }
    }

    public static boolean getNeedRegister() {
        return a;
    }

    public static boolean hasNetwork(Context context) {
        return j.m743a(context);
    }

    public static void onNotificationMessageCome(Context context, String str) {
    }

    public static void onPassThoughMessageCome(Context context, String str) {
    }

    public static void registerCOSAssemblePush(Context context) {
        AbstractPushManager a2 = g.a(context).a(f.ASSEMBLE_PUSH_COS);
        if (a2 != null) {
            b.m149a("ASSEMBLE_PUSH :  register cos when network change!");
            a2.register();
        }
    }

    public static synchronized void setNeedRegister(boolean z) {
        synchronized (COSPushHelper.class) {
            a = z;
        }
    }

    public static void uploadToken(Context context, String str) {
        j.a(context, f.ASSEMBLE_PUSH_COS, str);
    }
}
