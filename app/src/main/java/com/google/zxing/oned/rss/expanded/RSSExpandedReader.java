package com.google.zxing.oned.rss.expanded;

import androidx.renderscript.ScriptIntrinsicBLAS;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import com.xiaomi.mi_connect_sdk.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import miuix.animation.internal.AnimTask;
import org.eclipse.jetty.http.HttpStatus;

/* loaded from: classes2.dex */
public final class RSSExpandedReader extends AbstractRSSReader {
    private static final int[] a = {7, 5, 4, 3, 1};
    private static final int[] b = {4, 20, 52, 104, 204};
    private static final int[] c = {0, 348, 1388, 2948, 3988};
    private static final int[][] d = {new int[]{1, 8, 4, 1}, new int[]{3, 6, 4, 1}, new int[]{3, 4, 6, 1}, new int[]{3, 2, 8, 1}, new int[]{2, 6, 5, 1}, new int[]{2, 2, 9, 1}};
    private static final int[][] e = {new int[]{1, 3, 9, 27, 81, 32, 96, 77}, new int[]{20, 60, Opcodes.GETFIELD, 118, 143, 7, 21, 63}, new int[]{PsExtractor.PRIVATE_STREAM_1, 145, 13, 39, 117, 140, 209, 205}, new int[]{Opcodes.INSTANCEOF, 157, 49, 147, 19, 57, 171, 91}, new int[]{62, 186, 136, 197, Opcodes.RET, 85, 44, 132}, new int[]{Opcodes.INVOKEINTERFACE, 133, 188, ScriptIntrinsicBLAS.RIGHT, 4, 12, 36, 108}, new int[]{113, 128, 173, 97, 80, 29, 87, 50}, new int[]{AnimTask.MAX_PAGE_SIZE, 28, 84, 41, 123, Opcodes.IFLE, 52, BuildConfig.MiConnectVersionBuild}, new int[]{46, TsExtractor.TS_STREAM_TYPE_DTS, 203, Opcodes.NEW, 139, 206, 196, Opcodes.IF_ACMPNE}, new int[]{76, 17, 51, Opcodes.IFEQ, 37, 111, 122, 155}, new int[]{43, 129, Opcodes.ARETURN, 106, 107, 110, 119, 146}, new int[]{16, 48, 144, 10, 30, 90, 59, Opcodes.RETURN}, new int[]{109, 116, 137, 200, Opcodes.GETSTATIC, 112, 125, 164}, new int[]{70, 210, 208, 202, Opcodes.INVOKESTATIC, 130, 179, 115}, new int[]{134, 191, Opcodes.DCMPL, 31, 93, 68, 204, 190}, new int[]{Opcodes.LCMP, 22, 66, Opcodes.IFNULL, TsExtractor.TS_STREAM_TYPE_AC4, 94, 71, 2}, new int[]{6, 18, 54, Opcodes.IF_ICMPGE, 64, 192, Opcodes.IFNE, 40}, new int[]{120, Opcodes.FCMPL, 25, 75, 14, 42, 126, Opcodes.GOTO}, new int[]{79, 26, 78, 23, 69, HttpStatus.MULTI_STATUS_207, Opcodes.IFNONNULL, 175}, new int[]{103, 98, 83, 38, 114, ScriptIntrinsicBLAS.NON_UNIT, Opcodes.INVOKEVIRTUAL, 124}, new int[]{Opcodes.IF_ICMPLT, 61, Opcodes.INVOKESPECIAL, 127, 170, 88, 53, Opcodes.IF_ICMPEQ}, new int[]{55, Opcodes.IF_ACMPEQ, 73, 8, 24, 72, 5, 15}, new int[]{45, 135, 194, Opcodes.IF_ICMPNE, 58, 174, 100, 89}};
    private static final int[][] f = {new int[]{0, 0}, new int[]{0, 1, 1}, new int[]{0, 2, 1, 3}, new int[]{0, 4, 1, 3, 2}, new int[]{0, 4, 1, 3, 3, 5}, new int[]{0, 4, 1, 3, 4, 5, 5}, new int[]{0, 0, 1, 1, 2, 2, 3, 3}, new int[]{0, 0, 1, 1, 2, 2, 3, 4, 4}, new int[]{0, 0, 1, 1, 2, 2, 3, 4, 5, 5}, new int[]{0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5}};
    private final List<b> g = new ArrayList(11);
    private final List<c> h = new ArrayList();
    private final int[] i = new int[2];
    private boolean j;

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        this.g.clear();
        this.j = false;
        try {
            return a(a(i, bitArray));
        } catch (NotFoundException unused) {
            this.g.clear();
            this.j = true;
            return a(a(i, bitArray));
        }
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public void reset() {
        this.g.clear();
        this.h.clear();
    }

    List<b> a(int i, BitArray bitArray) throws NotFoundException {
        while (true) {
            try {
                this.g.add(a(bitArray, this.g, i));
            } catch (NotFoundException e2) {
                if (this.g.isEmpty()) {
                    throw e2;
                } else if (a()) {
                    return this.g;
                } else {
                    boolean z = !this.h.isEmpty();
                    a(i, false);
                    if (z) {
                        List<b> a2 = a(false);
                        if (a2 != null) {
                            return a2;
                        }
                        List<b> a3 = a(true);
                        if (a3 != null) {
                            return a3;
                        }
                    }
                    throw NotFoundException.getNotFoundInstance();
                }
            }
        }
    }

    private List<b> a(boolean z) {
        List<b> list = null;
        if (this.h.size() > 25) {
            this.h.clear();
            return null;
        }
        this.g.clear();
        if (z) {
            Collections.reverse(this.h);
        }
        try {
            list = a(new ArrayList(), 0);
        } catch (NotFoundException unused) {
        }
        if (z) {
            Collections.reverse(this.h);
        }
        return list;
    }

    private List<b> a(List<c> list, int i) throws NotFoundException {
        while (i < this.h.size()) {
            c cVar = this.h.get(i);
            this.g.clear();
            for (c cVar2 : list) {
                this.g.addAll(cVar2.a());
            }
            this.g.addAll(cVar.a());
            if (b(this.g)) {
                if (a()) {
                    return this.g;
                }
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(list);
                arrayList.add(cVar);
                try {
                    return a(arrayList, i + 1);
                } catch (NotFoundException unused) {
                    continue;
                }
            }
            i++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static boolean b(List<b> list) {
        boolean z;
        int[][] iArr = f;
        for (int[] iArr2 : iArr) {
            if (list.size() <= iArr2.length) {
                int i = 0;
                while (true) {
                    if (i >= list.size()) {
                        z = true;
                        break;
                    } else if (list.get(i).c().getValue() != iArr2[i]) {
                        z = false;
                        break;
                    } else {
                        i++;
                    }
                }
                if (z) {
                    return true;
                }
            }
        }
        return false;
    }

    private void a(int i, boolean z) {
        boolean z2 = false;
        int i2 = 0;
        boolean z3 = false;
        while (true) {
            if (i2 >= this.h.size()) {
                break;
            }
            c cVar = this.h.get(i2);
            if (cVar.b() > i) {
                z2 = cVar.a(this.g);
                break;
            } else {
                z3 = cVar.a(this.g);
                i2++;
            }
        }
        if (!z2 && !z3 && !a((Iterable<b>) this.g, (Iterable<c>) this.h)) {
            this.h.add(i2, new c(this.g, i, z));
            a(this.g, this.h);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x004c, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(java.util.List<com.google.zxing.oned.rss.expanded.b> r6, java.util.List<com.google.zxing.oned.rss.expanded.c> r7) {
        /*
            java.util.Iterator r7 = r7.iterator()
        L_0x0004:
            boolean r0 = r7.hasNext()
            if (r0 == 0) goto L_0x0056
            java.lang.Object r0 = r7.next()
            com.google.zxing.oned.rss.expanded.c r0 = (com.google.zxing.oned.rss.expanded.c) r0
            java.util.List r1 = r0.a()
            int r1 = r1.size()
            int r2 = r6.size()
            if (r1 == r2) goto L_0x0004
            java.util.List r0 = r0.a()
            java.util.Iterator r0 = r0.iterator()
        L_0x0026:
            boolean r1 = r0.hasNext()
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x004f
            java.lang.Object r1 = r0.next()
            com.google.zxing.oned.rss.expanded.b r1 = (com.google.zxing.oned.rss.expanded.b) r1
            java.util.Iterator r4 = r6.iterator()
        L_0x0038:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x004b
            java.lang.Object r5 = r4.next()
            com.google.zxing.oned.rss.expanded.b r5 = (com.google.zxing.oned.rss.expanded.b) r5
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L_0x0038
            goto L_0x004c
        L_0x004b:
            r3 = r2
        L_0x004c:
            if (r3 != 0) goto L_0x0026
            goto L_0x0050
        L_0x004f:
            r2 = r3
        L_0x0050:
            if (r2 == 0) goto L_0x0004
            r7.remove()
            goto L_0x0004
        L_0x0056:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.rss.expanded.RSSExpandedReader.a(java.util.List, java.util.List):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0043, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(java.lang.Iterable<com.google.zxing.oned.rss.expanded.b> r7, java.lang.Iterable<com.google.zxing.oned.rss.expanded.c> r8) {
        /*
            java.util.Iterator r8 = r8.iterator()
        L_0x0004:
            boolean r0 = r8.hasNext()
            r1 = 0
            if (r0 == 0) goto L_0x0046
            java.lang.Object r0 = r8.next()
            com.google.zxing.oned.rss.expanded.c r0 = (com.google.zxing.oned.rss.expanded.c) r0
            java.util.Iterator r2 = r7.iterator()
        L_0x0015:
            boolean r3 = r2.hasNext()
            r4 = 1
            if (r3 == 0) goto L_0x0042
            java.lang.Object r3 = r2.next()
            com.google.zxing.oned.rss.expanded.b r3 = (com.google.zxing.oned.rss.expanded.b) r3
            java.util.List r5 = r0.a()
            java.util.Iterator r5 = r5.iterator()
        L_0x002a:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x003e
            java.lang.Object r6 = r5.next()
            com.google.zxing.oned.rss.expanded.b r6 = (com.google.zxing.oned.rss.expanded.b) r6
            boolean r6 = r3.equals(r6)
            if (r6 == 0) goto L_0x002a
            r3 = r4
            goto L_0x003f
        L_0x003e:
            r3 = r1
        L_0x003f:
            if (r3 != 0) goto L_0x0015
            goto L_0x0043
        L_0x0042:
            r1 = r4
        L_0x0043:
            if (r1 == 0) goto L_0x0004
            return r4
        L_0x0046:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.rss.expanded.RSSExpandedReader.a(java.lang.Iterable, java.lang.Iterable):boolean");
    }

    static Result a(List<b> list) throws NotFoundException, FormatException {
        String parseInformation = AbstractExpandedDecoder.createDecoder(a.a(list)).parseInformation();
        ResultPoint[] resultPoints = list.get(0).c().getResultPoints();
        ResultPoint[] resultPoints2 = list.get(list.size() - 1).c().getResultPoints();
        return new Result(parseInformation, null, new ResultPoint[]{resultPoints[0], resultPoints[1], resultPoints2[0], resultPoints2[1]}, BarcodeFormat.RSS_EXPANDED);
    }

    private boolean a() {
        b bVar = this.g.get(0);
        DataCharacter a2 = bVar.a();
        DataCharacter b2 = bVar.b();
        if (b2 == null) {
            return false;
        }
        int i = 2;
        int checksumPortion = b2.getChecksumPortion();
        for (int i2 = 1; i2 < this.g.size(); i2++) {
            b bVar2 = this.g.get(i2);
            checksumPortion += bVar2.a().getChecksumPortion();
            i++;
            DataCharacter b3 = bVar2.b();
            if (b3 != null) {
                checksumPortion += b3.getChecksumPortion();
                i++;
            }
        }
        return ((i + (-4)) * 211) + (checksumPortion % 211) == a2.getValue();
    }

    private static int a(BitArray bitArray, int i) {
        if (bitArray.get(i)) {
            return bitArray.getNextSet(bitArray.getNextUnset(i));
        }
        return bitArray.getNextUnset(bitArray.getNextSet(i));
    }

    b a(BitArray bitArray, List<b> list, int i) throws NotFoundException {
        FinderPattern a2;
        DataCharacter dataCharacter;
        boolean z = list.size() % 2 == 0;
        if (this.j) {
            z = !z;
        }
        int i2 = -1;
        boolean z2 = true;
        do {
            b(bitArray, list, i2);
            a2 = a(bitArray, i, z);
            if (a2 == null) {
                i2 = a(bitArray, this.i[0]);
                continue;
            } else {
                z2 = false;
                continue;
            }
        } while (z2);
        DataCharacter a3 = a(bitArray, a2, z, true);
        if (list.isEmpty() || !list.get(list.size() - 1).d()) {
            try {
                dataCharacter = a(bitArray, a2, z, false);
            } catch (NotFoundException unused) {
                dataCharacter = null;
            }
            return new b(a3, dataCharacter, a2, true);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void b(BitArray bitArray, List<b> list, int i) throws NotFoundException {
        int[] decodeFinderCounters = getDecodeFinderCounters();
        decodeFinderCounters[0] = 0;
        decodeFinderCounters[1] = 0;
        decodeFinderCounters[2] = 0;
        decodeFinderCounters[3] = 0;
        int size = bitArray.getSize();
        if (i < 0) {
            i = list.isEmpty() ? 0 : list.get(list.size() - 1).c().getStartEnd()[1];
        }
        boolean z = list.size() % 2 != 0;
        if (this.j) {
            z = !z;
        }
        boolean z2 = false;
        while (i < size) {
            z2 = !bitArray.get(i);
            if (!z2) {
                break;
            }
            i++;
        }
        int i2 = i;
        int i3 = 0;
        while (i < size) {
            if (bitArray.get(i) != z2) {
                decodeFinderCounters[i3] = decodeFinderCounters[i3] + 1;
            } else {
                if (i3 == 3) {
                    if (z) {
                        a(decodeFinderCounters);
                    }
                    if (isFinderPattern(decodeFinderCounters)) {
                        int[] iArr = this.i;
                        iArr[0] = i2;
                        iArr[1] = i;
                        return;
                    }
                    if (z) {
                        a(decodeFinderCounters);
                    }
                    i2 += decodeFinderCounters[0] + decodeFinderCounters[1];
                    decodeFinderCounters[0] = decodeFinderCounters[2];
                    decodeFinderCounters[1] = decodeFinderCounters[3];
                    decodeFinderCounters[2] = 0;
                    decodeFinderCounters[3] = 0;
                    i3--;
                } else {
                    i3++;
                }
                decodeFinderCounters[i3] = 1;
                z2 = !z2;
            }
            i++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static void a(int[] iArr) {
        int length = iArr.length;
        for (int i = 0; i < length / 2; i++) {
            int i2 = iArr[i];
            int i3 = (length - i) - 1;
            iArr[i] = iArr[i3];
            iArr[i3] = i2;
        }
    }

    private FinderPattern a(BitArray bitArray, int i, boolean z) {
        int i2;
        int i3;
        int i4;
        if (z) {
            int i5 = this.i[0] - 1;
            while (i5 >= 0 && !bitArray.get(i5)) {
                i5--;
            }
            int i6 = i5 + 1;
            int[] iArr = this.i;
            i2 = iArr[0] - i6;
            i3 = iArr[1];
            i4 = i6;
        } else {
            int[] iArr2 = this.i;
            i4 = iArr2[0];
            int nextUnset = bitArray.getNextUnset(iArr2[1] + 1);
            i2 = nextUnset - this.i[1];
            i3 = nextUnset;
        }
        int[] decodeFinderCounters = getDecodeFinderCounters();
        System.arraycopy(decodeFinderCounters, 0, decodeFinderCounters, 1, decodeFinderCounters.length - 1);
        decodeFinderCounters[0] = i2;
        try {
            return new FinderPattern(parseFinderValue(decodeFinderCounters, d), new int[]{i4, i3}, i4, i3, i);
        } catch (NotFoundException unused) {
            return null;
        }
    }

    DataCharacter a(BitArray bitArray, FinderPattern finderPattern, boolean z, boolean z2) throws NotFoundException {
        int[] dataCharacterCounters = getDataCharacterCounters();
        for (int i = 0; i < dataCharacterCounters.length; i++) {
            dataCharacterCounters[i] = 0;
        }
        if (z2) {
            recordPatternInReverse(bitArray, finderPattern.getStartEnd()[0], dataCharacterCounters);
        } else {
            recordPattern(bitArray, finderPattern.getStartEnd()[1], dataCharacterCounters);
            int i2 = 0;
            for (int length = dataCharacterCounters.length - 1; i2 < length; length--) {
                int i3 = dataCharacterCounters[i2];
                dataCharacterCounters[i2] = dataCharacterCounters[length];
                dataCharacterCounters[length] = i3;
                i2++;
            }
        }
        float sum = MathUtils.sum(dataCharacterCounters) / 17.0f;
        float f2 = (finderPattern.getStartEnd()[1] - finderPattern.getStartEnd()[0]) / 15.0f;
        if (Math.abs(sum - f2) / f2 <= 0.3f) {
            int[] oddCounts = getOddCounts();
            int[] evenCounts = getEvenCounts();
            float[] oddRoundingErrors = getOddRoundingErrors();
            float[] evenRoundingErrors = getEvenRoundingErrors();
            for (int i4 = 0; i4 < dataCharacterCounters.length; i4++) {
                float f3 = (dataCharacterCounters[i4] * 1.0f) / sum;
                int i5 = (int) (0.5f + f3);
                int i6 = 8;
                if (i5 <= 0) {
                    if (f3 >= 0.3f) {
                        i6 = 1;
                    } else {
                        throw NotFoundException.getNotFoundInstance();
                    }
                } else if (i5 <= 8) {
                    i6 = i5;
                } else if (f3 > 8.7f) {
                    throw NotFoundException.getNotFoundInstance();
                }
                int i7 = i4 / 2;
                if ((i4 & 1) == 0) {
                    oddCounts[i7] = i6;
                    oddRoundingErrors[i7] = f3 - i6;
                } else {
                    evenCounts[i7] = i6;
                    evenRoundingErrors[i7] = f3 - i6;
                }
            }
            a(17);
            int value = (((finderPattern.getValue() * 4) + (z ? 0 : 2)) + (!z2 ? 1 : 0)) - 1;
            int i8 = 0;
            int i9 = 0;
            for (int length2 = oddCounts.length - 1; length2 >= 0; length2--) {
                if (a(finderPattern, z, z2)) {
                    i8 += oddCounts[length2] * e[value][length2 * 2];
                }
                i9 += oddCounts[length2];
            }
            int i10 = 0;
            for (int length3 = evenCounts.length - 1; length3 >= 0; length3--) {
                if (a(finderPattern, z, z2)) {
                    i10 += evenCounts[length3] * e[value][(length3 * 2) + 1];
                }
            }
            int i11 = i8 + i10;
            if ((i9 & 1) != 0 || i9 > 13 || i9 < 4) {
                throw NotFoundException.getNotFoundInstance();
            }
            int i12 = (13 - i9) / 2;
            int i13 = a[i12];
            return new DataCharacter((RSSUtils.getRSSvalue(oddCounts, i13, true) * b[i12]) + RSSUtils.getRSSvalue(evenCounts, 9 - i13, false) + c[i12], i11);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static boolean a(FinderPattern finderPattern, boolean z, boolean z2) {
        return finderPattern.getValue() != 0 || !z || !z2;
    }

    private void a(int i) throws NotFoundException {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int sum = MathUtils.sum(getOddCounts());
        int sum2 = MathUtils.sum(getEvenCounts());
        boolean z5 = false;
        if (sum > 13) {
            z = false;
            z2 = true;
        } else if (sum < 4) {
            z2 = false;
            z = true;
        } else {
            z2 = false;
            z = false;
        }
        if (sum2 > 13) {
            z4 = false;
            z3 = true;
        } else if (sum2 < 4) {
            z3 = false;
            z4 = true;
        } else {
            z4 = false;
            z3 = false;
        }
        int i2 = (sum + sum2) - i;
        boolean z6 = (sum & 1) == 1;
        if ((sum2 & 1) == 0) {
            z5 = true;
        }
        if (i2 == 1) {
            if (z6) {
                if (!z5) {
                    z2 = true;
                } else {
                    throw NotFoundException.getNotFoundInstance();
                }
            } else if (z5) {
                z3 = true;
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } else if (i2 == -1) {
            if (z6) {
                if (!z5) {
                    z = true;
                } else {
                    throw NotFoundException.getNotFoundInstance();
                }
            } else if (z5) {
                z4 = true;
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } else if (i2 != 0) {
            throw NotFoundException.getNotFoundInstance();
        } else if (z6) {
            if (!z5) {
                throw NotFoundException.getNotFoundInstance();
            } else if (sum < sum2) {
                z3 = true;
                z = true;
            } else {
                z4 = true;
                z2 = true;
            }
        } else if (z5) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (z) {
            if (!z2) {
                increment(getOddCounts(), getOddRoundingErrors());
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        if (z2) {
            decrement(getOddCounts(), getOddRoundingErrors());
        }
        if (z4) {
            if (!z3) {
                increment(getEvenCounts(), getOddRoundingErrors());
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        if (z3) {
            decrement(getEvenCounts(), getEvenRoundingErrors());
        }
    }
}
