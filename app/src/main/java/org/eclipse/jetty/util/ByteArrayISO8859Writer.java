package org.eclipse.jetty.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/* loaded from: classes5.dex */
public class ByteArrayISO8859Writer extends Writer {
    private ByteArrayOutputStream2 _bout;
    private byte[] _buf;
    private boolean _fixed;
    private int _size;
    private OutputStreamWriter _writer;

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    public ByteArrayISO8859Writer() {
        this._bout = null;
        this._writer = null;
        this._fixed = false;
        this._buf = new byte[2048];
    }

    public ByteArrayISO8859Writer(int i) {
        this._bout = null;
        this._writer = null;
        this._fixed = false;
        this._buf = new byte[i];
    }

    public ByteArrayISO8859Writer(byte[] bArr) {
        this._bout = null;
        this._writer = null;
        this._fixed = false;
        this._buf = bArr;
        this._fixed = true;
    }

    public Object getLock() {
        return this.lock;
    }

    public int size() {
        return this._size;
    }

    public int capacity() {
        return this._buf.length;
    }

    public int spareCapacity() {
        return this._buf.length - this._size;
    }

    public void setLength(int i) {
        this._size = i;
    }

    public byte[] getBuf() {
        return this._buf;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(this._buf, 0, this._size);
    }

    public void write(char c) throws IOException {
        ensureSpareCapacity(1);
        if (c < 0 || c > 127) {
            writeEncoded(new char[]{c}, 0, 1);
            return;
        }
        byte[] bArr = this._buf;
        int i = this._size;
        this._size = i + 1;
        bArr[i] = (byte) c;
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws IOException {
        ensureSpareCapacity(cArr.length);
        for (int i = 0; i < cArr.length; i++) {
            char c = cArr[i];
            if (c < 0 || c > 127) {
                writeEncoded(cArr, i, cArr.length - i);
                return;
            }
            byte[] bArr = this._buf;
            int i2 = this._size;
            this._size = i2 + 1;
            bArr[i2] = (byte) c;
        }
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        ensureSpareCapacity(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = i + i3;
            char c = cArr[i4];
            if (c < 0 || c > 127) {
                writeEncoded(cArr, i4, i2 - i3);
                return;
            }
            byte[] bArr = this._buf;
            int i5 = this._size;
            this._size = i5 + 1;
            bArr[i5] = (byte) c;
        }
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        if (str == null) {
            write("null", 0, 4);
            return;
        }
        int length = str.length();
        ensureSpareCapacity(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt < 0 || charAt > 127) {
                writeEncoded(str.toCharArray(), i, length - i);
                return;
            }
            byte[] bArr = this._buf;
            int i2 = this._size;
            this._size = i2 + 1;
            bArr[i2] = (byte) charAt;
        }
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) throws IOException {
        ensureSpareCapacity(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = i + i3;
            char charAt = str.charAt(i4);
            if (charAt < 0 || charAt > 127) {
                writeEncoded(str.toCharArray(), i4, i2 - i3);
                return;
            }
            byte[] bArr = this._buf;
            int i5 = this._size;
            this._size = i5 + 1;
            bArr[i5] = (byte) charAt;
        }
    }

    private void writeEncoded(char[] cArr, int i, int i2) throws IOException {
        ByteArrayOutputStream2 byteArrayOutputStream2 = this._bout;
        if (byteArrayOutputStream2 == null) {
            this._bout = new ByteArrayOutputStream2(i2 * 2);
            this._writer = new OutputStreamWriter(this._bout, "ISO-8859-1");
        } else {
            byteArrayOutputStream2.reset();
        }
        this._writer.write(cArr, i, i2);
        this._writer.flush();
        ensureSpareCapacity(this._bout.getCount());
        System.arraycopy(this._bout.getBuf(), 0, this._buf, this._size, this._bout.getCount());
        this._size += this._bout.getCount();
    }

    public void resetWriter() {
        this._size = 0;
    }

    public void destroy() {
        this._buf = null;
    }

    public void ensureSpareCapacity(int i) throws IOException {
        int i2 = this._size;
        int i3 = i2 + i;
        byte[] bArr = this._buf;
        if (i3 <= bArr.length) {
            return;
        }
        if (!this._fixed) {
            byte[] bArr2 = new byte[((bArr.length + i) * 4) / 3];
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            this._buf = bArr2;
            return;
        }
        throw new IOException("Buffer overflow: " + this._buf.length);
    }

    public byte[] getByteArray() {
        int i = this._size;
        byte[] bArr = new byte[i];
        System.arraycopy(this._buf, 0, bArr, 0, i);
        return bArr;
    }
}
