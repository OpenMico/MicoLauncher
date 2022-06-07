package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.LRUMap;
import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class TypeFactory implements Serializable {
    private static final long serialVersionUID = 1;
    protected final ClassLoader _classLoader;
    protected final TypeModifier[] _modifiers;
    protected final TypeParser _parser;
    protected final LRUMap<Object, JavaType> _typeCache;
    private static final JavaType[] a = new JavaType[0];
    protected static final TypeFactory instance = new TypeFactory();
    protected static final TypeBindings EMPTY_BINDINGS = TypeBindings.emptyBindings();
    private static final Class<?> b = String.class;
    private static final Class<?> c = Object.class;
    private static final Class<?> d = Comparable.class;
    private static final Class<?> e = Class.class;
    private static final Class<?> f = Enum.class;
    private static final Class<?> g = Boolean.TYPE;
    private static final Class<?> h = Integer.TYPE;
    private static final Class<?> i = Long.TYPE;
    protected static final SimpleType CORE_TYPE_BOOL = new SimpleType(g);
    protected static final SimpleType CORE_TYPE_INT = new SimpleType(h);
    protected static final SimpleType CORE_TYPE_LONG = new SimpleType(i);
    protected static final SimpleType CORE_TYPE_STRING = new SimpleType(b);
    protected static final SimpleType CORE_TYPE_OBJECT = new SimpleType(c);
    protected static final SimpleType CORE_TYPE_COMPARABLE = new SimpleType(d);
    protected static final SimpleType CORE_TYPE_ENUM = new SimpleType(f);
    protected static final SimpleType CORE_TYPE_CLASS = new SimpleType(e);

    private TypeFactory() {
        this(null);
    }

    protected TypeFactory(LRUMap<Object, JavaType> lRUMap) {
        this._typeCache = lRUMap == null ? new LRUMap<>(16, 200) : lRUMap;
        this._parser = new TypeParser(this);
        this._modifiers = null;
        this._classLoader = null;
    }

    protected TypeFactory(LRUMap<Object, JavaType> lRUMap, TypeParser typeParser, TypeModifier[] typeModifierArr, ClassLoader classLoader) {
        this._typeCache = lRUMap == null ? new LRUMap<>(16, 200) : lRUMap;
        this._parser = typeParser.withFactory(this);
        this._modifiers = typeModifierArr;
        this._classLoader = classLoader;
    }

    public TypeFactory withModifier(TypeModifier typeModifier) {
        LRUMap<Object, JavaType> lRUMap = this._typeCache;
        TypeModifier[] typeModifierArr = null;
        if (typeModifier == null) {
            lRUMap = null;
        } else {
            TypeModifier[] typeModifierArr2 = this._modifiers;
            typeModifierArr = typeModifierArr2 == null ? new TypeModifier[]{typeModifier} : (TypeModifier[]) ArrayBuilders.insertInListNoDup(typeModifierArr2, typeModifier);
        }
        return new TypeFactory(lRUMap, this._parser, typeModifierArr, this._classLoader);
    }

    public TypeFactory withClassLoader(ClassLoader classLoader) {
        return new TypeFactory(this._typeCache, this._parser, this._modifiers, classLoader);
    }

    public TypeFactory withCache(LRUMap<Object, JavaType> lRUMap) {
        return new TypeFactory(lRUMap, this._parser, this._modifiers, this._classLoader);
    }

    public static TypeFactory defaultInstance() {
        return instance;
    }

    public void clearCache() {
        this._typeCache.clear();
    }

    public ClassLoader getClassLoader() {
        return this._classLoader;
    }

    public static JavaType unknownType() {
        return defaultInstance()._unknownType();
    }

    public static Class<?> rawClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        return defaultInstance().constructType(type).getRawClass();
    }

    public Class<?> findClass(String str) throws ClassNotFoundException {
        Class<?> _findPrimitive;
        if (str.indexOf(46) < 0 && (_findPrimitive = _findPrimitive(str)) != null) {
            return _findPrimitive;
        }
        Throwable th = null;
        ClassLoader classLoader = getClassLoader();
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        if (classLoader != null) {
            try {
                return classForName(str, true, classLoader);
            } catch (Exception e2) {
                th = ClassUtil.getRootCause(e2);
            }
        }
        try {
            return classForName(str);
        } catch (Exception e3) {
            if (th == null) {
                th = ClassUtil.getRootCause(e3);
            }
            ClassUtil.throwIfRTE(th);
            throw new ClassNotFoundException(th.getMessage(), th);
        }
    }

    protected Class<?> classForName(String str, boolean z, ClassLoader classLoader) throws ClassNotFoundException {
        return Class.forName(str, true, classLoader);
    }

    protected Class<?> classForName(String str) throws ClassNotFoundException {
        return Class.forName(str);
    }

    protected Class<?> _findPrimitive(String str) {
        if ("int".equals(str)) {
            return Integer.TYPE;
        }
        if ("long".equals(str)) {
            return Long.TYPE;
        }
        if ("float".equals(str)) {
            return Float.TYPE;
        }
        if ("double".equals(str)) {
            return Double.TYPE;
        }
        if ("boolean".equals(str)) {
            return Boolean.TYPE;
        }
        if ("byte".equals(str)) {
            return Byte.TYPE;
        }
        if ("char".equals(str)) {
            return Character.TYPE;
        }
        if ("short".equals(str)) {
            return Short.TYPE;
        }
        if ("void".equals(str)) {
            return Void.TYPE;
        }
        return null;
    }

    public JavaType constructSpecializedType(JavaType javaType, Class<?> cls) {
        JavaType javaType2;
        Class<?> rawClass = javaType.getRawClass();
        if (rawClass == cls) {
            return javaType;
        }
        if (rawClass == Object.class) {
            javaType2 = _fromClass(null, cls, EMPTY_BINDINGS);
        } else if (!rawClass.isAssignableFrom(cls)) {
            throw new IllegalArgumentException(String.format("Class %s not subtype of %s", cls.getName(), javaType));
        } else if (javaType.getBindings().isEmpty()) {
            javaType2 = _fromClass(null, cls, EMPTY_BINDINGS);
        } else {
            if (javaType.isContainerType()) {
                if (javaType.isMapLikeType()) {
                    if (cls == HashMap.class || cls == LinkedHashMap.class || cls == EnumMap.class || cls == TreeMap.class) {
                        javaType2 = _fromClass(null, cls, TypeBindings.create(cls, javaType.getKeyType(), javaType.getContentType()));
                    }
                } else if (javaType.isCollectionLikeType()) {
                    if (cls == ArrayList.class || cls == LinkedList.class || cls == HashSet.class || cls == TreeSet.class) {
                        javaType2 = _fromClass(null, cls, TypeBindings.create(cls, javaType.getContentType()));
                    } else if (rawClass == EnumSet.class) {
                        return javaType;
                    }
                }
            }
            int length = cls.getTypeParameters().length;
            if (length == 0) {
                javaType2 = _fromClass(null, cls, EMPTY_BINDINGS);
            } else {
                javaType2 = _fromClass(null, cls, a(javaType, length, cls));
            }
        }
        return javaType2.withHandlersFrom(javaType);
    }

    private TypeBindings a(JavaType javaType, int i2, Class<?> cls) {
        PlaceholderForType[] placeholderForTypeArr = new PlaceholderForType[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            placeholderForTypeArr[i3] = new PlaceholderForType(i3);
        }
        JavaType findSuperType = _fromClass(null, cls, TypeBindings.create(cls, placeholderForTypeArr)).findSuperType(javaType.getRawClass());
        if (findSuperType != null) {
            String a2 = a(javaType, findSuperType);
            if (a2 == null) {
                JavaType[] javaTypeArr = new JavaType[i2];
                for (int i4 = 0; i4 < i2; i4++) {
                    JavaType actualType = placeholderForTypeArr[i4].actualType();
                    if (actualType == null) {
                        actualType = unknownType();
                    }
                    javaTypeArr[i4] = actualType;
                }
                return TypeBindings.create(cls, javaTypeArr);
            }
            throw new IllegalArgumentException("Failed to specialize base type " + javaType.toCanonical() + " as " + cls.getName() + ", problem: " + a2);
        }
        throw new IllegalArgumentException(String.format("Internal error: unable to locate supertype (%s) from resolved subtype %s", javaType.getRawClass().getName(), cls.getName()));
    }

    private String a(JavaType javaType, JavaType javaType2) throws IllegalArgumentException {
        List<JavaType> typeParameters = javaType.getBindings().getTypeParameters();
        List<JavaType> typeParameters2 = javaType2.getBindings().getTypeParameters();
        int size = typeParameters.size();
        for (int i2 = 0; i2 < size; i2++) {
            JavaType javaType3 = typeParameters.get(i2);
            JavaType javaType4 = typeParameters2.get(i2);
            if (!(b(javaType3, javaType4) || javaType3.hasRawClass(Object.class) || ((i2 == 0 && javaType.hasRawClass(Map.class) && javaType4.hasRawClass(Object.class)) || (javaType3.isInterface() && javaType3.isTypeOrSuperTypeOf(javaType4.getRawClass()))))) {
                return String.format("Type parameter #%d/%d differs; can not specialize %s with %s", Integer.valueOf(i2 + 1), Integer.valueOf(size), javaType3.toCanonical(), javaType4.toCanonical());
            }
        }
        return null;
    }

    private boolean b(JavaType javaType, JavaType javaType2) {
        if (javaType2 instanceof PlaceholderForType) {
            ((PlaceholderForType) javaType2).actualType(javaType);
            return true;
        } else if (javaType.getRawClass() != javaType2.getRawClass()) {
            return false;
        } else {
            List<JavaType> typeParameters = javaType.getBindings().getTypeParameters();
            List<JavaType> typeParameters2 = javaType2.getBindings().getTypeParameters();
            int size = typeParameters.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (!b(typeParameters.get(i2), typeParameters2.get(i2))) {
                    return false;
                }
            }
            return true;
        }
    }

    public JavaType constructGeneralizedType(JavaType javaType, Class<?> cls) {
        Class<?> rawClass = javaType.getRawClass();
        if (rawClass == cls) {
            return javaType;
        }
        JavaType findSuperType = javaType.findSuperType(cls);
        if (findSuperType != null) {
            return findSuperType;
        }
        if (!cls.isAssignableFrom(rawClass)) {
            throw new IllegalArgumentException(String.format("Class %s not a super-type of %s", cls.getName(), javaType));
        }
        throw new IllegalArgumentException(String.format("Internal error: class %s not included as super-type for %s", cls.getName(), javaType));
    }

    public JavaType constructFromCanonical(String str) throws IllegalArgumentException {
        return this._parser.parse(str);
    }

    public JavaType[] findTypeParameters(JavaType javaType, Class<?> cls) {
        JavaType findSuperType = javaType.findSuperType(cls);
        if (findSuperType == null) {
            return a;
        }
        return findSuperType.getBindings().typeParameterArray();
    }

    @Deprecated
    public JavaType[] findTypeParameters(Class<?> cls, Class<?> cls2, TypeBindings typeBindings) {
        return findTypeParameters(constructType(cls, typeBindings), cls2);
    }

    @Deprecated
    public JavaType[] findTypeParameters(Class<?> cls, Class<?> cls2) {
        return findTypeParameters(constructType(cls), cls2);
    }

    public JavaType moreSpecificType(JavaType javaType, JavaType javaType2) {
        Class<?> rawClass;
        Class<?> rawClass2;
        return javaType == null ? javaType2 : (javaType2 == null || (rawClass = javaType.getRawClass()) == (rawClass2 = javaType2.getRawClass()) || !rawClass.isAssignableFrom(rawClass2)) ? javaType : javaType2;
    }

    public JavaType constructType(Type type) {
        return _fromAny(null, type, EMPTY_BINDINGS);
    }

    public JavaType constructType(Type type, TypeBindings typeBindings) {
        return _fromAny(null, type, typeBindings);
    }

    public JavaType constructType(TypeReference<?> typeReference) {
        return _fromAny(null, typeReference.getType(), EMPTY_BINDINGS);
    }

    @Deprecated
    public JavaType constructType(Type type, Class<?> cls) {
        return constructType(type, cls == null ? null : constructType(cls));
    }

    @Deprecated
    public JavaType constructType(Type type, JavaType javaType) {
        TypeBindings typeBindings;
        if (javaType == null) {
            typeBindings = EMPTY_BINDINGS;
        } else {
            TypeBindings bindings = javaType.getBindings();
            if (type.getClass() != Class.class) {
                JavaType javaType2 = javaType;
                typeBindings = bindings;
                while (typeBindings.isEmpty() && (javaType2 = javaType2.getSuperClass()) != null) {
                    typeBindings = javaType2.getBindings();
                }
            } else {
                typeBindings = bindings;
            }
        }
        return _fromAny(null, type, typeBindings);
    }

    public ArrayType constructArrayType(Class<?> cls) {
        return ArrayType.construct(_fromAny(null, cls, null), null);
    }

    public ArrayType constructArrayType(JavaType javaType) {
        return ArrayType.construct(javaType, null);
    }

    public CollectionType constructCollectionType(Class<? extends Collection> cls, Class<?> cls2) {
        return constructCollectionType(cls, _fromClass(null, cls2, EMPTY_BINDINGS));
    }

    public CollectionType constructCollectionType(Class<? extends Collection> cls, JavaType javaType) {
        TypeBindings createIfNeeded = TypeBindings.createIfNeeded(cls, javaType);
        CollectionType collectionType = (CollectionType) _fromClass(null, cls, createIfNeeded);
        if (createIfNeeded.isEmpty() && javaType != null) {
            JavaType contentType = collectionType.findSuperType(Collection.class).getContentType();
            if (!contentType.equals(javaType)) {
                throw new IllegalArgumentException(String.format("Non-generic Collection class %s did not resolve to something with element type %s but %s ", ClassUtil.nameOf(cls), javaType, contentType));
            }
        }
        return collectionType;
    }

    public CollectionLikeType constructCollectionLikeType(Class<?> cls, Class<?> cls2) {
        return constructCollectionLikeType(cls, _fromClass(null, cls2, EMPTY_BINDINGS));
    }

    public CollectionLikeType constructCollectionLikeType(Class<?> cls, JavaType javaType) {
        JavaType _fromClass = _fromClass(null, cls, TypeBindings.createIfNeeded(cls, javaType));
        if (_fromClass instanceof CollectionLikeType) {
            return (CollectionLikeType) _fromClass;
        }
        return CollectionLikeType.upgradeFrom(_fromClass, javaType);
    }

    public MapType constructMapType(Class<? extends Map> cls, Class<?> cls2, Class<?> cls3) {
        JavaType javaType;
        JavaType javaType2;
        if (cls == Properties.class) {
            javaType2 = CORE_TYPE_STRING;
            javaType = javaType2;
        } else {
            javaType2 = _fromClass(null, cls2, EMPTY_BINDINGS);
            javaType = _fromClass(null, cls3, EMPTY_BINDINGS);
        }
        return constructMapType(cls, javaType2, javaType);
    }

    public MapType constructMapType(Class<? extends Map> cls, JavaType javaType, JavaType javaType2) {
        TypeBindings createIfNeeded = TypeBindings.createIfNeeded(cls, new JavaType[]{javaType, javaType2});
        MapType mapType = (MapType) _fromClass(null, cls, createIfNeeded);
        if (createIfNeeded.isEmpty()) {
            JavaType findSuperType = mapType.findSuperType(Map.class);
            JavaType keyType = findSuperType.getKeyType();
            if (keyType.equals(javaType)) {
                JavaType contentType = findSuperType.getContentType();
                if (!contentType.equals(javaType2)) {
                    throw new IllegalArgumentException(String.format("Non-generic Map class %s did not resolve to something with value type %s but %s ", ClassUtil.nameOf(cls), javaType2, contentType));
                }
            } else {
                throw new IllegalArgumentException(String.format("Non-generic Map class %s did not resolve to something with key type %s but %s ", ClassUtil.nameOf(cls), javaType, keyType));
            }
        }
        return mapType;
    }

    public MapLikeType constructMapLikeType(Class<?> cls, Class<?> cls2, Class<?> cls3) {
        return constructMapLikeType(cls, _fromClass(null, cls2, EMPTY_BINDINGS), _fromClass(null, cls3, EMPTY_BINDINGS));
    }

    public MapLikeType constructMapLikeType(Class<?> cls, JavaType javaType, JavaType javaType2) {
        JavaType _fromClass = _fromClass(null, cls, TypeBindings.createIfNeeded(cls, new JavaType[]{javaType, javaType2}));
        if (_fromClass instanceof MapLikeType) {
            return (MapLikeType) _fromClass;
        }
        return MapLikeType.upgradeFrom(_fromClass, javaType, javaType2);
    }

    public JavaType constructSimpleType(Class<?> cls, JavaType[] javaTypeArr) {
        return _fromClass(null, cls, TypeBindings.create(cls, javaTypeArr));
    }

    @Deprecated
    public JavaType constructSimpleType(Class<?> cls, Class<?> cls2, JavaType[] javaTypeArr) {
        return constructSimpleType(cls, javaTypeArr);
    }

    public JavaType constructReferenceType(Class<?> cls, JavaType javaType) {
        return ReferenceType.construct(cls, null, null, null, javaType);
    }

    @Deprecated
    public JavaType uncheckedSimpleType(Class<?> cls) {
        return _constructSimple(cls, EMPTY_BINDINGS, null, null);
    }

    public JavaType constructParametricType(Class<?> cls, Class<?>... clsArr) {
        int length = clsArr.length;
        JavaType[] javaTypeArr = new JavaType[length];
        for (int i2 = 0; i2 < length; i2++) {
            javaTypeArr[i2] = _fromClass(null, clsArr[i2], EMPTY_BINDINGS);
        }
        return constructParametricType(cls, javaTypeArr);
    }

    public JavaType constructParametricType(Class<?> cls, JavaType... javaTypeArr) {
        return _fromClass(null, cls, TypeBindings.create(cls, javaTypeArr));
    }

    @Deprecated
    public JavaType constructParametrizedType(Class<?> cls, Class<?> cls2, JavaType... javaTypeArr) {
        return constructParametricType(cls, javaTypeArr);
    }

    @Deprecated
    public JavaType constructParametrizedType(Class<?> cls, Class<?> cls2, Class<?>... clsArr) {
        return constructParametricType(cls, clsArr);
    }

    public CollectionType constructRawCollectionType(Class<? extends Collection> cls) {
        return constructCollectionType(cls, unknownType());
    }

    public CollectionLikeType constructRawCollectionLikeType(Class<?> cls) {
        return constructCollectionLikeType(cls, unknownType());
    }

    public MapType constructRawMapType(Class<? extends Map> cls) {
        return constructMapType(cls, unknownType(), unknownType());
    }

    public MapLikeType constructRawMapLikeType(Class<?> cls) {
        return constructMapLikeType(cls, unknownType(), unknownType());
    }

    private JavaType a(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        JavaType javaType2;
        JavaType javaType3;
        if (cls == Properties.class) {
            javaType3 = CORE_TYPE_STRING;
            javaType2 = javaType3;
        } else {
            List<JavaType> typeParameters = typeBindings.getTypeParameters();
            int size = typeParameters.size();
            if (size == 0) {
                javaType3 = _unknownType();
                javaType2 = javaType3;
            } else if (size == 2) {
                javaType3 = typeParameters.get(0);
                javaType2 = typeParameters.get(1);
            } else {
                throw new IllegalArgumentException("Strange Map type " + cls.getName() + ": cannot determine type parameters");
            }
        }
        return MapType.construct(cls, typeBindings, javaType, javaTypeArr, javaType3, javaType2);
    }

    private JavaType b(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        JavaType javaType2;
        List<JavaType> typeParameters = typeBindings.getTypeParameters();
        if (typeParameters.isEmpty()) {
            javaType2 = _unknownType();
        } else if (typeParameters.size() == 1) {
            javaType2 = typeParameters.get(0);
        } else {
            throw new IllegalArgumentException("Strange Collection type " + cls.getName() + ": cannot determine type parameters");
        }
        return CollectionType.construct(cls, typeBindings, javaType, javaTypeArr, javaType2);
    }

    private JavaType c(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        JavaType javaType2;
        List<JavaType> typeParameters = typeBindings.getTypeParameters();
        if (typeParameters.isEmpty()) {
            javaType2 = _unknownType();
        } else if (typeParameters.size() == 1) {
            javaType2 = typeParameters.get(0);
        } else {
            throw new IllegalArgumentException("Strange Reference type " + cls.getName() + ": cannot determine type parameters");
        }
        return ReferenceType.construct(cls, typeBindings, javaType, javaTypeArr, javaType2);
    }

    protected JavaType _constructSimple(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        JavaType _findWellKnownSimple;
        return (!typeBindings.isEmpty() || (_findWellKnownSimple = _findWellKnownSimple(cls)) == null) ? _newSimpleType(cls, typeBindings, javaType, javaTypeArr) : _findWellKnownSimple;
    }

    protected JavaType _newSimpleType(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        return new SimpleType(cls, typeBindings, javaType, javaTypeArr);
    }

    protected JavaType _unknownType() {
        return CORE_TYPE_OBJECT;
    }

    protected JavaType _findWellKnownSimple(Class<?> cls) {
        if (cls.isPrimitive()) {
            if (cls == g) {
                return CORE_TYPE_BOOL;
            }
            if (cls == h) {
                return CORE_TYPE_INT;
            }
            if (cls == i) {
                return CORE_TYPE_LONG;
            }
            return null;
        } else if (cls == b) {
            return CORE_TYPE_STRING;
        } else {
            if (cls == c) {
                return CORE_TYPE_OBJECT;
            }
            return null;
        }
    }

    protected JavaType _fromAny(ClassStack classStack, Type type, TypeBindings typeBindings) {
        JavaType javaType;
        if (type instanceof Class) {
            javaType = _fromClass(classStack, (Class) type, EMPTY_BINDINGS);
        } else if (type instanceof ParameterizedType) {
            javaType = _fromParamType(classStack, (ParameterizedType) type, typeBindings);
        } else if (type instanceof JavaType) {
            return (JavaType) type;
        } else {
            if (type instanceof GenericArrayType) {
                javaType = _fromArrayType(classStack, (GenericArrayType) type, typeBindings);
            } else if (type instanceof TypeVariable) {
                javaType = _fromVariable(classStack, (TypeVariable) type, typeBindings);
            } else if (type instanceof WildcardType) {
                javaType = _fromWildcard(classStack, (WildcardType) type, typeBindings);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unrecognized Type: ");
                sb.append(type == null ? "[null]" : type.toString());
                throw new IllegalArgumentException(sb.toString());
            }
        }
        if (this._modifiers == null) {
            return javaType;
        }
        TypeBindings bindings = javaType.getBindings();
        if (bindings == null) {
            bindings = EMPTY_BINDINGS;
        }
        TypeModifier[] typeModifierArr = this._modifiers;
        int length = typeModifierArr.length;
        JavaType javaType2 = javaType;
        int i2 = 0;
        while (i2 < length) {
            TypeModifier typeModifier = typeModifierArr[i2];
            JavaType modifyType = typeModifier.modifyType(javaType2, type, bindings, this);
            if (modifyType != null) {
                i2++;
                javaType2 = modifyType;
            } else {
                throw new IllegalStateException(String.format("TypeModifier %s (of type %s) return null for type %s", typeModifier, typeModifier.getClass().getName(), javaType2));
            }
        }
        return javaType2;
    }

    public JavaType _fromClass(ClassStack classStack, Class<?> cls, TypeBindings typeBindings) {
        ClassStack classStack2;
        JavaType[] javaTypeArr;
        JavaType javaType;
        JavaType _findWellKnownSimple = _findWellKnownSimple(cls);
        if (_findWellKnownSimple != null) {
            return _findWellKnownSimple;
        }
        Object asKey = (typeBindings == null || typeBindings.isEmpty()) ? cls : typeBindings.asKey(cls);
        JavaType javaType2 = this._typeCache.get(asKey);
        if (javaType2 != null) {
            return javaType2;
        }
        if (classStack == null) {
            classStack2 = new ClassStack(cls);
        } else {
            ClassStack find = classStack.find(cls);
            if (find != null) {
                ResolvedRecursiveType resolvedRecursiveType = new ResolvedRecursiveType(cls, EMPTY_BINDINGS);
                find.addSelfReference(resolvedRecursiveType);
                return resolvedRecursiveType;
            }
            classStack2 = classStack.child(cls);
        }
        if (cls.isArray()) {
            javaType2 = ArrayType.construct(_fromAny(classStack2, cls.getComponentType(), typeBindings), typeBindings);
        } else {
            if (cls.isInterface()) {
                javaType = null;
                javaTypeArr = _resolveSuperInterfaces(classStack2, cls, typeBindings);
            } else {
                javaType = _resolveSuperClass(classStack2, cls, typeBindings);
                javaTypeArr = _resolveSuperInterfaces(classStack2, cls, typeBindings);
            }
            if (cls == Properties.class) {
                SimpleType simpleType = CORE_TYPE_STRING;
                javaType2 = MapType.construct(cls, typeBindings, javaType, javaTypeArr, simpleType, simpleType);
            } else if (javaType != null) {
                javaType2 = javaType.refine(cls, typeBindings, javaType, javaTypeArr);
            }
            if (javaType2 == null && (javaType2 = _fromWellKnownClass(classStack2, cls, typeBindings, javaType, javaTypeArr)) == null && (javaType2 = _fromWellKnownInterface(classStack2, cls, typeBindings, javaType, javaTypeArr)) == null) {
                javaType2 = _newSimpleType(cls, typeBindings, javaType, javaTypeArr);
            }
        }
        classStack2.resolveSelfReferences(javaType2);
        if (!javaType2.hasHandlers()) {
            this._typeCache.putIfAbsent(asKey, javaType2);
        }
        return javaType2;
    }

    protected JavaType _resolveSuperClass(ClassStack classStack, Class<?> cls, TypeBindings typeBindings) {
        Type genericSuperclass = ClassUtil.getGenericSuperclass(cls);
        if (genericSuperclass == null) {
            return null;
        }
        return _fromAny(classStack, genericSuperclass, typeBindings);
    }

    protected JavaType[] _resolveSuperInterfaces(ClassStack classStack, Class<?> cls, TypeBindings typeBindings) {
        Type[] genericInterfaces = ClassUtil.getGenericInterfaces(cls);
        if (genericInterfaces == null || genericInterfaces.length == 0) {
            return a;
        }
        int length = genericInterfaces.length;
        JavaType[] javaTypeArr = new JavaType[length];
        for (int i2 = 0; i2 < length; i2++) {
            javaTypeArr[i2] = _fromAny(classStack, genericInterfaces[i2], typeBindings);
        }
        return javaTypeArr;
    }

    protected JavaType _fromWellKnownClass(ClassStack classStack, Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        if (typeBindings == null) {
            typeBindings = EMPTY_BINDINGS;
        }
        if (cls == Map.class) {
            return a(cls, typeBindings, javaType, javaTypeArr);
        }
        if (cls == Collection.class) {
            return b(cls, typeBindings, javaType, javaTypeArr);
        }
        if (cls == AtomicReference.class) {
            return c(cls, typeBindings, javaType, javaTypeArr);
        }
        return null;
    }

    protected JavaType _fromWellKnownInterface(ClassStack classStack, Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        for (JavaType javaType2 : javaTypeArr) {
            JavaType refine = javaType2.refine(cls, typeBindings, javaType, javaTypeArr);
            if (refine != null) {
                return refine;
            }
        }
        return null;
    }

    protected JavaType _fromParamType(ClassStack classStack, ParameterizedType parameterizedType, TypeBindings typeBindings) {
        TypeBindings typeBindings2;
        Class<?> cls = (Class) parameterizedType.getRawType();
        if (cls == f) {
            return CORE_TYPE_ENUM;
        }
        if (cls == d) {
            return CORE_TYPE_COMPARABLE;
        }
        if (cls == e) {
            return CORE_TYPE_CLASS;
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        int length = actualTypeArguments == null ? 0 : actualTypeArguments.length;
        if (length == 0) {
            typeBindings2 = EMPTY_BINDINGS;
        } else {
            JavaType[] javaTypeArr = new JavaType[length];
            for (int i2 = 0; i2 < length; i2++) {
                javaTypeArr[i2] = _fromAny(classStack, actualTypeArguments[i2], typeBindings);
            }
            typeBindings2 = TypeBindings.create(cls, javaTypeArr);
        }
        return _fromClass(classStack, cls, typeBindings2);
    }

    protected JavaType _fromArrayType(ClassStack classStack, GenericArrayType genericArrayType, TypeBindings typeBindings) {
        return ArrayType.construct(_fromAny(classStack, genericArrayType.getGenericComponentType(), typeBindings), typeBindings);
    }

    protected JavaType _fromVariable(ClassStack classStack, TypeVariable<?> typeVariable, TypeBindings typeBindings) {
        Type[] bounds;
        String name = typeVariable.getName();
        if (typeBindings != null) {
            JavaType findBoundType = typeBindings.findBoundType(name);
            if (findBoundType != null) {
                return findBoundType;
            }
            if (typeBindings.hasUnbound(name)) {
                return CORE_TYPE_OBJECT;
            }
            TypeBindings withUnboundVariable = typeBindings.withUnboundVariable(name);
            synchronized (typeVariable) {
                bounds = typeVariable.getBounds();
            }
            return _fromAny(classStack, bounds[0], withUnboundVariable);
        }
        throw new IllegalArgumentException("Null `bindings` passed (type variable \"" + name + "\")");
    }

    protected JavaType _fromWildcard(ClassStack classStack, WildcardType wildcardType, TypeBindings typeBindings) {
        return _fromAny(classStack, wildcardType.getUpperBounds()[0], typeBindings);
    }
}
