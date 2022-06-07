package com.xiaomi.micolauncher.module.setting.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class InputProgressView extends View {
    private Paint a;
    private Paint b;
    private int c;
    private int d;
    private int e;
    private int f;
    private float g;
    private float h;
    private int i;
    private RectF j;

    public InputProgressView(Context context) {
        this(context, null);
    }

    public InputProgressView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public InputProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
        a();
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.InputProgressView, 0, 0);
            this.g = obtainStyledAttributes.getDimension(1, context.getResources().getDimension(R.dimen.input_progress_radius));
            this.h = obtainStyledAttributes.getDimension(2, context.getResources().getDimension(R.dimen.input_progress_storke_width));
            this.c = obtainStyledAttributes.getColor(3, context.getColor(R.color.input_progress_ring_color));
            this.d = obtainStyledAttributes.getColor(3, context.getColor(R.color.input_progress_success_ring_color));
            this.e = obtainStyledAttributes.getColor(3, context.getColor(R.color.input_progress_failed_ring_color));
            this.f = context.getColor(R.color.color_F6F6F6);
            obtainStyledAttributes.recycle();
        }
    }

    private void a() {
        this.a = new Paint();
        this.a.setAntiAlias(true);
        this.a.setDither(true);
        this.a.setColor(this.c);
        this.a.setStyle(Paint.Style.STROKE);
        this.a.setStrokeCap(Paint.Cap.ROUND);
        this.a.setStrokeWidth(this.h);
        this.b = new Paint();
        this.b.setAntiAlias(true);
        this.b.setDither(true);
        this.b.setColor(this.f);
        this.b.setStyle(Paint.Style.STROKE);
        this.b.setStrokeCap(Paint.Cap.ROUND);
        this.b.setStrokeWidth(this.h);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.j = new RectF((getWidth() >> 1) - this.g, (getHeight() >> 1) - this.g, (getWidth() >> 1) + this.g, (getHeight() >> 1) + this.g);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(this.j, -90.0f, 360.0f, false, this.b);
        int i = this.i;
        if (i >= 0) {
            canvas.drawArc(this.j, -90.0f, (i / 100.0f) * 360.0f, false, this.a);
        }
    }

    public void setProgress(int i) {
        this.i = i;
        postInvalidate();
    }

    public int getProgress() {
        return this.i;
    }

    public void setSuccess() {
        this.a.setColor(this.d);
        postInvalidate();
    }

    public void setFailed() {
        this.a.setColor(this.e);
        postInvalidate();
    }

    public void reset() {
        this.a.setColor(this.c);
        setProgress(0);
    }
}
