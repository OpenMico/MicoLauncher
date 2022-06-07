package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.security.MessageDigest;

/* loaded from: classes5.dex */
public class CropTransformation extends BitmapTransformation {
    private int a;
    private int b;
    private CropType c;

    /* loaded from: classes5.dex */
    public enum CropType {
        TOP,
        CENTER,
        BOTTOM
    }

    public CropTransformation(int i, int i2) {
        this(i, i2, CropType.CENTER);
    }

    public CropTransformation(int i, int i2, CropType cropType) {
        this.c = CropType.CENTER;
        this.a = i;
        this.b = i2;
        this.c = cropType;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        int i3 = this.a;
        if (i3 == 0) {
            i3 = bitmap.getWidth();
        }
        this.a = i3;
        int i4 = this.b;
        if (i4 == 0) {
            i4 = bitmap.getHeight();
        }
        this.b = i4;
        Bitmap bitmap2 = bitmapPool.get(this.a, this.b, bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888);
        bitmap2.setHasAlpha(true);
        float max = Math.max(this.a / bitmap.getWidth(), this.b / bitmap.getHeight());
        float width = bitmap.getWidth() * max;
        float height = max * bitmap.getHeight();
        float f = (this.a - width) / 2.0f;
        float a = a(height);
        new Canvas(bitmap2).drawBitmap(bitmap, (Rect) null, new RectF(f, a, width + f, height + a), (Paint) null);
        return bitmap2;
    }

    private float a(float f) {
        switch (this.c) {
            case TOP:
                return 0.0f;
            case CENTER:
                return (this.b - f) / 2.0f;
            case BOTTOM:
                return this.b - f;
            default:
                return 0.0f;
        }
    }

    public String toString() {
        return "CropTransformation(width=" + this.a + ", height=" + this.b + ", cropType=" + this.c + ")";
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof CropTransformation) {
            CropTransformation cropTransformation = (CropTransformation) obj;
            if (cropTransformation.a == this.a && cropTransformation.b == this.b && cropTransformation.c == this.c) {
                return true;
            }
        }
        return false;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return "jp.wasabeef.glide.transformations.CropTransformation.1".hashCode() + (this.a * 100000) + (this.b * 1000) + (this.c.ordinal() * 10);
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("jp.wasabeef.glide.transformations.CropTransformation.1" + this.a + this.b + this.c).getBytes(CHARSET));
    }
}
