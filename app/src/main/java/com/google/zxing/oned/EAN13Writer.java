package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* loaded from: classes2.dex */
public final class EAN13Writer extends UPCEANWriter {
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.EAN_13) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode EAN_13, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int length = str.length();
        switch (length) {
            case 12:
                try {
                    str = str + UPCEANReader.b(str);
                    break;
                } catch (FormatException e) {
                    throw new IllegalArgumentException(e);
                }
            case 13:
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
                throw new IllegalArgumentException("Requested contents should be 12 or 13 digits long, but got " + length);
        }
        int i = EAN13Reader.a[Character.digit(str.charAt(0), 10)];
        boolean[] zArr = new boolean[95];
        int appendPattern = appendPattern(zArr, 0, UPCEANReader.b, true) + 0;
        for (int i2 = 1; i2 <= 6; i2++) {
            int digit = Character.digit(str.charAt(i2), 10);
            if (((i >> (6 - i2)) & 1) == 1) {
                digit += 10;
            }
            appendPattern += appendPattern(zArr, appendPattern, UPCEANReader.f[digit], false);
        }
        int appendPattern2 = appendPattern + appendPattern(zArr, appendPattern, UPCEANReader.c, false);
        for (int i3 = 7; i3 <= 12; i3++) {
            appendPattern2 += appendPattern(zArr, appendPattern2, UPCEANReader.e[Character.digit(str.charAt(i3), 10)], true);
        }
        appendPattern(zArr, appendPattern2, UPCEANReader.b, true);
        return zArr;
    }
}
