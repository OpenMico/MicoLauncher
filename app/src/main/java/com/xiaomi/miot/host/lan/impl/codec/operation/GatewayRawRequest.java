package com.xiaomi.miot.host.lan.impl.codec.operation;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class GatewayRawRequest extends MiotRequest {
    private int id;
    private String message;
    private String method;

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return null;
    }

    public GatewayRawRequest(String str) {
        this.message = str;
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.id = jSONObject.optInt("id");
            this.method = jSONObject.optString(SchemaActivity.KEY_METHOD);
            setId(this.id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        return this.message.getBytes();
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return this.message.getBytes();
    }

    public boolean isRpcRequest() {
        return !TextUtils.isEmpty(this.method);
    }
}
