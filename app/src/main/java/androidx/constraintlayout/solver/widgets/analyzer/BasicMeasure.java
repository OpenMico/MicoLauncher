package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class BasicMeasure {
    public static final int AT_MOST = Integer.MIN_VALUE;
    public static final int EXACTLY = 1073741824;
    public static final int FIXED = -3;
    public static final int MATCH_PARENT = -1;
    public static final int UNSPECIFIED = 0;
    public static final int WRAP_CONTENT = -2;
    private final ArrayList<ConstraintWidget> a = new ArrayList<>();
    private Measure b = new Measure();
    private ConstraintWidgetContainer c;

    /* loaded from: classes.dex */
    public static class Measure {
        public static int SELF_DIMENSIONS = 0;
        public static int TRY_GIVEN_DIMENSIONS = 1;
        public static int USE_GIVEN_DIMENSIONS = 2;
        public ConstraintWidget.DimensionBehaviour horizontalBehavior;
        public int horizontalDimension;
        public int measureStrategy;
        public int measuredBaseline;
        public boolean measuredHasBaseline;
        public int measuredHeight;
        public boolean measuredNeedsSolverPass;
        public int measuredWidth;
        public ConstraintWidget.DimensionBehaviour verticalBehavior;
        public int verticalDimension;
    }

    /* loaded from: classes.dex */
    public interface Measurer {
        void didMeasures();

        void measure(ConstraintWidget constraintWidget, Measure measure);
    }

    public void updateHierarchy(ConstraintWidgetContainer constraintWidgetContainer) {
        this.a.clear();
        int size = constraintWidgetContainer.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) constraintWidgetContainer.mChildren.get(i);
            if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                this.a.add(constraintWidget);
            }
        }
        constraintWidgetContainer.invalidateGraph();
    }

    public BasicMeasure(ConstraintWidgetContainer constraintWidgetContainer) {
        this.c = constraintWidgetContainer;
    }

    private void a(ConstraintWidgetContainer constraintWidgetContainer) {
        int size = constraintWidgetContainer.mChildren.size();
        boolean optimizeFor = constraintWidgetContainer.optimizeFor(64);
        Measurer measurer = constraintWidgetContainer.getMeasurer();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) constraintWidgetContainer.mChildren.get(i);
            if (!(constraintWidget instanceof Guideline) && !(constraintWidget instanceof Barrier) && !constraintWidget.isInVirtualLayout() && (!optimizeFor || constraintWidget.horizontalRun == null || constraintWidget.verticalRun == null || !constraintWidget.horizontalRun.d.resolved || !constraintWidget.verticalRun.d.resolved)) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.getDimensionBehaviour(0);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.getDimensionBehaviour(1);
                boolean z = dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth != 1 && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight != 1;
                if (!z && constraintWidgetContainer.optimizeFor(1) && !(constraintWidget instanceof VirtualLayout)) {
                    if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0 && dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !constraintWidget.isInHorizontalChain()) {
                        z = true;
                    }
                    if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0 && dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !constraintWidget.isInHorizontalChain()) {
                        z = true;
                    }
                    if ((dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && constraintWidget.mDimensionRatio > 0.0f) {
                        z = true;
                    }
                }
                if (!z) {
                    a(measurer, constraintWidget, Measure.SELF_DIMENSIONS);
                    if (constraintWidgetContainer.mMetrics != null) {
                        constraintWidgetContainer.mMetrics.measuredWidgets++;
                    }
                }
            }
        }
        measurer.didMeasures();
    }

    private void a(ConstraintWidgetContainer constraintWidgetContainer, String str, int i, int i2) {
        int minWidth = constraintWidgetContainer.getMinWidth();
        int minHeight = constraintWidgetContainer.getMinHeight();
        constraintWidgetContainer.setMinWidth(0);
        constraintWidgetContainer.setMinHeight(0);
        constraintWidgetContainer.setWidth(i);
        constraintWidgetContainer.setHeight(i2);
        constraintWidgetContainer.setMinWidth(minWidth);
        constraintWidgetContainer.setMinHeight(minHeight);
        this.c.layout();
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x008e, code lost:
        r2 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long solverMeasure(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r23, int r24, int r25, int r26, int r27, int r28, int r29, int r30, int r31, int r32) {
        /*
            Method dump skipped, instructions count: 848
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure.solverMeasure(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, int, int, int, int, int, int, int, int, int):long");
    }

    private boolean a(Measurer measurer, ConstraintWidget constraintWidget, int i) {
        this.b.horizontalBehavior = constraintWidget.getHorizontalDimensionBehaviour();
        this.b.verticalBehavior = constraintWidget.getVerticalDimensionBehaviour();
        this.b.horizontalDimension = constraintWidget.getWidth();
        this.b.verticalDimension = constraintWidget.getHeight();
        Measure measure = this.b;
        measure.measuredNeedsSolverPass = false;
        measure.measureStrategy = i;
        boolean z = measure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z2 = this.b.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z3 = z && constraintWidget.mDimensionRatio > 0.0f;
        boolean z4 = z2 && constraintWidget.mDimensionRatio > 0.0f;
        if (z3 && constraintWidget.mResolvedMatchConstraintDefault[0] == 4) {
            this.b.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        if (z4 && constraintWidget.mResolvedMatchConstraintDefault[1] == 4) {
            this.b.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        measurer.measure(constraintWidget, this.b);
        constraintWidget.setWidth(this.b.measuredWidth);
        constraintWidget.setHeight(this.b.measuredHeight);
        constraintWidget.setHasBaseline(this.b.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.b.measuredBaseline);
        this.b.measureStrategy = Measure.SELF_DIMENSIONS;
        return this.b.measuredNeedsSolverPass;
    }
}
