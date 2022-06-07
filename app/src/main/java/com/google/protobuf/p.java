package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: IterableByteBufferInputStream.java */
/* loaded from: classes2.dex */
public class p extends InputStream {
    private Iterator<ByteBuffer> a;
    private ByteBuffer b;
    private int c = 0;
    private int d;
    private int e;
    private boolean f;
    private byte[] g;
    private int h;
    private long i;

    /* JADX INFO: Access modifiers changed from: package-private */
    public p(Iterable<ByteBuffer> iterable) {
        this.a = iterable.iterator();
        for (ByteBuffer byteBuffer : iterable) {
            this.c++;
        }
        this.d = -1;
        if (!a()) {
            this.b = Internal.EMPTY_BYTE_BUFFER;
            this.d = 0;
            this.e = 0;
            this.i = 0L;
        }
    }

    private boolean a() {
        this.d++;
        if (!this.a.hasNext()) {
            return false;
        }
        this.b = this.a.next();
        this.e = this.b.position();
        if (this.b.hasArray()) {
            this.f = true;
            this.g = this.b.array();
            this.h = this.b.arrayOffset();
        } else {
            this.f = false;
            this.i = at.a(this.b);
            this.g = null;
        }
        return true;
    }

    private void a(int i) {
        this.e += i;
        if (this.e == this.b.limit()) {
            a();
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.d == this.c) {
            return -1;
        }
        if (this.f) {
            int i = this.g[this.e + this.h] & 255;
            a(1);
            return i;
        }
        int a = at.a(this.e + this.i) & 255;
        a(1);
        return a;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.d == this.c) {
            return -1;
        }
        int limit = this.b.limit() - this.e;
        if (i2 > limit) {
            i2 = limit;
        }
        if (this.f) {
            System.arraycopy(this.g, this.e + this.h, bArr, i, i2);
            a(i2);
        } else {
            int position = this.b.position();
            this.b.position(this.e);
            this.b.get(bArr, i, i2);
            this.b.position(position);
            a(i2);
        }
        return i2;
    }
}
