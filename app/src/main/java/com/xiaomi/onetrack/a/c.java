package com.xiaomi.onetrack.a;

import android.text.TextUtils;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.onetrack.BuildConfig;
import com.xiaomi.onetrack.f.b;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.i;
import com.xiaomi.onetrack.util.oaid.a;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.q;
import com.xiaomi.onetrack.util.x;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class c {
    public static final String a = "l";
    private static final String b = "CommonConfigUpdater";
    private static final String c = "t";
    private static final String d = "levels";
    private static final String e = "Android";
    private static ConcurrentHashMap<Integer, Integer> f = new ConcurrentHashMap<>();
    private static final long g = 172800000;

    public static void a() {
        i.a(new d());
    }

    public static void b() {
        if (e()) {
            f();
        } else {
            p.a(b, "CommonConfigUpdater Does not meet prerequisites for request");
        }
    }

    private static boolean e() {
        if (!com.xiaomi.onetrack.f.c.a()) {
            p.b(b, "net is not connected!");
            return false;
        } else if (TextUtils.isEmpty(aa.l())) {
            return true;
        } else {
            long j = aa.j();
            return j < System.currentTimeMillis() || j - System.currentTimeMillis() > g;
        }
    }

    public static void f() {
        if (!q.a(b)) {
            HashMap hashMap = new HashMap();
            try {
                hashMap.put(k.a, a.a().a(com.xiaomi.onetrack.e.a.a()));
                hashMap.put(k.b, q.h());
                hashMap.put(k.c, q.d());
                hashMap.put(k.d, q.x() ? "1" : "0");
                hashMap.put("sv", BuildConfig.SDK_VERSION);
                hashMap.put(k.m, com.xiaomi.onetrack.e.a.b());
                hashMap.put(k.f, q.i());
                hashMap.put(k.g, DeviceUtil.c());
                hashMap.put(k.h, q.y());
                hashMap.put("platform", "Android");
                String d2 = x.a().d();
                String b2 = b.b(d2, hashMap, true);
                p.a(b, "url:" + d2 + " response:" + b2);
                a(b2);
            } catch (IOException e2) {
                p.a(b, "requestCloudData: " + e2.toString());
            }
        }
    }

    private static void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.optInt("code") == 0) {
                    String optString = jSONObject.optString("hash");
                    JSONObject optJSONObject = jSONObject.optJSONObject("data");
                    if (optJSONObject != null) {
                        JSONObject optJSONObject2 = optJSONObject.optJSONObject("regionUrl");
                        if (optJSONObject2 != null) {
                            x.a().a(optJSONObject2);
                        }
                        aa.d(optJSONObject.toString());
                        aa.c(optString);
                    }
                    aa.j(System.currentTimeMillis() + 86400000 + new Random().nextInt(86400000));
                }
            } catch (JSONException e2) {
                p.a(b, "saveCommonCloudData: " + e2.toString());
            }
        }
    }

    public static Map<Integer, Integer> c() {
        try {
        } catch (Exception e2) {
            p.a(b, "getLevelIntervalConfig: " + e2.toString());
        }
        if (!f.isEmpty()) {
            return f;
        }
        String l = aa.l();
        if (!TextUtils.isEmpty(l)) {
            JSONArray optJSONArray = new JSONObject(l).optJSONArray(d);
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject jSONObject = optJSONArray.getJSONObject(i);
                int optInt = jSONObject.optInt(a);
                int optInt2 = jSONObject.optInt("t");
                if (optInt > 0 && optInt2 > 0) {
                    f.put(Integer.valueOf(optInt), Integer.valueOf(optInt2));
                }
            }
        }
        return f.isEmpty() ? g() : f;
    }

    private static HashMap<Integer, Integer> g() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(1, 5000);
        hashMap.put(2, 15000);
        hashMap.put(3, Integer.valueOf((int) Common.REQUEST_TIME_LIMIT));
        return hashMap;
    }
}
