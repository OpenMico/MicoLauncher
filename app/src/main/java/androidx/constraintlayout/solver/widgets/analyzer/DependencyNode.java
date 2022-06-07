package androidx.constraintlayout.solver.widgets.analyzer;

import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class DependencyNode implements Dependency {
    WidgetRun a;
    int c;
    public int value;
    public Dependency updateDelegate = null;
    public boolean delegateToWidgetRun = false;
    public boolean readyToSolve = false;
    a b = a.UNKNOWN;
    int d = 1;
    b e = null;
    public boolean resolved = false;
    List<Dependency> f = new ArrayList();
    List<DependencyNode> g = new ArrayList();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum a {
        UNKNOWN,
        HORIZONTAL_DIMENSION,
        VERTICAL_DIMENSION,
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        BASELINE
    }

    public DependencyNode(WidgetRun widgetRun) {
        this.a = widgetRun;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.b.getDebugName());
        sb.append(Constants.COLON_SEPARATOR);
        sb.append(this.b);
        sb.append("(");
        sb.append(this.resolved ? Integer.valueOf(this.value) : "unresolved");
        sb.append(") <t=");
        sb.append(this.g.size());
        sb.append(":d=");
        sb.append(this.f.size());
        sb.append(">");
        return sb.toString();
    }

    public void resolve(int i) {
        if (!this.resolved) {
            this.resolved = true;
            this.value = i;
            for (Dependency dependency : this.f) {
                dependency.update(dependency);
            }
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        for (DependencyNode dependencyNode : this.g) {
            if (!dependencyNode.resolved) {
                return;
            }
        }
        this.readyToSolve = true;
        Dependency dependency2 = this.updateDelegate;
        if (dependency2 != null) {
            dependency2.update(this);
        }
        if (this.delegateToWidgetRun) {
            this.a.update(this);
            return;
        }
        DependencyNode dependencyNode2 = null;
        int i = 0;
        for (DependencyNode dependencyNode3 : this.g) {
            if (!(dependencyNode3 instanceof b)) {
                i++;
                dependencyNode2 = dependencyNode3;
            }
        }
        if (dependencyNode2 != null && i == 1 && dependencyNode2.resolved) {
            b bVar = this.e;
            if (bVar != null) {
                if (bVar.resolved) {
                    this.c = this.d * this.e.value;
                } else {
                    return;
                }
            }
            resolve(dependencyNode2.value + this.c);
        }
        Dependency dependency3 = this.updateDelegate;
        if (dependency3 != null) {
            dependency3.update(this);
        }
    }

    public void addDependency(Dependency dependency) {
        this.f.add(dependency);
        if (this.resolved) {
            dependency.update(dependency);
        }
    }

    public String name() {
        String str;
        String debugName = this.a.b.getDebugName();
        if (this.b == a.LEFT || this.b == a.RIGHT) {
            str = debugName + "_HORIZONTAL";
        } else {
            str = debugName + "_VERTICAL";
        }
        return str + Constants.COLON_SEPARATOR + this.b.name();
    }

    public void clear() {
        this.g.clear();
        this.f.clear();
        this.resolved = false;
        this.value = 0;
        this.readyToSolve = false;
        this.delegateToWidgetRun = false;
    }
}
