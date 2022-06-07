package com.xiaomi.mico.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/* loaded from: classes3.dex */
public class DisplayUtils {
    public static int dip2px(Activity activity, float f) {
        if (activity == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) ((f * displayMetrics.density) + 0.5f);
    }

    public static int dip2px(Context context, float f) {
        if (context != null) {
            return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
        }
        return 0;
    }

    public static int px2dip(Activity activity, float f) {
        if (activity == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) ((f / displayMetrics.density) + 0.5f);
    }

    public static int px2dip(Context context, float f) {
        if (context != null) {
            return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
        }
        return 0;
    }

    public static int getScreenWidthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeightPixels(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static Rect getScreenRect(Context context) {
        return new Rect(0, 0, getScreenWidthPixels(context), getScreenHeightPixels(context));
    }

    public static int getDisplayRealHeight(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.y;
    }

    public static int getDisplayRealWidth(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.x;
    }

    public static double getPhysicsScreenSize(Context context) {
        Point point = new Point();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealSize(point);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.sqrt(Math.pow(point.x / displayMetrics.xdpi, 2.0d) + Math.pow(point.y / displayMetrics.ydpi, 2.0d));
    }

    private DisplayUtils() {
    }
}
