package com.xiaomi.analytics.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.analytics.PolicyConfiguration;
import com.xiaomi.analytics.internal.a;
import com.xiaomi.analytics.internal.util.ALog;
import com.xiaomi.analytics.internal.util.AndroidUtils;
import com.xiaomi.analytics.internal.util.ApkTools;
import com.xiaomi.analytics.internal.util.AssetUtils;
import com.xiaomi.analytics.internal.util.FileUtils;
import com.xiaomi.analytics.internal.util.TaskRunner;
import com.xiaomi.analytics.internal.util.TimeUtils;
import com.xiaomi.analytics.internal.v1.AnalyticsInterface;
import com.xiaomi.analytics.internal.v1.DexAnalytics;
import com.xiaomi.analytics.internal.v1.SysAnalytics;
import java.io.File;

/* loaded from: classes3.dex */
public class SdkManager {
    private static volatile SdkManager b;
    private static Object c;
    private Context e;
    private AnalyticsInterface f;
    private SysAnalytics h;
    private OnSdkCorePrepareListener i;
    private boolean m;
    private long n;
    private static final int a = TimeUtils.ONE_SECOND_IN_MS * 30;
    private static boolean d = false;
    private PolicyConfiguration g = null;
    private long j = 0;
    private volatile boolean k = false;
    private boolean l = false;
    private AnalyticsInterface q = null;
    private Runnable r = new Runnable() { // from class: com.xiaomi.analytics.internal.SdkManager.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (SdkManager.this.f == null || a.a(SdkManager.this.e).a()) {
                    a.a(SdkManager.this.e).a(new File(SdkManager.this.e()).getAbsolutePath());
                }
            } catch (Exception e) {
                Log.w(ALog.addPrefix("SdkManager"), "mUpdateChecker exception", e);
            }
        }
    };
    private Runnable s = new Runnable() { // from class: com.xiaomi.analytics.internal.SdkManager.2
        /* JADX WARN: Removed duplicated region for block: B:42:0x00e8 A[Catch: all -> 0x0133, TryCatch #0 {, blocks: (B:5:0x0006, B:7:0x0014, B:9:0x001a, B:11:0x0027, B:13:0x002c, B:14:0x0046, B:16:0x004c, B:17:0x0064, B:20:0x006b, B:24:0x0083, B:27:0x00a2, B:30:0x00b0, B:34:0x00c1, B:35:0x00ca, B:38:0x00d7, B:42:0x00e8, B:43:0x00fb, B:45:0x0105, B:46:0x0108, B:48:0x0110, B:50:0x011c, B:51:0x0121, B:52:0x0131), top: B:64:0x0006 }] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0105 A[Catch: all -> 0x0133, TryCatch #0 {, blocks: (B:5:0x0006, B:7:0x0014, B:9:0x001a, B:11:0x0027, B:13:0x002c, B:14:0x0046, B:16:0x004c, B:17:0x0064, B:20:0x006b, B:24:0x0083, B:27:0x00a2, B:30:0x00b0, B:34:0x00c1, B:35:0x00ca, B:38:0x00d7, B:42:0x00e8, B:43:0x00fb, B:45:0x0105, B:46:0x0108, B:48:0x0110, B:50:0x011c, B:51:0x0121, B:52:0x0131), top: B:64:0x0006 }] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0110 A[Catch: all -> 0x0133, TryCatch #0 {, blocks: (B:5:0x0006, B:7:0x0014, B:9:0x001a, B:11:0x0027, B:13:0x002c, B:14:0x0046, B:16:0x004c, B:17:0x0064, B:20:0x006b, B:24:0x0083, B:27:0x00a2, B:30:0x00b0, B:34:0x00c1, B:35:0x00ca, B:38:0x00d7, B:42:0x00e8, B:43:0x00fb, B:45:0x0105, B:46:0x0108, B:48:0x0110, B:50:0x011c, B:51:0x0121, B:52:0x0131), top: B:64:0x0006 }] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 336
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.analytics.internal.SdkManager.AnonymousClass2.run():void");
        }
    };
    private a.AbstractC0159a t = new a.AbstractC0159a() { // from class: com.xiaomi.analytics.internal.SdkManager.3
        @Override // com.xiaomi.analytics.internal.a.AbstractC0159a
        public void a(String str, boolean z) {
            if (SdkManager.this.f == null) {
                ALog.d("SdkManager", "download finished, use new analytics.");
                AnalyticsInterface m = SdkManager.this.m();
                if (m != null) {
                    m.init();
                }
                SdkManager.this.f = m;
                SdkManager sdkManager = SdkManager.this;
                sdkManager.a(sdkManager.f);
            } else if (z && !AndroidUtils.isForeground(SdkManager.this.e)) {
                Process.killProcess(Process.myPid());
            }
        }
    };
    private BroadcastReceiver u = new BroadcastReceiver() { // from class: com.xiaomi.analytics.internal.SdkManager.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                try {
                    String action = intent.getAction();
                    if ("android.intent.action.SCREEN_OFF".equals(action)) {
                        SdkManager.this.n = System.currentTimeMillis();
                        SdkManager.this.m = true;
                        if (SdkManager.this.q != null) {
                            SdkManager.this.a(SdkManager.this.r());
                        } else {
                            SdkManager.this.e.unregisterReceiver(SdkManager.this.u);
                            ALog.d("SdkManager", "pending dex is null, unregister");
                        }
                    } else if ("android.intent.action.SCREEN_ON".equals(action)) {
                        SdkManager.this.m = false;
                    }
                    ALog.d("SdkManager", "screen off : " + SdkManager.this.m);
                } catch (Exception e) {
                    ALog.d("SdkManager", "mScreenReceiver onReceive e", e);
                }
            }
        }
    };
    private Runnable v = new Runnable() { // from class: com.xiaomi.analytics.internal.SdkManager.5
        @Override // java.lang.Runnable
        public void run() {
            try {
                synchronized (SdkManager.b) {
                    if (!SdkManager.this.q() || SdkManager.this.q == null) {
                        ALog.d("SdkManager", "skip init dex");
                    } else {
                        SdkManager.this.q.init();
                        SdkManager.this.q = null;
                        SdkManager.this.e.unregisterReceiver(SdkManager.this.u);
                        ALog.d("SdkManager", "pending dex init executed, unregister and clear pending");
                    }
                }
            } catch (Exception e) {
                ALog.e("SdkManager", "dexInitTask", e);
            }
        }
    };
    private HandlerThread p = new HandlerThread("api-sdkmgr", 10);
    private Handler o = new Handler(this.p.getLooper());

    /* loaded from: classes3.dex */
    public interface OnSdkCorePrepareListener {
        void onSdkCorePrepared(AnalyticsInterface analyticsInterface);
    }

    private SdkManager(Context context) {
        this.e = AndroidUtils.getApplicationContext(context);
        Context context2 = this.e;
        c = "connectivity";
        this.p.start();
        this.h = new SysAnalytics(this.e);
        a.a(this.e).a(this.t);
        TaskRunner.SINGLE_EXECUTOR.execute(this.s);
    }

    public static synchronized SdkManager getInstance(Context context) {
        SdkManager sdkManager;
        synchronized (SdkManager.class) {
            if (b == null) {
                b = new SdkManager(context);
            }
            sdkManager = b;
        }
        return sdkManager;
    }

    public static void setUseSysAnalyticsOnly() {
        d = true;
    }

    public void setOnSdkPrepareListener(OnSdkCorePrepareListener onSdkCorePrepareListener) {
        this.i = onSdkCorePrepareListener;
    }

    public void setDontUseSystemAnalytics(boolean z) {
        this.l = z;
    }

    public AnalyticsInterface getAnalytics() {
        return this.f;
    }

    private String d() {
        return this.e.getDir("analytics", 0).getAbsolutePath();
    }

    public String e() {
        return d() + "/analytics.apk";
    }

    private String f() {
        return d() + "/analytics_asset.apk";
    }

    private String g() {
        return d() + "/lib/";
    }

    private String h() {
        return d() + "/asset_lib/";
    }

    public void i() {
        File file = new File(g());
        if (!file.exists()) {
            file.mkdirs();
        } else {
            FileUtils.deleteAllFiles(file);
        }
        File file2 = new File(h());
        if (!file2.exists()) {
            file2.mkdirs();
        } else {
            FileUtils.deleteAllFiles(file2);
        }
    }

    public void pollUpdate() {
        if (this.k) {
            j();
        }
    }

    public synchronized void j() {
        if (System.currentTimeMillis() - this.j > TimeUtils.ONE_HOUR_IN_MS) {
            this.j = System.currentTimeMillis();
            TaskRunner.SINGLE_EXECUTOR.execute(this.r);
        }
    }

    public void a(AnalyticsInterface analyticsInterface) {
        this.f = analyticsInterface;
        AnalyticsInterface analyticsInterface2 = this.f;
        if (analyticsInterface2 != null) {
            if (this.i != null) {
                analyticsInterface2.setDebugOn(ALog.sEnable);
                ALog.d("SdkManager", "Analytics module loaded, version is " + this.f.getVersion());
                this.i.onSdkCorePrepared(this.f);
            }
            PolicyConfiguration policyConfiguration = this.g;
            if (policyConfiguration != null) {
                policyConfiguration.apply(this.f);
            }
        }
    }

    public AnalyticsInterface k() {
        if (this.h.isOnline()) {
            this.h.waitForConnected();
        }
        return this.h;
    }

    public AnalyticsInterface l() {
        try {
            String[] list = this.e.getAssets().list("");
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    if (!TextUtils.isEmpty(list[i]) && list[i].startsWith("analytics_core")) {
                        AssetUtils.extractAssetFile(this.e, list[i], f());
                        File file = new File(f());
                        if (file.exists()) {
                            if (!s() || a(f())) {
                                ApkTools.extractSo(this.e, f(), h());
                                return new DexAnalytics(this.e, f(), h());
                            }
                            ALog.d("SdkManager", "Not suitable for Android P, so delete it");
                            file.delete();
                            return null;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.w(ALog.addPrefix("SdkManager"), "loadAssetAnalytics exception", e);
        }
        return null;
    }

    public AnalyticsInterface m() {
        try {
            File file = new File(e());
            if (file.exists()) {
                if (!s() || a(e())) {
                    ApkTools.extractSo(this.e, file.getAbsolutePath(), g());
                    return new DexAnalytics(this.e, file.getAbsolutePath(), g());
                }
                ALog.d("SdkManager", "Not suitable for Android P, so delete it");
                file.delete();
                return null;
            }
        } catch (Exception e) {
            Log.w(ALog.addPrefix("SdkManager"), "loadLocalAnalytics exception", e);
        }
        return null;
    }

    public void setPolicyConfiguration(PolicyConfiguration policyConfiguration) {
        PolicyConfiguration policyConfiguration2;
        this.g = policyConfiguration;
        AnalyticsInterface analyticsInterface = this.f;
        if (analyticsInterface != null && (policyConfiguration2 = this.g) != null) {
            policyConfiguration2.apply(analyticsInterface);
        }
    }

    public Version getVersion() {
        if (getAnalytics() != null) {
            return getAnalytics().getVersion();
        }
        return new Version(Constants.INVALID_CORE_VER_STR);
    }

    public void n() {
        if (!o()) {
            this.q = null;
        } else {
            p();
        }
    }

    public void a(boolean z) {
        try {
            this.e.getSharedPreferences(Constants.PREFS_FILE, 0).edit().putBoolean("pld", z).apply();
        } catch (Exception e) {
            Log.w(ALog.addPrefix("SdkManager"), "savePreviousLoadDex exception", e);
        }
    }

    private boolean o() {
        try {
            return this.e.getSharedPreferences(Constants.PREFS_FILE, 0).getBoolean("pld", true);
        } catch (Exception e) {
            Log.w(ALog.addPrefix("SdkManager"), "getPreviousLoadDex exception", e);
            return false;
        }
    }

    private void p() {
        ALog.d("SdkManager", "register screen receiver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.e.registerReceiver(this.u, intentFilter);
    }

    public void a(long j) {
        this.o.removeCallbacks(this.v);
        this.o.postDelayed(this.v, j);
        ALog.d("SdkManager", "post dex init task");
    }

    public boolean q() {
        return this.m && TimeUtils.expired(this.n, (long) r());
    }

    public int r() {
        if (ALog.sEnable) {
            return 10000;
        }
        return a;
    }

    private boolean s() {
        return Build.VERSION.SDK_INT >= 28;
    }

    private boolean a(String str) {
        try {
            String str2 = this.e.getPackageManager().getPackageArchiveInfo(str, 1).versionName;
            ALog.d("SdkManager", "" + str + " verName: " + str2);
            if (!TextUtils.isEmpty(str2)) {
                return new Version(str2).compareTo(new Version("2.7.3")) >= 0;
            }
            return false;
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SdkManager"), "isApkSuitableForAndroidPOrAbove exception: ", e);
            return false;
        }
    }
}
