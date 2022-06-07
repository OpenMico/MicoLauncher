package com.xiaomi.push;

import java.io.InputStream;
import java.util.Vector;

/* loaded from: classes4.dex */
public final class b {
    private final byte[] a;
    private int b;
    private int c;
    private int d;
    private final InputStream e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;

    private b(InputStream inputStream) {
        this.h = Integer.MAX_VALUE;
        this.j = 64;
        this.k = 67108864;
        this.a = new byte[4096];
        this.b = 0;
        this.d = 0;
        this.e = inputStream;
    }

    private b(byte[] bArr, int i, int i2) {
        this.h = Integer.MAX_VALUE;
        this.j = 64;
        this.k = 67108864;
        this.a = bArr;
        this.b = i2 + i;
        this.d = i;
        this.e = null;
    }

    public static b a(InputStream inputStream) {
        return new b(inputStream);
    }

    public static b a(byte[] bArr, int i, int i2) {
        return new b(bArr, i, i2);
    }

    private boolean a(boolean z) {
        int i = this.d;
        int i2 = this.b;
        if (i >= i2) {
            int i3 = this.g;
            if (i3 + i2 != this.h) {
                this.g = i3 + i2;
                this.d = 0;
                InputStream inputStream = this.e;
                this.b = inputStream == null ? -1 : inputStream.read(this.a);
                int i4 = this.b;
                if (i4 == 0 || i4 < -1) {
                    throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + this.b + "\nThe InputStream implementation is buggy.");
                } else if (i4 == -1) {
                    this.b = 0;
                    if (!z) {
                        return false;
                    }
                    throw d.a();
                } else {
                    f();
                    int i5 = this.g + this.b + this.c;
                    if (i5 <= this.k && i5 >= 0) {
                        return true;
                    }
                    throw d.h();
                }
            } else if (!z) {
                return false;
            } else {
                throw d.a();
            }
        } else {
            throw new IllegalStateException("refillBuffer() called when buffer wasn't empty.");
        }
    }

    private void f() {
        this.b += this.c;
        int i = this.g;
        int i2 = this.b;
        int i3 = i + i2;
        int i4 = this.h;
        if (i3 > i4) {
            this.c = i3 - i4;
            this.b = i2 - this.c;
            return;
        }
        this.c = 0;
    }

    public byte a() {
        if (this.d == this.b) {
            a(true);
        }
        byte[] bArr = this.a;
        int i = this.d;
        this.d = i + 1;
        return bArr[i];
    }

    /* renamed from: a */
    public int m764a() {
        if (m774b()) {
            this.f = 0;
            return 0;
        }
        this.f = d();
        int i = this.f;
        if (i != 0) {
            return i;
        }
        throw d.d();
    }

    public int a(int i) {
        if (i >= 0) {
            int i2 = i + this.g + this.d;
            int i3 = this.h;
            if (i2 <= i3) {
                this.h = i2;
                f();
                return i3;
            }
            throw d.a();
        }
        throw d.b();
    }

    /* renamed from: a */
    public long m765a() {
        return m775c();
    }

    /* renamed from: a */
    public a m766a() {
        int d = d();
        int i = this.b;
        int i2 = this.d;
        if (d > i - i2 || d <= 0) {
            return a.a(m772a(d));
        }
        a a = a.a(this.a, i2, d);
        this.d += d;
        return a;
    }

    /* renamed from: a */
    public String m767a() {
        int d = d();
        int i = this.b;
        int i2 = this.d;
        if (d > i - i2 || d <= 0) {
            return new String(m772a(d), "UTF-8");
        }
        String str = new String(this.a, i2, d, "UTF-8");
        this.d += d;
        return str;
    }

    /* renamed from: a */
    public void m768a() {
        int a;
        do {
            a = m764a();
            if (a == 0) {
                return;
            }
        } while (m771a(a));
    }

    /* renamed from: a */
    public void m769a(int i) {
        if (this.f != i) {
            throw d.e();
        }
    }

    public void a(e eVar) {
        int d = d();
        if (this.i < this.j) {
            int a = a(d);
            this.i++;
            eVar.a(this);
            m769a(0);
            this.i--;
            b(a);
            return;
        }
        throw d.g();
    }

    /* renamed from: a */
    public boolean m770a() {
        return d() != 0;
    }

    /* renamed from: a */
    public boolean m771a(int i) {
        switch (f.a(i)) {
            case 0:
                b();
                return true;
            case 1:
                m776d();
                return true;
            case 2:
                c(d());
                return true;
            case 3:
                m768a();
                m769a(f.a(f.b(i), 4));
                return true;
            case 4:
                return false;
            case 5:
                e();
                return true;
            default:
                throw d.f();
        }
    }

    /* renamed from: a */
    public byte[] m772a(int i) {
        if (i >= 0) {
            int i2 = this.g;
            int i3 = this.d;
            int i4 = i2 + i3 + i;
            int i5 = this.h;
            if (i4 <= i5) {
                int i6 = this.b;
                if (i <= i6 - i3) {
                    byte[] bArr = new byte[i];
                    System.arraycopy(this.a, i3, bArr, 0, i);
                    this.d += i;
                    return bArr;
                } else if (i < 4096) {
                    byte[] bArr2 = new byte[i];
                    int i7 = i6 - i3;
                    System.arraycopy(this.a, i3, bArr2, 0, i7);
                    this.d = this.b;
                    while (true) {
                        a(true);
                        int i8 = i - i7;
                        int i9 = this.b;
                        if (i8 > i9) {
                            System.arraycopy(this.a, 0, bArr2, i7, i9);
                            int i10 = this.b;
                            i7 += i10;
                            this.d = i10;
                        } else {
                            System.arraycopy(this.a, 0, bArr2, i7, i8);
                            this.d = i8;
                            return bArr2;
                        }
                    }
                } else {
                    this.g = i2 + i6;
                    this.d = 0;
                    this.b = 0;
                    int i11 = i6 - i3;
                    int i12 = i - i11;
                    Vector vector = new Vector();
                    while (i12 > 0) {
                        byte[] bArr3 = new byte[Math.min(i12, 4096)];
                        int i13 = 0;
                        while (i13 < bArr3.length) {
                            InputStream inputStream = this.e;
                            int read = inputStream == null ? -1 : inputStream.read(bArr3, i13, bArr3.length - i13);
                            if (read != -1) {
                                this.g += read;
                                i13 += read;
                            } else {
                                throw d.a();
                            }
                        }
                        i12 -= bArr3.length;
                        vector.addElement(bArr3);
                    }
                    byte[] bArr4 = new byte[i];
                    System.arraycopy(this.a, i3, bArr4, 0, i11);
                    for (int i14 = 0; i14 < vector.size(); i14++) {
                        byte[] bArr5 = (byte[]) vector.elementAt(i14);
                        System.arraycopy(bArr5, 0, bArr4, i11, bArr5.length);
                        i11 += bArr5.length;
                    }
                    return bArr4;
                }
            } else {
                c((i5 - i2) - i3);
                throw d.a();
            }
        } else {
            throw d.b();
        }
    }

    public int b() {
        return d();
    }

    /* renamed from: b */
    public long m773b() {
        return m775c();
    }

    public void b(int i) {
        this.h = i;
        f();
    }

    /* renamed from: b */
    public boolean m774b() {
        return this.d == this.b && !a(false);
    }

    public int c() {
        return d();
    }

    /* renamed from: c */
    public long m775c() {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte a = a();
            j |= (a & Byte.MAX_VALUE) << i;
            if ((a & 128) == 0) {
                return j;
            }
        }
        throw d.c();
    }

    public void c(int i) {
        if (i >= 0) {
            int i2 = this.g;
            int i3 = this.d;
            int i4 = i2 + i3 + i;
            int i5 = this.h;
            if (i4 <= i5) {
                int i6 = this.b;
                if (i <= i6 - i3) {
                    this.d = i3 + i;
                    return;
                }
                int i7 = i6 - i3;
                this.g = i2 + i6;
                this.d = 0;
                this.b = 0;
                while (i7 < i) {
                    InputStream inputStream = this.e;
                    int skip = inputStream == null ? -1 : (int) inputStream.skip(i - i7);
                    if (skip > 0) {
                        i7 += skip;
                        this.g += skip;
                    } else {
                        throw d.a();
                    }
                }
                return;
            }
            c((i5 - i2) - i3);
            throw d.a();
        }
        throw d.b();
    }

    public int d() {
        int i;
        byte a = a();
        if (a >= 0) {
            return a;
        }
        int i2 = a & Byte.MAX_VALUE;
        byte a2 = a();
        if (a2 >= 0) {
            i = a2 << 7;
        } else {
            i2 |= (a2 & Byte.MAX_VALUE) << 7;
            byte a3 = a();
            if (a3 >= 0) {
                i = a3 << 14;
            } else {
                i2 |= (a3 & Byte.MAX_VALUE) << 14;
                byte a4 = a();
                if (a4 >= 0) {
                    i = a4 << 21;
                } else {
                    int i3 = i2 | ((a4 & Byte.MAX_VALUE) << 21);
                    byte a5 = a();
                    int i4 = i3 | (a5 << 28);
                    if (a5 >= 0) {
                        return i4;
                    }
                    for (int i5 = 0; i5 < 5; i5++) {
                        if (a() >= 0) {
                            return i4;
                        }
                    }
                    throw d.c();
                }
            }
        }
        return i2 | i;
    }

    /* renamed from: d */
    public long m776d() {
        byte a = a();
        byte a2 = a();
        return ((a2 & 255) << 8) | (a & 255) | ((a() & 255) << 16) | ((a() & 255) << 24) | ((a() & 255) << 32) | ((a() & 255) << 40) | ((a() & 255) << 48) | ((a() & 255) << 56);
    }

    public int e() {
        return (a() & 255) | ((a() & 255) << 8) | ((a() & 255) << 16) | ((a() & 255) << 24);
    }
}
