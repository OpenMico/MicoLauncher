package com.xiaomi.micolauncher.module.child.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;

/* loaded from: classes3.dex */
public class MaskView extends View {
    private final int a = ViewCompat.MEASURED_STATE_MASK;
    private final Paint b = new Paint(1);
    private final RectF c = new RectF();
    private float d;

    public MaskView(Context context) {
        super(context);
        a();
    }

    public MaskView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public MaskView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        this.b.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void setHollowRect(float f, float f2, float f3, float f4, float f5) {
        RectF rectF = this.c;
        rectF.left = f;
        rectF.top = f2;
        rectF.right = f3;
        rectF.bottom = f4;
        this.d = f5;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
        RectF rectF = this.c;
        float f = this.d;
        canvas.drawRoundRect(rectF, f, f, this.b);
    }
}
