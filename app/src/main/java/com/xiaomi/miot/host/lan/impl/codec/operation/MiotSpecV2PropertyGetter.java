package com.xiaomi.miot.host.lan.impl.codec.operation;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.onetrack.api.b;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotSpecV2PropertyGetter extends MiotRequest {
    public static final String MIOT_SPEC_V2_REQUEST_METHOD = "get_properties";
    private List<MiotSpecV2Data> properties = new ArrayList();

    public MiotSpecV2PropertyGetter(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (MIOT_SPEC_V2_REQUEST_METHOD.equals(optString)) {
            JSONArray optJSONArray = jSONObject.optJSONArray("params");
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    try {
                        JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                        String string = jSONObject2.getString("did");
                        int i2 = jSONObject2.getInt("siid");
                        int i3 = jSONObject2.getInt("piid");
                        MiotSpecV2Data miotSpecV2Data = new MiotSpecV2Data();
                        miotSpecV2Data.did = string;
                        miotSpecV2Data.siid = i2;
                        miotSpecV2Data.piid = i3;
                        this.properties.add(miotSpecV2Data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setId(jSONObject.optInt("id"));
                return;
            }
            throw new MiotException(MiotError.MIOT_SPEC_V2_OPERATE_FAILED, "params is null");
        }
        MiotError miotError = MiotError.MIOT_SPEC_V2_OPERATE_FAILED;
        throw new MiotException(miotError, "method invalid: " + optString);
    }

    public List<MiotSpecV2Data> getProperties() {
        return this.properties;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.MIOT_SPEC_V2_GET_PROPERTY;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", getId());
            jSONObject.put(SchemaActivity.KEY_METHOD, MIOT_SPEC_V2_REQUEST_METHOD);
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < this.properties.size(); i++) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("did", this.properties.get(i).did);
                jSONObject2.put("siid", this.properties.get(i).siid);
                jSONObject2.put("piid", this.properties.get(i).piid);
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("params", jSONArray);
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
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < this.properties.size(); i++) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("did", this.properties.get(i).did);
                jSONObject2.put("siid", this.properties.get(i).siid);
                jSONObject2.put("piid", this.properties.get(i).piid);
                MiotError miotError = this.properties.get(i).result;
                if (miotError == null) {
                    jSONObject2.put("code", MiotError.MIOT_SPEC_V2_OPERATE_FAILED.getCode());
                } else if (miotError == MiotError.OK) {
                    jSONObject2.put(b.p, this.properties.get(i).value);
                    jSONObject2.put("code", 0);
                } else {
                    jSONObject2.put("code", miotError.getCode());
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("result", jSONArray);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
