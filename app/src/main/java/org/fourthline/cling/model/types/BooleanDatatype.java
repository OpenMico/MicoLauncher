package org.fourthline.cling.model.types;

import java.util.Locale;

/* loaded from: classes5.dex */
public class BooleanDatatype extends AbstractDatatype<Boolean> {
    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public boolean isHandlingJavaType(Class cls) {
        return cls == Boolean.TYPE || Boolean.class.isAssignableFrom(cls);
    }

    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public Boolean valueOf(String str) throws InvalidValueException {
        if (str.equals("")) {
            return null;
        }
        if (str.equals("1") || str.toUpperCase(Locale.ROOT).equals("YES") || str.toUpperCase(Locale.ROOT).equals("TRUE")) {
            return true;
        }
        if (str.equals("0") || str.toUpperCase(Locale.ROOT).equals("NO") || str.toUpperCase(Locale.ROOT).equals("FALSE")) {
            return false;
        }
        throw new InvalidValueException("Invalid boolean value string: " + str);
    }

    public String getString(Boolean bool) throws InvalidValueException {
        return bool == null ? "" : bool.booleanValue() ? "1" : "0";
    }
}
