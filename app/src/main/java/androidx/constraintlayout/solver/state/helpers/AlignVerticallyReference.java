package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.State;
import java.util.Iterator;

/* loaded from: classes.dex */
public class AlignVerticallyReference extends HelperReference {
    private float b = 0.5f;
    private Object c;
    private Object d;
    private Object e;
    private Object f;

    public AlignVerticallyReference(State state) {
        super(state, State.Helper.ALIGN_VERTICALLY);
    }

    @Override // androidx.constraintlayout.solver.state.HelperReference
    public void apply() {
        Iterator it = this.mReferences.iterator();
        while (it.hasNext()) {
            ConstraintReference constraints = this.mState.constraints(it.next());
            constraints.clearVertical();
            Object obj = this.c;
            if (obj != null) {
                constraints.topToTop(obj);
            } else {
                Object obj2 = this.d;
                if (obj2 != null) {
                    constraints.topToBottom(obj2);
                } else {
                    constraints.topToTop(State.PARENT);
                }
            }
            Object obj3 = this.e;
            if (obj3 != null) {
                constraints.bottomToTop(obj3);
            } else {
                Object obj4 = this.f;
                if (obj4 != null) {
                    constraints.bottomToBottom(obj4);
                } else {
                    constraints.bottomToBottom(State.PARENT);
                }
            }
            float f = this.b;
            if (f != 0.5f) {
                constraints.verticalBias(f);
            }
        }
    }

    public void topToTop(Object obj) {
        this.c = obj;
    }

    public void topToBottom(Object obj) {
        this.d = obj;
    }

    public void bottomToTop(Object obj) {
        this.e = obj;
    }

    public void bottomToBottom(Object obj) {
        this.f = obj;
    }

    public void bias(float f) {
        this.b = f;
    }
}
