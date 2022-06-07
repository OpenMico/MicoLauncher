package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public class DefaultBinaryMemcacheRequest extends AbstractBinaryMemcacheMessage implements BinaryMemcacheRequest {
    public static final byte REQUEST_MAGIC_BYTE = Byte.MIN_VALUE;
    private short a;

    public DefaultBinaryMemcacheRequest() {
        this(null, null);
    }

    public DefaultBinaryMemcacheRequest(ByteBuf byteBuf) {
        this(byteBuf, null);
    }

    public DefaultBinaryMemcacheRequest(ByteBuf byteBuf, ByteBuf byteBuf2) {
        super(byteBuf, byteBuf2);
        setMagic(Byte.MIN_VALUE);
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest
    public short reserved() {
        return this.a;
    }

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest
    public BinaryMemcacheRequest setReserved(short s) {
        this.a = s;
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public BinaryMemcacheRequest retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public BinaryMemcacheRequest retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public BinaryMemcacheRequest touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.ReferenceCounted
    public BinaryMemcacheRequest touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
