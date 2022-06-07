package com.journeyapps.barcodescanner;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.HybridBinarizer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class Decoder implements ResultPointCallback {
    private Reader a;
    private List<ResultPoint> b = new ArrayList();

    public Decoder(Reader reader) {
        this.a = reader;
    }

    protected Reader getReader() {
        return this.a;
    }

    public Result decode(LuminanceSource luminanceSource) {
        return decode(toBitmap(luminanceSource));
    }

    protected BinaryBitmap toBitmap(LuminanceSource luminanceSource) {
        return new BinaryBitmap(new HybridBinarizer(luminanceSource));
    }

    protected Result decode(BinaryBitmap binaryBitmap) {
        this.b.clear();
        try {
            if (this.a instanceof MultiFormatReader) {
                return ((MultiFormatReader) this.a).decodeWithState(binaryBitmap);
            }
            return this.a.decode(binaryBitmap);
        } catch (Exception unused) {
            return null;
        } finally {
            this.a.reset();
        }
    }

    public List<ResultPoint> getPossibleResultPoints() {
        return new ArrayList(this.b);
    }

    @Override // com.google.zxing.ResultPointCallback
    public void foundPossibleResultPoint(ResultPoint resultPoint) {
        this.b.add(resultPoint);
    }
}
