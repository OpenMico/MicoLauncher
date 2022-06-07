package com.fasterxml.jackson.core.io;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class MergedStream extends InputStream {
    private final IOContext a;
    private final InputStream b;
    private byte[] c;
    private int d;
    private final int e;

    public MergedStream(IOContext iOContext, InputStream inputStream, byte[] bArr, int i, int i2) {
        this.a = iOContext;
        this.b = inputStream;
        this.c = bArr;
        this.d = i;
        this.e = i2;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.c != null) {
            return this.e - this.d;
        }
        return this.b.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        a();
        this.b.close();
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        if (this.c == null) {
            this.b.mark(i);
        }
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.c == null && this.b.markSupported();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        byte[] bArr = this.c;
        if (bArr == null) {
            return this.b.read();
        }
        int i = this.d;
        this.d = i + 1;
        int i2 = bArr[i] & 255;
        if (this.d >= this.e) {
            a();
        }
        return i2;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.c == null) {
            return this.b.read(bArr, i, i2);
        }
        int i3 = this.e - this.d;
        if (i2 > i3) {
            i2 = i3;
        }
        System.arraycopy(this.c, this.d, bArr, i, i2);
        this.d += i2;
        if (this.d >= this.e) {
            a();
        }
        return i2;
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        if (this.c == null) {
            this.b.reset();
        }
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        long j2;
        if (this.c != null) {
            int i = this.e;
            int i2 = this.d;
            long j3 = i - i2;
            if (j3 > j) {
                this.d = i2 + ((int) j);
                return j;
            }
            a();
            j2 = j3 + 0;
            j -= j3;
        } else {
            j2 = 0;
        }
        return j > 0 ? j2 + this.b.skip(j) : j2;
    }

    private void a() {
        byte[] bArr = this.c;
        if (bArr != null) {
            this.c = null;
            IOContext iOContext = this.a;
            if (iOContext != null) {
                iOContext.releaseReadIOBuffer(bArr);
            }
        }
    }
}
