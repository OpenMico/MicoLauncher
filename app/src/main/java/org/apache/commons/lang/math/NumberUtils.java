package org.apache.commons.lang.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.commons.lang.StringUtils;

/* loaded from: classes5.dex */
public class NumberUtils {
    public static final Long LONG_ZERO = new Long(0);
    public static final Long LONG_ONE = new Long(1);
    public static final Long LONG_MINUS_ONE = new Long(-1);
    public static final Integer INTEGER_ZERO = new Integer(0);
    public static final Integer INTEGER_ONE = new Integer(1);
    public static final Integer INTEGER_MINUS_ONE = new Integer(-1);
    public static final Short SHORT_ZERO = new Short((short) 0);
    public static final Short SHORT_ONE = new Short((short) 1);
    public static final Short SHORT_MINUS_ONE = new Short((short) -1);
    public static final Byte BYTE_ZERO = new Byte((byte) 0);
    public static final Byte BYTE_ONE = new Byte((byte) 1);
    public static final Byte BYTE_MINUS_ONE = new Byte((byte) -1);
    public static final Double DOUBLE_ZERO = new Double(0.0d);
    public static final Double DOUBLE_ONE = new Double(1.0d);
    public static final Double DOUBLE_MINUS_ONE = new Double(-1.0d);
    public static final Float FLOAT_ZERO = new Float(0.0f);
    public static final Float FLOAT_ONE = new Float(1.0f);
    public static final Float FLOAT_MINUS_ONE = new Float(-1.0f);

    public static byte max(byte b, byte b2, byte b3) {
        if (b2 > b) {
            b = b2;
        }
        return b3 > b ? b3 : b;
    }

    public static int max(int i, int i2, int i3) {
        if (i2 > i) {
            i = i2;
        }
        return i3 > i ? i3 : i;
    }

    public static long max(long j, long j2, long j3) {
        if (j2 > j) {
            j = j2;
        }
        return j3 > j ? j3 : j;
    }

    public static short max(short s, short s2, short s3) {
        if (s2 > s) {
            s = s2;
        }
        return s3 > s ? s3 : s;
    }

    public static byte min(byte b, byte b2, byte b3) {
        if (b2 < b) {
            b = b2;
        }
        return b3 < b ? b3 : b;
    }

    public static int min(int i, int i2, int i3) {
        if (i2 < i) {
            i = i2;
        }
        return i3 < i ? i3 : i;
    }

    public static long min(long j, long j2, long j3) {
        if (j2 < j) {
            j = j2;
        }
        return j3 < j ? j3 : j;
    }

    public static short min(short s, short s2, short s3) {
        if (s2 < s) {
            s = s2;
        }
        return s3 < s ? s3 : s;
    }

    public static int stringToInt(String str) {
        return toInt(str);
    }

    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static int stringToInt(String str, int i) {
        return toInt(str, i);
    }

    public static int toInt(String str, int i) {
        if (str == null) {
            return i;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    public static long toLong(String str) {
        return toLong(str, 0L);
    }

    public static long toLong(String str, long j) {
        if (str == null) {
            return j;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return j;
        }
    }

    public static float toFloat(String str) {
        return toFloat(str, 0.0f);
    }

    public static float toFloat(String str, float f) {
        if (str == null) {
            return f;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException unused) {
            return f;
        }
    }

    public static double toDouble(String str) {
        return toDouble(str, 0.0d);
    }

    public static double toDouble(String str, double d) {
        if (str == null) {
            return d;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException unused) {
            return d;
        }
    }

    public static byte toByte(String str) {
        return toByte(str, (byte) 0);
    }

    public static byte toByte(String str, byte b) {
        if (str == null) {
            return b;
        }
        try {
            return Byte.parseByte(str);
        } catch (NumberFormatException unused) {
            return b;
        }
    }

    public static short toShort(String str) {
        return toShort(str, (short) 0);
    }

    public static short toShort(String str, short s) {
        if (str == null) {
            return s;
        }
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException unused) {
            return s;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x00ec, code lost:
        if (r1 == 'l') goto L_0x00ee;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Number createNumber(java.lang.String r13) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 481
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.math.NumberUtils.createNumber(java.lang.String):java.lang.Number");
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
        if (str == null) {
            return null;
        }
        return Float.valueOf(str);
    }

    public static Double createDouble(String str) {
        if (str == null) {
            return null;
        }
        return Double.valueOf(str);
    }

    public static Integer createInteger(String str) {
        if (str == null) {
            return null;
        }
        return Integer.decode(str);
    }

    public static Long createLong(String str) {
        if (str == null) {
            return null;
        }
        return Long.valueOf(str);
    }

    public static BigInteger createBigInteger(String str) {
        if (str == null) {
            return null;
        }
        return new BigInteger(str);
    }

    public static BigDecimal createBigDecimal(String str) {
        if (str == null) {
            return null;
        }
        if (!StringUtils.isBlank(str)) {
            return new BigDecimal(str);
        }
        throw new NumberFormatException("A blank string is not a valid number");
    }

    public static long min(long[] jArr) {
        if (jArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (jArr.length != 0) {
            long j = jArr[0];
            for (int i = 1; i < jArr.length; i++) {
                if (jArr[i] < j) {
                    j = jArr[i];
                }
            }
            return j;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static int min(int[] iArr) {
        if (iArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (iArr.length != 0) {
            int i = iArr[0];
            for (int i2 = 1; i2 < iArr.length; i2++) {
                if (iArr[i2] < i) {
                    i = iArr[i2];
                }
            }
            return i;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static short min(short[] sArr) {
        if (sArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (sArr.length != 0) {
            short s = sArr[0];
            for (int i = 1; i < sArr.length; i++) {
                if (sArr[i] < s) {
                    s = sArr[i];
                }
            }
            return s;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static byte min(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (bArr.length != 0) {
            byte b = bArr[0];
            for (int i = 1; i < bArr.length; i++) {
                if (bArr[i] < b) {
                    b = bArr[i];
                }
            }
            return b;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static double min(double[] dArr) {
        if (dArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (dArr.length != 0) {
            double d = dArr[0];
            for (int i = 1; i < dArr.length; i++) {
                if (Double.isNaN(dArr[i])) {
                    return Double.NaN;
                }
                if (dArr[i] < d) {
                    d = dArr[i];
                }
            }
            return d;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static float min(float[] fArr) {
        if (fArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (fArr.length != 0) {
            float f = fArr[0];
            for (int i = 1; i < fArr.length; i++) {
                if (Float.isNaN(fArr[i])) {
                    return Float.NaN;
                }
                if (fArr[i] < f) {
                    f = fArr[i];
                }
            }
            return f;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static long max(long[] jArr) {
        if (jArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (jArr.length != 0) {
            long j = jArr[0];
            for (int i = 1; i < jArr.length; i++) {
                if (jArr[i] > j) {
                    j = jArr[i];
                }
            }
            return j;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static int max(int[] iArr) {
        if (iArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (iArr.length != 0) {
            int i = iArr[0];
            for (int i2 = 1; i2 < iArr.length; i2++) {
                if (iArr[i2] > i) {
                    i = iArr[i2];
                }
            }
            return i;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static short max(short[] sArr) {
        if (sArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (sArr.length != 0) {
            short s = sArr[0];
            for (int i = 1; i < sArr.length; i++) {
                if (sArr[i] > s) {
                    s = sArr[i];
                }
            }
            return s;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static byte max(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (bArr.length != 0) {
            byte b = bArr[0];
            for (int i = 1; i < bArr.length; i++) {
                if (bArr[i] > b) {
                    b = bArr[i];
                }
            }
            return b;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static double max(double[] dArr) {
        if (dArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (dArr.length != 0) {
            double d = dArr[0];
            for (int i = 1; i < dArr.length; i++) {
                if (Double.isNaN(dArr[i])) {
                    return Double.NaN;
                }
                if (dArr[i] > d) {
                    d = dArr[i];
                }
            }
            return d;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static float max(float[] fArr) {
        if (fArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (fArr.length != 0) {
            float f = fArr[0];
            for (int i = 1; i < fArr.length; i++) {
                if (Float.isNaN(fArr[i])) {
                    return Float.NaN;
                }
                if (fArr[i] > f) {
                    f = fArr[i];
                }
            }
            return f;
        } else {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    public static double min(double d, double d2, double d3) {
        return Math.min(Math.min(d, d2), d3);
    }

    public static float min(float f, float f2, float f3) {
        return Math.min(Math.min(f, f2), f3);
    }

    public static double max(double d, double d2, double d3) {
        return Math.max(Math.max(d, d2), d3);
    }

    public static float max(float f, float f2, float f3) {
        return Math.max(Math.max(f, f2), f3);
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
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:134:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0071, code lost:
        if (r3 >= r0.length) goto L_0x00be;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0075, code lost:
        if (r0[r3] < '0') goto L_0x007c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0079, code lost:
        if (r0[r3] > '9') goto L_0x007c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x007b, code lost:
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x007e, code lost:
        if (r0[r3] == 'e') goto L_0x00bd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0082, code lost:
        if (r0[r3] != 'E') goto L_0x0085;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0087, code lost:
        if (r0[r3] != '.') goto L_0x0090;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0089, code lost:
        if (r13 != false) goto L_0x008f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x008b, code lost:
        if (r12 == false) goto L_0x008e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x008e, code lost:
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x008f, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0090, code lost:
        if (r6 != false) goto L_0x00a9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0096, code lost:
        if (r0[r3] == 'd') goto L_0x00a8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x009c, code lost:
        if (r0[r3] == 'D') goto L_0x00a8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00a0, code lost:
        if (r0[r3] == 'f') goto L_0x00a8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00a6, code lost:
        if (r0[r3] != 'F') goto L_0x00a9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00a8, code lost:
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00ad, code lost:
        if (r0[r3] == 'l') goto L_0x00b7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00b3, code lost:
        if (r0[r3] != 'L') goto L_0x00b6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00b6, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00b7, code lost:
        if (r11 == false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00b9, code lost:
        if (r12 != false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00bc, code lost:
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00bd, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00be, code lost:
        if (r6 != false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00c0, code lost:
        if (r11 == false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00c3, code lost:
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00de, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isNumber(java.lang.String r16) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.math.NumberUtils.isNumber(java.lang.String):boolean");
    }
}
