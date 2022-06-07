package com.xiaomi.miot.host.lan.impl.codec.internal;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotToken extends MiotRequest {
    private static final String PARAMS_DIR = "/etc/miio/";
    public static final String REQUEST_METHOD = "_internal.request_ot_config";
    private static final String RESPONSE_METHOD = "_internal.res_ot_config";
    private String ntoken;

    public MiotToken() {
    }

    public MiotToken(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (REQUEST_METHOD.equals(optString)) {
            JSONObject optJSONObject = jSONObject.optJSONObject("params");
            if (PARAMS_DIR.equals(optJSONObject.optString("dir"))) {
                this.ntoken = optJSONObject.optString("ntoken");
                return;
            }
            MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
            throw new MiotException(miotError, "params invalid: " + optJSONObject);
        }
        MiotError miotError2 = MiotError.INTERNAL_INVALID_ARGS;
        throw new MiotException(miotError2, "method invalid: " + optString);
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.TOKEN;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, REQUEST_METHOD);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("dir", PARAMS_DIR);
            jSONObject2.put("ntoken", this.ntoken);
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
            jSONObject.put(SchemaActivity.KEY_METHOD, RESPONSE_METHOD);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("token", this.ntoken);
            jSONObject.put("params", jSONObject2);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getNtoken() {
        return this.ntoken;
    }

    public void setNtoken(String str) {
        this.ntoken = str;
    }
}
