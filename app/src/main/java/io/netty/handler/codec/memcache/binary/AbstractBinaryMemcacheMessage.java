package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.AbstractMemcacheObject;

/* loaded from: classes4.dex */
public abstract class AbstractBinaryMemcacheMessage extends AbstractMemcacheObject implements BinaryMemcacheMessage {
    private ByteBuf a;
    private ByteBuf b;
    private byte c;
    private byte d;
    private short e;
    private byte f;
    private byte g;
    private int h;
    private int i;
    private long j;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractBinaryMemcacheMessage(ByteBuf byteBuf, ByteBuf byteBuf2) {
        this.a = byteBuf;
        byte b = 0;
        this.e = byteBuf == null ? (short) 0 : (short) byteBuf.readableBytes();
        this.b = byteBuf2;
        this.f = byteBuf2 != null ? (byte) byteBuf2.readableBytes() : b;
        this.h = this.e + this.f;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public ByteBuf key() {
        return this.a;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public ByteBuf extras() {
        return this.b;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public BinaryMemcacheMessage setKey(ByteBuf byteBuf) {
        ByteBuf byteBuf2 = this.a;
        if (byteBuf2 != null) {
            byteBuf2.release();
        }
        this.a = byteBuf;
        short s = this.e;
        this.e = byteBuf == null ? (short) 0 : (short) byteBuf.readableBytes();
        this.h = (this.h + this.e) - s;
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public BinaryMemcacheMessage setExtras(ByteBuf byteBuf) {
        ByteBuf byteBuf2 = this.b;
        if (byteBuf2 != null) {
            byteBuf2.release();
        }
        this.b = byteBuf;
        short s = this.f;
        this.f = byteBuf == null ? (byte) 0 : (byte) byteBuf.readableBytes();
        this.h = (this.h + this.f) - s;
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public byte magic() {
        return this.c;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public BinaryMemcacheMessage setMagic(byte b) {
        this.c = b;
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public long cas() {
        return this.j;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public BinaryMemcacheMessage setCas(long j) {
        this.j = j;
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public int opaque() {
        return this.i;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public BinaryMemcacheMessage setOpaque(int i) {
        this.i = i;
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public int totalBodyLength() {
        return this.h;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public BinaryMemcacheMessage setTotalBodyLength(int i) {
        this.h = i;
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public byte dataType() {
        return this.g;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public BinaryMemcacheMessage setDataType(byte b) {
        this.g = b;
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public byte extrasLength() {
        return this.f;
    }

    BinaryMemcacheMessage a(byte b) {
        this.f = b;
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public short keyLength() {
        return this.e;
    }

    BinaryMemcacheMessage a(short s) {
        this.e = s;
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public byte opcode() {
        return this.d;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage
    public BinaryMemcacheMessage setOpcode(byte b) {
        this.d = b;
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public BinaryMemcacheMessage retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public BinaryMemcacheMessage retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted
    protected void deallocate() {
        ByteBuf byteBuf = this.a;
        if (byteBuf != null) {
            byteBuf.release();
        }
        ByteBuf byteBuf2 = this.b;
        if (byteBuf2 != null) {
            byteBuf2.release();
        }
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public BinaryMemcacheMessage touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public BinaryMemcacheMessage touch(Object obj) {
        ByteBuf byteBuf = this.a;
        if (byteBuf != null) {
            byteBuf.touch(obj);
        }
        ByteBuf byteBuf2 = this.b;
        if (byteBuf2 != null) {
            byteBuf2.touch(obj);
        }
        return this;
    }
}
