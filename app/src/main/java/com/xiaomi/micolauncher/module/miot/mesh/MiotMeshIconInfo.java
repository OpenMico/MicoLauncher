package com.xiaomi.micolauncher.module.miot.mesh;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotMeshIconInfo {
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("result")
    public ResultBean result;

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public ResultBean getResult() {
        return this.result;
    }

    public void setResult(ResultBean resultBean) {
        this.result = resultBean;
    }

    public String toString() {
        ResultBean resultBean = this.result;
        if (resultBean == null || resultBean.getConfigs() == null || this.result.getConfigs().size() <= 0) {
            return "MiotMeshIconInfo{code=" + this.code + ", message='" + this.message + "', result=" + this.result + '}';
        }
        String icon_real = this.result.getConfigs().get(0).getIcon_real();
        String name = this.result.getConfigs().get(0).getName();
        return "MiotMeshIconInfo{name='" + name + "', iconUrl=" + icon_real + '}';
    }

    /* loaded from: classes3.dex */
    public static class ResultBean {
        @SerializedName("configs")
        private List<ConfigsBean> a;

        public List<ConfigsBean> getConfigs() {
            return this.a;
        }

        public void setConfigs(List<ConfigsBean> list) {
            this.a = list;
        }

        /* loaded from: classes3.dex */
        public static class ConfigsBean {
            @SerializedName("model")
            private String a;
            @SerializedName("name")
            private String b;
            @SerializedName("neg_screen")
            private NegScreenBean c;
            @SerializedName("wifi_rssi")
            private int d;
            @SerializedName("icon_real")
            private String e;

            public String getModel() {
                return this.a;
            }

            public void setModel(String str) {
                this.a = str;
            }

            public String getName() {
                return this.b;
            }

            public void setName(String str) {
                this.b = str;
            }

            public NegScreenBean getNeg_screen() {
                return this.c;
            }

            public void setNeg_screen(NegScreenBean negScreenBean) {
                this.c = negScreenBean;
            }

            public int getWifi_rssi() {
                return this.d;
            }

            public void setWifi_rssi(int i) {
                this.d = i;
            }

            public String getIcon_real() {
                return this.e;
            }

            public void setIcon_real(String str) {
                this.e = str;
            }

            /* loaded from: classes3.dex */
            public static class NegScreenBean {
                private String a;
                private String b;
                private String c;
                private String d;

                public String getNeg_480() {
                    return this.a;
                }

                public void setNeg_480(String str) {
                    this.a = str;
                }

                public String getShort_480() {
                    return this.b;
                }

                public void setShort_480(String str) {
                    this.b = str;
                }

                public String getNeg_video() {
                    return this.c;
                }

                public void setNeg_video(String str) {
                    this.c = str;
                }

                public String getShort_video() {
                    return this.d;
                }

                public void setShort_video(String str) {
                    this.d = str;
                }
            }
        }
    }
}
