package io.netty.handler.codec.http2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultLastHttpContent;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.ReferenceCountUtil;
import java.util.List;

/* loaded from: classes4.dex */
public class Http2ServerDowngrader extends MessageToMessageCodec<Http2StreamFrame, HttpObject> {
    private final boolean a;

    @Override // io.netty.handler.codec.MessageToMessageCodec
    protected /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, Http2StreamFrame http2StreamFrame, List list) throws Exception {
        decode2(channelHandlerContext, http2StreamFrame, (List<Object>) list);
    }

    @Override // io.netty.handler.codec.MessageToMessageCodec
    protected /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List list) throws Exception {
        encode2(channelHandlerContext, httpObject, (List<Object>) list);
    }

    public Http2ServerDowngrader(boolean z) {
        this.a = z;
    }

    public Http2ServerDowngrader() {
        this(true);
    }

    @Override // io.netty.handler.codec.MessageToMessageCodec
    public boolean acceptInboundMessage(Object obj) throws Exception {
        return (obj instanceof Http2HeadersFrame) || (obj instanceof Http2DataFrame);
    }

    /* renamed from: decode  reason: avoid collision after fix types in other method */
    protected void decode2(ChannelHandlerContext channelHandlerContext, Http2StreamFrame http2StreamFrame, List<Object> list) throws Exception {
        if (http2StreamFrame instanceof Http2HeadersFrame) {
            Http2HeadersFrame http2HeadersFrame = (Http2HeadersFrame) http2StreamFrame;
            Http2Headers headers = http2HeadersFrame.headers();
            if (!http2HeadersFrame.isEndStream()) {
                list.add(HttpConversionUtil.toHttpRequest(0, http2HeadersFrame.headers(), this.a));
            } else if (headers.method() == null) {
                DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.a);
                HttpConversionUtil.addHttp2ToHttpHeaders(0, headers, defaultLastHttpContent.trailingHeaders(), HttpVersion.HTTP_1_1, true, true);
                list.add(defaultLastHttpContent);
            } else {
                list.add(HttpConversionUtil.toFullHttpRequest(0, headers, channelHandlerContext.alloc(), this.a));
            }
        } else if (http2StreamFrame instanceof Http2DataFrame) {
            Http2DataFrame http2DataFrame = (Http2DataFrame) http2StreamFrame;
            if (http2DataFrame.isEndStream()) {
                list.add(new DefaultLastHttpContent(http2DataFrame.content(), this.a));
            } else {
                list.add(new DefaultHttpContent(http2DataFrame.content()));
            }
        }
        ReferenceCountUtil.retain(http2StreamFrame);
    }

    private void a(LastHttpContent lastHttpContent, List<Object> list) {
        boolean z = !(lastHttpContent instanceof FullHttpResponse) && lastHttpContent.trailingHeaders().isEmpty();
        if (lastHttpContent.content().isReadable() || z) {
            list.add(new DefaultHttp2DataFrame(lastHttpContent.content(), lastHttpContent.trailingHeaders().isEmpty()));
        }
        if (!lastHttpContent.trailingHeaders().isEmpty()) {
            list.add(new DefaultHttp2HeadersFrame(HttpConversionUtil.toHttp2Headers(lastHttpContent.trailingHeaders(), this.a), true));
        }
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        boolean z;
        if (httpObject instanceof HttpResponse) {
            Http2Headers http2Headers = HttpConversionUtil.toHttp2Headers((HttpResponse) httpObject, this.a);
            if (httpObject instanceof FullHttpResponse) {
                FullHttpResponse fullHttpResponse = (FullHttpResponse) httpObject;
                z = !fullHttpResponse.content().isReadable() && fullHttpResponse.trailingHeaders().isEmpty();
            } else {
                z = false;
            }
            list.add(new DefaultHttp2HeadersFrame(http2Headers, z));
        }
        if (httpObject instanceof LastHttpContent) {
            a((LastHttpContent) httpObject, list);
        } else if (httpObject instanceof HttpContent) {
            list.add(new DefaultHttp2DataFrame(((HttpContent) httpObject).content(), false));
        }
        ReferenceCountUtil.retain(httpObject);
    }
}
