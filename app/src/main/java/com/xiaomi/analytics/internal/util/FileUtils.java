package com.xiaomi.analytics.internal.util;

import java.io.File;

/* loaded from: classes3.dex */
public class FileUtils {
    public static void deleteFile(File file) {
        try {
            file.delete();
        } catch (Exception unused) {
        }
    }

    public static void deleteAllFiles(String str) {
        deleteAllFiles(new File(str));
    }

    public static void deleteAllFiles(File file) {
        try {
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                for (File file2 : listFiles) {
                    if (file2.isDirectory()) {
                        deleteDir(file2.getAbsolutePath());
                    } else {
                        deleteFile(file2);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    public static void deleteDir(String str) {
        try {
            File file = new File(str);
            deleteAllFiles(str);
            file.delete();
        } catch (Exception unused) {
        }
    }
}
