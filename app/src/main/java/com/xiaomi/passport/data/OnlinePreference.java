package com.xiaomi.passport.data;

import org.json.JSONObject;

/* loaded from: classes4.dex */
public class OnlinePreference {
    public String dataCenterZone;
    public String diagnosisDomain;

    public static OnlinePreference parse(JSONObject jSONObject) {
        OnlinePreference onlinePreference = new OnlinePreference();
        onlinePreference.diagnosisDomain = jSONObject.optString("diagnosisDomain", null);
        onlinePreference.dataCenterZone = jSONObject.optString("dataCenterZone", null);
        return onlinePreference;
    }
}
