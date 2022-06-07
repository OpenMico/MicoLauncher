package org.fourthline.cling.model.types;

/* loaded from: classes5.dex */
public class IntegerDatatype extends AbstractDatatype<Integer> {
    private int byteSize;

    public IntegerDatatype(int i) {
        this.byteSize = i;
    }

    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public boolean isHandlingJavaType(Class cls) {
        return cls == Integer.TYPE || Integer.class.isAssignableFrom(cls);
    }

    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public Integer valueOf(String str) throws InvalidValueException {
        if (str.equals("")) {
            return null;
        }
        try {
            Integer valueOf = Integer.valueOf(Integer.parseInt(str.trim()));
            if (isValid(valueOf)) {
                return valueOf;
            }
            throw new InvalidValueException("Not a " + getByteSize() + " byte(s) integer: " + str);
        } catch (NumberFormatException e) {
            if (str.equals("NOT_IMPLEMENTED")) {
                return Integer.valueOf(getMaxValue());
            }
            throw new InvalidValueException("Can't convert string to number: " + str, e);
        }
    }

    public boolean isValid(Integer num) {
        return num == null || (num.intValue() >= getMinValue() && num.intValue() <= getMaxValue());
    }

    public int getMinValue() {
        int byteSize = getByteSize();
        if (byteSize == 4) {
            return Integer.MIN_VALUE;
        }
        switch (byteSize) {
            case 1:
                return -128;
            case 2:
                return -32768;
            default:
                throw new IllegalArgumentException("Invalid integer byte size: " + getByteSize());
        }
    }

    public int getMaxValue() {
        int byteSize = getByteSize();
        if (byteSize == 4) {
            return Integer.MAX_VALUE;
        }
        switch (byteSize) {
            case 1:
                return 127;
            case 2:
                return 32767;
            default:
                throw new IllegalArgumentException("Invalid integer byte size: " + getByteSize());
        }
    }

    public int getByteSize() {
        return this.byteSize;
    }
}
