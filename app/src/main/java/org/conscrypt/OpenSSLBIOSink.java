package org.conscrypt;

import java.io.ByteArrayOutputStream;

/* loaded from: classes5.dex */
final class OpenSSLBIOSink {
    private final ByteArrayOutputStream buffer;
    private final long ctx;
    private int position;

    static OpenSSLBIOSink create() {
        return new OpenSSLBIOSink(new ByteArrayOutputStream());
    }

    private OpenSSLBIOSink(ByteArrayOutputStream byteArrayOutputStream) {
        this.ctx = NativeCrypto.create_BIO_OutputStream(byteArrayOutputStream);
        this.buffer = byteArrayOutputStream;
    }

    int available() {
        return this.buffer.size() - this.position;
    }

    void reset() {
        this.buffer.reset();
        this.position = 0;
    }

    long skip(long j) {
        int min = Math.min(available(), (int) j);
        this.position += min;
        if (this.position == this.buffer.size()) {
            reset();
        }
        return min;
    }

    long getContext() {
        return this.ctx;
    }

    byte[] toByteArray() {
        return this.buffer.toByteArray();
    }

    int position() {
        return this.position;
    }

    protected void finalize() throws Throwable {
        try {
            NativeCrypto.BIO_free_all(this.ctx);
        } finally {
            super.finalize();
        }
    }
}
