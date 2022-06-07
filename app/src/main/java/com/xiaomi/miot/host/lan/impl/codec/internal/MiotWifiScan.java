package com.xiaomi.miot.host.lan.impl.codec.internal;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotWifiScan extends MiotRequest {
    public static final String REQUEST_METHOD = "_internal.wifi_scan_req";
    public static final String RESPONSE_METHOD = "_internal.wifi_scan_resp";
    private List<Item> item;
    private int itemNum;
    private String ssid;

    public MiotWifiScan() {
    }

    public MiotWifiScan(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if ("_internal.wifi_scan_req".equals(optString)) {
            this.ssid = jSONObject.optJSONObject("params").optString("ssid");
            return;
        }
        MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
        throw new MiotException(miotError, "method invalid: " + optString);
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.WIFI_SCAN;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.wifi_scan_req");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("ssid", this.ssid);
            jSONObject.put("params", jSONObject2);
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
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.wifi_scan_resp");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("ssid", this.ssid);
            jSONObject2.put("item_num", this.itemNum);
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < this.item.size(); i++) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("bssid", this.item.get(i).getBssid());
                jSONObject3.put("channel", this.item.get(i).getChannel());
                jSONObject3.put("rssi", this.item.get(i).getRssi());
                jSONArray.put(jSONObject3);
            }
            jSONObject2.put("item", jSONArray);
            jSONObject.put("params", jSONObject2);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public int getItemNum() {
        return this.itemNum;
    }

    public void setItemNum(int i) {
        this.itemNum = i;
    }

    public List<Item> getItem() {
        return this.item;
    }

    public void setItem(List<Item> list) {
        this.item = list;
    }

    /* loaded from: classes2.dex */
    public static class Item {
        private String bssid;
        private int channel;
        private int rssi;

        public String getBssid() {
            return this.bssid;
        }

        public void setBssid(String str) {
            this.bssid = str;
        }

        public int getRssi() {
            return this.rssi;
        }

        public void setRssi(int i) {
            this.rssi = i;
        }

        public int getChannel() {
            return this.channel;
        }

        public void setChannel(int i) {
            this.channel = i;
        }

        public String toString() {
            return "Item{bssid='" + this.bssid + "', rssi=" + this.rssi + ", channel=" + this.channel + '}';
        }
    }
}
