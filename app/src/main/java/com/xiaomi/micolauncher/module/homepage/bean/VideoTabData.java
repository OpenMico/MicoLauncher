package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes3.dex */
public class VideoTabData {
    @SerializedName("tabKey")
    private String a;
    @SerializedName(IChannel.EXTRA_ERROR_DESC)
    private String b;
    @SerializedName("type")
    private int c;

    public String getTabKey() {
        return this.a;
    }

    public void setTabKey(String str) {
        this.a = str;
    }

    public String getDesc() {
        return this.b;
    }

    public void setDesc(String str) {
        this.b = str;
    }

    public int getType() {
        return this.c;
    }

    public void setType(int i) {
        this.c = i;
    }
}
