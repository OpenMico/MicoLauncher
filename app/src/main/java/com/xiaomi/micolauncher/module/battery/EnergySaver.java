package com.xiaomi.micolauncher.module.battery;

import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.BatteryUtils;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class EnergySaver {
    private static volatile EnergySaver a;
    private final Context b;
    private final b c;

    private EnergySaver(Context context) {
        this.c = new b();
        this.b = context;
    }

    public static EnergySaver getInstance(Context context) {
        if (a != null) {
            return a;
        }
        synchronized (EnergySaver.class) {
            if (a == null) {
                a = BatteryUtils.isBatteryPresent(context) ? new EnergySaver(context) : new a(context);
            }
        }
        return a;
    }

    public void scheduleScreenOff(boolean z) {
        int autoScreenOffTimeIndex = MicoSettings.getAutoScreenOffTimeIndex(this.b, -1);
        L.battery.d("EnergySaver scheduleScreenOff timeIndex=%s", Integer.valueOf(autoScreenOffTimeIndex));
        if (z && autoScreenOffTimeIndex > 0) {
            autoScreenOffTimeIndex = 1;
        }
        if (autoScreenOffTimeIndex < 0) {
            autoScreenOffTimeIndex = 1;
        }
        Settings.System.putLong(this.b.getContentResolver(), "screen_off_timeout", BatteryHelper.convertScreenOffTime(autoScreenOffTimeIndex));
    }

    public void setPowerOnMode(final boolean z) {
        Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.battery.-$$Lambda$EnergySaver$iXH_qNUU9E_mz2_s2ZZhWNsTAXo
            @Override // java.lang.Runnable
            public final void run() {
                EnergySaver.this.a(z);
            }
        });
        Settings.System.putLong(this.b.getContentResolver(), "screen_off_timeout", 2147483647L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z) {
        L.battery.d("EnergySaver set power on mode, just charged %s", Boolean.valueOf(z));
        if (z && this.c.c(this.b)) {
            turnOnScreen();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.battery.-$$Lambda$EnergySaver$hqVtT98-lJcgVJM3noe4Kn4KePw
            @Override // java.lang.Runnable
            public final void run() {
                EnergySaver.this.b();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        BatteryStatusMonitor instance = BatteryStatusMonitor.getInstance();
        if (instance.isBatteryInfoValid()) {
            if (instance.isCharging()) {
                L.battery.d("clear power save mode for charging");
                setPowerSaveMode(false);
                return;
            }
            int level = instance.getLevel();
            if (level > 20) {
                L.battery.d("clear power save mode for level %s", Integer.valueOf(level));
                setPowerSaveMode(false);
            }
        }
    }

    public void setPowerSaveMode(boolean z) {
        this.c.a(this.b, z);
    }

    public boolean isBatteryMode() {
        return BatteryStatusMonitor.getInstance().isBatteryInfoValid() && !BatteryStatusMonitor.getInstance().isCharging();
    }

    public void turnOnScreen() {
        this.c.a(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a extends EnergySaver {
        @Override // com.xiaomi.micolauncher.module.battery.EnergySaver
        public void scheduleScreenOff(boolean z) {
        }

        @Override // com.xiaomi.micolauncher.module.battery.EnergySaver
        public void setPowerOnMode(boolean z) {
        }

        a(Context context) {
            super(context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class b {
        private PowerManager a;
        private boolean b;
        private boolean c;
        private final AtomicBoolean d;
        private final AtomicBoolean e;

        private boolean b(Context context, boolean z) {
            return true;
        }

        private b() {
            this.d = new AtomicBoolean(false);
            this.e = new AtomicBoolean(true);
        }

        synchronized void a(Context context, boolean z) {
            if (!this.c || this.b != z) {
                b(context);
                if (!this.a.setPowerSaveMode(z)) {
                    L.battery.d("failed to set power save mode %s", Boolean.valueOf(z));
                    this.c = false;
                    return;
                }
                this.b = z;
                this.c = true;
                L.battery.d("set power save mode %s", Boolean.valueOf(z));
                return;
            }
            L.battery.d("same power save mode state %B", Boolean.valueOf(z));
        }

        private synchronized void b(Context context) {
            if (this.a == null) {
                this.a = (PowerManager) context.getSystemService(PowerManager.class);
            }
        }

        void a(Context context) {
            if (b(context, false)) {
                L.battery.v("EnergySaver turn on screen");
                ScreenUtil.turnScreenOn(context);
                return;
            }
            L.battery.d("EnergySaver screen already on");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean c(Context context) {
            b(context);
            return this.a.isInteractive();
        }
    }
}
