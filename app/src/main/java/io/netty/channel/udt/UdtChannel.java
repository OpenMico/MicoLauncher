package io.netty.channel.udt;

import io.netty.channel.Channel;
import java.net.InetSocketAddress;

/* loaded from: classes4.dex */
public interface UdtChannel extends Channel {
    @Override // io.netty.channel.Channel
    UdtChannelConfig config();

    @Override // io.netty.channel.Channel
    InetSocketAddress localAddress();

    @Override // io.netty.channel.Channel
    InetSocketAddress remoteAddress();
}
