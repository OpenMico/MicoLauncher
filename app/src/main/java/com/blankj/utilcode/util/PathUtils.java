package com.blankj.utilcode.util;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;

/* loaded from: classes.dex */
public final class PathUtils {
    private static final char a = File.separatorChar;

    private PathUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String join(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return str;
        }
        if (str == null) {
            str = "";
        }
        int length = str.length();
        String a2 = a(str2);
        if (length == 0) {
            return a + a2;
        } else if (str.charAt(length - 1) == a) {
            return str + a2;
        } else {
            return str + a + a2;
        }
    }

    private static String a(String str) {
        char[] charArray = str.toCharArray();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < charArray.length; i3++) {
            if (charArray[i3] != a) {
                if (i == -1) {
                    i = i3;
                }
                i2 = i3;
            }
        }
        if (i >= 0 && i2 >= i) {
            return str.substring(i, i2 + 1);
        }
        throw new IllegalArgumentException("segment of <" + str + "> is illegal");
    }

    public static String getRootPath() {
        return a(Environment.getRootDirectory());
    }

    public static String getDataPath() {
        return a(Environment.getDataDirectory());
    }

    public static String getDownloadCachePath() {
        return a(Environment.getDownloadCacheDirectory());
    }

    public static String getInternalAppDataPath() {
        if (Build.VERSION.SDK_INT < 24) {
            return Utils.getApp().getApplicationInfo().dataDir;
        }
        return a(Utils.getApp().getDataDir());
    }

    public static String getInternalAppCodeCacheDir() {
        if (Build.VERSION.SDK_INT >= 21) {
            return a(Utils.getApp().getCodeCacheDir());
        }
        return Utils.getApp().getApplicationInfo().dataDir + "/code_cache";
    }

    public static String getInternalAppCachePath() {
        return a(Utils.getApp().getCacheDir());
    }

    public static String getInternalAppDbsPath() {
        return Utils.getApp().getApplicationInfo().dataDir + "/databases";
    }

    public static String getInternalAppDbPath(String str) {
        return a(Utils.getApp().getDatabasePath(str));
    }

    public static String getInternalAppFilesPath() {
        return a(Utils.getApp().getFilesDir());
    }

    public static String getInternalAppSpPath() {
        return Utils.getApp().getApplicationInfo().dataDir + "/shared_prefs";
    }

    public static String getInternalAppNoBackupFilesPath() {
        if (Build.VERSION.SDK_INT >= 21) {
            return a(Utils.getApp().getNoBackupFilesDir());
        }
        return Utils.getApp().getApplicationInfo().dataDir + "/no_backup";
    }

    public static String getExternalStoragePath() {
        return !b.s() ? "" : a(Environment.getExternalStorageDirectory());
    }

    public static String getExternalMusicPath() {
        return !b.s() ? "" : a(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
    }

    public static String getExternalPodcastsPath() {
        return !b.s() ? "" : a(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS));
    }

    public static String getExternalRingtonesPath() {
        return !b.s() ? "" : a(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES));
    }

    public static String getExternalAlarmsPath() {
        return !b.s() ? "" : a(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS));
    }

    public static String getExternalNotificationsPath() {
        return !b.s() ? "" : a(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS));
    }

    public static String getExternalPicturesPath() {
        return !b.s() ? "" : a(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    public static String getExternalMoviesPath() {
        return !b.s() ? "" : a(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
    }

    public static String getExternalDownloadsPath() {
        return !b.s() ? "" : a(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    public static String getExternalDcimPath() {
        return !b.s() ? "" : a(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

    public static String getExternalDocumentsPath() {
        if (!b.s()) {
            return "";
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return a(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
        }
        return a(Environment.getExternalStorageDirectory()) + "/Documents";
    }

    public static String getExternalAppDataPath() {
        File externalCacheDir;
        return (b.s() && (externalCacheDir = Utils.getApp().getExternalCacheDir()) != null) ? a(externalCacheDir.getParentFile()) : "";
    }

    public static String getExternalAppCachePath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalCacheDir());
    }

    public static String getExternalAppFilesPath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalFilesDir(null));
    }

    public static String getExternalAppMusicPath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MUSIC));
    }

    public static String getExternalAppPodcastsPath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PODCASTS));
    }

    public static String getExternalAppRingtonesPath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_RINGTONES));
    }

    public static String getExternalAppAlarmsPath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_ALARMS));
    }

    public static String getExternalAppNotificationsPath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));
    }

    public static String getExternalAppPicturesPath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    public static String getExternalAppMoviesPath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MOVIES));
    }

    public static String getExternalAppDownloadPath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
    }

    public static String getExternalAppDcimPath() {
        return !b.s() ? "" : a(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DCIM));
    }

    public static String getExternalAppDocumentsPath() {
        if (!b.s()) {
            return "";
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return a(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
        }
        return a(Utils.getApp().getExternalFilesDir(null)) + "/Documents";
    }

    public static String getExternalAppObbPath() {
        return !b.s() ? "" : a(Utils.getApp().getObbDir());
    }

    public static String getRootPathExternalFirst() {
        String externalStoragePath = getExternalStoragePath();
        return TextUtils.isEmpty(externalStoragePath) ? getRootPath() : externalStoragePath;
    }

    public static String getAppDataPathExternalFirst() {
        String externalAppDataPath = getExternalAppDataPath();
        return TextUtils.isEmpty(externalAppDataPath) ? getInternalAppDataPath() : externalAppDataPath;
    }

    public static String getFilesPathExternalFirst() {
        String externalAppFilesPath = getExternalAppFilesPath();
        return TextUtils.isEmpty(externalAppFilesPath) ? getInternalAppFilesPath() : externalAppFilesPath;
    }

    public static String getCachePathExternalFirst() {
        String externalAppCachePath = getExternalAppCachePath();
        return TextUtils.isEmpty(externalAppCachePath) ? getInternalAppCachePath() : externalAppCachePath;
    }

    private static String a(File file) {
        return file == null ? "" : file.getAbsolutePath();
    }
}
