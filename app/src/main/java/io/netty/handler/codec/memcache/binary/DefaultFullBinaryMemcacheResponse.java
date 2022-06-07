package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/* loaded from: classes4.dex */
public class DefaultFullBinaryMemcacheResponse extends DefaultBinaryMemcacheResponse implements FullBinaryMemcacheResponse {
    private final ByteBuf a;

    public DefaultFullBinaryMemcacheResponse(ByteBuf byteBuf, ByteBuf byteBuf2) {
        this(byteBuf, byteBuf2, Unpooled.buffer(0));
    }

    public DefaultFullBinaryMemcacheResponse(ByteBuf byteBuf, ByteBuf byteBuf2, ByteBuf byteBuf3) {
        super(byteBuf, byteBuf2);
        if (byteBuf3 != null) {
            this.a = byteBuf3;
            setTotalBodyLength(keyLength() + extrasLength() + byteBuf3.readableBytes());
            return;
        }
        throw new NullPointerException("Supplied content is null.");
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        return this.a;
    }

    @Override // io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheResponse, io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FullBinaryMemcacheResponse retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheResponse, io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FullBinaryMemcacheResponse retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheResponse, io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FullBinaryMemcacheResponse touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheResponse, io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.ReferenceCounted
    public FullBinaryMemcacheResponse touch(Object obj) {
        super.touch(obj);
        this.a.touch(obj);
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted
    protected void deallocate() {
        super.deallocate();
        this.a.release();
    }

    @Override // io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse, io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public FullBinaryMemcacheResponse copy() {
        ByteBuf key = key();
        if (key != null) {
            key = key.copy();
        }
        ByteBuf extras = extras();
        if (extras != null) {
            extras = extras.copy();
        }
        return new DefaultFullBinaryMemcacheResponse(key, extras, content().copy());
    }

    @Override // io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse, io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public FullBinaryMemcacheResponse duplicate() {
        ByteBuf key = key();
        if (key != null) {
            key = key.duplicate();
        }
        ByteBuf extras = extras();
        if (extras != null) {
            extras = extras.duplicate();
        }
        return new DefaultFullBinaryMemcacheResponse(key, extras, content().duplicate());
    }

    @Override // io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse, io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public FullBinaryMemcacheResponse retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    @Override // io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse, io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public FullBinaryMemcacheResponse replace(ByteBuf byteBuf) {
        ByteBuf key = key();
        if (key != null) {
            key = key.retainedDuplicate();
        }
        ByteBuf extras = extras();
        if (extras != null) {
            extras = extras.retainedDuplicate();
        }
        return new DefaultFullBinaryMemcacheResponse(key, extras, byteBuf);
    }
}
