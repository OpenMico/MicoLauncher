package org.apache.commons.lang.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;

/* loaded from: classes5.dex */
public class MethodUtils {
    public static Object invokeMethod(Object obj, String str, Object obj2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeMethod(obj, str, new Object[]{obj2});
    }

    public static Object invokeMethod(Object obj, String str, Object[] objArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        return invokeMethod(obj, str, objArr, clsArr);
    }

    public static Object invokeMethod(Object obj, String str, Object[] objArr, Class[] clsArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        Method matchingAccessibleMethod = getMatchingAccessibleMethod(obj.getClass(), str, clsArr);
        if (matchingAccessibleMethod != null) {
            return matchingAccessibleMethod.invoke(obj, objArr);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("No such accessible method: ");
        stringBuffer.append(str);
        stringBuffer.append("() on object: ");
        stringBuffer.append(obj.getClass().getName());
        throw new NoSuchMethodException(stringBuffer.toString());
    }

    public static Object invokeExactMethod(Object obj, String str, Object obj2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeExactMethod(obj, str, new Object[]{obj2});
    }

    public static Object invokeExactMethod(Object obj, String str, Object[] objArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        return invokeExactMethod(obj, str, objArr, clsArr);
    }

    public static Object invokeExactMethod(Object obj, String str, Object[] objArr, Class[] clsArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        Method accessibleMethod = getAccessibleMethod(obj.getClass(), str, clsArr);
        if (accessibleMethod != null) {
            return accessibleMethod.invoke(obj, objArr);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("No such accessible method: ");
        stringBuffer.append(str);
        stringBuffer.append("() on object: ");
        stringBuffer.append(obj.getClass().getName());
        throw new NoSuchMethodException(stringBuffer.toString());
    }

    public static Object invokeExactStaticMethod(Class cls, String str, Object[] objArr, Class[] clsArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        Method accessibleMethod = getAccessibleMethod(cls, str, clsArr);
        if (accessibleMethod != null) {
            return accessibleMethod.invoke(null, objArr);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("No such accessible method: ");
        stringBuffer.append(str);
        stringBuffer.append("() on class: ");
        stringBuffer.append(cls.getName());
        throw new NoSuchMethodException(stringBuffer.toString());
    }

    public static Object invokeStaticMethod(Class cls, String str, Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeStaticMethod(cls, str, new Object[]{obj});
    }

    public static Object invokeStaticMethod(Class cls, String str, Object[] objArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        return invokeStaticMethod(cls, str, objArr, clsArr);
    }

    public static Object invokeStaticMethod(Class cls, String str, Object[] objArr, Class[] clsArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        Method matchingAccessibleMethod = getMatchingAccessibleMethod(cls, str, clsArr);
        if (matchingAccessibleMethod != null) {
            return matchingAccessibleMethod.invoke(null, objArr);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("No such accessible method: ");
        stringBuffer.append(str);
        stringBuffer.append("() on class: ");
        stringBuffer.append(cls.getName());
        throw new NoSuchMethodException(stringBuffer.toString());
    }

    public static Object invokeExactStaticMethod(Class cls, String str, Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeExactStaticMethod(cls, str, new Object[]{obj});
    }

    public static Object invokeExactStaticMethod(Class cls, String str, Object[] objArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        return invokeExactStaticMethod(cls, str, objArr, clsArr);
    }

    public static Method getAccessibleMethod(Class cls, String str, Class cls2) {
        return getAccessibleMethod(cls, str, new Class[]{cls2});
    }

    public static Method getAccessibleMethod(Class cls, String str, Class[] clsArr) {
        try {
            return getAccessibleMethod(cls.getMethod(str, clsArr));
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static Method getAccessibleMethod(Method method) {
        if (!a.a((Member) method)) {
            return null;
        }
        Class<?> declaringClass = method.getDeclaringClass();
        if (Modifier.isPublic(declaringClass.getModifiers())) {
            return method;
        }
        String name = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Method b = b(declaringClass, name, parameterTypes);
        return b == null ? a(declaringClass, name, parameterTypes) : b;
    }

    private static Method a(Class cls, String str, Class[] clsArr) {
        for (Class superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            if (Modifier.isPublic(superclass.getModifiers())) {
                try {
                    return superclass.getMethod(str, clsArr);
                } catch (NoSuchMethodException unused) {
                    return null;
                }
            }
        }
        return null;
    }

    private static Method b(Class cls, String str, Class[] clsArr) {
        Method method = null;
        while (cls != null) {
            Class<?>[] interfaces = cls.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                if (Modifier.isPublic(interfaces[i].getModifiers())) {
                    try {
                        method = interfaces[i].getDeclaredMethod(str, clsArr);
                    } catch (NoSuchMethodException unused) {
                    }
                    if (method == null && (method = b(interfaces[i], str, clsArr)) == null) {
                    }
                    cls = cls.getSuperclass();
                }
            }
            cls = cls.getSuperclass();
        }
        return method;
    }

    public static Method getMatchingAccessibleMethod(Class cls, String str, Class[] clsArr) {
        Method accessibleMethod;
        try {
            Method method = cls.getMethod(str, clsArr);
            a.a((AccessibleObject) method);
            return method;
        } catch (NoSuchMethodException unused) {
            Method method2 = null;
            Method[] methods = cls.getMethods();
            int length = methods.length;
            for (int i = 0; i < length; i++) {
                if (methods[i].getName().equals(str) && ClassUtils.isAssignable(clsArr, (Class[]) methods[i].getParameterTypes(), true) && (accessibleMethod = getAccessibleMethod(methods[i])) != null && (method2 == null || a.a(accessibleMethod.getParameterTypes(), method2.getParameterTypes(), clsArr) < 0)) {
                    method2 = accessibleMethod;
                }
            }
            if (method2 != null) {
                a.a((AccessibleObject) method2);
            }
            return method2;
        }
    }
}
