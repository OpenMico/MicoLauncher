package io.netty.channel;

/* loaded from: classes4.dex */
public interface ChannelInboundInvoker {
    ChannelInboundInvoker fireChannelActive();

    ChannelInboundInvoker fireChannelInactive();

    ChannelInboundInvoker fireChannelRead(Object obj);

    ChannelInboundInvoker fireChannelReadComplete();

    ChannelInboundInvoker fireChannelRegistered();

    ChannelInboundInvoker fireChannelUnregistered();

    ChannelInboundInvoker fireChannelWritabilityChanged();

    ChannelInboundInvoker fireExceptionCaught(Throwable th);

    ChannelInboundInvoker fireUserEventTriggered(Object obj);
}
