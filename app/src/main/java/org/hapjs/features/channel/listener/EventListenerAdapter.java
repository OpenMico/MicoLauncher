package org.hapjs.features.channel.listener;

import org.hapjs.features.channel.ChannelMessage;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes5.dex */
public class EventListenerAdapter implements ChannelEventListener {
    @Override // org.hapjs.features.channel.listener.ChannelEventListener
    public void onClose(IChannel iChannel, int i, String str) {
    }

    @Override // org.hapjs.features.channel.listener.ChannelEventListener
    public void onError(IChannel iChannel, int i, String str) {
    }

    @Override // org.hapjs.features.channel.listener.ChannelEventListener
    public void onOpen(IChannel iChannel) {
    }

    @Override // org.hapjs.features.channel.listener.ChannelEventListener
    public void onReceiveMessage(IChannel iChannel, ChannelMessage channelMessage) {
    }
}
