package io.netty.handler.codec.memcache.binary;

/* loaded from: classes4.dex */
public interface BinaryMemcacheResponse extends BinaryMemcacheMessage {
    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage, io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheResponse retain();

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage, io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheResponse retain(int i);

    BinaryMemcacheResponse setStatus(short s);

    short status();

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage, io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheResponse touch();

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage, io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheResponse touch(Object obj);
}
