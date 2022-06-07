package org.fourthline.cling.binding.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.model.Constants;
import org.fourthline.cling.model.ModelUtil;
import org.fourthline.cling.model.action.ActionExecutor;
import org.fourthline.cling.model.action.MethodActionExecutor;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.ActionArgument;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.StateVariable;
import org.fourthline.cling.model.profile.RemoteClientInfo;
import org.fourthline.cling.model.state.GetterStateVariableAccessor;
import org.fourthline.cling.model.state.StateVariableAccessor;
import org.fourthline.cling.model.types.Datatype;
import org.seamless.util.Reflections;

/* loaded from: classes5.dex */
public class AnnotationActionBinder {
    private static Logger log = Logger.getLogger(AnnotationLocalServiceBinder.class.getName());
    protected UpnpAction annotation;
    protected Method method;
    protected Map<StateVariable, StateVariableAccessor> stateVariables;
    protected Set<Class> stringConvertibleTypes;

    public AnnotationActionBinder(Method method, Map<StateVariable, StateVariableAccessor> map, Set<Class> set) {
        this.annotation = (UpnpAction) method.getAnnotation(UpnpAction.class);
        this.stateVariables = map;
        this.method = method;
        this.stringConvertibleTypes = set;
    }

    public UpnpAction getAnnotation() {
        return this.annotation;
    }

    public Map<StateVariable, StateVariableAccessor> getStateVariables() {
        return this.stateVariables;
    }

    public Method getMethod() {
        return this.method;
    }

    public Set<Class> getStringConvertibleTypes() {
        return this.stringConvertibleTypes;
    }

    public Action appendAction(Map<Action, ActionExecutor> map) throws LocalServiceBindingException {
        String str;
        if (getAnnotation().name().length() != 0) {
            str = getAnnotation().name();
        } else {
            str = AnnotationLocalServiceBinder.toUpnpActionName(getMethod().getName());
        }
        Logger logger = log;
        logger.fine("Creating action and executor: " + str);
        List<ActionArgument> createInputArguments = createInputArguments();
        Map<ActionArgument<LocalService>, StateVariableAccessor> createOutputArguments = createOutputArguments();
        createInputArguments.addAll(createOutputArguments.keySet());
        Action action = new Action(str, (ActionArgument[]) createInputArguments.toArray(new ActionArgument[createInputArguments.size()]));
        map.put(action, createExecutor(createOutputArguments));
        return action;
    }

    protected ActionExecutor createExecutor(Map<ActionArgument<LocalService>, StateVariableAccessor> map) {
        return new MethodActionExecutor(map, getMethod());
    }

    protected List<ActionArgument> createInputArguments() throws LocalServiceBindingException {
        ArrayList arrayList = new ArrayList();
        Annotation[][] parameterAnnotations = getMethod().getParameterAnnotations();
        int i = 0;
        int i2 = 0;
        while (i < parameterAnnotations.length) {
            Annotation[] annotationArr = parameterAnnotations[i];
            int i3 = i2;
            for (Annotation annotation : annotationArr) {
                if (annotation instanceof UpnpInputArgument) {
                    UpnpInputArgument upnpInputArgument = (UpnpInputArgument) annotation;
                    i3++;
                    String name = upnpInputArgument.name();
                    StateVariable findRelatedStateVariable = findRelatedStateVariable(upnpInputArgument.stateVariable(), name, getMethod().getName());
                    if (findRelatedStateVariable != null) {
                        validateType(findRelatedStateVariable, getMethod().getParameterTypes()[i]);
                        arrayList.add(new ActionArgument(name, upnpInputArgument.aliases(), findRelatedStateVariable.getName(), ActionArgument.Direction.IN));
                    } else {
                        throw new LocalServiceBindingException("Could not detected related state variable of argument: " + name);
                    }
                }
            }
            i++;
            i2 = i3;
        }
        if (i2 >= getMethod().getParameterTypes().length || RemoteClientInfo.class.isAssignableFrom(this.method.getParameterTypes()[this.method.getParameterTypes().length - 1])) {
            return arrayList;
        }
        throw new LocalServiceBindingException("Method has parameters that are not input arguments: " + getMethod().getName());
    }

    protected Map<ActionArgument<LocalService>, StateVariableAccessor> createOutputArguments() throws LocalServiceBindingException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        UpnpAction upnpAction = (UpnpAction) getMethod().getAnnotation(UpnpAction.class);
        if (upnpAction.out().length == 0) {
            return linkedHashMap;
        }
        boolean z = true;
        if (upnpAction.out().length <= 1) {
            z = false;
        }
        UpnpOutputArgument[] out = upnpAction.out();
        for (UpnpOutputArgument upnpOutputArgument : out) {
            String name = upnpOutputArgument.name();
            StateVariable findRelatedStateVariable = findRelatedStateVariable(upnpOutputArgument.stateVariable(), name, getMethod().getName());
            if (findRelatedStateVariable == null && upnpOutputArgument.getterName().length() > 0) {
                findRelatedStateVariable = findRelatedStateVariable(null, null, upnpOutputArgument.getterName());
            }
            if (findRelatedStateVariable != null) {
                StateVariableAccessor findOutputArgumentAccessor = findOutputArgumentAccessor(findRelatedStateVariable, upnpOutputArgument.getterName(), z);
                log.finer("Found related state variable for output argument '" + name + "': " + findRelatedStateVariable);
                linkedHashMap.put(new ActionArgument(name, findRelatedStateVariable.getName(), ActionArgument.Direction.OUT, z ^ true), findOutputArgumentAccessor);
            } else {
                throw new LocalServiceBindingException("Related state variable not found for output argument: " + name);
            }
        }
        return linkedHashMap;
    }

    protected StateVariableAccessor findOutputArgumentAccessor(StateVariable stateVariable, String str, boolean z) throws LocalServiceBindingException {
        if (getMethod().getReturnType().equals(Void.TYPE)) {
            if (str == null || str.length() <= 0) {
                Logger logger = log;
                logger.finer("Action method is void, trying to find existing accessor of related: " + stateVariable);
                return getStateVariables().get(stateVariable);
            }
            Logger logger2 = log;
            logger2.finer("Action method is void, will use getter method named: " + str);
            Method method = Reflections.getMethod(getMethod().getDeclaringClass(), str);
            if (method != null) {
                validateType(stateVariable, method.getReturnType());
                return new GetterStateVariableAccessor(method);
            }
            throw new LocalServiceBindingException("Declared getter method '" + str + "' not found on: " + getMethod().getDeclaringClass());
        } else if (str != null && str.length() > 0) {
            Logger logger3 = log;
            logger3.finer("Action method is not void, will use getter method on returned instance: " + str);
            Method method2 = Reflections.getMethod(getMethod().getReturnType(), str);
            if (method2 != null) {
                validateType(stateVariable, method2.getReturnType());
                return new GetterStateVariableAccessor(method2);
            }
            throw new LocalServiceBindingException("Declared getter method '" + str + "' not found on return type: " + getMethod().getReturnType());
        } else if (z) {
            return null;
        } else {
            Logger logger4 = log;
            logger4.finer("Action method is not void, will use the returned instance: " + getMethod().getReturnType());
            validateType(stateVariable, getMethod().getReturnType());
            return null;
        }
    }

    protected StateVariable findRelatedStateVariable(String str, String str2, String str3) throws LocalServiceBindingException {
        String methodPropertyName;
        StateVariable stateVariable = (str == null || str.length() <= 0) ? null : getStateVariable(str);
        if (stateVariable == null && str2 != null && str2.length() > 0) {
            String upnpStateVariableName = AnnotationLocalServiceBinder.toUpnpStateVariableName(str2);
            log.finer("Finding related state variable with argument name (converted to UPnP name): " + upnpStateVariableName);
            stateVariable = getStateVariable(str2);
        }
        if (stateVariable == null && str2 != null && str2.length() > 0) {
            String str4 = Constants.ARG_TYPE_PREFIX + AnnotationLocalServiceBinder.toUpnpStateVariableName(str2);
            log.finer("Finding related state variable with prefixed argument name (converted to UPnP name): " + str4);
            stateVariable = getStateVariable(str4);
        }
        if (stateVariable != null || str3 == null || str3.length() <= 0 || (methodPropertyName = Reflections.getMethodPropertyName(str3)) == null) {
            return stateVariable;
        }
        log.finer("Finding related state variable with method property name: " + methodPropertyName);
        return getStateVariable(AnnotationLocalServiceBinder.toUpnpStateVariableName(methodPropertyName));
    }

    protected void validateType(StateVariable stateVariable, Class cls) throws LocalServiceBindingException {
        Datatype.Default byJavaType = ModelUtil.isStringConvertibleType(getStringConvertibleTypes(), cls) ? Datatype.Default.STRING : Datatype.Default.getByJavaType(cls);
        Logger logger = log;
        logger.finer("Expecting '" + stateVariable + "' to match default mapping: " + byJavaType);
        if (byJavaType != null && !stateVariable.getTypeDetails().getDatatype().isHandlingJavaType(byJavaType.getJavaType())) {
            throw new LocalServiceBindingException("State variable '" + stateVariable + "' datatype can't handle action argument's Java type (change one): " + byJavaType.getJavaType());
        } else if (byJavaType != null || stateVariable.getTypeDetails().getDatatype().getBuiltin() == null) {
            log.finer("State variable matches required argument datatype (or can't be validated because it is custom)");
        } else {
            throw new LocalServiceBindingException("State variable '" + stateVariable + "' should be custom datatype (action argument type is unknown Java type): " + cls.getSimpleName());
        }
    }

    protected StateVariable getStateVariable(String str) {
        for (StateVariable stateVariable : getStateVariables().keySet()) {
            if (stateVariable.getName().equals(str)) {
                return stateVariable;
            }
        }
        return null;
    }
}
