package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;

/* compiled from: Bzip2BitWriter.java */
/* loaded from: classes4.dex */
final class b {
    private long a;
    private int b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteBuf byteBuf, int i, long j) {
        if (i < 0 || i > 32) {
            throw new IllegalArgumentException("count: " + i + " (expected: 0-32)");
        }
        int i2 = this.b;
        long j2 = ((j << (64 - i)) >>> i2) | this.a;
        int i3 = i2 + i;
        if (i3 >= 32) {
            byteBuf.writeInt((int) (j2 >>> 32));
            j2 <<= 32;
            i3 -= 32;
        }
        this.a = j2;
        this.b = i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteBuf byteBuf, boolean z) {
        int i = this.b + 1;
        long j = this.a | (z ? 1 << (64 - i) : 0L);
        if (i == 32) {
            byteBuf.writeInt((int) (j >>> 32));
            i = 0;
            j = 0;
        }
        this.a = j;
        this.b = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteBuf byteBuf, int i) {
        if (i >= 0) {
            while (true) {
                i--;
                if (i > 0) {
                    a(byteBuf, true);
                } else {
                    a(byteBuf, false);
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("value: " + i + " (expected 0 or more)");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(ByteBuf byteBuf, int i) {
        a(byteBuf, 32, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteBuf byteBuf) {
        int i = this.b;
        if (i > 0) {
            long j = this.a;
            int i2 = 64 - i;
            if (i <= 8) {
                byteBuf.writeByte((int) ((j >>> i2) << (8 - i)));
            } else if (i <= 16) {
                byteBuf.writeShort((int) ((j >>> i2) << (16 - i)));
            } else if (i <= 24) {
                byteBuf.writeMedium((int) ((j >>> i2) << (24 - i)));
            } else {
                byteBuf.writeInt((int) ((j >>> i2) << (32 - i)));
            }
        }
    }
}
