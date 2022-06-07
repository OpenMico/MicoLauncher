package java8.util;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import java.util.Arrays;
import java8.util.concurrent.CountedCompleter;
import java8.util.concurrent.RecursiveTask;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DualPivotQuicksort.java */
/* loaded from: classes5.dex */
public final class p {
    private static int a(int i, int i2) {
        int i3 = 0;
        while (true) {
            i >>= 3;
            if (i <= 0 || (i2 = i2 >> 2) <= 0) {
                break;
            }
            i3 -= 2;
        }
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(int[] iArr, int i, int i2, int i3) {
        int i4 = i3 - i2;
        if (i <= 1 || i4 <= 4096) {
            a((c) null, iArr, 0, i2, i3);
            return;
        }
        int a2 = a(i, i4 >> 12);
        new c(null, iArr, a2 == 0 ? null : new int[i4], i2, i4, i2, a2).invoke();
    }

    static void a(c cVar, int[] iArr, int i, int i2, int i3) {
        while (true) {
            int i4 = i3 - 1;
            int i5 = i3 - i2;
            if (i5 < i + 65 && (i & 1) > 0) {
                b(iArr, i2, i3 - (((i5 >> 5) << 3) * 3), i3);
                return;
            } else if (i5 < 44) {
                a(iArr, i2, i3);
                return;
            } else if ((i != 0 && (i5 <= 4096 || (i & 1) <= 0)) || !a(cVar, iArr, i2, i5)) {
                i += 6;
                if (i > 384) {
                    b(iArr, i2, i3);
                    return;
                }
                int i6 = ((i5 >> 3) * 3) + 3;
                int i7 = i2 + i6;
                int i8 = i4 - i6;
                int i9 = (i7 + i8) >>> 1;
                int i10 = (i7 + i9) >>> 1;
                int i11 = (i9 + i8) >>> 1;
                int i12 = iArr[i9];
                if (iArr[i8] < iArr[i10]) {
                    int i13 = iArr[i8];
                    iArr[i8] = iArr[i10];
                    iArr[i10] = i13;
                }
                if (iArr[i11] < iArr[i7]) {
                    int i14 = iArr[i11];
                    iArr[i11] = iArr[i7];
                    iArr[i7] = i14;
                }
                if (iArr[i8] < iArr[i11]) {
                    int i15 = iArr[i8];
                    iArr[i8] = iArr[i11];
                    iArr[i11] = i15;
                }
                if (iArr[i10] < iArr[i7]) {
                    int i16 = iArr[i10];
                    iArr[i10] = iArr[i7];
                    iArr[i7] = i16;
                }
                if (iArr[i11] < iArr[i10]) {
                    int i17 = iArr[i11];
                    iArr[i11] = iArr[i10];
                    iArr[i10] = i17;
                }
                if (i12 < iArr[i10]) {
                    if (i12 < iArr[i7]) {
                        iArr[i9] = iArr[i10];
                        iArr[i10] = iArr[i7];
                        iArr[i7] = i12;
                    } else {
                        iArr[i9] = iArr[i10];
                        iArr[i10] = i12;
                    }
                } else if (i12 > iArr[i11]) {
                    if (i12 > iArr[i8]) {
                        iArr[i9] = iArr[i11];
                        iArr[i11] = iArr[i8];
                        iArr[i8] = i12;
                    } else {
                        iArr[i9] = iArr[i11];
                        iArr[i11] = i12;
                    }
                }
                if (iArr[i7] >= iArr[i10] || iArr[i10] >= iArr[i9] || iArr[i9] >= iArr[i11] || iArr[i11] >= iArr[i8]) {
                    int i18 = iArr[i9];
                    iArr[i9] = iArr[i2];
                    int i19 = i4 + 1;
                    int i20 = i2;
                    int i21 = i19;
                    while (true) {
                        i19--;
                        if (i19 <= i20) {
                            break;
                        }
                        int i22 = iArr[i19];
                        if (i22 != i18) {
                            iArr[i19] = i18;
                            if (i22 < i18) {
                                do {
                                    i20++;
                                } while (iArr[i20] < i18);
                                if (iArr[i20] > i18) {
                                    i21--;
                                    iArr[i21] = iArr[i20];
                                }
                                iArr[i20] = i22;
                            } else {
                                i21--;
                                iArr[i21] = i22;
                            }
                        }
                    }
                    iArr[i2] = iArr[i20];
                    iArr[i20] = i18;
                    if (i5 <= 4096 || cVar == null) {
                        a(cVar, iArr, i | 1, i21, i3);
                    } else {
                        cVar.a(i | 1, i21, i3);
                    }
                    i3 = i20;
                } else {
                    int i23 = iArr[i7];
                    int i24 = iArr[i8];
                    iArr[i7] = iArr[i2];
                    iArr[i8] = iArr[i4];
                    int i25 = i2;
                    do {
                        i25++;
                    } while (iArr[i25] < i23);
                    int i26 = i4;
                    do {
                        i26--;
                    } while (iArr[i26] > i24);
                    int i27 = i25 - 1;
                    int i28 = i26 + 1;
                    int i29 = i28;
                    while (true) {
                        i28--;
                        if (i28 <= i27) {
                            break;
                        }
                        int i30 = iArr[i28];
                        if (i30 < i23) {
                            while (true) {
                                if (i27 < i28) {
                                    i27++;
                                    if (iArr[i27] >= i23) {
                                        if (iArr[i27] > i24) {
                                            i29--;
                                            iArr[i28] = iArr[i29];
                                            iArr[i29] = iArr[i27];
                                        } else {
                                            iArr[i28] = iArr[i27];
                                        }
                                        iArr[i27] = i30;
                                    }
                                }
                            }
                        } else if (i30 > i24) {
                            i29--;
                            iArr[i28] = iArr[i29];
                            iArr[i29] = i30;
                        }
                    }
                    iArr[i2] = iArr[i27];
                    iArr[i27] = i23;
                    iArr[i4] = iArr[i29];
                    iArr[i29] = i24;
                    if (i5 <= 4096 || cVar == null) {
                        int i31 = i | 1;
                        a(cVar, iArr, i31, i27 + 1, i29);
                        a(cVar, iArr, i31, i29 + 1, i3);
                    } else {
                        int i32 = i | 1;
                        cVar.a(i32, i27 + 1, i29);
                        cVar.a(i32, i29 + 1, i3);
                    }
                    i3 = i27;
                }
            } else {
                return;
            }
        }
    }

    private static void b(int[] iArr, int i, int i2, int i3) {
        int i4;
        if (i2 == i3) {
            while (true) {
                i++;
                if (i < i2) {
                    int i5 = iArr[i];
                    int i6 = i;
                    while (true) {
                        i6--;
                        if (i5 < iArr[i6]) {
                            iArr[i6 + 1] = iArr[i6];
                        }
                    }
                    iArr[i6 + 1] = i5;
                } else {
                    return;
                }
            }
        } else {
            int i7 = iArr[i2];
            int i8 = i3;
            while (true) {
                i++;
                if (i < i2) {
                    int i9 = iArr[i];
                    if (i9 < iArr[i - 1]) {
                        int i10 = i - 1;
                        iArr[i] = iArr[i10];
                        while (true) {
                            i10--;
                            if (i9 >= iArr[i10]) {
                                break;
                            }
                            iArr[i10 + 1] = iArr[i10];
                        }
                        iArr[i10 + 1] = i9;
                    } else if (i8 > i && i9 > i7) {
                        do {
                            i8--;
                        } while (iArr[i8] > i7);
                        if (i8 > i) {
                            i9 = iArr[i8];
                            iArr[i8] = iArr[i];
                            i4 = i;
                        } else {
                            i4 = i;
                        }
                        while (true) {
                            i4--;
                            if (i9 >= iArr[i4]) {
                                break;
                            }
                            iArr[i4 + 1] = iArr[i4];
                        }
                        iArr[i4 + 1] = i9;
                    }
                }
            }
            while (i < i3) {
                int i11 = iArr[i];
                int i12 = i + 1;
                int i13 = iArr[i12];
                if (i11 > i13) {
                    while (true) {
                        i--;
                        if (i11 >= iArr[i]) {
                            break;
                        }
                        iArr[i + 2] = iArr[i];
                    }
                    int i14 = i + 1;
                    iArr[i14 + 1] = i11;
                    while (true) {
                        i14--;
                        if (i13 >= iArr[i14]) {
                            break;
                        }
                        iArr[i14 + 1] = iArr[i14];
                    }
                    iArr[i14 + 1] = i13;
                } else if (i11 < iArr[i - 1]) {
                    while (true) {
                        i--;
                        if (i13 >= iArr[i]) {
                            break;
                        }
                        iArr[i + 2] = iArr[i];
                    }
                    int i15 = i + 1;
                    iArr[i15 + 1] = i13;
                    while (true) {
                        i15--;
                        if (i11 >= iArr[i15]) {
                            break;
                        }
                        iArr[i15 + 1] = iArr[i15];
                    }
                    iArr[i15 + 1] = i11;
                }
                i = i12 + 1;
            }
        }
    }

    private static void a(int[] iArr, int i, int i2) {
        int i3 = i;
        while (true) {
            i3++;
            if (i3 < i2) {
                int i4 = iArr[i3];
                if (i4 < iArr[i3 - 1]) {
                    int i5 = i3;
                    while (true) {
                        i5--;
                        if (i5 < i || i4 >= iArr[i5]) {
                            break;
                        }
                        iArr[i5 + 1] = iArr[i5];
                    }
                    iArr[i5 + 1] = i4;
                }
            } else {
                return;
            }
        }
    }

    private static void b(int[] iArr, int i, int i2) {
        int i3 = (i + i2) >>> 1;
        while (i3 > i) {
            i3--;
            a(iArr, i3, iArr[i3], i, i2);
        }
        while (true) {
            i2--;
            if (i2 > i) {
                int i4 = iArr[i];
                a(iArr, i, iArr[i2], i, i2);
                iArr[i2] = i4;
            } else {
                return;
            }
        }
    }

    private static void a(int[] iArr, int i, int i2, int i3, int i4) {
        while (true) {
            int i5 = ((i << 1) - i3) + 2;
            if (i5 > i4) {
                break;
            }
            if (i5 == i4 || iArr[i5] < iArr[i5 - 1]) {
                i5--;
            }
            if (iArr[i5] <= i2) {
                break;
            }
            iArr[i] = iArr[i5];
            i = i5;
        }
        iArr[i] = i2;
    }

    private static boolean a(c cVar, int[] iArr, int i, int i2) {
        int[] iArr2;
        int i3;
        int[] iArr3;
        int i4 = i + i2;
        int i5 = i + 1;
        int[] iArr4 = null;
        int i6 = 1;
        int i7 = i;
        while (i5 < i4) {
            int i8 = i5 - 1;
            if (iArr[i8] < iArr[i5]) {
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (iArr[i5 - 1] <= iArr[i5]);
            } else if (iArr[i8] > iArr[i5]) {
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (iArr[i5 - 1] >= iArr[i5]);
                int i9 = i7 - 1;
                int i10 = i5;
                while (true) {
                    i9++;
                    i10--;
                    if (i9 >= i10 || iArr[i9] <= iArr[i10]) {
                        break;
                    }
                    int i11 = iArr[i9];
                    iArr[i9] = iArr[i10];
                    iArr[i10] = i11;
                }
            } else {
                int i12 = iArr[i5];
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (i12 == iArr[i5]);
                if (i5 < i4) {
                    continue;
                }
            }
            if (iArr4 == null) {
                if (i5 == i4) {
                    return true;
                }
                if (i5 - i < 16) {
                    return false;
                }
                int[] iArr5 = new int[((i2 >> 10) | 127) & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES];
                iArr5[0] = i;
                iArr4 = iArr5;
            } else if (iArr[i7 - 1] > iArr[i7]) {
                if (i6 > ((i5 - i) >> 7) || (i6 = i6 + 1) == 5120) {
                    return false;
                }
                if (i6 == iArr4.length) {
                    iArr4 = Arrays.copyOf(iArr4, i6 << 1);
                }
            }
            iArr4[i6] = i5;
            i7 = i5;
        }
        if (i6 > 1) {
            if (cVar == null || (iArr3 = (int[]) cVar.b) == null) {
                iArr2 = new int[i2];
                i3 = i;
            } else {
                i3 = cVar.offset;
                iArr2 = iArr3;
            }
            a(iArr, iArr2, i3, 1, cVar != null, iArr4, 0, i6);
        }
        return true;
    }

    static int[] a(int[] iArr, int[] iArr2, int i, int i2, boolean z, int[] iArr3, int i3, int i4) {
        int i5;
        int[] iArr4;
        int[] iArr5;
        int i6 = i4 - i3;
        if (i6 != 1) {
            int i7 = i3;
            while (true) {
                i5 = i7 + 1;
                if (iArr3[i5 + 1] > ((iArr3[i3] + iArr3[i4]) >>> 1)) {
                    break;
                }
                i7 = i5;
            }
            if (!z || i6 <= 4) {
                iArr4 = a(iArr, iArr2, i, -i2, false, iArr3, i3, i5);
                iArr5 = a(iArr, iArr2, i, 0, false, iArr3, i5, i4);
            } else {
                b a2 = new b(iArr, iArr2, i, 0, iArr3, i5, i4).a();
                int[] a3 = a(iArr, iArr2, i, -i2, true, iArr3, i3, i5);
                iArr5 = (int[]) a2.b();
                iArr4 = a3;
            }
            int[] iArr6 = iArr4 == iArr ? iArr2 : iArr;
            int i8 = iArr4 == iArr ? iArr3[i3] - i : iArr3[i3];
            int i9 = iArr4 == iArr2 ? iArr3[i3] - i : iArr3[i3];
            int i10 = iArr4 == iArr2 ? iArr3[i5] - i : iArr3[i5];
            int i11 = iArr5 == iArr2 ? iArr3[i5] - i : iArr3[i5];
            int i12 = iArr5 == iArr2 ? iArr3[i4] - i : iArr3[i4];
            if (z) {
                new a(null, iArr6, i8, iArr4, i9, i10, iArr5, i11, i12).invoke();
            } else {
                a((a) null, iArr6, i8, iArr4, i9, i10, iArr5, i11, i12);
            }
            return iArr6;
        } else if (i2 >= 0) {
            return iArr;
        } else {
            int i13 = iArr3[i4];
            int i14 = i13 - i;
            int i15 = iArr3[i3];
            while (i13 > i15) {
                i14--;
                i13--;
                iArr2[i14] = iArr[i13];
            }
            return iArr2;
        }
    }

    static void a(a aVar, int[] iArr, int i, int[] iArr2, int i2, int i3, int[] iArr3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        if (aVar == null || iArr2 != iArr3) {
            i10 = i;
            i7 = i2;
            i9 = i3;
            i6 = i4;
            i8 = i5;
        } else {
            int i12 = i2;
            int i13 = i3;
            int i14 = i4;
            int i15 = i5;
            while (true) {
                if (i13 - i12 < i15 - i14) {
                    i8 = i13;
                    i6 = i12;
                    i9 = i15;
                    i7 = i14;
                } else {
                    i9 = i13;
                    i7 = i12;
                    i8 = i15;
                    i6 = i14;
                }
                if (i9 - i7 < 4096) {
                    break;
                }
                int i16 = (i7 + i9) >>> 1;
                int i17 = iArr2[i16];
                int i18 = i8;
                int i19 = i6;
                while (i19 < i18) {
                    int i20 = (i19 + i18) >>> 1;
                    if (i17 > iArr3[i20]) {
                        i19 = i20 + 1;
                    } else {
                        i18 = i20;
                    }
                }
                aVar.a(iArr, i + (((i18 - i6) + i16) - i7), iArr2, i16, i9, iArr3, i18, i8);
                i12 = i7;
                i14 = i6;
                i13 = i16;
                i15 = i18;
            }
            i10 = i;
        }
        while (i7 < i9 && i6 < i8) {
            i10++;
            if (iArr2[i7] < iArr3[i6]) {
                i7++;
                i11 = iArr2[i7];
            } else {
                i6++;
                i11 = iArr3[i6];
            }
            iArr[i10] = i11;
        }
        if (iArr != iArr2 || i10 < i7) {
            while (i7 < i9) {
                i10++;
                i7++;
                iArr[i10] = iArr2[i7];
            }
        }
        if (iArr != iArr3 || i10 < i6) {
            while (i6 < i8) {
                i10++;
                i6++;
                iArr[i10] = iArr3[i6];
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(long[] jArr, int i, int i2, int i3) {
        int i4 = i3 - i2;
        if (i <= 1 || i4 <= 4096) {
            a((c) null, jArr, 0, i2, i3);
            return;
        }
        int a2 = a(i, i4 >> 12);
        new c(null, jArr, a2 == 0 ? null : new long[i4], i2, i4, i2, a2).invoke();
    }

    static void a(c cVar, long[] jArr, int i, int i2, int i3) {
        int i4 = i;
        int i5 = i3;
        while (true) {
            int i6 = i5 - 1;
            int i7 = i5 - i2;
            if (i7 < i4 + 65 && (i4 & 1) > 0) {
                b(jArr, i2, i5 - (((i7 >> 5) << 3) * 3), i5);
                return;
            } else if (i7 < 44) {
                a(jArr, i2, i5);
                return;
            } else if ((i4 != 0 && (i7 <= 4096 || (i4 & 1) <= 0)) || !a(cVar, jArr, i2, i7)) {
                i4 += 6;
                if (i4 > 384) {
                    b(jArr, i2, i5);
                    return;
                }
                int i8 = ((i7 >> 3) * 3) + 3;
                int i9 = i2 + i8;
                int i10 = i6 - i8;
                int i11 = (i9 + i10) >>> 1;
                int i12 = (i9 + i11) >>> 1;
                int i13 = (i11 + i10) >>> 1;
                long j = jArr[i11];
                if (jArr[i10] < jArr[i12]) {
                    long j2 = jArr[i10];
                    jArr[i10] = jArr[i12];
                    jArr[i12] = j2;
                }
                if (jArr[i13] < jArr[i9]) {
                    long j3 = jArr[i13];
                    jArr[i13] = jArr[i9];
                    jArr[i9] = j3;
                }
                if (jArr[i10] < jArr[i13]) {
                    long j4 = jArr[i10];
                    jArr[i10] = jArr[i13];
                    jArr[i13] = j4;
                }
                if (jArr[i12] < jArr[i9]) {
                    long j5 = jArr[i12];
                    jArr[i12] = jArr[i9];
                    jArr[i9] = j5;
                }
                if (jArr[i13] < jArr[i12]) {
                    long j6 = jArr[i13];
                    jArr[i13] = jArr[i12];
                    jArr[i12] = j6;
                }
                if (j < jArr[i12]) {
                    if (j < jArr[i9]) {
                        jArr[i11] = jArr[i12];
                        jArr[i12] = jArr[i9];
                        jArr[i9] = j;
                    } else {
                        jArr[i11] = jArr[i12];
                        jArr[i12] = j;
                    }
                } else if (j > jArr[i13]) {
                    if (j > jArr[i10]) {
                        jArr[i11] = jArr[i13];
                        jArr[i13] = jArr[i10];
                        jArr[i10] = j;
                    } else {
                        jArr[i11] = jArr[i13];
                        jArr[i13] = j;
                    }
                }
                if (jArr[i9] >= jArr[i12] || jArr[i12] >= jArr[i11] || jArr[i11] >= jArr[i13] || jArr[i13] >= jArr[i10]) {
                    long j7 = jArr[i11];
                    jArr[i11] = jArr[i2];
                    int i14 = i6 + 1;
                    int i15 = i2;
                    int i16 = i14;
                    while (true) {
                        i14--;
                        if (i14 <= i15) {
                            break;
                        }
                        long j8 = jArr[i14];
                        int i17 = (j8 > j7 ? 1 : (j8 == j7 ? 0 : -1));
                        if (i17 != 0) {
                            jArr[i14] = j7;
                            if (i17 < 0) {
                                do {
                                    i15++;
                                } while (jArr[i15] < j7);
                                if (jArr[i15] > j7) {
                                    i16--;
                                    jArr[i16] = jArr[i15];
                                }
                                jArr[i15] = j8;
                            } else {
                                i16--;
                                jArr[i16] = j8;
                            }
                        }
                    }
                    jArr[i2] = jArr[i15];
                    jArr[i15] = j7;
                    if (i7 <= 4096 || cVar == null) {
                        a(cVar, jArr, i4 | 1, i16, i5);
                    } else {
                        cVar.a(i4 | 1, i16, i5);
                    }
                    i5 = i15;
                } else {
                    long j9 = jArr[i9];
                    long j10 = jArr[i10];
                    jArr[i9] = jArr[i2];
                    jArr[i10] = jArr[i6];
                    int i18 = i2;
                    do {
                        i18++;
                    } while (jArr[i18] < j9);
                    int i19 = i6;
                    do {
                        i19--;
                    } while (jArr[i19] > j10);
                    int i20 = i18 - 1;
                    int i21 = i19 + 1;
                    int i22 = i21;
                    while (true) {
                        i21--;
                        if (i21 <= i20) {
                            break;
                        }
                        long j11 = jArr[i21];
                        if (j11 < j9) {
                            while (true) {
                                if (i20 < i21) {
                                    i20++;
                                    if (jArr[i20] >= j9) {
                                        if (jArr[i20] > j10) {
                                            i22--;
                                            jArr[i21] = jArr[i22];
                                            jArr[i22] = jArr[i20];
                                        } else {
                                            jArr[i21] = jArr[i20];
                                        }
                                        jArr[i20] = j11;
                                    }
                                }
                            }
                        } else if (j11 > j10) {
                            i22--;
                            jArr[i21] = jArr[i22];
                            jArr[i22] = j11;
                        }
                    }
                    jArr[i2] = jArr[i20];
                    jArr[i20] = j9;
                    jArr[i6] = jArr[i22];
                    jArr[i22] = j10;
                    if (i7 <= 4096 || cVar == null) {
                        int i23 = i4 | 1;
                        a(cVar, jArr, i23, i20 + 1, i22);
                        a(cVar, jArr, i23, i22 + 1, i5);
                    } else {
                        int i24 = i4 | 1;
                        cVar.a(i24, i20 + 1, i22);
                        cVar.a(i24, i22 + 1, i5);
                    }
                    i5 = i20;
                }
            } else {
                return;
            }
        }
    }

    private static void b(long[] jArr, int i, int i2, int i3) {
        int i4;
        if (i2 == i3) {
            while (true) {
                i++;
                if (i < i2) {
                    long j = jArr[i];
                    int i5 = i;
                    while (true) {
                        i5--;
                        if (j < jArr[i5]) {
                            jArr[i5 + 1] = jArr[i5];
                        }
                    }
                    jArr[i5 + 1] = j;
                } else {
                    return;
                }
            }
        } else {
            long j2 = jArr[i2];
            int i6 = i3;
            while (true) {
                i++;
                if (i < i2) {
                    long j3 = jArr[i];
                    if (j3 < jArr[i - 1]) {
                        int i7 = i - 1;
                        jArr[i] = jArr[i7];
                        while (true) {
                            i7--;
                            if (j3 >= jArr[i7]) {
                                break;
                            }
                            jArr[i7 + 1] = jArr[i7];
                        }
                        jArr[i7 + 1] = j3;
                    } else if (i6 > i && j3 > j2) {
                        do {
                            i6--;
                        } while (jArr[i6] > j2);
                        if (i6 > i) {
                            j3 = jArr[i6];
                            jArr[i6] = jArr[i];
                            i4 = i;
                        } else {
                            i4 = i;
                        }
                        while (true) {
                            i4--;
                            if (j3 >= jArr[i4]) {
                                break;
                            }
                            jArr[i4 + 1] = jArr[i4];
                        }
                        jArr[i4 + 1] = j3;
                    }
                }
            }
            while (i < i3) {
                long j4 = jArr[i];
                int i8 = i + 1;
                long j5 = jArr[i8];
                if (j4 > j5) {
                    while (true) {
                        i--;
                        if (j4 >= jArr[i]) {
                            break;
                        }
                        jArr[i + 2] = jArr[i];
                    }
                    int i9 = i + 1;
                    jArr[i9 + 1] = j4;
                    while (true) {
                        i9--;
                        if (j5 >= jArr[i9]) {
                            break;
                        }
                        jArr[i9 + 1] = jArr[i9];
                    }
                    jArr[i9 + 1] = j5;
                } else if (j4 < jArr[i - 1]) {
                    while (true) {
                        i--;
                        if (j5 >= jArr[i]) {
                            break;
                        }
                        jArr[i + 2] = jArr[i];
                    }
                    int i10 = i + 1;
                    jArr[i10 + 1] = j5;
                    while (true) {
                        i10--;
                        if (j4 >= jArr[i10]) {
                            break;
                        }
                        jArr[i10 + 1] = jArr[i10];
                    }
                    jArr[i10 + 1] = j4;
                }
                i = i8 + 1;
            }
        }
    }

    private static void a(long[] jArr, int i, int i2) {
        int i3 = i;
        while (true) {
            i3++;
            if (i3 < i2) {
                long j = jArr[i3];
                if (j < jArr[i3 - 1]) {
                    int i4 = i3;
                    while (true) {
                        i4--;
                        if (i4 < i || j >= jArr[i4]) {
                            break;
                        }
                        jArr[i4 + 1] = jArr[i4];
                    }
                    jArr[i4 + 1] = j;
                }
            } else {
                return;
            }
        }
    }

    private static void b(long[] jArr, int i, int i2) {
        int i3 = (i + i2) >>> 1;
        while (i3 > i) {
            i3--;
            a(jArr, i3, jArr[i3], i, i2);
        }
        while (true) {
            i2--;
            if (i2 > i) {
                long j = jArr[i];
                a(jArr, i, jArr[i2], i, i2);
                jArr[i2] = j;
            } else {
                return;
            }
        }
    }

    private static void a(long[] jArr, int i, long j, int i2, int i3) {
        while (true) {
            int i4 = ((i << 1) - i2) + 2;
            if (i4 > i3) {
                break;
            }
            if (i4 == i3 || jArr[i4] < jArr[i4 - 1]) {
                i4--;
            }
            if (jArr[i4] <= j) {
                break;
            }
            jArr[i] = jArr[i4];
            i = i4;
        }
        jArr[i] = j;
    }

    private static boolean a(c cVar, long[] jArr, int i, int i2) {
        int i3;
        long[] jArr2;
        long[] jArr3;
        int i4 = i + i2;
        int i5 = i + 1;
        int[] iArr = null;
        int i6 = 1;
        int i7 = i;
        while (i5 < i4) {
            int i8 = i5 - 1;
            if (jArr[i8] < jArr[i5]) {
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (jArr[i5 - 1] <= jArr[i5]);
            } else if (jArr[i8] > jArr[i5]) {
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (jArr[i5 - 1] >= jArr[i5]);
                int i9 = i7 - 1;
                int i10 = i5;
                while (true) {
                    i9++;
                    i10--;
                    if (i9 >= i10 || jArr[i9] <= jArr[i10]) {
                        break;
                    }
                    long j = jArr[i9];
                    jArr[i9] = jArr[i10];
                    jArr[i10] = j;
                }
            } else {
                long j2 = jArr[i5];
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (j2 == jArr[i5]);
                if (i5 < i4) {
                    continue;
                }
            }
            if (iArr == null) {
                if (i5 == i4) {
                    return true;
                }
                if (i5 - i < 16) {
                    return false;
                }
                iArr = new int[((i2 >> 10) | 127) & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES];
                iArr[0] = i;
            } else if (jArr[i7 - 1] > jArr[i7]) {
                if (i6 > ((i5 - i) >> 7) || (i6 = i6 + 1) == 5120) {
                    return false;
                }
                if (i6 == iArr.length) {
                    iArr = Arrays.copyOf(iArr, i6 << 1);
                }
            }
            iArr[i6] = i5;
            i7 = i5;
        }
        if (i6 > 1) {
            if (cVar == null || (jArr3 = (long[]) cVar.b) == null) {
                jArr2 = new long[i2];
                i3 = i;
            } else {
                i3 = cVar.offset;
                jArr2 = jArr3;
            }
            a(jArr, jArr2, i3, 1, cVar != null, iArr, 0, i6);
        }
        return true;
    }

    static long[] a(long[] jArr, long[] jArr2, int i, int i2, boolean z, int[] iArr, int i3, int i4) {
        int i5;
        long[] jArr3;
        long[] jArr4;
        int i6 = i4 - i3;
        if (i6 != 1) {
            int i7 = i3;
            while (true) {
                i5 = i7 + 1;
                if (iArr[i5 + 1] > ((iArr[i3] + iArr[i4]) >>> 1)) {
                    break;
                }
                i7 = i5;
            }
            if (!z || i6 <= 4) {
                jArr3 = a(jArr, jArr2, i, -i2, false, iArr, i3, i5);
                jArr4 = a(jArr, jArr2, i, 0, false, iArr, i5, i4);
            } else {
                b a2 = new b(jArr, jArr2, i, 0, iArr, i5, i4).a();
                long[] a3 = a(jArr, jArr2, i, -i2, true, iArr, i3, i5);
                jArr4 = (long[]) a2.b();
                jArr3 = a3;
            }
            long[] jArr5 = jArr3 == jArr ? jArr2 : jArr;
            int i8 = jArr3 == jArr ? iArr[i3] - i : iArr[i3];
            int i9 = jArr3 == jArr2 ? iArr[i3] - i : iArr[i3];
            int i10 = jArr3 == jArr2 ? iArr[i5] - i : iArr[i5];
            int i11 = jArr4 == jArr2 ? iArr[i5] - i : iArr[i5];
            int i12 = jArr4 == jArr2 ? iArr[i4] - i : iArr[i4];
            if (z) {
                new a(null, jArr5, i8, jArr3, i9, i10, jArr4, i11, i12).invoke();
            } else {
                a((a) null, jArr5, i8, jArr3, i9, i10, jArr4, i11, i12);
            }
            return jArr5;
        } else if (i2 >= 0) {
            return jArr;
        } else {
            int i13 = iArr[i4];
            int i14 = i13 - i;
            int i15 = iArr[i3];
            while (i13 > i15) {
                i14--;
                i13--;
                jArr2[i14] = jArr[i13];
            }
            return jArr2;
        }
    }

    static void a(a aVar, long[] jArr, int i, long[] jArr2, int i2, int i3, long[] jArr3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        long j;
        if (aVar == null || jArr2 != jArr3) {
            i10 = i;
            i7 = i2;
            i9 = i3;
            i6 = i4;
            i8 = i5;
        } else {
            int i11 = i2;
            int i12 = i3;
            int i13 = i4;
            int i14 = i5;
            while (true) {
                if (i12 - i11 < i14 - i13) {
                    i8 = i12;
                    i6 = i11;
                    i9 = i14;
                    i7 = i13;
                } else {
                    i9 = i12;
                    i7 = i11;
                    i8 = i14;
                    i6 = i13;
                }
                if (i9 - i7 < 4096) {
                    break;
                }
                int i15 = (i7 + i9) >>> 1;
                long j2 = jArr2[i15];
                int i16 = i8;
                int i17 = i6;
                while (i17 < i16) {
                    int i18 = (i17 + i16) >>> 1;
                    if (j2 > jArr3[i18]) {
                        i17 = i18 + 1;
                    } else {
                        i16 = i18;
                    }
                }
                aVar.a(jArr, i + (((i16 - i6) + i15) - i7), jArr2, i15, i9, jArr3, i16, i8);
                i11 = i7;
                i13 = i6;
                i12 = i15;
                i14 = i16;
            }
            i10 = i;
        }
        while (i7 < i9 && i6 < i8) {
            i10++;
            if (jArr2[i7] < jArr3[i6]) {
                i7++;
                j = jArr2[i7];
            } else {
                i6++;
                j = jArr3[i6];
            }
            jArr[i10] = j;
        }
        if (jArr != jArr2 || i10 < i7) {
            while (i7 < i9) {
                i10++;
                i7++;
                jArr[i10] = jArr2[i7];
            }
        }
        if (jArr != jArr3 || i10 < i6) {
            while (i6 < i8) {
                i10++;
                i6++;
                jArr[i10] = jArr3[i6];
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(byte[] bArr, int i, int i2) {
        if (i2 - i > 64) {
            c(bArr, i, i2);
        } else {
            b(bArr, i, i2);
        }
    }

    private static void b(byte[] bArr, int i, int i2) {
        int i3 = i;
        while (true) {
            i3++;
            if (i3 < i2) {
                byte b2 = bArr[i3];
                if (b2 < bArr[i3 - 1]) {
                    int i4 = i3;
                    while (true) {
                        i4--;
                        if (i4 < i || b2 >= bArr[i4]) {
                            break;
                        }
                        bArr[i4 + 1] = bArr[i4];
                    }
                    bArr[i4 + 1] = b2;
                }
            } else {
                return;
            }
        }
    }

    private static void c(byte[] bArr, int i, int i2) {
        int i3;
        int[] iArr = new int[256];
        int i4 = i2;
        while (i4 > i) {
            i4--;
            int i5 = bArr[i4] & 255;
            iArr[i5] = iArr[i5] + 1;
        }
        int i6 = 384;
        if (i2 - i <= 256) {
            while (i2 > i) {
                do {
                    i6--;
                    i3 = i6 & 255;
                } while (iArr[i3] == 0);
                int i7 = iArr[i3];
                do {
                    i2--;
                    bArr[i2] = (byte) i3;
                    i7--;
                } while (i7 > 0);
            }
            return;
        }
        while (true) {
            i6--;
            if (i6 > 127) {
                int i8 = i6 & 255;
                int i9 = i2 - iArr[i8];
                while (i2 > i9) {
                    i2--;
                    bArr[i2] = (byte) i8;
                }
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(char[] cArr, int i, int i2) {
        if (i2 - i > 1750) {
            c(cArr, i, i2);
        } else {
            a(cArr, 0, i, i2);
        }
    }

    static void a(char[] cArr, int i, int i2, int i3) {
        while (true) {
            int i4 = i3 - 1;
            int i5 = i3 - i2;
            if (i5 < 44) {
                b(cArr, i2, i3);
                return;
            }
            i += 6;
            if (i > 384) {
                c(cArr, i2, i3);
                return;
            }
            int i6 = ((i5 >> 3) * 3) + 3;
            int i7 = i2 + i6;
            int i8 = i4 - i6;
            int i9 = (i7 + i8) >>> 1;
            int i10 = (i7 + i9) >>> 1;
            int i11 = (i9 + i8) >>> 1;
            char c2 = cArr[i9];
            if (cArr[i8] < cArr[i10]) {
                char c3 = cArr[i8];
                cArr[i8] = cArr[i10];
                cArr[i10] = c3;
            }
            if (cArr[i11] < cArr[i7]) {
                char c4 = cArr[i11];
                cArr[i11] = cArr[i7];
                cArr[i7] = c4;
            }
            if (cArr[i8] < cArr[i11]) {
                char c5 = cArr[i8];
                cArr[i8] = cArr[i11];
                cArr[i11] = c5;
            }
            if (cArr[i10] < cArr[i7]) {
                char c6 = cArr[i10];
                cArr[i10] = cArr[i7];
                cArr[i7] = c6;
            }
            if (cArr[i11] < cArr[i10]) {
                char c7 = cArr[i11];
                cArr[i11] = cArr[i10];
                cArr[i10] = c7;
            }
            if (c2 < cArr[i10]) {
                if (c2 < cArr[i7]) {
                    cArr[i9] = cArr[i10];
                    cArr[i10] = cArr[i7];
                    cArr[i7] = c2;
                } else {
                    cArr[i9] = cArr[i10];
                    cArr[i10] = c2;
                }
            } else if (c2 > cArr[i11]) {
                if (c2 > cArr[i8]) {
                    cArr[i9] = cArr[i11];
                    cArr[i11] = cArr[i8];
                    cArr[i8] = c2;
                } else {
                    cArr[i9] = cArr[i11];
                    cArr[i11] = c2;
                }
            }
            if (cArr[i7] >= cArr[i10] || cArr[i10] >= cArr[i9] || cArr[i9] >= cArr[i11] || cArr[i11] >= cArr[i8]) {
                char c8 = cArr[i9];
                cArr[i9] = cArr[i2];
                int i12 = i4 + 1;
                int i13 = i2;
                int i14 = i12;
                while (true) {
                    i12--;
                    if (i12 <= i13) {
                        break;
                    }
                    char c9 = cArr[i12];
                    if (c9 != c8) {
                        cArr[i12] = c8;
                        if (c9 < c8) {
                            do {
                                i13++;
                            } while (cArr[i13] < c8);
                            if (cArr[i13] > c8) {
                                i14--;
                                cArr[i14] = cArr[i13];
                            }
                            cArr[i13] = c9;
                        } else {
                            i14--;
                            cArr[i14] = c9;
                        }
                    }
                }
                cArr[i2] = cArr[i13];
                cArr[i13] = c8;
                a(cArr, i | 1, i14, i3);
                i3 = i13;
            } else {
                char c10 = cArr[i7];
                char c11 = cArr[i8];
                cArr[i7] = cArr[i2];
                cArr[i8] = cArr[i4];
                int i15 = i2;
                do {
                    i15++;
                } while (cArr[i15] < c10);
                int i16 = i4;
                do {
                    i16--;
                } while (cArr[i16] > c11);
                int i17 = i15 - 1;
                int i18 = i16 + 1;
                int i19 = i18;
                while (true) {
                    i18--;
                    if (i18 <= i17) {
                        break;
                    }
                    char c12 = cArr[i18];
                    if (c12 < c10) {
                        while (true) {
                            if (i17 < i18) {
                                i17++;
                                if (cArr[i17] >= c10) {
                                    if (cArr[i17] > c11) {
                                        i19--;
                                        cArr[i18] = cArr[i19];
                                        cArr[i19] = cArr[i17];
                                    } else {
                                        cArr[i18] = cArr[i17];
                                    }
                                    cArr[i17] = c12;
                                }
                            }
                        }
                    } else if (c12 > c11) {
                        i19--;
                        cArr[i18] = cArr[i19];
                        cArr[i19] = c12;
                    }
                }
                cArr[i2] = cArr[i17];
                cArr[i17] = c10;
                cArr[i4] = cArr[i19];
                cArr[i19] = c11;
                int i20 = i | 1;
                a(cArr, i20, i17 + 1, i19);
                a(cArr, i20, i19 + 1, i3);
                i3 = i17;
            }
        }
    }

    private static void b(char[] cArr, int i, int i2) {
        int i3 = i;
        while (true) {
            i3++;
            if (i3 < i2) {
                char c2 = cArr[i3];
                if (c2 < cArr[i3 - 1]) {
                    int i4 = i3;
                    while (true) {
                        i4--;
                        if (i4 < i || c2 >= cArr[i4]) {
                            break;
                        }
                        cArr[i4 + 1] = cArr[i4];
                    }
                    cArr[i4 + 1] = c2;
                }
            } else {
                return;
            }
        }
    }

    private static void c(char[] cArr, int i, int i2) {
        int i3 = 65536;
        int[] iArr = new int[65536];
        int i4 = i2;
        while (i4 > i) {
            i4--;
            char c2 = cArr[i4];
            iArr[c2] = iArr[c2] + 1;
        }
        if (i2 - i <= 65536) {
            while (i2 > i) {
                do {
                    i3--;
                } while (iArr[i3] == 0);
                int i5 = iArr[i3];
                do {
                    i2--;
                    cArr[i2] = (char) i3;
                    i5--;
                } while (i5 > 0);
            }
            return;
        }
        while (i3 > 0) {
            i3--;
            int i6 = i2 - iArr[i3];
            while (i2 > i6) {
                i2--;
                cArr[i2] = (char) i3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(short[] sArr, int i, int i2) {
        if (i2 - i > 1750) {
            c(sArr, i, i2);
        } else {
            a(sArr, 0, i, i2);
        }
    }

    static void a(short[] sArr, int i, int i2, int i3) {
        while (true) {
            int i4 = i3 - 1;
            int i5 = i3 - i2;
            if (i5 < 44) {
                b(sArr, i2, i3);
                return;
            }
            i += 6;
            if (i > 384) {
                c(sArr, i2, i3);
                return;
            }
            int i6 = ((i5 >> 3) * 3) + 3;
            int i7 = i2 + i6;
            int i8 = i4 - i6;
            int i9 = (i7 + i8) >>> 1;
            int i10 = (i7 + i9) >>> 1;
            int i11 = (i9 + i8) >>> 1;
            short s = sArr[i9];
            if (sArr[i8] < sArr[i10]) {
                short s2 = sArr[i8];
                sArr[i8] = sArr[i10];
                sArr[i10] = s2;
            }
            if (sArr[i11] < sArr[i7]) {
                short s3 = sArr[i11];
                sArr[i11] = sArr[i7];
                sArr[i7] = s3;
            }
            if (sArr[i8] < sArr[i11]) {
                short s4 = sArr[i8];
                sArr[i8] = sArr[i11];
                sArr[i11] = s4;
            }
            if (sArr[i10] < sArr[i7]) {
                short s5 = sArr[i10];
                sArr[i10] = sArr[i7];
                sArr[i7] = s5;
            }
            if (sArr[i11] < sArr[i10]) {
                short s6 = sArr[i11];
                sArr[i11] = sArr[i10];
                sArr[i10] = s6;
            }
            if (s < sArr[i10]) {
                if (s < sArr[i7]) {
                    sArr[i9] = sArr[i10];
                    sArr[i10] = sArr[i7];
                    sArr[i7] = s;
                } else {
                    sArr[i9] = sArr[i10];
                    sArr[i10] = s;
                }
            } else if (s > sArr[i11]) {
                if (s > sArr[i8]) {
                    sArr[i9] = sArr[i11];
                    sArr[i11] = sArr[i8];
                    sArr[i8] = s;
                } else {
                    sArr[i9] = sArr[i11];
                    sArr[i11] = s;
                }
            }
            if (sArr[i7] >= sArr[i10] || sArr[i10] >= sArr[i9] || sArr[i9] >= sArr[i11] || sArr[i11] >= sArr[i8]) {
                short s7 = sArr[i9];
                sArr[i9] = sArr[i2];
                int i12 = i4 + 1;
                int i13 = i2;
                int i14 = i12;
                while (true) {
                    i12--;
                    if (i12 <= i13) {
                        break;
                    }
                    short s8 = sArr[i12];
                    if (s8 != s7) {
                        sArr[i12] = s7;
                        if (s8 < s7) {
                            do {
                                i13++;
                            } while (sArr[i13] < s7);
                            if (sArr[i13] > s7) {
                                i14--;
                                sArr[i14] = sArr[i13];
                            }
                            sArr[i13] = s8;
                        } else {
                            i14--;
                            sArr[i14] = s8;
                        }
                    }
                }
                sArr[i2] = sArr[i13];
                sArr[i13] = s7;
                a(sArr, i | 1, i14, i3);
                i3 = i13;
            } else {
                short s9 = sArr[i7];
                short s10 = sArr[i8];
                sArr[i7] = sArr[i2];
                sArr[i8] = sArr[i4];
                int i15 = i2;
                do {
                    i15++;
                } while (sArr[i15] < s9);
                int i16 = i4;
                do {
                    i16--;
                } while (sArr[i16] > s10);
                int i17 = i15 - 1;
                int i18 = i16 + 1;
                int i19 = i18;
                while (true) {
                    i18--;
                    if (i18 <= i17) {
                        break;
                    }
                    short s11 = sArr[i18];
                    if (s11 < s9) {
                        while (true) {
                            if (i17 < i18) {
                                i17++;
                                if (sArr[i17] >= s9) {
                                    if (sArr[i17] > s10) {
                                        i19--;
                                        sArr[i18] = sArr[i19];
                                        sArr[i19] = sArr[i17];
                                    } else {
                                        sArr[i18] = sArr[i17];
                                    }
                                    sArr[i17] = s11;
                                }
                            }
                        }
                    } else if (s11 > s10) {
                        i19--;
                        sArr[i18] = sArr[i19];
                        sArr[i19] = s11;
                    }
                }
                sArr[i2] = sArr[i17];
                sArr[i17] = s9;
                sArr[i4] = sArr[i19];
                sArr[i19] = s10;
                int i20 = i | 1;
                a(sArr, i20, i17 + 1, i19);
                a(sArr, i20, i19 + 1, i3);
                i3 = i17;
            }
        }
    }

    private static void b(short[] sArr, int i, int i2) {
        int i3 = i;
        while (true) {
            i3++;
            if (i3 < i2) {
                short s = sArr[i3];
                if (s < sArr[i3 - 1]) {
                    int i4 = i3;
                    while (true) {
                        i4--;
                        if (i4 < i || s >= sArr[i4]) {
                            break;
                        }
                        sArr[i4 + 1] = sArr[i4];
                    }
                    sArr[i4 + 1] = s;
                }
            } else {
                return;
            }
        }
    }

    private static void c(short[] sArr, int i, int i2) {
        int i3;
        int[] iArr = new int[65536];
        int i4 = i2;
        while (i4 > i) {
            i4--;
            int i5 = 65535 & sArr[i4];
            iArr[i5] = iArr[i5] + 1;
        }
        int i6 = 98304;
        if (i2 - i <= 65536) {
            while (i2 > i) {
                do {
                    i6--;
                    i3 = i6 & 65535;
                } while (iArr[i3] == 0);
                int i7 = iArr[i3];
                do {
                    i2--;
                    sArr[i2] = (short) i3;
                    i7--;
                } while (i7 > 0);
            }
            return;
        }
        while (true) {
            i6--;
            if (i6 > 32767) {
                int i8 = i6 & 65535;
                int i9 = i2 - iArr[i8];
                while (i2 > i9) {
                    i2--;
                    sArr[i2] = (short) i8;
                }
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(float[] fArr, int i, int i2, int i3) {
        int i4 = i2;
        int i5 = i3;
        int i6 = i5;
        int i7 = 0;
        while (i5 > i4) {
            i5--;
            float f = fArr[i5];
            if (f == 0.0f && Float.floatToRawIntBits(f) < 0) {
                i7++;
                fArr[i5] = 0.0f;
            } else if (f != f) {
                i6--;
                fArr[i5] = fArr[i6];
                fArr[i6] = f;
            }
        }
        int i8 = i6 - i4;
        if (i <= 1 || i8 <= 4096) {
            a((c) null, fArr, 0, i4, i6);
        } else {
            int a2 = a(i, i8 >> 12);
            new c(null, fArr, a2 == 0 ? null : new float[i8], i2, i8, i2, a2).invoke();
        }
        int i9 = i7 + 1;
        if (i9 != 1) {
            while (i4 <= i6) {
                int i10 = (i4 + i6) >>> 1;
                if (fArr[i10] < 0.0f) {
                    i4 = i10 + 1;
                } else {
                    i6 = i10 - 1;
                }
            }
            while (true) {
                i9--;
                if (i9 > 0) {
                    i6++;
                    fArr[i6] = -0.0f;
                } else {
                    return;
                }
            }
        }
    }

    static void a(c cVar, float[] fArr, int i, int i2, int i3) {
        while (true) {
            int i4 = i3 - 1;
            int i5 = i3 - i2;
            if (i5 < i + 65 && (i & 1) > 0) {
                b(fArr, i2, i3 - (((i5 >> 5) << 3) * 3), i3);
                return;
            } else if (i5 < 44) {
                a(fArr, i2, i3);
                return;
            } else if ((i != 0 && (i5 <= 4096 || (i & 1) <= 0)) || !a(cVar, fArr, i2, i5)) {
                i += 6;
                if (i > 384) {
                    b(fArr, i2, i3);
                    return;
                }
                int i6 = ((i5 >> 3) * 3) + 3;
                int i7 = i2 + i6;
                int i8 = i4 - i6;
                int i9 = (i7 + i8) >>> 1;
                int i10 = (i7 + i9) >>> 1;
                int i11 = (i9 + i8) >>> 1;
                float f = fArr[i9];
                if (fArr[i8] < fArr[i10]) {
                    float f2 = fArr[i8];
                    fArr[i8] = fArr[i10];
                    fArr[i10] = f2;
                }
                if (fArr[i11] < fArr[i7]) {
                    float f3 = fArr[i11];
                    fArr[i11] = fArr[i7];
                    fArr[i7] = f3;
                }
                if (fArr[i8] < fArr[i11]) {
                    float f4 = fArr[i8];
                    fArr[i8] = fArr[i11];
                    fArr[i11] = f4;
                }
                if (fArr[i10] < fArr[i7]) {
                    float f5 = fArr[i10];
                    fArr[i10] = fArr[i7];
                    fArr[i7] = f5;
                }
                if (fArr[i11] < fArr[i10]) {
                    float f6 = fArr[i11];
                    fArr[i11] = fArr[i10];
                    fArr[i10] = f6;
                }
                if (f < fArr[i10]) {
                    if (f < fArr[i7]) {
                        fArr[i9] = fArr[i10];
                        fArr[i10] = fArr[i7];
                        fArr[i7] = f;
                    } else {
                        fArr[i9] = fArr[i10];
                        fArr[i10] = f;
                    }
                } else if (f > fArr[i11]) {
                    if (f > fArr[i8]) {
                        fArr[i9] = fArr[i11];
                        fArr[i11] = fArr[i8];
                        fArr[i8] = f;
                    } else {
                        fArr[i9] = fArr[i11];
                        fArr[i11] = f;
                    }
                }
                if (fArr[i7] >= fArr[i10] || fArr[i10] >= fArr[i9] || fArr[i9] >= fArr[i11] || fArr[i11] >= fArr[i8]) {
                    float f7 = fArr[i9];
                    fArr[i9] = fArr[i2];
                    int i12 = i4 + 1;
                    int i13 = i2;
                    int i14 = i12;
                    while (true) {
                        i12--;
                        if (i12 <= i13) {
                            break;
                        }
                        float f8 = fArr[i12];
                        if (f8 != f7) {
                            fArr[i12] = f7;
                            if (f8 < f7) {
                                do {
                                    i13++;
                                } while (fArr[i13] < f7);
                                if (fArr[i13] > f7) {
                                    i14--;
                                    fArr[i14] = fArr[i13];
                                }
                                fArr[i13] = f8;
                            } else {
                                i14--;
                                fArr[i14] = f8;
                            }
                        }
                    }
                    fArr[i2] = fArr[i13];
                    fArr[i13] = f7;
                    if (i5 <= 4096 || cVar == null) {
                        a(cVar, fArr, i | 1, i14, i3);
                    } else {
                        cVar.a(i | 1, i14, i3);
                    }
                    i3 = i13;
                } else {
                    float f9 = fArr[i7];
                    float f10 = fArr[i8];
                    fArr[i7] = fArr[i2];
                    fArr[i8] = fArr[i4];
                    int i15 = i2;
                    do {
                        i15++;
                    } while (fArr[i15] < f9);
                    int i16 = i4;
                    do {
                        i16--;
                    } while (fArr[i16] > f10);
                    int i17 = i15 - 1;
                    int i18 = i16 + 1;
                    int i19 = i18;
                    while (true) {
                        i18--;
                        if (i18 <= i17) {
                            break;
                        }
                        float f11 = fArr[i18];
                        if (f11 < f9) {
                            while (true) {
                                if (i17 < i18) {
                                    i17++;
                                    if (fArr[i17] >= f9) {
                                        if (fArr[i17] > f10) {
                                            i19--;
                                            fArr[i18] = fArr[i19];
                                            fArr[i19] = fArr[i17];
                                        } else {
                                            fArr[i18] = fArr[i17];
                                        }
                                        fArr[i17] = f11;
                                    }
                                }
                            }
                        } else if (f11 > f10) {
                            i19--;
                            fArr[i18] = fArr[i19];
                            fArr[i19] = f11;
                        }
                    }
                    fArr[i2] = fArr[i17];
                    fArr[i17] = f9;
                    fArr[i4] = fArr[i19];
                    fArr[i19] = f10;
                    if (i5 <= 4096 || cVar == null) {
                        int i20 = i | 1;
                        a(cVar, fArr, i20, i17 + 1, i19);
                        a(cVar, fArr, i20, i19 + 1, i3);
                    } else {
                        int i21 = i | 1;
                        cVar.a(i21, i17 + 1, i19);
                        cVar.a(i21, i19 + 1, i3);
                    }
                    i3 = i17;
                }
            } else {
                return;
            }
        }
    }

    private static void b(float[] fArr, int i, int i2, int i3) {
        int i4;
        if (i2 == i3) {
            while (true) {
                i++;
                if (i < i2) {
                    float f = fArr[i];
                    int i5 = i;
                    while (true) {
                        i5--;
                        if (f < fArr[i5]) {
                            fArr[i5 + 1] = fArr[i5];
                        }
                    }
                    fArr[i5 + 1] = f;
                } else {
                    return;
                }
            }
        } else {
            float f2 = fArr[i2];
            int i6 = i3;
            while (true) {
                i++;
                if (i < i2) {
                    float f3 = fArr[i];
                    if (f3 < fArr[i - 1]) {
                        int i7 = i - 1;
                        fArr[i] = fArr[i7];
                        while (true) {
                            i7--;
                            if (f3 >= fArr[i7]) {
                                break;
                            }
                            fArr[i7 + 1] = fArr[i7];
                        }
                        fArr[i7 + 1] = f3;
                    } else if (i6 > i && f3 > f2) {
                        do {
                            i6--;
                        } while (fArr[i6] > f2);
                        if (i6 > i) {
                            f3 = fArr[i6];
                            fArr[i6] = fArr[i];
                            i4 = i;
                        } else {
                            i4 = i;
                        }
                        while (true) {
                            i4--;
                            if (f3 >= fArr[i4]) {
                                break;
                            }
                            fArr[i4 + 1] = fArr[i4];
                        }
                        fArr[i4 + 1] = f3;
                    }
                }
            }
            while (i < i3) {
                float f4 = fArr[i];
                int i8 = i + 1;
                float f5 = fArr[i8];
                if (f4 > f5) {
                    while (true) {
                        i--;
                        if (f4 >= fArr[i]) {
                            break;
                        }
                        fArr[i + 2] = fArr[i];
                    }
                    int i9 = i + 1;
                    fArr[i9 + 1] = f4;
                    while (true) {
                        i9--;
                        if (f5 >= fArr[i9]) {
                            break;
                        }
                        fArr[i9 + 1] = fArr[i9];
                    }
                    fArr[i9 + 1] = f5;
                } else if (f4 < fArr[i - 1]) {
                    while (true) {
                        i--;
                        if (f5 >= fArr[i]) {
                            break;
                        }
                        fArr[i + 2] = fArr[i];
                    }
                    int i10 = i + 1;
                    fArr[i10 + 1] = f5;
                    while (true) {
                        i10--;
                        if (f4 >= fArr[i10]) {
                            break;
                        }
                        fArr[i10 + 1] = fArr[i10];
                    }
                    fArr[i10 + 1] = f4;
                }
                i = i8 + 1;
            }
        }
    }

    private static void a(float[] fArr, int i, int i2) {
        int i3 = i;
        while (true) {
            i3++;
            if (i3 < i2) {
                float f = fArr[i3];
                if (f < fArr[i3 - 1]) {
                    int i4 = i3;
                    while (true) {
                        i4--;
                        if (i4 < i || f >= fArr[i4]) {
                            break;
                        }
                        fArr[i4 + 1] = fArr[i4];
                    }
                    fArr[i4 + 1] = f;
                }
            } else {
                return;
            }
        }
    }

    private static void b(float[] fArr, int i, int i2) {
        int i3 = (i + i2) >>> 1;
        while (i3 > i) {
            i3--;
            a(fArr, i3, fArr[i3], i, i2);
        }
        while (true) {
            i2--;
            if (i2 > i) {
                float f = fArr[i];
                a(fArr, i, fArr[i2], i, i2);
                fArr[i2] = f;
            } else {
                return;
            }
        }
    }

    private static void a(float[] fArr, int i, float f, int i2, int i3) {
        while (true) {
            int i4 = ((i << 1) - i2) + 2;
            if (i4 > i3) {
                break;
            }
            if (i4 == i3 || fArr[i4] < fArr[i4 - 1]) {
                i4--;
            }
            if (fArr[i4] <= f) {
                break;
            }
            fArr[i] = fArr[i4];
            i = i4;
        }
        fArr[i] = f;
    }

    private static boolean a(c cVar, float[] fArr, int i, int i2) {
        float[] fArr2;
        int i3;
        float[] fArr3;
        int i4 = i + i2;
        int i5 = i + 1;
        int[] iArr = null;
        int i6 = 1;
        int i7 = i;
        while (i5 < i4) {
            int i8 = i5 - 1;
            if (fArr[i8] < fArr[i5]) {
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (fArr[i5 - 1] <= fArr[i5]);
            } else if (fArr[i8] > fArr[i5]) {
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (fArr[i5 - 1] >= fArr[i5]);
                int i9 = i7 - 1;
                int i10 = i5;
                while (true) {
                    i9++;
                    i10--;
                    if (i9 >= i10 || fArr[i9] <= fArr[i10]) {
                        break;
                    }
                    float f = fArr[i9];
                    fArr[i9] = fArr[i10];
                    fArr[i10] = f;
                }
            } else {
                float f2 = fArr[i5];
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (f2 == fArr[i5]);
                if (i5 < i4) {
                    continue;
                }
            }
            if (iArr == null) {
                if (i5 == i4) {
                    return true;
                }
                if (i5 - i < 16) {
                    return false;
                }
                int[] iArr2 = new int[((i2 >> 10) | 127) & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES];
                iArr2[0] = i;
                iArr = iArr2;
            } else if (fArr[i7 - 1] > fArr[i7]) {
                if (i6 > ((i5 - i) >> 7) || (i6 = i6 + 1) == 5120) {
                    return false;
                }
                if (i6 == iArr.length) {
                    iArr = Arrays.copyOf(iArr, i6 << 1);
                }
            }
            iArr[i6] = i5;
            i7 = i5;
        }
        if (i6 > 1) {
            if (cVar == null || (fArr3 = (float[]) cVar.b) == null) {
                fArr2 = new float[i2];
                i3 = i;
            } else {
                i3 = cVar.offset;
                fArr2 = fArr3;
            }
            a(fArr, fArr2, i3, 1, cVar != null, iArr, 0, i6);
        }
        return true;
    }

    static float[] a(float[] fArr, float[] fArr2, int i, int i2, boolean z, int[] iArr, int i3, int i4) {
        int i5;
        float[] fArr3;
        float[] fArr4;
        int i6 = i4 - i3;
        if (i6 != 1) {
            int i7 = i3;
            while (true) {
                i5 = i7 + 1;
                if (iArr[i5 + 1] > ((iArr[i3] + iArr[i4]) >>> 1)) {
                    break;
                }
                i7 = i5;
            }
            if (!z || i6 <= 4) {
                fArr3 = a(fArr, fArr2, i, -i2, false, iArr, i3, i5);
                fArr4 = a(fArr, fArr2, i, 0, false, iArr, i5, i4);
            } else {
                b a2 = new b(fArr, fArr2, i, 0, iArr, i5, i4).a();
                float[] a3 = a(fArr, fArr2, i, -i2, true, iArr, i3, i5);
                fArr4 = (float[]) a2.b();
                fArr3 = a3;
            }
            float[] fArr5 = fArr3 == fArr ? fArr2 : fArr;
            int i8 = fArr3 == fArr ? iArr[i3] - i : iArr[i3];
            int i9 = fArr3 == fArr2 ? iArr[i3] - i : iArr[i3];
            int i10 = fArr3 == fArr2 ? iArr[i5] - i : iArr[i5];
            int i11 = fArr4 == fArr2 ? iArr[i5] - i : iArr[i5];
            int i12 = fArr4 == fArr2 ? iArr[i4] - i : iArr[i4];
            if (z) {
                new a(null, fArr5, i8, fArr3, i9, i10, fArr4, i11, i12).invoke();
            } else {
                a((a) null, fArr5, i8, fArr3, i9, i10, fArr4, i11, i12);
            }
            return fArr5;
        } else if (i2 >= 0) {
            return fArr;
        } else {
            int i13 = iArr[i4];
            int i14 = i13 - i;
            int i15 = iArr[i3];
            while (i13 > i15) {
                i14--;
                i13--;
                fArr2[i14] = fArr[i13];
            }
            return fArr2;
        }
    }

    static void a(a aVar, float[] fArr, int i, float[] fArr2, int i2, int i3, float[] fArr3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        float f;
        if (aVar == null || fArr2 != fArr3) {
            i10 = i;
            i7 = i2;
            i9 = i3;
            i6 = i4;
            i8 = i5;
        } else {
            int i11 = i2;
            int i12 = i3;
            int i13 = i4;
            int i14 = i5;
            while (true) {
                if (i12 - i11 < i14 - i13) {
                    i8 = i12;
                    i6 = i11;
                    i9 = i14;
                    i7 = i13;
                } else {
                    i9 = i12;
                    i7 = i11;
                    i8 = i14;
                    i6 = i13;
                }
                if (i9 - i7 < 4096) {
                    break;
                }
                int i15 = (i7 + i9) >>> 1;
                float f2 = fArr2[i15];
                int i16 = i8;
                int i17 = i6;
                while (i17 < i16) {
                    int i18 = (i17 + i16) >>> 1;
                    if (f2 > fArr3[i18]) {
                        i17 = i18 + 1;
                    } else {
                        i16 = i18;
                    }
                }
                aVar.a(fArr, i + (((i16 - i6) + i15) - i7), fArr2, i15, i9, fArr3, i16, i8);
                i11 = i7;
                i13 = i6;
                i12 = i15;
                i14 = i16;
            }
            i10 = i;
        }
        while (i7 < i9 && i6 < i8) {
            i10++;
            if (fArr2[i7] < fArr3[i6]) {
                i7++;
                f = fArr2[i7];
            } else {
                i6++;
                f = fArr3[i6];
            }
            fArr[i10] = f;
        }
        if (fArr != fArr2 || i10 < i7) {
            while (i7 < i9) {
                i10++;
                i7++;
                fArr[i10] = fArr2[i7];
            }
        }
        if (fArr != fArr3 || i10 < i6) {
            while (i6 < i8) {
                i10++;
                i6++;
                fArr[i10] = fArr3[i6];
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(double[] dArr, int i, int i2, int i3) {
        int i4 = i2;
        int i5 = i3;
        int i6 = i5;
        int i7 = 0;
        while (i5 > i4) {
            i5--;
            double d = dArr[i5];
            if (d == 0.0d && Double.doubleToRawLongBits(d) < 0) {
                i7++;
                dArr[i5] = 0.0d;
            } else if (d != d) {
                i6--;
                dArr[i5] = dArr[i6];
                dArr[i6] = d;
            }
        }
        int i8 = i6 - i4;
        if (i <= 1 || i8 <= 4096) {
            a((c) null, dArr, 0, i4, i6);
        } else {
            int a2 = a(i, i8 >> 12);
            new c(null, dArr, a2 == 0 ? null : new double[i8], i2, i8, i2, a2).invoke();
        }
        int i9 = i7 + 1;
        if (i9 != 1) {
            while (i4 <= i6) {
                int i10 = (i4 + i6) >>> 1;
                if (dArr[i10] < 0.0d) {
                    i4 = i10 + 1;
                } else {
                    i6 = i10 - 1;
                }
            }
            while (true) {
                i9--;
                if (i9 > 0) {
                    i6++;
                    dArr[i6] = -0.0d;
                } else {
                    return;
                }
            }
        }
    }

    static void a(c cVar, double[] dArr, int i, int i2, int i3) {
        int i4 = i;
        int i5 = i3;
        while (true) {
            int i6 = i5 - 1;
            int i7 = i5 - i2;
            if (i7 < i4 + 65 && (i4 & 1) > 0) {
                b(dArr, i2, i5 - (((i7 >> 5) << 3) * 3), i5);
                return;
            } else if (i7 < 44) {
                a(dArr, i2, i5);
                return;
            } else if ((i4 != 0 && (i7 <= 4096 || (i4 & 1) <= 0)) || !a(cVar, dArr, i2, i7)) {
                i4 += 6;
                if (i4 > 384) {
                    b(dArr, i2, i5);
                    return;
                }
                int i8 = ((i7 >> 3) * 3) + 3;
                int i9 = i2 + i8;
                int i10 = i6 - i8;
                int i11 = (i9 + i10) >>> 1;
                int i12 = (i9 + i11) >>> 1;
                int i13 = (i11 + i10) >>> 1;
                double d = dArr[i11];
                if (dArr[i10] < dArr[i12]) {
                    double d2 = dArr[i10];
                    dArr[i10] = dArr[i12];
                    dArr[i12] = d2;
                }
                if (dArr[i13] < dArr[i9]) {
                    double d3 = dArr[i13];
                    dArr[i13] = dArr[i9];
                    dArr[i9] = d3;
                }
                if (dArr[i10] < dArr[i13]) {
                    double d4 = dArr[i10];
                    dArr[i10] = dArr[i13];
                    dArr[i13] = d4;
                }
                if (dArr[i12] < dArr[i9]) {
                    double d5 = dArr[i12];
                    dArr[i12] = dArr[i9];
                    dArr[i9] = d5;
                }
                if (dArr[i13] < dArr[i12]) {
                    double d6 = dArr[i13];
                    dArr[i13] = dArr[i12];
                    dArr[i12] = d6;
                }
                if (d < dArr[i12]) {
                    if (d < dArr[i9]) {
                        dArr[i11] = dArr[i12];
                        dArr[i12] = dArr[i9];
                        dArr[i9] = d;
                    } else {
                        dArr[i11] = dArr[i12];
                        dArr[i12] = d;
                    }
                } else if (d > dArr[i13]) {
                    if (d > dArr[i10]) {
                        dArr[i11] = dArr[i13];
                        dArr[i13] = dArr[i10];
                        dArr[i10] = d;
                    } else {
                        dArr[i11] = dArr[i13];
                        dArr[i13] = d;
                    }
                }
                if (dArr[i9] >= dArr[i12] || dArr[i12] >= dArr[i11] || dArr[i11] >= dArr[i13] || dArr[i13] >= dArr[i10]) {
                    double d7 = dArr[i11];
                    dArr[i11] = dArr[i2];
                    int i14 = i6 + 1;
                    int i15 = i2;
                    int i16 = i14;
                    while (true) {
                        i14--;
                        if (i14 <= i15) {
                            break;
                        }
                        double d8 = dArr[i14];
                        if (d8 != d7) {
                            dArr[i14] = d7;
                            if (d8 < d7) {
                                do {
                                    i15++;
                                } while (dArr[i15] < d7);
                                if (dArr[i15] > d7) {
                                    i16--;
                                    dArr[i16] = dArr[i15];
                                }
                                dArr[i15] = d8;
                            } else {
                                i16--;
                                dArr[i16] = d8;
                            }
                        }
                    }
                    dArr[i2] = dArr[i15];
                    dArr[i15] = d7;
                    if (i7 <= 4096 || cVar == null) {
                        a(cVar, dArr, i4 | 1, i16, i5);
                    } else {
                        cVar.a(i4 | 1, i16, i5);
                    }
                    i5 = i15;
                } else {
                    double d9 = dArr[i9];
                    double d10 = dArr[i10];
                    dArr[i9] = dArr[i2];
                    dArr[i10] = dArr[i6];
                    int i17 = i2;
                    do {
                        i17++;
                    } while (dArr[i17] < d9);
                    int i18 = i6;
                    do {
                        i18--;
                    } while (dArr[i18] > d10);
                    int i19 = i17 - 1;
                    int i20 = i18 + 1;
                    int i21 = i20;
                    while (true) {
                        i20--;
                        if (i20 <= i19) {
                            break;
                        }
                        double d11 = dArr[i20];
                        if (d11 < d9) {
                            while (true) {
                                if (i19 < i20) {
                                    i19++;
                                    if (dArr[i19] >= d9) {
                                        if (dArr[i19] > d10) {
                                            i21--;
                                            dArr[i20] = dArr[i21];
                                            dArr[i21] = dArr[i19];
                                        } else {
                                            dArr[i20] = dArr[i19];
                                        }
                                        dArr[i19] = d11;
                                    }
                                }
                            }
                        } else if (d11 > d10) {
                            i21--;
                            dArr[i20] = dArr[i21];
                            dArr[i21] = d11;
                        }
                    }
                    dArr[i2] = dArr[i19];
                    dArr[i19] = d9;
                    dArr[i6] = dArr[i21];
                    dArr[i21] = d10;
                    if (i7 <= 4096 || cVar == null) {
                        int i22 = i4 | 1;
                        a(cVar, dArr, i22, i19 + 1, i21);
                        a(cVar, dArr, i22, i21 + 1, i5);
                    } else {
                        int i23 = i4 | 1;
                        cVar.a(i23, i19 + 1, i21);
                        cVar.a(i23, i21 + 1, i5);
                    }
                    i5 = i19;
                }
            } else {
                return;
            }
        }
    }

    private static void b(double[] dArr, int i, int i2, int i3) {
        int i4;
        if (i2 == i3) {
            while (true) {
                i++;
                if (i < i2) {
                    double d = dArr[i];
                    int i5 = i;
                    while (true) {
                        i5--;
                        if (d < dArr[i5]) {
                            dArr[i5 + 1] = dArr[i5];
                        }
                    }
                    dArr[i5 + 1] = d;
                } else {
                    return;
                }
            }
        } else {
            double d2 = dArr[i2];
            int i6 = i3;
            while (true) {
                i++;
                if (i < i2) {
                    double d3 = dArr[i];
                    if (d3 < dArr[i - 1]) {
                        int i7 = i - 1;
                        dArr[i] = dArr[i7];
                        while (true) {
                            i7--;
                            if (d3 >= dArr[i7]) {
                                break;
                            }
                            dArr[i7 + 1] = dArr[i7];
                        }
                        dArr[i7 + 1] = d3;
                    } else if (i6 > i && d3 > d2) {
                        do {
                            i6--;
                        } while (dArr[i6] > d2);
                        if (i6 > i) {
                            d3 = dArr[i6];
                            dArr[i6] = dArr[i];
                            i4 = i;
                        } else {
                            i4 = i;
                        }
                        while (true) {
                            i4--;
                            if (d3 >= dArr[i4]) {
                                break;
                            }
                            dArr[i4 + 1] = dArr[i4];
                        }
                        dArr[i4 + 1] = d3;
                    }
                }
            }
            while (i < i3) {
                double d4 = dArr[i];
                int i8 = i + 1;
                double d5 = dArr[i8];
                if (d4 > d5) {
                    while (true) {
                        i--;
                        if (d4 >= dArr[i]) {
                            break;
                        }
                        dArr[i + 2] = dArr[i];
                    }
                    int i9 = i + 1;
                    dArr[i9 + 1] = d4;
                    while (true) {
                        i9--;
                        if (d5 >= dArr[i9]) {
                            break;
                        }
                        dArr[i9 + 1] = dArr[i9];
                    }
                    dArr[i9 + 1] = d5;
                } else if (d4 < dArr[i - 1]) {
                    while (true) {
                        i--;
                        if (d5 >= dArr[i]) {
                            break;
                        }
                        dArr[i + 2] = dArr[i];
                    }
                    int i10 = i + 1;
                    dArr[i10 + 1] = d5;
                    while (true) {
                        i10--;
                        if (d4 >= dArr[i10]) {
                            break;
                        }
                        dArr[i10 + 1] = dArr[i10];
                    }
                    dArr[i10 + 1] = d4;
                }
                i = i8 + 1;
            }
        }
    }

    private static void a(double[] dArr, int i, int i2) {
        int i3 = i;
        while (true) {
            i3++;
            if (i3 < i2) {
                double d = dArr[i3];
                if (d < dArr[i3 - 1]) {
                    int i4 = i3;
                    while (true) {
                        i4--;
                        if (i4 < i || d >= dArr[i4]) {
                            break;
                        }
                        dArr[i4 + 1] = dArr[i4];
                    }
                    dArr[i4 + 1] = d;
                }
            } else {
                return;
            }
        }
    }

    private static void b(double[] dArr, int i, int i2) {
        int i3 = (i + i2) >>> 1;
        while (i3 > i) {
            i3--;
            a(dArr, i3, dArr[i3], i, i2);
        }
        while (true) {
            i2--;
            if (i2 > i) {
                double d = dArr[i];
                a(dArr, i, dArr[i2], i, i2);
                dArr[i2] = d;
            } else {
                return;
            }
        }
    }

    private static void a(double[] dArr, int i, double d, int i2, int i3) {
        while (true) {
            int i4 = ((i << 1) - i2) + 2;
            if (i4 > i3) {
                break;
            }
            if (i4 == i3 || dArr[i4] < dArr[i4 - 1]) {
                i4--;
            }
            if (dArr[i4] <= d) {
                break;
            }
            dArr[i] = dArr[i4];
            i = i4;
        }
        dArr[i] = d;
    }

    private static boolean a(c cVar, double[] dArr, int i, int i2) {
        int i3;
        double[] dArr2;
        double[] dArr3;
        int i4 = i + i2;
        int i5 = i + 1;
        int[] iArr = null;
        int i6 = 1;
        int i7 = i;
        while (i5 < i4) {
            int i8 = i5 - 1;
            if (dArr[i8] < dArr[i5]) {
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (dArr[i5 - 1] <= dArr[i5]);
            } else if (dArr[i8] > dArr[i5]) {
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (dArr[i5 - 1] >= dArr[i5]);
                int i9 = i7 - 1;
                int i10 = i5;
                while (true) {
                    i9++;
                    i10--;
                    if (i9 >= i10 || dArr[i9] <= dArr[i10]) {
                        break;
                    }
                    double d = dArr[i9];
                    dArr[i9] = dArr[i10];
                    dArr[i10] = d;
                }
            } else {
                double d2 = dArr[i5];
                do {
                    i5++;
                    if (i5 >= i4) {
                        break;
                    }
                } while (d2 == dArr[i5]);
                if (i5 < i4) {
                    continue;
                }
            }
            if (iArr == null) {
                if (i5 == i4) {
                    return true;
                }
                if (i5 - i < 16) {
                    return false;
                }
                iArr = new int[((i2 >> 10) | 127) & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES];
                iArr[0] = i;
            } else if (dArr[i7 - 1] > dArr[i7]) {
                if (i6 > ((i5 - i) >> 7) || (i6 = i6 + 1) == 5120) {
                    return false;
                }
                if (i6 == iArr.length) {
                    iArr = Arrays.copyOf(iArr, i6 << 1);
                }
            }
            iArr[i6] = i5;
            i7 = i5;
        }
        if (i6 > 1) {
            if (cVar == null || (dArr3 = (double[]) cVar.b) == null) {
                dArr2 = new double[i2];
                i3 = i;
            } else {
                i3 = cVar.offset;
                dArr2 = dArr3;
            }
            a(dArr, dArr2, i3, 1, cVar != null, iArr, 0, i6);
        }
        return true;
    }

    static double[] a(double[] dArr, double[] dArr2, int i, int i2, boolean z, int[] iArr, int i3, int i4) {
        int i5;
        double[] dArr3;
        double[] dArr4;
        int i6 = i4 - i3;
        if (i6 != 1) {
            int i7 = i3;
            while (true) {
                i5 = i7 + 1;
                if (iArr[i5 + 1] > ((iArr[i3] + iArr[i4]) >>> 1)) {
                    break;
                }
                i7 = i5;
            }
            if (!z || i6 <= 4) {
                dArr3 = a(dArr, dArr2, i, -i2, false, iArr, i3, i5);
                dArr4 = a(dArr, dArr2, i, 0, false, iArr, i5, i4);
            } else {
                b a2 = new b(dArr, dArr2, i, 0, iArr, i5, i4).a();
                double[] a3 = a(dArr, dArr2, i, -i2, true, iArr, i3, i5);
                dArr4 = (double[]) a2.b();
                dArr3 = a3;
            }
            double[] dArr5 = dArr3 == dArr ? dArr2 : dArr;
            int i8 = dArr3 == dArr ? iArr[i3] - i : iArr[i3];
            int i9 = dArr3 == dArr2 ? iArr[i3] - i : iArr[i3];
            int i10 = dArr3 == dArr2 ? iArr[i5] - i : iArr[i5];
            int i11 = dArr4 == dArr2 ? iArr[i5] - i : iArr[i5];
            int i12 = dArr4 == dArr2 ? iArr[i4] - i : iArr[i4];
            if (z) {
                new a(null, dArr5, i8, dArr3, i9, i10, dArr4, i11, i12).invoke();
            } else {
                a((a) null, dArr5, i8, dArr3, i9, i10, dArr4, i11, i12);
            }
            return dArr5;
        } else if (i2 >= 0) {
            return dArr;
        } else {
            int i13 = iArr[i4];
            int i14 = i13 - i;
            int i15 = iArr[i3];
            while (i13 > i15) {
                i14--;
                i13--;
                dArr2[i14] = dArr[i13];
            }
            return dArr2;
        }
    }

    static void a(a aVar, double[] dArr, int i, double[] dArr2, int i2, int i3, double[] dArr3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        double d;
        if (aVar == null || dArr2 != dArr3) {
            i10 = i;
            i7 = i2;
            i9 = i3;
            i6 = i4;
            i8 = i5;
        } else {
            int i11 = i2;
            int i12 = i3;
            int i13 = i4;
            int i14 = i5;
            while (true) {
                if (i12 - i11 < i14 - i13) {
                    i8 = i12;
                    i6 = i11;
                    i9 = i14;
                    i7 = i13;
                } else {
                    i9 = i12;
                    i7 = i11;
                    i8 = i14;
                    i6 = i13;
                }
                if (i9 - i7 < 4096) {
                    break;
                }
                int i15 = (i7 + i9) >>> 1;
                double d2 = dArr2[i15];
                int i16 = i8;
                int i17 = i6;
                while (i17 < i16) {
                    int i18 = (i17 + i16) >>> 1;
                    if (d2 > dArr3[i18]) {
                        i17 = i18 + 1;
                    } else {
                        i16 = i18;
                    }
                }
                aVar.a(dArr, i + (((i16 - i6) + i15) - i7), dArr2, i15, i9, dArr3, i16, i8);
                i11 = i7;
                i13 = i6;
                i12 = i15;
                i14 = i16;
            }
            i10 = i;
        }
        while (i7 < i9 && i6 < i8) {
            i10++;
            if (dArr2[i7] < dArr3[i6]) {
                i7++;
                d = dArr2[i7];
            } else {
                i6++;
                d = dArr3[i6];
            }
            dArr[i10] = d;
        }
        if (dArr != dArr2 || i10 < i7) {
            while (i7 < i9) {
                i10++;
                i7++;
                dArr[i10] = dArr2[i7];
            }
        }
        if (dArr != dArr3 || i10 < i6) {
            while (i6 < i8) {
                i10++;
                i6++;
                dArr[i10] = dArr3[i6];
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DualPivotQuicksort.java */
    /* loaded from: classes5.dex */
    public static final class c extends CountedCompleter<Void> {
        private static final long serialVersionUID = 20180818;
        final Object a;
        final Object b;
        final int depth;
        final int low;
        final int offset;
        final int size;

        c(CountedCompleter<?> countedCompleter, Object obj, Object obj2, int i, int i2, int i3, int i4) {
            super(countedCompleter);
            this.a = obj;
            this.b = obj2;
            this.low = i;
            this.size = i2;
            this.offset = i3;
            this.depth = i4;
        }

        @Override // java8.util.concurrent.CountedCompleter
        public final void compute() {
            int i = this.depth;
            if (i < 0) {
                setPendingCount(2);
                int i2 = this.size >> 1;
                new c(this, this.b, this.a, this.low, i2, this.offset, this.depth + 1).fork();
                new c(this, this.b, this.a, this.low + i2, this.size - i2, this.offset, this.depth + 1).compute();
            } else {
                Object obj = this.a;
                if (obj instanceof int[]) {
                    int i3 = this.low;
                    p.a(this, (int[]) obj, i, i3, this.size + i3);
                } else if (obj instanceof long[]) {
                    int i4 = this.low;
                    p.a(this, (long[]) obj, i, i4, this.size + i4);
                } else if (obj instanceof float[]) {
                    int i5 = this.low;
                    p.a(this, (float[]) obj, i, i5, this.size + i5);
                } else if (obj instanceof double[]) {
                    int i6 = this.low;
                    p.a(this, (double[]) obj, i, i6, this.size + i6);
                } else {
                    throw new IllegalArgumentException("Unknown type of array: " + this.a.getClass().getName());
                }
            }
            tryComplete();
        }

        @Override // java8.util.concurrent.CountedCompleter
        public final void onCompletion(CountedCompleter<?> countedCompleter) {
            int i = this.depth;
            if (i < 0) {
                boolean z = true;
                int i2 = this.low + (this.size >> 1);
                if ((i & 1) != 0) {
                    z = false;
                }
                Object obj = this.a;
                int i3 = z ? this.low : this.low - this.offset;
                Object obj2 = this.b;
                int i4 = z ? this.low - this.offset : this.low;
                int i5 = z ? i2 - this.offset : i2;
                Object obj3 = this.b;
                if (z) {
                    i2 -= this.offset;
                }
                new a(null, obj, i3, obj2, i4, i5, obj3, i2, z ? (this.low + this.size) - this.offset : this.low + this.size).invoke();
            }
        }

        void a(int i, int i2, int i3) {
            addToPendingCount(1);
            new c(this, this.a, this.b, i2, i3 - i2, this.offset, i).fork();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DualPivotQuicksort.java */
    /* loaded from: classes5.dex */
    public static final class a extends CountedCompleter<Void> {
        private static final long serialVersionUID = 20180818;
        private final Object a1;
        private final Object a2;
        private final Object dst;
        private final int hi1;
        private final int hi2;
        private final int k;
        private final int lo1;
        private final int lo2;

        a(CountedCompleter<?> countedCompleter, Object obj, int i, Object obj2, int i2, int i3, Object obj3, int i4, int i5) {
            super(countedCompleter);
            this.dst = obj;
            this.k = i;
            this.a1 = obj2;
            this.lo1 = i2;
            this.hi1 = i3;
            this.a2 = obj3;
            this.lo2 = i4;
            this.hi2 = i5;
        }

        @Override // java8.util.concurrent.CountedCompleter
        public final void compute() {
            Object obj = this.dst;
            if (obj instanceof int[]) {
                p.a(this, (int[]) obj, this.k, (int[]) this.a1, this.lo1, this.hi1, (int[]) this.a2, this.lo2, this.hi2);
            } else if (obj instanceof long[]) {
                p.a(this, (long[]) obj, this.k, (long[]) this.a1, this.lo1, this.hi1, (long[]) this.a2, this.lo2, this.hi2);
            } else if (obj instanceof float[]) {
                p.a(this, (float[]) obj, this.k, (float[]) this.a1, this.lo1, this.hi1, (float[]) this.a2, this.lo2, this.hi2);
            } else if (obj instanceof double[]) {
                p.a(this, (double[]) obj, this.k, (double[]) this.a1, this.lo1, this.hi1, (double[]) this.a2, this.lo2, this.hi2);
            } else {
                throw new IllegalArgumentException("Unknown type of array: " + this.dst.getClass().getName());
            }
            propagateCompletion();
        }

        void a(Object obj, int i, Object obj2, int i2, int i3, Object obj3, int i4, int i5) {
            addToPendingCount(1);
            new a(this, obj, i, obj2, i2, i3, obj3, i4, i5).fork();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DualPivotQuicksort.java */
    /* loaded from: classes5.dex */
    public static final class b extends RecursiveTask<Object> {
        private static final long serialVersionUID = 20180818;
        private final Object a;
        private final int aim;
        private final Object b;
        private final int hi;
        private final int lo;
        private final int offset;
        private final int[] run;

        b(Object obj, Object obj2, int i, int i2, int[] iArr, int i3, int i4) {
            this.a = obj;
            this.b = obj2;
            this.offset = i;
            this.aim = i2;
            this.run = iArr;
            this.lo = i3;
            this.hi = i4;
        }

        @Override // java8.util.concurrent.RecursiveTask
        protected final Object compute() {
            Object obj = this.a;
            if (obj instanceof int[]) {
                return p.a((int[]) obj, (int[]) this.b, this.offset, this.aim, true, this.run, this.lo, this.hi);
            }
            if (obj instanceof long[]) {
                return p.a((long[]) obj, (long[]) this.b, this.offset, this.aim, true, this.run, this.lo, this.hi);
            }
            if (obj instanceof float[]) {
                return p.a((float[]) obj, (float[]) this.b, this.offset, this.aim, true, this.run, this.lo, this.hi);
            }
            if (obj instanceof double[]) {
                return p.a((double[]) obj, (double[]) this.b, this.offset, this.aim, true, this.run, this.lo, this.hi);
            }
            throw new IllegalArgumentException("Unknown type of array: " + this.a.getClass().getName());
        }

        b a() {
            fork();
            return this;
        }

        Object b() {
            join();
            return getRawResult();
        }
    }
}
