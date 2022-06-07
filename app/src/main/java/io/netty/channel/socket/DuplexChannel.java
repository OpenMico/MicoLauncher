package io.netty.channel.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;

/* loaded from: classes4.dex */
public interface DuplexChannel extends Channel {
    boolean isInputShutdown();

    boolean isOutputShutdown();

    boolean isShutdown();

    ChannelFuture shutdown();

    ChannelFuture shutdown(ChannelPromise channelPromise);

    ChannelFuture shutdownInput();

    ChannelFuture shutdownInput(ChannelPromise channelPromise);

    ChannelFuture shutdownOutput();

    ChannelFuture shutdownOutput(ChannelPromise channelPromise);
}
