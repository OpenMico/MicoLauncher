package org.fourthline.cling.model.state;

import org.fourthline.cling.model.VariableValue;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.meta.StateVariable;
import org.fourthline.cling.model.types.InvalidValueException;

/* loaded from: classes5.dex */
public class StateVariableValue<S extends Service> extends VariableValue {
    private StateVariable<S> stateVariable;

    public StateVariableValue(StateVariable<S> stateVariable, Object obj) throws InvalidValueException {
        super(stateVariable.getTypeDetails().getDatatype(), obj);
        this.stateVariable = stateVariable;
    }

    public StateVariable<S> getStateVariable() {
        return this.stateVariable;
    }
}
