package io.netty.handler.codec.compression;

import io.netty.handler.codec.dns.DnsRecord;

/* compiled from: Bzip2DivSufSort.java */
/* loaded from: classes4.dex */
final class e {
    private static final int[] a = {-1, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
    private final int[] b;
    private final byte[] c;
    private final int d;

    private static int a(int i, int i2) {
        return i | (i2 << 8);
    }

    private static int b(int i) {
        return i >= 0 ? i : ~i;
    }

    private static int b(int i, int i2) {
        return (i << 8) | i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(byte[] bArr, int[] iArr, int i) {
        this.c = bArr;
        this.b = iArr;
        this.d = i;
    }

    private static void a(int[] iArr, int i, int[] iArr2, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr2[i2];
        iArr2[i2] = i3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0033, code lost:
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:?, code lost:
        return (r1[r5] & 255) - (r1[r7] & 255);
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0027, code lost:
        if (r7 >= r3) goto L_0x0033;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int a(int r5, int r6, int r7) {
        /*
            r4 = this;
            int[] r0 = r4.b
            byte[] r1 = r4.c
            int r2 = r5 + 1
            r2 = r0[r2]
            int r2 = r2 + 2
            int r3 = r6 + 1
            r3 = r0[r3]
            int r3 = r3 + 2
            r5 = r0[r5]
            int r5 = r5 + r7
            r6 = r0[r6]
            int r7 = r7 + r6
        L_0x0016:
            if (r5 >= r2) goto L_0x0025
            if (r7 >= r3) goto L_0x0025
            byte r6 = r1[r5]
            byte r0 = r1[r7]
            if (r6 != r0) goto L_0x0025
            int r5 = r5 + 1
            int r7 = r7 + 1
            goto L_0x0016
        L_0x0025:
            if (r5 >= r2) goto L_0x0035
            if (r7 >= r3) goto L_0x0033
            byte r5 = r1[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte r6 = r1[r7]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r5 = r5 - r6
            goto L_0x003a
        L_0x0033:
            r5 = 1
            goto L_0x003a
        L_0x0035:
            if (r7 >= r3) goto L_0x0039
            r5 = -1
            goto L_0x003a
        L_0x0039:
            r5 = 0
        L_0x003a:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.e.a(int, int, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002d, code lost:
        return (r1[r7] & 255) - (r1[r9] & 255);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0047, code lost:
        if (r9 >= r8) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:?, code lost:
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:?, code lost:
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:?, code lost:
        return (r1[r7] & 255) - (r1[r9] & 255);
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0021, code lost:
        if (r9 >= r8) goto L_?;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int a(int r6, int r7, int r8, int r9, int r10) {
        /*
            r5 = this;
            int[] r0 = r5.b
            byte[] r1 = r5.c
            r7 = r0[r7]
            int r7 = r7 + r9
            r2 = r0[r8]
            int r9 = r9 + r2
            r2 = 1
            int r8 = r8 + r2
            r8 = r0[r8]
            int r8 = r8 + 2
        L_0x0010:
            if (r7 >= r10) goto L_0x001f
            if (r9 >= r8) goto L_0x001f
            byte r3 = r1[r7]
            byte r4 = r1[r9]
            if (r3 != r4) goto L_0x001f
            int r7 = r7 + 1
            int r9 = r9 + 1
            goto L_0x0010
        L_0x001f:
            if (r7 >= r10) goto L_0x002e
            if (r9 >= r8) goto L_0x002d
            byte r6 = r1[r7]
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte r7 = r1[r9]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r2 = r6 - r7
        L_0x002d:
            return r2
        L_0x002e:
            if (r9 != r8) goto L_0x0031
            return r2
        L_0x0031:
            int r7 = r7 % r10
            r6 = r0[r6]
            int r6 = r6 + 2
        L_0x0036:
            if (r7 >= r6) goto L_0x0045
            if (r9 >= r8) goto L_0x0045
            byte r10 = r1[r7]
            byte r0 = r1[r9]
            if (r10 != r0) goto L_0x0045
            int r7 = r7 + 1
            int r9 = r9 + 1
            goto L_0x0036
        L_0x0045:
            if (r7 >= r6) goto L_0x0054
            if (r9 >= r8) goto L_0x0059
            byte r6 = r1[r7]
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte r7 = r1[r9]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r2 = r6 - r7
            goto L_0x0059
        L_0x0054:
            if (r9 >= r8) goto L_0x0058
            r2 = -1
            goto L_0x0059
        L_0x0058:
            r2 = 0
        L_0x0059:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.e.a(int, int, int, int, int):int");
    }

    private void a(int i, int i2, int i3, int i4) {
        int a2;
        int[] iArr = this.b;
        for (int i5 = i3 - 2; i2 <= i5; i5--) {
            int i6 = iArr[i5];
            int i7 = i5 + 1;
            do {
                a2 = a(i + i6, iArr[i7] + i, i4);
                if (a2 <= 0) {
                    break;
                }
                do {
                    iArr[i7 - 1] = iArr[i7];
                    i7++;
                    if (i7 < i3) {
                    }
                } while (iArr[i7] < 0);
                continue;
            } while (i3 > i7);
            if (a2 == 0) {
                iArr[i7] = ~iArr[i7];
            }
            iArr[i7 - 1] = i6;
        }
    }

    private void b(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = this.b;
        byte[] bArr = this.c;
        int i6 = iArr[i3 + i4];
        int i7 = bArr[iArr[i2 + i6] + i] & 255;
        while (true) {
            int i8 = (i4 * 2) + 1;
            if (i8 >= i5) {
                break;
            }
            int i9 = i8 + 1;
            int i10 = bArr[iArr[iArr[i3 + i8] + i2] + i] & 255;
            int i11 = bArr[iArr[iArr[i3 + i9] + i2] + i] & 255;
            if (i10 < i11) {
                i8 = i9;
                i10 = i11;
            }
            if (i10 <= i7) {
                break;
            }
            iArr[i4 + i3] = iArr[i3 + i8];
            i4 = i8;
        }
        iArr[i3 + i4] = i6;
    }

    private void b(int i, int i2, int i3, int i4) {
        int i5;
        int[] iArr = this.b;
        byte[] bArr = this.c;
        int i6 = i4 % 2;
        if (i6 == 0) {
            int i7 = i4 - 1;
            int i8 = (i7 / 2) + i3;
            int i9 = i3 + i7;
            if ((bArr[iArr[iArr[i8] + i2] + i] & 255) < (bArr[iArr[iArr[i9] + i2] + i] & 255)) {
                a(iArr, i9, iArr, i8);
            }
            i5 = i7;
        } else {
            i5 = i4;
        }
        for (int i10 = (i5 / 2) - 1; i10 >= 0; i10--) {
            b(i, i2, i3, i10, i5);
        }
        if (i6 == 0) {
            a(iArr, i3, iArr, i3 + i5);
            b(i, i2, i3, 0, i5);
        }
        for (int i11 = i5 - 1; i11 > 0; i11--) {
            int i12 = iArr[i3];
            int i13 = i3 + i11;
            iArr[i3] = iArr[i13];
            b(i, i2, i3, 0, i11);
            iArr[i13] = i12;
        }
    }

    private int c(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = this.b;
        byte[] bArr = this.c;
        int i6 = bArr[iArr[iArr[i3] + i2] + i] & 255;
        int i7 = bArr[iArr[iArr[i4] + i2] + i] & 255;
        int i8 = bArr[i + iArr[i2 + iArr[i5]]] & 255;
        if (i6 <= i7) {
            i4 = i3;
            i3 = i4;
            i7 = i6;
            i6 = i7;
        }
        return i6 > i8 ? i7 > i8 ? i4 : i5 : i3;
    }

    private int a(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8;
        int i9;
        int i10;
        int[] iArr = this.b;
        byte[] bArr = this.c;
        int i11 = bArr[iArr[iArr[i3] + i2] + i] & 255;
        int i12 = bArr[iArr[iArr[i4] + i2] + i] & 255;
        int i13 = bArr[iArr[iArr[i5] + i2] + i] & 255;
        int i14 = bArr[iArr[iArr[i6] + i2] + i] & 255;
        int i15 = bArr[iArr[iArr[i7] + i2] + i] & 255;
        if (i12 > i13) {
            i8 = i5;
            i11 = i12;
            i12 = i13;
            i8 = i4;
        } else {
            i8 = i4;
            i11 = i13;
            i8 = i5;
        }
        if (i14 > i15) {
            i8 = i7;
            i9 = i14;
            i10 = i6;
        } else {
            i8 = i6;
            i9 = i15;
            i15 = i14;
            i10 = i7;
        }
        if (i12 > i15) {
            i15 = i12;
            i10 = i8;
            i8 = i10;
        } else {
            i9 = i11;
            i11 = i9;
        }
        if (i11 > i9) {
            i10 = i3;
        } else {
            i10 = i8;
            i8 = i3;
            i9 = i11;
            i11 = i9;
        }
        if (i9 <= i15) {
            i9 = i15;
        }
        return i11 > i9 ? i8 : i10;
    }

    private int c(int i, int i2, int i3, int i4) {
        int i5 = i4 - i3;
        int i6 = i3 + (i5 / 2);
        if (i5 > 512) {
            int i7 = i5 >> 3;
            int i8 = i7 << 1;
            int i9 = i4 - 1;
            return c(i, i2, c(i, i2, i3, i3 + i7, i3 + i8), c(i, i2, i6 - i7, i6, i6 + i7), c(i, i2, i9 - i8, i9 - i7, i9));
        } else if (i5 <= 32) {
            return c(i, i2, i3, i6, i4 - 1);
        } else {
            int i10 = i5 >> 2;
            int i11 = i4 - 1;
            return a(i, i2, i3, i3 + i10, i6, i11 - i10, i11);
        }
    }

    private static int a(int i) {
        return (65280 & i) != 0 ? a[(i >> 8) & 255] + 8 : a[i & 255];
    }

    private int d(int i, int i2, int i3, int i4) {
        int[] iArr = this.b;
        int i5 = i2 - 1;
        while (true) {
            i5++;
            if (i5 >= i3 || iArr[iArr[i5] + i] + i4 < iArr[iArr[i5] + i + 1] + 1) {
                i3--;
                while (i5 < i3 && iArr[iArr[i3] + i] + i4 < iArr[iArr[i3] + i + 1] + 1) {
                    i3--;
                }
                if (i3 <= i5) {
                    break;
                }
                int i6 = ~iArr[i3];
                iArr[i3] = iArr[i5];
                iArr[i5] = i6;
            } else {
                iArr[i5] = ~iArr[i5];
            }
        }
        if (i2 < i5) {
            iArr[i2] = ~iArr[i2];
        }
        return i5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Bzip2DivSufSort.java */
    /* loaded from: classes4.dex */
    public static class b {
        final int a;
        final int b;
        final int c;
        final int d;

        b(int i, int i2, int i3, int i4) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
        }
    }

    private void e(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        e eVar = this;
        int[] iArr = eVar.b;
        byte[] bArr = eVar.c;
        b[] bVarArr = new b[64];
        int i8 = 0;
        int i9 = -1;
        int i10 = i4;
        int a2 = a(i3 - i2);
        int i11 = 0;
        int i12 = i2;
        int i13 = i3;
        while (true) {
            int i14 = i13 - i12;
            if (i14 <= 8) {
                if (1 < i14) {
                    eVar.a(i, i12, i13, i10);
                }
                if (i8 != 0) {
                    i8--;
                    b bVar = bVarArr[i8];
                    i12 = bVar.a;
                    int i15 = bVar.b;
                    int i16 = bVar.c;
                    a2 = bVar.d;
                    i13 = i15;
                    i10 = i16;
                } else {
                    return;
                }
            } else {
                int i17 = a2 - 1;
                if (a2 == 0) {
                    eVar.b(i10, i, i12, i14);
                }
                if (i17 < 0) {
                    i12++;
                    int i18 = bArr[iArr[iArr[i12] + i] + i10] & 255;
                    int i19 = i12;
                    while (i12 < i13) {
                        i11 = bArr[iArr[iArr[i12] + i] + i10] & 255;
                        if (i11 != i18) {
                            if (1 < i12 - i19) {
                                break;
                            }
                            i19 = i12;
                            i18 = i11;
                        }
                        i12++;
                    }
                    if ((bArr[(iArr[iArr[i19] + i] + i10) - 1] & 255) < i18) {
                        i19 = eVar.d(i, i19, i12, i10);
                    }
                    int i20 = i12 - i19;
                    int i21 = i13 - i12;
                    if (i20 <= i21) {
                        if (1 < i20) {
                            i8++;
                            bVarArr[i8] = new b(i12, i13, i10, i9);
                            i10++;
                            a2 = a(i20);
                            i13 = i12;
                            i12 = i19;
                        } else {
                            a2 = i9;
                        }
                    } else if (1 < i21) {
                        i8++;
                        bVarArr[i8] = new b(i19, i12, i10 + 1, a(i20));
                        a2 = i9;
                    } else {
                        i10++;
                        a2 = a(i20);
                        i13 = i12;
                        i12 = i19;
                    }
                } else {
                    int c2 = eVar.c(i10, i, i12, i13);
                    int i22 = bArr[iArr[iArr[c2] + i] + i10] & 255;
                    a(iArr, i12, iArr, c2);
                    int i23 = i12 + 1;
                    while (i23 < i13) {
                        i11 = bArr[iArr[iArr[i23] + i] + i10] & 255;
                        if (i11 != i22) {
                            break;
                        }
                        i23++;
                    }
                    if (i23 < i13 && i11 < i22) {
                        i5 = i23;
                        while (true) {
                            i23++;
                            if (i23 >= i13 || (i11 = bArr[iArr[iArr[i23] + i] + i10] & 255) > i22) {
                                break;
                            } else if (i11 == i22) {
                                a(iArr, i23, iArr, i5);
                                i5++;
                            }
                        }
                    } else {
                        i5 = i23;
                    }
                    int i24 = i13 - 1;
                    while (i23 < i24) {
                        i11 = bArr[i10 + iArr[i + iArr[i24]]] & 255;
                        if (i11 != i22) {
                            break;
                        }
                        i24--;
                        i11 = i11;
                    }
                    if (i23 < i24 && i11 > i22) {
                        i6 = i11;
                        i7 = i24;
                        while (true) {
                            i24 += i9;
                            if (i23 < i24) {
                                int i25 = bArr[i10 + iArr[i + iArr[i24]]] & 255;
                                if (i25 < i22) {
                                    i6 = i25;
                                    break;
                                } else if (i25 == i22) {
                                    a(iArr, i24, iArr, i7);
                                    i7--;
                                    i6 = i25;
                                    i9 = -1;
                                } else {
                                    i6 = i25;
                                    i9 = -1;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        i6 = i11;
                        i7 = i24;
                    }
                    while (i23 < i24) {
                        a(iArr, i23, iArr, i24);
                        while (true) {
                            i23++;
                            if (i23 < i24) {
                                int i26 = bArr[iArr[iArr[i23] + i] + i10] & 255;
                                if (i26 > i22) {
                                    i6 = i26;
                                    break;
                                } else if (i26 == i22) {
                                    a(iArr, i23, iArr, i5);
                                    i5++;
                                    i6 = i26;
                                } else {
                                    i6 = i26;
                                }
                            } else {
                                break;
                            }
                        }
                        while (true) {
                            i24--;
                            if (i23 < i24) {
                                int i27 = bArr[i10 + iArr[i + iArr[i24]]] & 255;
                                if (i27 < i22) {
                                    i6 = i27;
                                    break;
                                } else if (i27 == i22) {
                                    a(iArr, i24, iArr, i7);
                                    i7--;
                                    i6 = i27;
                                } else {
                                    i6 = i27;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                    if (i5 <= i7) {
                        int i28 = i23 - 1;
                        int i29 = i5 - i12;
                        int i30 = i23 - i5;
                        if (i29 > i30) {
                            i29 = i30;
                        }
                        int i31 = i23 - i29;
                        int i32 = i12;
                        while (i29 > 0) {
                            a(iArr, i32, iArr, i31);
                            i29--;
                            i32++;
                            i31++;
                        }
                        int i33 = i7 - i28;
                        int i34 = (i13 - i7) - 1;
                        if (i33 <= i34) {
                            i34 = i33;
                        }
                        int i35 = i13 - i34;
                        int i36 = i23;
                        while (i34 > 0) {
                            a(iArr, i36, iArr, i35);
                            i34--;
                            i36++;
                            i35++;
                        }
                        int i37 = i12 + i30;
                        int i38 = i13 - i33;
                        int d = i22 <= (bArr[(iArr[iArr[i37] + i] + i10) + (-1)] & 255) ? i37 : eVar.d(i, i37, i38, i10);
                        int i39 = i37 - i12;
                        int i40 = i13 - i38;
                        if (i39 <= i40) {
                            int i41 = i38 - d;
                            if (i40 <= i41) {
                                int i42 = i8 + 1;
                                bVarArr[i8] = new b(d, i38, i10 + 1, a(i41));
                                i8 = i42 + 1;
                                bVarArr[i42] = new b(i38, i13, i10, i17);
                                i13 = i37;
                                a2 = i17;
                                i11 = i6;
                                i9 = -1;
                            } else if (i39 <= i41) {
                                int i43 = i8 + 1;
                                bVarArr[i8] = new b(i38, i13, i10, i17);
                                i8 = i43 + 1;
                                bVarArr[i43] = new b(d, i38, i10 + 1, a(i41));
                                i13 = i37;
                                a2 = i17;
                                i11 = i6;
                                i9 = -1;
                            } else {
                                int i44 = i8 + 1;
                                bVarArr[i8] = new b(i38, i13, i10, i17);
                                i8 = i44 + 1;
                                bVarArr[i44] = new b(i12, i37, i10, i17);
                                i10++;
                                a2 = a(i41);
                                i13 = i38;
                                i12 = d;
                                i11 = i6;
                                i9 = -1;
                            }
                        } else {
                            int i45 = i38 - d;
                            if (i39 <= i45) {
                                int i46 = i8 + 1;
                                bVarArr[i8] = new b(d, i38, i10 + 1, a(i45));
                                i8 = i46 + 1;
                                bVarArr[i46] = new b(i12, i37, i10, i17);
                                i12 = i38;
                                a2 = i17;
                                i11 = i6;
                                eVar = this;
                                i9 = -1;
                            } else if (i40 <= i45) {
                                int i47 = i8 + 1;
                                bVarArr[i8] = new b(i12, i37, i10, i17);
                                i8 = i47 + 1;
                                bVarArr[i47] = new b(d, i38, i10 + 1, a(i45));
                                i12 = i38;
                                a2 = i17;
                                i11 = i6;
                                eVar = this;
                                i9 = -1;
                            } else {
                                int i48 = i8 + 1;
                                bVarArr[i8] = new b(i12, i37, i10, i17);
                                i8 = i48 + 1;
                                bVarArr[i48] = new b(i38, i13, i10, i17);
                                i10++;
                                a2 = a(i45);
                                i13 = i38;
                                i12 = d;
                                i11 = i6;
                                eVar = this;
                                i9 = -1;
                            }
                        }
                    } else {
                        a2 = i17 + 1;
                        if ((bArr[(iArr[iArr[i12] + i] + i10) - 1] & 255) < i22) {
                            eVar = this;
                            i12 = eVar.d(i, i12, i13, i10);
                            a2 = a(i13 - i12);
                        } else {
                            eVar = this;
                        }
                        i10++;
                        i11 = i6;
                        i9 = -1;
                    }
                }
            }
        }
    }

    private static void a(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        while (i3 > 0) {
            a(iArr, i, iArr2, i2);
            i3--;
            i++;
            i2++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0044, code lost:
        if (r9 >= r2) goto L_0x0055;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0046, code lost:
        r7 = r3 + 1;
        r0[r3] = r8[r9];
        r9 = r9 + 1;
        r8[r9] = r0[r7];
        r3 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0055, code lost:
        r0[r3] = r8[r9];
        r8[r9] = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005b, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0080, code lost:
        r9 = r3 + 1;
        r0[r3] = r0[r11];
        r3 = r11 + 1;
        r0[r11] = r0[r9];
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008c, code lost:
        if (r12 > r3) goto L_0x00a6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008e, code lost:
        if (r10 >= r2) goto L_0x009f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0090, code lost:
        r7 = r9 + 1;
        r0[r9] = r8[r10];
        r10 = r10 + 1;
        r8[r10] = r0[r7];
        r9 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x009f, code lost:
        r0[r9] = r8[r10];
        r8[r10] = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a5, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a8, code lost:
        if (r0[r3] < 0) goto L_0x00b0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00aa, code lost:
        r11 = r3;
        r10 = r9;
        r9 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b0, code lost:
        r11 = r3;
        r3 = r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(int r7, int[] r8, int r9, int r10, int r11, int r12, int r13) {
        /*
            Method dump skipped, instructions count: 182
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.e.a(int, int[], int, int, int, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a3, code lost:
        if (r11 >= r2) goto L_0x00b4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a5, code lost:
        r9 = r5 - 1;
        r0[r5] = r10[r2];
        r2 = r2 - 1;
        r10[r2] = r0[r9];
        r5 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b4, code lost:
        r0[r5] = r10[r2];
        r10[r2] = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ba, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x012c, code lost:
        if (r11 >= r14) goto L_0x013d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x012e, code lost:
        r9 = r3 - 1;
        r0[r3] = r10[r14];
        r14 = r14 - 1;
        r10[r14] = r0[r9];
        r3 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x013d, code lost:
        r0[r3] = r10[r14];
        r10[r14] = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0143, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(int r9, int[] r10, int r11, int r12, int r13, int r14, int r15) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.e.b(int, int[], int, int, int, int, int):void");
    }

    private void b(int i, int i2, int i3) {
        int[] iArr = this.b;
        if (iArr[i3] >= 0 && a(b(iArr[i3 - 1]) + i, i + iArr[i3], i2) == 0) {
            iArr[i3] = ~iArr[i3];
        }
    }

    private void a(int i, int i2, int i3, int i4, int[] iArr, int i5, int i6, int i7) {
        int i8;
        int i9;
        int i10;
        int[] iArr2 = this.b;
        b[] bVarArr = new b[64];
        int i11 = i2;
        int i12 = i3;
        int i13 = i4;
        int i14 = 0;
        int i15 = 0;
        while (true) {
            int i16 = i13 - i12;
            if (i16 <= i6) {
                if (i11 >= i12 || i12 >= i13) {
                    i8 = i11;
                } else {
                    i8 = i11;
                    b(i, iArr, i5, i11, i12, i13, i7);
                }
                if ((i14 & 1) != 0) {
                    b(i, i7, i8);
                }
                if ((i14 & 2) != 0) {
                    b(i, i7, i13);
                }
                if (i15 != 0) {
                    i15--;
                    b bVar = bVarArr[i15];
                    i11 = bVar.a;
                    i12 = bVar.b;
                    i13 = bVar.c;
                    i14 = bVar.d;
                } else {
                    return;
                }
            } else {
                int i17 = i12 - i11;
                if (i17 <= i6) {
                    if (i11 < i12) {
                        a(i, iArr, i5, i11, i12, i13, i7);
                    }
                    if ((i14 & 1) != 0) {
                        b(i, i7, i11);
                    }
                    if ((i14 & 2) != 0) {
                        b(i, i7, i13);
                    }
                    if (i15 != 0) {
                        i15--;
                        b bVar2 = bVarArr[i15];
                        i11 = bVar2.a;
                        i12 = bVar2.b;
                        i13 = bVar2.c;
                        i14 = bVar2.d;
                    } else {
                        return;
                    }
                } else {
                    int min = Math.min(i17, i16);
                    min >>= 1;
                    int i18 = 0;
                    while (min > 0) {
                        if (a(b(iArr2[i12 + i18 + min]) + i, b(iArr2[((i12 - i18) - min) - 1]) + i, i7) < 0) {
                            i18 += min + 1;
                            min -= (min & 1) ^ 1;
                        }
                        min >>= 1;
                    }
                    if (i18 > 0) {
                        int i19 = i12 - i18;
                        a(iArr2, i19, iArr2, i12, i18);
                        int i20 = i18 + i12;
                        if (i20 < i13) {
                            if (iArr2[i20] < 0) {
                                i10 = i12;
                                while (iArr2[i10 - 1] < 0) {
                                    i10--;
                                }
                                iArr2[i20] = ~iArr2[i20];
                            } else {
                                i10 = i12;
                            }
                            int i21 = i12;
                            while (iArr2[i21] < 0) {
                                i21++;
                            }
                            i11 = i21;
                            i9 = 1;
                        } else {
                            i10 = i12;
                            i11 = i10;
                            i9 = 0;
                        }
                        if (i10 - i11 <= i13 - i11) {
                            i15++;
                            bVarArr[i15] = new b(i11, i20, i13, (i9 & 1) | (i14 & 2));
                            i14 &= 1;
                            i12 = i19;
                            i13 = i10;
                            i11 = i11;
                        } else {
                            if (i10 == i12 && i12 == i11) {
                                i9 <<= 1;
                            }
                            i15++;
                            bVarArr[i15] = new b(i11, i19, i10, (i14 & 1) | (i9 & 2));
                            i14 = (i14 & 2) | (1 & i9);
                            i12 = i20;
                        }
                    } else {
                        if ((i14 & 1) != 0) {
                            b(i, i7, i11);
                        }
                        b(i, i7, i12);
                        if ((i14 & 2) != 0) {
                            b(i, i7, i13);
                        }
                        if (i15 != 0) {
                            i15--;
                            b bVar3 = bVarArr[i15];
                            i11 = bVar3.a;
                            i12 = bVar3.b;
                            i13 = bVar3.c;
                            i14 = bVar3.d;
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    private void a(int i, int i2, int i3, int[] iArr, int i4, int i5, int i6, boolean z, int i7) {
        int i8;
        int[] iArr2;
        int i9;
        int i10;
        int[] iArr3 = this.b;
        int i11 = z ? i2 + 1 : i2;
        int i12 = 0;
        int i13 = i11;
        while (true) {
            int i14 = i13 + 1024;
            i8 = 1;
            if (i14 >= i3) {
                break;
            }
            e(i, i13, i14, i6);
            int i15 = i3 - i14;
            if (i15 <= i5) {
                iArr2 = iArr;
                i10 = i4;
                i9 = i5;
            } else {
                i9 = i15;
                i10 = i14;
                iArr2 = iArr3;
            }
            int i16 = i13;
            int i17 = 1024;
            int i18 = i12;
            while ((i18 & 1) != 0) {
                int i19 = i16 - i17;
                a(i, i19, i16, i16 + i17, iArr2, i10, i9, i6);
                i17 <<= 1;
                i18 >>>= 1;
                i16 = i19;
                i14 = i14;
            }
            i12++;
            i13 = i14;
        }
        e(i, i13, i3, i6);
        int i20 = i13;
        int i21 = 1024;
        while (i12 != 0) {
            if ((i12 & 1) != 0) {
                int i22 = i20 - i21;
                a(i, i22, i20, i3, iArr, i4, i5, i6);
                i20 = i22;
            }
            i21 <<= 1;
            i12 >>= 1;
        }
        if (z) {
            int i23 = iArr3[i11 - 1];
            while (i11 < i3 && (iArr3[i11] < 0 || (i8 = a(i, i + i23, i + iArr3[i11], i6, i7)) > 0)) {
                iArr3[i11 - 1] = iArr3[i11];
                i11++;
            }
            if (i8 == 0) {
                iArr3[i11] = ~iArr3[i11];
            }
            iArr3[i11 - 1] = i23;
        }
    }

    private int f(int i, int i2, int i3, int i4) {
        int i5 = i2 + i4;
        return i5 < i3 ? this.b[i5] : this.b[i + (((i2 - i) + i4) % (i3 - i))];
    }

    private void a(int i, int i2, int i3, int i4, int i5, int i6) {
        int[] iArr = this.b;
        int i7 = iArr[i4 + i5];
        int f = f(i, i2, i3, i7);
        while (true) {
            int i8 = (i5 * 2) + 1;
            if (i8 >= i6) {
                break;
            }
            int i9 = i8 + 1;
            int f2 = f(i, i2, i3, iArr[i4 + i8]);
            int f3 = f(i, i2, i3, iArr[i4 + i9]);
            if (f2 < f3) {
                i8 = i9;
                f2 = f3;
            }
            if (f2 <= f) {
                break;
            }
            iArr[i5 + i4] = iArr[i4 + i8];
            i5 = i8;
        }
        iArr[i4 + i5] = i7;
    }

    private void d(int i, int i2, int i3, int i4, int i5) {
        int i6;
        int[] iArr = this.b;
        int i7 = i5 % 2;
        if (i7 == 0) {
            int i8 = i5 - 1;
            int i9 = (i8 / 2) + i4;
            int i10 = i4 + i8;
            if (f(i, i2, i3, iArr[i9]) < f(i, i2, i3, iArr[i10])) {
                a(iArr, i10, iArr, i9);
            }
            i6 = i8;
        } else {
            i6 = i5;
        }
        for (int i11 = (i6 / 2) - 1; i11 >= 0; i11--) {
            a(i, i2, i3, i4, i11, i6);
        }
        if (i7 == 0) {
            a(iArr, i4, iArr, i4 + i6);
            a(i, i2, i3, i4, 0, i6);
        }
        for (int i12 = i6 - 1; i12 > 0; i12--) {
            int i13 = iArr[i4];
            int i14 = i4 + i12;
            iArr[i4] = iArr[i14];
            a(i, i2, i3, i4, 0, i12);
            iArr[i14] = i13;
        }
    }

    private void e(int i, int i2, int i3, int i4, int i5) {
        int f;
        int[] iArr = this.b;
        for (int i6 = i4 + 1; i6 < i5; i6++) {
            int i7 = iArr[i6];
            int i8 = i6 - 1;
            do {
                f = f(i, i2, i3, i7) - f(i, i2, i3, iArr[i8]);
                if (f >= 0) {
                    break;
                }
                do {
                    iArr[i8 + 1] = iArr[i8];
                    i8--;
                    if (i4 <= i8) {
                    }
                } while (iArr[i8] < 0);
                continue;
            } while (i8 >= i4);
            if (f == 0) {
                iArr[i8] = ~iArr[i8];
            }
            iArr[i8 + 1] = i7;
        }
    }

    private static int c(int i) {
        return ((-65536) & i) != 0 ? ((-16777216) & i) != 0 ? a[(i >> 24) & 255] + 24 : a[(i >> 16) & 271] : (65280 & i) != 0 ? a[(i >> 8) & 255] + 8 : a[i & 255];
    }

    private int b(int i, int i2, int i3, int i4, int i5, int i6) {
        int[] iArr = this.b;
        int f = f(i, i2, i3, iArr[i4]);
        int f2 = f(i, i2, i3, iArr[i5]);
        int f3 = f(i, i2, i3, iArr[i6]);
        if (f <= f2) {
            i5 = i4;
            i4 = i5;
            f2 = f;
            f = f2;
        }
        return f > f3 ? f2 > f3 ? i5 : i6 : i4;
    }

    private int a(int i, int i2, int i3, int i4, int i42, int i5, int i52, int i422) {
        int[] iArr = this.b;
        int f = f(i, i2, i3, iArr[i422]);
        int f2 = f(i, i2, i3, iArr[i422]);
        int f3 = f(i, i2, i3, iArr[i52]);
        f3 = f(i, i2, i3, iArr[i52]);
        int f4 = f(i, i2, i3, iArr[i422]);
        if (f2 > f3) {
            i52 = i422;
            i422 = i52;
            f3 = f2;
            f2 = f3;
        }
        if (f3 <= f4) {
            f3 = f4;
            f4 = f3;
            i422 = i52;
            i52 = i422;
        }
        if (f2 > f4) {
            f4 = f2;
            i52 = i52;
            i52 = i52;
            f3 = f3;
            f3 = f3;
        }
        if (f > f3) {
            i52 = i422;
            i422 = i52;
            f3 = f;
            f = f3;
        }
        if (f > f4) {
            f4 = f;
        }
        return f3 > f4 ? i422 : i52;
    }

    private int f(int i, int i2, int i3, int i4, int i5) {
        int i6 = i5 - i4;
        int i7 = i4 + (i6 / 2);
        if (i6 > 512) {
            int i8 = i6 >> 3;
            int i9 = i8 << 1;
            int i10 = i5 - 1;
            return b(i, i2, i3, b(i, i2, i3, i4, i4 + i8, i4 + i9), b(i, i2, i3, i7 - i8, i7, i7 + i8), b(i, i2, i3, i10 - i9, i10 - i8, i10));
        } else if (i6 <= 32) {
            return b(i, i2, i3, i4, i7, i5 - 1);
        } else {
            int i11 = i6 >> 2;
            int i12 = i5 - 1;
            return a(i, i2, i3, i4, i4 + i11, i7, i12 - i11, i12);
        }
    }

    private void c(int i, int i2, int i3) {
        int[] iArr = this.b;
        while (i2 < i3) {
            if (iArr[i2] >= 0) {
                int i4 = i2;
                do {
                    iArr[iArr[i4] + i] = i4;
                    i4++;
                    if (i4 >= i3) {
                        break;
                    }
                } while (iArr[i4] >= 0);
                iArr[i2] = i2 - i4;
                if (i3 > i4) {
                    i2 = i4;
                } else {
                    return;
                }
            }
            int i5 = i2;
            do {
                iArr[i5] = ~iArr[i5];
                i5++;
            } while (iArr[i5] < 0);
            do {
                iArr[iArr[i2] + i] = i5;
                i2++;
            } while (i2 <= i5);
            i2 = i5 + 1;
        }
    }

    private void g(int i, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int[] iArr = this.b;
        b[] bVarArr = new b[64];
        int c2 = c(i5 - i4);
        int i8 = i4;
        int i9 = i5;
        int i10 = 0;
        int i11 = 0;
        while (true) {
            int i12 = i9 - i8;
            if (i12 <= 8) {
                if (1 < i12) {
                    e(i, i2, i3, i8, i9);
                    c(i, i8, i9);
                } else if (i12 == 1) {
                    iArr[i8] = -1;
                }
                if (i10 != 0) {
                    i10--;
                    b bVar = bVarArr[i10];
                    i8 = bVar.a;
                    i9 = bVar.b;
                    c2 = bVar.c;
                } else {
                    return;
                }
            } else {
                int i13 = c2 - 1;
                if (c2 == 0) {
                    d(i, i2, i3, i8, i12);
                    int i14 = i9 - 1;
                    while (i8 < i14) {
                        int f = f(i, i2, i3, iArr[i14]);
                        i14--;
                        while (i8 <= i14 && f(i, i2, i3, iArr[i14]) == f) {
                            iArr[i14] = ~iArr[i14];
                            i14--;
                        }
                        i11 = f;
                    }
                    c(i, i8, i9);
                    if (i10 != 0) {
                        i10--;
                        b bVar2 = bVarArr[i10];
                        i8 = bVar2.a;
                        i9 = bVar2.b;
                        c2 = bVar2.c;
                    } else {
                        return;
                    }
                } else {
                    a(iArr, i8, iArr, f(i, i2, i3, i8, i9));
                    int f2 = f(i, i2, i3, iArr[i8]);
                    int i15 = i8 + 1;
                    while (i15 < i9) {
                        i11 = f(i, i2, i3, iArr[i15]);
                        if (i11 != f2) {
                            break;
                        }
                        i15++;
                        i11 = i11;
                    }
                    if (i15 < i9 && i11 < f2) {
                        i6 = i15;
                        while (true) {
                            i15++;
                            if (i15 >= i9 || (i11 = f(i, i2, i3, iArr[i15])) > f2) {
                                break;
                            } else if (i11 == f2) {
                                a(iArr, i15, iArr, i6);
                                i6++;
                            }
                        }
                    } else {
                        i6 = i15;
                    }
                    int i16 = i9 - 1;
                    while (i15 < i16) {
                        i11 = f(i, i2, i3, iArr[i16]);
                        if (i11 != f2) {
                            break;
                        }
                        i16--;
                    }
                    if (i15 >= i16 || i11 <= f2) {
                        i11 = i11;
                        i7 = i16;
                    } else {
                        i11 = i11;
                        i7 = i16;
                        while (true) {
                            i16--;
                            if (i15 >= i16 || (i11 = f(i, i2, i3, iArr[i16])) < f2) {
                                break;
                            } else if (i11 == f2) {
                                a(iArr, i16, iArr, i7);
                                i7--;
                            }
                        }
                    }
                    while (i15 < i16) {
                        a(iArr, i15, iArr, i16);
                        while (true) {
                            i15++;
                            if (i15 >= i16) {
                                break;
                            }
                            int f3 = f(i, i2, i3, iArr[i15]);
                            if (f3 > f2) {
                                i11 = f3;
                                break;
                            } else if (f3 == f2) {
                                a(iArr, i15, iArr, i6);
                                i6++;
                                i11 = f3;
                            } else {
                                i11 = f3;
                            }
                        }
                        while (true) {
                            i16--;
                            if (i15 >= i16) {
                                break;
                            }
                            int f4 = f(i, i2, i3, iArr[i16]);
                            if (f4 < f2) {
                                i11 = f4;
                                break;
                            } else if (f4 == f2) {
                                a(iArr, i16, iArr, i7);
                                i7--;
                                i11 = f4;
                            } else {
                                i11 = f4;
                            }
                        }
                    }
                    if (i6 <= i7) {
                        int i17 = i15 - 1;
                        int i18 = i6 - i8;
                        int i19 = i15 - i6;
                        if (i18 > i19) {
                            i18 = i19;
                        }
                        int i20 = i15 - i18;
                        int i21 = i8;
                        while (i18 > 0) {
                            a(iArr, i21, iArr, i20);
                            i18--;
                            i21++;
                            i20++;
                        }
                        int i22 = i7 - i17;
                        int i23 = (i9 - i7) - 1;
                        if (i22 <= i23) {
                            i23 = i22;
                        }
                        int i24 = i9 - i23;
                        while (i23 > 0) {
                            a(iArr, i15, iArr, i24);
                            i23--;
                            i15++;
                            i24++;
                        }
                        int i25 = i8 + i19;
                        int i26 = i9 - i22;
                        int i27 = i25 - 1;
                        for (int i28 = i8; i28 < i25; i28++) {
                            iArr[iArr[i28] + i] = i27;
                        }
                        if (i26 < i9) {
                            int i29 = i26 - 1;
                            for (int i30 = i25; i30 < i26; i30++) {
                                iArr[iArr[i30] + i] = i29;
                            }
                        }
                        if (i26 - i25 == 1) {
                            iArr[i25] = -1;
                        }
                        if (i25 - i8 <= i9 - i26) {
                            if (i8 < i25) {
                                i10++;
                                bVarArr[i10] = new b(i26, i9, i13, 0);
                                i9 = i25;
                                c2 = i13;
                            } else {
                                i8 = i26;
                                c2 = i13;
                            }
                        } else if (i26 < i9) {
                            i10++;
                            bVarArr[i10] = new b(i8, i25, i13, 0);
                            i8 = i26;
                            c2 = i13;
                        } else {
                            i9 = i25;
                            c2 = i13;
                        }
                    } else if (i10 != 0) {
                        i10--;
                        b bVar3 = bVarArr[i10];
                        i8 = bVar3.a;
                        i9 = bVar3.b;
                        c2 = bVar3.c;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0036, code lost:
        r13 = r0[r7];
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0038, code lost:
        if (r13 >= 0) goto L_0x003c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003a, code lost:
        r7 = r7 - r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003c, code lost:
        r13 = r0[r13 + r11] + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0041, code lost:
        if (r7 >= r13) goto L_0x004b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0043, code lost:
        r0[r0[r7] + r11] = r7;
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004b, code lost:
        r7 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004c, code lost:
        if (r7 < r12) goto L_0x0036;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:?, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void d(int r11, int r12, int r13) {
        /*
            r10 = this;
            int[] r0 = r10.b
            int r13 = r13 + r11
        L_0x0003:
            int r1 = -r12
            r7 = 0
            r2 = r0[r7]
            if (r1 >= r2) goto L_0x0051
            r1 = r7
            r5 = r1
        L_0x000b:
            r2 = r0[r5]
            if (r2 >= 0) goto L_0x0012
            int r5 = r5 - r2
            int r1 = r1 + r2
            goto L_0x002b
        L_0x0012:
            if (r1 == 0) goto L_0x001a
            int r3 = r5 + r1
            r0[r3] = r1
            r8 = r7
            goto L_0x001b
        L_0x001a:
            r8 = r1
        L_0x001b:
            int r2 = r2 + r11
            r1 = r0[r2]
            int r9 = r1 + 1
            int r4 = r11 + r12
            r1 = r10
            r2 = r11
            r3 = r13
            r6 = r9
            r1.g(r2, r3, r4, r5, r6)
            r1 = r8
            r5 = r9
        L_0x002b:
            if (r5 < r12) goto L_0x000b
            if (r1 == 0) goto L_0x0032
            int r5 = r5 + r1
            r0[r5] = r1
        L_0x0032:
            int r1 = r13 - r11
            if (r12 >= r1) goto L_0x004f
        L_0x0036:
            r13 = r0[r7]
            if (r13 >= 0) goto L_0x003c
            int r7 = r7 - r13
            goto L_0x004c
        L_0x003c:
            int r13 = r13 + r11
            r13 = r0[r13]
            int r13 = r13 + 1
        L_0x0041:
            if (r7 >= r13) goto L_0x004b
            r1 = r0[r7]
            int r1 = r1 + r11
            r0[r1] = r7
            int r7 = r7 + 1
            goto L_0x0041
        L_0x004b:
            r7 = r13
        L_0x004c:
            if (r7 < r12) goto L_0x0036
            goto L_0x0051
        L_0x004f:
            int r13 = r13 + r1
            goto L_0x0003
        L_0x0051:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.e.d(int, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Bzip2DivSufSort.java */
    /* loaded from: classes4.dex */
    public static class a {
        final int a;
        final int b;

        a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }
    }

    private a c(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        int i8;
        int f;
        int f2;
        int f3;
        int[] iArr = this.b;
        int i9 = 0;
        int i10 = i4;
        while (i10 < i5) {
            i9 = f(i, i2, i3, iArr[i10]);
            if (i9 != i6) {
                break;
            }
            i10++;
        }
        if (i10 < i5 && i9 < i6) {
            i7 = i10;
            while (true) {
                i10++;
                if (i10 >= i5 || (i9 = f(i, i2, i3, iArr[i10])) > i6) {
                    break;
                } else if (i9 == i6) {
                    a(iArr, i10, iArr, i7);
                    i7++;
                }
            }
        } else {
            i7 = i10;
        }
        int i11 = i5 - 1;
        while (i10 < i11) {
            i9 = f(i, i2, i3, iArr[i11]);
            if (i9 != i6) {
                break;
            }
            i11--;
        }
        if (i10 < i11 && i9 > i6) {
            i8 = i11;
            while (true) {
                i11--;
                if (i10 >= i11 || (f3 = f(i, i2, i3, iArr[i11])) < i6) {
                    break;
                } else if (f3 == i6) {
                    a(iArr, i11, iArr, i8);
                    i8--;
                }
            }
        } else {
            i8 = i11;
        }
        while (i10 < i11) {
            a(iArr, i10, iArr, i11);
            while (true) {
                i10++;
                if (i10 >= i11 || (f2 = f(i, i2, i3, iArr[i10])) > i6) {
                    break;
                } else if (f2 == i6) {
                    a(iArr, i10, iArr, i7);
                    i7++;
                }
            }
            while (true) {
                i11--;
                if (i10 < i11 && (f = f(i, i2, i3, iArr[i11])) >= i6) {
                    if (f == i6) {
                        a(iArr, i11, iArr, i8);
                        i8--;
                    }
                }
            }
        }
        if (i7 <= i8) {
            int i12 = i10 - 1;
            int i13 = i7 - i4;
            int i14 = i10 - i7;
            if (i13 > i14) {
                i13 = i14;
            }
            int i15 = i10 - i13;
            int i16 = i4;
            while (i13 > 0) {
                a(iArr, i16, iArr, i15);
                i13--;
                i16++;
                i15++;
            }
            int i17 = i8 - i12;
            int i18 = (i5 - i8) - 1;
            if (i17 <= i18) {
                i18 = i17;
            }
            int i19 = i5 - i18;
            while (i18 > 0) {
                a(iArr, i10, iArr, i19);
                i18--;
                i10++;
                i19++;
            }
            i4 += i14;
            i5 -= i17;
        }
        return new a(i4, i5);
    }

    private void b(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int[] iArr = this.b;
        int i8 = i5 - 1;
        int i9 = i4 - 1;
        while (i3 <= i9) {
            int i10 = iArr[i3] - i7;
            if (i10 < 0) {
                i10 += i2 - i;
            }
            int i11 = i + i10;
            if (iArr[i11] == i8) {
                i9++;
                iArr[i9] = i10;
                iArr[i11] = i9;
            }
            i3++;
        }
        int i12 = i6 - 1;
        int i13 = i9 + 1;
        while (i13 < i5) {
            int i14 = iArr[i12] - i7;
            if (i14 < 0) {
                i14 += i2 - i;
            }
            int i15 = i + i14;
            if (iArr[i15] == i8) {
                i5--;
                iArr[i5] = i14;
                iArr[i15] = i5;
            }
            i12--;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:226:0x058d, code lost:
        if (r0 >= r15) goto L_0x05a4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x0594, code lost:
        if (r14[r0].d != (-3)) goto L_0x05a1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x0596, code lost:
        r8.c(r23, r14[r0].b, r14[r0].c);
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x05a1, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x05a4, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(int r23, int r24, int r25, int r26, int r27, io.netty.handler.codec.compression.e.c r28, int r29) {
        /*
            Method dump skipped, instructions count: 1461
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.e.a(int, int, int, int, int, io.netty.handler.codec.compression.e$c, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Bzip2DivSufSort.java */
    /* loaded from: classes4.dex */
    public static class c {
        int a;
        int b;

        c(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        boolean a(int i, int i2) {
            this.a -= i2;
            int i3 = this.a;
            if (i3 <= 0) {
                int i4 = this.b - 1;
                this.b = i4;
                if (i4 == 0) {
                    return false;
                }
                this.a = i3 + i;
            }
            return true;
        }
    }

    private void e(int i, int i2, int i3) {
        int[] iArr = this.b;
        if ((-i2) < iArr[0]) {
            c cVar = new c(i2, ((c(i2) * 2) / 3) + 1);
            int i4 = 0;
            do {
                int i5 = iArr[i4];
                if (i5 < 0) {
                    i4 -= i5;
                    continue;
                } else {
                    int i6 = iArr[i + i5] + 1;
                    if (1 < i6 - i4) {
                        a(i, i + i3, i + i2, i4, i6, cVar, i2);
                        if (cVar.b == 0) {
                            if (i4 > 0) {
                                iArr[0] = -i4;
                            }
                            d(i, i2, i3);
                            return;
                        }
                    }
                    i4 = i6;
                    continue;
                }
            } while (i4 < i2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0029, code lost:
        r16 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int a(int[] r30, int[] r31) {
        /*
            Method dump skipped, instructions count: 663
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.e.a(int[], int[]):int");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int b(int[] iArr, int[] iArr2) {
        byte[] bArr = this.c;
        int[] iArr3 = this.b;
        int i = this.d;
        int i2 = DnsRecord.CLASS_NONE;
        int i3 = 0;
        int i4 = 0;
        while (i2 >= 0) {
            int i5 = i2 + 1;
            int i6 = iArr2[b(i2, i5)];
            int i7 = -1;
            int i8 = 0;
            for (int i9 = iArr[i5]; i6 <= i9; i9--) {
                int i10 = iArr3[i9];
                if (i10 >= 0) {
                    int i11 = i10 - 1;
                    if (i11 < 0) {
                        i11 = i - 1;
                    }
                    int i12 = bArr[i11] & 255;
                    if (i12 <= i2) {
                        iArr3[i9] = ~i10;
                        if (i11 > 0 && (bArr[i11 - 1] & 255) > i12) {
                            i11 = ~i11;
                        }
                        if (i7 == i12) {
                            i8--;
                            iArr3[i8] = i11;
                        } else {
                            if (i7 >= 0) {
                                iArr2[a(i7, i2)] = i8;
                            }
                            i8 = iArr2[a(i12, i2)] - 1;
                            iArr3[i8] = i11;
                            i7 = i12;
                        }
                    }
                } else {
                    iArr3[i9] = ~i10;
                }
            }
            i2--;
            i3 = i8;
            i4 = i7;
        }
        int i13 = -1;
        for (int i14 = 0; i14 < i; i14++) {
            int i15 = iArr3[i14];
            if (i15 >= 0) {
                int i16 = i15 - 1;
                if (i16 < 0) {
                    i16 = i - 1;
                }
                int i17 = bArr[i16] & 255;
                if (i17 >= (bArr[i16 + 1] & 255)) {
                    if (i16 > 0 && (bArr[i16 - 1] & 255) < i17) {
                        i16 = ~i16;
                    }
                    if (i17 == i4) {
                        i3++;
                        iArr3[i3] = i16;
                    } else {
                        if (i4 != -1) {
                            iArr[i4] = i3;
                        }
                        i3 = iArr[i17] + 1;
                        iArr3[i3] = i16;
                        i4 = i17;
                    }
                }
            } else {
                i15 = ~i15;
            }
            if (i15 == 0) {
                iArr3[i14] = bArr[i - 1];
                i13 = i14;
            } else {
                iArr3[i14] = bArr[i15 - 1];
            }
        }
        return i13;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int a() {
        int[] iArr = this.b;
        byte[] bArr = this.c;
        int i = this.d;
        int[] iArr2 = new int[256];
        int[] iArr3 = new int[65536];
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            iArr[0] = bArr[0];
            return 0;
        } else if (a(iArr2, iArr3) > 0) {
            return b(iArr2, iArr3);
        } else {
            return 0;
        }
    }
}
