package com.xiaomi.phonenum.utils;

import android.util.Log;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class LoggerManager {
    private static String a = "phoneNum";
    private static volatile Logger b = new Logger() { // from class: com.xiaomi.phonenum.utils.LoggerManager.1
        @Override // com.xiaomi.phonenum.utils.Logger
        public void d(String str, String str2) {
        }

        @Override // com.xiaomi.phonenum.utils.Logger
        public void i(String str, String str2) {
            String str3 = LoggerManager.a;
            Log.i(str3, str + StringUtils.SPACE + str2);
        }

        @Override // com.xiaomi.phonenum.utils.Logger
        public void e(String str, String str2) {
            String str3 = LoggerManager.a;
            Log.e(str3, str + StringUtils.SPACE + str2);
        }

        @Override // com.xiaomi.phonenum.utils.Logger
        public void e(String str, String str2, Throwable th) {
            String str3 = LoggerManager.a;
            Log.e(str3, str + StringUtils.SPACE + str2, th);
        }
    };

    public static Logger getLogger() {
        return b;
    }

    public static void setLogger(Logger logger) {
        b = logger;
    }
}
