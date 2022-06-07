package com.xiaomi.miot.host.lan.impl.codec.internal;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotWifiConnectResult extends MiotRequest {
    public static final String REQUEST_METHOD = "_internal.wifi_connected";
    public static final String RESPONSE_METHOD = "_internal.wifi_connect_failed";
    private String bssid;
    private String result;
    private String ssid;

    public MiotWifiConnectResult() {
    }

    public MiotWifiConnectResult(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if ("_internal.wifi_connected".equals(optString)) {
            JSONObject optJSONObject = jSONObject.optJSONObject("params");
            this.ssid = optJSONObject.optString("ssid");
            this.bssid = optJSONObject.optString("bssid");
            this.result = optJSONObject.optString("result");
            return;
        }
        MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
        throw new MiotException(miotError, "method invalid: " + optString);
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.WIFI_START;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.wifi_connected");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("ssid", this.ssid);
            jSONObject2.put("bssid", this.bssid);
            jSONObject2.put("result", this.result);
            jSONObject.put("params", jSONObject2);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.wifi_connect_failed");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("ssid", this.ssid);
            jSONObject2.put("bssid", this.bssid);
            jSONObject2.put("result", this.result);
            jSONObject.put("params", jSONObject2);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public String getBssid() {
        return this.bssid;
    }

    public void setBssid(String str) {
        this.bssid = str;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }
}
