package com.alibaba.fastjson.util;

/* loaded from: classes.dex */
public final class RyuFloat {
    private static final int[][] POW5_SPLIT = {new int[]{536870912, 0}, new int[]{671088640, 0}, new int[]{838860800, 0}, new int[]{1048576000, 0}, new int[]{655360000, 0}, new int[]{819200000, 0}, new int[]{1024000000, 0}, new int[]{640000000, 0}, new int[]{800000000, 0}, new int[]{1000000000, 0}, new int[]{625000000, 0}, new int[]{781250000, 0}, new int[]{976562500, 0}, new int[]{610351562, 1073741824}, new int[]{762939453, 268435456}, new int[]{953674316, 872415232}, new int[]{596046447, 1619001344}, new int[]{745058059, 1486880768}, new int[]{931322574, 1321730048}, new int[]{582076609, 289210368}, new int[]{727595761, 898383872}, new int[]{909494701, 1659850752}, new int[]{568434188, 1305842176}, new int[]{710542735, 1632302720}, new int[]{888178419, 1503507488}, new int[]{555111512, 671256724}, new int[]{693889390, 839070905}, new int[]{867361737, 2122580455}, new int[]{542101086, 521306416}, new int[]{677626357, 1725374844}, new int[]{847032947, 546105819}, new int[]{1058791184, 145761362}, new int[]{661744490, 91100851}, new int[]{827180612, 1187617888}, new int[]{1033975765, 1484522360}, new int[]{646234853, 1196261931}, new int[]{807793566, 2032198326}, new int[]{1009741958, 1466506084}, new int[]{631088724, 379695390}, new int[]{788860905, 474619238}, new int[]{986076131, 1130144959}, new int[]{616297582, 437905143}, new int[]{770371977, 1621123253}, new int[]{962964972, 415791331}, new int[]{601853107, 1333611405}, new int[]{752316384, 1130143345}, new int[]{940395480, 1412679181}};
    private static final int[][] POW5_INV_SPLIT = {new int[]{268435456, 1}, new int[]{214748364, 1717986919}, new int[]{171798691, 1803886265}, new int[]{137438953, 1013612282}, new int[]{219902325, 1192282922}, new int[]{175921860, 953826338}, new int[]{140737488, 763061070}, new int[]{225179981, 791400982}, new int[]{180143985, 203624056}, new int[]{144115188, 162899245}, new int[]{230584300, 1978625710}, new int[]{184467440, 1582900568}, new int[]{147573952, 1266320455}, new int[]{236118324, 308125809}, new int[]{188894659, 675997377}, new int[]{151115727, 970294631}, new int[]{241785163, 1981968139}, new int[]{193428131, 297084323}, new int[]{154742504, 1955654377}, new int[]{247588007, 1840556814}, new int[]{198070406, 613451992}, new int[]{158456325, 61264864}, new int[]{253530120, 98023782}, new int[]{202824096, 78419026}, new int[]{162259276, 1780722139}, new int[]{259614842, 1990161963}, new int[]{207691874, 733136111}, new int[]{166153499, 1016005619}, new int[]{265845599, 337118801}, new int[]{212676479, 699191770}, new int[]{170141183, 988850146}};

    public static String toString(float f) {
        char[] cArr = new char[15];
        return new String(cArr, 0, toString(f, cArr, 0));
    }

    public static int toString(float f, char[] cArr, int i) {
        int i2;
        boolean z;
        int i3;
        int i4;
        boolean z2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        if (Float.isNaN(f)) {
            int i20 = i + 1;
            cArr[i] = 'N';
            int i21 = i20 + 1;
            cArr[i20] = 'a';
            cArr[i21] = 'N';
            return (i21 + 1) - i;
        } else if (f == Float.POSITIVE_INFINITY) {
            int i22 = i + 1;
            cArr[i] = 'I';
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
        } else if (f == Float.NEGATIVE_INFINITY) {
            int i29 = i + 1;
            cArr[i] = '-';
            int i30 = i29 + 1;
            cArr[i29] = 'I';
            int i31 = i30 + 1;
            cArr[i30] = 'n';
            int i32 = i31 + 1;
            cArr[i31] = 'f';
            int i33 = i32 + 1;
            cArr[i32] = 'i';
            int i34 = i33 + 1;
            cArr[i33] = 'n';
            int i35 = i34 + 1;
            cArr[i34] = 'i';
            int i36 = i35 + 1;
            cArr[i35] = 't';
            cArr[i36] = 'y';
            return (i36 + 1) - i;
        } else {
            int floatToIntBits = Float.floatToIntBits(f);
            if (floatToIntBits == 0) {
                int i37 = i + 1;
                cArr[i] = '0';
                int i38 = i37 + 1;
                cArr[i37] = '.';
                cArr[i38] = '0';
                return (i38 + 1) - i;
            } else if (floatToIntBits == Integer.MIN_VALUE) {
                int i39 = i + 1;
                cArr[i] = '-';
                int i40 = i39 + 1;
                cArr[i39] = '0';
                int i41 = i40 + 1;
                cArr[i40] = '.';
                cArr[i41] = '0';
                return (i41 + 1) - i;
            } else {
                int i42 = (floatToIntBits >> 23) & 255;
                int i43 = 8388607 & floatToIntBits;
                if (i42 == 0) {
                    i2 = -149;
                } else {
                    i2 = (i42 - 127) - 23;
                    i43 |= 8388608;
                }
                boolean z3 = floatToIntBits < 0;
                boolean z4 = (i43 & 1) == 0;
                int i44 = i43 * 4;
                int i45 = i44 + 2;
                int i46 = i44 - ((((long) i43) != 8388608 || i42 <= 1) ? 2 : 1);
                int i47 = i2 - 2;
                if (i47 >= 0) {
                    int i48 = (int) ((i47 * 3010299) / 10000000);
                    if (i48 == 0) {
                        i19 = i47;
                        i18 = 1;
                    } else {
                        i19 = i47;
                        i18 = (int) ((((i48 * 23219280) + 10000000) - 1) / 10000000);
                    }
                    int i49 = (-i19) + i48;
                    int[][] iArr = POW5_INV_SPLIT;
                    long j = iArr[i48][0];
                    long j2 = iArr[i48][1];
                    long j3 = i44;
                    int i50 = (((i18 + 59) - 1) + i49) - 31;
                    i4 = (int) (((j3 * j) + ((j3 * j2) >> 31)) >> i50);
                    z = z4;
                    long j4 = i45;
                    i5 = (int) (((j4 * j) + ((j4 * j2) >> 31)) >> i50);
                    long j5 = i46;
                    i7 = (int) (((j * j5) + ((j5 * j2) >> 31)) >> i50);
                    if (i48 == 0 || (i5 - 1) / 10 > i7 / 10) {
                        i6 = 0;
                    } else {
                        int i51 = i48 - 1;
                        int i52 = (i49 - 1) + (((i51 == 0 ? 1 : (int) ((((i51 * 23219280) + 10000000) - 1) / 10000000)) + 59) - 1);
                        int[][] iArr2 = POW5_INV_SPLIT;
                        i6 = (int) ((((iArr2[i51][0] * j3) + ((j3 * iArr2[i51][1]) >> 31)) >> (i52 - 31)) % 10);
                    }
                    int i53 = 0;
                    while (i45 > 0 && i45 % 5 == 0) {
                        i45 /= 5;
                        i53++;
                    }
                    int i54 = 0;
                    while (i44 > 0 && i44 % 5 == 0) {
                        i44 /= 5;
                        i54++;
                    }
                    int i55 = 0;
                    while (i46 > 0 && i46 % 5 == 0) {
                        i46 /= 5;
                        i55++;
                    }
                    i10 = i53 >= i48 ? 1 : 0;
                    i9 = i54 >= i48 ? 1 : 0;
                    i8 = i55 >= i48 ? 1 : 0;
                    i3 = 0;
                    z2 = z3;
                    i11 = i48;
                } else {
                    z = z4;
                    int i56 = -i47;
                    int i57 = (int) ((i56 * 6989700) / 10000000);
                    int i58 = i56 - i57;
                    int i59 = i58 == 0 ? 1 : (int) ((((i58 * 23219280) + 10000000) - 1) / 10000000);
                    int[][] iArr3 = POW5_SPLIT;
                    long j6 = iArr3[i58][0];
                    long j7 = iArr3[i58][1];
                    int i60 = (i57 - (i59 - 61)) - 31;
                    z2 = z3;
                    long j8 = i44;
                    i4 = (int) (((j8 * j6) + ((j8 * j7) >> 31)) >> i60);
                    long j9 = i45;
                    int i61 = (int) (((j9 * j6) + ((j9 * j7) >> 31)) >> i60);
                    long j10 = i46;
                    i7 = (int) (((j6 * j10) + ((j10 * j7) >> 31)) >> i60);
                    if (i57 == 0 || (i61 - 1) / 10 > i7 / 10) {
                        i3 = 0;
                        i6 = 0;
                    } else {
                        int i62 = i58 + 1;
                        int[][] iArr4 = POW5_SPLIT;
                        i3 = 0;
                        i6 = (int) ((((iArr4[i62][0] * j8) + ((j8 * iArr4[i62][1]) >> 31)) >> (((i57 - 1) - ((i62 == 0 ? 1 : (int) ((((i62 * 23219280) + 10000000) - 1) / 10000000)) - 61)) - 31)) % 10);
                    }
                    i11 = i57 + i47;
                    i10 = 1 >= i57 ? 1 : i3;
                    i9 = (i57 >= 23 || (((1 << (i57 + (-1))) - 1) & i44) != 0) ? i3 : 1;
                    i8 = (i46 % 2 == 1 ? i3 : 1) >= i57 ? 1 : i3;
                    i5 = i61;
                }
                int i63 = 1000000000;
                int i64 = 10;
                while (i64 > 0 && i5 < i63) {
                    i63 /= 10;
                    i64--;
                }
                int i65 = (i11 + i64) - 1;
                int i66 = (i65 < -3 || i65 >= 7) ? 1 : i3;
                if (i10 == 0 || z) {
                    i12 = i3;
                } else {
                    i5--;
                    i12 = i3;
                }
                while (true) {
                    int i67 = i5 / 10;
                    int i68 = i7 / 10;
                    if (i67 <= i68 || (i5 < 100 && i66 != 0)) {
                        break;
                    }
                    i8 &= i7 % 10 == 0 ? 1 : i3;
                    i6 = i4 % 10;
                    i4 /= 10;
                    i12++;
                    i5 = i67;
                    i7 = i68;
                }
                if (i8 != 0 && z) {
                    while (i7 % 10 == 0 && (i5 >= 100 || i66 == 0)) {
                        i5 /= 10;
                        i6 = i4 % 10;
                        i4 /= 10;
                        i7 /= 10;
                        i12++;
                    }
                }
                if (i9 != 0 && i6 == 5 && i4 % 2 == 0) {
                    i6 = 4;
                }
                int i69 = i4 + (((i4 != i7 || (i8 != 0 && z)) && i6 < 5) ? i3 : 1);
                int i70 = i64 - i12;
                if (z2) {
                    i13 = i + 1;
                    cArr[i] = '-';
                } else {
                    i13 = i;
                }
                if (i66 != 0) {
                    while (i3 < i70 - 1) {
                        int i71 = i69 % 10;
                        i69 /= 10;
                        cArr[(i13 + i70) - i3] = (char) (i71 + 48);
                        i3++;
                    }
                    cArr[i13] = (char) ((i69 % 10) + 48);
                    cArr[i13 + 1] = '.';
                    int i72 = i13 + i70 + 1;
                    if (i70 == 1) {
                        i72++;
                        cArr[i72] = '0';
                    }
                    int i73 = i72 + 1;
                    cArr[i72] = 'E';
                    if (i65 < 0) {
                        i16 = i73 + 1;
                        cArr[i73] = '-';
                        i65 = -i65;
                        i15 = 10;
                    } else {
                        i16 = i73;
                        i15 = 10;
                    }
                    if (i65 >= i15) {
                        i16++;
                        i17 = 48;
                        cArr[i16] = (char) ((i65 / 10) + 48);
                    } else {
                        i17 = 48;
                    }
                    i14 = i16 + 1;
                    cArr[i16] = (char) ((i65 % 10) + i17);
                } else {
                    int i74 = 48;
                    if (i65 < 0) {
                        int i75 = i13 + 1;
                        cArr[i13] = '0';
                        int i76 = i75 + 1;
                        cArr[i75] = '.';
                        for (int i77 = -1; i77 > i65; i77--) {
                            i76++;
                            cArr[i76] = '0';
                        }
                        i14 = i76;
                        while (i3 < i70) {
                            cArr[((i76 + i70) - i3) - 1] = (char) ((i69 % 10) + i74);
                            i69 /= 10;
                            i14++;
                            i3++;
                            i74 = 48;
                        }
                    } else {
                        int i78 = i65 + 1;
                        if (i78 >= i70) {
                            while (i3 < i70) {
                                cArr[((i13 + i70) - i3) - 1] = (char) ((i69 % 10) + 48);
                                i69 /= 10;
                                i3++;
                            }
                            int i79 = i13 + i70;
                            while (i70 < i78) {
                                i79++;
                                cArr[i79] = '0';
                                i70++;
                            }
                            int i80 = i79 + 1;
                            cArr[i79] = '.';
                            i14 = i80 + 1;
                            cArr[i80] = '0';
                        } else {
                            int i81 = i13 + 1;
                            while (i3 < i70) {
                                if ((i70 - i3) - 1 == i65) {
                                    cArr[((i81 + i70) - i3) - 1] = '.';
                                    i81--;
                                }
                                cArr[((i81 + i70) - i3) - 1] = (char) ((i69 % 10) + 48);
                                i69 /= 10;
                                i3++;
                            }
                            i14 = i13 + i70 + 1;
                        }
                    }
                }
                return i14 - i;
            }
        }
    }
}
