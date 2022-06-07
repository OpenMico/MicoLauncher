package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class Detector {
    private static final int[] a = {0, 4, 1, 5};
    private static final int[] b = {6, 2, 7, 3};
    private static final int[] c = {8, 1, 1, 1, 1, 1, 1, 3};
    private static final int[] d = {7, 1, 1, 3, 1, 1, 1, 2, 1};

    private Detector() {
    }

    public static PDF417DetectorResult detect(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, boolean z) throws NotFoundException {
        BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
        List<ResultPoint[]> a2 = a(z, blackMatrix);
        if (a2.isEmpty()) {
            blackMatrix = blackMatrix.clone();
            blackMatrix.rotate180();
            a2 = a(z, blackMatrix);
        }
        return new PDF417DetectorResult(blackMatrix, a2);
    }

    private static List<ResultPoint[]> a(boolean z, BitMatrix bitMatrix) {
        ArrayList<ResultPoint[]> arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        while (i < bitMatrix.getHeight()) {
            ResultPoint[] a2 = a(bitMatrix, i, i2);
            if (a2[0] == null && a2[3] == null) {
                if (!z2) {
                    break;
                }
                for (ResultPoint[] resultPointArr : arrayList) {
                    if (resultPointArr[1] != null) {
                        i = (int) Math.max(i, resultPointArr[1].getY());
                    }
                    if (resultPointArr[3] != null) {
                        i = Math.max(i, (int) resultPointArr[3].getY());
                    }
                }
                i += 5;
                i2 = 0;
                z2 = false;
            } else {
                arrayList.add(a2);
                if (!z) {
                    break;
                } else if (a2[2] != null) {
                    i2 = (int) a2[2].getX();
                    i = (int) a2[2].getY();
                    z2 = true;
                } else {
                    i2 = (int) a2[4].getX();
                    i = (int) a2[4].getY();
                    z2 = true;
                }
            }
        }
        return arrayList;
    }

    private static ResultPoint[] a(BitMatrix bitMatrix, int i, int i2) {
        int i3;
        int i4;
        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        ResultPoint[] resultPointArr = new ResultPoint[8];
        a(resultPointArr, a(bitMatrix, height, width, i, i2, c), a);
        if (resultPointArr[4] != null) {
            i3 = (int) resultPointArr[4].getX();
            i4 = (int) resultPointArr[4].getY();
        } else {
            i4 = i;
            i3 = i2;
        }
        a(resultPointArr, a(bitMatrix, height, width, i4, i3, d), b);
        return resultPointArr;
    }

    private static void a(ResultPoint[] resultPointArr, ResultPoint[] resultPointArr2, int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            resultPointArr[iArr[i]] = resultPointArr2[i];
        }
    }

    private static ResultPoint[] a(BitMatrix bitMatrix, int i, int i2, int i3, int i4, int[] iArr) {
        boolean z;
        int i5;
        ResultPoint[] resultPointArr = new ResultPoint[4];
        int[] iArr2 = new int[iArr.length];
        int i6 = i3;
        while (true) {
            if (i6 >= i) {
                z = false;
                break;
            }
            int[] a2 = a(bitMatrix, i4, i6, i2, false, iArr, iArr2);
            if (a2 != null) {
                int[] iArr3 = a2;
                while (true) {
                    if (i6 <= 0) {
                        break;
                    }
                    i6--;
                    int[] a3 = a(bitMatrix, i4, i6, i2, false, iArr, iArr2);
                    if (a3 == null) {
                        i6++;
                        break;
                    }
                    iArr3 = a3;
                }
                float f = i6;
                resultPointArr[0] = new ResultPoint(iArr3[0], f);
                resultPointArr[1] = new ResultPoint(iArr3[1], f);
                z = true;
            } else {
                i6 += 5;
            }
        }
        int i7 = i6 + 1;
        if (z) {
            int[] iArr4 = {(int) resultPointArr[0].getX(), (int) resultPointArr[1].getX()};
            int i8 = i7;
            int i9 = 0;
            while (true) {
                if (i8 >= i) {
                    i5 = i9;
                    break;
                }
                i5 = i9;
                int[] a4 = a(bitMatrix, iArr4[0], i8, i2, false, iArr, iArr2);
                if (a4 == null || Math.abs(iArr4[0] - a4[0]) >= 5 || Math.abs(iArr4[1] - a4[1]) >= 5) {
                    if (i5 > 25) {
                        break;
                    }
                    i9 = i5 + 1;
                } else {
                    iArr4 = a4;
                    i9 = 0;
                }
                i8++;
            }
            i7 = i8 - (i5 + 1);
            float f2 = i7;
            resultPointArr[2] = new ResultPoint(iArr4[0], f2);
            resultPointArr[3] = new ResultPoint(iArr4[1], f2);
        }
        if (i7 - i6 < 10) {
            Arrays.fill(resultPointArr, (Object) null);
        }
        return resultPointArr;
    }

    private static int[] a(BitMatrix bitMatrix, int i, int i2, int i3, boolean z, int[] iArr, int[] iArr2) {
        Arrays.fill(iArr2, 0, iArr2.length, 0);
        int i4 = 0;
        while (bitMatrix.get(i, i2) && i > 0) {
            i4++;
            if (i4 >= 3) {
                break;
            }
            i--;
        }
        int length = iArr.length;
        int i5 = i;
        boolean z2 = z;
        int i6 = 0;
        while (true) {
            z2 = true;
            if (i < i3) {
                if (bitMatrix.get(i, i2) != z2) {
                    iArr2[i6] = iArr2[i6] + 1;
                } else {
                    if (i6 != length - 1) {
                        i6++;
                    } else if (a(iArr2, iArr, 0.8f) < 0.42f) {
                        return new int[]{i5, i};
                    } else {
                        i5 += iArr2[0] + iArr2[1];
                        int i7 = i6 - 1;
                        System.arraycopy(iArr2, 2, iArr2, 0, i7);
                        iArr2[i7] = 0;
                        iArr2[i6] = 0;
                        i6--;
                    }
                    iArr2[i6] = 1;
                    if (z2) {
                        z2 = false;
                    }
                }
                i++;
            } else if (i6 != length - 1 || a(iArr2, iArr, 0.8f) >= 0.42f) {
                return null;
            } else {
                return new int[]{i5, i - 1};
            }
        }
    }

    private static float a(int[] iArr, int[] iArr2, float f) {
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            i += iArr[i3];
            i2 += iArr2[i3];
        }
        if (i < i2) {
            return Float.POSITIVE_INFINITY;
        }
        float f2 = i;
        float f3 = f2 / i2;
        float f4 = f * f3;
        float f5 = 0.0f;
        for (int i4 = 0; i4 < length; i4++) {
            float f6 = iArr2[i4] * f3;
            float f7 = iArr[i4];
            float f8 = f7 > f6 ? f7 - f6 : f6 - f7;
            if (f8 > f4) {
                return Float.POSITIVE_INFINITY;
            }
            f5 += f8;
        }
        return f5 / f2;
    }
}
