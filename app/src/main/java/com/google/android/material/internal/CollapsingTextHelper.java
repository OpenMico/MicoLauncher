package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.a;
import com.google.android.material.resources.CancelableFontCallback;
import com.google.android.material.resources.TextAppearance;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public final class CollapsingTextHelper {
    private static final boolean a;
    @NonNull
    private static final Paint b;
    private Typeface A;
    private CancelableFontCallback B;
    private CancelableFontCallback C;
    @Nullable
    private CharSequence D;
    @Nullable
    private CharSequence E;
    private boolean F;
    private boolean H;
    @Nullable
    private Bitmap I;
    private Paint J;
    private float K;
    private float L;
    private int[] M;
    private boolean N;
    private TimeInterpolator Q;
    private TimeInterpolator R;
    private float S;
    private float T;
    private float U;
    private ColorStateList V;
    private float W;
    private float X;
    private float Y;
    private ColorStateList Z;
    private float aa;
    private float ab;
    private StaticLayout ac;
    private float ad;
    private float ae;
    private float af;
    private CharSequence ag;
    private final View c;
    private boolean d;
    private float e;
    private boolean f;
    private float g;
    private int i;
    private ColorStateList q;
    private ColorStateList r;
    private float s;
    private float t;
    private float u;
    private float v;
    private float w;
    private float x;
    private Typeface y;
    private Typeface z;
    private int m = 16;
    private int n = 16;
    private float o = 15.0f;
    private float p = 15.0f;
    private boolean G = true;
    private int ah = 1;
    private float ai = 0.0f;
    private float aj = 1.0f;
    private int ak = a.a;
    @NonNull
    private final TextPaint O = new TextPaint(129);
    @NonNull
    private final TextPaint P = new TextPaint(this.O);
    @NonNull
    private final Rect k = new Rect();
    @NonNull
    private final Rect j = new Rect();
    @NonNull
    private final RectF l = new RectF();
    private float h = b();

    static {
        a = Build.VERSION.SDK_INT < 18;
        b = null;
        Paint paint = b;
        if (paint != null) {
            paint.setAntiAlias(true);
            b.setColor(-65281);
        }
    }

    public CollapsingTextHelper(View view) {
        this.c = view;
    }

    public void setTextSizeInterpolator(TimeInterpolator timeInterpolator) {
        this.R = timeInterpolator;
        recalculate();
    }

    public void setPositionInterpolator(TimeInterpolator timeInterpolator) {
        this.Q = timeInterpolator;
        recalculate();
    }

    public void setExpandedTextSize(float f) {
        if (this.o != f) {
            this.o = f;
            recalculate();
        }
    }

    public void setCollapsedTextSize(float f) {
        if (this.p != f) {
            this.p = f;
            recalculate();
        }
    }

    public void setCollapsedTextColor(ColorStateList colorStateList) {
        if (this.r != colorStateList) {
            this.r = colorStateList;
            recalculate();
        }
    }

    public void setExpandedTextColor(ColorStateList colorStateList) {
        if (this.q != colorStateList) {
            this.q = colorStateList;
            recalculate();
        }
    }

    public void setExpandedBounds(int i, int i2, int i3, int i4) {
        if (!a(this.j, i, i2, i3, i4)) {
            this.j.set(i, i2, i3, i4);
            this.N = true;
            a();
        }
    }

    public void setExpandedBounds(@NonNull Rect rect) {
        setExpandedBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setCollapsedBounds(int i, int i2, int i3, int i4) {
        if (!a(this.k, i, i2, i3, i4)) {
            this.k.set(i, i2, i3, i4);
            this.N = true;
            a();
        }
    }

    public void setCollapsedBounds(@NonNull Rect rect) {
        setCollapsedBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void getCollapsedTextActualBounds(@NonNull RectF rectF, int i, int i2) {
        this.F = a(this.D);
        rectF.left = a(i, i2);
        rectF.top = this.k.top;
        rectF.right = a(rectF, i, i2);
        rectF.bottom = this.k.top + getCollapsedTextHeight();
    }

    private float a(int i, int i2) {
        if (i2 == 17 || (i2 & 7) == 1) {
            return (i / 2.0f) - (calculateCollapsedTextWidth() / 2.0f);
        }
        return ((i2 & GravityCompat.END) == 8388613 || (i2 & 5) == 5) ? this.F ? this.k.left : this.k.right - calculateCollapsedTextWidth() : this.F ? this.k.right - calculateCollapsedTextWidth() : this.k.left;
    }

    private float a(@NonNull RectF rectF, int i, int i2) {
        if (i2 == 17 || (i2 & 7) == 1) {
            return (i / 2.0f) + (calculateCollapsedTextWidth() / 2.0f);
        }
        return ((i2 & GravityCompat.END) == 8388613 || (i2 & 5) == 5) ? this.F ? rectF.left + calculateCollapsedTextWidth() : this.k.right : this.F ? this.k.right : rectF.left + calculateCollapsedTextWidth();
    }

    public float calculateCollapsedTextWidth() {
        if (this.D == null) {
            return 0.0f;
        }
        b(this.P);
        TextPaint textPaint = this.P;
        CharSequence charSequence = this.D;
        return textPaint.measureText(charSequence, 0, charSequence.length());
    }

    public float getExpandedTextHeight() {
        a(this.P);
        return -this.P.ascent();
    }

    public float getExpandedTextFullHeight() {
        a(this.P);
        return (-this.P.ascent()) + this.P.descent();
    }

    public float getCollapsedTextHeight() {
        b(this.P);
        return -this.P.ascent();
    }

    public void setCurrentOffsetY(int i) {
        this.i = i;
    }

    public void setFadeModeStartFraction(float f) {
        this.g = f;
        this.h = b();
    }

    private float b() {
        float f = this.g;
        return f + ((1.0f - f) * 0.5f);
    }

    public void setFadeModeEnabled(boolean z) {
        this.f = z;
    }

    private void a(@NonNull TextPaint textPaint) {
        textPaint.setTextSize(this.o);
        textPaint.setTypeface(this.z);
        if (Build.VERSION.SDK_INT >= 21) {
            textPaint.setLetterSpacing(this.ab);
        }
    }

    private void b(@NonNull TextPaint textPaint) {
        textPaint.setTextSize(this.p);
        textPaint.setTypeface(this.y);
        if (Build.VERSION.SDK_INT >= 21) {
            textPaint.setLetterSpacing(this.aa);
        }
    }

    void a() {
        this.d = this.k.width() > 0 && this.k.height() > 0 && this.j.width() > 0 && this.j.height() > 0;
    }

    public void setExpandedTextGravity(int i) {
        if (this.m != i) {
            this.m = i;
            recalculate();
        }
    }

    public int getExpandedTextGravity() {
        return this.m;
    }

    public void setCollapsedTextGravity(int i) {
        if (this.n != i) {
            this.n = i;
            recalculate();
        }
    }

    public int getCollapsedTextGravity() {
        return this.n;
    }

    public void setCollapsedTextAppearance(int i) {
        TextAppearance textAppearance = new TextAppearance(this.c.getContext(), i);
        if (textAppearance.textColor != null) {
            this.r = textAppearance.textColor;
        }
        if (textAppearance.textSize != 0.0f) {
            this.p = textAppearance.textSize;
        }
        if (textAppearance.shadowColor != null) {
            this.V = textAppearance.shadowColor;
        }
        this.T = textAppearance.shadowDx;
        this.U = textAppearance.shadowDy;
        this.S = textAppearance.shadowRadius;
        this.aa = textAppearance.letterSpacing;
        CancelableFontCallback cancelableFontCallback = this.C;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        this.C = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() { // from class: com.google.android.material.internal.CollapsingTextHelper.1
            @Override // com.google.android.material.resources.CancelableFontCallback.ApplyFont
            public void apply(Typeface typeface) {
                CollapsingTextHelper.this.setCollapsedTypeface(typeface);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.c.getContext(), this.C);
        recalculate();
    }

    public void setExpandedTextAppearance(int i) {
        TextAppearance textAppearance = new TextAppearance(this.c.getContext(), i);
        if (textAppearance.textColor != null) {
            this.q = textAppearance.textColor;
        }
        if (textAppearance.textSize != 0.0f) {
            this.o = textAppearance.textSize;
        }
        if (textAppearance.shadowColor != null) {
            this.Z = textAppearance.shadowColor;
        }
        this.X = textAppearance.shadowDx;
        this.Y = textAppearance.shadowDy;
        this.W = textAppearance.shadowRadius;
        this.ab = textAppearance.letterSpacing;
        CancelableFontCallback cancelableFontCallback = this.B;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        this.B = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() { // from class: com.google.android.material.internal.CollapsingTextHelper.2
            @Override // com.google.android.material.resources.CancelableFontCallback.ApplyFont
            public void apply(Typeface typeface) {
                CollapsingTextHelper.this.setExpandedTypeface(typeface);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.c.getContext(), this.B);
        recalculate();
    }

    public void setCollapsedTypeface(Typeface typeface) {
        if (a(typeface)) {
            recalculate();
        }
    }

    public void setExpandedTypeface(Typeface typeface) {
        if (b(typeface)) {
            recalculate();
        }
    }

    public void setTypefaces(Typeface typeface) {
        boolean a2 = a(typeface);
        boolean b2 = b(typeface);
        if (a2 || b2) {
            recalculate();
        }
    }

    private boolean a(Typeface typeface) {
        CancelableFontCallback cancelableFontCallback = this.C;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        if (this.y == typeface) {
            return false;
        }
        this.y = typeface;
        return true;
    }

    private boolean b(Typeface typeface) {
        CancelableFontCallback cancelableFontCallback = this.B;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        if (this.z == typeface) {
            return false;
        }
        this.z = typeface;
        return true;
    }

    public Typeface getCollapsedTypeface() {
        Typeface typeface = this.y;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    public Typeface getExpandedTypeface() {
        Typeface typeface = this.z;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    public void setExpansionFraction(float f) {
        float clamp = MathUtils.clamp(f, 0.0f, 1.0f);
        if (clamp != this.e) {
            this.e = clamp;
            c();
        }
    }

    public final boolean setState(int[] iArr) {
        this.M = iArr;
        if (!isStateful()) {
            return false;
        }
        recalculate();
        return true;
    }

    public final boolean isStateful() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = this.r;
        return (colorStateList2 != null && colorStateList2.isStateful()) || ((colorStateList = this.q) != null && colorStateList.isStateful());
    }

    public float getFadeModeThresholdFraction() {
        return this.h;
    }

    public float getExpansionFraction() {
        return this.e;
    }

    public float getCollapsedTextSize() {
        return this.p;
    }

    public float getExpandedTextSize() {
        return this.o;
    }

    public void setRtlTextDirectionHeuristicsEnabled(boolean z) {
        this.G = z;
    }

    public boolean isRtlTextDirectionHeuristicsEnabled() {
        return this.G;
    }

    private void c() {
        a(this.e);
    }

    private void a(float f) {
        float f2;
        c(f);
        if (!this.f) {
            this.w = a(this.u, this.v, f, this.Q);
            this.x = a(this.s, this.t, f, this.Q);
            f(a(this.o, this.p, f, this.R));
            f2 = f;
        } else if (f < this.h) {
            this.w = this.u;
            this.x = this.s;
            f(this.o);
            f2 = 0.0f;
        } else {
            this.w = this.v;
            this.x = this.t - Math.max(0, this.i);
            f(this.p);
            f2 = 1.0f;
        }
        d(1.0f - a(0.0f, 1.0f, 1.0f - f, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        e(a(1.0f, 0.0f, f, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        if (this.r != this.q) {
            this.O.setColor(a(d(), getCurrentCollapsedTextColor(), f2));
        } else {
            this.O.setColor(getCurrentCollapsedTextColor());
        }
        if (Build.VERSION.SDK_INT >= 21) {
            float f3 = this.aa;
            float f4 = this.ab;
            if (f3 != f4) {
                this.O.setLetterSpacing(a(f4, f3, f, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
            } else {
                this.O.setLetterSpacing(f3);
            }
        }
        this.O.setShadowLayer(a(this.W, this.S, f, null), a(this.X, this.T, f, null), a(this.Y, this.U, f, null), a(a(this.Z), a(this.V), f));
        if (this.f) {
            this.O.setAlpha((int) (b(f) * 255.0f));
        }
        ViewCompat.postInvalidateOnAnimation(this.c);
    }

    private float b(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        float f2 = this.h;
        if (f <= f2) {
            return AnimationUtils.lerp(1.0f, 0.0f, this.g, f2, f);
        }
        return AnimationUtils.lerp(0.0f, 1.0f, f2, 1.0f, f);
    }

    @ColorInt
    private int d() {
        return a(this.q);
    }

    @ColorInt
    public int getCurrentCollapsedTextColor() {
        return a(this.r);
    }

    @ColorInt
    private int a(@Nullable ColorStateList colorStateList) {
        if (colorStateList == null) {
            return 0;
        }
        int[] iArr = this.M;
        if (iArr != null) {
            return colorStateList.getColorForState(iArr, 0);
        }
        return colorStateList.getDefaultColor();
    }

    private void a(boolean z) {
        StaticLayout staticLayout;
        float f = this.L;
        a(this.p, z);
        CharSequence charSequence = this.E;
        if (!(charSequence == null || (staticLayout = this.ac) == null)) {
            this.ag = TextUtils.ellipsize(charSequence, this.O, staticLayout.getWidth(), TextUtils.TruncateAt.END);
        }
        CharSequence charSequence2 = this.ag;
        float f2 = 0.0f;
        float measureText = charSequence2 != null ? this.O.measureText(charSequence2, 0, charSequence2.length()) : 0.0f;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(this.n, this.F ? 1 : 0);
        int i = absoluteGravity & 112;
        if (i == 48) {
            this.t = this.k.top;
        } else if (i != 80) {
            this.t = this.k.centerY() - ((this.O.descent() - this.O.ascent()) / 2.0f);
        } else {
            this.t = this.k.bottom + this.O.ascent();
        }
        int i2 = absoluteGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i2 == 1) {
            this.v = this.k.centerX() - (measureText / 2.0f);
        } else if (i2 != 5) {
            this.v = this.k.left;
        } else {
            this.v = this.k.right - measureText;
        }
        a(this.o, z);
        StaticLayout staticLayout2 = this.ac;
        float height = staticLayout2 != null ? staticLayout2.getHeight() : 0.0f;
        CharSequence charSequence3 = this.E;
        float measureText2 = charSequence3 != null ? this.O.measureText(charSequence3, 0, charSequence3.length()) : 0.0f;
        StaticLayout staticLayout3 = this.ac;
        if (staticLayout3 != null && this.ah > 1) {
            measureText2 = staticLayout3.getWidth();
        }
        StaticLayout staticLayout4 = this.ac;
        if (staticLayout4 != null) {
            f2 = this.ah > 1 ? staticLayout4.getLineStart(0) : staticLayout4.getLineLeft(0);
        }
        this.af = f2;
        int absoluteGravity2 = GravityCompat.getAbsoluteGravity(this.m, this.F ? 1 : 0);
        int i3 = absoluteGravity2 & 112;
        if (i3 == 48) {
            this.s = this.j.top;
        } else if (i3 != 80) {
            this.s = this.j.centerY() - (height / 2.0f);
        } else {
            this.s = (this.j.bottom - height) + this.O.descent();
        }
        int i4 = absoluteGravity2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i4 == 1) {
            this.u = this.j.centerX() - (measureText2 / 2.0f);
        } else if (i4 != 5) {
            this.u = this.j.left;
        } else {
            this.u = this.j.right - measureText2;
        }
        h();
        f(f);
    }

    private void c(float f) {
        if (this.f) {
            this.l.set(f < this.h ? this.j : this.k);
            return;
        }
        this.l.left = a(this.j.left, this.k.left, f, this.Q);
        this.l.top = a(this.s, this.t, f, this.Q);
        this.l.right = a(this.j.right, this.k.right, f, this.Q);
        this.l.bottom = a(this.j.bottom, this.k.bottom, f, this.Q);
    }

    private void d(float f) {
        this.ad = f;
        ViewCompat.postInvalidateOnAnimation(this.c);
    }

    private void e(float f) {
        this.ae = f;
        ViewCompat.postInvalidateOnAnimation(this.c);
    }

    public void draw(@NonNull Canvas canvas) {
        int save = canvas.save();
        if (this.E != null && this.d) {
            boolean z = true;
            float lineStart = (this.w + (this.ah > 1 ? this.ac.getLineStart(0) : this.ac.getLineLeft(0))) - (this.af * 2.0f);
            this.O.setTextSize(this.L);
            float f = this.w;
            float f2 = this.x;
            if (!this.H || this.I == null) {
                z = false;
            }
            float f3 = this.K;
            if (f3 != 1.0f && !this.f) {
                canvas.scale(f3, f3, f, f2);
            }
            if (z) {
                canvas.drawBitmap(this.I, f, f2, this.J);
                canvas.restoreToCount(save);
                return;
            }
            if (!e() || (this.f && this.e <= this.h)) {
                canvas.translate(f, f2);
                this.ac.draw(canvas);
            } else {
                a(canvas, lineStart, f2);
            }
            canvas.restoreToCount(save);
        }
    }

    private boolean e() {
        return this.ah > 1 && (!this.F || this.f) && !this.H;
    }

    private void a(@NonNull Canvas canvas, float f, float f2) {
        int alpha = this.O.getAlpha();
        canvas.translate(f, f2);
        float f3 = alpha;
        this.O.setAlpha((int) (this.ae * f3));
        this.ac.draw(canvas);
        this.O.setAlpha((int) (this.ad * f3));
        int lineBaseline = this.ac.getLineBaseline(0);
        CharSequence charSequence = this.ag;
        float f4 = lineBaseline;
        canvas.drawText(charSequence, 0, charSequence.length(), 0.0f, f4, this.O);
        if (!this.f) {
            String trim = this.ag.toString().trim();
            String substring = trim.endsWith("…") ? trim.substring(0, trim.length() - 1) : trim;
            this.O.setAlpha(alpha);
            canvas.drawText(substring, 0, Math.min(this.ac.getLineEnd(0), substring.length()), 0.0f, f4, (Paint) this.O);
        }
    }

    private boolean a(@NonNull CharSequence charSequence) {
        boolean f = f();
        return this.G ? a(charSequence, f) : f;
    }

    private boolean f() {
        return ViewCompat.getLayoutDirection(this.c) == 1;
    }

    private boolean a(@NonNull CharSequence charSequence, boolean z) {
        return (z ? TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL : TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR).isRtl(charSequence, 0, charSequence.length());
    }

    private void f(float f) {
        g(f);
        this.H = a && this.K != 1.0f;
        if (this.H) {
            g();
        }
        ViewCompat.postInvalidateOnAnimation(this.c);
    }

    private void g(float f) {
        a(f, false);
    }

    private void a(float f, boolean z) {
        boolean z2;
        float f2;
        if (this.D != null) {
            float width = this.k.width();
            float width2 = this.j.width();
            boolean z3 = false;
            int i = 1;
            if (a(f, this.p)) {
                float f3 = this.p;
                this.K = 1.0f;
                Typeface typeface = this.A;
                Typeface typeface2 = this.y;
                if (typeface != typeface2) {
                    this.A = typeface2;
                    z2 = true;
                } else {
                    z2 = false;
                }
                f2 = f3;
            } else {
                f2 = this.o;
                Typeface typeface3 = this.A;
                Typeface typeface4 = this.z;
                if (typeface3 != typeface4) {
                    this.A = typeface4;
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (a(f, this.o)) {
                    this.K = 1.0f;
                } else {
                    this.K = f / this.o;
                }
                float f4 = this.p / this.o;
                float f5 = width2 * f4;
                if (z) {
                    width = width2;
                } else {
                    width = f5 > width ? Math.min(width / f4, width2) : width2;
                }
            }
            if (width > 0.0f) {
                z2 = this.L != f2 || this.N || z2;
                this.L = f2;
                this.N = false;
            }
            if (this.E == null || z2) {
                this.O.setTextSize(this.L);
                this.O.setTypeface(this.A);
                TextPaint textPaint = this.O;
                if (this.K != 1.0f) {
                    z3 = true;
                }
                textPaint.setLinearText(z3);
                this.F = a(this.D);
                if (e()) {
                    i = this.ah;
                }
                this.ac = a(i, width, this.F);
                this.E = this.ac.getText();
            }
        }
    }

    private StaticLayout a(int i, float f, boolean z) {
        StaticLayout staticLayout;
        try {
            staticLayout = a.a(this.D, this.O, (int) f).a(TextUtils.TruncateAt.END).b(z).a(Layout.Alignment.ALIGN_NORMAL).a(false).a(i).a(this.ai, this.aj).b(this.ak).a();
        } catch (a.C0077a e) {
            Log.e("CollapsingTextHelper", e.getCause().getMessage(), e);
            staticLayout = null;
        }
        return (StaticLayout) Preconditions.checkNotNull(staticLayout);
    }

    private void g() {
        if (this.I == null && !this.j.isEmpty() && !TextUtils.isEmpty(this.E)) {
            a(0.0f);
            int width = this.ac.getWidth();
            int height = this.ac.getHeight();
            if (width > 0 && height > 0) {
                this.I = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                this.ac.draw(new Canvas(this.I));
                if (this.J == null) {
                    this.J = new Paint(3);
                }
            }
        }
    }

    public void recalculate() {
        recalculate(false);
    }

    public void recalculate(boolean z) {
        if ((this.c.getHeight() > 0 && this.c.getWidth() > 0) || z) {
            a(z);
            c();
        }
    }

    public void setText(@Nullable CharSequence charSequence) {
        if (charSequence == null || !TextUtils.equals(this.D, charSequence)) {
            this.D = charSequence;
            this.E = null;
            h();
            recalculate();
        }
    }

    @Nullable
    public CharSequence getText() {
        return this.D;
    }

    private void h() {
        Bitmap bitmap = this.I;
        if (bitmap != null) {
            bitmap.recycle();
            this.I = null;
        }
    }

    public void setMaxLines(int i) {
        if (i != this.ah) {
            this.ah = i;
            h();
            recalculate();
        }
    }

    public int getMaxLines() {
        return this.ah;
    }

    public int getLineCount() {
        StaticLayout staticLayout = this.ac;
        if (staticLayout != null) {
            return staticLayout.getLineCount();
        }
        return 0;
    }

    @RequiresApi(23)
    public void setLineSpacingAdd(float f) {
        this.ai = f;
    }

    @RequiresApi(23)
    public float getLineSpacingAdd() {
        return this.ac.getSpacingAdd();
    }

    @RequiresApi(23)
    public void setLineSpacingMultiplier(@FloatRange(from = 0.0d) float f) {
        this.aj = f;
    }

    @RequiresApi(23)
    public float getLineSpacingMultiplier() {
        return this.ac.getSpacingMultiplier();
    }

    @RequiresApi(23)
    public void setHyphenationFrequency(int i) {
        this.ak = i;
    }

    @RequiresApi(23)
    public int getHyphenationFrequency() {
        return this.ak;
    }

    private static boolean a(float f, float f2) {
        return Math.abs(f - f2) < 0.001f;
    }

    public ColorStateList getExpandedTextColor() {
        return this.q;
    }

    public ColorStateList getCollapsedTextColor() {
        return this.r;
    }

    private static int a(int i, int i2, float f) {
        float f2 = 1.0f - f;
        return Color.argb((int) ((Color.alpha(i) * f2) + (Color.alpha(i2) * f)), (int) ((Color.red(i) * f2) + (Color.red(i2) * f)), (int) ((Color.green(i) * f2) + (Color.green(i2) * f)), (int) ((Color.blue(i) * f2) + (Color.blue(i2) * f)));
    }

    private static float a(float f, float f2, float f3, @Nullable TimeInterpolator timeInterpolator) {
        if (timeInterpolator != null) {
            f3 = timeInterpolator.getInterpolation(f3);
        }
        return AnimationUtils.lerp(f, f2, f3);
    }

    private static boolean a(@NonNull Rect rect, int i, int i2, int i3, int i4) {
        return rect.left == i && rect.top == i2 && rect.right == i3 && rect.bottom == i4;
    }
}
