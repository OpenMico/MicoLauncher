package com.xiaomi.micolauncher.common.event;

/* loaded from: classes3.dex */
public class LocalAlbumFilesUpdated {
    private int a;

    private LocalAlbumFilesUpdated() {
    }

    public LocalAlbumFilesUpdated(int i) {
        this.a = i;
    }

    public int getFilesCount() {
        return this.a;
    }
}
