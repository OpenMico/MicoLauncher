package com.xiaomi.micolauncher.skills.alarm.model;

import android.content.Context;
import android.content.SharedPreferences;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class WorkDaySyncService {
    public static final int WEEK_MILLIS = 604800000;
    public static final String WORK_DAY_SYNC = "work_day_sync";
    private static final Context b = MicoApplication.getGlobalContext();
    static volatile AtomicBoolean a = new AtomicBoolean(true);

    public static String getWorkDayConfig(String str) {
        String string = b.getSharedPreferences(WORK_DAY_SYNC, 0).getString(str, "");
        return (string == null || string.length() == 0) ? a(str) : string;
    }

    public static void startWorkDayAsyncTask() {
        boolean z = a.get();
        long lastSyncTime = getLastSyncTime();
        boolean z2 = System.currentTimeMillis() - lastSyncTime > 604800000;
        if (!z || (lastSyncTime != 0 && !z2)) {
            L.base.d("no need SysWorkDay, need sync %s, last sync time %s, outof week millis %s", Boolean.valueOf(z), Long.valueOf(lastSyncTime), Boolean.valueOf(z2));
            return;
        }
        L.base.d("begin SysWorkDay..., need sync %s, last sync time %s, outof week millis %s", Boolean.valueOf(z), Long.valueOf(lastSyncTime), Boolean.valueOf(z2));
        new WorkDayAsyncTask().executeOnExecutor(ThreadUtil.getIoThreadPool(), new Void[0]);
    }

    private static String a(String str) {
        return CommonUtils.getFromAssets(b, str, "");
    }

    public static void setWorkDayConfig(Map<String, String> map, String str) {
        SharedPreferences sharedPreferences = b.getSharedPreferences(WORK_DAY_SYNC, 0);
        if (map.isEmpty() || str == null || str.length() == 0) {
            L.base.e("workDayConfig or version is empty!");
            return;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            edit.putString(entry.getKey(), entry.getValue());
        }
        edit.putString("version", str);
        edit.putLong("last_sync_time", System.currentTimeMillis());
        edit.apply();
    }

    public static long getLastSyncTime() {
        return b.getSharedPreferences(WORK_DAY_SYNC, 0).getLong("last_sync_time", 0L);
    }

    public static String getWorkDayVersion() {
        return b.getSharedPreferences(WORK_DAY_SYNC, 0).getString("version", "0");
    }

    public static void setLastSyncTime() {
        SharedPreferences.Editor edit = b.getSharedPreferences(WORK_DAY_SYNC, 0).edit();
        edit.putLong("last_sync_time", System.currentTimeMillis());
        edit.apply();
    }
}
