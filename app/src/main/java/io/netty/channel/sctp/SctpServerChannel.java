package io.netty.channel.sctp;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ServerChannel;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

/* loaded from: classes4.dex */
public interface SctpServerChannel extends ServerChannel {
    Set<InetSocketAddress> allLocalAddresses();

    ChannelFuture bindAddress(InetAddress inetAddress);

    ChannelFuture bindAddress(InetAddress inetAddress, ChannelPromise channelPromise);

    @Override // io.netty.channel.Channel
    SctpServerChannelConfig config();

    @Override // io.netty.channel.Channel
    InetSocketAddress localAddress();

    ChannelFuture unbindAddress(InetAddress inetAddress);

    ChannelFuture unbindAddress(InetAddress inetAddress, ChannelPromise channelPromise);
}
