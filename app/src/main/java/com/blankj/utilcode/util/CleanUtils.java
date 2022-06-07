package com.blankj.utilcode.util;

import android.app.ActivityManager;
import android.os.Environment;
import androidx.annotation.RequiresApi;
import com.xiaomi.idm.api.IDMServer;
import java.io.File;

/* loaded from: classes.dex */
public final class CleanUtils {
    private CleanUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean cleanInternalCache() {
        return b.c(Utils.getApp().getCacheDir());
    }

    public static boolean cleanInternalFiles() {
        return b.c(Utils.getApp().getFilesDir());
    }

    public static boolean cleanInternalDbs() {
        return b.c(new File(Utils.getApp().getFilesDir().getParent(), "databases"));
    }

    public static boolean cleanInternalDbByName(String str) {
        return Utils.getApp().deleteDatabase(str);
    }

    public static boolean cleanInternalSp() {
        return b.c(new File(Utils.getApp().getFilesDir().getParent(), "shared_prefs"));
    }

    public static boolean cleanExternalCache() {
        return "mounted".equals(Environment.getExternalStorageState()) && b.c(Utils.getApp().getExternalCacheDir());
    }

    public static boolean cleanCustomDir(String str) {
        return b.c(b.f(str));
    }

    @RequiresApi(api = 19)
    public static void cleanAppUserData() {
        ((ActivityManager) Utils.getApp().getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).clearApplicationUserData();
    }
}
