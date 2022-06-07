package com.xiaomi.push;

import java.io.File;
import java.io.FileFilter;

/* loaded from: classes4.dex */
final class j implements FileFilter {
    @Override // java.io.FileFilter
    public boolean accept(File file) {
        return file.isDirectory();
    }
}
