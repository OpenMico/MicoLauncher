package com.xiaomi.miot.host.lan.impl.codec.broadcast;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.MiotStore;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.onetrack.OneTrack;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotWifiStart extends MiotRequest {
    public static final String REQUEST_METHOD = "_internal.wifi_start";
    private String bssid;
    private String countryDomain;
    private String datadir;
    private String passwd;
    private String passwd5g;
    private int routerConfigured;
    private String ssid;
    private String ssid5g;
    private String tz;
    private String uid;

    public MiotWifiStart(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if ("_internal.wifi_start".equals(optString)) {
            JSONObject optJSONObject = jSONObject.optJSONObject("params");
            if (optJSONObject != null) {
                this.ssid = optJSONObject.optString("ssid");
                this.passwd = optJSONObject.optString("passwd");
                this.routerConfigured = optJSONObject.optInt("router_password");
                this.bssid = optJSONObject.optString("bssid");
                this.ssid5g = optJSONObject.optString("ssid_5g");
                this.passwd5g = optJSONObject.optString("passwd_5g");
                this.countryDomain = optJSONObject.optString(MiotStore.COUNTRYDOMAIN);
                this.datadir = optJSONObject.optString("datadir");
                this.uid = optJSONObject.optString(OneTrack.Param.UID);
                return;
            }
            return;
        }
        MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
        throw new MiotException(miotError, "method invalid: " + optString);
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.WIFI_START;
    }

    public JSONObject getRequestJsonObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.wifi_start");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("ssid", this.ssid);
            jSONObject2.put("passwd", this.passwd);
            jSONObject2.put("router_password", this.routerConfigured);
            jSONObject2.put("bssid", this.bssid);
            jSONObject2.put("ssid_5g", this.ssid5g);
            jSONObject2.put("passwd_5g", this.passwd5g);
            jSONObject2.put(MiotStore.COUNTRYDOMAIN, this.countryDomain);
            jSONObject2.put("datadir", this.datadir);
            jSONObject2.put(OneTrack.Param.UID, this.uid);
            jSONObject.put("params", jSONObject2);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, "_internal.wifi_start");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("ssid", this.ssid);
            jSONObject2.put("passwd", this.passwd);
            jSONObject2.put("router_password", this.routerConfigured);
            jSONObject2.put("bssid", this.bssid);
            jSONObject2.put("ssid_5g", this.ssid5g);
            jSONObject2.put("passwd_5g", this.passwd5g);
            jSONObject2.put(MiotStore.COUNTRYDOMAIN, this.countryDomain);
            jSONObject2.put("datadir", this.datadir);
            jSONObject2.put(OneTrack.Param.UID, this.uid);
            jSONObject.put("params", jSONObject2);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new JSONObject().toString().getBytes();
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String str) {
        this.passwd = str;
    }

    public int getRouterConfigured() {
        return this.routerConfigured;
    }

    public void setRouterConfigured(int i) {
        this.routerConfigured = i;
    }

    public String getBssid() {
        return this.bssid;
    }

    public void setBssid(String str) {
        this.bssid = str;
    }

    public String getSsid5g() {
        return this.ssid5g;
    }

    public void setSsid5g(String str) {
        this.ssid5g = str;
    }

    public String getPasswd5g() {
        return this.passwd5g;
    }

    public void setPasswd5g(String str) {
        this.passwd5g = str;
    }

    public String getCountryDomain() {
        return this.countryDomain;
    }

    public void setCountryDomain(String str) {
        this.countryDomain = str;
    }

    public String getDatadir() {
        return this.datadir;
    }

    public void setDatadir(String str) {
        this.datadir = str;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getTz() {
        return this.tz;
    }

    public void setTz(String str) {
        this.tz = str;
    }
}
