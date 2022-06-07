package com.squareup.javapoet.x2c;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;

/* loaded from: classes2.dex */
public final class WildcardTypeName extends TypeName {
    public final List<TypeName> lowerBounds;
    public final List<TypeName> upperBounds;

    private WildcardTypeName(List<TypeName> list, List<TypeName> list2) {
        this(list, list2, new ArrayList());
    }

    private WildcardTypeName(List<TypeName> list, List<TypeName> list2, List<AnnotationSpec> list3) {
        super(list3);
        this.upperBounds = c.a(list);
        this.lowerBounds = c.a(list2);
        c.a(this.upperBounds.size() == 1, "unexpected extends bounds: %s", list);
        Iterator<TypeName> it = this.upperBounds.iterator();
        while (it.hasNext()) {
            TypeName next = it.next();
            c.a(!next.isPrimitive() && next != VOID, "invalid upper bound: %s", next);
        }
        Iterator<TypeName> it2 = this.lowerBounds.iterator();
        while (it2.hasNext()) {
            TypeName next2 = it2.next();
            c.a(!next2.isPrimitive() && next2 != VOID, "invalid lower bound: %s", next2);
        }
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public WildcardTypeName annotated(List<AnnotationSpec> list) {
        return new WildcardTypeName(this.upperBounds, this.lowerBounds, concatAnnotations(list));
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public TypeName withoutAnnotations() {
        return new WildcardTypeName(this.upperBounds, this.lowerBounds);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.squareup.javapoet.x2c.TypeName
    public a a(a aVar) throws IOException {
        return this.lowerBounds.size() == 1 ? aVar.a("? super $T", this.lowerBounds.get(0)) : this.upperBounds.get(0).equals(TypeName.OBJECT) ? aVar.b("?") : aVar.a("? extends $T", this.upperBounds.get(0));
    }

    public static WildcardTypeName subtypeOf(TypeName typeName) {
        return new WildcardTypeName(Collections.singletonList(typeName), Collections.emptyList());
    }

    public static WildcardTypeName subtypeOf(Type type) {
        return subtypeOf(TypeName.get(type));
    }

    public static WildcardTypeName supertypeOf(TypeName typeName) {
        return new WildcardTypeName(Collections.singletonList(OBJECT), Collections.singletonList(typeName));
    }

    public static WildcardTypeName supertypeOf(Type type) {
        return supertypeOf(TypeName.get(type));
    }

    public static TypeName get(WildcardType wildcardType) {
        return a(wildcardType, new LinkedHashMap());
    }

    static TypeName a(WildcardType wildcardType, Map<TypeParameterElement, TypeVariableName> map) {
        TypeMirror extendsBound = wildcardType.getExtendsBound();
        if (extendsBound != null) {
            return subtypeOf(TypeName.a(extendsBound, map));
        }
        TypeMirror superBound = wildcardType.getSuperBound();
        if (superBound == null) {
            return subtypeOf(Object.class);
        }
        return supertypeOf(TypeName.a(superBound, map));
    }

    public static TypeName get(java.lang.reflect.WildcardType wildcardType) {
        return a(wildcardType, (Map<Type, TypeVariableName>) new LinkedHashMap());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TypeName a(java.lang.reflect.WildcardType wildcardType, Map<Type, TypeVariableName> map) {
        return new WildcardTypeName(a(wildcardType.getUpperBounds(), map), a(wildcardType.getLowerBounds(), map));
    }
}
