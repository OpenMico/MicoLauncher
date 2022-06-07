package com.alibaba.fastjson.util;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.asm.ClassReader;
import com.alibaba.fastjson.asm.TypeCollector;
import com.fasterxml.jackson.core.JsonPointer;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class ASMUtils {
    public static final String JAVA_VM_NAME = System.getProperty("java.vm.name");
    public static final boolean IS_ANDROID = isAndroid(JAVA_VM_NAME);

    public static boolean isAndroid(String str) {
        if (str == null) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        return lowerCase.contains("dalvik") || lowerCase.contains("lemur");
    }

    public static String desc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        StringBuilder sb = new StringBuilder((parameterTypes.length + 1) << 4);
        sb.append('(');
        for (Class<?> cls : parameterTypes) {
            sb.append(desc(cls));
        }
        sb.append(')');
        sb.append(desc(method.getReturnType()));
        return sb.toString();
    }

    public static String desc(Class<?> cls) {
        if (cls.isPrimitive()) {
            return getPrimitiveLetter(cls);
        }
        if (cls.isArray()) {
            return "[" + desc(cls.getComponentType());
        }
        return "L" + type(cls) + ";";
    }

    public static String type(Class<?> cls) {
        if (cls.isArray()) {
            return "[" + desc(cls.getComponentType());
        } else if (!cls.isPrimitive()) {
            return cls.getName().replace('.', JsonPointer.SEPARATOR);
        } else {
            return getPrimitiveLetter(cls);
        }
    }

    public static String getPrimitiveLetter(Class<?> cls) {
        if (Integer.TYPE == cls) {
            return "I";
        }
        if (Void.TYPE == cls) {
            return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
        }
        if (Boolean.TYPE == cls) {
            return "Z";
        }
        if (Character.TYPE == cls) {
            return HomePageRecordHelper.AREA_C;
        }
        if (Byte.TYPE == cls) {
            return "B";
        }
        if (Short.TYPE == cls) {
            return ExifInterface.LATITUDE_SOUTH;
        }
        if (Float.TYPE == cls) {
            return HomePageRecordHelper.AREA_F;
        }
        if (Long.TYPE == cls) {
            return "J";
        }
        if (Double.TYPE == cls) {
            return HomePageRecordHelper.AREA_D;
        }
        throw new IllegalStateException("Type: " + cls.getCanonicalName() + " is not a primitive type");
    }

    public static Type getMethodType(Class<?> cls, String str) {
        try {
            return cls.getMethod(str, new Class[0]).getGenericReturnType();
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean checkName(String str) {
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt < 1 || charAt > 127 || charAt == '.') {
                return false;
            }
        }
        return true;
    }

    public static String[] lookupParameterNames(AccessibleObject accessibleObject) {
        Annotation[][] annotationArr;
        Class<?> cls;
        String str;
        Class<?>[] clsArr;
        String name;
        if (IS_ANDROID) {
            return new String[0];
        }
        if (accessibleObject instanceof Method) {
            Method method = (Method) accessibleObject;
            clsArr = method.getParameterTypes();
            str = method.getName();
            cls = method.getDeclaringClass();
            annotationArr = TypeUtils.getParameterAnnotations(method);
        } else {
            Constructor constructor = (Constructor) accessibleObject;
            clsArr = constructor.getParameterTypes();
            cls = constructor.getDeclaringClass();
            str = "<init>";
            annotationArr = TypeUtils.getParameterAnnotations(constructor);
        }
        if (clsArr.length == 0) {
            return new String[0];
        }
        ClassLoader classLoader = cls.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        String name2 = cls.getName();
        InputStream resourceAsStream = classLoader.getResourceAsStream(name2.replace('.', JsonPointer.SEPARATOR) + ".class");
        try {
            if (resourceAsStream == null) {
                return new String[0];
            }
            ClassReader classReader = new ClassReader(resourceAsStream, false);
            TypeCollector typeCollector = new TypeCollector(str, clsArr);
            classReader.accept(typeCollector);
            String[] parameterNamesForMethod = typeCollector.getParameterNamesForMethod();
            for (int i = 0; i < parameterNamesForMethod.length; i++) {
                Annotation[] annotationArr2 = annotationArr[i];
                if (annotationArr2 != null) {
                    for (int i2 = 0; i2 < annotationArr2.length; i2++) {
                        if ((annotationArr2[i2] instanceof JSONField) && (name = ((JSONField) annotationArr2[i2]).name()) != null && name.length() > 0) {
                            parameterNamesForMethod[i] = name;
                        }
                    }
                }
            }
            return parameterNamesForMethod;
        } catch (IOException unused) {
            return new String[0];
        } finally {
            IOUtils.close(resourceAsStream);
        }
    }
}
