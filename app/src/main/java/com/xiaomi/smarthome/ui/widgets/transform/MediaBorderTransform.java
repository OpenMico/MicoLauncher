package com.xiaomi.smarthome.ui.widgets.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.security.MessageDigest;
import jp.wasabeef.glide.transformations.BitmapTransformation;

/* loaded from: classes4.dex */
public class MediaBorderTransform extends BitmapTransformation {
    private final String a = getClass().getName();
    private Paint b = new Paint();
    private float c;
    private int d;
    private float e;

    public MediaBorderTransform(float f, float f2, int i) {
        this.c = f2;
        this.d = i;
        this.e = f;
        this.b.setColor(i);
        this.b.setStyle(Paint.Style.STROKE);
        this.b.setAntiAlias(true);
        this.b.setStrokeWidth(f2);
        this.b.setDither(true);
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        Bitmap bitmap2 = bitmapPool.get(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        if (bitmap2 == null) {
            bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0.0f, 0.0f, bitmap2.getWidth(), bitmap2.getHeight());
        float f = this.e;
        canvas.drawRoundRect(rectF, f, f, paint);
        float f2 = this.e;
        canvas.drawRoundRect(rectF, f2, f2, this.b);
        return bitmap2;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(this.a.getBytes(CHARSET));
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return obj instanceof MediaBorderTransform;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return this.a.hashCode();
    }
}
