package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;

/* compiled from: Bzip2BitReader.java */
/* loaded from: classes4.dex */
class a {
    private ByteBuf a;
    private long b;
    private int c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteBuf byteBuf) {
        this.a = byteBuf;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(int i) {
        int i2;
        long j;
        if (i < 0 || i > 32) {
            throw new IllegalArgumentException("count: " + i + " (expected: 0-32 )");
        }
        int i3 = this.c;
        long j2 = this.b;
        if (i3 < i) {
            switch (this.a.readableBytes()) {
                case 1:
                    j = this.a.readUnsignedByte();
                    i2 = 8;
                    break;
                case 2:
                    j = this.a.readUnsignedShort();
                    i2 = 16;
                    break;
                case 3:
                    j = this.a.readUnsignedMedium();
                    i2 = 24;
                    break;
                default:
                    j = this.a.readUnsignedInt();
                    i2 = 32;
                    break;
            }
            j2 = (j2 << i2) | j;
            i3 += i2;
            this.b = j2;
        }
        int i4 = i3 - i;
        this.c = i4;
        return (int) ((j2 >>> i4) & (i != 32 ? (1 << i) - 1 : 4294967295L));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return a(1) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return a(32);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c() {
        this.b = (this.b << 8) | this.a.readUnsignedByte();
        this.c += 8;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean d() {
        return this.c > 0 || this.a.isReadable();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b(int i) {
        if (i >= 0) {
            return this.c >= i || ((this.a.readableBytes() << 3) & Integer.MAX_VALUE) >= i - this.c;
        }
        throw new IllegalArgumentException("count: " + i + " (expected value greater than 0)");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c(int i) {
        if (i >= 0 && i <= 268435455) {
            return b(i << 3);
        }
        throw new IllegalArgumentException("count: " + i + " (expected: 0-268435455)");
    }
}
