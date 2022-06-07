package com.xiaomi.micolauncher.common.event;

import androidx.annotation.NonNull;
import java.io.File;

/* loaded from: classes3.dex */
public class LocalAlbumSingleFileAdded {
    private File a;

    public LocalAlbumSingleFileAdded(@NonNull File file) {
        this.a = file;
    }

    @NonNull
    public File getFile() {
        return this.a;
    }
}
