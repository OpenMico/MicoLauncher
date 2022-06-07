package com.xiaomi.miot.host.lan.impl.codec.internal;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotLocalQueryStatus extends MiotRequest {
    public static final String REQUEST_METHOD = "local.query_status";
    public static final String RESPONSE_METHOD = "local.status";

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.USERINFO;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", getId());
            jSONObject.put(SchemaActivity.KEY_METHOD, REQUEST_METHOD);
            jSONObject.put("params", new JSONObject());
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new byte[0];
    }
}
