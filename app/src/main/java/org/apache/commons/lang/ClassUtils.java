package org.apache.commons.lang;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.HttpUrl;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class ClassUtils {
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    public static final char PACKAGE_SEPARATOR_CHAR = '.';
    static Class a;
    static Class b;
    static Class c;
    static Class d;
    static Class e;
    static Class f;
    static Class g;
    static Class h;
    static Class i;
    private static final Map k;
    private static final Map l;
    private static final Map m;
    public static final String PACKAGE_SEPARATOR = String.valueOf('.');
    public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');
    private static final Map j = new HashMap();

    static {
        Map map = j;
        Class cls = Boolean.TYPE;
        Class cls2 = a;
        if (cls2 == null) {
            cls2 = a("java.lang.Boolean");
            a = cls2;
        }
        map.put(cls, cls2);
        Map map2 = j;
        Class cls3 = Byte.TYPE;
        Class cls4 = b;
        if (cls4 == null) {
            cls4 = a("java.lang.Byte");
            b = cls4;
        }
        map2.put(cls3, cls4);
        Map map3 = j;
        Class cls5 = Character.TYPE;
        Class cls6 = c;
        if (cls6 == null) {
            cls6 = a("java.lang.Character");
            c = cls6;
        }
        map3.put(cls5, cls6);
        Map map4 = j;
        Class cls7 = Short.TYPE;
        Class cls8 = d;
        if (cls8 == null) {
            cls8 = a("java.lang.Short");
            d = cls8;
        }
        map4.put(cls7, cls8);
        Map map5 = j;
        Class cls9 = Integer.TYPE;
        Class cls10 = e;
        if (cls10 == null) {
            cls10 = a("java.lang.Integer");
            e = cls10;
        }
        map5.put(cls9, cls10);
        Map map6 = j;
        Class cls11 = Long.TYPE;
        Class cls12 = f;
        if (cls12 == null) {
            cls12 = a("java.lang.Long");
            f = cls12;
        }
        map6.put(cls11, cls12);
        Map map7 = j;
        Class cls13 = Double.TYPE;
        Class cls14 = g;
        if (cls14 == null) {
            cls14 = a("java.lang.Double");
            g = cls14;
        }
        map7.put(cls13, cls14);
        Map map8 = j;
        Class cls15 = Float.TYPE;
        Class cls16 = h;
        if (cls16 == null) {
            cls16 = a("java.lang.Float");
            h = cls16;
        }
        map8.put(cls15, cls16);
        j.put(Void.TYPE, Void.TYPE);
        k = new HashMap();
        for (Class cls17 : j.keySet()) {
            Class cls18 = (Class) j.get(cls17);
            if (!cls17.equals(cls18)) {
                k.put(cls18, cls17);
            }
        }
        l = new HashMap();
        m = new HashMap();
        a("int", "I");
        a("boolean", "Z");
        a("float", HomePageRecordHelper.AREA_F);
        a("long", "J");
        a("short", ExifInterface.LATITUDE_SOUTH);
        a("byte", "B");
        a("double", HomePageRecordHelper.AREA_D);
        a("char", HomePageRecordHelper.AREA_C);
    }

    static Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    private static void a(String str, String str2) {
        l.put(str, str2);
        m.put(str2, str);
    }

    public static String getShortClassName(Object obj, String str) {
        return obj == null ? str : getShortClassName(obj.getClass());
    }

    public static String getShortClassName(Class cls) {
        return cls == null ? "" : getShortClassName(cls.getName());
    }

    public static String getShortClassName(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        StrBuilder strBuilder = new StrBuilder();
        int i2 = 0;
        if (str.startsWith("[")) {
            while (str.charAt(0) == '[') {
                str = str.substring(1);
                strBuilder.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
            if (str.charAt(0) == 'L' && str.charAt(str.length() - 1) == ';') {
                str = str.substring(1, str.length() - 1);
            }
        }
        if (m.containsKey(str)) {
            str = (String) m.get(str);
        }
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf != -1) {
            i2 = lastIndexOf + 1;
        }
        int indexOf = str.indexOf(36, i2);
        String substring = str.substring(lastIndexOf + 1);
        if (indexOf != -1) {
            substring = substring.replace('$', '.');
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(substring);
        stringBuffer.append(strBuilder);
        return stringBuffer.toString();
    }

    public static String getPackageName(Object obj, String str) {
        return obj == null ? str : getPackageName(obj.getClass());
    }

    public static String getPackageName(Class cls) {
        return cls == null ? "" : getPackageName(cls.getName());
    }

    public static String getPackageName(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        while (str.charAt(0) == '[') {
            str = str.substring(1);
        }
        if (str.charAt(0) == 'L' && str.charAt(str.length() - 1) == ';') {
            str = str.substring(1);
        }
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf == -1 ? "" : str.substring(0, lastIndexOf);
    }

    public static List getAllSuperclasses(Class cls) {
        if (cls == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Class superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            arrayList.add(superclass);
        }
        return arrayList;
    }

    public static List getAllInterfaces(Class cls) {
        if (cls == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        a(cls, arrayList);
        return arrayList;
    }

    private static void a(Class cls, List list) {
        while (cls != null) {
            Class<?>[] interfaces = cls.getInterfaces();
            for (int i2 = 0; i2 < interfaces.length; i2++) {
                if (!list.contains(interfaces[i2])) {
                    list.add(interfaces[i2]);
                    a(interfaces[i2], list);
                }
            }
            cls = cls.getSuperclass();
        }
    }

    public static List convertClassNamesToClasses(List list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(Class.forName((String) it.next()));
            } catch (Exception unused) {
                arrayList.add(null);
            }
        }
        return arrayList;
    }

    public static List convertClassesToClassNames(List list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Class cls = (Class) it.next();
            if (cls == null) {
                arrayList.add(null);
            } else {
                arrayList.add(cls.getName());
            }
        }
        return arrayList;
    }

    public static boolean isAssignable(Class[] clsArr, Class[] clsArr2) {
        return isAssignable(clsArr, clsArr2, false);
    }

    public static boolean isAssignable(Class[] clsArr, Class[] clsArr2, boolean z) {
        if (!ArrayUtils.isSameLength(clsArr, clsArr2)) {
            return false;
        }
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (clsArr2 == null) {
            clsArr2 = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        for (int i2 = 0; i2 < clsArr.length; i2++) {
            if (!isAssignable(clsArr[i2], clsArr2[i2], z)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAssignable(Class cls, Class cls2) {
        return isAssignable(cls, cls2, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isAssignable(Class cls, Class cls2, boolean z) {
        if (cls2 == 0) {
            return false;
        }
        if (cls == null) {
            return !cls2.isPrimitive();
        }
        if (z) {
            if (cls.isPrimitive() && !cls2.isPrimitive() && (cls = primitiveToWrapper(cls)) == null) {
                return false;
            }
            if (cls2.isPrimitive() && !cls.isPrimitive() && (cls = wrapperToPrimitive(cls)) == null) {
                return false;
            }
        }
        if (cls.equals(cls2)) {
            return true;
        }
        if (!cls.isPrimitive()) {
            return cls2.isAssignableFrom(cls);
        }
        if (!cls2.isPrimitive()) {
            return false;
        }
        if (Integer.TYPE.equals(cls)) {
            return Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
        }
        if (Long.TYPE.equals(cls)) {
            return Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
        }
        if (Boolean.TYPE.equals(cls) || Double.TYPE.equals(cls)) {
            return false;
        }
        if (Float.TYPE.equals(cls)) {
            return Double.TYPE.equals(cls2);
        }
        if (Character.TYPE.equals(cls)) {
            return Integer.TYPE.equals(cls2) || Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
        }
        if (Short.TYPE.equals(cls)) {
            return Integer.TYPE.equals(cls2) || Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
        }
        if (Byte.TYPE.equals(cls)) {
            return Short.TYPE.equals(cls2) || Integer.TYPE.equals(cls2) || Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
        }
        return false;
    }

    public static Class primitiveToWrapper(Class cls) {
        return (cls == null || !cls.isPrimitive()) ? cls : (Class) j.get(cls);
    }

    public static Class[] primitivesToWrappers(Class[] clsArr) {
        if (clsArr == null) {
            return null;
        }
        if (clsArr.length == 0) {
            return clsArr;
        }
        Class[] clsArr2 = new Class[clsArr.length];
        for (int i2 = 0; i2 < clsArr.length; i2++) {
            clsArr2[i2] = primitiveToWrapper(clsArr[i2]);
        }
        return clsArr2;
    }

    public static Class wrapperToPrimitive(Class cls) {
        return (Class) k.get(cls);
    }

    public static Class[] wrappersToPrimitives(Class[] clsArr) {
        if (clsArr == null) {
            return null;
        }
        if (clsArr.length == 0) {
            return clsArr;
        }
        Class[] clsArr2 = new Class[clsArr.length];
        for (int i2 = 0; i2 < clsArr.length; i2++) {
            clsArr2[i2] = wrapperToPrimitive(clsArr[i2]);
        }
        return clsArr2;
    }

    public static boolean isInnerClass(Class cls) {
        return cls != null && cls.getName().indexOf(36) >= 0;
    }

    public static Class getClass(ClassLoader classLoader, String str, boolean z) throws ClassNotFoundException {
        try {
            if (!l.containsKey(str)) {
                return Class.forName(b(str), z, classLoader);
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[");
            stringBuffer.append(l.get(str));
            return Class.forName(stringBuffer.toString(), z, classLoader).getComponentType();
        } catch (ClassNotFoundException e2) {
            int lastIndexOf = str.lastIndexOf(46);
            if (lastIndexOf != -1) {
                try {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append(str.substring(0, lastIndexOf));
                    stringBuffer2.append('$');
                    stringBuffer2.append(str.substring(lastIndexOf + 1));
                    return getClass(classLoader, stringBuffer2.toString(), z);
                } catch (ClassNotFoundException unused) {
                    throw e2;
                }
            }
            throw e2;
        }
    }

    public static Class getClass(ClassLoader classLoader, String str) throws ClassNotFoundException {
        return getClass(classLoader, str, true);
    }

    public static Class getClass(String str) throws ClassNotFoundException {
        return getClass(str, true);
    }

    public static Class getClass(String str, boolean z) throws ClassNotFoundException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader == null) {
            Class cls = i;
            if (cls == null) {
                cls = a("org.apache.commons.lang.ClassUtils");
                i = cls;
            }
            contextClassLoader = cls.getClassLoader();
        }
        return getClass(contextClassLoader, str, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.reflect.Method getPublicMethod(java.lang.Class r2, java.lang.String r3, java.lang.Class[] r4) throws java.lang.SecurityException, java.lang.NoSuchMethodException {
        /*
            java.lang.reflect.Method r0 = r2.getMethod(r3, r4)
            java.lang.Class r1 = r0.getDeclaringClass()
            int r1 = r1.getModifiers()
            boolean r1 = java.lang.reflect.Modifier.isPublic(r1)
            if (r1 == 0) goto L_0x0013
            return r0
        L_0x0013:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r1 = getAllInterfaces(r2)
            r0.addAll(r1)
            java.util.List r2 = getAllSuperclasses(r2)
            r0.addAll(r2)
            java.util.Iterator r2 = r0.iterator()
        L_0x002a:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0054
            java.lang.Object r0 = r2.next()
            java.lang.Class r0 = (java.lang.Class) r0
            int r1 = r0.getModifiers()
            boolean r1 = java.lang.reflect.Modifier.isPublic(r1)
            if (r1 != 0) goto L_0x0041
            goto L_0x002a
        L_0x0041:
            java.lang.reflect.Method r0 = r0.getMethod(r3, r4)     // Catch: NoSuchMethodException -> 0x002a
            java.lang.Class r1 = r0.getDeclaringClass()
            int r1 = r1.getModifiers()
            boolean r1 = java.lang.reflect.Modifier.isPublic(r1)
            if (r1 == 0) goto L_0x002a
            return r0
        L_0x0054:
            java.lang.NoSuchMethodException r2 = new java.lang.NoSuchMethodException
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "Can't find a public method for "
            r0.append(r1)
            r0.append(r3)
            java.lang.String r3 = " "
            r0.append(r3)
            java.lang.String r3 = org.apache.commons.lang.ArrayUtils.toString(r4)
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.ClassUtils.getPublicMethod(java.lang.Class, java.lang.String, java.lang.Class[]):java.lang.reflect.Method");
    }

    private static String b(String str) {
        String deleteWhitespace = StringUtils.deleteWhitespace(str);
        if (deleteWhitespace == null) {
            throw new NullArgumentException("className");
        } else if (!deleteWhitespace.endsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI)) {
            return deleteWhitespace;
        } else {
            StrBuilder strBuilder = new StrBuilder();
            while (deleteWhitespace.endsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI)) {
                deleteWhitespace = deleteWhitespace.substring(0, deleteWhitespace.length() - 2);
                strBuilder.append("[");
            }
            String str2 = (String) l.get(deleteWhitespace);
            if (str2 != null) {
                strBuilder.append(str2);
            } else {
                strBuilder.append("L").append(deleteWhitespace).append(";");
            }
            return strBuilder.toString();
        }
    }

    public static Class[] toClass(Object[] objArr) {
        if (objArr == null) {
            return null;
        }
        if (objArr.length == 0) {
            return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        Class[] clsArr = new Class[objArr.length];
        for (int i2 = 0; i2 < objArr.length; i2++) {
            clsArr[i2] = objArr[i2] == null ? null : objArr[i2].getClass();
        }
        return clsArr;
    }

    public static String getShortCanonicalName(Object obj, String str) {
        return obj == null ? str : getShortCanonicalName(obj.getClass().getName());
    }

    public static String getShortCanonicalName(Class cls) {
        return cls == null ? "" : getShortCanonicalName(cls.getName());
    }

    public static String getShortCanonicalName(String str) {
        return getShortClassName(c(str));
    }

    public static String getPackageCanonicalName(Object obj, String str) {
        return obj == null ? str : getPackageCanonicalName(obj.getClass().getName());
    }

    public static String getPackageCanonicalName(Class cls) {
        return cls == null ? "" : getPackageCanonicalName(cls.getName());
    }

    public static String getPackageCanonicalName(String str) {
        return getPackageName(c(str));
    }

    private static String c(String str) {
        String deleteWhitespace = StringUtils.deleteWhitespace(str);
        if (deleteWhitespace == null) {
            return null;
        }
        int i2 = 0;
        while (deleteWhitespace.startsWith("[")) {
            i2++;
            deleteWhitespace = deleteWhitespace.substring(1);
        }
        if (i2 < 1) {
            return deleteWhitespace;
        }
        if (deleteWhitespace.startsWith("L")) {
            deleteWhitespace = deleteWhitespace.substring(1, deleteWhitespace.endsWith(";") ? deleteWhitespace.length() - 1 : deleteWhitespace.length());
        } else if (deleteWhitespace.length() > 0) {
            deleteWhitespace = (String) m.get(deleteWhitespace.substring(0, 1));
        }
        StrBuilder strBuilder = new StrBuilder(deleteWhitespace);
        for (int i3 = 0; i3 < i2; i3++) {
            strBuilder.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        }
        return strBuilder.toString();
    }
}
