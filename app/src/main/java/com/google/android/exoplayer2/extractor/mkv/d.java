package com.google.android.exoplayer2.extractor.mkv;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import java.io.IOException;

/* compiled from: VarintReader.java */
/* loaded from: classes2.dex */
final class d {
    private static final long[] a = {128, 64, 32, 16, 8, 4, 2, 1};
    private final byte[] b = new byte[8];
    private int c;
    private int d;

    public void a() {
        this.c = 0;
        this.d = 0;
    }

    public long a(ExtractorInput extractorInput, boolean z, boolean z2, int i) throws IOException {
        if (this.c == 0) {
            if (!extractorInput.readFully(this.b, 0, 1, z)) {
                return -1L;
            }
            this.d = a(this.b[0] & 255);
            if (this.d != -1) {
                this.c = 1;
            } else {
                throw new IllegalStateException("No valid varint length mask found");
            }
        }
        int i2 = this.d;
        if (i2 > i) {
            this.c = 0;
            return -2L;
        }
        if (i2 != 1) {
            extractorInput.readFully(this.b, 1, i2 - 1);
        }
        this.c = 0;
        return a(this.b, this.d, z2);
    }

    public int b() {
        return this.d;
    }

    public static int a(int i) {
        int i2 = 0;
        while (true) {
            long[] jArr = a;
            if (i2 >= jArr.length) {
                return -1;
            }
            if ((jArr[i2] & i) != 0) {
                return i2 + 1;
            }
            i2++;
        }
    }

    public static long a(byte[] bArr, int i, boolean z) {
        long j = bArr[0] & 255;
        if (z) {
            j &= ~a[i - 1];
        }
        for (int i2 = 1; i2 < i; i2++) {
            j = (j << 8) | (bArr[i2] & 255);
        }
        return j;
    }
}
