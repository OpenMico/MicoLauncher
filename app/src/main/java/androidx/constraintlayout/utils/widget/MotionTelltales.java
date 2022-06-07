package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewParent;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R;

/* loaded from: classes.dex */
public class MotionTelltales extends MockView {
    MotionLayout a;
    float[] b;
    Matrix c;
    int d;
    int e;
    float f;
    private Paint g;

    public MotionTelltales(Context context) {
        super(context);
        this.g = new Paint();
        this.b = new float[2];
        this.c = new Matrix();
        this.d = 0;
        this.e = -65281;
        this.f = 0.25f;
        a(context, null);
    }

    public MotionTelltales(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.g = new Paint();
        this.b = new float[2];
        this.c = new Matrix();
        this.d = 0;
        this.e = -65281;
        this.f = 0.25f;
        a(context, attributeSet);
    }

    public MotionTelltales(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.g = new Paint();
        this.b = new float[2];
        this.c = new Matrix();
        this.d = 0;
        this.e = -65281;
        this.f = 0.25f;
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MotionTelltales);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.MotionTelltales_telltales_tailColor) {
                    this.e = obtainStyledAttributes.getColor(index, this.e);
                } else if (index == R.styleable.MotionTelltales_telltales_velocityMode) {
                    this.d = obtainStyledAttributes.getInt(index, this.d);
                } else if (index == R.styleable.MotionTelltales_telltales_tailScale) {
                    this.f = obtainStyledAttributes.getFloat(index, this.f);
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.g.setColor(this.e);
        this.g.setStrokeWidth(5.0f);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setText(CharSequence charSequence) {
        this.mText = charSequence.toString();
        requestLayout();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        postInvalidate();
    }

    @Override // androidx.constraintlayout.utils.widget.MockView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getMatrix().invert(this.c);
        if (this.a == null) {
            ViewParent parent = getParent();
            if (parent instanceof MotionLayout) {
                this.a = (MotionLayout) parent;
                return;
            }
            return;
        }
        int width = getWidth();
        int height = getHeight();
        float[] fArr = {0.1f, 0.25f, 0.5f, 0.75f, 0.9f};
        for (float f : fArr) {
            for (float f2 : fArr) {
                this.a.getViewVelocity(this, f2, f, this.b, this.d);
                this.c.mapVectors(this.b);
                float f3 = width * f2;
                float f4 = height * f;
                float[] fArr2 = this.b;
                float f5 = fArr2[0];
                float f6 = this.f;
                float f7 = f4 - (fArr2[1] * f6);
                this.c.mapVectors(fArr2);
                canvas.drawLine(f3, f4, f3 - (f5 * f6), f7, this.g);
            }
        }
    }
}
