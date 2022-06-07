package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.appstore.bean.AppInfo;
import java.util.List;

/* loaded from: classes3.dex */
public class AppCollection {
    @SerializedName("title")
    private String a;
    @SerializedName("appKeyList")
    private int[] b;
    private List<AppInfo> c;

    public String getTitle() {
        return this.a;
    }

    public void setTitle(String str) {
        this.a = str;
    }

    public int[] getAppKeyList() {
        return this.b;
    }

    public void setAppKeyList(int[] iArr) {
        this.b = iArr;
    }

    public List<AppInfo> getAppInfos() {
        return this.c;
    }

    public void setAppInfos(List<AppInfo> list) {
        this.c = list;
    }
}
