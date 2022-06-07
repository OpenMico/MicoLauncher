package com.xiaomi.miot;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DeviceActionInfo {
    private String actionType;
    private int aiid;
    private String serviceType;
    private int siid;

    public int getSiid() {
        return this.siid;
    }

    public void setSiid(int i) {
        this.siid = i;
    }

    public int getAiid() {
        return this.aiid;
    }

    public void setAiid(int i) {
        this.aiid = i;
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String str) {
        this.actionType = str;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String str) {
        this.serviceType = str;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("siid", this.siid);
            jSONObject.put("aiid", this.aiid);
            jSONObject.put("actionType", this.actionType);
            jSONObject.put("serviceType", this.serviceType);
        } catch (JSONException e) {
            Log.e(MicoSupConstants.TAG_SUP_MIJIA, "device action info json error: " + e.getMessage());
        }
        return jSONObject.toString();
    }
}
