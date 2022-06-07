package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: HelperReferences.java */
/* loaded from: classes.dex */
public class d extends WidgetRun {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public boolean a() {
        return false;
    }

    public d(ConstraintWidget constraintWidget) {
        super(constraintWidget);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void b() {
        this.c = null;
        this.start.clear();
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    void c() {
        this.start.resolved = false;
    }

    private void a(DependencyNode dependencyNode) {
        this.start.f.add(dependencyNode);
        dependencyNode.g.add(this.start);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void d() {
        if (this.b instanceof Barrier) {
            this.start.delegateToWidgetRun = true;
            Barrier barrier = (Barrier) this.b;
            int barrierType = barrier.getBarrierType();
            boolean allowsGoneWidget = barrier.allowsGoneWidget();
            int i = 0;
            switch (barrierType) {
                case 0:
                    this.start.b = DependencyNode.a.LEFT;
                    while (i < barrier.mWidgetsCount) {
                        ConstraintWidget constraintWidget = barrier.mWidgets[i];
                        if (allowsGoneWidget || constraintWidget.getVisibility() != 8) {
                            DependencyNode dependencyNode = constraintWidget.horizontalRun.start;
                            dependencyNode.f.add(this.start);
                            this.start.g.add(dependencyNode);
                        }
                        i++;
                    }
                    a(this.b.horizontalRun.start);
                    a(this.b.horizontalRun.end);
                    return;
                case 1:
                    this.start.b = DependencyNode.a.RIGHT;
                    while (i < barrier.mWidgetsCount) {
                        ConstraintWidget constraintWidget2 = barrier.mWidgets[i];
                        if (allowsGoneWidget || constraintWidget2.getVisibility() != 8) {
                            DependencyNode dependencyNode2 = constraintWidget2.horizontalRun.end;
                            dependencyNode2.f.add(this.start);
                            this.start.g.add(dependencyNode2);
                        }
                        i++;
                    }
                    a(this.b.horizontalRun.start);
                    a(this.b.horizontalRun.end);
                    return;
                case 2:
                    this.start.b = DependencyNode.a.TOP;
                    while (i < barrier.mWidgetsCount) {
                        ConstraintWidget constraintWidget3 = barrier.mWidgets[i];
                        if (allowsGoneWidget || constraintWidget3.getVisibility() != 8) {
                            DependencyNode dependencyNode3 = constraintWidget3.verticalRun.start;
                            dependencyNode3.f.add(this.start);
                            this.start.g.add(dependencyNode3);
                        }
                        i++;
                    }
                    a(this.b.verticalRun.start);
                    a(this.b.verticalRun.end);
                    return;
                case 3:
                    this.start.b = DependencyNode.a.BOTTOM;
                    while (i < barrier.mWidgetsCount) {
                        ConstraintWidget constraintWidget4 = barrier.mWidgets[i];
                        if (allowsGoneWidget || constraintWidget4.getVisibility() != 8) {
                            DependencyNode dependencyNode4 = constraintWidget4.verticalRun.end;
                            dependencyNode4.f.add(this.start);
                            this.start.g.add(dependencyNode4);
                        }
                        i++;
                    }
                    a(this.b.verticalRun.start);
                    a(this.b.verticalRun.end);
                    return;
                default:
                    return;
            }
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun, androidx.constraintlayout.solver.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        Barrier barrier = (Barrier) this.b;
        int barrierType = barrier.getBarrierType();
        int i = 0;
        int i2 = -1;
        for (DependencyNode dependencyNode : this.start.g) {
            int i3 = dependencyNode.value;
            if (i2 == -1 || i3 < i2) {
                i2 = i3;
            }
            if (i < i3) {
                i = i3;
            }
        }
        if (barrierType == 0 || barrierType == 2) {
            this.start.resolve(i2 + barrier.getMargin());
        } else {
            this.start.resolve(i + barrier.getMargin());
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        if (this.b instanceof Barrier) {
            int barrierType = ((Barrier) this.b).getBarrierType();
            if (barrierType == 0 || barrierType == 1) {
                this.b.setX(this.start.value);
            } else {
                this.b.setY(this.start.value);
            }
        }
    }
}
