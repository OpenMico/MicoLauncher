package com.xiaomi.idm.account;

import com.xiaomi.mi_connect_sdk.util.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public abstract class TokenInfo {
    String g;

    public abstract String getKeyId();

    public abstract JSONObject toJsonSub();

    public final String getToken() {
        return this.g;
    }

    public static TokenInfo buildFromJson(String str) {
        try {
            return buildFromJson(new JSONObject(str));
        } catch (JSONException e) {
            LogUtil.e("TokenInfo", e.getMessage(), e);
            return null;
        }
    }

    public static TokenInfo buildFromJson(JSONObject jSONObject) {
        try {
            if (jSONObject.has(ServiceTokenInfo.KEY_ID)) {
                return ServiceTokenInfo.buildFromJson(jSONObject.getJSONObject(ServiceTokenInfo.KEY_ID));
            }
            return null;
        } catch (JSONException e) {
            LogUtil.e("TokenInfo", e.getMessage(), e);
            return null;
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(getKeyId(), toJsonSub());
        } catch (JSONException e) {
            LogUtil.e("TokenInfo", e.getMessage(), e);
        }
        return jSONObject;
    }
}
