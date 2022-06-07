package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class HttpContentDecoder extends MessageToMessageDecoder<HttpObject> {
    static final String a = HttpHeaderValues.IDENTITY.toString();
    private EmbeddedChannel b;
    private boolean c;
    protected ChannelHandlerContext ctx;

    protected abstract EmbeddedChannel newContentDecoder(String str) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List list) throws Exception {
        decode2(channelHandlerContext, httpObject, (List<Object>) list);
    }

    /* renamed from: decode  reason: avoid collision after fix types in other method */
    protected void decode2(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        String str;
        HttpMessage httpMessage;
        if ((httpObject instanceof HttpResponse) && ((HttpResponse) httpObject).status().code() == 100) {
            if (!(httpObject instanceof LastHttpContent)) {
                this.c = true;
            }
            list.add(ReferenceCountUtil.retain(httpObject));
        } else if (this.c) {
            if (httpObject instanceof LastHttpContent) {
                this.c = false;
            }
            list.add(ReferenceCountUtil.retain(httpObject));
        } else {
            if (httpObject instanceof HttpMessage) {
                a();
                HttpMessage httpMessage2 = (HttpMessage) httpObject;
                HttpHeaders headers = httpMessage2.headers();
                String str2 = headers.get(HttpHeaderNames.CONTENT_ENCODING);
                if (str2 != null) {
                    str = str2.trim();
                } else {
                    str = a;
                }
                this.b = newContentDecoder(str);
                if (this.b == null) {
                    if (httpMessage2 instanceof HttpContent) {
                        ((HttpContent) httpMessage2).retain();
                    }
                    list.add(httpMessage2);
                    return;
                }
                headers.remove(HttpHeaderNames.CONTENT_LENGTH);
                String targetContentEncoding = getTargetContentEncoding(str);
                if (HttpHeaderValues.IDENTITY.contentEquals(targetContentEncoding)) {
                    headers.remove(HttpHeaderNames.CONTENT_ENCODING);
                } else {
                    headers.set(HttpHeaderNames.CONTENT_ENCODING, targetContentEncoding);
                }
                if (httpMessage2 instanceof HttpContent) {
                    if (httpMessage2 instanceof HttpRequest) {
                        HttpRequest httpRequest = (HttpRequest) httpMessage2;
                        httpMessage = new DefaultHttpRequest(httpRequest.protocolVersion(), httpRequest.method(), httpRequest.uri());
                    } else if (httpMessage2 instanceof HttpResponse) {
                        HttpResponse httpResponse = (HttpResponse) httpMessage2;
                        httpMessage = new DefaultHttpResponse(httpResponse.protocolVersion(), httpResponse.status());
                    } else {
                        throw new CodecException("Object of class " + httpMessage2.getClass().getName() + " is not a HttpRequest or HttpResponse");
                    }
                    httpMessage.headers().set(httpMessage2.headers());
                    httpMessage.setDecoderResult(httpMessage2.decoderResult());
                    list.add(httpMessage);
                } else {
                    list.add(httpMessage2);
                }
            }
            if (httpObject instanceof HttpContent) {
                HttpContent httpContent = (HttpContent) httpObject;
                if (this.b == null) {
                    list.add(httpContent.retain());
                } else {
                    a(httpContent, list);
                }
            }
        }
    }

    private void a(HttpContent httpContent, List<Object> list) {
        a(httpContent.content(), list);
        if (httpContent instanceof LastHttpContent) {
            a(list);
            HttpHeaders trailingHeaders = ((LastHttpContent) httpContent).trailingHeaders();
            if (trailingHeaders.isEmpty()) {
                list.add(LastHttpContent.EMPTY_LAST_CONTENT);
            } else {
                list.add(new a(trailingHeaders));
            }
        }
    }

    protected String getTargetContentEncoding(String str) throws Exception {
        return a;
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

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
        super.handlerAdded(channelHandlerContext);
    }

    private void a() {
        EmbeddedChannel embeddedChannel = this.b;
        if (embeddedChannel != null) {
            if (embeddedChannel.finish()) {
                while (true) {
                    ByteBuf byteBuf = (ByteBuf) this.b.readInbound();
                    if (byteBuf == null) {
                        break;
                    }
                    byteBuf.release();
                }
            }
            this.b = null;
        }
    }

    private void a(ByteBuf byteBuf, List<Object> list) {
        this.b.writeInbound(byteBuf.retain());
        b(list);
    }

    private void a(List<Object> list) {
        if (this.b.finish()) {
            b(list);
        }
        this.b = null;
    }

    private void b(List<Object> list) {
        while (true) {
            ByteBuf byteBuf = (ByteBuf) this.b.readInbound();
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
}
