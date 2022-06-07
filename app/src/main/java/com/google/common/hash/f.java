package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: AbstractStreamingHasher.java */
@CanIgnoreReturnValue
/* loaded from: classes2.dex */
abstract class f extends d {
    private final ByteBuffer a;
    private final int b;
    private final int c;

    protected abstract HashCode a();

    protected abstract void a(ByteBuffer byteBuffer);

    /* JADX INFO: Access modifiers changed from: protected */
    public f(int i) {
        this(i, i);
    }

    protected f(int i, int i2) {
        Preconditions.checkArgument(i2 % i == 0);
        this.a = ByteBuffer.allocate(i2 + 7).order(ByteOrder.LITTLE_ENDIAN);
        this.b = i2;
        this.c = i;
    }

    protected void b(ByteBuffer byteBuffer) {
        byteBuffer.position(byteBuffer.limit());
        byteBuffer.limit(this.c + 7);
        while (true) {
            int position = byteBuffer.position();
            int i = this.c;
            if (position < i) {
                byteBuffer.putLong(0L);
            } else {
                byteBuffer.limit(i);
                byteBuffer.flip();
                a(byteBuffer);
                return;
            }
        }
    }

    @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
    public final Hasher putBytes(byte[] bArr, int i, int i2) {
        return c(ByteBuffer.wrap(bArr, i, i2).order(ByteOrder.LITTLE_ENDIAN));
    }

    @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
    public final Hasher putBytes(ByteBuffer byteBuffer) {
        ByteOrder order = byteBuffer.order();
        try {
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            return c(byteBuffer);
        } finally {
            byteBuffer.order(order);
        }
    }

    private Hasher c(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() <= this.a.remaining()) {
            this.a.put(byteBuffer);
            b();
            return this;
        }
        int position = this.b - this.a.position();
        for (int i = 0; i < position; i++) {
            this.a.put(byteBuffer.get());
        }
        c();
        while (byteBuffer.remaining() >= this.c) {
            a(byteBuffer);
        }
        this.a.put(byteBuffer);
        return this;
    }

    @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
    public final Hasher putByte(byte b) {
        this.a.put(b);
        b();
        return this;
    }

    @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
    public final Hasher putShort(short s) {
        this.a.putShort(s);
        b();
        return this;
    }

    @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
    public final Hasher putChar(char c) {
        this.a.putChar(c);
        b();
        return this;
    }

    @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
    public final Hasher putInt(int i) {
        this.a.putInt(i);
        b();
        return this;
    }

    @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
    public final Hasher putLong(long j) {
        this.a.putLong(j);
        b();
        return this;
    }

    @Override // com.google.common.hash.Hasher
    public final HashCode hash() {
        c();
        this.a.flip();
        if (this.a.remaining() > 0) {
            b(this.a);
            ByteBuffer byteBuffer = this.a;
            byteBuffer.position(byteBuffer.limit());
        }
        return a();
    }

    private void b() {
        if (this.a.remaining() < 8) {
            c();
        }
    }

    private void c() {
        this.a.flip();
        while (this.a.remaining() >= this.c) {
            a(this.a);
        }
        this.a.compact();
    }
}
