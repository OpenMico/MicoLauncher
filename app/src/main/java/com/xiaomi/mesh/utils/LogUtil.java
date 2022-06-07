package com.xiaomi.mesh.utils;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mesh.internal.IMeshLogListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public final class LogUtil {
    private static boolean DEBUG = true;
    public static final int LOG_DEBUG = 3;
    public static final int LOG_ERROR = 6;
    public static final int LOG_INFO = 4;
    public static final int LOG_VERBOSE = 2;
    public static final int LOG_WARN = 5;
    private static final int MAX_TAG_LENGTH = 23;
    private static ExecutorService sExecutorService = Executors.newSingleThreadExecutor();
    private static IMeshLogListener sListener;

    public static void enableDebugLog(boolean z) {
        DEBUG = z;
    }

    public static void setLogListener(IMeshLogListener iMeshLogListener) {
        sListener = iMeshLogListener;
    }

    public static void v(String str, String str2) {
        if (DEBUG) {
            String validTag = getValidTag(str);
            Log.v(validTag, str2);
            logCallback(2, validTag, str2);
        }
    }

    public static void d(String str, String str2) {
        if (DEBUG) {
            String validTag = getValidTag(str);
            Log.d(validTag, str2);
            logCallback(3, validTag, str2);
        }
    }

    public static void i(String str, String str2) {
        if (DEBUG) {
            String validTag = getValidTag(str);
            Log.i(validTag, str2);
            logCallback(4, validTag, str2);
        }
    }

    public static void w(String str, String str2) {
        if (DEBUG) {
            String validTag = getValidTag(str);
            Log.w(validTag, str2);
            logCallback(5, validTag, str2);
        }
    }

    public static void e(String str, String str2) {
        if (DEBUG) {
            String validTag = getValidTag(str);
            Log.e(validTag, str2);
            logCallback(6, validTag, str2);
        }
    }

    public static String getValidTag(String str) {
        return TextUtils.isEmpty(str) ? "" : str.length() <= 23 ? str : str.substring(0, 23);
    }

    private static void logCallback(final int i, final String str, final String str2) {
        if (sListener != null) {
            sExecutorService.submit(new Runnable() { // from class: com.xiaomi.mesh.utils.LogUtil.1
                @Override // java.lang.Runnable
                public void run() {
                    if (LogUtil.sListener != null) {
                        try {
                            switch (i) {
                                case 2:
                                    LogUtil.sListener.logV(str, str2);
                                    break;
                                case 3:
                                    LogUtil.sListener.logD(str, str2);
                                    break;
                                case 4:
                                    LogUtil.sListener.logI(str, str2);
                                    break;
                                case 5:
                                    LogUtil.sListener.logW(str, str2);
                                    break;
                                case 6:
                                    LogUtil.sListener.logE(str, str2);
                                    break;
                                default:
                            }
                        } catch (Exception unused) {
                        }
                    }
                }
            });
        }
    }
}
