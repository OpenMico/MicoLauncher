package com.xiaomi.miot;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DevicePropertyBriefInfo {
    private String key;
    private DevicePropValue propValue;
    private String serviceTypeName;
    private int siid;
    private String typeName;

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String str) {
        this.typeName = str;
    }

    public DevicePropValue getPropValue() {
        return this.propValue;
    }

    public void setPropValue(DevicePropValue devicePropValue) {
        this.propValue = devicePropValue;
    }

    public int getSiid() {
        return this.siid;
    }

    public void setSiid(int i) {
        this.siid = i;
    }

    public String getServiceTypeName() {
        return this.serviceTypeName;
    }

    public void setServiceTypeName(String str) {
        this.serviceTypeName = str;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("key", this.key);
            jSONObject.put("typeName", this.typeName);
            jSONObject.put("siid", this.siid);
            jSONObject.put("srvTypeName", this.serviceTypeName);
            if (this.propValue != null) {
                jSONObject.put("propValue", new JSONObject(this.propValue.toString()));
            }
        } catch (JSONException e) {
            Log.e(MicoSupConstants.TAG_SUP_MIJIA, "DevicePropertyBriefInfo json error: " + e.getMessage());
        }
        return jSONObject.toString();
    }
}
