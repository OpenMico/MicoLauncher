package com.google.zxing.common;

import com.google.zxing.ResultPoint;

/* loaded from: classes2.dex */
public class DetectorResult {
    private final BitMatrix a;
    private final ResultPoint[] b;

    public DetectorResult(BitMatrix bitMatrix, ResultPoint[] resultPointArr) {
        this.a = bitMatrix;
        this.b = resultPointArr;
    }

    public final BitMatrix getBits() {
        return this.a;
    }

    public final ResultPoint[] getPoints() {
        return this.b;
    }
}
