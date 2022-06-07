package com.xiaomi.micolauncher.skills.mitv;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class ActionDeviceInfo {
    @SerializedName("data")
    private DataBean a;
    @SerializedName("code")
    private int b;

    public DataBean getData() {
        return this.a;
    }

    public void setData(DataBean dataBean) {
        this.a = dataBean;
    }

    public int getCode() {
        return this.b;
    }

    public void setCode(int i) {
        this.b = i;
    }

    /* loaded from: classes3.dex */
    public static class DataBean {
        @SerializedName("version_name")
        private String a;
        @SerializedName("ability")
        private AbilityBean b;
        @SerializedName("bluetooth_mac")
        private String c;
        @SerializedName("version")
        private int d;

        public String getVersion_name() {
            return this.a;
        }

        public void setVersion_name(String str) {
            this.a = str;
        }

        public AbilityBean getAbility() {
            return this.b;
        }

        public void setAbility(AbilityBean abilityBean) {
            this.b = abilityBean;
        }

        public String getBluetooth_mac() {
            return this.c;
        }

        public void setBluetooth_mac(String str) {
            this.c = str;
        }

        public int getVersion() {
            return this.d;
        }

        public void setVersion(int i) {
            this.d = i;
        }

        /* loaded from: classes3.dex */
        public static class AbilityBean {
            @SerializedName("voice")
            private int a;
            @SerializedName("voice_module")
            private boolean b;

            public int getVoice() {
                return this.a;
            }

            public void setVoice(int i) {
                this.a = i;
            }

            public boolean isVoice_module() {
                return this.b;
            }

            public void setVoice_module(boolean z) {
                this.b = z;
            }
        }
    }
}
