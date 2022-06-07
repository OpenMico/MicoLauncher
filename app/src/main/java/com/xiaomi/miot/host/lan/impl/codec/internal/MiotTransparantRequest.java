package com.xiaomi.miot.host.lan.impl.codec.internal;

import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotTransparantRequest extends MiotRequest {
    private String mMethod;
    private JSONObject mRequest;

    public MiotTransparantRequest() {
    }

    public MiotTransparantRequest(String str, JSONObject jSONObject) {
        this.mRequest = jSONObject;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.MIOT_TRANSPARENT_REQUEST;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        return this.mRequest.toString().getBytes();
    }

    public String getOriginRequest() {
        JSONObject jSONObject = this.mRequest;
        if (jSONObject != null) {
            return jSONObject.toString();
        }
        return "";
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new JSONObject().toString().getBytes();
    }
}
