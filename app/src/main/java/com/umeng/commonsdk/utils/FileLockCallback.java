package com.umeng.commonsdk.utils;

import java.io.File;

/* loaded from: classes2.dex */
public interface FileLockCallback {
    boolean onFileLock(File file, int i);

    boolean onFileLock(String str);

    boolean onFileLock(String str, Object obj);
}
