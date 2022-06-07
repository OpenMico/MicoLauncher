package cn.bingoogolapple.swipebacklayout;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;

/* compiled from: UIUtil.java */
/* loaded from: classes.dex */
class c {
    public static int a(Activity activity) {
        Resources resources = activity.getResources();
        int identifier = resources.getIdentifier(b(activity) ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
        if (identifier <= 0 || !f(activity) || !e(activity)) {
            return 0;
        }
        return resources.getDimensionPixelSize(identifier);
    }

    public static boolean b(Activity activity) {
        return activity.getResources().getConfiguration().orientation == 1;
    }

    private static boolean e(Activity activity) {
        if (Build.VERSION.SDK_INT < 17) {
            return false;
        }
        Display defaultDisplay = activity.getWindow().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        View decorView = activity.getWindow().getDecorView();
        if (2 == activity.getResources().getConfiguration().orientation) {
            View findViewById = decorView.findViewById(16908290);
            return (findViewById == null || point.x == findViewById.getWidth()) ? false : true;
        }
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        return rect.bottom != point.y;
    }

    private static boolean f(Activity activity) {
        if (Build.VERSION.SDK_INT >= 17) {
            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getRealMetrics(displayMetrics);
            int i = displayMetrics.heightPixels;
            int i2 = displayMetrics.widthPixels;
            DisplayMetrics displayMetrics2 = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics2);
            return i2 - displayMetrics2.widthPixels > 0 || i - displayMetrics2.heightPixels > 0;
        }
        Resources resources = activity.getResources();
        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean z = identifier > 0 ? resources.getBoolean(identifier) : false;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            String str = (String) cls.getMethod(BluetoothConstants.GET, String.class).invoke(cls, "qemu.hw.mainkeys");
            if ("1".equals(str)) {
                return false;
            }
            if ("0".equals(str)) {
                return true;
            }
            return z;
        } catch (Exception unused) {
            return z;
        }
    }

    public static int c(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealMetrics(displayMetrics);
        } else {
            defaultDisplay.getMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }

    public static int d(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealMetrics(displayMetrics);
        } else {
            defaultDisplay.getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }
}
