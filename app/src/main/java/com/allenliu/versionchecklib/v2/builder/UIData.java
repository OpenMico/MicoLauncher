package com.allenliu.versionchecklib.v2.builder;

import android.os.Bundle;

/* loaded from: classes.dex */
public class UIData {
    private final String a = "title";
    private final String b = "content";
    private final String c = "download_url";
    private Bundle d = new Bundle();

    public static UIData create() {
        return new UIData();
    }

    public String getDownloadUrl() {
        return this.d.getString("download_url");
    }

    public UIData setDownloadUrl(String str) {
        this.d.putString("download_url", str);
        return this;
    }

    private UIData() {
        this.d.putString("title", "by `UIData.setTitle()` to set your update title");
        this.d.putString("content", "by `UIData.setContent()` to set your update content ");
    }

    public UIData setTitle(String str) {
        this.d.putString("title", str);
        return this;
    }

    public UIData setContent(String str) {
        this.d.putString("content", str);
        return this;
    }

    public String getTitle() {
        return this.d.getString("title");
    }

    public String getContent() {
        return this.d.getString("content");
    }

    public Bundle getVersionBundle() {
        return this.d;
    }
}
