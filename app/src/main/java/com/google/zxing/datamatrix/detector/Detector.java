package com.google.zxing.datamatrix.detector;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class Detector {
    private final BitMatrix a;
    private final WhiteRectangleDetector b;

    public Detector(BitMatrix bitMatrix) throws NotFoundException {
        this.a = bitMatrix;
        this.b = new WhiteRectangleDetector(bitMatrix);
    }

    public DetectorResult detect() throws NotFoundException {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        BitMatrix bitMatrix;
        ResultPoint resultPoint3;
        ResultPoint[] detect = this.b.detect();
        ResultPoint resultPoint4 = detect[0];
        ResultPoint resultPoint5 = detect[1];
        ResultPoint resultPoint6 = detect[2];
        ResultPoint resultPoint7 = detect[3];
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(b(resultPoint4, resultPoint5));
        arrayList.add(b(resultPoint4, resultPoint6));
        arrayList.add(b(resultPoint5, resultPoint7));
        arrayList.add(b(resultPoint6, resultPoint7));
        ResultPoint resultPoint8 = null;
        Collections.sort(arrayList, new b());
        a aVar = (a) arrayList.get(0);
        a aVar2 = (a) arrayList.get(1);
        HashMap hashMap = new HashMap();
        a(hashMap, aVar.a());
        a(hashMap, aVar.b());
        a(hashMap, aVar2.a());
        a(hashMap, aVar2.b());
        ResultPoint resultPoint9 = null;
        ResultPoint resultPoint10 = null;
        for (Map.Entry entry : hashMap.entrySet()) {
            ResultPoint resultPoint11 = (ResultPoint) entry.getKey();
            if (((Integer) entry.getValue()).intValue() == 2) {
                resultPoint9 = resultPoint11;
            } else if (resultPoint8 == null) {
                resultPoint8 = resultPoint11;
            } else {
                resultPoint10 = resultPoint11;
            }
        }
        if (resultPoint8 == null || resultPoint9 == null || resultPoint10 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        ResultPoint[] resultPointArr = {resultPoint8, resultPoint9, resultPoint10};
        ResultPoint.orderBestPatterns(resultPointArr);
        ResultPoint resultPoint12 = resultPointArr[0];
        ResultPoint resultPoint13 = resultPointArr[1];
        ResultPoint resultPoint14 = resultPointArr[2];
        if (!hashMap.containsKey(resultPoint4)) {
            resultPoint = resultPoint4;
        } else if (!hashMap.containsKey(resultPoint5)) {
            resultPoint = resultPoint5;
        } else {
            resultPoint = !hashMap.containsKey(resultPoint6) ? resultPoint6 : resultPoint7;
        }
        int c = b(resultPoint14, resultPoint).c();
        int c2 = b(resultPoint12, resultPoint).c();
        if ((c & 1) == 1) {
            c++;
        }
        int i = c + 2;
        if ((c2 & 1) == 1) {
            c2++;
        }
        int i2 = c2 + 2;
        if (i * 4 >= i2 * 7 || i2 * 4 >= i * 7) {
            resultPoint2 = resultPoint14;
            resultPoint3 = a(resultPoint13, resultPoint12, resultPoint14, resultPoint, i, i2);
            if (resultPoint3 == null) {
                resultPoint3 = resultPoint;
            }
            int c3 = b(resultPoint2, resultPoint3).c();
            int c4 = b(resultPoint12, resultPoint3).c();
            bitMatrix = a(this.a, resultPoint2, resultPoint13, resultPoint12, resultPoint3, (c3 & 1) == 1 ? c3 + 1 : c3, (c4 & 1) == 1 ? c4 + 1 : c4);
        } else {
            resultPoint3 = a(resultPoint13, resultPoint12, resultPoint14, resultPoint, Math.min(i2, i));
            if (resultPoint3 == null) {
                resultPoint3 = resultPoint;
            }
            int max = Math.max(b(resultPoint14, resultPoint3).c(), b(resultPoint12, resultPoint3).c()) + 1;
            int i3 = (max & 1) == 1 ? max + 1 : max;
            bitMatrix = a(this.a, resultPoint14, resultPoint13, resultPoint12, resultPoint3, i3, i3);
            resultPoint2 = resultPoint14;
        }
        return new DetectorResult(bitMatrix, new ResultPoint[]{resultPoint2, resultPoint13, resultPoint12, resultPoint3});
    }

    private ResultPoint a(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i, int i2) {
        float a2 = a(resultPoint, resultPoint2) / i;
        float a3 = a(resultPoint3, resultPoint4);
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint3.getX()) / a3) * a2), resultPoint4.getY() + (a2 * ((resultPoint4.getY() - resultPoint3.getY()) / a3)));
        float a4 = a(resultPoint, resultPoint3) / i2;
        float a5 = a(resultPoint2, resultPoint4);
        ResultPoint resultPoint6 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint2.getX()) / a5) * a4), resultPoint4.getY() + (a4 * ((resultPoint4.getY() - resultPoint2.getY()) / a5)));
        if (a(resultPoint5)) {
            return (a(resultPoint6) && Math.abs(i - b(resultPoint3, resultPoint5).c()) + Math.abs(i2 - b(resultPoint2, resultPoint5).c()) > Math.abs(i - b(resultPoint3, resultPoint6).c()) + Math.abs(i2 - b(resultPoint2, resultPoint6).c())) ? resultPoint6 : resultPoint5;
        }
        if (a(resultPoint6)) {
            return resultPoint6;
        }
        return null;
    }

    private ResultPoint a(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i) {
        float f = i;
        float a2 = a(resultPoint, resultPoint2) / f;
        float a3 = a(resultPoint3, resultPoint4);
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint3.getX()) / a3) * a2), resultPoint4.getY() + (a2 * ((resultPoint4.getY() - resultPoint3.getY()) / a3)));
        float a4 = a(resultPoint, resultPoint3) / f;
        float a5 = a(resultPoint2, resultPoint4);
        ResultPoint resultPoint6 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint2.getX()) / a5) * a4), resultPoint4.getY() + (a4 * ((resultPoint4.getY() - resultPoint2.getY()) / a5)));
        if (a(resultPoint5)) {
            return (a(resultPoint6) && Math.abs(b(resultPoint3, resultPoint5).c() - b(resultPoint2, resultPoint5).c()) > Math.abs(b(resultPoint3, resultPoint6).c() - b(resultPoint2, resultPoint6).c())) ? resultPoint6 : resultPoint5;
        }
        if (a(resultPoint6)) {
            return resultPoint6;
        }
        return null;
    }

    private boolean a(ResultPoint resultPoint) {
        return resultPoint.getX() >= 0.0f && resultPoint.getX() < ((float) this.a.getWidth()) && resultPoint.getY() > 0.0f && resultPoint.getY() < ((float) this.a.getHeight());
    }

    private static int a(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2));
    }

    private static void a(Map<ResultPoint, Integer> map, ResultPoint resultPoint) {
        Integer num = map.get(resultPoint);
        int i = 1;
        if (num != null) {
            i = 1 + num.intValue();
        }
        map.put(resultPoint, Integer.valueOf(i));
    }

    private static BitMatrix a(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i, int i2) throws NotFoundException {
        float f = i - 0.5f;
        float f2 = i2 - 0.5f;
        return GridSampler.getInstance().sampleGrid(bitMatrix, i, i2, 0.5f, 0.5f, f, 0.5f, f, f2, 0.5f, f2, resultPoint.getX(), resultPoint.getY(), resultPoint4.getX(), resultPoint4.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private a b(ResultPoint resultPoint, ResultPoint resultPoint2) {
        int x = (int) resultPoint.getX();
        int y = (int) resultPoint.getY();
        int x2 = (int) resultPoint2.getX();
        int y2 = (int) resultPoint2.getY();
        int i = 0;
        int i2 = 1;
        boolean z = Math.abs(y2 - y) > Math.abs(x2 - x);
        if (z) {
            y = x;
            x = y;
            y2 = x2;
            x2 = y2;
        }
        int abs = Math.abs(x2 - x);
        int abs2 = Math.abs(y2 - y);
        int i3 = (-abs) / 2;
        int i4 = y < y2 ? 1 : -1;
        if (x >= x2) {
            i2 = -1;
        }
        boolean z2 = this.a.get(z ? y : x, z ? x : y);
        while (x != x2) {
            boolean z3 = this.a.get(z ? y : x, z ? x : y);
            if (z3 != z2) {
                i++;
                z2 = z3;
            }
            i3 += abs2;
            if (i3 > 0) {
                if (y == y2) {
                    break;
                }
                y += i4;
                i3 -= abs;
            }
            x += i2;
        }
        return new a(resultPoint, resultPoint2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        private final ResultPoint a;
        private final ResultPoint b;
        private final int c;

        private a(ResultPoint resultPoint, ResultPoint resultPoint2, int i) {
            this.a = resultPoint;
            this.b = resultPoint2;
            this.c = i;
        }

        ResultPoint a() {
            return this.a;
        }

        ResultPoint b() {
            return this.b;
        }

        int c() {
            return this.c;
        }

        public String toString() {
            return this.a + "/" + this.b + JsonPointer.SEPARATOR + this.c;
        }
    }

    /* loaded from: classes2.dex */
    private static final class b implements Serializable, Comparator<a> {
        private b() {
        }

        /* renamed from: a */
        public int compare(a aVar, a aVar2) {
            return aVar.c() - aVar2.c();
        }
    }
}
