package org.apache.commons.lang.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import org.apache.commons.lang.ClassUtils;

/* loaded from: classes5.dex */
public class FieldUtils {
    public static Field getField(Class cls, String str) {
        Field field = getField(cls, str, false);
        a.a((AccessibleObject) field);
        return field;
    }

    public static Field getField(Class cls, String str, boolean z) {
        Field declaredField;
        if (cls == null) {
            throw new IllegalArgumentException("The class must not be null");
        } else if (str != null) {
            for (Class cls2 = cls; cls2 != null; cls2 = cls2.getSuperclass()) {
                try {
                    declaredField = cls2.getDeclaredField(str);
                } catch (NoSuchFieldException unused) {
                }
                if (!Modifier.isPublic(declaredField.getModifiers())) {
                    if (z) {
                        declaredField.setAccessible(true);
                    } else {
                        continue;
                    }
                }
                return declaredField;
            }
            Field field = null;
            for (Class cls3 : ClassUtils.getAllInterfaces(cls)) {
                try {
                    field = cls3.getField(str);
                } catch (NoSuchFieldException unused2) {
                }
                if (field != null) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Reference to field ");
                    stringBuffer.append(str);
                    stringBuffer.append(" is ambiguous relative to ");
                    stringBuffer.append(cls);
                    stringBuffer.append("; a matching field exists on two or more implemented interfaces.");
                    throw new IllegalArgumentException(stringBuffer.toString());
                    break;
                }
            }
            return field;
        } else {
            throw new IllegalArgumentException("The field name must not be null");
        }
    }

    public static Field getDeclaredField(Class cls, String str) {
        return getDeclaredField(cls, str, false);
    }

    public static Field getDeclaredField(Class cls, String str, boolean z) {
        if (cls == null) {
            throw new IllegalArgumentException("The class must not be null");
        } else if (str != null) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                if (!a.a((Member) declaredField)) {
                    if (!z) {
                        return null;
                    }
                    declaredField.setAccessible(true);
                }
                return declaredField;
            } catch (NoSuchFieldException unused) {
                return null;
            }
        } else {
            throw new IllegalArgumentException("The field name must not be null");
        }
    }

    public static Object readStaticField(Field field) throws IllegalAccessException {
        return readStaticField(field, false);
    }

    public static Object readStaticField(Field field, boolean z) throws IllegalAccessException {
        if (field == null) {
            throw new IllegalArgumentException("The field must not be null");
        } else if (Modifier.isStatic(field.getModifiers())) {
            return readField(field, (Object) null, z);
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("The field '");
            stringBuffer.append(field.getName());
            stringBuffer.append("' is not static");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public static Object readStaticField(Class cls, String str) throws IllegalAccessException {
        return readStaticField(cls, str, false);
    }

    public static Object readStaticField(Class cls, String str, boolean z) throws IllegalAccessException {
        Field field = getField(cls, str, z);
        if (field != null) {
            return readStaticField(field, false);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Cannot locate field ");
        stringBuffer.append(str);
        stringBuffer.append(" on ");
        stringBuffer.append(cls);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static Object readDeclaredStaticField(Class cls, String str) throws IllegalAccessException {
        return readDeclaredStaticField(cls, str, false);
    }

    public static Object readDeclaredStaticField(Class cls, String str, boolean z) throws IllegalAccessException {
        Field declaredField = getDeclaredField(cls, str, z);
        if (declaredField != null) {
            return readStaticField(declaredField, false);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Cannot locate declared field ");
        stringBuffer.append(cls.getName());
        stringBuffer.append(".");
        stringBuffer.append(str);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static Object readField(Field field, Object obj) throws IllegalAccessException {
        return readField(field, obj, false);
    }

    public static Object readField(Field field, Object obj, boolean z) throws IllegalAccessException {
        if (field != null) {
            if (!z || field.isAccessible()) {
                a.a((AccessibleObject) field);
            } else {
                field.setAccessible(true);
            }
            return field.get(obj);
        }
        throw new IllegalArgumentException("The field must not be null");
    }

    public static Object readField(Object obj, String str) throws IllegalAccessException {
        return readField(obj, str, false);
    }

    public static Object readField(Object obj, String str, boolean z) throws IllegalAccessException {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            Field field = getField(cls, str, z);
            if (field != null) {
                return readField(field, obj);
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot locate field ");
            stringBuffer.append(str);
            stringBuffer.append(" on ");
            stringBuffer.append(cls);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        throw new IllegalArgumentException("target object must not be null");
    }

    public static Object readDeclaredField(Object obj, String str) throws IllegalAccessException {
        return readDeclaredField(obj, str, false);
    }

    public static Object readDeclaredField(Object obj, String str, boolean z) throws IllegalAccessException {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            Field declaredField = getDeclaredField(cls, str, z);
            if (declaredField != null) {
                return readField(declaredField, obj);
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot locate declared field ");
            stringBuffer.append(cls.getName());
            stringBuffer.append(".");
            stringBuffer.append(str);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        throw new IllegalArgumentException("target object must not be null");
    }

    public static void writeStaticField(Field field, Object obj) throws IllegalAccessException {
        writeStaticField(field, obj, false);
    }

    public static void writeStaticField(Field field, Object obj, boolean z) throws IllegalAccessException {
        if (field == null) {
            throw new IllegalArgumentException("The field must not be null");
        } else if (Modifier.isStatic(field.getModifiers())) {
            writeField(field, (Object) null, obj, z);
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("The field '");
            stringBuffer.append(field.getName());
            stringBuffer.append("' is not static");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public static void writeStaticField(Class cls, String str, Object obj) throws IllegalAccessException {
        writeStaticField(cls, str, obj, false);
    }

    public static void writeStaticField(Class cls, String str, Object obj, boolean z) throws IllegalAccessException {
        Field field = getField(cls, str, z);
        if (field != null) {
            writeStaticField(field, obj);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Cannot locate field ");
        stringBuffer.append(str);
        stringBuffer.append(" on ");
        stringBuffer.append(cls);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static void writeDeclaredStaticField(Class cls, String str, Object obj) throws IllegalAccessException {
        writeDeclaredStaticField(cls, str, obj, false);
    }

    public static void writeDeclaredStaticField(Class cls, String str, Object obj, boolean z) throws IllegalAccessException {
        Field declaredField = getDeclaredField(cls, str, z);
        if (declaredField != null) {
            writeField(declaredField, (Object) null, obj);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Cannot locate declared field ");
        stringBuffer.append(cls.getName());
        stringBuffer.append(".");
        stringBuffer.append(str);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static void writeField(Field field, Object obj, Object obj2) throws IllegalAccessException {
        writeField(field, obj, obj2, false);
    }

    public static void writeField(Field field, Object obj, Object obj2, boolean z) throws IllegalAccessException {
        if (field != null) {
            if (!z || field.isAccessible()) {
                a.a((AccessibleObject) field);
            } else {
                field.setAccessible(true);
            }
            field.set(obj, obj2);
            return;
        }
        throw new IllegalArgumentException("The field must not be null");
    }

    public static void writeField(Object obj, String str, Object obj2) throws IllegalAccessException {
        writeField(obj, str, obj2, false);
    }

    public static void writeField(Object obj, String str, Object obj2, boolean z) throws IllegalAccessException {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            Field field = getField(cls, str, z);
            if (field != null) {
                writeField(field, obj, obj2);
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot locate declared field ");
            stringBuffer.append(cls.getName());
            stringBuffer.append(".");
            stringBuffer.append(str);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        throw new IllegalArgumentException("target object must not be null");
    }

    public static void writeDeclaredField(Object obj, String str, Object obj2) throws IllegalAccessException {
        writeDeclaredField(obj, str, obj2, false);
    }

    public static void writeDeclaredField(Object obj, String str, Object obj2, boolean z) throws IllegalAccessException {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            Field declaredField = getDeclaredField(cls, str, z);
            if (declaredField != null) {
                writeField(declaredField, obj, obj2);
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot locate declared field ");
            stringBuffer.append(cls.getName());
            stringBuffer.append(".");
            stringBuffer.append(str);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        throw new IllegalArgumentException("target object must not be null");
    }
}
