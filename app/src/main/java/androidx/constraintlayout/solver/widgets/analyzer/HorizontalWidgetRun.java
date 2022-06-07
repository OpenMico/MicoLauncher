package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;

/* loaded from: classes.dex */
public class HorizontalWidgetRun extends WidgetRun {
    private static int[] a = new int[2];

    public HorizontalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.b = DependencyNode.a.LEFT;
        this.end.b = DependencyNode.a.RIGHT;
        this.orientation = 0;
    }

    public String toString() {
        return "HorizontalRun " + this.b.getDebugName();
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void b() {
        this.c = null;
        this.start.clear();
        this.end.clear();
        this.d.clear();
        this.e = false;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void c() {
        this.e = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.d.resolved = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public boolean a() {
        return this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.b.mMatchConstraintDefaultWidth == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void d() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        if (this.b.measured) {
            this.d.resolve(this.b.getWidth());
        }
        if (!this.d.resolved) {
            this.dimensionBehavior = this.b.getHorizontalDimensionBehaviour();
            if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (((parent2 = this.b.getParent()) != null && parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
                    int width = (parent2.getWidth() - this.b.mLeft.getMargin()) - this.b.mRight.getMargin();
                    addTarget(this.start, parent2.horizontalRun.start, this.b.mLeft.getMargin());
                    addTarget(this.end, parent2.horizontalRun.end, -this.b.mRight.getMargin());
                    this.d.resolve(width);
                    return;
                } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.d.resolve(this.b.getWidth());
                }
            }
        } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (((parent = this.b.getParent()) != null && parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
            addTarget(this.start, parent.horizontalRun.start, this.b.mLeft.getMargin());
            addTarget(this.end, parent.horizontalRun.end, -this.b.mRight.getMargin());
            return;
        }
        if (!this.d.resolved || !this.b.measured) {
            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                switch (this.b.mMatchConstraintDefaultWidth) {
                    case 2:
                        ConstraintWidget parent3 = this.b.getParent();
                        if (parent3 != null) {
                            b bVar = parent3.verticalRun.d;
                            this.d.g.add(bVar);
                            bVar.f.add(this.d);
                            this.d.delegateToWidgetRun = true;
                            this.d.f.add(this.start);
                            this.d.f.add(this.end);
                            break;
                        }
                        break;
                    case 3:
                        if (this.b.mMatchConstraintDefaultHeight != 3) {
                            b bVar2 = this.b.verticalRun.d;
                            this.d.g.add(bVar2);
                            bVar2.f.add(this.d);
                            this.b.verticalRun.start.f.add(this.d);
                            this.b.verticalRun.end.f.add(this.d);
                            this.d.delegateToWidgetRun = true;
                            this.d.f.add(this.start);
                            this.d.f.add(this.end);
                            this.start.g.add(this.d);
                            this.end.g.add(this.d);
                            break;
                        } else {
                            this.start.updateDelegate = this;
                            this.end.updateDelegate = this;
                            this.b.verticalRun.start.updateDelegate = this;
                            this.b.verticalRun.end.updateDelegate = this;
                            this.d.updateDelegate = this;
                            if (!this.b.isInVerticalChain()) {
                                if (!this.b.isInHorizontalChain()) {
                                    this.b.verticalRun.d.g.add(this.d);
                                    break;
                                } else {
                                    this.b.verticalRun.d.g.add(this.d);
                                    this.d.f.add(this.b.verticalRun.d);
                                    break;
                                }
                            } else {
                                this.d.g.add(this.b.verticalRun.d);
                                this.b.verticalRun.d.f.add(this.d);
                                this.b.verticalRun.d.updateDelegate = this;
                                this.d.g.add(this.b.verticalRun.start);
                                this.d.g.add(this.b.verticalRun.end);
                                this.b.verticalRun.start.f.add(this.d);
                                this.b.verticalRun.end.f.add(this.d);
                                break;
                            }
                        }
                }
            }
            if (this.b.mListAnchors[0].mTarget == null || this.b.mListAnchors[1].mTarget == null) {
                if (this.b.mListAnchors[0].mTarget != null) {
                    DependencyNode target = getTarget(this.b.mListAnchors[0]);
                    if (target != null) {
                        addTarget(this.start, target, this.b.mListAnchors[0].getMargin());
                        addTarget(this.end, this.start, 1, this.d);
                    }
                } else if (this.b.mListAnchors[1].mTarget != null) {
                    DependencyNode target2 = getTarget(this.b.mListAnchors[1]);
                    if (target2 != null) {
                        addTarget(this.end, target2, -this.b.mListAnchors[1].getMargin());
                        addTarget(this.start, this.end, -1, this.d);
                    }
                } else if (!(this.b instanceof Helper) && this.b.getParent() != null) {
                    addTarget(this.start, this.b.getParent().horizontalRun.start, this.b.getX());
                    addTarget(this.end, this.start, 1, this.d);
                }
            } else if (this.b.isInHorizontalChain()) {
                this.start.c = this.b.mListAnchors[0].getMargin();
                this.end.c = -this.b.mListAnchors[1].getMargin();
            } else {
                DependencyNode target3 = getTarget(this.b.mListAnchors[0]);
                DependencyNode target4 = getTarget(this.b.mListAnchors[1]);
                target3.addDependency(this);
                target4.addDependency(this);
                this.mRunType = WidgetRun.a.CENTER;
            }
        } else if (this.b.mListAnchors[0].mTarget == null || this.b.mListAnchors[1].mTarget == null) {
            if (this.b.mListAnchors[0].mTarget != null) {
                DependencyNode target5 = getTarget(this.b.mListAnchors[0]);
                if (target5 != null) {
                    addTarget(this.start, target5, this.b.mListAnchors[0].getMargin());
                    addTarget(this.end, this.start, this.d.value);
                }
            } else if (this.b.mListAnchors[1].mTarget != null) {
                DependencyNode target6 = getTarget(this.b.mListAnchors[1]);
                if (target6 != null) {
                    addTarget(this.end, target6, -this.b.mListAnchors[1].getMargin());
                    addTarget(this.start, this.end, -this.d.value);
                }
            } else if (!(this.b instanceof Helper) && this.b.getParent() != null && this.b.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null) {
                addTarget(this.start, this.b.getParent().horizontalRun.start, this.b.getX());
                addTarget(this.end, this.start, this.d.value);
            }
        } else if (this.b.isInHorizontalChain()) {
            this.start.c = this.b.mListAnchors[0].getMargin();
            this.end.c = -this.b.mListAnchors[1].getMargin();
        } else {
            DependencyNode target7 = getTarget(this.b.mListAnchors[0]);
            if (target7 != null) {
                addTarget(this.start, target7, this.b.mListAnchors[0].getMargin());
            }
            DependencyNode target8 = getTarget(this.b.mListAnchors[1]);
            if (target8 != null) {
                addTarget(this.end, target8, -this.b.mListAnchors[1].getMargin());
            }
            this.start.delegateToWidgetRun = true;
            this.end.delegateToWidgetRun = true;
        }
    }

    private void a(int[] iArr, int i, int i2, int i3, int i4, float f, int i5) {
        int i6 = i2 - i;
        int i7 = i4 - i3;
        switch (i5) {
            case -1:
                int i8 = (int) ((i7 * f) + 0.5f);
                int i9 = (int) ((i6 / f) + 0.5f);
                if (i8 <= i6 && i7 <= i7) {
                    iArr[0] = i8;
                    iArr[1] = i7;
                    return;
                } else if (i6 <= i6 && i9 <= i7) {
                    iArr[0] = i6;
                    iArr[1] = i9;
                    return;
                } else {
                    return;
                }
            case 0:
                iArr[0] = (int) ((i7 * f) + 0.5f);
                iArr[1] = i7;
                return;
            case 1:
                iArr[0] = i6;
                iArr[1] = (int) ((i6 * f) + 0.5f);
                return;
            default:
                return;
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun, androidx.constraintlayout.solver.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        int i;
        switch (this.mRunType) {
            case START:
                updateRunStart(dependency);
                break;
            case END:
                updateRunEnd(dependency);
                break;
            case CENTER:
                updateRunCenter(dependency, this.b.mLeft, this.b.mRight, 0);
                return;
        }
        if (!this.d.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            switch (this.b.mMatchConstraintDefaultWidth) {
                case 2:
                    ConstraintWidget parent = this.b.getParent();
                    if (parent != null && parent.horizontalRun.d.resolved) {
                        this.d.resolve((int) ((parent.horizontalRun.d.value * this.b.mMatchConstraintPercentWidth) + 0.5f));
                        break;
                    }
                    break;
                case 3:
                    if (this.b.mMatchConstraintDefaultHeight != 0 && this.b.mMatchConstraintDefaultHeight != 3) {
                        switch (this.b.getDimensionRatioSide()) {
                            case -1:
                                i = (int) ((this.b.verticalRun.d.value * this.b.getDimensionRatio()) + 0.5f);
                                break;
                            case 0:
                                i = (int) ((this.b.verticalRun.d.value / this.b.getDimensionRatio()) + 0.5f);
                                break;
                            case 1:
                                i = (int) ((this.b.verticalRun.d.value * this.b.getDimensionRatio()) + 0.5f);
                                break;
                            default:
                                i = 0;
                                break;
                        }
                        this.d.resolve(i);
                        break;
                    } else {
                        DependencyNode dependencyNode = this.b.verticalRun.start;
                        DependencyNode dependencyNode2 = this.b.verticalRun.end;
                        boolean z = this.b.mLeft.mTarget != null;
                        boolean z2 = this.b.mTop.mTarget != null;
                        boolean z3 = this.b.mRight.mTarget != null;
                        boolean z4 = this.b.mBottom.mTarget != null;
                        int dimensionRatioSide = this.b.getDimensionRatioSide();
                        if (!z || !z2 || !z3 || !z4) {
                            if (z && z3) {
                                if (this.start.readyToSolve && this.end.readyToSolve) {
                                    float dimensionRatio = this.b.getDimensionRatio();
                                    int i2 = this.start.g.get(0).value + this.start.c;
                                    int i3 = this.end.g.get(0).value - this.end.c;
                                    switch (dimensionRatioSide) {
                                        case -1:
                                        case 0:
                                            int limitedDimension = getLimitedDimension(i3 - i2, 0);
                                            int i4 = (int) ((limitedDimension * dimensionRatio) + 0.5f);
                                            int limitedDimension2 = getLimitedDimension(i4, 1);
                                            if (i4 != limitedDimension2) {
                                                limitedDimension = (int) ((limitedDimension2 / dimensionRatio) + 0.5f);
                                            }
                                            this.d.resolve(limitedDimension);
                                            this.b.verticalRun.d.resolve(limitedDimension2);
                                            break;
                                        case 1:
                                            int limitedDimension3 = getLimitedDimension(i3 - i2, 0);
                                            int i5 = (int) ((limitedDimension3 / dimensionRatio) + 0.5f);
                                            int limitedDimension4 = getLimitedDimension(i5, 1);
                                            if (i5 != limitedDimension4) {
                                                limitedDimension3 = (int) ((limitedDimension4 * dimensionRatio) + 0.5f);
                                            }
                                            this.d.resolve(limitedDimension3);
                                            this.b.verticalRun.d.resolve(limitedDimension4);
                                            break;
                                    }
                                } else {
                                    return;
                                }
                            } else if (z2 && z4) {
                                if (dependencyNode.readyToSolve && dependencyNode2.readyToSolve) {
                                    float dimensionRatio2 = this.b.getDimensionRatio();
                                    int i6 = dependencyNode.g.get(0).value + dependencyNode.c;
                                    int i7 = dependencyNode2.g.get(0).value - dependencyNode2.c;
                                    switch (dimensionRatioSide) {
                                        case -1:
                                        case 1:
                                            int limitedDimension5 = getLimitedDimension(i7 - i6, 1);
                                            int i8 = (int) ((limitedDimension5 / dimensionRatio2) + 0.5f);
                                            int limitedDimension6 = getLimitedDimension(i8, 0);
                                            if (i8 != limitedDimension6) {
                                                limitedDimension5 = (int) ((limitedDimension6 * dimensionRatio2) + 0.5f);
                                            }
                                            this.d.resolve(limitedDimension6);
                                            this.b.verticalRun.d.resolve(limitedDimension5);
                                            break;
                                        case 0:
                                            int limitedDimension7 = getLimitedDimension(i7 - i6, 1);
                                            int i9 = (int) ((limitedDimension7 * dimensionRatio2) + 0.5f);
                                            int limitedDimension8 = getLimitedDimension(i9, 0);
                                            if (i9 != limitedDimension8) {
                                                limitedDimension7 = (int) ((limitedDimension8 / dimensionRatio2) + 0.5f);
                                            }
                                            this.d.resolve(limitedDimension8);
                                            this.b.verticalRun.d.resolve(limitedDimension7);
                                            break;
                                    }
                                } else {
                                    return;
                                }
                            }
                        } else {
                            float dimensionRatio3 = this.b.getDimensionRatio();
                            if (!dependencyNode.resolved || !dependencyNode2.resolved) {
                                if (this.start.resolved && this.end.resolved) {
                                    if (dependencyNode.readyToSolve && dependencyNode2.readyToSolve) {
                                        a(a, this.start.value + this.start.c, this.end.value - this.end.c, dependencyNode.g.get(0).value + dependencyNode.c, dependencyNode2.g.get(0).value - dependencyNode2.c, dimensionRatio3, dimensionRatioSide);
                                        this.d.resolve(a[0]);
                                        this.b.verticalRun.d.resolve(a[1]);
                                    } else {
                                        return;
                                    }
                                }
                                if (this.start.readyToSolve && this.end.readyToSolve && dependencyNode.readyToSolve && dependencyNode2.readyToSolve) {
                                    a(a, this.start.g.get(0).value + this.start.c, this.end.g.get(0).value - this.end.c, dependencyNode.g.get(0).value + dependencyNode.c, dependencyNode2.g.get(0).value - dependencyNode2.c, dimensionRatio3, dimensionRatioSide);
                                    this.d.resolve(a[0]);
                                    this.b.verticalRun.d.resolve(a[1]);
                                    break;
                                } else {
                                    return;
                                }
                            } else if (this.start.readyToSolve && this.end.readyToSolve) {
                                a(a, this.start.g.get(0).value + this.start.c, this.end.g.get(0).value - this.end.c, dependencyNode.value + dependencyNode.c, dependencyNode2.value - dependencyNode2.c, dimensionRatio3, dimensionRatioSide);
                                this.d.resolve(a[0]);
                                this.b.verticalRun.d.resolve(a[1]);
                                return;
                            } else {
                                return;
                            }
                        }
                    }
                    break;
            }
        }
        if (this.start.readyToSolve && this.end.readyToSolve) {
            if (this.start.resolved && this.end.resolved && this.d.resolved) {
                return;
            }
            if (this.d.resolved || this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.b.mMatchConstraintDefaultWidth != 0 || this.b.isInHorizontalChain()) {
                if (!this.d.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.g.size() > 0 && this.end.g.size() > 0) {
                    int min = Math.min((this.end.g.get(0).value + this.end.c) - (this.start.g.get(0).value + this.start.c), this.d.h);
                    int i10 = this.b.mMatchConstraintMaxWidth;
                    int max = Math.max(this.b.mMatchConstraintMinWidth, min);
                    if (i10 > 0) {
                        max = Math.min(i10, max);
                    }
                    this.d.resolve(max);
                }
                if (this.d.resolved) {
                    DependencyNode dependencyNode3 = this.start.g.get(0);
                    DependencyNode dependencyNode4 = this.end.g.get(0);
                    int i11 = dependencyNode3.value + this.start.c;
                    int i12 = dependencyNode4.value + this.end.c;
                    float horizontalBiasPercent = this.b.getHorizontalBiasPercent();
                    if (dependencyNode3 == dependencyNode4) {
                        i11 = dependencyNode3.value;
                        i12 = dependencyNode4.value;
                        horizontalBiasPercent = 0.5f;
                    }
                    this.start.resolve((int) (i11 + 0.5f + (((i12 - i11) - this.d.value) * horizontalBiasPercent)));
                    this.end.resolve(this.start.value + this.d.value);
                    return;
                }
                return;
            }
            int i13 = this.start.g.get(0).value + this.start.c;
            int i14 = this.end.g.get(0).value + this.end.c;
            this.start.resolve(i13);
            this.end.resolve(i14);
            this.d.resolve(i14 - i13);
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        if (this.start.resolved) {
            this.b.setX(this.start.value);
        }
    }
}
