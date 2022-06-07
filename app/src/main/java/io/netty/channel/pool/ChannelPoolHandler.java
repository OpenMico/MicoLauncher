package io.netty.channel.pool;

import io.netty.channel.Channel;

/* loaded from: classes4.dex */
public interface ChannelPoolHandler {
    void channelAcquired(Channel channel) throws Exception;

    void channelCreated(Channel channel) throws Exception;

    void channelReleased(Channel channel) throws Exception;
}
