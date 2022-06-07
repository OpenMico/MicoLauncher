package androidx.work;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public abstract class Logger {
    private static Logger a = null;
    private static final int b = 20;

    public abstract void debug(String str, String str2, Throwable... thArr);

    public abstract void error(String str, String str2, Throwable... thArr);

    public abstract void info(String str, String str2, Throwable... thArr);

    public abstract void verbose(String str, String str2, Throwable... thArr);

    public abstract void warning(String str, String str2, Throwable... thArr);

    public static synchronized void setLogger(Logger logger) {
        synchronized (Logger.class) {
            a = logger;
        }
    }

    public static String tagWithPrefix(@NonNull String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder(23);
        sb.append("WM-");
        int i = b;
        if (length >= i) {
            sb.append(str.substring(0, i));
        } else {
            sb.append(str);
        }
        return sb.toString();
    }

    public static synchronized Logger get() {
        Logger logger;
        synchronized (Logger.class) {
            if (a == null) {
                a = new LogcatLogger(3);
            }
            logger = a;
        }
        return logger;
    }

    public Logger(int i) {
    }

    /* loaded from: classes.dex */
    public static class LogcatLogger extends Logger {
        private int a;

        public LogcatLogger(int i) {
            super(i);
            this.a = i;
        }

        @Override // androidx.work.Logger
        public void verbose(String str, String str2, Throwable... thArr) {
            if (this.a > 2) {
                return;
            }
            if (thArr == null || thArr.length < 1) {
                Log.v(str, str2);
            } else {
                Log.v(str, str2, thArr[0]);
            }
        }

        @Override // androidx.work.Logger
        public void debug(String str, String str2, Throwable... thArr) {
            if (this.a > 3) {
                return;
            }
            if (thArr == null || thArr.length < 1) {
                Log.d(str, str2);
            } else {
                Log.d(str, str2, thArr[0]);
            }
        }

        @Override // androidx.work.Logger
        public void info(String str, String str2, Throwable... thArr) {
            if (this.a > 4) {
                return;
            }
            if (thArr == null || thArr.length < 1) {
                Log.i(str, str2);
            } else {
                Log.i(str, str2, thArr[0]);
            }
        }

        @Override // androidx.work.Logger
        public void warning(String str, String str2, Throwable... thArr) {
            if (this.a > 5) {
                return;
            }
            if (thArr == null || thArr.length < 1) {
                Log.w(str, str2);
            } else {
                Log.w(str, str2, thArr[0]);
            }
        }

        @Override // androidx.work.Logger
        public void error(String str, String str2, Throwable... thArr) {
            if (this.a > 6) {
                return;
            }
            if (thArr == null || thArr.length < 1) {
                Log.e(str, str2);
            } else {
                Log.e(str, str2, thArr[0]);
            }
        }
    }
}
