package com.xiaomi.micolauncher.common.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public class CenterAlignImageSpan extends ImageSpan {
    public CenterAlignImageSpan(Drawable drawable) {
        super(drawable);
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(@NonNull Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, @NonNull Paint paint) {
        Drawable drawable = getDrawable();
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        canvas.save();
        canvas.translate(f, ((((fontMetricsInt.descent + i4) + i4) + fontMetricsInt.ascent) / 2) - (drawable.getBounds().bottom / 2));
        drawable.draw(canvas);
        canvas.restore();
    }
}
