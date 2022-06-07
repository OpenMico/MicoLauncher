package com.xiaomi.miot.host.lan.impl.codec.operation;

import android.util.Log;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotAction extends MiotRequest {
    private String action;
    private boolean hasId;
    private String mOriginRequest;
    private Object params;
    private JSONArray results_JA = new JSONArray();
    private JSONObject results_JO = new JSONObject();
    private boolean isReponseParamJSONObject = false;
    private MiotError error = MiotError.OK;

    public MiotAction(JSONObject jSONObject) throws MiotException {
        this.action = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (this.action != null) {
            this.params = jSONObject.optJSONArray("params");
            if (this.params == null) {
                this.params = jSONObject.optJSONObject("params");
            }
            if (this.params == null) {
                this.params = new JSONArray();
                if (jSONObject.has("params")) {
                    ((JSONArray) this.params).put(jSONObject.optString("params"));
                } else {
                    Log.e("MiotAction", "params is null");
                }
            }
            this.hasId = jSONObject.has("id");
            if (this.hasId) {
                setId(jSONObject.optInt("id"));
            }
            this.mOriginRequest = jSONObject.toString();
            return;
        }
        throw new MiotException(MiotError.INTERNAL_INVALID_ARGS, "method not found");
    }

    public String getAction() {
        return this.action;
    }

    public Object getParams() {
        if (this.params == null) {
            this.params = new JSONArray();
        }
        return this.params;
    }

    public JSONArray getResults_JA() {
        return this.results_JA;
    }

    public JSONObject getResults_JO() {
        return this.results_JO;
    }

    public void setReponseParamJSONObject(boolean z) {
        this.isReponseParamJSONObject = z;
    }

    public void setError(MiotError miotError) {
        this.error = miotError;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.ACTION;
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
            if (MiotError.OK.equals(this.error)) {
                jSONObject.put("code", 0);
                jSONObject.put("message", "ok");
                if (this.isReponseParamJSONObject) {
                    jSONObject.put("result", this.results_JO);
                } else {
                    if (this.results_JA.length() == 0) {
                        this.results_JA.put("ok");
                    }
                    jSONObject.put("result", this.results_JA);
                }
            } else {
                jSONObject.put("code", this.error.getCode());
                jSONObject.put("message", MiotMeshDeviceItem.CONNECT_STATE_FAILED);
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(this.error.getMessage());
                jSONObject.put("result", jSONArray);
            }
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getOriginRequest() {
        return this.mOriginRequest;
    }
}
