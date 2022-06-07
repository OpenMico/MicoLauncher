package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.qrcode.detector.Detector;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes2.dex */
public final class MultiDetector extends Detector {
    private static final DetectorResult[] a = new DetectorResult[0];

    public MultiDetector(BitMatrix bitMatrix) {
        super(bitMatrix);
    }

    public DetectorResult[] detectMulti(Map<DecodeHintType, ?> map) throws NotFoundException {
        FinderPatternInfo[] a2 = new a(getImage(), map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK)).a(map);
        if (a2.length != 0) {
            ArrayList arrayList = new ArrayList();
            for (FinderPatternInfo finderPatternInfo : a2) {
                try {
                    arrayList.add(processFinderPatternInfo(finderPatternInfo));
                } catch (ReaderException unused) {
                }
            }
            if (arrayList.isEmpty()) {
                return a;
            }
            return (DetectorResult[]) arrayList.toArray(new DetectorResult[arrayList.size()]);
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
