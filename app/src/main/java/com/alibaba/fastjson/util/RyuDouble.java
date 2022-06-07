package com.alibaba.fastjson.util;

import com.google.android.exoplayer2.C;
import java.lang.reflect.Array;
import java.math.BigInteger;
import okhttp3.internal.connection.RealConnection;

/* loaded from: classes.dex */
public final class RyuDouble {
    private static final int[][] POW5_SPLIT = (int[][]) Array.newInstance(int.class, 326, 4);
    private static final int[][] POW5_INV_SPLIT = (int[][]) Array.newInstance(int.class, 291, 4);

    static {
        BigInteger subtract = BigInteger.ONE.shiftLeft(31).subtract(BigInteger.ONE);
        BigInteger subtract2 = BigInteger.ONE.shiftLeft(31).subtract(BigInteger.ONE);
        int i = 0;
        while (i < 326) {
            BigInteger pow = BigInteger.valueOf(5L).pow(i);
            int bitLength = pow.bitLength();
            int i2 = i == 0 ? 1 : (int) ((((i * 23219280) + 10000000) - 1) / 10000000);
            if (i2 == bitLength) {
                if (i < POW5_SPLIT.length) {
                    for (int i3 = 0; i3 < 4; i3++) {
                        POW5_SPLIT[i][i3] = pow.shiftRight((bitLength - 121) + ((3 - i3) * 31)).and(subtract).intValue();
                    }
                }
                if (i < POW5_INV_SPLIT.length) {
                    BigInteger add = BigInteger.ONE.shiftLeft(bitLength + 121).divide(pow).add(BigInteger.ONE);
                    for (int i4 = 0; i4 < 4; i4++) {
                        if (i4 == 0) {
                            POW5_INV_SPLIT[i][i4] = add.shiftRight((3 - i4) * 31).intValue();
                        } else {
                            POW5_INV_SPLIT[i][i4] = add.shiftRight((3 - i4) * 31).and(subtract2).intValue();
                        }
                    }
                }
                i++;
            } else {
                throw new IllegalStateException(bitLength + " != " + i2);
            }
        }
    }

    public static String toString(double d) {
        char[] cArr = new char[24];
        return new String(cArr, 0, toString(d, cArr, 0));
    }

    public static int toString(double d, char[] cArr, int i) {
        int i2;
        boolean z;
        boolean z2;
        long j;
        long j2;
        boolean z3;
        boolean z4;
        long j3;
        int i3;
        long j4;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        if (Double.isNaN(d)) {
            int i12 = i + 1;
            cArr[i] = 'N';
            int i13 = i12 + 1;
            cArr[i12] = 'a';
            cArr[i13] = 'N';
            return (i13 + 1) - i;
        } else if (d == Double.POSITIVE_INFINITY) {
            int i14 = i + 1;
            cArr[i] = 'I';
            int i15 = i14 + 1;
            cArr[i14] = 'n';
            int i16 = i15 + 1;
            cArr[i15] = 'f';
            int i17 = i16 + 1;
            cArr[i16] = 'i';
            int i18 = i17 + 1;
            cArr[i17] = 'n';
            int i19 = i18 + 1;
            cArr[i18] = 'i';
            int i20 = i19 + 1;
            cArr[i19] = 't';
            cArr[i20] = 'y';
            return (i20 + 1) - i;
        } else if (d == Double.NEGATIVE_INFINITY) {
            int i21 = i + 1;
            cArr[i] = '-';
            int i22 = i21 + 1;
            cArr[i21] = 'I';
            int i23 = i22 + 1;
            cArr[i22] = 'n';
            int i24 = i23 + 1;
            cArr[i23] = 'f';
            int i25 = i24 + 1;
            cArr[i24] = 'i';
            int i26 = i25 + 1;
            cArr[i25] = 'n';
            int i27 = i26 + 1;
            cArr[i26] = 'i';
            int i28 = i27 + 1;
            cArr[i27] = 't';
            cArr[i28] = 'y';
            return (i28 + 1) - i;
        } else {
            long doubleToLongBits = Double.doubleToLongBits(d);
            int i29 = (doubleToLongBits > 0L ? 1 : (doubleToLongBits == 0L ? 0 : -1));
            if (i29 == 0) {
                int i30 = i + 1;
                cArr[i] = '0';
                int i31 = i30 + 1;
                cArr[i30] = '.';
                cArr[i31] = '0';
                return (i31 + 1) - i;
            } else if (doubleToLongBits == Long.MIN_VALUE) {
                int i32 = i + 1;
                cArr[i] = '-';
                int i33 = i32 + 1;
                cArr[i32] = '0';
                int i34 = i33 + 1;
                cArr[i33] = '.';
                cArr[i34] = '0';
                return (i34 + 1) - i;
            } else {
                int i35 = (int) ((doubleToLongBits >>> 52) & 2047);
                long j5 = doubleToLongBits & 4503599627370495L;
                if (i35 == 0) {
                    i2 = -1074;
                } else {
                    i2 = (i35 - 1023) - 52;
                    j5 |= 4503599627370496L;
                }
                boolean z5 = i29 < 0;
                boolean z6 = (j5 & 1) == 0;
                long j6 = 4 * j5;
                long j7 = j6 + 2;
                int i36 = (j5 != 4503599627370496L || i35 <= 1) ? 1 : 0;
                long j8 = (j6 - 1) - i36;
                int i37 = i2 - 2;
                if (i37 >= 0) {
                    int max = Math.max(0, ((int) ((i37 * 3010299) / 10000000)) - 1);
                    int i38 = ((((-i37) + max) + (((max == 0 ? 1 : (int) ((((max * 23219280) + 10000000) - 1) / 10000000)) + 122) - 1)) - 93) - 21;
                    if (i38 >= 0) {
                        int[] iArr = POW5_INV_SPLIT[max];
                        long j9 = j6 >>> 31;
                        long j10 = j6 & 2147483647L;
                        z2 = z6;
                        z = z5;
                        j2 = ((((((((((((j10 * iArr[3]) >>> 31) + (iArr[2] * j10)) + (j9 * iArr[3])) >>> 31) + (iArr[1] * j10)) + (iArr[2] * j9)) >>> 31) + (iArr[0] * j10)) + (iArr[1] * j9)) >>> 21) + ((iArr[0] * j9) << 10)) >>> i38;
                        long j11 = j7 >>> 31;
                        long j12 = j7 & 2147483647L;
                        j = ((((((((((((j12 * iArr[3]) >>> 31) + (iArr[2] * j12)) + (j11 * iArr[3])) >>> 31) + (iArr[1] * j12)) + (iArr[2] * j11)) >>> 31) + (iArr[0] * j12)) + (iArr[1] * j11)) >>> 21) + ((iArr[0] * j11) << 10)) >>> i38;
                        long j13 = j8 >>> 31;
                        long j14 = j8 & 2147483647L;
                        j3 = ((((((((((((j14 * iArr[3]) >>> 31) + (iArr[2] * j14)) + (j13 * iArr[3])) >>> 31) + (iArr[1] * j14)) + (iArr[2] * j13)) >>> 31) + (iArr[0] * j14)) + (iArr[1] * j13)) >>> 21) + ((iArr[0] * j13) << 10)) >>> i38;
                        if (max <= 21) {
                            int i39 = ((j6 % 5) > 0L ? 1 : ((j6 % 5) == 0L ? 0 : -1));
                            if (i39 == 0) {
                                if (i39 != 0) {
                                    i11 = 0;
                                } else if (j6 % 25 != 0) {
                                    i11 = 1;
                                } else if (j6 % 125 != 0) {
                                    i11 = 2;
                                } else if (j6 % 625 != 0) {
                                    i11 = 3;
                                } else {
                                    i11 = 4;
                                    long j15 = j6 / 625;
                                    for (long j16 = 0; j15 > j16 && j15 % 5 == j16; j16 = 0) {
                                        j15 /= 5;
                                        i11++;
                                    }
                                }
                                z4 = i11 >= max;
                                z3 = false;
                            } else if (z2) {
                                if (j8 % 5 != 0) {
                                    i10 = 0;
                                } else if (j8 % 25 != 0) {
                                    i10 = 1;
                                } else if (j8 % 125 != 0) {
                                    i10 = 2;
                                } else if (j8 % 625 != 0) {
                                    i10 = 3;
                                } else {
                                    i10 = 4;
                                    long j17 = j8 / 625;
                                    for (long j18 = 0; j17 > j18 && j17 % 5 == j18; j18 = 0) {
                                        j17 /= 5;
                                        i10++;
                                    }
                                }
                                z3 = i10 >= max;
                                z4 = false;
                            } else {
                                if (j7 % 5 != 0) {
                                    i9 = 0;
                                } else if (j7 % 25 != 0) {
                                    i9 = 1;
                                } else if (j7 % 125 != 0) {
                                    i9 = 2;
                                } else if (j7 % 625 != 0) {
                                    i9 = 3;
                                } else {
                                    i9 = 4;
                                    long j19 = j7 / 625;
                                    for (long j20 = 0; j19 > j20 && j19 % 5 == j20; j20 = 0) {
                                        j19 /= 5;
                                        i9++;
                                    }
                                }
                                if (i9 >= max) {
                                    j--;
                                    z4 = false;
                                    z3 = false;
                                }
                            }
                            i3 = max;
                        }
                        z4 = false;
                        z3 = false;
                        i3 = max;
                    } else {
                        throw new IllegalArgumentException("" + i38);
                    }
                } else {
                    z = z5;
                    z2 = z6;
                    int i40 = -i37;
                    int max2 = Math.max(0, ((int) ((i40 * 6989700) / 10000000)) - 1);
                    int i41 = i40 - max2;
                    int i42 = ((max2 - ((i41 == 0 ? 1 : (int) ((((i41 * 23219280) + 10000000) - 1) / 10000000)) - 121)) - 93) - 21;
                    if (i42 >= 0) {
                        int[] iArr2 = POW5_SPLIT[i41];
                        long j21 = j6 >>> 31;
                        long j22 = j6 & 2147483647L;
                        j2 = ((((((((((((j22 * iArr2[3]) >>> 31) + (iArr2[2] * j22)) + (j21 * iArr2[3])) >>> 31) + (iArr2[1] * j22)) + (iArr2[2] * j21)) >>> 31) + (iArr2[0] * j22)) + (iArr2[1] * j21)) >>> 21) + ((iArr2[0] * j21) << 10)) >>> i42;
                        long j23 = j7 >>> 31;
                        long j24 = j7 & 2147483647L;
                        j = ((((((((((((j24 * iArr2[3]) >>> 31) + (iArr2[2] * j24)) + (j23 * iArr2[3])) >>> 31) + (iArr2[1] * j24)) + (iArr2[2] * j23)) >>> 31) + (iArr2[0] * j24)) + (iArr2[1] * j23)) >>> 21) + ((iArr2[0] * j23) << 10)) >>> i42;
                        long j25 = j8 >>> 31;
                        long j26 = j8 & 2147483647L;
                        j3 = ((((((((((((j26 * iArr2[3]) >>> 31) + (iArr2[2] * j26)) + (j25 * iArr2[3])) >>> 31) + (iArr2[1] * j26)) + (iArr2[2] * j25)) >>> 31) + (iArr2[0] * j26)) + (iArr2[1] * j25)) >>> 21) + ((iArr2[0] * j25) << 10)) >>> i42;
                        i3 = i37 + max2;
                        z4 = true;
                        if (max2 <= 1) {
                            if (z2) {
                                z3 = i36 == 1;
                            } else {
                                j--;
                                z3 = false;
                            }
                        } else if (max2 < 63) {
                            z4 = (j6 & ((1 << (max2 - 1)) - 1)) == 0;
                            z3 = false;
                        } else {
                            z4 = false;
                            z3 = false;
                        }
                    } else {
                        throw new IllegalArgumentException("" + i42);
                    }
                }
                int i43 = j >= 1000000000000000000L ? 19 : j >= 100000000000000000L ? 18 : j >= 10000000000000000L ? 17 : j >= 1000000000000000L ? 16 : j >= 100000000000000L ? 15 : j >= 10000000000000L ? 14 : j >= 1000000000000L ? 13 : j >= 100000000000L ? 12 : j >= RealConnection.IDLE_CONNECTION_HEALTHY_NS ? 11 : j >= C.NANOS_PER_SECOND ? 10 : j >= 100000000 ? 9 : j >= 10000000 ? 8 : j >= 1000000 ? 7 : j >= 100000 ? 6 : j >= 10000 ? 5 : j >= 1000 ? 4 : j >= 100 ? 3 : j >= 10 ? 2 : 1;
                int i44 = (i3 + i43) - 1;
                boolean z7 = i44 < -3 || i44 >= 7;
                if (z3 || z4) {
                    int i45 = 0;
                    i4 = 0;
                    while (true) {
                        long j27 = j / 10;
                        long j28 = j3 / 10;
                        if (j27 <= j28 || (j < 100 && z7)) {
                            break;
                        }
                        z3 &= j3 % 10 == 0;
                        z4 &= i45 == 0;
                        i45 = (int) (j2 % 10);
                        j2 /= 10;
                        i4++;
                        j = j27;
                        j3 = j28;
                    }
                    if (z3 && z2) {
                        while (j3 % 10 == 0 && (j >= 100 || !z7)) {
                            z4 &= i45 == 0;
                            i45 = (int) (j2 % 10);
                            j /= 10;
                            j2 /= 10;
                            j3 /= 10;
                            i4++;
                        }
                    }
                    if (z4 && i45 == 5 && j2 % 2 == 0) {
                        i45 = 4;
                    }
                    j4 = j2 + (((j2 != j3 || (z3 && z2)) && i45 < 5) ? 0 : 1);
                } else {
                    i4 = 0;
                    int i46 = 0;
                    while (true) {
                        long j29 = j / 10;
                        long j30 = j3 / 10;
                        if (j29 <= j30 || (j < 100 && z7)) {
                            break;
                        }
                        i46 = (int) (j2 % 10);
                        j2 /= 10;
                        i4++;
                        j = j29;
                        j3 = j30;
                    }
                    j4 = j2 + ((j2 == j3 || i46 >= 5) ? 1 : 0);
                }
                int i47 = i43 - i4;
                if (z) {
                    i5 = i + 1;
                    cArr[i] = '-';
                } else {
                    i5 = i;
                }
                if (z7) {
                    for (int i48 = 0; i48 < i47 - 1; i48++) {
                        j4 /= 10;
                        cArr[(i5 + i47) - i48] = (char) (((int) (j4 % 10)) + 48);
                    }
                    cArr[i5] = (char) ((j4 % 10) + 48);
                    cArr[i5 + 1] = '.';
                    int i49 = i5 + i47 + 1;
                    if (i47 == 1) {
                        i49++;
                        cArr[i49] = '0';
                    }
                    int i50 = i49 + 1;
                    cArr[i49] = 'E';
                    if (i44 < 0) {
                        i7 = i50 + 1;
                        cArr[i50] = '-';
                        i44 = -i44;
                    } else {
                        i7 = i50;
                    }
                    if (i44 >= 100) {
                        int i51 = i7 + 1;
                        i8 = 48;
                        cArr[i7] = (char) ((i44 / 100) + 48);
                        i44 %= 100;
                        i7 = i51 + 1;
                        cArr[i51] = (char) ((i44 / 10) + 48);
                    } else {
                        i8 = 48;
                        if (i44 >= 10) {
                            i7++;
                            cArr[i7] = (char) ((i44 / 10) + 48);
                        }
                    }
                    cArr[i7] = (char) ((i44 % 10) + i8);
                    return (i7 + 1) - i;
                }
                char c = '0';
                if (i44 < 0) {
                    int i52 = i5 + 1;
                    cArr[i5] = '0';
                    int i53 = i52 + 1;
                    cArr[i52] = '.';
                    int i54 = -1;
                    while (i54 > i44) {
                        i53++;
                        cArr[i53] = c;
                        i54--;
                        c = '0';
                    }
                    i6 = i53;
                    for (int i55 = 0; i55 < i47; i55++) {
                        cArr[((i53 + i47) - i55) - 1] = (char) ((j4 % 10) + 48);
                        j4 /= 10;
                        i6++;
                    }
                } else {
                    int i56 = i44 + 1;
                    if (i56 >= i47) {
                        for (int i57 = 0; i57 < i47; i57++) {
                            cArr[((i5 + i47) - i57) - 1] = (char) ((j4 % 10) + 48);
                            j4 /= 10;
                        }
                        int i58 = i5 + i47;
                        while (i47 < i56) {
                            i58++;
                            cArr[i58] = '0';
                            i47++;
                        }
                        int i59 = i58 + 1;
                        cArr[i58] = '.';
                        i6 = i59 + 1;
                        cArr[i59] = '0';
                    } else {
                        int i60 = i5 + 1;
                        for (int i61 = 0; i61 < i47; i61++) {
                            if ((i47 - i61) - 1 == i44) {
                                cArr[((i60 + i47) - i61) - 1] = '.';
                                i60--;
                            }
                            cArr[((i60 + i47) - i61) - 1] = (char) ((j4 % 10) + 48);
                            j4 /= 10;
                        }
                        i6 = i5 + i47 + 1;
                    }
                }
                return i6 - i;
            }
        }
    }
}
