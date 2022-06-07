package io.netty.channel;

/* loaded from: classes4.dex */
public interface ChannelInboundHandler extends ChannelHandler {
    void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception;

    void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception;

    void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception;

    void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception;

    void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception;

    void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception;

    void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception;

    @Override // 
    void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception;

    void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception;
}
