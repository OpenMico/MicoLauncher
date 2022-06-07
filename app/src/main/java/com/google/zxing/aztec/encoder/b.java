package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import kotlin.text.Typography;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SimpleToken.java */
/* loaded from: classes2.dex */
public final class b extends d {
    private final short b;
    private final short c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(d dVar, int i, int i2) {
        super(dVar);
        this.b = (short) i;
        this.c = (short) i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.zxing.aztec.encoder.d
    public void a(BitArray bitArray, byte[] bArr) {
        bitArray.appendBits(this.b, this.c);
    }

    public String toString() {
        short s = this.b;
        short s2 = this.c;
        int i = (s & ((1 << s2) - 1)) | (1 << s2);
        return "<" + Integer.toBinaryString(i | (1 << this.c)).substring(1) + Typography.greater;
    }
}
