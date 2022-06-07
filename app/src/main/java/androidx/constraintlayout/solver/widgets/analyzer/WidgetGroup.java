package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.Chain;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class WidgetGroup {
    static int b;
    int c;
    int e;
    ArrayList<ConstraintWidget> a = new ArrayList<>();
    boolean d = false;
    ArrayList<a> f = null;
    private int g = -1;

    public WidgetGroup(int i) {
        this.c = -1;
        this.e = 0;
        int i2 = b;
        b = i2 + 1;
        this.c = i2;
        this.e = i;
    }

    public int getOrientation() {
        return this.e;
    }

    public int getId() {
        return this.c;
    }

    public boolean add(ConstraintWidget constraintWidget) {
        if (this.a.contains(constraintWidget)) {
            return false;
        }
        this.a.add(constraintWidget);
        return true;
    }

    public void setAuthoritative(boolean z) {
        this.d = z;
    }

    public boolean isAuthoritative() {
        return this.d;
    }

    private String a() {
        int i = this.e;
        return i == 0 ? "Horizontal" : i == 1 ? "Vertical" : i == 2 ? "Both" : "Unknown";
    }

    public String toString() {
        String str = a() + " [" + this.c + "] <";
        Iterator<ConstraintWidget> it = this.a.iterator();
        while (it.hasNext()) {
            str = str + StringUtils.SPACE + it.next().getDebugName();
        }
        return str + " >";
    }

    public void moveTo(int i, WidgetGroup widgetGroup) {
        Iterator<ConstraintWidget> it = this.a.iterator();
        while (it.hasNext()) {
            ConstraintWidget next = it.next();
            widgetGroup.add(next);
            if (i == 0) {
                next.horizontalGroup = widgetGroup.getId();
            } else {
                next.verticalGroup = widgetGroup.getId();
            }
        }
        this.g = widgetGroup.c;
    }

    public void clear() {
        this.a.clear();
    }

    public int measureWrap(LinearSystem linearSystem, int i) {
        if (this.a.size() == 0) {
            return 0;
        }
        return a(linearSystem, this.a, i);
    }

    private int a(LinearSystem linearSystem, ArrayList<ConstraintWidget> arrayList, int i) {
        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) arrayList.get(0).getParent();
        linearSystem.reset();
        constraintWidgetContainer.addToSolver(linearSystem, false);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList.get(i2).addToSolver(linearSystem, false);
        }
        if (i == 0 && constraintWidgetContainer.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(constraintWidgetContainer, linearSystem, arrayList, 0);
        }
        if (i == 1 && constraintWidgetContainer.mVerticalChainsSize > 0) {
            Chain.applyChainConstraints(constraintWidgetContainer, linearSystem, arrayList, 1);
        }
        try {
            linearSystem.minimize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.f = new ArrayList<>();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            this.f.add(new a(arrayList.get(i3), linearSystem, i));
        }
        if (i == 0) {
            int objectVariableValue = linearSystem.getObjectVariableValue(constraintWidgetContainer.mLeft);
            int objectVariableValue2 = linearSystem.getObjectVariableValue(constraintWidgetContainer.mRight);
            linearSystem.reset();
            return objectVariableValue2 - objectVariableValue;
        }
        int objectVariableValue3 = linearSystem.getObjectVariableValue(constraintWidgetContainer.mTop);
        int objectVariableValue4 = linearSystem.getObjectVariableValue(constraintWidgetContainer.mBottom);
        linearSystem.reset();
        return objectVariableValue4 - objectVariableValue3;
    }

    public void setOrientation(int i) {
        this.e = i;
    }

    public void apply() {
        if (this.f != null && this.d) {
            for (int i = 0; i < this.f.size(); i++) {
                this.f.get(i).a();
            }
        }
    }

    public boolean intersectWith(WidgetGroup widgetGroup) {
        for (int i = 0; i < this.a.size(); i++) {
            if (widgetGroup.a(this.a.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean a(ConstraintWidget constraintWidget) {
        return this.a.contains(constraintWidget);
    }

    public int size() {
        return this.a.size();
    }

    public void cleanup(ArrayList<WidgetGroup> arrayList) {
        int size = this.a.size();
        if (this.g != -1 && size > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                WidgetGroup widgetGroup = arrayList.get(i);
                if (this.g == widgetGroup.c) {
                    moveTo(this.e, widgetGroup);
                }
            }
        }
        if (size == 0) {
            arrayList.remove(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class a {
        WeakReference<ConstraintWidget> a;
        int b;
        int c;
        int d;
        int e;
        int f;
        int g;

        public a(ConstraintWidget constraintWidget, LinearSystem linearSystem, int i) {
            this.a = new WeakReference<>(constraintWidget);
            this.b = linearSystem.getObjectVariableValue(constraintWidget.mLeft);
            this.c = linearSystem.getObjectVariableValue(constraintWidget.mTop);
            this.d = linearSystem.getObjectVariableValue(constraintWidget.mRight);
            this.e = linearSystem.getObjectVariableValue(constraintWidget.mBottom);
            this.f = linearSystem.getObjectVariableValue(constraintWidget.mBaseline);
            this.g = i;
        }

        public void a() {
            ConstraintWidget constraintWidget = this.a.get();
            if (constraintWidget != null) {
                constraintWidget.setFinalFrame(this.b, this.c, this.d, this.e, this.f, this.g);
            }
        }
    }
}
