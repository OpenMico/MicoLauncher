package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.State;
import java.util.Iterator;

/* loaded from: classes.dex */
public class VerticalChainReference extends ChainReference {
    private Object b;
    private Object c;
    private Object d;
    private Object e;

    public VerticalChainReference(State state) {
        super(state, State.Helper.VERTICAL_CHAIN);
    }

    @Override // androidx.constraintlayout.solver.state.HelperReference
    public void apply() {
        Iterator it = this.mReferences.iterator();
        while (it.hasNext()) {
            this.mState.constraints(it.next()).clearVertical();
        }
        Iterator it2 = this.mReferences.iterator();
        ConstraintReference constraintReference = null;
        ConstraintReference constraintReference2 = null;
        while (it2.hasNext()) {
            ConstraintReference constraints = this.mState.constraints(it2.next());
            if (constraintReference2 == null) {
                Object obj = this.b;
                if (obj != null) {
                    constraints.topToTop(obj);
                } else {
                    Object obj2 = this.c;
                    if (obj2 != null) {
                        constraints.topToBottom(obj2);
                    } else {
                        constraints.topToTop(State.PARENT);
                    }
                }
                constraintReference2 = constraints;
            }
            if (constraintReference != null) {
                constraintReference.bottomToTop(constraints.getKey());
                constraints.topToBottom(constraintReference.getKey());
            }
            constraintReference = constraints;
        }
        if (constraintReference != null) {
            Object obj3 = this.d;
            if (obj3 != null) {
                constraintReference.bottomToTop(obj3);
            } else {
                Object obj4 = this.e;
                if (obj4 != null) {
                    constraintReference.bottomToBottom(obj4);
                } else {
                    constraintReference.bottomToBottom(State.PARENT);
                }
            }
        }
        if (!(constraintReference2 == null || this.mBias == 0.5f)) {
            constraintReference2.verticalBias(this.mBias);
        }
        switch (this.mStyle) {
            case SPREAD:
                constraintReference2.setVerticalChainStyle(0);
                return;
            case SPREAD_INSIDE:
                constraintReference2.setVerticalChainStyle(1);
                return;
            case PACKED:
                constraintReference2.setVerticalChainStyle(2);
                return;
            default:
                return;
        }
    }

    public void topToTop(Object obj) {
        this.b = obj;
    }

    public void topToBottom(Object obj) {
        this.c = obj;
    }

    public void bottomToTop(Object obj) {
        this.d = obj;
    }

    public void bottomToBottom(Object obj) {
        this.e = obj;
    }
}
