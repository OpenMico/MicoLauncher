package androidx.cardview.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.cardview.R;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RoundRectDrawableWithShadow.java */
/* loaded from: classes.dex */
public class g extends Drawable {
    static a a;
    private static final double b = Math.cos(Math.toRadians(45.0d));
    private final int c;
    private float h;
    private Path i;
    private float j;
    private float k;
    private float l;
    private ColorStateList m;
    private final int o;
    private final int p;
    private boolean n = true;
    private boolean q = true;
    private boolean r = false;
    private Paint d = new Paint(5);
    private Paint e = new Paint(5);
    private final RectF g = new RectF();
    private Paint f = new Paint(this.e);

    /* compiled from: RoundRectDrawableWithShadow.java */
    /* loaded from: classes.dex */
    interface a {
        void a(Canvas canvas, RectF rectF, float f, Paint paint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public g(Resources resources, ColorStateList colorStateList, float f, float f2, float f3) {
        this.o = resources.getColor(R.color.cardview_shadow_start_color);
        this.p = resources.getColor(R.color.cardview_shadow_end_color);
        this.c = resources.getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow);
        b(colorStateList);
        this.e.setStyle(Paint.Style.FILL);
        this.h = (int) (f + 0.5f);
        this.f.setAntiAlias(false);
        a(f2, f3);
    }

    private void b(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.m = colorStateList;
        this.d.setColor(this.m.getColorForState(getState(), this.m.getDefaultColor()));
    }

    private int d(float f) {
        int i = (int) (f + 0.5f);
        return i % 2 == 1 ? i - 1 : i;
    }

    public void a(boolean z) {
        this.q = z;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.d.setAlpha(i);
        this.e.setAlpha(i);
        this.f.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.n = true;
    }

    private void a(float f, float f2) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("Invalid shadow size " + f + ". Must be >= 0");
        } else if (f2 >= 0.0f) {
            float d = d(f);
            float d2 = d(f2);
            if (d > d2) {
                if (!this.r) {
                    this.r = true;
                }
                d = d2;
            }
            if (this.l != d || this.j != d2) {
                this.l = d;
                this.j = d2;
                this.k = (int) ((d * 1.5f) + this.c + 0.5f);
                this.n = true;
                invalidateSelf();
            }
        } else {
            throw new IllegalArgumentException("Invalid max shadow size " + f2 + ". Must be >= 0");
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        int ceil = (int) Math.ceil(a(this.j, this.h, this.q));
        int ceil2 = (int) Math.ceil(b(this.j, this.h, this.q));
        rect.set(ceil2, ceil, ceil2, ceil);
        return true;
    }

    public static float a(float f, float f2, boolean z) {
        return z ? (float) ((f * 1.5f) + ((1.0d - b) * f2)) : f * 1.5f;
    }

    public static float b(float f, float f2, boolean z) {
        return z ? (float) (f + ((1.0d - b) * f2)) : f;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        ColorStateList colorStateList = this.m;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        if (this.d.getColor() == colorForState) {
            return false;
        }
        this.d.setColor(colorForState);
        this.n = true;
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList = this.m;
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.d.setColorFilter(colorFilter);
    }

    public void a(float f) {
        if (f >= 0.0f) {
            float f2 = (int) (f + 0.5f);
            if (this.h != f2) {
                this.h = f2;
                this.n = true;
                invalidateSelf();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Invalid radius " + f + ". Must be >= 0");
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.n) {
            b(getBounds());
            this.n = false;
        }
        canvas.translate(0.0f, this.l / 2.0f);
        a(canvas);
        canvas.translate(0.0f, (-this.l) / 2.0f);
        a.a(canvas, this.g, this.h, this.d);
    }

    private void a(Canvas canvas) {
        float f = this.h;
        float f2 = (-f) - this.k;
        float f3 = f + this.c + (this.l / 2.0f);
        float f4 = f3 * 2.0f;
        boolean z = this.g.width() - f4 > 0.0f;
        boolean z2 = this.g.height() - f4 > 0.0f;
        int save = canvas.save();
        canvas.translate(this.g.left + f3, this.g.top + f3);
        canvas.drawPath(this.i, this.e);
        if (z) {
            canvas.drawRect(0.0f, f2, this.g.width() - f4, -this.h, this.f);
        }
        canvas.restoreToCount(save);
        int save2 = canvas.save();
        canvas.translate(this.g.right - f3, this.g.bottom - f3);
        canvas.rotate(180.0f);
        canvas.drawPath(this.i, this.e);
        if (z) {
            canvas.drawRect(0.0f, f2, this.g.width() - f4, (-this.h) + this.k, this.f);
        }
        canvas.restoreToCount(save2);
        int save3 = canvas.save();
        canvas.translate(this.g.left + f3, this.g.bottom - f3);
        canvas.rotate(270.0f);
        canvas.drawPath(this.i, this.e);
        if (z2) {
            canvas.drawRect(0.0f, f2, this.g.height() - f4, -this.h, this.f);
        }
        canvas.restoreToCount(save3);
        int save4 = canvas.save();
        canvas.translate(this.g.right - f3, this.g.top + f3);
        canvas.rotate(90.0f);
        canvas.drawPath(this.i, this.e);
        if (z2) {
            canvas.drawRect(0.0f, f2, this.g.height() - f4, -this.h, this.f);
        }
        canvas.restoreToCount(save4);
    }

    private void g() {
        float f = this.h;
        RectF rectF = new RectF(-f, -f, f, f);
        RectF rectF2 = new RectF(rectF);
        float f2 = this.k;
        rectF2.inset(-f2, -f2);
        Path path = this.i;
        if (path == null) {
            this.i = new Path();
        } else {
            path.reset();
        }
        this.i.setFillType(Path.FillType.EVEN_ODD);
        this.i.moveTo(-this.h, 0.0f);
        this.i.rLineTo(-this.k, 0.0f);
        this.i.arcTo(rectF2, 180.0f, 90.0f, false);
        this.i.arcTo(rectF, 270.0f, -90.0f, false);
        this.i.close();
        float f3 = this.h;
        float f4 = this.k;
        Paint paint = this.e;
        float f5 = f3 + f4;
        int i = this.o;
        paint.setShader(new RadialGradient(0.0f, 0.0f, f5, new int[]{i, i, this.p}, new float[]{0.0f, f3 / (f3 + f4), 1.0f}, Shader.TileMode.CLAMP));
        Paint paint2 = this.f;
        float f6 = this.h;
        float f7 = this.k;
        int i2 = this.o;
        paint2.setShader(new LinearGradient(0.0f, (-f6) + f7, 0.0f, (-f6) - f7, new int[]{i2, i2, this.p}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP));
        this.f.setAntiAlias(false);
    }

    private void b(Rect rect) {
        float f = this.j * 1.5f;
        this.g.set(rect.left + this.j, rect.top + f, rect.right - this.j, rect.bottom - f);
        g();
    }

    public float a() {
        return this.h;
    }

    public void a(Rect rect) {
        getPadding(rect);
    }

    public void b(float f) {
        a(f, this.j);
    }

    public void c(float f) {
        a(this.l, f);
    }

    public float b() {
        return this.l;
    }

    public float c() {
        return this.j;
    }

    public float d() {
        float f = this.j;
        return (Math.max(f, this.h + this.c + (f / 2.0f)) * 2.0f) + ((this.j + this.c) * 2.0f);
    }

    public float e() {
        float f = this.j;
        return (Math.max(f, this.h + this.c + ((f * 1.5f) / 2.0f)) * 2.0f) + (((this.j * 1.5f) + this.c) * 2.0f);
    }

    public void a(@Nullable ColorStateList colorStateList) {
        b(colorStateList);
        invalidateSelf();
    }

    public ColorStateList f() {
        return this.m;
    }
}
