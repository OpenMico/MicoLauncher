package org.apache.commons.lang3.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.ClassUtils;

/* compiled from: MemberUtils.java */
/* loaded from: classes5.dex */
abstract class a {
    private static final Class<?>[] a = {Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE};

    static boolean a(int i) {
        return (i & 7) == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(AccessibleObject accessibleObject) {
        if (accessibleObject == null || accessibleObject.isAccessible()) {
            return false;
        }
        Member member = (Member) accessibleObject;
        if (!accessibleObject.isAccessible() && Modifier.isPublic(member.getModifiers()) && a(member.getDeclaringClass().getModifiers())) {
            try {
                accessibleObject.setAccessible(true);
                return true;
            } catch (SecurityException unused) {
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(Member member) {
        return member != null && Modifier.isPublic(member.getModifiers()) && !member.isSynthetic();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(Constructor<?> constructor, Constructor<?> constructor2, Class<?>[] clsArr) {
        return a(C0381a.b(constructor), C0381a.b(constructor2), clsArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(Method method, Method method2, Class<?>[] clsArr) {
        return a(C0381a.b(method), C0381a.b(method2), clsArr);
    }

    private static int a(C0381a aVar, C0381a aVar2, Class<?>[] clsArr) {
        float a2 = a(clsArr, aVar);
        float a3 = a(clsArr, aVar2);
        if (a2 < a3) {
            return -1;
        }
        return a3 < a2 ? 1 : 0;
    }

    private static float a(Class<?>[] clsArr, C0381a aVar) {
        Class<?>[] a2 = aVar.a();
        boolean b = aVar.b();
        long length = b ? a2.length - 1 : a2.length;
        if (clsArr.length < length) {
            return Float.MAX_VALUE;
        }
        boolean z = false;
        float f = 0.0f;
        for (int i = 0; i < length; i++) {
            f += a(clsArr[i], a2[i]);
        }
        if (!b) {
            return f;
        }
        boolean z2 = clsArr.length < a2.length;
        if (clsArr.length == a2.length && clsArr[clsArr.length - 1].isArray()) {
            z = true;
        }
        Class<?> componentType = a2[a2.length - 1].getComponentType();
        if (z2) {
            return f + a(componentType, Object.class) + 0.001f;
        }
        if (z) {
            return f + a(clsArr[clsArr.length - 1].getComponentType(), componentType) + 0.001f;
        }
        for (int length2 = a2.length - 1; length2 < clsArr.length; length2++) {
            f += a(clsArr[length2], componentType) + 0.001f;
        }
        return f;
    }

    private static float a(Class<?> cls, Class<?> cls2) {
        if (cls2.isPrimitive()) {
            return b(cls, cls2);
        }
        float f = 0.0f;
        while (true) {
            if (cls != null && !cls2.equals(cls)) {
                if (cls2.isInterface() && ClassUtils.isAssignable(cls, cls2)) {
                    f += 0.25f;
                    break;
                }
                f += 1.0f;
                cls = cls.getSuperclass();
            } else {
                break;
            }
        }
        return cls == null ? f + 1.5f : f;
    }

    private static float b(Class<?> cls, Class<?> cls2) {
        float f;
        if (!cls.isPrimitive()) {
            cls = ClassUtils.wrapperToPrimitive(cls);
            f = 0.1f;
        } else {
            f = 0.0f;
        }
        int i = 0;
        while (cls != cls2) {
            Class<?>[] clsArr = a;
            if (i >= clsArr.length) {
                break;
            }
            if (cls == clsArr[i]) {
                f += 0.1f;
                if (i < clsArr.length - 1) {
                    cls = clsArr[i + 1];
                }
            }
            i++;
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(Method method, Class<?>[] clsArr) {
        return a(C0381a.b(method), clsArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(Constructor<?> constructor, Class<?>[] clsArr) {
        return a(C0381a.b(constructor), clsArr);
    }

    private static boolean a(C0381a aVar, Class<?>[] clsArr) {
        Class<?>[] a2 = aVar.a();
        if (!aVar.b()) {
            return ClassUtils.isAssignable(clsArr, a2, true);
        }
        int i = 0;
        while (i < a2.length - 1 && i < clsArr.length) {
            if (!ClassUtils.isAssignable(clsArr[i], a2[i], true)) {
                return false;
            }
            i++;
        }
        Class<?> componentType = a2[a2.length - 1].getComponentType();
        while (i < clsArr.length) {
            if (!ClassUtils.isAssignable(clsArr[i], componentType, true)) {
                return false;
            }
            i++;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MemberUtils.java */
    /* renamed from: org.apache.commons.lang3.reflect.a$a  reason: collision with other inner class name */
    /* loaded from: classes5.dex */
    public static final class C0381a {
        private final Class<?>[] a;
        private final boolean b;

        /* JADX INFO: Access modifiers changed from: private */
        public static C0381a b(Method method) {
            return new C0381a(method);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static C0381a b(Constructor<?> constructor) {
            return new C0381a(constructor);
        }

        private C0381a(Method method) {
            this.a = method.getParameterTypes();
            this.b = method.isVarArgs();
        }

        private C0381a(Constructor<?> constructor) {
            this.a = constructor.getParameterTypes();
            this.b = constructor.isVarArgs();
        }

        public Class<?>[] a() {
            return this.a;
        }

        public boolean b() {
            return this.b;
        }
    }
}
