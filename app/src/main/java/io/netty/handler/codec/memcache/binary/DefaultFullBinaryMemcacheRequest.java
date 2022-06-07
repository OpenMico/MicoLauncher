package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/* loaded from: classes4.dex */
public class DefaultFullBinaryMemcacheRequest extends DefaultBinaryMemcacheRequest implements FullBinaryMemcacheRequest {
    private final ByteBuf a;

    public DefaultFullBinaryMemcacheRequest(ByteBuf byteBuf, ByteBuf byteBuf2) {
        this(byteBuf, byteBuf2, Unpooled.buffer(0));
    }

    public DefaultFullBinaryMemcacheRequest(ByteBuf byteBuf, ByteBuf byteBuf2, ByteBuf byteBuf3) {
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

    @Override // io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest, io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FullBinaryMemcacheRequest retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest, io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FullBinaryMemcacheRequest retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest, io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FullBinaryMemcacheRequest touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest, io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.ReferenceCounted
    public FullBinaryMemcacheRequest touch(Object obj) {
        super.touch(obj);
        this.a.touch(obj);
        return this;
    }

    @Override // io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage, io.netty.util.AbstractReferenceCounted
    protected void deallocate() {
        super.deallocate();
        this.a.release();
    }

    @Override // io.netty.handler.codec.memcache.binary.FullBinaryMemcacheRequest, io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public FullBinaryMemcacheRequest copy() {
        ByteBuf key = key();
        if (key != null) {
            key = key.copy();
        }
        ByteBuf extras = extras();
        if (extras != null) {
            extras = extras.copy();
        }
        return new DefaultFullBinaryMemcacheRequest(key, extras, content().copy());
    }

    @Override // io.netty.handler.codec.memcache.binary.FullBinaryMemcacheRequest, io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public FullBinaryMemcacheRequest duplicate() {
        ByteBuf key = key();
        if (key != null) {
            key = key.duplicate();
        }
        ByteBuf extras = extras();
        if (extras != null) {
            extras = extras.duplicate();
        }
        return new DefaultFullBinaryMemcacheRequest(key, extras, content().duplicate());
    }

    @Override // io.netty.handler.codec.memcache.binary.FullBinaryMemcacheRequest, io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public FullBinaryMemcacheRequest retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    @Override // io.netty.handler.codec.memcache.binary.FullBinaryMemcacheRequest, io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public FullBinaryMemcacheRequest replace(ByteBuf byteBuf) {
        ByteBuf key = key();
        if (key != null) {
            key = key.retainedDuplicate();
        }
        ByteBuf extras = extras();
        if (extras != null) {
            extras = extras.retainedDuplicate();
        }
        return new DefaultFullBinaryMemcacheRequest(key, extras, byteBuf);
    }
}
