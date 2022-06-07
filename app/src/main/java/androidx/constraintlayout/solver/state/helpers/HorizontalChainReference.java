package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.State;
import java.util.Iterator;

/* loaded from: classes.dex */
public class HorizontalChainReference extends ChainReference {
    private Object b;
    private Object c;
    private Object d;
    private Object e;

    public HorizontalChainReference(State state) {
        super(state, State.Helper.HORIZONTAL_CHAIN);
    }

    @Override // androidx.constraintlayout.solver.state.HelperReference
    public void apply() {
        Iterator it = this.mReferences.iterator();
        while (it.hasNext()) {
            this.mState.constraints(it.next()).clearHorizontal();
        }
        Iterator it2 = this.mReferences.iterator();
        ConstraintReference constraintReference = null;
        ConstraintReference constraintReference2 = null;
        while (it2.hasNext()) {
            ConstraintReference constraints = this.mState.constraints(it2.next());
            if (constraintReference2 == null) {
                Object obj = this.b;
                if (obj != null) {
                    constraints.startToStart(obj);
                } else {
                    Object obj2 = this.c;
                    if (obj2 != null) {
                        constraints.startToEnd(obj2);
                    } else {
                        constraints.startToStart(State.PARENT);
                    }
                }
                constraintReference2 = constraints;
            }
            if (constraintReference != null) {
                constraintReference.endToStart(constraints.getKey());
                constraints.startToEnd(constraintReference.getKey());
            }
            constraintReference = constraints;
        }
        if (constraintReference != null) {
            Object obj3 = this.d;
            if (obj3 != null) {
                constraintReference.endToStart(obj3);
            } else {
                Object obj4 = this.e;
                if (obj4 != null) {
                    constraintReference.endToEnd(obj4);
                } else {
                    constraintReference.endToEnd(State.PARENT);
                }
            }
        }
        if (!(constraintReference2 == null || this.mBias == 0.5f)) {
            constraintReference2.horizontalBias(this.mBias);
        }
        switch (this.mStyle) {
            case SPREAD:
                constraintReference2.setHorizontalChainStyle(0);
                return;
            case SPREAD_INSIDE:
                constraintReference2.setHorizontalChainStyle(1);
                return;
            case PACKED:
                constraintReference2.setHorizontalChainStyle(2);
                return;
            default:
                return;
        }
    }

    public void startToStart(Object obj) {
        this.b = obj;
    }

    public void startToEnd(Object obj) {
        this.c = obj;
    }

    public void endToStart(Object obj) {
        this.d = obj;
    }

    public void endToEnd(Object obj) {
        this.e = obj;
    }
}
