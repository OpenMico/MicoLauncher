package com.xiaomi.miot.typedef.field;

/* loaded from: classes3.dex */
public enum FieldType {
    UNKNOWN("unkown"),
    BOOLEAN("boolean"),
    FLOAT("float"),
    DOUBLE("double"),
    INTEGER("integer"),
    LONG("long"),
    STRING("string");
    
    private String string;

    FieldType(String str) {
        this.string = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.string;
    }

    public static FieldType retrieveType(String str) {
        FieldType[] values = values();
        for (FieldType fieldType : values) {
            if (fieldType.toString().equals(str)) {
                return fieldType;
            }
        }
        return UNKNOWN;
    }

    public Object createObjectValue() {
        switch (this) {
            case UNKNOWN:
            default:
                return null;
            case BOOLEAN:
                return false;
            case FLOAT:
                return Float.valueOf(0.0f);
            case DOUBLE:
                return Double.valueOf(0.0d);
            case INTEGER:
                return 0;
            case LONG:
                return 0L;
            case STRING:
                return "";
        }
    }
}
