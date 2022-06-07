package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.MemcacheMessage;

/* loaded from: classes4.dex */
public interface BinaryMemcacheMessage extends MemcacheMessage {
    long cas();

    byte dataType();

    ByteBuf extras();

    byte extrasLength();

    ByteBuf key();

    short keyLength();

    byte magic();

    int opaque();

    byte opcode();

    @Override // io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheMessage retain();

    @Override // io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheMessage retain(int i);

    BinaryMemcacheMessage setCas(long j);

    BinaryMemcacheMessage setDataType(byte b);

    BinaryMemcacheMessage setExtras(ByteBuf byteBuf);

    BinaryMemcacheMessage setKey(ByteBuf byteBuf);

    BinaryMemcacheMessage setMagic(byte b);

    BinaryMemcacheMessage setOpaque(int i);

    BinaryMemcacheMessage setOpcode(byte b);

    BinaryMemcacheMessage setTotalBodyLength(int i);

    int totalBodyLength();

    @Override // io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheMessage touch();

    @Override // io.netty.handler.codec.memcache.MemcacheMessage, io.netty.util.ReferenceCounted
    BinaryMemcacheMessage touch(Object obj);
}
