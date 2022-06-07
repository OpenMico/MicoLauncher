package org.apache.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public final class NumberUtils {
    public static int maximum(int i, int i2, int i3) {
        if (i2 > i) {
            i = i2;
        }
        return i3 > i ? i3 : i;
    }

    public static long maximum(long j, long j2, long j3) {
        if (j2 > j) {
            j = j2;
        }
        return j3 > j ? j3 : j;
    }

    public static int minimum(int i, int i2, int i3) {
        if (i2 < i) {
            i = i2;
        }
        return i3 < i ? i3 : i;
    }

    public static long minimum(long j, long j2, long j3) {
        if (j2 < j) {
            j = j2;
        }
        return j3 < j ? j3 : j;
    }

    public static int stringToInt(String str) {
        return stringToInt(str, 0);
    }

    public static int stringToInt(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ef, code lost:
        if (r1 == 'l') goto L_0x00f1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Number createNumber(java.lang.String r12) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 483
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.NumberUtils.createNumber(java.lang.String):java.lang.Number");
    }

    private static boolean a(String str) {
        if (str == null) {
            return true;
        }
        for (int length = str.length() - 1; length >= 0; length--) {
            if (str.charAt(length) != '0') {
                return false;
            }
        }
        return str.length() > 0;
    }

    public static Float createFloat(String str) {
        return Float.valueOf(str);
    }

    public static Double createDouble(String str) {
        return Double.valueOf(str);
    }

    public static Integer createInteger(String str) {
        return Integer.decode(str);
    }

    public static Long createLong(String str) {
        return Long.valueOf(str);
    }

    public static BigInteger createBigInteger(String str) {
        return new BigInteger(str);
    }

    public static BigDecimal createBigDecimal(String str) {
        return new BigDecimal(str);
    }

    public static int compare(double d, double d2) {
        if (d < d2) {
            return -1;
        }
        if (d > d2) {
            return 1;
        }
        int i = (Double.doubleToLongBits(d) > Double.doubleToLongBits(d2) ? 1 : (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2) ? 0 : -1));
        if (i == 0) {
            return 0;
        }
        return i < 0 ? -1 : 1;
    }

    public static int compare(float f, float f2) {
        if (f < f2) {
            return -1;
        }
        if (f > f2) {
            return 1;
        }
        int floatToIntBits = Float.floatToIntBits(f);
        int floatToIntBits2 = Float.floatToIntBits(f2);
        if (floatToIntBits == floatToIntBits2) {
            return 0;
        }
        return floatToIntBits < floatToIntBits2 ? -1 : 1;
    }

    public static boolean isDigits(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:127:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x006d, code lost:
        if (r3 >= r0.length) goto L_0x00ad;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0071, code lost:
        if (r0[r3] < '0') goto L_0x0078;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0075, code lost:
        if (r0[r3] > '9') goto L_0x0078;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0077, code lost:
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x007a, code lost:
        if (r0[r3] == 'e') goto L_0x00ac;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x007e, code lost:
        if (r0[r3] != 'E') goto L_0x0081;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0081, code lost:
        if (r6 != false) goto L_0x0098;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0087, code lost:
        if (r0[r3] == 'd') goto L_0x0097;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x008d, code lost:
        if (r0[r3] == 'D') goto L_0x0097;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0091, code lost:
        if (r0[r3] == 'f') goto L_0x0097;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0095, code lost:
        if (r0[r3] != 'F') goto L_0x0098;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0097, code lost:
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x009c, code lost:
        if (r0[r3] == 'l') goto L_0x00a6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00a2, code lost:
        if (r0[r3] != 'L') goto L_0x00a5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00a5, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00a6, code lost:
        if (r11 == false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00a8, code lost:
        if (r12 != false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00ab, code lost:
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ac, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00ad, code lost:
        if (r6 != false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00af, code lost:
        if (r11 == false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00b2, code lost:
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x00cf, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isNumber(java.lang.String r16) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.NumberUtils.isNumber(java.lang.String):boolean");
    }
}
