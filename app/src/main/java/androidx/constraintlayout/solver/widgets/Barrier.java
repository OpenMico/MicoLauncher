package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.HashMap;

/* loaded from: classes.dex */
public class Barrier extends HelperWidget {
    public static final int BOTTOM = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    private int E = 0;
    private boolean F = true;
    private int G = 0;
    boolean a = false;

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public boolean allowedInBarrier() {
        return true;
    }

    public Barrier() {
    }

    public Barrier(String str) {
        setDebugName(str);
    }

    public int getBarrierType() {
        return this.E;
    }

    public void setBarrierType(int i) {
        this.E = i;
    }

    public void setAllowsGoneWidget(boolean z) {
        this.F = z;
    }

    public boolean allowsGoneWidget() {
        return this.F;
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public boolean isResolvedHorizontally() {
        return this.a;
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public boolean isResolvedVertically() {
        return this.a;
    }

    @Override // androidx.constraintlayout.solver.widgets.HelperWidget, androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        super.copy(constraintWidget, hashMap);
        Barrier barrier = (Barrier) constraintWidget;
        this.E = barrier.E;
        this.F = barrier.F;
        this.G = barrier.G;
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public String toString() {
        String str = "[Barrier] " + getDebugName() + " {";
        for (int i = 0; i < this.mWidgetsCount; i++) {
            ConstraintWidget constraintWidget = this.mWidgets[i];
            if (i > 0) {
                str = str + ", ";
            }
            str = str + constraintWidget.getDebugName();
        }
        return str + "}";
    }

    public void markWidgets() {
        for (int i = 0; i < this.mWidgetsCount; i++) {
            ConstraintWidget constraintWidget = this.mWidgets[i];
            int i2 = this.E;
            if (i2 == 0 || i2 == 1) {
                constraintWidget.setInBarrier(0, true);
            } else if (i2 == 2 || i2 == 3) {
                constraintWidget.setInBarrier(1, true);
            }
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem, boolean z) {
        boolean z2;
        this.mListAnchors[0] = this.mLeft;
        this.mListAnchors[2] = this.mTop;
        this.mListAnchors[1] = this.mRight;
        this.mListAnchors[3] = this.mBottom;
        for (int i = 0; i < this.mListAnchors.length; i++) {
            this.mListAnchors[i].b = linearSystem.createObjectVariable(this.mListAnchors[i]);
        }
        int i2 = this.E;
        if (i2 >= 0 && i2 < 4) {
            ConstraintAnchor constraintAnchor = this.mListAnchors[this.E];
            if (!this.a) {
                allSolved();
            }
            if (this.a) {
                this.a = false;
                int i3 = this.E;
                if (i3 == 0 || i3 == 1) {
                    linearSystem.addEquality(this.mLeft.b, this.mX);
                    linearSystem.addEquality(this.mRight.b, this.mX);
                } else if (i3 == 2 || i3 == 3) {
                    linearSystem.addEquality(this.mTop.b, this.mY);
                    linearSystem.addEquality(this.mBottom.b, this.mY);
                }
            } else {
                int i4 = 0;
                while (true) {
                    if (i4 >= this.mWidgetsCount) {
                        z2 = false;
                        break;
                    }
                    ConstraintWidget constraintWidget = this.mWidgets[i4];
                    if (this.F || constraintWidget.allowedInBarrier()) {
                        int i5 = this.E;
                        if ((i5 == 0 || i5 == 1) && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mLeft.mTarget != null && constraintWidget.mRight.mTarget != null) {
                            z2 = true;
                            break;
                        }
                        int i6 = this.E;
                        if ((i6 == 2 || i6 == 3) && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget != null) {
                            z2 = true;
                            break;
                        }
                    }
                    i4++;
                }
                boolean z3 = this.mLeft.hasCenteredDependents() || this.mRight.hasCenteredDependents();
                boolean z4 = this.mTop.hasCenteredDependents() || this.mBottom.hasCenteredDependents();
                boolean z5 = !z2 && ((this.E == 0 && z3) || ((this.E == 2 && z4) || ((this.E == 1 && z3) || (this.E == 3 && z4))));
                int i7 = 5;
                if (!z5) {
                    i7 = 4;
                }
                for (int i8 = 0; i8 < this.mWidgetsCount; i8++) {
                    ConstraintWidget constraintWidget2 = this.mWidgets[i8];
                    if (this.F || constraintWidget2.allowedInBarrier()) {
                        SolverVariable createObjectVariable = linearSystem.createObjectVariable(constraintWidget2.mListAnchors[this.E]);
                        constraintWidget2.mListAnchors[this.E].b = createObjectVariable;
                        int i9 = (constraintWidget2.mListAnchors[this.E].mTarget == null || constraintWidget2.mListAnchors[this.E].mTarget.mOwner != this) ? 0 : constraintWidget2.mListAnchors[this.E].mMargin + 0;
                        int i10 = this.E;
                        if (i10 == 0 || i10 == 2) {
                            linearSystem.addLowerBarrier(constraintAnchor.b, createObjectVariable, this.G - i9, z2);
                        } else {
                            linearSystem.addGreaterBarrier(constraintAnchor.b, createObjectVariable, this.G + i9, z2);
                        }
                        linearSystem.addEquality(constraintAnchor.b, createObjectVariable, this.G + i9, i7);
                    }
                }
                int i11 = this.E;
                if (i11 == 0) {
                    linearSystem.addEquality(this.mRight.b, this.mLeft.b, 0, 8);
                    linearSystem.addEquality(this.mLeft.b, this.mParent.mRight.b, 0, 4);
                    linearSystem.addEquality(this.mLeft.b, this.mParent.mLeft.b, 0, 0);
                } else if (i11 == 1) {
                    linearSystem.addEquality(this.mLeft.b, this.mRight.b, 0, 8);
                    linearSystem.addEquality(this.mLeft.b, this.mParent.mLeft.b, 0, 4);
                    linearSystem.addEquality(this.mLeft.b, this.mParent.mRight.b, 0, 0);
                } else if (i11 == 2) {
                    linearSystem.addEquality(this.mBottom.b, this.mTop.b, 0, 8);
                    linearSystem.addEquality(this.mTop.b, this.mParent.mBottom.b, 0, 4);
                    linearSystem.addEquality(this.mTop.b, this.mParent.mTop.b, 0, 0);
                } else if (i11 == 3) {
                    linearSystem.addEquality(this.mTop.b, this.mBottom.b, 0, 8);
                    linearSystem.addEquality(this.mTop.b, this.mParent.mTop.b, 0, 4);
                    linearSystem.addEquality(this.mTop.b, this.mParent.mBottom.b, 0, 0);
                }
            }
        }
    }

    public void setMargin(int i) {
        this.G = i;
    }

    public int getMargin() {
        return this.G;
    }

    public int getOrientation() {
        switch (this.E) {
            case 0:
            case 1:
                return 0;
            case 2:
            case 3:
                return 1;
            default:
                return -1;
        }
    }

    public boolean allSolved() {
        boolean z = true;
        for (int i = 0; i < this.mWidgetsCount; i++) {
            ConstraintWidget constraintWidget = this.mWidgets[i];
            if (this.F || constraintWidget.allowedInBarrier()) {
                int i2 = this.E;
                if ((i2 == 0 || i2 == 1) && !constraintWidget.isResolvedHorizontally()) {
                    z = false;
                } else {
                    int i3 = this.E;
                    if ((i3 == 2 || i3 == 3) && !constraintWidget.isResolvedVertically()) {
                        z = false;
                    }
                }
            }
        }
        if (!z || this.mWidgetsCount <= 0) {
            return false;
        }
        int i4 = 0;
        boolean z2 = false;
        for (int i5 = 0; i5 < this.mWidgetsCount; i5++) {
            ConstraintWidget constraintWidget2 = this.mWidgets[i5];
            if (this.F || constraintWidget2.allowedInBarrier()) {
                if (!z2) {
                    int i6 = this.E;
                    if (i6 == 0) {
                        i4 = constraintWidget2.getAnchor(ConstraintAnchor.Type.LEFT).getFinalValue();
                    } else if (i6 == 1) {
                        i4 = constraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT).getFinalValue();
                    } else if (i6 == 2) {
                        i4 = constraintWidget2.getAnchor(ConstraintAnchor.Type.TOP).getFinalValue();
                    } else if (i6 == 3) {
                        i4 = constraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM).getFinalValue();
                    }
                    z2 = true;
                }
                int i7 = this.E;
                if (i7 == 0) {
                    i4 = Math.min(i4, constraintWidget2.getAnchor(ConstraintAnchor.Type.LEFT).getFinalValue());
                } else if (i7 == 1) {
                    i4 = Math.max(i4, constraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT).getFinalValue());
                } else if (i7 == 2) {
                    i4 = Math.min(i4, constraintWidget2.getAnchor(ConstraintAnchor.Type.TOP).getFinalValue());
                } else if (i7 == 3) {
                    i4 = Math.max(i4, constraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM).getFinalValue());
                }
            }
        }
        int i8 = i4 + this.G;
        int i9 = this.E;
        if (i9 == 0 || i9 == 1) {
            setFinalHorizontal(i8, i8);
        } else {
            setFinalVertical(i8, i8);
        }
        this.a = true;
        return true;
    }
}
