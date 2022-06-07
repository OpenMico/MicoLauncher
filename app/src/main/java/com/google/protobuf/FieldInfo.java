package com.google.protobuf;

import com.google.protobuf.Internal;
import java.lang.reflect.Field;

/* loaded from: classes2.dex */
final class FieldInfo implements Comparable<FieldInfo> {
    private final Field a;
    private final FieldType b;
    private final Class<?> c;
    private final int d;
    private final Field e;
    private final int f;
    private final boolean g;
    private final boolean h;
    private final af i;
    private final Field j;
    private final Class<?> k;
    private final Object l;
    private final Internal.EnumVerifier m;

    private static boolean b(int i) {
        return i != 0 && (i & (i + (-1))) == 0;
    }

    public static FieldInfo a(Field field, int i, FieldType fieldType, boolean z) {
        a(i);
        Internal.a(field, "field");
        Internal.a(fieldType, "fieldType");
        if (fieldType != FieldType.MESSAGE_LIST && fieldType != FieldType.GROUP_LIST) {
            return new FieldInfo(field, i, fieldType, null, null, 0, false, z, null, null, null, null, null);
        }
        throw new IllegalStateException("Shouldn't be called for repeated message fields.");
    }

    public static FieldInfo a(Field field, int i, FieldType fieldType, Field field2) {
        a(i);
        Internal.a(field, "field");
        Internal.a(fieldType, "fieldType");
        if (fieldType != FieldType.MESSAGE_LIST && fieldType != FieldType.GROUP_LIST) {
            return new FieldInfo(field, i, fieldType, null, null, 0, false, false, null, null, null, null, field2);
        }
        throw new IllegalStateException("Shouldn't be called for repeated message fields.");
    }

    public static FieldInfo a(Field field, int i, FieldType fieldType, Internal.EnumVerifier enumVerifier) {
        a(i);
        Internal.a(field, "field");
        return new FieldInfo(field, i, fieldType, null, null, 0, false, false, null, null, null, enumVerifier, null);
    }

    public static FieldInfo a(Field field, int i, FieldType fieldType, Internal.EnumVerifier enumVerifier, Field field2) {
        a(i);
        Internal.a(field, "field");
        return new FieldInfo(field, i, fieldType, null, null, 0, false, false, null, null, null, enumVerifier, field2);
    }

    public static FieldInfo a(Field field, int i, FieldType fieldType, Field field2, int i2, boolean z, Internal.EnumVerifier enumVerifier) {
        a(i);
        Internal.a(field, "field");
        Internal.a(fieldType, "fieldType");
        Internal.a(field2, "presenceField");
        if (field2 == null || b(i2)) {
            return new FieldInfo(field, i, fieldType, null, field2, i2, false, z, null, null, null, enumVerifier, null);
        }
        throw new IllegalArgumentException("presenceMask must have exactly one bit set: " + i2);
    }

    public static FieldInfo a(int i, FieldType fieldType, af afVar, Class<?> cls, boolean z, Internal.EnumVerifier enumVerifier) {
        a(i);
        Internal.a(fieldType, "fieldType");
        Internal.a(afVar, "oneof");
        Internal.a(cls, "oneofStoredType");
        if (fieldType.isScalar()) {
            return new FieldInfo(null, i, fieldType, null, null, 0, false, z, afVar, cls, null, enumVerifier, null);
        }
        throw new IllegalArgumentException("Oneof is only supported for scalar fields. Field " + i + " is of type " + fieldType);
    }

    private static void a(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("fieldNumber must be positive: " + i);
        }
    }

    public static FieldInfo b(Field field, int i, FieldType fieldType, Field field2, int i2, boolean z, Internal.EnumVerifier enumVerifier) {
        a(i);
        Internal.a(field, "field");
        Internal.a(fieldType, "fieldType");
        Internal.a(field2, "presenceField");
        if (field2 == null || b(i2)) {
            return new FieldInfo(field, i, fieldType, null, field2, i2, true, z, null, null, null, enumVerifier, null);
        }
        throw new IllegalArgumentException("presenceMask must have exactly one bit set: " + i2);
    }

    public static FieldInfo a(Field field, int i, Object obj, Internal.EnumVerifier enumVerifier) {
        Internal.a(obj, "mapDefaultEntry");
        a(i);
        Internal.a(field, "field");
        return new FieldInfo(field, i, FieldType.MAP, null, null, 0, false, true, null, null, obj, enumVerifier, null);
    }

    private FieldInfo(Field field, int i, FieldType fieldType, Class<?> cls, Field field2, int i2, boolean z, boolean z2, af afVar, Class<?> cls2, Object obj, Internal.EnumVerifier enumVerifier, Field field3) {
        this.a = field;
        this.b = fieldType;
        this.c = cls;
        this.d = i;
        this.e = field2;
        this.f = i2;
        this.g = z;
        this.h = z2;
        this.i = afVar;
        this.k = cls2;
        this.l = obj;
        this.m = enumVerifier;
        this.j = field3;
    }

    public int a() {
        return this.d;
    }

    public Field b() {
        return this.a;
    }

    public FieldType c() {
        return this.b;
    }

    public af d() {
        return this.i;
    }

    public Internal.EnumVerifier e() {
        return this.m;
    }

    /* renamed from: a */
    public int compareTo(FieldInfo fieldInfo) {
        return this.d - fieldInfo.d;
    }

    public Field f() {
        return this.e;
    }

    public Object g() {
        return this.l;
    }

    public int h() {
        return this.f;
    }

    public boolean i() {
        return this.g;
    }

    public boolean j() {
        return this.h;
    }

    public Field k() {
        return this.j;
    }

    public Class<?> l() {
        switch (this.b) {
            case MESSAGE:
            case GROUP:
                Field field = this.a;
                return field != null ? field.getType() : this.k;
            case MESSAGE_LIST:
            case GROUP_LIST:
                return this.c;
            default:
                return null;
        }
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private Field a;
        private FieldType b;
        private int c;
        private Field d;
        private int e;
        private boolean f;
        private boolean g;
        private af h;
        private Class<?> i;
        private Object j;
        private Internal.EnumVerifier k;
        private Field l;

        private Builder() {
        }

        public Builder withField(Field field) {
            if (this.h == null) {
                this.a = field;
                return this;
            }
            throw new IllegalStateException("Cannot set field when building a oneof.");
        }

        public Builder withType(FieldType fieldType) {
            this.b = fieldType;
            return this;
        }

        public Builder withFieldNumber(int i) {
            this.c = i;
            return this;
        }

        public Builder withPresence(Field field, int i) {
            this.d = (Field) Internal.a(field, "presenceField");
            this.e = i;
            return this;
        }

        public Builder withOneof(af afVar, Class<?> cls) {
            if (this.a == null && this.d == null) {
                this.h = afVar;
                this.i = cls;
                return this;
            }
            throw new IllegalStateException("Cannot set oneof when field or presenceField have been provided");
        }

        public Builder withRequired(boolean z) {
            this.f = z;
            return this;
        }

        public Builder withMapDefaultEntry(Object obj) {
            this.j = obj;
            return this;
        }

        public Builder withEnforceUtf8(boolean z) {
            this.g = z;
            return this;
        }

        public Builder withEnumVerifier(Internal.EnumVerifier enumVerifier) {
            this.k = enumVerifier;
            return this;
        }

        public Builder withCachedSizeField(Field field) {
            this.l = field;
            return this;
        }

        public FieldInfo build() {
            af afVar = this.h;
            if (afVar != null) {
                return FieldInfo.a(this.c, this.b, afVar, this.i, this.g, this.k);
            }
            Object obj = this.j;
            if (obj != null) {
                return FieldInfo.a(this.a, this.c, obj, this.k);
            }
            Field field = this.d;
            if (field == null) {
                Internal.EnumVerifier enumVerifier = this.k;
                if (enumVerifier != null) {
                    Field field2 = this.l;
                    if (field2 == null) {
                        return FieldInfo.a(this.a, this.c, this.b, enumVerifier);
                    }
                    return FieldInfo.a(this.a, this.c, this.b, enumVerifier, field2);
                }
                Field field3 = this.l;
                if (field3 == null) {
                    return FieldInfo.a(this.a, this.c, this.b, this.g);
                }
                return FieldInfo.a(this.a, this.c, this.b, field3);
            } else if (this.f) {
                return FieldInfo.b(this.a, this.c, this.b, field, this.e, this.g, this.k);
            } else {
                return FieldInfo.a(this.a, this.c, this.b, field, this.e, this.g, this.k);
            }
        }
    }
}
