package org.fourthline.cling.model.types;

/* loaded from: classes5.dex */
public class DoubleDatatype extends AbstractDatatype<Double> {
    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public boolean isHandlingJavaType(Class cls) {
        return cls == Double.TYPE || Double.class.isAssignableFrom(cls);
    }

    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public Double valueOf(String str) throws InvalidValueException {
        if (str.equals("")) {
            return null;
        }
        try {
            return Double.valueOf(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Can't convert string to number: " + str, e);
        }
    }
}
