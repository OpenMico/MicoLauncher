package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import java.util.LinkedList;

/* compiled from: State.java */
/* loaded from: classes2.dex */
final class c {
    static final c a = new c(d.a, 0, 0, 0);
    private final int b;
    private final d c;
    private final int d;
    private final int e;

    private c(d dVar, int i, int i2, int i3) {
        this.c = dVar;
        this.b = i;
        this.d = i2;
        this.e = i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c a(int i, int i2) {
        int i3 = this.e;
        d dVar = this.c;
        if (i != this.b) {
            int i4 = HighLevelEncoder.b[this.b][i];
            int i5 = 65535 & i4;
            int i6 = i4 >> 16;
            dVar = dVar.a(i5, i6);
            i3 += i6;
        }
        int i7 = i == 2 ? 4 : 5;
        return new c(dVar.a(i2, i7), i, 0, i3 + i7);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c b(int i, int i2) {
        d dVar = this.c;
        int i3 = this.b == 2 ? 4 : 5;
        return new c(dVar.a(HighLevelEncoder.c[this.b][i], i3).a(i2, 5), this.b, 0, this.e + i3 + 5);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c a(int i) {
        d dVar = this.c;
        int i2 = this.b;
        int i3 = this.e;
        if (i2 == 4 || i2 == 2) {
            int i4 = HighLevelEncoder.b[i2][0];
            int i5 = 65535 & i4;
            int i6 = i4 >> 16;
            dVar = dVar.a(i5, i6);
            i3 += i6;
            i2 = 0;
        }
        int i7 = this.d;
        c cVar = new c(dVar, i2, this.d + 1, i3 + ((i7 == 0 || i7 == 31) ? 18 : i7 == 62 ? 9 : 8));
        return cVar.d == 2078 ? cVar.b(i + 1) : cVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c b(int i) {
        int i2 = this.d;
        return i2 == 0 ? this : new c(this.c.b(i - i2, i2), this.b, 0, this.e);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(c cVar) {
        int i;
        int i2 = this.e + (HighLevelEncoder.b[this.b][cVar.b] >> 16);
        int i3 = cVar.d;
        if (i3 > 0 && ((i = this.d) == 0 || i > i3)) {
            i2 += 10;
        }
        return i2 <= cVar.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BitArray a(byte[] bArr) {
        LinkedList<d> linkedList = new LinkedList();
        for (d dVar = b(bArr.length).c; dVar != null; dVar = dVar.a()) {
            linkedList.addFirst(dVar);
        }
        BitArray bitArray = new BitArray();
        for (d dVar2 : linkedList) {
            dVar2.a(bitArray, bArr);
        }
        return bitArray;
    }

    public String toString() {
        return String.format("%s bits=%d bytes=%d", HighLevelEncoder.a[this.b], Integer.valueOf(this.e), Integer.valueOf(this.d));
    }
}
