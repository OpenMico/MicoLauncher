package com.xiaomi.miot.host.lan.impl.codec.operation;

import android.util.Log;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.host.lan.impl.codec.MiotSupportRpcType;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotPropertySetter extends MiotRequest {
    private String deviceId;
    private MiotError error = MiotError.OK;
    private String name;
    private String propertyName;
    private String serviceName;
    private Object value;

    public MiotPropertySetter(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (MiotSupportRpcType.isMitvType()) {
            this.name = optString.substring(3);
            if (!optString.startsWith("Set")) {
                MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
                throw new MiotException(miotError, "method invalid: " + optString);
            }
        } else if (MiotSupportRpcType.isYunmiType()) {
            String[] split = optString.split("#");
            if (split.length == 2) {
                String str = split[0];
                this.name = split[1];
                if (!str.equals(BluetoothConstants.SET)) {
                    MiotError miotError2 = MiotError.INTERNAL_INVALID_ARGS;
                    throw new MiotException(miotError2, "method invalid: " + optString);
                }
            }
        } else {
            String[] split2 = optString.split("_");
            if (split2.length == 3) {
                String str2 = split2[0];
                this.serviceName = split2[1];
                this.propertyName = split2[2];
                if (!str2.equals(BluetoothConstants.SET)) {
                    MiotError miotError3 = MiotError.INTERNAL_INVALID_ARGS;
                    throw new MiotException(miotError3, "method invalid: " + optString);
                }
            }
        }
        setId(jSONObject.optInt("id"));
        JSONArray optJSONArray = jSONObject.optJSONArray("params");
        if (optJSONArray == null) {
            if (jSONObject.has("params")) {
                optJSONArray = new JSONArray();
                optJSONArray.put(jSONObject.optString("params"));
            } else {
                Log.e("MiotPropertySetter", "params is null");
            }
        }
        if (optJSONArray == null) {
            throw new MiotException(MiotError.INTERNAL_INVALID_ARGS, "params is null");
        } else if (optJSONArray.length() == 1) {
            this.value = optJSONArray.opt(0);
        } else {
            MiotError miotError4 = MiotError.INTERNAL_INVALID_ARGS;
            throw new MiotException(miotError4, "params length invalid: " + optJSONArray.length());
        }
    }

    public void setError(MiotError miotError) {
        this.error = miotError;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.SET_PROPERTY;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", getId());
            if (MiotSupportRpcType.isMitvType()) {
                jSONObject.put(SchemaActivity.KEY_METHOD, "Set" + this.name);
            } else if (MiotSupportRpcType.isYunmiType()) {
                jSONObject.put(SchemaActivity.KEY_METHOD, "set#" + this.name);
            } else {
                jSONObject.put(SchemaActivity.KEY_METHOD, "set_" + this.serviceName + "_" + this.propertyName);
            }
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(this.value);
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
            if (MiotError.OK.equals(this.error)) {
                jSONObject.put("code", 0);
                jSONObject.put("message", "ok");
                JSONArray jSONArray = new JSONArray();
                jSONArray.put("ok");
                jSONObject.put("result", jSONArray);
            } else {
                jSONObject.put("code", this.error.getCode());
                jSONObject.put("message", MiotMeshDeviceItem.CONNECT_STATE_FAILED);
                JSONArray jSONArray2 = new JSONArray();
                jSONArray2.put(this.error.getMessage());
                jSONObject.put("result", jSONArray2);
            }
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public String getName() {
        if (MiotSupportRpcType.isMitvType() || MiotSupportRpcType.isYunmiType()) {
            return this.name;
        }
        return this.serviceName + "_" + this.propertyName;
    }

    public Object getValue() {
        return this.value;
    }
}
