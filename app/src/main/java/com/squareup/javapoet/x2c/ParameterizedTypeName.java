package com.squareup.javapoet.x2c;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class ParameterizedTypeName extends TypeName {
    private final ParameterizedTypeName a;
    public final ClassName rawType;
    public final List<TypeName> typeArguments;

    ParameterizedTypeName(ParameterizedTypeName parameterizedTypeName, ClassName className, List<TypeName> list) {
        this(parameterizedTypeName, className, list, new ArrayList());
    }

    private ParameterizedTypeName(ParameterizedTypeName parameterizedTypeName, ClassName className, List<TypeName> list, List<AnnotationSpec> list2) {
        super(list2);
        this.rawType = ((ClassName) c.a(className, "rawType == null", new Object[0])).annotated(list2);
        this.a = parameterizedTypeName;
        this.typeArguments = c.a(list);
        c.a(!this.typeArguments.isEmpty() || parameterizedTypeName != null, "no type arguments: %s", className);
        Iterator<TypeName> it = this.typeArguments.iterator();
        while (it.hasNext()) {
            TypeName next = it.next();
            c.a(!next.isPrimitive() && next != VOID, "invalid type parameter: %s", next);
        }
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public ParameterizedTypeName annotated(List<AnnotationSpec> list) {
        return new ParameterizedTypeName(this.a, this.rawType, this.typeArguments, concatAnnotations(list));
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public TypeName withoutAnnotations() {
        return new ParameterizedTypeName(this.a, this.rawType.withoutAnnotations(), this.typeArguments, new ArrayList());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.squareup.javapoet.x2c.TypeName
    public a a(a aVar) throws IOException {
        ParameterizedTypeName parameterizedTypeName = this.a;
        if (parameterizedTypeName != null) {
            parameterizedTypeName.a(aVar);
            aVar.b(".");
            if (isAnnotated()) {
                aVar.b(StringUtils.SPACE);
                b(aVar);
            }
            aVar.b(this.rawType.simpleName());
        } else {
            this.rawType.a(aVar);
        }
        if (!this.typeArguments.isEmpty()) {
            aVar.c("<");
            boolean z = true;
            for (TypeName typeName : this.typeArguments) {
                if (!z) {
                    aVar.c(", ");
                }
                typeName.a(aVar);
                z = false;
            }
            aVar.c(">");
        }
        return aVar;
    }

    public ParameterizedTypeName nestedClass(String str) {
        c.a(str, "name == null", new Object[0]);
        return new ParameterizedTypeName(this, this.rawType.nestedClass(str), new ArrayList(), new ArrayList());
    }

    public ParameterizedTypeName nestedClass(String str, List<TypeName> list) {
        c.a(str, "name == null", new Object[0]);
        return new ParameterizedTypeName(this, this.rawType.nestedClass(str), list, new ArrayList());
    }

    public static ParameterizedTypeName get(ClassName className, TypeName... typeNameArr) {
        return new ParameterizedTypeName(null, className, Arrays.asList(typeNameArr));
    }

    public static ParameterizedTypeName get(Class<?> cls, Type... typeArr) {
        return new ParameterizedTypeName(null, ClassName.get(cls), a(typeArr));
    }

    public static ParameterizedTypeName get(ParameterizedType parameterizedType) {
        return a(parameterizedType, (Map<Type, TypeVariableName>) new LinkedHashMap());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ParameterizedTypeName a(ParameterizedType parameterizedType, Map<Type, TypeVariableName> map) {
        ClassName className = ClassName.get((Class<?>) parameterizedType.getRawType());
        ParameterizedType parameterizedType2 = (!(parameterizedType.getOwnerType() instanceof ParameterizedType) || Modifier.isStatic(((Class) parameterizedType.getRawType()).getModifiers())) ? null : (ParameterizedType) parameterizedType.getOwnerType();
        List<TypeName> a = TypeName.a(parameterizedType.getActualTypeArguments(), map);
        return parameterizedType2 != null ? a(parameterizedType2, map).nestedClass(className.simpleName(), a) : new ParameterizedTypeName(null, className, a);
    }
}
