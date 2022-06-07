package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.FullMemcacheMessage;

/* loaded from: classes4.dex */
public interface FullBinaryMemcacheResponse extends FullMemcacheMessage, BinaryMemcacheResponse {
    @Override // io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    FullBinaryMemcacheResponse copy();

    @Override // io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    FullBinaryMemcacheResponse duplicate();

    @Override // io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    FullBinaryMemcacheResponse replace(ByteBuf byteBuf);

    @Override // io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FullBinaryMemcacheResponse retain();

    @Override // io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FullBinaryMemcacheResponse retain(int i);

    @Override // io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    FullBinaryMemcacheResponse retainedDuplicate();

    @Override // io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FullBinaryMemcacheResponse touch();

    @Override // io.netty.handler.codec.memcache.FullMemcacheMessage, io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FullBinaryMemcacheResponse touch(Object obj);
}
