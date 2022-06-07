package com.xiaomi.analytics.internal.v1;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;
import com.xiaomi.analytics.internal.Version;
import com.xiaomi.analytics.internal.util.ALog;
import com.xiaomi.analytics.internal.util.AndroidUtils;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import dalvik.system.DexClassLoader;

/* loaded from: classes3.dex */
public class DexAnalytics implements AnalyticsInterface {
    private Context a;
    private ClassLoader b;
    private int c;
    private String d;
    private String e;
    private String f;
    private volatile boolean g;

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void close() {
    }

    public DexAnalytics(Context context, String str, String str2) {
        this.d = "";
        this.a = AndroidUtils.getApplicationContext(context);
        this.e = str;
        this.f = str2;
        PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(str, 1);
        this.c = packageArchiveInfo.versionCode;
        this.d = packageArchiveInfo.versionName;
    }

    private void a() {
        try {
            this.b.loadClass("com.miui.analytics.Analytics").getDeclaredMethod("initialize", Context.class, Integer.TYPE, String.class).invoke(null, this.a, Integer.valueOf(this.c), this.d);
        } catch (Throwable th) {
            Log.w(ALog.addPrefix("DexAnalytics"), "initAnalytics exception", th);
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void init() {
        try {
            if (!this.g) {
                this.b = new DexClassLoader(this.e, this.a.getDir("dex", 0).getAbsolutePath(), this.f, ClassLoader.getSystemClassLoader());
                a();
                this.g = true;
                ALog.d("DexAnalytics", MicoSettings.Secure.KEY_INITIALIZED);
            }
        } catch (Exception e) {
            Log.e(ALog.addPrefix("DexAnalytics"), "init e", e);
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public Version getVersion() {
        return new Version(this.d);
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public String getClientExtra(String str) {
        try {
            init();
            return (String) this.b.loadClass("com.miui.analytics.Analytics").getDeclaredMethod("getClientExtra", String.class, String.class).invoke(null, this.a.getPackageName(), str);
        } catch (Throwable th) {
            Log.w(ALog.addPrefix("DexAnalytics"), "getClientExtra exception", th);
            return "";
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public boolean isPolicyReady(String str) {
        try {
            init();
            return ((Boolean) this.b.loadClass("com.miui.analytics.Analytics").getDeclaredMethod("isPolicyReady", String.class, String.class).invoke(null, this.a.getPackageName(), str)).booleanValue();
        } catch (Throwable th) {
            Log.w(ALog.addPrefix("DexAnalytics"), "isPolicyReady exception", th);
            return false;
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void trackEvent(String str) {
        try {
            init();
            this.b.loadClass("com.miui.analytics.Analytics").getDeclaredMethod("trackEvent", String.class).invoke(null, str);
        } catch (Throwable th) {
            Log.w(ALog.addPrefix("DexAnalytics"), "trackEvent exception", th);
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void trackEvents(String[] strArr) {
        try {
            init();
            this.b.loadClass("com.miui.analytics.Analytics").getDeclaredMethod("trackEvents", String[].class).invoke(null, strArr);
        } catch (Throwable th) {
            Log.w(ALog.addPrefix("DexAnalytics"), "trackEvents exception", th);
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void setDebugOn(boolean z) {
        try {
            init();
            this.b.loadClass("com.miui.analytics.Analytics").getDeclaredMethod("setDebugOn", Boolean.TYPE).invoke(null, Boolean.valueOf(z));
        } catch (Throwable th) {
            Log.w(ALog.addPrefix("DexAnalytics"), "setDebugOn exception", th);
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void setDefaultPolicy(String str, String str2) {
        try {
            init();
            this.b.loadClass("com.miui.analytics.Analytics").getDeclaredMethod("setDefaultPolicy", String.class, String.class).invoke(null, str, str2);
        } catch (Throwable th) {
            Log.w(ALog.addPrefix("DexAnalytics"), "setDefaultPolicy exception", th);
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void deleteAllEvents(String str) {
        try {
            init();
            this.b.loadClass("com.miui.analytics.Analytics").getDeclaredMethod("deleteAllEvents", String.class).invoke(null, str);
        } catch (Throwable th) {
            Log.w(ALog.addPrefix("DexAnalytics"), "deleteAllEvents exception", th);
        }
    }
}
