package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.aztec.encoder.Encoder;
import com.google.zxing.common.BitMatrix;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/* loaded from: classes2.dex */
public final class AztecWriter implements Writer {
    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2) {
        return encode(str, barcodeFormat, i, i2, null);
    }

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) {
        int i3;
        int i4;
        Charset charset;
        Charset charset2 = StandardCharsets.ISO_8859_1;
        int i5 = 33;
        if (map != null) {
            if (map.containsKey(EncodeHintType.CHARACTER_SET)) {
                charset2 = Charset.forName(map.get(EncodeHintType.CHARACTER_SET).toString());
            }
            if (map.containsKey(EncodeHintType.ERROR_CORRECTION)) {
                i5 = Integer.parseInt(map.get(EncodeHintType.ERROR_CORRECTION).toString());
            }
            if (map.containsKey(EncodeHintType.AZTEC_LAYERS)) {
                i3 = Integer.parseInt(map.get(EncodeHintType.AZTEC_LAYERS).toString());
                charset = charset2;
                i4 = i5;
            } else {
                charset = charset2;
                i4 = i5;
                i3 = 0;
            }
        } else {
            charset = charset2;
            i4 = 33;
            i3 = 0;
        }
        return a(str, barcodeFormat, i, i2, charset, i4, i3);
    }

    private static BitMatrix a(String str, BarcodeFormat barcodeFormat, int i, int i2, Charset charset, int i3, int i4) {
        if (barcodeFormat == BarcodeFormat.AZTEC) {
            return a(Encoder.encode(str.getBytes(charset), i3, i4), i, i2);
        }
        throw new IllegalArgumentException("Can only encode AZTEC, but got " + barcodeFormat);
    }

    private static BitMatrix a(AztecCode aztecCode, int i, int i2) {
        BitMatrix matrix = aztecCode.getMatrix();
        if (matrix != null) {
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int max = Math.max(i, width);
            int max2 = Math.max(i2, height);
            int min = Math.min(max / width, max2 / height);
            int i3 = (max - (width * min)) / 2;
            int i4 = (max2 - (height * min)) / 2;
            BitMatrix bitMatrix = new BitMatrix(max, max2);
            int i5 = 0;
            while (i5 < height) {
                int i6 = 0;
                int i7 = i3;
                while (i6 < width) {
                    if (matrix.get(i6, i5)) {
                        bitMatrix.setRegion(i7, i4, min, min);
                    }
                    i6++;
                    i7 += min;
                }
                i5++;
                i4 += min;
            }
            return bitMatrix;
        }
        throw new IllegalStateException();
    }
}
