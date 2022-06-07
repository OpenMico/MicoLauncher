package com.xiaomi.miot.host.lan.impl.codec.internal;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotWifiDisconnectedEvent extends MiotRequest {
    public static final String REQUEST_METHOD = "_internal.wifi_disconnected_event";
    private String mBSSID;
    private String mSSID;

    public MiotWifiDisconnectedEvent(String str, String str2) {
        this.mSSID = str;
        this.mBSSID = str2;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.WIFI_DISCONNECTED;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.wifi_disconnected_event");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("ssid", this.mSSID);
            jSONObject2.put("bssid", this.mBSSID);
            jSONObject.put("params", jSONObject2);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new byte[0];
    }
}
