package org.fourthline.cling.binding.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.fourthline.cling.binding.LocalServiceBinder;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.model.ValidationError;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.action.ActionExecutor;
import org.fourthline.cling.model.action.QueryStateVariableExecutor;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.QueryStateVariableAction;
import org.fourthline.cling.model.meta.StateVariable;
import org.fourthline.cling.model.state.FieldStateVariableAccessor;
import org.fourthline.cling.model.state.GetterStateVariableAccessor;
import org.fourthline.cling.model.state.StateVariableAccessor;
import org.fourthline.cling.model.types.ServiceId;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDAServiceId;
import org.fourthline.cling.model.types.UDAServiceType;
import org.fourthline.cling.model.types.csv.CSV;
import org.seamless.util.Reflections;

/* loaded from: classes5.dex */
public class AnnotationLocalServiceBinder implements LocalServiceBinder {
    private static Logger log = Logger.getLogger(AnnotationLocalServiceBinder.class.getName());

    protected boolean isActionExcluded(Action action) {
        return false;
    }

    @Override // org.fourthline.cling.binding.LocalServiceBinder
    public LocalService read(Class<?> cls) throws LocalServiceBindingException {
        ServiceId serviceId;
        ServiceType serviceType;
        Logger logger = log;
        logger.fine("Reading and binding annotations of service implementation class: " + cls);
        if (cls.isAnnotationPresent(UpnpService.class)) {
            UpnpService upnpService = (UpnpService) cls.getAnnotation(UpnpService.class);
            UpnpServiceId serviceId2 = upnpService.serviceId();
            UpnpServiceType serviceType2 = upnpService.serviceType();
            if (serviceId2.namespace().equals(UDAServiceId.DEFAULT_NAMESPACE)) {
                serviceId = new UDAServiceId(serviceId2.value());
            } else {
                serviceId = new ServiceId(serviceId2.namespace(), serviceId2.value());
            }
            if (serviceType2.namespace().equals("schemas-upnp-org")) {
                serviceType = new UDAServiceType(serviceType2.value(), serviceType2.version());
            } else {
                serviceType = new ServiceType(serviceType2.namespace(), serviceType2.value(), serviceType2.version());
            }
            return read(cls, serviceId, serviceType, upnpService.supportsQueryStateVariables(), readStringConvertibleTypes(upnpService.stringConvertibleTypes()));
        }
        throw new LocalServiceBindingException("Given class is not an @UpnpService");
    }

    @Override // org.fourthline.cling.binding.LocalServiceBinder
    public LocalService read(Class<?> cls, ServiceId serviceId, ServiceType serviceType, boolean z, Class[] clsArr) throws LocalServiceBindingException {
        return read(cls, serviceId, serviceType, z, new HashSet(Arrays.asList(clsArr)));
    }

    public LocalService read(Class<?> cls, ServiceId serviceId, ServiceType serviceType, boolean z, Set<Class> set) throws LocalServiceBindingException {
        Map<StateVariable, StateVariableAccessor> readStateVariables = readStateVariables(cls, set);
        Map<Action, ActionExecutor> readActions = readActions(cls, readStateVariables, set);
        if (z) {
            readActions.put(new QueryStateVariableAction(), new QueryStateVariableExecutor());
        }
        try {
            return new LocalService(serviceType, serviceId, readActions, readStateVariables, set, z);
        } catch (ValidationException e) {
            Logger logger = log;
            logger.severe("Could not validate device model: " + e.toString());
            for (ValidationError validationError : e.getErrors()) {
                log.severe(validationError.toString());
            }
            throw new LocalServiceBindingException("Validation of model failed, check the log");
        }
    }

    protected Set<Class> readStringConvertibleTypes(Class[] clsArr) throws LocalServiceBindingException {
        for (Class cls : clsArr) {
            if (Modifier.isPublic(cls.getModifiers())) {
                try {
                    cls.getConstructor(String.class);
                } catch (NoSuchMethodException unused) {
                    throw new LocalServiceBindingException("Declared string-convertible type needs a public single-argument String constructor: " + cls);
                }
            } else {
                throw new LocalServiceBindingException("Declared string-convertible type must be public: " + cls);
            }
        }
        HashSet hashSet = new HashSet(Arrays.asList(clsArr));
        hashSet.add(URI.class);
        hashSet.add(URL.class);
        hashSet.add(CSV.class);
        return hashSet;
    }

    protected Map<StateVariable, StateVariableAccessor> readStateVariables(Class<?> cls, Set<Class> set) throws LocalServiceBindingException {
        String str;
        String str2;
        HashMap hashMap = new HashMap();
        if (cls.isAnnotationPresent(UpnpStateVariables.class)) {
            UpnpStateVariables upnpStateVariables = (UpnpStateVariables) cls.getAnnotation(UpnpStateVariables.class);
            UpnpStateVariable[] value = upnpStateVariables.value();
            for (UpnpStateVariable upnpStateVariable : value) {
                if (upnpStateVariable.name().length() != 0) {
                    String javaStateVariableName = toJavaStateVariableName(upnpStateVariable.name());
                    Method getterMethod = Reflections.getGetterMethod(cls, javaStateVariableName);
                    Field field = Reflections.getField(cls, javaStateVariableName);
                    StateVariableAccessor stateVariableAccessor = null;
                    if (getterMethod != null && field != null) {
                        stateVariableAccessor = upnpStateVariables.preferFields() ? new FieldStateVariableAccessor(field) : new GetterStateVariableAccessor(getterMethod);
                    } else if (field != null) {
                        stateVariableAccessor = new FieldStateVariableAccessor(field);
                    } else if (getterMethod != null) {
                        stateVariableAccessor = new GetterStateVariableAccessor(getterMethod);
                    } else {
                        log.finer("No field or getter found for state variable, skipping accessor: " + upnpStateVariable.name());
                    }
                    hashMap.put(new AnnotationStateVariableBinder(upnpStateVariable, upnpStateVariable.name(), stateVariableAccessor, set).createStateVariable(), stateVariableAccessor);
                } else {
                    throw new LocalServiceBindingException("Class-level @UpnpStateVariable name attribute value required");
                }
            }
        }
        for (Field field2 : Reflections.getFields(cls, UpnpStateVariable.class)) {
            UpnpStateVariable upnpStateVariable2 = (UpnpStateVariable) field2.getAnnotation(UpnpStateVariable.class);
            FieldStateVariableAccessor fieldStateVariableAccessor = new FieldStateVariableAccessor(field2);
            if (upnpStateVariable2.name().length() == 0) {
                str2 = toUpnpStateVariableName(field2.getName());
            } else {
                str2 = upnpStateVariable2.name();
            }
            hashMap.put(new AnnotationStateVariableBinder(upnpStateVariable2, str2, fieldStateVariableAccessor, set).createStateVariable(), fieldStateVariableAccessor);
        }
        for (Method method : Reflections.getMethods(cls, UpnpStateVariable.class)) {
            String methodPropertyName = Reflections.getMethodPropertyName(method.getName());
            if (methodPropertyName == null) {
                throw new LocalServiceBindingException("Annotated method is not a getter method (: " + method);
            } else if (method.getParameterTypes().length <= 0) {
                UpnpStateVariable upnpStateVariable3 = (UpnpStateVariable) method.getAnnotation(UpnpStateVariable.class);
                GetterStateVariableAccessor getterStateVariableAccessor = new GetterStateVariableAccessor(method);
                if (upnpStateVariable3.name().length() == 0) {
                    str = toUpnpStateVariableName(methodPropertyName);
                } else {
                    str = upnpStateVariable3.name();
                }
                hashMap.put(new AnnotationStateVariableBinder(upnpStateVariable3, str, getterStateVariableAccessor, set).createStateVariable(), getterStateVariableAccessor);
            } else {
                throw new LocalServiceBindingException("Getter method defined as @UpnpStateVariable can not have parameters: " + method);
            }
        }
        return hashMap;
    }

    protected Map<Action, ActionExecutor> readActions(Class<?> cls, Map<StateVariable, StateVariableAccessor> map, Set<Class> set) throws LocalServiceBindingException {
        HashMap hashMap = new HashMap();
        for (Method method : Reflections.getMethods(cls, UpnpAction.class)) {
            Action appendAction = new AnnotationActionBinder(method, map, set).appendAction(hashMap);
            if (isActionExcluded(appendAction)) {
                hashMap.remove(appendAction);
            }
        }
        return hashMap;
    }

    public static String toUpnpStateVariableName(String str) {
        if (str.length() >= 1) {
            return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
        }
        throw new IllegalArgumentException("Variable name must be at least 1 character long");
    }

    static String toJavaStateVariableName(String str) {
        if (str.length() >= 1) {
            return str.substring(0, 1).toLowerCase(Locale.ROOT) + str.substring(1);
        }
        throw new IllegalArgumentException("Variable name must be at least 1 character long");
    }

    public static String toUpnpActionName(String str) {
        if (str.length() >= 1) {
            return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
        }
        throw new IllegalArgumentException("Action name must be at least 1 character long");
    }

    static String toJavaActionName(String str) {
        if (str.length() >= 1) {
            return str.substring(0, 1).toLowerCase(Locale.ROOT) + str.substring(1);
        }
        throw new IllegalArgumentException("Variable name must be at least 1 character long");
    }
}
