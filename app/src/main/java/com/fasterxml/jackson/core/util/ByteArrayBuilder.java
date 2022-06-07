package com.fasterxml.jackson.core.util;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes.dex */
public final class ByteArrayBuilder extends OutputStream {
    public static final byte[] NO_BYTES = new byte[0];
    private final BufferRecycler a;
    private final LinkedList<byte[]> b;
    private int c;
    private byte[] d;
    private int e;

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() {
    }

    public ByteArrayBuilder() {
        this((BufferRecycler) null);
    }

    public ByteArrayBuilder(BufferRecycler bufferRecycler) {
        this(bufferRecycler, 500);
    }

    public ByteArrayBuilder(int i) {
        this(null, i);
    }

    public ByteArrayBuilder(BufferRecycler bufferRecycler, int i) {
        this.b = new LinkedList<>();
        this.a = bufferRecycler;
        this.d = bufferRecycler == null ? new byte[i] : bufferRecycler.allocByteBuffer(2);
    }

    public void reset() {
        this.c = 0;
        this.e = 0;
        if (!this.b.isEmpty()) {
            this.b.clear();
        }
    }

    public int size() {
        return this.c + this.e;
    }

    public void release() {
        byte[] bArr;
        reset();
        BufferRecycler bufferRecycler = this.a;
        if (bufferRecycler != null && (bArr = this.d) != null) {
            bufferRecycler.releaseByteBuffer(2, bArr);
            this.d = null;
        }
    }

    public void append(int i) {
        if (this.e >= this.d.length) {
            a();
        }
        byte[] bArr = this.d;
        int i2 = this.e;
        this.e = i2 + 1;
        bArr[i2] = (byte) i;
    }

    public void appendTwoBytes(int i) {
        int i2 = this.e;
        int i3 = i2 + 1;
        byte[] bArr = this.d;
        if (i3 < bArr.length) {
            this.e = i2 + 1;
            bArr[i2] = (byte) (i >> 8);
            int i4 = this.e;
            this.e = i4 + 1;
            bArr[i4] = (byte) i;
            return;
        }
        append(i >> 8);
        append(i);
    }

    public void appendThreeBytes(int i) {
        int i2 = this.e;
        int i3 = i2 + 2;
        byte[] bArr = this.d;
        if (i3 < bArr.length) {
            this.e = i2 + 1;
            bArr[i2] = (byte) (i >> 16);
            int i4 = this.e;
            this.e = i4 + 1;
            bArr[i4] = (byte) (i >> 8);
            int i5 = this.e;
            this.e = i5 + 1;
            bArr[i5] = (byte) i;
            return;
        }
        append(i >> 16);
        append(i >> 8);
        append(i);
    }

    public void appendFourBytes(int i) {
        int i2 = this.e;
        int i3 = i2 + 3;
        byte[] bArr = this.d;
        if (i3 < bArr.length) {
            this.e = i2 + 1;
            bArr[i2] = (byte) (i >> 24);
            int i4 = this.e;
            this.e = i4 + 1;
            bArr[i4] = (byte) (i >> 16);
            int i5 = this.e;
            this.e = i5 + 1;
            bArr[i5] = (byte) (i >> 8);
            int i6 = this.e;
            this.e = i6 + 1;
            bArr[i6] = (byte) i;
            return;
        }
        append(i >> 24);
        append(i >> 16);
        append(i >> 8);
        append(i);
    }

    public byte[] toByteArray() {
        int i = this.c + this.e;
        if (i == 0) {
            return NO_BYTES;
        }
        byte[] bArr = new byte[i];
        Iterator<byte[]> it = this.b.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            byte[] next = it.next();
            int length = next.length;
            System.arraycopy(next, 0, bArr, i2, length);
            i2 += length;
        }
        System.arraycopy(this.d, 0, bArr, i2, this.e);
        int i3 = i2 + this.e;
        if (i3 == i) {
            if (!this.b.isEmpty()) {
                reset();
            }
            return bArr;
        }
        throw new RuntimeException("Internal error: total len assumed to be " + i + ", copied " + i3 + " bytes");
    }

    public byte[] resetAndGetFirstSegment() {
        reset();
        return this.d;
    }

    public byte[] finishCurrentSegment() {
        a();
        return this.d;
    }

    public byte[] completeAndCoalesce(int i) {
        this.e = i;
        return toByteArray();
    }

    public byte[] getCurrentSegment() {
        return this.d;
    }

    public void setCurrentSegmentLength(int i) {
        this.e = i;
    }

    public int getCurrentSegmentLength() {
        return this.e;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) {
        while (true) {
            int min = Math.min(this.d.length - this.e, i2);
            if (min > 0) {
                System.arraycopy(bArr, i, this.d, this.e, min);
                i += min;
                this.e += min;
                i2 -= min;
            }
            if (i2 > 0) {
                a();
            } else {
                return;
            }
        }
    }

    @Override // java.io.OutputStream
    public void write(int i) {
        append(i);
    }

    private void a() {
        int length = this.c + this.d.length;
        if (length >= 0) {
            this.c = length;
            int max = Math.max(this.c >> 1, 1000);
            if (max > 262144) {
                max = 262144;
            }
            this.b.add(this.d);
            this.d = new byte[max];
            this.e = 0;
            return;
        }
        throw new IllegalStateException("Maximum Java array size (2GB) exceeded by `ByteArrayBuilder`");
    }
}
