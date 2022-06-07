package com.xiaomi.miui.pushads.sdk;

import android.os.Bundle;
import com.xiaomi.push.cf;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a extends cf {
    public String a;

    @Override // com.xiaomi.push.cf
    public Bundle a() {
        Bundle a = super.a();
        a.putString("content", this.a);
        return a;
    }

    @Override // com.xiaomi.push.cf
    public void a(JSONObject jSONObject) {
        super.a(jSONObject);
        this.a = jSONObject.optString("content");
    }

    @Override // com.xiaomi.push.cf
    public String toString() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", this.f16a);
            jSONObject.put("showType", this.a);
            jSONObject.put("lastShowTime", this.f17b);
            jSONObject.put("content", this.a);
            return jSONObject.toString();
        } catch (Exception unused) {
            return "";
        }
    }
}
