package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;

/* loaded from: classes4.dex */
public interface LastStompContentSubframe extends StompContentSubframe {
    public static final LastStompContentSubframe EMPTY_LAST_CONTENT = new LastStompContentSubframe() { // from class: io.netty.handler.codec.stomp.LastStompContentSubframe.1
        @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
        public LastStompContentSubframe duplicate() {
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

        @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastStompContentSubframe retain() {
            return this;
        }

        @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastStompContentSubframe retain(int i) {
            return this;
        }

        @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
        public LastStompContentSubframe retainedDuplicate() {
            return this;
        }

        @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastStompContentSubframe touch() {
            return this;
        }

        @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastStompContentSubframe touch(Object obj) {
            return this;
        }

        @Override // io.netty.buffer.ByteBufHolder
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
        public LastStompContentSubframe copy() {
            return EMPTY_LAST_CONTENT;
        }

        @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
        public LastStompContentSubframe replace(ByteBuf byteBuf) {
            return new DefaultLastStompContentSubframe(byteBuf);
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

    @Override // io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    LastStompContentSubframe copy();

    @Override // io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    LastStompContentSubframe duplicate();

    @Override // io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    LastStompContentSubframe replace(ByteBuf byteBuf);

    @Override // io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastStompContentSubframe retain();

    @Override // io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastStompContentSubframe retain(int i);

    @Override // io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    LastStompContentSubframe retainedDuplicate();

    @Override // io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastStompContentSubframe touch();

    @Override // io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastStompContentSubframe touch(Object obj);
}
