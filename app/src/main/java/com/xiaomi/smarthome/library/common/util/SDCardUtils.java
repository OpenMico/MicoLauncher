package com.xiaomi.smarthome.library.common.util;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import java.io.File;

/* loaded from: classes4.dex */
public class SDCardUtils {
    public static boolean isSDCardUnavailable() {
        return Environment.getExternalStorageState().equals("removed");
    }

    public static boolean isSDCardBusy() {
        return !Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isSDCardFull() {
        return getSDCardAvailableBytes() <= 102400;
    }

    public static boolean isSDCardUseful() {
        return !isSDCardBusy() && !isSDCardFull() && !isSDCardUnavailable();
    }

    public static long getSDCardAvailableBytes() {
        File externalStorageDirectory;
        if (isSDCardBusy() || (externalStorageDirectory = Environment.getExternalStorageDirectory()) == null || TextUtils.isEmpty(externalStorageDirectory.getPath())) {
            return 0L;
        }
        StatFs statFs = new StatFs(externalStorageDirectory.getPath());
        return statFs.getBlockSize() * (statFs.getAvailableBlocks() - 4);
    }
}
