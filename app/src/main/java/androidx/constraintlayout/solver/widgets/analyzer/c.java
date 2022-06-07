package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Guideline;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GuidelineReference.java */
/* loaded from: classes.dex */
public class c extends WidgetRun {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public boolean a() {
        return false;
    }

    public c(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        constraintWidget.horizontalRun.b();
        constraintWidget.verticalRun.b();
        this.orientation = ((Guideline) constraintWidget).getOrientation();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void b() {
        this.start.clear();
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    void c() {
        this.start.resolved = false;
        this.end.resolved = false;
    }

    private void a(DependencyNode dependencyNode) {
        this.start.f.add(dependencyNode);
        dependencyNode.g.add(this.start);
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun, androidx.constraintlayout.solver.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        if (this.start.readyToSolve && !this.start.resolved) {
            this.start.resolve((int) ((this.start.g.get(0).value * ((Guideline) this.b).getRelativePercent()) + 0.5f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void d() {
        Guideline guideline = (Guideline) this.b;
        int relativeBegin = guideline.getRelativeBegin();
        int relativeEnd = guideline.getRelativeEnd();
        guideline.getRelativePercent();
        if (guideline.getOrientation() == 1) {
            if (relativeBegin != -1) {
                this.start.g.add(this.b.mParent.horizontalRun.start);
                this.b.mParent.horizontalRun.start.f.add(this.start);
                this.start.c = relativeBegin;
            } else if (relativeEnd != -1) {
                this.start.g.add(this.b.mParent.horizontalRun.end);
                this.b.mParent.horizontalRun.end.f.add(this.start);
                this.start.c = -relativeEnd;
            } else {
                this.start.delegateToWidgetRun = true;
                this.start.g.add(this.b.mParent.horizontalRun.end);
                this.b.mParent.horizontalRun.end.f.add(this.start);
            }
            a(this.b.horizontalRun.start);
            a(this.b.horizontalRun.end);
            return;
        }
        if (relativeBegin != -1) {
            this.start.g.add(this.b.mParent.verticalRun.start);
            this.b.mParent.verticalRun.start.f.add(this.start);
            this.start.c = relativeBegin;
        } else if (relativeEnd != -1) {
            this.start.g.add(this.b.mParent.verticalRun.end);
            this.b.mParent.verticalRun.end.f.add(this.start);
            this.start.c = -relativeEnd;
        } else {
            this.start.delegateToWidgetRun = true;
            this.start.g.add(this.b.mParent.verticalRun.end);
            this.b.mParent.verticalRun.end.f.add(this.start);
        }
        a(this.b.verticalRun.start);
        a(this.b.verticalRun.end);
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        if (((Guideline) this.b).getOrientation() == 1) {
            this.b.setX(this.start.value);
        } else {
            this.b.setY(this.start.value);
        }
    }
}
