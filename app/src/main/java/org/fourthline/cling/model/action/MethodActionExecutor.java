package org.fourthline.cling.model.action;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;
import org.fourthline.cling.model.meta.ActionArgument;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.profile.RemoteClientInfo;
import org.fourthline.cling.model.state.StateVariableAccessor;
import org.fourthline.cling.model.types.ErrorCode;
import org.seamless.util.Reflections;

/* loaded from: classes5.dex */
public class MethodActionExecutor extends AbstractActionExecutor {
    private static Logger log = Logger.getLogger(MethodActionExecutor.class.getName());
    protected Method method;

    public MethodActionExecutor(Method method) {
        this.method = method;
    }

    public MethodActionExecutor(Map<ActionArgument<LocalService>, StateVariableAccessor> map, Method method) {
        super(map);
        this.method = method;
    }

    public Method getMethod() {
        return this.method;
    }

    @Override // org.fourthline.cling.model.action.AbstractActionExecutor
    protected void execute(ActionInvocation<LocalService> actionInvocation, Object obj) throws Exception {
        boolean z;
        Object obj2;
        Object[] createInputArgumentValues = createInputArgumentValues(actionInvocation, this.method);
        if (!actionInvocation.getAction().hasOutputArguments()) {
            log.fine("Calling local service method with no output arguments: " + this.method);
            Reflections.invoke(this.method, obj, createInputArgumentValues);
            return;
        }
        boolean equals = this.method.getReturnType().equals(Void.TYPE);
        log.fine("Calling local service method with output arguments: " + this.method);
        if (equals) {
            log.fine("Action method is void, calling declared accessors(s) on service instance to retrieve ouput argument(s)");
            Reflections.invoke(this.method, obj, createInputArgumentValues);
            obj2 = readOutputArgumentValues(actionInvocation.getAction(), obj);
            z = true;
        } else if (isUseOutputArgumentAccessors(actionInvocation)) {
            log.fine("Action method is not void, calling declared accessor(s) on returned instance to retrieve ouput argument(s)");
            obj2 = readOutputArgumentValues(actionInvocation.getAction(), Reflections.invoke(this.method, obj, createInputArgumentValues));
            z = true;
        } else {
            log.fine("Action method is not void, using returned value as (single) output argument");
            obj2 = Reflections.invoke(this.method, obj, createInputArgumentValues);
            z = false;
        }
        ActionArgument<LocalService>[] outputArguments = actionInvocation.getAction().getOutputArguments();
        if (z && (obj2 instanceof Object[])) {
            Object[] objArr = (Object[]) obj2;
            log.fine("Accessors returned Object[], setting output argument values: " + objArr.length);
            for (int i = 0; i < outputArguments.length; i++) {
                setOutputArgumentValue(actionInvocation, outputArguments[i], objArr[i]);
            }
        } else if (outputArguments.length == 1) {
            setOutputArgumentValue(actionInvocation, outputArguments[0], obj2);
        } else {
            throw new ActionException(ErrorCode.ACTION_FAILED, "Method return does not match required number of output arguments: " + outputArguments.length);
        }
    }

    protected boolean isUseOutputArgumentAccessors(ActionInvocation<LocalService> actionInvocation) {
        for (ActionArgument<LocalService> actionArgument : actionInvocation.getAction().getOutputArguments()) {
            if (getOutputArgumentAccessors().get(actionArgument) != null) {
                return true;
            }
        }
        return false;
    }

    protected Object[] createInputArgumentValues(ActionInvocation<LocalService> actionInvocation, Method method) throws ActionException {
        LocalService service = actionInvocation.getAction().getService();
        ArrayList arrayList = new ArrayList();
        ActionArgument<LocalService>[] inputArguments = actionInvocation.getAction().getInputArguments();
        char c = 0;
        int i = 0;
        for (ActionArgument<LocalService> actionArgument : inputArguments) {
            Class<?> cls = method.getParameterTypes()[i];
            ActionArgumentValue<LocalService> input = actionInvocation.getInput(actionArgument);
            if (!cls.isPrimitive() || !(input == null || input.toString().length() == 0)) {
                if (input == null) {
                    i++;
                    arrayList.add(i, null);
                    c = c;
                } else {
                    String actionArgumentValue = input.toString();
                    if (actionArgumentValue.length() <= 0 || !service.isStringConvertibleType((Class) cls) || cls.isEnum()) {
                        c = c;
                        i++;
                        arrayList.add(i, input.getValue());
                    } else {
                        try {
                            Class<?>[] clsArr = new Class[1];
                            clsArr[c] = String.class;
                            Constructor<?> constructor = cls.getConstructor(clsArr);
                            log.finer("Creating new input argument value instance with String.class constructor of type: " + cls);
                            c = 0;
                            i++;
                            arrayList.add(i, constructor.newInstance(actionArgumentValue));
                        } catch (Exception e) {
                            log.warning("Error preparing action method call: " + method);
                            log.warning("Can't convert input argument string to desired type of '" + actionArgument.getName() + "': " + e);
                            throw new ActionException(ErrorCode.ARGUMENT_VALUE_INVALID, "Can't convert input argument string to desired type of '" + actionArgument.getName() + "': " + e);
                        }
                    }
                }
            } else {
                throw new ActionException(ErrorCode.ARGUMENT_VALUE_INVALID, "Primitive action method argument '" + actionArgument.getName() + "' requires input value, can't be null or empty string");
            }
        }
        if (method.getParameterTypes().length > 0 && RemoteClientInfo.class.isAssignableFrom(method.getParameterTypes()[method.getParameterTypes().length - 1])) {
            if (actionInvocation instanceof RemoteActionInvocation) {
                RemoteActionInvocation remoteActionInvocation = (RemoteActionInvocation) actionInvocation;
                if (remoteActionInvocation.getRemoteClientInfo() != null) {
                    log.finer("Providing remote client info as last action method input argument: " + method);
                    arrayList.add(i, remoteActionInvocation.getRemoteClientInfo());
                }
            }
            arrayList.add(i, null);
        }
        return arrayList.toArray(new Object[arrayList.size()]);
    }
}
