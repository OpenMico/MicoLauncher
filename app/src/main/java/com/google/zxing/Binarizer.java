package com.google.zxing;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

/* loaded from: classes2.dex */
public abstract class Binarizer {
    private final LuminanceSource a;

    public abstract Binarizer createBinarizer(LuminanceSource luminanceSource);

    public abstract BitMatrix getBlackMatrix() throws NotFoundException;

    public abstract BitArray getBlackRow(int i, BitArray bitArray) throws NotFoundException;

    /* JADX INFO: Access modifiers changed from: protected */
    public Binarizer(LuminanceSource luminanceSource) {
        this.a = luminanceSource;
    }

    public final LuminanceSource getLuminanceSource() {
        return this.a;
    }

    public final int getWidth() {
        return this.a.getWidth();
    }

    public final int getHeight() {
        return this.a.getHeight();
    }
}
