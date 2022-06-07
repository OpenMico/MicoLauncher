package com.xiaomi.miot.host.lan.impl.codec.operation;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.smarthome.setting.ServerSetting;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotSpecV2Action extends MiotRequest {
    public static final String MIOT_SPEC_V2_REQUEST_METHOD = "action";
    private String action;
    private int aiid;
    private String did;
    private boolean hasId;
    private JSONArray inArray;
    private JSONObject params;
    private int siid;
    private JSONArray outArray = new JSONArray();
    private MiotError error = MiotError.OK;

    public MiotSpecV2Action(JSONObject jSONObject) throws MiotException {
        this.inArray = new JSONArray();
        this.action = jSONObject.optString(SchemaActivity.KEY_METHOD);
        String str = this.action;
        if (str == null) {
            throw new MiotException(MiotError.MIOT_SPEC_V2_OPERATE_FAILED, "method not found");
        } else if ("action".equals(str)) {
            this.params = jSONObject.optJSONObject("params");
            JSONObject jSONObject2 = this.params;
            if (jSONObject2 != null) {
                try {
                    this.did = jSONObject2.getString("did");
                    this.siid = this.params.getInt("siid");
                    this.aiid = this.params.getInt("aiid");
                    this.inArray = this.params.optJSONArray(ServerSetting.SERVER_IN);
                    if (this.inArray == null) {
                        this.inArray = new JSONArray();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.hasId = jSONObject.has("id");
                if (this.hasId) {
                    setId(jSONObject.optInt("id"));
                    return;
                }
                return;
            }
            throw new MiotException(MiotError.MIOT_SPEC_V2_OPERATE_FAILED, "params not found");
        } else {
            MiotError miotError = MiotError.MIOT_SPEC_V2_OPERATE_FAILED;
            throw new MiotException(miotError, "method invalid: " + this.action);
        }
    }

    public String getAction() {
        return this.action;
    }

    public String getDid() {
        return this.did;
    }

    public int getSiid() {
        return this.siid;
    }

    public int getAiid() {
        return this.aiid;
    }

    public JSONArray getInArray() {
        return this.inArray;
    }

    public JSONArray getOutArray() {
        return this.outArray;
    }

    public void setError(MiotError miotError) {
        this.error = miotError;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.MIOT_SPEC_V2_ACTION;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", getId());
            jSONObject.put(SchemaActivity.KEY_METHOD, this.action);
            jSONObject.put("params", this.params);
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
            jSONObject.put("id", getId());
            jSONObject.put("code", 0);
            jSONObject.put("message", "");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("did", this.did);
            jSONObject2.put("siid", this.siid);
            jSONObject2.put("aiid", this.aiid);
            if (MiotError.OK.equals(this.error)) {
                jSONObject2.put("code", 0);
                jSONObject2.put("out", this.outArray);
            } else {
                jSONObject2.put("code", this.error.getCode());
            }
            jSONObject.put("result", jSONObject2);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
