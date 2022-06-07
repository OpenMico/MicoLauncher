package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: RunGroup.java */
/* loaded from: classes.dex */
public class e {
    public static int a;
    WidgetRun d;
    WidgetRun e;
    int g;
    int h;
    public int b = 0;
    public boolean c = false;
    ArrayList<WidgetRun> f = new ArrayList<>();

    public e(WidgetRun widgetRun, int i) {
        this.d = null;
        this.e = null;
        this.g = 0;
        int i2 = a;
        this.g = i2;
        a = i2 + 1;
        this.d = widgetRun;
        this.e = widgetRun;
        this.h = i;
    }

    public void a(WidgetRun widgetRun) {
        this.f.add(widgetRun);
        this.e = widgetRun;
    }

    private long a(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.a;
        if (widgetRun instanceof d) {
            return j;
        }
        int size = dependencyNode.f.size();
        long j2 = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = dependencyNode.f.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.a != widgetRun) {
                    j2 = Math.max(j2, a(dependencyNode2, dependencyNode2.c + j));
                }
            }
        }
        if (dependencyNode != widgetRun.start) {
            return j2;
        }
        long wrapDimension = j + widgetRun.getWrapDimension();
        return Math.max(Math.max(j2, a(widgetRun.end, wrapDimension)), wrapDimension - widgetRun.end.c);
    }

    private long b(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.a;
        if (widgetRun instanceof d) {
            return j;
        }
        int size = dependencyNode.f.size();
        long j2 = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = dependencyNode.f.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.a != widgetRun) {
                    j2 = Math.min(j2, b(dependencyNode2, dependencyNode2.c + j));
                }
            }
        }
        if (dependencyNode != widgetRun.end) {
            return j2;
        }
        long wrapDimension = j - widgetRun.getWrapDimension();
        return Math.min(Math.min(j2, b(widgetRun.start, wrapDimension)), wrapDimension - widgetRun.start.c);
    }

    public long a(ConstraintWidgetContainer constraintWidgetContainer, int i) {
        WidgetRun widgetRun = this.d;
        long j = 0;
        if (widgetRun instanceof ChainRun) {
            if (((ChainRun) widgetRun).orientation != i) {
                return 0L;
            }
        } else if (i == 0) {
            if (!(widgetRun instanceof HorizontalWidgetRun)) {
                return 0L;
            }
        } else if (!(widgetRun instanceof VerticalWidgetRun)) {
            return 0L;
        }
        DependencyNode dependencyNode = i == 0 ? constraintWidgetContainer.horizontalRun.start : constraintWidgetContainer.verticalRun.start;
        DependencyNode dependencyNode2 = i == 0 ? constraintWidgetContainer.horizontalRun.end : constraintWidgetContainer.verticalRun.end;
        boolean contains = this.d.start.g.contains(dependencyNode);
        boolean contains2 = this.d.end.g.contains(dependencyNode2);
        long wrapDimension = this.d.getWrapDimension();
        if (contains && contains2) {
            long a2 = a(this.d.start, 0L);
            long b = b(this.d.end, 0L);
            long j2 = a2 - wrapDimension;
            if (j2 >= (-this.d.end.c)) {
                j2 += this.d.end.c;
            }
            long j3 = ((-b) - wrapDimension) - this.d.start.c;
            if (j3 >= this.d.start.c) {
                j3 -= this.d.start.c;
            }
            float biasPercent = this.d.b.getBiasPercent(i);
            if (biasPercent > 0.0f) {
                j = (((float) j3) / biasPercent) + (((float) j2) / (1.0f - biasPercent));
            }
            float f = (float) j;
            return (this.d.start.c + ((((f * biasPercent) + 0.5f) + wrapDimension) + ((f * (1.0f - biasPercent)) + 0.5f))) - this.d.end.c;
        } else if (contains) {
            return Math.max(a(this.d.start, this.d.start.c), this.d.start.c + wrapDimension);
        } else {
            if (!contains2) {
                return (this.d.start.c + this.d.getWrapDimension()) - this.d.end.c;
            }
            return Math.max(-b(this.d.end, this.d.end.c), (-this.d.end.c) + wrapDimension);
        }
    }

    private boolean a(WidgetRun widgetRun, int i) {
        if (!widgetRun.b.isTerminalWidget[i]) {
            return false;
        }
        for (Dependency dependency : widgetRun.start.f) {
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode = (DependencyNode) dependency;
                if (dependencyNode.a != widgetRun && dependencyNode == dependencyNode.a.start) {
                    if (widgetRun instanceof ChainRun) {
                        Iterator<WidgetRun> it = ((ChainRun) widgetRun).a.iterator();
                        while (it.hasNext()) {
                            a(it.next(), i);
                        }
                    } else if (!(widgetRun instanceof d)) {
                        widgetRun.b.isTerminalWidget[i] = false;
                    }
                    a(dependencyNode.a, i);
                }
            }
        }
        for (Dependency dependency2 : widgetRun.end.f) {
            if (dependency2 instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency2;
                if (dependencyNode2.a != widgetRun && dependencyNode2 == dependencyNode2.a.start) {
                    if (widgetRun instanceof ChainRun) {
                        Iterator<WidgetRun> it2 = ((ChainRun) widgetRun).a.iterator();
                        while (it2.hasNext()) {
                            a(it2.next(), i);
                        }
                    } else if (!(widgetRun instanceof d)) {
                        widgetRun.b.isTerminalWidget[i] = false;
                    }
                    a(dependencyNode2.a, i);
                }
            }
        }
        return false;
    }

    public void a(boolean z, boolean z2) {
        if (z) {
            WidgetRun widgetRun = this.d;
            if (widgetRun instanceof HorizontalWidgetRun) {
                a(widgetRun, 0);
            }
        }
        if (z2) {
            WidgetRun widgetRun2 = this.d;
            if (widgetRun2 instanceof VerticalWidgetRun) {
                a(widgetRun2, 1);
            }
        }
    }
}
