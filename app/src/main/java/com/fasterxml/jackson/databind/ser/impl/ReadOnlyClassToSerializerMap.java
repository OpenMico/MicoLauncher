package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.util.TypeKey;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class ReadOnlyClassToSerializerMap {
    private final a[] a;
    private final int b;
    private final int c;

    private static final int a(int i) {
        int i2 = 8;
        while (i2 < (i <= 64 ? i + i : i + (i >> 2))) {
            i2 += i2;
        }
        return i2;
    }

    public ReadOnlyClassToSerializerMap(Map<TypeKey, JsonSerializer<Object>> map) {
        int a2 = a(map.size());
        this.b = a2;
        this.c = a2 - 1;
        a[] aVarArr = new a[a2];
        for (Map.Entry<TypeKey, JsonSerializer<Object>> entry : map.entrySet()) {
            TypeKey key = entry.getKey();
            int hashCode = key.hashCode() & this.c;
            aVarArr[hashCode] = new a(aVarArr[hashCode], key, entry.getValue());
        }
        this.a = aVarArr;
    }

    public static ReadOnlyClassToSerializerMap from(HashMap<TypeKey, JsonSerializer<Object>> hashMap) {
        return new ReadOnlyClassToSerializerMap(hashMap);
    }

    public int size() {
        return this.b;
    }

    public JsonSerializer<Object> typedValueSerializer(JavaType javaType) {
        a aVar = this.a[TypeKey.typedHash(javaType) & this.c];
        if (aVar == null) {
            return null;
        }
        if (aVar.a(javaType)) {
            return aVar.a;
        }
        do {
            aVar = aVar.b;
            if (aVar == null) {
                return null;
            }
        } while (!aVar.a(javaType));
        return aVar.a;
    }

    public JsonSerializer<Object> typedValueSerializer(Class<?> cls) {
        a aVar = this.a[TypeKey.typedHash(cls) & this.c];
        if (aVar == null) {
            return null;
        }
        if (aVar.a(cls)) {
            return aVar.a;
        }
        do {
            aVar = aVar.b;
            if (aVar == null) {
                return null;
            }
        } while (!aVar.a(cls));
        return aVar.a;
    }

    public JsonSerializer<Object> untypedValueSerializer(JavaType javaType) {
        a aVar = this.a[TypeKey.untypedHash(javaType) & this.c];
        if (aVar == null) {
            return null;
        }
        if (aVar.b(javaType)) {
            return aVar.a;
        }
        do {
            aVar = aVar.b;
            if (aVar == null) {
                return null;
            }
        } while (!aVar.b(javaType));
        return aVar.a;
    }

    public JsonSerializer<Object> untypedValueSerializer(Class<?> cls) {
        a aVar = this.a[TypeKey.untypedHash(cls) & this.c];
        if (aVar == null) {
            return null;
        }
        if (aVar.b(cls)) {
            return aVar.a;
        }
        do {
            aVar = aVar.b;
            if (aVar == null) {
                return null;
            }
        } while (!aVar.b(cls));
        return aVar.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        public final JsonSerializer<Object> a;
        public final a b;
        protected final Class<?> c;
        protected final JavaType d;
        protected final boolean e;

        public a(a aVar, TypeKey typeKey, JsonSerializer<Object> jsonSerializer) {
            this.b = aVar;
            this.a = jsonSerializer;
            this.e = typeKey.isTyped();
            this.c = typeKey.getRawType();
            this.d = typeKey.getType();
        }

        public boolean a(Class<?> cls) {
            return this.c == cls && this.e;
        }

        public boolean b(Class<?> cls) {
            return this.c == cls && !this.e;
        }

        public boolean a(JavaType javaType) {
            return this.e && javaType.equals(this.d);
        }

        public boolean b(JavaType javaType) {
            return !this.e && javaType.equals(this.d);
        }
    }
}
