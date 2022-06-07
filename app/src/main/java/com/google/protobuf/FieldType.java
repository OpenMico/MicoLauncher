package com.google.protobuf;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

/* loaded from: classes2.dex */
public enum FieldType {
    DOUBLE(0, a.SCALAR, JavaType.DOUBLE),
    FLOAT(1, a.SCALAR, JavaType.FLOAT),
    INT64(2, a.SCALAR, JavaType.LONG),
    UINT64(3, a.SCALAR, JavaType.LONG),
    INT32(4, a.SCALAR, JavaType.INT),
    FIXED64(5, a.SCALAR, JavaType.LONG),
    FIXED32(6, a.SCALAR, JavaType.INT),
    BOOL(7, a.SCALAR, JavaType.BOOLEAN),
    STRING(8, a.SCALAR, JavaType.STRING),
    MESSAGE(9, a.SCALAR, JavaType.MESSAGE),
    BYTES(10, a.SCALAR, JavaType.BYTE_STRING),
    UINT32(11, a.SCALAR, JavaType.INT),
    ENUM(12, a.SCALAR, JavaType.ENUM),
    SFIXED32(13, a.SCALAR, JavaType.INT),
    SFIXED64(14, a.SCALAR, JavaType.LONG),
    SINT32(15, a.SCALAR, JavaType.INT),
    SINT64(16, a.SCALAR, JavaType.LONG),
    GROUP(17, a.SCALAR, JavaType.MESSAGE),
    DOUBLE_LIST(18, a.VECTOR, JavaType.DOUBLE),
    FLOAT_LIST(19, a.VECTOR, JavaType.FLOAT),
    INT64_LIST(20, a.VECTOR, JavaType.LONG),
    UINT64_LIST(21, a.VECTOR, JavaType.LONG),
    INT32_LIST(22, a.VECTOR, JavaType.INT),
    FIXED64_LIST(23, a.VECTOR, JavaType.LONG),
    FIXED32_LIST(24, a.VECTOR, JavaType.INT),
    BOOL_LIST(25, a.VECTOR, JavaType.BOOLEAN),
    STRING_LIST(26, a.VECTOR, JavaType.STRING),
    MESSAGE_LIST(27, a.VECTOR, JavaType.MESSAGE),
    BYTES_LIST(28, a.VECTOR, JavaType.BYTE_STRING),
    UINT32_LIST(29, a.VECTOR, JavaType.INT),
    ENUM_LIST(30, a.VECTOR, JavaType.ENUM),
    SFIXED32_LIST(31, a.VECTOR, JavaType.INT),
    SFIXED64_LIST(32, a.VECTOR, JavaType.LONG),
    SINT32_LIST(33, a.VECTOR, JavaType.INT),
    SINT64_LIST(34, a.VECTOR, JavaType.LONG),
    DOUBLE_LIST_PACKED(35, a.PACKED_VECTOR, JavaType.DOUBLE),
    FLOAT_LIST_PACKED(36, a.PACKED_VECTOR, JavaType.FLOAT),
    INT64_LIST_PACKED(37, a.PACKED_VECTOR, JavaType.LONG),
    UINT64_LIST_PACKED(38, a.PACKED_VECTOR, JavaType.LONG),
    INT32_LIST_PACKED(39, a.PACKED_VECTOR, JavaType.INT),
    FIXED64_LIST_PACKED(40, a.PACKED_VECTOR, JavaType.LONG),
    FIXED32_LIST_PACKED(41, a.PACKED_VECTOR, JavaType.INT),
    BOOL_LIST_PACKED(42, a.PACKED_VECTOR, JavaType.BOOLEAN),
    UINT32_LIST_PACKED(43, a.PACKED_VECTOR, JavaType.INT),
    ENUM_LIST_PACKED(44, a.PACKED_VECTOR, JavaType.ENUM),
    SFIXED32_LIST_PACKED(45, a.PACKED_VECTOR, JavaType.INT),
    SFIXED64_LIST_PACKED(46, a.PACKED_VECTOR, JavaType.LONG),
    SINT32_LIST_PACKED(47, a.PACKED_VECTOR, JavaType.INT),
    SINT64_LIST_PACKED(48, a.PACKED_VECTOR, JavaType.LONG),
    GROUP_LIST(49, a.VECTOR, JavaType.MESSAGE),
    MAP(50, a.MAP, JavaType.VOID);
    
    private static final FieldType[] a;
    private static final Type[] b = new Type[0];
    private final a collection;
    private final Class<?> elementType;
    private final int id;
    private final JavaType javaType;
    private final boolean primitiveScalar;

    static {
        FieldType[] values = values();
        a = new FieldType[values.length];
        for (FieldType fieldType : values) {
            a[fieldType.id] = fieldType;
        }
    }

    FieldType(int i, a aVar, JavaType javaType) {
        this.id = i;
        this.collection = aVar;
        this.javaType = javaType;
        switch (aVar) {
            case MAP:
                this.elementType = javaType.getBoxedType();
                break;
            case VECTOR:
                this.elementType = javaType.getBoxedType();
                break;
            default:
                this.elementType = null;
                break;
        }
        boolean z = false;
        if (aVar == a.SCALAR) {
            switch (javaType) {
                case BYTE_STRING:
                case MESSAGE:
                case STRING:
                    break;
                default:
                    z = true;
                    break;
            }
        }
        this.primitiveScalar = z;
    }

    public int id() {
        return this.id;
    }

    public JavaType getJavaType() {
        return this.javaType;
    }

    public boolean isPacked() {
        return a.PACKED_VECTOR.equals(this.collection);
    }

    public boolean isPrimitiveScalar() {
        return this.primitiveScalar;
    }

    public boolean isScalar() {
        return this.collection == a.SCALAR;
    }

    public boolean isList() {
        return this.collection.a();
    }

    public boolean isMap() {
        return this.collection == a.MAP;
    }

    public boolean isValidForField(Field field) {
        if (a.VECTOR.equals(this.collection)) {
            return a(field);
        }
        return this.javaType.getType().isAssignableFrom(field.getType());
    }

    private boolean a(Field field) {
        Class<?> type = field.getType();
        if (!this.javaType.getType().isAssignableFrom(type)) {
            return false;
        }
        Type[] typeArr = b;
        if (field.getGenericType() instanceof ParameterizedType) {
            typeArr = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        }
        Type a2 = a(type, typeArr);
        if (!(a2 instanceof Class)) {
            return true;
        }
        return this.elementType.isAssignableFrom((Class) a2);
    }

    public static FieldType forId(int i) {
        if (i < 0) {
            return null;
        }
        FieldType[] fieldTypeArr = a;
        if (i >= fieldTypeArr.length) {
            return null;
        }
        return fieldTypeArr[i];
    }

    private static Type a(Class<?> cls) {
        Type[] genericInterfaces = cls.getGenericInterfaces();
        for (Type type : genericInterfaces) {
            if ((type instanceof ParameterizedType) && List.class.isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                return type;
            }
        }
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType) || !List.class.isAssignableFrom((Class) ((ParameterizedType) genericSuperclass).getRawType())) {
            return null;
        }
        return genericSuperclass;
    }

    private static Type a(Class<?> cls, Type[] typeArr) {
        boolean z;
        while (true) {
            int i = 0;
            if (cls != List.class) {
                Type a2 = a(cls);
                if (!(a2 instanceof ParameterizedType)) {
                    typeArr = b;
                    Class<?>[] interfaces = cls.getInterfaces();
                    int length = interfaces.length;
                    while (true) {
                        if (i >= length) {
                            cls = cls.getSuperclass();
                            break;
                        }
                        Class<?> cls2 = interfaces[i];
                        if (List.class.isAssignableFrom(cls2)) {
                            cls = cls2;
                            break;
                        }
                        i++;
                    }
                } else {
                    ParameterizedType parameterizedType = (ParameterizedType) a2;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    for (int i2 = 0; i2 < actualTypeArguments.length; i2++) {
                        Type type = actualTypeArguments[i2];
                        if (type instanceof TypeVariable) {
                            TypeVariable<Class<?>>[] typeParameters = cls.getTypeParameters();
                            if (typeArr.length == typeParameters.length) {
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= typeParameters.length) {
                                        z = false;
                                        break;
                                    } else if (type == typeParameters[i3]) {
                                        actualTypeArguments[i2] = typeArr[i3];
                                        z = true;
                                        break;
                                    } else {
                                        i3++;
                                    }
                                }
                                if (!z) {
                                    throw new RuntimeException("Unable to find replacement for " + type);
                                }
                            } else {
                                throw new RuntimeException("Type array mismatch");
                            }
                        }
                    }
                    cls = (Class) parameterizedType.getRawType();
                    typeArr = actualTypeArguments;
                }
            } else if (typeArr.length == 1) {
                return typeArr[0];
            } else {
                throw new RuntimeException("Unable to identify parameter type for List<T>");
            }
        }
    }

    /* loaded from: classes2.dex */
    enum a {
        SCALAR(false),
        VECTOR(true),
        PACKED_VECTOR(true),
        MAP(false);
        
        private final boolean isList;

        a(boolean z) {
            this.isList = z;
        }

        public boolean a() {
            return this.isList;
        }
    }
}
