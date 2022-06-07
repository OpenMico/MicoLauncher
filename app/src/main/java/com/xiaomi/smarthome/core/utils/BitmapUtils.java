package com.xiaomi.smarthome.core.utils;

import android.graphics.Bitmap;

/* loaded from: classes4.dex */
public class BitmapUtils {
    public static boolean compareBitmapByPixel(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap == bitmap2) {
            return true;
        }
        if (bitmap == null || bitmap2 == null) {
            return false;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (!(width == bitmap2.getWidth() && height == bitmap2.getHeight())) {
            return false;
        }
        int min = Math.min(height, width);
        for (int i = 0; i < min; i++) {
            if (bitmap.getPixel(i, i) != bitmap2.getPixel(i, i)) {
                return false;
            }
        }
        return true;
    }
}
