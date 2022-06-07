package com.xiaomi.miio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class MiioLocalRpcResult {
    public MiioLocalErrorCode code;
    public String data;
    public long did;
    public String ip;
    public int stamp;
    public String token;

    public MiioLocalRpcResult(MiioLocalErrorCode miioLocalErrorCode) {
        this.code = miioLocalErrorCode;
    }

    public MiioLocalRpcResult(MiioLocalErrorCode miioLocalErrorCode, String str, long j, int i, String str2, String str3) {
        this.code = miioLocalErrorCode;
        this.data = str;
        this.did = j;
        this.stamp = i;
        this.token = str2;
        this.ip = str3;
    }

    public String toAPIString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", this.code.getCode());
            jSONObject.put("message", this.code.getMessage());
            if (this.code != MiioLocalErrorCode.SUCCESS) {
                return jSONObject.toString();
            }
            JSONObject jSONObject2 = new JSONObject(this.data);
            try {
                if (jSONObject2.getInt("result") == 0) {
                    return jSONObject.toString();
                }
            } catch (JSONException unused) {
            }
            JSONObject optJSONObject = jSONObject2.optJSONObject("result");
            JSONArray jSONArray = null;
            if (optJSONObject == null) {
                jSONArray = jSONObject2.optJSONArray("result");
            }
            int i = jSONObject2.getInt("id");
            if (optJSONObject != null) {
                jSONObject.put("result", optJSONObject);
                jSONObject.put("id", i);
                return jSONObject.toString();
            } else if (jSONArray == null) {
                return jSONObject2.toString();
            } else {
                jSONObject.put("result", jSONArray);
                jSONObject.put("id", i);
                return jSONObject.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject.toString();
        }
    }
}
