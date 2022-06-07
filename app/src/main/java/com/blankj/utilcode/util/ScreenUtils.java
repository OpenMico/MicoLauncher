package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.alibaba.fastjson.asm.Opcodes;

/* loaded from: classes.dex */
public final class ScreenUtils {
    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int getScreenWidth() {
        WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            windowManager.getDefaultDisplay().getRealSize(point);
        } else {
            windowManager.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    public static int getScreenHeight() {
        WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            windowManager.getDefaultDisplay().getRealSize(point);
        } else {
            windowManager.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    public static int getAppScreenWidth() {
        WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static int getAppScreenHeight() {
        WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.y;
    }

    public static float getScreenDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static int getScreenDensityDpi() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    public static float getScreenXDpi() {
        return Resources.getSystem().getDisplayMetrics().xdpi;
    }

    public static float getScreenYDpi() {
        return Resources.getSystem().getDisplayMetrics().ydpi;
    }

    public int calculateDistanceByX(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return getScreenWidth() - iArr[0];
    }

    public int calculateDistanceByY(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return getScreenHeight() - iArr[1];
    }

    public int getViewX(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return iArr[0];
    }

    public int getViewY(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return iArr[1];
    }

    public static void setFullScreen(@NonNull Activity activity) {
        if (activity != null) {
            activity.getWindow().addFlags(1024);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void setNonFullScreen(@NonNull Activity activity) {
        if (activity != null) {
            activity.getWindow().clearFlags(1024);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void toggleFullScreen(@NonNull Activity activity) {
        if (activity != null) {
            boolean isFullScreen = isFullScreen(activity);
            Window window = activity.getWindow();
            if (isFullScreen) {
                window.clearFlags(1024);
            } else {
                window.addFlags(1024);
            }
        } else {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static boolean isFullScreen(@NonNull Activity activity) {
        if (activity != null) {
            return (activity.getWindow().getAttributes().flags & 1024) == 1024;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @SuppressLint({"SourceLockedOrientationActivity"})
    public static void setLandscape(@NonNull Activity activity) {
        if (activity != null) {
            activity.setRequestedOrientation(0);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @SuppressLint({"SourceLockedOrientationActivity"})
    public static void setPortrait(@NonNull Activity activity) {
        if (activity != null) {
            activity.setRequestedOrientation(1);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isLandscape() {
        return Utils.getApp().getResources().getConfiguration().orientation == 2;
    }

    public static boolean isPortrait() {
        return Utils.getApp().getResources().getConfiguration().orientation == 1;
    }

    public static int getScreenRotation(@NonNull Activity activity) {
        if (activity != null) {
            switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
                case 0:
                    return 0;
                case 1:
                    return 90;
                case 2:
                    return Opcodes.GETFIELD;
                case 3:
                    return 270;
                default:
                    return 0;
            }
        } else {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Bitmap screenShot(@NonNull Activity activity) {
        if (activity != null) {
            return screenShot(activity, false);
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Bitmap screenShot(@NonNull Activity activity, boolean z) {
        if (activity != null) {
            Bitmap a = b.a(activity.getWindow().getDecorView());
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            if (!z) {
                return Bitmap.createBitmap(a, 0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
            }
            int j = b.j();
            return Bitmap.createBitmap(a, 0, j, displayMetrics.widthPixels, displayMetrics.heightPixels - j);
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isScreenLock() {
        KeyguardManager keyguardManager = (KeyguardManager) Utils.getApp().getSystemService("keyguard");
        if (keyguardManager == null) {
            return false;
        }
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    @RequiresPermission("android.permission.WRITE_SETTINGS")
    public static void setSleepDuration(int i) {
        Settings.System.putInt(Utils.getApp().getContentResolver(), "screen_off_timeout", i);
    }

    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(Utils.getApp().getContentResolver(), "screen_off_timeout");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }
}
