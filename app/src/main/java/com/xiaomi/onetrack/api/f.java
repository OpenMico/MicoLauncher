package com.xiaomi.onetrack.api;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.CrashAnalysis;
import com.xiaomi.onetrack.OnMainThreadException;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.ServiceQualityEvent;
import com.xiaomi.onetrack.b.g;
import com.xiaomi.onetrack.e.a;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.k;
import com.xiaomi.onetrack.util.o;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.q;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.v;
import com.xiaomi.onetrack.util.w;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class f {
    private static final String a = "OneTrackImp";
    private static ExecutorService c;
    private d b;
    private Context d;
    private e e;
    private Configuration f;
    private OneTrack.ICommonPropertyProvider g;
    private OneTrack.IEventHook h;
    private v i;
    private BroadcastReceiver j = new w(this);

    public f(Context context, Configuration configuration) {
        Context applicationContext = context.getApplicationContext();
        this.d = applicationContext;
        this.f = configuration;
        b(applicationContext);
        Log.d(a, "OneTrackImp init : " + configuration.toString());
    }

    private void b(Context context) {
        p.a();
        q.a(this.f.isInternational(), this.f.getRegion(), this.f.getMode());
        if (c == null) {
            c = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        }
        this.i = new v(this.f);
        if (!q.a() || !e() || !c()) {
            o.a().a((Boolean) false);
            this.b = new af(context, this.f, this.i);
        } else {
            o.a().a((Boolean) true);
            this.b = new ah(this.f, this.i);
        }
        if (this.f.getMode() == OneTrack.Mode.APP) {
            q.a(this.f.isOverrideMiuiRegionSetting());
            c(context);
            if (this.f.isExceptionCatcherEnable()) {
                CrashAnalysis.start(context, this);
                if (!CrashAnalysis.isSupport()) {
                    this.e = new e();
                    this.e.a();
                }
            }
        }
        d(context);
        c.execute(new g(this));
    }

    private boolean c() {
        if (!this.f.isOverrideMiuiRegionSetting()) {
            return true;
        }
        return TextUtils.equals(q.z(), this.f.getRegion());
    }

    public void a(String str, String str2, Map<String, Object> map) {
        c.execute(new r(this, str2, map, str));
    }

    public void a(String str, Map<String, Object> map) {
        c.execute(new y(this, str, map));
    }

    public void a(String str, String str2) {
        c.execute(new z(this, str, str2));
    }

    public void a(String str, String str2, String str3, String str4, String str5, long j) {
        c.execute(new aa(this, str, str2, str3, str5, str4, j));
    }

    public void a(Map<String, Object> map) {
        c.execute(new ab(this, map));
    }

    public void a(String str, Object obj) {
        c.execute(new ac(this, obj, str));
    }

    public void a(String str, OneTrack.UserIdType userIdType, Map<String, Object> map, boolean z) {
        c.execute(new ad(this, str, userIdType, z, map));
    }

    public void b(Map<String, ? extends Number> map) {
        c.execute(new ae(this, map));
    }

    public void a(String str, Number number) {
        c.execute(new h(this, str, number));
    }

    public void a(Map<String, Object> map, boolean z) {
        c.execute(new i(this, z, map));
    }

    private void c(Context context) {
        ((Application) context).registerActivityLifecycleCallbacks(new j(this));
    }

    public void d() {
        c.execute(new k(this));
    }

    public void a(String str, boolean z) {
        c.execute(new l(this, str, z));
    }

    public void a(String str, long j) {
        c.execute(new m(this, str, j));
    }

    public void c(boolean z) {
        c.execute(new n(this, z));
    }

    private boolean e() {
        if (p.a) {
            p.a(a, "enable:" + f() + " isSupportEmptyEvent: " + g());
        }
        return f() && g();
    }

    private boolean f() {
        try {
            int componentEnabledSetting = a.a().getPackageManager().getComponentEnabledSetting(new ComponentName("com.miui.analytics", aj.b));
            return componentEnabledSetting == 1 || componentEnabledSetting == 0;
        } catch (Exception e) {
            p.b(a, "enable error:" + e.toString());
            return false;
        }
    }

    private static boolean g() {
        int i;
        try {
            i = a.a().getPackageManager().getPackageInfo("com.miui.analytics", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (i >= 2020062900) {
            return true;
        }
        p.a(a, "system analytics version: " + i);
        return false;
    }

    public boolean c(String str) {
        boolean a2 = r.a(str);
        if (!a2) {
            p.b(a, String.format("Invalid eventname: %s. Eventname can only consist of numbers, letters, underscores ,and can not start with a number or \"onetrack_\" or \"ot_\"", str));
        }
        return !a2;
    }

    public void c(Map<String, Object> map) {
        if (map != null) {
            c.execute(new o(this, map));
        }
    }

    public void a() {
        c.execute(new p(this));
    }

    public void a(String str) {
        c.execute(new q(this, str));
    }

    public void a(OneTrack.ICommonPropertyProvider iCommonPropertyProvider) {
        this.g = iCommonPropertyProvider;
    }

    public JSONObject d(String str) {
        JSONObject jSONObject;
        try {
            JSONObject a2 = r.a(this.g != null ? this.g.getDynamicProperty(str) : null, false);
            String a3 = k.a(r.a(this.f));
            if (!TextUtils.isEmpty(a3)) {
                jSONObject = new JSONObject(a3);
            } else {
                jSONObject = null;
            }
            return r.a(a2, jSONObject);
        } catch (Exception e) {
            p.b(a, "getCommonProperty: " + e.toString());
            return null;
        }
    }

    public void b(String str) {
        c.execute(new s(this, str));
    }

    public String b() throws OnMainThreadException {
        if (!w.a()) {
            return o.a().b();
        }
        throw new OnMainThreadException("Can't call this method on main thread");
    }

    public String a(Context context) throws OnMainThreadException {
        if (!w.a()) {
            return DeviceUtil.y(context);
        }
        throw new OnMainThreadException("Can't call this method on main thread");
    }

    public void a(ServiceQualityEvent serviceQualityEvent) {
        if (serviceQualityEvent != null) {
            c.execute(new t(this, serviceQualityEvent));
        }
    }

    public void a(boolean z) {
        p.a = z;
    }

    private void h() {
        c.execute(new u(this));
    }

    public void i() {
        if (g.d()) {
            c.execute(new v(this));
        }
    }

    public void j() {
        try {
            if (this.f.getMode() == OneTrack.Mode.APP) {
                long c2 = a.c();
                String a2 = a(c2, a.b());
                String A = aa.A();
                if (TextUtils.isEmpty(A)) {
                    aa.j(a2);
                    return;
                }
                JSONObject jSONObject = new JSONObject(A);
                long optLong = jSONObject.optLong(b.X);
                String optString = jSONObject.optString(b.Y);
                if (optLong != c2) {
                    aa.j(a2);
                    this.b.a(b.j, c.a(optLong, optString, c2, a.e(), this.f, this.h, this.i));
                }
            }
        } catch (Exception e) {
            p.b(a, "trackUpgradeEvent error: " + e.toString());
        }
    }

    private String a(long j, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b.Y, str);
        jSONObject.put(b.X, j);
        return jSONObject.toString();
    }

    public void a(OneTrack.IEventHook iEventHook) {
        this.h = iEventHook;
        this.i.a(iEventHook);
    }

    private void d(Context context) {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            context.registerReceiver(this.j, intentFilter);
        } catch (Exception unused) {
        }
    }

    public void b(boolean z) {
        if (this.f.isUseCustomPrivacyPolicy()) {
            c.execute(new x(this, z));
        }
    }
}
