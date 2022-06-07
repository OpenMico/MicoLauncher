package com.xiaomi.micolauncher.module.appstore.manager;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import java.util.List;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes3.dex */
public class AppStoreOtaResult {
    @SerializedName("id")
    private int a;
    @SerializedName(SchemaActivity.KEY_METHOD)
    private String b;
    @SerializedName("params")
    private OtaResultSummay c;

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

    public void setParams(OtaResultSummay otaResultSummay) {
        this.c = otaResultSummay;
    }

    public OtaResultSummay getParams() {
        return this.c;
    }

    /* loaded from: classes3.dex */
    public static class OtaResultSummay {
        @SerializedName("ota.result")
        private OtaResult a;
        @SerializedName("model")
        private String b;
        @SerializedName("fw_ver")
        private String c;
        @SerializedName("mcu_fw_ver")
        private String d;

        public void setParams(OtaResult otaResult) {
            this.a = otaResult;
        }

        public OtaResult getParams() {
            return this.a;
        }

        public void setModel(String str) {
            this.b = str;
        }

        public String getModel() {
            return this.b;
        }

        public void setFirmwareVer(String str) {
            this.c = str;
        }

        public String getFirmwareVer() {
            return this.c;
        }

        public void setMcuFirmwareVer(String str) {
            this.d = str;
        }

        public String getMcuFirmwareVer() {
            return this.d;
        }
    }

    /* loaded from: classes3.dex */
    public static class OtaResult {
        @SerializedName("type")
        private int a;
        @SerializedName("result")
        private int b;
        @SerializedName("costtime")
        private int c;
        @SerializedName("miio_bt")
        private OtaInfo d;

        public void setType(int i) {
            this.a = i;
        }

        public int getType() {
            return this.a;
        }

        public void setResult(int i) {
            this.b = i;
        }

        public int getResult() {
            return this.b;
        }

        public int getCosttime() {
            return this.c;
        }

        public void setCosttime(int i) {
            this.c = i;
        }

        public void setMeshGateway(OtaInfo otaInfo) {
            this.d = otaInfo;
        }

        public OtaInfo getMeshGateway() {
            return this.d;
        }
    }

    /* loaded from: classes3.dex */
    public static class OtaInfo {
        @SerializedName("result")
        private List<Integer> a;
        @SerializedName(IChannel.EXTRA_CLOSE_REASON)
        private int b;
        @SerializedName("cur_version")
        private String c;
        @SerializedName("ota_version")
        private String d;

        public void setResult(List<Integer> list) {
            this.a = list;
        }

        public List<Integer> getResult() {
            return this.a;
        }

        public void setReason(int i) {
            this.b = i;
        }

        public int getReason() {
            return this.b;
        }

        public void setCurVersion(String str) {
            this.c = str;
        }

        public String getCurVersion() {
            return this.c;
        }

        public void setOtaVersion(String str) {
            this.d = str;
        }

        public String getOtaVersion() {
            return this.d;
        }
    }
}
