package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http2.HttpConversionUtil;

/* loaded from: classes4.dex */
public class InboundHttpToHttp2Adapter extends ChannelInboundHandlerAdapter {
    private final Http2Connection a;
    private final Http2FrameListener b;

    public InboundHttpToHttp2Adapter(Http2Connection http2Connection, Http2FrameListener http2FrameListener) {
        this.a = http2Connection;
        this.b = http2FrameListener;
    }

    private int a(HttpHeaders httpHeaders) {
        return httpHeaders.getInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text(), this.a.remote().incrementAndGetNextStreamId());
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof FullHttpMessage) {
            FullHttpMessage fullHttpMessage = (FullHttpMessage) obj;
            try {
                int a = a(fullHttpMessage.headers());
                Http2Stream stream = this.a.stream(a);
                Http2Stream createStream = stream == null ? this.a.remote().createStream(a, false) : stream;
                fullHttpMessage.headers().set(HttpConversionUtil.ExtensionHeaderNames.SCHEME.text(), HttpScheme.HTTP.name());
                Http2Headers http2Headers = HttpConversionUtil.toHttp2Headers((HttpMessage) fullHttpMessage, true);
                boolean isReadable = fullHttpMessage.content().isReadable();
                boolean z = !fullHttpMessage.trailingHeaders().isEmpty();
                this.b.onHeadersRead(channelHandlerContext, a, http2Headers, 0, !isReadable && !z);
                if (isReadable) {
                    this.b.onDataRead(channelHandlerContext, a, fullHttpMessage.content(), 0, !z);
                }
                if (z) {
                    this.b.onHeadersRead(channelHandlerContext, a, HttpConversionUtil.toHttp2Headers(fullHttpMessage.trailingHeaders(), true), 0, true);
                }
                createStream.closeRemoteSide();
            } finally {
                fullHttpMessage.release();
            }
        } else {
            super.channelRead(channelHandlerContext, obj);
        }
    }
}
