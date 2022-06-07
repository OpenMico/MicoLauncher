package com.allenliu.versionchecklib.v2.builder;

import com.allenliu.versionchecklib.R;

/* loaded from: classes.dex */
public class NotificationBuilder {
    private String b;
    private String c;
    private String d;
    private int a = R.mipmap.ic_launcher;
    private boolean e = true;

    public static NotificationBuilder create() {
        return new NotificationBuilder();
    }

    private NotificationBuilder() {
    }

    public int getIcon() {
        return this.a;
    }

    public NotificationBuilder setIcon(int i) {
        this.a = i;
        return this;
    }

    public String getContentTitle() {
        return this.b;
    }

    public NotificationBuilder setContentTitle(String str) {
        this.b = str;
        return this;
    }

    public String getTicker() {
        return this.c;
    }

    public NotificationBuilder setTicker(String str) {
        this.c = str;
        return this;
    }

    public String getContentText() {
        return this.d;
    }

    public NotificationBuilder setContentText(String str) {
        this.d = str;
        return this;
    }

    public boolean isRingtone() {
        return this.e;
    }

    public NotificationBuilder setRingtone(boolean z) {
        this.e = z;
        return this;
    }
}
