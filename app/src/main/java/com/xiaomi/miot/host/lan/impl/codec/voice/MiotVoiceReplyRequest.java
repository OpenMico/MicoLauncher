package com.xiaomi.miot.host.lan.impl.codec.voice;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotVoiceReplyRequest extends MiotRequest {
    public static final String REQUEST_METHOD = "voicectrl_reply";
    private int code;
    private int commandId;
    private String extraData;
    private String sessionId;
    private String voiceTxt;

    public MiotVoiceReplyRequest(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (REQUEST_METHOD.equals(optString)) {
            JSONArray optJSONArray = jSONObject.optJSONArray("params");
            if (optJSONArray == null) {
                MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
                throw new MiotException(miotError, "params invalid: " + optJSONArray);
            } else if (optJSONArray.length() != 0) {
                String optString2 = optJSONArray.optString(0);
                if (optString2 != null) {
                    try {
                        JSONObject jSONObject2 = new JSONObject(optString2);
                        this.commandId = jSONObject2.optInt("serialid");
                        this.sessionId = jSONObject2.optString("sessionid");
                        this.sessionId = jSONObject2.optString("sessionid");
                        this.code = jSONObject2.optInt("code");
                        this.voiceTxt = jSONObject2.optString("voice_txt");
                        this.extraData = jSONObject2.optString("extra_data");
                    } catch (JSONException unused) {
                        MiotError miotError2 = MiotError.INTERNAL_INVALID_ARGS;
                        throw new MiotException(miotError2, "params invalid: " + optString2);
                    }
                } else {
                    MiotError miotError3 = MiotError.INTERNAL_INVALID_ARGS;
                    throw new MiotException(miotError3, "params invalid: " + optJSONArray);
                }
            } else {
                MiotError miotError4 = MiotError.INTERNAL_INVALID_ARGS;
                throw new MiotException(miotError4, "params invalid: " + optJSONArray);
            }
        } else {
            MiotError miotError5 = MiotError.INTERNAL_INVALID_ARGS;
            throw new MiotException(miotError5, "method invalid: " + optString);
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.VOICE_CTRL_REPLY;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        return new byte[0];
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", super.getId());
            jSONObject.put("result", "ok");
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getCommandId() {
        return this.commandId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public int getCode() {
        return this.code;
    }

    public String getVoiceTxt() {
        return this.voiceTxt;
    }

    public String getExtraData() {
        return this.extraData;
    }
}
