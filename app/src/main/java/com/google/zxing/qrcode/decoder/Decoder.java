package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Map;

/* loaded from: classes2.dex */
public final class Decoder {
    private final ReedSolomonDecoder a = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

    public DecoderResult decode(boolean[][] zArr) throws ChecksumException, FormatException {
        return decode(zArr, (Map<DecodeHintType, ?>) null);
    }

    public DecoderResult decode(boolean[][] zArr, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        return decode(BitMatrix.parse(zArr), map);
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return decode(bitMatrix, (Map<DecodeHintType, ?>) null);
    }

    public DecoderResult decode(BitMatrix bitMatrix, Map<DecodeHintType, ?> map) throws FormatException, ChecksumException {
        ChecksumException e;
        a aVar = new a(bitMatrix);
        FormatException e2 = null;
        try {
            return a(aVar, map);
        } catch (ChecksumException e3) {
            e = e3;
            try {
                aVar.d();
                aVar.a(true);
                aVar.b();
                aVar.a();
                aVar.e();
                DecoderResult a = a(aVar, map);
                a.setOther(new QRCodeDecoderMetaData(true));
                return a;
            } catch (ChecksumException | FormatException e4) {
                if (e2 != null) {
                    throw e2;
                } else if (e != null) {
                    throw e;
                } else {
                    throw e4;
                }
            }
        } catch (FormatException e5) {
            e2 = e5;
            e = null;
            aVar.d();
            aVar.a(true);
            aVar.b();
            aVar.a();
            aVar.e();
            DecoderResult a2 = a(aVar, map);
            a2.setOther(new QRCodeDecoderMetaData(true));
            return a2;
        }
    }

    private DecoderResult a(a aVar, Map<DecodeHintType, ?> map) throws FormatException, ChecksumException {
        Version b = aVar.b();
        ErrorCorrectionLevel a = aVar.a().a();
        b[] a2 = b.a(aVar.c(), b, a);
        int i = 0;
        for (b bVar : a2) {
            i += bVar.a();
        }
        byte[] bArr = new byte[i];
        int length = a2.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            b bVar2 = a2[i2];
            byte[] b2 = bVar2.b();
            int a3 = bVar2.a();
            a(b2, a3);
            for (int i4 = 0; i4 < a3; i4++) {
                i3++;
                bArr[i3] = b2[i4];
            }
            i2++;
            i3 = i3;
        }
        return d.a(bArr, b, a, map);
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
