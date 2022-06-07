package com.google.zxing.datamatrix.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

/* loaded from: classes2.dex */
public final class Decoder {
    private final ReedSolomonDecoder a = new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);

    public DecoderResult decode(boolean[][] zArr) throws FormatException, ChecksumException {
        return decode(BitMatrix.parse(zArr));
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws FormatException, ChecksumException {
        a aVar = new a(bitMatrix);
        b[] a = b.a(aVar.b(), aVar.a());
        int i = 0;
        for (b bVar : a) {
            i += bVar.a();
        }
        byte[] bArr = new byte[i];
        int length = a.length;
        for (int i2 = 0; i2 < length; i2++) {
            b bVar2 = a[i2];
            byte[] b = bVar2.b();
            int a2 = bVar2.a();
            a(b, a2);
            for (int i3 = 0; i3 < a2; i3++) {
                bArr[(i3 * length) + i2] = b[i3];
            }
        }
        return c.a(bArr);
    }

    private void a(byte[] bArr, int i) throws ChecksumException {
        int length = bArr.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = bArr[i2] & 255;
        }
        try {
            this.a.decode(iArr, bArr.length - i);
            for (int i3 = 0; i3 < i; i3++) {
                bArr[i3] = (byte) iArr[i3];
            }
        } catch (ReedSolomonException unused) {
            throw ChecksumException.getChecksumInstance();
        }
    }
}
