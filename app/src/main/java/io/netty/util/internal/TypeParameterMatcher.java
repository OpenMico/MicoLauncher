package io.netty.util.internal;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class TypeParameterMatcher {
    private static final TypeParameterMatcher a = new NoOpTypeParameterMatcher();
    private static final Object b = new Object();

    public abstract boolean match(Object obj);

    public static TypeParameterMatcher get(Class<?> cls) {
        Map<Class<?>, TypeParameterMatcher> typeParameterMatcherGetCache = InternalThreadLocalMap.get().typeParameterMatcherGetCache();
        TypeParameterMatcher typeParameterMatcher = typeParameterMatcherGetCache.get(cls);
        if (typeParameterMatcher == null) {
            if (cls == Object.class) {
                typeParameterMatcher = a;
            } else if (PlatformDependent.hasJavassist()) {
                try {
                    typeParameterMatcher = JavassistTypeParameterMatcherGenerator.generate(cls);
                    typeParameterMatcher.match(b);
                } catch (Exception unused) {
                    typeParameterMatcher = null;
                } catch (IllegalAccessError unused2) {
                    typeParameterMatcher = null;
                }
            }
            if (typeParameterMatcher == null) {
                typeParameterMatcher = new a(cls);
            }
            typeParameterMatcherGetCache.put(cls, typeParameterMatcher);
        }
        return typeParameterMatcher;
    }

    public static TypeParameterMatcher find(Object obj, Class<?> cls, String str) {
        Map<Class<?>, Map<String, TypeParameterMatcher>> typeParameterMatcherFindCache = InternalThreadLocalMap.get().typeParameterMatcherFindCache();
        Class<?> cls2 = obj.getClass();
        Map<String, TypeParameterMatcher> map = typeParameterMatcherFindCache.get(cls2);
        if (map == null) {
            map = new HashMap<>();
            typeParameterMatcherFindCache.put(cls2, map);
        }
        TypeParameterMatcher typeParameterMatcher = map.get(str);
        if (typeParameterMatcher != null) {
            return typeParameterMatcher;
        }
        TypeParameterMatcher typeParameterMatcher2 = get(a(obj, cls, str));
        map.put(str, typeParameterMatcher2);
        return typeParameterMatcher2;
    }

    private static Class<?> a(Object obj, Class<?> cls, String str) {
        Class<?> cls2 = obj.getClass();
        String str2 = str;
        Class<?> cls3 = cls;
        Class<?> cls4 = cls2;
        while (true) {
            if (cls4.getSuperclass() == cls3) {
                int i = -1;
                TypeVariable<Class<? super Object>>[] typeParameters = cls4.getSuperclass().getTypeParameters();
                int i2 = 0;
                while (true) {
                    if (i2 >= typeParameters.length) {
                        break;
                    } else if (str2.equals(typeParameters[i2].getName())) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    Type genericSuperclass = cls4.getGenericSuperclass();
                    if (!(genericSuperclass instanceof ParameterizedType)) {
                        return Object.class;
                    }
                    Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[i];
                    if (type instanceof ParameterizedType) {
                        type = ((ParameterizedType) type).getRawType();
                    }
                    if (type instanceof Class) {
                        return (Class) type;
                    }
                    if (type instanceof GenericArrayType) {
                        Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
                        if (genericComponentType instanceof ParameterizedType) {
                            genericComponentType = ((ParameterizedType) genericComponentType).getRawType();
                        }
                        if (genericComponentType instanceof Class) {
                            return Array.newInstance((Class) genericComponentType, 0).getClass();
                        }
                    }
                    if (!(type instanceof TypeVariable)) {
                        return a(cls2, str2);
                    }
                    TypeVariable typeVariable = (TypeVariable) type;
                    if (!(typeVariable.getGenericDeclaration() instanceof Class)) {
                        return Object.class;
                    }
                    cls3 = (Class) typeVariable.getGenericDeclaration();
                    str2 = typeVariable.getName();
                    if (!cls3.isAssignableFrom(cls2)) {
                        return Object.class;
                    }
                    cls4 = cls2;
                } else {
                    throw new IllegalStateException("unknown type parameter '" + str2 + "': " + cls3);
                }
            } else {
                cls4 = cls4.getSuperclass();
                if (cls4 == null) {
                    return a(cls2, str2);
                }
            }
        }
    }

    private static Class<?> a(Class<?> cls, String str) {
        throw new IllegalStateException("cannot determine the type of the type parameter '" + str + "': " + cls);
    }

    /* loaded from: classes4.dex */
    public static final class a extends TypeParameterMatcher {
        private final Class<?> a;

        a(Class<?> cls) {
            this.a = cls;
        }

        @Override // io.netty.util.internal.TypeParameterMatcher
        public boolean match(Object obj) {
            return this.a.isInstance(obj);
        }
    }
}
