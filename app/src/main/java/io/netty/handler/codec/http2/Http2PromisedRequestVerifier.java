package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;

/* loaded from: classes4.dex */
public interface Http2PromisedRequestVerifier {
    public static final Http2PromisedRequestVerifier ALWAYS_VERIFY = new Http2PromisedRequestVerifier() { // from class: io.netty.handler.codec.http2.Http2PromisedRequestVerifier.1
        @Override // io.netty.handler.codec.http2.Http2PromisedRequestVerifier
        public boolean isAuthoritative(ChannelHandlerContext channelHandlerContext, Http2Headers http2Headers) {
            return true;
        }

        @Override // io.netty.handler.codec.http2.Http2PromisedRequestVerifier
        public boolean isCacheable(Http2Headers http2Headers) {
            return true;
        }

        @Override // io.netty.handler.codec.http2.Http2PromisedRequestVerifier
        public boolean isSafe(Http2Headers http2Headers) {
            return true;
        }
    };

    boolean isAuthoritative(ChannelHandlerContext channelHandlerContext, Http2Headers http2Headers);

    boolean isCacheable(Http2Headers http2Headers);

    boolean isSafe(Http2Headers http2Headers);
}
