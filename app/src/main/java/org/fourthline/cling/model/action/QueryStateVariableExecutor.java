package org.fourthline.cling.model.action;

import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.QueryStateVariableAction;
import org.fourthline.cling.model.meta.StateVariable;
import org.fourthline.cling.model.state.StateVariableAccessor;
import org.fourthline.cling.model.types.ErrorCode;

/* loaded from: classes5.dex */
public class QueryStateVariableExecutor extends AbstractActionExecutor {
    @Override // org.fourthline.cling.model.action.AbstractActionExecutor
    protected void execute(ActionInvocation<LocalService> actionInvocation, Object obj) throws Exception {
        if (!(actionInvocation.getAction() instanceof QueryStateVariableAction)) {
            throw new IllegalStateException("This class can only execute QueryStateVariableAction's, not: " + actionInvocation.getAction());
        } else if (!actionInvocation.getAction().getService().isSupportsQueryStateVariables()) {
            actionInvocation.setFailure(new ActionException(ErrorCode.INVALID_ACTION, "This service does not support querying state variables"));
        } else {
            executeQueryStateVariable(actionInvocation, obj);
        }
    }

    protected void executeQueryStateVariable(ActionInvocation<LocalService> actionInvocation, Object obj) throws Exception {
        LocalService service = actionInvocation.getAction().getService();
        String actionArgumentValue = actionInvocation.getInput(QueryStateVariableAction.INPUT_ARG_VAR_NAME).toString();
        StateVariable<LocalService> stateVariable = service.getStateVariable(actionArgumentValue);
        if (stateVariable != null) {
            StateVariableAccessor accessor = service.getAccessor(stateVariable.getName());
            if (accessor != null) {
                try {
                    setOutputArgumentValue(actionInvocation, actionInvocation.getAction().getOutputArgument(QueryStateVariableAction.OUTPUT_ARG_RETURN), accessor.read(stateVariable, obj).toString());
                } catch (Exception e) {
                    throw new ActionException(ErrorCode.ACTION_FAILED, e.getMessage());
                }
            } else {
                ErrorCode errorCode = ErrorCode.ARGUMENT_VALUE_INVALID;
                throw new ActionException(errorCode, "No accessor for state variable, can't read state: " + actionArgumentValue);
            }
        } else {
            ErrorCode errorCode2 = ErrorCode.ARGUMENT_VALUE_INVALID;
            throw new ActionException(errorCode2, "No state variable found: " + actionArgumentValue);
        }
    }
}
