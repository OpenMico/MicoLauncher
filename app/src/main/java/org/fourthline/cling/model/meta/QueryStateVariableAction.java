package org.fourthline.cling.model.meta;

import java.util.Collections;
import java.util.List;
import org.fourthline.cling.model.ValidationError;
import org.fourthline.cling.model.meta.ActionArgument;
import org.fourthline.cling.model.meta.Service;

/* loaded from: classes5.dex */
public class QueryStateVariableAction<S extends Service> extends Action<S> {
    public static final String ACTION_NAME = "QueryStateVariable";
    public static final String INPUT_ARG_VAR_NAME = "varName";
    public static final String OUTPUT_ARG_RETURN = "return";
    public static final String VIRTUAL_STATEVARIABLE_INPUT = "VirtualQueryActionInput";
    public static final String VIRTUAL_STATEVARIABLE_OUTPUT = "VirtualQueryActionOutput";

    @Override // org.fourthline.cling.model.meta.Action
    public String getName() {
        return ACTION_NAME;
    }

    public QueryStateVariableAction() {
        this(null);
    }

    public QueryStateVariableAction(S s) {
        super(ACTION_NAME, new ActionArgument[]{new ActionArgument(INPUT_ARG_VAR_NAME, VIRTUAL_STATEVARIABLE_INPUT, ActionArgument.Direction.IN), new ActionArgument(OUTPUT_ARG_RETURN, VIRTUAL_STATEVARIABLE_OUTPUT, ActionArgument.Direction.OUT)});
        setService(s);
    }

    @Override // org.fourthline.cling.model.meta.Action, org.fourthline.cling.model.Validatable
    public List<ValidationError> validate() {
        return Collections.EMPTY_LIST;
    }
}
