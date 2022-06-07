package com.xiaomi.onetrack.util;

import android.text.TextUtils;
import java.io.File;
import java.io.FilenameFilter;

/* loaded from: classes4.dex */
final class c implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return TextUtils.isDigitsOnly(str);
    }
}
