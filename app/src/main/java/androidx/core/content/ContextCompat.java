package androidx.core.content;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionsManager;
import android.content.pm.LauncherApps;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DoNotInline;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.os.ExecutorCompat;
import com.umeng.analytics.pro.ai;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.idm.service.iot.InputMethodService;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.ui.settings.UserAvatarUpdateActivity;
import com.xiaomi.smarthome.library.common.network.Network;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class ContextCompat {
    private static final Object a = new Object();
    private static final Object b = new Object();
    private static TypedValue c;

    @Nullable
    public static String getAttributionTag(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 30) {
            return h.a(context);
        }
        return null;
    }

    public static boolean startActivities(@NonNull Context context, @NonNull Intent[] intentArr) {
        return startActivities(context, intentArr, null);
    }

    public static boolean startActivities(@NonNull Context context, @NonNull Intent[] intentArr, @Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            a.a(context, intentArr, bundle);
            return true;
        }
        context.startActivities(intentArr);
        return true;
    }

    public static void startActivity(@NonNull Context context, @NonNull Intent intent, @Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            a.a(context, intent, bundle);
        } else {
            context.startActivity(intent);
        }
    }

    @Nullable
    public static File getDataDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return e.a(context);
        }
        String str = context.getApplicationInfo().dataDir;
        if (str != null) {
            return new File(str);
        }
        return null;
    }

    @NonNull
    public static File[] getObbDirs(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 19 ? b.b(context) : new File[]{context.getObbDir()};
    }

    @NonNull
    public static File[] getExternalFilesDirs(@NonNull Context context, @Nullable String str) {
        return Build.VERSION.SDK_INT >= 19 ? b.a(context, str) : new File[]{context.getExternalFilesDir(str)};
    }

    @NonNull
    public static File[] getExternalCacheDirs(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 19 ? b.a(context) : new File[]{context.getExternalCacheDir()};
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int i2) {
        int i3;
        if (Build.VERSION.SDK_INT >= 21) {
            return c.a(context, i2);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return context.getResources().getDrawable(i2);
        }
        synchronized (a) {
            if (c == null) {
                c = new TypedValue();
            }
            context.getResources().getValue(i2, c, true);
            i3 = c.resourceId;
        }
        return context.getResources().getDrawable(i3);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int i2) {
        return ResourcesCompat.getColorStateList(context.getResources(), i2, context.getTheme());
    }

    @ColorInt
    public static int getColor(@NonNull Context context, @ColorRes int i2) {
        if (Build.VERSION.SDK_INT >= 23) {
            return d.b(context, i2);
        }
        return context.getResources().getColor(i2);
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String str) {
        if (str != null) {
            return context.checkPermission(str, Process.myPid(), Process.myUid());
        }
        throw new IllegalArgumentException("permission is null");
    }

    @Nullable
    public static File getNoBackupFilesDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return c.a(context);
        }
        return a(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }

    public static File getCodeCacheDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return c.b(context);
        }
        return a(new File(context.getApplicationInfo().dataDir, "code_cache"));
    }

    private static File a(File file) {
        synchronized (b) {
            if (!file.exists()) {
                if (file.mkdirs()) {
                    return file;
                }
                Log.w("ContextCompat", "Unable to create files subdir " + file.getPath());
            }
            return file;
        }
    }

    @Nullable
    public static Context createDeviceProtectedStorageContext(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return e.b(context);
        }
        return null;
    }

    public static boolean isDeviceProtectedStorage(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return e.c(context);
        }
        return false;
    }

    public static Executor getMainExecutor(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return g.a(context);
        }
        return ExecutorCompat.create(new Handler(context.getMainLooper()));
    }

    public static void startForegroundService(@NonNull Context context, @NonNull Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            f.a(context, intent);
        } else {
            context.startService(intent);
        }
    }

    @Nullable
    public static <T> T getSystemService(@NonNull Context context, @NonNull Class<T> cls) {
        if (Build.VERSION.SDK_INT >= 23) {
            return (T) d.a(context, cls);
        }
        String systemServiceName = getSystemServiceName(context, cls);
        if (systemServiceName != null) {
            return (T) context.getSystemService(systemServiceName);
        }
        return null;
    }

    @Nullable
    public static String getSystemServiceName(@NonNull Context context, @NonNull Class<?> cls) {
        if (Build.VERSION.SDK_INT >= 23) {
            return d.b(context, cls);
        }
        return i.a.get(cls);
    }

    /* loaded from: classes.dex */
    public static final class i {
        static final HashMap<Class<?>, String> a = new HashMap<>();

        static {
            if (Build.VERSION.SDK_INT >= 22) {
                a.put(SubscriptionManager.class, "telephony_subscription_service");
                a.put(UsageStatsManager.class, "usagestats");
            }
            if (Build.VERSION.SDK_INT >= 21) {
                a.put(AppWidgetManager.class, "appwidget");
                a.put(BatteryManager.class, "batterymanager");
                a.put(CameraManager.class, UserAvatarUpdateActivity.CAMERA);
                a.put(JobScheduler.class, "jobscheduler");
                a.put(LauncherApps.class, "launcherapps");
                a.put(MediaProjectionManager.class, "media_projection");
                a.put(MediaSessionManager.class, "media_session");
                a.put(RestrictionsManager.class, "restrictions");
                a.put(TelecomManager.class, "telecom");
                a.put(TvInputManager.class, "tv_input");
            }
            if (Build.VERSION.SDK_INT >= 19) {
                a.put(AppOpsManager.class, "appops");
                a.put(CaptioningManager.class, "captioning");
                a.put(ConsumerIrManager.class, "consumer_ir");
                a.put(PrintManager.class, "print");
            }
            if (Build.VERSION.SDK_INT >= 18) {
                a.put(BluetoothManager.class, "bluetooth");
            }
            if (Build.VERSION.SDK_INT >= 17) {
                a.put(DisplayManager.class, "display");
                a.put(UserManager.class, "user");
            }
            if (Build.VERSION.SDK_INT >= 16) {
                a.put(InputManager.class, InputMethodService.InputPropertyCommand.INPUT_SERVICE_DESC);
                a.put(MediaRouter.class, "media_router");
                a.put(NsdManager.class, "servicediscovery");
            }
            a.put(AccessibilityManager.class, "accessibility");
            a.put(AccountManager.class, "account");
            a.put(ActivityManager.class, IDMServer.PERSIST_TYPE_ACTIVITY);
            a.put(AlarmManager.class, "alarm");
            a.put(AudioManager.class, "audio");
            a.put(ClipboardManager.class, "clipboard");
            a.put(ConnectivityManager.class, "connectivity");
            a.put(DevicePolicyManager.class, "device_policy");
            a.put(DownloadManager.class, OneTrack.Event.DOWNLOAD);
            a.put(DropBoxManager.class, "dropbox");
            a.put(InputMethodManager.class, "input_method");
            a.put(KeyguardManager.class, "keyguard");
            a.put(LayoutInflater.class, "layout_inflater");
            a.put(LocationManager.class, "location");
            a.put(NfcManager.class, "nfc");
            a.put(NotificationManager.class, "notification");
            a.put(PowerManager.class, "power");
            a.put(SearchManager.class, "search");
            a.put(SensorManager.class, ai.ac);
            a.put(StorageManager.class, "storage");
            a.put(TelephonyManager.class, "phone");
            a.put(TextServicesManager.class, "textservices");
            a.put(UiModeManager.class, "uimode");
            a.put(UsbManager.class, "usb");
            a.put(Vibrator.class, "vibrator");
            a.put(WallpaperManager.class, "wallpaper");
            a.put(WifiP2pManager.class, "wifip2p");
            a.put(WifiManager.class, Network.NETWORK_TYPE_WIFI);
            a.put(WindowManager.class, "window");
        }
    }

    @RequiresApi(16)
    /* loaded from: classes.dex */
    public static class a {
        @DoNotInline
        static void a(Context context, Intent[] intentArr, Bundle bundle) {
            context.startActivities(intentArr, bundle);
        }

        @DoNotInline
        static void a(Context context, Intent intent, Bundle bundle) {
            context.startActivity(intent, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(19)
    /* loaded from: classes.dex */
    public static class b {
        @DoNotInline
        static File[] a(Context context) {
            return context.getExternalCacheDirs();
        }

        @DoNotInline
        static File[] a(Context context, String str) {
            return context.getExternalFilesDirs(str);
        }

        @DoNotInline
        static File[] b(Context context) {
            return context.getObbDirs();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class c {
        @DoNotInline
        static Drawable a(Context context, int i) {
            return context.getDrawable(i);
        }

        @DoNotInline
        static File a(Context context) {
            return context.getNoBackupFilesDir();
        }

        @DoNotInline
        static File b(Context context) {
            return context.getCodeCacheDir();
        }
    }

    @RequiresApi(23)
    /* loaded from: classes.dex */
    public static class d {
        @DoNotInline
        static ColorStateList a(Context context, int i) {
            return context.getColorStateList(i);
        }

        @DoNotInline
        static int b(Context context, int i) {
            return context.getColor(i);
        }

        @DoNotInline
        static <T> T a(Context context, Class<T> cls) {
            return (T) context.getSystemService(cls);
        }

        @DoNotInline
        static String b(Context context, Class<?> cls) {
            return context.getSystemServiceName(cls);
        }
    }

    @RequiresApi(24)
    /* loaded from: classes.dex */
    static class e {
        @DoNotInline
        static File a(Context context) {
            return context.getDataDir();
        }

        @DoNotInline
        static Context b(Context context) {
            return context.createDeviceProtectedStorageContext();
        }

        @DoNotInline
        static boolean c(Context context) {
            return context.isDeviceProtectedStorage();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(26)
    /* loaded from: classes.dex */
    public static class f {
        @DoNotInline
        static ComponentName a(Context context, Intent intent) {
            return context.startForegroundService(intent);
        }
    }

    @RequiresApi(28)
    /* loaded from: classes.dex */
    static class g {
        @DoNotInline
        static Executor a(Context context) {
            return context.getMainExecutor();
        }
    }

    @RequiresApi(30)
    /* loaded from: classes.dex */
    static class h {
        @DoNotInline
        static String a(Context context) {
            return context.getAttributionTag();
        }
    }
}
