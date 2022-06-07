package io.netty.channel.group;

import io.netty.channel.Channel;

/* loaded from: classes4.dex */
public interface ChannelMatcher {
    boolean matches(Channel channel);
}
