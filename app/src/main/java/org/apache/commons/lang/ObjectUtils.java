package org.apache.commons.lang;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang.exception.CloneFailedException;
import org.apache.commons.lang.reflect.MethodUtils;

/* loaded from: classes5.dex */
public class ObjectUtils {
    public static final Null NULL = new Null();

    public static Object defaultIfNull(Object obj, Object obj2) {
        return obj != null ? obj : obj2;
    }

    public static boolean equals(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return obj.equals(obj2);
    }

    public static boolean notEqual(Object obj, Object obj2) {
        return !equals(obj, obj2);
    }

    public static int hashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public static String identityToString(Object obj) {
        if (obj == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        identityToString(stringBuffer, obj);
        return stringBuffer.toString();
    }

    public static void identityToString(StringBuffer stringBuffer, Object obj) {
        if (obj != null) {
            stringBuffer.append(obj.getClass().getName());
            stringBuffer.append('@');
            stringBuffer.append(Integer.toHexString(System.identityHashCode(obj)));
            return;
        }
        throw new NullPointerException("Cannot get the toString of a null identity");
    }

    public static StringBuffer appendIdentityToString(StringBuffer stringBuffer, Object obj) {
        if (obj == null) {
            return null;
        }
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer();
        }
        stringBuffer.append(obj.getClass().getName());
        stringBuffer.append('@');
        stringBuffer.append(Integer.toHexString(System.identityHashCode(obj)));
        return stringBuffer;
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String toString(Object obj, String str) {
        return obj == null ? str : obj.toString();
    }

    public static Object min(Comparable comparable, Comparable comparable2) {
        return compare(comparable, comparable2, true) <= 0 ? comparable : comparable2;
    }

    public static Object max(Comparable comparable, Comparable comparable2) {
        return compare(comparable, comparable2, false) >= 0 ? comparable : comparable2;
    }

    public static int compare(Comparable comparable, Comparable comparable2) {
        return compare(comparable, comparable2, false);
    }

    public static int compare(Comparable comparable, Comparable comparable2, boolean z) {
        if (comparable == comparable2) {
            return 0;
        }
        if (comparable == null) {
            return z ? 1 : -1;
        }
        if (comparable2 == null) {
            return z ? -1 : 1;
        }
        return comparable.compareTo(comparable2);
    }

    public static Object clone(Object obj) {
        if (!(obj instanceof Cloneable)) {
            return null;
        }
        if (obj.getClass().isArray()) {
            Class<?> componentType = obj.getClass().getComponentType();
            if (!componentType.isPrimitive()) {
                return ((Object[]) obj).clone();
            }
            int length = Array.getLength(obj);
            Object newInstance = Array.newInstance(componentType, length);
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    return newInstance;
                }
                Array.set(newInstance, i, Array.get(obj, i));
                length = i;
            }
        } else {
            try {
                return MethodUtils.invokeMethod(obj, "clone", (Object[]) null);
            } catch (IllegalAccessException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cannot clone Cloneable type ");
                stringBuffer.append(obj.getClass().getName());
                throw new CloneFailedException(stringBuffer.toString(), e);
            } catch (NoSuchMethodException e2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Cloneable type ");
                stringBuffer2.append(obj.getClass().getName());
                stringBuffer2.append(" has no clone method");
                throw new CloneFailedException(stringBuffer2.toString(), e2);
            } catch (InvocationTargetException e3) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Exception cloning Cloneable type ");
                stringBuffer3.append(obj.getClass().getName());
                throw new CloneFailedException(stringBuffer3.toString(), e3.getTargetException());
            }
        }
    }

    public static Object cloneIfPossible(Object obj) {
        Object clone = clone(obj);
        return clone == null ? obj : clone;
    }

    /* loaded from: classes5.dex */
    public static class Null implements Serializable {
        private static final long serialVersionUID = 7092611880189329093L;

        Null() {
        }

        private Object readResolve() {
            return ObjectUtils.NULL;
        }
    }
}
