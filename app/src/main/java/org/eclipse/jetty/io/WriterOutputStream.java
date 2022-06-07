package org.eclipse.jetty.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/* loaded from: classes5.dex */
public class WriterOutputStream extends OutputStream {
    private final byte[] _buf;
    protected final String _encoding;
    protected final Writer _writer;

    public WriterOutputStream(Writer writer, String str) {
        this._buf = new byte[1];
        this._writer = writer;
        this._encoding = str;
    }

    public WriterOutputStream(Writer writer) {
        this._buf = new byte[1];
        this._writer = writer;
        this._encoding = null;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this._writer.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this._writer.flush();
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        String str = this._encoding;
        if (str == null) {
            this._writer.write(new String(bArr));
        } else {
            this._writer.write(new String(bArr, str));
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        String str = this._encoding;
        if (str == null) {
            this._writer.write(new String(bArr, i, i2));
        } else {
            this._writer.write(new String(bArr, i, i2, str));
        }
    }

    @Override // java.io.OutputStream
    public synchronized void write(int i) throws IOException {
        this._buf[0] = (byte) i;
        write(this._buf);
    }
}
