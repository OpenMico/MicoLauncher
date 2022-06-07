package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;

/* loaded from: classes4.dex */
public interface LastHttpContent extends HttpContent {
    public static final LastHttpContent EMPTY_LAST_CONTENT = new LastHttpContent() { // from class: io.netty.handler.codec.http.LastHttpContent.1
        @Override // io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public LastHttpContent duplicate() {
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

        @Override // io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastHttpContent retain() {
            return this;
        }

        @Override // io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastHttpContent retain(int i) {
            return this;
        }

        @Override // io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public LastHttpContent retainedDuplicate() {
            return this;
        }

        public String toString() {
            return "EmptyLastHttpContent";
        }

        @Override // io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastHttpContent touch() {
            return this;
        }

        @Override // io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public LastHttpContent touch(Object obj) {
            return this;
        }

        @Override // io.netty.buffer.ByteBufHolder
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override // io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public LastHttpContent copy() {
            return EMPTY_LAST_CONTENT;
        }

        @Override // io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public LastHttpContent replace(ByteBuf byteBuf) {
            return new DefaultLastHttpContent(byteBuf);
        }

        @Override // io.netty.handler.codec.http.LastHttpContent
        public HttpHeaders trailingHeaders() {
            return EmptyHttpHeaders.INSTANCE;
        }

        @Override // io.netty.handler.codec.DecoderResultProvider
        public DecoderResult decoderResult() {
            return DecoderResult.SUCCESS;
        }

        @Override // io.netty.handler.codec.http.HttpObject
        @Deprecated
        public DecoderResult getDecoderResult() {
            return decoderResult();
        }

        @Override // io.netty.handler.codec.DecoderResultProvider
        public void setDecoderResult(DecoderResult decoderResult) {
            throw new UnsupportedOperationException("read only");
        }
    };

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    LastHttpContent copy();

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    LastHttpContent duplicate();

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    LastHttpContent replace(ByteBuf byteBuf);

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastHttpContent retain();

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastHttpContent retain(int i);

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    LastHttpContent retainedDuplicate();

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastHttpContent touch();

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    LastHttpContent touch(Object obj);

    HttpHeaders trailingHeaders();
}
