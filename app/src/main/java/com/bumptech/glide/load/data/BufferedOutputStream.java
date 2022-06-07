package com.bumptech.glide.load.data;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public final class BufferedOutputStream extends OutputStream {
    @NonNull
    private final OutputStream a;
    private byte[] b;
    private ArrayPool c;
    private int d;

    public BufferedOutputStream(@NonNull OutputStream outputStream, @NonNull ArrayPool arrayPool) {
        this(outputStream, arrayPool, 65536);
    }

    @VisibleForTesting
    BufferedOutputStream(@NonNull OutputStream outputStream, ArrayPool arrayPool, int i) {
        this.a = outputStream;
        this.c = arrayPool;
        this.b = (byte[]) arrayPool.get(i, byte[].class);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        byte[] bArr = this.b;
        int i2 = this.d;
        this.d = i2 + 1;
        bArr[i2] = (byte) i;
        b();
    }

    @Override // java.io.OutputStream
    public void write(@NonNull byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(@NonNull byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        do {
            int i4 = i2 - i3;
            int i5 = i + i3;
            if (this.d != 0 || i4 < this.b.length) {
                int min = Math.min(i4, this.b.length - this.d);
                System.arraycopy(bArr, i5, this.b, this.d, min);
                this.d += min;
                i3 += min;
                b();
            } else {
                this.a.write(bArr, i5, i4);
                return;
            }
        } while (i3 < i2);
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        a();
        this.a.flush();
    }

    private void a() throws IOException {
        int i = this.d;
        if (i > 0) {
            this.a.write(this.b, 0, i);
            this.d = 0;
        }
    }

    private void b() throws IOException {
        if (this.d == this.b.length) {
            a();
        }
    }

    /* JADX WARN: Finally extract failed */
    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            flush();
            this.a.close();
            c();
        } catch (Throwable th) {
            this.a.close();
            throw th;
        }
    }

    private void c() {
        byte[] bArr = this.b;
        if (bArr != null) {
            this.c.put(bArr);
            this.b = null;
        }
    }
}
