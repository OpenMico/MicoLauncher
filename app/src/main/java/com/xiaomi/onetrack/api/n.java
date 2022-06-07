package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.p;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class n implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(f fVar, boolean z) {
        this.b = fVar;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        d dVar;
        try {
            String z = aa.z();
            if (!TextUtils.isEmpty(z)) {
                JSONObject jSONObject = new JSONObject(z);
                JSONObject put = jSONObject.optJSONObject("B").put(b.u, this.a);
                dVar = this.b.b;
                dVar.a(b.g, jSONObject.put("B", put).toString());
                if (p.a) {
                    p.a("OneTrackImp", "trackPageEndAuto");
                }
                aa.i("");
            }
        } catch (Exception e) {
            p.b("OneTrackImp", "trackPageEndAuto error:" + e.toString());
        }
    }
}
