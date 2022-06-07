package com.xiaomi.onetrack.util;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.onetrack.a.c;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class x {
    private static final String a = "RegionDomainManager";
    private static final String b = "CN";
    private static final String c = "INTL";
    private static final String d = "IN";
    private static final String e = "RU";
    private static final String f = "http://";
    private static final String g = "https://";
    private static final String h = "tracking.miui.com";
    private static final String i = "tracking.intl.miui.com";
    private static final String j = "tracking.rus.miui.com";
    private static final String k = "tracking.india.miui.com";
    private static final String l = "sdkconfig.ad.xiaomi.com";
    private static final String m = "sdkconfig.ad.intl.xiaomi.com";
    private static final String n = "sdkconfig.ad.india.xiaomi.com";
    private static final String o = "sdkconfig.ad.rus.xiaomi.com";
    private static final String p = "staging.sdkconfig.ad.xiaomi.srv";
    private static final String q = "staging.tracking.miui.com";
    private static final String r = "/track/v4";
    private static final String s = "/track/key_get";
    private static final String t = "/api/v4/detail/config";
    private static final String u = "/api/v4/detail/config_common";
    private static ConcurrentHashMap<String, String> w = new ConcurrentHashMap<>();
    private Context v;

    private String g() {
        return g;
    }

    private x() {
        w.put(d, k);
        w.put(e, j);
        f();
    }

    /* loaded from: classes4.dex */
    private static class a {
        private static final x a = new x();

        private a() {
        }
    }

    public static x a() {
        return a.a;
    }

    private void f() {
        try {
            String h2 = aa.h();
            if (!TextUtils.isEmpty(h2)) {
                a(new JSONObject(h2));
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public synchronized void a(JSONObject jSONObject) {
        p.a(a, "updateHostMap:" + jSONObject.toString());
        if (jSONObject != null) {
            try {
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    String optString = jSONObject.optString(str);
                    if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(optString)) {
                        w.put(str, optString);
                    }
                }
                aa.b(new JSONObject((Map) w).toString());
            } catch (Exception e2) {
                p.a(a, "updateHostMap: " + e2.toString());
            }
            p.a(a, "merge config:" + new JSONObject((Map) w).toString());
        }
    }

    public String b() {
        try {
            if (TextUtils.isEmpty(aa.l())) {
                c.b();
            }
        } catch (Exception e2) {
            p.a(a, "getTrackingUrl: " + e2.toString());
        }
        return a(g(), h(), r);
    }

    public String c() {
        return a(g(), i(), t);
    }

    public String d() {
        return a(g(), i(), u);
    }

    public String e() {
        return a(g(), h(), s);
    }

    public String a(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    private String h() {
        return a(q.x(), q.y());
    }

    private String a(boolean z, String str) {
        if (!z) {
            return h;
        }
        String str2 = w.get(str);
        return TextUtils.isEmpty(str2) ? i : str2;
    }

    private String i() {
        boolean x = q.x();
        String y = q.y();
        return !x ? l : TextUtils.equals(y, d) ? n : TextUtils.equals(y, e) ? o : m;
    }
}
