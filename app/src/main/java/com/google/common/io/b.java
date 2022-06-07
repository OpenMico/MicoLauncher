package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

/* compiled from: CharSequenceReader.java */
@GwtIncompatible
/* loaded from: classes2.dex */
final class b extends Reader {
    private CharSequence a;
    private int b;
    private int c;

    @Override // java.io.Reader
    public boolean markSupported() {
        return true;
    }

    public b(CharSequence charSequence) {
        this.a = (CharSequence) Preconditions.checkNotNull(charSequence);
    }

    private void a() throws IOException {
        if (this.a == null) {
            throw new IOException("reader closed");
        }
    }

    private boolean b() {
        return c() > 0;
    }

    private int c() {
        return this.a.length() - this.b;
    }

    @Override // java.io.Reader, java.lang.Readable
    public synchronized int read(CharBuffer charBuffer) throws IOException {
        Preconditions.checkNotNull(charBuffer);
        a();
        if (!b()) {
            return -1;
        }
        int min = Math.min(charBuffer.remaining(), c());
        for (int i = 0; i < min; i++) {
            CharSequence charSequence = this.a;
            int i2 = this.b;
            this.b = i2 + 1;
            charBuffer.put(charSequence.charAt(i2));
        }
        return min;
    }

    @Override // java.io.Reader
    public synchronized int read() throws IOException {
        char c;
        a();
        if (b()) {
            CharSequence charSequence = this.a;
            int i = this.b;
            this.b = i + 1;
            c = charSequence.charAt(i);
        } else {
            c = 65535;
        }
        return c;
    }

    @Override // java.io.Reader
    public synchronized int read(char[] cArr, int i, int i2) throws IOException {
        Preconditions.checkPositionIndexes(i, i + i2, cArr.length);
        a();
        if (!b()) {
            return -1;
        }
        int min = Math.min(i2, c());
        for (int i3 = 0; i3 < min; i3++) {
            CharSequence charSequence = this.a;
            int i4 = this.b;
            this.b = i4 + 1;
            cArr[i + i3] = charSequence.charAt(i4);
        }
        return min;
    }

    @Override // java.io.Reader
    public synchronized long skip(long j) throws IOException {
        int min;
        Preconditions.checkArgument(j >= 0, "n (%s) may not be negative", j);
        a();
        min = (int) Math.min(c(), j);
        this.b += min;
        return min;
    }

    @Override // java.io.Reader
    public synchronized boolean ready() throws IOException {
        a();
        return true;
    }

    @Override // java.io.Reader
    public synchronized void mark(int i) throws IOException {
        Preconditions.checkArgument(i >= 0, "readAheadLimit (%s) may not be negative", i);
        a();
        this.c = this.b;
    }

    @Override // java.io.Reader
    public synchronized void reset() throws IOException {
        a();
        this.b = this.c;
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        this.a = null;
    }
}
