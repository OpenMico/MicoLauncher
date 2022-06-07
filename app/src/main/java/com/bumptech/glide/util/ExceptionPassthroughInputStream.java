package com.bumptech.glide.util;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

/* loaded from: classes.dex */
public final class ExceptionPassthroughInputStream extends InputStream {
    @GuardedBy("POOL")
    private static final Queue<ExceptionPassthroughInputStream> a = Util.createQueue(0);
    private InputStream b;
    private IOException c;

    @NonNull
    public static ExceptionPassthroughInputStream obtain(@NonNull InputStream inputStream) {
        ExceptionPassthroughInputStream poll;
        synchronized (a) {
            poll = a.poll();
        }
        if (poll == null) {
            poll = new ExceptionPassthroughInputStream();
        }
        poll.a(inputStream);
        return poll;
    }

    ExceptionPassthroughInputStream() {
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
    public int read() throws IOException {
        try {
            return this.b.read();
        } catch (IOException e) {
            this.c = e;
            throw e;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        try {
            return this.b.read(bArr);
        } catch (IOException e) {
            this.c = e;
            throw e;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        try {
            return this.b.read(bArr, i, i2);
        } catch (IOException e) {
            this.c = e;
            throw e;
        }
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        this.b.reset();
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        try {
            return this.b.skip(j);
        } catch (IOException e) {
            this.c = e;
            throw e;
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
