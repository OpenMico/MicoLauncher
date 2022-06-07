package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DimensionDependency.java */
/* loaded from: classes.dex */
public class b extends DependencyNode {
    public int h;

    public b(WidgetRun widgetRun) {
        super(widgetRun);
        if (widgetRun instanceof HorizontalWidgetRun) {
            this.b = DependencyNode.a.HORIZONTAL_DIMENSION;
        } else {
            this.b = DependencyNode.a.VERTICAL_DIMENSION;
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.DependencyNode
    public void resolve(int i) {
        if (!this.resolved) {
            this.resolved = true;
            this.value = i;
            for (Dependency dependency : this.f) {
                dependency.update(dependency);
            }
        }
    }
}
