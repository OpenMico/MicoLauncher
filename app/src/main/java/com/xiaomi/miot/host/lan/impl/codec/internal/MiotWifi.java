package com.xiaomi.miot.host.lan.impl.codec.internal;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotWifi extends MiotRequest {
    public static final String REQUEST_METHOD = "_internal.req_wifi_conf_status";
    private static final String REQUEST_PARAMS = "/etc/miio/";
    private static final String RESPONSE_METHOD = "_internal.res_wifi_conf_status";
    private int mWifiMode;

    public MiotWifi() {
    }

    public MiotWifi(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if ("_internal.req_wifi_conf_status".equals(optString)) {
            String optString2 = jSONObject.optString("params");
            if (!REQUEST_PARAMS.equals(optString2)) {
                MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
                throw new MiotException(miotError, "params invalid: " + optString2);
            }
            return;
        }
        MiotError miotError2 = MiotError.INTERNAL_INVALID_ARGS;
        throw new MiotException(miotError2, "method invalid: " + optString);
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.WIFI;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.req_wifi_conf_status");
            jSONObject.put("params", REQUEST_PARAMS);
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
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.res_wifi_conf_status");
            jSONObject.put("params", this.mWifiMode);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setWifiMode(int i) {
        this.mWifiMode = i;
    }
}
