package org.apache.commons.lang3.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.Builder;

/* loaded from: classes5.dex */
public class TypeUtils {
    public static final WildcardType WILDCARD_ALL = wildcardType().withUpperBounds(Object.class).build();

    /* loaded from: classes5.dex */
    public static class WildcardTypeBuilder implements Builder<WildcardType> {
        private Type[] a;
        private Type[] b;

        private WildcardTypeBuilder() {
        }

        public WildcardTypeBuilder withUpperBounds(Type... typeArr) {
            this.a = typeArr;
            return this;
        }

        public WildcardTypeBuilder withLowerBounds(Type... typeArr) {
            this.b = typeArr;
            return this;
        }

        @Override // org.apache.commons.lang3.builder.Builder
        public WildcardType build() {
            return new c(this.a, this.b);
        }
    }

    /* loaded from: classes5.dex */
    private static final class a implements GenericArrayType {
        private final Type a;

        private a(Type type) {
            this.a = type;
        }

        @Override // java.lang.reflect.GenericArrayType
        public Type getGenericComponentType() {
            return this.a;
        }

        public String toString() {
            return TypeUtils.toString(this);
        }

        public boolean equals(Object obj) {
            return obj == this || ((obj instanceof GenericArrayType) && TypeUtils.b(this, (GenericArrayType) obj));
        }

        public int hashCode() {
            return this.a.hashCode() | 1072;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class b implements ParameterizedType {
        private final Class<?> a;
        private final Type b;
        private final Type[] c;

        private b(Class<?> cls, Type type, Type[] typeArr) {
            this.a = cls;
            this.b = type;
            this.c = (Type[]) typeArr.clone();
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getRawType() {
            return this.a;
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getOwnerType() {
            return this.b;
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type[] getActualTypeArguments() {
            return (Type[]) this.c.clone();
        }

        public String toString() {
            return TypeUtils.toString(this);
        }

        public boolean equals(Object obj) {
            return obj == this || ((obj instanceof ParameterizedType) && TypeUtils.b(this, (ParameterizedType) obj));
        }

        public int hashCode() {
            return ((((this.a.hashCode() | 1136) << 4) | ObjectUtils.hashCode(this.b)) << 8) | Arrays.hashCode(this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class c implements WildcardType {
        private static final Type[] a = new Type[0];
        private final Type[] b;
        private final Type[] c;

        private c(Type[] typeArr, Type[] typeArr2) {
            this.b = (Type[]) ObjectUtils.defaultIfNull(typeArr, a);
            this.c = (Type[]) ObjectUtils.defaultIfNull(typeArr2, a);
        }

        @Override // java.lang.reflect.WildcardType
        public Type[] getUpperBounds() {
            return (Type[]) this.b.clone();
        }

        @Override // java.lang.reflect.WildcardType
        public Type[] getLowerBounds() {
            return (Type[]) this.c.clone();
        }

        public String toString() {
            return TypeUtils.toString(this);
        }

        public boolean equals(Object obj) {
            return obj == this || ((obj instanceof WildcardType) && TypeUtils.b(this, (WildcardType) obj));
        }

        public int hashCode() {
            return ((Arrays.hashCode(this.b) | 18688) << 8) | Arrays.hashCode(this.c);
        }
    }

    public static boolean isAssignable(Type type, Type type2) {
        return a(type, type2, (Map<TypeVariable<?>, Type>) null);
    }

    private static boolean a(Type type, Type type2, Map<TypeVariable<?>, Type> map) {
        if (type2 == null || (type2 instanceof Class)) {
            return a(type, (Class) type2);
        }
        if (type2 instanceof ParameterizedType) {
            return a(type, (ParameterizedType) type2, map);
        }
        if (type2 instanceof GenericArrayType) {
            return a(type, (GenericArrayType) type2, map);
        }
        if (type2 instanceof WildcardType) {
            return a(type, (WildcardType) type2, map);
        }
        if (type2 instanceof TypeVariable) {
            return a(type, (TypeVariable<?>) type2, map);
        }
        throw new IllegalStateException("found an unhandled type: " + type2);
    }

    private static boolean a(Type type, Class<?> cls) {
        if (type == null) {
            return cls == null || !cls.isPrimitive();
        }
        if (cls == null) {
            return false;
        }
        if (cls.equals(type)) {
            return true;
        }
        if (type instanceof Class) {
            return ClassUtils.isAssignable((Class) type, cls);
        }
        if (type instanceof ParameterizedType) {
            return a((Type) a((ParameterizedType) type), cls);
        }
        if (type instanceof TypeVariable) {
            for (Type type2 : ((TypeVariable) type).getBounds()) {
                if (a(type2, cls)) {
                    return true;
                }
            }
            return false;
        } else if (type instanceof GenericArrayType) {
            if (!cls.equals(Object.class)) {
                return cls.isArray() && a(((GenericArrayType) type).getGenericComponentType(), cls.getComponentType());
            }
            return true;
        } else if (type instanceof WildcardType) {
            return false;
        } else {
            throw new IllegalStateException("found an unhandled type: " + type);
        }
    }

    private static boolean a(Type type, ParameterizedType parameterizedType, Map<TypeVariable<?>, Type> map) {
        if (type == null) {
            return true;
        }
        if (parameterizedType == null) {
            return false;
        }
        if (parameterizedType.equals(type)) {
            return true;
        }
        Class<?> a2 = a(parameterizedType);
        Map<TypeVariable<?>, Type> a3 = a(type, a2, (Map<TypeVariable<?>, Type>) null);
        if (a3 == null) {
            return false;
        }
        if (a3.isEmpty()) {
            return true;
        }
        Map<TypeVariable<?>, Type> a4 = a(parameterizedType, a2, map);
        for (TypeVariable<?> typeVariable : a4.keySet()) {
            Type a5 = a(typeVariable, a4);
            Type a6 = a(typeVariable, a3);
            if (a5 != null || !(a6 instanceof Class)) {
                if (a6 != null && !a5.equals(a6) && (!(a5 instanceof WildcardType) || !a(a6, a5, map))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static Type a(TypeVariable<?> typeVariable, Map<TypeVariable<?>, Type> map) {
        Type type;
        while (true) {
            type = map.get(typeVariable);
            if (!(type instanceof TypeVariable) || type.equals(typeVariable)) {
                break;
            }
            typeVariable = (TypeVariable) type;
        }
        return type;
    }

    private static boolean a(Type type, GenericArrayType genericArrayType, Map<TypeVariable<?>, Type> map) {
        if (type == null) {
            return true;
        }
        if (genericArrayType == null) {
            return false;
        }
        if (genericArrayType.equals(type)) {
            return true;
        }
        Type genericComponentType = genericArrayType.getGenericComponentType();
        if (type instanceof Class) {
            Class cls = (Class) type;
            return cls.isArray() && a(cls.getComponentType(), genericComponentType, map);
        } else if (type instanceof GenericArrayType) {
            return a(((GenericArrayType) type).getGenericComponentType(), genericComponentType, map);
        } else {
            if (type instanceof WildcardType) {
                for (Type type2 : getImplicitUpperBounds((WildcardType) type)) {
                    if (isAssignable(type2, genericArrayType)) {
                        return true;
                    }
                }
                return false;
            } else if (type instanceof TypeVariable) {
                for (Type type3 : getImplicitBounds((TypeVariable) type)) {
                    if (isAssignable(type3, genericArrayType)) {
                        return true;
                    }
                }
                return false;
            } else if (type instanceof ParameterizedType) {
                return false;
            } else {
                throw new IllegalStateException("found an unhandled type: " + type);
            }
        }
    }

    private static boolean a(Type type, WildcardType wildcardType, Map<TypeVariable<?>, Type> map) {
        if (type == null) {
            return true;
        }
        if (wildcardType == null) {
            return false;
        }
        if (wildcardType.equals(type)) {
            return true;
        }
        Type[] implicitUpperBounds = getImplicitUpperBounds(wildcardType);
        Type[] implicitLowerBounds = getImplicitLowerBounds(wildcardType);
        if (type instanceof WildcardType) {
            WildcardType wildcardType2 = (WildcardType) type;
            Type[] implicitUpperBounds2 = getImplicitUpperBounds(wildcardType2);
            Type[] implicitLowerBounds2 = getImplicitLowerBounds(wildcardType2);
            for (Type type2 : implicitUpperBounds) {
                Type a2 = a(type2, map);
                for (Type type3 : implicitUpperBounds2) {
                    if (!a(type3, a2, map)) {
                        return false;
                    }
                }
            }
            for (Type type4 : implicitLowerBounds) {
                Type a3 = a(type4, map);
                for (Type type5 : implicitLowerBounds2) {
                    if (!a(a3, type5, map)) {
                        return false;
                    }
                }
            }
            return true;
        }
        for (Type type6 : implicitUpperBounds) {
            if (!a(type, a(type6, map), map)) {
                return false;
            }
        }
        for (Type type7 : implicitLowerBounds) {
            if (!a(a(type7, map), type, map)) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(Type type, TypeVariable<?> typeVariable, Map<TypeVariable<?>, Type> map) {
        if (type == null) {
            return true;
        }
        if (typeVariable == null) {
            return false;
        }
        if (typeVariable.equals(type)) {
            return true;
        }
        if (type instanceof TypeVariable) {
            for (Type type2 : getImplicitBounds((TypeVariable) type)) {
                if (a(type2, typeVariable, map)) {
                    return true;
                }
            }
        }
        if ((type instanceof Class) || (type instanceof ParameterizedType) || (type instanceof GenericArrayType) || (type instanceof WildcardType)) {
            return false;
        }
        throw new IllegalStateException("found an unhandled type: " + type);
    }

    private static Type a(Type type, Map<TypeVariable<?>, Type> map) {
        if (!(type instanceof TypeVariable) || map == null) {
            return type;
        }
        Type type2 = map.get(type);
        if (type2 != null) {
            return type2;
        }
        throw new IllegalArgumentException("missing assignment type for type variable " + type);
    }

    public static Map<TypeVariable<?>, Type> getTypeArguments(ParameterizedType parameterizedType) {
        return a(parameterizedType, a(parameterizedType), (Map<TypeVariable<?>, Type>) null);
    }

    public static Map<TypeVariable<?>, Type> getTypeArguments(Type type, Class<?> cls) {
        return a(type, cls, (Map<TypeVariable<?>, Type>) null);
    }

    private static Map<TypeVariable<?>, Type> a(Type type, Class<?> cls, Map<TypeVariable<?>, Type> map) {
        if (type instanceof Class) {
            return a((Class<?>) type, cls, map);
        }
        if (type instanceof ParameterizedType) {
            return a((ParameterizedType) type, cls, map);
        }
        if (type instanceof GenericArrayType) {
            Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            if (cls.isArray()) {
                cls = cls.getComponentType();
            }
            return a(genericComponentType, cls, map);
        }
        int i = 0;
        if (type instanceof WildcardType) {
            Type[] implicitUpperBounds = getImplicitUpperBounds((WildcardType) type);
            int length = implicitUpperBounds.length;
            while (i < length) {
                Type type2 = implicitUpperBounds[i];
                if (a(type2, cls)) {
                    return a(type2, cls, map);
                }
                i++;
            }
            return null;
        } else if (type instanceof TypeVariable) {
            Type[] implicitBounds = getImplicitBounds((TypeVariable) type);
            int length2 = implicitBounds.length;
            while (i < length2) {
                Type type3 = implicitBounds[i];
                if (a(type3, cls)) {
                    return a(type3, cls, map);
                }
                i++;
            }
            return null;
        } else {
            throw new IllegalStateException("found an unhandled type: " + type);
        }
    }

    private static Map<TypeVariable<?>, Type> a(ParameterizedType parameterizedType, Class<?> cls, Map<TypeVariable<?>, Type> map) {
        Map<TypeVariable<?>, Type> map2;
        Class<?> a2 = a(parameterizedType);
        if (!a((Type) a2, cls)) {
            return null;
        }
        Type ownerType = parameterizedType.getOwnerType();
        if (ownerType instanceof ParameterizedType) {
            ParameterizedType parameterizedType2 = (ParameterizedType) ownerType;
            map2 = a(parameterizedType2, a(parameterizedType2), map);
        } else {
            map2 = map == null ? new HashMap<>() : new HashMap<>(map);
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        TypeVariable<Class<?>>[] typeParameters = a2.getTypeParameters();
        for (int i = 0; i < typeParameters.length; i++) {
            Type type = actualTypeArguments[i];
            TypeVariable<Class<?>> typeVariable = typeParameters[i];
            if (map2.containsKey(type)) {
                type = map2.get(type);
            }
            map2.put(typeVariable, type);
        }
        return cls.equals(a2) ? map2 : a(a(a2, cls), cls, map2);
    }

    private static Map<TypeVariable<?>, Type> a(Class<?> cls, Class<?> cls2, Map<TypeVariable<?>, Type> map) {
        if (!a((Type) cls, cls2)) {
            return null;
        }
        if (cls.isPrimitive()) {
            if (cls2.isPrimitive()) {
                return new HashMap();
            }
            cls = ClassUtils.primitiveToWrapper(cls);
        }
        HashMap hashMap = map == null ? new HashMap() : new HashMap(map);
        return cls2.equals(cls) ? hashMap : a(a(cls, cls2), cls2, (Map<TypeVariable<?>, Type>) hashMap);
    }

    public static Map<TypeVariable<?>, Type> determineTypeArguments(Class<?> cls, ParameterizedType parameterizedType) {
        Validate.notNull(cls, "cls is null", new Object[0]);
        Validate.notNull(parameterizedType, "superType is null", new Object[0]);
        Class<?> a2 = a(parameterizedType);
        if (!a((Type) cls, a2)) {
            return null;
        }
        if (cls.equals(a2)) {
            return a(parameterizedType, a2, (Map<TypeVariable<?>, Type>) null);
        }
        Type a3 = a(cls, a2);
        if (a3 instanceof Class) {
            return determineTypeArguments((Class) a3, parameterizedType);
        }
        ParameterizedType parameterizedType2 = (ParameterizedType) a3;
        Map<TypeVariable<?>, Type> determineTypeArguments = determineTypeArguments(a(parameterizedType2), parameterizedType);
        a((Class) cls, parameterizedType2, determineTypeArguments);
        return determineTypeArguments;
    }

    private static <T> void a(Class<T> cls, ParameterizedType parameterizedType, Map<TypeVariable<?>, Type> map) {
        Type ownerType = parameterizedType.getOwnerType();
        if (ownerType instanceof ParameterizedType) {
            a((Class) cls, (ParameterizedType) ownerType, map);
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        TypeVariable<Class<?>>[] typeParameters = a(parameterizedType).getTypeParameters();
        List asList = Arrays.asList(cls.getTypeParameters());
        for (int i = 0; i < actualTypeArguments.length; i++) {
            TypeVariable<Class<?>> typeVariable = typeParameters[i];
            Type type = actualTypeArguments[i];
            if (asList.contains(type) && map.containsKey(typeVariable)) {
                map.put((TypeVariable) type, map.get(typeVariable));
            }
        }
    }

    private static Type a(Class<?> cls, Class<?> cls2) {
        Class<?> cls3;
        if (cls2.isInterface()) {
            Type[] genericInterfaces = cls.getGenericInterfaces();
            Type type = null;
            for (Type type2 : genericInterfaces) {
                if (type2 instanceof ParameterizedType) {
                    cls3 = a((ParameterizedType) type2);
                } else if (type2 instanceof Class) {
                    cls3 = (Class) type2;
                } else {
                    throw new IllegalStateException("Unexpected generic interface type found: " + type2);
                }
                if (a((Type) cls3, cls2) && isAssignable(type, cls3)) {
                    type = type2;
                }
            }
            if (type != null) {
                return type;
            }
        }
        return cls.getGenericSuperclass();
    }

    public static boolean isInstance(Object obj, Type type) {
        if (type == null) {
            return false;
        }
        if (obj == null) {
            return !(type instanceof Class) || !((Class) type).isPrimitive();
        }
        return a(obj.getClass(), type, (Map<TypeVariable<?>, Type>) null);
    }

    public static Type[] normalizeUpperBounds(Type[] typeArr) {
        boolean z;
        Validate.notNull(typeArr, "null value specified for bounds array", new Object[0]);
        if (typeArr.length < 2) {
            return typeArr;
        }
        HashSet hashSet = new HashSet(typeArr.length);
        for (Type type : typeArr) {
            int length = typeArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = false;
                    break;
                }
                Type type2 = typeArr[i];
                if (type != type2 && a(type2, type, (Map<TypeVariable<?>, Type>) null)) {
                    z = true;
                    break;
                }
                i++;
            }
            if (!z) {
                hashSet.add(type);
            }
        }
        return (Type[]) hashSet.toArray(new Type[hashSet.size()]);
    }

    public static Type[] getImplicitBounds(TypeVariable<?> typeVariable) {
        Validate.notNull(typeVariable, "typeVariable is null", new Object[0]);
        Type[] bounds = typeVariable.getBounds();
        return bounds.length == 0 ? new Type[]{Object.class} : normalizeUpperBounds(bounds);
    }

    public static Type[] getImplicitUpperBounds(WildcardType wildcardType) {
        Validate.notNull(wildcardType, "wildcardType is null", new Object[0]);
        Type[] upperBounds = wildcardType.getUpperBounds();
        return upperBounds.length == 0 ? new Type[]{Object.class} : normalizeUpperBounds(upperBounds);
    }

    public static Type[] getImplicitLowerBounds(WildcardType wildcardType) {
        Validate.notNull(wildcardType, "wildcardType is null", new Object[0]);
        Type[] lowerBounds = wildcardType.getLowerBounds();
        return lowerBounds.length == 0 ? new Type[]{null} : lowerBounds;
    }

    public static boolean typesSatisfyVariables(Map<TypeVariable<?>, Type> map) {
        Validate.notNull(map, "typeVarAssigns is null", new Object[0]);
        for (Map.Entry<TypeVariable<?>, Type> entry : map.entrySet()) {
            Type value = entry.getValue();
            for (Type type : getImplicitBounds(entry.getKey())) {
                if (!a(value, a(type, map), map)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static Class<?> a(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        if (rawType instanceof Class) {
            return (Class) rawType;
        }
        throw new IllegalStateException("Wait... What!? Type of rawType: " + rawType);
    }

    public static Class<?> getRawType(Type type, Type type2) {
        Map<TypeVariable<?>, Type> typeArguments;
        Type type3;
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return a((ParameterizedType) type);
        }
        if (type instanceof TypeVariable) {
            if (type2 == null) {
                return null;
            }
            GenericDeclaration genericDeclaration = ((TypeVariable) type).getGenericDeclaration();
            if (!(genericDeclaration instanceof Class) || (typeArguments = getTypeArguments(type2, (Class) genericDeclaration)) == null || (type3 = typeArguments.get(type)) == null) {
                return null;
            }
            return getRawType(type3, type2);
        } else if (type instanceof GenericArrayType) {
            return Array.newInstance(getRawType(((GenericArrayType) type).getGenericComponentType(), type2), 0).getClass();
        } else {
            if (type instanceof WildcardType) {
                return null;
            }
            throw new IllegalArgumentException("unknown type: " + type);
        }
    }

    public static boolean isArrayType(Type type) {
        return (type instanceof GenericArrayType) || ((type instanceof Class) && ((Class) type).isArray());
    }

    public static Type getArrayComponentType(Type type) {
        if (type instanceof Class) {
            Class cls = (Class) type;
            if (cls.isArray()) {
                return cls.getComponentType();
            }
            return null;
        } else if (type instanceof GenericArrayType) {
            return ((GenericArrayType) type).getGenericComponentType();
        } else {
            return null;
        }
    }

    public static Type unrollVariables(Map<TypeVariable<?>, Type> map, Type type) {
        if (map == null) {
            map = Collections.emptyMap();
        }
        if (containsTypeVariables(type)) {
            if (type instanceof TypeVariable) {
                return unrollVariables(map, map.get(type));
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (parameterizedType.getOwnerType() != null) {
                    HashMap hashMap = new HashMap(map);
                    hashMap.putAll(getTypeArguments(parameterizedType));
                    map = hashMap;
                }
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    Type unrollVariables = unrollVariables(map, actualTypeArguments[i]);
                    if (unrollVariables != null) {
                        actualTypeArguments[i] = unrollVariables;
                    }
                }
                return parameterizeWithOwner(parameterizedType.getOwnerType(), (Class) parameterizedType.getRawType(), actualTypeArguments);
            } else if (type instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) type;
                return wildcardType().withUpperBounds(a(map, wildcardType.getUpperBounds())).withLowerBounds(a(map, wildcardType.getLowerBounds())).build();
            }
        }
        return type;
    }

    private static Type[] a(Map<TypeVariable<?>, Type> map, Type[] typeArr) {
        int i = 0;
        while (i < typeArr.length) {
            Type unrollVariables = unrollVariables(map, typeArr[i]);
            if (unrollVariables == null) {
                i--;
                typeArr = (Type[]) ArrayUtils.remove(typeArr, i);
            } else {
                typeArr[i] = unrollVariables;
            }
            i++;
        }
        return typeArr;
    }

    public static boolean containsTypeVariables(Type type) {
        if (type instanceof TypeVariable) {
            return true;
        }
        if (type instanceof Class) {
            return ((Class) type).getTypeParameters().length > 0;
        }
        if (type instanceof ParameterizedType) {
            for (Type type2 : ((ParameterizedType) type).getActualTypeArguments()) {
                if (containsTypeVariables(type2)) {
                    return true;
                }
            }
            return false;
        } else if (!(type instanceof WildcardType)) {
            return false;
        } else {
            WildcardType wildcardType = (WildcardType) type;
            return containsTypeVariables(getImplicitLowerBounds(wildcardType)[0]) || containsTypeVariables(getImplicitUpperBounds(wildcardType)[0]);
        }
    }

    public static final ParameterizedType parameterize(Class<?> cls, Type... typeArr) {
        return parameterizeWithOwner((Type) null, cls, typeArr);
    }

    public static final ParameterizedType parameterize(Class<?> cls, Map<TypeVariable<?>, Type> map) {
        Validate.notNull(cls, "raw class is null", new Object[0]);
        Validate.notNull(map, "typeArgMappings is null", new Object[0]);
        return parameterizeWithOwner((Type) null, cls, a(map, (TypeVariable<?>[]) cls.getTypeParameters()));
    }

    public static final ParameterizedType parameterizeWithOwner(Type type, Class<?> cls, Type... typeArr) {
        Validate.notNull(cls, "raw class is null", new Object[0]);
        if (cls.getEnclosingClass() == null) {
            Validate.isTrue(type == null, "no owner allowed for top-level %s", cls);
            type = null;
        } else if (type == null) {
            type = cls.getEnclosingClass();
        } else {
            Validate.isTrue(a(type, cls.getEnclosingClass()), "%s is invalid owner type for parameterized %s", type, cls);
        }
        Validate.noNullElements(typeArr, "null type argument at index %s", new Object[0]);
        Validate.isTrue(cls.getTypeParameters().length == typeArr.length, "invalid number of type parameters specified: expected %d, got %d", Integer.valueOf(cls.getTypeParameters().length), Integer.valueOf(typeArr.length));
        return new b(cls, type, typeArr);
    }

    public static final ParameterizedType parameterizeWithOwner(Type type, Class<?> cls, Map<TypeVariable<?>, Type> map) {
        Validate.notNull(cls, "raw class is null", new Object[0]);
        Validate.notNull(map, "typeArgMappings is null", new Object[0]);
        return parameterizeWithOwner(type, cls, a(map, (TypeVariable<?>[]) cls.getTypeParameters()));
    }

    private static Type[] a(Map<TypeVariable<?>, Type> map, TypeVariable<?>[] typeVariableArr) {
        Type[] typeArr = new Type[typeVariableArr.length];
        int i = 0;
        for (TypeVariable<?> typeVariable : typeVariableArr) {
            Validate.isTrue(map.containsKey(typeVariable), "missing argument mapping for %s", toString(typeVariable));
            i++;
            typeArr[i] = map.get(typeVariable);
        }
        return typeArr;
    }

    public static WildcardTypeBuilder wildcardType() {
        return new WildcardTypeBuilder();
    }

    public static GenericArrayType genericArrayType(Type type) {
        return new a((Type) Validate.notNull(type, "componentType is null", new Object[0]));
    }

    public static boolean equals(Type type, Type type2) {
        if (ObjectUtils.equals(type, type2)) {
            return true;
        }
        if (type instanceof ParameterizedType) {
            return b((ParameterizedType) type, type2);
        }
        if (type instanceof GenericArrayType) {
            return b((GenericArrayType) type, type2);
        }
        if (type instanceof WildcardType) {
            return b((WildcardType) type, type2);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(ParameterizedType parameterizedType, Type type) {
        if (!(type instanceof ParameterizedType)) {
            return false;
        }
        ParameterizedType parameterizedType2 = (ParameterizedType) type;
        if (!equals(parameterizedType.getRawType(), parameterizedType2.getRawType()) || !equals(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType())) {
            return false;
        }
        return a(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(GenericArrayType genericArrayType, Type type) {
        return (type instanceof GenericArrayType) && equals(genericArrayType.getGenericComponentType(), ((GenericArrayType) type).getGenericComponentType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(WildcardType wildcardType, Type type) {
        if (!(type instanceof WildcardType)) {
            return false;
        }
        WildcardType wildcardType2 = (WildcardType) type;
        return a(getImplicitLowerBounds(wildcardType), getImplicitLowerBounds(wildcardType2)) && a(getImplicitUpperBounds(wildcardType), getImplicitUpperBounds(wildcardType2));
    }

    private static boolean a(Type[] typeArr, Type[] typeArr2) {
        if (typeArr.length != typeArr2.length) {
            return false;
        }
        for (int i = 0; i < typeArr.length; i++) {
            if (!equals(typeArr[i], typeArr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static String toString(Type type) {
        Validate.notNull(type);
        if (type instanceof Class) {
            return a((Class) type);
        }
        if (type instanceof ParameterizedType) {
            return b((ParameterizedType) type);
        }
        if (type instanceof WildcardType) {
            return a((WildcardType) type);
        }
        if (type instanceof TypeVariable) {
            return a((TypeVariable) type);
        }
        if (type instanceof GenericArrayType) {
            return a((GenericArrayType) type);
        }
        throw new IllegalArgumentException(ObjectUtils.identityToString(type));
    }

    public static String toLongString(TypeVariable<?> typeVariable) {
        Validate.notNull(typeVariable, "var is null", new Object[0]);
        StringBuilder sb = new StringBuilder();
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            Class<?> cls = (Class) genericDeclaration;
            while (cls.getEnclosingClass() != null) {
                sb.insert(0, cls.getSimpleName()).insert(0, '.');
                cls = cls.getEnclosingClass();
            }
            sb.insert(0, cls.getName());
        } else if (genericDeclaration instanceof Type) {
            sb.append(toString((Type) genericDeclaration));
        } else {
            sb.append(genericDeclaration);
        }
        sb.append(':');
        sb.append(a(typeVariable));
        return sb.toString();
    }

    public static <T> Typed<T> wrap(final Type type) {
        return new Typed<T>() { // from class: org.apache.commons.lang3.reflect.TypeUtils.1
            @Override // org.apache.commons.lang3.reflect.Typed
            public Type getType() {
                return type;
            }
        };
    }

    public static <T> Typed<T> wrap(Class<T> cls) {
        return wrap((Type) cls);
    }

    private static String a(Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        if (cls.getEnclosingClass() != null) {
            sb.append(a(cls.getEnclosingClass()));
            sb.append('.');
            sb.append(cls.getSimpleName());
        } else {
            sb.append(cls.getName());
        }
        if (cls.getTypeParameters().length > 0) {
            sb.append(Typography.less);
            a(sb, ", ", cls.getTypeParameters());
            sb.append(Typography.greater);
        }
        return sb.toString();
    }

    private static String a(TypeVariable<?> typeVariable) {
        StringBuilder sb = new StringBuilder(typeVariable.getName());
        Type[] bounds = typeVariable.getBounds();
        if (bounds.length > 0 && (bounds.length != 1 || !Object.class.equals(bounds[0]))) {
            sb.append(" extends ");
            a(sb, " & ", typeVariable.getBounds());
        }
        return sb.toString();
    }

    private static String b(ParameterizedType parameterizedType) {
        StringBuilder sb = new StringBuilder();
        Type ownerType = parameterizedType.getOwnerType();
        Class cls = (Class) parameterizedType.getRawType();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (ownerType == null) {
            sb.append(cls.getName());
        } else {
            if (ownerType instanceof Class) {
                sb.append(((Class) ownerType).getName());
            } else {
                sb.append(ownerType.toString());
            }
            sb.append('.');
            sb.append(cls.getSimpleName());
        }
        sb.append(Typography.less);
        a(sb, ", ", actualTypeArguments).append(Typography.greater);
        return sb.toString();
    }

    private static String a(WildcardType wildcardType) {
        StringBuilder sb = new StringBuilder();
        sb.append('?');
        Type[] lowerBounds = wildcardType.getLowerBounds();
        Type[] upperBounds = wildcardType.getUpperBounds();
        if (lowerBounds.length > 1 || (lowerBounds.length == 1 && lowerBounds[0] != null)) {
            sb.append(" super ");
            a(sb, " & ", lowerBounds);
        } else if (upperBounds.length > 1 || (upperBounds.length == 1 && !Object.class.equals(upperBounds[0]))) {
            sb.append(" extends ");
            a(sb, " & ", upperBounds);
        }
        return sb.toString();
    }

    private static String a(GenericArrayType genericArrayType) {
        return String.format("%s[]", toString(genericArrayType.getGenericComponentType()));
    }

    private static StringBuilder a(StringBuilder sb, String str, Type... typeArr) {
        Validate.notEmpty(Validate.noNullElements(typeArr));
        if (typeArr.length > 0) {
            sb.append(toString(typeArr[0]));
            for (int i = 1; i < typeArr.length; i++) {
                sb.append(str);
                sb.append(toString(typeArr[i]));
            }
        }
        return sb;
    }
}
