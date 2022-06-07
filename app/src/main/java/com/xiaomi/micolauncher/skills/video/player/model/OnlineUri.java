package com.xiaomi.micolauncher.skills.video.player.model;

/* loaded from: classes3.dex */
public class OnlineUri {
    public String source;
    public String title;
    public String uri;

    public OnlineUri() {
    }

    public OnlineUri(String str, String str2, String str3) {
        this.uri = str;
        this.title = str2;
        this.source = str3;
    }

    public String toString() {
        return "[" + this.source + ", " + this.uri + ", " + this.title + "]";
    }
}
