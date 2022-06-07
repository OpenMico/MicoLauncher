package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public final class DefaultLastBulkStringRedisContent extends DefaultBulkStringRedisContent implements LastBulkStringRedisContent {
    public DefaultLastBulkStringRedisContent(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override // io.netty.handler.codec.redis.DefaultBulkStringRedisContent, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public LastBulkStringRedisContent copy() {
        return (LastBulkStringRedisContent) super.copy();
    }

    @Override // io.netty.handler.codec.redis.DefaultBulkStringRedisContent, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public LastBulkStringRedisContent duplicate() {
        return (LastBulkStringRedisContent) super.duplicate();
    }

    @Override // io.netty.handler.codec.redis.DefaultBulkStringRedisContent, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public LastBulkStringRedisContent retainedDuplicate() {
        return (LastBulkStringRedisContent) super.retainedDuplicate();
    }

    @Override // io.netty.handler.codec.redis.DefaultBulkStringRedisContent, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public LastBulkStringRedisContent replace(ByteBuf byteBuf) {
        return new DefaultLastBulkStringRedisContent(byteBuf);
    }

    @Override // io.netty.handler.codec.redis.DefaultBulkStringRedisContent, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public LastBulkStringRedisContent retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.redis.DefaultBulkStringRedisContent, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public LastBulkStringRedisContent retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.redis.DefaultBulkStringRedisContent, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public LastBulkStringRedisContent touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.redis.DefaultBulkStringRedisContent, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public LastBulkStringRedisContent touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
