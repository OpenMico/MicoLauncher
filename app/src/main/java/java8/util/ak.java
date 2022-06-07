package java8.util;

import java.lang.reflect.Array;
import java.util.Comparator;

/* compiled from: TimSort.java */
/* loaded from: classes5.dex */
class ak<T> {
    private final T[] a;
    private final Comparator<? super T> b;
    private T[] d;
    private int e;
    private int f;
    private final int[] h;
    private final int[] i;
    private int c = 7;
    private int g = 0;

    private static int a(int i) {
        int i2 = 0;
        while (i >= 32) {
            i2 |= i & 1;
            i >>= 1;
        }
        return i + i2;
    }

    private ak(T[] tArr, Comparator<? super T> comparator, T[] tArr2, int i, int i2) {
        this.a = tArr;
        this.b = comparator;
        int length = tArr.length;
        int i3 = length < 512 ? length >>> 1 : 256;
        if (tArr2 == null || i2 < i3 || i + i3 > tArr2.length) {
            this.d = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i3));
            this.e = 0;
            this.f = i3;
        } else {
            this.d = tArr2;
            this.e = i;
            this.f = i2;
        }
        int i4 = length < 120 ? 5 : length < 1542 ? 10 : length < 119151 ? 24 : 49;
        this.h = new int[i4];
        this.i = new int[i4];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> void a(T[] tArr, int i, int i2, Comparator<? super T> comparator, T[] tArr2, int i3, int i4) {
        int i5 = i2 - i;
        if (i5 >= 2) {
            if (i5 < 32) {
                a(tArr, i, i2, a(tArr, i, i2, comparator) + i, comparator);
                return;
            }
            ak akVar = new ak(tArr, comparator, tArr2, i3, i4);
            int a = a(i5);
            do {
                int a2 = a(tArr, i, i2, comparator);
                if (a2 < a) {
                    int i6 = i5 <= a ? i5 : a;
                    a(tArr, i, i + i6, a2 + i, comparator);
                    a2 = i6;
                }
                akVar.a(i, a2);
                akVar.a();
                i += a2;
                i5 -= a2;
            } while (i5 != 0);
            akVar.b();
        }
    }

    private static <T> void a(T[] tArr, int i, int i2, int i3, Comparator<? super T> comparator) {
        if (i3 == i) {
            i3++;
        }
        while (i3 < i2) {
            T t = tArr[i3];
            int i4 = i;
            int i5 = i3;
            while (i4 < i5) {
                int i6 = (i4 + i5) >>> 1;
                if (comparator.compare(t, tArr[i6]) < 0) {
                    i5 = i6;
                } else {
                    i4 = i6 + 1;
                }
            }
            int i7 = i3 - i4;
            switch (i7) {
                case 1:
                    break;
                case 2:
                    tArr[i4 + 2] = tArr[i4 + 1];
                    break;
                default:
                    System.arraycopy(tArr, i4, tArr, i4 + 1, i7);
                    continue;
                    tArr[i4] = t;
                    i3++;
            }
            tArr[i4 + 1] = tArr[i4];
            tArr[i4] = t;
            i3++;
        }
    }

    private static <T> int a(T[] tArr, int i, int i2, Comparator<? super T> comparator) {
        int i3 = i + 1;
        if (i3 == i2) {
            return 1;
        }
        int i4 = i3 + 1;
        if (comparator.compare(tArr[i3], tArr[i]) < 0) {
            while (i4 < i2 && comparator.compare(tArr[i4], tArr[i4 - 1]) < 0) {
                i4++;
            }
            a(tArr, i, i4);
        } else {
            while (i4 < i2 && comparator.compare(tArr[i4], tArr[i4 - 1]) >= 0) {
                i4++;
            }
        }
        return i4 - i;
    }

    private static void a(Object[] objArr, int i, int i2) {
        int i3 = i2 - 1;
        while (i < i3) {
            Object obj = objArr[i];
            i++;
            objArr[i] = objArr[i3];
            i3--;
            objArr[i3] = obj;
        }
    }

    private void a(int i, int i2) {
        int[] iArr = this.h;
        int i3 = this.g;
        iArr[i3] = i;
        this.i[i3] = i2;
        this.g = i3 + 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0027, code lost:
        if (r1[r0 - 2] <= (r1[r0] + r1[r0 - 1])) goto L_0x0029;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0029, code lost:
        r1 = r6.i;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0033, code lost:
        if (r1[r0 - 1] >= r1[r0 + 1]) goto L_0x0045;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0035, code lost:
        r0 = r0 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0016, code lost:
        if (r2[r0 - 1] > (r2[r0] + r2[r0 + 1])) goto L_0x0018;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a() {
        /*
            r6 = this;
        L_0x0000:
            int r0 = r6.g
            r1 = 1
            if (r0 <= r1) goto L_0x0049
            int r0 = r0 + (-2)
            if (r0 <= 0) goto L_0x0018
            int[] r2 = r6.i
            int r3 = r0 + (-1)
            r3 = r2[r3]
            r4 = r2[r0]
            int r5 = r0 + 1
            r2 = r2[r5]
            int r4 = r4 + r2
            if (r3 <= r4) goto L_0x0029
        L_0x0018:
            if (r0 <= r1) goto L_0x0038
            int[] r1 = r6.i
            int r2 = r0 + (-2)
            r2 = r1[r2]
            r3 = r1[r0]
            int r4 = r0 + (-1)
            r1 = r1[r4]
            int r3 = r3 + r1
            if (r2 > r3) goto L_0x0038
        L_0x0029:
            int[] r1 = r6.i
            int r2 = r0 + (-1)
            r2 = r1[r2]
            int r3 = r0 + 1
            r1 = r1[r3]
            if (r2 >= r1) goto L_0x0045
            int r0 = r0 + (-1)
            goto L_0x0045
        L_0x0038:
            if (r0 < 0) goto L_0x0049
            int[] r1 = r6.i
            r2 = r1[r0]
            int r3 = r0 + 1
            r1 = r1[r3]
            if (r2 <= r1) goto L_0x0045
            goto L_0x0049
        L_0x0045:
            r6.b(r0)
            goto L_0x0000
        L_0x0049:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.ak.a():void");
    }

    private void b() {
        while (true) {
            int i = this.g;
            if (i > 1) {
                int i2 = i - 2;
                if (i2 > 0) {
                    int[] iArr = this.i;
                    if (iArr[i2 - 1] < iArr[i2 + 1]) {
                        i2--;
                    }
                }
                b(i2);
            } else {
                return;
            }
        }
    }

    private void b(int i) {
        int[] iArr = this.h;
        int i2 = iArr[i];
        int[] iArr2 = this.i;
        int i3 = iArr2[i];
        int i4 = i + 1;
        int i5 = iArr[i4];
        int i6 = iArr2[i4];
        iArr2[i] = i3 + i6;
        if (i == this.g - 3) {
            int i7 = i + 2;
            iArr[i4] = iArr[i7];
            iArr2[i4] = iArr2[i7];
        }
        this.g--;
        T[] tArr = this.a;
        int b = b(tArr[i5], tArr, i2, i3, 0, this.b);
        int i8 = i2 + b;
        int i9 = i3 - b;
        if (i9 != 0) {
            T[] tArr2 = this.a;
            int a = a(tArr2[(i8 + i9) - 1], tArr2, i5, i6, i6 - 1, this.b);
            if (a != 0) {
                if (i9 <= a) {
                    a(i8, i9, i5, a);
                } else {
                    b(i8, i9, i5, a);
                }
            }
        }
    }

    private static <T> int a(T t, T[] tArr, int i, int i2, int i3, Comparator<? super T> comparator) {
        int i4;
        int i5;
        int i6 = i + i3;
        if (comparator.compare(t, tArr[i6]) > 0) {
            int i7 = i2 - i3;
            int i8 = 0;
            int i9 = 1;
            while (i9 < i7 && comparator.compare(t, tArr[i6 + i9]) > 0) {
                int i10 = (i9 << 1) + 1;
                if (i10 <= 0) {
                    i8 = i9;
                    i9 = i7;
                } else {
                    i9 = i10;
                    i8 = i9;
                }
            }
            if (i9 <= i7) {
                i7 = i9;
            }
            i5 = i8 + i3;
            i4 = i7 + i3;
        } else {
            int i11 = i3 + 1;
            int i12 = 0;
            int i13 = 1;
            while (i13 < i11 && comparator.compare(t, tArr[i6 - i13]) <= 0) {
                int i14 = (i13 << 1) + 1;
                if (i14 <= 0) {
                    i12 = i13;
                    i13 = i11;
                } else {
                    i13 = i14;
                    i12 = i13;
                }
            }
            if (i13 <= i11) {
                i11 = i13;
            }
            i5 = i3 - i11;
            i4 = i3 - i12;
        }
        int i15 = i5 + 1;
        while (i15 < i4) {
            int i16 = ((i4 - i15) >>> 1) + i15;
            if (comparator.compare(t, tArr[i + i16]) > 0) {
                i15 = i16 + 1;
            } else {
                i4 = i16;
            }
        }
        return i4;
    }

    private static <T> int b(T t, T[] tArr, int i, int i2, int i3, Comparator<? super T> comparator) {
        int i4;
        int i5;
        int i6 = i + i3;
        if (comparator.compare(t, tArr[i6]) < 0) {
            int i7 = i3 + 1;
            int i8 = 0;
            int i9 = 1;
            while (i9 < i7 && comparator.compare(t, tArr[i6 - i9]) < 0) {
                int i10 = (i9 << 1) + 1;
                if (i10 <= 0) {
                    i8 = i9;
                    i9 = i7;
                } else {
                    i9 = i10;
                    i8 = i9;
                }
            }
            if (i9 <= i7) {
                i7 = i9;
            }
            i5 = i3 - i7;
            i4 = i3 - i8;
        } else {
            int i11 = i2 - i3;
            int i12 = 0;
            int i13 = 1;
            while (i13 < i11 && comparator.compare(t, tArr[i6 + i13]) >= 0) {
                int i14 = (i13 << 1) + 1;
                if (i14 <= 0) {
                    i12 = i13;
                    i13 = i11;
                } else {
                    i13 = i14;
                    i12 = i13;
                }
            }
            if (i13 <= i11) {
                i11 = i13;
            }
            i5 = i12 + i3;
            i4 = i3 + i11;
        }
        int i15 = i5 + 1;
        while (i15 < i4) {
            int i16 = ((i4 - i15) >>> 1) + i15;
            if (comparator.compare(t, tArr[i + i16]) < 0) {
                i4 = i16;
            } else {
                i15 = i16 + 1;
            }
        }
        return i4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x006d, code lost:
        r14 = r2;
        r15 = r4;
        r12 = r5;
        r17 = r6;
        r16 = r13;
        r13 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0075, code lost:
        r6 = b(r7[r12], r8, r13, r14, 0, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0080, code lost:
        if (r6 == 0) goto L_0x0097;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0082, code lost:
        java.lang.System.arraycopy(r8, r13, r7, r15, r6);
        r1 = r15 + r6;
        r3 = r13 + r6;
        r2 = r14 - r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x008b, code lost:
        if (r2 > r9) goto L_0x0094;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x008d, code lost:
        r14 = r2;
        r13 = r16;
        r6 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0094, code lost:
        r15 = r1;
        r14 = r2;
        r13 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0097, code lost:
        r5 = r15 + 1;
        r4 = r12 + 1;
        r7[r15] = r7[r12];
        r12 = r16 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a1, code lost:
        if (r12 != 0) goto L_0x00aa;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a3, code lost:
        r1 = r5;
        r3 = r13;
        r6 = r17;
        r13 = r12;
        r12 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00aa, code lost:
        r1 = a(r8[r13], r7, r4, r12, 0, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b9, code lost:
        if (r1 == 0) goto L_0x00d5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00bb, code lost:
        java.lang.System.arraycopy(r7, r4, r7, r5, r1);
        r2 = r5 + r1;
        r5 = r4 + r1;
        r3 = r12 - r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00c4, code lost:
        if (r3 != 0) goto L_0x00d1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00c6, code lost:
        r1 = r2;
        r12 = r5;
        r6 = r17;
        r9 = 1;
        r13 = r3;
        r3 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00d1, code lost:
        r16 = r3;
        r12 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00d5, code lost:
        r2 = r5;
        r16 = r12;
        r12 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00d9, code lost:
        r3 = r2 + 1;
        r4 = r13 + 1;
        r7[r2] = r8[r13];
        r14 = r14 - 1;
        r9 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00e4, code lost:
        if (r14 != 1) goto L_0x010a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e6, code lost:
        r1 = r3;
        r3 = r4;
        r13 = r16;
        r6 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x010a, code lost:
        r17 = r17 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x010d, code lost:
        if (r6 < 7) goto L_0x0111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x010f, code lost:
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0111, code lost:
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0112, code lost:
        if (r1 < 7) goto L_0x0116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0114, code lost:
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0116, code lost:
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0118, code lost:
        if ((r1 | r5) != false) goto L_0x012b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x011a, code lost:
        if (r17 >= 0) goto L_0x011e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x011c, code lost:
        r17 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x012b, code lost:
        r15 = r3;
        r13 = r4;
     */
    /* JADX WARN: Removed duplicated region for block: B:75:0x006d A[EDGE_INSN: B:75:0x006d->B:24:0x006d ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(int r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.ak.a(int, int, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x007b, code lost:
        r16 = r2;
        r17 = r3;
        r12 = r4;
        r13 = r5;
        r15 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0082, code lost:
        r6 = r14 - b(r8[r12], r7, r21, r14, r14 - 1, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0091, code lost:
        if (r6 == 0) goto L_0x00aa;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0093, code lost:
        r1 = r13 - r6;
        r2 = r15 - r6;
        r14 = r14 - r6;
        java.lang.System.arraycopy(r7, r2 + 1, r7, r1 + 1, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x009f, code lost:
        if (r14 != 0) goto L_0x00a8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a1, code lost:
        r15 = r2;
        r2 = r16;
        r3 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00a8, code lost:
        r13 = r1;
        r15 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00aa, code lost:
        r18 = r13 - 1;
        r19 = r12 - 1;
        r7[r13] = r8[r12];
        r12 = r16 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b4, code lost:
        if (r12 != 1) goto L_0x00be;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b6, code lost:
        r2 = r12;
        r3 = r17;
        r1 = r18;
        r12 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00be, code lost:
        r1 = r12 - a(r7[r15], r8, r9, r12, r12 - 1, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00cd, code lost:
        if (r1 == 0) goto L_0x00ea;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00cf, code lost:
        r2 = r18 - r1;
        r4 = r19 - r1;
        r3 = r12 - r1;
        java.lang.System.arraycopy(r8, r4 + 1, r7, r2 + 1, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00dc, code lost:
        if (r3 > 1) goto L_0x00e4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00de, code lost:
        r1 = r2;
        r2 = r3;
        r12 = r4;
        r3 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00e4, code lost:
        r18 = r2;
        r16 = r3;
        r12 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ea, code lost:
        r16 = r12;
        r12 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ee, code lost:
        r2 = r18 - 1;
        r3 = r15 - 1;
        r7[r18] = r7[r15];
        r14 = r14 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00f8, code lost:
        if (r14 != 0) goto L_0x0125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00fa, code lost:
        r1 = r2;
        r15 = r3;
        r2 = r16;
        r3 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0125, code lost:
        r17 = r17 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0128, code lost:
        if (r6 < 7) goto L_0x012c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x012a, code lost:
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012c, code lost:
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x012d, code lost:
        if (r1 < 7) goto L_0x0131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x012f, code lost:
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0131, code lost:
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0133, code lost:
        if ((r1 | r5) != false) goto L_0x0144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0135, code lost:
        if (r17 >= 0) goto L_0x0139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0137, code lost:
        r17 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0144, code lost:
        r13 = r2;
        r15 = r3;
     */
    /* JADX WARN: Removed duplicated region for block: B:74:0x007b A[EDGE_INSN: B:74:0x007b->B:23:0x007b ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(int r21, int r22, int r23, int r24) {
        /*
            Method dump skipped, instructions count: 328
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.ak.b(int, int, int, int):void");
    }

    private T[] c(int i) {
        if (this.f < i) {
            int i2 = (i >> 1) | i;
            int i3 = i2 | (i2 >> 2);
            int i4 = i3 | (i3 >> 4);
            int i5 = i4 | (i4 >> 8);
            int i6 = (i5 | (i5 >> 16)) + 1;
            if (i6 >= 0) {
                i = Math.min(i6, this.a.length >>> 1);
            }
            this.d = (T[]) ((Object[]) Array.newInstance(this.a.getClass().getComponentType(), i));
            this.f = i;
            this.e = 0;
        }
        return this.d;
    }
}
