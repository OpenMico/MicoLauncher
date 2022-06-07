package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import android.content.IntentFilter;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.BatteryUtils;
import com.xiaomi.micolauncher.module.battery.BatteryStatusMonitor;

/* loaded from: classes3.dex */
public class BatteryMonitorSetup implements ISetupRule {
    private volatile Context a;

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        if (b(context)) {
            L.battery.d("no battery");
            return;
        }
        this.a = context;
        a(context);
    }

    private void a(Context context) {
        IntentFilter intentFilter = new IntentFilter(BatteryStatusMonitor.ACTION_BATTERY_LEVEL_CHANGED);
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        intentFilter.addAction("android.intent.action.BATTERY_OKAY");
        context.registerReceiver(BatteryStatusMonitor.getInstance(), intentFilter, null, Threads.getLightWorkHandler());
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
        Context context = this.a;
        if (context != null && !b(context)) {
            context.unregisterReceiver(BatteryStatusMonitor.getInstance());
        }
    }

    private boolean b(Context context) {
        return !BatteryUtils.isBatteryPresent(context);
    }
}
