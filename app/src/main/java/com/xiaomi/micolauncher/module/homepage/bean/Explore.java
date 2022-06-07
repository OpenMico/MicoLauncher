package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes3.dex */
public class Explore {
    @SerializedName("version")
    private int a;
    @SerializedName("data")
    private List<ExploreBean> b;

    public int getVersion() {
        return this.a;
    }

    public void setVersion(int i) {
        this.a = i;
    }

    public List<ExploreBean> getExplores() {
        return this.b;
    }

    public void setExplores(List<ExploreBean> list) {
        this.b = list;
    }

    /* loaded from: classes3.dex */
    public static class ExploreBean {
        @SerializedName("type")
        private String a;
        @SerializedName("content")
        private String b;

        public String getType() {
            return this.a;
        }

        public void setType(String str) {
            this.a = str;
        }

        public String getContent() {
            return this.b;
        }

        public void setContent(String str) {
            this.b = str;
        }
    }
}
