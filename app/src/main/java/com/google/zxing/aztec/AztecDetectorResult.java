package com.google.zxing.aztec;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;

/* loaded from: classes2.dex */
public final class AztecDetectorResult extends DetectorResult {
    private final boolean a;
    private final int b;
    private final int c;

    public AztecDetectorResult(BitMatrix bitMatrix, ResultPoint[] resultPointArr, boolean z, int i, int i2) {
        super(bitMatrix, resultPointArr);
        this.a = z;
        this.b = i;
        this.c = i2;
    }

    public int getNbLayers() {
        return this.c;
    }

    public int getNbDatablocks() {
        return this.b;
    }

    public boolean isCompact() {
        return this.a;
    }
}
