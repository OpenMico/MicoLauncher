package com.umeng.analytics.pro;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.b;
import com.umeng.analytics.pro.d;
import com.umeng.analytics.pro.h;
import com.umeng.analytics.vshelper.PageNameMonitor;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.hapjs.features.channel.IChannel;
import org.json.JSONArray;
import org.json.JSONObject;
import xcrash.TombstoneParser;

/* compiled from: AutoViewPageTracker.java */
/* loaded from: classes2.dex */
public class k {
    public static String a;
    boolean c;
    boolean d;
    com.umeng.analytics.vshelper.a g;
    Application.ActivityLifecycleCallbacks h;
    private final Map<String, Long> i;
    private boolean m;
    private int n;
    private int o;
    private static JSONArray j = new JSONArray();
    private static Object k = new Object();
    private static Application l = null;
    public static MobclickAgent.PageMode b = MobclickAgent.PageMode.AUTO;
    static String e = null;
    static int f = -1;
    private static boolean p = true;
    private static Object q = new Object();

    static /* synthetic */ int a(k kVar) {
        int i = kVar.o;
        kVar.o = i - 1;
        return i;
    }

    static /* synthetic */ int b(k kVar) {
        int i = kVar.n;
        kVar.n = i - 1;
        return i;
    }

    static /* synthetic */ int e(k kVar) {
        int i = kVar.o;
        kVar.o = i + 1;
        return i;
    }

    static /* synthetic */ int f(k kVar) {
        int i = kVar.n;
        kVar.n = i + 1;
        return i;
    }

    public boolean a() {
        return this.m;
    }

    /* compiled from: AutoViewPageTracker.java */
    /* loaded from: classes2.dex */
    public static class a {
        private static final k a = new k();

        private a() {
        }
    }

    public static synchronized k a(Context context) {
        k kVar;
        synchronized (k.class) {
            if (l == null && context != null) {
                if (context instanceof Activity) {
                    l = ((Activity) context).getApplication();
                } else if (context instanceof Application) {
                    l = (Application) context;
                }
            }
            kVar = a.a;
        }
        return kVar;
    }

    public void b(Context context) {
        synchronized (q) {
            if (p) {
                p = false;
                Activity globleActivity = DeviceConfig.getGlobleActivity(context);
                if (globleActivity == null) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> init触发onResume: 无前台Activity，直接退出。");
                    return;
                }
                String localClassName = globleActivity.getLocalClassName();
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> init触发onResume: 补救成功，前台Activity名：" + localClassName);
                a(globleActivity);
                return;
            }
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> init触发onResume: firstResumeCall = false，直接返回。");
        }
    }

    private k() {
        this.i = new HashMap();
        this.m = false;
        this.c = false;
        this.d = false;
        this.n = 0;
        this.o = 0;
        this.g = PageNameMonitor.getInstance();
        this.h = new Application.ActivityLifecycleCallbacks() { // from class: com.umeng.analytics.pro.k.1
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                MobclickAgent.PageMode pageMode = k.b;
                MobclickAgent.PageMode pageMode2 = MobclickAgent.PageMode.AUTO;
                if (activity == null) {
                    return;
                }
                if (activity.isChangingConfigurations()) {
                    k.a(k.this);
                    return;
                }
                k.b(k.this);
                if (k.this.n > 0) {
                    return;
                }
                if (k.f == 0 && UMUtils.isMainProgress(activity)) {
                    return;
                }
                if ((k.f == 1 || (k.f == 0 && !UMUtils.isMainProgress(activity))) && activity != null) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("pairUUID", k.e);
                    hashMap.put(IChannel.EXTRA_CLOSE_REASON, "Normal");
                    hashMap.put(TombstoneParser.keyProcessId, Integer.valueOf(Process.myPid()));
                    hashMap.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(activity) ? 1 : 0));
                    hashMap.put("activityName", activity.toString());
                    b a2 = b.a();
                    if (a2 != null) {
                        a2.a((Context) activity, "$$_onUMengEnterBackground", (Map<String, Object>) hashMap);
                    }
                    if (k.e != null) {
                        k.e = null;
                    }
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                if (activity != null) {
                    if (k.this.n <= 0) {
                        if (k.e == null) {
                            k.e = UUID.randomUUID().toString();
                        }
                        if (k.f == -1) {
                            k.f = activity.isTaskRoot() ? 1 : 0;
                        }
                        if (k.f == 0 && UMUtils.isMainProgress(activity)) {
                            HashMap hashMap = new HashMap();
                            hashMap.put("activityName", activity.toString());
                            hashMap.put(TombstoneParser.keyProcessId, Integer.valueOf(Process.myPid()));
                            hashMap.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(activity) ? 1 : 0));
                            b a2 = b.a();
                            if (a2 != null) {
                                a2.a((Context) activity, "$$_onUMengEnterForegroundInitError", (Map<String, Object>) hashMap);
                            }
                            k.f = -2;
                            if (UMConfigure.isDebugLog()) {
                                UMLog.mutlInfo(2, i.ar);
                            }
                        } else if (k.f == 1 || !UMUtils.isMainProgress(activity)) {
                            HashMap hashMap2 = new HashMap();
                            hashMap2.put("pairUUID", k.e);
                            hashMap2.put(TombstoneParser.keyProcessId, Integer.valueOf(Process.myPid()));
                            hashMap2.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(activity) ? 1 : 0));
                            hashMap2.put("activityName", activity.toString());
                            if (b.a() != null) {
                                b.a().a((Context) activity, "$$_onUMengEnterForeground", (Map<String, Object>) hashMap2);
                            }
                        }
                    }
                    if (k.this.o < 0) {
                        k.e(k.this);
                    } else {
                        k.f(k.this);
                    }
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.F)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityResumed: FirstResumeTrigger enabled.");
                    synchronized (k.q) {
                        if (k.p) {
                            boolean unused = k.p = false;
                        }
                    }
                    k.this.a(activity);
                    return;
                }
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityResumed: FirstResumeTrigger disabled.");
                k.this.a(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.F)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityPaused: FirstResumeTrigger enabled.");
                    synchronized (k.q) {
                        if (k.p) {
                            return;
                        }
                    }
                } else {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityPaused: FirstResumeTrigger disabled.");
                }
                if (k.b == MobclickAgent.PageMode.AUTO) {
                    k.this.c(activity);
                    b.a().i();
                    k.this.c = false;
                }
            }
        };
        synchronized (this) {
            if (l != null) {
                f();
            }
        }
    }

    private void f() {
        if (!this.m) {
            this.m = true;
            if (l != null && Build.VERSION.SDK_INT >= 14) {
                l.registerActivityLifecycleCallbacks(this.h);
            }
        }
    }

    public void b() {
        this.m = false;
        if (l != null) {
            if (Build.VERSION.SDK_INT >= 14) {
                l.unregisterActivityLifecycleCallbacks(this.h);
            }
            l = null;
        }
    }

    public void c() {
        c((Activity) null);
        b();
    }

    public static void a(Context context, String str) {
        if (f == 1 && UMUtils.isMainProgress(context)) {
            HashMap hashMap = new HashMap();
            hashMap.put("pairUUID", e);
            hashMap.put(IChannel.EXTRA_CLOSE_REASON, str);
            if (e != null) {
                e = null;
            }
            if (context != null) {
                hashMap.put(TombstoneParser.keyProcessId, Integer.valueOf(Process.myPid()));
                hashMap.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(context) ? 1 : 0));
                hashMap.put("Context", context.toString());
                b.a().a(context, "$$_onUMengEnterBackground", (Map<String, Object>) hashMap);
            }
        }
    }

    public static void c(Context context) {
        String jSONArray;
        if (context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                synchronized (k) {
                    jSONArray = j.toString();
                    j = new JSONArray();
                }
                if (jSONArray.length() > 0) {
                    jSONObject.put(d.C0138d.a.c, new JSONArray(jSONArray));
                    h.a(context).a(t.a().c(), jSONObject, h.a.AUTOPAGE);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public void a(Activity activity) {
        if (b == MobclickAgent.PageMode.AUTO && activity != null) {
            String str = activity.getPackageName() + "." + activity.getLocalClassName();
            this.g.activityResume(str);
            if (this.c) {
                this.c = false;
                if (TextUtils.isEmpty(a)) {
                    a = str;
                } else if (!a.equals(str)) {
                    b(activity);
                    synchronized (q) {
                        b.a().h();
                    }
                }
            } else {
                b(activity);
                synchronized (q) {
                    b.a().h();
                }
            }
        }
    }

    private void b(Activity activity) {
        a = activity.getPackageName() + "." + activity.getLocalClassName();
        synchronized (this.i) {
            this.i.put(a, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public void c(Activity activity) {
        long j2;
        long j3;
        try {
            synchronized (this.i) {
                if (a == null && activity != null) {
                    a = activity.getPackageName() + "." + activity.getLocalClassName();
                }
                j2 = 0;
                if (TextUtils.isEmpty(a) || !this.i.containsKey(a)) {
                    j3 = 0;
                } else {
                    long longValue = this.i.get(a).longValue();
                    j2 = System.currentTimeMillis() - longValue;
                    this.i.remove(a);
                    j3 = longValue;
                }
            }
            synchronized (k) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(c.v, a);
                    jSONObject.put("duration", j2);
                    jSONObject.put(c.x, j3);
                    jSONObject.put("type", 0);
                    j.put(jSONObject);
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable unused2) {
        }
    }
}
