package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

/* loaded from: classes4.dex */
public interface BulkStringRedisContent extends ByteBufHolder, RedisMessage {
    @Override // io.netty.buffer.ByteBufHolder
    BulkStringRedisContent copy();

    @Override // io.netty.buffer.ByteBufHolder
    BulkStringRedisContent duplicate();

    @Override // io.netty.buffer.ByteBufHolder
    BulkStringRedisContent replace(ByteBuf byteBuf);

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    BulkStringRedisContent retain();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    BulkStringRedisContent retain(int i);

    @Override // io.netty.buffer.ByteBufHolder
    BulkStringRedisContent retainedDuplicate();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    BulkStringRedisContent touch();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    BulkStringRedisContent touch(Object obj);
}
