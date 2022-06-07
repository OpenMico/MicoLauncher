package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.nio.ByteOrder;
import kotlin.UShort;

/* compiled from: AbstractUnsafeSwappedByteBuf.java */
/* loaded from: classes4.dex */
abstract class b extends SwappedByteBuf {
    static final /* synthetic */ boolean a = !b.class.desiredAssertionStatus();
    private final boolean b;
    private final AbstractByteBuf c;

    protected abstract short a(AbstractByteBuf abstractByteBuf, int i);

    protected abstract void a(AbstractByteBuf abstractByteBuf, int i, int i2);

    protected abstract void a(AbstractByteBuf abstractByteBuf, int i, long j);

    protected abstract void a(AbstractByteBuf abstractByteBuf, int i, short s);

    protected abstract int b(AbstractByteBuf abstractByteBuf, int i);

    protected abstract long c(AbstractByteBuf abstractByteBuf, int i);

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
        if (a || PlatformDependent.isUnaligned()) {
            this.c = abstractByteBuf;
            this.b = PlatformDependent.BIG_ENDIAN_NATIVE_ORDER != (order() == ByteOrder.BIG_ENDIAN) ? false : true;
            return;
        }
        throw new AssertionError();
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final long getLong(int i) {
        this.c.checkIndex(i, 8);
        long c = c(this.c, i);
        return this.b ? c : Long.reverseBytes(c);
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final float getFloat(int i) {
        return Float.intBitsToFloat(getInt(i));
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final double getDouble(int i) {
        return Double.longBitsToDouble(getLong(i));
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final char getChar(int i) {
        return (char) getShort(i);
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final long getUnsignedInt(int i) {
        return getInt(i) & 4294967295L;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final int getInt(int i) {
        this.c.a(i, 4);
        int b = b(this.c, i);
        return this.b ? b : Integer.reverseBytes(b);
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final int getUnsignedShort(int i) {
        return getShort(i) & UShort.MAX_VALUE;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final short getShort(int i) {
        this.c.a(i, 2);
        short a2 = a(this.c, i);
        return this.b ? a2 : Short.reverseBytes(a2);
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf setShort(int i, int i2) {
        this.c.a(i, 2);
        a(this.c, i, this.b ? (short) i2 : Short.reverseBytes((short) i2));
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf setInt(int i, int i2) {
        this.c.a(i, 4);
        AbstractByteBuf abstractByteBuf = this.c;
        if (!this.b) {
            i2 = Integer.reverseBytes(i2);
        }
        a(abstractByteBuf, i, i2);
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf setLong(int i, long j) {
        this.c.checkIndex(i, 8);
        AbstractByteBuf abstractByteBuf = this.c;
        if (!this.b) {
            j = Long.reverseBytes(j);
        }
        a(abstractByteBuf, i, j);
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf setChar(int i, int i2) {
        setShort(i, i2);
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf setFloat(int i, float f) {
        setInt(i, Float.floatToRawIntBits(f));
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf setDouble(int i, double d) {
        setLong(i, Double.doubleToRawLongBits(d));
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf writeShort(int i) {
        this.c.ensureWritable(2);
        AbstractByteBuf abstractByteBuf = this.c;
        a(abstractByteBuf, abstractByteBuf.c, this.b ? (short) i : Short.reverseBytes((short) i));
        this.c.c += 2;
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf writeInt(int i) {
        this.c.ensureWritable(4);
        AbstractByteBuf abstractByteBuf = this.c;
        int i2 = abstractByteBuf.c;
        if (!this.b) {
            i = Integer.reverseBytes(i);
        }
        a(abstractByteBuf, i2, i);
        this.c.c += 4;
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf writeLong(long j) {
        this.c.ensureWritable(8);
        AbstractByteBuf abstractByteBuf = this.c;
        int i = abstractByteBuf.c;
        if (!this.b) {
            j = Long.reverseBytes(j);
        }
        a(abstractByteBuf, i, j);
        this.c.c += 8;
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf writeChar(int i) {
        writeShort(i);
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf writeFloat(float f) {
        writeInt(Float.floatToRawIntBits(f));
        return this;
    }

    @Override // io.netty.buffer.SwappedByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf writeDouble(double d) {
        writeLong(Double.doubleToRawLongBits(d));
        return this;
    }
}
