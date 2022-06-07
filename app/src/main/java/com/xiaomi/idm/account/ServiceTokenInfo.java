package com.xiaomi.idm.account;

import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ServiceTokenInfo extends TokenInfo {
    public static final String KEY_ID = "ServiceTokenInfo";
    String a;
    String b;
    String c;
    String d;
    String e;
    long f;

    @Override // com.xiaomi.idm.account.TokenInfo
    public String getKeyId() {
        return KEY_ID;
    }

    public boolean equals(Object obj) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        if ((obj instanceof ServiceTokenInfo) && this.g != null) {
            ServiceTokenInfo serviceTokenInfo = (ServiceTokenInfo) obj;
            if (this.g.equals(serviceTokenInfo.g) && (str = this.a) != null && str.equals(serviceTokenInfo.a) && (str2 = this.b) != null && str2.equals(serviceTokenInfo.b) && (str3 = this.c) != null && str3.equals(serviceTokenInfo.c) && (str4 = this.d) != null && str4.equals(serviceTokenInfo.d) && (str5 = this.e) != null && str5.equals(serviceTokenInfo.e)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.xiaomi.idm.account.TokenInfo
    public JSONObject toJsonSub() {
        JSONObject jSONObject = new JSONObject();
        try {
            a(jSONObject, "token", this.g);
            a(jSONObject, "sid", this.b);
            a(jSONObject, BaseConstants.EXTRA_USER_ID, this.a);
            a(jSONObject, "cUserId", this.c);
            a(jSONObject, "ssecurity", this.d);
            a(jSONObject, "domain", this.e);
            a(jSONObject, "timeDiff", Long.valueOf(this.f));
        } catch (JSONException e) {
            LogUtil.e(KEY_ID, e.getMessage(), e);
        }
        return jSONObject;
    }

    public static ServiceTokenInfo buildFromJson(JSONObject jSONObject) {
        try {
            ServiceTokenInfo serviceTokenInfo = new ServiceTokenInfo();
            serviceTokenInfo.g = jSONObject.getString("token");
            serviceTokenInfo.a = jSONObject.getString(BaseConstants.EXTRA_USER_ID);
            serviceTokenInfo.b = jSONObject.getString("sid");
            serviceTokenInfo.c = jSONObject.getString("cUserId");
            serviceTokenInfo.d = jSONObject.getString("ssecurity");
            serviceTokenInfo.e = jSONObject.getString("domain");
            serviceTokenInfo.f = jSONObject.getLong("timeDiff");
            return serviceTokenInfo;
        } catch (JSONException e) {
            LogUtil.e(KEY_ID, e.getMessage(), e);
            return null;
        }
    }

    private void a(JSONObject jSONObject, String str, Object obj) throws JSONException {
        if (obj != null) {
            jSONObject.put(str, obj);
        }
    }
}
