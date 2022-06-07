package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;

/* loaded from: classes4.dex */
public interface LastMemcacheContent extends MemcacheContent {
    public static final LastMemcacheContent EMPTY_LAST_CONTENT = new LastMemcacheContent() { // from class: io.netty.handler.codec.memcache.LastMemcacheContent.1
        @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
        public LastMemcacheContent duplicate() {
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

        @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastMemcacheContent retain() {
            return this;
        }

        @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastMemcacheContent retain(int i) {
            return this;
        }

        @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
        public LastMemcacheContent retainedDuplicate() {
            return this;
        }

        @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastMemcacheContent touch() {
            return this;
        }

        @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastMemcacheContent touch(Object obj) {
            return this;
        }

        @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
        public LastMemcacheContent copy() {
            return EMPTY_LAST_CONTENT;
        }

        @Override // io.netty.handler.codec.memcache.LastMemcacheContent, io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
        public LastMemcacheContent replace(ByteBuf byteBuf) {
            return new DefaultLastMemcacheContent(byteBuf);
        }

        @Override // io.netty.buffer.ByteBufHolder
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override // io.netty.handler.codec.DecoderResultProvider
        public DecoderResult decoderResult() {
            return DecoderResult.SUCCESS;
        }

        @Override // io.netty.handler.codec.DecoderResultProvider
        public void setDecoderResult(DecoderResult decoderResult) {
            throw new UnsupportedOperationException("read only");
        }
    };

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    LastMemcacheContent copy();

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    LastMemcacheContent duplicate();

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    LastMemcacheContent replace(ByteBuf byteBuf);

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastMemcacheContent retain();

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastMemcacheContent retain(int i);

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder
    LastMemcacheContent retainedDuplicate();

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastMemcacheContent touch();

    @Override // io.netty.handler.codec.memcache.MemcacheContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastMemcacheContent touch(Object obj);
}
