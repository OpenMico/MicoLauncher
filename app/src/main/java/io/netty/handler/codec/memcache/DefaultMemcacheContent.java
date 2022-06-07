package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultMemcacheContent extends AbstractMemcacheObject implements MemcacheContent {
    private final ByteBuf a;

    public DefaultMemcacheContent(ByteBuf byteBuf) {
        if (byteBuf != null) {
            this.a = byteBuf;
            return;
        }
        throw new NullPointerException("Content cannot be null.");
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        return this.a;
    }

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public MemcacheContent copy() {
        return replace(this.a.copy());
    }

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public MemcacheContent duplicate() {
        return replace(this.a.duplicate());
    }

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public MemcacheContent retainedDuplicate() {
        return replace(this.a.retainedDuplicate());
    }

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    public MemcacheContent replace(ByteBuf byteBuf) {
        return new DefaultMemcacheContent(byteBuf);
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public MemcacheContent retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public MemcacheContent retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public MemcacheContent touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public MemcacheContent touch(Object obj) {
        this.a.touch(obj);
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted
    protected void deallocate() {
        this.a.release();
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(data: " + content() + ", decoderResult: " + decoderResult() + ')';
    }
}
