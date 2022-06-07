package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class AppItem {
    @SerializedName("itemName")
    private String a;
    @SerializedName("description")
    private String b;
    @SerializedName("appKey")
    private long c;

    public String getItemName() {
        return this.a;
    }

    public void setItemName(String str) {
        this.a = str;
    }

    public String getDescription() {
        return this.b;
    }

    public void setDescription(String str) {
        this.b = str;
    }

    public long getAppKey() {
        return this.c;
    }

    public void setAppKey(long j) {
        this.c = j;
    }
}
