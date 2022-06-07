package com.xiaomi.miot.host.lan.impl.util;

import android.text.TextUtils;
import com.xiaomi.miot.host.lan.impl.MiotStore;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class OTInfo {
    private String bindkey;
    private String bleMac;
    private String bssid;
    private String country_domain;
    private long did;
    private int freq;
    private String fw_ver;
    private String gw;
    private String gw_mac;
    private String hw_ver;
    private String key;
    private String localIp;
    private String mac;
    private String mask;
    private String mcu_fw_ver;
    private int mmfree;
    private String model;
    private String partnerId;
    private String partnerToken;
    private String password;
    private int rssi;
    private String ssid;
    private String uid;
    private String wifi_fw_ver;

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.putOpt(b.B, this.mac);
        jSONObject.putOpt("fw_ver", this.fw_ver);
        jSONObject.putOpt("hw_ver", this.hw_ver);
        jSONObject.putOpt("mcu_fw_ver", this.mcu_fw_ver);
        jSONObject.putOpt("model", this.model);
        jSONObject.putOpt("wifi_fw_ver", this.wifi_fw_ver);
        jSONObject.putOpt(MiotStore.COUNTRYDOMAIN, this.country_domain);
        String str = this.uid;
        if (str != null && str.length() > 0) {
            jSONObject.putOpt(OneTrack.Param.UID, this.uid);
        }
        if (!TextUtils.isEmpty(this.bindkey)) {
            jSONObject.putOpt("bindkey", this.bindkey);
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.putOpt("rssi", String.valueOf(this.rssi));
        jSONObject2.putOpt("freq", Integer.valueOf(this.freq));
        jSONObject2.putOpt("ssid", this.ssid);
        jSONObject2.putOpt("bssid", this.bssid);
        if (!TextUtils.isEmpty(this.password)) {
            jSONObject2.putOpt("psk", this.password);
        }
        jSONObject.putOpt("ap", jSONObject2);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.putOpt("localIp", this.localIp);
        jSONObject3.putOpt("mask", this.mask);
        jSONObject3.putOpt("gw", this.gw);
        jSONObject3.putOpt("gw_mac", this.gw_mac);
        jSONObject.putOpt("netif", jSONObject3);
        jSONObject.putOpt("mmfree", Integer.valueOf(this.mmfree));
        if (!TextUtils.isEmpty(this.bleMac)) {
            jSONObject.putOpt("ble_mac", this.bleMac);
        }
        if (!TextUtils.isEmpty(this.partnerId)) {
            jSONObject.putOpt("partner_id", this.partnerId);
        }
        if (!TextUtils.isEmpty(this.partnerToken)) {
            jSONObject.putOpt("partner_token", this.partnerToken);
        }
        return jSONObject;
    }

    public String getMac() {
        return this.mac;
    }

    public String getFw_ver() {
        return this.fw_ver;
    }

    public String getHw_ver() {
        return this.hw_ver;
    }

    public String getMcu_fw_ver() {
        return this.mcu_fw_ver;
    }

    public String getModel() {
        return this.model;
    }

    public String getWifi_fw_ver() {
        return this.wifi_fw_ver;
    }

    public int getRssi() {
        return this.rssi;
    }

    public String getSsid() {
        return this.ssid;
    }

    public String getBssid() {
        return this.bssid;
    }

    public String getLocalIp() {
        return this.localIp;
    }

    public String getMask() {
        return this.mask;
    }

    public String getGw() {
        return this.gw;
    }

    public String getGw_mac() {
        return this.gw_mac;
    }

    public int getMmfree() {
        return this.mmfree;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public void setFw_ver(String str) {
        this.fw_ver = str;
    }

    public void setHw_ver(String str) {
        this.hw_ver = str;
    }

    public void setMcu_fw_ver(String str) {
        this.mcu_fw_ver = str;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public void setWifi_fw_ver(String str) {
        this.wifi_fw_ver = str;
    }

    public void setRssi(int i) {
        this.rssi = i;
    }

    public void setFreq(int i) {
        this.freq = i;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public void setBssid(String str) {
        this.bssid = str;
    }

    public void setLocalIp(String str) {
        this.localIp = str;
    }

    public void setMask(String str) {
        this.mask = str;
    }

    public void setGw(String str) {
        this.gw = str;
    }

    public void setGw_mac(String str) {
        this.gw_mac = str;
    }

    public void setMmfree(int i) {
        this.mmfree = i;
    }

    public long getDid() {
        return this.did;
    }

    public void setDid(long j) {
        this.did = j;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getBindkey() {
        return this.bindkey;
    }

    public void setBindkey(String str) {
        this.bindkey = str;
    }

    public String getPartnerId() {
        return this.partnerId;
    }

    public void setPartnerId(String str) {
        this.partnerId = str;
    }

    public void setPartnerToken(String str) {
        this.partnerToken = str;
    }

    public void setBleMac(String str) {
        this.bleMac = str;
    }

    public String getCountry_domain() {
        return this.country_domain;
    }

    public void setCountry_domain(String str) {
        this.country_domain = str;
    }
}
