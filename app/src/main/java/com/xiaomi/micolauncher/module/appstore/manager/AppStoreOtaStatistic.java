package com.xiaomi.micolauncher.module.appstore.manager;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;

/* loaded from: classes3.dex */
public class AppStoreOtaStatistic {
    @SerializedName("id")
    private int a;
    @SerializedName(SchemaActivity.KEY_METHOD)
    private String b;
    @SerializedName("params")
    private OtaStatisticSummay c;

    public void setId(int i) {
        this.a = i;
    }

    public int getId() {
        return this.a;
    }

    public void setMethod(String str) {
        this.b = str;
    }

    public String getMethod() {
        return this.b;
    }

    public void setParams(OtaStatisticSummay otaStatisticSummay) {
        this.c = otaStatisticSummay;
    }

    public OtaStatisticSummay getParams() {
        return this.c;
    }

    /* loaded from: classes3.dex */
    public static class OtaStatisticSummay {
        @SerializedName("id")
        private int a;
        @SerializedName(SchemaActivity.KEY_METHOD)
        private String b;
        @SerializedName("ota.statistic")
        private OtaStatistic c;
        @SerializedName("model")
        private String d;
        @SerializedName("fw_ver")
        private String e;
        @SerializedName("mcu_fw_ver")
        private String f;
        @SerializedName("device_sn")
        private String g;

        public void setId(int i) {
            this.a = i;
        }

        public int getId() {
            return this.a;
        }

        public void setMethod(String str) {
            this.b = str;
        }

        public String getMethod() {
            return this.b;
        }

        public void setParams(OtaStatistic otaStatistic) {
            this.c = otaStatistic;
        }

        public OtaStatistic getParams() {
            return this.c;
        }

        public void setModel(String str) {
            this.d = str;
        }

        public String getModel() {
            return this.d;
        }

        public void setFirmwareVer(String str) {
            this.e = str;
        }

        public String getFirmwareVer() {
            return this.e;
        }

        public void setMcuFirmwareVer(String str) {
            this.f = str;
        }

        public String getMcuFirmwareVer() {
            return this.f;
        }

        public void setDeviceSn(String str) {
            this.g = str;
        }

        public String getDeviceSn() {
            return this.g;
        }
    }

    /* loaded from: classes3.dex */
    public static class OtaStatistic {
        @SerializedName("miio_bt")
        private OtaInfo a;

        public void setMeshGateway(OtaInfo otaInfo) {
            this.a = otaInfo;
        }

        public OtaInfo getMeshGateway() {
            return this.a;
        }
    }

    /* loaded from: classes3.dex */
    public static class OtaInfo {
        @SerializedName("cur_version")
        private String a;
        @SerializedName("ota_version")
        private String b;

        public void setCurVersion(String str) {
            this.a = str;
        }

        public String getCurVersion() {
            return this.a;
        }

        public void setOtaVersion(String str) {
            this.b = str;
        }

        public String getOtaVersion() {
            return this.b;
        }
    }
}
