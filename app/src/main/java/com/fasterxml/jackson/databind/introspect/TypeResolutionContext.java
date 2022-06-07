package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public interface TypeResolutionContext {
    JavaType resolveType(Type type);

    /* loaded from: classes.dex */
    public static class Basic implements TypeResolutionContext {
        private final TypeFactory a;
        private final TypeBindings b;

        public Basic(TypeFactory typeFactory, TypeBindings typeBindings) {
            this.a = typeFactory;
            this.b = typeBindings;
        }

        @Override // com.fasterxml.jackson.databind.introspect.TypeResolutionContext
        public JavaType resolveType(Type type) {
            return this.a.constructType(type, this.b);
        }
    }
}
