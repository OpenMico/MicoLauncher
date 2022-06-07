package com.blankj.utilcode.util;

import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class ReflectUtils {
    private final Class<?> a;
    private final Object b;

    private ReflectUtils(Class<?> cls) {
        this(cls, cls);
    }

    private ReflectUtils(Class<?> cls, Object obj) {
        this.a = cls;
        this.b = obj;
    }

    public static ReflectUtils reflect(String str) throws ReflectException {
        return reflect(b(str));
    }

    public static ReflectUtils reflect(String str, ClassLoader classLoader) throws ReflectException {
        return reflect(a(str, classLoader));
    }

    public static ReflectUtils reflect(Class<?> cls) throws ReflectException {
        return new ReflectUtils(cls);
    }

    public static ReflectUtils reflect(Object obj) throws ReflectException {
        return new ReflectUtils(obj == null ? Object.class : obj.getClass(), obj);
    }

    private static Class<?> b(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    private static Class<?> a(String str, ClassLoader classLoader) {
        try {
            return Class.forName(str, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    public ReflectUtils newInstance() {
        return newInstance(new Object[0]);
    }

    public ReflectUtils newInstance(Object... objArr) {
        Class<?>[] a2 = a(objArr);
        try {
            return a(a().getDeclaredConstructor(a2), objArr);
        } catch (NoSuchMethodException e) {
            ArrayList arrayList = new ArrayList();
            Constructor<?>[] declaredConstructors = a().getDeclaredConstructors();
            for (Constructor<?> constructor : declaredConstructors) {
                if (a(constructor.getParameterTypes(), a2)) {
                    arrayList.add(constructor);
                }
            }
            if (!arrayList.isEmpty()) {
                a((List<Constructor<?>>) arrayList);
                return a(arrayList.get(0), objArr);
            }
            throw new ReflectException(e);
        }
    }

    private Class<?>[] a(Object... objArr) {
        if (objArr == null) {
            return new Class[0];
        }
        Class<?>[] clsArr = new Class[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            clsArr[i] = obj == null ? a.class : obj.getClass();
        }
        return clsArr;
    }

    private void a(List<Constructor<?>> list) {
        Collections.sort(list, new Comparator<Constructor<?>>() { // from class: com.blankj.utilcode.util.ReflectUtils.1
            /* renamed from: a */
            public int compare(Constructor<?> constructor, Constructor<?> constructor2) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Class<?>[] parameterTypes2 = constructor2.getParameterTypes();
                int length = parameterTypes.length;
                for (int i = 0; i < length; i++) {
                    if (!parameterTypes[i].equals(parameterTypes2[i])) {
                        return ReflectUtils.this.a(parameterTypes[i]).isAssignableFrom(ReflectUtils.this.a(parameterTypes2[i])) ? 1 : -1;
                    }
                }
                return 0;
            }
        });
    }

    private ReflectUtils a(Constructor<?> constructor, Object... objArr) {
        try {
            return new ReflectUtils(constructor.getDeclaringClass(), ((Constructor) a((ReflectUtils) constructor)).newInstance(objArr));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    public ReflectUtils field(String str) {
        try {
            Field c = c(str);
            return new ReflectUtils(c.getType(), c.get(this.b));
        } catch (IllegalAccessException e) {
            throw new ReflectException(e);
        }
    }

    public ReflectUtils field(String str, Object obj) {
        try {
            c(str).set(this.b, a(obj));
            return this;
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private Field c(String str) throws IllegalAccessException {
        Field d = d(str);
        if ((d.getModifiers() & 16) == 16) {
            try {
                Field declaredField = Field.class.getDeclaredField("modifiers");
                declaredField.setAccessible(true);
                declaredField.setInt(d, d.getModifiers() & (-17));
            } catch (NoSuchFieldException unused) {
                d.setAccessible(true);
            }
        }
        return d;
    }

    private Field d(String str) {
        Class<?> a2 = a();
        try {
            return (Field) a((ReflectUtils) a2.getField(str));
        } catch (NoSuchFieldException e) {
            do {
                try {
                    return (Field) a((ReflectUtils) a2.getDeclaredField(str));
                } catch (NoSuchFieldException unused) {
                    a2 = a2.getSuperclass();
                    if (a2 != null) {
                        throw new ReflectException(e);
                    }
                }
            } while (a2 != null);
            throw new ReflectException(e);
        }
    }

    private Object a(Object obj) {
        return obj instanceof ReflectUtils ? ((ReflectUtils) obj).get() : obj;
    }

    public ReflectUtils method(String str) throws ReflectException {
        return method(str, new Object[0]);
    }

    public ReflectUtils method(String str, Object... objArr) throws ReflectException {
        Class<?>[] a2 = a(objArr);
        try {
            try {
                return a(a(str, a2), this.b, objArr);
            } catch (NoSuchMethodException e) {
                throw new ReflectException(e);
            }
        } catch (NoSuchMethodException unused) {
            return a(b(str, a2), this.b, objArr);
        }
    }

    private ReflectUtils a(Method method, Object obj, Object... objArr) {
        try {
            a((ReflectUtils) method);
            if (method.getReturnType() != Void.TYPE) {
                return reflect(method.invoke(obj, objArr));
            }
            method.invoke(obj, objArr);
            return reflect(obj);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private Method a(String str, Class<?>[] clsArr) throws NoSuchMethodException {
        Class<?> a2 = a();
        try {
            return a2.getMethod(str, clsArr);
        } catch (NoSuchMethodException unused) {
            do {
                try {
                    return a2.getDeclaredMethod(str, clsArr);
                } catch (NoSuchMethodException unused2) {
                    a2 = a2.getSuperclass();
                    if (a2 == null) {
                        throw new NoSuchMethodException();
                    }
                }
            } while (a2 == null);
            throw new NoSuchMethodException();
        }
    }

    private Method b(String str, Class<?>[] clsArr) throws NoSuchMethodException {
        Class<?> a2 = a();
        ArrayList arrayList = new ArrayList();
        Method[] methods = a2.getMethods();
        for (Method method : methods) {
            if (a(method, str, clsArr)) {
                arrayList.add(method);
            }
        }
        if (!arrayList.isEmpty()) {
            b(arrayList);
            return arrayList.get(0);
        }
        do {
            Method[] declaredMethods = a2.getDeclaredMethods();
            for (Method method2 : declaredMethods) {
                if (a(method2, str, clsArr)) {
                    arrayList.add(method2);
                }
            }
            if (!arrayList.isEmpty()) {
                b(arrayList);
                return arrayList.get(0);
            }
            a2 = a2.getSuperclass();
        } while (a2 != null);
        throw new NoSuchMethodException("No similar method " + str + " with params " + Arrays.toString(clsArr) + " could be found on type " + a() + ".");
    }

    private void b(List<Method> list) {
        Collections.sort(list, new Comparator<Method>() { // from class: com.blankj.utilcode.util.ReflectUtils.2
            /* renamed from: a */
            public int compare(Method method, Method method2) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?>[] parameterTypes2 = method2.getParameterTypes();
                int length = parameterTypes.length;
                for (int i = 0; i < length; i++) {
                    if (!parameterTypes[i].equals(parameterTypes2[i])) {
                        return ReflectUtils.this.a(parameterTypes[i]).isAssignableFrom(ReflectUtils.this.a(parameterTypes2[i])) ? 1 : -1;
                    }
                }
                return 0;
            }
        });
    }

    private boolean a(Method method, String str, Class<?>[] clsArr) {
        return method.getName().equals(str) && a(method.getParameterTypes(), clsArr);
    }

    private boolean a(Class<?>[] clsArr, Class<?>[] clsArr2) {
        if (clsArr.length != clsArr2.length) {
            return false;
        }
        for (int i = 0; i < clsArr2.length; i++) {
            if (!(clsArr2[i] == a.class || a(clsArr[i]).isAssignableFrom(a(clsArr2[i])))) {
                return false;
            }
        }
        return true;
    }

    private <T extends AccessibleObject> T a(T t) {
        if (t == null) {
            return null;
        }
        if (t instanceof Member) {
            Member member = (Member) t;
            if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return t;
            }
        }
        if (!t.isAccessible()) {
            t.setAccessible(true);
        }
        return t;
    }

    public <P> P proxy(Class<P> cls) {
        final boolean z = this.b instanceof Map;
        return (P) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() { // from class: com.blankj.utilcode.util.ReflectUtils.3
            @Override // java.lang.reflect.InvocationHandler
            public Object invoke(Object obj, Method method, Object[] objArr) {
                String name = method.getName();
                try {
                    return ReflectUtils.reflect(ReflectUtils.this.b).method(name, objArr).get();
                } catch (ReflectException e) {
                    if (z) {
                        Map map = (Map) ReflectUtils.this.b;
                        int length = objArr == null ? 0 : objArr.length;
                        if (length == 0 && name.startsWith(BluetoothConstants.GET)) {
                            return map.get(ReflectUtils.e(name.substring(3)));
                        }
                        if (length == 0 && name.startsWith(ai.ae)) {
                            return map.get(ReflectUtils.e(name.substring(2)));
                        }
                        if (length == 1 && name.startsWith(BluetoothConstants.SET)) {
                            map.put(ReflectUtils.e(name.substring(3)), objArr[0]);
                            return null;
                        }
                    }
                    throw e;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String e(String str) {
        int length = str.length();
        if (length == 0) {
            return "";
        }
        if (length == 1) {
            return str.toLowerCase();
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private Class<?> a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Class<?> a(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        if (cls.isPrimitive()) {
            if (Boolean.TYPE == cls) {
                return Boolean.class;
            }
            if (Integer.TYPE == cls) {
                return Integer.class;
            }
            if (Long.TYPE == cls) {
                return Long.class;
            }
            if (Short.TYPE == cls) {
                return Short.class;
            }
            if (Byte.TYPE == cls) {
                return Byte.class;
            }
            if (Double.TYPE == cls) {
                return Double.class;
            }
            if (Float.TYPE == cls) {
                return Float.class;
            }
            if (Character.TYPE == cls) {
                return Character.class;
            }
            if (Void.TYPE == cls) {
                return Void.class;
            }
        }
        return cls;
    }

    public <T> T get() {
        return (T) this.b;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ReflectUtils) && this.b.equals(((ReflectUtils) obj).get());
    }

    public String toString() {
        return this.b.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        private a() {
        }
    }

    /* loaded from: classes.dex */
    public static class ReflectException extends RuntimeException {
        private static final long serialVersionUID = 858774075258496016L;

        public ReflectException(String str) {
            super(str);
        }

        public ReflectException(String str, Throwable th) {
            super(str, th);
        }

        public ReflectException(Throwable th) {
            super(th);
        }
    }
}
