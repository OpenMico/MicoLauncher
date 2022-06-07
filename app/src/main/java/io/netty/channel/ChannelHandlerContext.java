package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;
import io.netty.util.concurrent.EventExecutor;

/* loaded from: classes4.dex */
public interface ChannelHandlerContext extends ChannelInboundInvoker, ChannelOutboundInvoker, AttributeMap {
    ByteBufAllocator alloc();

    @Override // io.netty.util.AttributeMap
    @Deprecated
    <T> Attribute<T> attr(AttributeKey<T> attributeKey);

    Channel channel();

    EventExecutor executor();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelHandlerContext fireChannelActive();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelHandlerContext fireChannelInactive();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelHandlerContext fireChannelRead(Object obj);

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelHandlerContext fireChannelReadComplete();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelHandlerContext fireChannelRegistered();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelHandlerContext fireChannelUnregistered();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelHandlerContext fireChannelWritabilityChanged();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelHandlerContext fireExceptionCaught(Throwable th);

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelHandlerContext fireUserEventTriggered(Object obj);

    @Override // io.netty.channel.ChannelOutboundInvoker
    ChannelHandlerContext flush();

    ChannelHandler handler();

    @Override // io.netty.util.AttributeMap
    @Deprecated
    <T> boolean hasAttr(AttributeKey<T> attributeKey);

    boolean isRemoved();

    String name();

    ChannelPipeline pipeline();

    @Override // io.netty.channel.ChannelOutboundInvoker
    ChannelHandlerContext read();
}
