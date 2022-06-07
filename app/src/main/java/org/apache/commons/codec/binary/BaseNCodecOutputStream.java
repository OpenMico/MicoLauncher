package org.apache.commons.codec.binary;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.codec.binary.BaseNCodec;

/* loaded from: classes5.dex */
public class BaseNCodecOutputStream extends FilterOutputStream {
    private final boolean a;
    private final BaseNCodec b;
    private final byte[] c = new byte[1];
    private final BaseNCodec.a d = new BaseNCodec.a();

    public BaseNCodecOutputStream(OutputStream outputStream, BaseNCodec baseNCodec, boolean z) {
        super(outputStream);
        this.b = baseNCodec;
        this.a = z;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        byte[] bArr = this.c;
        bArr[0] = (byte) i;
        write(bArr, 0, 1);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (bArr == null) {
            throw new NullPointerException();
        } else if (i < 0 || i2 < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i > bArr.length || i + i2 > bArr.length) {
            throw new IndexOutOfBoundsException();
        } else if (i2 > 0) {
            if (this.a) {
                this.b.b(bArr, i, i2, this.d);
            } else {
                this.b.a(bArr, i, i2, this.d);
            }
            a(false);
        }
    }

    private void a(boolean z) throws IOException {
        byte[] bArr;
        int c;
        int b = this.b.b(this.d);
        if (b > 0 && (c = this.b.c((bArr = new byte[b]), 0, b, this.d)) > 0) {
            this.out.write(bArr, 0, c);
        }
        if (z) {
            this.out.flush();
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        a(true);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.a) {
            this.b.b(this.c, 0, -1, this.d);
        } else {
            this.b.a(this.c, 0, -1, this.d);
        }
        flush();
        this.out.close();
    }
}
