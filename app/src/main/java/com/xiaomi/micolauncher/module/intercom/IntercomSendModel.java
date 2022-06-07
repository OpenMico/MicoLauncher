package com.xiaomi.micolauncher.module.intercom;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class IntercomSendModel {
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    int g;

    public IntercomSendModel(String str, String str2, String str3, String str4, String str5, String str6, int i) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.g = i;
    }

    /* loaded from: classes3.dex */
    static class a {
        @SerializedName("send_type")
        public String a;
        @SerializedName("send_device_type")
        public String b;
        @SerializedName("send_device_category")
        public String c;
        @SerializedName("sender_device_id")
        public String d;

        public a(String str, String str2, String str3, String str4) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
        }
    }
}
