package com.xiaomi.push;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.blankj.utilcode.constant.CacheConstants;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.ag;

/* loaded from: classes4.dex */
public class dt {
    private static volatile dt a;
    private Context b;

    private dt(Context context) {
        this.b = context;
    }

    private int a(int i) {
        return Math.max(60, i);
    }

    public static dt a(Context context) {
        if (a == null) {
            synchronized (dt.class) {
                if (a == null) {
                    a = new dt(context);
                }
            }
        }
        return a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        aj a2 = aj.a(this.b);
        ag a3 = ag.a(this.b);
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("mipush_extra", 0);
        long currentTimeMillis = System.currentTimeMillis();
        long j = sharedPreferences.getLong("first_try_ts", currentTimeMillis);
        if (j == currentTimeMillis) {
            sharedPreferences.edit().putLong("first_try_ts", currentTimeMillis).commit();
        }
        if (Math.abs(currentTimeMillis - j) >= 172800000) {
            boolean a4 = a3.a(hm.ScreenSizeCollectionSwitch.a(), true);
            boolean a5 = a3.a(hm.AndroidVnCollectionSwitch.a(), true);
            boolean a6 = a3.a(hm.AndroidVcCollectionSwitch.a(), true);
            boolean a7 = a3.a(hm.AndroidIdCollectionSwitch.a(), true);
            boolean a8 = a3.a(hm.OperatorSwitch.a(), true);
            if (a4 || a5 || a6 || a7 || a8) {
                int a9 = a(a3.a(hm.DeviceInfoCollectionFrequency.a(), 1209600));
                a2.a(new ea(this.b, a9, a4, a5, a6, a7, a8), a9, 30);
            }
            boolean a10 = a3.a(hm.MacCollectionSwitch.a(), false);
            boolean a11 = a3.a(hm.IMSICollectionSwitch.a(), false);
            boolean a12 = a3.a(hm.IccidCollectionSwitch.a(), false);
            boolean a13 = a3.a(hm.DeviceIdSwitch.a(), false);
            if (a10 || a11 || a12 || a13) {
                int a14 = a(a3.a(hm.DeviceBaseInfoCollectionFrequency.a(), 1209600));
                a2.a(new dz(this.b, a14, a10, a11, a12, a13), a14, 30);
            }
            if (Build.VERSION.SDK_INT < 21 && a3.a(hm.AppActiveListCollectionSwitch.a(), false)) {
                int a15 = a(a3.a(hm.AppActiveListCollectionFrequency.a(), 900));
                a2.a(new dv(this.b, a15), a15, 30);
            }
            if (a3.a(hm.StorageCollectionSwitch.a(), true)) {
                int a16 = a(a3.a(hm.StorageCollectionFrequency.a(), CacheConstants.DAY));
                a2.a(new eb(this.b, a16), a16, 30);
            }
            if (a3.a(hm.TopAppCollectionSwitch.a(), false)) {
                int a17 = a(a3.a(hm.TopAppCollectionFrequency.a(), 300));
                a2.a(new ec(this.b, a17), a17, 30);
            }
            if (a3.a(hm.BroadcastActionCollectionSwitch.a(), true)) {
                int a18 = a(a3.a(hm.BroadcastActionCollectionFrequency.a(), 900));
                a2.a(new dx(this.b, a18), a18, 30);
            }
            if (a3.a(hm.ActivityTSSwitch.a(), false)) {
                c();
            }
            if (a3.a(hm.UploadSwitch.a(), true)) {
                a2.a(new ed(this.b), a(a3.a(hm.UploadFrequency.a(), CacheConstants.DAY)), 60);
            }
            if (a3.a(hm.BatteryCollectionSwitch.a(), false)) {
                int a19 = a(a3.a(hm.BatteryCollectionFrequency.a(), CacheConstants.HOUR));
                a2.a(new dw(this.b, a19), a19, 30);
            }
        }
    }

    private boolean c() {
        if (Build.VERSION.SDK_INT >= 14) {
            try {
                ((Application) (this.b instanceof Application ? this.b : this.b.getApplicationContext())).registerActivityLifecycleCallbacks(new dk(this.b, String.valueOf(System.currentTimeMillis() / 1000)));
                return true;
            } catch (Exception e) {
                b.a(e);
            }
        }
        return false;
    }

    public void a() {
        aj.a(this.b).a(new dg(this), 30);
    }
}
