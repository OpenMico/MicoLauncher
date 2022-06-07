package com.squareup.javapoet.x2c;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

/* loaded from: classes2.dex */
public final class TypeVariableName extends TypeName {
    public final List<TypeName> bounds;
    public final String name;

    private TypeVariableName(String str, List<TypeName> list) {
        this(str, list, new ArrayList());
    }

    private TypeVariableName(String str, List<TypeName> list, List<AnnotationSpec> list2) {
        super(list2);
        this.name = (String) c.a(str, "name == null", new Object[0]);
        this.bounds = list;
        Iterator<TypeName> it = this.bounds.iterator();
        while (it.hasNext()) {
            TypeName next = it.next();
            c.a(!next.isPrimitive() && next != VOID, "invalid bound: %s", next);
        }
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public TypeVariableName annotated(List<AnnotationSpec> list) {
        return new TypeVariableName(this.name, this.bounds, list);
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public TypeName withoutAnnotations() {
        return new TypeVariableName(this.name, this.bounds);
    }

    public TypeVariableName withBounds(Type... typeArr) {
        return withBounds(TypeName.a(typeArr));
    }

    public TypeVariableName withBounds(TypeName... typeNameArr) {
        return withBounds(Arrays.asList(typeNameArr));
    }

    public TypeVariableName withBounds(List<? extends TypeName> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.bounds);
        arrayList.addAll(list);
        return new TypeVariableName(this.name, arrayList, this.annotations);
    }

    private static TypeVariableName a(String str, List<TypeName> list) {
        ArrayList arrayList = new ArrayList(list);
        arrayList.remove(OBJECT);
        return new TypeVariableName(str, Collections.unmodifiableList(arrayList));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.squareup.javapoet.x2c.TypeName
    public a a(a aVar) throws IOException {
        b(aVar);
        return aVar.c(this.name);
    }

    public static TypeVariableName get(String str) {
        return a(str, Collections.emptyList());
    }

    public static TypeVariableName get(String str, TypeName... typeNameArr) {
        return a(str, Arrays.asList(typeNameArr));
    }

    public static TypeVariableName get(String str, Type... typeArr) {
        return a(str, TypeName.a(typeArr));
    }

    public static TypeVariableName get(TypeVariable typeVariable) {
        return get(typeVariable.asElement());
    }

    public static TypeVariableName get(TypeParameterElement typeParameterElement) {
        String obj = typeParameterElement.getSimpleName().toString();
        List<TypeMirror> bounds = typeParameterElement.getBounds();
        ArrayList arrayList = new ArrayList();
        for (TypeMirror typeMirror : bounds) {
            arrayList.add(TypeName.get(typeMirror));
        }
        return a(obj, arrayList);
    }

    public static TypeVariableName get(java.lang.reflect.TypeVariable<?> typeVariable) {
        return a(typeVariable, (Map<Type, TypeVariableName>) new LinkedHashMap());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TypeVariableName a(java.lang.reflect.TypeVariable<?> typeVariable, Map<Type, TypeVariableName> map) {
        TypeVariableName typeVariableName = map.get(typeVariable);
        if (typeVariableName != null) {
            return typeVariableName;
        }
        ArrayList arrayList = new ArrayList();
        TypeVariableName typeVariableName2 = new TypeVariableName(typeVariable.getName(), Collections.unmodifiableList(arrayList));
        map.put(typeVariable, typeVariableName2);
        for (Type type : typeVariable.getBounds()) {
            arrayList.add(TypeName.a(type, map));
        }
        arrayList.remove(OBJECT);
        return typeVariableName2;
    }
}
