package com.xiaomi.micolauncher.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient;
import java.io.File;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class Constants {
    public static final String ACTION_DISPLAY_BAR_EXTRA_TEXT = "com.android.systemui.actions.ACTION_DISPLAY_BAR_EXTRA_TEXT";
    public static final String ACTION_RESET_VIEW_POSITION = "com.xiaomi.micovoip.RESET_SCROLL_VIEW_POSITION";
    public static final String AI_SERVICE_SID = "ai-service";
    public static final boolean DEBUG = false;
    public static final int JOB_CHECK_ALARM_STATE_ID = -102;
    public static final int JOB_ID_REFRESH_WEATHER = -101;
    public static final int JOB_SERVICE_STAT_ID = -100;
    public static final String KEY_CONTENT = "content";
    public static final String MICO_SID = a();
    public static final String MICO_VOIP_PKG = "com.xiaomi.micovoip";
    public static final boolean MIOT_ENABLED = true;
    public static final String NOTIFICATION_TAG = "micolauncher";
    public static final String ONETRACK_APP_ID = "31000000620";
    public static final String OTT_MINI_PROGRAM_SID = "ott_miniprogram";
    public static final String PKG_SYSTEM_UI = "com.android.systemui";
    public static final String PUSH_CHANNEL = "com.xiaomi.micolauncher";
    public static final String XM_APP_ID = "2882303761517783133";
    public static final String XM_APP_KEY = "5831778382133";
    public static final String XM_APP_REDIRECT = "https://www.mico.miwifi.com";
    public static final String XM_APP_SECRET = "WAKU2PHgqt7asj5PMNwWDg==";
    private static String a;

    /* loaded from: classes3.dex */
    public static class CpType {
        public static final String IQIYI = "iqiyi";
        public static final String LEKAN = "lekan";
        public static final String NONE = "none";
        public static final String XINGYU = "xingyu";
    }

    /* loaded from: classes3.dex */
    public static class TimeConstants {
        public static final int AUTO_DISMISS_LONG = 60000;
        public static final int AUTO_DISMISS_SHORT = 7000;
        public static final int AUTO_RESET_CALENDAR_INTERVAL = 60000;
        public static final int DEFAULT_GLOBAL_ACTION_DELAY_MS = 800;
        public static final int TIPS_DELAY_TIME = 3000;
        public static final int TIPS_INTERVAL = 7000;
        public static final long SECONDS_10S_TO_MILLIS = TimeUnit.SECONDS.toMillis(10);
        public static final long RESET_SCROLLVIEW_POSITION_TIME_THRESHOLD = TimeUnit.MINUTES.toMillis(10);
    }

    private static String a() {
        return "micoapi";
    }

    public static String getExternalStoragePath(@NonNull Context context) {
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir == null) {
            externalFilesDir = context.getFilesDir();
        }
        return externalFilesDir.getAbsolutePath();
    }

    public static String getLogDirectory(@NonNull Context context) {
        return getExternalStoragePath(context) + File.separator + MicoVoipClient.MICOLAUNCHER_LOG;
    }

    @Deprecated
    public static String getCacheDirectory() {
        return getCacheDirectory(MicoApplication.getGlobalContext());
    }

    public static String getCacheDirectory(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir == null) {
            externalCacheDir = context.getCacheDir();
        }
        return externalCacheDir.getAbsolutePath();
    }

    @SuppressLint({"MissingPermission"})
    public static String getSn() {
        if (a == null) {
            a = Build.getSerial();
        }
        return a;
    }

    public static String getSnSimple() {
        int length;
        String sn = getSn();
        return (sn == null || (length = sn.length()) <= 4) ? sn : sn.substring(length - 4);
    }
}
