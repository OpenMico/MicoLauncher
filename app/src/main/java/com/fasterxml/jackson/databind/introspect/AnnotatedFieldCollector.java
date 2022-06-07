package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class AnnotatedFieldCollector extends b {
    private final TypeFactory a;
    private final ClassIntrospector.MixInResolver b;

    AnnotatedFieldCollector(AnnotationIntrospector annotationIntrospector, TypeFactory typeFactory, ClassIntrospector.MixInResolver mixInResolver) {
        super(annotationIntrospector);
        this.a = typeFactory;
        this.b = annotationIntrospector == null ? null : mixInResolver;
    }

    public static List<AnnotatedField> collectFields(AnnotationIntrospector annotationIntrospector, TypeResolutionContext typeResolutionContext, ClassIntrospector.MixInResolver mixInResolver, TypeFactory typeFactory, JavaType javaType) {
        return new AnnotatedFieldCollector(annotationIntrospector, typeFactory, mixInResolver).a(typeResolutionContext, javaType);
    }

    List<AnnotatedField> a(TypeResolutionContext typeResolutionContext, JavaType javaType) {
        Map<String, a> a2 = a(typeResolutionContext, javaType, (Map<String, a>) null);
        if (a2 == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(a2.size());
        for (a aVar : a2.values()) {
            arrayList.add(aVar.a());
        }
        return arrayList;
    }

    private Map<String, a> a(TypeResolutionContext typeResolutionContext, JavaType javaType, Map<String, a> map) {
        Class<?> findMixInClassFor;
        JavaType superClass = javaType.getSuperClass();
        if (superClass == null) {
            return map;
        }
        Class<?> rawClass = javaType.getRawClass();
        Map<String, a> a2 = a(new TypeResolutionContext.Basic(this.a, superClass.getBindings()), superClass, map);
        Field[] declaredFields = ClassUtil.getDeclaredFields(rawClass);
        for (Field field : declaredFields) {
            if (a(field)) {
                if (a2 == null) {
                    a2 = new LinkedHashMap<>();
                }
                a aVar = new a(typeResolutionContext, field);
                if (this._intr != null) {
                    aVar.c = collectAnnotations(aVar.c, field.getDeclaredAnnotations());
                }
                a2.put(field.getName(), aVar);
            }
        }
        ClassIntrospector.MixInResolver mixInResolver = this.b;
        if (!(mixInResolver == null || (findMixInClassFor = mixInResolver.findMixInClassFor(rawClass)) == null)) {
            a(findMixInClassFor, rawClass, a2);
        }
        return a2;
    }

    private void a(Class<?> cls, Class<?> cls2, Map<String, a> map) {
        a aVar;
        for (Class<?> cls3 : ClassUtil.findSuperClasses(cls, cls2, true)) {
            Field[] declaredFields = ClassUtil.getDeclaredFields(cls3);
            for (Field field : declaredFields) {
                if (a(field) && (aVar = map.get(field.getName())) != null) {
                    aVar.c = collectAnnotations(aVar.c, field.getDeclaredAnnotations());
                }
            }
        }
    }

    private boolean a(Field field) {
        return !field.isSynthetic() && !Modifier.isStatic(field.getModifiers());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        public final TypeResolutionContext a;
        public final Field b;
        public AnnotationCollector c = AnnotationCollector.emptyCollector();

        public a(TypeResolutionContext typeResolutionContext, Field field) {
            this.a = typeResolutionContext;
            this.b = field;
        }

        public AnnotatedField a() {
            return new AnnotatedField(this.a, this.b, this.c.asAnnotationMap());
        }
    }
}
