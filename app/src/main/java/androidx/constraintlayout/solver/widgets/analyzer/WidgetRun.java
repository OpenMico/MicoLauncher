package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;

/* loaded from: classes.dex */
public abstract class WidgetRun implements Dependency {
    ConstraintWidget b;
    e c;
    protected ConstraintWidget.DimensionBehaviour dimensionBehavior;
    public int matchConstraintsType;
    b d = new b(this);
    public int orientation = 0;
    boolean e = false;
    public DependencyNode start = new DependencyNode(this);
    public DependencyNode end = new DependencyNode(this);
    protected a mRunType = a.NONE;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum a {
        NONE,
        START,
        END,
        CENTER
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean a();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void applyToWidget();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void b();

    abstract void c();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void d();

    @Override // androidx.constraintlayout.solver.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
    }

    protected void updateRunEnd(Dependency dependency) {
    }

    protected void updateRunStart(Dependency dependency) {
    }

    public WidgetRun(ConstraintWidget constraintWidget) {
        this.b = constraintWidget;
    }

    public boolean isDimensionResolved() {
        return this.d.resolved;
    }

    public boolean isCenterConnection() {
        int size = this.start.g.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (this.start.g.get(i2).a != this) {
                i++;
            }
        }
        int size2 = this.end.g.size();
        for (int i3 = 0; i3 < size2; i3++) {
            if (this.end.g.get(i3).a != this) {
                i++;
            }
        }
        return i >= 2;
    }

    public long wrapSize(int i) {
        if (!this.d.resolved) {
            return 0L;
        }
        long j = this.d.value;
        if (isCenterConnection()) {
            return j + (this.start.c - this.end.c);
        }
        if (i == 0) {
            return j + this.start.c;
        }
        return j - this.end.c;
    }

    protected final DependencyNode getTarget(ConstraintAnchor constraintAnchor) {
        if (constraintAnchor.mTarget == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor.mTarget.mOwner;
        switch (constraintAnchor.mTarget.mType) {
            case LEFT:
                return constraintWidget.horizontalRun.start;
            case RIGHT:
                return constraintWidget.horizontalRun.end;
            case TOP:
                return constraintWidget.verticalRun.start;
            case BASELINE:
                return constraintWidget.verticalRun.baseline;
            case BOTTOM:
                return constraintWidget.verticalRun.end;
            default:
                return null;
        }
    }

    protected void updateRunCenter(Dependency dependency, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i) {
        float f;
        DependencyNode target = getTarget(constraintAnchor);
        DependencyNode target2 = getTarget(constraintAnchor2);
        if (target.resolved && target2.resolved) {
            int margin = target.value + constraintAnchor.getMargin();
            int margin2 = target2.value - constraintAnchor2.getMargin();
            int i2 = margin2 - margin;
            if (!this.d.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                a(i, i2);
            }
            if (this.d.resolved) {
                if (this.d.value == i2) {
                    this.start.resolve(margin);
                    this.end.resolve(margin2);
                    return;
                }
                if (i == 0) {
                    f = this.b.getHorizontalBiasPercent();
                } else {
                    f = this.b.getVerticalBiasPercent();
                }
                if (target == target2) {
                    margin = target.value;
                    margin2 = target2.value;
                    f = 0.5f;
                }
                this.start.resolve((int) (margin + 0.5f + (((margin2 - margin) - this.d.value) * f)));
                this.end.resolve(this.start.value + this.d.value);
            }
        }
    }

    private void a(int i, int i2) {
        int i3;
        switch (this.matchConstraintsType) {
            case 0:
                this.d.resolve(getLimitedDimension(i2, i));
                return;
            case 1:
                this.d.resolve(Math.min(getLimitedDimension(this.d.h, i), i2));
                return;
            case 2:
                ConstraintWidget parent = this.b.getParent();
                if (parent != null) {
                    WidgetRun widgetRun = i == 0 ? parent.horizontalRun : parent.verticalRun;
                    if (widgetRun.d.resolved) {
                        this.d.resolve(getLimitedDimension((int) ((widgetRun.d.value * (i == 0 ? this.b.mMatchConstraintPercentWidth : this.b.mMatchConstraintPercentHeight)) + 0.5f), i));
                        return;
                    }
                    return;
                }
                return;
            case 3:
                if (this.b.horizontalRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.b.horizontalRun.matchConstraintsType != 3 || this.b.verticalRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.b.verticalRun.matchConstraintsType != 3) {
                    WidgetRun widgetRun2 = i == 0 ? this.b.verticalRun : this.b.horizontalRun;
                    if (widgetRun2.d.resolved) {
                        float dimensionRatio = this.b.getDimensionRatio();
                        if (i == 1) {
                            i3 = (int) ((widgetRun2.d.value / dimensionRatio) + 0.5f);
                        } else {
                            i3 = (int) ((dimensionRatio * widgetRun2.d.value) + 0.5f);
                        }
                        this.d.resolve(i3);
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected final int getLimitedDimension(int i, int i2) {
        if (i2 == 0) {
            int i3 = this.b.mMatchConstraintMaxWidth;
            int max = Math.max(this.b.mMatchConstraintMinWidth, i);
            if (i3 > 0) {
                max = Math.min(i3, i);
            }
            return max != i ? max : i;
        }
        int i4 = this.b.mMatchConstraintMaxHeight;
        int min = i4 > 0 ? Math.min(i4, i) : Math.max(this.b.mMatchConstraintMinHeight, i);
        return min != i ? min : i;
    }

    protected final DependencyNode getTarget(ConstraintAnchor constraintAnchor, int i) {
        if (constraintAnchor.mTarget == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor.mTarget.mOwner;
        WidgetRun widgetRun = i == 0 ? constraintWidget.horizontalRun : constraintWidget.verticalRun;
        int i2 = AnonymousClass1.a[constraintAnchor.mTarget.mType.ordinal()];
        if (i2 != 5) {
            switch (i2) {
                case 1:
                case 3:
                    return widgetRun.start;
                case 2:
                    break;
                default:
                    return null;
            }
        }
        return widgetRun.end;
    }

    protected final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i) {
        dependencyNode.g.add(dependencyNode2);
        dependencyNode.c = i;
        dependencyNode2.f.add(dependencyNode);
    }

    protected final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i, b bVar) {
        dependencyNode.g.add(dependencyNode2);
        dependencyNode.g.add(this.d);
        dependencyNode.d = i;
        dependencyNode.e = bVar;
        dependencyNode2.f.add(dependencyNode);
        bVar.f.add(dependencyNode);
    }

    public long getWrapDimension() {
        if (this.d.resolved) {
            return this.d.value;
        }
        return 0L;
    }

    public boolean isResolved() {
        return this.e;
    }
}
