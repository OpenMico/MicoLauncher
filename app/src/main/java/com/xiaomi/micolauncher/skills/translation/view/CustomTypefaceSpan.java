package com.xiaomi.micolauncher.skills.translation.view;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/* loaded from: classes3.dex */
public class CustomTypefaceSpan extends TypefaceSpan {
    private final Typeface a;

    public CustomTypefaceSpan(String str, Typeface typeface) {
        super(str);
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

    private static void a(Paint paint, Typeface typeface) {
        Typeface typeface2 = paint.getTypeface();
        int style = (typeface2 == null ? 0 : typeface2.getStyle()) & (~typeface.getStyle());
        if ((style & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((style & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(typeface);
    }
}
