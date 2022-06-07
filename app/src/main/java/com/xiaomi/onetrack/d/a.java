package com.xiaomi.onetrack.d;

import com.xiaomi.onetrack.a.f;
import com.xiaomi.onetrack.e.b;
import com.xiaomi.onetrack.util.p;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a extends b {
    private static final String d = "CustomEvent";

    public a(String str, String str2, String str3, String str4) {
        try {
            a(str);
            c(str3);
            b(str2);
            b(System.currentTimeMillis());
            a(new JSONObject(str4));
            a(f.a().a(str, str3, com.xiaomi.onetrack.a.a.d, 1));
        } catch (Exception e) {
            p.b(d, "CustomEvent error:" + e.toString());
        }
    }
}
