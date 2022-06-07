package org.slf4j.helpers;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class MessageFormatter {
    public static final FormattingTuple format(String str, Object obj) {
        return arrayFormat(str, new Object[]{obj});
    }

    public static final FormattingTuple format(String str, Object obj, Object obj2) {
        return arrayFormat(str, new Object[]{obj, obj2});
    }

    static final Throwable a(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            return null;
        }
        Object obj = objArr[objArr.length - 1];
        if (obj instanceof Throwable) {
            return (Throwable) obj;
        }
        return null;
    }

    public static final FormattingTuple arrayFormat(String str, Object[] objArr) {
        Throwable a = a(objArr);
        if (str == null) {
            return new FormattingTuple(null, objArr, a);
        }
        if (objArr == null) {
            return new FormattingTuple(str);
        }
        StringBuilder sb = new StringBuilder(str.length() + 50);
        int i = 0;
        int i2 = 0;
        while (i < objArr.length) {
            int indexOf = str.indexOf("{}", i2);
            if (indexOf != -1) {
                if (!a(str, indexOf)) {
                    sb.append(str.substring(i2, indexOf));
                    a(sb, objArr[i], new HashMap());
                    i2 = indexOf + 2;
                } else if (!b(str, indexOf)) {
                    i--;
                    sb.append(str.substring(i2, indexOf - 1));
                    sb.append('{');
                    i2 = indexOf + 1;
                } else {
                    sb.append(str.substring(i2, indexOf - 1));
                    a(sb, objArr[i], new HashMap());
                    i2 = indexOf + 2;
                }
                i++;
            } else if (i2 == 0) {
                return new FormattingTuple(str, objArr, a);
            } else {
                sb.append(str.substring(i2, str.length()));
                return new FormattingTuple(sb.toString(), objArr, a);
            }
        }
        sb.append(str.substring(i2, str.length()));
        if (i < objArr.length - 1) {
            return new FormattingTuple(sb.toString(), objArr, a);
        }
        return new FormattingTuple(sb.toString(), objArr, null);
    }

    static final boolean a(String str, int i) {
        return i != 0 && str.charAt(i - 1) == '\\';
    }

    static final boolean b(String str, int i) {
        return i >= 2 && str.charAt(i - 2) == '\\';
    }

    private static void a(StringBuilder sb, Object obj, Map<Object[], Object> map) {
        if (obj == null) {
            sb.append("null");
        } else if (!obj.getClass().isArray()) {
            a(sb, obj);
        } else if (obj instanceof boolean[]) {
            a(sb, (boolean[]) obj);
        } else if (obj instanceof byte[]) {
            a(sb, (byte[]) obj);
        } else if (obj instanceof char[]) {
            a(sb, (char[]) obj);
        } else if (obj instanceof short[]) {
            a(sb, (short[]) obj);
        } else if (obj instanceof int[]) {
            a(sb, (int[]) obj);
        } else if (obj instanceof long[]) {
            a(sb, (long[]) obj);
        } else if (obj instanceof float[]) {
            a(sb, (float[]) obj);
        } else if (obj instanceof double[]) {
            a(sb, (double[]) obj);
        } else {
            a(sb, (Object[]) obj, map);
        }
    }

    private static void a(StringBuilder sb, Object obj) {
        try {
            sb.append(obj.toString());
        } catch (Throwable th) {
            PrintStream printStream = System.err;
            printStream.println("SLF4J: Failed toString() invocation on an object of type [" + obj.getClass().getName() + "]");
            th.printStackTrace();
            sb.append("[FAILED toString()]");
        }
    }

    private static void a(StringBuilder sb, Object[] objArr, Map<Object[], Object> map) {
        sb.append('[');
        if (!map.containsKey(objArr)) {
            map.put(objArr, null);
            int length = objArr.length;
            for (int i = 0; i < length; i++) {
                a(sb, objArr[i], map);
                if (i != length - 1) {
                    sb.append(", ");
                }
            }
            map.remove(objArr);
        } else {
            sb.append("...");
        }
        sb.append(']');
    }

    private static void a(StringBuilder sb, boolean[] zArr) {
        sb.append('[');
        int length = zArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(zArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void a(StringBuilder sb, byte[] bArr) {
        sb.append('[');
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            sb.append((int) bArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void a(StringBuilder sb, char[] cArr) {
        sb.append('[');
        int length = cArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(cArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void a(StringBuilder sb, short[] sArr) {
        sb.append('[');
        int length = sArr.length;
        for (int i = 0; i < length; i++) {
            sb.append((int) sArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void a(StringBuilder sb, int[] iArr) {
        sb.append('[');
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(iArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void a(StringBuilder sb, long[] jArr) {
        sb.append('[');
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(jArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void a(StringBuilder sb, float[] fArr) {
        sb.append('[');
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(fArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void a(StringBuilder sb, double[] dArr) {
        sb.append('[');
        int length = dArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(dArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }
}
