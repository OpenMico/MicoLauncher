package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpStatusClass;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class InboundHttp2ToHttpAdapter extends Http2EventAdapter {
    private static final a a = new a() { // from class: io.netty.handler.codec.http2.InboundHttp2ToHttpAdapter.1
        @Override // io.netty.handler.codec.http2.InboundHttp2ToHttpAdapter.a
        public boolean a(FullHttpMessage fullHttpMessage) {
            if (fullHttpMessage instanceof FullHttpResponse) {
                return ((FullHttpResponse) fullHttpMessage).status().codeClass() == HttpStatusClass.INFORMATIONAL;
            }
            if (fullHttpMessage instanceof FullHttpRequest) {
                return fullHttpMessage.headers().contains(HttpHeaderNames.EXPECT);
            }
            return false;
        }

        @Override // io.netty.handler.codec.http2.InboundHttp2ToHttpAdapter.a
        public FullHttpMessage b(FullHttpMessage fullHttpMessage) {
            if (!(fullHttpMessage instanceof FullHttpRequest)) {
                return null;
            }
            FullHttpRequest replace = ((FullHttpRequest) fullHttpMessage).replace(Unpooled.buffer(0));
            replace.headers().remove(HttpHeaderNames.EXPECT);
            return replace;
        }
    };
    private final int b;
    private final a c;
    protected final Http2Connection connection;
    private final Http2Connection.PropertyKey d;
    private final boolean e;
    protected final boolean validateHttpHeaders;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public interface a {
        boolean a(FullHttpMessage fullHttpMessage);

        FullHttpMessage b(FullHttpMessage fullHttpMessage);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public InboundHttp2ToHttpAdapter(Http2Connection http2Connection, int i, boolean z, boolean z2) {
        ObjectUtil.checkNotNull(http2Connection, "connection");
        if (i > 0) {
            this.connection = http2Connection;
            this.b = i;
            this.validateHttpHeaders = z;
            this.e = z2;
            this.c = a;
            this.d = http2Connection.newKey();
            return;
        }
        throw new IllegalArgumentException("maxContentLength: " + i + " (expected: > 0)");
    }

    protected final void removeMessage(Http2Stream http2Stream, boolean z) {
        FullHttpMessage fullHttpMessage = (FullHttpMessage) http2Stream.removeProperty(this.d);
        if (z && fullHttpMessage != null) {
            fullHttpMessage.release();
        }
    }

    protected final FullHttpMessage getMessage(Http2Stream http2Stream) {
        return (FullHttpMessage) http2Stream.getProperty(this.d);
    }

    protected final void putMessage(Http2Stream http2Stream, FullHttpMessage fullHttpMessage) {
        FullHttpMessage fullHttpMessage2 = (FullHttpMessage) http2Stream.setProperty(this.d, fullHttpMessage);
        if (fullHttpMessage2 != fullHttpMessage && fullHttpMessage2 != null) {
            fullHttpMessage2.release();
        }
    }

    @Override // io.netty.handler.codec.http2.Http2EventAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
    public void onStreamRemoved(Http2Stream http2Stream) {
        removeMessage(http2Stream, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void fireChannelRead(ChannelHandlerContext channelHandlerContext, FullHttpMessage fullHttpMessage, boolean z, Http2Stream http2Stream) {
        removeMessage(http2Stream, z);
        HttpUtil.setContentLength(fullHttpMessage, fullHttpMessage.content().readableBytes());
        channelHandlerContext.fireChannelRead((Object) fullHttpMessage);
    }

    protected FullHttpMessage newMessage(Http2Stream http2Stream, Http2Headers http2Headers, boolean z, ByteBufAllocator byteBufAllocator) throws Http2Exception {
        return this.connection.isServer() ? HttpConversionUtil.toFullHttpRequest(http2Stream.id(), http2Headers, byteBufAllocator, z) : HttpConversionUtil.toHttpResponse(http2Stream.id(), http2Headers, byteBufAllocator, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public FullHttpMessage processHeadersBegin(ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream, Http2Headers http2Headers, boolean z, boolean z2, boolean z3) throws Http2Exception {
        FullHttpMessage message = getMessage(http2Stream);
        FullHttpMessage fullHttpMessage = null;
        boolean z4 = false;
        if (message == null) {
            message = newMessage(http2Stream, http2Headers, this.validateHttpHeaders, channelHandlerContext.alloc());
            z4 = true;
        } else if (z2) {
            HttpConversionUtil.addHttp2ToHttpHeaders(http2Stream.id(), http2Headers, message, z3);
        } else {
            message = null;
        }
        if (!this.c.a(message)) {
            return message;
        }
        if (!z) {
            fullHttpMessage = this.c.b(message);
        }
        fireChannelRead(channelHandlerContext, message, z4, http2Stream);
        return fullHttpMessage;
    }

    private void a(ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream, FullHttpMessage fullHttpMessage, boolean z) {
        if (z) {
            fireChannelRead(channelHandlerContext, fullHttpMessage, getMessage(http2Stream) != fullHttpMessage, http2Stream);
        } else {
            putMessage(http2Stream, fullHttpMessage);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2EventAdapter, io.netty.handler.codec.http2.Http2FrameListener
    public int onDataRead(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z) throws Http2Exception {
        Http2Stream stream = this.connection.stream(i);
        FullHttpMessage message = getMessage(stream);
        if (message != null) {
            ByteBuf content = message.content();
            int readableBytes = byteBuf.readableBytes();
            if (content.readableBytes() <= this.b - readableBytes) {
                content.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
                if (z) {
                    fireChannelRead(channelHandlerContext, message, false, stream);
                }
                return readableBytes + i2;
            }
            throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, "Content length exceeded max of %d for stream id %d", Integer.valueOf(this.b), Integer.valueOf(i));
        }
        throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Data Frame received for unknown stream id %d", Integer.valueOf(i));
    }

    @Override // io.netty.handler.codec.http2.Http2EventAdapter, io.netty.handler.codec.http2.Http2FrameListener
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z) throws Http2Exception {
        Http2Stream stream = this.connection.stream(i);
        FullHttpMessage processHeadersBegin = processHeadersBegin(channelHandlerContext, stream, http2Headers, z, true, true);
        if (processHeadersBegin != null) {
            a(channelHandlerContext, stream, processHeadersBegin, z);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2EventAdapter, io.netty.handler.codec.http2.Http2FrameListener
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2) throws Http2Exception {
        Http2Stream stream = this.connection.stream(i);
        FullHttpMessage processHeadersBegin = processHeadersBegin(channelHandlerContext, stream, http2Headers, z2, true, true);
        if (processHeadersBegin != null) {
            a(channelHandlerContext, stream, processHeadersBegin, z2);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2EventAdapter, io.netty.handler.codec.http2.Http2FrameListener
    public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int i, long j) throws Http2Exception {
        Http2Stream stream = this.connection.stream(i);
        FullHttpMessage message = getMessage(stream);
        if (message != null) {
            onRstStreamRead(stream, message);
        }
        channelHandlerContext.fireExceptionCaught((Throwable) Http2Exception.streamError(i, Http2Error.valueOf(j), "HTTP/2 to HTTP layer caught stream reset", new Object[0]));
    }

    @Override // io.netty.handler.codec.http2.Http2EventAdapter, io.netty.handler.codec.http2.Http2FrameListener
    public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int i, int i2, Http2Headers http2Headers, int i3) throws Http2Exception {
        Http2Stream stream = this.connection.stream(i2);
        FullHttpMessage processHeadersBegin = processHeadersBegin(channelHandlerContext, stream, http2Headers, false, false, false);
        if (processHeadersBegin != null) {
            processHeadersBegin.headers().setInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_PROMISE_ID.text(), i);
            a(channelHandlerContext, stream, processHeadersBegin, false);
            return;
        }
        throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Push Promise Frame received for pre-existing stream id %d", Integer.valueOf(i2));
    }

    @Override // io.netty.handler.codec.http2.Http2EventAdapter, io.netty.handler.codec.http2.Http2FrameListener
    public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception {
        if (this.e) {
            channelHandlerContext.fireChannelRead((Object) http2Settings);
        }
    }

    protected void onRstStreamRead(Http2Stream http2Stream, FullHttpMessage fullHttpMessage) {
        removeMessage(http2Stream, true);
    }
}
