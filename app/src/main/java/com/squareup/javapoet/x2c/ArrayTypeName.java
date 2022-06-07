package com.squareup.javapoet.x2c;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ArrayType;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class ArrayTypeName extends TypeName {
    public final TypeName componentType;

    private ArrayTypeName(TypeName typeName) {
        this(typeName, new ArrayList());
    }

    private ArrayTypeName(TypeName typeName, List<AnnotationSpec> list) {
        super(list);
        this.componentType = (TypeName) c.a(typeName, "rawType == null", new Object[0]);
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public ArrayTypeName annotated(List<AnnotationSpec> list) {
        return new ArrayTypeName(this.componentType, concatAnnotations(list));
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public TypeName withoutAnnotations() {
        return new ArrayTypeName(this.componentType);
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public a a(a aVar) throws IOException {
        return a(aVar, false);
    }

    public a a(a aVar, boolean z) throws IOException {
        c(aVar);
        return b(aVar, z);
    }

    private a c(a aVar) throws IOException {
        if (TypeName.a(this.componentType) != null) {
            return TypeName.a(this.componentType).c(aVar);
        }
        return this.componentType.a(aVar);
    }

    private a b(a aVar, boolean z) throws IOException {
        if (isAnnotated()) {
            aVar.b(StringUtils.SPACE);
            b(aVar);
        }
        if (TypeName.a(this.componentType) == null) {
            return aVar.b(z ? "..." : HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        }
        aVar.b(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        return TypeName.a(this.componentType).b(aVar, z);
    }

    public static ArrayTypeName of(TypeName typeName) {
        return new ArrayTypeName(typeName);
    }

    public static ArrayTypeName of(Type type) {
        return of(TypeName.get(type));
    }

    public static ArrayTypeName get(ArrayType arrayType) {
        return a(arrayType, new LinkedHashMap());
    }

    static ArrayTypeName a(ArrayType arrayType, Map<TypeParameterElement, TypeVariableName> map) {
        return new ArrayTypeName(a(arrayType.getComponentType(), map));
    }

    public static ArrayTypeName get(GenericArrayType genericArrayType) {
        return a(genericArrayType, (Map<Type, TypeVariableName>) new LinkedHashMap());
    }

    public static ArrayTypeName a(GenericArrayType genericArrayType, Map<Type, TypeVariableName> map) {
        return of(a(genericArrayType.getGenericComponentType(), map));
    }
}
