package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
    private final Reader a;

    public GenericMultipleBarcodeReader(Reader reader) {
        this.a = reader;
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return decodeMultiple(binaryBitmap, null);
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList arrayList = new ArrayList();
        a(binaryBitmap, map, arrayList, 0, 0, 0);
        if (!arrayList.isEmpty()) {
            return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void a(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int i, int i2, int i3) {
        boolean z;
        int i4;
        int i5;
        float f;
        if (i3 <= 4) {
            try {
                Result decode = this.a.decode(binaryBitmap, map);
                Iterator<Result> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().getText().equals(decode.getText())) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (!z) {
                    list.add(a(decode, i, i2));
                }
                ResultPoint[] resultPoints = decode.getResultPoints();
                if (!(resultPoints == null || resultPoints.length == 0)) {
                    int width = binaryBitmap.getWidth();
                    int height = binaryBitmap.getHeight();
                    float f2 = width;
                    float f3 = height;
                    float f4 = 0.0f;
                    float f5 = 0.0f;
                    for (ResultPoint resultPoint : resultPoints) {
                        if (resultPoint != null) {
                            float x = resultPoint.getX();
                            float y = resultPoint.getY();
                            if (x < f2) {
                                f2 = x;
                            }
                            if (y < f3) {
                                f3 = y;
                            }
                            if (x > f4) {
                                f4 = x;
                            }
                            if (y > f5) {
                                f5 = y;
                            }
                        }
                    }
                    if (f2 > 100.0f) {
                        f = f3;
                        i5 = height;
                        i4 = width;
                        a(binaryBitmap.crop(0, 0, (int) f2, height), map, list, i, i2, i3 + 1);
                    } else {
                        f = f3;
                        i5 = height;
                        i4 = width;
                    }
                    if (f > 100.0f) {
                        a(binaryBitmap.crop(0, 0, i4, (int) f), map, list, i, i2, i3 + 1);
                    }
                    if (f4 < i4 - 100) {
                        int i6 = (int) f4;
                        a(binaryBitmap.crop(i6, 0, i4 - i6, i5), map, list, i + i6, i2, i3 + 1);
                    }
                    if (f5 < i5 - 100) {
                        int i7 = (int) f5;
                        a(binaryBitmap.crop(0, i7, i4, i5 - i7), map, list, i, i2 + i7, i3 + 1);
                    }
                }
            } catch (ReaderException unused) {
            }
        }
    }

    private static Result a(Result result, int i, int i2) {
        ResultPoint[] resultPoints = result.getResultPoints();
        if (resultPoints == null) {
            return result;
        }
        ResultPoint[] resultPointArr = new ResultPoint[resultPoints.length];
        for (int i3 = 0; i3 < resultPoints.length; i3++) {
            ResultPoint resultPoint = resultPoints[i3];
            if (resultPoint != null) {
                resultPointArr[i3] = new ResultPoint(resultPoint.getX() + i, resultPoint.getY() + i2);
            }
        }
        Result result2 = new Result(result.getText(), result.getRawBytes(), result.getNumBits(), resultPointArr, result.getBarcodeFormat(), result.getTimestamp());
        result2.putAllMetadata(result.getResultMetadata());
        return result2;
    }
}
