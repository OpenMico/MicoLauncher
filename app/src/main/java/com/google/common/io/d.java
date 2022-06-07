package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: MultiInputStream.java */
@GwtIncompatible
/* loaded from: classes2.dex */
final class d extends InputStream {
    private Iterator<? extends ByteSource> a;
    @NullableDecl
    private InputStream b;

    @Override // java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    public d(Iterator<? extends ByteSource> it) throws IOException {
        this.a = (Iterator) Preconditions.checkNotNull(it);
        a();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        InputStream inputStream = this.b;
        if (inputStream != null) {
            try {
                inputStream.close();
            } finally {
                this.b = null;
            }
        }
    }

    private void a() throws IOException {
        close();
        if (this.a.hasNext()) {
            this.b = ((ByteSource) this.a.next()).openStream();
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        InputStream inputStream = this.b;
        if (inputStream == null) {
            return 0;
        }
        return inputStream.available();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        while (true) {
            InputStream inputStream = this.b;
            if (inputStream == null) {
                return -1;
            }
            int read = inputStream.read();
            if (read != -1) {
                return read;
            }
            a();
        }
    }

    @Override // java.io.InputStream
    public int read(@NullableDecl byte[] bArr, int i, int i2) throws IOException {
        while (true) {
            InputStream inputStream = this.b;
            if (inputStream == null) {
                return -1;
            }
            int read = inputStream.read(bArr, i, i2);
            if (read != -1) {
                return read;
            }
            a();
        }
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        InputStream inputStream = this.b;
        if (inputStream == null || j <= 0) {
            return 0L;
        }
        long skip = inputStream.skip(j);
        if (skip != 0) {
            return skip;
        }
        if (read() == -1) {
            return 0L;
        }
        return this.b.skip(j - 1) + 1;
    }
}
