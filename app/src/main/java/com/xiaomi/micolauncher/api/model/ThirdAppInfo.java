package com.xiaomi.micolauncher.api.model;

import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public class ThirdAppInfo {
    private String appName;
    private Drawable image;
    private String packageName;

    public Drawable getImage() {
        return this.image;
    }

    public void setImage(Drawable drawable) {
        this.image = drawable;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }
}
