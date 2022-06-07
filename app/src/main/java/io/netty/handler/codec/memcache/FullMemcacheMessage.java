package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public interface FullMemcacheMessage extends LastMemcacheContent, MemcacheMessage {
    @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    FullMemcacheMessage copy();

    @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    FullMemcacheMessage duplicate();

    @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    FullMemcacheMessage replace(ByteBuf byteBuf);

    @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FullMemcacheMessage retain();

    @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FullMemcacheMessage retain(int i);

    @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    FullMemcacheMessage retainedDuplicate();

    @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FullMemcacheMessage touch();

    @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FullMemcacheMessage touch(Object obj);
}
