package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.TypefaceUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.manager.HomeVideoDataManager;

/* loaded from: classes3.dex */
public class StaticLayoutView extends View {
    private Layout a;
    private int b;
    private int c;
    private TextPaint d;

    public StaticLayoutView(Context context) {
        this(context, null);
    }

    public StaticLayoutView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StaticLayoutView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = null;
        this.d = new TextPaint(1);
        this.d.density = getResources().getDisplayMetrics().density;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StaticLayoutView);
        this.d.setTextSize(obtainStyledAttributes.getDimensionPixelSize(1, 24));
        this.d.setColor(obtainStyledAttributes.getColor(0, getResources().getColor(R.color.color_272727)));
        this.d.setTypeface(Typeface.create(TypefaceUtil.FONT_WEIGHT_W400, 0));
        obtainStyledAttributes.recycle();
    }

    public void setLayout(Layout layout) {
        this.a = layout;
        if (this.a.getWidth() != this.b || this.a.getHeight() != this.c) {
            this.b = this.a.getWidth();
            this.c = this.a.getHeight();
            requestLayout();
        }
    }

    public void setTextSize(int i) {
        this.d.setTextSize(i);
    }

    public void setTextColor(int i) {
        this.d.setColor(i);
    }

    public void setText(String str, TextPaint textPaint) {
        this.a = HomeVideoDataManager.getManager().getLayout(str);
        if (ContainerUtil.isEmpty(str)) {
            str = "";
        }
        if (this.a == null) {
            this.a = StaticLayout.Builder.obtain(str, 0, str.length(), textPaint, 370).setTextDirection(TextDirectionHeuristics.LTR).setAlignment(Layout.Alignment.ALIGN_NORMAL).build();
            HomeVideoDataManager.getManager().putLayout(str, this.a);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Layout layout = this.a;
        if (layout != null) {
            layout.draw(canvas, null, null, 0);
        }
        canvas.restore();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        Layout layout = this.a;
        if (layout != null) {
            setMeasuredDimension(layout.getWidth(), this.a.getHeight());
        } else {
            super.onMeasure(i, i2);
        }
    }
}
