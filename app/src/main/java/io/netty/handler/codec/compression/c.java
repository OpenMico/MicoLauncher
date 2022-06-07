package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;

/* compiled from: Bzip2BlockCompressor.java */
/* loaded from: classes4.dex */
final class c {
    private final b a;
    private final byte[] c;
    private int d;
    private final int e;
    private final int[] g;
    private int i;
    private final l b = new l();
    private final boolean[] f = new boolean[256];
    private int h = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(b bVar, int i) {
        this.a = bVar;
        int i2 = i + 1;
        this.c = new byte[i2];
        this.g = new int[i2];
        this.e = i - 6;
    }

    private void b(ByteBuf byteBuf) {
        b bVar = this.a;
        boolean[] zArr = this.f;
        boolean[] zArr2 = new boolean[16];
        for (int i = 0; i < zArr2.length; i++) {
            int i2 = i << 4;
            int i3 = 0;
            while (i3 < 16) {
                if (zArr[i2]) {
                    zArr2[i] = true;
                }
                i3++;
                i2++;
            }
        }
        for (boolean z : zArr2) {
            bVar.a(byteBuf, z);
        }
        for (int i4 = 0; i4 < zArr2.length; i4++) {
            if (zArr2[i4]) {
                int i5 = i4 << 4;
                int i6 = 0;
                while (i6 < 16) {
                    bVar.a(byteBuf, zArr[i5]);
                    i6++;
                    i5++;
                }
            }
        }
    }

    private void a(int i, int i2) {
        int i3 = this.d;
        byte[] bArr = this.c;
        this.f[i] = true;
        this.b.a(i, i2);
        byte b = (byte) i;
        switch (i2) {
            case 1:
                bArr[i3] = b;
                this.d = i3 + 1;
                return;
            case 2:
                bArr[i3] = b;
                bArr[i3 + 1] = b;
                this.d = i3 + 2;
                return;
            case 3:
                bArr[i3] = b;
                bArr[i3 + 1] = b;
                bArr[i3 + 2] = b;
                this.d = i3 + 3;
                return;
            default:
                int i4 = i2 - 4;
                this.f[i4] = true;
                bArr[i3] = b;
                bArr[i3 + 1] = b;
                bArr[i3 + 2] = b;
                bArr[i3 + 3] = b;
                bArr[i3 + 4] = (byte) i4;
                this.d = i3 + 5;
                return;
        }
    }

    boolean a(int i) {
        if (this.d > this.e) {
            return false;
        }
        int i2 = this.h;
        int i3 = this.i;
        if (i3 == 0) {
            this.h = i;
            this.i = 1;
        } else if (i2 != i) {
            a(i2 & 255, i3);
            this.h = i;
            this.i = 1;
        } else if (i3 == 254) {
            a(i2 & 255, 255);
            this.i = 0;
        } else {
            this.i = i3 + 1;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(byte[] bArr, int i, int i2) {
        int i3 = 0;
        while (true) {
            i2--;
            if (i2 <= 0) {
                break;
            }
            i++;
            if (!a(bArr[i])) {
                break;
            }
            i3++;
        }
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteBuf byteBuf) {
        int i = this.i;
        if (i > 0) {
            a(this.h & 255, i);
        }
        byte[] bArr = this.c;
        int i2 = this.d;
        bArr[i2] = bArr[0];
        int a = new e(bArr, this.g, i2).a();
        b bVar = this.a;
        bVar.a(byteBuf, 24, 3227993L);
        bVar.a(byteBuf, 24, 2511705L);
        bVar.b(byteBuf, this.b.a());
        bVar.a(byteBuf, false);
        bVar.a(byteBuf, 24, a);
        b(byteBuf);
        i iVar = new i(this.g, this.d, this.f);
        iVar.a();
        new h(bVar, iVar.b(), iVar.c(), iVar.d(), iVar.e()).a(byteBuf);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        int i = this.d;
        if (i == 0) {
            return this.e + 2;
        }
        return (this.e - i) + 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return this.d > this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c() {
        return this.d == 0 && this.i == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int d() {
        return this.b.a();
    }
}
