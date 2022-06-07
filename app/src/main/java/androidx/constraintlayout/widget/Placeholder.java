package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintLayout;

/* loaded from: classes.dex */
public class Placeholder extends View {
    private int a = -1;
    private View b = null;
    private int c = 4;

    public Placeholder(Context context) {
        super(context);
        a(null);
    }

    public Placeholder(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
    }

    public Placeholder(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(attributeSet);
    }

    public Placeholder(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        a(attributeSet);
    }

    private void a(AttributeSet attributeSet) {
        super.setVisibility(this.c);
        this.a = -1;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_placeholder);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.ConstraintLayout_placeholder_content) {
                    this.a = obtainStyledAttributes.getResourceId(index, this.a);
                } else if (index == R.styleable.ConstraintLayout_placeholder_placeholder_emptyVisibility) {
                    this.c = obtainStyledAttributes.getInt(index, this.c);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    public void setEmptyVisibility(int i) {
        this.c = i;
    }

    public int getEmptyVisibility() {
        return this.c;
    }

    public View getContent() {
        return this.b;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            canvas.drawRGB(223, 223, 223);
            Paint paint = new Paint();
            paint.setARGB(255, 210, 210, 210);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
            Rect rect = new Rect();
            canvas.getClipBounds(rect);
            paint.setTextSize(rect.height());
            int height = rect.height();
            int width = rect.width();
            paint.setTextAlign(Paint.Align.LEFT);
            paint.getTextBounds("?", 0, 1, rect);
            canvas.drawText("?", ((width / 2.0f) - (rect.width() / 2.0f)) - rect.left, ((height / 2.0f) + (rect.height() / 2.0f)) - rect.bottom, paint);
        }
    }

    public void updatePreLayout(ConstraintLayout constraintLayout) {
        if (this.a == -1 && !isInEditMode()) {
            setVisibility(this.c);
        }
        this.b = constraintLayout.findViewById(this.a);
        View view = this.b;
        if (view != null) {
            ((ConstraintLayout.LayoutParams) view.getLayoutParams()).h = true;
            this.b.setVisibility(0);
            setVisibility(0);
        }
    }

    public void setContentId(int i) {
        View findViewById;
        if (this.a != i) {
            View view = this.b;
            if (view != null) {
                view.setVisibility(0);
                ((ConstraintLayout.LayoutParams) this.b.getLayoutParams()).h = false;
                this.b = null;
            }
            this.a = i;
            if (i != -1 && (findViewById = ((View) getParent()).findViewById(i)) != null) {
                findViewById.setVisibility(8);
            }
        }
    }

    public void updatePostMeasure(ConstraintLayout constraintLayout) {
        if (this.b != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.b.getLayoutParams();
            layoutParams2.t.setVisibility(0);
            if (layoutParams.t.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
                layoutParams.t.setWidth(layoutParams2.t.getWidth());
            }
            if (layoutParams.t.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
                layoutParams.t.setHeight(layoutParams2.t.getHeight());
            }
            layoutParams2.t.setVisibility(8);
        }
    }
}
