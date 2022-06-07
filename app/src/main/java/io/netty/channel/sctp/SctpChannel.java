package io.netty.channel.sctp;

import com.sun.nio.sctp.Association;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

/* loaded from: classes4.dex */
public interface SctpChannel extends Channel {
    Set<InetSocketAddress> allLocalAddresses();

    Set<InetSocketAddress> allRemoteAddresses();

    Association association();

    ChannelFuture bindAddress(InetAddress inetAddress);

    ChannelFuture bindAddress(InetAddress inetAddress, ChannelPromise channelPromise);

    @Override // io.netty.channel.Channel
    SctpChannelConfig config();

    @Override // io.netty.channel.Channel
    InetSocketAddress localAddress();

    @Override // io.netty.channel.Channel
    SctpServerChannel parent();

    @Override // io.netty.channel.Channel
    InetSocketAddress remoteAddress();

    ChannelFuture unbindAddress(InetAddress inetAddress);

    ChannelFuture unbindAddress(InetAddress inetAddress, ChannelPromise channelPromise);
}
