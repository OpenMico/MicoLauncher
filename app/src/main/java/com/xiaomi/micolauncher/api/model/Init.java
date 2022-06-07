package com.xiaomi.micolauncher.api.model;

import com.google.gson.annotations.SerializedName;
import com.umeng.analytics.pro.ai;
import java.util.Objects;

/* loaded from: classes3.dex */
public class Init {

    /* loaded from: classes3.dex */
    public static class InitWifiInfoResp {
        public String code;
        public String did;
        public String info;
        public String qr;
    }

    /* loaded from: classes3.dex */
    public static class TimeSyncInfo {
        @SerializedName(ai.aF)
        public long t;
    }

    /* loaded from: classes3.dex */
    public static class InitWifiInfo {
        @SerializedName(ai.aD)
        public String countryCode;
        public String identity;
        public String password;
        public String ssid;
        public String uid;
        @SerializedName(ai.aC)
        public int version;

        public String toString() {
            return "InitWifiInfo{ssid='" + this.ssid + "', password='" + this.password + "', identity='" + this.identity + "', uid='" + this.uid + "', version=" + this.version + ", countryCode='" + this.countryCode + "'}";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            InitWifiInfo initWifiInfo = (InitWifiInfo) obj;
            return this.version == initWifiInfo.version && Objects.equals(this.ssid, initWifiInfo.ssid) && Objects.equals(this.password, initWifiInfo.password) && Objects.equals(this.identity, initWifiInfo.identity) && Objects.equals(this.uid, initWifiInfo.uid) && Objects.equals(this.countryCode, initWifiInfo.countryCode);
        }

        public int hashCode() {
            return Objects.hash(this.ssid, this.password, this.identity, this.uid, Integer.valueOf(this.version), this.countryCode);
        }
    }
}
