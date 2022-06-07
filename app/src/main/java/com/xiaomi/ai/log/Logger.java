package com.xiaomi.ai.log;

import com.xiaomi.mipush.sdk.Constants;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes3.dex */
public final class Logger {
    public static final int LOG_LEVEL_DEBUG = 3;
    public static final int LOG_LEVEL_ERROR = 0;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_WARN = 1;
    private static boolean a = true;
    private static int b = 2;
    private static LoggerHooker c = new LoggerHooker() { // from class: com.xiaomi.ai.log.Logger.1
        @Override // com.xiaomi.ai.log.LoggerHooker
        public void d(String str, String str2) {
            PrintStream printStream = System.out;
            printStream.println("[DEBUG][" + str + "]" + str2);
        }

        @Override // com.xiaomi.ai.log.LoggerHooker
        public void e(String str, String str2) {
            PrintStream printStream = System.out;
            printStream.println("[ERROR][" + str + "]" + str2);
        }

        @Override // com.xiaomi.ai.log.LoggerHooker
        public void i(String str, String str2) {
            PrintStream printStream = System.out;
            printStream.println("[INFO][" + str + "]" + str2);
        }

        @Override // com.xiaomi.ai.log.LoggerHooker
        public void w(String str, String str2) {
            PrintStream printStream = System.out;
            printStream.println("[WARN][" + str + "]" + str2);
        }
    };
    private static LoggerHooker d;

    private Logger() {
    }

    private static String a(String str) {
        if (!a) {
            return str;
        }
        String name = Thread.currentThread().getName();
        if (name != null && name.length() > 64) {
            name = name.substring(0, 64);
        }
        return "[AIVS " + Thread.currentThread().getId() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + name + "] " + str;
    }

    public static void a(String str, String str2) {
        LoggerHooker loggerHooker;
        if (b >= 3 && (loggerHooker = c) != null) {
            loggerHooker.d(a(str), str2);
        }
    }

    public static void a(String str, String str2, boolean z) {
        a(str, str2, z, true);
    }

    public static void a(String str, String str2, boolean z, boolean z2) {
        LoggerHooker loggerHooker;
        LoggerHooker loggerHooker2;
        if (b >= 2) {
            if (z2) {
                str = a(str);
            }
            if (z2 && (loggerHooker2 = c) != null) {
                loggerHooker2.i(str, str2);
            }
            if (z && (loggerHooker = d) != null) {
                loggerHooker.i(str, str2);
            }
        }
    }

    public static void b(String str, String str2) {
        a(str, str2, true);
    }

    public static void b(String str, String str2, boolean z) {
        b(str, str2, z, true);
    }

    public static void b(String str, String str2, boolean z, boolean z2) {
        LoggerHooker loggerHooker;
        LoggerHooker loggerHooker2;
        if (b >= 1) {
            if (z2) {
                str = a(str);
            }
            if (z2 && (loggerHooker2 = c) != null) {
                loggerHooker2.w(str, str2);
            }
            if (z && (loggerHooker = d) != null) {
                loggerHooker.w(str, str2);
            }
        }
    }

    public static void c(String str, String str2) {
        b(str, str2, true);
    }

    public static void c(String str, String str2, boolean z) {
        c(str, str2, z, true);
    }

    public static void c(String str, String str2, boolean z, boolean z2) {
        LoggerHooker loggerHooker;
        LoggerHooker loggerHooker2;
        if (b >= 0) {
            if (z2) {
                str = a(str);
            }
            if (z2 && (loggerHooker2 = c) != null) {
                loggerHooker2.e(str, str2);
            }
            if (z && (loggerHooker = d) != null) {
                loggerHooker.e(str, str2);
            }
        }
    }

    public static void d(String str, String str2) {
        c(str, str2, true);
    }

    public static void enableThreadTag(boolean z) {
        a = z;
    }

    public static int getLogLevel() {
        return b;
    }

    public static void setLogHooker(LoggerHooker loggerHooker) {
        c = loggerHooker;
    }

    public static void setLogLevel(int i) {
        b = i;
    }

    public static String throwableToString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
