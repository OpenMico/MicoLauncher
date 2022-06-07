package com.xiaomi.micolauncher.module.homepage.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.xiaomi.micolauncher.common.BlurBuilder;
import com.xiaomi.micolauncher.common.transformation.MicoTransformUtils;

/* loaded from: classes3.dex */
public class BlurManager {
    public static final int RADIUS = 10;

    /* loaded from: classes3.dex */
    public static class a {
        private static final BlurManager a = new BlurManager();
    }

    private BlurManager() {
    }

    public static BlurManager getManager() {
        return a.a;
    }

    public Bitmap blur(Context context, Bitmap bitmap, int i) {
        return blur(context, null, bitmap, i, 10, 10);
    }

    public Bitmap blur(Context context, Bitmap bitmap, int i, int i2) {
        return blur(context, null, bitmap, i, i2, 10);
    }

    public Bitmap blur(Context context, @Nullable BitmapPool bitmapPool, Bitmap bitmap, int i, int i2, int i3) {
        Bitmap bitmap2;
        Bitmap bitmap3;
        int width = bitmap.getWidth();
        int width2 = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (bitmapPool != null) {
            bitmap2 = bitmapPool.get(width, i, MicoTransformUtils.getNonNullConfig(bitmap));
        } else {
            bitmap2 = Bitmap.createBitmap(width, i, MicoTransformUtils.getNonNullConfig(bitmap));
        }
        Canvas canvas = new Canvas(bitmap2);
        int i4 = height - i;
        float f = width;
        float f2 = i;
        canvas.drawBitmap(bitmap, new Rect(0, i4, width2, height), new RectF(0.0f, 0.0f, f, f2), BlurBuilder.DEFAULT_PAINT);
        canvas.setBitmap(null);
        Bitmap blur = BlurBuilder.blur(context, bitmapPool, bitmap2, i2, i3);
        if (bitmapPool == null || blur.equals(bitmap2)) {
            bitmap2.recycle();
        } else {
            bitmapPool.put(bitmap2);
        }
        if (bitmapPool != null) {
            bitmap3 = bitmapPool.get(width2, height, MicoTransformUtils.getNonNullConfig(bitmap));
        } else {
            bitmap3 = Bitmap.createBitmap(width2, height, MicoTransformUtils.getNonNullConfig(bitmap));
        }
        Canvas canvas2 = new Canvas(bitmap3);
        Rect rect = new Rect(0, 0, width2, i4);
        float f3 = i4;
        canvas2.drawBitmap(bitmap, rect, new RectF(0.0f, 0.0f, width2, f3), BlurBuilder.DEFAULT_PAINT);
        Matrix matrix = new Matrix();
        matrix.setScale(f / blur.getWidth(), f2 / blur.getHeight());
        matrix.postTranslate(0.0f, f3);
        canvas2.drawBitmap(blur, matrix, BlurBuilder.DEFAULT_PAINT);
        canvas2.setBitmap(null);
        if (bitmapPool != null) {
            bitmapPool.put(blur);
        } else {
            blur.recycle();
        }
        return bitmap3;
    }
}
