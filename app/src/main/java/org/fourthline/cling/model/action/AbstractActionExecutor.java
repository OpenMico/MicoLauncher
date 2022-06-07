package org.fourthline.cling.model.action;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.model.Command;
import org.fourthline.cling.model.ServiceManager;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.ActionArgument;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.state.StateVariableAccessor;
import org.fourthline.cling.model.types.ErrorCode;
import org.fourthline.cling.model.types.InvalidValueException;
import org.seamless.util.Exceptions;

/* loaded from: classes5.dex */
public abstract class AbstractActionExecutor implements ActionExecutor {
    private static Logger log = Logger.getLogger(AbstractActionExecutor.class.getName());
    protected Map<ActionArgument<LocalService>, StateVariableAccessor> outputArgumentAccessors;

    protected abstract void execute(ActionInvocation<LocalService> actionInvocation, Object obj) throws Exception;

    public AbstractActionExecutor() {
        this.outputArgumentAccessors = new HashMap();
    }

    public AbstractActionExecutor(Map<ActionArgument<LocalService>, StateVariableAccessor> map) {
        this.outputArgumentAccessors = new HashMap();
        this.outputArgumentAccessors = map;
    }

    public Map<ActionArgument<LocalService>, StateVariableAccessor> getOutputArgumentAccessors() {
        return this.outputArgumentAccessors;
    }

    @Override // org.fourthline.cling.model.action.ActionExecutor
    public void execute(final ActionInvocation<LocalService> actionInvocation) {
        Logger logger = log;
        logger.fine("Invoking on local service: " + actionInvocation);
        LocalService service = actionInvocation.getAction().getService();
        try {
            if (service.getManager() != null) {
                service.getManager().execute(new Command() { // from class: org.fourthline.cling.model.action.AbstractActionExecutor.1
                    @Override // org.fourthline.cling.model.Command
                    public void execute(ServiceManager serviceManager) throws Exception {
                        AbstractActionExecutor.this.execute(actionInvocation, serviceManager.getImplementation());
                    }

                    public String toString() {
                        return "Action invocation: " + actionInvocation.getAction();
                    }
                });
                return;
            }
            throw new IllegalStateException("Service has no implementation factory, can't get service instance");
        } catch (InterruptedException e) {
            if (log.isLoggable(Level.FINE)) {
                Logger logger2 = log;
                logger2.fine("InterruptedException thrown by service, wrapping in invocation and returning: " + e);
                log.log(Level.FINE, "Exception root cause: ", Exceptions.unwrap(e));
            }
            actionInvocation.setFailure(new ActionCancelledException(e));
        } catch (ActionException e2) {
            if (log.isLoggable(Level.FINE)) {
                Logger logger3 = log;
                logger3.fine("ActionException thrown by service, wrapping in invocation and returning: " + e2);
                log.log(Level.FINE, "Exception root cause: ", Exceptions.unwrap(e2));
            }
            actionInvocation.setFailure(e2);
        } catch (Throwable th) {
            Throwable unwrap = Exceptions.unwrap(th);
            if (log.isLoggable(Level.FINE)) {
                Logger logger4 = log;
                logger4.fine("Execution has thrown, wrapping root cause in ActionException and returning: " + th);
                log.log(Level.FINE, "Exception root cause: ", unwrap);
            }
            actionInvocation.setFailure(new ActionException(ErrorCode.ACTION_FAILED, unwrap.getMessage() != null ? unwrap.getMessage() : unwrap.toString(), unwrap));
        }
    }

    protected Object readOutputArgumentValues(Action<LocalService> action, Object obj) throws Exception {
        Object[] objArr = new Object[action.getOutputArguments().length];
        log.fine("Attempting to retrieve output argument values using accessor: " + objArr.length);
        ActionArgument<LocalService>[] outputArguments = action.getOutputArguments();
        int length = outputArguments.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            ActionArgument<LocalService> actionArgument = outputArguments[i2];
            log.finer("Calling acccessor method for: " + actionArgument);
            StateVariableAccessor stateVariableAccessor = getOutputArgumentAccessors().get(actionArgument);
            if (stateVariableAccessor != null) {
                log.fine("Calling accessor to read output argument value: " + stateVariableAccessor);
                i++;
                objArr[i] = stateVariableAccessor.read(obj);
            } else {
                throw new IllegalStateException("No accessor bound for: " + actionArgument);
            }
        }
        if (objArr.length == 1) {
            return objArr[0];
        }
        if (objArr.length > 0) {
            return objArr;
        }
        return null;
    }

    protected void setOutputArgumentValue(ActionInvocation<LocalService> actionInvocation, ActionArgument<LocalService> actionArgument, Object obj) throws ActionException {
        LocalService service = actionInvocation.getAction().getService();
        if (obj != null) {
            try {
                if (service.isStringConvertibleType(obj)) {
                    log.fine("Result of invocation matches convertible type, setting toString() single output argument value");
                    actionInvocation.setOutput(new ActionArgumentValue<>(actionArgument, obj.toString()));
                } else {
                    log.fine("Result of invocation is Object, setting single output argument value");
                    actionInvocation.setOutput(new ActionArgumentValue<>(actionArgument, obj));
                }
            } catch (InvalidValueException e) {
                ErrorCode errorCode = ErrorCode.ARGUMENT_VALUE_INVALID;
                throw new ActionException(errorCode, "Wrong type or invalid value for '" + actionArgument.getName() + "': " + e.getMessage(), e);
            }
        } else {
            log.fine("Result of invocation is null, not setting any output argument value(s)");
        }
    }
}
