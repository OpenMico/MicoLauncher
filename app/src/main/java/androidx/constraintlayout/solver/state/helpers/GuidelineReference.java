package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.Reference;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Guideline;

/* loaded from: classes.dex */
public class GuidelineReference implements Reference {
    final State a;
    private int b;
    private Guideline c;
    private int d = -1;
    private int e = -1;
    private float f = 0.0f;
    private Object g;

    @Override // androidx.constraintlayout.solver.state.Reference
    public void setKey(Object obj) {
        this.g = obj;
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public Object getKey() {
        return this.g;
    }

    public GuidelineReference(State state) {
        this.a = state;
    }

    public void start(Object obj) {
        this.d = this.a.convertDimension(obj);
        this.e = -1;
        this.f = 0.0f;
    }

    public void end(Object obj) {
        this.d = -1;
        this.e = this.a.convertDimension(obj);
        this.f = 0.0f;
    }

    public void percent(float f) {
        this.d = -1;
        this.e = -1;
        this.f = f;
    }

    public void setOrientation(int i) {
        this.b = i;
    }

    public int getOrientation() {
        return this.b;
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public void apply() {
        this.c.setOrientation(this.b);
        int i = this.d;
        if (i != -1) {
            this.c.setGuideBegin(i);
            return;
        }
        int i2 = this.e;
        if (i2 != -1) {
            this.c.setGuideEnd(i2);
        } else {
            this.c.setGuidePercent(this.f);
        }
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public ConstraintWidget getConstraintWidget() {
        if (this.c == null) {
            this.c = new Guideline();
        }
        return this.c;
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public void setConstraintWidget(ConstraintWidget constraintWidget) {
        if (constraintWidget instanceof Guideline) {
            this.c = (Guideline) constraintWidget;
        } else {
            this.c = null;
        }
    }
}
