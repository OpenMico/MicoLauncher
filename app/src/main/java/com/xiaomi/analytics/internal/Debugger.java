package com.xiaomi.analytics.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.xiaomi.analytics.internal.util.ALog;
import com.xiaomi.analytics.internal.util.AndroidUtils;

/* loaded from: classes3.dex */
public class Debugger {
    private static volatile Debugger a = null;
    public static boolean sUseStaging = false;
    private Context b;
    private volatile boolean c = false;
    private BroadcastReceiver d = new BroadcastReceiver() { // from class: com.xiaomi.analytics.internal.Debugger.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                String addPrefix = ALog.addPrefix("Debugger");
                Log.d(addPrefix, "action = " + action);
                if ("com.xiaomi.analytics.intent.DEBUG_ON".equals(action)) {
                    ALog.sEnable = true;
                } else if ("com.xiaomi.analytics.intent.DEBUG_OFF".equals(action)) {
                    ALog.sEnable = false;
                } else if ("com.xiaomi.analytics.intent.STAGING_ON".equals(action)) {
                    Debugger.sUseStaging = true;
                } else if ("com.xiaomi.analytics.intent.STAGING_OFF".equals(action)) {
                    Debugger.sUseStaging = false;
                }
            }
        }
    };

    private Debugger(Context context) {
        this.b = AndroidUtils.getApplicationContext(context);
    }

    public static synchronized Debugger getDebugger(Context context) {
        Debugger debugger;
        synchronized (Debugger.class) {
            if (a == null) {
                a = new Debugger(context);
            }
            debugger = a;
        }
        return debugger;
    }

    public void register() {
        if (!this.c) {
            this.c = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.xiaomi.analytics.intent.DEBUG_ON");
            intentFilter.addAction("com.xiaomi.analytics.intent.DEBUG_OFF");
            intentFilter.addAction("com.xiaomi.analytics.intent.STAGING_ON");
            intentFilter.addAction("com.xiaomi.analytics.intent.STAGING_OFF");
            this.b.registerReceiver(this.d, intentFilter);
        }
    }

    public void unregister() {
        this.b.unregisterReceiver(this.d);
        this.c = false;
    }
}
