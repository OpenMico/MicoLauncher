package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class AnnotatedClassResolver {
    private static final Annotations a = AnnotationCollector.emptyAnnotations();
    private final MapperConfig<?> b;
    private final AnnotationIntrospector c;
    private final ClassIntrospector.MixInResolver d;
    private final TypeBindings e;
    private final JavaType f;
    private final Class<?> g;
    private final Class<?> h;

    AnnotatedClassResolver(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        this.b = mapperConfig;
        this.f = javaType;
        this.g = javaType.getRawClass();
        this.d = mixInResolver;
        this.e = javaType.getBindings();
        this.c = mapperConfig.isAnnotationProcessingEnabled() ? mapperConfig.getAnnotationIntrospector() : null;
        this.h = this.b.findMixInClassFor(this.g);
    }

    AnnotatedClassResolver(MapperConfig<?> mapperConfig, Class<?> cls, ClassIntrospector.MixInResolver mixInResolver) {
        this.b = mapperConfig;
        AnnotationIntrospector annotationIntrospector = null;
        this.f = null;
        this.g = cls;
        this.d = mixInResolver;
        this.e = TypeBindings.emptyBindings();
        if (mapperConfig == null) {
            this.c = null;
            this.h = null;
            return;
        }
        this.c = mapperConfig.isAnnotationProcessingEnabled() ? mapperConfig.getAnnotationIntrospector() : annotationIntrospector;
        this.h = this.b.findMixInClassFor(this.g);
    }

    public static AnnotatedClass resolve(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        if (!javaType.isArrayType() || !b(mapperConfig, javaType.getRawClass())) {
            return new AnnotatedClassResolver(mapperConfig, javaType, mixInResolver).a();
        }
        return a(mapperConfig, javaType.getRawClass());
    }

    public static AnnotatedClass resolveWithoutSuperTypes(MapperConfig<?> mapperConfig, Class<?> cls) {
        return resolveWithoutSuperTypes(mapperConfig, cls, mapperConfig);
    }

    public static AnnotatedClass resolveWithoutSuperTypes(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        if (!javaType.isArrayType() || !b(mapperConfig, javaType.getRawClass())) {
            return new AnnotatedClassResolver(mapperConfig, javaType, mixInResolver).b();
        }
        return a(mapperConfig, javaType.getRawClass());
    }

    public static AnnotatedClass resolveWithoutSuperTypes(MapperConfig<?> mapperConfig, Class<?> cls, ClassIntrospector.MixInResolver mixInResolver) {
        if (!cls.isArray() || !b(mapperConfig, cls)) {
            return new AnnotatedClassResolver(mapperConfig, cls, mixInResolver).b();
        }
        return a(mapperConfig, cls);
    }

    private static boolean b(MapperConfig<?> mapperConfig, Class<?> cls) {
        return mapperConfig == null || mapperConfig.findMixInClassFor(cls) == null;
    }

    public static AnnotatedClass a(Class<?> cls) {
        return new AnnotatedClass(cls);
    }

    static AnnotatedClass a(MapperConfig<?> mapperConfig, Class<?> cls) {
        return new AnnotatedClass(cls);
    }

    AnnotatedClass a() {
        List<JavaType> findSuperTypes = ClassUtil.findSuperTypes(this.f, (Class<?>) null, false);
        return new AnnotatedClass(this.f, this.g, findSuperTypes, this.h, a(findSuperTypes), this.e, this.c, this.d, this.b.getTypeFactory());
    }

    AnnotatedClass b() {
        List<JavaType> emptyList = Collections.emptyList();
        Class<?> cls = this.g;
        Class<?> cls2 = this.h;
        Annotations a2 = a(emptyList);
        TypeBindings typeBindings = this.e;
        AnnotationIntrospector annotationIntrospector = this.c;
        MapperConfig<?> mapperConfig = this.b;
        return new AnnotatedClass(null, cls, emptyList, cls2, a2, typeBindings, annotationIntrospector, mapperConfig, mapperConfig.getTypeFactory());
    }

    private Annotations a(List<JavaType> list) {
        if (this.c == null) {
            return a;
        }
        AnnotationCollector emptyCollector = AnnotationCollector.emptyCollector();
        Class<?> cls = this.h;
        if (cls != null) {
            emptyCollector = a(emptyCollector, this.g, cls);
        }
        AnnotationCollector a2 = a(emptyCollector, ClassUtil.findClassAnnotations(this.g));
        for (JavaType javaType : list) {
            if (this.d != null) {
                Class<?> rawClass = javaType.getRawClass();
                a2 = a(a2, rawClass, this.d.findMixInClassFor(rawClass));
            }
            a2 = a(a2, ClassUtil.findClassAnnotations(javaType.getRawClass()));
        }
        ClassIntrospector.MixInResolver mixInResolver = this.d;
        if (mixInResolver != null) {
            a2 = a(a2, Object.class, mixInResolver.findMixInClassFor(Object.class));
        }
        return a2.asAnnotations();
    }

    private AnnotationCollector a(AnnotationCollector annotationCollector, Class<?> cls, Class<?> cls2) {
        if (cls2 != null) {
            annotationCollector = a(annotationCollector, ClassUtil.findClassAnnotations(cls2));
            for (Class<?> cls3 : ClassUtil.findSuperClasses(cls2, cls, false)) {
                annotationCollector = a(annotationCollector, ClassUtil.findClassAnnotations(cls3));
            }
        }
        return annotationCollector;
    }

    private AnnotationCollector a(AnnotationCollector annotationCollector, Annotation[] annotationArr) {
        if (annotationArr != null) {
            for (Annotation annotation : annotationArr) {
                if (!annotationCollector.isPresent(annotation)) {
                    annotationCollector = annotationCollector.addOrOverride(annotation);
                    if (this.c.isAnnotationBundle(annotation)) {
                        annotationCollector = a(annotationCollector, annotation);
                    }
                }
            }
        }
        return annotationCollector;
    }

    private AnnotationCollector a(AnnotationCollector annotationCollector, Annotation annotation) {
        Annotation[] findClassAnnotations = ClassUtil.findClassAnnotations(annotation.annotationType());
        for (Annotation annotation2 : findClassAnnotations) {
            if (!(annotation2 instanceof Target) && !(annotation2 instanceof Retention) && !annotationCollector.isPresent(annotation2)) {
                annotationCollector = annotationCollector.addOrOverride(annotation2);
                if (this.c.isAnnotationBundle(annotation2)) {
                    annotationCollector = a(annotationCollector, annotation2);
                }
            }
        }
        return annotationCollector;
    }
}
