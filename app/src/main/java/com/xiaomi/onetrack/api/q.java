package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.util.k;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.r;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class q implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public q(f fVar, String str) {
        this.b = fVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        Configuration configuration;
        Configuration configuration2;
        try {
            configuration = this.b.f;
            String a = k.a(r.a(configuration));
            if (!TextUtils.isEmpty(a)) {
                JSONObject jSONObject = new JSONObject(a);
                jSONObject.remove(this.a);
                configuration2 = this.b.f;
                k.a(r.a(configuration2), jSONObject.toString());
            }
        } catch (Exception e) {
            p.b("OneTrackImp", "removeCommonProperty error:" + e.toString());
        }
    }
}
