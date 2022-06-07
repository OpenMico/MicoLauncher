package com.xiaomi.micolauncher.skills.voiceprint.view;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class GradientSpan extends CharacterStyle implements UpdateAppearance {
    private int[] a;
    private float b;
    private int c;

    public GradientSpan(Context context, float f, int i) {
        this.a = new int[]{context.getColor(R.color.color_text_gradient_begin), context.getColor(R.color.color_text_gradient_end)};
        this.b = f;
        this.c = i;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        LinearGradient linearGradient;
        TextPaint textPaint2;
        if (this.c == 0) {
            linearGradient = new LinearGradient(this.b, textPaint.getTextSize(), 0.0f, textPaint.getTextSize(), this.a, (float[]) null, Shader.TileMode.MIRROR);
            textPaint2 = textPaint;
        } else {
            linearGradient = new LinearGradient(0.0f, textPaint.getTextSize(), this.b, textPaint.getTextSize(), this.a, (float[]) null, Shader.TileMode.MIRROR);
            textPaint2 = textPaint;
        }
        textPaint2.setShader(linearGradient);
    }
}
