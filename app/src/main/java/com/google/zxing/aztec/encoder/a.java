package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import kotlin.text.Typography;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BinaryShiftToken.java */
/* loaded from: classes2.dex */
public final class a extends d {
    private final short b;
    private final short c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(d dVar, int i, int i2) {
        super(dVar);
        this.b = (short) i;
        this.c = (short) i2;
    }

    @Override // com.google.zxing.aztec.encoder.d
    public void a(BitArray bitArray, byte[] bArr) {
        int i = 0;
        while (true) {
            short s = this.c;
            if (i < s) {
                if (i == 0 || (i == 31 && s <= 62)) {
                    bitArray.appendBits(31, 5);
                    short s2 = this.c;
                    if (s2 > 62) {
                        bitArray.appendBits(s2 - 31, 16);
                    } else if (i == 0) {
                        bitArray.appendBits(Math.min((int) s2, 31), 5);
                    } else {
                        bitArray.appendBits(s2 - 31, 5);
                    }
                }
                bitArray.appendBits(bArr[this.b + i], 8);
                i++;
            } else {
                return;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("<");
        sb.append((int) this.b);
        sb.append("::");
        sb.append((this.b + this.c) - 1);
        sb.append(Typography.greater);
        return sb.toString();
    }
}
