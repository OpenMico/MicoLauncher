package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class HelperReference {
    final State.Helper a;
    private HelperWidget b;
    protected ArrayList<Object> mReferences = new ArrayList<>();
    protected final State mState;

    public void apply() {
    }

    public HelperReference(State state, State.Helper helper) {
        this.mState = state;
        this.a = helper;
    }

    public State.Helper getType() {
        return this.a;
    }

    public HelperReference add(Object... objArr) {
        for (Object obj : objArr) {
            this.mReferences.add(obj);
        }
        return this;
    }

    public void setHelperWidget(HelperWidget helperWidget) {
        this.b = helperWidget;
    }

    public HelperWidget getHelperWidget() {
        return this.b;
    }
}
