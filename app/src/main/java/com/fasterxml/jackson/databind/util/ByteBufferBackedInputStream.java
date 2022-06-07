package com.fasterxml.jackson.databind.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class ByteBufferBackedInputStream extends InputStream {
    protected final ByteBuffer _b;

    public ByteBufferBackedInputStream(ByteBuffer byteBuffer) {
        this._b = byteBuffer;
    }

    @Override // java.io.InputStream
    public int available() {
        return this._b.remaining();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this._b.hasRemaining()) {
            return this._b.get() & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (!this._b.hasRemaining()) {
            return -1;
        }
        int min = Math.min(i2, this._b.remaining());
        this._b.get(bArr, i, min);
        return min;
    }
}
