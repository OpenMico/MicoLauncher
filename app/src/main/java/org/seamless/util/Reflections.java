package org.seamless.util;

import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class Reflections {
    public static Object invoke(Method method, Object obj, Object... objArr) throws Exception {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalArgumentException e) {
            String str = "Could not invoke method by reflection: " + toString(method);
            if (objArr != null && objArr.length > 0) {
                str = str + " with parameters: (" + toClassNameString(", ", objArr) + ')';
            }
            throw new IllegalArgumentException(str + " on: " + obj.getClass().getName(), e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof Exception) {
                throw ((Exception) e2.getCause());
            }
            throw e2;
        }
    }

    public static Object get(Field field, Object obj) throws Exception {
        boolean isAccessible;
        try {
            isAccessible = field.isAccessible();
            try {
                field.setAccessible(true);
                return field.get(obj);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Could not get field value by reflection: " + toString(field) + " on: " + obj.getClass().getName(), e);
            }
        } finally {
            field.setAccessible(isAccessible);
        }
    }

    public static Method getMethod(Class cls, String str) {
        for (Class cls2 = cls; cls2 != null && cls2 != Object.class; cls2 = cls2.getSuperclass()) {
            try {
                return cls2.getDeclaredMethod(str, new Class[0]);
            } catch (NoSuchMethodException unused) {
            }
        }
        throw new IllegalArgumentException("No such method: " + cls.getName() + '.' + str);
    }

    public static void set(Field field, Object obj, Object obj2) throws Exception {
        String str;
        boolean isAccessible = field.isAccessible();
        try {
            try {
                field.setAccessible(true);
                field.set(obj, obj2);
            } catch (IllegalArgumentException e) {
                String str2 = "Could not set field value by reflection: " + toString(field) + " on: " + field.getDeclaringClass().getName();
                if (obj2 == null) {
                    str = str2 + " with null value";
                } else {
                    str = str2 + " with value: " + obj2.getClass();
                }
                throw new IllegalArgumentException(str, e);
            }
        } finally {
            field.setAccessible(isAccessible);
        }
    }

    public static String getMethodPropertyName(String str) {
        if (str.startsWith(BluetoothConstants.GET)) {
            return decapitalize(str.substring(3));
        }
        if (str.startsWith(ai.ae)) {
            return decapitalize(str.substring(2));
        }
        if (str.startsWith(BluetoothConstants.SET)) {
            return decapitalize(str.substring(3));
        }
        return null;
    }

    public static Method getGetterMethod(Class cls, String str) {
        while (cls != null && cls != Object.class) {
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (Method method : declaredMethods) {
                String name = method.getName();
                if (method.getParameterTypes().length == 0) {
                    if (name.startsWith(BluetoothConstants.GET)) {
                        if (decapitalize(name.substring(3)).equals(str)) {
                            return method;
                        }
                    } else if (name.startsWith(ai.ae) && decapitalize(name.substring(2)).equals(str)) {
                        return method;
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        return null;
    }

    public static List<Method> getMethods(Class cls, Class cls2) {
        ArrayList arrayList = new ArrayList();
        while (cls != null && cls != Object.class) {
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(cls2)) {
                    arrayList.add(method);
                }
            }
            cls = cls.getSuperclass();
        }
        return arrayList;
    }

    public static Field getField(Class cls, String str) {
        while (cls != null && cls != Object.class) {
            try {
                return cls.getDeclaredField(str);
            } catch (NoSuchFieldException unused) {
                cls = cls.getSuperclass();
            }
        }
        return null;
    }

    public static List<Field> getFields(Class cls, Class cls2) {
        ArrayList arrayList = new ArrayList();
        while (cls != null && cls != Object.class) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(cls2)) {
                    arrayList.add(field);
                }
            }
            cls = cls.getSuperclass();
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> List<Class<?>> getTypeArguments(Class<T> cls, Class<? extends T> cls2) {
        int i;
        Type[] typeArr;
        HashMap hashMap = new HashMap();
        while (true) {
            i = 0;
            if (getClass(cls2).equals(cls)) {
                break;
            } else if (cls2 instanceof Class) {
                cls2 = ((Class) cls2).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) cls2;
                Class cls3 = (Class) parameterizedType.getRawType();
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<Class<T>>[] typeParameters = cls3.getTypeParameters();
                while (i < actualTypeArguments.length) {
                    hashMap.put(typeParameters[i], actualTypeArguments[i]);
                    i++;
                }
                if (!cls3.equals(cls)) {
                    cls2 = cls3.getGenericSuperclass();
                }
            }
        }
        if (cls2 instanceof Class) {
            typeArr = ((Class) cls2).getTypeParameters();
        } else {
            typeArr = ((ParameterizedType) cls2).getActualTypeArguments();
        }
        ArrayList arrayList = new ArrayList();
        int length = typeArr.length;
        while (i < length) {
            Type type = typeArr[i];
            while (hashMap.containsKey(type)) {
                type = (Type) hashMap.get(type);
            }
            arrayList.add(getClass(type));
            i++;
        }
        return arrayList;
    }

    public static Class<?> getClass(Type type) {
        Class<?> cls;
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        }
        if (!(type instanceof GenericArrayType) || (cls = getClass(((GenericArrayType) type).getGenericComponentType())) == null) {
            return null;
        }
        return Array.newInstance(cls, 0).getClass();
    }

    public static Object getAndWrap(Field field, Object obj) {
        boolean isAccessible;
        try {
            isAccessible = field.isAccessible();
            try {
                field.setAccessible(true);
                return get(field, obj);
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw ((RuntimeException) e);
                }
                throw new IllegalArgumentException("exception setting: " + field.getName(), e);
            }
        } finally {
            field.setAccessible(isAccessible);
        }
    }

    public static void setAndWrap(Field field, Object obj, Object obj2) {
        boolean isAccessible;
        try {
            isAccessible = field.isAccessible();
            try {
                field.setAccessible(true);
                set(field, obj, obj2);
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw ((RuntimeException) e);
                }
                throw new IllegalArgumentException("exception setting: " + field.getName(), e);
            }
        } finally {
            field.setAccessible(isAccessible);
        }
    }

    public static Object invokeAndWrap(Method method, Object obj, Object... objArr) {
        try {
            return invoke(method, obj, objArr);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw ((RuntimeException) e);
            }
            throw new RuntimeException("exception invoking: " + method.getName(), e);
        }
    }

    public static String toString(Member member) {
        return unqualify(member.getDeclaringClass().getName()) + '.' + member.getName();
    }

    public static Class classForName(String str) throws ClassNotFoundException {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(str);
        } catch (Exception unused) {
            return Class.forName(str);
        }
    }

    public static boolean isClassAvailable(String str) {
        try {
            classForName(str);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static Class getCollectionElementType(Type type) {
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            if (actualTypeArguments.length != 0) {
                Type type2 = actualTypeArguments.length == 1 ? actualTypeArguments[0] : actualTypeArguments[1];
                if (type2 instanceof ParameterizedType) {
                    type2 = ((ParameterizedType) type2).getRawType();
                }
                if (type2 instanceof Class) {
                    return (Class) type2;
                }
                throw new IllegalArgumentException("type argument not a class");
            }
            throw new IllegalArgumentException("no type arguments for collection type");
        }
        throw new IllegalArgumentException("collection type not parameterized");
    }

    public static Class getMapKeyType(Type type) {
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            if (actualTypeArguments.length != 0) {
                Type type2 = actualTypeArguments[0];
                if (type2 instanceof Class) {
                    return (Class) type2;
                }
                throw new IllegalArgumentException("type argument not a class");
            }
            throw new IllegalArgumentException("no type arguments for collection type");
        }
        throw new IllegalArgumentException("collection type not parameterized");
    }

    public static Method getSetterMethod(Class cls, String str) {
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith(BluetoothConstants.SET) && method.getParameterTypes().length == 1 && decapitalize(name.substring(3)).equals(str)) {
                return method;
            }
        }
        throw new IllegalArgumentException("no such setter method: " + cls.getName() + '.' + str);
    }

    public static Method getMethod(Annotation annotation, String str) {
        try {
            return annotation.annotationType().getMethod(str, new Class[0]);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static boolean isInstanceOf(Class cls, String str) {
        if (str != null) {
            while (cls != Object.class) {
                if (instanceOf(cls, str)) {
                    return true;
                }
                cls = cls.getSuperclass();
            }
            return false;
        }
        throw new IllegalArgumentException("name cannot be null");
    }

    private static boolean instanceOf(Class cls, String str) {
        if (str.equals(cls.getName())) {
            return true;
        }
        Class<?>[] interfaces = cls.getInterfaces();
        boolean z = false;
        for (int i = 0; i < interfaces.length && !z; i++) {
            z = instanceOf(interfaces[i], str);
        }
        return z;
    }

    public static String toClassNameString(String str, Object... objArr) {
        if (objArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : objArr) {
            sb.append(str);
            if (obj == null) {
                sb.append("null");
            } else {
                sb.append(obj.getClass().getName());
            }
        }
        return sb.substring(str.length());
    }

    public static String unqualify(String str) {
        return unqualify(str, '.');
    }

    public static String unqualify(String str, char c) {
        return str.substring(str.lastIndexOf(c) + 1, str.length());
    }

    public static String decapitalize(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0 || (str.length() > 1 && Character.isUpperCase(str.charAt(1)))) {
            return str;
        }
        char[] charArray = str.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return new String(charArray);
    }
}
