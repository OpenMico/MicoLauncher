package com.fasterxml.jackson.core.io;

import com.google.android.exoplayer2.C;
import java.math.BigDecimal;

/* loaded from: classes.dex */
public final class NumberInput {
    public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";
    static final String a = String.valueOf(Long.MIN_VALUE).substring(1);
    static final String b = String.valueOf(Long.MAX_VALUE);

    public static int parseInt(char[] cArr, int i, int i2) {
        int i3 = cArr[i] - '0';
        if (i2 > 4) {
            int i4 = i + 1;
            int i5 = i4 + 1;
            int i6 = i5 + 1;
            i = i6 + 1;
            i3 = (((((((i3 * 10) + (cArr[i4] - '0')) * 10) + (cArr[i5] - '0')) * 10) + (cArr[i6] - '0')) * 10) + (cArr[i] - '0');
            i2 -= 4;
            if (i2 > 4) {
                int i7 = i + 1;
                int i8 = i7 + 1;
                int i9 = i8 + 1;
                return (((((((i3 * 10) + (cArr[i7] - '0')) * 10) + (cArr[i8] - '0')) * 10) + (cArr[i9] - '0')) * 10) + (cArr[i9 + 1] - '0');
            }
        }
        if (i2 <= 1) {
            return i3;
        }
        int i10 = i + 1;
        int i11 = (i3 * 10) + (cArr[i10] - '0');
        if (i2 <= 2) {
            return i11;
        }
        int i12 = i10 + 1;
        int i13 = (i11 * 10) + (cArr[i12] - '0');
        return i2 > 3 ? (i13 * 10) + (cArr[i12 + 1] - '0') : i13;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0075, code lost:
        return java.lang.Integer.parseInt(r9);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int parseInt(java.lang.String r9) {
        /*
            r0 = 0
            char r1 = r9.charAt(r0)
            int r2 = r9.length()
            r3 = 1
            r4 = 45
            if (r1 != r4) goto L_0x000f
            r0 = r3
        L_0x000f:
            r4 = 10
            if (r0 == 0) goto L_0x0026
            if (r2 == r3) goto L_0x0021
            if (r2 <= r4) goto L_0x0018
            goto L_0x0021
        L_0x0018:
            r1 = 2
            char r3 = r9.charAt(r3)
            r8 = r3
            r3 = r1
            r1 = r8
            goto L_0x002f
        L_0x0021:
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        L_0x0026:
            r5 = 9
            if (r2 <= r5) goto L_0x002f
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        L_0x002f:
            r5 = 57
            if (r1 > r5) goto L_0x0084
            r6 = 48
            if (r1 >= r6) goto L_0x0038
            goto L_0x0084
        L_0x0038:
            int r1 = r1 - r6
            if (r3 >= r2) goto L_0x0080
            int r7 = r3 + 1
            char r3 = r9.charAt(r3)
            if (r3 > r5) goto L_0x007b
            if (r3 >= r6) goto L_0x0046
            goto L_0x007b
        L_0x0046:
            int r1 = r1 * 10
            int r3 = r3 - r6
            int r1 = r1 + r3
            if (r7 >= r2) goto L_0x0080
            int r3 = r7 + 1
            char r7 = r9.charAt(r7)
            if (r7 > r5) goto L_0x0076
            if (r7 >= r6) goto L_0x0057
            goto L_0x0076
        L_0x0057:
            int r1 = r1 * 10
            int r7 = r7 - r6
            int r1 = r1 + r7
            if (r3 >= r2) goto L_0x0080
        L_0x005d:
            int r7 = r3 + 1
            char r3 = r9.charAt(r3)
            if (r3 > r5) goto L_0x0071
            if (r3 >= r6) goto L_0x0068
            goto L_0x0071
        L_0x0068:
            int r1 = r1 * r4
            int r3 = r3 + (-48)
            int r1 = r1 + r3
            if (r7 < r2) goto L_0x006f
            goto L_0x0080
        L_0x006f:
            r3 = r7
            goto L_0x005d
        L_0x0071:
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        L_0x0076:
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        L_0x007b:
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        L_0x0080:
            if (r0 == 0) goto L_0x0083
            int r1 = -r1
        L_0x0083:
            return r1
        L_0x0084:
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.io.NumberInput.parseInt(java.lang.String):int");
    }

    public static long parseLong(char[] cArr, int i, int i2) {
        int i3 = i2 - 9;
        return (parseInt(cArr, i, i3) * C.NANOS_PER_SECOND) + parseInt(cArr, i + i3, 9);
    }

    public static long parseLong(String str) {
        if (str.length() <= 9) {
            return parseInt(str);
        }
        return Long.parseLong(str);
    }

    public static boolean inLongRange(char[] cArr, int i, int i2, boolean z) {
        String str = z ? a : b;
        int length = str.length();
        if (i2 < length) {
            return true;
        }
        if (i2 > length) {
            return false;
        }
        for (int i3 = 0; i3 < length; i3++) {
            int charAt = cArr[i + i3] - str.charAt(i3);
            if (charAt != 0) {
                return charAt < 0;
            }
        }
        return true;
    }

    public static boolean inLongRange(String str, boolean z) {
        String str2 = z ? a : b;
        int length = str2.length();
        int length2 = str.length();
        if (length2 < length) {
            return true;
        }
        if (length2 > length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            int charAt = str.charAt(i) - str2.charAt(i);
            if (charAt != 0) {
                return charAt < 0;
            }
        }
        return true;
    }

    public static int parseAsInt(String str, int i) {
        String trim;
        int length;
        if (str == null || (length = (trim = str.trim()).length()) == 0) {
            return i;
        }
        int i2 = 0;
        if (length > 0) {
            char charAt = trim.charAt(0);
            if (charAt == '+') {
                trim = trim.substring(1);
                length = trim.length();
            } else if (charAt == '-') {
                i2 = 1;
            }
        }
        while (i2 < length) {
            char charAt2 = trim.charAt(i2);
            if (charAt2 > '9' || charAt2 < '0') {
                try {
                    return (int) parseDouble(trim);
                } catch (NumberFormatException unused) {
                    return i;
                }
            } else {
                i2++;
            }
        }
        try {
            return Integer.parseInt(trim);
        } catch (NumberFormatException unused2) {
            return i;
        }
    }

    public static long parseAsLong(String str, long j) {
        String trim;
        int length;
        if (str == null || (length = (trim = str.trim()).length()) == 0) {
            return j;
        }
        int i = 0;
        if (length > 0) {
            char charAt = trim.charAt(0);
            if (charAt == '+') {
                trim = trim.substring(1);
                length = trim.length();
            } else if (charAt == '-') {
                i = 1;
            }
        }
        while (i < length) {
            char charAt2 = trim.charAt(i);
            if (charAt2 > '9' || charAt2 < '0') {
                try {
                    return (long) parseDouble(trim);
                } catch (NumberFormatException unused) {
                    return j;
                }
            } else {
                i++;
            }
        }
        try {
            return Long.parseLong(trim);
        } catch (NumberFormatException unused2) {
            return j;
        }
    }

    public static double parseAsDouble(String str, double d) {
        if (str == null) {
            return d;
        }
        String trim = str.trim();
        if (trim.length() == 0) {
            return d;
        }
        try {
            return parseDouble(trim);
        } catch (NumberFormatException unused) {
            return d;
        }
    }

    public static double parseDouble(String str) throws NumberFormatException {
        if (NASTY_SMALL_DOUBLE.equals(str)) {
            return Double.MIN_VALUE;
        }
        return Double.parseDouble(str);
    }

    public static BigDecimal parseBigDecimal(String str) throws NumberFormatException {
        try {
            return new BigDecimal(str);
        } catch (NumberFormatException unused) {
            throw a(str);
        }
    }

    public static BigDecimal parseBigDecimal(char[] cArr) throws NumberFormatException {
        return parseBigDecimal(cArr, 0, cArr.length);
    }

    public static BigDecimal parseBigDecimal(char[] cArr, int i, int i2) throws NumberFormatException {
        try {
            return new BigDecimal(cArr, i, i2);
        } catch (NumberFormatException unused) {
            throw a(new String(cArr, i, i2));
        }
    }

    private static NumberFormatException a(String str) {
        return new NumberFormatException("Value \"" + str + "\" can not be represented as BigDecimal");
    }
}
