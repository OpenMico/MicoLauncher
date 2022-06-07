package com.google.zxing.pdf417.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.pdf417.PDF417Common;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Formatter;

/* loaded from: classes2.dex */
public final class PDF417ScanningDecoder {
    private static final ErrorCorrection a = new ErrorCorrection();

    private static int a(int i) {
        return 2 << i;
    }

    private static boolean a(int i, int i2, int i3) {
        return i2 + (-2) <= i && i <= i3 + 2;
    }

    private PDF417ScanningDecoder() {
    }

    public static DecoderResult decode(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i, int i2) throws NotFoundException, FormatException, ChecksumException {
        h hVar;
        g gVar;
        int i3;
        h hVar2 = null;
        f fVar = null;
        h hVar3 = null;
        int i4 = 0;
        c cVar = new c(bitMatrix, resultPoint, resultPoint2, resultPoint3, resultPoint4);
        while (i4 < 2) {
            hVar = resultPoint != null ? a(bitMatrix, cVar, resultPoint, true, i, i2) : hVar2;
            if (resultPoint3 != null) {
                hVar3 = a(bitMatrix, cVar, resultPoint3, false, i, i2);
            }
            fVar = a(hVar, hVar3);
            if (fVar == null) {
                throw NotFoundException.getNotFoundInstance();
            } else if (i4 != 0 || fVar.e() == null || (fVar.e().c() >= cVar.c() && fVar.e().d() <= cVar.d())) {
                fVar.a(cVar);
                break;
            } else {
                cVar = fVar.e();
                i4++;
                hVar2 = hVar;
            }
        }
        hVar = hVar2;
        int b = fVar.b() + 1;
        fVar.a(0, hVar);
        fVar.a(b, hVar3);
        boolean z = hVar != null;
        int i5 = i;
        int i6 = i2;
        for (int i7 = 1; i7 <= b; i7++) {
            int i8 = z ? i7 : b - i7;
            if (fVar.a(i8) == null) {
                if (i8 == 0 || i8 == b) {
                    gVar = new h(cVar, i8 == 0);
                } else {
                    gVar = new g(cVar);
                }
                fVar.a(i8, gVar);
                int i9 = -1;
                int i10 = i6;
                int i11 = -1;
                int i12 = i5;
                for (int c = cVar.c(); c <= cVar.d(); c++) {
                    int a2 = a(fVar, i8, c, z);
                    if (a2 >= 0 && a2 <= cVar.b()) {
                        i3 = a2;
                    } else if (i11 != i9) {
                        i3 = i11;
                    } else {
                        i11 = i11;
                        i10 = i10;
                        i9 = i9;
                    }
                    i11 = i11;
                    i9 = i9;
                    d a3 = a(bitMatrix, cVar.a(), cVar.b(), z, i3, c, i12, i10);
                    if (a3 != null) {
                        gVar.a(c, a3);
                        i12 = Math.min(i12, a3.c());
                        i10 = Math.max(i10, a3.c());
                        i11 = i3;
                    } else {
                        i10 = i10;
                    }
                }
                i5 = i12;
                i6 = i10;
            }
        }
        return a(fVar);
    }

    private static f a(h hVar, h hVar2) throws NotFoundException {
        a b;
        if ((hVar == null && hVar2 == null) || (b = b(hVar, hVar2)) == null) {
            return null;
        }
        return new f(b, c.a(a(hVar), a(hVar2)));
    }

    private static c a(h hVar) throws NotFoundException {
        int[] c;
        if (hVar == null || (c = hVar.c()) == null) {
            return null;
        }
        int a2 = a(c);
        int i = 0;
        int i2 = 0;
        for (int i3 : c) {
            i2 += a2 - i3;
            if (i3 > 0) {
                break;
            }
        }
        d[] b = hVar.b();
        for (int i4 = 0; i2 > 0 && b[i4] == null; i4++) {
            i2--;
        }
        for (int length = c.length - 1; length >= 0; length--) {
            i += a2 - c[length];
            if (c[length] > 0) {
                break;
            }
        }
        for (int length2 = b.length - 1; i > 0 && b[length2] == null; length2--) {
            i--;
        }
        return hVar.a().a(i2, i, hVar.e());
    }

    private static int a(int[] iArr) {
        int i = -1;
        for (int i2 : iArr) {
            i = Math.max(i, i2);
        }
        return i;
    }

    private static a b(h hVar, h hVar2) {
        a d;
        a d2;
        if (hVar == null || (d = hVar.d()) == null) {
            if (hVar2 == null) {
                return null;
            }
            return hVar2.d();
        } else if (hVar2 == null || (d2 = hVar2.d()) == null || d.a() == d2.a() || d.b() == d2.b() || d.c() == d2.c()) {
            return d;
        } else {
            return null;
        }
    }

    private static h a(BitMatrix bitMatrix, c cVar, ResultPoint resultPoint, boolean z, int i, int i2) {
        h hVar = new h(cVar, z);
        int i3 = 0;
        while (i3 < 2) {
            int i4 = i3 == 0 ? 1 : -1;
            int x = (int) resultPoint.getX();
            for (int y = (int) resultPoint.getY(); y <= cVar.d() && y >= cVar.c(); y += i4) {
                d a2 = a(bitMatrix, 0, bitMatrix.getWidth(), z, x, y, i, i2);
                if (a2 != null) {
                    hVar.a(y, a2);
                    if (z) {
                        x = a2.d();
                    } else {
                        x = a2.e();
                    }
                }
            }
            i3++;
        }
        return hVar;
    }

    private static void a(f fVar, b[][] bVarArr) throws NotFoundException {
        b bVar = bVarArr[0][1];
        int[] a2 = bVar.a();
        int b = (fVar.b() * fVar.c()) - a(fVar.d());
        if (a2.length == 0) {
            if (b <= 0 || b > 928) {
                throw NotFoundException.getNotFoundInstance();
            }
            bVar.a(b);
        } else if (a2[0] != b) {
            bVar.a(b);
        }
    }

    private static DecoderResult a(f fVar) throws FormatException, ChecksumException, NotFoundException {
        b[][] b = b(fVar);
        a(fVar, b);
        ArrayList arrayList = new ArrayList();
        int[] iArr = new int[fVar.c() * fVar.b()];
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (int i = 0; i < fVar.c(); i++) {
            int i2 = 0;
            while (i2 < fVar.b()) {
                int i3 = i2 + 1;
                int[] a2 = b[i][i3].a();
                int b2 = (fVar.b() * i) + i2;
                if (a2.length == 0) {
                    arrayList.add(Integer.valueOf(b2));
                } else if (a2.length == 1) {
                    iArr[b2] = a2[0];
                } else {
                    arrayList3.add(Integer.valueOf(b2));
                    arrayList2.add(a2);
                }
                i2 = i3;
            }
        }
        int[][] iArr2 = new int[arrayList2.size()];
        for (int i4 = 0; i4 < iArr2.length; i4++) {
            iArr2[i4] = (int[]) arrayList2.get(i4);
        }
        return a(fVar.d(), iArr, PDF417Common.toIntArray(arrayList), PDF417Common.toIntArray(arrayList3), iArr2);
    }

    private static DecoderResult a(int i, int[] iArr, int[] iArr2, int[] iArr3, int[][] iArr4) throws FormatException, ChecksumException {
        int[] iArr5 = new int[iArr3.length];
        int i2 = 100;
        while (true) {
            i2--;
            if (i2 > 0) {
                for (int i3 = 0; i3 < iArr5.length; i3++) {
                    iArr[iArr3[i3]] = iArr4[i3][iArr5[i3]];
                }
                try {
                    return a(iArr, i, iArr2);
                } catch (ChecksumException unused) {
                    if (iArr5.length != 0) {
                        int i4 = 0;
                        while (true) {
                            if (i4 >= iArr5.length) {
                                break;
                            } else if (iArr5[i4] < iArr4[i4].length - 1) {
                                iArr5[i4] = iArr5[i4] + 1;
                                break;
                            } else {
                                iArr5[i4] = 0;
                                if (i4 != iArr5.length - 1) {
                                    i4++;
                                } else {
                                    throw ChecksumException.getChecksumInstance();
                                }
                            }
                        }
                    } else {
                        throw ChecksumException.getChecksumInstance();
                    }
                }
            } else {
                throw ChecksumException.getChecksumInstance();
            }
        }
    }

    private static b[][] b(f fVar) {
        int h;
        b[][] bVarArr = (b[][]) Array.newInstance(b.class, fVar.c(), fVar.b() + 2);
        for (int i = 0; i < bVarArr.length; i++) {
            for (int i2 = 0; i2 < bVarArr[i].length; i2++) {
                bVarArr[i][i2] = new b();
            }
        }
        g[] a2 = fVar.a();
        int i3 = 0;
        for (g gVar : a2) {
            if (gVar != null) {
                d[] b = gVar.b();
                for (d dVar : b) {
                    if (dVar != null && (h = dVar.h()) >= 0 && h < bVarArr.length) {
                        bVarArr[h][i3].a(dVar.g());
                    }
                }
            }
            i3++;
        }
        return bVarArr;
    }

    private static boolean a(f fVar, int i) {
        return i >= 0 && i <= fVar.b() + 1;
    }

    private static int a(f fVar, int i, int i2, boolean z) {
        int i3 = z ? 1 : -1;
        d dVar = null;
        int i4 = i - i3;
        if (a(fVar, i4)) {
            dVar = fVar.a(i4).c(i2);
        }
        if (dVar != null) {
            return z ? dVar.e() : dVar.d();
        }
        d a2 = fVar.a(i).a(i2);
        if (a2 != null) {
            return z ? a2.d() : a2.e();
        }
        if (a(fVar, i4)) {
            a2 = fVar.a(i4).a(i2);
        }
        if (a2 != null) {
            return z ? a2.e() : a2.d();
        }
        int i5 = 0;
        while (true) {
            i -= i3;
            if (!a(fVar, i)) {
                return z ? fVar.e().a() : fVar.e().b();
            }
            d[] b = fVar.a(i).b();
            for (d dVar2 : b) {
                if (dVar2 != null) {
                    return (z ? dVar2.e() : dVar2.d()) + (i3 * i5 * (dVar2.e() - dVar2.d()));
                }
            }
            i5++;
        }
    }

    private static d a(BitMatrix bitMatrix, int i, int i2, boolean z, int i3, int i4, int i5, int i6) {
        int i7;
        int a2;
        int codeword;
        int b = b(bitMatrix, i, i2, z, i3, i4);
        int[] a3 = a(bitMatrix, i, i2, z, b, i4);
        if (a3 == null) {
            return null;
        }
        int sum = MathUtils.sum(a3);
        if (z) {
            b += sum;
            i7 = b;
        } else {
            for (int i8 = 0; i8 < a3.length / 2; i8++) {
                int i9 = a3[i8];
                a3[i8] = a3[(a3.length - 1) - i8];
                a3[(a3.length - 1) - i8] = i9;
            }
            i7 = b - sum;
        }
        if (a(sum, i5, i6) && (codeword = PDF417Common.getCodeword((a2 = i.a(a3)))) != -1) {
            return new d(i7, b, c(a2), codeword);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0016  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x002b A[EDGE_INSN: B:27:0x002b->B:18:0x002b ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int[] a(com.google.zxing.common.BitMatrix r8, int r9, int r10, boolean r11, int r12, int r13) {
        /*
            r0 = 8
            int[] r1 = new int[r0]
            r2 = 1
            if (r11 == 0) goto L_0x0009
            r3 = r2
            goto L_0x000a
        L_0x0009:
            r3 = -1
        L_0x000a:
            r4 = 0
            r6 = r11
            r5 = r4
        L_0x000d:
            if (r11 == 0) goto L_0x0012
            if (r12 >= r10) goto L_0x002b
            goto L_0x0014
        L_0x0012:
            if (r12 < r9) goto L_0x002b
        L_0x0014:
            if (r5 >= r0) goto L_0x002b
            boolean r7 = r8.get(r12, r13)
            if (r7 != r6) goto L_0x0023
            r7 = r1[r5]
            int r7 = r7 + r2
            r1[r5] = r7
            int r12 = r12 + r3
            goto L_0x000d
        L_0x0023:
            int r5 = r5 + 1
            if (r6 != 0) goto L_0x0029
            r6 = r2
            goto L_0x000d
        L_0x0029:
            r6 = r4
            goto L_0x000d
        L_0x002b:
            if (r5 == r0) goto L_0x0038
            if (r11 == 0) goto L_0x0030
            r9 = r10
        L_0x0030:
            if (r12 != r9) goto L_0x0036
            r8 = 7
            if (r5 != r8) goto L_0x0036
            goto L_0x0038
        L_0x0036:
            r8 = 0
            return r8
        L_0x0038:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.a(com.google.zxing.common.BitMatrix, int, int, boolean, int, int):int[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0026 A[EDGE_INSN: B:30:0x0026->B:18:0x0026 ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int b(com.google.zxing.common.BitMatrix r7, int r8, int r9, boolean r10, int r11, int r12) {
        /*
            r0 = 1
            if (r10 == 0) goto L_0x0005
            r1 = -1
            goto L_0x0006
        L_0x0005:
            r1 = r0
        L_0x0006:
            r2 = 0
            r4 = r10
            r3 = r1
            r10 = r2
            r1 = r11
        L_0x000b:
            r5 = 2
            if (r10 >= r5) goto L_0x002f
        L_0x000e:
            if (r4 == 0) goto L_0x0013
            if (r1 < r8) goto L_0x0026
            goto L_0x0015
        L_0x0013:
            if (r1 >= r9) goto L_0x0026
        L_0x0015:
            boolean r6 = r7.get(r1, r12)
            if (r4 != r6) goto L_0x0026
            int r6 = r11 - r1
            int r6 = java.lang.Math.abs(r6)
            if (r6 <= r5) goto L_0x0024
            return r11
        L_0x0024:
            int r1 = r1 + r3
            goto L_0x000e
        L_0x0026:
            int r3 = -r3
            if (r4 != 0) goto L_0x002b
            r4 = r0
            goto L_0x002c
        L_0x002b:
            r4 = r2
        L_0x002c:
            int r10 = r10 + 1
            goto L_0x000b
        L_0x002f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.b(com.google.zxing.common.BitMatrix, int, int, boolean, int, int):int");
    }

    private static DecoderResult a(int[] iArr, int i, int[] iArr2) throws FormatException, ChecksumException {
        if (iArr.length != 0) {
            int i2 = 1 << (i + 1);
            int a2 = a(iArr, iArr2, i2);
            a(iArr, i2);
            DecoderResult a3 = e.a(iArr, String.valueOf(i));
            a3.setErrorsCorrected(Integer.valueOf(a2));
            a3.setErasures(Integer.valueOf(iArr2.length));
            return a3;
        }
        throw FormatException.getFormatInstance();
    }

    private static int a(int[] iArr, int[] iArr2, int i) throws ChecksumException {
        if ((iArr2 == null || iArr2.length <= (i / 2) + 3) && i >= 0 && i <= 512) {
            return a.decode(iArr, i, iArr2);
        }
        throw ChecksumException.getChecksumInstance();
    }

    private static void a(int[] iArr, int i) throws FormatException {
        if (iArr.length >= 4) {
            int i2 = iArr[0];
            if (i2 > iArr.length) {
                throw FormatException.getFormatInstance();
            } else if (i2 != 0) {
            } else {
                if (i < iArr.length) {
                    iArr[0] = iArr.length - i;
                    return;
                }
                throw FormatException.getFormatInstance();
            }
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private static int[] b(int i) {
        int[] iArr = new int[8];
        int i2 = 0;
        int i3 = 7;
        while (true) {
            int i4 = i & 1;
            if (i4 != i2) {
                i3--;
                if (i3 < 0) {
                    return iArr;
                }
                i2 = i4;
            }
            iArr[i3] = iArr[i3] + 1;
            i >>= 1;
        }
    }

    private static int c(int i) {
        return b(b(i));
    }

    private static int b(int[] iArr) {
        return ((((iArr[0] - iArr[2]) + iArr[4]) - iArr[6]) + 9) % 9;
    }

    public static String toString(b[][] bVarArr) {
        Formatter formatter = new Formatter();
        int i = 0;
        while (true) {
            Throwable th = null;
            try {
                if (i < bVarArr.length) {
                    formatter.format("Row %2d: ", Integer.valueOf(i));
                    for (int i2 = 0; i2 < bVarArr[i].length; i2++) {
                        b bVar = bVarArr[i][i2];
                        if (bVar.a().length == 0) {
                            formatter.format("        ", null);
                        } else {
                            formatter.format("%4d(%2d)", Integer.valueOf(bVar.a()[0]), bVar.b(bVar.a()[0]));
                        }
                    }
                    formatter.format("%n", new Object[0]);
                    i++;
                } else {
                    String formatter2 = formatter.toString();
                    formatter.close();
                    return formatter2;
                }
            } catch (Throwable th2) {
                if (0 != 0) {
                    try {
                        formatter.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                } else {
                    formatter.close();
                }
                throw th2;
            }
        }
    }
}
