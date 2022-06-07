package com.xiaomi.miui.pushads.sdk;

import android.os.Bundle;
import com.xiaomi.micolauncher.module.homepage.activity.SimpleWebActivity;
import com.xiaomi.push.cf;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class h extends cf {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;

    public h() {
    }

    public h(h hVar) {
        super(hVar);
        this.a = hVar.a;
        this.b = hVar.b;
        this.c = hVar.c;
        this.d = hVar.d;
        this.e = hVar.e;
        this.f = hVar.f;
        this.g = hVar.g;
    }

    @Override // com.xiaomi.push.cf
    public Bundle a() {
        Bundle a = super.a();
        a.putString(SimpleWebActivity.NAME, this.a);
        a.putString("imgUrl", this.b);
        a.putString("titText", this.c);
        a.putString("priText", this.d);
        a.putString("secText", this.e);
        a.putString("type", this.f);
        a.putString("actionText", this.g);
        return a;
    }

    @Override // com.xiaomi.push.cf
    public void a(JSONObject jSONObject) {
        super.a(jSONObject);
        this.a = jSONObject.optString(SimpleWebActivity.NAME);
        this.b = jSONObject.optString("imgUrl");
        this.c = jSONObject.optString("titText");
        this.d = jSONObject.optString("priText");
        this.e = jSONObject.optString("secText");
        this.f = jSONObject.optString("type");
        this.g = jSONObject.optString("actionText");
    }

    @Override // com.xiaomi.push.cf
    public String toString() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("showType", this.a);
            jSONObject.put("lastShowTime", this.f17b);
            jSONObject.put(SimpleWebActivity.NAME, this.a);
            jSONObject.put("type", this.f);
            jSONObject.put("imgUrl", this.b);
            jSONObject.put("receiveUpperBound", this.c);
            jSONObject.put("downloadedPath", a());
            jSONObject.put("titText", this.c);
            jSONObject.put("priText", this.d);
            jSONObject.put("secText", this.e);
            jSONObject.put("actionText", this.g);
            return jSONObject.toString();
        } catch (Exception unused) {
            return null;
        }
    }
}
