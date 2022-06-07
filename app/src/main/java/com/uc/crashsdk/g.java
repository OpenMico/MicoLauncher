package com.uc.crashsdk;

import android.os.Build;
import android.os.Bundle;
import com.uc.crashsdk.a.a;
import com.uc.crashsdk.export.CustomInfo;
import com.uc.crashsdk.export.VersionInfo;
import com.xiaomi.mipush.sdk.Constants;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import java.io.File;
import java.lang.reflect.Field;
import java.util.zip.ZipFile;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class g {
    static final /* synthetic */ boolean c = !g.class.desiredAssertionStatus();
    private static CustomInfo d = null;
    private static VersionInfo e = null;
    public static RuntimeException a = null;
    public static RuntimeException b = null;
    private static final Object f = new Object();
    private static String g = null;
    private static String h = null;
    private static String i = null;
    private static String j = null;
    private static final Object k = new Object();

    public static void a(CustomInfo customInfo, VersionInfo versionInfo) {
        CustomInfo customInfo2 = new CustomInfo(customInfo);
        d = customInfo2;
        c(customInfo2);
        if (!d.mZipLog) {
            a = new RuntimeException("initialize set mZipLog to false, info.mZipLog: " + customInfo.mZipLog);
        }
        if (d.mEncryptLog) {
            b = new RuntimeException("initialize set mEncryptLog to true, info.mEncryptLog: " + customInfo.mEncryptLog);
        }
        e = new VersionInfo(versionInfo);
        if (!b.I()) {
            try {
                a();
            } catch (Throwable th) {
                com.uc.crashsdk.a.g.a(th);
            }
        }
    }

    private static void c(CustomInfo customInfo) {
        if (customInfo.mZippedLogExtension == null) {
            customInfo.mZippedLogExtension = "";
        }
        if (!customInfo.mZippedLogExtension.equals(DiskFileUpload.postfix)) {
            if (customInfo.mOmitJavaCrash) {
                customInfo.mCallJavaDefaultHandler = false;
            }
            if (customInfo.mOmitNativeCrash) {
                customInfo.mCallNativeDefaultHandler = false;
            }
            long b2 = e.b();
            if (b2 >= 1) {
                customInfo.mMaxBuiltinLogFilesCount = 200;
                customInfo.mMaxCustomLogFilesCount = 100;
                customInfo.mMaxUploadBytesPerDay = 268435456L;
                customInfo.mMaxUploadBuiltinLogCountPerDay = 2000;
                customInfo.mMaxUploadCustomLogCountPerDay = 2000;
                customInfo.mMaxCustomLogCountPerTypePerDay = 100;
                customInfo.mMaxAnrLogCountPerProcess = 100;
                customInfo.mAnrTraceStrategy = 2;
                if (b2 >= 2) {
                    customInfo.mSyncUploadSetupCrashLogs = true;
                    customInfo.mSyncUploadLogs = true;
                    if (b2 >= 3) {
                        customInfo.mBackupLogs = true;
                        customInfo.mPrintStackInfos = true;
                        customInfo.mDebug = true;
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("mZippedLogExtension can not be '.tmp'!");
    }

    public static void a(CustomInfo customInfo) {
        if (!c && customInfo.mTagFilesFolderName == null) {
            throw new AssertionError();
        } else if (!c && customInfo.mCrashLogsFolderName == null) {
            throw new AssertionError();
        } else if (customInfo.mTagFilesFolderName.equals(customInfo.mCrashLogsFolderName)) {
            throw new IllegalArgumentException("mTagFilesFolderName and mCrashLogsFolderName can not be set to the same!");
        }
    }

    public static void a(VersionInfo versionInfo) {
        synchronized (f) {
            e = new VersionInfo(versionInfo);
            e.c();
            if (b.d) {
                JNIBridge.set(109, R());
                JNIBridge.set(110, S());
                JNIBridge.set(111, T());
                JNIBridge.set(112, "210105150455");
                JNIBridge.cmd(2);
            }
        }
    }

    public static void a() {
        b.v();
        b.u();
        if (d.mBackupLogs) {
            File file = new File(W());
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    public static void b() {
        JNIBridge.set(103, com.uc.crashsdk.a.g.b());
        JNIBridge.set(104, d.mTagFilesFolderName);
        JNIBridge.set(105, d.mCrashLogsFolderName);
        JNIBridge.set(106, W());
        JNIBridge.set(107, e.h());
        JNIBridge.set(108, b.a());
        JNIBridge.set(109, R());
        JNIBridge.set(110, S());
        JNIBridge.set(111, T());
        JNIBridge.set(112, "210105150455");
        JNIBridge.set(116, Build.MODEL);
        JNIBridge.set(117, Build.VERSION.RELEASE);
        JNIBridge.set(118, e.p());
        JNIBridge.set(5, d.mCallNativeDefaultHandler);
        JNIBridge.set(6, d.mDumpUserSolibBuildId);
        JNIBridge.set(7, d.mReservedNativeMemoryBytes);
        JNIBridge.set(100, d.mNativeCrashLogFileName);
        JNIBridge.set(101, d.mUnexpCrashLogFileName);
        JNIBridge.set(102, d.mAppId);
    }

    public static void c() {
        JNIBridge.set(11, N());
        JNIBridge.set(12, d.mBackupLogs);
        JNIBridge.set(13, d.mCrashRestartInterval);
        JNIBridge.set(14, d.mMaxBuiltinLogFilesCount);
        JNIBridge.set(15, d.mMaxNativeLogcatLineCount);
        JNIBridge.set(16, d.mMaxUnexpLogcatLineCount);
        JNIBridge.set(31, d.mMaxAnrLogcatLineCount);
        JNIBridge.set(18, M());
        JNIBridge.set(20, Build.VERSION.SDK_INT);
        JNIBridge.set(21, d.mOmitNativeCrash);
        JNIBridge.set(32, d.mMaxAnrLogCountPerProcess);
        JNIBridge.set(8, d.mDisableSignals);
        JNIBridge.set(9, d.mDisableBackgroundSignals);
        JNIBridge.nativeSet(3, d.mZipLog ? 1L : 0L, d.mZippedLogExtension, null);
        JNIBridge.set(4, d.mLogMaxBytesLimit);
        JNIBridge.set(119, Build.FINGERPRINT);
    }

    public static void d() {
        JNIBridge.set(23, d.mIsInternational);
        if (b.E()) {
            JNIBridge.set(34, true);
        }
        if (e.i()) {
            JNIBridge.set(1, true);
        }
        JNIBridge.set(10, d.mFdDumpMinLimit);
        JNIBridge.nativeCmd(3, d.mReservedNativeFileHandleCount, null, null);
        JNIBridge.nativeSetForeground(b.y());
        JNIBridge.set(2, b.C());
        a.e();
        a.g();
        a.i();
        a.k();
        JNIBridge.set(113, a.a);
        JNIBridge.cmd(1);
        JNIBridge.set(22, d.mThreadsDumpMinLimit);
        JNIBridge.set(122, a.a());
        JNIBridge.set(33, a.c());
        Y();
        b.H();
        b.A();
        com.uc.crashsdk.a.g.k();
    }

    public static String e() {
        return d.mAppId;
    }

    public static boolean f() {
        if (!com.uc.crashsdk.a.g.b(d.mJavaCrashLogFileName) && !com.uc.crashsdk.a.g.b(d.mNativeCrashLogFileName)) {
            return com.uc.crashsdk.a.g.b(d.mUnexpCrashLogFileName);
        }
        return true;
    }

    public static String g() {
        return d.mJavaCrashLogFileName;
    }

    public static int h() {
        return d.mCrashRestartInterval;
    }

    public static boolean i() {
        return d.mCallJavaDefaultHandler;
    }

    public static boolean j() {
        return d.mDumpHprofDataForJavaOOM;
    }

    public static boolean k() {
        return d.mRenameFileToDefaultName;
    }

    public static int l() {
        return d.mMaxBuiltinLogFilesCount;
    }

    public static int m() {
        return d.mMaxCustomLogFilesCount;
    }

    public static int n() {
        return d.mMaxJavaLogcatLineCount;
    }

    public static int o() {
        return d.mUnexpDelayMillSeconds;
    }

    public static int p() {
        return d.mUnexpSubTypes;
    }

    public static boolean q() {
        return d.mBackupLogs;
    }

    public static boolean r() {
        return d.mSyncUploadSetupCrashLogs;
    }

    public static boolean s() {
        return d.mSyncUploadLogs;
    }

    public static boolean t() {
        return d.mOmitJavaCrash;
    }

    public static boolean u() {
        return d.mAutoDeleteOldVersionStats;
    }

    public static boolean v() {
        return d.mZipLog;
    }

    public static String w() {
        return d.mZippedLogExtension;
    }

    public static boolean x() {
        return d.mEncryptLog;
    }

    public static int y() {
        return d.mLogMaxBytesLimit;
    }

    public static int z() {
        return d.mLogMaxUploadBytesLimit;
    }

    public static long A() {
        return d.mMaxUploadBytesPerDay;
    }

    public static int B() {
        return d.mMaxUploadBuiltinLogCountPerDay;
    }

    public static int C() {
        return d.mMaxUploadCustomLogCountPerDay;
    }

    public static int D() {
        return d.mMaxCustomLogCountPerTypePerDay;
    }

    public static int E() {
        return d.mInfoUpdateInterval;
    }

    public static int F() {
        return d.mInfoSaveFrequency;
    }

    public static int G() {
        return d.mReservedJavaFileHandleCount;
    }

    public static int H() {
        return d.mFdDumpMinLimit;
    }

    public static int I() {
        return d.mThreadsDumpMinLimit;
    }

    public static boolean J() {
        return d.mAutoDetectLifeCycle;
    }

    public static boolean K() {
        return d.mMonitorBattery;
    }

    public static int L() {
        return d.mAnrTraceStrategy;
    }

    public static boolean M() {
        CustomInfo customInfo = d;
        return customInfo == null || customInfo.mDebug;
    }

    public static boolean N() {
        CustomInfo customInfo = d;
        return customInfo == null || customInfo.mPrintStackInfos;
    }

    public static boolean O() {
        return d.mEnableStatReport;
    }

    public static boolean P() {
        return d.mIsInternational;
    }

    public static boolean Q() {
        return d.mAddPvForNewDay;
    }

    public static String R() {
        if (com.uc.crashsdk.a.g.a(e.mVersion)) {
            return a.a();
        }
        return a(e.mVersion);
    }

    public static String S() {
        return com.uc.crashsdk.a.g.a(e.mSubVersion) ? "release" : e.mSubVersion;
    }

    public static String T() {
        if (com.uc.crashsdk.a.g.a(e.mBuildId)) {
            return X();
        }
        return a(e.mBuildId);
    }

    private static String a(String str) {
        return (str == null || !str.contains("_")) ? str : str.replaceAll("_", Constants.ACCEPT_TIME_SEPARATOR_SERVER);
    }

    private static String X() {
        ZipFile zipFile;
        Throwable th;
        Throwable th2;
        String str = g;
        if (str != null) {
            return str;
        }
        try {
            try {
            } catch (Throwable th3) {
                th = th3;
            }
            try {
                zipFile = new ZipFile(com.uc.crashsdk.a.g.c());
                try {
                    g = Long.toHexString(zipFile.getEntry("classes.dex").getCrc());
                    a.a("crashsdk", "version unique build id: " + g);
                    zipFile.close();
                } catch (Throwable th4) {
                    th2 = th4;
                    g = "";
                    com.uc.crashsdk.a.g.a(th2);
                    if (zipFile != null) {
                        zipFile.close();
                    }
                    return g;
                }
            } catch (Throwable th5) {
                th = th5;
                zipFile = null;
                if (zipFile != null) {
                    try {
                        zipFile.close();
                    } catch (Throwable unused) {
                    }
                }
                throw th;
            }
        } catch (Throwable unused2) {
        }
        return g;
    }

    public static String U() {
        if (h == null) {
            h = com.uc.crashsdk.a.g.b() + File.separatorChar + d.mTagFilesFolderName + File.separatorChar;
        }
        return h;
    }

    public static String V() {
        if (i == null) {
            i = com.uc.crashsdk.a.g.b() + File.separatorChar + d.mCrashLogsFolderName + File.separatorChar;
        }
        return i;
    }

    public static String W() {
        if (j == null) {
            if (!com.uc.crashsdk.a.g.a(d.mLogsBackupPathName)) {
                String trim = d.mLogsBackupPathName.trim();
                if (!trim.endsWith(File.separator)) {
                    trim = trim + File.separator;
                }
                j = trim;
            } else {
                j = (com.uc.crashsdk.a.g.b() + File.separatorChar + "msdb" + File.separatorChar) + File.separatorChar + d.mCrashLogsFolderName + File.separatorChar;
            }
        }
        return j;
    }

    public static CustomInfo a(CustomInfo customInfo, Bundle bundle) {
        if (customInfo == null) {
            CustomInfo customInfo2 = d;
            if (customInfo2 == null) {
                customInfo = new CustomInfo();
            } else {
                customInfo = new CustomInfo(customInfo2);
            }
        }
        Field[] fields = customInfo.getClass().getFields();
        for (String str : bundle.keySet()) {
            for (Field field : fields) {
                if (field.getName().equals(str)) {
                    Object obj = bundle.get(str);
                    try {
                        field.set(customInfo, obj);
                    } catch (Exception e2) {
                        com.uc.crashsdk.a.g.a(e2);
                        StringBuilder sb = new StringBuilder("Field ");
                        sb.append(str);
                        sb.append(" must be a ");
                        sb.append(field.getType().getName());
                        sb.append(", but give a ");
                        sb.append(obj != null ? obj.getClass().getName() : "(null)");
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
            }
        }
        return customInfo;
    }

    public static VersionInfo a(Bundle bundle) {
        VersionInfo versionInfo;
        VersionInfo versionInfo2 = e;
        if (versionInfo2 == null) {
            versionInfo = new VersionInfo();
        } else {
            versionInfo = new VersionInfo(versionInfo2);
        }
        String string = bundle.getString("mVersion");
        if (!com.uc.crashsdk.a.g.a(string)) {
            versionInfo.mVersion = string;
        }
        String string2 = bundle.getString("mSubVersion");
        if (!com.uc.crashsdk.a.g.a(string2)) {
            versionInfo.mSubVersion = string2;
        }
        String string3 = bundle.getString("mBuildId");
        if (!com.uc.crashsdk.a.g.a(string3)) {
            versionInfo.mBuildId = string3;
        }
        String string4 = bundle.getString("crver");
        if (!com.uc.crashsdk.a.g.a(string4)) {
            a.b = string4;
            Y();
        }
        return versionInfo;
    }

    private static void Y() {
        if (b.d) {
            JNIBridge.nativeSet(24, 1L, a.b, null);
        }
    }

    private static boolean a(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equals(str2);
    }

    public static int b(CustomInfo customInfo) {
        int i2;
        synchronized (k) {
            i2 = 0;
            if (customInfo != null) {
                c(customInfo);
                if (d == null) {
                    d = new CustomInfo();
                }
                CustomInfo customInfo2 = d;
                boolean z = true;
                if (!a(customInfo.mAppId, customInfo2.mAppId)) {
                    customInfo2.mAppId = customInfo.mAppId;
                    if (b.d) {
                        JNIBridge.set(102, customInfo2.mAppId);
                    }
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                int i3 = i2;
                if (!a(customInfo.mJavaCrashLogFileName, customInfo2.mJavaCrashLogFileName)) {
                    customInfo2.mJavaCrashLogFileName = customInfo.mJavaCrashLogFileName;
                    i2++;
                }
                if (!a(customInfo.mNativeCrashLogFileName, customInfo2.mNativeCrashLogFileName)) {
                    customInfo2.mNativeCrashLogFileName = customInfo.mNativeCrashLogFileName;
                    if (b.d) {
                        JNIBridge.set(100, customInfo2.mNativeCrashLogFileName);
                    }
                    i2++;
                    i3 = 1;
                }
                if (!a(customInfo.mUnexpCrashLogFileName, customInfo2.mUnexpCrashLogFileName)) {
                    customInfo2.mUnexpCrashLogFileName = customInfo.mUnexpCrashLogFileName;
                    if (b.d) {
                        JNIBridge.set(101, customInfo2.mUnexpCrashLogFileName);
                    }
                    i2++;
                    i3 = 1;
                }
                if (i3 != 0) {
                    e.c();
                    if (b.d) {
                        JNIBridge.cmd(2);
                    }
                }
                if (customInfo2.mPrintStackInfos != customInfo.mPrintStackInfos) {
                    customInfo2.mPrintStackInfos = customInfo.mPrintStackInfos;
                    if (b.d) {
                        JNIBridge.set(11, customInfo2.mPrintStackInfos);
                    }
                    i2++;
                }
                if (customInfo2.mDebug != customInfo.mDebug) {
                    customInfo2.mDebug = customInfo.mDebug;
                    if (b.d) {
                        JNIBridge.set(18, customInfo2.mDebug);
                    }
                    i2++;
                }
                if (customInfo2.mBackupLogs != customInfo.mBackupLogs) {
                    customInfo2.mBackupLogs = customInfo.mBackupLogs;
                    if (b.d) {
                        JNIBridge.set(12, customInfo2.mBackupLogs);
                    }
                    i2++;
                }
                if (customInfo2.mOmitNativeCrash != customInfo.mOmitNativeCrash) {
                    customInfo2.mOmitNativeCrash = customInfo.mOmitNativeCrash;
                    if (b.d) {
                        JNIBridge.set(21, customInfo2.mOmitNativeCrash);
                    }
                    i2++;
                }
                if (customInfo2.mCrashRestartInterval != customInfo.mCrashRestartInterval) {
                    customInfo2.mCrashRestartInterval = customInfo.mCrashRestartInterval;
                    if (b.d) {
                        JNIBridge.set(13, customInfo2.mCrashRestartInterval);
                    }
                    if (customInfo2.mCrashRestartInterval >= 0) {
                        b.J();
                    }
                    i2++;
                }
                if (customInfo2.mMaxBuiltinLogFilesCount != customInfo.mMaxBuiltinLogFilesCount) {
                    customInfo2.mMaxBuiltinLogFilesCount = customInfo.mMaxBuiltinLogFilesCount;
                    if (b.d) {
                        JNIBridge.set(14, customInfo2.mMaxBuiltinLogFilesCount);
                    }
                    i2++;
                }
                if (customInfo2.mMaxNativeLogcatLineCount != customInfo.mMaxNativeLogcatLineCount) {
                    customInfo2.mMaxNativeLogcatLineCount = customInfo.mMaxNativeLogcatLineCount;
                    if (b.d) {
                        JNIBridge.set(15, customInfo2.mMaxNativeLogcatLineCount);
                    }
                    i2++;
                }
                if (customInfo2.mMaxJavaLogcatLineCount != customInfo.mMaxJavaLogcatLineCount) {
                    customInfo2.mMaxJavaLogcatLineCount = customInfo.mMaxJavaLogcatLineCount;
                    i2++;
                }
                if (customInfo2.mMaxUnexpLogcatLineCount != customInfo.mMaxUnexpLogcatLineCount) {
                    customInfo2.mMaxUnexpLogcatLineCount = customInfo.mMaxUnexpLogcatLineCount;
                    if (b.d) {
                        JNIBridge.set(16, customInfo2.mMaxUnexpLogcatLineCount);
                    }
                    i2++;
                }
                if (customInfo2.mMaxAnrLogcatLineCount != customInfo.mMaxAnrLogcatLineCount) {
                    customInfo2.mMaxAnrLogcatLineCount = customInfo.mMaxAnrLogcatLineCount;
                    if (b.d) {
                        JNIBridge.set(31, customInfo2.mMaxAnrLogcatLineCount);
                    }
                    i2++;
                }
                if (customInfo2.mZipLog != customInfo.mZipLog) {
                    customInfo2.mZipLog = customInfo.mZipLog;
                    if (!customInfo2.mZipLog) {
                        a.d("DEBUG", "updateCustomInfoImpl set mZipLog to false");
                        a = new RuntimeException("updateCustomInfoImpl set mZipLog to false");
                    }
                    i2++;
                    z = true;
                } else {
                    z = false;
                }
                if (!a(customInfo.mZippedLogExtension, customInfo2.mZippedLogExtension)) {
                    customInfo2.mZippedLogExtension = customInfo.mZippedLogExtension;
                    i2++;
                }
                if (z && b.d) {
                    JNIBridge.nativeSet(3, customInfo2.mZipLog ? 1L : 0L, customInfo2.mZippedLogExtension, null);
                }
                if (customInfo2.mLogMaxBytesLimit != customInfo.mLogMaxBytesLimit) {
                    customInfo2.mLogMaxBytesLimit = customInfo.mLogMaxBytesLimit;
                    if (b.d) {
                        JNIBridge.set(4, customInfo2.mLogMaxBytesLimit);
                    }
                    i2++;
                }
                if (customInfo2.mEncryptLog != customInfo.mEncryptLog) {
                    customInfo2.mEncryptLog = customInfo.mEncryptLog;
                    if (customInfo2.mEncryptLog) {
                        a.d("DEBUG", "updateCustomInfoImpl set mEncryptLog to true");
                        b = new RuntimeException("updateCustomInfoImpl set mEncryptLog to true");
                    }
                    i2++;
                }
                if (customInfo2.mSyncUploadSetupCrashLogs != customInfo.mSyncUploadSetupCrashLogs) {
                    customInfo2.mSyncUploadSetupCrashLogs = customInfo.mSyncUploadSetupCrashLogs;
                    i2++;
                }
                if (customInfo2.mSyncUploadLogs != customInfo.mSyncUploadLogs) {
                    customInfo2.mSyncUploadLogs = customInfo.mSyncUploadLogs;
                    i2++;
                }
                if (customInfo2.mMaxCustomLogFilesCount != customInfo.mMaxCustomLogFilesCount) {
                    customInfo2.mMaxCustomLogFilesCount = customInfo.mMaxCustomLogFilesCount;
                    i2++;
                }
                if (customInfo2.mOmitJavaCrash != customInfo.mOmitJavaCrash) {
                    customInfo2.mOmitJavaCrash = customInfo.mOmitJavaCrash;
                    i2++;
                }
                if (customInfo2.mLogMaxUploadBytesLimit != customInfo.mLogMaxUploadBytesLimit) {
                    customInfo2.mLogMaxUploadBytesLimit = customInfo.mLogMaxUploadBytesLimit;
                    i2++;
                }
                if (customInfo2.mMaxUploadBytesPerDay != customInfo.mMaxUploadBytesPerDay) {
                    customInfo2.mMaxUploadBytesPerDay = customInfo.mMaxUploadBytesPerDay;
                    i2++;
                }
                if (customInfo2.mMaxUploadBuiltinLogCountPerDay != customInfo.mMaxUploadBuiltinLogCountPerDay) {
                    customInfo2.mMaxUploadBuiltinLogCountPerDay = customInfo.mMaxUploadBuiltinLogCountPerDay;
                    i2++;
                }
                if (customInfo2.mMaxUploadCustomLogCountPerDay != customInfo.mMaxUploadCustomLogCountPerDay) {
                    customInfo2.mMaxUploadCustomLogCountPerDay = customInfo.mMaxUploadCustomLogCountPerDay;
                    i2++;
                }
                if (customInfo2.mMaxCustomLogCountPerTypePerDay != customInfo.mMaxCustomLogCountPerTypePerDay) {
                    customInfo2.mMaxCustomLogCountPerTypePerDay = customInfo.mMaxCustomLogCountPerTypePerDay;
                    i2++;
                }
                if (customInfo2.mMaxAnrLogCountPerProcess != customInfo.mMaxAnrLogCountPerProcess) {
                    customInfo2.mMaxAnrLogCountPerProcess = customInfo.mMaxAnrLogCountPerProcess;
                    if (b.d) {
                        JNIBridge.set(32, d.mMaxAnrLogCountPerProcess);
                    }
                    i2++;
                }
                if (customInfo2.mCallJavaDefaultHandler != customInfo.mCallJavaDefaultHandler) {
                    customInfo2.mCallJavaDefaultHandler = customInfo.mCallJavaDefaultHandler;
                    i2++;
                }
                if (customInfo2.mCallNativeDefaultHandler != customInfo.mCallNativeDefaultHandler) {
                    customInfo2.mCallNativeDefaultHandler = customInfo.mCallNativeDefaultHandler;
                    i2++;
                    if (b.d) {
                        JNIBridge.set(5, d.mCallNativeDefaultHandler);
                    }
                }
                if (customInfo2.mDumpUserSolibBuildId != customInfo.mDumpUserSolibBuildId) {
                    customInfo2.mDumpUserSolibBuildId = customInfo.mDumpUserSolibBuildId;
                    i2++;
                    if (b.d) {
                        JNIBridge.set(6, d.mDumpUserSolibBuildId);
                    }
                }
                if (customInfo2.mDumpHprofDataForJavaOOM != customInfo.mDumpHprofDataForJavaOOM) {
                    customInfo2.mDumpHprofDataForJavaOOM = customInfo.mDumpHprofDataForJavaOOM;
                    i2++;
                }
                if (customInfo2.mRenameFileToDefaultName != customInfo.mRenameFileToDefaultName) {
                    customInfo2.mRenameFileToDefaultName = customInfo.mRenameFileToDefaultName;
                    i2++;
                }
                if (customInfo2.mAutoDeleteOldVersionStats != customInfo.mAutoDeleteOldVersionStats) {
                    customInfo2.mAutoDeleteOldVersionStats = customInfo.mAutoDeleteOldVersionStats;
                    i2++;
                }
                if (customInfo2.mFdDumpMinLimit != customInfo.mFdDumpMinLimit) {
                    customInfo2.mFdDumpMinLimit = customInfo.mFdDumpMinLimit;
                    if (b.d) {
                        JNIBridge.set(10, customInfo2.mFdDumpMinLimit);
                    }
                    i2++;
                }
                if (customInfo2.mThreadsDumpMinLimit != customInfo.mThreadsDumpMinLimit) {
                    customInfo2.mThreadsDumpMinLimit = customInfo.mThreadsDumpMinLimit;
                    if (b.d) {
                        JNIBridge.set(22, customInfo2.mThreadsDumpMinLimit);
                    }
                    i2++;
                }
                if (customInfo2.mInfoUpdateInterval != customInfo.mInfoUpdateInterval) {
                    if (customInfo2.mInfoUpdateInterval <= 0 && customInfo.mInfoUpdateInterval > 0) {
                        a.a(false);
                    }
                    customInfo2.mInfoUpdateInterval = customInfo.mInfoUpdateInterval;
                    i2++;
                }
                if (customInfo2.mInfoSaveFrequency != customInfo.mInfoSaveFrequency) {
                    customInfo2.mInfoSaveFrequency = customInfo.mInfoSaveFrequency;
                    i2++;
                }
                if (customInfo2.mDisableBackgroundSignals != customInfo.mDisableBackgroundSignals) {
                    customInfo2.mDisableBackgroundSignals = customInfo.mDisableBackgroundSignals;
                    if (b.d) {
                        JNIBridge.set(9, customInfo2.mDisableBackgroundSignals);
                    }
                    i2++;
                }
                if (customInfo2.mEnableStatReport != customInfo.mEnableStatReport) {
                    customInfo2.mEnableStatReport = customInfo.mEnableStatReport;
                    if (customInfo2.mEnableStatReport) {
                        e.A();
                    }
                    i2++;
                }
                if (customInfo2.mIsInternational != customInfo.mIsInternational) {
                    customInfo2.mIsInternational = customInfo.mIsInternational;
                    if (b.d) {
                        JNIBridge.set(23, customInfo2.mIsInternational);
                    }
                    i2++;
                }
                if (customInfo2.mAutoDetectLifeCycle != customInfo.mAutoDetectLifeCycle) {
                    customInfo2.mAutoDetectLifeCycle = customInfo.mAutoDetectLifeCycle;
                    if (customInfo2.mAutoDetectLifeCycle) {
                        b.z();
                    }
                    i2++;
                }
                if (customInfo2.mMonitorBattery != customInfo.mMonitorBattery) {
                    customInfo2.mMonitorBattery = customInfo.mMonitorBattery;
                    e.c(b.y());
                    i2++;
                }
                if (customInfo2.mUnexpSubTypes != customInfo.mUnexpSubTypes) {
                    customInfo2.mUnexpSubTypes = customInfo.mUnexpSubTypes;
                    i2++;
                }
            }
        }
        return i2;
    }
}
