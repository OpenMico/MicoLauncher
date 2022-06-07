package com.blankj.utilcode.util;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: UtilsBridge.java */
/* loaded from: classes.dex */
public class b {
    public static void a(Application application) {
        a.a.a(application);
    }

    public static void b(Application application) {
        a.a.b(application);
    }

    public static void a() {
        a(AdaptScreenUtils.a());
    }

    public static Activity b() {
        return a.a.a();
    }

    public static void a(Utils.OnAppStatusChangedListener onAppStatusChangedListener) {
        a.a.a(onAppStatusChangedListener);
    }

    public static void b(Utils.OnAppStatusChangedListener onAppStatusChangedListener) {
        a.a.b(onAppStatusChangedListener);
    }

    public static void a(Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        a.a.a(activityLifecycleCallbacks);
    }

    public static void b(Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        a.a.b(activityLifecycleCallbacks);
    }

    public static void a(Activity activity, Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        a.a.a(activity, activityLifecycleCallbacks);
    }

    public static void a(Activity activity) {
        a.a.a(activity);
    }

    public static void b(Activity activity, Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        a.a.b(activity, activityLifecycleCallbacks);
    }

    public static List<Activity> c() {
        return a.a.b();
    }

    public static Application d() {
        return a.a.d();
    }

    public static boolean e() {
        return a.a.c();
    }

    public static boolean b(Activity activity) {
        return ActivityUtils.isActivityAlive(activity);
    }

    public static String a(String str) {
        return ActivityUtils.getLauncherActivity(str);
    }

    public static Activity a(Context context) {
        return ActivityUtils.getActivityByContext(context);
    }

    public static void f() {
        ActivityUtils.startHomeActivity();
    }

    public static void g() {
        ActivityUtils.finishAllActivities();
    }

    public static boolean b(@NonNull String str) {
        if (str != null) {
            return AppUtils.isAppRunning(str);
        }
        throw new NullPointerException("Argument 'pkgName' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean c(String str) {
        return AppUtils.isAppInstalled(str);
    }

    public static boolean h() {
        return AppUtils.isAppDebug();
    }

    public static void i() {
        AppUtils.relaunchApp();
    }

    public static int j() {
        return BarUtils.getStatusBarHeight();
    }

    public static int k() {
        return BarUtils.getNavBarHeight();
    }

    public static String a(byte[] bArr) {
        return ConvertUtils.bytes2HexString(bArr);
    }

    public static byte[] d(String str) {
        return ConvertUtils.hexString2Bytes(str);
    }

    public static byte[] e(String str) {
        return ConvertUtils.string2Bytes(str);
    }

    public static String b(byte[] bArr) {
        return ConvertUtils.bytes2String(bArr);
    }

    public static byte[] a(JSONObject jSONObject) {
        return ConvertUtils.jsonObject2Bytes(jSONObject);
    }

    public static JSONObject c(byte[] bArr) {
        return ConvertUtils.bytes2JSONObject(bArr);
    }

    public static byte[] a(JSONArray jSONArray) {
        return ConvertUtils.jsonArray2Bytes(jSONArray);
    }

    public static JSONArray d(byte[] bArr) {
        return ConvertUtils.bytes2JSONArray(bArr);
    }

    public static byte[] a(Parcelable parcelable) {
        return ConvertUtils.parcelable2Bytes(parcelable);
    }

    public static <T> T a(byte[] bArr, Parcelable.Creator<T> creator) {
        return (T) ConvertUtils.bytes2Parcelable(bArr, creator);
    }

    public static byte[] a(Serializable serializable) {
        return ConvertUtils.serializable2Bytes(serializable);
    }

    public static Object e(byte[] bArr) {
        return ConvertUtils.bytes2Object(bArr);
    }

    public static String a(long j) {
        return ConvertUtils.byte2FitMemorySize(j);
    }

    public static byte[] a(InputStream inputStream) {
        return ConvertUtils.inputStream2Bytes(inputStream);
    }

    public static List<String> a(InputStream inputStream, String str) {
        return ConvertUtils.inputStream2Lines(inputStream, str);
    }

    public static boolean a(@NonNull View view, long j) {
        if (view != null) {
            return DebouncingUtils.isValid(view, j);
        }
        throw new NullPointerException("Argument 'view' of type View (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static byte[] f(byte[] bArr) {
        return EncodeUtils.base64Encode(bArr);
    }

    public static byte[] g(byte[] bArr) {
        return EncodeUtils.base64Decode(bArr);
    }

    public static byte[] a(byte[] bArr, String str) {
        return EncryptUtils.a(bArr, str);
    }

    public static boolean a(File file, byte[] bArr) {
        return FileIOUtils.writeFileFromBytesByChannel(file, bArr, true);
    }

    public static byte[] a(File file) {
        return FileIOUtils.readFile2BytesByChannel(file);
    }

    public static boolean a(String str, String str2, boolean z) {
        return FileIOUtils.writeFileFromString(str, str2, z);
    }

    public static boolean a(String str, InputStream inputStream) {
        return FileIOUtils.writeFileFromIS(str, inputStream);
    }

    public static boolean b(File file) {
        return FileUtils.isFileExists(file);
    }

    public static File f(String str) {
        return FileUtils.getFileByPath(str);
    }

    public static boolean c(File file) {
        return FileUtils.deleteAllInDir(file);
    }

    public static boolean d(File file) {
        return FileUtils.createOrExistsFile(file);
    }

    public static boolean e(File file) {
        return FileUtils.createOrExistsDir(file);
    }

    public static boolean f(File file) {
        return FileUtils.createFileByDeleteOldFile(file);
    }

    public static long g(String str) {
        return FileUtils.getFsTotalSize(str);
    }

    public static long h(String str) {
        return FileUtils.getFsAvailableSize(str);
    }

    public static void g(File file) {
        FileUtils.notifySystemToScan(file);
    }

    public static String a(Object obj) {
        return GsonUtils.toJson(obj);
    }

    public static <T> T a(String str, Type type) {
        return (T) GsonUtils.fromJson(str, type);
    }

    public static Gson l() {
        return GsonUtils.a();
    }

    public static byte[] a(Bitmap bitmap) {
        return ImageUtils.bitmap2Bytes(bitmap);
    }

    public static byte[] a(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i) {
        return ImageUtils.bitmap2Bytes(bitmap, compressFormat, i);
    }

    public static Bitmap h(byte[] bArr) {
        return ImageUtils.bytes2Bitmap(bArr);
    }

    public static byte[] a(Drawable drawable) {
        return ImageUtils.drawable2Bytes(drawable);
    }

    public static byte[] a(Drawable drawable, Bitmap.CompressFormat compressFormat, int i) {
        return ImageUtils.drawable2Bytes(drawable, compressFormat, i);
    }

    public static Drawable i(byte[] bArr) {
        return ImageUtils.bytes2Drawable(bArr);
    }

    public static Bitmap a(View view) {
        return ImageUtils.view2Bitmap(view);
    }

    public static Bitmap b(Drawable drawable) {
        return ImageUtils.drawable2Bitmap(drawable);
    }

    public static Drawable b(Bitmap bitmap) {
        return ImageUtils.bitmap2Drawable(bitmap);
    }

    public static boolean a(Intent intent) {
        return IntentUtils.isIntentAvailable(intent);
    }

    public static Intent i(String str) {
        return IntentUtils.getLaunchAppIntent(str);
    }

    public static Intent h(File file) {
        return IntentUtils.getInstallAppIntent(file);
    }

    public static Intent a(Uri uri) {
        return IntentUtils.getInstallAppIntent(uri);
    }

    public static Intent j(String str) {
        return IntentUtils.getUninstallAppIntent(str);
    }

    public static Intent k(String str) {
        return IntentUtils.getDialIntent(str);
    }

    @RequiresPermission("android.permission.CALL_PHONE")
    public static Intent l(String str) {
        return IntentUtils.getCallIntent(str);
    }

    public static Intent a(String str, String str2) {
        return IntentUtils.getSendSmsIntent(str, str2);
    }

    public static Intent a(String str, boolean z) {
        return IntentUtils.getLaunchAppDetailsSettingsIntent(str, z);
    }

    public static String m(String str) {
        return JsonUtils.formatJson(str);
    }

    public static void c(Activity activity) {
        KeyboardUtils.fixSoftInputLeaks(activity);
    }

    public static Notification a(NotificationUtils.ChannelConfig channelConfig, Utils.Consumer<NotificationCompat.Builder> consumer) {
        return NotificationUtils.getNotification(channelConfig, consumer);
    }

    public static boolean a(String... strArr) {
        return PermissionUtils.isGranted(strArr);
    }

    @RequiresApi(api = 23)
    public static boolean m() {
        return PermissionUtils.isGrantedDrawOverlays();
    }

    public static boolean n() {
        return ProcessUtils.isMainProcess();
    }

    public static String o() {
        return ProcessUtils.getForegroundProcessName();
    }

    public static String p() {
        return ProcessUtils.getCurrentProcessName();
    }

    public static boolean q() {
        return RomUtils.isSamsung();
    }

    public static int r() {
        return ScreenUtils.getAppScreenWidth();
    }

    public static boolean s() {
        return SDCardUtils.isSDCardEnableByEnvironment();
    }

    public static boolean n(String str) {
        return ServiceUtils.isServiceRunning(str);
    }

    public static ShellUtils.CommandResult b(String str, boolean z) {
        return ShellUtils.execCmd(str, z);
    }

    public static int a(float f) {
        return SizeUtils.dp2px(f);
    }

    public static int b(float f) {
        return SizeUtils.px2dp(f);
    }

    public static int c(float f) {
        return SizeUtils.sp2px(f);
    }

    public static int d(float f) {
        return SizeUtils.px2sp(f);
    }

    public static SPUtils t() {
        return SPUtils.getInstance("Utils");
    }

    public static boolean o(String str) {
        return StringUtils.isSpace(str);
    }

    public static boolean a(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtils.equals(charSequence, charSequence2);
    }

    public static String a(@StringRes int i) {
        return StringUtils.getString(i);
    }

    public static String a(@StringRes int i, Object... objArr) {
        return StringUtils.getString(i, objArr);
    }

    public static String a(@Nullable String str, Object... objArr) {
        return StringUtils.format(str, objArr);
    }

    public static <T> Utils.Task<T> a(Utils.Task<T> task) {
        ThreadUtils.getCachedPool().execute(task);
        return task;
    }

    public static void a(Runnable runnable) {
        ThreadUtils.runOnUiThread(runnable);
    }

    public static void a(Runnable runnable, long j) {
        ThreadUtils.runOnUiThreadDelayed(runnable, j);
    }

    public static String a(Throwable th) {
        return ThrowableUtils.getFullStackTrace(th);
    }

    public static String a(long j, int i) {
        return TimeUtils.a(j, i);
    }

    public static void a(CharSequence charSequence) {
        ToastUtils.showShort(charSequence);
    }

    public static void u() {
        ToastUtils.cancel();
    }

    private static void a(Runnable... runnableArr) {
        for (Runnable runnable : runnableArr) {
            ThreadUtils.getCachedPool().execute(runnable);
        }
    }

    public static Uri i(File file) {
        return UriUtils.file2Uri(file);
    }

    public static File b(Uri uri) {
        return UriUtils.uri2File(uri);
    }

    public static View b(@LayoutRes int i) {
        return ViewUtils.layoutId2View(i);
    }

    public static boolean v() {
        return ViewUtils.isLayoutRtl();
    }

    /* compiled from: UtilsBridge.java */
    /* loaded from: classes.dex */
    public static final class a {
        private String a;
        private LinkedHashMap<String, String> b = new LinkedHashMap<>();
        private LinkedHashMap<String, String> c = new LinkedHashMap<>();

        public a(String str) {
            this.a = str;
        }

        public void a(String str, String str2) {
            a(this.b, str, str2);
        }

        public void a(Map<String, String> map) {
            a(this.c, map);
        }

        public void b(String str, String str2) {
            a(this.c, str, str2);
        }

        private void a(Map<String, String> map, Map<String, String> map2) {
            if (!(map2 == null || map2.isEmpty())) {
                for (Map.Entry<String, String> entry : map2.entrySet()) {
                    a(map, entry.getKey(), entry.getValue());
                }
            }
        }

        private void a(Map<String, String> map, String str, String str2) {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                int length = 19 - str.length();
                if (length > 0) {
                    str = str + "                   ".substring(0, length);
                }
                map.put(str, str2);
            }
        }

        public String a() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : this.c.entrySet()) {
                sb.append(entry.getKey());
                sb.append(": ");
                sb.append(entry.getValue());
                sb.append("\n");
            }
            return sb.toString();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            String str = "************* " + this.a + " Head ****************\n";
            sb.append(str);
            for (Map.Entry<String, String> entry : this.b.entrySet()) {
                sb.append(entry.getKey());
                sb.append(": ");
                sb.append(entry.getValue());
                sb.append("\n");
            }
            sb.append("Rom Info           : ");
            sb.append(RomUtils.getRomInfo());
            sb.append("\n");
            sb.append("Device Manufacturer: ");
            sb.append(Build.MANUFACTURER);
            sb.append("\n");
            sb.append("Device Model       : ");
            sb.append(Build.MODEL);
            sb.append("\n");
            sb.append("Android Version    : ");
            sb.append(Build.VERSION.RELEASE);
            sb.append("\n");
            sb.append("Android SDK        : ");
            sb.append(Build.VERSION.SDK_INT);
            sb.append("\n");
            sb.append("App VersionName    : ");
            sb.append(AppUtils.getAppVersionName());
            sb.append("\n");
            sb.append("App VersionCode    : ");
            sb.append(AppUtils.getAppVersionCode());
            sb.append("\n");
            sb.append(a());
            sb.append(str);
            sb.append("\n");
            return sb.toString();
        }
    }
}
