package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes3.dex */
public class RecommendCard {
    @SerializedName("cartType")
    private int a;
    @SerializedName("appKey")
    private long b;
    @SerializedName("appTabKey")
    private String c;
    @SerializedName("title")
    private String d;
    @SerializedName("description")
    private String e;
    @SerializedName("coverUrl")
    private String f;
    @SerializedName("appKeyList")
    private List<Long> g;
    @SerializedName("itemList")
    private List<AppItem> h;
    @SerializedName("trackKey")
    private String i;

    public int getCardType() {
        return this.a;
    }

    public void setCardType(int i) {
        this.a = i;
    }

    public String getAppTabKey() {
        return this.c;
    }

    public void setAppTabKey(String str) {
        this.c = str;
    }

    public String getTitle() {
        return this.d;
    }

    public void setTitle(String str) {
        this.d = str;
    }

    public String getDescription() {
        return this.e;
    }

    public void setDescription(String str) {
        this.e = str;
    }

    public String getCoverUrl() {
        return this.f;
    }

    public void setCoverUrl(String str) {
        this.f = str;
    }

    public List<Long> getAppKeyList() {
        return this.g;
    }

    public void setAppKeyList(List<Long> list) {
        this.g = list;
    }

    public List<AppItem> getItemList() {
        return this.h;
    }

    public void setItemList(List<AppItem> list) {
        this.h = list;
    }

    public long getAppKey() {
        return this.b;
    }

    public void setAppKey(int i) {
        this.b = i;
    }

    public String getTrackKey() {
        return this.i;
    }

    public void setTrackKey(String str) {
        this.i = str;
    }
}
