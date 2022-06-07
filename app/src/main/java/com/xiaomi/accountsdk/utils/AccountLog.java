package com.xiaomi.accountsdk.utils;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes2.dex */
public abstract class AccountLog {
    private static final AccountLog sAndroidLog = new AccountLog() { // from class: com.xiaomi.accountsdk.utils.AccountLog.1
        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logV(String str, String str2) {
            return Log.v(str, str2);
        }

        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logV(String str, String str2, Throwable th) {
            return Log.v(str, str2, th);
        }

        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logD(String str, String str2) {
            return Log.d(str, str2);
        }

        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logD(String str, String str2, Throwable th) {
            return Log.d(str, str2, th);
        }

        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logI(String str, String str2) {
            return Log.i(str, str2);
        }

        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logI(String str, String str2, Throwable th) {
            return Log.i(str, str2, th);
        }

        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logW(String str, String str2) {
            return Log.w(str, str2);
        }

        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logW(String str, String str2, Throwable th) {
            return Log.w(str, str2, th);
        }

        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logW(String str, Throwable th) {
            return Log.w(str, th);
        }

        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logE(String str, String str2) {
            return Log.e(str, str2);
        }

        @Override // com.xiaomi.accountsdk.utils.AccountLog
        public int logE(String str, String str2, Throwable th) {
            return Log.e(str, str2, th);
        }
    };
    private static AccountLog sInstance = sAndroidLog;

    protected abstract int logD(String str, String str2);

    protected abstract int logD(String str, String str2, Throwable th);

    protected abstract int logE(String str, String str2);

    protected abstract int logE(String str, String str2, Throwable th);

    protected abstract int logI(String str, String str2);

    protected abstract int logI(String str, String str2, Throwable th);

    protected abstract int logV(String str, String str2);

    protected abstract int logV(String str, String str2, Throwable th);

    protected abstract int logW(String str, String str2);

    protected abstract int logW(String str, String str2, Throwable th);

    protected abstract int logW(String str, Throwable th);

    public static AccountLog getInstance() {
        return sInstance;
    }

    public static void setInstance(AccountLog accountLog) {
        if (accountLog != null) {
            sInstance = accountLog;
            return;
        }
        throw new IllegalArgumentException("log == null");
    }

    public static int v(String str, String str2) {
        return getInstance().logV(str, str2);
    }

    public static int v(String str, String str2, Throwable th) {
        AccountLog instance = getInstance();
        return instance.logV(str, str2 + "\n" + enThrowableMsgIfHasIPAddress(th));
    }

    public static int d(String str, String str2) {
        return getInstance().logD(str, str2);
    }

    public static int d(String str, String str2, Throwable th) {
        AccountLog instance = getInstance();
        return instance.logD(str, str2 + "\n" + enThrowableMsgIfHasIPAddress(th));
    }

    public static int i(String str, String str2) {
        return getInstance().logI(str, str2);
    }

    public static int i(String str, String str2, Throwable th) {
        AccountLog instance = getInstance();
        return instance.logI(str, str2 + "\n" + enThrowableMsgIfHasIPAddress(th));
    }

    public static int w(String str, String str2) {
        return getInstance().logW(str, str2);
    }

    public static int w(String str, String str2, Throwable th) {
        AccountLog instance = getInstance();
        return instance.logW(str, str2 + "\n" + enThrowableMsgIfHasIPAddress(th));
    }

    public static int w(String str, Throwable th) {
        return getInstance().logW(str, enThrowableMsgIfHasIPAddress(th));
    }

    public static int e(String str, String str2) {
        return getInstance().logE(str, str2);
    }

    public static int e(String str, String str2, Throwable th) {
        AccountLog instance = getInstance();
        return instance.logE(str, str2 + "\n" + enThrowableMsgIfHasIPAddress(th));
    }

    private static String getStackTraceAsString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private static String enThrowableMsgIfHasIPAddress(Throwable th) {
        return th == null ? "" : IpFilterHelper.envIPAddressIfHas(getStackTraceAsString(th));
    }
}
