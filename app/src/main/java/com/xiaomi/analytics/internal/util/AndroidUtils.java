package com.xiaomi.analytics.internal.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.Signature;
import com.xiaomi.idm.api.IDMServer;
import java.io.File;
import java.util.List;

/* loaded from: classes3.dex */
public class AndroidUtils {
    public static Context getApplicationContext(Context context) {
        return (context == null || context.getApplicationContext() == null) ? context : context.getApplicationContext();
    }

    public static boolean isForeground(Context context) {
        try {
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningTasks(1);
            if (runningTasks != null && !runningTasks.isEmpty()) {
                if (runningTasks.get(0).topActivity.getPackageName().equals(context.getPackageName())) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static Signature[] getSignature(Context context, File file) {
        try {
            return context.getPackageManager().getPackageArchiveInfo(file.getPath(), 64).signatures;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Signature[] getSignature(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 64).signatures;
        } catch (Exception unused) {
            return null;
        }
    }
}
