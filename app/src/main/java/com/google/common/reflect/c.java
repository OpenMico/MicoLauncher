package com.google.common.reflect;

import com.google.common.collect.Sets;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Set;

/* compiled from: TypeVisitor.java */
/* loaded from: classes2.dex */
abstract class c {
    private final Set<Type> a = Sets.newHashSet();

    void a(Class<?> cls) {
    }

    void a(GenericArrayType genericArrayType) {
    }

    void a(ParameterizedType parameterizedType) {
    }

    void a(TypeVariable<?> typeVariable) {
    }

    void a(WildcardType wildcardType) {
    }

    public final void a(Type... typeArr) {
        for (Type type : typeArr) {
            if (type != null && this.a.add(type)) {
                try {
                    if (type instanceof TypeVariable) {
                        a((TypeVariable) type);
                    } else if (type instanceof WildcardType) {
                        a((WildcardType) type);
                    } else if (type instanceof ParameterizedType) {
                        a((ParameterizedType) type);
                    } else if (type instanceof Class) {
                        a((Class) type);
                    } else if (type instanceof GenericArrayType) {
                        a((GenericArrayType) type);
                    } else {
                        throw new AssertionError("Unknown type: " + type);
                    }
                } catch (Throwable th) {
                    this.a.remove(type);
                    throw th;
                }
            }
        }
    }
}
