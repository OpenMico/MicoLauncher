package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public final class InboundHttp2ToHttpPriorityAdapter extends InboundHttp2ToHttpAdapter {
    private static final AsciiString a = new AsciiString(HttpConversionUtil.OUT_OF_MESSAGE_SEQUENCE_METHOD.toString());
    private static final AsciiString b = new AsciiString("");
    private static final AsciiString c = new AsciiString(HttpConversionUtil.OUT_OF_MESSAGE_SEQUENCE_RETURN_CODE.toString());
    private final Http2Connection.PropertyKey d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public InboundHttp2ToHttpPriorityAdapter(Http2Connection http2Connection, int i, boolean z, boolean z2) {
        super(http2Connection, i, z, z2);
        this.d = http2Connection.newKey();
    }

    private HttpHeaders a(Http2Stream http2Stream) {
        return (HttpHeaders) http2Stream.getProperty(this.d);
    }

    private void a(Http2Stream http2Stream, HttpHeaders httpHeaders) {
        http2Stream.setProperty(this.d, httpHeaders);
    }

    private HttpHeaders b(Http2Stream http2Stream) {
        return (HttpHeaders) http2Stream.removeProperty(this.d);
    }

    private static HttpHeaders a(FullHttpMessage fullHttpMessage) {
        return fullHttpMessage.content().isReadable() ? fullHttpMessage.trailingHeaders() : fullHttpMessage.headers();
    }

    private void b(Http2Stream http2Stream, HttpHeaders httpHeaders) {
        HttpHeaders a2 = a(http2Stream);
        if (a2 == null) {
            a(http2Stream, httpHeaders);
        } else {
            a2.setAll(httpHeaders);
        }
    }

    private void c(Http2Stream http2Stream, HttpHeaders httpHeaders) {
        HttpHeaders a2 = a(http2Stream);
        if (a2 != null) {
            httpHeaders.setAll(a2);
        }
    }

    private static void a(HttpHeaders httpHeaders) {
        httpHeaders.remove(HttpConversionUtil.ExtensionHeaderNames.STREAM_DEPENDENCY_ID.text());
        httpHeaders.remove(HttpConversionUtil.ExtensionHeaderNames.STREAM_WEIGHT.text());
    }

    private void a(Http2Headers http2Headers) {
        if (this.connection.isServer()) {
            http2Headers.method(a).path(b);
        } else {
            http2Headers.status(c);
        }
    }

    private static void a(HttpHeaders httpHeaders, Http2Headers http2Headers) {
        Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence = httpHeaders.iteratorCharSequence();
        while (iteratorCharSequence.hasNext()) {
            Map.Entry<CharSequence, CharSequence> next = iteratorCharSequence.next();
            http2Headers.add((Http2Headers) AsciiString.of(next.getKey()), AsciiString.of(next.getValue()));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.http2.InboundHttp2ToHttpAdapter
    public void fireChannelRead(ChannelHandlerContext channelHandlerContext, FullHttpMessage fullHttpMessage, boolean z, Http2Stream http2Stream) {
        c(http2Stream, a(fullHttpMessage));
        super.fireChannelRead(channelHandlerContext, fullHttpMessage, z, http2Stream);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.http2.InboundHttp2ToHttpAdapter
    public FullHttpMessage processHeadersBegin(ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream, Http2Headers http2Headers, boolean z, boolean z2, boolean z3) throws Http2Exception {
        FullHttpMessage processHeadersBegin = super.processHeadersBegin(channelHandlerContext, http2Stream, http2Headers, z, z2, z3);
        if (processHeadersBegin != null) {
            c(http2Stream, a(processHeadersBegin));
        }
        return processHeadersBegin;
    }

    @Override // io.netty.handler.codec.http2.Http2EventAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
    public void onPriorityTreeParentChanged(Http2Stream http2Stream, Http2Stream http2Stream2) {
        Http2Stream parent = http2Stream.parent();
        FullHttpMessage message = getMessage(http2Stream);
        if (message == null) {
            if (parent != null && !parent.equals(this.connection.connectionStream())) {
                DefaultHttpHeaders defaultHttpHeaders = new DefaultHttpHeaders();
                defaultHttpHeaders.setInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_DEPENDENCY_ID.text(), parent.id());
                b(http2Stream, defaultHttpHeaders);
            }
        } else if (parent == null) {
            a(message.headers());
            a(message.trailingHeaders());
        } else if (!parent.equals(this.connection.connectionStream())) {
            a(message).setInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_DEPENDENCY_ID.text(), parent.id());
        }
    }

    @Override // io.netty.handler.codec.http2.Http2EventAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
    public void onWeightChanged(Http2Stream http2Stream, short s) {
        HttpHeaders httpHeaders;
        FullHttpMessage message = getMessage(http2Stream);
        if (message == null) {
            httpHeaders = new DefaultHttpHeaders();
            b(http2Stream, httpHeaders);
        } else {
            httpHeaders = a(message);
        }
        httpHeaders.setShort(HttpConversionUtil.ExtensionHeaderNames.STREAM_WEIGHT.text(), http2Stream.weight());
    }

    @Override // io.netty.handler.codec.http2.Http2EventAdapter, io.netty.handler.codec.http2.Http2FrameListener
    public void onPriorityRead(ChannelHandlerContext channelHandlerContext, int i, int i2, short s, boolean z) throws Http2Exception {
        Http2Stream stream = this.connection.stream(i);
        if (stream != null && getMessage(stream) == null) {
            HttpHeaders b2 = b(stream);
            if (b2 != null) {
                DefaultHttp2Headers defaultHttp2Headers = new DefaultHttp2Headers(this.validateHttpHeaders, b2.size());
                a(defaultHttp2Headers);
                a(b2, defaultHttp2Headers);
                fireChannelRead(channelHandlerContext, newMessage(stream, defaultHttp2Headers, this.validateHttpHeaders, channelHandlerContext.alloc()), false, stream);
                return;
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Priority Frame recieved for unknown stream id %d", Integer.valueOf(i));
        }
    }
}
