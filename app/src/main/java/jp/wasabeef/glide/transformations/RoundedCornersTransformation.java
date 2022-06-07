package jp.wasabeef.glide.transformations;

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

/* loaded from: classes5.dex */
public class RoundedCornersTransformation extends BitmapTransformation {
    private int a;
    private int b;
    private int c;
    private CornerType d;

    /* loaded from: classes5.dex */
    public enum CornerType {
        ALL,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        OTHER_TOP_LEFT,
        OTHER_TOP_RIGHT,
        OTHER_BOTTOM_LEFT,
        OTHER_BOTTOM_RIGHT,
        DIAGONAL_FROM_TOP_LEFT,
        DIAGONAL_FROM_TOP_RIGHT
    }

    public RoundedCornersTransformation(int i, int i2) {
        this(i, i2, CornerType.ALL);
    }

    public RoundedCornersTransformation(int i, int i2, CornerType cornerType) {
        this.a = i;
        this.b = this.a * 2;
        this.c = i2;
        this.d = cornerType;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmap2 = bitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        bitmap2.setHasAlpha(true);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        a(canvas, paint, width, height);
        return bitmap2;
    }

    private void a(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.c;
        float f3 = f - i;
        float f4 = f2 - i;
        switch (this.d) {
            case ALL:
                int i2 = this.c;
                RectF rectF = new RectF(i2, i2, f3, f4);
                int i3 = this.a;
                canvas.drawRoundRect(rectF, i3, i3, paint);
                return;
            case TOP_LEFT:
                b(canvas, paint, f3, f4);
                return;
            case TOP_RIGHT:
                c(canvas, paint, f3, f4);
                return;
            case BOTTOM_LEFT:
                d(canvas, paint, f3, f4);
                return;
            case BOTTOM_RIGHT:
                e(canvas, paint, f3, f4);
                return;
            case TOP:
                f(canvas, paint, f3, f4);
                return;
            case BOTTOM:
                g(canvas, paint, f3, f4);
                return;
            case LEFT:
                h(canvas, paint, f3, f4);
                return;
            case RIGHT:
                i(canvas, paint, f3, f4);
                return;
            case OTHER_TOP_LEFT:
                j(canvas, paint, f3, f4);
                return;
            case OTHER_TOP_RIGHT:
                k(canvas, paint, f3, f4);
                return;
            case OTHER_BOTTOM_LEFT:
                l(canvas, paint, f3, f4);
                return;
            case OTHER_BOTTOM_RIGHT:
                m(canvas, paint, f3, f4);
                return;
            case DIAGONAL_FROM_TOP_LEFT:
                n(canvas, paint, f3, f4);
                return;
            case DIAGONAL_FROM_TOP_RIGHT:
                o(canvas, paint, f3, f4);
                return;
            default:
                int i4 = this.c;
                RectF rectF2 = new RectF(i4, i4, f3, f4);
                int i5 = this.a;
                canvas.drawRoundRect(rectF2, i5, i5, paint);
                return;
        }
    }

    private void b(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.c;
        int i2 = this.b;
        RectF rectF = new RectF(i, i, i + i2, i + i2);
        int i3 = this.a;
        canvas.drawRoundRect(rectF, i3, i3, paint);
        int i4 = this.c;
        int i5 = this.a;
        canvas.drawRect(new RectF(i4, i4 + i5, i4 + i5, f2), paint);
        int i6 = this.c;
        canvas.drawRect(new RectF(this.a + i6, i6, f, f2), paint);
    }

    private void c(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.b;
        int i2 = this.c;
        RectF rectF = new RectF(f - i, i2, f, i2 + i);
        int i3 = this.a;
        canvas.drawRoundRect(rectF, i3, i3, paint);
        int i4 = this.c;
        canvas.drawRect(new RectF(i4, i4, f - this.a, f2), paint);
        int i5 = this.a;
        canvas.drawRect(new RectF(f - i5, this.c + i5, f, f2), paint);
    }

    private void d(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.c;
        int i2 = this.b;
        RectF rectF = new RectF(i, f2 - i2, i + i2, f2);
        int i3 = this.a;
        canvas.drawRoundRect(rectF, i3, i3, paint);
        int i4 = this.c;
        canvas.drawRect(new RectF(i4, i4, i4 + this.b, f2 - this.a), paint);
        int i5 = this.c;
        canvas.drawRect(new RectF(this.a + i5, i5, f, f2), paint);
    }

    private void e(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.b;
        RectF rectF = new RectF(f - i, f2 - i, f, f2);
        int i2 = this.a;
        canvas.drawRoundRect(rectF, i2, i2, paint);
        int i3 = this.c;
        canvas.drawRect(new RectF(i3, i3, f - this.a, f2), paint);
        int i4 = this.a;
        canvas.drawRect(new RectF(f - i4, this.c, f, f2 - i4), paint);
    }

    private void f(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.c;
        RectF rectF = new RectF(i, i, f, i + this.b);
        int i2 = this.a;
        canvas.drawRoundRect(rectF, i2, i2, paint);
        int i3 = this.c;
        canvas.drawRect(new RectF(i3, i3 + this.a, f, f2), paint);
    }

    private void g(Canvas canvas, Paint paint, float f, float f2) {
        RectF rectF = new RectF(this.c, f2 - this.b, f, f2);
        int i = this.a;
        canvas.drawRoundRect(rectF, i, i, paint);
        int i2 = this.c;
        canvas.drawRect(new RectF(i2, i2, f, f2 - this.a), paint);
    }

    private void h(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.c;
        RectF rectF = new RectF(i, i, i + this.b, f2);
        int i2 = this.a;
        canvas.drawRoundRect(rectF, i2, i2, paint);
        int i3 = this.c;
        canvas.drawRect(new RectF(this.a + i3, i3, f, f2), paint);
    }

    private void i(Canvas canvas, Paint paint, float f, float f2) {
        RectF rectF = new RectF(f - this.b, this.c, f, f2);
        int i = this.a;
        canvas.drawRoundRect(rectF, i, i, paint);
        int i2 = this.c;
        canvas.drawRect(new RectF(i2, i2, f - this.a, f2), paint);
    }

    private void j(Canvas canvas, Paint paint, float f, float f2) {
        RectF rectF = new RectF(this.c, f2 - this.b, f, f2);
        int i = this.a;
        canvas.drawRoundRect(rectF, i, i, paint);
        RectF rectF2 = new RectF(f - this.b, this.c, f, f2);
        int i2 = this.a;
        canvas.drawRoundRect(rectF2, i2, i2, paint);
        int i3 = this.c;
        int i4 = this.a;
        canvas.drawRect(new RectF(i3, i3, f - i4, f2 - i4), paint);
    }

    private void k(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.c;
        RectF rectF = new RectF(i, i, i + this.b, f2);
        int i2 = this.a;
        canvas.drawRoundRect(rectF, i2, i2, paint);
        RectF rectF2 = new RectF(this.c, f2 - this.b, f, f2);
        int i3 = this.a;
        canvas.drawRoundRect(rectF2, i3, i3, paint);
        int i4 = this.c;
        int i5 = this.a;
        canvas.drawRect(new RectF(i4 + i5, i4, f, f2 - i5), paint);
    }

    private void l(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.c;
        RectF rectF = new RectF(i, i, f, i + this.b);
        int i2 = this.a;
        canvas.drawRoundRect(rectF, i2, i2, paint);
        RectF rectF2 = new RectF(f - this.b, this.c, f, f2);
        int i3 = this.a;
        canvas.drawRoundRect(rectF2, i3, i3, paint);
        int i4 = this.c;
        int i5 = this.a;
        canvas.drawRect(new RectF(i4, i4 + i5, f - i5, f2), paint);
    }

    private void m(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.c;
        RectF rectF = new RectF(i, i, f, i + this.b);
        int i2 = this.a;
        canvas.drawRoundRect(rectF, i2, i2, paint);
        int i3 = this.c;
        RectF rectF2 = new RectF(i3, i3, i3 + this.b, f2);
        int i4 = this.a;
        canvas.drawRoundRect(rectF2, i4, i4, paint);
        int i5 = this.c;
        int i6 = this.a;
        canvas.drawRect(new RectF(i5 + i6, i5 + i6, f, f2), paint);
    }

    private void n(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.c;
        int i2 = this.b;
        RectF rectF = new RectF(i, i, i + i2, i + i2);
        int i3 = this.a;
        canvas.drawRoundRect(rectF, i3, i3, paint);
        int i4 = this.b;
        RectF rectF2 = new RectF(f - i4, f2 - i4, f, f2);
        int i5 = this.a;
        canvas.drawRoundRect(rectF2, i5, i5, paint);
        int i6 = this.c;
        canvas.drawRect(new RectF(i6, i6 + this.a, f - this.b, f2), paint);
        int i7 = this.c;
        canvas.drawRect(new RectF(this.b + i7, i7, f, f2 - this.a), paint);
    }

    private void o(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.b;
        int i2 = this.c;
        RectF rectF = new RectF(f - i, i2, f, i2 + i);
        int i3 = this.a;
        canvas.drawRoundRect(rectF, i3, i3, paint);
        int i4 = this.c;
        int i5 = this.b;
        RectF rectF2 = new RectF(i4, f2 - i5, i4 + i5, f2);
        int i6 = this.a;
        canvas.drawRoundRect(rectF2, i6, i6, paint);
        int i7 = this.c;
        int i8 = this.a;
        canvas.drawRect(new RectF(i7, i7, f - i8, f2 - i8), paint);
        int i9 = this.c;
        int i10 = this.a;
        canvas.drawRect(new RectF(i9 + i10, i9 + i10, f, f2), paint);
    }

    public String toString() {
        return "RoundedTransformation(radius=" + this.a + ", margin=" + this.c + ", diameter=" + this.b + ", cornerType=" + this.d.name() + ")";
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof RoundedCornersTransformation) {
            RoundedCornersTransformation roundedCornersTransformation = (RoundedCornersTransformation) obj;
            if (roundedCornersTransformation.a == this.a && roundedCornersTransformation.b == this.b && roundedCornersTransformation.c == this.c && roundedCornersTransformation.d == this.d) {
                return true;
            }
        }
        return false;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return "jp.wasabeef.glide.transformations.RoundedCornersTransformation.1".hashCode() + (this.a * 10000) + (this.b * 1000) + (this.c * 100) + (this.d.ordinal() * 10);
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("jp.wasabeef.glide.transformations.RoundedCornersTransformation.1" + this.a + this.b + this.c + this.d).getBytes(CHARSET));
    }
}
