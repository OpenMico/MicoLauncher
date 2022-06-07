package com.xiaomi.onetrack.e;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.onetrack.BuildConfig;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.c;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.o;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.q;
import com.xiaomi.onetrack.util.v;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    private static final String d = "Event";
    private long e;
    private String f;
    private String g;
    private String h;
    private int i;
    private JSONObject j;
    private long k;

    /* renamed from: com.xiaomi.onetrack.e.b$b */
    /* loaded from: classes4.dex */
    public static class C0178b {
        public static String A = "sdk_mode";
        public static String B = "ot_first_day";
        public static String C = "ot_test_env";
        public static String D = "ot_privacy_policy";
        public static String a = "event";
        public static String b = "imei";
        public static String c = "oaid";
        public static String d = "sn";
        public static String e = "gaid";
        public static String f = "android_id";
        public static String g = "instance_id";
        public static String h = "mfrs";
        public static String i = "model";
        public static String j = "platform";
        public static String k = "miui";
        public static String l = "build";
        public static String m = "os_ver";
        public static String n = "app_id";
        public static String o = "app_ver";
        public static String p = "pkg";
        public static String q = "channel";
        public static String r = "e_ts";
        public static String s = "tz";
        public static String t = "net";
        public static String u = "region";
        public static String v = "plugin_id";
        public static String w = "sdk_ver";
        public static String x = "uid";
        public static String y = "uid_type";
        public static String z = "sid";
    }

    public b() {
    }

    public long a() {
        return this.e;
    }

    public void a(long j) {
        this.e = j;
    }

    public String b() {
        return this.f;
    }

    public void a(String str) {
        this.f = str;
    }

    public String c() {
        return this.g;
    }

    public void b(String str) {
        this.g = str;
    }

    public String d() {
        return this.h;
    }

    public void c(String str) {
        this.h = str;
    }

    public int e() {
        return this.i;
    }

    public void a(int i) {
        this.i = i;
    }

    public JSONObject f() {
        return this.j;
    }

    public void a(JSONObject jSONObject) {
        this.j = jSONObject;
    }

    public long g() {
        return this.k;
    }

    public void b(long j) {
        this.k = j;
    }

    public boolean h() {
        try {
            if (this.j == null || !this.j.has(c.b) || !this.j.has("B") || TextUtils.isEmpty(this.f)) {
                return false;
            }
            return !TextUtils.isEmpty(this.g);
        } catch (Exception e) {
            p.b(d, "check event isValid error, ", e);
            return false;
        }
    }

    private b(a aVar) {
        this.e = aVar.a;
        this.f = aVar.b;
        this.g = aVar.c;
        this.h = aVar.d;
        this.i = aVar.e;
        this.j = aVar.f;
        this.k = aVar.g;
    }

    /* loaded from: classes4.dex */
    public static class a {
        private long a;
        private String b;
        private String c;
        private String d;
        private int e;
        private JSONObject f;
        private long g;

        public a a(long j) {
            this.a = this.a;
            return this;
        }

        public a a(String str) {
            this.b = str;
            return this;
        }

        public a b(String str) {
            this.c = str;
            return this;
        }

        public a c(String str) {
            this.d = str;
            return this;
        }

        public a a(int i) {
            this.e = i;
            return this;
        }

        public a a(JSONObject jSONObject) {
            this.f = jSONObject;
            return this;
        }

        public a b(long j) {
            this.g = j;
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public static JSONObject a(String str, Configuration configuration, OneTrack.IEventHook iEventHook, v vVar) throws JSONException {
        return a(str, configuration, iEventHook, "", vVar);
    }

    public static JSONObject a(String str, Configuration configuration, OneTrack.IEventHook iEventHook, String str2, v vVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        Context a2 = a.a();
        jSONObject.put(C0178b.a, str);
        if (!(q.a() ? q.x() : configuration.isInternational())) {
            jSONObject.put(C0178b.b, DeviceUtil.b(a2));
            jSONObject.put(C0178b.c, com.xiaomi.onetrack.util.oaid.a.a().a(a2));
        } else if (iEventHook != null && iEventHook.isRecommendEvent(str)) {
            String p = DeviceUtil.p(a2);
            if (!TextUtils.isEmpty(p)) {
                jSONObject.put(C0178b.e, p);
            }
        }
        jSONObject.put(C0178b.g, o.a().b());
        jSONObject.put(C0178b.h, DeviceUtil.d());
        jSONObject.put(C0178b.i, DeviceUtil.c());
        jSONObject.put(C0178b.j, "Android");
        jSONObject.put(C0178b.k, q.h());
        jSONObject.put(C0178b.l, q.d());
        jSONObject.put(C0178b.m, q.i());
        jSONObject.put(C0178b.o, a.b());
        jSONObject.put(C0178b.r, System.currentTimeMillis());
        jSONObject.put(C0178b.s, q.b());
        jSONObject.put(C0178b.t, com.xiaomi.onetrack.f.c.a(a2).toString());
        jSONObject.put(C0178b.u, q.y());
        jSONObject.put(C0178b.w, BuildConfig.SDK_VERSION);
        jSONObject.put(C0178b.n, configuration.getAppId());
        jSONObject.put(C0178b.p, a.d());
        jSONObject.put(C0178b.q, !TextUtils.isEmpty(configuration.getChannel()) ? configuration.getChannel() : "default");
        a(jSONObject, configuration, str2);
        a(jSONObject, a2);
        jSONObject.put(C0178b.z, q.r());
        jSONObject.put(C0178b.A, (configuration.getMode() != null ? configuration.getMode() : OneTrack.Mode.APP).getType());
        jSONObject.put(C0178b.B, ac.d(aa.B()));
        if (p.c) {
            jSONObject.put(C0178b.C, true);
        }
        jSONObject.put(C0178b.D, vVar.a());
        return jSONObject;
    }

    private static void a(JSONObject jSONObject, Context context) throws JSONException {
        String u = aa.u();
        String w = aa.w();
        if (!TextUtils.isEmpty(u) && !TextUtils.isEmpty(w)) {
            jSONObject.put(C0178b.x, u);
            jSONObject.put(C0178b.y, w);
        }
    }

    private static void a(JSONObject jSONObject, Configuration configuration, String str) throws JSONException {
        if (!TextUtils.isEmpty(str)) {
            jSONObject.put(C0178b.v, str);
        } else {
            jSONObject.put(C0178b.v, configuration.getPluginId());
        }
    }
}
