package org.apache.commons.lang3.event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.reflect.MethodUtils;

/* loaded from: classes5.dex */
public class EventUtils {
    public static <L> void addEventListener(Object obj, Class<L> cls, L l) {
        try {
            MethodUtils.invokeMethod(obj, "add" + cls.getSimpleName(), l);
        } catch (IllegalAccessException unused) {
            throw new IllegalArgumentException("Class " + obj.getClass().getName() + " does not have an accessible add" + cls.getSimpleName() + " method which takes a parameter of type " + cls.getName() + ".");
        } catch (NoSuchMethodException unused2) {
            throw new IllegalArgumentException("Class " + obj.getClass().getName() + " does not have a public add" + cls.getSimpleName() + " method which takes a parameter of type " + cls.getName() + ".");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to add listener.", e.getCause());
        }
    }

    public static <L> void bindEventsToMethod(Object obj, String str, Object obj2, Class<L> cls, String... strArr) {
        addEventListener(obj2, cls, cls.cast(Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[]{cls}, new a(obj, str, strArr))));
    }

    /* loaded from: classes5.dex */
    private static class a implements InvocationHandler {
        private final Object a;
        private final String b;
        private final Set<String> c;

        a(Object obj, String str, String[] strArr) {
            this.a = obj;
            this.b = str;
            this.c = new HashSet(Arrays.asList(strArr));
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            if (!this.c.isEmpty() && !this.c.contains(method.getName())) {
                return null;
            }
            if (a(method)) {
                return MethodUtils.invokeMethod(this.a, this.b, objArr);
            }
            return MethodUtils.invokeMethod(this.a, this.b);
        }

        private boolean a(Method method) {
            return MethodUtils.getAccessibleMethod(this.a.getClass(), this.b, method.getParameterTypes()) != null;
        }
    }
}
