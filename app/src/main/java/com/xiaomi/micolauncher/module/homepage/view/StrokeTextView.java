package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class StrokeTextView extends TextView {
    private int a;
    private float b;
    private TextPaint c;

    public StrokeTextView(Context context) {
        this(context, null);
    }

    public StrokeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StrokeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = new TextPaint();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StrokeTextView);
        this.a = obtainStyledAttributes.getColor(0, -1);
        this.b = obtainStyledAttributes.getDimension(1, 0.0f);
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        ColorStateList textColors = getTextColors();
        this.c = getPaint();
        this.c.setStyle(Paint.Style.STROKE);
        this.c.setStrokeJoin(Paint.Join.ROUND);
        this.c.setStrokeMiter(10.0f);
        setTextColor(this.a);
        this.c.setStrokeWidth(this.b);
        super.onDraw(canvas);
        this.c.setStyle(Paint.Style.FILL);
        setTextColor(textColors);
        super.onDraw(canvas);
    }
}
