package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/* loaded from: classes4.dex */
public interface LastBulkStringRedisContent extends BulkStringRedisContent {
    public static final LastBulkStringRedisContent EMPTY_LAST_CONTENT = new LastBulkStringRedisContent() { // from class: io.netty.handler.codec.redis.LastBulkStringRedisContent.1
        @Override // io.netty.handler.codec.redis.LastBulkStringRedisContent, io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder
        public LastBulkStringRedisContent copy() {
            return this;
        }

        @Override // io.netty.handler.codec.redis.LastBulkStringRedisContent, io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder
        public LastBulkStringRedisContent duplicate() {
            return this;
        }

        @Override // io.netty.util.ReferenceCounted
        public int refCnt() {
            return 1;
        }

        @Override // io.netty.util.ReferenceCounted
        public boolean release() {
            return false;
        }

        @Override // io.netty.util.ReferenceCounted
        public boolean release(int i) {
            return false;
        }

        @Override // io.netty.handler.codec.redis.LastBulkStringRedisContent, io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastBulkStringRedisContent retain() {
            return this;
        }

        @Override // io.netty.handler.codec.redis.LastBulkStringRedisContent, io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastBulkStringRedisContent retain(int i) {
            return this;
        }

        @Override // io.netty.handler.codec.redis.LastBulkStringRedisContent, io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder
        public LastBulkStringRedisContent retainedDuplicate() {
            return this;
        }

        @Override // io.netty.handler.codec.redis.LastBulkStringRedisContent, io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastBulkStringRedisContent touch() {
            return this;
        }

        @Override // io.netty.handler.codec.redis.LastBulkStringRedisContent, io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastBulkStringRedisContent touch(Object obj) {
            return this;
        }

        @Override // io.netty.buffer.ByteBufHolder
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override // io.netty.handler.codec.redis.LastBulkStringRedisContent, io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder
        public LastBulkStringRedisContent replace(ByteBuf byteBuf) {
            return new DefaultLastBulkStringRedisContent(byteBuf);
        }
    };

    @Override // io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder
    LastBulkStringRedisContent copy();

    @Override // io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder
    LastBulkStringRedisContent duplicate();

    @Override // io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder
    LastBulkStringRedisContent replace(ByteBuf byteBuf);

    @Override // io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastBulkStringRedisContent retain();

    @Override // io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastBulkStringRedisContent retain(int i);

    @Override // io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder
    LastBulkStringRedisContent retainedDuplicate();

    @Override // io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastBulkStringRedisContent touch();

    @Override // io.netty.handler.codec.redis.BulkStringRedisContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastBulkStringRedisContent touch(Object obj);
}
