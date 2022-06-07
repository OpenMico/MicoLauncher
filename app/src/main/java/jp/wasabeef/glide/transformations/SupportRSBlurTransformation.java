package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.renderscript.RSRuntimeException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.security.MessageDigest;
import jp.wasabeef.glide.transformations.internal.FastBlur;
import jp.wasabeef.glide.transformations.internal.SupportRSBlur;

/* loaded from: classes5.dex */
public class SupportRSBlurTransformation extends BitmapTransformation {
    private static int a = 25;
    private static int b = 1;
    private int c;
    private int d;

    public SupportRSBlurTransformation() {
        this(a, b);
    }

    public SupportRSBlurTransformation(int i) {
        this(i, b);
    }

    public SupportRSBlurTransformation(int i, int i2) {
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
        if (Build.VERSION.SDK_INT < 18) {
            return FastBlur.blur(bitmap2, this.c, true);
        }
        try {
            return SupportRSBlur.blur(context, bitmap2, this.c);
        } catch (RSRuntimeException unused) {
            return FastBlur.blur(bitmap2, this.c, true);
        }
    }

    public String toString() {
        return "SupportRSBlurTransformation(radius=" + this.c + ", sampling=" + this.d + ")";
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof SupportRSBlurTransformation) {
            SupportRSBlurTransformation supportRSBlurTransformation = (SupportRSBlurTransformation) obj;
            if (supportRSBlurTransformation.c == this.c && supportRSBlurTransformation.d == this.d) {
                return true;
            }
        }
        return false;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return "jp.wasabeef.glide.transformations.SupportRSBlurTransformation.1".hashCode() + (this.c * 1000) + (this.d * 10);
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("jp.wasabeef.glide.transformations.SupportRSBlurTransformation.1" + this.c + this.d).getBytes(CHARSET));
    }
}
