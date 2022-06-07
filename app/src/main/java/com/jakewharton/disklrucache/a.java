package com.jakewharton.disklrucache;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/* compiled from: StrictLineReader.java */
/* loaded from: classes2.dex */
class a implements Closeable {
    private final InputStream a;
    private final Charset b;
    private byte[] c;
    private int d;
    private int e;

    public a(InputStream inputStream, Charset charset) {
        this(inputStream, 8192, charset);
    }

    public a(InputStream inputStream, int i, Charset charset) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        } else if (i < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else if (charset.equals(Util.a)) {
            this.a = inputStream;
            this.b = charset;
            this.c = new byte[i];
        } else {
            throw new IllegalArgumentException("Unsupported encoding");
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.a) {
            if (this.c != null) {
                this.c = null;
                this.a.close();
            }
        }
    }

    public String a() throws IOException {
        int i;
        int i2;
        synchronized (this.a) {
            if (this.c != null) {
                if (this.d >= this.e) {
                    b();
                }
                for (int i3 = this.d; i3 != this.e; i3++) {
                    if (this.c[i3] == 10) {
                        if (i3 != this.d) {
                            i2 = i3 - 1;
                            if (this.c[i2] == 13) {
                                String str = new String(this.c, this.d, i2 - this.d, this.b.name());
                                this.d = i3 + 1;
                                return str;
                            }
                        }
                        i2 = i3;
                        String str2 = new String(this.c, this.d, i2 - this.d, this.b.name());
                        this.d = i3 + 1;
                        return str2;
                    }
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((this.e - this.d) + 80) { // from class: com.jakewharton.disklrucache.a.1
                    @Override // java.io.ByteArrayOutputStream
                    public String toString() {
                        try {
                            return new String(this.buf, 0, (this.count <= 0 || this.buf[this.count + (-1)] != 13) ? this.count : this.count - 1, a.this.b.name());
                        } catch (UnsupportedEncodingException e) {
                            throw new AssertionError(e);
                        }
                    }
                };
                loop1: while (true) {
                    byteArrayOutputStream.write(this.c, this.d, this.e - this.d);
                    this.e = -1;
                    b();
                    i = this.d;
                    while (i != this.e) {
                        if (this.c[i] == 10) {
                            break loop1;
                        }
                        i++;
                    }
                }
                if (i != this.d) {
                    byteArrayOutputStream.write(this.c, this.d, i - this.d);
                }
                this.d = i + 1;
                return byteArrayOutputStream.toString();
            }
            throw new IOException("LineReader is closed");
        }
    }

    private void b() throws IOException {
        InputStream inputStream = this.a;
        byte[] bArr = this.c;
        int read = inputStream.read(bArr, 0, bArr.length);
        if (read != -1) {
            this.d = 0;
            this.e = read;
            return;
        }
        throw new EOFException();
    }
}
