package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;

/* loaded from: classes.dex */
public class VirtualLayout extends HelperWidget {
    private int E = 0;
    private int F = 0;
    private int G = 0;
    private int H = 0;
    private int I = 0;
    private int J = 0;
    private int K = 0;
    private int L = 0;
    private boolean M = false;
    private int N = 0;
    private int O = 0;
    protected BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
    BasicMeasure.Measurer a = null;

    public void measure(int i, int i2, int i3, int i4) {
    }

    public void setPadding(int i) {
        this.G = i;
        this.E = i;
        this.H = i;
        this.F = i;
        this.I = i;
        this.J = i;
    }

    public void setPaddingStart(int i) {
        this.I = i;
        this.K = i;
        this.L = i;
    }

    public void setPaddingEnd(int i) {
        this.J = i;
    }

    public void setPaddingLeft(int i) {
        this.G = i;
        this.K = i;
    }

    public void applyRtl(boolean z) {
        if (this.I <= 0 && this.J <= 0) {
            return;
        }
        if (z) {
            this.K = this.J;
            this.L = this.I;
            return;
        }
        this.K = this.I;
        this.L = this.J;
    }

    public void setPaddingTop(int i) {
        this.E = i;
    }

    public void setPaddingRight(int i) {
        this.H = i;
        this.L = i;
    }

    public void setPaddingBottom(int i) {
        this.F = i;
    }

    public int getPaddingTop() {
        return this.E;
    }

    public int getPaddingBottom() {
        return this.F;
    }

    public int getPaddingLeft() {
        return this.K;
    }

    public int getPaddingRight() {
        return this.L;
    }

    protected void needsCallbackFromSolver(boolean z) {
        this.M = z;
    }

    public boolean needSolverPass() {
        return this.M;
    }

    @Override // androidx.constraintlayout.solver.widgets.HelperWidget, androidx.constraintlayout.solver.widgets.Helper
    public void updateConstraints(ConstraintWidgetContainer constraintWidgetContainer) {
        captureWidgets();
    }

    public void captureWidgets() {
        for (int i = 0; i < this.mWidgetsCount; i++) {
            ConstraintWidget constraintWidget = this.mWidgets[i];
            if (constraintWidget != null) {
                constraintWidget.setInVirtualLayout(true);
            }
        }
    }

    public int getMeasuredWidth() {
        return this.N;
    }

    public int getMeasuredHeight() {
        return this.O;
    }

    public void setMeasure(int i, int i2) {
        this.N = i;
        this.O = i2;
    }

    protected boolean measureChildren() {
        BasicMeasure.Measurer measurer = this.mParent != null ? ((ConstraintWidgetContainer) this.mParent).getMeasurer() : null;
        if (measurer == null) {
            return false;
        }
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= this.mWidgetsCount) {
                return true;
            }
            ConstraintWidget constraintWidget = this.mWidgets[i];
            if (constraintWidget != null && !(constraintWidget instanceof Guideline)) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.getDimensionBehaviour(0);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.getDimensionBehaviour(1);
                if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.mMatchConstraintDefaultWidth == 1 || dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.mMatchConstraintDefaultHeight == 1) {
                    z = false;
                }
                if (!z) {
                    if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    }
                    if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    }
                    BasicMeasure.Measure measure = this.mMeasure;
                    measure.horizontalBehavior = dimensionBehaviour;
                    measure.verticalBehavior = dimensionBehaviour2;
                    measure.horizontalDimension = constraintWidget.getWidth();
                    this.mMeasure.verticalDimension = constraintWidget.getHeight();
                    measurer.measure(constraintWidget, this.mMeasure);
                    constraintWidget.setWidth(this.mMeasure.measuredWidth);
                    constraintWidget.setHeight(this.mMeasure.measuredHeight);
                    constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
                }
            }
            i++;
        }
    }

    protected void measure(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int i, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int i2) {
        while (this.a == null && getParent() != null) {
            this.a = ((ConstraintWidgetContainer) getParent()).getMeasurer();
        }
        BasicMeasure.Measure measure = this.mMeasure;
        measure.horizontalBehavior = dimensionBehaviour;
        measure.verticalBehavior = dimensionBehaviour2;
        measure.horizontalDimension = i;
        measure.verticalDimension = i2;
        this.a.measure(constraintWidget, measure);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        constraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
    }
}
