package com.xiaomi.miot.host.lan.impl.codec.internal;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotWifiConnected extends MiotRequest {
    public static final String REQUEST_METHOD = "_internal.wifi_connected";
    private String mSSID;

    public MiotWifiConnected(String str) {
        this.mSSID = str;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.WIFI_CONNECTED;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.wifi_connected");
            JSONObject jSONObject2 = new JSONObject();
            if (!TextUtils.isEmpty(this.mSSID)) {
                jSONObject2.put("ssid", this.mSSID);
            }
            jSONObject2.put("result", "ok");
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
