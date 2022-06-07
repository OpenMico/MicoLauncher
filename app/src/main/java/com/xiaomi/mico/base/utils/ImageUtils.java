package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.util.Log;

/* loaded from: classes3.dex */
public class ImageUtils {
    private ImageUtils() {
    }

    public static Bitmap reduceBitmapSize(long j, Bitmap bitmap) {
        try {
            int rowBytes = bitmap.getRowBytes() * bitmap.getHeight();
            double d = 1.0d;
            Bitmap bitmap2 = null;
            while (rowBytes > j) {
                if (bitmap2 != null && !bitmap2.equals(bitmap)) {
                    bitmap2.recycle();
                }
                bitmap2 = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * d), (int) (bitmap.getHeight() * d), true);
                rowBytes = bitmap2.getRowBytes() * bitmap2.getHeight();
                d *= 0.9f;
            }
            return bitmap2 == null ? bitmap : bitmap2;
        } catch (OutOfMemoryError unused) {
            return null;
        }
    }

    public static Bitmap getRotatedBitmap(String str, Bitmap bitmap) {
        int i;
        try {
            i = (int) ImageExifUtils.exifOrientationToDegrees(new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
        } catch (Exception e) {
            Log.e("ImageUtils", "getRotatedBitmap", e);
            i = 0;
        }
        if (i == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(i);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (createBitmap == bitmap) {
            return createBitmap;
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap rotate(Bitmap bitmap, int i) {
        int i2 = i % 360;
        if (i2 == 0 || bitmap == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(i2, bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap == createBitmap) {
                return bitmap;
            }
            bitmap.recycle();
            return createBitmap;
        } catch (OutOfMemoryError unused) {
            return bitmap;
        }
    }

    public static Bitmap transform(Matrix matrix, Bitmap bitmap, int i, int i2, boolean z) {
        Matrix matrix2 = matrix;
        int width = bitmap.getWidth() - i;
        int height = bitmap.getHeight() - i2;
        if (z || (width >= 0 && height >= 0)) {
            float width2 = bitmap.getWidth();
            float height2 = bitmap.getHeight();
            float f = i;
            float f2 = i2;
            if (width2 / height2 > f / f2) {
                float f3 = f2 / height2;
                if (f3 < 0.9f || f3 > 1.0f) {
                    matrix.setScale(f3, f3);
                } else {
                    matrix2 = null;
                }
            } else {
                float f4 = f / width2;
                if (f4 < 0.9f || f4 > 1.0f) {
                    matrix.setScale(f4, f4);
                    matrix2 = matrix2;
                } else {
                    matrix2 = null;
                }
            }
            Bitmap createBitmap = matrix2 != null ? Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix2, true) : bitmap;
            Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, Math.max(0, createBitmap.getWidth() - i) / 2, Math.max(0, createBitmap.getHeight() - i2) / 2, i, i2);
            if (createBitmap != bitmap) {
                createBitmap.recycle();
            }
            return createBitmap2;
        }
        Bitmap createBitmap3 = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap3);
        int max = Math.max(0, width / 2);
        int max2 = Math.max(0, height / 2);
        Rect rect = new Rect(max, max2, Math.min(i, bitmap.getWidth()) + max, Math.min(i2, bitmap.getHeight()) + max2);
        int width3 = (i - rect.width()) / 2;
        int height3 = (i2 - rect.height()) / 2;
        canvas.drawBitmap(bitmap, rect, new Rect(width3, height3, i - width3, i2 - height3), (Paint) null);
        return createBitmap3;
    }

    public static <T> int indexOf(T[] tArr, T t) {
        for (int i = 0; i < tArr.length; i++) {
            if (tArr[i].equals(t)) {
                return i;
            }
        }
        return -1;
    }

    public static int getDrawableResId(Context context, String str) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(str, "drawable", context.getPackageName());
    }

    public static boolean floatEquals(float f, float f2) {
        return Math.abs(f - f2) <= 1.0E-7f;
    }

    public static boolean doubleEquals(double d, double d2) {
        return Math.abs(d - d2) <= 1.0E-7d;
    }
}
