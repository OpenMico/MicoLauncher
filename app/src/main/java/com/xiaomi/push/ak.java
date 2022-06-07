package com.xiaomi.push;

import android.text.TextUtils;
import java.io.File;
import java.io.FilenameFilter;

/* loaded from: classes4.dex */
final class ak implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return !TextUtils.isEmpty(str) && !str.toLowerCase().endsWith(".lock");
    }
}
