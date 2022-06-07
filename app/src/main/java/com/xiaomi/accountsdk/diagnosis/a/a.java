package com.xiaomi.accountsdk.diagnosis.a;

import org.json.JSONObject;

/* loaded from: classes2.dex */
public class a {
    public String a;
    public String b;

    public static a a(JSONObject jSONObject) {
        a aVar = new a();
        aVar.b = jSONObject.optString("diagnosisDomain", null);
        aVar.a = jSONObject.optString("dataCenterZone", null);
        return aVar;
    }
}
