package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public final class DataSourceInputStream extends InputStream {
    private final DataSource a;
    private final DataSpec b;
    private long f;
    private boolean d = false;
    private boolean e = false;
    private final byte[] c = new byte[1];

    public DataSourceInputStream(DataSource dataSource, DataSpec dataSpec) {
        this.a = dataSource;
        this.b = dataSpec;
    }

    public long bytesRead() {
        return this.f;
    }

    public void open() throws IOException {
        a();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.c) == -1) {
            return -1;
        }
        return this.c[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        Assertions.checkState(!this.e);
        a();
        int read = this.a.read(bArr, i, i2);
        if (read == -1) {
            return -1;
        }
        this.f += read;
        return read;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.e) {
            this.a.close();
            this.e = true;
        }
    }

    private void a() throws IOException {
        if (!this.d) {
            this.a.open(this.b);
            this.d = true;
        }
    }
}
