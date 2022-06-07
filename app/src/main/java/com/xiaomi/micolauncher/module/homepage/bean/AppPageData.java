package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.appstore.bean.AppInfo;
import java.util.List;

/* loaded from: classes3.dex */
public class AppPageData {
    @SerializedName("tabList")
    private List<CategoryTab> a;
    @SerializedName("recommendCardList")
    private List<RecommendCard> b;
    @SerializedName("appList")
    private List<AppInfo> c;

    public List<CategoryTab> getTabList() {
        return this.a;
    }

    public void setTabList(List<CategoryTab> list) {
        this.a = list;
    }

    public List<RecommendCard> getRecommendCardList() {
        return this.b;
    }

    public void setRecommendCardList(List<RecommendCard> list) {
        this.b = list;
    }

    public List<AppInfo> getAppList() {
        return this.c;
    }

    public void setAppList(List<AppInfo> list) {
        this.c = list;
    }
}
