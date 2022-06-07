package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.fastjson.asm.Opcodes;

/* loaded from: classes.dex */
public abstract class RoundedBitmapDrawable extends Drawable {
    final Bitmap a;
    private int c;
    private final BitmapShader f;
    private float h;
    private boolean k;
    private int l;
    private int m;
    private int d = 119;
    private final Paint e = new Paint(3);
    private final Matrix g = new Matrix();
    final Rect b = new Rect();
    private final RectF i = new RectF();
    private boolean j = true;

    private static boolean a(float f) {
        return f > 0.05f;
    }

    @NonNull
    public final Paint getPaint() {
        return this.e;
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.a;
    }

    private void b() {
        this.l = this.a.getScaledWidth(this.c);
        this.m = this.a.getScaledHeight(this.c);
    }

    public void setTargetDensity(@NonNull Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(@NonNull DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }

    public void setTargetDensity(int i) {
        if (this.c != i) {
            if (i == 0) {
                i = Opcodes.IF_ICMPNE;
            }
            this.c = i;
            if (this.a != null) {
                b();
            }
            invalidateSelf();
        }
    }

    public int getGravity() {
        return this.d;
    }

    public void setGravity(int i) {
        if (this.d != i) {
            this.d = i;
            this.j = true;
            invalidateSelf();
        }
    }

    public void setMipMap(boolean z) {
        throw new UnsupportedOperationException();
    }

    public boolean hasMipMap() {
        throw new UnsupportedOperationException();
    }

    public void setAntiAlias(boolean z) {
        this.e.setAntiAlias(z);
        invalidateSelf();
    }

    public boolean hasAntiAlias() {
        return this.e.isAntiAlias();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.e.setFilterBitmap(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.e.setDither(z);
        invalidateSelf();
    }

    void a(int i, int i2, int i3, Rect rect, Rect rect2) {
        throw new UnsupportedOperationException();
    }

    void a() {
        if (this.j) {
            if (this.k) {
                int min = Math.min(this.l, this.m);
                a(this.d, min, min, getBounds(), this.b);
                int min2 = Math.min(this.b.width(), this.b.height());
                this.b.inset(Math.max(0, (this.b.width() - min2) / 2), Math.max(0, (this.b.height() - min2) / 2));
                this.h = min2 * 0.5f;
            } else {
                a(this.d, this.l, this.m, getBounds(), this.b);
            }
            this.i.set(this.b);
            if (this.f != null) {
                this.g.setTranslate(this.i.left, this.i.top);
                this.g.preScale(this.i.width() / this.a.getWidth(), this.i.height() / this.a.getHeight());
                this.f.setLocalMatrix(this.g);
                this.e.setShader(this.f);
            }
            this.j = false;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap = this.a;
        if (bitmap != null) {
            a();
            if (this.e.getShader() == null) {
                canvas.drawBitmap(bitmap, (Rect) null, this.b, this.e);
                return;
            }
            RectF rectF = this.i;
            float f = this.h;
            canvas.drawRoundRect(rectF, f, f, this.e);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.e.getAlpha()) {
            this.e.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.e.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.e.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.e.getColorFilter();
    }

    public void setCircular(boolean z) {
        this.k = z;
        this.j = true;
        if (z) {
            c();
            this.e.setShader(this.f);
            invalidateSelf();
            return;
        }
        setCornerRadius(0.0f);
    }

    private void c() {
        this.h = Math.min(this.m, this.l) / 2;
    }

    public boolean isCircular() {
        return this.k;
    }

    public void setCornerRadius(float f) {
        if (this.h != f) {
            this.k = false;
            if (a(f)) {
                this.e.setShader(this.f);
            } else {
                this.e.setShader(null);
            }
            this.h = f;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.k) {
            c();
        }
        this.j = true;
    }

    public float getCornerRadius() {
        return this.h;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.l;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.m;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Bitmap bitmap;
        return (this.d != 119 || this.k || (bitmap = this.a) == null || bitmap.hasAlpha() || this.e.getAlpha() < 255 || a(this.h)) ? -3 : -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        this.c = Opcodes.IF_ICMPNE;
        if (resources != null) {
            this.c = resources.getDisplayMetrics().densityDpi;
        }
        this.a = bitmap;
        if (this.a != null) {
            b();
            this.f = new BitmapShader(this.a, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            return;
        }
        this.m = -1;
        this.l = -1;
        this.f = null;
    }
}
