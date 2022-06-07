package com.fasterxml.jackson.core.io;

import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.miplay.mylibrary.DataModel;

/* loaded from: classes.dex */
public final class NumberOutput {
    private static int c = 1000000;
    private static int d = 1000000000;
    private static long e = 1000000000;
    private static long f = -2147483648L;
    private static long g = 2147483647L;
    private static final String[] i;
    private static final String[] j;
    static final String a = String.valueOf(Integer.MIN_VALUE);
    static final String b = String.valueOf(Long.MIN_VALUE);
    private static final int[] h = new int[1000];

    static {
        int i2 = 0;
        int i3 = 0;
        while (i2 < 10) {
            int i4 = 0;
            while (i4 < 10) {
                int i5 = i3;
                for (int i6 = 0; i6 < 10; i6++) {
                    i5++;
                    h[i5] = ((i2 + 48) << 16) | ((i4 + 48) << 8) | (i6 + 48);
                }
                i4++;
                i3 = i5;
            }
            i2++;
            i3 = i3;
        }
        i = new String[]{"0", "1", "2", "3", Commands.ResolutionValues.BITSTREAM_BLUE_HIGH, Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND, "6", "7", "8", Commands.ResolutionValues.BITSTREAM_4K, "10"};
        j = new String[]{DataModel.CIRCULATEFAIL_NO_SUPPORT, "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10"};
    }

    public static int outputInt(int i2, char[] cArr, int i3) {
        int i4;
        if (i2 < 0) {
            if (i2 == Integer.MIN_VALUE) {
                return b(cArr, i3);
            }
            i3++;
            cArr[i3] = '-';
            i2 = -i2;
        }
        if (i2 >= c) {
            int i5 = d;
            if (i2 >= i5) {
                int i6 = i2 - i5;
                if (i6 >= i5) {
                    i6 -= i5;
                    i4 = i3 + 1;
                    cArr[i3] = '2';
                } else {
                    i4 = i3 + 1;
                    cArr[i3] = '1';
                }
                return b(i6, cArr, i4);
            }
            int i7 = i2 / 1000;
            int i8 = i7 / 1000;
            return d(i2 - (i7 * 1000), cArr, d(i7 - (i8 * 1000), cArr, c(i8, cArr, i3)));
        } else if (i2 >= 1000) {
            int i9 = i2 / 1000;
            return d(i2 - (i9 * 1000), cArr, c(i9, cArr, i3));
        } else if (i2 >= 10) {
            return c(i2, cArr, i3);
        } else {
            cArr[i3] = (char) (i2 + 48);
            return i3 + 1;
        }
    }

    public static int outputInt(int i2, byte[] bArr, int i3) {
        int i4;
        if (i2 < 0) {
            if (i2 == Integer.MIN_VALUE) {
                return b(bArr, i3);
            }
            i3++;
            bArr[i3] = 45;
            i2 = -i2;
        }
        if (i2 >= c) {
            int i5 = d;
            if (i2 >= i5) {
                int i6 = i2 - i5;
                if (i6 >= i5) {
                    i6 -= i5;
                    i4 = i3 + 1;
                    bArr[i3] = 50;
                } else {
                    i4 = i3 + 1;
                    bArr[i3] = 49;
                }
                return b(i6, bArr, i4);
            }
            int i7 = i2 / 1000;
            int i8 = i7 / 1000;
            return d(i2 - (i7 * 1000), bArr, d(i7 - (i8 * 1000), bArr, c(i8, bArr, i3)));
        } else if (i2 >= 1000) {
            int i9 = i2 / 1000;
            return d(i2 - (i9 * 1000), bArr, c(i9, bArr, i3));
        } else if (i2 >= 10) {
            return c(i2, bArr, i3);
        } else {
            int i10 = i3 + 1;
            bArr[i3] = (byte) (i2 + 48);
            return i10;
        }
    }

    public static int outputLong(long j2, char[] cArr, int i2) {
        int i3;
        if (j2 < 0) {
            if (j2 > f) {
                return outputInt((int) j2, cArr, i2);
            }
            if (j2 == Long.MIN_VALUE) {
                return a(cArr, i2);
            }
            i2++;
            cArr[i2] = '-';
            j2 = -j2;
        } else if (j2 <= g) {
            return outputInt((int) j2, cArr, i2);
        }
        long j3 = e;
        long j4 = j2 / j3;
        long j5 = j2 - (j4 * j3);
        if (j4 < j3) {
            i3 = a((int) j4, cArr, i2);
        } else {
            long j6 = j4 / j3;
            int c2 = c((int) j6, cArr, i2);
            i3 = b((int) (j4 - (j3 * j6)), cArr, c2);
        }
        return b((int) j5, cArr, i3);
    }

    public static int outputLong(long j2, byte[] bArr, int i2) {
        int i3;
        if (j2 < 0) {
            if (j2 > f) {
                return outputInt((int) j2, bArr, i2);
            }
            if (j2 == Long.MIN_VALUE) {
                return a(bArr, i2);
            }
            i2++;
            bArr[i2] = 45;
            j2 = -j2;
        } else if (j2 <= g) {
            return outputInt((int) j2, bArr, i2);
        }
        long j3 = e;
        long j4 = j2 / j3;
        long j5 = j2 - (j4 * j3);
        if (j4 < j3) {
            i3 = a((int) j4, bArr, i2);
        } else {
            long j6 = j4 / j3;
            int c2 = c((int) j6, bArr, i2);
            i3 = b((int) (j4 - (j3 * j6)), bArr, c2);
        }
        return b((int) j5, bArr, i3);
    }

    public static String toString(int i2) {
        String[] strArr = i;
        if (i2 < strArr.length) {
            if (i2 >= 0) {
                return strArr[i2];
            }
            int i3 = (-i2) - 1;
            String[] strArr2 = j;
            if (i3 < strArr2.length) {
                return strArr2[i3];
            }
        }
        return Integer.toString(i2);
    }

    public static String toString(long j2) {
        if (j2 > 2147483647L || j2 < -2147483648L) {
            return Long.toString(j2);
        }
        return toString((int) j2);
    }

    public static String toString(double d2) {
        return Double.toString(d2);
    }

    public static String toString(float f2) {
        return Float.toString(f2);
    }

    private static int a(int i2, char[] cArr, int i3) {
        if (i2 >= c) {
            int i4 = i2 / 1000;
            int i5 = i2 - (i4 * 1000);
            int i6 = i4 / 1000;
            int c2 = c(i6, cArr, i3);
            int[] iArr = h;
            int i7 = iArr[i4 - (i6 * 1000)];
            int i8 = c2 + 1;
            cArr[c2] = (char) (i7 >> 16);
            int i9 = i8 + 1;
            cArr[i8] = (char) ((i7 >> 8) & 127);
            int i10 = i9 + 1;
            cArr[i9] = (char) (i7 & 127);
            int i11 = iArr[i5];
            int i12 = i10 + 1;
            cArr[i10] = (char) (i11 >> 16);
            int i13 = i12 + 1;
            cArr[i12] = (char) ((i11 >> 8) & 127);
            int i14 = i13 + 1;
            cArr[i13] = (char) (i11 & 127);
            return i14;
        } else if (i2 < 1000) {
            return c(i2, cArr, i3);
        } else {
            int i15 = i2 / 1000;
            return a(cArr, i3, i15, i2 - (i15 * 1000));
        }
    }

    private static int b(int i2, char[] cArr, int i3) {
        int i4 = i2 / 1000;
        int i5 = i2 - (i4 * 1000);
        int i6 = i4 / 1000;
        int[] iArr = h;
        int i7 = iArr[i6];
        int i8 = i3 + 1;
        cArr[i3] = (char) (i7 >> 16);
        int i9 = i8 + 1;
        cArr[i8] = (char) ((i7 >> 8) & 127);
        int i10 = i9 + 1;
        cArr[i9] = (char) (i7 & 127);
        int i11 = iArr[i4 - (i6 * 1000)];
        int i12 = i10 + 1;
        cArr[i10] = (char) (i11 >> 16);
        int i13 = i12 + 1;
        cArr[i12] = (char) ((i11 >> 8) & 127);
        int i14 = i13 + 1;
        cArr[i13] = (char) (i11 & 127);
        int i15 = iArr[i5];
        int i16 = i14 + 1;
        cArr[i14] = (char) (i15 >> 16);
        int i17 = i16 + 1;
        cArr[i16] = (char) ((i15 >> 8) & 127);
        int i18 = i17 + 1;
        cArr[i17] = (char) (i15 & 127);
        return i18;
    }

    private static int a(int i2, byte[] bArr, int i3) {
        if (i2 >= c) {
            int i4 = i2 / 1000;
            int i5 = i2 - (i4 * 1000);
            int i6 = i4 / 1000;
            int c2 = c(i6, bArr, i3);
            int[] iArr = h;
            int i7 = iArr[i4 - (i6 * 1000)];
            int i8 = c2 + 1;
            bArr[c2] = (byte) (i7 >> 16);
            int i9 = i8 + 1;
            bArr[i8] = (byte) (i7 >> 8);
            int i10 = i9 + 1;
            bArr[i9] = (byte) i7;
            int i11 = iArr[i5];
            int i12 = i10 + 1;
            bArr[i10] = (byte) (i11 >> 16);
            int i13 = i12 + 1;
            bArr[i12] = (byte) (i11 >> 8);
            int i14 = i13 + 1;
            bArr[i13] = (byte) i11;
            return i14;
        } else if (i2 < 1000) {
            return c(i2, bArr, i3);
        } else {
            int i15 = i2 / 1000;
            return a(bArr, i3, i15, i2 - (i15 * 1000));
        }
    }

    private static int b(int i2, byte[] bArr, int i3) {
        int i4 = i2 / 1000;
        int i5 = i2 - (i4 * 1000);
        int i6 = i4 / 1000;
        int i7 = i4 - (i6 * 1000);
        int[] iArr = h;
        int i8 = iArr[i6];
        int i9 = i3 + 1;
        bArr[i3] = (byte) (i8 >> 16);
        int i10 = i9 + 1;
        bArr[i9] = (byte) (i8 >> 8);
        int i11 = i10 + 1;
        bArr[i10] = (byte) i8;
        int i12 = iArr[i7];
        int i13 = i11 + 1;
        bArr[i11] = (byte) (i12 >> 16);
        int i14 = i13 + 1;
        bArr[i13] = (byte) (i12 >> 8);
        int i15 = i14 + 1;
        bArr[i14] = (byte) i12;
        int i16 = iArr[i5];
        int i17 = i15 + 1;
        bArr[i15] = (byte) (i16 >> 16);
        int i18 = i17 + 1;
        bArr[i17] = (byte) (i16 >> 8);
        int i19 = i18 + 1;
        bArr[i18] = (byte) i16;
        return i19;
    }

    private static int a(char[] cArr, int i2, int i3, int i4) {
        int i5 = h[i3];
        if (i3 > 9) {
            if (i3 > 99) {
                i2++;
                cArr[i2] = (char) (i5 >> 16);
            }
            i2++;
            cArr[i2] = (char) ((i5 >> 8) & 127);
        }
        int i6 = i2 + 1;
        cArr[i2] = (char) (i5 & 127);
        int i7 = h[i4];
        int i8 = i6 + 1;
        cArr[i6] = (char) (i7 >> 16);
        int i9 = i8 + 1;
        cArr[i8] = (char) ((i7 >> 8) & 127);
        int i10 = i9 + 1;
        cArr[i9] = (char) (i7 & 127);
        return i10;
    }

    private static int a(byte[] bArr, int i2, int i3, int i4) {
        int i5 = h[i3];
        if (i3 > 9) {
            if (i3 > 99) {
                i2++;
                bArr[i2] = (byte) (i5 >> 16);
            }
            i2++;
            bArr[i2] = (byte) (i5 >> 8);
        }
        int i6 = i2 + 1;
        bArr[i2] = (byte) i5;
        int i7 = h[i4];
        int i8 = i6 + 1;
        bArr[i6] = (byte) (i7 >> 16);
        int i9 = i8 + 1;
        bArr[i8] = (byte) (i7 >> 8);
        int i10 = i9 + 1;
        bArr[i9] = (byte) i7;
        return i10;
    }

    private static int c(int i2, char[] cArr, int i3) {
        int i4;
        int i5 = h[i2];
        if (i2 > 9) {
            if (i2 > 99) {
                i4 = i3 + 1;
                cArr[i3] = (char) (i5 >> 16);
            } else {
                i4 = i3;
            }
            i3 = i4 + 1;
            cArr[i4] = (char) ((i5 >> 8) & 127);
        }
        int i6 = i3 + 1;
        cArr[i3] = (char) (i5 & 127);
        return i6;
    }

    private static int c(int i2, byte[] bArr, int i3) {
        int i4;
        int i5 = h[i2];
        if (i2 > 9) {
            if (i2 > 99) {
                i4 = i3 + 1;
                bArr[i3] = (byte) (i5 >> 16);
            } else {
                i4 = i3;
            }
            i3 = i4 + 1;
            bArr[i4] = (byte) (i5 >> 8);
        }
        int i6 = i3 + 1;
        bArr[i3] = (byte) i5;
        return i6;
    }

    private static int d(int i2, char[] cArr, int i3) {
        int i4 = h[i2];
        int i5 = i3 + 1;
        cArr[i3] = (char) (i4 >> 16);
        int i6 = i5 + 1;
        cArr[i5] = (char) ((i4 >> 8) & 127);
        int i7 = i6 + 1;
        cArr[i6] = (char) (i4 & 127);
        return i7;
    }

    private static int d(int i2, byte[] bArr, int i3) {
        int i4 = h[i2];
        int i5 = i3 + 1;
        bArr[i3] = (byte) (i4 >> 16);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (i4 >> 8);
        int i7 = i6 + 1;
        bArr[i6] = (byte) i4;
        return i7;
    }

    private static int a(char[] cArr, int i2) {
        int length = b.length();
        b.getChars(0, length, cArr, i2);
        return i2 + length;
    }

    private static int a(byte[] bArr, int i2) {
        int length = b.length();
        for (int i3 = 0; i3 < length; i3++) {
            i2++;
            bArr[i2] = (byte) b.charAt(i3);
        }
        return i2;
    }

    private static int b(char[] cArr, int i2) {
        int length = a.length();
        a.getChars(0, length, cArr, i2);
        return i2 + length;
    }

    private static int b(byte[] bArr, int i2) {
        int length = a.length();
        for (int i3 = 0; i3 < length; i3++) {
            i2++;
            bArr[i2] = (byte) a.charAt(i3);
        }
        return i2;
    }
}
