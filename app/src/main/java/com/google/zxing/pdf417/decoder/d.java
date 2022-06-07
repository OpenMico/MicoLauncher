package com.google.zxing.pdf417.decoder;

/* compiled from: Codeword.java */
/* loaded from: classes2.dex */
final class d {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private int e = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(int i, int i2, int i3, int i4) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return a(this.e);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(int i) {
        return i != -1 && this.c == (i % 3) * 3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        this.e = ((this.d / 30) * 3) + (this.c / 3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c() {
        return this.b - this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int d() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int e() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int f() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int g() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int h() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(int i) {
        this.e = i;
    }

    public String toString() {
        return this.e + "|" + this.d;
    }
}
