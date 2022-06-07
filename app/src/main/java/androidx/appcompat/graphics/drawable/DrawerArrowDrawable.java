package androidx.appcompat.graphics.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.graphics.drawable.DrawableCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class DrawerArrowDrawable extends Drawable {
    public static final int ARROW_DIRECTION_END = 3;
    public static final int ARROW_DIRECTION_LEFT = 0;
    public static final int ARROW_DIRECTION_RIGHT = 1;
    public static final int ARROW_DIRECTION_START = 2;
    private static final float b = (float) Math.toRadians(45.0d);
    private float c;
    private float d;
    private float e;
    private float f;
    private boolean g;
    private final int i;
    private float k;
    private float l;
    private final Paint a = new Paint();
    private final Path h = new Path();
    private boolean j = false;
    private int m = 2;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface ArrowDirection {
    }

    private static float a(float f, float f2, float f3) {
        return f + ((f2 - f) * f3);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public DrawerArrowDrawable(Context context) {
        this.a.setStyle(Paint.Style.STROKE);
        this.a.setStrokeJoin(Paint.Join.MITER);
        this.a.setStrokeCap(Paint.Cap.BUTT);
        this.a.setAntiAlias(true);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, R.style.Base_Widget_AppCompat_DrawerArrowToggle);
        setColor(obtainStyledAttributes.getColor(R.styleable.DrawerArrowToggle_color, 0));
        setBarThickness(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_thickness, 0.0f));
        setSpinEnabled(obtainStyledAttributes.getBoolean(R.styleable.DrawerArrowToggle_spinBars, true));
        setGapSize(Math.round(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0f)));
        this.i = obtainStyledAttributes.getDimensionPixelSize(R.styleable.DrawerArrowToggle_drawableSize, 0);
        this.d = Math.round(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_barLength, 0.0f));
        this.c = Math.round(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_arrowHeadLength, 0.0f));
        this.e = obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_arrowShaftLength, 0.0f);
        obtainStyledAttributes.recycle();
    }

    public void setArrowHeadLength(float f) {
        if (this.c != f) {
            this.c = f;
            invalidateSelf();
        }
    }

    public float getArrowHeadLength() {
        return this.c;
    }

    public void setArrowShaftLength(float f) {
        if (this.e != f) {
            this.e = f;
            invalidateSelf();
        }
    }

    public float getArrowShaftLength() {
        return this.e;
    }

    public float getBarLength() {
        return this.d;
    }

    public void setBarLength(float f) {
        if (this.d != f) {
            this.d = f;
            invalidateSelf();
        }
    }

    public void setColor(@ColorInt int i) {
        if (i != this.a.getColor()) {
            this.a.setColor(i);
            invalidateSelf();
        }
    }

    @ColorInt
    public int getColor() {
        return this.a.getColor();
    }

    public void setBarThickness(float f) {
        if (this.a.getStrokeWidth() != f) {
            this.a.setStrokeWidth(f);
            this.l = (float) ((f / 2.0f) * Math.cos(b));
            invalidateSelf();
        }
    }

    public float getBarThickness() {
        return this.a.getStrokeWidth();
    }

    public float getGapSize() {
        return this.f;
    }

    public void setGapSize(float f) {
        if (f != this.f) {
            this.f = f;
            invalidateSelf();
        }
    }

    public void setDirection(int i) {
        if (i != this.m) {
            this.m = i;
            invalidateSelf();
        }
    }

    public boolean isSpinEnabled() {
        return this.g;
    }

    public void setSpinEnabled(boolean z) {
        if (this.g != z) {
            this.g = z;
            invalidateSelf();
        }
    }

    public int getDirection() {
        return this.m;
    }

    public void setVerticalMirror(boolean z) {
        if (this.j != z) {
            this.j = z;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int i = this.m;
        boolean z = false;
        if (i != 3) {
            switch (i) {
                case 0:
                    break;
                case 1:
                    z = true;
                    break;
                default:
                    if (DrawableCompat.getLayoutDirection(this) == 1) {
                        z = true;
                        break;
                    }
                    break;
            }
        } else if (DrawableCompat.getLayoutDirection(this) == 0) {
            z = true;
        }
        float f = this.c;
        float a = a(this.d, (float) Math.sqrt(f * f * 2.0f), this.k);
        float a2 = a(this.d, this.e, this.k);
        float round = Math.round(a(0.0f, this.l, this.k));
        float a3 = a(0.0f, b, this.k);
        float a4 = a(z ? 0.0f : -180.0f, z ? 180.0f : 0.0f, this.k);
        double d = a;
        double d2 = a3;
        float round2 = (float) Math.round(Math.cos(d2) * d);
        float round3 = (float) Math.round(d * Math.sin(d2));
        this.h.rewind();
        float a5 = a(this.f + this.a.getStrokeWidth(), -this.l, this.k);
        float f2 = (-a2) / 2.0f;
        this.h.moveTo(f2 + round, 0.0f);
        this.h.rLineTo(a2 - (round * 2.0f), 0.0f);
        this.h.moveTo(f2, a5);
        this.h.rLineTo(round2, round3);
        this.h.moveTo(f2, -a5);
        this.h.rLineTo(round2, -round3);
        this.h.close();
        canvas.save();
        float strokeWidth = this.a.getStrokeWidth();
        float height = bounds.height() - (3.0f * strokeWidth);
        float f3 = this.f;
        canvas.translate(bounds.centerX(), ((((int) (height - (2.0f * f3))) / 4) * 2) + (strokeWidth * 1.5f) + f3);
        if (this.g) {
            canvas.rotate(a4 * (this.j ^ z ? -1 : 1));
        } else if (z) {
            canvas.rotate(180.0f);
        }
        canvas.drawPath(this.h, this.a);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.a.getAlpha()) {
            this.a.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.a.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.i;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.i;
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.k;
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        if (this.k != f) {
            this.k = f;
            invalidateSelf();
        }
    }

    public final Paint getPaint() {
        return this.a;
    }
}
