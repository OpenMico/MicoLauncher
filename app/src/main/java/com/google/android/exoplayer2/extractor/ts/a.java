package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.util.Assertions;
import java.util.Arrays;

/* compiled from: NalUnitTargetBuffer.java */
/* loaded from: classes2.dex */
final class a {
    public byte[] a;
    public int b;
    private final int c;
    private boolean d;
    private boolean e;

    public a(int i, int i2) {
        this.c = i;
        this.a = new byte[i2 + 3];
        this.a[2] = 1;
    }

    public void a() {
        this.d = false;
        this.e = false;
    }

    public boolean b() {
        return this.e;
    }

    public void a(int i) {
        boolean z = true;
        Assertions.checkState(!this.d);
        if (i != this.c) {
            z = false;
        }
        this.d = z;
        if (this.d) {
            this.b = 3;
            this.e = false;
        }
    }

    public void a(byte[] bArr, int i, int i2) {
        if (this.d) {
            int i3 = i2 - i;
            byte[] bArr2 = this.a;
            int length = bArr2.length;
            int i4 = this.b;
            if (length < i4 + i3) {
                this.a = Arrays.copyOf(bArr2, (i4 + i3) * 2);
            }
            System.arraycopy(bArr, i, this.a, this.b, i3);
            this.b += i3;
        }
    }

    public boolean b(int i) {
        if (!this.d) {
            return false;
        }
        this.b -= i;
        this.d = false;
        this.e = true;
        return true;
    }
}
