package com.xiaomi.smarthome.core.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.xiaomi.mico.router.proxy.Proxies;

/* loaded from: classes4.dex */
public class UiUtils {
    private static long a;

    public static void showToast(String str) {
        try {
            Toast.makeText(Proxies.Instance.getApp(), str, 0).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showStrictToast(String str) {
        if (!isFastClick()) {
            showToast(str);
        }
    }

    public static void showLongToast(String str) {
        try {
            Toast.makeText(Proxies.Instance.getApp(), str, 1).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showShortToast(String str) {
        try {
            Toast.makeText(Proxies.Instance.getApp(), str, 0).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToastSafe(String str) {
        try {
            if (!isFastClick()) {
                Toast.makeText(Proxies.Instance.getApp(), str, 0).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized boolean isFastClick() {
        boolean z;
        synchronized (UiUtils.class) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = currentTimeMillis - a;
            a = currentTimeMillis;
            z = j < 500;
        }
        return z;
    }

    public static synchronized boolean isNotFastClick() {
        boolean z;
        synchronized (UiUtils.class) {
            z = !isFastClick();
        }
        return z;
    }

    public static synchronized boolean isNotFastClick(long j) {
        boolean z;
        synchronized (UiUtils.class) {
            z = !isFastClick(j);
        }
        return z;
    }

    public static synchronized boolean isFastClick(long j) {
        boolean z;
        synchronized (UiUtils.class) {
            long currentTimeMillis = System.currentTimeMillis();
            long j2 = currentTimeMillis - a;
            a = currentTimeMillis;
            z = j2 < j;
        }
        return z;
    }

    public static String convertToStr(Editable editable) {
        return editable == null ? "" : editable.toString();
    }

    public static Bitmap screenShotImageView(@NonNull Activity activity, ImageView imageView) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap drawingCache = decorView.getDrawingCache();
        int[] iArr = new int[2];
        imageView.getLocationOnScreen(iArr);
        return Bitmap.createBitmap(drawingCache, iArr[0], iArr[1], imageView.getWidth(), imageView.getHeight());
    }
}
