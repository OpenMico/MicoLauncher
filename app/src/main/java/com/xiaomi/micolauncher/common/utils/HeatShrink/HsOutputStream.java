package com.xiaomi.micolauncher.common.utils.HeatShrink;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes3.dex */
public class HsOutputStream extends FilterOutputStream {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private int e;
    private final byte[] g;
    private int h;
    private int f = 128;
    private final byte[] i = new byte[1];
    public final Result wr = new Result();

    public HsOutputStream(OutputStream outputStream, int i, int i2) {
        super(outputStream);
        this.g = new byte[2 << i];
        this.c = i;
        this.d = i2;
        this.a = 1 << this.c;
        this.b = 1 << this.d;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        byte[] bArr = this.i;
        bArr[0] = (byte) i;
        write(bArr);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (bArr == null) {
            throw new NullPointerException();
        } else if (i < 0 || i > bArr.length || i2 < 0 || (i3 = i + i2) > bArr.length || i3 < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i2 != 0) {
            this.wr.a(bArr, i, i2);
            while (this.wr.a < this.wr.c) {
                if (a(this.wr)) {
                    a(false);
                }
            }
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        a(true);
        super.flush();
    }

    private boolean a(Result result) {
        int i = this.a - this.h;
        if (i > 0) {
            int min = Math.min(i, result.b);
            System.arraycopy(result.d, result.a, this.g, this.h + this.a, min);
            result.a += min;
            result.b -= min;
            this.h += min;
        }
        return this.h == this.a;
    }

    private void a(boolean z) throws IOException {
        if (this.h > 0) {
            int i = 0;
            int i2 = ((this.c + 1) + this.d) / 8;
            while (true) {
                if (i > this.h - (z ? 1 : this.b)) {
                    break;
                }
                i = a(i, i2) + 1;
            }
            a(z, i);
        }
        if (z && this.f != 128) {
            a();
        }
    }

    private void a(boolean z, int i) {
        int i2;
        if (z || i > (i2 = this.h)) {
            this.h = 0;
            return;
        }
        int i3 = this.a;
        int i4 = i3 - i;
        byte[] bArr = this.g;
        System.arraycopy(bArr, i2 - i4, bArr, 0, i3 + i4);
        this.h = i4;
    }

    private int a(int i, int i2) throws IOException {
        int min = Math.min(this.b, this.h - i);
        int i3 = this.a;
        int i4 = i3 + i;
        int i5 = i4 - i3;
        int i6 = i4 - 1;
        int i7 = 0;
        int i8 = 0;
        while (true) {
            if (i6 < i5) {
                i6 = i8;
                break;
            }
            byte[] bArr = this.g;
            if (bArr[i6 + i7] == bArr[i4 + i7] && bArr[i6] == bArr[i4]) {
                int i9 = 1;
                while (i9 < min) {
                    byte[] bArr2 = this.g;
                    if (bArr2[i6 + i9] != bArr2[i4 + i9]) {
                        break;
                    }
                    i9++;
                }
                if (i9 <= i7) {
                    continue;
                } else if (i9 == min) {
                    i7 = i9;
                    break;
                } else {
                    i8 = i6;
                    i7 = i9;
                }
            }
            i6--;
        }
        if (i7 > i2) {
            b(i4 - i6, i7);
            return i + (i7 - 1);
        }
        a(this.g[this.a + i]);
        return i;
    }

    private void a(byte b) throws IOException {
        c(1, 1);
        c(8, b);
    }

    private void b(int i, int i2) throws IOException {
        c(1, 0);
        c(this.c, i - 1);
        c(this.d, i2 - 1);
    }

    private void c(int i, int i2) throws IOException {
        if (i == 8 && this.f == 128) {
            this.e = i2 & 255;
            a();
            return;
        }
        for (int i3 = i - 1; i3 >= 0; i3--) {
            if (((1 << i3) & i2) != 0) {
                this.e |= this.f;
            }
            int i4 = this.f >> 1;
            this.f = i4;
            if (i4 == 0) {
                a();
            }
        }
    }

    private void a() throws IOException {
        this.out.write(this.e);
        this.f = 128;
        this.e = 0;
    }

    public void clear() {
        this.f = 128;
        this.e = 0;
        this.h = 0;
    }
}
