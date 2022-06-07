package org.apache.commons.lang.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

/* loaded from: classes5.dex */
public class ExceptionUtils {
    static Class a;
    private static final Object b = new Object();
    private static String[] c = {"getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable"};
    private static final Method d;
    private static final Method e;

    static {
        Method method;
        Class cls;
        Class<?> cls2;
        Class cls3;
        Method method2 = null;
        try {
            if (a == null) {
                cls3 = b("java.lang.Throwable");
                a = cls3;
            } else {
                cls3 = a;
            }
            method = cls3.getMethod("getCause", null);
        } catch (Exception unused) {
            method = null;
        }
        d = method;
        try {
            if (a == null) {
                cls = b("java.lang.Throwable");
                a = cls;
            } else {
                cls = a;
            }
            Class<?>[] clsArr = new Class[1];
            if (a == null) {
                cls2 = b("java.lang.Throwable");
                a = cls2;
            } else {
                cls2 = a;
            }
            clsArr[0] = cls2;
            method2 = cls.getMethod("initCause", clsArr);
        } catch (Exception unused2) {
        }
        e = method2;
    }

    static Class b(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public static void addCauseMethodName(String str) {
        if (StringUtils.isNotEmpty(str) && !isCauseMethodName(str)) {
            ArrayList a2 = a();
            if (a2.add(str)) {
                synchronized (b) {
                    c = a(a2);
                }
            }
        }
    }

    public static void removeCauseMethodName(String str) {
        if (StringUtils.isNotEmpty(str)) {
            ArrayList a2 = a();
            if (a2.remove(str)) {
                synchronized (b) {
                    c = a(a2);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001e A[Catch: IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x0033, TryCatch #1 {IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x0033, blocks: (B:8:0x0012, B:10:0x001e, B:11:0x0027, B:12:0x0029), top: B:21:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0027 A[Catch: IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x0033, TryCatch #1 {IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x0033, blocks: (B:8:0x0012, B:10:0x001e, B:11:0x0027, B:12:0x0029), top: B:21:0x0012 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean setCause(java.lang.Throwable r7, java.lang.Throwable r8) {
        /*
            if (r7 == 0) goto L_0x0034
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r8
            java.lang.reflect.Method r8 = org.apache.commons.lang.exception.ExceptionUtils.e
            if (r8 == 0) goto L_0x0011
            r8.invoke(r7, r1)     // Catch: IllegalAccessException | InvocationTargetException -> 0x0011
            r8 = r0
            goto L_0x0012
        L_0x0011:
            r8 = r2
        L_0x0012:
            java.lang.Class r3 = r7.getClass()     // Catch: NoSuchMethodException | IllegalAccessException | InvocationTargetException -> 0x0033
            java.lang.String r4 = "setCause"
            java.lang.Class[] r5 = new java.lang.Class[r0]     // Catch: NoSuchMethodException | IllegalAccessException | InvocationTargetException -> 0x0033
            java.lang.Class r6 = org.apache.commons.lang.exception.ExceptionUtils.a     // Catch: NoSuchMethodException | IllegalAccessException | InvocationTargetException -> 0x0033
            if (r6 != 0) goto L_0x0027
            java.lang.String r6 = "java.lang.Throwable"
            java.lang.Class r6 = b(r6)     // Catch: NoSuchMethodException | IllegalAccessException | InvocationTargetException -> 0x0033
            org.apache.commons.lang.exception.ExceptionUtils.a = r6     // Catch: NoSuchMethodException | IllegalAccessException | InvocationTargetException -> 0x0033
            goto L_0x0029
        L_0x0027:
            java.lang.Class r6 = org.apache.commons.lang.exception.ExceptionUtils.a     // Catch: NoSuchMethodException | IllegalAccessException | InvocationTargetException -> 0x0033
        L_0x0029:
            r5[r2] = r6     // Catch: NoSuchMethodException | IllegalAccessException | InvocationTargetException -> 0x0033
            java.lang.reflect.Method r2 = r3.getMethod(r4, r5)     // Catch: NoSuchMethodException | IllegalAccessException | InvocationTargetException -> 0x0033
            r2.invoke(r7, r1)     // Catch: NoSuchMethodException | IllegalAccessException | InvocationTargetException -> 0x0033
            r8 = r0
        L_0x0033:
            return r8
        L_0x0034:
            org.apache.commons.lang.NullArgumentException r7 = new org.apache.commons.lang.NullArgumentException
            java.lang.String r8 = "target"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.exception.ExceptionUtils.setCause(java.lang.Throwable, java.lang.Throwable):boolean");
    }

    private static String[] a(List list) {
        return (String[]) list.toArray(new String[list.size()]);
    }

    private static ArrayList a() {
        ArrayList arrayList;
        synchronized (b) {
            arrayList = new ArrayList(Arrays.asList(c));
        }
        return arrayList;
    }

    public static boolean isCauseMethodName(String str) {
        boolean z;
        synchronized (b) {
            z = ArrayUtils.indexOf(c, str) >= 0;
        }
        return z;
    }

    public static Throwable getCause(Throwable th) {
        Throwable cause;
        synchronized (b) {
            cause = getCause(th, c);
        }
        return cause;
    }

    public static Throwable getCause(Throwable th, String[] strArr) {
        String str;
        if (th == null) {
            return null;
        }
        Throwable b2 = b(th);
        if (b2 != null) {
            return b2;
        }
        if (strArr == null) {
            synchronized (b) {
                strArr = c;
            }
        }
        for (int i = 0; i < strArr.length && ((str = strArr[i]) == null || (b2 = a(th, str)) == null); i++) {
        }
        return b2 == null ? b(th, "detail") : b2;
    }

    public static Throwable getRootCause(Throwable th) {
        List throwableList = getThrowableList(th);
        if (throwableList.size() < 2) {
            return null;
        }
        return (Throwable) throwableList.get(throwableList.size() - 1);
    }

    private static Throwable b(Throwable th) {
        if (th instanceof Nestable) {
            return ((Nestable) th).getCause();
        }
        if (th instanceof SQLException) {
            return ((SQLException) th).getNextException();
        }
        if (th instanceof InvocationTargetException) {
            return ((InvocationTargetException) th).getTargetException();
        }
        return null;
    }

    private static Throwable a(Throwable th, String str) {
        Method method;
        try {
            method = th.getClass().getMethod(str, null);
        } catch (NoSuchMethodException | SecurityException unused) {
            method = null;
        }
        if (method != null) {
            Class cls = a;
            if (cls == null) {
                cls = b("java.lang.Throwable");
                a = cls;
            }
            if (cls.isAssignableFrom(method.getReturnType())) {
                try {
                    return (Throwable) method.invoke(th, ArrayUtils.EMPTY_OBJECT_ARRAY);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused2) {
                }
            }
        }
        return null;
    }

    private static Throwable b(Throwable th, String str) {
        Field field;
        try {
            field = th.getClass().getField(str);
        } catch (NoSuchFieldException | SecurityException unused) {
            field = null;
        }
        if (field != null) {
            Class cls = a;
            if (cls == null) {
                cls = b("java.lang.Throwable");
                a = cls;
            }
            if (cls.isAssignableFrom(field.getType())) {
                try {
                    return (Throwable) field.get(th);
                } catch (IllegalAccessException | IllegalArgumentException unused2) {
                }
            }
        }
        return null;
    }

    public static boolean isThrowableNested() {
        return d != null;
    }

    public static boolean isNestedThrowable(Throwable th) {
        Class cls;
        if (th == null) {
            return false;
        }
        if ((th instanceof Nestable) || (th instanceof SQLException) || (th instanceof InvocationTargetException) || isThrowableNested()) {
            return true;
        }
        Class<?> cls2 = th.getClass();
        synchronized (b) {
            int length = c.length;
            for (int i = 0; i < length; i++) {
                try {
                    Method method = cls2.getMethod(c[i], null);
                    if (method == null) {
                        continue;
                    } else {
                        if (a == null) {
                            cls = b("java.lang.Throwable");
                            a = cls;
                        } else {
                            cls = a;
                        }
                        if (cls.isAssignableFrom(method.getReturnType())) {
                            return true;
                        }
                    }
                } catch (NoSuchMethodException | SecurityException unused) {
                }
            }
            return cls2.getField("detail") != null;
        }
    }

    public static int getThrowableCount(Throwable th) {
        return getThrowableList(th).size();
    }

    public static Throwable[] getThrowables(Throwable th) {
        List throwableList = getThrowableList(th);
        return (Throwable[]) throwableList.toArray(new Throwable[throwableList.size()]);
    }

    public static List getThrowableList(Throwable th) {
        ArrayList arrayList = new ArrayList();
        while (th != null && !arrayList.contains(th)) {
            arrayList.add(th);
            th = getCause(th);
        }
        return arrayList;
    }

    public static int indexOfThrowable(Throwable th, Class cls) {
        return a(th, cls, 0, false);
    }

    public static int indexOfThrowable(Throwable th, Class cls, int i) {
        return a(th, cls, i, false);
    }

    public static int indexOfType(Throwable th, Class cls) {
        return a(th, cls, 0, true);
    }

    public static int indexOfType(Throwable th, Class cls, int i) {
        return a(th, cls, i, true);
    }

    private static int a(Throwable th, Class cls, int i, boolean z) {
        if (th == null || cls == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        Throwable[] throwables = getThrowables(th);
        if (i >= throwables.length) {
            return -1;
        }
        if (z) {
            while (i < throwables.length) {
                if (cls.isAssignableFrom(throwables[i].getClass())) {
                    return i;
                }
                i++;
            }
        } else {
            while (i < throwables.length) {
                if (cls.equals(throwables[i].getClass())) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    public static void printRootCauseStackTrace(Throwable th) {
        printRootCauseStackTrace(th, System.err);
    }

    public static void printRootCauseStackTrace(Throwable th, PrintStream printStream) {
        if (th != null) {
            if (printStream != null) {
                for (String str : getRootCauseStackTrace(th)) {
                    printStream.println(str);
                }
                printStream.flush();
                return;
            }
            throw new IllegalArgumentException("The PrintStream must not be null");
        }
    }

    public static void printRootCauseStackTrace(Throwable th, PrintWriter printWriter) {
        if (th != null) {
            if (printWriter != null) {
                for (String str : getRootCauseStackTrace(th)) {
                    printWriter.println(str);
                }
                printWriter.flush();
                return;
            }
            throw new IllegalArgumentException("The PrintWriter must not be null");
        }
    }

    public static String[] getRootCauseStackTrace(Throwable th) {
        if (th == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        Throwable[] throwables = getThrowables(th);
        int length = throwables.length;
        ArrayList arrayList = new ArrayList();
        int i = length - 1;
        List a2 = a(throwables[i]);
        while (true) {
            length--;
            if (length < 0) {
                return (String[]) arrayList.toArray(new String[0]);
            }
            if (length != 0) {
                a2 = a(throwables[length - 1]);
                removeCommonFrames(a2, a2);
            } else {
                a2 = a2;
            }
            if (length == i) {
                arrayList.add(throwables[length].toString());
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(" [wrapped] ");
                stringBuffer.append(throwables[length].toString());
                arrayList.add(stringBuffer.toString());
            }
            for (int i2 = 0; i2 < a2.size(); i2++) {
                arrayList.add(a2.get(i2));
            }
        }
    }

    public static void removeCommonFrames(List list, List list2) {
        if (list == null || list2 == null) {
            throw new IllegalArgumentException("The List must not be null");
        }
        int size = list.size() - 1;
        for (int size2 = list2.size() - 1; size >= 0 && size2 >= 0; size2--) {
            if (((String) list.get(size)).equals((String) list2.get(size2))) {
                list.remove(size);
            }
            size--;
        }
    }

    public static String getFullStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter((Writer) stringWriter, true);
        Throwable[] throwables = getThrowables(th);
        for (int i = 0; i < throwables.length; i++) {
            throwables[i].printStackTrace(printWriter);
            if (isNestedThrowable(throwables[i])) {
                break;
            }
        }
        return stringWriter.getBuffer().toString();
    }

    public static String getStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter((Writer) stringWriter, true));
        return stringWriter.getBuffer().toString();
    }

    public static String[] getStackFrames(Throwable th) {
        if (th == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return a(getStackTrace(th));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String[] a(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, SystemUtils.LINE_SEPARATOR);
        ArrayList arrayList = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return a(arrayList);
    }

    static List a(Throwable th) {
        StringTokenizer stringTokenizer = new StringTokenizer(getStackTrace(th), SystemUtils.LINE_SEPARATOR);
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            int indexOf = nextToken.indexOf("at");
            if (indexOf != -1 && nextToken.substring(0, indexOf).trim().length() == 0) {
                z = true;
                arrayList.add(nextToken);
            } else if (z) {
                break;
            }
        }
        return arrayList;
    }

    public static String getMessage(Throwable th) {
        if (th == null) {
            return "";
        }
        String shortClassName = ClassUtils.getShortClassName(th, null);
        String message = th.getMessage();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(shortClassName);
        stringBuffer.append(": ");
        stringBuffer.append(StringUtils.defaultString(message));
        return stringBuffer.toString();
    }

    public static String getRootCauseMessage(Throwable th) {
        Throwable rootCause = getRootCause(th);
        if (rootCause != null) {
            th = rootCause;
        }
        return getMessage(th);
    }
}
