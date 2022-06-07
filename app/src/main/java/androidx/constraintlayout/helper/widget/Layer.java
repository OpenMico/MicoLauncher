package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R;

/* loaded from: classes.dex */
public class Layer extends ConstraintHelper {
    ConstraintLayout a;
    private boolean k;
    private boolean l;
    private float d = Float.NaN;
    private float e = Float.NaN;
    private float f = Float.NaN;
    private float g = 1.0f;
    private float h = 1.0f;
    protected float mComputedCenterX = Float.NaN;
    protected float mComputedCenterY = Float.NaN;
    protected float mComputedMaxX = Float.NaN;
    protected float mComputedMaxY = Float.NaN;
    protected float mComputedMinX = Float.NaN;
    protected float mComputedMinY = Float.NaN;
    boolean b = true;
    View[] c = null;
    private float i = 0.0f;
    private float j = 0.0f;

    public Layer(Context context) {
        super(context);
    }

    public Layer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Layer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        this.mUseViewMeasure = false;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.ConstraintLayout_Layout_android_visibility) {
                    this.k = true;
                } else if (index == R.styleable.ConstraintLayout_Layout_android_elevation) {
                    this.l = true;
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.a = (ConstraintLayout) getParent();
        if (this.k || this.l) {
            int visibility = getVisibility();
            float elevation = Build.VERSION.SDK_INT >= 21 ? getElevation() : 0.0f;
            for (int i = 0; i < this.mCount; i++) {
                View viewById = this.a.getViewById(this.mIds[i]);
                if (viewById != null) {
                    if (this.k) {
                        viewById.setVisibility(visibility);
                    }
                    if (this.l && elevation > 0.0f && Build.VERSION.SDK_INT >= 21) {
                        viewById.setTranslationZ(viewById.getTranslationZ() + elevation);
                    }
                }
            }
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void updatePreDraw(ConstraintLayout constraintLayout) {
        this.a = constraintLayout;
        float rotation = getRotation();
        if (rotation != 0.0f) {
            this.f = rotation;
        } else if (!Float.isNaN(this.f)) {
            this.f = rotation;
        }
    }

    @Override // android.view.View
    public void setRotation(float f) {
        this.f = f;
        b();
    }

    @Override // android.view.View
    public void setScaleX(float f) {
        this.g = f;
        b();
    }

    @Override // android.view.View
    public void setScaleY(float f) {
        this.h = f;
        b();
    }

    @Override // android.view.View
    public void setPivotX(float f) {
        this.d = f;
        b();
    }

    @Override // android.view.View
    public void setPivotY(float f) {
        this.e = f;
        b();
    }

    @Override // android.view.View
    public void setTranslationX(float f) {
        this.i = f;
        b();
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        this.j = f;
        b();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        applyLayoutFeatures();
    }

    @Override // android.view.View
    public void setElevation(float f) {
        super.setElevation(f);
        applyLayoutFeatures();
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void updatePostLayout(ConstraintLayout constraintLayout) {
        a();
        this.mComputedCenterX = Float.NaN;
        this.mComputedCenterY = Float.NaN;
        ConstraintWidget constraintWidget = ((ConstraintLayout.LayoutParams) getLayoutParams()).getConstraintWidget();
        constraintWidget.setWidth(0);
        constraintWidget.setHeight(0);
        calcCenters();
        layout(((int) this.mComputedMinX) - getPaddingLeft(), ((int) this.mComputedMinY) - getPaddingTop(), ((int) this.mComputedMaxX) + getPaddingRight(), ((int) this.mComputedMaxY) + getPaddingBottom());
        b();
    }

    private void a() {
        if (!(this.a == null || this.mCount == 0)) {
            View[] viewArr = this.c;
            if (viewArr == null || viewArr.length != this.mCount) {
                this.c = new View[this.mCount];
            }
            for (int i = 0; i < this.mCount; i++) {
                this.c[i] = this.a.getViewById(this.mIds[i]);
            }
        }
    }

    protected void calcCenters() {
        if (this.a != null) {
            if (!(this.b || Float.isNaN(this.mComputedCenterX) || Float.isNaN(this.mComputedCenterY))) {
                return;
            }
            if (Float.isNaN(this.d) || Float.isNaN(this.e)) {
                View[] views = getViews(this.a);
                int left = views[0].getLeft();
                int top = views[0].getTop();
                int right = views[0].getRight();
                int bottom = views[0].getBottom();
                for (int i = 0; i < this.mCount; i++) {
                    View view = views[i];
                    left = Math.min(left, view.getLeft());
                    top = Math.min(top, view.getTop());
                    right = Math.max(right, view.getRight());
                    bottom = Math.max(bottom, view.getBottom());
                }
                this.mComputedMaxX = right;
                this.mComputedMaxY = bottom;
                this.mComputedMinX = left;
                this.mComputedMinY = top;
                if (Float.isNaN(this.d)) {
                    this.mComputedCenterX = (left + right) / 2;
                } else {
                    this.mComputedCenterX = this.d;
                }
                if (Float.isNaN(this.e)) {
                    this.mComputedCenterY = (top + bottom) / 2;
                } else {
                    this.mComputedCenterY = this.e;
                }
            } else {
                this.mComputedCenterY = this.e;
                this.mComputedCenterX = this.d;
            }
        }
    }

    private void b() {
        if (this.a != null) {
            if (this.c == null) {
                a();
            }
            calcCenters();
            double radians = Float.isNaN(this.f) ? 0.0d : Math.toRadians(this.f);
            float sin = (float) Math.sin(radians);
            float cos = (float) Math.cos(radians);
            float f = this.g;
            float f2 = f * cos;
            float f3 = this.h;
            float f4 = (-f3) * sin;
            float f5 = f * sin;
            float f6 = f3 * cos;
            for (int i = 0; i < this.mCount; i++) {
                View view = this.c[i];
                float left = ((view.getLeft() + view.getRight()) / 2) - this.mComputedCenterX;
                float top = ((view.getTop() + view.getBottom()) / 2) - this.mComputedCenterY;
                view.setTranslationX((((f2 * left) + (f4 * top)) - left) + this.i);
                view.setTranslationY((((left * f5) + (f6 * top)) - top) + this.j);
                view.setScaleY(this.h);
                view.setScaleX(this.g);
                if (!Float.isNaN(this.f)) {
                    view.setRotation(this.f);
                }
            }
        }
    }
}
