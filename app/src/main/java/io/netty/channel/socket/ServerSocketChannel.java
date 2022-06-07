package io.netty.channel.socket;

import io.netty.channel.ServerChannel;
import java.net.InetSocketAddress;

/* loaded from: classes4.dex */
public interface ServerSocketChannel extends ServerChannel {
    @Override // io.netty.channel.Channel
    ServerSocketChannelConfig config();

    @Override // io.netty.channel.Channel
    InetSocketAddress localAddress();

    @Override // io.netty.channel.Channel
    InetSocketAddress remoteAddress();
}
