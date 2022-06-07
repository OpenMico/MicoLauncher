package com.xiaomi.micolauncher.skills.baike.controller.uievent;

/* loaded from: classes3.dex */
public class BaikeQueryEvent {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;

    public BaikeQueryEvent(String str, String str2, String str3, String str4) {
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
    }

    public BaikeQueryEvent(String str, String str2, String str3, String str4, String str5) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
    }

    public String getTitle() {
        return this.b;
    }

    public String getContent() {
        return this.c;
    }

    public String getUrl() {
        return this.d;
    }

    public String getToSpeak() {
        return this.e;
    }

    public String getDialogId() {
        return this.a;
    }
}
