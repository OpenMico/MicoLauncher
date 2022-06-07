package com.xiaomi.micolauncher.skills.music.controller;

/* loaded from: classes3.dex */
public enum RetCode {
    UNKNOWN(-1),
    SUCCESS(0),
    FAILED(1),
    UNSUPPORTED(2);
    
    private int id;

    RetCode(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }
}
