package io.netty.util.internal;

import com.xiaomi.onetrack.api.b;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/* loaded from: classes4.dex */
public final class StringUtil {
    public static final char CARRIAGE_RETURN = '\r';
    public static final char COMMA = ',';
    public static final char DOUBLE_QUOTE = '\"';
    public static final String EMPTY_STRING = "";
    public static final char LINE_FEED = '\n';
    public static final String NEWLINE;
    public static final char TAB = '\t';
    static final /* synthetic */ boolean a = !StringUtil.class.desiredAssertionStatus();
    private static final String[] b = new String[256];
    private static final String[] c = new String[256];

    private static boolean a(char c2) {
        return c2 == '\"';
    }

    public static boolean isSurrogate(char c2) {
        return c2 >= 55296 && c2 <= 57343;
    }

    static {
        String str;
        Formatter formatter = new Formatter();
        int i = 0;
        try {
            str = formatter.format("%n", new Object[0]).toString();
        } catch (Exception unused) {
            str = "\n";
        } catch (Throwable th) {
            formatter.close();
            throw th;
        }
        formatter.close();
        NEWLINE = str;
        while (i < 10) {
            StringBuilder sb = new StringBuilder(2);
            sb.append('0');
            sb.append(i);
            b[i] = sb.toString();
            c[i] = String.valueOf(i);
            i++;
        }
        while (i < 16) {
            StringBuilder sb2 = new StringBuilder(2);
            char c2 = (char) ((i + 97) - 10);
            sb2.append('0');
            sb2.append(c2);
            b[i] = sb2.toString();
            c[i] = String.valueOf(c2);
            i++;
        }
        while (i < b.length) {
            StringBuilder sb3 = new StringBuilder(2);
            sb3.append(Integer.toHexString(i));
            String sb4 = sb3.toString();
            b[i] = sb4;
            c[i] = sb4;
            i++;
        }
    }

    public static String[] split(String str, char c2) {
        int length = str.length();
        ArrayList arrayList = InternalThreadLocalMap.get().arrayList();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (str.charAt(i2) == c2) {
                if (i == i2) {
                    arrayList.add("");
                } else {
                    arrayList.add(str.substring(i, i2));
                }
                i = i2 + 1;
            }
        }
        if (i == 0) {
            arrayList.add(str);
        } else if (i != length) {
            arrayList.add(str.substring(i, length));
        } else {
            for (int size = arrayList.size() - 1; size >= 0 && ((String) arrayList.get(size)).isEmpty(); size--) {
                arrayList.remove(size);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] split(String str, char c2, int i) {
        int length = str.length();
        ArrayList arrayList = InternalThreadLocalMap.get().arrayList();
        int i2 = 0;
        int i3 = 1;
        for (int i4 = 0; i4 < length && i3 < i; i4++) {
            if (str.charAt(i4) == c2) {
                if (i2 == i4) {
                    arrayList.add("");
                } else {
                    arrayList.add(str.substring(i2, i4));
                }
                i2 = i4 + 1;
                i3++;
            }
        }
        if (i2 == 0) {
            arrayList.add(str);
        } else if (i2 != length) {
            arrayList.add(str.substring(i2, length));
        } else {
            for (int size = arrayList.size() - 1; size >= 0 && ((String) arrayList.get(size)).isEmpty(); size--) {
                arrayList.remove(size);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String substringAfter(String str, char c2) {
        int indexOf = str.indexOf(c2);
        if (indexOf >= 0) {
            return str.substring(indexOf + 1);
        }
        return null;
    }

    public static boolean commonSuffixOfLength(String str, String str2, int i) {
        return str != null && str2 != null && i >= 0 && str.regionMatches(str.length() - i, str2, str2.length() - i, i);
    }

    public static String byteToHexStringPadded(int i) {
        return b[i & 255];
    }

    public static <T extends Appendable> T byteToHexStringPadded(T t, int i) {
        try {
            t.append(byteToHexStringPadded(i));
        } catch (IOException e) {
            PlatformDependent.throwException(e);
        }
        return t;
    }

    public static String toHexStringPadded(byte[] bArr) {
        return toHexStringPadded(bArr, 0, bArr.length);
    }

    public static String toHexStringPadded(byte[] bArr, int i, int i2) {
        return ((StringBuilder) toHexStringPadded(new StringBuilder(i2 << 1), bArr, i, i2)).toString();
    }

    public static <T extends Appendable> T toHexStringPadded(T t, byte[] bArr) {
        return (T) toHexStringPadded(t, bArr, 0, bArr.length);
    }

    public static <T extends Appendable> T toHexStringPadded(T t, byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            byteToHexStringPadded(t, bArr[i]);
            i++;
        }
        return t;
    }

    public static String byteToHexString(int i) {
        return c[i & 255];
    }

    public static <T extends Appendable> T byteToHexString(T t, int i) {
        try {
            t.append(byteToHexString(i));
        } catch (IOException e) {
            PlatformDependent.throwException(e);
        }
        return t;
    }

    public static String toHexString(byte[] bArr) {
        return toHexString(bArr, 0, bArr.length);
    }

    public static String toHexString(byte[] bArr, int i, int i2) {
        return ((StringBuilder) toHexString(new StringBuilder(i2 << 1), bArr, i, i2)).toString();
    }

    public static <T extends Appendable> T toHexString(T t, byte[] bArr) {
        return (T) toHexString(t, bArr, 0, bArr.length);
    }

    public static <T extends Appendable> T toHexString(T t, byte[] bArr, int i, int i2) {
        if (!a && i2 < 0) {
            throw new AssertionError();
        } else if (i2 == 0) {
            return t;
        } else {
            int i3 = i2 + i;
            int i4 = i3 - 1;
            while (i < i4 && bArr[i] == 0) {
                i++;
            }
            int i5 = i + 1;
            byteToHexString(t, bArr[i]);
            toHexStringPadded(t, bArr, i5, i3 - i5);
            return t;
        }
    }

    public static String simpleClassName(Object obj) {
        return obj == null ? "null_object" : simpleClassName(obj.getClass());
    }

    public static String simpleClassName(Class<?> cls) {
        String name = ((Class) ObjectUtil.checkNotNull(cls, "clazz")).getName();
        int lastIndexOf = name.lastIndexOf(46);
        return lastIndexOf > -1 ? name.substring(lastIndexOf + 1) : name;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004c, code lost:
        if (r9 != ',') goto L_0x0079;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.CharSequence escapeCsv(java.lang.CharSequence r13) {
        /*
            java.lang.String r0 = "value"
            java.lang.Object r0 = io.netty.util.internal.ObjectUtil.checkNotNull(r13, r0)
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            int r0 = r0.length()
            if (r0 != 0) goto L_0x000f
            return r13
        L_0x000f:
            int r1 = r0 + (-1)
            r2 = 0
            char r3 = r13.charAt(r2)
            boolean r3 = a(r3)
            r4 = 1
            if (r3 == 0) goto L_0x002b
            char r3 = r13.charAt(r1)
            boolean r3 = a(r3)
            if (r3 == 0) goto L_0x002b
            if (r0 == r4) goto L_0x002b
            r3 = r4
            goto L_0x002c
        L_0x002b:
            r3 = r2
        L_0x002c:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            int r6 = r0 + 7
            r5.<init>(r6)
            r6 = 34
            r5.append(r6)
            r7 = r2
            r8 = r7
        L_0x003a:
            if (r2 >= r0) goto L_0x007f
            char r9 = r13.charAt(r2)
            r10 = 10
            if (r9 == r10) goto L_0x0078
            r10 = 13
            if (r9 == r10) goto L_0x0078
            if (r9 == r6) goto L_0x004f
            r10 = 44
            if (r9 == r10) goto L_0x0078
            goto L_0x0079
        L_0x004f:
            if (r2 == 0) goto L_0x0073
            if (r2 != r1) goto L_0x0054
            goto L_0x0073
        L_0x0054:
            int r10 = r2 + 1
            char r11 = r13.charAt(r10)
            boolean r11 = a(r11)
            int r12 = r2 + (-1)
            char r12 = r13.charAt(r12)
            boolean r12 = a(r12)
            if (r12 != 0) goto L_0x0079
            if (r11 == 0) goto L_0x006e
            if (r10 != r1) goto L_0x0079
        L_0x006e:
            r5.append(r6)
            r7 = r4
            goto L_0x0079
        L_0x0073:
            if (r3 != 0) goto L_0x007c
            r5.append(r6)
        L_0x0078:
            r8 = r4
        L_0x0079:
            r5.append(r9)
        L_0x007c:
            int r2 = r2 + 1
            goto L_0x003a
        L_0x007f:
            if (r7 != 0) goto L_0x0085
            if (r8 == 0) goto L_0x0089
            if (r3 != 0) goto L_0x0089
        L_0x0085:
            r5.append(r6)
            r13 = r5
        L_0x0089:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.StringUtil.escapeCsv(java.lang.CharSequence):java.lang.CharSequence");
    }

    public static CharSequence unescapeCsv(CharSequence charSequence) {
        int length = ((CharSequence) ObjectUtil.checkNotNull(charSequence, b.p)).length();
        if (length == 0) {
            return charSequence;
        }
        int i = length - 1;
        boolean z = false;
        if (a(charSequence.charAt(0)) && a(charSequence.charAt(i)) && length != 1) {
            z = true;
        }
        if (!z) {
            a(charSequence);
            return charSequence;
        }
        StringBuilder stringBuilder = InternalThreadLocalMap.get().stringBuilder();
        int i2 = 1;
        while (i2 < i) {
            char charAt = charSequence.charAt(i2);
            if (charAt == '\"') {
                int i3 = i2 + 1;
                if (!a(charSequence.charAt(i3)) || i3 == i) {
                    throw a(charSequence, i2);
                }
                i2 = i3;
            }
            stringBuilder.append(charAt);
            i2++;
        }
        return stringBuilder.toString();
    }

    public static List<CharSequence> unescapeCsvFields(CharSequence charSequence) {
        ArrayList arrayList = new ArrayList(2);
        StringBuilder stringBuilder = InternalThreadLocalMap.get().stringBuilder();
        int length = charSequence.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            char charAt = charSequence.charAt(i);
            if (!z) {
                if (!(charAt == '\n' || charAt == '\r')) {
                    if (charAt != '\"') {
                        if (charAt != ',') {
                            stringBuilder.append(charAt);
                        } else {
                            arrayList.add(stringBuilder.toString());
                            stringBuilder.setLength(0);
                        }
                    } else if (stringBuilder.length() == 0) {
                        z = true;
                    }
                }
                throw a(charSequence, i);
            } else if (charAt != '\"') {
                stringBuilder.append(charAt);
            } else if (i == length) {
                arrayList.add(stringBuilder.toString());
                return arrayList;
            } else {
                i++;
                char charAt2 = charSequence.charAt(i);
                if (charAt2 == '\"') {
                    stringBuilder.append('\"');
                } else if (charAt2 == ',') {
                    arrayList.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                    z = false;
                } else {
                    throw a(charSequence, i - 1);
                }
            }
            i++;
        }
        if (!z) {
            arrayList.add(stringBuilder.toString());
            return arrayList;
        }
        throw a(charSequence, length);
    }

    private static void a(CharSequence charSequence) {
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            char charAt = charSequence.charAt(i);
            if (charAt == '\n' || charAt == '\r' || charAt == '\"' || charAt == ',') {
                throw a(charSequence, i);
            }
        }
    }

    private static IllegalArgumentException a(CharSequence charSequence, int i) {
        return new IllegalArgumentException("invalid escaped CSV field: " + ((Object) charSequence) + " index: " + i);
    }

    public static int length(String str) {
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private StringUtil() {
    }
}
