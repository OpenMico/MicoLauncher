package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

/* loaded from: classes.dex */
public class Barrier extends ConstraintHelper {
    public static final int BOTTOM = 3;
    public static final int END = 6;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int START = 5;
    public static final int TOP = 2;
    private int a;
    private int b;
    private androidx.constraintlayout.solver.widgets.Barrier c;

    public Barrier(Context context) {
        super(context);
        super.setVisibility(8);
    }

    public Barrier(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        super.setVisibility(8);
    }

    public Barrier(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        super.setVisibility(8);
    }

    public int getType() {
        return this.a;
    }

    public void setType(int i) {
        this.a = i;
    }

    private void a(ConstraintWidget constraintWidget, int i, boolean z) {
        this.b = i;
        if (Build.VERSION.SDK_INT < 17) {
            int i2 = this.a;
            if (i2 == 5) {
                this.b = 0;
            } else if (i2 == 6) {
                this.b = 1;
            }
        } else if (z) {
            int i3 = this.a;
            if (i3 == 5) {
                this.b = 1;
            } else if (i3 == 6) {
                this.b = 0;
            }
        } else {
            int i4 = this.a;
            if (i4 == 5) {
                this.b = 0;
            } else if (i4 == 6) {
                this.b = 1;
            }
        }
        if (constraintWidget instanceof androidx.constraintlayout.solver.widgets.Barrier) {
            ((androidx.constraintlayout.solver.widgets.Barrier) constraintWidget).setBarrierType(this.b);
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void resolveRtl(ConstraintWidget constraintWidget, boolean z) {
        a(constraintWidget, this.a, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        this.c = new androidx.constraintlayout.solver.widgets.Barrier();
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.ConstraintLayout_Layout_barrierDirection) {
                    setType(obtainStyledAttributes.getInt(index, 0));
                } else if (index == R.styleable.ConstraintLayout_Layout_barrierAllowsGoneWidgets) {
                    this.c.setAllowsGoneWidget(obtainStyledAttributes.getBoolean(index, true));
                } else if (index == R.styleable.ConstraintLayout_Layout_barrierMargin) {
                    this.c.setMargin(obtainStyledAttributes.getDimensionPixelSize(index, 0));
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.mHelperWidget = this.c;
        validateParams();
    }

    public void setAllowsGoneWidget(boolean z) {
        this.c.setAllowsGoneWidget(z);
    }

    public boolean allowsGoneWidget() {
        return this.c.allowsGoneWidget();
    }

    public void setDpMargin(int i) {
        androidx.constraintlayout.solver.widgets.Barrier barrier = this.c;
        barrier.setMargin((int) ((i * getResources().getDisplayMetrics().density) + 0.5f));
    }

    public int getMargin() {
        return this.c.getMargin();
    }

    public void setMargin(int i) {
        this.c.setMargin(i);
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void loadParameters(ConstraintSet.Constraint constraint, HelperWidget helperWidget, ConstraintLayout.LayoutParams layoutParams, SparseArray<ConstraintWidget> sparseArray) {
        super.loadParameters(constraint, helperWidget, layoutParams, sparseArray);
        if (helperWidget instanceof androidx.constraintlayout.solver.widgets.Barrier) {
            androidx.constraintlayout.solver.widgets.Barrier barrier = (androidx.constraintlayout.solver.widgets.Barrier) helperWidget;
            a(barrier, constraint.layout.mBarrierDirection, ((ConstraintWidgetContainer) helperWidget.getParent()).isRtl());
            barrier.setAllowsGoneWidget(constraint.layout.mBarrierAllowsGoneWidgets);
            barrier.setMargin(constraint.layout.mBarrierMargin);
        }
    }
}
