package org.eclipse.jetty.util;

import java.io.ByteArrayOutputStream;

/* loaded from: classes5.dex */
public class ByteArrayOutputStream2 extends ByteArrayOutputStream {
    public ByteArrayOutputStream2() {
    }

    public ByteArrayOutputStream2(int i) {
        super(i);
    }

    public byte[] getBuf() {
        return this.buf;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public void reset(int i) {
        reset();
        if (this.buf.length < i) {
            this.buf = new byte[i];
        }
    }

    public void writeUnchecked(int i) {
        byte[] bArr = this.buf;
        int i2 = this.count;
        this.count = i2 + 1;
        bArr[i2] = (byte) i;
    }
}
