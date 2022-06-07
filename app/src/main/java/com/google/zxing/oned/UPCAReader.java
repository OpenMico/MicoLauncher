package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import java.util.Map;

/* loaded from: classes2.dex */
public final class UPCAReader extends UPCEANReader {
    private final UPCEANReader a = new EAN13Reader();

    @Override // com.google.zxing.oned.UPCEANReader
    public Result decodeRow(int i, BitArray bitArray, int[] iArr, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException, ChecksumException {
        return a(this.a.decodeRow(i, bitArray, iArr, map));
    }

    @Override // com.google.zxing.oned.UPCEANReader, com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException, ChecksumException {
        return a(this.a.decodeRow(i, bitArray, map));
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return a(this.a.decode(binaryBitmap));
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        return a(this.a.decode(binaryBitmap, map));
    }

    @Override // com.google.zxing.oned.UPCEANReader
    BarcodeFormat a() {
        return BarcodeFormat.UPC_A;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.zxing.oned.UPCEANReader
    public int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder sb) throws NotFoundException {
        return this.a.decodeMiddle(bitArray, iArr, sb);
    }

    private static Result a(Result result) throws FormatException {
        String text = result.getText();
        if (text.charAt(0) == '0') {
            return new Result(text.substring(1), null, result.getResultPoints(), BarcodeFormat.UPC_A);
        }
        throw FormatException.getFormatInstance();
    }
}
