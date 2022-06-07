package com.xiaomi.miot.typedef.data;

import android.util.Log;
import com.xiaomi.miot.typedef.data.value.Vbool;
import com.xiaomi.miot.typedef.data.value.Vfloat;
import com.xiaomi.miot.typedef.data.value.Vint;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.data.value.Vuint16;
import com.xiaomi.miot.typedef.data.value.Vuint32;
import com.xiaomi.miot.typedef.data.value.Vuint64;
import com.xiaomi.miot.typedef.data.value.Vuint8;
import com.xiaomi.miot.typedef.exception.InvalidValueException;

/* loaded from: classes3.dex */
public enum DataType {
    UNKNOWN("unknown"),
    BOOL("bool"),
    UINT8("uint8"),
    UINT16("uint16"),
    UINT32("uint32"),
    UINT64("uint64"),
    INT("int"),
    FLOAT("float"),
    STRING("string");
    
    private static final String TAG = "DataType";
    private String value;

    DataType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static DataType retrieveType(String str) throws InvalidValueException {
        DataType[] values = values();
        for (DataType dataType : values) {
            if (dataType.toString().equals(str)) {
                return dataType;
            }
        }
        throw new InvalidValueException("DataType invalid: " + str);
    }

    public boolean validate(DataValue dataValue) {
        switch (this) {
            case BOOL:
                return dataValue.getClass() == Vbool.class;
            case UINT8:
                return dataValue.getClass() == Vuint8.class;
            case UINT16:
                return dataValue.getClass() == Vuint16.class;
            case UINT32:
                return dataValue.getClass() == Vuint32.class;
            case UINT64:
                return dataValue.getClass() == Vuint64.class;
            case INT:
                return dataValue.getClass() == Vint.class;
            case FLOAT:
                return dataValue.getClass() == Vfloat.class;
            case STRING:
                return dataValue.getClass() == Vstring.class;
            default:
                return false;
        }
    }

    public boolean validate(DataValue dataValue, DataValue dataValue2) {
        return dataValue.lessThan(dataValue2);
    }

    public boolean validate(DataValue dataValue, DataValue dataValue2, DataValue dataValue3) {
        return dataValue2.validate(dataValue, dataValue3);
    }

    public Class<?> getJavaClass() throws RuntimeException {
        switch (this) {
            case BOOL:
                return Vbool.class;
            case UINT8:
                return Vuint8.class;
            case UINT16:
                return Vuint16.class;
            case UINT32:
                return Vuint32.class;
            case UINT64:
                return Vuint64.class;
            case INT:
                return Vint.class;
            case FLOAT:
                return Vfloat.class;
            case STRING:
                return Vstring.class;
            default:
                throw new RuntimeException("DataType invalid");
        }
    }

    public Object createObjectValue(String str) {
        switch (this) {
            case BOOL:
                return BooleanValueOf(str);
            case UINT8:
                return Integer.valueOf(str);
            case UINT16:
                return Integer.valueOf(str);
            case UINT32:
                return Integer.valueOf(str);
            case UINT64:
                return Long.valueOf(str);
            case INT:
                return Integer.valueOf(str);
            case FLOAT:
                if (str.equals("0")) {
                    str = "0.0f";
                }
                return Float.valueOf(str);
            case STRING:
                return str;
            default:
                Log.d(TAG, "createObjectValue failed, invalid type");
                return null;
        }
    }

    public DataValue createDefaultValue() {
        switch (this) {
            case BOOL:
                return new Vbool();
            case UINT8:
                return new Vuint8();
            case UINT16:
                return new Vuint16();
            case UINT32:
                return new Vuint32();
            case UINT64:
                return new Vuint64();
            case INT:
                return new Vint();
            case FLOAT:
                return new Vfloat();
            case STRING:
                return new Vstring();
            default:
                Log.d(TAG, "createDefaultValue failed, invalid type");
                return null;
        }
    }

    public DataValue createValue(Object obj) {
        switch (this) {
            case BOOL:
                return Vbool.valueOf(obj);
            case UINT8:
                return Vuint8.valueOf(obj);
            case UINT16:
                return Vuint16.valueOf(obj);
            case UINT32:
                return Vuint32.valueOf(obj);
            case UINT64:
                return Vuint64.valueOf(obj);
            case INT:
                return Vint.valueOf(obj);
            case FLOAT:
                return Vfloat.valueOf(obj);
            case STRING:
                return Vstring.valueOf(obj);
            default:
                Log.d(TAG, "createValue failed, invalid type");
                return null;
        }
    }

    private static Boolean BooleanValueOf(String str) {
        if (str == null) {
            return false;
        }
        String upperCase = str.toUpperCase();
        if (upperCase.equals("1") || upperCase.equals("YES") || upperCase.equals("TRUE")) {
            return true;
        }
        if (upperCase.equals("0") || upperCase.equals("NO") || upperCase.equals("FALSE")) {
            return false;
        }
        Log.e(TAG, "invalid value: " + str);
        return false;
    }
}
