package com.google.zxing.qrcode.decoder;

import com.google.zxing.ResultPoint;

/* loaded from: classes2.dex */
public final class QRCodeDecoderMetaData {
    private final boolean a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public QRCodeDecoderMetaData(boolean z) {
        this.a = z;
    }

    public boolean isMirrored() {
        return this.a;
    }

    public void applyMirroredCorrection(ResultPoint[] resultPointArr) {
        if (this.a && resultPointArr != null && resultPointArr.length >= 3) {
            ResultPoint resultPoint = resultPointArr[0];
            resultPointArr[0] = resultPointArr[2];
            resultPointArr[2] = resultPoint;
        }
    }
}
