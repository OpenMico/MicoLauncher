package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ChainRun extends WidgetRun {
    ArrayList<WidgetRun> a = new ArrayList<>();
    private int f;

    public ChainRun(ConstraintWidget constraintWidget, int i) {
        super(constraintWidget);
        this.orientation = i;
        e();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ChainRun ");
        sb.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        String sb2 = sb.toString();
        Iterator<WidgetRun> it = this.a.iterator();
        while (it.hasNext()) {
            sb2 = ((sb2 + "<") + it.next()) + "> ";
        }
        return sb2;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    boolean a() {
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            if (!this.a.get(i).a()) {
                return false;
            }
        }
        return true;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public long getWrapDimension() {
        int size = this.a.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            WidgetRun widgetRun = this.a.get(i);
            j = j + widgetRun.start.c + widgetRun.getWrapDimension() + widgetRun.end.c;
        }
        return j;
    }

    private void e() {
        ConstraintWidget constraintWidget = this.b;
        ConstraintWidget previousChainMember = constraintWidget.getPreviousChainMember(this.orientation);
        ConstraintWidget constraintWidget2 = constraintWidget;
        while (previousChainMember != null) {
            previousChainMember = previousChainMember.getPreviousChainMember(this.orientation);
            constraintWidget2 = previousChainMember;
        }
        this.b = constraintWidget2;
        this.a.add(constraintWidget2.getRun(this.orientation));
        ConstraintWidget nextChainMember = constraintWidget2.getNextChainMember(this.orientation);
        while (nextChainMember != null) {
            this.a.add(nextChainMember.getRun(this.orientation));
            nextChainMember = nextChainMember.getNextChainMember(this.orientation);
        }
        Iterator<WidgetRun> it = this.a.iterator();
        while (it.hasNext()) {
            WidgetRun next = it.next();
            if (this.orientation == 0) {
                next.b.horizontalChainRun = this;
            } else if (this.orientation == 1) {
                next.b.verticalChainRun = this;
            }
        }
        if ((this.orientation == 0 && ((ConstraintWidgetContainer) this.b.getParent()).isRtl()) && this.a.size() > 1) {
            ArrayList<WidgetRun> arrayList = this.a;
            this.b = arrayList.get(arrayList.size() - 1).b;
        }
        this.f = this.orientation == 0 ? this.b.getHorizontalChainStyle() : this.b.getVerticalChainStyle();
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    void b() {
        this.c = null;
        Iterator<WidgetRun> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().b();
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    void c() {
        this.start.resolved = false;
        this.end.resolved = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:271:0x0417, code lost:
        r2 = r2 - r9;
     */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun, androidx.constraintlayout.solver.widgets.analyzer.Dependency
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void update(androidx.constraintlayout.solver.widgets.analyzer.Dependency r23) {
        /*
            Method dump skipped, instructions count: 1088
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.ChainRun.update(androidx.constraintlayout.solver.widgets.analyzer.Dependency):void");
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        for (int i = 0; i < this.a.size(); i++) {
            this.a.get(i).applyToWidget();
        }
    }

    private ConstraintWidget f() {
        for (int i = 0; i < this.a.size(); i++) {
            WidgetRun widgetRun = this.a.get(i);
            if (widgetRun.b.getVisibility() != 8) {
                return widgetRun.b;
            }
        }
        return null;
    }

    private ConstraintWidget g() {
        for (int size = this.a.size() - 1; size >= 0; size--) {
            WidgetRun widgetRun = this.a.get(size);
            if (widgetRun.b.getVisibility() != 8) {
                return widgetRun.b;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    void d() {
        Iterator<WidgetRun> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().d();
        }
        int size = this.a.size();
        if (size >= 1) {
            ConstraintWidget constraintWidget = this.a.get(0).b;
            ConstraintWidget constraintWidget2 = this.a.get(size - 1).b;
            if (this.orientation == 0) {
                ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
                ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
                DependencyNode target = getTarget(constraintAnchor, 0);
                int margin = constraintAnchor.getMargin();
                ConstraintWidget f = f();
                if (f != null) {
                    margin = f.mLeft.getMargin();
                }
                if (target != null) {
                    addTarget(this.start, target, margin);
                }
                DependencyNode target2 = getTarget(constraintAnchor2, 0);
                int margin2 = constraintAnchor2.getMargin();
                ConstraintWidget g = g();
                if (g != null) {
                    margin2 = g.mRight.getMargin();
                }
                if (target2 != null) {
                    addTarget(this.end, target2, -margin2);
                }
            } else {
                ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
                ConstraintAnchor constraintAnchor4 = constraintWidget2.mBottom;
                DependencyNode target3 = getTarget(constraintAnchor3, 1);
                int margin3 = constraintAnchor3.getMargin();
                ConstraintWidget f2 = f();
                if (f2 != null) {
                    margin3 = f2.mTop.getMargin();
                }
                if (target3 != null) {
                    addTarget(this.start, target3, margin3);
                }
                DependencyNode target4 = getTarget(constraintAnchor4, 1);
                int margin4 = constraintAnchor4.getMargin();
                ConstraintWidget g2 = g();
                if (g2 != null) {
                    margin4 = g2.mBottom.getMargin();
                }
                if (target4 != null) {
                    addTarget(this.end, target4, -margin4);
                }
            }
            this.start.updateDelegate = this;
            this.end.updateDelegate = this;
        }
    }
}
