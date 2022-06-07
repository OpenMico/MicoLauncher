package com.xiaomi.miot.host.lan.impl.codec.voice;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotCreateVoiceSessionRequest extends MiotRequest {
    private static final String REQUEST_METHOD = "_sync.voicectrl_start";
    private JSONObject params;
    private String partnerId;

    public MiotCreateVoiceSessionRequest(String str, String str2) {
        try {
            this.params = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.partnerId = str2;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.VOICE_CREATE_SESSION;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(this.partnerId)) {
                jSONObject.put("partner_id", this.partnerId);
            }
            jSONObject.put("id", getId());
            jSONObject.put(SchemaActivity.KEY_METHOD, REQUEST_METHOD);
            jSONObject.put("params", this.params);
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
