package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import org.eclipse.jetty.http.HttpMethods;

/* loaded from: classes4.dex */
public abstract class HttpContentEncoder extends MessageToMessageCodec<HttpRequest, HttpObject> {
    static final /* synthetic */ boolean a = !HttpContentEncoder.class.desiredAssertionStatus();
    private static final CharSequence b = "HEAD";
    private static final CharSequence c = HttpMethods.CONNECT;
    private static final int d = HttpResponseStatus.CONTINUE.code();
    private CharSequence f;
    private EmbeddedChannel g;
    private final Queue<CharSequence> e = new ArrayDeque();
    private a h = a.AWAIT_HEADERS;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public enum a {
        PASS_THROUGH,
        AWAIT_HEADERS,
        AWAIT_CONTENT
    }

    protected abstract Result beginEncode(HttpResponse httpResponse, String str) throws Exception;

    @Override // io.netty.handler.codec.MessageToMessageCodec
    protected /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest, List list) throws Exception {
        decode2(channelHandlerContext, httpRequest, (List<Object>) list);
    }

    @Override // io.netty.handler.codec.MessageToMessageCodec
    protected /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List list) throws Exception {
        encode2(channelHandlerContext, httpObject, (List<Object>) list);
    }

    @Override // io.netty.handler.codec.MessageToMessageCodec
    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return (obj instanceof HttpContent) || (obj instanceof HttpResponse);
    }

    /* renamed from: decode  reason: avoid collision after fix types in other method */
    protected void decode2(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest, List<Object> list) throws Exception {
        CharSequence charSequence = httpRequest.headers().get(HttpHeaderNames.ACCEPT_ENCODING);
        if (charSequence == null) {
            charSequence = HttpContentDecoder.a;
        }
        HttpMethod method = httpRequest.method();
        if (method == HttpMethod.HEAD) {
            charSequence = b;
        } else if (method == HttpMethod.CONNECT) {
            charSequence = c;
        }
        this.e.add(charSequence);
        list.add(ReferenceCountUtil.retain(httpRequest));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        boolean z = (httpObject instanceof HttpResponse) && (httpObject instanceof LastHttpContent);
        switch (this.h) {
            case AWAIT_HEADERS:
                a(httpObject);
                if (a || this.g == null) {
                    HttpResponse httpResponse = (HttpResponse) httpObject;
                    int code = httpResponse.status().code();
                    if (code == d) {
                        this.f = null;
                    } else {
                        this.f = this.e.poll();
                        if (this.f == null) {
                            throw new IllegalStateException("cannot send more responses than requests");
                        }
                    }
                    if (a(code, this.f)) {
                        if (z) {
                            list.add(ReferenceCountUtil.retain(httpResponse));
                            return;
                        }
                        list.add(httpResponse);
                        this.h = a.PASS_THROUGH;
                        return;
                    } else if (!z || ((ByteBufHolder) httpResponse).content().isReadable()) {
                        Result beginEncode = beginEncode(httpResponse, this.f.toString());
                        if (beginEncode != null) {
                            this.g = beginEncode.contentEncoder();
                            httpResponse.headers().set(HttpHeaderNames.CONTENT_ENCODING, beginEncode.targetContentEncoding());
                            httpResponse.headers().remove(HttpHeaderNames.CONTENT_LENGTH);
                            httpResponse.headers().set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
                            if (z) {
                                DefaultHttpResponse defaultHttpResponse = new DefaultHttpResponse(httpResponse.protocolVersion(), httpResponse.status());
                                defaultHttpResponse.headers().set(httpResponse.headers());
                                list.add(defaultHttpResponse);
                                break;
                            } else {
                                list.add(httpResponse);
                                this.h = a.AWAIT_CONTENT;
                                if (!(httpObject instanceof HttpContent)) {
                                    return;
                                }
                            }
                        } else if (z) {
                            list.add(ReferenceCountUtil.retain(httpResponse));
                            return;
                        } else {
                            list.add(httpResponse);
                            this.h = a.PASS_THROUGH;
                            return;
                        }
                    } else {
                        list.add(ReferenceCountUtil.retain(httpResponse));
                        return;
                    }
                } else {
                    throw new AssertionError();
                }
                break;
            case AWAIT_CONTENT:
                break;
            case PASS_THROUGH:
                b(httpObject);
                list.add(ReferenceCountUtil.retain(httpObject));
                if (httpObject instanceof LastHttpContent) {
                    this.h = a.AWAIT_HEADERS;
                    return;
                }
                return;
            default:
                return;
        }
        b(httpObject);
        if (a((HttpContent) httpObject, list)) {
            this.h = a.AWAIT_HEADERS;
        }
    }

    private static boolean a(int i, CharSequence charSequence) {
        return i < 200 || i == 204 || i == 304 || charSequence == b || (charSequence == c && i == 200);
    }

    private static void a(HttpObject httpObject) {
        if (!(httpObject instanceof HttpResponse)) {
            throw new IllegalStateException("unexpected message type: " + httpObject.getClass().getName() + " (expected: " + HttpResponse.class.getSimpleName() + ')');
        }
    }

    private static void b(HttpObject httpObject) {
        if (!(httpObject instanceof HttpContent)) {
            throw new IllegalStateException("unexpected message type: " + httpObject.getClass().getName() + " (expected: " + HttpContent.class.getSimpleName() + ')');
        }
    }

    private boolean a(HttpContent httpContent, List<Object> list) {
        a(httpContent.content(), list);
        if (!(httpContent instanceof LastHttpContent)) {
            return false;
        }
        a(list);
        HttpHeaders trailingHeaders = ((LastHttpContent) httpContent).trailingHeaders();
        if (trailingHeaders.isEmpty()) {
            list.add(LastHttpContent.EMPTY_LAST_CONTENT);
            return true;
        }
        list.add(new a(trailingHeaders));
        return true;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        a();
        super.handlerRemoved(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a();
        super.channelInactive(channelHandlerContext);
    }

    private void a() {
        EmbeddedChannel embeddedChannel = this.g;
        if (embeddedChannel != null) {
            if (embeddedChannel.finish()) {
                while (true) {
                    ByteBuf byteBuf = (ByteBuf) this.g.readOutbound();
                    if (byteBuf == null) {
                        break;
                    }
                    byteBuf.release();
                }
            }
            this.g = null;
        }
    }

    private void a(ByteBuf byteBuf, List<Object> list) {
        this.g.writeOutbound(byteBuf.retain());
        b(list);
    }

    private void a(List<Object> list) {
        if (this.g.finish()) {
            b(list);
        }
        this.g = null;
    }

    private void b(List<Object> list) {
        while (true) {
            ByteBuf byteBuf = (ByteBuf) this.g.readOutbound();
            if (byteBuf != null) {
                if (!byteBuf.isReadable()) {
                    byteBuf.release();
                } else {
                    list.add(new DefaultHttpContent(byteBuf));
                }
            } else {
                return;
            }
        }
    }

    /* loaded from: classes4.dex */
    public static final class Result {
        private final String a;
        private final EmbeddedChannel b;

        public Result(String str, EmbeddedChannel embeddedChannel) {
            if (str == null) {
                throw new NullPointerException("targetContentEncoding");
            } else if (embeddedChannel != null) {
                this.a = str;
                this.b = embeddedChannel;
            } else {
                throw new NullPointerException("contentEncoder");
            }
        }

        public String targetContentEncoding() {
            return this.a;
        }

        public EmbeddedChannel contentEncoder() {
            return this.b;
        }
    }
}
