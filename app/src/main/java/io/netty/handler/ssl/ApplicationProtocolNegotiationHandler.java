package io.netty.handler.ssl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/* loaded from: classes4.dex */
public abstract class ApplicationProtocolNegotiationHandler extends ChannelInboundHandlerAdapter {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(ApplicationProtocolNegotiationHandler.class);
    private final String b;

    protected abstract void configurePipeline(ChannelHandlerContext channelHandlerContext, String str) throws Exception;

    protected ApplicationProtocolNegotiationHandler(String str) {
        this.b = (String) ObjectUtil.checkNotNull(str, "fallbackProtocol");
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof SslHandshakeCompletionEvent) {
            channelHandlerContext.pipeline().remove(this);
            SslHandshakeCompletionEvent sslHandshakeCompletionEvent = (SslHandshakeCompletionEvent) obj;
            if (sslHandshakeCompletionEvent.isSuccess()) {
                SslHandler sslHandler = (SslHandler) channelHandlerContext.pipeline().get(SslHandler.class);
                if (sslHandler != null) {
                    String applicationProtocol = sslHandler.applicationProtocol();
                    if (applicationProtocol == null) {
                        applicationProtocol = this.b;
                    }
                    configurePipeline(channelHandlerContext, applicationProtocol);
                } else {
                    throw new IllegalStateException("cannot find a SslHandler in the pipeline (required for application-level protocol negotiation)");
                }
            } else {
                handshakeFailure(channelHandlerContext, sslHandshakeCompletionEvent.cause());
            }
        }
        channelHandlerContext.fireUserEventTriggered(obj);
    }

    protected void handshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        a.warn("{} TLS handshake failed:", channelHandlerContext.channel(), th);
        channelHandlerContext.close();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        a.warn("{} Failed to select the application-level protocol:", channelHandlerContext.channel(), th);
        channelHandlerContext.close();
    }
}
