package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;
import com.milink.base.contract.MiLinkKeys;

/* loaded from: classes3.dex */
public class CategoryTab {
    @SerializedName("appTabKey")
    private String a;
    @SerializedName("appTabName")
    private String b;
    @SerializedName("configurable")
    private boolean c;
    @SerializedName("modeType")
    private String d;
    @SerializedName(MiLinkKeys.PARAM_TAG)
    private String e;

    public String getAppTabKey() {
        return this.a;
    }

    public void setAppTabKey(String str) {
        this.a = str;
    }

    public String getAppTabName() {
        return this.b;
    }

    public void setAppTabName(String str) {
        this.b = str;
    }

    public boolean isConfigurable() {
        return this.c;
    }

    public void setConfigurable(boolean z) {
        this.c = z;
    }

    public String getModeType() {
        return this.d;
    }

    public void setModeType(String str) {
        this.d = str;
    }

    public String getTag() {
        return this.e;
    }

    public void setTag(String str) {
        this.e = str;
    }

    public boolean needShow(boolean z) {
        if ("GENERAL".equals(this.d)) {
            return true;
        }
        if (z) {
            return "CHILD".equals(this.d);
        }
        return "ADULT".equals(this.d);
    }
}
