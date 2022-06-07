package com.xiaomi.micolauncher.module.setting.utils;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class WifiDeviceBindKeyResult {
    @SerializedName("ret")
    private int a;
    @SerializedName("bindkey")
    private String b;

    public int getRet() {
        return this.a;
    }

    public void setRet(int i) {
        this.a = i;
    }

    public String getBindkey() {
        return this.b;
    }

    public void setBindkey(String str) {
        this.b = str;
    }
}
