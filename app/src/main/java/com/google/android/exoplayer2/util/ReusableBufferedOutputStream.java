package com.google.android.exoplayer2.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public final class ReusableBufferedOutputStream extends BufferedOutputStream {
    private boolean a;

    public ReusableBufferedOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public ReusableBufferedOutputStream(OutputStream outputStream, int i) {
        super(outputStream, i);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Throwable th;
        this.a = true;
        try {
            flush();
            th = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            this.out.close();
        } catch (Throwable th3) {
            th = th3;
            if (th == null) {
            }
        }
        if (th != null) {
            Util.sneakyThrow(th);
        }
    }

    public void reset(OutputStream outputStream) {
        Assertions.checkState(this.a);
        this.out = outputStream;
        this.count = 0;
        this.a = false;
    }
}
