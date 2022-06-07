package com.blankj.utilcode.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.Utils;
import com.xiaomi.idm.api.IDMServer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class AppUtils {
    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void registerAppStatusChangedListener(@NonNull Utils.OnAppStatusChangedListener onAppStatusChangedListener) {
        if (onAppStatusChangedListener != null) {
            b.a(onAppStatusChangedListener);
            return;
        }
        throw new NullPointerException("Argument 'listener' of type Utils.OnAppStatusChangedListener (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void unregisterAppStatusChangedListener(@NonNull Utils.OnAppStatusChangedListener onAppStatusChangedListener) {
        if (onAppStatusChangedListener != null) {
            b.b(onAppStatusChangedListener);
            return;
        }
        throw new NullPointerException("Argument 'listener' of type Utils.OnAppStatusChangedListener (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void installApp(String str) {
        installApp(b.f(str));
    }

    public static void installApp(File file) {
        Intent h = b.h(file);
        if (h != null) {
            Utils.getApp().startActivity(h);
        }
    }

    public static void installApp(Uri uri) {
        Intent a = b.a(uri);
        if (a != null) {
            Utils.getApp().startActivity(a);
        }
    }

    public static void uninstallApp(String str) {
        if (!b.o(str)) {
            Utils.getApp().startActivity(b.j(str));
        }
    }

    public static boolean isAppInstalled(String str) {
        if (b.o(str)) {
            return false;
        }
        try {
            return Utils.getApp().getPackageManager().getApplicationInfo(str, 0).enabled;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isAppRoot() {
        return b.b("echo root", true).result == 0;
    }

    public static boolean isAppDebug() {
        return isAppDebug(Utils.getApp().getPackageName());
    }

    public static boolean isAppDebug(String str) {
        if (b.o(str)) {
            return false;
        }
        try {
            return (Utils.getApp().getPackageManager().getApplicationInfo(str, 0).flags & 2) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isAppSystem() {
        return isAppSystem(Utils.getApp().getPackageName());
    }

    public static boolean isAppSystem(String str) {
        if (b.o(str)) {
            return false;
        }
        try {
            return (Utils.getApp().getPackageManager().getApplicationInfo(str, 0).flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isAppForeground() {
        return b.e();
    }

    public static boolean isAppForeground(@NonNull String str) {
        if (str != null) {
            return !b.o(str) && str.equals(b.o());
        }
        throw new NullPointerException("Argument 'pkgName' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isAppRunning(String str) {
        ActivityManager activityManager;
        if (!b.o(str) && (activityManager = (ActivityManager) Utils.getApp().getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)) != null) {
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
            if (runningTasks != null && runningTasks.size() > 0) {
                for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                    if (runningTaskInfo.baseActivity != null && str.equals(runningTaskInfo.baseActivity.getPackageName())) {
                        return true;
                    }
                }
            }
            List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(Integer.MAX_VALUE);
            if (runningServices != null && runningServices.size() > 0) {
                for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
                    if (str.equals(runningServiceInfo.service.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void launchApp(String str) {
        if (!b.o(str)) {
            Intent i = b.i(str);
            if (i == null) {
                Log.e("AppUtils", "Didn't exist launcher activity.");
            } else {
                Utils.getApp().startActivity(i);
            }
        }
    }

    public static void relaunchApp() {
        relaunchApp(false);
    }

    public static void relaunchApp(boolean z) {
        Intent i = b.i(Utils.getApp().getPackageName());
        if (i == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        i.addFlags(335577088);
        Utils.getApp().startActivity(i);
        if (z) {
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }

    public static void launchAppDetailsSettings() {
        launchAppDetailsSettings(Utils.getApp().getPackageName());
    }

    public static void launchAppDetailsSettings(String str) {
        if (!b.o(str)) {
            Intent a = b.a(str, true);
            if (b.a(a)) {
                Utils.getApp().startActivity(a);
            }
        }
    }

    public static void launchAppDetailsSettings(Activity activity, int i) {
        launchAppDetailsSettings(activity, i, Utils.getApp().getPackageName());
    }

    public static void launchAppDetailsSettings(Activity activity, int i, String str) {
        if (activity != null && !b.o(str)) {
            Intent a = b.a(str, false);
            if (b.a(a)) {
                activity.startActivityForResult(a, i);
            }
        }
    }

    public static void exitApp() {
        b.g();
        System.exit(0);
    }

    @Nullable
    public static Drawable getAppIcon() {
        return getAppIcon(Utils.getApp().getPackageName());
    }

    @Nullable
    public static Drawable getAppIcon(String str) {
        if (b.o(str)) {
            return null;
        }
        try {
            PackageManager packageManager = Utils.getApp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getAppIconId() {
        return getAppIconId(Utils.getApp().getPackageName());
    }

    public static int getAppIconId(String str) {
        if (b.o(str)) {
            return 0;
        }
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return 0;
            }
            return packageInfo.applicationInfo.icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @NonNull
    public static String getAppPackageName() {
        String packageName = Utils.getApp().getPackageName();
        if (packageName != null) {
            return packageName;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppPackageName() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static String getAppName() {
        String appName = getAppName(Utils.getApp().getPackageName());
        if (appName != null) {
            return appName;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppName() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static String getAppName(String str) {
        if (b.o(str)) {
            return "";
        }
        try {
            PackageManager packageManager = Utils.getApp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            String charSequence = packageInfo == null ? "" : packageInfo.applicationInfo.loadLabel(packageManager).toString();
            if (charSequence != null) {
                return charSequence;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppName() marked by @androidx.annotation.NonNull");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    @NonNull
    public static String getAppPath() {
        String appPath = getAppPath(Utils.getApp().getPackageName());
        if (appPath != null) {
            return appPath;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppPath() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static String getAppPath(String str) {
        if (b.o(str)) {
            return "";
        }
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            String str2 = packageInfo == null ? "" : packageInfo.applicationInfo.sourceDir;
            if (str2 != null) {
                return str2;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppPath() marked by @androidx.annotation.NonNull");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    @NonNull
    public static String getAppVersionName() {
        String appVersionName = getAppVersionName(Utils.getApp().getPackageName());
        if (appVersionName != null) {
            return appVersionName;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppVersionName() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static String getAppVersionName(String str) {
        if (b.o(str)) {
            return "";
        }
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            String str2 = packageInfo == null ? "" : packageInfo.versionName;
            if (str2 != null) {
                return str2;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppVersionName() marked by @androidx.annotation.NonNull");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getAppVersionCode() {
        return getAppVersionCode(Utils.getApp().getPackageName());
    }

    public static int getAppVersionCode(String str) {
        if (b.o(str)) {
            return -1;
        }
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return -1;
            }
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Nullable
    public static Signature[] getAppSignatures() {
        return getAppSignatures(Utils.getApp().getPackageName());
    }

    @Nullable
    public static Signature[] getAppSignatures(String str) {
        if (b.o(str)) {
            return null;
        }
        try {
            PackageManager packageManager = Utils.getApp().getPackageManager();
            if (Build.VERSION.SDK_INT >= 28) {
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 134217728);
                if (packageInfo == null) {
                    return null;
                }
                SigningInfo signingInfo = packageInfo.signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    return signingInfo.getApkContentsSigners();
                }
                return signingInfo.getSigningCertificateHistory();
            }
            PackageInfo packageInfo2 = packageManager.getPackageInfo(str, 64);
            if (packageInfo2 == null) {
                return null;
            }
            return packageInfo2.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Signature[] getAppSignatures(File file) {
        if (file == null) {
            return null;
        }
        PackageManager packageManager = Utils.getApp().getPackageManager();
        if (Build.VERSION.SDK_INT >= 28) {
            PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(file.getAbsolutePath(), 134217728);
            if (packageArchiveInfo == null) {
                return null;
            }
            SigningInfo signingInfo = packageArchiveInfo.signingInfo;
            if (signingInfo.hasMultipleSigners()) {
                return signingInfo.getApkContentsSigners();
            }
            return signingInfo.getSigningCertificateHistory();
        }
        PackageInfo packageArchiveInfo2 = packageManager.getPackageArchiveInfo(file.getAbsolutePath(), 64);
        if (packageArchiveInfo2 == null) {
            return null;
        }
        return packageArchiveInfo2.signatures;
    }

    @NonNull
    public static List<String> getAppSignaturesSHA1() {
        List<String> appSignaturesSHA1 = getAppSignaturesSHA1(Utils.getApp().getPackageName());
        if (appSignaturesSHA1 != null) {
            return appSignaturesSHA1;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesSHA1() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static List<String> getAppSignaturesSHA1(String str) {
        List<String> a = a(str, "SHA1");
        if (a != null) {
            return a;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesSHA1() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static List<String> getAppSignaturesSHA256() {
        List<String> appSignaturesSHA256 = getAppSignaturesSHA256(Utils.getApp().getPackageName());
        if (appSignaturesSHA256 != null) {
            return appSignaturesSHA256;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesSHA256() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static List<String> getAppSignaturesSHA256(String str) {
        List<String> a = a(str, "SHA256");
        if (a != null) {
            return a;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesSHA256() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static List<String> getAppSignaturesMD5() {
        List<String> appSignaturesMD5 = getAppSignaturesMD5(Utils.getApp().getPackageName());
        if (appSignaturesMD5 != null) {
            return appSignaturesMD5;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesMD5() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static List<String> getAppSignaturesMD5(String str) {
        List<String> a = a(str, "MD5");
        if (a != null) {
            return a;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesMD5() marked by @androidx.annotation.NonNull");
    }

    public static int getAppUid() {
        return getAppUid(Utils.getApp().getPackageName());
    }

    public static int getAppUid(String str) {
        try {
            return Utils.getApp().getPackageManager().getApplicationInfo(str, 0).uid;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static List<String> a(String str, String str2) {
        Signature[] appSignatures;
        ArrayList arrayList = new ArrayList();
        if (b.o(str) || (appSignatures = getAppSignatures(str)) == null || appSignatures.length <= 0) {
            return arrayList;
        }
        for (Signature signature : appSignatures) {
            arrayList.add(b.a(b.a(signature.toByteArray(), str2)).replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0"));
        }
        return arrayList;
    }

    @Nullable
    public static AppInfo getAppInfo() {
        return getAppInfo(Utils.getApp().getPackageName());
    }

    @Nullable
    public static AppInfo getAppInfo(String str) {
        try {
            PackageManager packageManager = Utils.getApp().getPackageManager();
            if (packageManager == null) {
                return null;
            }
            return a(packageManager, packageManager.getPackageInfo(str, 0));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @NonNull
    public static List<AppInfo> getAppsInfo() {
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = Utils.getApp().getPackageManager();
        if (packageManager == null) {
            return arrayList;
        }
        for (PackageInfo packageInfo : packageManager.getInstalledPackages(0)) {
            AppInfo a = a(packageManager, packageInfo);
            if (a != null) {
                arrayList.add(a);
            }
        }
        return arrayList;
    }

    @Nullable
    public static AppInfo getApkInfo(File file) {
        if (file == null || !file.isFile() || !file.exists()) {
            return null;
        }
        return getApkInfo(file.getAbsolutePath());
    }

    @Nullable
    public static AppInfo getApkInfo(String str) {
        PackageManager packageManager;
        PackageInfo packageArchiveInfo;
        if (b.o(str) || (packageManager = Utils.getApp().getPackageManager()) == null || (packageArchiveInfo = packageManager.getPackageArchiveInfo(str, 0)) == null) {
            return null;
        }
        ApplicationInfo applicationInfo = packageArchiveInfo.applicationInfo;
        applicationInfo.sourceDir = str;
        applicationInfo.publicSourceDir = str;
        return a(packageManager, packageArchiveInfo);
    }

    private static AppInfo a(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageInfo == null) {
            return null;
        }
        String str = packageInfo.versionName;
        int i = packageInfo.versionCode;
        String str2 = packageInfo.packageName;
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        if (applicationInfo == null) {
            return new AppInfo(str2, "", null, "", str, i, false);
        }
        return new AppInfo(str2, applicationInfo.loadLabel(packageManager).toString(), applicationInfo.loadIcon(packageManager), applicationInfo.sourceDir, str, i, (applicationInfo.flags & 1) != 0);
    }

    /* loaded from: classes.dex */
    public static class AppInfo {
        private String a;
        private String b;
        private Drawable c;
        private String d;
        private String e;
        private int f;
        private boolean g;

        public Drawable getIcon() {
            return this.c;
        }

        public void setIcon(Drawable drawable) {
            this.c = drawable;
        }

        public boolean isSystem() {
            return this.g;
        }

        public void setSystem(boolean z) {
            this.g = z;
        }

        public String getPackageName() {
            return this.a;
        }

        public void setPackageName(String str) {
            this.a = str;
        }

        public String getName() {
            return this.b;
        }

        public void setName(String str) {
            this.b = str;
        }

        public String getPackagePath() {
            return this.d;
        }

        public void setPackagePath(String str) {
            this.d = str;
        }

        public int getVersionCode() {
            return this.f;
        }

        public void setVersionCode(int i) {
            this.f = i;
        }

        public String getVersionName() {
            return this.e;
        }

        public void setVersionName(String str) {
            this.e = str;
        }

        public AppInfo(String str, String str2, Drawable drawable, String str3, String str4, int i, boolean z) {
            setName(str2);
            setIcon(drawable);
            setPackageName(str);
            setPackagePath(str3);
            setVersionName(str4);
            setVersionCode(i);
            setSystem(z);
        }

        @NonNull
        public String toString() {
            String str = "{\n    pkg name: " + getPackageName() + "\n    app icon: " + getIcon() + "\n    app name: " + getName() + "\n    app path: " + getPackagePath() + "\n    app v name: " + getVersionName() + "\n    app v code: " + getVersionCode() + "\n    is system: " + isSystem() + "\n}";
            if (str != null) {
                return str;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.AppInfo.toString() marked by @androidx.annotation.NonNull");
        }
    }
}
