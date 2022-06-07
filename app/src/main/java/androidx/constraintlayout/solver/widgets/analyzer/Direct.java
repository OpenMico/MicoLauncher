package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ChainHead;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Direct {
    private static BasicMeasure.Measure a = new BasicMeasure.Measure();

    public static void solvingPass(ConstraintWidgetContainer constraintWidgetContainer, BasicMeasure.Measurer measurer) {
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = constraintWidgetContainer.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour = constraintWidgetContainer.getVerticalDimensionBehaviour();
        constraintWidgetContainer.resetFinalResolution();
        ArrayList<ConstraintWidget> children = constraintWidgetContainer.getChildren();
        int size = children.size();
        for (int i = 0; i < size; i++) {
            children.get(i).resetFinalResolution();
        }
        boolean isRtl = constraintWidgetContainer.isRtl();
        if (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
            constraintWidgetContainer.setFinalHorizontal(0, constraintWidgetContainer.getWidth());
        } else {
            constraintWidgetContainer.setFinalLeft(0);
        }
        boolean z = false;
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = children.get(i2);
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                if (guideline.getOrientation() == 1) {
                    if (guideline.getRelativeBegin() != -1) {
                        guideline.setFinalValue(guideline.getRelativeBegin());
                    } else if (guideline.getRelativeEnd() != -1 && constraintWidgetContainer.isResolvedHorizontally()) {
                        guideline.setFinalValue(constraintWidgetContainer.getWidth() - guideline.getRelativeEnd());
                    } else if (constraintWidgetContainer.isResolvedHorizontally()) {
                        guideline.setFinalValue((int) ((guideline.getRelativePercent() * constraintWidgetContainer.getWidth()) + 0.5f));
                    }
                    z = true;
                }
            } else if ((constraintWidget instanceof Barrier) && ((Barrier) constraintWidget).getOrientation() == 0) {
                z2 = true;
            }
        }
        if (z) {
            for (int i3 = 0; i3 < size; i3++) {
                ConstraintWidget constraintWidget2 = children.get(i3);
                if (constraintWidget2 instanceof Guideline) {
                    Guideline guideline2 = (Guideline) constraintWidget2;
                    if (guideline2.getOrientation() == 1) {
                        a(guideline2, measurer, isRtl);
                    }
                }
            }
        }
        a(constraintWidgetContainer, measurer, isRtl);
        if (z2) {
            for (int i4 = 0; i4 < size; i4++) {
                ConstraintWidget constraintWidget3 = children.get(i4);
                if (constraintWidget3 instanceof Barrier) {
                    Barrier barrier = (Barrier) constraintWidget3;
                    if (barrier.getOrientation() == 0) {
                        a(barrier, measurer, 0, isRtl);
                    }
                }
            }
        }
        if (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
            constraintWidgetContainer.setFinalVertical(0, constraintWidgetContainer.getHeight());
        } else {
            constraintWidgetContainer.setFinalTop(0);
        }
        boolean z3 = false;
        boolean z4 = false;
        for (int i5 = 0; i5 < size; i5++) {
            ConstraintWidget constraintWidget4 = children.get(i5);
            if (constraintWidget4 instanceof Guideline) {
                Guideline guideline3 = (Guideline) constraintWidget4;
                if (guideline3.getOrientation() == 0) {
                    if (guideline3.getRelativeBegin() != -1) {
                        guideline3.setFinalValue(guideline3.getRelativeBegin());
                    } else if (guideline3.getRelativeEnd() != -1 && constraintWidgetContainer.isResolvedVertically()) {
                        guideline3.setFinalValue(constraintWidgetContainer.getHeight() - guideline3.getRelativeEnd());
                    } else if (constraintWidgetContainer.isResolvedVertically()) {
                        guideline3.setFinalValue((int) ((guideline3.getRelativePercent() * constraintWidgetContainer.getHeight()) + 0.5f));
                    }
                    z3 = true;
                }
            } else if ((constraintWidget4 instanceof Barrier) && ((Barrier) constraintWidget4).getOrientation() == 1) {
                z4 = true;
            }
        }
        if (z3) {
            for (int i6 = 0; i6 < size; i6++) {
                ConstraintWidget constraintWidget5 = children.get(i6);
                if (constraintWidget5 instanceof Guideline) {
                    Guideline guideline4 = (Guideline) constraintWidget5;
                    if (guideline4.getOrientation() == 0) {
                        a(guideline4, measurer);
                    }
                }
            }
        }
        a(constraintWidgetContainer, measurer);
        if (z4) {
            for (int i7 = 0; i7 < size; i7++) {
                ConstraintWidget constraintWidget6 = children.get(i7);
                if (constraintWidget6 instanceof Barrier) {
                    Barrier barrier2 = (Barrier) constraintWidget6;
                    if (barrier2.getOrientation() == 1) {
                        a(barrier2, measurer, 1, isRtl);
                    }
                }
            }
        }
        for (int i8 = 0; i8 < size; i8++) {
            ConstraintWidget constraintWidget7 = children.get(i8);
            if (constraintWidget7.isMeasureRequested() && a(constraintWidget7)) {
                ConstraintWidgetContainer.measure(constraintWidget7, measurer, a, BasicMeasure.Measure.SELF_DIMENSIONS);
                a(constraintWidget7, measurer, isRtl);
                a(constraintWidget7, measurer);
            }
        }
    }

    private static void a(Barrier barrier, BasicMeasure.Measurer measurer, int i, boolean z) {
        if (!barrier.allSolved()) {
            return;
        }
        if (i == 0) {
            a(barrier, measurer, z);
        } else {
            a(barrier, measurer);
        }
    }

    private static void a(ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, boolean z) {
        if (!(constraintWidget instanceof ConstraintWidgetContainer) && constraintWidget.isMeasureRequested() && a(constraintWidget)) {
            ConstraintWidgetContainer.measure(constraintWidget, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
        }
        ConstraintAnchor anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor anchor2 = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
        int finalValue = anchor.getFinalValue();
        int finalValue2 = anchor2.getFinalValue();
        if (anchor.getDependents() != null && anchor.hasFinalValue()) {
            Iterator<ConstraintAnchor> it = anchor.getDependents().iterator();
            while (it.hasNext()) {
                ConstraintAnchor next = it.next();
                ConstraintWidget constraintWidget2 = next.mOwner;
                boolean a2 = a(constraintWidget2);
                if (constraintWidget2.isMeasureRequested() && a2) {
                    ConstraintWidgetContainer.measure(constraintWidget2, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                }
                if (constraintWidget2.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || a2) {
                    if (!constraintWidget2.isMeasureRequested()) {
                        if (next == constraintWidget2.mLeft && constraintWidget2.mRight.mTarget == null) {
                            int margin = constraintWidget2.mLeft.getMargin() + finalValue;
                            constraintWidget2.setFinalHorizontal(margin, constraintWidget2.getWidth() + margin);
                            a(constraintWidget2, measurer, z);
                        } else if (next == constraintWidget2.mRight && constraintWidget2.mLeft.mTarget == null) {
                            int margin2 = finalValue - constraintWidget2.mRight.getMargin();
                            constraintWidget2.setFinalHorizontal(margin2 - constraintWidget2.getWidth(), margin2);
                            a(constraintWidget2, measurer, z);
                        } else if (next == constraintWidget2.mLeft && constraintWidget2.mRight.mTarget != null && constraintWidget2.mRight.mTarget.hasFinalValue() && !constraintWidget2.isInHorizontalChain()) {
                            a(measurer, constraintWidget2, z);
                        }
                    }
                } else if (constraintWidget2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget2.mMatchConstraintMaxWidth >= 0 && constraintWidget2.mMatchConstraintMinWidth >= 0 && (constraintWidget2.getVisibility() == 8 || (constraintWidget2.mMatchConstraintDefaultWidth == 0 && constraintWidget2.getDimensionRatio() == 0.0f))) {
                    if (!constraintWidget2.isInHorizontalChain() && !constraintWidget2.isInVirtualLayout()) {
                        if (((next == constraintWidget2.mLeft && constraintWidget2.mRight.mTarget != null && constraintWidget2.mRight.mTarget.hasFinalValue()) || (next == constraintWidget2.mRight && constraintWidget2.mLeft.mTarget != null && constraintWidget2.mLeft.mTarget.hasFinalValue())) && !constraintWidget2.isInHorizontalChain()) {
                            a(constraintWidget, measurer, constraintWidget2, z);
                        }
                    }
                }
            }
        }
        if (!(constraintWidget instanceof Guideline) && anchor2.getDependents() != null && anchor2.hasFinalValue()) {
            Iterator<ConstraintAnchor> it2 = anchor2.getDependents().iterator();
            while (it2.hasNext()) {
                ConstraintAnchor next2 = it2.next();
                ConstraintWidget constraintWidget3 = next2.mOwner;
                boolean a3 = a(constraintWidget3);
                if (constraintWidget3.isMeasureRequested() && a3) {
                    ConstraintWidgetContainer.measure(constraintWidget3, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                }
                boolean z2 = (next2 == constraintWidget3.mLeft && constraintWidget3.mRight.mTarget != null && constraintWidget3.mRight.mTarget.hasFinalValue()) || (next2 == constraintWidget3.mRight && constraintWidget3.mLeft.mTarget != null && constraintWidget3.mLeft.mTarget.hasFinalValue());
                if (constraintWidget3.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || a3) {
                    if (!constraintWidget3.isMeasureRequested()) {
                        if (next2 == constraintWidget3.mLeft && constraintWidget3.mRight.mTarget == null) {
                            int margin3 = constraintWidget3.mLeft.getMargin() + finalValue2;
                            constraintWidget3.setFinalHorizontal(margin3, constraintWidget3.getWidth() + margin3);
                            a(constraintWidget3, measurer, z);
                        } else if (next2 == constraintWidget3.mRight && constraintWidget3.mLeft.mTarget == null) {
                            int margin4 = finalValue2 - constraintWidget3.mRight.getMargin();
                            constraintWidget3.setFinalHorizontal(margin4 - constraintWidget3.getWidth(), margin4);
                            a(constraintWidget3, measurer, z);
                        } else if (z2 && !constraintWidget3.isInHorizontalChain()) {
                            a(measurer, constraintWidget3, z);
                        }
                    }
                } else if (constraintWidget3.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget3.mMatchConstraintMaxWidth >= 0 && constraintWidget3.mMatchConstraintMinWidth >= 0 && (constraintWidget3.getVisibility() == 8 || (constraintWidget3.mMatchConstraintDefaultWidth == 0 && constraintWidget3.getDimensionRatio() == 0.0f))) {
                    if (!constraintWidget3.isInHorizontalChain() && !constraintWidget3.isInVirtualLayout() && z2 && !constraintWidget3.isInHorizontalChain()) {
                        a(constraintWidget, measurer, constraintWidget3, z);
                    }
                }
            }
        }
    }

    private static void a(ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer) {
        if (!(constraintWidget instanceof ConstraintWidgetContainer) && constraintWidget.isMeasureRequested() && a(constraintWidget)) {
            ConstraintWidgetContainer.measure(constraintWidget, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
        }
        ConstraintAnchor anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
        ConstraintAnchor anchor2 = constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM);
        int finalValue = anchor.getFinalValue();
        int finalValue2 = anchor2.getFinalValue();
        if (anchor.getDependents() != null && anchor.hasFinalValue()) {
            Iterator<ConstraintAnchor> it = anchor.getDependents().iterator();
            while (it.hasNext()) {
                ConstraintAnchor next = it.next();
                ConstraintWidget constraintWidget2 = next.mOwner;
                boolean a2 = a(constraintWidget2);
                if (constraintWidget2.isMeasureRequested() && a2) {
                    ConstraintWidgetContainer.measure(constraintWidget2, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                }
                if (constraintWidget2.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || a2) {
                    if (!constraintWidget2.isMeasureRequested()) {
                        if (next == constraintWidget2.mTop && constraintWidget2.mBottom.mTarget == null) {
                            int margin = constraintWidget2.mTop.getMargin() + finalValue;
                            constraintWidget2.setFinalVertical(margin, constraintWidget2.getHeight() + margin);
                            a(constraintWidget2, measurer);
                        } else if (next == constraintWidget2.mBottom && constraintWidget2.mBottom.mTarget == null) {
                            int margin2 = finalValue - constraintWidget2.mBottom.getMargin();
                            constraintWidget2.setFinalVertical(margin2 - constraintWidget2.getHeight(), margin2);
                            a(constraintWidget2, measurer);
                        } else if (next == constraintWidget2.mTop && constraintWidget2.mBottom.mTarget != null && constraintWidget2.mBottom.mTarget.hasFinalValue()) {
                            a(measurer, constraintWidget2);
                        }
                    }
                } else if (constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget2.mMatchConstraintMaxHeight >= 0 && constraintWidget2.mMatchConstraintMinHeight >= 0 && (constraintWidget2.getVisibility() == 8 || (constraintWidget2.mMatchConstraintDefaultHeight == 0 && constraintWidget2.getDimensionRatio() == 0.0f))) {
                    if (!constraintWidget2.isInVerticalChain() && !constraintWidget2.isInVirtualLayout()) {
                        if (((next == constraintWidget2.mTop && constraintWidget2.mBottom.mTarget != null && constraintWidget2.mBottom.mTarget.hasFinalValue()) || (next == constraintWidget2.mBottom && constraintWidget2.mTop.mTarget != null && constraintWidget2.mTop.mTarget.hasFinalValue())) && !constraintWidget2.isInVerticalChain()) {
                            a(constraintWidget, measurer, constraintWidget2);
                        }
                    }
                }
            }
        }
        if (!(constraintWidget instanceof Guideline)) {
            if (anchor2.getDependents() != null && anchor2.hasFinalValue()) {
                Iterator<ConstraintAnchor> it2 = anchor2.getDependents().iterator();
                while (it2.hasNext()) {
                    ConstraintAnchor next2 = it2.next();
                    ConstraintWidget constraintWidget3 = next2.mOwner;
                    boolean a3 = a(constraintWidget3);
                    if (constraintWidget3.isMeasureRequested() && a3) {
                        ConstraintWidgetContainer.measure(constraintWidget3, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                    }
                    boolean z = (next2 == constraintWidget3.mTop && constraintWidget3.mBottom.mTarget != null && constraintWidget3.mBottom.mTarget.hasFinalValue()) || (next2 == constraintWidget3.mBottom && constraintWidget3.mTop.mTarget != null && constraintWidget3.mTop.mTarget.hasFinalValue());
                    if (constraintWidget3.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || a3) {
                        if (!constraintWidget3.isMeasureRequested()) {
                            if (next2 == constraintWidget3.mTop && constraintWidget3.mBottom.mTarget == null) {
                                int margin3 = constraintWidget3.mTop.getMargin() + finalValue2;
                                constraintWidget3.setFinalVertical(margin3, constraintWidget3.getHeight() + margin3);
                                a(constraintWidget3, measurer);
                            } else if (next2 == constraintWidget3.mBottom && constraintWidget3.mTop.mTarget == null) {
                                int margin4 = finalValue2 - constraintWidget3.mBottom.getMargin();
                                constraintWidget3.setFinalVertical(margin4 - constraintWidget3.getHeight(), margin4);
                                a(constraintWidget3, measurer);
                            } else if (z && !constraintWidget3.isInVerticalChain()) {
                                a(measurer, constraintWidget3);
                            }
                        }
                    } else if (constraintWidget3.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget3.mMatchConstraintMaxHeight >= 0 && constraintWidget3.mMatchConstraintMinHeight >= 0 && (constraintWidget3.getVisibility() == 8 || (constraintWidget3.mMatchConstraintDefaultHeight == 0 && constraintWidget3.getDimensionRatio() == 0.0f))) {
                        if (!constraintWidget3.isInVerticalChain() && !constraintWidget3.isInVirtualLayout() && z && !constraintWidget3.isInVerticalChain()) {
                            a(constraintWidget, measurer, constraintWidget3);
                        }
                    }
                }
            }
            ConstraintAnchor anchor3 = constraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE);
            if (anchor3.getDependents() != null && anchor3.hasFinalValue()) {
                int finalValue3 = anchor3.getFinalValue();
                Iterator<ConstraintAnchor> it3 = anchor3.getDependents().iterator();
                while (it3.hasNext()) {
                    ConstraintAnchor next3 = it3.next();
                    ConstraintWidget constraintWidget4 = next3.mOwner;
                    boolean a4 = a(constraintWidget4);
                    if (constraintWidget4.isMeasureRequested() && a4) {
                        ConstraintWidgetContainer.measure(constraintWidget4, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                    }
                    if (constraintWidget4.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || a4) {
                        if (!constraintWidget4.isMeasureRequested() && next3 == constraintWidget4.mBaseline) {
                            constraintWidget4.setFinalBaseline(finalValue3);
                            a(constraintWidget4, measurer);
                        }
                    }
                }
            }
        }
    }

    private static void a(BasicMeasure.Measurer measurer, ConstraintWidget constraintWidget, boolean z) {
        float horizontalBiasPercent = constraintWidget.getHorizontalBiasPercent();
        int finalValue = constraintWidget.mLeft.mTarget.getFinalValue();
        int finalValue2 = constraintWidget.mRight.mTarget.getFinalValue();
        finalValue = constraintWidget.mLeft.getMargin() + finalValue;
        finalValue2 -= constraintWidget.mRight.getMargin();
        if (finalValue == finalValue2) {
            horizontalBiasPercent = 0.5f;
        }
        int width = constraintWidget.getWidth();
        int i = (finalValue2 - finalValue) - width;
        if (finalValue > finalValue2) {
            i = (finalValue - finalValue2) - width;
        }
        int i2 = ((int) ((horizontalBiasPercent * i) + 0.5f)) + finalValue;
        int i3 = i2 + width;
        if (finalValue > finalValue2) {
            i3 = i2 - width;
        }
        constraintWidget.setFinalHorizontal(i2, i3);
        a(constraintWidget, measurer, z);
    }

    private static void a(BasicMeasure.Measurer measurer, ConstraintWidget constraintWidget) {
        float verticalBiasPercent = constraintWidget.getVerticalBiasPercent();
        int finalValue = constraintWidget.mTop.mTarget.getFinalValue();
        int finalValue2 = constraintWidget.mBottom.mTarget.getFinalValue();
        finalValue = constraintWidget.mTop.getMargin() + finalValue;
        finalValue2 -= constraintWidget.mBottom.getMargin();
        if (finalValue == finalValue2) {
            verticalBiasPercent = 0.5f;
        }
        int height = constraintWidget.getHeight();
        int i = (finalValue2 - finalValue) - height;
        if (finalValue > finalValue2) {
            i = (finalValue - finalValue2) - height;
        }
        int i2 = (int) ((verticalBiasPercent * i) + 0.5f);
        int i3 = finalValue + i2;
        int i4 = i3 + height;
        if (finalValue > finalValue2) {
            i3 = finalValue - i2;
            i4 = i3 - height;
        }
        constraintWidget.setFinalVertical(i3, i4);
        a(constraintWidget, measurer);
    }

    private static void a(ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, ConstraintWidget constraintWidget2, boolean z) {
        int i;
        float horizontalBiasPercent = constraintWidget2.getHorizontalBiasPercent();
        int finalValue = constraintWidget2.mLeft.mTarget.getFinalValue() + constraintWidget2.mLeft.getMargin();
        int finalValue2 = constraintWidget2.mRight.mTarget.getFinalValue() - constraintWidget2.mRight.getMargin();
        if (finalValue2 >= finalValue) {
            int width = constraintWidget2.getWidth();
            if (constraintWidget2.getVisibility() != 8) {
                if (constraintWidget2.mMatchConstraintDefaultWidth == 2) {
                    if (constraintWidget instanceof ConstraintWidgetContainer) {
                        i = constraintWidget.getWidth();
                    } else {
                        i = constraintWidget.getParent().getWidth();
                    }
                    width = (int) (constraintWidget2.getHorizontalBiasPercent() * 0.5f * i);
                } else if (constraintWidget2.mMatchConstraintDefaultWidth == 0) {
                    width = finalValue2 - finalValue;
                }
                width = Math.max(constraintWidget2.mMatchConstraintMinWidth, width);
                if (constraintWidget2.mMatchConstraintMaxWidth > 0) {
                    width = Math.min(constraintWidget2.mMatchConstraintMaxWidth, width);
                }
            }
            int i2 = finalValue + ((int) ((horizontalBiasPercent * ((finalValue2 - finalValue) - width)) + 0.5f));
            constraintWidget2.setFinalHorizontal(i2, width + i2);
            a(constraintWidget2, measurer, z);
        }
    }

    private static void a(ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, ConstraintWidget constraintWidget2) {
        int i;
        float verticalBiasPercent = constraintWidget2.getVerticalBiasPercent();
        int finalValue = constraintWidget2.mTop.mTarget.getFinalValue() + constraintWidget2.mTop.getMargin();
        int finalValue2 = constraintWidget2.mBottom.mTarget.getFinalValue() - constraintWidget2.mBottom.getMargin();
        if (finalValue2 >= finalValue) {
            int height = constraintWidget2.getHeight();
            if (constraintWidget2.getVisibility() != 8) {
                if (constraintWidget2.mMatchConstraintDefaultHeight == 2) {
                    if (constraintWidget instanceof ConstraintWidgetContainer) {
                        i = constraintWidget.getHeight();
                    } else {
                        i = constraintWidget.getParent().getHeight();
                    }
                    height = (int) (verticalBiasPercent * 0.5f * i);
                } else if (constraintWidget2.mMatchConstraintDefaultHeight == 0) {
                    height = finalValue2 - finalValue;
                }
                height = Math.max(constraintWidget2.mMatchConstraintMinHeight, height);
                if (constraintWidget2.mMatchConstraintMaxHeight > 0) {
                    height = Math.min(constraintWidget2.mMatchConstraintMaxHeight, height);
                }
            }
            int i2 = finalValue + ((int) ((verticalBiasPercent * ((finalValue2 - finalValue) - height)) + 0.5f));
            constraintWidget2.setFinalVertical(i2, height + i2);
            a(constraintWidget2, measurer);
        }
    }

    private static boolean a(ConstraintWidget constraintWidget) {
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = constraintWidget.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour = constraintWidget.getVerticalDimensionBehaviour();
        ConstraintWidgetContainer constraintWidgetContainer = constraintWidget.getParent() != null ? (ConstraintWidgetContainer) constraintWidget.getParent() : null;
        if (constraintWidgetContainer != null) {
            constraintWidgetContainer.getHorizontalDimensionBehaviour();
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        if (constraintWidgetContainer != null) {
            constraintWidgetContainer.getVerticalDimensionBehaviour();
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        boolean z = horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0 && constraintWidget.mDimensionRatio == 0.0f && constraintWidget.hasDanglingDimension(0)) || constraintWidget.isResolvedHorizontally();
        boolean z2 = verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0 && constraintWidget.mDimensionRatio == 0.0f && constraintWidget.hasDanglingDimension(1)) || constraintWidget.isResolvedVertically();
        if (constraintWidget.mDimensionRatio <= 0.0f || (!z && !z2)) {
            return z && z2;
        }
        return true;
    }

    public static boolean solveChain(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i, int i2, ChainHead chainHead, boolean z, boolean z2, boolean z3) {
        int finalValue;
        int finalValue2;
        int finalValue3;
        int i3;
        boolean z4;
        LinearSystem linearSystem2;
        ConstraintWidget constraintWidget;
        int i4;
        float f;
        int i5;
        if (z3) {
            return false;
        }
        if (i == 0) {
            if (!constraintWidgetContainer.isResolvedHorizontally()) {
                return false;
            }
        } else if (!constraintWidgetContainer.isResolvedVertically()) {
            return false;
        }
        boolean isRtl = constraintWidgetContainer.isRtl();
        ConstraintWidget first = chainHead.getFirst();
        ConstraintWidget last = chainHead.getLast();
        ConstraintWidget firstVisibleWidget = chainHead.getFirstVisibleWidget();
        ConstraintWidget lastVisibleWidget = chainHead.getLastVisibleWidget();
        ConstraintWidget head = chainHead.getHead();
        ConstraintAnchor constraintAnchor = first.mListAnchors[i2];
        int i6 = i2 + 1;
        ConstraintAnchor constraintAnchor2 = last.mListAnchors[i6];
        if (constraintAnchor.mTarget == null || constraintAnchor2.mTarget == null || !constraintAnchor.mTarget.hasFinalValue() || !constraintAnchor2.mTarget.hasFinalValue() || firstVisibleWidget == null || lastVisibleWidget == null || (finalValue3 = (finalValue2 = constraintAnchor2.mTarget.getFinalValue() - lastVisibleWidget.mListAnchors[i6].getMargin()) - (finalValue = constraintAnchor.mTarget.getFinalValue() + firstVisibleWidget.mListAnchors[i2].getMargin())) <= 0) {
            return false;
        }
        BasicMeasure.Measure measure = new BasicMeasure.Measure();
        boolean z5 = false;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        ConstraintWidget constraintWidget2 = first;
        while (true) {
            ConstraintWidget constraintWidget3 = null;
            if (!z5) {
                ConstraintAnchor constraintAnchor3 = constraintWidget2.mListAnchors[i2];
                if (!a(constraintWidget2) || constraintWidget2.mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    return false;
                }
                if (constraintWidget2.isMeasureRequested()) {
                    ConstraintWidgetContainer.measure(constraintWidget2, constraintWidgetContainer.getMeasurer(), measure, BasicMeasure.Measure.SELF_DIMENSIONS);
                }
                int margin = i9 + constraintWidget2.mListAnchors[i2].getMargin();
                if (i == 0) {
                    i5 = margin + constraintWidget2.getWidth();
                } else {
                    i5 = margin + constraintWidget2.getHeight();
                }
                i9 = i5 + constraintWidget2.mListAnchors[i6].getMargin();
                i8++;
                if (constraintWidget2.getVisibility() != 8) {
                    i7++;
                }
                ConstraintAnchor constraintAnchor4 = constraintWidget2.mListAnchors[i6].mTarget;
                if (constraintAnchor4 != null) {
                    ConstraintWidget constraintWidget4 = constraintAnchor4.mOwner;
                    if (constraintWidget4.mListAnchors[i2].mTarget != null && constraintWidget4.mListAnchors[i2].mTarget.mOwner == constraintWidget2) {
                        constraintWidget3 = constraintWidget4;
                    }
                }
                if (constraintWidget3 != null) {
                    constraintWidget2 = constraintWidget3;
                } else {
                    z5 = true;
                }
                first = first;
            } else if (i7 == 0 || i7 != i8 || finalValue3 < i9) {
                return false;
            } else {
                int i10 = finalValue3 - i9;
                if (z) {
                    i10 /= i7 + 1;
                    i3 = 1;
                } else if (!z2) {
                    i3 = 1;
                } else if (i7 > 2) {
                    i3 = 1;
                    i10 = (i10 / i7) - 1;
                } else {
                    i3 = 1;
                }
                if (i7 == i3) {
                    if (i == 0) {
                        f = head.getHorizontalBiasPercent();
                    } else {
                        f = head.getVerticalBiasPercent();
                    }
                    int i11 = (int) (finalValue + 0.5f + (i10 * f));
                    if (i == 0) {
                        firstVisibleWidget.setFinalHorizontal(i11, firstVisibleWidget.getWidth() + i11);
                    } else {
                        firstVisibleWidget.setFinalVertical(i11, firstVisibleWidget.getHeight() + i11);
                    }
                    a(firstVisibleWidget, constraintWidgetContainer.getMeasurer(), isRtl);
                    return true;
                } else if (z) {
                    int i12 = finalValue + i10;
                    ConstraintWidget constraintWidget5 = first;
                    boolean z6 = false;
                    while (!z6) {
                        ConstraintAnchor constraintAnchor5 = constraintWidget5.mListAnchors[i2];
                        if (constraintWidget5.getVisibility() == 8) {
                            if (i == 0) {
                                constraintWidget5.setFinalHorizontal(i12, i12);
                                a(constraintWidget5, constraintWidgetContainer.getMeasurer(), isRtl);
                            } else {
                                constraintWidget5.setFinalVertical(i12, i12);
                                a(constraintWidget5, constraintWidgetContainer.getMeasurer());
                            }
                            linearSystem2 = linearSystem;
                            z4 = false;
                        } else {
                            int margin2 = i12 + constraintWidget5.mListAnchors[i2].getMargin();
                            if (i == 0) {
                                constraintWidget5.setFinalHorizontal(margin2, constraintWidget5.getWidth() + margin2);
                                a(constraintWidget5, constraintWidgetContainer.getMeasurer(), isRtl);
                                i4 = margin2 + constraintWidget5.getWidth();
                            } else {
                                constraintWidget5.setFinalVertical(margin2, constraintWidget5.getHeight() + margin2);
                                a(constraintWidget5, constraintWidgetContainer.getMeasurer());
                                i4 = margin2 + constraintWidget5.getHeight();
                            }
                            i12 = i4 + constraintWidget5.mListAnchors[i6].getMargin() + i10;
                            linearSystem2 = linearSystem;
                            z4 = false;
                        }
                        constraintWidget5.addToSolver(linearSystem2, z4);
                        ConstraintAnchor constraintAnchor6 = constraintWidget5.mListAnchors[i6].mTarget;
                        if (constraintAnchor6 != null) {
                            constraintWidget = constraintAnchor6.mOwner;
                            if (constraintWidget.mListAnchors[i2].mTarget == null || constraintWidget.mListAnchors[i2].mTarget.mOwner != constraintWidget5) {
                                constraintWidget = null;
                            }
                        } else {
                            constraintWidget = null;
                        }
                        if (constraintWidget != null) {
                            constraintWidget5 = constraintWidget;
                        } else {
                            z6 = true;
                        }
                    }
                    return true;
                } else if (!z2) {
                    return true;
                } else {
                    if (i7 != 2) {
                        return false;
                    }
                    if (i == 0) {
                        firstVisibleWidget.setFinalHorizontal(finalValue, firstVisibleWidget.getWidth() + finalValue);
                        lastVisibleWidget.setFinalHorizontal(finalValue2 - lastVisibleWidget.getWidth(), finalValue2);
                        a(firstVisibleWidget, constraintWidgetContainer.getMeasurer(), isRtl);
                        a(lastVisibleWidget, constraintWidgetContainer.getMeasurer(), isRtl);
                        return true;
                    }
                    firstVisibleWidget.setFinalVertical(finalValue, firstVisibleWidget.getHeight() + finalValue);
                    lastVisibleWidget.setFinalVertical(finalValue2 - lastVisibleWidget.getHeight(), finalValue2);
                    a(firstVisibleWidget, constraintWidgetContainer.getMeasurer());
                    a(lastVisibleWidget, constraintWidgetContainer.getMeasurer());
                    return true;
                }
            }
        }
    }
}
