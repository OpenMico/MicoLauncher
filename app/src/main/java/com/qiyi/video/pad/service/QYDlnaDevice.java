package com.qiyi.video.pad.service;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class QYDlnaDevice {
    public boolean connected;
    public String ipAddr;
    public String manufacturer;
    public String name;
    public int type;
    public String uuid;

    public QYDlnaDevice() {
    }

    public QYDlnaDevice(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                String str = null;
                this.uuid = jSONObject.has("uuid") ? jSONObject.getString("uuid") : null;
                this.name = jSONObject.has("name") ? jSONObject.getString("name") : null;
                this.connected = jSONObject.has("connected") ? jSONObject.getBoolean("connected") : false;
                this.type = jSONObject.has("type") ? jSONObject.getInt("type") : -1;
                this.manufacturer = jSONObject.has("manufacturer") ? jSONObject.getString("manufacturer") : null;
                this.ipAddr = jSONObject.has("ipAddr") ? jSONObject.getString("ipAddr") : str;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject toJsonObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("uuid", this.uuid);
            jSONObject.put("name", this.name);
            jSONObject.put("connected", this.connected);
            jSONObject.put("type", this.type);
            jSONObject.put("manufacturer", this.manufacturer);
            jSONObject.put("ipAddr", this.ipAddr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
