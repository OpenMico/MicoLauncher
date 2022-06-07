package com.xiaomi.miplay.mylibrary.utils;

import android.util.Log;
import java.io.FileOutputStream;

/* loaded from: classes4.dex */
public class WriteFile {
    public static final String SDPATH = "/mnt/sdcard";
    private static final String a = "WriteFile";

    public static void writeFileToSdcard(String str, byte[] bArr, int i, boolean z) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str, z);
            fileOutputStream.write(bArr, 0, i);
            fileOutputStream.close();
        } catch (Exception e) {
            Log.e(a, "error", e);
        }
    }
}
