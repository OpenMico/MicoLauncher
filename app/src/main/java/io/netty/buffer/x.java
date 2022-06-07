package io.netty.buffer;

/* compiled from: SlicedAbstractByteBuf.java */
/* loaded from: classes4.dex */
final class x extends SlicedByteBuf {
    /* JADX INFO: Access modifiers changed from: package-private */
    public x(AbstractByteBuf abstractByteBuf, int i, int i2) {
        super(abstractByteBuf, i, i2);
    }

    /* renamed from: b */
    public AbstractByteBuf unwrap() {
        return (AbstractByteBuf) super.unwrap();
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected byte _getByte(int i) {
        return unwrap()._getByte(a(i));
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected short _getShort(int i) {
        return unwrap()._getShort(a(i));
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected short _getShortLE(int i) {
        return unwrap()._getShortLE(a(i));
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected int _getUnsignedMedium(int i) {
        return unwrap()._getUnsignedMedium(a(i));
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected int _getUnsignedMediumLE(int i) {
        return unwrap()._getUnsignedMediumLE(a(i));
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected int _getInt(int i) {
        return unwrap()._getInt(a(i));
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected int _getIntLE(int i) {
        return unwrap()._getIntLE(a(i));
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected long _getLong(int i) {
        return unwrap()._getLong(a(i));
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected long _getLongLE(int i) {
        return unwrap()._getLongLE(a(i));
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setByte(int i, int i2) {
        unwrap()._setByte(a(i), i2);
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setShort(int i, int i2) {
        unwrap()._setShort(a(i), i2);
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setShortLE(int i, int i2) {
        unwrap()._setShortLE(a(i), i2);
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setMedium(int i, int i2) {
        unwrap()._setMedium(a(i), i2);
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setMediumLE(int i, int i2) {
        unwrap()._setMediumLE(a(i), i2);
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setInt(int i, int i2) {
        unwrap()._setInt(a(i), i2);
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setIntLE(int i, int i2) {
        unwrap()._setIntLE(a(i), i2);
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setLong(int i, long j) {
        unwrap()._setLong(a(i), j);
    }

    @Override // io.netty.buffer.SlicedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setLongLE(int i, long j) {
        unwrap()._setLongLE(a(i), j);
    }
}
