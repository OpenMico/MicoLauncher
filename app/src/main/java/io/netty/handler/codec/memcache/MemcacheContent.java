package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

/* loaded from: classes4.dex */
public interface MemcacheContent extends ByteBufHolder, MemcacheObject {
    @Override // io.netty.buffer.ByteBufHolder
    MemcacheContent copy();

    @Override // io.netty.buffer.ByteBufHolder
    MemcacheContent duplicate();

    @Override // io.netty.buffer.ByteBufHolder
    MemcacheContent replace(ByteBuf byteBuf);

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    MemcacheContent retain();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    MemcacheContent retain(int i);

    @Override // io.netty.buffer.ByteBufHolder
    MemcacheContent retainedDuplicate();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    MemcacheContent touch();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    MemcacheContent touch(Object obj);
}
