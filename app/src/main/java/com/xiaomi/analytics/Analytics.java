package com.xiaomi.analytics;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.xiaomi.analytics.LogEvent;
import com.xiaomi.analytics.internal.Debugger;
import com.xiaomi.analytics.internal.SdkManager;
import com.xiaomi.analytics.internal.util.ALog;
import com.xiaomi.analytics.internal.util.AndroidUtils;
import com.xiaomi.analytics.internal.util.CertificateUtils;
import com.xiaomi.analytics.internal.util.SysUtils;
import com.xiaomi.analytics.internal.v1.AnalyticsInterface;
import com.xiaomi.analytics.internal.v1.SysAnalytics;
import java.util.concurrent.TimeoutException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class Analytics {
    private static volatile boolean c = true;
    private static volatile Analytics d;
    private b<Tracker> a = new b<>();
    private Context b;

    private Analytics(Context context) {
        this.b = AndroidUtils.getApplicationContext(context);
        a.a(this.b);
        a();
        SdkManager.getInstance(this.b);
        Debugger.getDebugger(this.b).register();
        SysUtils.deleteDeviceIdInSpFile(this.b);
    }

    private void a() {
        new Tracker("");
    }

    public static synchronized Analytics getInstance(Context context) {
        Analytics analytics;
        synchronized (Analytics.class) {
            if (d == null) {
                d = new Analytics(context);
            }
            analytics = d;
        }
        return analytics;
    }

    public Tracker getTracker(String str) {
        return this.a.a(Tracker.class, str);
    }

    public void setDebugOn(boolean z) {
        ALog.sEnable = z;
        AnalyticsInterface analytics = SdkManager.getInstance(this.b).getAnalytics();
        if (analytics != null) {
            analytics.setDebugOn(z);
        }
    }

    public static void setUseSystemAnalyticsOnly(Context context) {
        if (!SysAnalytics.isServiceBuiltIn(context)) {
            Log.e(ALog.addPrefix("Analytics"), "system analytics is not exist.");
            return;
        }
        Log.d(ALog.addPrefix("Analytics"), "use system analytics only");
        SdkManager.setUseSysAnalyticsOnly();
        setUpdateEnable(false);
    }

    public void deleteAllEvents() {
        AnalyticsInterface analytics = SdkManager.getInstance(this.b).getAnalytics();
        if (analytics != null) {
            analytics.deleteAllEvents(this.b.getPackageName());
        }
    }

    public void setPolicyConfiguration(PolicyConfiguration policyConfiguration) {
        SdkManager.getInstance(this.b).setPolicyConfiguration(policyConfiguration);
    }

    public void setDontUseSystemAnalytics(boolean z) {
        SdkManager.getInstance(this.b).setDontUseSystemAnalytics(z);
    }

    public String getClientExtraSync(String str) throws TimeoutException {
        return getClientExtraSync(str, 5000);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x002c, code lost:
        if (r0.isPolicyReady(r4) != false) goto L_0x002e;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getClientExtraSync(final java.lang.String r4, final int r5) throws java.util.concurrent.TimeoutException {
        /*
            r3 = this;
            java.util.concurrent.FutureTask r0 = new java.util.concurrent.FutureTask     // Catch: Exception -> 0x002f
            com.xiaomi.analytics.Analytics$1 r1 = new com.xiaomi.analytics.Analytics$1     // Catch: Exception -> 0x002f
            r1.<init>()     // Catch: Exception -> 0x002f
            r0.<init>(r1)     // Catch: Exception -> 0x002f
            com.xiaomi.analytics.internal.util.TaskRunner.execute(r0)     // Catch: Exception -> 0x002f
            long r1 = (long) r5     // Catch: Exception -> 0x002f
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: Exception -> 0x002f
            java.lang.Object r5 = r0.get(r1, r5)     // Catch: Exception -> 0x002f
            java.lang.String r5 = (java.lang.String) r5     // Catch: Exception -> 0x002f
            android.content.Context r0 = r3.b     // Catch: Exception -> 0x002f
            com.xiaomi.analytics.internal.SdkManager r0 = com.xiaomi.analytics.internal.SdkManager.getInstance(r0)     // Catch: Exception -> 0x002f
            com.xiaomi.analytics.internal.v1.AnalyticsInterface r0 = r0.getAnalytics()     // Catch: Exception -> 0x002f
            if (r0 == 0) goto L_0x002f
            boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch: Exception -> 0x002f
            if (r1 == 0) goto L_0x002e
            boolean r4 = r0.isPolicyReady(r4)     // Catch: Exception -> 0x002f
            if (r4 == 0) goto L_0x002f
        L_0x002e:
            return r5
        L_0x002f:
            java.util.concurrent.TimeoutException r4 = new java.util.concurrent.TimeoutException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.analytics.Analytics.getClientExtraSync(java.lang.String, int):java.lang.String");
    }

    public static void trackSystem(Context context, String str, Action action) throws Exception {
        if (b(context) || a(context)) {
            Intent intent = new Intent();
            intent.setClassName("com.miui.analytics", "com.miui.analytics.EventService");
            if (str == null) {
                str = "";
            }
            intent.putExtra("key", str);
            intent.putExtra("content", action.a().toString());
            intent.putExtra("extra", action.b().toString());
            if (context.getApplicationContext() != null) {
                intent.putExtra("appid", context.getPackageName());
            }
            if (action instanceof AdAction) {
                intent.putExtra("type", LogEvent.LogType.TYPE_AD.value());
            } else {
                intent.putExtra("type", LogEvent.LogType.TYPE_EVENT.value());
            }
            context.startService(intent);
            return;
        }
        throw new IllegalArgumentException("App is not allowed to use this method to track event, except system or platform signed apps. Use getTracker instead.");
    }

    public static void setUpdateEnable(boolean z) {
        c = z;
    }

    public static boolean isUpdateEnable() {
        return c;
    }

    private static boolean a(Context context) {
        boolean isXiaomiPlatformCertificate = CertificateUtils.isXiaomiPlatformCertificate(AndroidUtils.getSignature(context, context.getPackageName()));
        Log.d(ALog.addPrefix("Analytics"), String.format("%s is platform signatures : %b", context.getPackageName(), Boolean.valueOf(isXiaomiPlatformCertificate)));
        return isXiaomiPlatformCertificate;
    }

    private static boolean b(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        return (applicationInfo == null || (applicationInfo.flags & 1) == 0) ? false : true;
    }

    public void addJavascriptInterface(WebView webView, String str) {
        webView.addJavascriptInterface(this, str);
    }

    @JavascriptInterface
    public void trackCustomAction(String str, String str2) {
        try {
            CustomAction newCustomAction = Actions.newCustomAction();
            try {
                newCustomAction.a(new JSONObject(str2));
            } catch (Exception unused) {
            }
            getTracker(str).track(newCustomAction);
        } catch (Exception e) {
            ALog.e("Analytics", "JavascriptInterface trackCustomAction exception:", e);
        }
    }

    @JavascriptInterface
    public void trackEventAction(String str, String str2, String str3) {
        try {
            EventAction newEventAction = Actions.newEventAction(str2);
            try {
                newEventAction.a(new JSONObject(str3));
            } catch (Exception unused) {
            }
            getTracker(str).track(newEventAction);
        } catch (Exception e) {
            ALog.e("Analytics", "JavascriptInterface trackEventAction exception:", e);
        }
    }

    @JavascriptInterface
    public void trackEventAction(String str, String str2, String str3, String str4) {
        try {
            EventAction newEventAction = Actions.newEventAction(str2, str3);
            try {
                newEventAction.a(new JSONObject(str4));
            } catch (Exception unused) {
            }
            getTracker(str).track(newEventAction);
        } catch (Exception e) {
            ALog.e("Analytics", "JavascriptInterface trackEventAction exception:", e);
        }
    }

    @JavascriptInterface
    public void trackAdAction(String str, String str2, String str3) {
        try {
            AdAction newAdAction = Actions.newAdAction(str2);
            try {
                newAdAction.a(new JSONObject(str3));
            } catch (Exception unused) {
            }
            getTracker(str).track(newAdAction);
        } catch (Exception e) {
            ALog.e("Analytics", "JavascriptInterface trackAdAction exception:", e);
        }
    }

    @JavascriptInterface
    public void trackAdAction(String str, String str2, String str3, String str4) {
        try {
            AdAction newAdAction = Actions.newAdAction(str2, str3);
            try {
                newAdAction.a(new JSONObject(str4));
            } catch (Exception unused) {
            }
            getTracker(str).track(newAdAction);
        } catch (Exception e) {
            ALog.e("Analytics", "JavascriptInterface trackAdAction exception:", e);
        }
    }
}
