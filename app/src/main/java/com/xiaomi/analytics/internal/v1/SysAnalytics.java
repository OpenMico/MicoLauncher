package com.xiaomi.analytics.internal.v1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import com.miui.analytics.ICore;
import com.xiaomi.analytics.internal.Constants;
import com.xiaomi.analytics.internal.Version;
import com.xiaomi.analytics.internal.util.ALog;
import com.xiaomi.analytics.internal.util.AndroidUtils;
import com.xiaomi.analytics.internal.util.TimeUtils;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/* loaded from: classes3.dex */
public class SysAnalytics implements AnalyticsInterface {
    private boolean a;
    private ICore f;
    private Context g;
    private boolean b = false;
    private boolean c = false;
    private final Object d = new Object();
    private final Object e = new Object();
    private final Set<String> h = new ConcurrentSkipListSet();
    private ServiceConnection i = new ServiceConnection() { // from class: com.xiaomi.analytics.internal.v1.SysAnalytics.1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(ALog.addPrefix("SysAnalytics"), String.format("onServiceDisconnected, pid:%d, tid:%d", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid())));
            SysAnalytics.this.b = false;
            SysAnalytics.this.f = null;
            SysAnalytics.this.c = false;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SysAnalytics.this.b = true;
            SysAnalytics.this.c = false;
            SysAnalytics.this.f = ICore.Stub.asInterface(iBinder);
            Log.i(ALog.addPrefix("SysAnalytics"), String.format("onServiceConnected %s, pid:%d, tid:%d", SysAnalytics.this.f, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid())));
            synchronized (SysAnalytics.this.d) {
                try {
                    SysAnalytics.this.d.notifyAll();
                } catch (Exception e) {
                    Log.e(ALog.addPrefix("SysAnalytics"), "onServiceConnected notifyAll exception:", e);
                }
            }
            SysAnalytics.this.c();
        }
    };

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void close() {
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void init() {
    }

    public SysAnalytics(Context context) {
        this.a = false;
        this.g = AndroidUtils.getApplicationContext(context);
        this.a = isServiceBuiltIn(context);
        a();
    }

    private void a() {
        if (this.a) {
            try {
                Intent intent = new Intent();
                intent.setClassName("com.miui.analytics", "com.miui.analytics.AnalyticsService");
                this.g.bindService(intent, this.i, 1);
                this.c = true;
                ALog.i("SysAnalytics", "try bind sys service");
            } catch (Exception e) {
                Log.e(ALog.addPrefix("SysAnalytics"), "bind service exception:", e);
            }
        }
    }

    public static boolean isServiceBuiltIn(Context context) {
        List<ResolveInfo> queryIntentServices;
        try {
            Intent intent = new Intent();
            intent.setClassName("com.miui.analytics", "com.miui.analytics.AnalyticsService");
            if (!(context == null || context.getPackageManager() == null || (queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0)) == null)) {
                if (queryIntentServices.size() > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SysAnalytics"), "isServiceBuiltIn exception:", e);
        }
        return false;
    }

    private void b() {
        synchronized (this.e) {
            if (this.c || (this.b && this.f != null)) {
                Object[] objArr = new Object[3];
                int i = 0;
                objArr[0] = Boolean.valueOf(this.c);
                objArr[1] = Boolean.valueOf(this.b);
                if (this.f != null) {
                    i = 1;
                }
                objArr[2] = Integer.valueOf(i);
                ALog.i("SysAnalytics", String.format("ensureService mConnecting:%s, mConnected:%s, mAnalytics:%d", objArr));
            } else {
                this.g.unbindService(this.i);
                a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        new Thread(new Runnable() { // from class: com.xiaomi.analytics.internal.v1.SysAnalytics.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (SysAnalytics.this.h) {
                    try {
                        if (!SysAnalytics.this.h.isEmpty()) {
                            Class.forName("com.miui.analytics.ICore").getMethod("trackEvents", String[].class).invoke(SysAnalytics.this.f, (String[]) SysAnalytics.this.h.toArray(new String[SysAnalytics.this.h.size()]));
                            ALog.i("SysAnalytics", String.format("onServiceConnected drain %d pending events", Integer.valueOf(SysAnalytics.this.h.size())));
                            SysAnalytics.this.h.clear();
                        }
                    } catch (Exception e) {
                        Log.e(ALog.addPrefix("SysAnalytics"), "onServiceConnected drain pending events exception:", e);
                    }
                }
            }
        }).start();
    }

    public boolean isOnline() {
        return this.a;
    }

    public boolean isReady() {
        return this.a && this.b;
    }

    public void waitForConnected() {
        if (this.a && !this.b) {
            synchronized (this.d) {
                try {
                    this.d.wait(TimeUtils.ONE_SECOND_IN_MS * 3);
                } catch (Exception e) {
                    Log.e(ALog.addPrefix("SysAnalytics"), "waitForConnected mSyncGuard.wait exception:", e);
                }
            }
        }
    }

    private String d() {
        try {
            b();
            return this.f != null ? (String) Class.forName("com.miui.analytics.ICore").getMethod("getVersionName", new Class[0]).invoke(this.f, new Object[0]) : Constants.INVALID_CORE_VER_STR;
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SysAnalytics"), "getVersionName exception:", e);
            return Constants.INVALID_CORE_VER_STR;
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public Version getVersion() {
        return new Version(d());
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void trackEvent(String str) {
        try {
            b();
            if (this.f == null) {
                synchronized (this.h) {
                    this.h.add(str);
                }
                ALog.i("SysAnalytics", "add 1 event into pending event list");
            } else {
                Class.forName("com.miui.analytics.ICore").getMethod("trackEvent", String.class).invoke(this.f, str);
            }
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SysAnalytics"), "trackEvent exception:", e);
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void trackEvents(String[] strArr) {
        try {
            b();
            if (this.f == null) {
                synchronized (this.h) {
                    if (strArr != null && strArr.length > 0) {
                        Collections.addAll(this.h, strArr);
                    }
                }
                Object[] objArr = new Object[1];
                objArr[0] = Integer.valueOf(strArr == null ? 0 : strArr.length);
                ALog.i("SysAnalytics", String.format("add %d events into pending event list", objArr));
                return;
            }
            Class.forName("com.miui.analytics.ICore").getMethod("trackEvents", String[].class).invoke(this.f, strArr);
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SysAnalytics"), "trackEvents exception:", e);
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public String getClientExtra(String str) {
        try {
            b();
            return this.f != null ? (String) Class.forName("com.miui.analytics.ICore").getMethod("getClientExtra", String.class, String.class).invoke(this.f, this.g.getPackageName(), str) : "";
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SysAnalytics"), "getClientExtra exception:", e);
            return "";
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public boolean isPolicyReady(String str) {
        try {
            b();
            if (this.f != null) {
                return ((Boolean) Class.forName("com.miui.analytics.ICore").getMethod("isPolicyReady", String.class, String.class).invoke(this.f, this.g.getPackageName(), str)).booleanValue();
            }
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SysAnalytics"), "isPolicyReady exception:", e);
        }
        return false;
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void setDebugOn(boolean z) {
        try {
            b();
            if (this.f != null) {
                Class.forName("com.miui.analytics.ICore").getMethod("setDebugOn", Boolean.TYPE).invoke(this.f, Boolean.valueOf(z));
            }
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SysAnalytics"), "setDebugOn exception:", e);
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void setDefaultPolicy(String str, String str2) {
        try {
            b();
            if (this.f != null) {
                Class.forName("com.miui.analytics.ICore").getMethod("setDefaultPolicy", String.class, String.class).invoke(this.f, str, str2);
            }
        } catch (Throwable th) {
            Log.e(ALog.addPrefix("SysAnalytics"), "setDefaultPolicy exception:", th);
        }
    }

    @Override // com.xiaomi.analytics.internal.v1.AnalyticsInterface
    public void deleteAllEvents(String str) {
        try {
            ALog.d("SysAnalytics", "deleteAllEvents");
            b();
            if (this.f != null) {
                Class.forName("com.miui.analytics.ICore").getMethod("deleteAllEvents", String.class).invoke(this.f, str);
            }
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SysAnalytics"), "deleteAllEvents exception:", e);
        }
    }
}
