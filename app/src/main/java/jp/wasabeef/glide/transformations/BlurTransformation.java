package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.security.MessageDigest;
import jp.wasabeef.glide.transformations.internal.FastBlur;

/* loaded from: classes5.dex */
public class BlurTransformation extends BitmapTransformation {
    private static int a = 25;
    private static int b = 1;
    private int c;
    private int d;

    public BlurTransformation() {
        this(a, b);
    }

    public BlurTransformation(int i) {
        this(i, b);
    }

    public BlurTransformation(int i, int i2) {
        this.c = i;
        this.d = i2;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i3 = this.d;
        Bitmap bitmap2 = bitmapPool.get(width / i3, height / i3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        int i4 = this.d;
        canvas.scale(1.0f / i4, 1.0f / i4);
        Paint paint = new Paint();
        paint.setFlags(2);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return FastBlur.blur(bitmap2, this.c, true);
    }

    public String toString() {
        return "BlurTransformation(radius=" + this.c + ", sampling=" + this.d + ")";
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof BlurTransformation) {
            BlurTransformation blurTransformation = (BlurTransformation) obj;
            if (blurTransformation.c == this.c && blurTransformation.d == this.d) {
                return true;
            }
        }
        return false;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return "jp.wasabeef.glide.transformations.BlurTransformation.1".hashCode() + (this.c * 1000) + (this.d * 10);
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("jp.wasabeef.glide.transformations.BlurTransformation.1" + this.c + this.d).getBytes(CHARSET));
    }
}
