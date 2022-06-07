package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;

/* loaded from: classes.dex */
public class VerticalWidgetRun extends WidgetRun {
    public DependencyNode baseline = new DependencyNode(this);
    b a = null;

    public VerticalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.b = DependencyNode.a.TOP;
        this.end.b = DependencyNode.a.BOTTOM;
        this.baseline.b = DependencyNode.a.BASELINE;
        this.orientation = 1;
    }

    public String toString() {
        return "VerticalRun " + this.b.getDebugName();
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void b() {
        this.c = null;
        this.start.clear();
        this.end.clear();
        this.baseline.clear();
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
        this.baseline.clear();
        this.baseline.resolved = false;
        this.d.resolved = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public boolean a() {
        return this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.b.mMatchConstraintDefaultHeight == 0;
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
                updateRunCenter(dependency, this.b.mTop, this.b.mBottom, 1);
                return;
        }
        if (this.d.readyToSolve && !this.d.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            switch (this.b.mMatchConstraintDefaultHeight) {
                case 2:
                    ConstraintWidget parent = this.b.getParent();
                    if (parent != null && parent.verticalRun.d.resolved) {
                        this.d.resolve((int) ((parent.verticalRun.d.value * this.b.mMatchConstraintPercentHeight) + 0.5f));
                        break;
                    }
                    break;
                case 3:
                    if (this.b.horizontalRun.d.resolved) {
                        switch (this.b.getDimensionRatioSide()) {
                            case -1:
                                i = (int) ((this.b.horizontalRun.d.value / this.b.getDimensionRatio()) + 0.5f);
                                break;
                            case 0:
                                i = (int) ((this.b.horizontalRun.d.value * this.b.getDimensionRatio()) + 0.5f);
                                break;
                            case 1:
                                i = (int) ((this.b.horizontalRun.d.value / this.b.getDimensionRatio()) + 0.5f);
                                break;
                            default:
                                i = 0;
                                break;
                        }
                        this.d.resolve(i);
                        break;
                    }
                    break;
            }
        }
        if (this.start.readyToSolve && this.end.readyToSolve) {
            if (this.start.resolved && this.end.resolved && this.d.resolved) {
                return;
            }
            if (this.d.resolved || this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.b.mMatchConstraintDefaultWidth != 0 || this.b.isInVerticalChain()) {
                if (!this.d.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.g.size() > 0 && this.end.g.size() > 0) {
                    int i2 = (this.end.g.get(0).value + this.end.c) - (this.start.g.get(0).value + this.start.c);
                    if (i2 < this.d.h) {
                        this.d.resolve(i2);
                    } else {
                        this.d.resolve(this.d.h);
                    }
                }
                if (this.d.resolved && this.start.g.size() > 0 && this.end.g.size() > 0) {
                    DependencyNode dependencyNode = this.start.g.get(0);
                    DependencyNode dependencyNode2 = this.end.g.get(0);
                    int i3 = dependencyNode.value + this.start.c;
                    int i4 = dependencyNode2.value + this.end.c;
                    float verticalBiasPercent = this.b.getVerticalBiasPercent();
                    if (dependencyNode == dependencyNode2) {
                        i3 = dependencyNode.value;
                        i4 = dependencyNode2.value;
                        verticalBiasPercent = 0.5f;
                    }
                    this.start.resolve((int) (i3 + 0.5f + (((i4 - i3) - this.d.value) * verticalBiasPercent)));
                    this.end.resolve(this.start.value + this.d.value);
                    return;
                }
                return;
            }
            int i5 = this.start.g.get(0).value + this.start.c;
            int i6 = this.end.g.get(0).value + this.end.c;
            this.start.resolve(i5);
            this.end.resolve(i6);
            this.d.resolve(i6 - i5);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void d() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        if (this.b.measured) {
            this.d.resolve(this.b.getHeight());
        }
        if (!this.d.resolved) {
            this.dimensionBehavior = this.b.getVerticalDimensionBehaviour();
            if (this.b.hasBaseline()) {
                this.a = new a(this);
            }
            if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent2 = this.b.getParent()) != null && parent2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
                    int height = (parent2.getHeight() - this.b.mTop.getMargin()) - this.b.mBottom.getMargin();
                    addTarget(this.start, parent2.verticalRun.start, this.b.mTop.getMargin());
                    addTarget(this.end, parent2.verticalRun.end, -this.b.mBottom.getMargin());
                    this.d.resolve(height);
                    return;
                } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.d.resolve(this.b.getHeight());
                }
            }
        } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent = this.b.getParent()) != null && parent.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
            addTarget(this.start, parent.verticalRun.start, this.b.mTop.getMargin());
            addTarget(this.end, parent.verticalRun.end, -this.b.mBottom.getMargin());
            return;
        }
        if (!this.d.resolved || !this.b.measured) {
            if (!this.d.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                switch (this.b.mMatchConstraintDefaultHeight) {
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
                        if (!this.b.isInVerticalChain() && this.b.mMatchConstraintDefaultWidth != 3) {
                            b bVar2 = this.b.horizontalRun.d;
                            this.d.g.add(bVar2);
                            bVar2.f.add(this.d);
                            this.d.delegateToWidgetRun = true;
                            this.d.f.add(this.start);
                            this.d.f.add(this.end);
                            break;
                        }
                        break;
                }
            } else {
                this.d.addDependency(this);
            }
            if (this.b.mListAnchors[2].mTarget != null && this.b.mListAnchors[3].mTarget != null) {
                if (this.b.isInVerticalChain()) {
                    this.start.c = this.b.mListAnchors[2].getMargin();
                    this.end.c = -this.b.mListAnchors[3].getMargin();
                } else {
                    DependencyNode target = getTarget(this.b.mListAnchors[2]);
                    DependencyNode target2 = getTarget(this.b.mListAnchors[3]);
                    target.addDependency(this);
                    target2.addDependency(this);
                    this.mRunType = WidgetRun.a.CENTER;
                }
                if (this.b.hasBaseline()) {
                    addTarget(this.baseline, this.start, 1, this.a);
                }
            } else if (this.b.mListAnchors[2].mTarget != null) {
                DependencyNode target3 = getTarget(this.b.mListAnchors[2]);
                if (target3 != null) {
                    addTarget(this.start, target3, this.b.mListAnchors[2].getMargin());
                    addTarget(this.end, this.start, 1, this.d);
                    if (this.b.hasBaseline()) {
                        addTarget(this.baseline, this.start, 1, this.a);
                    }
                    if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.b.getDimensionRatio() > 0.0f && this.b.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        this.b.horizontalRun.d.f.add(this.d);
                        this.d.g.add(this.b.horizontalRun.d);
                        this.d.updateDelegate = this;
                    }
                }
            } else if (this.b.mListAnchors[3].mTarget != null) {
                DependencyNode target4 = getTarget(this.b.mListAnchors[3]);
                if (target4 != null) {
                    addTarget(this.end, target4, -this.b.mListAnchors[3].getMargin());
                    addTarget(this.start, this.end, -1, this.d);
                    if (this.b.hasBaseline()) {
                        addTarget(this.baseline, this.start, 1, this.a);
                    }
                }
            } else if (this.b.mListAnchors[4].mTarget != null) {
                DependencyNode target5 = getTarget(this.b.mListAnchors[4]);
                if (target5 != null) {
                    addTarget(this.baseline, target5, 0);
                    addTarget(this.start, this.baseline, -1, this.a);
                    addTarget(this.end, this.start, 1, this.d);
                }
            } else if (!(this.b instanceof Helper) && this.b.getParent() != null) {
                addTarget(this.start, this.b.getParent().verticalRun.start, this.b.getY());
                addTarget(this.end, this.start, 1, this.d);
                if (this.b.hasBaseline()) {
                    addTarget(this.baseline, this.start, 1, this.a);
                }
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.b.getDimensionRatio() > 0.0f && this.b.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.b.horizontalRun.d.f.add(this.d);
                    this.d.g.add(this.b.horizontalRun.d);
                    this.d.updateDelegate = this;
                }
            }
            if (this.d.g.size() == 0) {
                this.d.readyToSolve = true;
            }
        } else if (this.b.mListAnchors[2].mTarget != null && this.b.mListAnchors[3].mTarget != null) {
            if (this.b.isInVerticalChain()) {
                this.start.c = this.b.mListAnchors[2].getMargin();
                this.end.c = -this.b.mListAnchors[3].getMargin();
            } else {
                DependencyNode target6 = getTarget(this.b.mListAnchors[2]);
                if (target6 != null) {
                    addTarget(this.start, target6, this.b.mListAnchors[2].getMargin());
                }
                DependencyNode target7 = getTarget(this.b.mListAnchors[3]);
                if (target7 != null) {
                    addTarget(this.end, target7, -this.b.mListAnchors[3].getMargin());
                }
                this.start.delegateToWidgetRun = true;
                this.end.delegateToWidgetRun = true;
            }
            if (this.b.hasBaseline()) {
                addTarget(this.baseline, this.start, this.b.getBaselineDistance());
            }
        } else if (this.b.mListAnchors[2].mTarget != null) {
            DependencyNode target8 = getTarget(this.b.mListAnchors[2]);
            if (target8 != null) {
                addTarget(this.start, target8, this.b.mListAnchors[2].getMargin());
                addTarget(this.end, this.start, this.d.value);
                if (this.b.hasBaseline()) {
                    addTarget(this.baseline, this.start, this.b.getBaselineDistance());
                }
            }
        } else if (this.b.mListAnchors[3].mTarget != null) {
            DependencyNode target9 = getTarget(this.b.mListAnchors[3]);
            if (target9 != null) {
                addTarget(this.end, target9, -this.b.mListAnchors[3].getMargin());
                addTarget(this.start, this.end, -this.d.value);
            }
            if (this.b.hasBaseline()) {
                addTarget(this.baseline, this.start, this.b.getBaselineDistance());
            }
        } else if (this.b.mListAnchors[4].mTarget != null) {
            DependencyNode target10 = getTarget(this.b.mListAnchors[4]);
            if (target10 != null) {
                addTarget(this.baseline, target10, 0);
                addTarget(this.start, this.baseline, -this.b.getBaselineDistance());
                addTarget(this.end, this.start, this.d.value);
            }
        } else if (!(this.b instanceof Helper) && this.b.getParent() != null && this.b.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null) {
            addTarget(this.start, this.b.getParent().verticalRun.start, this.b.getY());
            addTarget(this.end, this.start, this.d.value);
            if (this.b.hasBaseline()) {
                addTarget(this.baseline, this.start, this.b.getBaselineDistance());
            }
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        if (this.start.resolved) {
            this.b.setY(this.start.value);
        }
    }
}
