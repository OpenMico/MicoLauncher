package com.google.zxing.datamatrix.encoder;

import java.util.Arrays;

/* loaded from: classes2.dex */
public class DefaultPlacement {
    private final CharSequence a;
    private final int b;
    private final int c;
    private final byte[] d;

    public DefaultPlacement(CharSequence charSequence, int i, int i2) {
        this.a = charSequence;
        this.c = i;
        this.b = i2;
        this.d = new byte[i * i2];
        Arrays.fill(this.d, (byte) -1);
    }

    public final boolean getBit(int i, int i2) {
        return this.d[(i2 * this.c) + i] == 1;
    }

    private void a(int i, int i2, boolean z) {
        this.d[(i2 * this.c) + i] = z ? (byte) 1 : (byte) 0;
    }

    private boolean a(int i, int i2) {
        return this.d[(i2 * this.c) + i] >= 0;
    }

    public final void place() {
        int i;
        int i2;
        int i3 = 0;
        int i4 = 0;
        int i5 = 4;
        while (true) {
            if (i5 == this.b && i3 == 0) {
                i4++;
                a(i4);
            }
            if (i5 == this.b - 2 && i3 == 0 && this.c % 4 != 0) {
                i4++;
                b(i4);
            }
            if (i5 == this.b - 2 && i3 == 0 && this.c % 8 == 4) {
                i4++;
                c(i4);
            }
            if (i5 == this.b + 4 && i3 == 2 && this.c % 8 == 0) {
                i4++;
                d(i4);
            }
            do {
                if (i5 < this.b && i3 >= 0 && !a(i3, i5)) {
                    i4++;
                    a(i5, i3, i4);
                }
                i5 -= 2;
                i3 += 2;
                if (i5 < 0) {
                    break;
                }
            } while (i3 < this.c);
            int i6 = i5 + 1;
            int i7 = i3 + 3;
            do {
                if (i6 >= 0 && i7 < this.c && !a(i7, i6)) {
                    i4++;
                    a(i6, i7, i4);
                }
                i6 += 2;
                i7 -= 2;
                if (i6 >= this.b) {
                    break;
                }
            } while (i7 >= 0);
            i5 = i6 + 3;
            i3 = i7 + 1;
            i = this.b;
            if (i5 >= i && i3 >= (i2 = this.c)) {
                break;
            }
        }
        if (!a(i2 - 1, i - 1)) {
            a(this.c - 1, this.b - 1, true);
            a(this.c - 2, this.b - 2, true);
        }
    }

    private void a(int i, int i2, int i3, int i4) {
        if (i < 0) {
            int i5 = this.b;
            i += i5;
            i2 += 4 - ((i5 + 4) % 8);
        }
        if (i2 < 0) {
            int i6 = this.c;
            i2 += i6;
            i += 4 - ((i6 + 4) % 8);
        }
        boolean z = true;
        if ((this.a.charAt(i3) & (1 << (8 - i4))) == 0) {
            z = false;
        }
        a(i2, i, z);
    }

    private void a(int i, int i2, int i3) {
        int i4 = i - 2;
        int i5 = i2 - 2;
        a(i4, i5, i3, 1);
        int i6 = i2 - 1;
        a(i4, i6, i3, 2);
        int i7 = i - 1;
        a(i7, i5, i3, 3);
        a(i7, i6, i3, 4);
        a(i7, i2, i3, 5);
        a(i, i5, i3, 6);
        a(i, i6, i3, 7);
        a(i, i2, i3, 8);
    }

    private void a(int i) {
        a(this.b - 1, 0, i, 1);
        a(this.b - 1, 1, i, 2);
        a(this.b - 1, 2, i, 3);
        a(0, this.c - 2, i, 4);
        a(0, this.c - 1, i, 5);
        a(1, this.c - 1, i, 6);
        a(2, this.c - 1, i, 7);
        a(3, this.c - 1, i, 8);
    }

    private void b(int i) {
        a(this.b - 3, 0, i, 1);
        a(this.b - 2, 0, i, 2);
        a(this.b - 1, 0, i, 3);
        a(0, this.c - 4, i, 4);
        a(0, this.c - 3, i, 5);
        a(0, this.c - 2, i, 6);
        a(0, this.c - 1, i, 7);
        a(1, this.c - 1, i, 8);
    }

    private void c(int i) {
        a(this.b - 3, 0, i, 1);
        a(this.b - 2, 0, i, 2);
        a(this.b - 1, 0, i, 3);
        a(0, this.c - 2, i, 4);
        a(0, this.c - 1, i, 5);
        a(1, this.c - 1, i, 6);
        a(2, this.c - 1, i, 7);
        a(3, this.c - 1, i, 8);
    }

    private void d(int i) {
        a(this.b - 1, 0, i, 1);
        a(this.b - 1, this.c - 1, i, 2);
        a(0, this.c - 3, i, 3);
        a(0, this.c - 2, i, 4);
        a(0, this.c - 1, i, 5);
        a(1, this.c - 3, i, 6);
        a(1, this.c - 2, i, 7);
        a(1, this.c - 1, i, 8);
    }
}
