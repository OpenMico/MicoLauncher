package com.journeyapps.barcodescanner;

import com.google.zxing.ResultPoint;
import java.util.List;

/* loaded from: classes2.dex */
public interface BarcodeCallback {
    void barcodeResult(BarcodeResult barcodeResult);

    void possibleResultPoints(List<ResultPoint> list);
}
