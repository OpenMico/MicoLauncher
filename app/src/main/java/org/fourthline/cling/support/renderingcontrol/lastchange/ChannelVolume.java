package org.fourthline.cling.support.renderingcontrol.lastchange;

import org.fourthline.cling.support.model.Channel;

/* loaded from: classes5.dex */
public class ChannelVolume {
    protected Channel channel;
    protected Integer volume;

    public ChannelVolume(Channel channel, Integer num) {
        this.channel = channel;
        this.volume = num;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public Integer getVolume() {
        return this.volume;
    }

    public String toString() {
        return "Volume: " + getVolume() + " (" + getChannel() + ")";
    }
}
