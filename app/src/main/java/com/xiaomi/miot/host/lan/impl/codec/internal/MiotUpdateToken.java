package com.xiaomi.miot.host.lan.impl.codec.internal;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotUpdateToken extends MiotRequest {
    public static final String REQUEST_METHOD = "_internal.update_dtoken";
    private static final String RESPONSE_METHOD = "_internal.token_updated";
    private String ntoken;

    public MiotUpdateToken() {
    }

    public MiotUpdateToken(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (REQUEST_METHOD.equals(optString)) {
            this.ntoken = jSONObject.optJSONObject("params").optString("ntoken");
            return;
        }
        MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
        throw new MiotException(miotError, "method invalid: " + optString);
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.TOKEN_UPDATE;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, REQUEST_METHOD);
            JSONObject jSONObject2 = new JSONObject();
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
            jSONObject.put("params", this.ntoken);
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
