package com.zlw.main.recorderlib.utils;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

/* loaded from: classes4.dex */
public class FileUtils {
    public static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean createOrExistsDir(String str) {
        return createOrExistsDir(getFileByPath(str));
    }

    public static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? file.mkdirs() : file.isDirectory());
    }

    public static File getFileByPath(String str) {
        if (a(str)) {
            return null;
        }
        return new File(str);
    }

    private static boolean a(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getNowString(DateFormat dateFormat) {
        return millis2String(System.currentTimeMillis(), dateFormat);
    }

    public static String millis2String(long j, DateFormat dateFormat) {
        return dateFormat.format(new Date(j));
    }

    public static boolean isFileExists(String str) {
        return isFileExists(getFileByPath(str));
    }

    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }
}
