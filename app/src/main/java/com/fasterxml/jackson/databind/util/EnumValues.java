package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes.dex */
public final class EnumValues implements Serializable {
    private static final long serialVersionUID = 1;
    private final Class<Enum<?>> _enumClass;
    private final SerializableString[] _textual;
    private final Enum<?>[] _values;
    private transient EnumMap<?, SerializableString> a;

    private EnumValues(Class<Enum<?>> cls, SerializableString[] serializableStringArr) {
        this._enumClass = cls;
        this._values = cls.getEnumConstants();
        this._textual = serializableStringArr;
    }

    public static EnumValues construct(SerializationConfig serializationConfig, Class<Enum<?>> cls) {
        if (serializationConfig.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
            return constructFromToString(serializationConfig, cls);
        }
        return constructFromName(serializationConfig, cls);
    }

    public static EnumValues constructFromName(MapperConfig<?> mapperConfig, Class<Enum<?>> cls) {
        Class<? extends Enum<?>> findEnumType = ClassUtil.findEnumType(cls);
        Enum<?>[] enumArr = (Enum[]) findEnumType.getEnumConstants();
        if (enumArr != null) {
            String[] findEnumValues = mapperConfig.getAnnotationIntrospector().findEnumValues(findEnumType, enumArr, new String[enumArr.length]);
            SerializableString[] serializableStringArr = new SerializableString[enumArr.length];
            int length = enumArr.length;
            for (int i = 0; i < length; i++) {
                Enum<?> r5 = enumArr[i];
                String str = findEnumValues[i];
                if (str == null) {
                    str = r5.name();
                }
                serializableStringArr[r5.ordinal()] = mapperConfig.compileString(str);
            }
            return new EnumValues(cls, serializableStringArr);
        }
        throw new IllegalArgumentException("Cannot determine enum constants for Class " + cls.getName());
    }

    public static EnumValues constructFromToString(MapperConfig<?> mapperConfig, Class<Enum<?>> cls) {
        Enum[] enumArr = (Enum[]) ClassUtil.findEnumType(cls).getEnumConstants();
        if (enumArr != null) {
            SerializableString[] serializableStringArr = new SerializableString[enumArr.length];
            for (Enum r4 : enumArr) {
                serializableStringArr[r4.ordinal()] = mapperConfig.compileString(r4.toString());
            }
            return new EnumValues(cls, serializableStringArr);
        }
        throw new IllegalArgumentException("Cannot determine enum constants for Class " + cls.getName());
    }

    public SerializableString serializedValueFor(Enum<?> r2) {
        return this._textual[r2.ordinal()];
    }

    public Collection<SerializableString> values() {
        return Arrays.asList(this._textual);
    }

    public List<Enum<?>> enums() {
        return Arrays.asList(this._values);
    }

    public EnumMap<?, SerializableString> internalMap() {
        EnumMap<?, SerializableString> enumMap = this.a;
        if (enumMap != null) {
            return enumMap;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Enum<?>[] enumArr = this._values;
        for (Enum<?> r4 : enumArr) {
            linkedHashMap.put(r4, this._textual[r4.ordinal()]);
        }
        return new EnumMap<>(linkedHashMap);
    }

    public Class<Enum<?>> getEnumClass() {
        return this._enumClass;
    }
}
