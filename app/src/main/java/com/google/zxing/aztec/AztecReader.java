package com.google.zxing.aztec;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;

/* loaded from: classes2.dex */
public final class AztecReader implements Reader {
    @Override // com.google.zxing.Reader
    public void reset() {
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return decode(binaryBitmap, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005f A[LOOP:0: B:29:0x005d->B:30:0x005f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0090  */
    @Override // com.google.zxing.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.Result decode(com.google.zxing.BinaryBitmap r13, java.util.Map<com.google.zxing.DecodeHintType, ?> r14) throws com.google.zxing.NotFoundException, com.google.zxing.FormatException {
        /*
            r12 = this;
            com.google.zxing.aztec.detector.Detector r0 = new com.google.zxing.aztec.detector.Detector
            com.google.zxing.common.BitMatrix r13 = r13.getBlackMatrix()
            r0.<init>(r13)
            r13 = 0
            r1 = 0
            com.google.zxing.aztec.AztecDetectorResult r2 = r0.detect(r13)     // Catch: NotFoundException -> 0x002b, FormatException -> 0x0025
            com.google.zxing.ResultPoint[] r3 = r2.getPoints()     // Catch: NotFoundException -> 0x002b, FormatException -> 0x0025
            com.google.zxing.aztec.decoder.Decoder r4 = new com.google.zxing.aztec.decoder.Decoder     // Catch: NotFoundException -> 0x0023, FormatException -> 0x0021
            r4.<init>()     // Catch: NotFoundException -> 0x0023, FormatException -> 0x0021
            com.google.zxing.common.DecoderResult r2 = r4.decode(r2)     // Catch: NotFoundException -> 0x0023, FormatException -> 0x0021
            r4 = r3
            r3 = r1
            r1 = r2
            r2 = r3
            goto L_0x002f
        L_0x0021:
            r2 = move-exception
            goto L_0x0027
        L_0x0023:
            r2 = move-exception
            goto L_0x002d
        L_0x0025:
            r2 = move-exception
            r3 = r1
        L_0x0027:
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x002f
        L_0x002b:
            r2 = move-exception
            r3 = r1
        L_0x002d:
            r4 = r3
            r3 = r1
        L_0x002f:
            if (r1 != 0) goto L_0x004f
            r1 = 1
            com.google.zxing.aztec.AztecDetectorResult r0 = r0.detect(r1)     // Catch: NotFoundException -> 0x0047, FormatException -> 0x0045
            com.google.zxing.ResultPoint[] r4 = r0.getPoints()     // Catch: NotFoundException -> 0x0047, FormatException -> 0x0045
            com.google.zxing.aztec.decoder.Decoder r1 = new com.google.zxing.aztec.decoder.Decoder     // Catch: NotFoundException -> 0x0047, FormatException -> 0x0045
            r1.<init>()     // Catch: NotFoundException -> 0x0047, FormatException -> 0x0045
            com.google.zxing.common.DecoderResult r1 = r1.decode(r0)     // Catch: NotFoundException -> 0x0047, FormatException -> 0x0045
            r8 = r4
            goto L_0x0050
        L_0x0045:
            r13 = move-exception
            goto L_0x0048
        L_0x0047:
            r13 = move-exception
        L_0x0048:
            if (r2 != 0) goto L_0x004e
            if (r3 == 0) goto L_0x004d
            throw r3
        L_0x004d:
            throw r13
        L_0x004e:
            throw r2
        L_0x004f:
            r8 = r4
        L_0x0050:
            if (r14 == 0) goto L_0x0067
            com.google.zxing.DecodeHintType r0 = com.google.zxing.DecodeHintType.NEED_RESULT_POINT_CALLBACK
            java.lang.Object r14 = r14.get(r0)
            com.google.zxing.ResultPointCallback r14 = (com.google.zxing.ResultPointCallback) r14
            if (r14 == 0) goto L_0x0067
            int r0 = r8.length
        L_0x005d:
            if (r13 >= r0) goto L_0x0067
            r2 = r8[r13]
            r14.foundPossibleResultPoint(r2)
            int r13 = r13 + 1
            goto L_0x005d
        L_0x0067:
            com.google.zxing.Result r13 = new com.google.zxing.Result
            java.lang.String r5 = r1.getText()
            byte[] r6 = r1.getRawBytes()
            int r7 = r1.getNumBits()
            com.google.zxing.BarcodeFormat r9 = com.google.zxing.BarcodeFormat.AZTEC
            long r10 = java.lang.System.currentTimeMillis()
            r4 = r13
            r4.<init>(r5, r6, r7, r8, r9, r10)
            java.util.List r14 = r1.getByteSegments()
            if (r14 == 0) goto L_0x008a
            com.google.zxing.ResultMetadataType r0 = com.google.zxing.ResultMetadataType.BYTE_SEGMENTS
            r13.putMetadata(r0, r14)
        L_0x008a:
            java.lang.String r14 = r1.getECLevel()
            if (r14 == 0) goto L_0x0095
            com.google.zxing.ResultMetadataType r0 = com.google.zxing.ResultMetadataType.ERROR_CORRECTION_LEVEL
            r13.putMetadata(r0, r14)
        L_0x0095:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.aztec.AztecReader.decode(com.google.zxing.BinaryBitmap, java.util.Map):com.google.zxing.Result");
    }
}
