package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* loaded from: classes2.dex */
public final class EAN8Writer extends UPCEANWriter {
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.EAN_8) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode EAN_8, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int length = str.length();
        switch (length) {
            case 7:
                try {
                    str = str + UPCEANReader.b(str);
                    break;
                } catch (FormatException e) {
                    throw new IllegalArgumentException(e);
                }
            case 8:
                try {
                    if (UPCEANReader.a(str)) {
                        break;
                    } else {
                        throw new IllegalArgumentException("Contents do not pass checksum");
                    }
                } catch (FormatException unused) {
                    throw new IllegalArgumentException("Illegal contents");
                }
            default:
                throw new IllegalArgumentException("Requested contents should be 8 digits long, but got " + length);
        }
        boolean[] zArr = new boolean[67];
        int appendPattern = appendPattern(zArr, 0, UPCEANReader.b, true) + 0;
        for (int i = 0; i <= 3; i++) {
            appendPattern += appendPattern(zArr, appendPattern, UPCEANReader.e[Character.digit(str.charAt(i), 10)], false);
        }
        int appendPattern2 = appendPattern + appendPattern(zArr, appendPattern, UPCEANReader.c, false);
        for (int i2 = 4; i2 <= 7; i2++) {
            appendPattern2 += appendPattern(zArr, appendPattern2, UPCEANReader.e[Character.digit(str.charAt(i2), 10)], true);
        }
        appendPattern(zArr, appendPattern2, UPCEANReader.b, true);
        return zArr;
    }
}
