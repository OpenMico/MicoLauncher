package com.xiaomi.push;

import android.os.Bundle;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class cf {
    public int a;

    /* renamed from: a */
    public long f16a;
    public int b;

    /* renamed from: b */
    public long f17b;
    public int c;
    public int d;
    public int e;
    private String f;
    public String h;

    public cf() {
    }

    public cf(cf cfVar) {
        this.f16a = cfVar.f16a;
        this.a = cfVar.a;
        this.h = cfVar.h;
        this.b = cfVar.b;
        this.c = cfVar.c;
        this.f17b = cfVar.f17b;
        this.d = cfVar.d;
        this.f = cfVar.f;
        this.e = cfVar.e;
    }

    public Bundle a() {
        Bundle bundle = new Bundle();
        bundle.putLong("id", this.f16a);
        bundle.putInt("showType", this.a);
        bundle.putInt("nonsense", this.b);
        bundle.putInt("receiveUpperBound", this.c);
        bundle.putLong("lastShowTime", this.f17b);
        bundle.putInt(VideoTrackHelper.FLOAT_MULTI, this.e);
        return bundle;
    }

    /* renamed from: a */
    public String m807a() {
        return this.f;
    }

    public void a(String str) {
        this.f = str;
    }

    public void a(JSONObject jSONObject) {
        this.f16a = jSONObject.optLong("id");
        this.a = jSONObject.optInt("showType");
        this.b = jSONObject.optInt("nonsense");
        this.c = jSONObject.optInt("receiveUpperBound");
        this.f17b = jSONObject.optLong("lastShowTime");
        this.e = jSONObject.optInt(VideoTrackHelper.FLOAT_MULTI);
    }

    public String toString() {
        return "";
    }
}
