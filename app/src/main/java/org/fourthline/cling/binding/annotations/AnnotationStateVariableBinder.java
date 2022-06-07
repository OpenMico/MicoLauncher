package org.fourthline.cling.binding.annotations;

import java.util.Set;
import java.util.logging.Logger;
import org.fourthline.cling.binding.AllowedValueProvider;
import org.fourthline.cling.binding.AllowedValueRangeProvider;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.model.ModelUtil;
import org.fourthline.cling.model.meta.StateVariable;
import org.fourthline.cling.model.meta.StateVariableAllowedValueRange;
import org.fourthline.cling.model.meta.StateVariableEventDetails;
import org.fourthline.cling.model.meta.StateVariableTypeDetails;
import org.fourthline.cling.model.state.StateVariableAccessor;
import org.fourthline.cling.model.types.Datatype;

/* loaded from: classes5.dex */
public class AnnotationStateVariableBinder {
    private static Logger log = Logger.getLogger(AnnotationLocalServiceBinder.class.getName());
    protected StateVariableAccessor accessor;
    protected UpnpStateVariable annotation;
    protected String name;
    protected Set<Class> stringConvertibleTypes;

    public AnnotationStateVariableBinder(UpnpStateVariable upnpStateVariable, String str, StateVariableAccessor stateVariableAccessor, Set<Class> set) {
        this.annotation = upnpStateVariable;
        this.name = str;
        this.accessor = stateVariableAccessor;
        this.stringConvertibleTypes = set;
    }

    public UpnpStateVariable getAnnotation() {
        return this.annotation;
    }

    public String getName() {
        return this.name;
    }

    public StateVariableAccessor getAccessor() {
        return this.accessor;
    }

    public Set<Class> getStringConvertibleTypes() {
        return this.stringConvertibleTypes;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public StateVariable createStateVariable() throws LocalServiceBindingException {
        String[] strArr;
        int i;
        boolean z;
        log.fine("Creating state variable '" + getName() + "' with accessor: " + getAccessor());
        Datatype createDatatype = createDatatype();
        String createDefaultValue = createDefaultValue(createDatatype);
        StateVariableAllowedValueRange stateVariableAllowedValueRange = null;
        int i2 = 0;
        if (Datatype.Builtin.STRING.equals(createDatatype.getBuiltin())) {
            if (getAnnotation().allowedValueProvider() != Void.TYPE) {
                strArr = getAllowedValuesFromProvider();
            } else if (getAnnotation().allowedValues().length > 0) {
                strArr = getAnnotation().allowedValues();
            } else if (getAnnotation().allowedValuesEnum() != Void.TYPE) {
                strArr = getAllowedValues(getAnnotation().allowedValuesEnum());
            } else if (getAccessor() == null || !getAccessor().getReturnType().isEnum()) {
                log.finer("Not restricting allowed values (of string typed state var): " + getName());
                strArr = null;
            } else {
                strArr = getAllowedValues(getAccessor().getReturnType());
            }
            if (!(strArr == null || createDefaultValue == null)) {
                int length = strArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        z = false;
                        break;
                    } else if (strArr[i3].equals(createDefaultValue)) {
                        z = true;
                        break;
                    } else {
                        i3++;
                    }
                }
                if (!z) {
                    throw new LocalServiceBindingException("Default value '" + createDefaultValue + "' is not in allowed values of: " + getName());
                }
            }
        } else {
            strArr = null;
        }
        if (Datatype.Builtin.isNumeric(createDatatype.getBuiltin())) {
            if (getAnnotation().allowedValueRangeProvider() != Void.TYPE) {
                stateVariableAllowedValueRange = getAllowedRangeFromProvider();
            } else if (getAnnotation().allowedValueMinimum() > 0 || getAnnotation().allowedValueMaximum() > 0) {
                stateVariableAllowedValueRange = getAllowedValueRange(getAnnotation().allowedValueMinimum(), getAnnotation().allowedValueMaximum(), getAnnotation().allowedValueStep());
            } else {
                log.finer("Not restricting allowed value range (of numeric typed state var): " + getName());
            }
            if (!(createDefaultValue == null || stateVariableAllowedValueRange == null)) {
                try {
                    if (!stateVariableAllowedValueRange.isInRange(Long.valueOf(createDefaultValue).longValue())) {
                        throw new LocalServiceBindingException("Default value '" + createDefaultValue + "' is not in allowed range of: " + getName());
                    }
                } catch (Exception unused) {
                    throw new LocalServiceBindingException("Default value '" + createDefaultValue + "' is not numeric (for range checking) of: " + getName());
                }
            }
        }
        boolean sendEvents = getAnnotation().sendEvents();
        if (!sendEvents || getAccessor() != null) {
            if (sendEvents) {
                if (getAnnotation().eventMaximumRateMilliseconds() > 0) {
                    log.finer("Moderating state variable events using maximum rate (milliseconds): " + getAnnotation().eventMaximumRateMilliseconds());
                    i = getAnnotation().eventMaximumRateMilliseconds();
                } else {
                    i = 0;
                }
                if (getAnnotation().eventMinimumDelta() > 0 && Datatype.Builtin.isNumeric(createDatatype.getBuiltin())) {
                    log.finer("Moderating state variable events using minimum delta: " + getAnnotation().eventMinimumDelta());
                    i2 = getAnnotation().eventMinimumDelta();
                }
            } else {
                i = 0;
            }
            return new StateVariable(getName(), new StateVariableTypeDetails(createDatatype, createDefaultValue, strArr, stateVariableAllowedValueRange), new StateVariableEventDetails(sendEvents, i, i2));
        }
        throw new LocalServiceBindingException("State variable sends events but has no accessor for field or getter: " + getName());
    }

    protected Datatype createDatatype() throws LocalServiceBindingException {
        String datatype = getAnnotation().datatype();
        if (datatype.length() == 0 && getAccessor() != null) {
            Class<?> returnType = getAccessor().getReturnType();
            Logger logger = log;
            logger.finer("Using accessor return type as state variable type: " + returnType);
            if (ModelUtil.isStringConvertibleType(getStringConvertibleTypes(), returnType)) {
                log.finer("Return type is string-convertible, using string datatype");
                return Datatype.Default.STRING.getBuiltinType().getDatatype();
            }
            Datatype.Default byJavaType = Datatype.Default.getByJavaType(returnType);
            if (byJavaType != null) {
                Logger logger2 = log;
                logger2.finer("Return type has default UPnP datatype: " + byJavaType);
                return byJavaType.getBuiltinType().getDatatype();
            }
        }
        if ((datatype == null || datatype.length() == 0) && (getAnnotation().allowedValues().length > 0 || getAnnotation().allowedValuesEnum() != Void.TYPE)) {
            log.finer("State variable has restricted allowed values, hence using 'string' datatype");
            datatype = "string";
        }
        if (datatype == null || datatype.length() == 0) {
            throw new LocalServiceBindingException("Could not detect datatype of state variable: " + getName());
        }
        Logger logger3 = log;
        logger3.finer("Trying to find built-in UPnP datatype for detected name: " + datatype);
        Datatype.Builtin byDescriptorName = Datatype.Builtin.getByDescriptorName(datatype);
        if (byDescriptorName != null) {
            Logger logger4 = log;
            logger4.finer("Found built-in UPnP datatype: " + byDescriptorName);
            return byDescriptorName.getDatatype();
        }
        throw new LocalServiceBindingException("No built-in UPnP datatype found, using CustomDataType (TODO: NOT IMPLEMENTED)");
    }

    protected String createDefaultValue(Datatype datatype) throws LocalServiceBindingException {
        if (getAnnotation().defaultValue().length() == 0) {
            return null;
        }
        try {
            datatype.valueOf(getAnnotation().defaultValue());
            Logger logger = log;
            logger.finer("Found state variable default value: " + getAnnotation().defaultValue());
            return getAnnotation().defaultValue();
        } catch (Exception e) {
            throw new LocalServiceBindingException("Default value doesn't match datatype of state variable '" + getName() + "': " + e.getMessage());
        }
    }

    protected String[] getAllowedValues(Class cls) throws LocalServiceBindingException {
        if (cls.isEnum()) {
            Logger logger = log;
            logger.finer("Restricting allowed values of state variable to Enum: " + getName());
            String[] strArr = new String[cls.getEnumConstants().length];
            for (int i = 0; i < cls.getEnumConstants().length; i++) {
                Object obj = cls.getEnumConstants()[i];
                if (obj.toString().length() <= 32) {
                    Logger logger2 = log;
                    logger2.finer("Adding allowed value (converted to string): " + obj.toString());
                    strArr[i] = obj.toString();
                } else {
                    throw new LocalServiceBindingException("Allowed value string (that is, Enum constant name) is longer than 32 characters: " + obj.toString());
                }
            }
            return strArr;
        }
        throw new LocalServiceBindingException("Allowed values type is not an Enum: " + cls);
    }

    protected StateVariableAllowedValueRange getAllowedValueRange(long j, long j2, long j3) throws LocalServiceBindingException {
        if (j2 >= j) {
            return new StateVariableAllowedValueRange(j, j2, j3);
        }
        throw new LocalServiceBindingException("Allowed value range maximum is smaller than minimum: " + getName());
    }

    protected String[] getAllowedValuesFromProvider() throws LocalServiceBindingException {
        Class allowedValueProvider = getAnnotation().allowedValueProvider();
        if (AllowedValueProvider.class.isAssignableFrom(allowedValueProvider)) {
            try {
                return ((AllowedValueProvider) allowedValueProvider.newInstance()).getValues();
            } catch (Exception e) {
                throw new LocalServiceBindingException("Allowed value provider can't be instantiated: " + getName(), e);
            }
        } else {
            throw new LocalServiceBindingException("Allowed value provider is not of type " + AllowedValueProvider.class + ": " + getName());
        }
    }

    protected StateVariableAllowedValueRange getAllowedRangeFromProvider() throws LocalServiceBindingException {
        Class allowedValueRangeProvider = getAnnotation().allowedValueRangeProvider();
        if (AllowedValueRangeProvider.class.isAssignableFrom(allowedValueRangeProvider)) {
            try {
                AllowedValueRangeProvider allowedValueRangeProvider2 = (AllowedValueRangeProvider) allowedValueRangeProvider.newInstance();
                return getAllowedValueRange(allowedValueRangeProvider2.getMinimum(), allowedValueRangeProvider2.getMaximum(), allowedValueRangeProvider2.getStep());
            } catch (Exception e) {
                throw new LocalServiceBindingException("Allowed value range provider can't be instantiated: " + getName(), e);
            }
        } else {
            throw new LocalServiceBindingException("Allowed value range provider is not of type " + AllowedValueRangeProvider.class + ": " + getName());
        }
    }
}
