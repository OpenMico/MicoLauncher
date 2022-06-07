package com.xiaomi.mico.settingslib.core;

/* loaded from: classes3.dex */
public enum AlbumType {
    JOIN(0),
    ORIGINAL(1),
    CROP(2);
    
    private int id;

    AlbumType(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }
}
