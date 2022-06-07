package com.xiaomi.miot.host.lan.impl;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.miot.typedef.listener.MiotLogListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public final class MiotLogUtil {
    private static final int MAX_TAG_LENGTH = 23;
    private static ExecutorService sExecutorService = Executors.newSingleThreadExecutor();
    private static MiotLogListener sListener;

    public static void setMiotLogListener(MiotLogListener miotLogListener) {
        sListener = miotLogListener;
    }

    public static void v(String str, String str2) {
        String validTag = getValidTag(str);
        Log.v(validTag, str2);
        logCallback(2, validTag, str2);
    }

    public static void d(String str, String str2) {
        String validTag = getValidTag(str);
        Log.d(validTag, str2);
        logCallback(3, validTag, str2);
    }

    public static void i(String str, String str2) {
        String validTag = getValidTag(str);
        Log.i(validTag, str2);
        logCallback(4, validTag, str2);
    }

    public static void w(String str, String str2) {
        String validTag = getValidTag(str);
        Log.w(validTag, str2);
        logCallback(5, validTag, str2);
    }

    public static void e(String str, String str2) {
        String validTag = getValidTag(str);
        Log.e(validTag, str2);
        logCallback(6, validTag, str2);
    }

    public static void specialLog(String str, String str2) {
        Log.d(getValidTag(str), str2);
    }

    public static String getValidTag(String str) {
        return TextUtils.isEmpty(str) ? "" : str.length() <= 23 ? str : str.substring(0, 23);
    }

    private static void logCallback(final int i, final String str, final String str2) {
        if (sListener != null) {
            sExecutorService.submit(new Runnable() { // from class: com.xiaomi.miot.host.lan.impl.MiotLogUtil.1
                @Override // java.lang.Runnable
                public void run() {
                    if (MiotLogUtil.sListener != null) {
                        MiotLogUtil.sListener.log(i, str, str2);
                    }
                }
            });
        }
    }
}
