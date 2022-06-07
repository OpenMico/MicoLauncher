package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.HelperWidget;

/* loaded from: classes.dex */
public class BarrierReference extends HelperReference {
    private State.Direction b;
    private int c;
    private Barrier d;

    public BarrierReference(State state) {
        super(state, State.Helper.BARRIER);
    }

    public void setBarrierDirection(State.Direction direction) {
        this.b = direction;
    }

    public void margin(Object obj) {
        margin(this.mState.convertDimension(obj));
    }

    public void margin(int i) {
        this.c = i;
    }

    @Override // androidx.constraintlayout.solver.state.HelperReference
    public HelperWidget getHelperWidget() {
        if (this.d == null) {
            this.d = new Barrier();
        }
        return this.d;
    }

    @Override // androidx.constraintlayout.solver.state.HelperReference
    public void apply() {
        getHelperWidget();
        int i = 0;
        switch (this.b) {
            case RIGHT:
            case END:
                i = 1;
                break;
            case TOP:
                i = 2;
                break;
            case BOTTOM:
                i = 3;
                break;
        }
        this.d.setBarrierType(i);
        this.d.setMargin(this.c);
    }
}
