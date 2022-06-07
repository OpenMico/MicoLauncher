package org.eclipse.jetty.util;

import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class IntrospectionUtil {
    public static boolean isJavaBeanCompliantSetter(Method method) {
        return method != null && method.getReturnType() == Void.TYPE && method.getName().startsWith(BluetoothConstants.SET) && method.getParameterTypes().length == 1;
    }

    public static Method findMethod(Class<?> cls, String str, Class<?>[] clsArr, boolean z, boolean z2) throws NoSuchMethodException {
        if (cls == null) {
            throw new NoSuchMethodException("No class");
        } else if (str == null || str.trim().equals("")) {
            throw new NoSuchMethodException("No method name");
        } else {
            Method method = null;
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length && method == null; i++) {
                if (declaredMethods[i].getName().equals(str)) {
                    if (checkParams(declaredMethods[i].getParameterTypes(), clsArr == null ? new Class[0] : clsArr, z2)) {
                        method = declaredMethods[i];
                    }
                }
            }
            if (method != null) {
                return method;
            }
            if (z) {
                return findInheritedMethod(cls.getPackage(), cls.getSuperclass(), str, clsArr, z2);
            }
            throw new NoSuchMethodException("No such method " + str + " on class " + cls.getName());
        }
    }

    public static Field findField(Class<?> cls, String str, Class<?> cls2, boolean z, boolean z2) throws NoSuchFieldException {
        if (cls == null) {
            throw new NoSuchFieldException("No class");
        } else if (str != null) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                if (z2) {
                    if (declaredField.getType().equals(cls2)) {
                        return declaredField;
                    }
                } else if (declaredField.getType().isAssignableFrom(cls2)) {
                    return declaredField;
                }
                if (z) {
                    return findInheritedField(cls.getPackage(), cls.getSuperclass(), str, cls2, z2);
                }
                throw new NoSuchFieldException("No field with name " + str + " in class " + cls.getName() + " of type " + cls2);
            } catch (NoSuchFieldException unused) {
                return findInheritedField(cls.getPackage(), cls.getSuperclass(), str, cls2, z2);
            }
        } else {
            throw new NoSuchFieldException("No field name");
        }
    }

    public static boolean isInheritable(Package r4, Member member) {
        if (r4 == null || member == null) {
            return false;
        }
        int modifiers = member.getModifiers();
        if (!Modifier.isPublic(modifiers) && !Modifier.isProtected(modifiers)) {
            return !Modifier.isPrivate(modifiers) && r4.equals(member.getDeclaringClass().getPackage());
        }
        return true;
    }

    public static boolean checkParams(Class<?>[] clsArr, Class<?>[] clsArr2, boolean z) {
        int i;
        if (clsArr == null) {
            return clsArr2 == null;
        }
        if (clsArr2 == null || clsArr.length != clsArr2.length) {
            return false;
        }
        if (clsArr.length == 0) {
            return true;
        }
        if (z) {
            i = 0;
            while (i < clsArr.length && clsArr[i].equals(clsArr2[i])) {
                i++;
            }
        } else {
            i = 0;
            while (i < clsArr.length && clsArr[i].isAssignableFrom(clsArr2[i])) {
                i++;
            }
        }
        return i == clsArr.length;
    }

    public static boolean isSameSignature(Method method, Method method2) {
        if (method == null || method2 == null) {
            return false;
        }
        return method.getName().equals(method2.getName()) && Arrays.asList(method.getParameterTypes()).containsAll(Arrays.asList(method2.getParameterTypes()));
    }

    public static boolean isTypeCompatible(Class<?> cls, Class<?> cls2, boolean z) {
        if (cls == null) {
            return cls2 == null;
        }
        if (cls2 == null) {
            return false;
        }
        if (z) {
            return cls.equals(cls2);
        }
        return cls.isAssignableFrom(cls2);
    }

    public static boolean containsSameMethodSignature(Method method, Class<?> cls, boolean z) {
        if (z && !cls.getPackage().equals(method.getDeclaringClass().getPackage())) {
            return false;
        }
        Method[] declaredMethods = cls.getDeclaredMethods();
        boolean z2 = false;
        for (int i = 0; i < declaredMethods.length && !z2; i++) {
            if (isSameSignature(method, declaredMethods[i])) {
                z2 = true;
            }
        }
        return z2;
    }

    public static boolean containsSameFieldName(Field field, Class<?> cls, boolean z) {
        if (z && !cls.getPackage().equals(field.getDeclaringClass().getPackage())) {
            return false;
        }
        Field[] declaredFields = cls.getDeclaredFields();
        boolean z2 = false;
        for (int i = 0; i < declaredFields.length && !z2; i++) {
            if (declaredFields[i].getName().equals(field.getName())) {
                z2 = true;
            }
        }
        return z2;
    }

    protected static Method findInheritedMethod(Package r4, Class<?> cls, String str, Class<?>[] clsArr, boolean z) throws NoSuchMethodException {
        if (cls == null) {
            throw new NoSuchMethodException("No class");
        } else if (str != null) {
            Method method = null;
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length && method == null; i++) {
                if (declaredMethods[i].getName().equals(str) && isInheritable(r4, declaredMethods[i]) && checkParams(declaredMethods[i].getParameterTypes(), clsArr, z)) {
                    method = declaredMethods[i];
                }
            }
            return method != null ? method : findInheritedMethod(cls.getPackage(), cls.getSuperclass(), str, clsArr, z);
        } else {
            throw new NoSuchMethodException("No method name");
        }
    }

    protected static Field findInheritedField(Package r1, Class<?> cls, String str, Class<?> cls2, boolean z) throws NoSuchFieldException {
        if (cls == null) {
            throw new NoSuchFieldException("No class");
        } else if (str != null) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                return (!isInheritable(r1, declaredField) || !isTypeCompatible(cls2, declaredField.getType(), z)) ? findInheritedField(cls.getPackage(), cls.getSuperclass(), str, cls2, z) : declaredField;
            } catch (NoSuchFieldException unused) {
                return findInheritedField(cls.getPackage(), cls.getSuperclass(), str, cls2, z);
            }
        } else {
            throw new NoSuchFieldException("No field name");
        }
    }
}
