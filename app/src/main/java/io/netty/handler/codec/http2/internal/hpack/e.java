package io.netty.handler.codec.http2.internal.hpack;

import java.io.IOException;
import java.io.OutputStream;

/* compiled from: HuffmanEncoder.java */
/* loaded from: classes4.dex */
final class e {
    private final int[] a;
    private final byte[] b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(int[] iArr, byte[] bArr) {
        this.a = iArr;
        this.b = bArr;
    }

    public void a(OutputStream outputStream, byte[] bArr) throws IOException {
        a(outputStream, bArr, 0, bArr.length);
    }

    public void a(OutputStream outputStream, byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (outputStream == null) {
            throw new NullPointerException("out");
        } else if (bArr == null) {
            throw new NullPointerException("data");
        } else if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i > bArr.length || i3 > bArr.length) {
            throw new IndexOutOfBoundsException();
        } else if (i2 != 0) {
            long j = 0;
            int i4 = 0;
            byte b = 0;
            while (i4 < i2) {
                int i5 = 255 & bArr[i + i4];
                int i6 = this.a[i5];
                byte b2 = this.b[i5];
                j = (j << b2) | i6;
                int i7 = b + b2;
                while (i7 >= 8) {
                    i7 = (i7 == 1 ? 1 : 0) - 8;
                    outputStream.write((int) (j >> i7));
                }
                i4++;
                b = i7;
            }
            if (b > 0) {
                outputStream.write((int) ((j << (8 - b)) | (255 >>> b)));
            }
        }
    }

    public int a(byte[] bArr) {
        if (bArr != null) {
            long j = 0;
            for (byte b : bArr) {
                j += this.b[b & 255];
            }
            return (int) ((j + 7) >> 3);
        }
        throw new NullPointerException("data");
    }
}
