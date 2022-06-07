package org.hapjs.features.channel.listener;

import org.hapjs.features.channel.ChannelMessage;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes5.dex */
public interface ChannelEventListener {
    void onClose(IChannel iChannel, int i, String str);

    void onError(IChannel iChannel, int i, String str);

    void onOpen(IChannel iChannel);

    void onReceiveMessage(IChannel iChannel, ChannelMessage channelMessage);
}
