package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class FinderPatternFinder {
    protected static final int MAX_MODULES = 97;
    protected static final int MIN_SKIP = 3;
    private final BitMatrix a;
    private final List<FinderPattern> b;
    private boolean c;
    private final int[] d;
    private final ResultPointCallback e;

    public FinderPatternFinder(BitMatrix bitMatrix) {
        this(bitMatrix, null);
    }

    public FinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        this.a = bitMatrix;
        this.b = new ArrayList();
        this.d = new int[5];
        this.e = resultPointCallback;
    }

    protected final BitMatrix getImage() {
        return this.a;
    }

    protected final List<FinderPattern> getPossibleCenters() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final FinderPatternInfo b(Map<DecodeHintType, ?> map) throws NotFoundException {
        boolean z = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        int height = this.a.getHeight();
        int width = this.a.getWidth();
        int i = (height * 3) / 388;
        if (i < 3 || z) {
            i = 3;
        }
        int[] iArr = new int[5];
        int i2 = i - 1;
        int i3 = i;
        boolean z2 = false;
        while (i2 < height && !z2) {
            clearCounts(iArr);
            boolean z3 = z2;
            int i4 = 0;
            int i5 = 0;
            while (i4 < width) {
                if (this.a.get(i4, i2)) {
                    if ((i5 & 1) == 1) {
                        i5++;
                    }
                    iArr[i5] = iArr[i5] + 1;
                } else if ((i5 & 1) != 0) {
                    iArr[i5] = iArr[i5] + 1;
                } else if (i5 != 4) {
                    i5++;
                    iArr[i5] = iArr[i5] + 1;
                } else if (!foundPatternCross(iArr)) {
                    shiftCounts2(iArr);
                    i5 = 3;
                } else if (handlePossibleCenter(iArr, i2, i4)) {
                    if (this.c) {
                        z3 = c();
                    } else {
                        int b2 = b();
                        if (b2 > iArr[2]) {
                            i2 += (b2 - iArr[2]) - 2;
                            i4 = width - 1;
                        }
                    }
                    clearCounts(iArr);
                    i5 = 0;
                    i3 = 2;
                } else {
                    shiftCounts2(iArr);
                    i5 = 3;
                }
                i4++;
            }
            if (!foundPatternCross(iArr) || !handlePossibleCenter(iArr, i2, width)) {
                i3 = i3;
                z2 = z3;
            } else {
                int i6 = iArr[0];
                if (this.c) {
                    i3 = i6;
                    z2 = c();
                } else {
                    i3 = i6;
                    z2 = z3;
                }
            }
            i2 += i3;
        }
        FinderPattern[] d = d();
        ResultPoint.orderBestPatterns(d);
        return new FinderPatternInfo(d);
    }

    private static float a(int[] iArr, int i) {
        return ((i - iArr[4]) - iArr[3]) - (iArr[2] / 2.0f);
    }

    protected static boolean foundPatternCross(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 5; i2++) {
            int i3 = iArr[i2];
            if (i3 == 0) {
                return false;
            }
            i += i3;
        }
        if (i < 7) {
            return false;
        }
        float f = i / 7.0f;
        float f2 = f / 2.0f;
        return Math.abs(f - ((float) iArr[0])) < f2 && Math.abs(f - ((float) iArr[1])) < f2 && Math.abs((f * 3.0f) - ((float) iArr[2])) < 3.0f * f2 && Math.abs(f - ((float) iArr[3])) < f2 && Math.abs(f - ((float) iArr[4])) < f2;
    }

    protected static boolean foundPatternDiagonal(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 5; i2++) {
            int i3 = iArr[i2];
            if (i3 == 0) {
                return false;
            }
            i += i3;
        }
        if (i < 7) {
            return false;
        }
        float f = i / 7.0f;
        float f2 = f / 1.333f;
        return Math.abs(f - ((float) iArr[0])) < f2 && Math.abs(f - ((float) iArr[1])) < f2 && Math.abs((f * 3.0f) - ((float) iArr[2])) < 3.0f * f2 && Math.abs(f - ((float) iArr[3])) < f2 && Math.abs(f - ((float) iArr[4])) < f2;
    }

    private int[] a() {
        clearCounts(this.d);
        return this.d;
    }

    protected final void clearCounts(int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = 0;
        }
    }

    protected final void shiftCounts2(int[] iArr) {
        iArr[0] = iArr[2];
        iArr[1] = iArr[3];
        iArr[2] = iArr[4];
        iArr[3] = 1;
        iArr[4] = 0;
    }

    private boolean a(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int[] a2 = a();
        int i6 = 0;
        while (i >= i6 && i2 >= i6 && this.a.get(i2 - i6, i - i6)) {
            a2[2] = a2[2] + 1;
            i6++;
        }
        if (a2[2] == 0) {
            return false;
        }
        while (i >= i6 && i2 >= i6 && !this.a.get(i2 - i6, i - i6)) {
            a2[1] = a2[1] + 1;
            i6++;
        }
        if (a2[1] == 0) {
            return false;
        }
        while (i >= i6 && i2 >= i6 && this.a.get(i2 - i6, i - i6)) {
            a2[0] = a2[0] + 1;
            i6++;
        }
        if (a2[0] == 0) {
            return false;
        }
        int height = this.a.getHeight();
        int width = this.a.getWidth();
        int i7 = 1;
        while (true) {
            int i8 = i + i7;
            if (i8 >= height || (i5 = i2 + i7) >= width || !this.a.get(i5, i8)) {
                break;
            }
            a2[2] = a2[2] + 1;
            i7++;
        }
        while (true) {
            int i9 = i + i7;
            if (i9 >= height || (i4 = i2 + i7) >= width || this.a.get(i4, i9)) {
                break;
            }
            a2[3] = a2[3] + 1;
            i7++;
        }
        if (a2[3] == 0) {
            return false;
        }
        while (true) {
            int i10 = i + i7;
            if (i10 >= height || (i3 = i2 + i7) >= width || !this.a.get(i3, i10)) {
                break;
            }
            a2[4] = a2[4] + 1;
            i7++;
        }
        if (a2[4] == 0) {
            return false;
        }
        return foundPatternDiagonal(a2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003a, code lost:
        if (r2[1] <= r13) goto L_0x003e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003f, code lost:
        if (r3 < 0) goto L_0x0053;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0045, code lost:
        if (r0.get(r12, r3) == false) goto L_0x0053;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0049, code lost:
        if (r2[0] > r13) goto L_0x0053;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x004b, code lost:
        r2[0] = r2[0] + 1;
        r3 = r3 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0055, code lost:
        if (r2[0] <= r13) goto L_0x0058;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0057, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0058, code lost:
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0059, code lost:
        if (r11 >= r1) goto L_0x0069;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x005f, code lost:
        if (r0.get(r12, r11) == false) goto L_0x0069;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0061, code lost:
        r2[2] = r2[2] + 1;
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0069, code lost:
        if (r11 != r1) goto L_0x006c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006b, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x006d, code lost:
        if (r11 >= r1) goto L_0x0081;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0073, code lost:
        if (r0.get(r12, r11) != false) goto L_0x0081;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0077, code lost:
        if (r2[3] >= r13) goto L_0x0081;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0079, code lost:
        r2[3] = r2[3] + 1;
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0081, code lost:
        if (r11 == r1) goto L_0x00c7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0085, code lost:
        if (r2[3] < r13) goto L_0x0088;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0089, code lost:
        if (r11 >= r1) goto L_0x009d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x008f, code lost:
        if (r0.get(r12, r11) == false) goto L_0x009d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0093, code lost:
        if (r2[4] >= r13) goto L_0x009d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0095, code lost:
        r2[4] = r2[4] + 1;
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x009f, code lost:
        if (r2[4] < r13) goto L_0x00a2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00a1, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00b8, code lost:
        if ((java.lang.Math.abs(((((r2[0] + r2[1]) + r2[2]) + r2[3]) + r2[4]) - r14) * 5) < (r14 * 2)) goto L_0x00bb;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00ba, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00bf, code lost:
        if (foundPatternCross(r2) == false) goto L_0x00c6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c5, code lost:
        return a(r2, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00c6, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00c7, code lost:
        return Float.NaN;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float a(int r11, int r12, int r13, int r14) {
        /*
            Method dump skipped, instructions count: 201
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.detector.FinderPatternFinder.a(int, int, int, int):float");
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003a, code lost:
        if (r2[1] <= r13) goto L_0x003e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003f, code lost:
        if (r3 < 0) goto L_0x0053;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0045, code lost:
        if (r0.get(r3, r12) == false) goto L_0x0053;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0049, code lost:
        if (r2[0] > r13) goto L_0x0053;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x004b, code lost:
        r2[0] = r2[0] + 1;
        r3 = r3 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0055, code lost:
        if (r2[0] <= r13) goto L_0x0058;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0057, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0058, code lost:
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0059, code lost:
        if (r11 >= r1) goto L_0x0069;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x005f, code lost:
        if (r0.get(r11, r12) == false) goto L_0x0069;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0061, code lost:
        r2[2] = r2[2] + 1;
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0069, code lost:
        if (r11 != r1) goto L_0x006c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006b, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x006d, code lost:
        if (r11 >= r1) goto L_0x0081;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0073, code lost:
        if (r0.get(r11, r12) != false) goto L_0x0081;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0077, code lost:
        if (r2[3] >= r13) goto L_0x0081;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0079, code lost:
        r2[3] = r2[3] + 1;
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0081, code lost:
        if (r11 == r1) goto L_0x00c6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0085, code lost:
        if (r2[3] < r13) goto L_0x0088;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0089, code lost:
        if (r11 >= r1) goto L_0x009d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x008f, code lost:
        if (r0.get(r11, r12) == false) goto L_0x009d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0093, code lost:
        if (r2[4] >= r13) goto L_0x009d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0095, code lost:
        r2[4] = r2[4] + 1;
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x009f, code lost:
        if (r2[4] < r13) goto L_0x00a2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00a1, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00b7, code lost:
        if ((java.lang.Math.abs(((((r2[0] + r2[1]) + r2[2]) + r2[3]) + r2[4]) - r14) * 5) < r14) goto L_0x00ba;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b9, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00be, code lost:
        if (foundPatternCross(r2) == false) goto L_0x00c5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c4, code lost:
        return a(r2, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00c5, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00c6, code lost:
        return Float.NaN;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float b(int r11, int r12, int r13, int r14) {
        /*
            Method dump skipped, instructions count: 200
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.detector.FinderPatternFinder.b(int, int, int, int):float");
    }

    @Deprecated
    protected final boolean handlePossibleCenter(int[] iArr, int i, int i2, boolean z) {
        return handlePossibleCenter(iArr, i, i2);
    }

    protected final boolean handlePossibleCenter(int[] iArr, int i, int i2) {
        boolean z = false;
        int i3 = iArr[0] + iArr[1] + iArr[2] + iArr[3] + iArr[4];
        int a2 = (int) a(iArr, i2);
        float a3 = a(i, a2, iArr[2], i3);
        if (!Float.isNaN(a3)) {
            int i4 = (int) a3;
            float b2 = b(a2, i4, iArr[2], i3);
            if (!Float.isNaN(b2) && a(i4, (int) b2)) {
                float f = i3 / 7.0f;
                int i5 = 0;
                while (true) {
                    if (i5 >= this.b.size()) {
                        break;
                    }
                    FinderPattern finderPattern = this.b.get(i5);
                    if (finderPattern.a(f, a3, b2)) {
                        this.b.set(i5, finderPattern.b(a3, b2, f));
                        z = true;
                        break;
                    }
                    i5++;
                }
                if (!z) {
                    FinderPattern finderPattern2 = new FinderPattern(b2, a3, f);
                    this.b.add(finderPattern2);
                    ResultPointCallback resultPointCallback = this.e;
                    if (resultPointCallback != null) {
                        resultPointCallback.foundPossibleResultPoint(finderPattern2);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private int b() {
        if (this.b.size() <= 1) {
            return 0;
        }
        FinderPattern finderPattern = null;
        for (FinderPattern finderPattern2 : this.b) {
            if (finderPattern2.a() >= 2) {
                if (finderPattern == null) {
                    finderPattern = finderPattern2;
                } else {
                    this.c = true;
                    return ((int) (Math.abs(finderPattern.getX() - finderPattern2.getX()) - Math.abs(finderPattern.getY() - finderPattern2.getY()))) / 2;
                }
            }
        }
        return 0;
    }

    private boolean c() {
        int size = this.b.size();
        float f = 0.0f;
        float f2 = 0.0f;
        int i = 0;
        for (FinderPattern finderPattern : this.b) {
            if (finderPattern.a() >= 2) {
                i++;
                f2 += finderPattern.getEstimatedModuleSize();
            }
        }
        if (i < 3) {
            return false;
        }
        float f3 = f2 / size;
        for (FinderPattern finderPattern2 : this.b) {
            f += Math.abs(finderPattern2.getEstimatedModuleSize() - f3);
        }
        return f <= f2 * 0.05f;
    }

    private FinderPattern[] d() throws NotFoundException {
        int size = this.b.size();
        if (size >= 3) {
            float f = 0.0f;
            if (size > 3) {
                float f2 = 0.0f;
                float f3 = 0.0f;
                for (FinderPattern finderPattern : this.b) {
                    float estimatedModuleSize = finderPattern.getEstimatedModuleSize();
                    f2 += estimatedModuleSize;
                    f3 += estimatedModuleSize * estimatedModuleSize;
                }
                float f4 = size;
                float f5 = f2 / f4;
                float sqrt = (float) Math.sqrt((f3 / f4) - (f5 * f5));
                Collections.sort(this.b, new b(f5));
                float max = Math.max(0.2f * f5, sqrt);
                int i = 0;
                while (i < this.b.size() && this.b.size() > 3) {
                    if (Math.abs(this.b.get(i).getEstimatedModuleSize() - f5) > max) {
                        this.b.remove(i);
                        i--;
                    }
                    i++;
                }
            }
            if (this.b.size() > 3) {
                for (FinderPattern finderPattern2 : this.b) {
                    f += finderPattern2.getEstimatedModuleSize();
                }
                Collections.sort(this.b, new a(f / this.b.size()));
                List<FinderPattern> list = this.b;
                list.subList(3, list.size()).clear();
            }
            return new FinderPattern[]{this.b.get(0), this.b.get(1), this.b.get(2)};
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b implements Serializable, Comparator<FinderPattern> {
        private final float average;

        private b(float f) {
            this.average = f;
        }

        /* renamed from: a */
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            return Float.compare(Math.abs(finderPattern2.getEstimatedModuleSize() - this.average), Math.abs(finderPattern.getEstimatedModuleSize() - this.average));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a implements Serializable, Comparator<FinderPattern> {
        private final float average;

        private a(float f) {
            this.average = f;
        }

        /* renamed from: a */
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            int compare = Integer.compare(finderPattern2.a(), finderPattern.a());
            return compare == 0 ? Float.compare(Math.abs(finderPattern.getEstimatedModuleSize() - this.average), Math.abs(finderPattern2.getEstimatedModuleSize() - this.average)) : compare;
        }
    }
}
