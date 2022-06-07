package io.netty.channel;

import java.net.SocketAddress;

/* loaded from: classes4.dex */
public interface ChannelOutboundInvoker {
    ChannelFuture bind(SocketAddress socketAddress);

    ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise);

    ChannelFuture close();

    ChannelFuture close(ChannelPromise channelPromise);

    ChannelFuture connect(SocketAddress socketAddress);

    ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise);

    ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2);

    ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise);

    ChannelFuture deregister();

    ChannelFuture deregister(ChannelPromise channelPromise);

    ChannelFuture disconnect();

    ChannelFuture disconnect(ChannelPromise channelPromise);

    ChannelOutboundInvoker flush();

    ChannelFuture newFailedFuture(Throwable th);

    ChannelProgressivePromise newProgressivePromise();

    ChannelPromise newPromise();

    ChannelFuture newSucceededFuture();

    ChannelOutboundInvoker read();

    ChannelPromise voidPromise();

    ChannelFuture write(Object obj);

    ChannelFuture write(Object obj, ChannelPromise channelPromise);

    ChannelFuture writeAndFlush(Object obj);

    ChannelFuture writeAndFlush(Object obj, ChannelPromise channelPromise);
}
