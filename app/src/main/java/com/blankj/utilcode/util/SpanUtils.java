package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public final class SpanUtils {
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 2;
    public static final int ALIGN_TOP = 3;
    private static final String a = System.getProperty("line.separator");
    private boolean A;
    private String B;
    private Typeface C;
    private Layout.Alignment D;
    private int E;
    private ClickableSpan F;
    private String G;
    private float H;
    private BlurMaskFilter.Blur I;
    private Shader J;
    private float K;
    private float L;
    private float M;
    private int N;
    private Object[] O;
    private Bitmap P;
    private Drawable Q;
    private Uri R;
    private int S;
    private int T;
    private int U;
    private int V;
    private g W;
    private boolean X;
    private int Y;
    private final int Z;
    private final int aa;
    private final int ab;
    private TextView b;
    private CharSequence c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private boolean r;
    private float s;
    private float t;
    private boolean u;
    private boolean v;
    private boolean w;
    private boolean x;
    private boolean y;
    private boolean z;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Align {
    }

    public static SpanUtils with(TextView textView) {
        return new SpanUtils(textView);
    }

    private SpanUtils(TextView textView) {
        this();
        this.b = textView;
    }

    public SpanUtils() {
        this.Z = 0;
        this.aa = 1;
        this.ab = 2;
        this.W = new g();
        this.c = "";
        this.Y = -1;
        a();
    }

    private void a() {
        this.d = 33;
        this.e = -16777217;
        this.f = -16777217;
        this.g = -1;
        this.i = -16777217;
        this.l = -1;
        this.n = -16777217;
        this.q = -1;
        this.s = -1.0f;
        this.t = -1.0f;
        this.u = false;
        this.v = false;
        this.w = false;
        this.x = false;
        this.y = false;
        this.z = false;
        this.A = false;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = -1;
        this.F = null;
        this.G = null;
        this.H = -1.0f;
        this.J = null;
        this.K = -1.0f;
        this.O = null;
        this.P = null;
        this.Q = null;
        this.R = null;
        this.S = -1;
        this.U = -1;
    }

    public SpanUtils setFlag(int i2) {
        this.d = i2;
        return this;
    }

    public SpanUtils setForegroundColor(@ColorInt int i2) {
        this.e = i2;
        return this;
    }

    public SpanUtils setBackgroundColor(@ColorInt int i2) {
        this.f = i2;
        return this;
    }

    public SpanUtils setLineHeight(@IntRange(from = 0) int i2) {
        return setLineHeight(i2, 2);
    }

    public SpanUtils setLineHeight(@IntRange(from = 0) int i2, int i3) {
        this.g = i2;
        this.h = i3;
        return this;
    }

    public SpanUtils setQuoteColor(@ColorInt int i2) {
        return setQuoteColor(i2, 2, 2);
    }

    public SpanUtils setQuoteColor(@ColorInt int i2, @IntRange(from = 1) int i3, @IntRange(from = 0) int i4) {
        this.i = i2;
        this.j = i3;
        this.k = i4;
        return this;
    }

    public SpanUtils setLeadingMargin(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        this.l = i2;
        this.m = i3;
        return this;
    }

    public SpanUtils setBullet(@IntRange(from = 0) int i2) {
        return setBullet(0, 3, i2);
    }

    public SpanUtils setBullet(@ColorInt int i2, @IntRange(from = 0) int i3, @IntRange(from = 0) int i4) {
        this.n = i2;
        this.o = i3;
        this.p = i4;
        return this;
    }

    public SpanUtils setFontSize(@IntRange(from = 0) int i2) {
        return setFontSize(i2, false);
    }

    public SpanUtils setFontSize(@IntRange(from = 0) int i2, boolean z) {
        this.q = i2;
        this.r = z;
        return this;
    }

    public SpanUtils setFontProportion(float f2) {
        this.s = f2;
        return this;
    }

    public SpanUtils setFontXProportion(float f2) {
        this.t = f2;
        return this;
    }

    public SpanUtils setStrikethrough() {
        this.u = true;
        return this;
    }

    public SpanUtils setUnderline() {
        this.v = true;
        return this;
    }

    public SpanUtils setSuperscript() {
        this.w = true;
        return this;
    }

    public SpanUtils setSubscript() {
        this.x = true;
        return this;
    }

    public SpanUtils setBold() {
        this.y = true;
        return this;
    }

    public SpanUtils setItalic() {
        this.z = true;
        return this;
    }

    public SpanUtils setBoldItalic() {
        this.A = true;
        return this;
    }

    public SpanUtils setFontFamily(@NonNull String str) {
        if (str != null) {
            this.B = str;
            return this;
        }
        throw new NullPointerException("Argument 'fontFamily' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils setTypeface(@NonNull Typeface typeface) {
        if (typeface != null) {
            this.C = typeface;
            return this;
        }
        throw new NullPointerException("Argument 'typeface' of type Typeface (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils setHorizontalAlign(@NonNull Layout.Alignment alignment) {
        if (alignment != null) {
            this.D = alignment;
            return this;
        }
        throw new NullPointerException("Argument 'alignment' of type Alignment (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils setVerticalAlign(int i2) {
        this.E = i2;
        return this;
    }

    public SpanUtils setClickSpan(@NonNull ClickableSpan clickableSpan) {
        if (clickableSpan != null) {
            b();
            this.F = clickableSpan;
            return this;
        }
        throw new NullPointerException("Argument 'clickSpan' of type ClickableSpan (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils setClickSpan(@ColorInt final int i2, final boolean z, final View.OnClickListener onClickListener) {
        b();
        this.F = new ClickableSpan() { // from class: com.blankj.utilcode.util.SpanUtils.1
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(@NonNull TextPaint textPaint) {
                if (textPaint != null) {
                    textPaint.setColor(i2);
                    textPaint.setUnderlineText(z);
                    return;
                }
                throw new NullPointerException("Argument 'paint' of type TextPaint (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(@NonNull View view) {
                if (view != null) {
                    View.OnClickListener onClickListener2 = onClickListener;
                    if (onClickListener2 != null) {
                        onClickListener2.onClick(view);
                        return;
                    }
                    return;
                }
                throw new NullPointerException("Argument 'widget' of type View (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        };
        return this;
    }

    public SpanUtils setUrl(@NonNull String str) {
        if (str != null) {
            b();
            this.G = str;
            return this;
        }
        throw new NullPointerException("Argument 'url' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private void b() {
        TextView textView = this.b;
        if (textView != null && textView.getMovementMethod() == null) {
            this.b.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public SpanUtils setBlur(@FloatRange(from = 0.0d, fromInclusive = false) float f2, BlurMaskFilter.Blur blur) {
        this.H = f2;
        this.I = blur;
        return this;
    }

    public SpanUtils setShader(@NonNull Shader shader) {
        if (shader != null) {
            this.J = shader;
            return this;
        }
        throw new NullPointerException("Argument 'shader' of type Shader (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils setShadow(@FloatRange(from = 0.0d, fromInclusive = false) float f2, float f3, float f4, int i2) {
        this.K = f2;
        this.L = f3;
        this.M = f4;
        this.N = i2;
        return this;
    }

    public SpanUtils setSpans(@NonNull Object... objArr) {
        if (objArr != null) {
            if (objArr.length > 0) {
                this.O = objArr;
            }
            return this;
        }
        throw new NullPointerException("Argument 'spans' of type Object[] (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils append(@NonNull CharSequence charSequence) {
        if (charSequence != null) {
            a(0);
            this.c = charSequence;
            return this;
        }
        throw new NullPointerException("Argument 'text' of type CharSequence (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils appendLine() {
        a(0);
        this.c = a;
        return this;
    }

    public SpanUtils appendLine(@NonNull CharSequence charSequence) {
        if (charSequence != null) {
            a(0);
            this.c = ((Object) charSequence) + a;
            return this;
        }
        throw new NullPointerException("Argument 'text' of type CharSequence (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils appendImage(@NonNull Bitmap bitmap) {
        if (bitmap != null) {
            return appendImage(bitmap, 0);
        }
        throw new NullPointerException("Argument 'bitmap' of type Bitmap (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils appendImage(@NonNull Bitmap bitmap, int i2) {
        if (bitmap != null) {
            a(1);
            this.P = bitmap;
            this.T = i2;
            return this;
        }
        throw new NullPointerException("Argument 'bitmap' of type Bitmap (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils appendImage(@NonNull Drawable drawable) {
        if (drawable != null) {
            return appendImage(drawable, 0);
        }
        throw new NullPointerException("Argument 'drawable' of type Drawable (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils appendImage(@NonNull Drawable drawable, int i2) {
        if (drawable != null) {
            a(1);
            this.Q = drawable;
            this.T = i2;
            return this;
        }
        throw new NullPointerException("Argument 'drawable' of type Drawable (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils appendImage(@NonNull Uri uri) {
        if (uri != null) {
            return appendImage(uri, 0);
        }
        throw new NullPointerException("Argument 'uri' of type Uri (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils appendImage(@NonNull Uri uri, int i2) {
        if (uri != null) {
            a(1);
            this.R = uri;
            this.T = i2;
            return this;
        }
        throw new NullPointerException("Argument 'uri' of type Uri (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SpanUtils appendImage(@DrawableRes int i2) {
        return appendImage(i2, 0);
    }

    public SpanUtils appendImage(@DrawableRes int i2, int i3) {
        a(1);
        this.S = i2;
        this.T = i3;
        return this;
    }

    public SpanUtils appendSpace(@IntRange(from = 0) int i2) {
        return appendSpace(i2, 0);
    }

    public SpanUtils appendSpace(@IntRange(from = 0) int i2, @ColorInt int i3) {
        a(2);
        this.U = i2;
        this.V = i3;
        return this;
    }

    private void a(int i2) {
        c();
        this.Y = i2;
    }

    public SpannableStringBuilder get() {
        return this.W;
    }

    public SpannableStringBuilder create() {
        c();
        TextView textView = this.b;
        if (textView != null) {
            textView.setText(this.W);
        }
        this.X = true;
        return this.W;
    }

    private void c() {
        if (!this.X) {
            int i2 = this.Y;
            if (i2 == 0) {
                d();
            } else if (i2 == 1) {
                e();
            } else if (i2 == 2) {
                f();
            }
            a();
        }
    }

    private void d() {
        if (this.c.length() != 0) {
            int length = this.W.length();
            if (length == 0 && this.g != -1) {
                this.W.append((CharSequence) Character.toString((char) 2)).append((CharSequence) "\n").setSpan(new AbsoluteSizeSpan(0), 0, 2, 33);
                length = 2;
            }
            this.W.append(this.c);
            int length2 = this.W.length();
            int i2 = this.E;
            if (i2 != -1) {
                this.W.setSpan(new k(i2), length, length2, this.d);
            }
            int i3 = this.e;
            if (i3 != -16777217) {
                this.W.setSpan(new ForegroundColorSpan(i3), length, length2, this.d);
            }
            int i4 = this.f;
            if (i4 != -16777217) {
                this.W.setSpan(new BackgroundColorSpan(i4), length, length2, this.d);
            }
            int i5 = this.l;
            if (i5 != -1) {
                this.W.setSpan(new LeadingMarginSpan.Standard(i5, this.m), length, length2, this.d);
            }
            int i6 = this.i;
            if (i6 != -16777217) {
                this.W.setSpan(new e(i6, this.j, this.k), length, length2, this.d);
            }
            int i7 = this.n;
            if (i7 != -16777217) {
                this.W.setSpan(new a(i7, this.o, this.p), length, length2, this.d);
            }
            int i8 = this.q;
            if (i8 != -1) {
                this.W.setSpan(new AbsoluteSizeSpan(i8, this.r), length, length2, this.d);
            }
            float f2 = this.s;
            if (f2 != -1.0f) {
                this.W.setSpan(new RelativeSizeSpan(f2), length, length2, this.d);
            }
            float f3 = this.t;
            if (f3 != -1.0f) {
                this.W.setSpan(new ScaleXSpan(f3), length, length2, this.d);
            }
            int i9 = this.g;
            if (i9 != -1) {
                this.W.setSpan(new d(i9, this.h), length, length2, this.d);
            }
            if (this.u) {
                this.W.setSpan(new StrikethroughSpan(), length, length2, this.d);
            }
            if (this.v) {
                this.W.setSpan(new UnderlineSpan(), length, length2, this.d);
            }
            if (this.w) {
                this.W.setSpan(new SuperscriptSpan(), length, length2, this.d);
            }
            if (this.x) {
                this.W.setSpan(new SubscriptSpan(), length, length2, this.d);
            }
            if (this.y) {
                this.W.setSpan(new StyleSpan(1), length, length2, this.d);
            }
            if (this.z) {
                this.W.setSpan(new StyleSpan(2), length, length2, this.d);
            }
            if (this.A) {
                this.W.setSpan(new StyleSpan(3), length, length2, this.d);
            }
            String str = this.B;
            if (str != null) {
                this.W.setSpan(new TypefaceSpan(str), length, length2, this.d);
            }
            Typeface typeface = this.C;
            if (typeface != null) {
                this.W.setSpan(new f(typeface), length, length2, this.d);
            }
            Layout.Alignment alignment = this.D;
            if (alignment != null) {
                this.W.setSpan(new AlignmentSpan.Standard(alignment), length, length2, this.d);
            }
            ClickableSpan clickableSpan = this.F;
            if (clickableSpan != null) {
                this.W.setSpan(clickableSpan, length, length2, this.d);
            }
            String str2 = this.G;
            if (str2 != null) {
                this.W.setSpan(new URLSpan(str2), length, length2, this.d);
            }
            float f4 = this.H;
            if (f4 != -1.0f) {
                this.W.setSpan(new MaskFilterSpan(new BlurMaskFilter(f4, this.I)), length, length2, this.d);
            }
            Shader shader = this.J;
            if (shader != null) {
                this.W.setSpan(new h(shader), length, length2, this.d);
            }
            float f5 = this.K;
            if (f5 != -1.0f) {
                this.W.setSpan(new i(f5, this.L, this.M, this.N), length, length2, this.d);
            }
            Object[] objArr = this.O;
            if (objArr != null) {
                for (Object obj : objArr) {
                    this.W.setSpan(obj, length, length2, this.d);
                }
            }
        }
    }

    private void e() {
        int length = this.W.length();
        this.c = "<img>";
        d();
        int length2 = this.W.length();
        Bitmap bitmap = this.P;
        if (bitmap != null) {
            this.W.setSpan(new c(bitmap, this.T), length, length2, this.d);
            return;
        }
        Drawable drawable = this.Q;
        if (drawable != null) {
            this.W.setSpan(new c(drawable, this.T), length, length2, this.d);
            return;
        }
        Uri uri = this.R;
        if (uri != null) {
            this.W.setSpan(new c(uri, this.T), length, length2, this.d);
            return;
        }
        int i2 = this.S;
        if (i2 != -1) {
            this.W.setSpan(new c(i2, this.T), length, length2, this.d);
        }
    }

    private void f() {
        int length = this.W.length();
        this.c = "< >";
        d();
        this.W.setSpan(new j(this.U, this.V), length, this.W.length(), this.d);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class k extends ReplacementSpan {
        final int a;

        k(int i) {
            this.a = i;
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(@NonNull Paint paint, CharSequence charSequence, int i, int i2, @Nullable Paint.FontMetricsInt fontMetricsInt) {
            if (paint != null) {
                return (int) paint.measureText(charSequence.subSequence(i, i2).toString());
            }
            throw new NullPointerException("Argument 'paint' of type Paint (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(@NonNull Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, @NonNull Paint paint) {
            if (canvas == null) {
                throw new NullPointerException("Argument 'canvas' of type Canvas (#0 out of 9, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            } else if (paint != null) {
                CharSequence subSequence = charSequence.subSequence(i, i2);
                Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
                canvas.drawText(subSequence.toString(), f, i4 - (((((fontMetricsInt.descent + i4) + i4) + fontMetricsInt.ascent) / 2) - ((i5 + i3) / 2)), paint);
            } else {
                throw new NullPointerException("Argument 'paint' of type Paint (#8 out of 9, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class d implements LineHeightSpan {
        static Paint.FontMetricsInt b;
        final int a;
        private final int c;

        d(int i, int i2) {
            this.c = i;
            this.a = i2;
        }

        @Override // android.text.style.LineHeightSpan
        public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
            Paint.FontMetricsInt fontMetricsInt2 = b;
            if (fontMetricsInt2 == null) {
                b = new Paint.FontMetricsInt();
                b.top = fontMetricsInt.top;
                b.ascent = fontMetricsInt.ascent;
                b.descent = fontMetricsInt.descent;
                b.bottom = fontMetricsInt.bottom;
                b.leading = fontMetricsInt.leading;
            } else {
                fontMetricsInt.top = fontMetricsInt2.top;
                fontMetricsInt.ascent = b.ascent;
                fontMetricsInt.descent = b.descent;
                fontMetricsInt.bottom = b.bottom;
                fontMetricsInt.leading = b.leading;
            }
            int i5 = this.c - (((fontMetricsInt.descent + i4) - fontMetricsInt.ascent) - i3);
            if (i5 > 0) {
                int i6 = this.a;
                if (i6 == 3) {
                    fontMetricsInt.descent += i5;
                } else if (i6 == 2) {
                    int i7 = i5 / 2;
                    fontMetricsInt.descent += i7;
                    fontMetricsInt.ascent -= i7;
                } else {
                    fontMetricsInt.ascent -= i5;
                }
            }
            int i8 = this.c - (((i4 + fontMetricsInt.bottom) - fontMetricsInt.top) - i3);
            if (i8 > 0) {
                int i9 = this.a;
                if (i9 == 3) {
                    fontMetricsInt.bottom += i8;
                } else if (i9 == 2) {
                    int i10 = i8 / 2;
                    fontMetricsInt.bottom += i10;
                    fontMetricsInt.top -= i10;
                } else {
                    fontMetricsInt.top -= i8;
                }
            }
            if (i2 == ((Spanned) charSequence).getSpanEnd(this)) {
                b = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class j extends ReplacementSpan {
        private final int a;
        private final Paint b;

        private j(int i, int i2) {
            this.b = new Paint();
            this.a = i;
            this.b.setColor(i2);
            this.b.setStyle(Paint.Style.FILL);
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(@NonNull Paint paint, CharSequence charSequence, @IntRange(from = 0) int i, @IntRange(from = 0) int i2, @Nullable Paint.FontMetricsInt fontMetricsInt) {
            if (paint != null) {
                return this.a;
            }
            throw new NullPointerException("Argument 'paint' of type Paint (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(@NonNull Canvas canvas, CharSequence charSequence, @IntRange(from = 0) int i, @IntRange(from = 0) int i2, float f, int i3, int i4, int i5, @NonNull Paint paint) {
            if (canvas == null) {
                throw new NullPointerException("Argument 'canvas' of type Canvas (#0 out of 9, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            } else if (paint != null) {
                canvas.drawRect(f, i3, f + this.a, i5, this.b);
            } else {
                throw new NullPointerException("Argument 'paint' of type Paint (#8 out of 9, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class e implements LeadingMarginSpan {
        private final int a;
        private final int b;
        private final int c;

        private e(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }

        @Override // android.text.style.LeadingMarginSpan
        public int getLeadingMargin(boolean z) {
            return this.b + this.c;
        }

        @Override // android.text.style.LeadingMarginSpan
        public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
            Paint.Style style = paint.getStyle();
            int color = paint.getColor();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(this.a);
            canvas.drawRect(i, i3, i + (this.b * i2), i5, paint);
            paint.setStyle(style);
            paint.setColor(color);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a implements LeadingMarginSpan {
        private final int a;
        private final int b;
        private final int c;
        private Path d;

        private a(int i, int i2, int i3) {
            this.d = null;
            this.a = i;
            this.b = i2;
            this.c = i3;
        }

        @Override // android.text.style.LeadingMarginSpan
        public int getLeadingMargin(boolean z) {
            return (this.b * 2) + this.c;
        }

        @Override // android.text.style.LeadingMarginSpan
        public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
            if (((Spanned) charSequence).getSpanStart(this) == i6) {
                Paint.Style style = paint.getStyle();
                int color = paint.getColor();
                paint.setColor(this.a);
                paint.setStyle(Paint.Style.FILL);
                if (canvas.isHardwareAccelerated()) {
                    if (this.d == null) {
                        this.d = new Path();
                        this.d.addCircle(0.0f, 0.0f, this.b, Path.Direction.CW);
                    }
                    canvas.save();
                    canvas.translate(i + (i2 * this.b), (i3 + i5) / 2.0f);
                    canvas.drawPath(this.d, paint);
                    canvas.restore();
                } else {
                    int i8 = this.b;
                    canvas.drawCircle(i + (i2 * i8), (i3 + i5) / 2.0f, i8, paint);
                }
                paint.setColor(color);
                paint.setStyle(style);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @SuppressLint({"ParcelCreator"})
    /* loaded from: classes.dex */
    public static class f extends TypefaceSpan {
        private final Typeface a;

        private f(Typeface typeface) {
            super("");
            this.a = typeface;
        }

        @Override // android.text.style.TypefaceSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            a(textPaint, this.a);
        }

        @Override // android.text.style.TypefaceSpan, android.text.style.MetricAffectingSpan
        public void updateMeasureState(TextPaint textPaint) {
            a(textPaint, this.a);
        }

        private void a(Paint paint, Typeface typeface) {
            Typeface typeface2 = paint.getTypeface();
            int style = (typeface2 == null ? 0 : typeface2.getStyle()) & (~typeface.getStyle());
            if ((style & 1) != 0) {
                paint.setFakeBoldText(true);
            }
            if ((style & 2) != 0) {
                paint.setTextSkewX(-0.25f);
            }
            paint.getShader();
            paint.setTypeface(typeface);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class c extends b {
        private Drawable b;
        private Uri c;
        private int d;

        private c(Bitmap bitmap, int i) {
            super(i);
            this.b = new BitmapDrawable(Utils.getApp().getResources(), bitmap);
            Drawable drawable = this.b;
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.b.getIntrinsicHeight());
        }

        private c(Drawable drawable, int i) {
            super(i);
            this.b = drawable;
            Drawable drawable2 = this.b;
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.b.getIntrinsicHeight());
        }

        private c(Uri uri, int i) {
            super(i);
            this.c = uri;
        }

        private c(@DrawableRes int i, int i2) {
            super(i2);
            this.d = i;
        }

        @Override // com.blankj.utilcode.util.SpanUtils.b
        public Drawable a() {
            Drawable drawable;
            Exception e;
            InputStream openInputStream;
            BitmapDrawable bitmapDrawable;
            Drawable drawable2 = this.b;
            if (drawable2 != null) {
                return drawable2;
            }
            BitmapDrawable bitmapDrawable2 = null;
            if (this.c != null) {
                try {
                    openInputStream = Utils.getApp().getContentResolver().openInputStream(this.c);
                    bitmapDrawable = new BitmapDrawable(Utils.getApp().getResources(), BitmapFactory.decodeStream(openInputStream));
                } catch (Exception e2) {
                    e = e2;
                }
                try {
                    bitmapDrawable.setBounds(0, 0, bitmapDrawable.getIntrinsicWidth(), bitmapDrawable.getIntrinsicHeight());
                    if (openInputStream != null) {
                        openInputStream.close();
                    }
                    return bitmapDrawable;
                } catch (Exception e3) {
                    e = e3;
                    bitmapDrawable2 = bitmapDrawable;
                    Log.e("sms", "Failed to loaded content " + this.c, e);
                    return bitmapDrawable2;
                }
            } else {
                try {
                    drawable = ContextCompat.getDrawable(Utils.getApp(), this.d);
                    try {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        return drawable;
                    } catch (Exception unused) {
                        Log.e("sms", "Unable to find resource: " + this.d);
                        return drawable;
                    }
                } catch (Exception unused2) {
                    drawable = null;
                }
            }
        }
    }

    /* loaded from: classes.dex */
    static abstract class b extends ReplacementSpan {
        final int a;
        private WeakReference<Drawable> b;

        public abstract Drawable a();

        private b(int i) {
            this.a = i;
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(@NonNull Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            int i3;
            if (paint != null) {
                Rect bounds = b().getBounds();
                if (fontMetricsInt != null && (i3 = fontMetricsInt.bottom - fontMetricsInt.top) < bounds.height()) {
                    int i4 = this.a;
                    if (i4 == 3) {
                        fontMetricsInt.top = fontMetricsInt.top;
                        fontMetricsInt.bottom = bounds.height() + fontMetricsInt.top;
                    } else if (i4 == 2) {
                        int i5 = i3 / 4;
                        fontMetricsInt.top = ((-bounds.height()) / 2) - i5;
                        fontMetricsInt.bottom = (bounds.height() / 2) - i5;
                    } else {
                        fontMetricsInt.top = (-bounds.height()) + fontMetricsInt.bottom;
                        fontMetricsInt.bottom = fontMetricsInt.bottom;
                    }
                    fontMetricsInt.ascent = fontMetricsInt.top;
                    fontMetricsInt.descent = fontMetricsInt.bottom;
                }
                return bounds.right;
            }
            throw new NullPointerException("Argument 'paint' of type Paint (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(@NonNull Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, @NonNull Paint paint) {
            float f2;
            if (canvas == null) {
                throw new NullPointerException("Argument 'canvas' of type Canvas (#0 out of 9, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            } else if (paint != null) {
                Drawable b = b();
                Rect bounds = b.getBounds();
                canvas.save();
                if (bounds.height() < i5 - i3) {
                    int i6 = this.a;
                    if (i6 == 3) {
                        f2 = i3;
                    } else if (i6 == 2) {
                        f2 = ((i5 + i3) - bounds.height()) / 2;
                    } else if (i6 == 1) {
                        f2 = i4 - bounds.height();
                    } else {
                        f2 = i5 - bounds.height();
                    }
                    canvas.translate(f, f2);
                } else {
                    canvas.translate(f, i3);
                }
                b.draw(canvas);
                canvas.restore();
            } else {
                throw new NullPointerException("Argument 'paint' of type Paint (#8 out of 9, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        private Drawable b() {
            WeakReference<Drawable> weakReference = this.b;
            Drawable drawable = weakReference != null ? weakReference.get() : null;
            if (drawable != null) {
                return drawable;
            }
            Drawable a = a();
            this.b = new WeakReference<>(a);
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class h extends CharacterStyle implements UpdateAppearance {
        private Shader a;

        private h(Shader shader) {
            this.a = shader;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setShader(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class i extends CharacterStyle implements UpdateAppearance {
        private float a;
        private float b;
        private float c;
        private int d;

        private i(float f, float f2, float f3, int i) {
            this.a = f;
            this.b = f2;
            this.c = f3;
            this.d = i;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setShadowLayer(this.a, this.b, this.c, this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class g extends SpannableStringBuilder implements Serializable {
        private static final long serialVersionUID = 4909567650765875771L;

        private g() {
        }
    }
}
