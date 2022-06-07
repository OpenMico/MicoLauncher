package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.MessageAggregator;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/* loaded from: classes4.dex */
public class HttpObjectAggregator extends MessageAggregator<HttpObject, HttpMessage, HttpContent, FullHttpMessage> {
    private final boolean f;
    static final /* synthetic */ boolean b = !HttpObjectAggregator.class.desiredAssertionStatus();
    private static final InternalLogger a = InternalLoggerFactory.getInstance(HttpObjectAggregator.class);
    private static final FullHttpResponse c = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER);
    private static final FullHttpResponse d = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.EXPECTATION_FAILED, Unpooled.EMPTY_BUFFER);
    private static final FullHttpResponse e = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE, Unpooled.EMPTY_BUFFER);

    static {
        d.headers().set((CharSequence) HttpHeaderNames.CONTENT_LENGTH, (Object) 0);
        e.headers().set((CharSequence) HttpHeaderNames.CONTENT_LENGTH, (Object) 0);
    }

    public HttpObjectAggregator(int i) {
        this(i, false);
    }

    public HttpObjectAggregator(int i, boolean z) {
        super(i);
        this.f = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isStartMessage(HttpObject httpObject) throws Exception {
        return httpObject instanceof HttpMessage;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isContentMessage(HttpObject httpObject) throws Exception {
        return httpObject instanceof HttpContent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isLastContentMessage(HttpContent httpContent) throws Exception {
        return httpContent instanceof LastHttpContent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isAggregated(HttpObject httpObject) throws Exception {
        return httpObject instanceof FullHttpMessage;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isContentLengthInvalid(HttpMessage httpMessage, int i) {
        return HttpUtil.getContentLength(httpMessage, -1L) > ((long) i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object newContinueResponse(HttpMessage httpMessage, int i, ChannelPipeline channelPipeline) {
        if (!HttpUtil.is100ContinueExpected(httpMessage)) {
            return null;
        }
        if (HttpUtil.getContentLength(httpMessage, -1L) <= i) {
            return c.retainedDuplicate();
        }
        channelPipeline.fireUserEventTriggered((Object) HttpExpectationFailedEvent.INSTANCE);
        return d.retainedDuplicate();
    }

    @Override // io.netty.handler.codec.MessageAggregator
    protected boolean closeAfterContinueResponse(Object obj) {
        return this.f && ignoreContentAfterContinueResponse(obj);
    }

    @Override // io.netty.handler.codec.MessageAggregator
    protected boolean ignoreContentAfterContinueResponse(Object obj) {
        return (obj instanceof HttpResponse) && ((HttpResponse) obj).status().code() == HttpResponseStatus.EXPECTATION_FAILED.code();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public FullHttpMessage beginAggregation(HttpMessage httpMessage, ByteBuf byteBuf) throws Exception {
        if (b || !(httpMessage instanceof FullHttpMessage)) {
            HttpUtil.setTransferEncodingChunked(httpMessage, false);
            if (httpMessage instanceof HttpRequest) {
                return new b((HttpRequest) httpMessage, byteBuf, null);
            }
            if (httpMessage instanceof HttpResponse) {
                return new c((HttpResponse) httpMessage, byteBuf, null);
            }
            throw new Error();
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void aggregate(FullHttpMessage fullHttpMessage, HttpContent httpContent) throws Exception {
        if (httpContent instanceof LastHttpContent) {
            ((a) fullHttpMessage).a(((LastHttpContent) httpContent).trailingHeaders());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void finishAggregation(FullHttpMessage fullHttpMessage) throws Exception {
        if (!HttpUtil.isContentLengthSet(fullHttpMessage)) {
            fullHttpMessage.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(fullHttpMessage.content().readableBytes()));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleOversizedMessage(final ChannelHandlerContext channelHandlerContext, HttpMessage httpMessage) throws Exception {
        if (httpMessage instanceof HttpRequest) {
            Future<Void> addListener = channelHandlerContext.writeAndFlush(e.retainedDuplicate()).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.http.HttpObjectAggregator.1
                /* renamed from: a */
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        HttpObjectAggregator.a.debug("Failed to send a 413 Request Entity Too Large.", channelFuture.cause());
                        channelHandlerContext.close();
                    }
                }
            });
            if ((httpMessage instanceof FullHttpMessage) || (!HttpUtil.is100ContinueExpected(httpMessage) && !HttpUtil.isKeepAlive(httpMessage))) {
                addListener.addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
            }
            HttpObjectDecoder httpObjectDecoder = (HttpObjectDecoder) channelHandlerContext.pipeline().get(HttpObjectDecoder.class);
            if (httpObjectDecoder != null) {
                httpObjectDecoder.reset();
            }
        } else if (httpMessage instanceof HttpResponse) {
            channelHandlerContext.close();
            throw new TooLongFrameException("Response entity too large: " + httpMessage);
        } else {
            throw new IllegalStateException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static abstract class a implements FullHttpMessage {
        protected final HttpMessage a;
        private final ByteBuf b;
        private HttpHeaders c;

        @Override // io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public abstract FullHttpMessage copy();

        @Override // io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public abstract FullHttpMessage duplicate();

        @Override // io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public abstract FullHttpMessage retainedDuplicate();

        a(HttpMessage httpMessage, ByteBuf byteBuf, HttpHeaders httpHeaders) {
            this.a = httpMessage;
            this.b = byteBuf;
            this.c = httpHeaders;
        }

        @Override // io.netty.handler.codec.http.LastHttpContent
        public HttpHeaders trailingHeaders() {
            HttpHeaders httpHeaders = this.c;
            return httpHeaders == null ? EmptyHttpHeaders.INSTANCE : httpHeaders;
        }

        void a(HttpHeaders httpHeaders) {
            this.c = httpHeaders;
        }

        @Override // io.netty.handler.codec.http.HttpMessage
        public HttpVersion getProtocolVersion() {
            return this.a.protocolVersion();
        }

        @Override // io.netty.handler.codec.http.HttpMessage
        public HttpVersion protocolVersion() {
            return this.a.protocolVersion();
        }

        /* renamed from: a */
        public FullHttpMessage setProtocolVersion(HttpVersion httpVersion) {
            this.a.setProtocolVersion(httpVersion);
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpMessage
        public HttpHeaders headers() {
            return this.a.headers();
        }

        @Override // io.netty.handler.codec.DecoderResultProvider
        public DecoderResult decoderResult() {
            return this.a.decoderResult();
        }

        @Override // io.netty.handler.codec.http.HttpObject
        public DecoderResult getDecoderResult() {
            return this.a.decoderResult();
        }

        @Override // io.netty.handler.codec.DecoderResultProvider
        public void setDecoderResult(DecoderResult decoderResult) {
            this.a.setDecoderResult(decoderResult);
        }

        @Override // io.netty.buffer.ByteBufHolder
        public ByteBuf content() {
            return this.b;
        }

        @Override // io.netty.util.ReferenceCounted
        public int refCnt() {
            return this.b.refCnt();
        }

        @Override // io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpMessage retain() {
            this.b.retain();
            return this;
        }

        @Override // io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpMessage retain(int i) {
            this.b.retain(i);
            return this;
        }

        @Override // io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpMessage touch(Object obj) {
            this.b.touch(obj);
            return this;
        }

        @Override // io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpMessage touch() {
            this.b.touch();
            return this;
        }

        @Override // io.netty.util.ReferenceCounted
        public boolean release() {
            return this.b.release();
        }

        @Override // io.netty.util.ReferenceCounted
        public boolean release(int i) {
            return this.b.release(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b extends a implements FullHttpRequest {
        b(HttpRequest httpRequest, ByteBuf byteBuf, HttpHeaders httpHeaders) {
            super(httpRequest, byteBuf, httpHeaders);
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpRequest copy() {
            return replace(content().copy());
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpRequest duplicate() {
            return replace(content().duplicate());
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpRequest retainedDuplicate() {
            return replace(content().retainedDuplicate());
        }

        @Override // io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpRequest replace(ByteBuf byteBuf) {
            DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(protocolVersion(), method(), uri(), byteBuf);
            defaultFullHttpRequest.headers().set(headers());
            defaultFullHttpRequest.trailingHeaders().set(trailingHeaders());
            return defaultFullHttpRequest;
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpRequest retain(int i) {
            super.retain(i);
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpRequest retain() {
            super.retain();
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpRequest touch() {
            super.touch();
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpRequest touch(Object obj) {
            super.touch(obj);
            return this;
        }

        @Override // io.netty.handler.codec.http.FullHttpRequest
        public FullHttpRequest setMethod(HttpMethod httpMethod) {
            ((HttpRequest) this.a).setMethod(httpMethod);
            return this;
        }

        @Override // io.netty.handler.codec.http.FullHttpRequest
        public FullHttpRequest setUri(String str) {
            ((HttpRequest) this.a).setUri(str);
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpRequest
        public HttpMethod getMethod() {
            return ((HttpRequest) this.a).method();
        }

        @Override // io.netty.handler.codec.http.HttpRequest
        public String getUri() {
            return ((HttpRequest) this.a).uri();
        }

        @Override // io.netty.handler.codec.http.HttpRequest
        public HttpMethod method() {
            return getMethod();
        }

        @Override // io.netty.handler.codec.http.HttpRequest
        public String uri() {
            return getUri();
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.HttpMessage, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
        public FullHttpRequest setProtocolVersion(HttpVersion httpVersion) {
            super.setProtocolVersion(httpVersion);
            return this;
        }

        public String toString() {
            return d.a(new StringBuilder(256), (FullHttpRequest) this).toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class c extends a implements FullHttpResponse {
        c(HttpResponse httpResponse, ByteBuf byteBuf, HttpHeaders httpHeaders) {
            super(httpResponse, byteBuf, httpHeaders);
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpResponse copy() {
            return replace(content().copy());
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpResponse duplicate() {
            return replace(content().duplicate());
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpResponse retainedDuplicate() {
            return replace(content().retainedDuplicate());
        }

        @Override // io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpResponse replace(ByteBuf byteBuf) {
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), byteBuf);
            defaultFullHttpResponse.headers().set(headers());
            defaultFullHttpResponse.trailingHeaders().set(trailingHeaders());
            return defaultFullHttpResponse;
        }

        @Override // io.netty.handler.codec.http.FullHttpResponse
        public FullHttpResponse setStatus(HttpResponseStatus httpResponseStatus) {
            ((HttpResponse) this.a).setStatus(httpResponseStatus);
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpResponse
        public HttpResponseStatus getStatus() {
            return ((HttpResponse) this.a).status();
        }

        @Override // io.netty.handler.codec.http.HttpResponse
        public HttpResponseStatus status() {
            return getStatus();
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.HttpMessage, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
        public FullHttpResponse setProtocolVersion(HttpVersion httpVersion) {
            super.setProtocolVersion(httpVersion);
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpResponse retain(int i) {
            super.retain(i);
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpResponse retain() {
            super.retain();
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpResponse touch(Object obj) {
            super.touch(obj);
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpObjectAggregator.a, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpResponse touch() {
            super.touch();
            return this;
        }

        public String toString() {
            return d.a(new StringBuilder(256), (FullHttpResponse) this).toString();
        }
    }
}
