package org.hapjs.features.channel;

import org.hapjs.features.channel.appinfo.HapApplication;
import org.hapjs.features.channel.listener.EventCallBack;

/* loaded from: classes5.dex */
public interface IHapChannel {
    void close(String str, EventCallBack eventCallBack);

    HapApplication getHapApplication();

    int getStatus();

    void send(ChannelMessage channelMessage, EventCallBack eventCallBack);
}
