package com.xiaomi.miot.host.lan.impl.codec.internal;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotInfo extends MiotRequest {
    public static final String REQUEST_METHOD = "_internal.info";
    private static final String RESPONSE_METHOD = "_internal.info";
    private String params;

    public MiotInfo() {
    }

    public MiotInfo(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (!"_internal.info".equals(optString)) {
            MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
            throw new MiotException(miotError, "method invalid: " + optString);
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.INFO;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.info");
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setResponse(String str) {
        this.params = str;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return this.params.getBytes();
    }
}
