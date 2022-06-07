package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* compiled from: SubtitlePainter.java */
/* loaded from: classes2.dex */
final class d {
    private int A;
    private int B;
    private int C;
    private int D;
    private StaticLayout E;
    private StaticLayout F;
    private int G;
    private int H;
    private int I;
    private Rect J;
    private final float a;
    private final float b;
    private final float c;
    private final float d;
    private final float e;
    private final TextPaint f = new TextPaint();
    private final Paint g = new Paint();
    private final Paint h = new Paint();
    @Nullable
    private CharSequence i;
    @Nullable
    private Layout.Alignment j;
    @Nullable
    private Bitmap k;
    private float l;
    private int m;
    private int n;
    private float o;
    private int p;
    private float q;
    private float r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private float x;
    private float y;
    private float z;

    public d(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, new int[]{16843287, 16843288}, 0, 0);
        this.e = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.d = obtainStyledAttributes.getFloat(1, 1.0f);
        obtainStyledAttributes.recycle();
        float round = Math.round((context.getResources().getDisplayMetrics().densityDpi * 2.0f) / 160.0f);
        this.a = round;
        this.b = round;
        this.c = round;
        this.f.setAntiAlias(true);
        this.f.setSubpixelText(true);
        this.g.setAntiAlias(true);
        this.g.setStyle(Paint.Style.FILL);
        this.h.setAntiAlias(true);
        this.h.setFilterBitmap(true);
    }

    public void a(Cue cue, CaptionStyleCompat captionStyleCompat, float f, float f2, float f3, Canvas canvas, int i, int i2, int i3, int i4) {
        boolean z = cue.bitmap == null;
        int i5 = ViewCompat.MEASURED_STATE_MASK;
        if (z) {
            if (!TextUtils.isEmpty(cue.text)) {
                i5 = cue.windowColorSet ? cue.windowColor : captionStyleCompat.windowColor;
            } else {
                return;
            }
        }
        if (a(this.i, cue.text) && Util.areEqual(this.j, cue.textAlignment) && this.k == cue.bitmap && this.l == cue.line && this.m == cue.lineType && Util.areEqual(Integer.valueOf(this.n), Integer.valueOf(cue.lineAnchor)) && this.o == cue.position && Util.areEqual(Integer.valueOf(this.p), Integer.valueOf(cue.positionAnchor)) && this.q == cue.size && this.r == cue.bitmapHeight && this.s == captionStyleCompat.foregroundColor && this.t == captionStyleCompat.backgroundColor && this.u == i5 && this.w == captionStyleCompat.edgeType && this.v == captionStyleCompat.edgeColor && Util.areEqual(this.f.getTypeface(), captionStyleCompat.typeface) && this.x == f && this.y == f2 && this.z == f3 && this.A == i && this.B == i2 && this.C == i3 && this.D == i4) {
            a(canvas, z);
            return;
        }
        this.i = cue.text;
        this.j = cue.textAlignment;
        this.k = cue.bitmap;
        this.l = cue.line;
        this.m = cue.lineType;
        this.n = cue.lineAnchor;
        this.o = cue.position;
        this.p = cue.positionAnchor;
        this.q = cue.size;
        this.r = cue.bitmapHeight;
        this.s = captionStyleCompat.foregroundColor;
        this.t = captionStyleCompat.backgroundColor;
        this.u = i5;
        this.w = captionStyleCompat.edgeType;
        this.v = captionStyleCompat.edgeColor;
        this.f.setTypeface(captionStyleCompat.typeface);
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.A = i;
        this.B = i2;
        this.C = i3;
        this.D = i4;
        if (z) {
            Assertions.checkNotNull(this.i);
            a();
        } else {
            Assertions.checkNotNull(this.k);
            b();
        }
        a(canvas, z);
    }

    @RequiresNonNull({"cueText"})
    private void a() {
        SpannableStringBuilder spannableStringBuilder;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        CharSequence charSequence = this.i;
        if (charSequence instanceof SpannableStringBuilder) {
            spannableStringBuilder = (SpannableStringBuilder) charSequence;
        } else {
            spannableStringBuilder = new SpannableStringBuilder(charSequence);
        }
        int i6 = this.C - this.A;
        int i7 = this.D - this.B;
        this.f.setTextSize(this.x);
        int i8 = (int) ((this.x * 0.125f) + 0.5f);
        int i9 = i8 * 2;
        int i10 = i6 - i9;
        float f = this.q;
        int i11 = f != -3.4028235E38f ? (int) (i10 * f) : i10;
        if (i11 <= 0) {
            Log.w("SubtitlePainter", "Skipped drawing subtitle cue (insufficient space)");
            return;
        }
        float f2 = this.y;
        if (f2 > 0.0f) {
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan((int) f2), 0, spannableStringBuilder.length(), 16711680);
        }
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(spannableStringBuilder);
        if (this.w == 1) {
            for (ForegroundColorSpan foregroundColorSpan : (ForegroundColorSpan[]) spannableStringBuilder2.getSpans(0, spannableStringBuilder2.length(), ForegroundColorSpan.class)) {
                spannableStringBuilder2.removeSpan(foregroundColorSpan);
            }
        }
        if (Color.alpha(this.t) > 0) {
            int i12 = this.w;
            if (i12 == 0 || i12 == 2) {
                spannableStringBuilder.setSpan(new BackgroundColorSpan(this.t), 0, spannableStringBuilder.length(), 16711680);
            } else {
                spannableStringBuilder2.setSpan(new BackgroundColorSpan(this.t), 0, spannableStringBuilder2.length(), 16711680);
            }
        }
        Layout.Alignment alignment = this.j;
        if (alignment == null) {
            alignment = Layout.Alignment.ALIGN_CENTER;
        }
        this.E = new StaticLayout(spannableStringBuilder, this.f, i11, alignment, this.d, this.e, true);
        int height = this.E.getHeight();
        int lineCount = this.E.getLineCount();
        int i13 = 0;
        for (int i14 = 0; i14 < lineCount; i14++) {
            i13 = Math.max((int) Math.ceil(this.E.getLineWidth(i14)), i13);
        }
        if (this.q == -3.4028235E38f || i13 >= i11) {
            i11 = i13;
        }
        int i15 = i11 + i9;
        float f3 = this.o;
        if (f3 != -3.4028235E38f) {
            int round = Math.round(i6 * f3) + this.A;
            switch (this.p) {
                case 1:
                    i2 = 2;
                    round = ((round * 2) - i15) / 2;
                    break;
                case 2:
                    round -= i15;
                    i2 = 2;
                    break;
                default:
                    i2 = 2;
                    break;
            }
            i3 = Math.max(round, this.A);
            i = Math.min(i15 + i3, this.C);
        } else {
            i2 = 2;
            i3 = ((i6 - i15) / 2) + this.A;
            i = i3 + i15;
        }
        int i16 = i - i3;
        if (i16 <= 0) {
            Log.w("SubtitlePainter", "Skipped drawing subtitle cue (invalid horizontal positioning)");
            return;
        }
        float f4 = this.l;
        if (f4 != -3.4028235E38f) {
            if (this.m == 0) {
                i5 = Math.round(i7 * f4) + this.B;
                int i17 = this.n;
                if (i17 == i2) {
                    i5 -= height;
                } else if (i17 == 1) {
                    i5 = ((i5 * 2) - height) / i2;
                }
            } else {
                int lineBottom = this.E.getLineBottom(0) - this.E.getLineTop(0);
                float f5 = this.l;
                i5 = f5 >= 0.0f ? this.B + Math.round(f5 * lineBottom) : (Math.round((f5 + 1.0f) * lineBottom) + this.D) - height;
            }
            int i18 = i5 + height;
            int i19 = this.D;
            if (i18 > i19) {
                i4 = i19 - height;
            } else {
                int i20 = this.B;
                i4 = i5 < i20 ? i20 : i5;
            }
        } else {
            i4 = (this.D - height) - ((int) (i7 * this.z));
        }
        this.E = new StaticLayout(spannableStringBuilder, this.f, i16, alignment, this.d, this.e, true);
        this.F = new StaticLayout(spannableStringBuilder2, this.f, i16, alignment, this.d, this.e, true);
        this.G = i3;
        this.H = i4;
        this.I = i8;
    }

    @RequiresNonNull({"cueBitmap"})
    private void b() {
        int i;
        Bitmap bitmap = this.k;
        int i2 = this.C;
        int i3 = this.A;
        int i4 = this.D;
        int i5 = this.B;
        float f = i2 - i3;
        float f2 = i3 + (this.o * f);
        float f3 = i4 - i5;
        float f4 = i5 + (this.l * f3);
        int round = Math.round(f * this.q);
        float f5 = this.r;
        if (f5 != -3.4028235E38f) {
            i = Math.round(f3 * f5);
        } else {
            i = Math.round(round * (bitmap.getHeight() / bitmap.getWidth()));
        }
        int i6 = this.p;
        if (i6 == 2) {
            f2 -= round;
        } else if (i6 == 1) {
            f2 -= round / 2;
        }
        int round2 = Math.round(f2);
        int i7 = this.n;
        if (i7 == 2) {
            f4 -= i;
        } else if (i7 == 1) {
            f4 -= i / 2;
        }
        int round3 = Math.round(f4);
        this.J = new Rect(round2, round3, round + round2, i + round3);
    }

    private void a(Canvas canvas, boolean z) {
        if (z) {
            a(canvas);
            return;
        }
        Assertions.checkNotNull(this.J);
        Assertions.checkNotNull(this.k);
        b(canvas);
    }

    private void a(Canvas canvas) {
        StaticLayout staticLayout = this.E;
        StaticLayout staticLayout2 = this.F;
        if (staticLayout != null && staticLayout2 != null) {
            int save = canvas.save();
            canvas.translate(this.G, this.H);
            if (Color.alpha(this.u) > 0) {
                this.g.setColor(this.u);
                canvas.drawRect(-this.I, 0.0f, staticLayout.getWidth() + this.I, staticLayout.getHeight(), this.g);
            }
            int i = this.w;
            boolean z = true;
            if (i == 1) {
                this.f.setStrokeJoin(Paint.Join.ROUND);
                this.f.setStrokeWidth(this.a);
                this.f.setColor(this.v);
                this.f.setStyle(Paint.Style.FILL_AND_STROKE);
                staticLayout2.draw(canvas);
            } else if (i == 2) {
                TextPaint textPaint = this.f;
                float f = this.b;
                float f2 = this.c;
                textPaint.setShadowLayer(f, f2, f2, this.v);
            } else if (i == 3 || i == 4) {
                if (this.w != 3) {
                    z = false;
                }
                int i2 = -1;
                int i3 = z ? -1 : this.v;
                if (z) {
                    i2 = this.v;
                }
                float f3 = this.b / 2.0f;
                this.f.setColor(this.s);
                this.f.setStyle(Paint.Style.FILL);
                float f4 = -f3;
                this.f.setShadowLayer(this.b, f4, f4, i3);
                staticLayout2.draw(canvas);
                this.f.setShadowLayer(this.b, f3, f3, i2);
            }
            this.f.setColor(this.s);
            this.f.setStyle(Paint.Style.FILL);
            staticLayout.draw(canvas);
            this.f.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            canvas.restoreToCount(save);
        }
    }

    @RequiresNonNull({"cueBitmap", "bitmapRect"})
    private void b(Canvas canvas) {
        canvas.drawBitmap(this.k, (Rect) null, this.J, this.h);
    }

    private static boolean a(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2) {
        return charSequence == charSequence2 || (charSequence != null && charSequence.equals(charSequence2));
    }
}
