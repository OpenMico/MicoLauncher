package com.journeyapps.barcodescanner;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.common.HybridBinarizer;

/* loaded from: classes2.dex */
public class MixedDecoder extends Decoder {
    private boolean a = true;

    public MixedDecoder(Reader reader) {
        super(reader);
    }

    @Override // com.journeyapps.barcodescanner.Decoder
    protected BinaryBitmap toBitmap(LuminanceSource luminanceSource) {
        if (this.a) {
            this.a = false;
            return new BinaryBitmap(new HybridBinarizer(luminanceSource.invert()));
        }
        this.a = true;
        return new BinaryBitmap(new HybridBinarizer(luminanceSource));
    }
}
