package com.xiaomi.miot.host.lan.impl.codec.internal;

import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.onetrack.api.b;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotConfig extends MiotRequest {
    public static final String REQUEST_METHOD = "_internal.request_dinfo";
    private static final String REQUEST_PARAMS = "/etc/miio/";
    private static final String RESPONSE_METHOD = "_internal.response_dinfo";
    private String devCert;
    private long deviceId;
    private String key;
    private String keyType;
    private String mac;
    private String manuCert;
    private String model;
    private int[] scType = {0, 1, 2, 3, 4, 16};
    private boolean smartConfig;
    private String sn;
    private String vendor;

    public MiotConfig(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (REQUEST_METHOD.equals(optString)) {
            String optString2 = jSONObject.optString("params");
            if (!REQUEST_PARAMS.equals(optString2)) {
                MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
                throw new MiotException(miotError, "params invalid: " + optString2);
            }
            return;
        }
        MiotError miotError2 = MiotError.INTERNAL_INVALID_ARGS;
        throw new MiotException(miotError2, "method invalid: " + optString);
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.CONFIG;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, REQUEST_METHOD);
            jSONObject.put("params", REQUEST_PARAMS);
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
            jSONObject.put(SchemaActivity.KEY_METHOD, RESPONSE_METHOD);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("did", this.deviceId);
            jSONObject2.put("key", this.key == null ? "" : this.key);
            if (!TextUtils.isEmpty(this.keyType)) {
                jSONObject2.put("key_type", this.keyType);
            }
            jSONObject2.put("vendor", this.vendor);
            jSONObject2.put(b.B, this.mac == null ? "null" : this.mac);
            jSONObject2.put("model", this.model);
            jSONObject2.put("sn", this.sn);
            jSONObject2.put("smart_config", this.smartConfig);
            if (Build.VERSION.SDK_INT >= 19) {
                jSONObject2.put("sc_type", new JSONArray(this.scType));
            }
            jSONObject2.put("dev_cert", this.devCert);
            jSONObject2.put("manu_cert", this.manuCert);
            jSONObject.put("params", jSONObject2);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(long j) {
        this.deviceId = j;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String str) {
        this.vendor = str;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public void setSn(String str) {
        this.sn = str;
    }

    public String getSn() {
        return this.sn;
    }

    public String getKeyType() {
        return this.keyType;
    }

    public void setKeyType(String str) {
        this.keyType = str;
    }

    public int[] getScType() {
        return this.scType;
    }

    public void setScType(int[] iArr) {
        this.scType = iArr;
    }

    public boolean isSmartConfig() {
        return this.smartConfig;
    }

    public void setSmartConfig(boolean z) {
        this.smartConfig = z;
    }

    public String getDevCert() {
        return this.devCert;
    }

    public void setDevCert(String str) {
        this.devCert = str;
    }

    public String getManuCert() {
        return this.manuCert;
    }

    public void setManuCert(String str) {
        this.manuCert = str;
    }
}
