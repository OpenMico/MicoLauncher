package com.xiaomi.miot.host.lan.impl.codec.operation;

import android.util.Log;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.host.lan.impl.codec.MiotSupportRpcType;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotPropertyGetter extends MiotRequest {
    public static final String REQUEST_METHOD = "get_prop";
    private String deviceId;
    private String name;
    private List<String> properties = new ArrayList();
    private List<Object> values = new ArrayList();

    public MiotPropertyGetter(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (MiotSupportRpcType.isMitvType()) {
            this.name = optString.substring(3);
            if (optString.startsWith("Get")) {
                this.properties.add(this.name);
                setId(jSONObject.optInt("id"));
                return;
            }
            MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
            throw new MiotException(miotError, "method invalid: " + optString);
        } else if (REQUEST_METHOD.equals(optString)) {
            setId(jSONObject.optInt("id"));
            JSONArray optJSONArray = jSONObject.optJSONArray("params");
            if (optJSONArray == null) {
                if (jSONObject.has("params")) {
                    optJSONArray = new JSONArray();
                    optJSONArray.put(jSONObject.optString("params"));
                } else {
                    Log.e("MiotPropertyGetter", "params is null");
                }
            }
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    try {
                        this.properties.add(optJSONArray.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            throw new MiotException(MiotError.INTERNAL_INVALID_ARGS, "params is null");
        } else {
            MiotError miotError2 = MiotError.INTERNAL_INVALID_ARGS;
            throw new MiotException(miotError2, "method invalid: " + optString);
        }
    }

    public List<String> getProperties() {
        return this.properties;
    }

    public List<Object> getValues() {
        return this.values;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.GET_PROPERTY;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", getId());
            if (MiotSupportRpcType.isMitvType()) {
                jSONObject.put(SchemaActivity.KEY_METHOD, "Get" + this.name);
            } else {
                jSONObject.put(SchemaActivity.KEY_METHOD, REQUEST_METHOD);
                JSONArray jSONArray = new JSONArray();
                for (String str : this.properties) {
                    jSONArray.put(str);
                }
                jSONObject.put("params", jSONArray);
            }
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
            JSONArray jSONArray = new JSONArray();
            for (Object obj : this.values) {
                jSONArray.put(obj);
            }
            jSONObject.put("result", jSONArray);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
