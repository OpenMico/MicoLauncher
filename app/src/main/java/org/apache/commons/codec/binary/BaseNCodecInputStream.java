package org.apache.commons.codec.binary;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.codec.binary.BaseNCodec;

/* loaded from: classes5.dex */
public class BaseNCodecInputStream extends FilterInputStream {
    private final BaseNCodec a;
    private final boolean b;
    private final byte[] c = new byte[1];
    private final BaseNCodec.a d = new BaseNCodec.a();

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseNCodecInputStream(InputStream inputStream, BaseNCodec baseNCodec, boolean z) {
        super(inputStream);
        this.b = z;
        this.a = baseNCodec;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        return !this.d.f ? 1 : 0;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i) {
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int read = read(this.c, 0, 1);
        while (read == 0) {
            read = read(this.c, 0, 1);
        }
        if (read <= 0) {
            return -1;
        }
        byte b = this.c[0];
        return b < 0 ? b + 256 : b;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (bArr == null) {
            throw new NullPointerException();
        } else if (i < 0 || i2 < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i > bArr.length || i + i2 > bArr.length) {
            throw new IndexOutOfBoundsException();
        } else if (i2 == 0) {
            return 0;
        } else {
            int i3 = 0;
            while (i3 == 0) {
                if (!this.a.a(this.d)) {
                    byte[] bArr2 = new byte[this.b ? 4096 : 8192];
                    int read = this.in.read(bArr2);
                    if (this.b) {
                        this.a.b(bArr2, 0, read, this.d);
                    } else {
                        this.a.a(bArr2, 0, read, this.d);
                    }
                }
                i3 = this.a.c(bArr, i, i2, this.d);
            }
            return i3;
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        int read;
        if (j >= 0) {
            byte[] bArr = new byte[512];
            long j2 = j;
            while (j2 > 0 && (read = read(bArr, 0, (int) Math.min(bArr.length, j2))) != -1) {
                j2 -= read;
            }
            return j - j2;
        }
        throw new IllegalArgumentException("Negative skip length: " + j);
    }
}
