package com.xiaomi.micolauncher.common.crash;

import android.os.Environment;
import java.io.File;

/* loaded from: classes3.dex */
class FileDirectoryManager {
    private static final String LAUNCHER_PARENT_LOG_DIR = "mico";
    private static final String WIFI_LOGS_DIR_NAME = "wifiLogs";

    FileDirectoryManager() {
    }

    private static File getLauncherLogDir() {
        return new File(Environment.getExternalStorageDirectory(), "mico");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static File createLauncherLogDir() {
        File launcherLogDir = getLauncherLogDir();
        launcherLogDir.mkdir();
        return launcherLogDir;
    }

    public static File getWifiLogDir() {
        return new File(Environment.getExternalStorageDirectory(), WIFI_LOGS_DIR_NAME);
    }
}
