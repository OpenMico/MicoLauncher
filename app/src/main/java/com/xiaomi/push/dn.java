package com.xiaomi.push;

import android.content.Context;
import android.content.IntentFilter;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.mpcd.receivers.BroadcastActionsReceiver;

/* loaded from: classes4.dex */
public class dn {
    private static ds a() {
        return new dd();
    }

    public static void a(Context context) {
        dt.a(context).a();
        try {
            context.registerReceiver(new BroadcastActionsReceiver(a()), b());
        } catch (Throwable th) {
            b.a(th);
        }
    }

    private static IntentFilter b() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        return intentFilter;
    }
}
