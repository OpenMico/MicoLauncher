package com.xiaomi.onetrack.c;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.onetrack.f.b;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.q;
import com.xiaomi.onetrack.util.x;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class f {
    public static final JSONObject a = new JSONObject();
    private static final String b = "SecretKeyManager";
    private static final String c = "secretKey";
    private static final String d = "sid";
    private static final String e = "key";
    private Context f;
    private JSONObject g;
    private String[] h;

    private f() {
        this.g = null;
        this.h = new String[2];
        this.f = com.xiaomi.onetrack.e.a.a();
    }

    /* loaded from: classes4.dex */
    public static final class a {
        private static final f a = new f();

        private a() {
        }
    }

    public static f a() {
        return a.a;
    }

    public synchronized String[] b() {
        JSONObject e2 = e();
        this.h[0] = e2 != null ? e2.optString(e) : "";
        this.h[1] = e2 != null ? e2.optString(d) : "";
        d();
        return this.h;
    }

    private void d() {
        if (!p.a) {
            return;
        }
        if (TextUtils.isEmpty(this.h[0]) || TextUtils.isEmpty(this.h[1])) {
            p.a(b, "key or sid is invalid!");
        } else {
            p.a(b, "key  and sid is valid! ");
        }
    }

    public JSONObject c() {
        try {
        } catch (Exception e2) {
            p.b(b, "requestSecretData: " + e2.toString());
        }
        if (q.a(b)) {
            return a;
        }
        byte[] a2 = a.a();
        String a3 = c.a(e.a(a2));
        HashMap hashMap = new HashMap();
        hashMap.put(c, a3);
        String b2 = b.b(x.a().e(), hashMap, true);
        if (!TextUtils.isEmpty(b2)) {
            JSONObject jSONObject = new JSONObject(b2);
            int optInt = jSONObject.optInt("code");
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optInt == 0 && optJSONObject != null) {
                String optString = optJSONObject.optString(e);
                String optString2 = optJSONObject.optString(d);
                if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString2)) {
                    String a4 = c.a(a.b(c.a(optString), a2));
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(e, a4);
                    jSONObject2.put(d, optString2);
                    this.g = jSONObject2;
                    aa.a(b.a(this.f, jSONObject2.toString()));
                    aa.i(System.currentTimeMillis());
                }
            }
        }
        return this.g;
    }

    private JSONObject e() {
        JSONObject jSONObject = this.g;
        if (jSONObject == null && (jSONObject = f()) != null) {
            this.g = jSONObject;
        }
        return jSONObject == null ? c() : jSONObject;
    }

    private JSONObject f() {
        try {
            String g = aa.g();
            if (TextUtils.isEmpty(g)) {
                return null;
            }
            return new JSONObject(b.b(this.f, g));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
