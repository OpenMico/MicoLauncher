package org.fourthline.cling.model.types;

/* loaded from: classes5.dex */
public class FloatDatatype extends AbstractDatatype<Float> {
    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public boolean isHandlingJavaType(Class cls) {
        return cls == Float.TYPE || Float.class.isAssignableFrom(cls);
    }

    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public Float valueOf(String str) throws InvalidValueException {
        if (str.equals("")) {
            return null;
        }
        try {
            return Float.valueOf(Float.parseFloat(str.trim()));
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Can't convert string to number: " + str, e);
        }
    }
}
