package com.xiaomi.miot.support;

import android.util.Log;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public class MiotLog {
    private static final String PREFIX = "[MIOT-SUPPORT] ";
    private static MiotLogger sLogger = new MiotLogger() { // from class: com.xiaomi.miot.support.MiotLog.1
        private static final String TAG = "MIOT";

        @Override // com.xiaomi.miot.support.MiotLogger
        public void d(String str) {
            Log.d(TAG, str);
        }

        @Override // com.xiaomi.miot.support.MiotLogger
        public void i(String str) {
            Log.i(TAG, str);
        }

        @Override // com.xiaomi.miot.support.MiotLogger
        public void w(String str) {
            Log.w(TAG, str);
        }

        @Override // com.xiaomi.miot.support.MiotLogger
        public void e(String str) {
            Log.e(TAG, str);
        }
    };

    public static void setLogger(@NonNull MiotLogger miotLogger) {
        sLogger = miotLogger;
    }

    public static void d(String str) {
        MiotLogger miotLogger = sLogger;
        miotLogger.d(PREFIX + str);
    }

    public static void i(String str) {
        MiotLogger miotLogger = sLogger;
        miotLogger.i(PREFIX + str);
    }

    public static void w(String str) {
        MiotLogger miotLogger = sLogger;
        miotLogger.w(PREFIX + str);
    }

    public static void e(String str) {
        MiotLogger miotLogger = sLogger;
        miotLogger.e(PREFIX + str);
    }
}
