package org.fourthline.cling.model.types;

/* loaded from: classes5.dex */
public class UnsignedIntegerFourBytesDatatype extends AbstractDatatype<UnsignedIntegerFourBytes> {
    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public UnsignedIntegerFourBytes valueOf(String str) throws InvalidValueException {
        if (str.equals("")) {
            return null;
        }
        try {
            return new UnsignedIntegerFourBytes(str);
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Can't convert string to number or not in range: " + str, e);
        }
    }
}
