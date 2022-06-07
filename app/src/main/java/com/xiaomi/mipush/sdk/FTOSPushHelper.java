package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import java.util.Map;

/* loaded from: classes4.dex */
public class FTOSPushHelper {
    private static long a = 0;
    private static volatile boolean b = false;

    private static void a(Context context) {
        AbstractPushManager a2 = g.a(context).a(f.ASSEMBLE_PUSH_FTOS);
        if (a2 != null) {
            b.m149a("ASSEMBLE_PUSH :  register fun touch os when network change!");
            a2.register();
        }
    }

    public static void doInNetworkChange(Context context) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (getNeedRegister()) {
            long j = a;
            if (j <= 0 || j + 300000 <= elapsedRealtime) {
                a = elapsedRealtime;
                a(context);
            }
        }
    }

    public static boolean getNeedRegister() {
        return b;
    }

    public static boolean hasNetwork(Context context) {
        return j.m743a(context);
    }

    public static void notifyFTOSNotificationClicked(Context context, Map<String, String> map) {
        PushMessageReceiver a2;
        if (map != null && map.containsKey("pushMsg")) {
            String str = map.get("pushMsg");
            if (!TextUtils.isEmpty(str) && (a2 = j.a(context)) != null) {
                MiPushMessage a3 = j.a(str);
                if (!a3.getExtra().containsKey("notify_effect")) {
                    a2.onNotificationMessageClicked(context, a3);
                }
            }
        }
    }

    public static void setNeedRegister(boolean z) {
        b = z;
    }

    public static void uploadToken(Context context, String str) {
        j.a(context, f.ASSEMBLE_PUSH_FTOS, str);
    }
}
