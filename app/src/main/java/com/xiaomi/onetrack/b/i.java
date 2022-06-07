package com.xiaomi.onetrack.b;

import com.xiaomi.onetrack.api.d;
import com.xiaomi.onetrack.util.p;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class i implements Runnable {
    final /* synthetic */ d a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(d dVar) {
        this.a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            List<JSONObject> c = g.c();
            if (c != null && c.size() > 0) {
                for (JSONObject jSONObject : c) {
                    this.a.a(jSONObject.optString(g.d), jSONObject.optString("data"));
                }
            }
            g.c(true);
        } catch (Exception e) {
            p.b("NetworkAccessManager", "cta event error: " + e.toString());
        }
        boolean unused = g.l = false;
    }
}
