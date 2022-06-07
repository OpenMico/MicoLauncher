package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.util.k;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.r;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class o implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public o(f fVar, Map map) {
        this.b = fVar;
        this.a = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        Configuration configuration;
        Configuration configuration2;
        try {
            JSONObject a = r.a((Map<String, Object>) this.a, true);
            configuration = this.b.f;
            String a2 = k.a(r.a(configuration));
            JSONObject jSONObject = null;
            if (!TextUtils.isEmpty(a2)) {
                jSONObject = new JSONObject(a2);
            }
            JSONObject a3 = r.a(a, jSONObject);
            configuration2 = this.b.f;
            k.a(r.a(configuration2), a3.toString());
        } catch (Exception e) {
            p.b("OneTrackImp", StringUtils.SPACE + e.toString());
        }
    }
}
