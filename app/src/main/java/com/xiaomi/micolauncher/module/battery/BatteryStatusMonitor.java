package com.xiaomi.micolauncher.module.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.statemodel.HomeModel;
import com.xiaomi.micolauncher.common.utils.BatteryUtils;

/* loaded from: classes3.dex */
public class BatteryStatusMonitor extends BroadcastReceiver {
    public static final String ACTION_BATTERY_LEVEL_CHANGED = "android.intent.action.BATTERY_LEVEL_CHANGED";
    private static final PowerStatusChangedEvent a = new PowerStatusChangedEvent(2);
    private static final PowerStatusChangedEvent b = new PowerStatusChangedEvent(1);
    private static final BatteryLevelStatusChangedEvent c = new BatteryLevelStatusChangedEvent(1);
    private static final BatteryLevelStatusChangedEvent d = new BatteryLevelStatusChangedEvent(2);
    private static final IntentFilter e = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private volatile int f;
    private volatile boolean g;
    private volatile boolean h;
    private volatile String i;
    private volatile boolean j;
    private volatile boolean k = false;

    /* loaded from: classes3.dex */
    public static class a {
        private static final BatteryStatusMonitor a = new BatteryStatusMonitor();
    }

    public static BatteryStatusMonitor getInstance() {
        return a.a;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        L.battery.v("BatteryStatusMonitor battery %s", action);
        if (action != null) {
            a(context, intent, action);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void a(Context context, @Nullable Intent intent, @NonNull String str) {
        char c2;
        boolean z = this.g;
        int i = this.f;
        String str2 = this.i;
        boolean z2 = this.h;
        this.i = str;
        a(context, intent);
        EnergySaver instance = EnergySaver.getInstance(context);
        switch (str.hashCode()) {
            case -1980154005:
                if (str.equals("android.intent.action.BATTERY_OKAY")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -1886648615:
                if (str.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -625323454:
                if (str.equals(ACTION_BATTERY_LEVEL_CHANGED)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 490310653:
                if (str.equals("android.intent.action.BATTERY_LOW")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 1019184907:
                if (str.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        Object obj = null;
        switch (c2) {
            case 0:
                obj = b;
                break;
            case 1:
                a();
                obj = a;
                break;
            case 2:
                obj = c;
                break;
            case 3:
                obj = d;
                break;
            case 4:
                obj = new BatteryStatusChangedEvent(this.f);
                break;
        }
        if (isBatteryInfoValid()) {
            if (isCharging()) {
                instance.setPowerOnMode(!z);
            } else if (HomeModel.getInstance().isInitialized()) {
                instance.scheduleScreenOff(false);
            }
            instance.a();
        }
        if (!this.j) {
            L.battery.e("battery info invalid, do not notify");
        } else if (obj == null || (TextUtils.equals(str, str2) && z == this.g && i == this.f && z2 == this.h)) {
            L.battery.v("ignore event for repeated or unknown action, action %s, charging %s, level %s", str, Boolean.valueOf(z), Integer.valueOf(i));
        } else {
            EventBusRegistry.getEventBus().post(obj);
        }
    }

    private void a() {
        if (this.g) {
            this.g = false;
            L.battery.w("charging set to false for power already disconnected");
        }
    }

    private void a(@NonNull Context context, @Nullable Intent intent) {
        int i;
        int i2;
        L.battery.v("readBatteryData from intent %s", intent);
        if (intent != null) {
            i2 = intent.getIntExtra(com.xiaomi.onetrack.a.a.d, -1);
            i = intent.getIntExtra("status", -1);
        } else {
            i = -1;
            i2 = 0;
        }
        if (i2 == -1 || i == -1) {
            intent = context.registerReceiver(null, e);
        }
        if (i2 >= 0) {
            this.f = i2;
        }
        if (intent != null) {
            int intExtra = intent.getIntExtra(com.xiaomi.onetrack.a.a.d, -1);
            int intExtra2 = intent.getIntExtra("status", -1);
            this.g = intExtra2 == 2 || intExtra2 == 5;
            this.h = intExtra2 == 5;
            this.f = BatteryUtils.calculateBatteryLevel(intExtra, intent.getIntExtra("scale", BatteryUtils.getDefaultScaleValue()));
            if (this.f < 0) {
                L.battery.e("battery level wrong : " + this.f + ",rawLevel:" + intExtra);
                this.f = 0;
            }
            this.j = true;
            L.battery.v("dump action %s, percent %s, status %s, charging %B", intent.getAction(), Integer.valueOf(this.f), Integer.valueOf(intExtra2), Boolean.valueOf(this.g));
        }
    }

    public boolean updateBatteryInfo(@NonNull Context context) {
        return updateBatteryInfo(context, context.registerReceiver(null, e), true);
    }

    public boolean updateBatteryInfo(@NonNull Context context, @Nullable Intent intent, boolean z) {
        if (!BatteryUtils.isBatteryPresent(context)) {
            return false;
        }
        a(context, intent, ACTION_BATTERY_LEVEL_CHANGED);
        return this.j;
    }

    public boolean isBatterOkay() {
        return isBatterOkay(this.f);
    }

    public static boolean isBatterOkay(int i) {
        return i > BatteryUtils.getLowBatteryThreshold();
    }

    public boolean isBatteryLow() {
        return isBatteryLow(this.f);
    }

    public static boolean isBatteryLow(int i) {
        return i <= BatteryUtils.getLowBatteryThreshold();
    }

    public int getLevel() {
        return this.f;
    }

    public boolean isBatteryInfoValid() {
        return this.j;
    }

    public boolean isCharging() {
        return this.g;
    }

    public boolean isFull() {
        return this.h;
    }

    public boolean isPowerSaveMode() {
        return this.k;
    }

    public void setPowerSaveMode(boolean z) {
        this.k = z;
    }

    /* loaded from: classes3.dex */
    public static class BatteryStatusChangedEvent {
        private int a;

        private BatteryStatusChangedEvent(int i) {
            this.a = i;
        }

        public int getLevel() {
            return this.a;
        }
    }

    /* loaded from: classes3.dex */
    public static class PowerStatusChangedEvent {
        public static final int EVENT_TYPE_POWER_CONNECTED = 1;
        public static final int EVENT_TYPE_POWER_DISCONNECTED = 2;
        private int a;

        private PowerStatusChangedEvent(int i) {
            this.a = i;
        }

        public int getEventType() {
            return this.a;
        }
    }

    /* loaded from: classes3.dex */
    public static class BatteryLevelStatusChangedEvent {
        public static final int EVENT_TYPE_BATTERY_LOW = 1;
        public static final int EVENT_TYPE_BATTERY_OKAY = 2;
        private int a;

        private BatteryLevelStatusChangedEvent(int i) {
            this.a = i;
        }

        public int getEventType() {
            return this.a;
        }
    }
}
