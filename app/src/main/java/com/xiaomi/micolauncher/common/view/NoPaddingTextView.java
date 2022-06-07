package com.xiaomi.micolauncher.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;

/* loaded from: classes3.dex */
public class NoPaddingTextView extends AppCompatTextView {
    int a;
    int b;
    private Paint c;
    private Rect d;

    public NoPaddingTextView(Context context) {
        this(context, null);
    }

    public NoPaddingTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NoPaddingTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = 0;
        this.b = 0;
        this.d = new Rect();
        this.c = getPaint();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        a(canvas);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.a = View.MeasureSpec.getSize(i);
        this.b = View.MeasureSpec.getSize(i2);
        a();
        setMeasuredDimension(this.d.right - this.d.left, (-this.d.top) + this.d.bottom);
    }

    private String a() {
        String charSequence = getText().toString();
        int length = charSequence.length();
        this.c.getTextBounds(charSequence, 0, length, this.d);
        if (length == 0) {
            Rect rect = this.d;
            rect.right = rect.left;
        }
        return charSequence;
    }

    private void a(Canvas canvas) {
        String a = a();
        int i = this.d.left;
        int i2 = this.d.bottom;
        Rect rect = this.d;
        rect.offset(-rect.left, -this.d.top);
        this.c.setAntiAlias(true);
        this.c.setColor(getCurrentTextColor());
        canvas.drawText(a, -i, this.d.bottom - i2, this.c);
    }
}
