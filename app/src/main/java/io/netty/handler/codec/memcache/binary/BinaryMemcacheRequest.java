package io.netty.handler.codec.memcache.binary;

/* loaded from: classes4.dex */
public interface BinaryMemcacheRequest extends BinaryMemcacheMessage {
    short reserved();

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage, io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheRequest retain();

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage, io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheRequest retain(int i);

    BinaryMemcacheRequest setReserved(short s);

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage, io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheRequest touch();

    @Override // io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage, io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheRequest touch(Object obj);
}
