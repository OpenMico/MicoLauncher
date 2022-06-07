package io.netty.buffer;

/* compiled from: DuplicatedAbstractByteBuf.java */
/* loaded from: classes4.dex */
final class e extends DuplicatedByteBuf {
    /* JADX INFO: Access modifiers changed from: package-private */
    public e(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
    }

    /* renamed from: b */
    public AbstractByteBuf unwrap() {
        return (AbstractByteBuf) super.unwrap();
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected byte _getByte(int i) {
        return unwrap()._getByte(i);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected short _getShort(int i) {
        return unwrap()._getShort(i);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected short _getShortLE(int i) {
        return unwrap()._getShortLE(i);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected int _getUnsignedMedium(int i) {
        return unwrap()._getUnsignedMedium(i);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected int _getUnsignedMediumLE(int i) {
        return unwrap()._getUnsignedMediumLE(i);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected int _getInt(int i) {
        return unwrap()._getInt(i);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected int _getIntLE(int i) {
        return unwrap()._getIntLE(i);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected long _getLong(int i) {
        return unwrap()._getLong(i);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected long _getLongLE(int i) {
        return unwrap()._getLongLE(i);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setByte(int i, int i2) {
        unwrap()._setByte(i, i2);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setShort(int i, int i2) {
        unwrap()._setShort(i, i2);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setShortLE(int i, int i2) {
        unwrap()._setShortLE(i, i2);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setMedium(int i, int i2) {
        unwrap()._setMedium(i, i2);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setMediumLE(int i, int i2) {
        unwrap()._setMediumLE(i, i2);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setInt(int i, int i2) {
        unwrap()._setInt(i, i2);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setIntLE(int i, int i2) {
        unwrap()._setIntLE(i, i2);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setLong(int i, long j) {
        unwrap()._setLong(i, j);
    }

    @Override // io.netty.buffer.DuplicatedByteBuf, io.netty.buffer.AbstractByteBuf
    protected void _setLongLE(int i, long j) {
        unwrap()._setLongLE(i, j);
    }
}
