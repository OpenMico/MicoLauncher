package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

@Deprecated
/* loaded from: classes.dex */
public class ExceptionCatchingInputStream extends InputStream {
    private static final Queue<ExceptionCatchingInputStream> a = Util.createQueue(0);
    private InputStream b;
    private IOException c;

    @NonNull
    public static ExceptionCatchingInputStream obtain(@NonNull InputStream inputStream) {
        ExceptionCatchingInputStream poll;
        synchronized (a) {
            poll = a.poll();
        }
        if (poll == null) {
            poll = new ExceptionCatchingInputStream();
        }
        poll.a(inputStream);
        return poll;
    }

    ExceptionCatchingInputStream() {
    }

    void a(@NonNull InputStream inputStream) {
        this.b = inputStream;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.b.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.b.close();
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        this.b.mark(i);
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.b.markSupported();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) {
        try {
            return this.b.read(bArr);
        } catch (IOException e) {
            this.c = e;
            return -1;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) {
        try {
            return this.b.read(bArr, i, i2);
        } catch (IOException e) {
            this.c = e;
            return -1;
        }
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        this.b.reset();
    }

    @Override // java.io.InputStream
    public long skip(long j) {
        try {
            return this.b.skip(j);
        } catch (IOException e) {
            this.c = e;
            return 0L;
        }
    }

    @Override // java.io.InputStream
    public int read() {
        try {
            return this.b.read();
        } catch (IOException e) {
            this.c = e;
            return -1;
        }
    }

    @Nullable
    public IOException getException() {
        return this.c;
    }

    public void release() {
        this.c = null;
        this.b = null;
        synchronized (a) {
            a.offer(this);
        }
    }
}
