package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotationCollector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class AnnotatedClass extends Annotated implements TypeResolutionContext {
    private static final Creators a = new Creators(null, Collections.emptyList(), Collections.emptyList());
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final TypeBindings _bindings;
    protected final Class<?> _class;
    protected final Annotations _classAnnotations;
    protected Creators _creators;
    protected List<AnnotatedField> _fields;
    protected AnnotatedMethodMap _memberMethods;
    protected final ClassIntrospector.MixInResolver _mixInResolver;
    protected transient Boolean _nonStaticInnerClass;
    protected final Class<?> _primaryMixIn;
    protected final List<JavaType> _superTypes;
    protected final JavaType _type;
    protected final TypeFactory _typeFactory;

    public AnnotatedClass(JavaType javaType, Class<?> cls, List<JavaType> list, Class<?> cls2, Annotations annotations, TypeBindings typeBindings, AnnotationIntrospector annotationIntrospector, ClassIntrospector.MixInResolver mixInResolver, TypeFactory typeFactory) {
        this._type = javaType;
        this._class = cls;
        this._superTypes = list;
        this._primaryMixIn = cls2;
        this._classAnnotations = annotations;
        this._bindings = typeBindings;
        this._annotationIntrospector = annotationIntrospector;
        this._mixInResolver = mixInResolver;
        this._typeFactory = typeFactory;
    }

    public AnnotatedClass(Class<?> cls) {
        this._type = null;
        this._class = cls;
        this._superTypes = Collections.emptyList();
        this._primaryMixIn = null;
        this._classAnnotations = AnnotationCollector.emptyAnnotations();
        this._bindings = TypeBindings.emptyBindings();
        this._annotationIntrospector = null;
        this._mixInResolver = null;
        this._typeFactory = null;
    }

    @Deprecated
    public static AnnotatedClass construct(JavaType javaType, MapperConfig<?> mapperConfig) {
        return construct(javaType, mapperConfig, mapperConfig);
    }

    @Deprecated
    public static AnnotatedClass construct(JavaType javaType, MapperConfig<?> mapperConfig, ClassIntrospector.MixInResolver mixInResolver) {
        return AnnotatedClassResolver.resolve(mapperConfig, javaType, mixInResolver);
    }

    @Deprecated
    public static AnnotatedClass constructWithoutSuperTypes(Class<?> cls, MapperConfig<?> mapperConfig) {
        return constructWithoutSuperTypes(cls, mapperConfig, mapperConfig);
    }

    @Deprecated
    public static AnnotatedClass constructWithoutSuperTypes(Class<?> cls, MapperConfig<?> mapperConfig, ClassIntrospector.MixInResolver mixInResolver) {
        return AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, cls, mixInResolver);
    }

    @Override // com.fasterxml.jackson.databind.introspect.TypeResolutionContext
    public JavaType resolveType(Type type) {
        return this._typeFactory.constructType(type, this._bindings);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Class<?> getAnnotated() {
        return this._class;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public int getModifiers() {
        return this._class.getModifiers();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public String getName() {
        return this._class.getName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        return (A) this._classAnnotations.get(cls);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public boolean hasAnnotation(Class<?> cls) {
        return this._classAnnotations.has(cls);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public boolean hasOneOf(Class<? extends Annotation>[] clsArr) {
        return this._classAnnotations.hasOneOf(clsArr);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Class<?> getRawType() {
        return this._class;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    @Deprecated
    public Iterable<Annotation> annotations() {
        Annotations annotations = this._classAnnotations;
        if (annotations instanceof AnnotationMap) {
            return ((AnnotationMap) annotations).annotations();
        }
        if (!(annotations instanceof AnnotationCollector.OneAnnotation) && !(annotations instanceof AnnotationCollector.TwoAnnotations)) {
            return Collections.emptyList();
        }
        throw new UnsupportedOperationException("please use getAnnotations/ hasAnnotation to check for Annotations");
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public JavaType getType() {
        return this._type;
    }

    public Annotations getAnnotations() {
        return this._classAnnotations;
    }

    public boolean hasAnnotations() {
        return this._classAnnotations.size() > 0;
    }

    public AnnotatedConstructor getDefaultConstructor() {
        return c().defaultConstructor;
    }

    public List<AnnotatedConstructor> getConstructors() {
        return c().constructors;
    }

    public List<AnnotatedMethod> getFactoryMethods() {
        return c().creatorMethods;
    }

    @Deprecated
    public List<AnnotatedMethod> getStaticMethods() {
        return getFactoryMethods();
    }

    public Iterable<AnnotatedMethod> memberMethods() {
        return b();
    }

    public int getMemberMethodCount() {
        return b().size();
    }

    public AnnotatedMethod findMethod(String str, Class<?>[] clsArr) {
        return b().find(str, clsArr);
    }

    public int getFieldCount() {
        return a().size();
    }

    public Iterable<AnnotatedField> fields() {
        return a();
    }

    public boolean isNonStaticInnerClass() {
        Boolean bool = this._nonStaticInnerClass;
        if (bool == null) {
            bool = Boolean.valueOf(ClassUtil.isNonStaticInnerClass(this._class));
            this._nonStaticInnerClass = bool;
        }
        return bool.booleanValue();
    }

    private final List<AnnotatedField> a() {
        List<AnnotatedField> list = this._fields;
        if (list == null) {
            JavaType javaType = this._type;
            if (javaType == null) {
                list = Collections.emptyList();
            } else {
                list = AnnotatedFieldCollector.collectFields(this._annotationIntrospector, this, this._mixInResolver, this._typeFactory, javaType);
            }
            this._fields = list;
        }
        return list;
    }

    private final AnnotatedMethodMap b() {
        AnnotatedMethodMap annotatedMethodMap = this._memberMethods;
        if (annotatedMethodMap == null) {
            JavaType javaType = this._type;
            if (javaType == null) {
                annotatedMethodMap = new AnnotatedMethodMap();
            } else {
                annotatedMethodMap = AnnotatedMethodCollector.collectMethods(this._annotationIntrospector, this, this._mixInResolver, this._typeFactory, javaType, this._superTypes, this._primaryMixIn);
            }
            this._memberMethods = annotatedMethodMap;
        }
        return annotatedMethodMap;
    }

    private final Creators c() {
        Creators creators = this._creators;
        if (creators == null) {
            JavaType javaType = this._type;
            if (javaType == null) {
                creators = a;
            } else {
                creators = a.a(this._annotationIntrospector, this, javaType, this._primaryMixIn);
            }
            this._creators = creators;
        }
        return creators;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public String toString() {
        return "[AnnotedClass " + this._class.getName() + "]";
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public int hashCode() {
        return this._class.getName().hashCode();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return ClassUtil.hasClass(obj, getClass()) && ((AnnotatedClass) obj)._class == this._class;
    }

    /* loaded from: classes.dex */
    public static final class Creators {
        public final List<AnnotatedConstructor> constructors;
        public final List<AnnotatedMethod> creatorMethods;
        public final AnnotatedConstructor defaultConstructor;

        public Creators(AnnotatedConstructor annotatedConstructor, List<AnnotatedConstructor> list, List<AnnotatedMethod> list2) {
            this.defaultConstructor = annotatedConstructor;
            this.constructors = list;
            this.creatorMethods = list2;
        }
    }
}
